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

// The sections of a build that stands in the world to be worked on: each is the box a structure block in save mode saves
// under its name. Nothing else is kept of them, so they are there again whenever the world is, for the game and the
// server alike
public class BuildSections {
    // A structure block stands beside its box, which is no longer than this
    private static final int SEARCH_CHUNKS = (StructureEditing.MAXIMUM_SIZE >> 4) + 2;

    public record Section(BlockPos block, String name, BlockBox box) {
        public long volume() {
            return (long) box.getBlockCountX() * box.getBlockCountY() * box.getBlockCountZ();
        }

        // Side by side, on top of one another or running through each other
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

    // Where one section lies inside another, as a room in the house it was cut from, the block is of the smaller
    public static @Nullable Section at(List<Section> sections, BlockPos pos) {
        return sections.stream().filter(section -> section.box().contains(pos)).min(Comparator.comparingLong(Section::volume)).orElse(null);
    }

    public static @Nullable Section at(World world, BlockPos pos) {
        return at(near(world, pos), pos);
    }

    public static @Nullable Section nearest(List<Section> sections, BlockPos pos) {
        return sections.stream().min(Comparator.comparingDouble(section -> section.box().getCenter().getSquaredDistance(pos))).orElse(null);
    }

    // The sections that make one build with this one: those it touches, and those they touch
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
