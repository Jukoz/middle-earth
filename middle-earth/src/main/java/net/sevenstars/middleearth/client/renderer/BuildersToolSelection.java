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
import net.sevenstars.middleearth.item.items.BuildersToolItem;
import net.sevenstars.middleearth.commands.custom.StructureEditSession;
import net.sevenstars.middleearth.gui.structuremanager.buildsection.BuildSectionScreen;
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

// What the left-click picked: a bed, a plug, a seam block, or section corners
public class BuildersToolSelection {
    // A held button repeats every 5 ticks, which mustn't pick and drop the bed over and over.
    // Wall-clock, since game time is frozen while editing
    private static final long REPEAT_MILLIS = 300;
    private static final double AIR_CORNER_DISTANCE = 3;
    private static final Direction[] SEAM_SIDES = {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST, Direction.DOWN, Direction.UP};

    private static @Nullable BlockPos selected = null;
    private static @Nullable BlockPos boundsAnchor = null;
    private static @Nullable BlockPos boundsFirst = null;
    private static @Nullable BlockPos jigsaw = null;
    private static List<BlockPos> jigsawFits = List.of();
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

    // Sneak-click starts bounds, the next two clicks are corners; sneak again to cancel
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

    // Loaded structure pieces just get their bounds; elsewhere the builder names the section
    private static void finishBounds(PlayerEntity player, BlockPos anchor, BlockPos first, BlockPos second) {
        MinecraftClient client = MinecraftClient.getInstance();
        if(client.getServer() != null && StructureEditSession.pieceAt(player.getUuid(), player.getEntityWorld().getRegistryKey(), anchor) != null) {
            ClientPlayNetworking.send(new PacketStructureBoundsRequest(anchor, first, second, ""));
            return;
        }
        BuildSections.Section section = BuildSections.at(player.getEntityWorld(), anchor);
        client.setScreen(new BuildSectionScreen(anchor, first, second, section == null ? "" : section.name()));
    }

    public static @Nullable BlockPos joinFrom() {
        return joinFrom;
    }

    public static @Nullable BlockPos joinTo() {
        return joinTo;
    }

    // Needs two blocks of the build: first click shows what it'd join, second makes plugs
    private static void clickSeam(PlayerEntity player, BlockPos pos) {
        if(pos.equals(joinFrom) && joinTo != null) {
            ClientPlayNetworking.send(new PacketSectionJoinRequest(joinFrom, joinTo));
            joinFrom = null;
            joinTo = null;
            return;
        }
        joinFrom = null;
        joinTo = null;
        List<BuildSections.Section> sections = BuildSections.near(player.getEntityWorld(), pos);
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
            player.sendMessage(Text.translatable("ui.%s.section_join.again".formatted(MiddleEarth.MOD_ID), BuildSections.pathOf(own.name()), BuildSections.pathOf(other.name())), true);
            return;
        }
        player.sendMessage(Text.translatable("ui.%s.section_join.no_seam".formatted(MiddleEarth.MOD_ID), BuildSections.pathOf(own.name())), true);
    }

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

    // A leftover selected bed would stop every other bed showing
    static void forgetOutOfRange(PlayerEntity player, int range) {
        if(selected != null && (!player.getBlockPos().isWithinDistance(selected, range) || !(player.getEntityWorld().getBlockState(selected).getBlock() instanceof BedBlock)))
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
        // Clicking nothing deselects, or sets an air corner while setting bounds
        ClientPreAttackCallback.EVENT.register((client, player, clickCount) -> {
            if(clickCount == 0 || !BuildersToolItem.isHeldBy(player) || client.crosshairTarget != null && client.crosshairTarget.getType() != HitResult.Type.MISS)
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
            if(!world.isClient() || !BuildersToolItem.isHeldBy(player))
                return ActionResult.PASS;
            if(!StructureEditing.isBuildMode(player))
                return ActionResult.FAIL;
            if(selected != null && !(world.getBlockState(selected).getBlock() instanceof BedBlock))
                selected = null;
            BlockState state = world.getBlockState(pos);
            boolean bed = state.getBlock() instanceof BedBlock;
            // Plug picked: sneak-clicking another plug ties them instead of starting bounds
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
                // Compatible plugs come from the server, which knows the pools
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
