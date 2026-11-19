package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.HoeItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldEvents;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.REST;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.REST_VARIATION;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.isLoaded;
import static net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.itemName;
import static net.sevenstars.middleearth.features.npcs.routine.StationActivity.WORK_RANGE;

class FarmingActivity extends RoutineActivity {
    private static final int FARMING_TIME = 20 * 90;
    private static final int FARMING_TIME_VARIATION = 20 * 60;

    private static final double FIELD_REACH = 3;
    // Plots this close to the bed's linked field are worked first
    private static final int LINKED_FIELD_RANGE = 8;

    private static final int FARM_ACT_ONE_IN = 2;

    @Nullable BlockPos fieldCell = null;
    private long farmingEnd = 0;
    // Plots found walled off. Skipped until it next sets out to farm
    private final Set<BlockPos> hiddenPlots = new HashSet<>();

    FarmingActivity(NpcRoutine routine) {
        super(routine);
    }

    @Override
    boolean isSupportedBy(RoutineSite site, RoutineData.Activity kind) {
        return site.hasFields();
    }

    @Override
    boolean isPossible(ServerWorld world, RoutineData routineData, BlockPos home, long time) {
        return fieldsNeedWork(world, home);
    }

    @Override
    boolean isTiedToSpot() {
        return false;
    }

    @Override
    boolean isFarming() {
        return true;
    }

    @Override
    @Nullable Item prop(RoutineData.Activity kind) {
        return npc.getMainHandStack().getItem() instanceof HoeItem ? null : Items.IRON_HOE;
    }

    @Override
    String label(RoutineData.Activity kind) {
        return fieldCell == null ? "farming" : "farming at " + fieldCell.toShortString();
    }

    @Override
    boolean begin(ServerWorld world, RoutineData.Activity picked, BlockPos home) {
        Random random = npc.getRandom();
        hiddenPlots.clear();
        fieldCell = nextFieldCell(world, home, npc.getBlockPos());
        if(fieldCell == null)
            return routine.cannotStart(picked, "no field near home needs work");
        routine.setSpot(fieldCell.up());
        farmingEnd = world.getTime() + FARMING_TIME + random.nextInt(FARMING_TIME_VARIATION);
        return true;
    }

    @Override
    void arrive(ServerWorld world, Random random, long time) {
        routine.setPhaseEnd(farmingEnd);
    }

    @Override
    void work(ServerWorld world, Random random, boolean done, long time) {
        if(done) {
            endFarming(world);
            return;
        }
        if(npc.getRandom().nextInt(FARM_ACT_ONE_IN) != 0)
            return;
        if(fieldCell != null && fieldCell.isWithinDistance(npc.getEntityPos(), FIELD_REACH)) {
            if(!routine.canSee(world, fieldCell.up()))
                hiddenPlots.add(fieldCell);
            else if(tend(world, fieldCell))
                return;
        }
        BlockPos home = npc.getRoutineHome() != null ? npc.getRoutineHome() : npc.getBlockPos();
        fieldCell = nextFieldCell(world, home, npc.getBlockPos());
        if(fieldCell == null) {
            endFarming(world);
        } else if(!fieldCell.isWithinDistance(npc.getEntityPos(), FIELD_REACH)) {
            routine.walkTo(fieldCell.up());
        }
    }

    private void endFarming(ServerWorld world) {
        fieldCell = null;
        if(routine.carried() != null)
            routine.startReturn(world);
        else
            routine.finish(world, REST + npc.getRandom().nextInt(REST_VARIATION));
    }

    private @Nullable BlockPos nextFieldCell(ServerWorld world, BlockPos home, BlockPos from) {
        BlockPos best = null;
        boolean bestLinked = false;
        for(BlockPos cell : routine.site.getFields(home, WORK_RANGE)) {
            if(hiddenPlots.contains(cell) || !isLoaded(world, cell) || !FarmPlots.needsWork(world, cell))
                continue;
            boolean linked = isByLinkedField(cell);
            if(best == null || linked && !bestLinked || linked == bestLinked && cell.getSquaredDistance(from) < best.getSquaredDistance(from)) {
                best = cell;
                bestLinked = linked;
            }
        }
        return best;
    }

    private boolean isByLinkedField(BlockPos cell) {
        for(BlockPos link : npc.getLinks()) {
            if(link.isWithinDistance(cell, LINKED_FIELD_RANGE))
                return true;
        }
        return false;
    }

    boolean fieldsNeedWork(ServerWorld world, BlockPos home) {
        for(BlockPos cell : routine.site.getFields(home, WORK_RANGE)) {
            if(isLoaded(world, cell) && FarmPlots.needsWork(world, cell))
                return true;
        }
        return false;
    }

    private boolean tend(ServerWorld world, BlockPos cell) {
        BlockState ground = world.getBlockState(cell);
        BlockPos up = cell.up();
        BlockState above = world.getBlockState(up);
        if(!(ground.getBlock() instanceof FarmlandBlock)) {
            if(!FarmPlots.isTillable(ground) || !(above.isAir() || above.isReplaceable()))
                return false;
            if(!above.isAir())
                world.breakBlock(up, false);
            world.setBlockState(cell, Blocks.FARMLAND.getDefaultState());
            world.playSound(null, cell, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1f, 1f);
            routine.did("tilled the plot at " + cell.toShortString());
        } else if(above.isAir()) {
            Block sown = FarmPlots.cropFor(world, cell);
            world.setBlockState(up, sown.getDefaultState());
            world.playSound(null, up, SoundEvents.ITEM_CROP_PLANT, SoundCategory.BLOCKS, 1f, 1f);
            routine.did("sowed " + Registries.BLOCK.getId(sown).getPath() + " at " + cell.toShortString());
        } else if(above.getBlock() instanceof CropBlock crop && crop.isMature(above)) {
            routine.setCarried(FarmPlots.harvestOf(world, up, above));
            routine.did("harvested " + itemName(routine.carried()) + " at " + cell.toShortString());
            world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, up, Block.getRawIdFromState(above));
            world.setBlockState(up, crop.withAge(0));
        } else {
            return false;
        }
        npc.getLookControl().lookAt(cell.getX() + 0.5, cell.getY() + 1, cell.getZ() + 0.5);
        npc.swingHand(Hand.MAIN_HAND);
        return true;
    }
}
