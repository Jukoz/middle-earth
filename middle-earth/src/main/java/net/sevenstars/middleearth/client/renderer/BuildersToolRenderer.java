package net.sevenstars.middleearth.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.block.special.beds.BedTagHolder;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.block.special.structureManager.marker.StructureMarkerBlock;
import net.sevenstars.middleearth.block.special.structureManager.marker.StructureMarkerBlockEntity;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import net.sevenstars.middleearth.registries.content.structurefeatures.BuildFeatures;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.StationKind;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// While the builder's tool is held, draws what the blocks around are taken for: tagged beds with who is born in them and the
// station or marker their job will use, free beds, stations, stores, seats, tables and structure markers
public class BuildersToolRenderer {
    // Far enough to find the station a bed will be given, which is looked for within 48 blocks
    private static final int RANGE = 32;
    private static final int RANGE_HEIGHT = 16;
    private static final int RESCAN_TICKS = 10;
    private static final int RESCAN_DISTANCE = 2;
    private static final String MINECRAFT_PREFIX = "minecraft:";
    private static final float LABEL_TEXT_SCALE = 0.02f;
    private static final float STATION_TEXT_SCALE = 0.012f;
    private static final float LABEL_HEIGHT = 1.3f;
    private static final int BED_TAGGED = 0xFFFFD54F;
    private static final int BED_PLAIN = 0xFF9E9E9E;
    private static final int BED_LOST = 0xFFE53935;
    private static final int SELECTED_BED = 0xFF00E676;
    private static final int LINK_MANUAL = 0xFF00E676;
    private static final int LINK_AUTOMATIC = 0xFFB2FF59;
    private static final double SELECTED_MARGIN = 0.08;
    private static final int STORE = 0xFFFFA726;
    private static final int SEAT = 0xFF26C6DA;
    private static final int TABLE = 0xFFEC407A;
    private static final int MARKER = 0xFFD500F9;
    private static final int[] STATION_COLORS = {0xFF8D6E63, 0xFFFF7043, 0xFFEF5350, 0xFF7E57C2, 0xFFA1887F, 0xFFFFEE58, 0xFF66BB6A, 0xFFD4E157, 0xFF78909C, 0xFFF06292, 0xFF9CCC65};
    private static final int NO_FEATURES = 0;
    private static final int STORE_FEATURE = 1;
    private static final int SEAT_FEATURE = 2;
    private static final int TABLE_FEATURE = 4;

    private record Bed(BlockPos pos, BedTag tag) {}

    private static final Map<Block, Integer> FEATURES = new IdentityHashMap<>();
    // Empty for the blocks that are no station, so that they are not looked up again
    private static final Map<Block, Optional<StationKind>> STATIONS = new IdentityHashMap<>();
    private static final List<Bed> beds = new ArrayList<>();
    private static final List<StructureFeatureData.Station> stations = new ArrayList<>();
    private static final List<StructureFeatureData.Marker> markers = new ArrayList<>();
    private static final List<BlockPos> stores = new ArrayList<>();
    private static final List<BlockPos> seats = new ArrayList<>();
    private static final List<BlockPos> tables = new ArrayList<>();
    private static final List<BlockPos> plainBeds = new ArrayList<>();
    private static ClientWorld scannedWorld;
    private static long scannedAt;
    private static BlockPos scannedFrom = BlockPos.ORIGIN;

    public static void register() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            MinecraftClient client = MinecraftClient.getInstance();
            PlayerEntity player = client.player;
            if(player == null || client.world == null || !holdsTool(player))
                return;
            try {
                scan(client.world, player.getBlockPos());
                DebugDraw.lines(context, (matrices, lines, camera) -> drawBoxes(matrices, lines));
                drawLabels(context.matrixStack() == null ? new MatrixStack() : context.matrixStack(), context.consumers());
            } catch (RuntimeException ignored) {
                // A block entity that is gone between the scan and the frame only costs one frame
            }
        });
    }

    static boolean holdsTool(PlayerEntity player) {
        return player.getMainHandStack().isOf(DecorativeItemsME.BUILDERS_TOOL) || player.getOffHandStack().isOf(DecorativeItemsME.BUILDERS_TOOL);
    }

    private static void scan(ClientWorld world, BlockPos from) {
        if(world == scannedWorld && world.getTime() - scannedAt < RESCAN_TICKS && scannedFrom.isWithinDistance(from, RESCAN_DISTANCE))
            return;
        scannedWorld = world;
        scannedAt = world.getTime();
        scannedFrom = from;
        beds.clear();
        plainBeds.clear();
        stations.clear();
        markers.clear();
        stores.clear();
        seats.clear();
        tables.clear();
        for(BlockPos pos : BlockPos.iterate(from.add(-RANGE, -RANGE_HEIGHT, -RANGE), from.add(RANGE, RANGE_HEIGHT, RANGE))) {
            BlockState state = world.getBlockState(pos);
            if(state.isAir())
                continue;
            Block block = state.getBlock();
            if(block == DecorativeBlockRegistryME.STRUCTURE_MARKER) {
                if(world.getBlockEntity(pos) instanceof StructureMarkerBlockEntity marker)
                    markers.add(new StructureFeatureData.Marker(marker.getKind(), pos.toImmutable(), state.get(StructureMarkerBlock.FACING), marker.getValue(), marker.getCount(), marker.getRespawns()));
                continue;
            }
            if(block instanceof BedBlock) {
                if(state.get(BedBlock.PART) == BedPart.HEAD) {
                    BlockPos head = pos.toImmutable();
                    BedTagHolder.tagAt(world, pos).ifPresentOrElse(tag -> beds.add(new Bed(head, tag)), () -> plainBeds.add(head));
                }
                continue;
            }
            int features = FEATURES.computeIfAbsent(block, BuildersToolRenderer::featuresOf);
            if((features & STORE_FEATURE) != 0)
                stores.add(pos.toImmutable());
            if((features & SEAT_FEATURE) != 0)
                seats.add(pos.toImmutable());
            if((features & TABLE_FEATURE) != 0)
                tables.add(pos.toImmutable());
            StationKind kind = STATIONS.computeIfAbsent(block, found -> Optional.ofNullable(BuildFeatures.stationOf(found))).orElse(null);
            if(kind != null)
                stations.add(new StructureFeatureData.Station(kind, pos.toImmutable()));
        }
    }

    private static int featuresOf(Block block) {
        int features = NO_FEATURES;
        if(BuildFeatures.STORE.test(block))
            features |= STORE_FEATURE;
        if(BuildFeatures.SEAT.test(block))
            features |= SEAT_FEATURE;
        if(BuildFeatures.TABLE.test(block))
            features |= TABLE_FEATURE;
        return features;
    }

    private static void drawBoxes(MatrixStack matrices, VertexConsumer lines) {
        BlockPos selected = BuildersToolSelection.selected();
        if(selected != null) {
            drawSelected(matrices, lines, selected);
            return;
        }
        for(BlockPos pos : plainBeds)
            DebugDraw.box(matrices, lines, pos, BED_PLAIN);
        for(BlockPos pos : stores)
            DebugDraw.box(matrices, lines, pos, STORE);
        for(BlockPos pos : seats)
            DebugDraw.box(matrices, lines, pos, SEAT);
        for(BlockPos pos : tables)
            DebugDraw.box(matrices, lines, pos, TABLE);
        for(StructureFeatureData.Marker marker : markers)
            DebugDraw.box(matrices, lines, marker.pos(), MARKER);
        for(StructureFeatureData.Station station : stations) {
            // A field is a plot of farmland by the hundred
            if(station.kind() != StationKind.FIELD)
                DebugDraw.box(matrices, lines, station.pos(), stationColor(station.kind()));
        }
        for(Bed bed : beds) {
            List<BlockPos> links = linksOf(bed);
            DebugDraw.box(matrices, lines, bed.pos(), links.isEmpty() && needsStation(bed) ? BED_LOST : BED_TAGGED);
            for(BlockPos link : links) {
                DebugDraw.box(matrices, lines, link, BED_TAGGED);
                drawLink(matrices, lines, bed, link, BED_TAGGED);
            }
        }
    }

    // Only the connections of the bed that was picked
    private static void drawSelected(MatrixStack matrices, VertexConsumer lines, BlockPos selected) {
        DebugDraw.box(matrices, lines, new Box(selected).expand(SELECTED_MARGIN), SELECTED_BED);
        Bed bed = selectedBed(selected);
        if(bed == null)
            return;
        int color = bed.tag().manual() ? LINK_MANUAL : LINK_AUTOMATIC;
        for(BlockPos link : linksOf(bed)) {
            DebugDraw.box(matrices, lines, link, color);
            drawLink(matrices, lines, bed, link, color);
        }
    }

    private static void drawLink(MatrixStack matrices, VertexConsumer lines, Bed bed, BlockPos link, int color) {
        if(bed.tag().manual())
            DebugDraw.line(matrices, lines, Vec3d.ofCenter(bed.pos()), Vec3d.ofCenter(link), color);
        else
            DebugDraw.dashedLine(matrices, lines, Vec3d.ofCenter(bed.pos()), Vec3d.ofCenter(link), color);
    }

    private static @Nullable Bed selectedBed(BlockPos selected) {
        for(Bed bed : beds) {
            if(bed.pos().equals(selected))
                return bed;
        }
        return null;
    }

    // What the bed works at: the places it was tied to by hand, or else what its job will use, picked as an npc of that job picks
    // it: the nearest of its kinds of station to the bed
    private static List<BlockPos> linksOf(Bed bed) {
        if(bed.tag().manual())
            return bed.tag().links().stream().map(offset -> bed.pos().add(offset)).toList();
        BlockPos station = bed.tag().job().map(job -> NpcJobs.stationFor(job, bed.pos(), stations, markers)).orElse(null);
        return station == null ? List.of() : List.of(station);
    }

    // For the server, which cannot see what the client does, when the bed is first tied by hand
    static List<BlockPos> automaticLinks(BlockPos bedPos) {
        Bed bed = selectedBed(bedPos);
        if(bed == null || bed.tag().manual())
            return List.of();
        return linksOf(bed);
    }

    // Not every job works at a place: a hunter goes out into the land
    private static boolean needsStation(Bed bed) {
        return bed.tag().job().filter(job -> !NpcJobs.stationKinds(job).isEmpty() || !NpcJobs.stationMarkers(job).isEmpty()).isPresent();
    }

    private static void drawLabels(MatrixStack matrices, VertexConsumerProvider consumers) {
        BlockPos selected = BuildersToolSelection.selected();
        if(selected != null) {
            Bed bed = selectedBed(selected);
            if(bed == null)
                return;
            label(matrices, consumers, bedLabel(bed), bed.pos(), SELECTED_BED, LABEL_TEXT_SCALE);
            for(BlockPos link : linksOf(bed)) {
                label(matrices, consumers, MinecraftClient.getInstance().world.getBlockState(link).getBlock().getName().getString(), link,
                        bed.tag().manual() ? LINK_MANUAL : LINK_AUTOMATIC, STATION_TEXT_SCALE);
            }
            return;
        }
        for(BlockPos pos : plainBeds)
            label(matrices, consumers, "free (wanderers)", pos, BED_PLAIN, LABEL_TEXT_SCALE);
        for(Bed bed : beds)
            label(matrices, consumers, bedLabel(bed), bed.pos(), needsStation(bed) && linksOf(bed).isEmpty() ? BED_LOST : BED_TAGGED, LABEL_TEXT_SCALE);
        for(StructureFeatureData.Marker marker : markers)
            label(matrices, consumers, markerLabel(marker), marker.pos(), MARKER, LABEL_TEXT_SCALE);
        for(StructureFeatureData.Station station : stations) {
            if(station.kind() != StationKind.FIELD)
                label(matrices, consumers, station.kind().asString(), station.pos(), stationColor(station.kind()), STATION_TEXT_SCALE);
        }
    }

    private static String bedLabel(Bed bed) {
        String job = bed.tag().job().map(Identifier::getPath).orElse("none");
        boolean lost = needsStation(bed) && linksOf(bed).isEmpty();
        return bed.tag().nestKind() + " / " + job + (bed.tag().manual() ? " (manual)" : "") + (lost ? " (no station)" : "");
    }

    // spawn_animal: horse x2
    private static String markerLabel(StructureFeatureData.Marker marker) {
        if(marker.kind() != MarkerKind.SPAWN_NPC && marker.kind() != MarkerKind.SPAWN_ANIMAL)
            return marker.kind().asString();
        String value = marker.value().isEmpty() ? "?" : marker.value().replace(MINECRAFT_PREFIX, "");
        return marker.kind().asString() + ": " + value + " x" + marker.count() + (marker.respawns() ? " (respawns)" : "");
    }

    private static void label(MatrixStack matrices, VertexConsumerProvider consumers, String text, BlockPos pos, int color, float scale) {
        DebugDraw.label(matrices, consumers, text, new Vec3d(pos.getX() + 0.5, pos.getY() + LABEL_HEIGHT, pos.getZ() + 0.5), color, scale);
    }

    private static int stationColor(StationKind kind) {
        return STATION_COLORS[kind.ordinal() % STATION_COLORS.length];
    }
}
