package net.sevenstars.middleearth.block.special.structureManager;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import net.sevenstars.middleearth.MiddleEarth;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

// Each section is the box a structure block saves; rebuilt from the world, never stored
public class BuildSections {
    public static final int MAXIMUM_NAME_LENGTH = 128;
    // A structure block sits next to its box, which is never longer than this
    private static final int SEARCH_CHUNKS = (StructureEditing.MAXIMUM_SIZE >> 4) + 2;

    public record Section(BlockPos block, String name, BlockBox box) {
        public long volume() {
            return (long) box.getBlockCountX() * box.getBlockCountY() * box.getBlockCountZ();
        }

        boolean touches(Section other) {
            return box.expand(1).intersects(other.box);
        }
    }

    // Null if it can't name a template. No namespace means the mod's
    public static @Nullable Identifier named(String typed) {
        String name = typed.trim();
        if(name.isEmpty() || name.endsWith("/") || name.endsWith(":") || name.contains("//") || Arrays.asList(name.split("[/:]")).contains(".."))
            return null;
        return Identifier.tryParse(name.contains(":") ? name : MiddleEarth.MOD_ID + ":" + name);
    }

    public static String pathOf(String name) {
        Identifier id = Identifier.tryParse(name);
        return id == null ? name : id.getPath();
    }

    public static String shortName(String name) {
        Identifier id = Identifier.tryParse(name);
        return id != null && id.getNamespace().equals(MiddleEarth.MOD_ID) ? id.getPath() : name;
    }

    public static List<Section> near(World world, BlockPos pos) {
        List<Section> sections = new ArrayList<>();
        ChunkPos middle = new ChunkPos(pos);
        for(int x = middle.x - SEARCH_CHUNKS; x <= middle.x + SEARCH_CHUNKS; x++) {
            for(int z = middle.z - SEARCH_CHUNKS; z <= middle.z + SEARCH_CHUNKS; z++) {
                WorldChunk chunk = world.getChunkManager().getWorldChunk(x, z);
                if(chunk == null)
                    continue;
                for(BlockEntity blockEntity : chunk.getBlockEntities().values()) {
                    if(!(blockEntity instanceof StructureBlockBlockEntity block) || block.getMode() != StructureBlockMode.SAVE || !block.hasStructureName())
                        continue;
                    if(block.getSize().getX() < 1 || block.getSize().getY() < 1 || block.getSize().getZ() < 1)
                        continue;
                    BlockPos from = block.getPos().add(block.getOffset());
                    sections.add(new Section(block.getPos().toImmutable(), block.getTemplateName(), BlockBox.create(from, from.add(block.getSize()).add(-1, -1, -1))));
                }
            }
        }
        sections.sort(Comparator.comparing(Section::block));
        return sections;
    }

    // When one section lies inside another (a room in a house), the block goes to the smaller
    public static @Nullable Section at(List<Section> sections, BlockPos pos) {
        return sections.stream().filter(section -> section.box().contains(pos)).min(Comparator.comparingLong(Section::volume)).orElse(null);
    }

    public static @Nullable Section at(World world, BlockPos pos) {
        return at(near(world, pos), pos);
    }

    public static @Nullable Section nearest(List<Section> sections, BlockPos pos) {
        return sections.stream().min(Comparator.comparingDouble(section -> section.box().getCenter().getSquaredDistance(pos))).orElse(null);
    }

    public static @Nullable Section atOrNearest(List<Section> sections, BlockPos pos) {
        Section own = at(sections, pos);
        return own != null ? own : nearest(sections, pos);
    }

    // Every section joined to the one at or nearest the position
    public static List<Section> buildAround(World world, BlockPos pos) {
        List<Section> near = near(world, pos);
        Section own = atOrNearest(near, pos);
        return own == null ? List.of() : buildOf(near, own);
    }

    public static List<Section> buildOf(List<Section> sections, Section of) {
        List<Section> build = new ArrayList<>();
        List<Section> open = new ArrayList<>();
        build.add(of);
        open.add(of);
        while(!open.isEmpty()) {
            Section next = open.removeLast();
            for(Section other : sections) {
                if(!build.contains(other) && next.touches(other)) {
                    build.add(other);
                    open.add(other);
                }
            }
        }
        build.sort(Comparator.comparing(Section::block));
        return build;
    }
}
