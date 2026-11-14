package net.sevenstars.middleearth.registries.content.forage;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.forage.ForageTable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ForageTables {
    private static final Map<RegistryKey<ForageTable>, ForageTable> TABLES = new LinkedHashMap<>();
    // The kind of land decides, unless the land is one with plants of its own
    private static final int LAND = 1;
    private static final int HARSH_LAND = 2;
    private static final int REALM = 10;

    static {
        land("plains", ConventionalBiomeTags.IS_PLAINS, LAND, "wild_carrot", "wild_onion", "wild_wheat", "wild_beetroot", "wild_lettuce", "wild_flax", "strawberries", "minecraft:apple");
        land("forest", ConventionalBiomeTags.IS_FOREST, LAND, "minecraft:brown_mushroom", "minecraft:red_mushroom", "minecraft:sweet_berries", "strawberries", "wild_garlic", "nettles", "minecraft:stick");
        land("hill", ConventionalBiomeTags.IS_HILL, LAND, "heather", "thistle", "wild_garlic", "wild_onion", "tough_berries");
        land("river", ConventionalBiomeTags.IS_RIVER, LAND, "reeds", "rushes", "wild_leek", "minecraft:brown_mushroom");
        land("beach", ConventionalBiomeTags.IS_BEACH, LAND, "minecraft:kelp", "wild_leek", "rushes", "swan_feather");
        land("savanna", ConventionalBiomeTags.IS_SAVANNA, LAND, "wild_tomato", "wild_bell_pepper", "wild_cucumber", "thistle");
        land("taiga", ConventionalBiomeTags.IS_TAIGA, HARSH_LAND, "minecraft:sweet_berries", "tough_berries", "minecraft:brown_mushroom", "heather", "minecraft:stick");
        land("swamp", ConventionalBiomeTags.IS_SWAMP, HARSH_LAND, "reeds", "rushes", "minecraft:brown_mushroom", "minecraft:red_mushroom", "wild_garlic");
        land("mountain", ConventionalBiomeTags.IS_MOUNTAIN, HARSH_LAND, "tough_berries", "heather", "thistle");
        land("desert", ConventionalBiomeTags.IS_DESERT, HARSH_LAND, "thistle", "minecraft:dead_bush", "minecraft:cactus");
        land("snowy", ConventionalBiomeTags.IS_SNOWY, HARSH_LAND + 1, "tough_berries", "dead_heather", "minecraft:stick");
        land("wasteland", ConventionalBiomeTags.IS_WASTELAND, HARSH_LAND + 1, "thistle", "minecraft:dead_bush", "minecraft:bone");
        land("cave", ConventionalBiomeTags.IS_CAVE, HARSH_LAND + 2, "ghostshroom", "white_mushroom", "trumpet_shroom", "tubeshroom", "minecraft:brown_mushroom");

        // Hobbits have a passion for mushrooms, surpassing even the greediest likings of Big People
        realm("shire", "minecraft:brown_mushroom", "minecraft:brown_mushroom", "minecraft:red_mushroom", "white_mushroom", "strawberries", "minecraft:apple", "wild_carrot", "wild_onion");
        // The golden elanor and the pale niphredil of Cerin Amroth, and mallos
        realm("lothlorien", "elanor", "niphredil", "mallos", "strawberries", "minecraft:sweet_berries");
        // Evermind grows on the mounds of the kings
        realm("rohan", "simbelmyne", "wild_wheat", "wild_onion", "wild_carrot", "heather");
        // Kingsfoil grows wild where the men of the West once dwelt, and Ithilien is a garden gone wild
        realm("ithilien", "athelas", "lavender", "wild_garlic", "wild_onion", "thistle", "strawberries");
        realm("gondor", "athelas", "wild_wheat", "wild_lettuce", "wild_onion", "lavender");
        realm("mirkwood", "minecraft:brown_mushroom", "minecraft:red_mushroom", "ghostshroom", "nettles", "minecraft:stick");
        realm("mordor", "thistle", "minecraft:bone", "minecraft:dead_bush");
    }

    public static void bootstrap(Registerable<ForageTable> context) {
        TABLES.forEach(context::register);
    }

    // What grows where somebody is foraging. Empty where no table knows the place
    public static List<Item> itemsAt(ServerWorld world, BlockPos pos) {
        Optional<Registry<ForageTable>> tables = world.getRegistryManager().getOptional(DynamicRegistriesME.FORAGE);
        if(tables.isEmpty())
            return List.of();
        RegistryEntry<Biome> biome = world.getBiome(pos);
        ForageTable best = null;
        for(ForageTable table : tables.get()) {
            if(table.fits(biome) && !table.items().isEmpty() && (best == null || table.priority() > best.priority()))
                best = table;
        }
        return best == null ? List.of() : best.items();
    }

    private static void land(String name, TagKey<Biome> tag, int priority, String... items) {
        TABLES.put(key(name), new ForageTable(Optional.of(tag), Optional.empty(), items(items), priority));
    }

    private static void realm(String name, String... items) {
        TABLES.put(key(name), new ForageTable(Optional.empty(), Optional.of(name), items(items), REALM));
    }

    private static RegistryKey<ForageTable> key(String name) {
        return RegistryKey.of(DynamicRegistriesME.FORAGE, MiddleEarth.id(name));
    }

    // The mod's own unless a namespace says otherwise
    private static List<Item> items(String... ids) {
        List<Item> items = new ArrayList<>();
        for(String id : ids) {
            Item item = Registries.ITEM.get(id.contains(":") ? net.minecraft.util.Identifier.of(id) : MiddleEarth.id(id));
            if(item == Items.AIR)
                throw new IllegalStateException("No such item to forage: " + id);
            items.add(item);
        }
        return items;
    }
}
