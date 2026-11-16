package net.sevenstars.middleearth.item.items;

import net.sevenstars.middleearth.registries.content.structurefeatures.BuildFeatures;
import net.sevenstars.middleearth.commands.custom.StructureEditSession;
import net.sevenstars.middleearth.block.special.structureManager.marker.StructureMarkerBlock;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.minecraft.util.math.Direction;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.fluid.Fluids;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.Block;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.block.enums.StructureBlockMode;
import net.sevenstars.middleearth.commands.custom.BuildSectionEditing;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.state.property.Properties;
import net.sevenstars.middleearth.block.special.NpcDoor;
import net.sevenstars.middleearth.block.special.beds.BedOccupant;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.block.special.beds.BedTagHolder;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.block.special.structureManager.marker.StructureMarkerBlockEntity;
import net.sevenstars.middleearth.gui.structuremanager.structuremarker.StructureMarkerScreenData;
import net.sevenstars.middleearth.gui.structuremanager.structuremarker.StructureMarkerScreenHandler;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import net.sevenstars.middleearth.gui.structuremanager.bedtag.BedTagScreenData;
import net.sevenstars.middleearth.gui.structuremanager.bedtag.BedTagScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import net.minecraft.util.Identifier;
import java.util.ArrayList;
// Right-click a bed to say who lives in it. Right-click a block the build is taken to have, such as a seat, a store, a door
// or a place of work, to put it off limits to npcs, and again to give it back. Right-click anything else, or sneak and
// right-click, to place or edit a structure marker. Left-click a bed to pick it, and then the blocks it works at or its
// store to tie them. With no bed picked, sneak and left-click a block, and then two corners, to set what of its build
// is saved or to make the box a section of its own under a name. Left-click twice a block where two sections meet to
// join them there by a pair of plugs. Left-click a plug to see what fits it, and sneak and left-click another to make
// the first ask for that one. Sneak and right-click a plug to turn it back into the block it stands for. Right-click the
// structure block of a section to name it anew or take its box away.
// While the tool is held, the client draws what the build's blocks are taken for
public class BuildersToolItem extends Item {
    // Left-clicks are for picking beds and links, never for breaking blocks
    @Override
    public boolean canMine(ItemStack stack, BlockState state, World world, BlockPos pos, LivingEntity user) {
        return false;
    }

    public BuildersToolItem(Settings settings) {
        super(settings);
    }

    // A bed answers a right-click itself before any item is asked, so the tool has to take the click first
    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            if(!player.getMainHandStack().isOf(DecorativeItemsME.BUILDERS_TOOL) && !player.getOffHandStack().isOf(DecorativeItemsME.BUILDERS_TOOL))
                return ActionResult.PASS;
            BlockState state = world.getBlockState(hit.getBlockPos());
            boolean bed = state.getBlock() instanceof BedBlock;
            // A plug keeps its own screen, unless the builder sneaks: then it is turned back into the block it stands for
            if(state.isOf(Blocks.JIGSAW) && StructureEditing.isBuildMode(player)) {
                if(!player.isSneaking())
                    return ActionResult.PASS;
                if(player instanceof ServerPlayerEntity serverPlayer && StructureEditing.mayEdit(player, hit.getBlockPos()))
                    BuildSectionEditing.unplug(serverPlayer.getWorld(), serverPlayer, hit.getBlockPos());
                return ActionResult.SUCCESS;
            }
            // The structure block of a section opens the tool's screen of it instead of its own, which cuts a box down to 48
            boolean section = state.isOf(Blocks.STRUCTURE_BLOCK) && world.getBlockEntity(hit.getBlockPos()) instanceof StructureBlockBlockEntity block
                    && block.getMode() == StructureBlockMode.SAVE && block.hasStructureName();
            if(world.isClient) {
                if(section && StructureEditing.isBuildMode(player))
                    StructureEditing.sectionScreen.accept(hit.getBlockPos().toImmutable());
                return StructureEditing.isBuildMode(player) ? ActionResult.SUCCESS : ActionResult.FAIL;
            }
            if(!(player instanceof ServerPlayerEntity serverPlayer) || !StructureEditing.isBuilder(player))
                return ActionResult.FAIL;
            if(section)
                return ActionResult.SUCCESS;
            if(!bed) {
                // What the build is taken to have is put off limits, or back in use, by the click itself. Sneaking, the
                // click is for a marker of any kind as it is on any other block
                if(!player.isSneaking() && isRecognized(state))
                    toggleOffLimits(serverPlayer, hit.getBlockPos(), state, hit.getSide());
                else
                    openMarker(serverPlayer, hit, state);
                return ActionResult.SUCCESS;
            }
            // The tag is kept on the head, which is the half the builds are read by
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

    // An off limits marker is put beside the block, facing it, or the one that is there is taken away again. The marker has
    // to stand in the piece the block is in to be saved with it
    private static void toggleOffLimits(ServerPlayerEntity player, BlockPos clicked, BlockState state, Direction clickedSide) {
        ServerWorld world = player.getWorld();
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
        if(state.isOf(DecorativeBlockRegistryME.STRUCTURE_MARKER) && player.getWorld().getBlockEntity(hit.getBlockPos()) instanceof StructureMarkerBlockEntity marker) {
            data = marker.screenData();
        } else {
            BlockPos pos = hit.getBlockPos().offset(hit.getSide());
            if(!player.getWorld().getBlockState(pos).isReplaceable()) {
                player.sendMessage(Text.translatable("ui.%s.structure_marker.no_room".formatted(MiddleEarth.MOD_ID)), true);
                return;
            }
            // On something that opens, the marker to put before it is most likely the one that keeps npcs out
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
            place = BedOccupant.likelyPlace(player.getWorld(), head, player.getUuid());
        BedTagScreenData data = new BedTagScreenData(head, place.map(Identifier::toString).orElse(""), tag == null ? "" : tag.nestKind(),
                tag == null || tag.job().isEmpty() ? "" : tag.job().get().toString(),
                tag == null || tag.npcClass().isEmpty() ? "" : tag.npcClass().get().toString(), BedOccupant.all(player.getWorld()));
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
