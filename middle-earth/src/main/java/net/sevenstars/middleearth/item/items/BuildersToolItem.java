package net.sevenstars.middleearth.item.items;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
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

// Right-click a bed to say who is born in it, anything else to place or edit a structure marker. Left-click a bed to pick it and then the blocks it works at to link them. While it is held, the client draws what the build's blocks are taken for
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
            if(world.isClient)
                return player.isCreative() ? ActionResult.SUCCESS : ActionResult.FAIL;
            if(!(player instanceof ServerPlayerEntity serverPlayer) || !player.isCreative() || !serverPlayer.isCreativeLevelTwoOp())
                return ActionResult.FAIL;
            if(!bed) {
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
            data = StructureMarkerScreenData.create(pos, true, MarkerKind.GUARD_POST, "", 1, false, player.getHorizontalFacing());
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
        BedTagScreenData data = new BedTagScreenData(head, tag == null ? "" : tag.nestKind(), tag == null || tag.job().isEmpty() ? "" : tag.job().get().toString(),
                tag == null || tag.npcClass().isEmpty() ? "" : tag.npcClass().get().toString(), BedTag.nestKinds());
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
