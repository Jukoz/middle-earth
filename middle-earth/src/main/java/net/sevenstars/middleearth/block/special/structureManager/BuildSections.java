package net.sevenstars.middleearth.block.special.structureManager;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// The sections of a build placed in the world for editing. Each is the box that a structure block in save mode saves
// under its name. Nothing else is stored, so they are rebuilt from the world itself, on the client and the server alike
public class BuildSections {
    // A structure block sits next to its box, and a box is never longer than this
    private static final int SEARCH_CHUNKS = (StructureEditing.MAXIMUM_SIZE >> 4) + 2;

    public record Section(BlockPos block, String name, BlockBox box) {
        public long volume() {
            return (long) box.getBlockCountX() * box.getBlockCountY() * box.getBlockCountZ();
        }

        // Adjacent, stacked or overlapping
        boolean touches(Section other) {
            return box.expand(1).intersects(other.box);
        }
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

    // When one section lies inside another, like a room in a house, the block belongs to the smaller one
    public static @Nullable Section at(List<Section> sections, BlockPos pos) {
        return sections.stream().filter(section -> section.box().contains(pos)).min(Comparator.comparingLong(Section::volume)).orElse(null);
    }

    public static @Nullable Section at(World world, BlockPos pos) {
        return at(near(world, pos), pos);
    }

    public static @Nullable Section nearest(List<Section> sections, BlockPos pos) {
        return sections.stream().min(Comparator.comparingDouble(section -> section.box().getCenter().getSquaredDistance(pos))).orElse(null);
    }

    // The sections that form one build with this one: the ones it touches, and the ones those touch
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
