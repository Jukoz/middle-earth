package net.sevenstars.middleearth.client.renderer;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.minecraft.entity.player.PlayerEntity;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlock;
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
    // The manager the bounds of its build are being set from, and the corner that was clicked first
    private static @Nullable BlockPos boundsAnchor = null;
    private static @Nullable BlockPos boundsFirst = null;
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

    // With no bed picked, a manager starts the bounds of its build: the next two blocks clicked are its corners
    private static void clickBounds(PlayerEntity player, BlockPos pos, boolean manager) {
        if(boundsAnchor == null) {
            boundsAnchor = pos;
            boundsFirst = null;
            player.sendMessage(Text.translatable("ui.%s.structure_bounds.first".formatted(MiddleEarth.MOD_ID)), true);
        } else if(manager && pos.equals(boundsAnchor) && boundsFirst == null) {
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

    public static void register() {
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
            boolean manager = state.getBlock() instanceof StructureManagerBlock;
            boolean bounds = boundsAnchor != null || manager && selected == null;
            if(!bed && selected == null && !bounds)
                return ActionResult.FAIL;
            long time = Util.getMeasuringTimeMs();
            boolean repeated = time - lastClick < REPEAT_MILLIS;
            lastClick = time;
            if(repeated)
                return ActionResult.FAIL;
            if(bounds) {
                clickBounds(player, pos.toImmutable(), manager);
                return ActionResult.FAIL;
            }
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
