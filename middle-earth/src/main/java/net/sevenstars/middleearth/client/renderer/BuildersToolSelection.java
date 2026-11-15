package net.sevenstars.middleearth.client.renderer;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.HitResult;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;
import net.sevenstars.middleearth.network.packets.client2server.PacketJigsawFitsRequest;
import java.util.List;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.minecraft.entity.player.PlayerEntity;
import net.sevenstars.middleearth.network.packets.client2server.PacketStructureBoundsRequest;
import net.minecraft.block.enums.BedPart;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.beds.BedTagHolder;
import net.sevenstars.middleearth.network.packets.client2server.PacketBedLinkRequest;
import org.jetbrains.annotations.Nullable;

// The bed picked with a left-click of the builder's tool, whose links the next left-clicks make and unmake
public class BuildersToolSelection {
    // A held button repeats its click every five ticks, which must not pick and drop the bed again and again
    // By the clock, not by the game's time, which stands still while a structure is edited
    private static final long REPEAT_MILLIS = 300;

    private static @Nullable BlockPos selected = null;
    // The block the bounds of its build are being set from, and the corner that was clicked first
    private static @Nullable BlockPos boundsAnchor = null;
    private static @Nullable BlockPos boundsFirst = null;
    // The jigsaw block that was picked, and the plugs it could be joined to
    private static @Nullable BlockPos jigsaw = null;
    private static List<BlockPos> jigsawFits = List.of();
    private static long lastClick = 0;

    public static @Nullable BlockPos selected() {
        return selected;
    }

    public static @Nullable BlockPos boundsAnchor() {
        return boundsAnchor;
    }

    public static @Nullable BlockPos boundsFirst() {
        return boundsFirst;
    }

    // With no bed picked, a block clicked while sneaking starts the bounds of the build it is in: the next two blocks
    // clicked are its corners. Clicked while sneaking again before the first of them, it is left as it was
    private static void clickBounds(PlayerEntity player, BlockPos pos, boolean starting) {
        if(boundsAnchor == null) {
            boundsAnchor = pos;
            boundsFirst = null;
            player.sendMessage(Text.translatable("ui.%s.structure_bounds.first".formatted(MiddleEarth.MOD_ID)), true);
        } else if(starting && boundsFirst == null) {
            boundsAnchor = null;
            player.sendMessage(Text.translatable("ui.%s.structure_bounds.stopped".formatted(MiddleEarth.MOD_ID)), true);
        } else if(boundsFirst == null) {
            boundsFirst = pos;
            player.sendMessage(Text.translatable("ui.%s.structure_bounds.second".formatted(MiddleEarth.MOD_ID)), true);
        } else {
            ClientPlayNetworking.send(new PacketStructureBoundsRequest(boundsAnchor, boundsFirst, pos));
            boundsAnchor = null;
            boundsFirst = null;
        }
    }

    public static @Nullable BlockPos jigsaw() {
        return jigsaw;
    }

    public static List<BlockPos> jigsawFits() {
        return jigsawFits;
    }

    public static void showJigsawFits(BlockPos pos, List<BlockPos> fits) {
        if(!pos.equals(jigsaw))
            return;
        jigsawFits = List.copyOf(fits);
        PlayerEntity player = MinecraftClient.getInstance().player;
        if(player != null)
            player.sendMessage(Text.translatable("ui.%s.jigsaw_fits.%s".formatted(MiddleEarth.MOD_ID, fits.isEmpty() ? "none" : "some"), fits.size()), true);
    }

    private static void unselect() {
        selected = null;
        boundsAnchor = null;
        boundsFirst = null;
        jigsaw = null;
        jigsawFits = List.of();
    }

    public static void register() {
        // A click that hits nothing lets go of whatever was picked
        ClientPreAttackCallback.EVENT.register((client, player, clickCount) -> {
            if(clickCount > 0 && BuildersToolRenderer.holdsTool(player) && (client.crosshairTarget == null || client.crosshairTarget.getType() == HitResult.Type.MISS))
                unselect();
            return false;
        });
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if(!world.isClient || !BuildersToolRenderer.holdsTool(player))
                return ActionResult.PASS;
            // The tool never breaks anything, whatever is clicked
            if(!StructureEditing.isBuildMode(player))
                return ActionResult.FAIL;
            if(selected != null && !(world.getBlockState(selected).getBlock() instanceof BedBlock))
                selected = null;
            BlockState state = world.getBlockState(pos);
            boolean bed = state.getBlock() instanceof BedBlock;
            boolean starting = player.isSneaking() && selected == null;
            boolean bounds = boundsAnchor != null || starting;
            boolean plug = state.isOf(Blocks.JIGSAW) && selected == null && !bounds;
            if(!bed && selected == null && !bounds && !plug) {
                jigsaw = null;
                jigsawFits = List.of();
                return ActionResult.FAIL;
            }
            long time = Util.getMeasuringTimeMs();
            boolean repeated = time - lastClick < REPEAT_MILLIS;
            lastClick = time;
            if(repeated)
                return ActionResult.FAIL;
            if(bounds) {
                clickBounds(player, pos.toImmutable(), starting);
                return ActionResult.FAIL;
            }
            if(plug) {
                // Its fits come from the server, which knows the pools. Clicked again, it is let go of
                boolean again = pos.equals(jigsaw);
                jigsaw = again ? null : pos.toImmutable();
                jigsawFits = List.of();
                if(!again)
                    ClientPlayNetworking.send(new PacketJigsawFitsRequest(jigsaw));
                return ActionResult.FAIL;
            }
            jigsaw = null;
            jigsawFits = List.of();
            if(bed) {
                BlockPos head = state.get(BedBlock.PART) == BedPart.HEAD ? pos : pos.offset(state.get(BedBlock.FACING));
                selected = head.equals(selected) ? null : head.toImmutable();
            } else if(BedTagHolder.tagAt(world, selected).isEmpty()) {
                player.sendMessage(Text.translatable("ui.%s.bed_link.untagged".formatted(MiddleEarth.MOD_ID)), true);
            } else {
                ClientPlayNetworking.send(new PacketBedLinkRequest(selected, pos.toImmutable(), BuildersToolRenderer.automaticLinks(selected)));
            }
            return ActionResult.SUCCESS;
        });
    }
}
