package net.sevenstars.middleearth.registries.content.structurefeatures;

import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.sevenstars.middleearth.block.special.NpcDoor;
import net.sevenstars.middleearth.block.special.SeatBlock;
import net.sevenstars.middleearth.block.special.StoneTableBlock;
import net.sevenstars.middleearth.block.special.ThickLadderBlock;
import net.sevenstars.middleearth.block.special.WoodTableBlock;
import net.sevenstars.middleearth.features.npcs.classes.data.StationKind;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

// Shared by template data, the datagen block tags (middle-earth:build/<kind>) and the building survey
// Tags aren't bound during datagen, so each kind is a test on the block
public enum BuildFeatures implements Predicate<Block> {
    STORE("store", block -> block instanceof BarrelBlock || block instanceof ChestBlock || block instanceof CampfireBlock),
    SEAT("seat", block -> block instanceof SeatBlock),
    // Work tables are left out; this is for tables to sit at
    TABLE("table", block -> (block instanceof WoodTableBlock || block instanceof StoneTableBlock) && !isWorkTable(block)),
    BED("bed", block -> block instanceof BedBlock),
    FIELD("field", block -> block instanceof FarmlandBlock),
    DOOR("door", block -> block instanceof DoorBlock || block instanceof FenceGateBlock || block instanceof NpcDoor),
    LADDER("ladder", block -> block instanceof LadderBlock || block instanceof ThickLadderBlock || block instanceof ScaffoldingBlock
            || block == DecorativeBlockRegistryME.ROPE || block == DecorativeBlockRegistryME.ROPE_LADDER || block == BlockRegistryME.NET);

    private static final List<String> WORK_TABLES = List.of("crafting_table", "cartography_table", "smithing_table", "fletching_table", "enchanting_table", "artisan_table");

    private final Predicate<Block> test;
    private final TagKey<Block> tag;

    BuildFeatures(String name, Predicate<Block> test) {
        this.test = test;
        this.tag = TagKey.of(RegistryKeys.BLOCK, MiddleEarth.id("build/" + name));
    }

    @Override
    public boolean test(Block block) {
        return test.test(block);
    }

    public TagKey<Block> getTag() {
        return tag;
    }

    private static boolean isWorkTable(Block block) {
        String path = Registries.BLOCK.getId(block).getPath();
        return WORK_TABLES.stream().anyMatch(path::endsWith);
    }

    public static @Nullable StationKind stationOf(Block block) {
        if(FIELD.test(block))
            return StationKind.FIELD;
        return StationKind.of(Registries.BLOCK.getId(block).getPath());
    }

    // Early templates name blocks in the "me" namespace. Null if the block no longer exists
    public static @Nullable Block resolve(String name) {
        Identifier id = Identifier.tryParse(name);
        if(id == null)
            return null;
        Optional<Block> block = Registries.BLOCK.getOptionalValue(id);
        if(block.isEmpty() && id.getNamespace().equals(MiddleEarth.OLD_MOD_ID))
            block = Registries.BLOCK.getOptionalValue(MiddleEarth.id(id.getPath()));
        return block.orElse(null);
    }
}
