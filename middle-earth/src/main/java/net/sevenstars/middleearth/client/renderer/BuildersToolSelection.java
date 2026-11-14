package net.sevenstars.middleearth.client.renderer;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.beds.BedTagHolder;
import net.sevenstars.middleearth.network.packets.client2server.PacketBedLinkRequest;
import org.jetbrains.annotations.Nullable;

// The bed picked with a left-click of the builder's tool, whose links the next left-clicks make and unmake
public class BuildersToolSelection {
    // A held button repeats its click every five ticks, which must not pick and drop the bed again and again
    private static final long REPEAT_TICKS = 6;

    private static @Nullable BlockPos selected = null;
    private static long lastClick = Long.MIN_VALUE;

    public static @Nullable BlockPos selected() {
        return selected;
    }

    public static void register() {
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if(!world.isClient || !BuildersToolRenderer.holdsTool(player))
                return ActionResult.PASS;
            // The tool never breaks anything, whatever is clicked
            if(!player.isCreative())
                return ActionResult.FAIL;
            if(selected != null && !(world.getBlockState(selected).getBlock() instanceof BedBlock))
                selected = null;
            BlockState state = world.getBlockState(pos);
            boolean bed = state.getBlock() instanceof BedBlock;
            if(!bed && selected == null)
                return ActionResult.FAIL;
            long time = world.getTime();
            boolean repeated = time - lastClick < REPEAT_TICKS;
            lastClick = time;
            if(repeated)
                return ActionResult.FAIL;
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
