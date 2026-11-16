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

// What was picked with a left-click of the builder's tool. A bed, whose links the next left-clicks add and remove. A
// plug, whose compatible plugs are shown, and which a sneaking left-click on another plug makes target that one. A
// block where two sections meet, which a second left-click turns into a pair of plugs. And the corners of a section,
// started with a sneaking left-click
public class BuildersToolSelection {
    // A held button repeats its click every five ticks, which must not pick and drop the bed over and over
    // Uses wall-clock time because game time is frozen while a structure is being edited
    private static final long REPEAT_MILLIS = 300;
    // How far in front of the builder a corner is set when no block is aimed at
    private static final double AIR_CORNER_DISTANCE = 3;
    // Horizontal sides come before up and down, for when a block has another section on several sides
    private static final Direction[] SEAM_SIDES = {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST, Direction.DOWN, Direction.UP};

    private static @Nullable BlockPos selected = null;
    // The block whose build the bounds are being set for, and the corner that was clicked first
    private static @Nullable BlockPos boundsAnchor = null;
    private static @Nullable BlockPos boundsFirst = null;
    // The jigsaw block that was picked, and the plugs it could be joined to
    private static @Nullable BlockPos jigsaw = null;
    private static List<BlockPos> jigsawFits = List.of();
    // The block clicked where two sections meet, and the block beside it in the other section
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

    // With no bed picked, sneak-clicking a block starts setting the bounds of the build it is in, and the next two
    // blocks clicked are its corners. Sneak-clicking again before the first corner cancels it
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

    // A piece of a loaded structure only gets its bounds set. Anywhere else the box can be its own section, and the
    // builder is asked to name it
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

    // For a block of one section with a block of another section beside it. The first click shows what it would be joined
    // to, and a second click turns the two into plugs. That uses up two blocks of the build, so one click isn't enough
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
        player.sendMessage(Text.translatable("ui.%s.section_join.no_seam".formatted(MiddleEarth.MOD_ID), pathOf(own.name())), true);
    }

    private static String pathOf(String name) {
        Identifier id = Identifier.tryParse(name);
        return id == null ? name : id.getPath();
    }

    // A corner of the bounds can be in the air, where there is nothing to click
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

    // A selected bed that was left behind would stop every other bed from being shown
    static void forgetOutOfRange(PlayerEntity player, int range) {
        if(selected != null && (!player.getBlockPos().isWithinDistance(selected, range) || !(player.getWorld().getBlockState(selected).getBlock() instanceof BedBlock)))
            selected = null;
    }

    public static void register() {
        StructureEditing.sectionScreen = pos -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if(client.world == null)
                return;
            BuildSections.near(client.world, pos).stream().filter(section -> section.block().equals(pos)).findFirst()
                    .ifPresent(section -> client.setScreen(new BuildSectionScreen(section)));
        };
        // A click that hits nothing deselects whatever was picked, unless bounds are being set, in which case it sets a corner in the air
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
                // The compatible plugs come from the server, which knows the pools. Clicking the same plug again deselects it
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
