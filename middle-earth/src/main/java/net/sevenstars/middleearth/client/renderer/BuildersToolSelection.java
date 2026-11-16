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
import net.sevenstars.middleearth.network.packets.client2server.PacketSectionJoinRequest;
import net.sevenstars.middleearth.block.special.structureManager.BuildSections;
import net.sevenstars.middleearth.commands.custom.StructureEditSession;
import net.sevenstars.middleearth.gui.structuremanager.buildsection.BuildSectionScreen;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.block.enums.BedPart;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.beds.BedTagHolder;
import net.sevenstars.middleearth.network.packets.client2server.PacketBedLinkRequest;
import org.jetbrains.annotations.Nullable;

// What was picked with a left-click of the builder's tool. A bed, whose links the next left-clicks make and unmake. A
// plug, whose fits are shown, and which a sneaking left-click on another plug makes ask for that one. A block where two
// sections meet, which a second left-click makes a pair of plugs. And, begun with a sneaking left-click, the corners of
// a section
public class BuildersToolSelection {
    // A held button repeats its click every five ticks, which must not pick and drop the bed again and again
    // By the clock, not by the game's time, which stands still while a structure is edited
    private static final long REPEAT_MILLIS = 300;
    // How far in front of the builder a corner is set when no block is aimed at
    private static final double AIR_CORNER_DISTANCE = 3;
    // Across the ground before up and down, where a block has another section on several sides
    private static final Direction[] SEAM_SIDES = {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST, Direction.DOWN, Direction.UP};

    private static @Nullable BlockPos selected = null;
    // The block the bounds of its build are being set from, and the corner that was clicked first
    private static @Nullable BlockPos boundsAnchor = null;
    private static @Nullable BlockPos boundsFirst = null;
    // The jigsaw block that was picked, and the plugs it could be joined to
    private static @Nullable BlockPos jigsaw = null;
    private static List<BlockPos> jigsawFits = List.of();
    // The block clicked where two sections meet, and the one beside it in the other section
    private static @Nullable BlockPos joinFrom = null;
    private static @Nullable BlockPos joinTo = null;
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
            finishBounds(player, boundsAnchor, boundsFirst, pos);
            boundsAnchor = null;
            boundsFirst = null;
        }
    }

    // A piece of a structure that is loaded only has its bounds set. Anywhere else the box may as well be a section of
    // its own, which the builder is asked to name
    private static void finishBounds(PlayerEntity player, BlockPos anchor, BlockPos first, BlockPos second) {
        MinecraftClient client = MinecraftClient.getInstance();
        StructureEditSession session = client.getServer() == null ? null : StructureEditSession.of(player.getUuid());
        if(session != null && session.getDimension().equals(player.getWorld().getRegistryKey()) && session.pieceContaining(anchor) != null) {
            ClientPlayNetworking.send(new PacketStructureBoundsRequest(anchor, first, second, ""));
            return;
        }
        BuildSections.Section section = BuildSections.at(player.getWorld(), anchor);
        client.setScreen(new BuildSectionScreen(anchor, first, second, section == null ? "" : section.name()));
    }

    public static @Nullable BlockPos joinFrom() {
        return joinFrom;
    }

    public static @Nullable BlockPos joinTo() {
        return joinTo;
    }

    // A block of one section that has a block of another beside it: clicked once it is shown with what it would be joined
    // to, and clicked again the two are made plugs. Two blocks of a build are given up for them, so one click is not enough
    private static void clickSeam(PlayerEntity player, BlockPos pos) {
        if(pos.equals(joinFrom) && joinTo != null) {
            ClientPlayNetworking.send(new PacketSectionJoinRequest(joinFrom, joinTo));
            joinFrom = null;
            joinTo = null;
            return;
        }
        joinFrom = null;
        joinTo = null;
        List<BuildSections.Section> sections = BuildSections.near(player.getWorld(), pos);
        BuildSections.Section own = BuildSections.at(sections, pos);
        if(own == null)
            return;
        for(Direction side : SEAM_SIDES) {
            BlockPos beside = pos.offset(side);
            BuildSections.Section other = BuildSections.at(sections, beside);
            if(other == null || other.equals(own) || own.box().contains(beside))
                continue;
            joinFrom = pos;
            joinTo = beside;
            player.sendMessage(Text.translatable("ui.%s.section_join.again".formatted(MiddleEarth.MOD_ID), pathOf(own.name()), pathOf(other.name())), true);
            return;
        }
    }

    private static String pathOf(String name) {
        Identifier id = Identifier.tryParse(name);
        return id == null ? name : id.getPath();
    }

    // A corner of the bounds may be in the air, where there is nothing to click
    static BlockPos cornerInAir(PlayerEntity player) {
        return BlockPos.ofFloored(player.getEyePos().add(player.getRotationVec(1f).multiply(AIR_CORNER_DISTANCE)));
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
        joinFrom = null;
        joinTo = null;
    }

    // A bed that was picked and then left behind would keep every other bed from being shown
    static void forgetOutOfRange(PlayerEntity player, int range) {
        if(selected != null && (!player.getBlockPos().isWithinDistance(selected, range) || !(player.getWorld().getBlockState(selected).getBlock() instanceof BedBlock)))
            selected = null;
    }

    public static void register() {
        // A click that hits nothing lets go of whatever was picked, unless bounds are being set: then it sets a corner in the air
        ClientPreAttackCallback.EVENT.register((client, player, clickCount) -> {
            if(clickCount == 0 || !BuildersToolRenderer.holdsTool(player) || client.crosshairTarget != null && client.crosshairTarget.getType() != HitResult.Type.MISS)
                return false;
            if(boundsAnchor == null) {
                unselect();
                return false;
            }
            long time = Util.getMeasuringTimeMs();
            boolean repeated = time - lastClick < REPEAT_MILLIS;
            lastClick = time;
            if(!repeated)
                clickBounds(player, cornerInAir(player), false);
            return true;
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
            // With a plug picked, a sneaking click on another plug ties the two instead of starting bounds there
            boolean tying = player.isSneaking() && selected == null && boundsAnchor == null && jigsaw != null && state.isOf(Blocks.JIGSAW) && !pos.equals(jigsaw);
            boolean starting = player.isSneaking() && selected == null && !tying;
            boolean bounds = boundsAnchor != null || starting;
            boolean plug = state.isOf(Blocks.JIGSAW) && selected == null && !bounds;
            long time = Util.getMeasuringTimeMs();
            boolean repeated = time - lastClick < REPEAT_MILLIS;
            lastClick = time;
            if(repeated)
                return ActionResult.FAIL;
            if(tying) {
                ClientPlayNetworking.send(new PacketSectionJoinRequest(jigsaw, pos.toImmutable()));
                return ActionResult.FAIL;
            }
            if(!bed && selected == null && !bounds && !plug) {
                jigsaw = null;
                jigsawFits = List.of();
                clickSeam(player, pos.toImmutable());
                return ActionResult.FAIL;
            }
            joinFrom = null;
            joinTo = null;
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
