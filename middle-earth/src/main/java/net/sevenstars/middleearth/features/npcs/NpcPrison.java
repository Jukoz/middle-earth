package net.sevenstars.middleearth.features.npcs;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.block.special.structureManager.features.NestHost;
import net.sevenstars.middleearth.features.npcs.ai.NpcBrain;
import net.sevenstars.middleearth.features.npcs.relations.NpcRelations;
import net.sevenstars.middleearth.features.npcs.routine.PrisonCell;
import net.sevenstars.middleearth.features.npcs.routine.RoutineSite;
import net.sevenstars.middleearth.features.npcs.routine.RoutineSites;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class NpcPrison {
    // In ticks
    private static final int CHECK_INTERVAL = 20;
    private static final int ESCAPE_RANGE = 16;
    // Blocks from the cell
    private static final int STRAY_REACH = 3;
    private static final int STRAY_CHECKS = 3;
    // Blocks from the cell's middle to aim for, tried in random directions
    private static final int ESCAPE_DISTANCE = 16;
    private static final int ESCAPE_TRIES = 2;
    private static final double ESCAPE_SPEED = 1.2;
    // Blocks; a rescuer further off is lost
    private static final double RESCUER_RANGE = 16;

    private final NpcEntity npc;
    private @Nullable PrisonCell cell = null;
    private @Nullable Path returnPath = null;
    private int strayChecks = 0;
    private boolean escaping = false;
    private @Nullable UUID rescuer = null;
    private ItemStack confiscatedMain = ItemStack.EMPTY;
    private ItemStack confiscatedOff = ItemStack.EMPTY;

    NpcPrison(NpcEntity npc) {
        this.npc = npc;
    }

    public @Nullable PrisonCell getCell() {
        return cell;
    }

    public void setCell(@Nullable PrisonCell cell) {
        this.cell = cell;
    }

    public boolean isConfined() {
        return cell != null && npc.isPrisoner();
    }

    public boolean isEscaping() {
        return escaping;
    }

    // Follows the player out once they open the way. Refused by someone its people are at war with
    public boolean followRescuer(PlayerEntity player) {
        if(!isConfined() || NpcRelations.isRescueRefused(npc, player))
            return false;
        rescuer = player.getUuid();
        escaping = true;
        return true;
    }

    public @Nullable PlayerEntity getRescuer() {
        if(rescuer == null)
            return null;
        PlayerEntity player = npc.getEntityWorld().getPlayerByUuid(rescuer);
        if(player != null && player.isAlive() && npc.squaredDistanceTo(player) <= RESCUER_RANGE * RESCUER_RANGE)
            return player;
        rescuer = null;
        escaping = false;
        return null;
    }

    // The way back for a stray, which PrisonerBehaviour walks
    public @Nullable Path takeReturnPath() {
        Path path = returnPath;
        returnPath = null;
        return path;
    }

    void tick(ServerWorld world) {
        boolean checkTime = npc.age % CHECK_INTERVAL == 0;
        if(checkTime && npc.isPrisoner())
            disarm();
        // Older saves have no cell, so hold it in the room by its bed
        if(cell == null && npc.isPrisoner()) {
            BlockPos bedStand = npc.getBedStand();
            cell = PrisonCell.around(world, bedStand != null ? bedStand : npc.getBlockPos());
        }
        if(cell != null && checkTime)
            checkCell(world);
    }

    void disarm() {
        ItemStack main = npc.getMainHandStack();
        ItemStack off = npc.getOffHandStack();
        if(main.isEmpty() && off.isEmpty())
            return;
        if(!main.isEmpty() && confiscatedMain.isEmpty())
            confiscatedMain = main.copy();
        if(!off.isEmpty() && confiscatedOff.isEmpty())
            confiscatedOff = off.copy();
        npc.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        npc.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        npc.updateAttackType();
    }

    private void rearm() {
        if(!confiscatedMain.isEmpty())
            npc.equipStack(EquipmentSlot.MAINHAND, confiscatedMain);
        if(!confiscatedOff.isEmpty())
            npc.equipStack(EquipmentSlot.OFFHAND, confiscatedOff);
        confiscatedMain = ItemStack.EMPTY;
        confiscatedOff = ItemStack.EMPTY;
        npc.updateAttackType();
    }

    private void checkCell(ServerWorld world) {
        returnPath = null;
        if(!npc.isPrisoner()) {
            cell = null;
            return;
        }
        if(cell.contains(npc.getBlockPos())) {
            strayChecks = 0;
            if(npc.getNavigation().isIdle() && rescuer == null)
                escaping = false;
            return;
        }
        // Only an escapee or a prisoner taken well away goes free. A stray walks back in, or is put back by its bed if it can't
        if(escaping || cell.distanceTo(npc.getBlockPos()) > STRAY_REACH) {
            strayChecks = 0;
            free(world);
            return;
        }
        Path back = npc.getNavigation().findPathTo(cell.centerX(), npc.getY(), cell.centerZ(), 0);
        BlockPos bedStand = npc.getBedStand();
        if(++strayChecks <= STRAY_CHECKS && back != null && back.reachesTarget()) {
            returnPath = back;
        } else if(bedStand != null && cell.contains(bedStand)) {
            npc.getNavigation().stop();
            npc.requestTeleport(bedStand.getX() + 0.5, bedStand.getY(), bedStand.getZ() + 0.5);
            strayChecks = 0;
        }
    }

    // Prisoners never open doors, so a path out means a gap, an open door or a broken wall
    public boolean tryEscape() {
        if(!isConfined())
            return false;
        for(int i = 0; i < ESCAPE_TRIES; i++) {
            float angle = npc.getRandom().nextFloat() * MathHelper.TAU;
            BlockPos goal = BlockPos.ofFloored(cell.centerX() + MathHelper.cos(angle) * ESCAPE_DISTANCE, npc.getY(), cell.centerZ() + MathHelper.sin(angle) * ESCAPE_DISTANCE);
            Path path = npc.getNavigation().findPathTo(goal, 1);
            // A partial path counts, as long as it gets well clear
            if(path != null && path.getEnd() != null && cell.distanceTo(path.getEnd().getBlockPos()) > STRAY_REACH) {
                escaping = true;
                npc.getNavigation().startMovingAlong(path, ESCAPE_SPEED);
                return true;
            }
        }
        return false;
    }

    // Out of its cell it goes back to its own people and flees the place that held it
    private void free(ServerWorld world) {
        escaping = false;
        rescuer = null;
        BlockPos managerPos = npc.getHostPos();
        boolean managerLoaded = managerPos != null && world.isChunkLoaded(managerPos.getX() >> 4, managerPos.getZ() >> 4);
        Vec3d placeCentre = managerLoaded ? centreOfSite(world, managerPos) : new Vec3d(cell.centerX(), 0, cell.centerZ());
        if(managerLoaded)
            NestHost.signalGone(world, managerPos, npc);
        cell = null;
        npc.setJob(null);
        rearm();
        npc.saveNpcData(npc.retrieveNpcData().withoutHostPos());
        npc.getHousehold().clear();
        npc.getNavigation().stop();
        npc.getParty().setTravelDestination(null);
        npc.getParty().setTravelHeading(headingAwayFrom(placeCentre));
        escape(world);
    }

    private Vec3d centreOfSite(ServerWorld world, BlockPos managerPos) {
        RoutineSite site = RoutineSites.forManager(world, managerPos);
        if(site == null)
            return new Vec3d(cell.centerX(), 0, cell.centerZ());
        return new Vec3d(site.getCenterX() + 0.5, 0, site.getCenterZ() + 0.5);
    }

    private float headingAwayFrom(Vec3d place) {
        double awayX = npc.getX() - place.x;
        double awayZ = npc.getZ() - place.z;
        if(awayX == 0 && awayZ == 0)
            return npc.getRandom().nextFloat() * 360f;
        return (float) (MathHelper.atan2(-awayX, awayZ) * MathHelper.DEGREES_PER_RADIAN);
    }

    // Fights a lone guard if armed, otherwise runs. Common folk don't count
    private void escape(ServerWorld world) {
        List<LivingEntity> enemies = world.getEntitiesByClass(LivingEntity.class, npc.getBoundingBox().expand(ESCAPE_RANGE),
                other -> other.isAlive() && !(other instanceof NpcEntity candidate && candidate.isTimid()) && npc.canTarget(other) && npc.canSee(other));
        LivingEntity nearest = null;
        for(LivingEntity enemy : enemies)
            if(nearest == null || npc.squaredDistanceTo(enemy) < npc.squaredDistanceTo(nearest))
                nearest = enemy;
        if(nearest == null)
            return;
        if(enemies.size() == 1 && !npc.getMainHandStack().isEmpty())
            npc.setTarget(nearest);
        else
            NpcBrain.flee(npc, nearest);
    }

    void write(WriteView view) {
        view.putNullable(NpcEntity.KeyStrings.PRISON_CELL, PrisonCell.CODEC, cell);
        view.put(NpcEntity.KeyStrings.CONFISCATED_MAIN, ItemStack.OPTIONAL_CODEC, confiscatedMain);
        view.put(NpcEntity.KeyStrings.CONFISCATED_OFF, ItemStack.OPTIONAL_CODEC, confiscatedOff);
    }

    void read(ReadView view) {
        cell = view.read(NpcEntity.KeyStrings.PRISON_CELL, PrisonCell.CODEC).orElse(null);
        confiscatedMain = view.read(NpcEntity.KeyStrings.CONFISCATED_MAIN, ItemStack.OPTIONAL_CODEC).orElse(ItemStack.EMPTY);
        confiscatedOff = view.read(NpcEntity.KeyStrings.CONFISCATED_OFF, ItemStack.OPTIONAL_CODEC).orElse(ItemStack.EMPTY);
    }
}
