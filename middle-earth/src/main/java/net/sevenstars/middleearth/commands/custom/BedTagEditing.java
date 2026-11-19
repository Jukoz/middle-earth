package net.sevenstars.middleearth.commands.custom;

import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.block.special.beds.BedTagHolder;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.features.npcs.jobs.NpcJobs;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.structurefeatures.BuildFeatures;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// What a builder's requests may do to a bed's tag. Every check is made again here, since a screen can be faked
public class BedTagEditing {
    public static final int MAXIMUM_LINKS = 32;
    private static final int REACH = 64;

    // An empty nest kind takes the tag off. An empty place keeps the bed's current one
    public static void retag(ServerPlayerEntity player, BlockPos pos, String place, String nestKind, String job, String npcClass, boolean resetLinks) {
        if(!StructureEditing.mayEdit(player, pos))
            return;
        BedTagHolder holder = bedAt(player.getEntityWorld(), pos);
        if(holder == null) {
            StructureEditing.log("bed tag at %s refused: no bed head there".formatted(pos.toShortString()));
            return;
        }
        BedTag tag = null;
        if(!nestKind.isEmpty()) {
            tag = tagFor(player.getEntityWorld(), holder, pos, place, nestKind, job, npcClass, resetLinks);
            if(tag == null)
                return;
        }
        holder.setBedTag(tag);
        StructureEditing.log("bed at %s tagged %s".formatted(pos.toShortString(), tag == null ? "nobody" : "`%s` of `%s` job `%s` class `%s` links %d%s".formatted(
                tag.nestKind(), tag.place().map(Identifier::toString).orElse("its manager's place"), tag.job().map(Identifier::toString).orElse("as the nest"), tag.npcClass().map(Identifier::toString).orElse("of the nest"),
                tag.links().size(), tag.manual() ? " by hand" : "")));
    }

    // Null if the request asks for something that does not exist or fit, after logging why
    private static @Nullable BedTag tagFor(World world, BedTagHolder holder, BlockPos pos, String place, String nestKind, String job, String npcClass, boolean resetLinks) {
        Identifier jobId = job.isEmpty() ? null : Identifier.tryParse(job);
        if(!BedTag.nestKinds().contains(nestKind) || !job.isEmpty() && (jobId == null || NpcJobs.get(world.getRegistryManager(), jobId) == null)) {
            StructureEditing.log("bed tag at %s refused: unknown nest kind `%s` or job `%s`".formatted(pos.toShortString(), nestKind, job));
            return null;
        }
        // The screen only offers jobs that fit, so a forged request must meet the same rule
        if(jobId != null && !NpcJobs.fits(world.getRegistryManager(), jobId, BedTag.folkOf(world.getRegistryManager(), nestKind), nestKind)) {
            StructureEditing.log("bed tag at %s refused: job `%s` does not fit `%s`, who are %s".formatted(pos.toShortString(), job, nestKind,
                    BedTag.folkOf(world.getRegistryManager(), nestKind)));
            return null;
        }
        Identifier classId = npcClass.isEmpty() ? null : Identifier.tryParse(npcClass);
        if(!npcClass.isEmpty() && (classId == null || !world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_CLASS).containsId(classId))) {
            StructureEditing.log("bed tag at %s refused: unknown class `%s`".formatted(pos.toShortString(), npcClass));
            return null;
        }
        BedTag before = holder.getBedTag();
        Optional<Identifier> placeId = placeOf(before, place);
        StructureManagerData placeData = placeId.map(found -> StructureManagerService.getStructureManagerData(world, found)).orElse(null);
        if(!place.isEmpty() && (placeData == null || placeData.findPeopleNest(nestKind) == null)) {
            StructureEditing.log("bed tag at %s refused: place `%s` has no `%s`".formatted(pos.toShortString(), place, nestKind));
            return null;
        }
        BedTag tag = new BedTag(nestKind, Optional.ofNullable(jobId), Optional.ofNullable(classId), placeId);
        if(before == null || resetLinks)
            return tag;
        return tag.withLinks(before.links(), before.manual()).withManager(before.manager()).withStore(before.store());
    }

    private static Optional<Identifier> placeOf(@Nullable BedTag before, String place) {
        if(!place.isEmpty())
            return Optional.ofNullable(Identifier.tryParse(place));
        return before == null ? Optional.empty() : before.place();
    }

    // Until the first manual link, the bed's links are auto-found ones only the drawing client knows, so it sends them
    public static void link(ServerPlayerEntity player, BlockPos bed, BlockPos target, List<BlockPos> automatic) {
        if(!StructureEditing.isBuilder(player) || bed.equals(target)) {
            StructureEditing.log("link of bed %s to %s refused: not a creative operator, or the bed itself".formatted(bed.toShortString(), target.toShortString()));
            return;
        }
        if(!isWithinReach(player, bed, target))
            return;
        World world = player.getEntityWorld();
        if(!isBedHead(world, bed)) {
            StructureEditing.log("link of bed %s refused: no bed head there".formatted(bed.toShortString()));
            return;
        }
        if(!(world.getBlockEntity(bed) instanceof BedTagHolder holder) || holder.getBedTag() == null) {
            StructureEditing.log("link of bed %s refused: the bed is not tagged".formatted(bed.toShortString()));
            return;
        }
        BedTag tag = holder.getBedTag();
        BlockPos offset = target.subtract(bed);
        // A chest or barrel becomes the bed's store, and clicking it again unlinks it and the nearest store is
        // used. A fire is a place of work, not a store
        Block clicked = world.getBlockState(target).getBlock();
        if(BuildFeatures.STORE.test(clicked) && BuildFeatures.stationOf(clicked) == null) {
            tieStore(player, holder, tag, bed, target, offset, clicked);
            return;
        }
        List<BlockPos> links = new ArrayList<>();
        if(tag.manual()) {
            links.addAll(tag.links());
        } else {
            for(BlockPos link : automatic) {
                if(link.isWithinDistance(bed, REACH) && !link.equals(bed))
                    links.add(link.subtract(bed));
            }
        }
        boolean unlinked = links.remove(offset);
        if(!unlinked) {
            if(links.size() >= MAXIMUM_LINKS) {
                StructureEditing.log("link of bed %s to %s refused: the bed already has %d links".formatted(bed.toShortString(), target.toShortString(), MAXIMUM_LINKS));
                return;
            }
            links.add(offset);
        }
        holder.setBedTag(tag.withLinks(links, true));
        Text block = clicked.getName();
        StructureEditing.log("bed %s %s %s (%s), %d links by hand now".formatted(bed.toShortString(), unlinked ? "unlinked from" : "linked to", target.toShortString(), block.getString(), links.size()));
        player.sendMessage(Text.translatable("ui.%s.bed_link.%s".formatted(MiddleEarth.MOD_ID, unlinked ? "unlinked" : "linked"), block), true);
    }

    private static void tieStore(ServerPlayerEntity player, BedTagHolder holder, BedTag tag, BlockPos bed, BlockPos target, BlockPos offset, Block clicked) {
        boolean untied = tag.store().filter(offset::equals).isPresent();
        holder.setBedTag(tag.withStore(untied ? Optional.empty() : Optional.of(offset)));
        player.sendMessage(Text.translatable("ui.%s.bed_link.%s".formatted(MiddleEarth.MOD_ID, untied ? "store_untied" : "store_tied"), clicked.getName()), true);
        StructureEditing.log("bed %s %s the store at %s (%s)".formatted(bed.toShortString(), untied ? "untied from" : "tied to", target.toShortString(), clicked.getName().getString()));
    }

    private static boolean isWithinReach(ServerPlayerEntity player, BlockPos bed, BlockPos target) {
        BlockPos at = player.getBlockPos();
        if(!at.isWithinDistance(bed, REACH) || !at.isWithinDistance(target, REACH)) {
            StructureEditing.log("link of bed %s to %s refused: more than %d blocks from the builder".formatted(bed.toShortString(), target.toShortString(), REACH));
            return false;
        }
        if(!StructureEditing.isNear(player.getEntityWorld(), at, bed) || !StructureEditing.isNear(player.getEntityWorld(), at, target)) {
            StructureEditing.log("link of bed %s to %s refused: not loaded".formatted(bed.toShortString(), target.toShortString()));
            return false;
        }
        return true;
    }

    // Null unless the block is the head of a bed that can hold a tag
    private static @Nullable BedTagHolder bedAt(World world, BlockPos pos) {
        if(!isBedHead(world, pos))
            return null;
        return world.getBlockEntity(pos) instanceof BedTagHolder holder ? holder : null;
    }

    private static boolean isBedHead(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.getBlock() instanceof BedBlock && state.get(BedBlock.PART) == BedPart.HEAD;
    }
}
