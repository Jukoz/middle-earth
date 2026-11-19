package net.sevenstars.middleearth.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.JigsawBlockEntity;
import net.minecraft.block.enums.BedPart;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.block.special.beds.BedTagHolder;
import net.sevenstars.middleearth.block.special.structureManager.BuildSections;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.block.special.structureManager.marker.StructureMarkerBlock;
import net.sevenstars.middleearth.block.special.structureManager.marker.StructureMarkerBlockEntity;
import net.sevenstars.middleearth.commands.custom.PieceProjections;
import net.sevenstars.middleearth.commands.custom.StructureEditSession;
import net.sevenstars.middleearth.item.items.BuildersToolItem;
import net.sevenstars.middleearth.features.npcs.jobs.NpcJobs;
import net.sevenstars.middleearth.registries.content.structurefeatures.BuildFeatures;
import net.sevenstars.middleearth.features.npcs.classes.data.StationKind;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class BuildersToolRenderer {
    // Beds search 48 blocks for stations, so one at the scan's edge can show none
    private static final int RANGE = 32;
    private static final int RANGE_HEIGHT = 16;
    // Wall-clock, since game time is frozen while editing
    private static final int RESCAN_MILLIS = 500;
    private static final int RESCAN_DISTANCE = 2;
    private static final int SCAN_BLOCKS_PER_FRAME = 8192;
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
    private static final int PIECE = 0xFF40C4FF;
    private static final int BOUNDS = 0xFFFFEB3B;
    private static final int JOIN = 0xFF00E676;
    private static final Identifier EMPTY_PLUG = Identifier.ofVanilla("empty");
    private static final int[] SECTION_COLORS = {0xFF40C4FF, 0xFFFF8A65, 0xFFB388FF, 0xFFFFD740, 0xFF69F0AE, 0xFFFF80AB, 0xFF84FFFF, 0xFFEEFF41};
    // Keeps the box outside vanilla's white structure box
    private static final double SECTION_MARGIN = 0.03;
    private static final int MAXIMUM_PLOT = 4096;
    private static final int PROJECTIONS_MILLIS = 2000;
    private static final int PLUG = 0xFF00E676;
    private static final int FAILURE_LOG_MILLIS = 10000;
    private static final int FAILURE_LOG_FRAMES = 6;
    private static final float PIECE_TEXT_SCALE = 0.04f;
    private static final double PIECE_LABEL_HEIGHT = 1.5;
    private static final int[] STATION_COLORS = {0xFF8D6E63, 0xFFFF7043, 0xFFEF5350, 0xFF7E57C2, 0xFFA1887F, 0xFFFFEE58, 0xFF66BB6A, 0xFFD4E157, 0xFF78909C, 0xFFF06292, 0xFF9CCC65};
    private static final int NO_FEATURES = 0;
    private static final int STORE_FEATURE = 1;
    private static final int SEAT_FEATURE = 2;
    private static final int TABLE_FEATURE = 4;

    // Links and lost are worked out once per scan
    private record Bed(BlockPos pos, BedTag tag, List<BlockPos> links, boolean lost) {}

    private static final Map<Block, Integer> FEATURES = new IdentityHashMap<>();
    // Empty for non-stations, so they aren't looked up again
    private static final Map<Block, Optional<StationKind>> STATIONS = new IdentityHashMap<>();

    // What one scan finds. The shown one is drawn while the next fills over several frames
    private static class Scan {
        final List<Bed> beds = new ArrayList<>();
        final List<StructureFeatureData.Station> stations = new ArrayList<>();
        final List<StructureFeatureData.Marker> markers = new ArrayList<>();
        final List<BlockPos> stores = new ArrayList<>();
        final Set<BlockPos> offLimits = new HashSet<>();
        // Stores not put off limits
        final Set<BlockPos> openStores = new HashSet<>();
        // Where a bed with no picked store goes: campfires only when there is no chest or barrel
        final List<BlockPos> nearestStores = new ArrayList<>();
        final List<BlockPos> seats = new ArrayList<>();
        final List<BlockPos> tables = new ArrayList<>();
        final List<BlockPos> plainBeds = new ArrayList<>();
        final Set<BlockPos> fields = new HashSet<>();
        final Map<BlockPos, String> plugs = new HashMap<>();
        final Map<BlockPos, List<Box>> plots = new HashMap<>();
        List<BuildSections.Section> sections = List.of();
        final BlockPos.Mutable cursor = new BlockPos.Mutable();
        int minX, minY, minZ, maxX, maxY, maxZ;
        int x, y, z;

        void clear() {
            beds.clear();
            plainBeds.clear();
            stations.clear();
            fields.clear();
            plots.clear();
            markers.clear();
            stores.clear();
            offLimits.clear();
            openStores.clear();
            nearestStores.clear();
            seats.clear();
            tables.clear();
            plugs.clear();
            sections = List.of();
        }
    }

    private static Scan shown = new Scan();
    private static Scan building = new Scan();
    private static boolean scanning = false;
    private static Map<Identifier, Set<StructurePool.Projection>> projections = Map.of();
    private static long projectionsReadAt = 0;
    private static ClientWorld scannedWorld;
    private static long failureLoggedAt = 0;
    private static long scannedAt;
    private static BlockPos scannedFrom = BlockPos.ORIGIN;

    public static void register() {
        WorldRenderEvents.END_EXTRACTION.register(context -> {
            MinecraftClient client = MinecraftClient.getInstance();
            PlayerEntity player = client.player;
            if(player == null || client.world == null || !BuildersToolItem.isHeldBy(player))
                return;
            try {
                scan(client.world, player.getBlockPos());
                BuildersToolSelection.forgetOutOfRange(player, RANGE);
                List<StructureEditSession.Piece> pieces = piecesAround(client, player);
                drawBoxes();
                for(StructureEditSession.Piece piece : pieces)
                    DebugDraw.box(Box.from(piece.box()), PIECE);
                drawLabels();
                for(StructureEditSession.Piece piece : pieces)
                    DebugDraw.label(piece.template().getPath() + projectionNote(client, piece.template()), new Vec3d(piece.box().getCenter().getX() + 0.5,
                            piece.box().getMaxY() + PIECE_LABEL_HEIGHT, piece.box().getCenter().getZ() + 0.5), PIECE, PIECE_TEXT_SCALE);
            } catch (RuntimeException e) {
                // Block entities can vanish between scan and frame; log a failure that repeats
                if(Util.getMeasuringTimeMs() - failureLoggedAt >= FAILURE_LOG_MILLIS) {
                    failureLoggedAt = Util.getMeasuringTimeMs();
                    MiddleEarth.LOGGER.logError("StructEdit :: the builder's tool could not draw: " + e);
                    for(int i = 0; i < Math.min(FAILURE_LOG_FRAMES, e.getStackTrace().length); i++)
                        MiddleEarth.LOGGER.logError("StructEdit ::   at " + e.getStackTrace()[i]);
                }
            }
        });
    }

    // Skips the piece they stand in (its walls show) unless its bounds are being set
    private static List<StructureEditSession.Piece> piecesAround(MinecraftClient client, PlayerEntity player) {
        StructureEditSession session = client.getServer() == null ? null : StructureEditSession.ofIn(player.getUuid(), player.getEntityWorld().getRegistryKey());
        if(session == null)
            return List.of();
        boolean settingBounds = BuildersToolSelection.boundsAnchor() != null;
        return session.getPieces().stream().filter(piece -> settingBounds || !piece.box().contains(player.getBlockPos())).toList();
    }

    // Re-read from the server now and then, since a command can change the pools
    private static String projectionNote(MinecraftClient client, Identifier template) {
        if(client.getServer() == null)
            return "";
        if(Util.getMeasuringTimeMs() - projectionsReadAt >= PROJECTIONS_MILLIS) {
            projectionsReadAt = Util.getMeasuringTimeMs();
            projections = PieceProjections.of(client.getServer().getRegistryManager().getOrThrow(RegistryKeys.TEMPLATE_POOL));
        }
        Set<StructurePool.Projection> of = projections.getOrDefault(template, Set.of());
        if(!of.contains(StructurePool.Projection.TERRAIN_MATCHING))
            return "";
        return of.size() > 1 ? " (follows the ground in some pools)" : " (follows the ground)";
    }

    private static void scan(ClientWorld world, BlockPos from) {
        if(scanning && world != scannedWorld)
            scanning = false;
        if(!scanning) {
            if(world == scannedWorld && Util.getMeasuringTimeMs() - scannedAt < RESCAN_MILLIS && scannedFrom.isWithinDistance(from, RESCAN_DISTANCE))
                return;
            if(world != scannedWorld)
                shown.clear();
            scannedWorld = world;
            scannedAt = Util.getMeasuringTimeMs();
            scannedFrom = from;
            building.clear();
            building.sections = BuildSections.near(world, from);
            building.minX = building.x = from.getX() - RANGE;
            building.minY = building.y = from.getY() - RANGE_HEIGHT;
            building.minZ = building.z = from.getZ() - RANGE;
            building.maxX = from.getX() + RANGE;
            building.maxY = from.getY() + RANGE_HEIGHT;
            building.maxZ = from.getZ() + RANGE;
            scanning = true;
        }
        Scan scan = building;
        // Same order as BlockPos.iterate, x first
        for(int left = SCAN_BLOCKS_PER_FRAME; left > 0 && scan.z <= scan.maxZ; left--) {
            scanBlock(world, scan, scan.cursor.set(scan.x, scan.y, scan.z));
            if(++scan.x > scan.maxX) {
                scan.x = scan.minX;
                if(++scan.y > scan.maxY) {
                    scan.y = scan.minY;
                    scan.z++;
                }
            }
        }
        if(scan.z <= scan.maxZ)
            return;
        indexStores(world, scan);
        DynamicRegistryManager registries = world.getRegistryManager();
        scan.beds.replaceAll(bed -> withLinks(scan, registries, bed));
        building = shown;
        shown = scan;
        building.clear();
        scanning = false;
    }

    private static void scanBlock(ClientWorld world, Scan scan, BlockPos.Mutable pos) {
        BlockState state = world.getBlockState(pos);
        if(state.isAir())
            return;
        Block block = state.getBlock();
        if(block == DecorativeBlockRegistryME.STRUCTURE_MARKER) {
            if(world.getBlockEntity(pos) instanceof StructureMarkerBlockEntity marker)
                scan.markers.add(new StructureFeatureData.Marker(marker.getKind(), pos.toImmutable(), state.get(StructureMarkerBlock.FACING), marker.getValue(), marker.getCount(), marker.getRespawns()));
            return;
        }
        if(block == Blocks.JIGSAW) {
            if(world.getBlockEntity(pos) instanceof JigsawBlockEntity plug)
                scan.plugs.put(pos.toImmutable(), plugLabel(plug));
            return;
        }
        if(block instanceof BedBlock) {
            if(state.get(BedBlock.PART) == BedPart.HEAD) {
                BlockPos head = pos.toImmutable();
                BedTagHolder.tagAt(world, pos).ifPresentOrElse(tag -> scan.beds.add(new Bed(head, tag, List.of(), false)), () -> scan.plainBeds.add(head));
            }
            return;
        }
        int features = FEATURES.computeIfAbsent(block, BuildersToolRenderer::featuresOf);
        if((features & STORE_FEATURE) != 0)
            scan.stores.add(pos.toImmutable());
        if((features & SEAT_FEATURE) != 0)
            scan.seats.add(pos.toImmutable());
        if((features & TABLE_FEATURE) != 0)
            scan.tables.add(pos.toImmutable());
        StationKind kind = STATIONS.computeIfAbsent(block, found -> Optional.ofNullable(BuildFeatures.stationOf(found))).orElse(null);
        if(kind != null)
            scan.stations.add(new StructureFeatureData.Station(kind, pos.toImmutable()));
        if(kind == StationKind.FIELD)
            scan.fields.add(pos.toImmutable());
    }

    private static void indexStores(ClientWorld world, Scan scan) {
        for(StructureFeatureData.Marker marker : scan.markers) {
            if(marker.kind() == MarkerKind.OFF_LIMITS)
                scan.offLimits.add(marker.pos().offset(marker.facing()));
        }
        for(BlockPos store : scan.stores) {
            if(!scan.offLimits.contains(store))
                scan.openStores.add(store);
        }
        boolean anyBox = scan.openStores.stream().anyMatch(pos -> !(world.getBlockState(pos).getBlock() instanceof CampfireBlock));
        for(BlockPos store : scan.stores) {
            if(!scan.offLimits.contains(store) && !(anyBox && world.getBlockState(store).getBlock() instanceof CampfireBlock))
                scan.nearestStores.add(store);
        }
    }

    private static Bed withLinks(Scan scan, DynamicRegistryManager registries, Bed bed) {
        List<BlockPos> links = linksOf(scan, registries, bed);
        return new Bed(bed.pos(), bed.tag(), links, links.isEmpty() && needsStation(registries, bed));
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

    private static @Nullable BlockPos aimedBlock() {
        HitResult hit = MinecraftClient.getInstance().crosshairTarget;
        return hit instanceof BlockHitResult block && hit.getType() == HitResult.Type.BLOCK ? block.getBlockPos() : null;
    }

    private static @Nullable Box boundsPreview() {
        BlockPos first = BuildersToolSelection.boundsFirst();
        PlayerEntity player = MinecraftClient.getInstance().player;
        if(BuildersToolSelection.boundsAnchor() == null || player == null)
            return null;
        BlockPos aimed = aimedBlock() == null ? BuildersToolSelection.cornerInAir(player) : aimedBlock();
        return first == null ? new Box(aimed) : Box.enclosing(first, aimed);
    }

    // Looks like: asks for rohan/village_house / answers as rohan/village_road
    private static String plugLabel(JigsawBlockEntity plug) {
        List<String> says = new ArrayList<>();
        if(!plug.getTarget().equals(EMPTY_PLUG))
            says.add("asks for " + shortened(plug.getTarget()) + (plug.getPool().getValue().equals(plug.getTarget()) ? "" : " of " + shortened(plug.getPool().getValue())));
        if(!plug.getName().equals(EMPTY_PLUG))
            says.add("answers as " + shortened(plug.getName()));
        return says.isEmpty() ? "plug, not joined" : String.join(" / ", says);
    }

    private static String shortened(Identifier id) {
        return BuildSections.shortName(id.toString());
    }

    private static int sectionColor(BuildSections.Section section) {
        return SECTION_COLORS[Math.floorMod(section.name().hashCode(), SECTION_COLORS.length)];
    }

    private static void drawBoxes() {
        for(BuildSections.Section section : shown.sections)
            DebugDraw.box(Box.from(section.box()).expand(SECTION_MARGIN), sectionColor(section));
        BlockPos joinFrom = BuildersToolSelection.joinFrom();
        BlockPos joinTo = BuildersToolSelection.joinTo();
        if(joinFrom != null && joinTo != null) {
            DebugDraw.box(new Box(joinFrom).expand(SELECTED_MARGIN), JOIN);
            DebugDraw.box(new Box(joinTo).expand(SELECTED_MARGIN), JOIN);
            DebugDraw.line(Vec3d.ofCenter(joinFrom), Vec3d.ofCenter(joinTo), JOIN);
        }
        BlockPos plug = BuildersToolSelection.jigsaw();
        if(plug != null) {
            DebugDraw.box(new Box(plug).expand(SELECTED_MARGIN), PLUG);
            for(BlockPos fit : BuildersToolSelection.jigsawFits()) {
                DebugDraw.box(fit, PLUG);
                DebugDraw.line(Vec3d.ofCenter(plug), Vec3d.ofCenter(fit), PLUG);
            }
        }
        if(BuildersToolSelection.boundsAnchor() != null) {
            DebugDraw.box(new Box(BuildersToolSelection.boundsAnchor()).expand(SELECTED_MARGIN), BOUNDS);
            Box preview = boundsPreview();
            if(preview != null)
                DebugDraw.box(preview, BOUNDS);
        }
        BlockPos selected = BuildersToolSelection.selected();
        if(selected != null) {
            drawSelected(selected);
            return;
        }
        for(BlockPos pos : shown.plainBeds)
            DebugDraw.box(pos, BED_PLAIN);
        for(BlockPos pos : shown.stores) {
            if(!shown.offLimits.contains(pos))
                DebugDraw.box(pos, STORE);
        }
        for(BlockPos pos : shown.seats) {
            if(!shown.offLimits.contains(pos))
                DebugDraw.box(pos, SEAT);
        }
        for(BlockPos pos : shown.tables) {
            if(!shown.offLimits.contains(pos))
                DebugDraw.box(pos, TABLE);
        }
        for(StructureFeatureData.Marker marker : shown.markers)
            DebugDraw.box(marker.pos(), MARKER);
        for(StructureFeatureData.Station station : shown.stations) {
            // Skip shown.fields, they're hundreds of farmland blocks
            if(station.kind() != StationKind.FIELD && !shown.offLimits.contains(station.pos()))
                DebugDraw.box(station.pos(), stationColor(station.kind()));
        }
        for(Bed bed : shown.beds) {
            drawStore(bed);
            DebugDraw.box(bed.pos(), bed.lost() ? BED_LOST : BED_TAGGED);
            for(BlockPos link : bed.links()) {
                drawLinked(link, BED_TAGGED);
                drawLink(bed, link, BED_TAGGED);
            }
        }
    }

    private static void drawSelected(BlockPos selected) {
        DebugDraw.box(new Box(selected).expand(SELECTED_MARGIN), SELECTED_BED);
        Bed bed = selectedBed(selected);
        if(bed == null)
            return;
        drawStore(bed);
        int color = bed.tag().manual() ? LINK_MANUAL : LINK_AUTOMATIC;
        for(BlockPos link : bed.links()) {
            drawLinked(link, color);
            drawLink(bed, link, color);
        }
    }

    // A field is all farmland connected to the linked plot
    private static void drawLinked(BlockPos link, int color) {
        if(!shown.fields.contains(link)) {
            DebugDraw.box(link, color);
            return;
        }
        for(Box row : shown.plots.computeIfAbsent(link, BuildersToolRenderer::plotOf))
            DebugDraw.box(row, color);
    }

    private static List<Box> plotOf(BlockPos from) {
        Set<BlockPos> plot = new HashSet<>();
        List<BlockPos> open = new ArrayList<>();
        plot.add(from);
        open.add(from);
        while(!open.isEmpty() && plot.size() < MAXIMUM_PLOT) {
            BlockPos cell = open.removeLast();
            for(Direction side : Direction.Type.HORIZONTAL) {
                for(int rise = -1; rise <= 1; rise++) {
                    BlockPos next = cell.offset(side).up(rise);
                    if(shown.fields.contains(next) && plot.add(next))
                        open.add(next);
                }
            }
        }
        List<BlockPos> cells = new ArrayList<>(plot);
        cells.sort(Comparator.comparingInt((BlockPos cell) -> cell.getY()).thenComparingInt(BlockPos::getZ).thenComparingInt(BlockPos::getX));
        List<Box> rows = new ArrayList<>();
        BlockPos first = null;
        BlockPos last = null;
        for(BlockPos cell : cells) {
            if(last != null && cell.getY() == last.getY() && cell.getZ() == last.getZ() && cell.getX() == last.getX() + 1) {
                last = cell;
                continue;
            }
            if(first != null)
                rows.add(Box.enclosing(first, last));
            first = cell;
            last = cell;
        }
        if(first != null)
            rows.add(Box.enclosing(first, last));
        return rows;
    }

    // Solid: picked store. Dashed: nearest scanned one
    private static void drawStore(Bed bed) {
        BlockPos picked = bed.tag().store().map(offset -> bed.pos().add(offset)).filter(shown.openStores::contains).orElse(null);
        if(picked != null) {
            DebugDraw.line(Vec3d.ofCenter(bed.pos()), Vec3d.ofCenter(picked), STORE);
            return;
        }
        BlockPos work = bed.links().isEmpty() ? bed.pos() : bed.links().getFirst();
        BlockPos nearest = null;
        for(BlockPos store : shown.nearestStores) {
            if(nearest == null || store.getSquaredDistance(work) < nearest.getSquaredDistance(work))
                nearest = store;
        }
        if(nearest != null)
            DebugDraw.dashedLine(Vec3d.ofCenter(bed.pos()), Vec3d.ofCenter(nearest), STORE);
    }

    private static void drawLink(Bed bed, BlockPos link, int color) {
        if(bed.tag().manual())
            DebugDraw.line(Vec3d.ofCenter(bed.pos()), Vec3d.ofCenter(link), color);
        else
            DebugDraw.dashedLine(Vec3d.ofCenter(bed.pos()), Vec3d.ofCenter(link), color);
    }

    private static @Nullable Bed selectedBed(BlockPos selected) {
        for(Bed bed : shown.beds) {
            if(bed.pos().equals(selected))
                return bed;
        }
        return null;
    }

    // Hand-tied places, else the nearest station its job would use
    private static List<BlockPos> linksOf(Scan scan, DynamicRegistryManager registries, Bed bed) {
        if(bed.tag().manual())
            return bed.tag().links().stream().map(offset -> bed.pos().add(offset)).toList();
        BlockPos station = bed.tag().job().map(job -> NpcJobs.stationFor(registries, job, bed.pos(), scan.stations, scan.markers)).orElse(null);
        return station == null ? List.of() : List.of(station);
    }

    // Sent to the server when a bed is first tied by hand; it can't see this
    static List<BlockPos> automaticLinks(BlockPos bedPos) {
        Bed bed = selectedBed(bedPos);
        if(bed == null || bed.tag().manual())
            return List.of();
        return bed.links();
    }

    // Not every job has a station or marker, e.g. hunters go into the wild
    private static boolean needsStation(DynamicRegistryManager registries, Bed bed) {
        return bed.tag().job().filter(job -> !NpcJobs.stationKinds(registries, job).isEmpty() || !NpcJobs.stationMarkers(registries, job).isEmpty()).isPresent();
    }

    private static void drawLabels() {
        Box preview = BuildersToolSelection.boundsFirst() == null ? null : boundsPreview();
        if(preview != null)
            DebugDraw.label("%d x %d x %d".formatted(Math.round(preview.getLengthX()), Math.round(preview.getLengthY()), Math.round(preview.getLengthZ())),
                    new Vec3d(preview.getCenter().x, preview.maxY + PIECE_LABEL_HEIGHT, preview.getCenter().z), BOUNDS, PIECE_TEXT_SCALE);
        for(Map.Entry<BlockPos, String> plug : shown.plugs.entrySet())
            label(plug.getValue(), plug.getKey(), PLUG, STATION_TEXT_SCALE);
        for(BuildSections.Section section : shown.sections)
            DebugDraw.label(BuildSections.shortName(section.name()), new Vec3d(section.box().getCenter().getX() + 0.5, section.box().getMaxY() + PIECE_LABEL_HEIGHT,
                    section.box().getCenter().getZ() + 0.5), sectionColor(section), PIECE_TEXT_SCALE);
        BlockPos selected = BuildersToolSelection.selected();
        if(selected != null) {
            Bed bed = selectedBed(selected);
            if(bed == null)
                return;
            label(bedLabel(bed), bed.pos(), SELECTED_BED, LABEL_TEXT_SCALE);
            for(BlockPos link : bed.links()) {
                label(MinecraftClient.getInstance().world.getBlockState(link).getBlock().getName().getString(), link,
                        bed.tag().manual() ? LINK_MANUAL : LINK_AUTOMATIC, STATION_TEXT_SCALE);
            }
            return;
        }
        for(BlockPos pos : shown.plainBeds)
            label("free (wanderers)", pos, BED_PLAIN, LABEL_TEXT_SCALE);
        for(Bed bed : shown.beds)
            label(bedLabel(bed), bed.pos(), bed.lost() ? BED_LOST : BED_TAGGED, LABEL_TEXT_SCALE);
        for(StructureFeatureData.Marker marker : shown.markers)
            label(markerLabel(marker), marker.pos(), MARKER, LABEL_TEXT_SCALE);
        for(StructureFeatureData.Station station : shown.stations) {
            if(station.kind() != StationKind.FIELD && !shown.offLimits.contains(station.pos()))
                label(station.kind().asString(), station.pos(), stationColor(station.kind()), STATION_TEXT_SCALE);
        }
    }

    private static String bedLabel(Bed bed) {
        String job = bed.tag().job().map(Identifier::getPath).orElse("none");
        String who = bed.tag().npcClass().map(npcClass -> bed.tag().nestKind() + " (" + npcClass.getPath() + ")").orElse(bed.tag().nestKind());
        return who + " / " + job + (bed.tag().manual() ? " (manual)" : "") + (bed.lost() ? " (no station)" : "");
    }

    // Looks like: spawn_animal: horse x2
    private static String markerLabel(StructureFeatureData.Marker marker) {
        if(marker.kind() != MarkerKind.SPAWN_NPC && marker.kind() != MarkerKind.SPAWN_ANIMAL)
            return marker.kind().asString();
        String value = marker.value().isEmpty() ? "?" : marker.value().substring(marker.value().indexOf(Identifier.NAMESPACE_SEPARATOR) + 1);
        return marker.kind().asString() + ": " + value + " x" + marker.count() + (marker.respawns() ? " (respawns)" : "");
    }

    private static void label(String text, BlockPos pos, int color, float scale) {
        DebugDraw.label(text, new Vec3d(pos.getX() + 0.5, pos.getY() + LABEL_HEIGHT, pos.getZ() + 0.5), color, scale);
    }

    private static int stationColor(StationKind kind) {
        return STATION_COLORS[kind.ordinal() % STATION_COLORS.length];
    }
}
