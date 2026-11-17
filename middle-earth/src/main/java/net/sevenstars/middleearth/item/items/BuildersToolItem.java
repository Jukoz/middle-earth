package net.sevenstars.middleearth.item.items;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.block.enums.BedPart;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.block.special.NpcDoor;
import net.sevenstars.middleearth.block.special.beds.BedOccupant;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.block.special.beds.BedTagHolder;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.block.special.structureManager.marker.StructureMarkerBlock;
import net.sevenstars.middleearth.block.special.structureManager.marker.StructureMarkerBlockEntity;
import net.sevenstars.middleearth.commands.custom.BuildSectionEditing;
import net.sevenstars.middleearth.commands.custom.StructureEditSession;
import net.sevenstars.middleearth.gui.structuremanager.bedtag.BedTagScreenData;
import net.sevenstars.middleearth.gui.structuremanager.bedtag.BedTagScreenHandler;
import net.sevenstars.middleearth.gui.structuremanager.structuremarker.StructureMarkerScreenData;
import net.sevenstars.middleearth.gui.structuremanager.structuremarker.StructureMarkerScreenHandler;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import net.sevenstars.middleearth.registries.content.structurefeatures.BuildFeatures;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Right-click a bed to choose who lives in it. Right-click a block the build treats as a feature, such as a seat, a
// store, a door or a place of work, to make it off limits to npcs, and right-click it again to undo that. Right-click
// anything else, or sneak and right-click, to place or edit a structure marker. Left-click a bed to select it, then
// left-click the blocks it works at or its store to link them. With no bed selected, sneak and left-click a block and
// then two corners to set which part of the build is saved, or to make the box a new named section. Left-click twice
// on a block where two sections meet to join them there with a pair of plugs. Left-click a plug to see what fits it,
// then sneak and left-click another plug to make the first one target it. Sneak and right-click a plug to turn it
// into its final-state block. Right-click a section's structure block to rename the section or remove its box.
// While the tool is held, the client draws what each of the build's blocks is recognized as
public class BuildersToolItem extends Item {
    // Left-clicks select beds and links and never break blocks
    @Override
    public boolean canMine(ItemStack stack, BlockState state, World world, BlockPos pos, LivingEntity user) {
        return false;
    }

    public BuildersToolItem(Settings settings) {
        super(settings);
    }

    public static boolean isHeldBy(PlayerEntity player) {
        return player.getMainHandStack().isOf(DecorativeItemsME.BUILDERS_TOOL) || player.getOffHandStack().isOf(DecorativeItemsME.BUILDERS_TOOL);
    }

    // A bed handles right-clicks itself before the item gets a chance, so the tool has to take the click first
    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            if(!isHeldBy(player))
                return ActionResult.PASS;
            BlockState state = world.getBlockState(hit.getBlockPos());
            boolean bed = state.getBlock() instanceof BedBlock;
            // A plug keeps its own screen, unless the builder sneaks. Then it's turned into its final-state block
            if(state.isOf(Blocks.JIGSAW) && StructureEditing.isBuildMode(player)) {
                if(!player.isSneaking())
                    return ActionResult.PASS;
                if(player instanceof ServerPlayerEntity serverPlayer && StructureEditing.mayEdit(player, hit.getBlockPos()))
                    BuildSectionEditing.unplug(serverPlayer.getEntityWorld(), serverPlayer, hit.getBlockPos());
                return ActionResult.SUCCESS;
            }
            // A section's structure block opens the tool's section screen instead of its own, which limits the box to 48
            boolean section = state.isOf(Blocks.STRUCTURE_BLOCK) && world.getBlockEntity(hit.getBlockPos()) instanceof StructureBlockBlockEntity block
                    && block.getMode() == StructureBlockMode.SAVE && block.hasStructureName();
            if(world.isClient()) {
                if(section && StructureEditing.isBuildMode(player))
                    StructureEditing.sectionScreen.accept(hit.getBlockPos().toImmutable());
                return StructureEditing.isBuildMode(player) ? ActionResult.SUCCESS : ActionResult.FAIL;
            }
            if(!(player instanceof ServerPlayerEntity serverPlayer) || !StructureEditing.isBuilder(player))
                return ActionResult.FAIL;
            if(section)
                return ActionResult.SUCCESS;
            if(!bed) {
                // The click itself makes a recognized block off limits or puts it back in use. When sneaking, the click
                // places a marker of any kind, as it does on any other block
                if(!player.isSneaking() && isRecognized(state))
                    toggleOffLimits(serverPlayer, hit.getBlockPos(), state, hit.getSide());
                else
                    openMarker(serverPlayer, hit, state);
                return ActionResult.SUCCESS;
            }
            // The tag is stored on the head half, which is the half builds are read from
            BlockPos head = state.get(BedBlock.PART) == BedPart.HEAD ? hit.getBlockPos() : hit.getBlockPos().offset(state.get(BedBlock.FACING));
            if(!(world.getBlockEntity(head) instanceof BedTagHolder holder))
                return ActionResult.FAIL;
            open(serverPlayer, head, holder.getBedTag());
            return ActionResult.SUCCESS;
        });
    }

    private static boolean isRecognized(BlockState state) {
        Block block = state.getBlock();
        return BuildFeatures.STORE.test(block) || BuildFeatures.SEAT.test(block) || BuildFeatures.TABLE.test(block) || BuildFeatures.DOOR.test(block)
                || BuildFeatures.LADDER.test(block) || BuildFeatures.stationOf(block) != null || state.contains(Properties.OPEN);
    }

    // Places an off limits marker beside the block, facing it, or removes the one that's already there. The marker has
    // to be in the same piece as the block so it's saved with it
    private static void toggleOffLimits(ServerPlayerEntity player, BlockPos clicked, BlockState state, Direction clickedSide) {
        ServerWorld world = player.getEntityWorld();
        // A door is marked on its lower half, whichever half was clicked
        BlockPos target = state.contains(Properties.DOUBLE_BLOCK_HALF) && state.get(Properties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER ? clicked.down() : clicked;
        for(Direction side : Direction.Type.HORIZONTAL) {
            BlockPos beside = target.offset(side);
            if(world.getBlockEntity(beside) instanceof StructureMarkerBlockEntity marker && marker.getKind() == MarkerKind.OFF_LIMITS
                    && world.getBlockState(beside).get(StructureMarkerBlock.FACING) == side.getOpposite()) {
                world.setBlockState(beside, world.getFluidState(beside).getBlockState(), Block.NOTIFY_ALL);
                StructureEditing.log("%s at %s is in use again, its off limits marker at %s taken away".formatted(state.getBlock().getName().getString(), target.toShortString(), beside.toShortString()));
                player.sendMessage(Text.translatable("ui.%s.structure_marker.in_use".formatted(MiddleEarth.MOD_ID), state.getBlock().getName()), true);
                return;
            }
        }
        StructureEditSession session = StructureEditSession.of(player.getUuid());
        StructureEditSession.Piece piece = session == null ? null : session.getPieces().stream().filter(found -> found.box().contains(target)).findFirst().orElse(null);
        List<Direction> sides = new ArrayList<>();
        sides.add(clickedSide.getAxis().isHorizontal() ? clickedSide : player.getHorizontalFacing().getOpposite());
        for(Direction side : Direction.Type.HORIZONTAL) {
            if(!sides.contains(side))
                sides.add(side);
        }
        for(Direction side : sides) {
            BlockPos beside = target.offset(side);
            if(!world.getBlockState(beside).isReplaceable() || piece != null && !piece.box().contains(beside))
                continue;
            world.setBlockState(beside, DecorativeBlockRegistryME.STRUCTURE_MARKER.getDefaultState().with(StructureMarkerBlock.FACING, side.getOpposite())
                    .with(StructureMarkerBlock.WATERLOGGED, world.getFluidState(beside).isEqualAndStill(Fluids.WATER)), Block.NOTIFY_ALL);
            if(world.getBlockEntity(beside) instanceof StructureMarkerBlockEntity marker)
                marker.update(MarkerKind.OFF_LIMITS, "", 1, false);
            StructureEditing.log("%s at %s put off limits by a marker at %s".formatted(state.getBlock().getName().getString(), target.toShortString(), beside.toShortString()));
            player.sendMessage(Text.translatable("ui.%s.structure_marker.off_limits".formatted(MiddleEarth.MOD_ID), state.getBlock().getName()), true);
            return;
        }
        StructureEditing.log("%s at %s not put off limits: no free block beside it%s".formatted(state.getBlock().getName().getString(), target.toShortString(), piece == null ? "" : " inside its piece"));
        player.sendMessage(Text.translatable("ui.%s.structure_marker.no_room".formatted(MiddleEarth.MOD_ID)), true);
    }

    private static void openMarker(ServerPlayerEntity player, BlockHitResult hit, BlockState state) {
        StructureMarkerScreenData data;
        if(state.isOf(DecorativeBlockRegistryME.STRUCTURE_MARKER) && player.getEntityWorld().getBlockEntity(hit.getBlockPos()) instanceof StructureMarkerBlockEntity marker) {
            data = marker.screenData();
        } else {
            BlockPos pos = hit.getBlockPos().offset(hit.getSide());
            if(!player.getEntityWorld().getBlockState(pos).isReplaceable()) {
                player.sendMessage(Text.translatable("ui.%s.structure_marker.no_room".formatted(MiddleEarth.MOD_ID)), true);
                return;
            }
            // On something that opens, the marker the builder most likely wants is the one that keeps npcs out
            boolean opens = state.getBlock() instanceof NpcDoor || state.contains(Properties.OPEN);
            data = StructureMarkerScreenData.create(pos, true, opens ? MarkerKind.OFF_LIMITS : MarkerKind.GUARD_POST, "", 1, false, hit.getSide().getAxis().isHorizontal() ? hit.getSide().getOpposite() : player.getHorizontalFacing());
        }
        player.openHandledScreen(new ExtendedScreenHandlerFactory<StructureMarkerScreenData>() {
            @Override
            public StructureMarkerScreenData getScreenOpeningData(ServerPlayerEntity opener) {
                return data;
            }

            @Override
            public Text getDisplayName() {
                return Text.translatable("screen.%s.structure_marker".formatted(MiddleEarth.MOD_ID));
            }

            @Nullable
            @Override
            public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity opener) {
                return new StructureMarkerScreenHandler(syncId, inventory, data);
            }
        });
    }

    private static void open(ServerPlayerEntity player, BlockPos head, @Nullable BedTag tag) {
        Optional<Identifier> place = tag == null ? Optional.empty() : tag.place();
        if(place.isEmpty())
            place = BedOccupant.likelyPlace(player.getEntityWorld(), head, player.getUuid());
        BedTagScreenData data = new BedTagScreenData(head, place.map(Identifier::toString).orElse(""), tag == null ? "" : tag.nestKind(),
                tag == null || tag.job().isEmpty() ? "" : tag.job().get().toString(),
                tag == null || tag.npcClass().isEmpty() ? "" : tag.npcClass().get().toString(), BedOccupant.all(player.getEntityWorld()));
        player.openHandledScreen(new ExtendedScreenHandlerFactory<BedTagScreenData>() {
            @Override
            public BedTagScreenData getScreenOpeningData(ServerPlayerEntity opener) {
                return data;
            }

            @Override
            public Text getDisplayName() {
                return Text.translatable("screen.%s.bed_tag".formatted(MiddleEarth.MOD_ID));
            }

            @Nullable
            @Override
            public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity opener) {
                return new BedTagScreenHandler(syncId, inventory, data);
            }
        });
    }
}
