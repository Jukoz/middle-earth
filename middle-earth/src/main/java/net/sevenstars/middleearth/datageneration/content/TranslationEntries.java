package net.sevenstars.middleearth.datageneration.content;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.sevenstars.api.enums.LangCategory;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.content.races.RacePools;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranslationEntries {
    public static List<Block> blockEntries = new ArrayList<>() {
    };

    public static List<Item> itemEntries = new ArrayList<>() {
    };

    public static List<Identifier> spawnEggEntries = new ArrayList<>() {
    };

    public static List<EntityType<?>> entityEntries = new ArrayList<>() {
    };

    public static List<String> biomeEntries = new ArrayList<>() {
    };

    public static List<String> bannerPatternEntries = new ArrayList<>() {
    };
    public static List<String> raceEntries = new ArrayList<>() {
    };
    public static List<String> factionEntries = new ArrayList<>() {
    };
    public static List<String> npcTypeEntries = new ArrayList<>() {
    };
    public static List<StructureManagerData> structureManagerEntries = new ArrayList<>() {
    };
    public static List<String> spawnEntries = new ArrayList<>() {
    };

    public static List<String> inscriptionEntries = new ArrayList<>() {

    };

    public static Map<String, String> manualEntries = new HashMap<>() {
        {
            put(of(LangCategory.ITEM_GROUP, "stone_blocks"), "Middle-earth Stone Blocks");
            put(of(LangCategory.ITEM_GROUP, "wood_blocks"), "Middle-earth Wood Blocks");
            put(of(LangCategory.ITEM_GROUP, "misc_blocks"), "Middle-earth Misc Blocks");
            put(of(LangCategory.ITEM_GROUP, "nature_blocks"), "Middle-earth Nature Blocks");
            put(of(LangCategory.ITEM_GROUP, "decorative_blocks"), "Middle-earth Decorative Blocks");
            put(of(LangCategory.ITEM_GROUP, "food_items"), "Middle-earth Food");
            put(of(LangCategory.ITEM_GROUP, "weapon_items"), "Middle-earth Weapons");
            put(of(LangCategory.ITEM_GROUP, "equipment_items"), "Middle-earth Equipment");
            put(of(LangCategory.ITEM_GROUP, "tool_items"), "Middle-earth Tools");
            put(of(LangCategory.ITEM_GROUP, "resource_items"), "Middle-earth Resources");
            put(of(LangCategory.ITEM_GROUP, "spawn_egg_items"), "Middle-earth Spawn Eggs");

            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.root.title"), "Long expected journey");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.root.description"), "Enter Middle-earth!");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_dale.title"), "The Kingdom of Dale");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_dale.description"), "Enter the realm of Dale");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_gondor.title"), "The South-kingdom");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_gondor.description"), "Enter the last Kingdom of Númenor's legacy");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_gundabad.title"), "The Three Peaks");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_gundabad.description"), "Gundabad, the awakening mountain of Durin the Deathless, now infested by orcs.");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_lothlorien.title"), "The Golden Trees");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_lothlorien.description"), "Enter Lothlórien");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_lonely_mountain.title"), "Erebor");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_lonely_mountain.description"), "The capital of the dwarves");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_mirkwood.title"), "Stay on the path!");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_mirkwood.description"), "Enter the forest of Mirkwood");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_dol_guldur.title"), "Necromancer's Hill");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_dol_guldur.description"), "Enter Dol Guldur");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_woodland_realm.title"), "Elvenking's Halls");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_woodland_realm.description"), "Enter The Woodland Realm");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_misty_mountains.title"), "Far over...");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_misty_mountains.description"), "Enter the Misty Mountains");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_mordor.title"), "One does not simply...");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_mordor.description"), "Walk into Mordor");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_nan_curunir.title"), "The valley of the Wizard");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_nan_curunir.description"), "Enter the valley of Nan Curunír");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_rohan.title"), "Riddermark");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_rohan.description"), "Enter the Kingdom of the Rohirrim");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_shire.title"), "The quiet countryside");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.enter_shire.description"), "Enter the Shire");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.brigand_dungeon.title"), "Shirriff, open up!");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.brigand_dungeon.description"), "Raid the thieves in their dungeon!");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.spider_burrows.title"), "Sticky lair");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.spider_burrows.description"), "Climb down to the Spider Burrows");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.kill_spawn_of_shelob.title"), "Attercop?");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.kill_spawn_of_shelob.description"), "Kill a spawn of Shelob!");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.arkenstone.title"), "Heart of the Mountain");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.arkenstone.description"), "Find the Arkenstone, the jewel of the Lonely Mountain!");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.lembas.title"), "Elvish Bread");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.lembas.description"), "One bite is enough to fill the stomach of a grown man!");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.strawberry.title"), "Do you remember?");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.strawberry.description"), "The taste of strawberries");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.mithril.title"), "As light as feathers");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.mithril.description"), "Mine some Mithril!");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.tin.title"), "Fool's Iron");
            put(ofRaw(LangCategory.ADVANCEMENTS, "middle_earth.tin.description"), "Tin again! Maybe I can find iron deeper");

            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.root.title"), "Smithing");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.root.description"), "All begins with the Forge!");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.bellows.title"), "Not Hot Enough?");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.bellows.description"), "Craft bellows to heat up your forge");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.artisan_table.title"), "Artisan Work");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.artisan_table.description"), "Craft the artisan table");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.inscription_table.title"), "Inspired Smithing");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.inscription_table.description"), "Improve a piece of equipment like the smiths of yore");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.chisel.title"), "Chiseled Figure");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.chisel.description"), "Create an Inscribing Chisel");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.smithing_hammer.title"), "Hammer Time");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.smithing_hammer.description"), "Create a Smithing Hammer");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.bronze_ingot.title"), "Bronze Age");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.bronze_ingot.description"), "A bit of tin and some copper");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.crude_ingot.title"), "Orcish Handiwork");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.crude_ingot.description"), "Forge the cruel and wicked crude metal");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.khazad_steel.title"), "Dwarven Mastery");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.khazad_steel.description"), "Forge the renowned Khazâd-Steel");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.dwarven_treated_anvil.title"), "Song of the Mountain Halls");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.dwarven_treated_anvil.description"), "Craft a Dwarven anvil to create Dwarven items");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.edhel_steel.title"), "Firstborn Artistry");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.edhel_steel.description"), "Forge the delightful Edhel-Steel");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.elven_treated_anvil.title"), "Ballad of Fëanor");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.elven_treated_anvil.description"), "Craft an Elven anvil to create Elven items");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.steel.title"), "Mannish Smithing");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.steel.description"), "Forge the popular steel of middle-men");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.sturdy_storage.title"), "Sturdy Storage");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.sturdy_storage.description"), "Craft the reinforced chest");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.tin_cauldron.title"), "Quenching the fire");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.tin_cauldron.description"), "Craft a tin cauldron to cool down your smithing work");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.treated_anvil.title"), "Forged in Fire");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.treated_anvil.description"), "Craft a basic anvil to being smithing");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.burzum_steel.title"), "Wicked Metalwork");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.burzum_steel.description"), "Forge the mighty Búrzum-Steel");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.orcish_treated_anvil.title"), "Touched by Shadow");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.orcish_treated_anvil.description"), "Craft an Orcish anvil to create Orcish items");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.orcish_sconce.title"), "Cruel Fire");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.orcish_sconce.description"), "Craft an orcish sconce");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.crystal_lamp.title"), "Light of Khazad-dûm");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.crystal_lamp.description"), "Craft a crystal lamp");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.dwarven_lantern.title"), "A light in the dark");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.dwarven_lantern.description"), "Craft a dwarven lantern");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.elven_lantern.title"), "Light of the Elves");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.elven_lantern.description"), "Light your way like the Quendi");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.hot_ones.title"), "Hot Ones");
            put(ofRaw(LangCategory.ADVANCEMENTS, "smithing.hot_ones.description"), "Eat a spicy chicken nugget");

            put(of(LangCategory.EFFECT, "hallucination"), "Hallucination");
            put(of(LangCategory.EFFECT, "enshrouded"), "Enshrouded");
            put(of(LangCategory.EFFECT, "restrained"), "Restrained");

            put(of(LangCategory.ENCHANTMENT, "ailment_protection"), "Ailment Protection");
            put(of(LangCategory.ENCHANTMENT, "aule_blessing"), "Aulë Blessing");
            put(of(LangCategory.ENCHANTMENT, "bane_of_giants"), "Bane of Giants");
            put(of(LangCategory.ENCHANTMENT, "beheading"), "Beheading");
            put(of(LangCategory.ENCHANTMENT, "celerity"), "Celerity");
            put(of(LangCategory.ENCHANTMENT, "first_strike"), "First Strike");
            put(of(LangCategory.ENCHANTMENT, "grounded"), "Grounded");
            put(of(LangCategory.ENCHANTMENT, "hewing"), "Hewing");
            put(of(LangCategory.ENCHANTMENT, "high_step"), "High Step");
            put(of(LangCategory.ENCHANTMENT, "miner_reach"), "Miner Reach");
            put(of(LangCategory.ENCHANTMENT, "stealthy_trail"), "Stealthy Trail");
            put(of(LangCategory.ENCHANTMENT, "stride"), "Stride");
            put(of(LangCategory.ENCHANTMENT, "stalwart"), "Stalwart");
            put(of(LangCategory.ENCHANTMENT, "tree_feller"), "Tree Feller");
            put(of(LangCategory.ENCHANTMENT, "vantage"), "Vantage");

            put(of(LangCategory.BLOCK, "potted_beech_sapling"), "Potted Beech Sapling");
            put(of(LangCategory.BLOCK, "potted_chestnut_sapling"), "Potted Chestnut Sapling");
            put(of(LangCategory.BLOCK, "potted_fir_sapling"), "Potted Fir Sapling");
            put(of(LangCategory.BLOCK, "potted_holly_sapling"), "Potted Holly Sapling");
            put(of(LangCategory.BLOCK, "potted_larch_sapling"), "Potted Larch Sapling");
            put(of(LangCategory.BLOCK, "potted_lebethron_sapling"), "Potted Lebethron Sapling");
            put(of(LangCategory.BLOCK, "potted_white_lebethron_sapling"), "Potted White Lebethron Sapling");
            put(of(LangCategory.BLOCK, "potted_mallorn_sapling"), "Potted Mallorn Sapling");
            put(of(LangCategory.BLOCK, "potted_maple_sapling"), "Potted Maple Sapling");
            put(of(LangCategory.BLOCK, "potted_silver_maple_sapling"), "Potted Silver Maple Sapling");
            put(of(LangCategory.BLOCK, "potted_mirkwood_sapling"), "Potted Mirkwood Sapling");
            put(of(LangCategory.BLOCK, "potted_palm_sapling"), "Potted Palm Sapling");
            put(of(LangCategory.BLOCK, "potted_white_palm_sapling"), "Potted White Palm Sapling");
            put(of(LangCategory.BLOCK, "potted_pine_sapling"), "Potted Pine Sapling");
            put(of(LangCategory.BLOCK, "potted_black_pine_sapling"), "Potted Black Pine Sapling");
            put(of(LangCategory.BLOCK, "potted_willow_sapling"), "Potted Willow Sapling");

            put(of(LangCategory.BLOCK, "potted_green_shrub"), "Potted Green Shrub");
            put(of(LangCategory.BLOCK, "potted_mallos"), "Potted Mallos");
            put(of(LangCategory.BLOCK, "potted_yellow_flower"), "Potted Yellow Flower");
            put(of(LangCategory.BLOCK, "potted_yellow_trollius"), "Potted Yellow Trollius");
            put(of(LangCategory.BLOCK, "potted_tan_shrub"), "Potted Tan Shrub");
            put(of(LangCategory.BLOCK, "potted_green_jewel_cornflower"), "Potted Green Jewel Cornflower");
            put(of(LangCategory.BLOCK, "potted_scorched_shrub"), "Potted Scorched Shrub");
            put(of(LangCategory.BLOCK, "potted_frozen_shrub"), "Potted Frozen Shrub");

            put(of(LangCategory.BLOCK, "potted_cave_amanita"), "Potted Cave Amanita");
            put(of(LangCategory.BLOCK, "potted_deep_firecap"), "Potted Deep Firecap");
            put(of(LangCategory.BLOCK, "potted_ghostshroom"), "Potted Ghostshroom");
            put(of(LangCategory.BLOCK, "potted_morsel"), "Potted Morsel");
            put(of(LangCategory.BLOCK, "potted_sky_firecap"), "Potted Sky Firecap");
            put(of(LangCategory.BLOCK, "potted_trumpet_shroom"), "Potted Trumpet Shroom");
            put(of(LangCategory.BLOCK, "potted_tubeshroom"), "Potted Tubeshroom");
            put(of(LangCategory.BLOCK, "potted_violet_caps"), "Potted Violet Caps");
            put(of(LangCategory.BLOCK, "potted_white_mushroom"), "Potted White Mushroom");
            put(of(LangCategory.BLOCK, "potted_yellow_amanita"), "Potted Yellow Amanita");

            put(of(LangCategory.BLOCK, "sconce"), "Sconce");
            put(of(LangCategory.BLOCK, "gilded_sconce"), "Gilded Sconce");
            put(of(LangCategory.BLOCK, "orcish_sconce"), "Orcish Sconce");

            put(of(LangCategory.BLOCK, "duckweed"), "Duckweed");
            put(of(LangCategory.BLOCK, "lily_pads"), "Lily Pads");
            put(of(LangCategory.BLOCK, "small_lily_pads"), "Small Lily Pads");
            put(of(LangCategory.BLOCK, "small_flowering_lily_pads"), "Small Flowering Lily Pads");

            put(of(LangCategory.BLOCK, "strawberry_bush"), "Strawberry Bush");
            put(of(LangCategory.BLOCK, "tough_berry_bush"), "Tough Berry Bush");

            put(of(LangCategory.BLOCK, "glowworm_main"), "Glowworm Main");

            put(of(LangCategory.BLOCK, "floating_ice"), "Floating Ice");

            put(of(LangCategory.BLOCK, "dwarven_lantern"), "Dwarven Lantern");
            put(of(LangCategory.BLOCK, "crystal_lamp"), "Crystal Lamp");
            put(of(LangCategory.BLOCK, "silver_lantern"), "Silver Lantern");
            put(of(LangCategory.BLOCK, "elven_lantern"), "Elven Lantern");

            put(of(LangCategory.BLOCK, "forge"), "Forge");
            put(of(LangCategory.BLOCK, "dwarven_forge"), "Dwarven Forge");
            put(of(LangCategory.BLOCK, "elven_forge"), "Elven Forge");
            put(of(LangCategory.BLOCK, "orcish_forge"), "Orcish Forge");
            put(of(LangCategory.BLOCK, "bellows"), "Bellows");
            put(of(LangCategory.BLOCK, "treated_anvil"), "Treated Anvil");
            put(of(LangCategory.BLOCK, "dwarven_treated_anvil"), "Dwarven Treated Anvil");
            put(of(LangCategory.BLOCK, "elven_treated_anvil"), "Elven Treated Anvil");
            put(of(LangCategory.BLOCK, "orcish_treated_anvil"), "Orcish Treated Anvil");
            put(of(LangCategory.BLOCK, "artisan_table"), "Artisan Table");
            put(of(LangCategory.BLOCK, "orcish_artisan_table"), "Orcish Artisan Table");
            put(of(LangCategory.BLOCK, "inscription_table"), "Inscription Table");

            put(of(LangCategory.BLOCK, "structure_manager"), "Structure Manager");

            put(of(LangCategory.BLOCK, "larch_coffer"), "Larch Coffer");
            put(of(LangCategory.BLOCK, "pine_coffer"), "Pine Coffer");
            put(of(LangCategory.BLOCK, "spruce_coffer"), "Spruce Coffer");
            put(of(LangCategory.BLOCK, "fir_coffer"), "Fir Coffer");
            put(of(LangCategory.BLOCK, "beech_coffer"), "Beech Coffer");
            put(of(LangCategory.BLOCK, "chestnut_coffer"), "Chestnut Coffer");
            put(of(LangCategory.BLOCK, "oak_coffer"), "Oak Coffer");
            put(of(LangCategory.BLOCK, "willow_coffer"), "Willow Coffer");
            put(of(LangCategory.BLOCK, "reinforced_chest"), "Reinforced Chest");
            put(of(LangCategory.BLOCK, "small_crate"), "Small Crate");
            put(of(LangCategory.BLOCK, "thin_barrel"), "Thin Barrel");

            put(of(LangCategory.BLOCK, "fire_of_orthanc"), "Fire of Orthanc");
            put(of(LangCategory.BLOCK, "torch_of_orthanc"), "Torch of Orthanc");

            put(of(LangCategory.BLOCK, ".wood_pile"), "Wood Pile");

            put(of(LangCategory.BLOCK, "tall_black_pine_door"), "Tall Black Pine Door");

            put(of(LangCategory.BLOCK, "oak_stable_door"), "Oak Stable Door");
            put(of(LangCategory.BLOCK, "reinforced_spruce_door"), "Reinforced Spruce Door");
            put(of(LangCategory.BLOCK, "reinforced_black_pine_door"), "Reinforced Black Pine Door");
            put(of(LangCategory.BLOCK, "simple_larch_gate"), "Simple Larch Gate");
            put(of(LangCategory.BLOCK, "rickety_simple_larch_door"), "Rickety Simple Larch Door");
            put(of(LangCategory.BLOCK, "spruce_stable_door"), "Spruce Stable Door");

            put(of(LangCategory.BLOCK, "large_sturdy_door"), "Large Sturdy Door");

            put(of(LangCategory.BLOCK, "large_beech_fence_gate"), "Large Beech Fence Gate");

            put(of(LangCategory.BLOCK, "larch_hobbit_door"), "Larch Hobbit Door");
            put(of(LangCategory.BLOCK, "spruce_hobbit_door"), "Spruce Hobbit Door");

            put(of(LangCategory.BLOCK, "blue_hobbit_door"), "Blue Hobbit Door");
            put(of(LangCategory.BLOCK, "green_hobbit_door"), "Green Hobbit Door");
            put(of(LangCategory.BLOCK, "light_blue_hobbit_door"), "Light Blue Hobbit Door");
            put(of(LangCategory.BLOCK, "red_hobbit_door"), "Red Hobbit Door");
            put(of(LangCategory.BLOCK, "yellow_hobbit_door"), "Yellow Hobbit Door");

            put(of(LangCategory.BLOCK, "great_gondorian_gate"), "Great Gondorian Gate");

            put(of(LangCategory.BLOCK, "great_dwarven_gate"), "Great Dwarven Gate");
            put(of(LangCategory.BLOCK, "varnished_dwarven_door"), "Varnished Dwarven Door");
            put(of(LangCategory.BLOCK, "ruined_dwarven_door"), "Ruined Dwarven Door");
            put(of(LangCategory.BLOCK, "hidden_dwarven_door"), "Hidden Dwarven Door");

            put(of(LangCategory.BLOCK, "great_elven_gate"), "Great Elven Gate");

            put(of(LangCategory.BLOCK, "great_orcish_gate"), "Great Orcish Gate");

            put(of(LangCategory.BLOCK, "faction_banner"), "%s Banner");

            put(of(LangCategory.ITEM, "gondor_banner_pattern.desc"), "White Tree of Gondor");
            put(of(LangCategory.ITEM, "rohan_banner_pattern.desc"), "Rohan Horse Head");
            put(of(LangCategory.ITEM, "lothlorien_banner_pattern.desc"), "Tree of Lórien");
            put(of(LangCategory.ITEM, "mordor_banner_pattern.desc"), "The Great Eye of Sauron");
            put(of(LangCategory.ITEM, "misty_mountains_orcs_banner_pattern.desc"), "The symbols of the Orcs of the Misty Mountains");
            put(of(LangCategory.ITEM, "isengard_banner_pattern.desc"), "The White Hand of Saruman");
            put(of(LangCategory.ITEM, "goblin_skull_banner_pattern.desc"), "The Skull of a Goblin");
            put(of(LangCategory.ITEM, "anvil_banner_pattern.desc"), "Anvil Icons");
            put(of(LangCategory.ITEM, "bell_banner_pattern.desc"), "Bell Icons");
            put(of(LangCategory.ITEM, "bow_banner_pattern.desc"), "Bow Icons");
            put(of(LangCategory.ITEM, "dwarf_crown_banner_pattern.desc"), "Dwarf Crown Icons");
            put(of(LangCategory.ITEM, "great_horn_pattern.desc"), "Elk and Stag");
            put(of(LangCategory.ITEM, "oak_leaf.desc"), "Oak Leaf");
            put(of(LangCategory.ITEM, "antlers.desc"), "Lost Antlers");
            put(of(LangCategory.ITEM, "dragon_banner_pattern.desc"), "A great Dragon");
            put(of(LangCategory.ITEM, "pipeweed_banner_pattern.desc"), "A delicacy from the shire");
            put(of(LangCategory.ITEM, "snail_banner_pattern.desc"), "The Slimy Overlord");
            put(of(LangCategory.ITEM, "spider_banner_pattern.desc"), "Spider of Mirkwood");

            put("middle-earth.music.menu.swept_away", "Swept Away");

             put(of(LangCategory.SOUNDS, "bellows_push"), "Bellows blowing");
             put(of(LangCategory.SOUNDS, "chisel_hit"), "Chisel hits");
             put(of(LangCategory.SOUNDS, "chisel_enchant"), "Chisel enchants");
             put(of(LangCategory.SOUNDS, "nazgul_fade"), "Nazgûl fading");
             put(of(LangCategory.SOUNDS, "nazgul_scream"), "Nazgûl screaming");
             put(of(LangCategory.SOUNDS, "pipe_refill"), "Pipe refills");
             put(of(LangCategory.SOUNDS, "pipe_exhale"), "Pipe exhales");
             put(of(LangCategory.SOUNDS, "pipe_ignite"), "Pipe ignites");
             put(of(LangCategory.SOUNDS, "orc_drum"), "Orc drums");
             put(of(LangCategory.SOUNDS, "great_horn_idle"), "Great Horn grunts");
             put(of(LangCategory.SOUNDS, "great_horn_hurt"), "Great Horn hurts");
             put(of(LangCategory.SOUNDS, "great_horn_call"), "Great Horn bugles");
             put(of(LangCategory.SOUNDS, "great_horn_death"), "Great Horn dies");

            put(of(LangCategory.SOUNDS, "cave_troll_death"), "Cave Troll dies");
            put(of(LangCategory.SOUNDS, "cave_troll_hurt"), "Cave Troll hurts");
            put(of(LangCategory.SOUNDS, "cave_troll_idle"), "Cave Troll grunts");
            put(of(LangCategory.SOUNDS, "cave_troll_roar"), "Cave Troll roars");
            put(of(LangCategory.SOUNDS, "cave_troll_step"), "Cave Troll steps");

            put(of(LangCategory.SCREEN, "forge"), "Forge");
            put(of(LangCategory.SCREEN, "structure_manager"), "Structure Manager");
            put(of(LangCategory.CONTAINER, "artisan_table"), "Artisan Table");
            put(of(LangCategory.CONTAINER, "inscription_table"), "Inscription Table");
            put(of(LangCategory.CONTAINER, "small_crate"), "Small Crate");
            put(of(LangCategory.CONTAINER, "thin_barrel"), "Thin Barrel");
            put(of(LangCategory.SCREEN, "larch_coffer"), "Larch Coffer");
            put(of(LangCategory.SCREEN, "pine_coffer"), "Pine Coffer");
            put(of(LangCategory.SCREEN, "spruce_coffer"), "Spruce Coffer");
            put(of(LangCategory.SCREEN, "fir_coffer"), "Fir Coffer");
            put(of(LangCategory.SCREEN, "beech_coffer"), "Beech Coffer");
            put(of(LangCategory.SCREEN, "chestnut_coffer"), "Chestnut Coffer");
            put(of(LangCategory.SCREEN, "oak_coffer"), "Oak Coffer");
            put(of(LangCategory.SCREEN, "willow_coffer"), "Willow Coffer");
            put(of(LangCategory.SCREEN, "reinforced_chest"), "Reinforced Chest");
            put(of(LangCategory.SCREEN, "sack"), "Sack");
            put(of(LangCategory.SCREEN, "wood_pile"), "Wood Pile");
            put(of(LangCategory.SCREEN, "shaping_anvil"), "Shaping Anvil");
            put(of(LangCategory.SCREEN, "artisan_table.weapons"), "Weapons");
            put(of(LangCategory.SCREEN, "artisan_table.sword"), "Sword");
            put(of(LangCategory.SCREEN, "artisan_table.axe"), "Axe");
            put(of(LangCategory.SCREEN, "artisan_table.spear"), "Spear");
            put(of(LangCategory.SCREEN, "artisan_table.bow"), "Bow");
            put(of(LangCategory.SCREEN, "artisan_table.crossbow"), "Crossbow");
            put(of(LangCategory.SCREEN, "artisan_table.tools"), "Tools");
            put(of(LangCategory.SCREEN, "artisan_table.pickaxe"), "Pickaxe");
            put(of(LangCategory.SCREEN, "artisan_table.shovel"), "Shovel");
            put(of(LangCategory.SCREEN, "artisan_table.hoe"), "Hoe");
            put(of(LangCategory.SCREEN, "artisan_table.chisel"), "Chisel");
            put(of(LangCategory.SCREEN, "artisan_table.armors"), "Armors");
            put(of(LangCategory.SCREEN, "artisan_table.helmet"), "Helmet");
            put(of(LangCategory.SCREEN, "artisan_table.chestplate"), "Chestplate");
            put(of(LangCategory.SCREEN, "artisan_table.leggings"), "Leggings");
            put(of(LangCategory.SCREEN, "artisan_table.boots"), "Boots");
            put(of(LangCategory.SCREEN, "artisan_table.mount_armor"), "Mount Armor");
            put(of(LangCategory.SCREEN, "artisan_table.shields"), "Shields");
            put(of(LangCategory.SCREEN, "artisan_table.light_shield"), "Light Shield");
            put(of(LangCategory.SCREEN, "artisan_table.medium_shield"), "Medium Shield");
            put(of(LangCategory.SCREEN, "artisan_table.heavy_shield"), "Heavy Shield");
            put(of(LangCategory.SCREEN, "artisan_table.misc"), "Misc");
            put(of(LangCategory.SCREEN, "artisan_table.hat"), "Hat");
            put(of(LangCategory.SCREEN, "artisan_table.helmet_attachment"), "Helmet Attachment");
            put(of(LangCategory.SCREEN, "artisan_table.back_attachment"), "Back Attachment");
            put(of(LangCategory.SCREEN, "artisan_table.pipe"), "Pipe");
            
            put(of(LangCategory.TOOLTIP, "type"), "Type: ");
            put(of(LangCategory.TOOLTIP, "faction"), "Faction: ");
            put(of(LangCategory.TOOLTIP, "sub_faction"), "Sub Faction: ");
            put(of(LangCategory.TOOLTIP, "race"), "Race: ");
            put(of(LangCategory.TOOLTIP, "customizations"), "Custom Parts: ");
            put(of(LangCategory.TOOLTIP, "reach"), "Reach: ");
            put(of(LangCategory.TOOLTIP, "dyed"), "Dyed: ");
            put(of(LangCategory.TOOLTIP, "artisan"), "Artisan: ");
            put(of(LangCategory.TOOLTIP, "author"), "Author: ");
            put(of(LangCategory.TOOLTIP, "blocks_range"), " Blocks");
            put(of(LangCategory.TOOLTIP, "backstab"), "+50% damage when backstabbing");
            put(of(LangCategory.TOOLTIP, "door_size"), "Door size: ");

            put(of(LangCategory.TOOLTIP, "tier_clothing"), "Clothing");
            put(of(LangCategory.TOOLTIP, "tier_basic"), "Basic Armor");
            put(of(LangCategory.TOOLTIP, "tier_light"), "Light Armor");
            put(of(LangCategory.TOOLTIP, "tier_medium"), "Medium Armor");
            put(of(LangCategory.TOOLTIP, "tier_sturdy"), "Sturdy Armor");
            put(of(LangCategory.TOOLTIP, "tier_heavy"), "Heavy Armor");
            put(of(LangCategory.TOOLTIP, "tier_mithril"), "Mithril");

            put(of(LangCategory.TOOLTIP, "artefact"), "Artefact");
            put(of(LangCategory.TOOLTIP, "broken"), "Broken");

            put(of(LangCategory.TOOLTIP, "sword"), "Sword");
            put(of(LangCategory.TOOLTIP, "axe"), "Axe");
            put(of(LangCategory.TOOLTIP, "dagger"), "Dagger");
            put(of(LangCategory.TOOLTIP, "spear"), "Spear");
            put(of(LangCategory.TOOLTIP, "longsword"), "Longsword");
            put(of(LangCategory.TOOLTIP, "troll_weapon"), "Troll Weapon");

            put(of(LangCategory.TOOLTIP, "bow"), "Bow");
            put(of(LangCategory.TOOLTIP, "longbow"), "Longbow");
            put(of(LangCategory.TOOLTIP, "crossbow"), "Crossbow");

            put(of(LangCategory.TOOLTIP, "light_shield"), "Light Shield");
            put(of(LangCategory.TOOLTIP, "medium_shield"), "Medium Shield");
            put(of(LangCategory.TOOLTIP, "heavy_shield"), "Heavy Shield");

            put(of(LangCategory.TOOLTIP, "generic"), "Generic");

            put(of(LangCategory.TOOLTIP, "dwarven"), "Dwarven");
            put(of(LangCategory.TOOLTIP, "elven"), "Elven");
            put(of(LangCategory.TOOLTIP, "orc"), "Orc");

            put(of(LangCategory.TOOLTIP, "color"), "Color");

            put(of(LangCategory.TOOLTIP, "mount_armor_addon_top"), "Top Armor Addon, Enabled");
            put(of(LangCategory.TOOLTIP, "mount_armor_addon_side"), "Side Armor Addon, Enabled");

            put(of(LangCategory.TOOLTIP, "forge_output_mode_await"), "Select a cast");
            put(of(LangCategory.TOOLTIP, "forge_output_mode0"), "Not enough Metal.");
            put(of(LangCategory.TOOLTIP, "forge_output_mode1"), "1 Nugget");
            put(of(LangCategory.TOOLTIP, "forge_output_mode2"), "1 Ingot");
            put(of(LangCategory.TOOLTIP, "forge_output_mode3"), "2 Ingots");
            put(of(LangCategory.TOOLTIP, "forge_output_mode5"), "3 Ingots");

            put(of(LangCategory.TOOLTIP, "anvil_hammer"), "Left click the anvil with a");
            put(of(LangCategory.TOOLTIP, "anvil_hammer_2"), "Smithing Hammer to shape the metal.");

            put(of(LangCategory.TOOLTIP, "forge_mode_switch_alloying"), "Click to switch to Heating Mode");
            put(of(LangCategory.TOOLTIP, "forge_mode_switch_heating"), "Click to switch to Alloying Mode");
            put(of(LangCategory.TOOLTIP, "ingots_number"), "Ingots");
            put(of(LangCategory.TOOLTIP, "nuggets_number"), "Nuggets");

            put(of(LangCategory.TOOLTIP, "temp_1"), "Warm");
            put(of(LangCategory.TOOLTIP, "temp_2"), "Hot");
            put(of(LangCategory.TOOLTIP, "temp_3"), "Very Hot");
            put(of(LangCategory.TOOLTIP, "temp_4"), "Searing Hot");
            put(of(LangCategory.TOOLTIP, "temp_5"), "Blazing Hot");

            put(of(LangCategory.TOOLTIP, "biome"), "Biome");

            //TODO try to automate
            put(of(LangCategory.TRIM_PATTERN, "smithing_part"), "Smithing Part");
            put(of(LangCategory.TRIM_MATERIAL, "copper"), "Copper");
            put(of(LangCategory.TRIM_MATERIAL, "iron"), "Iron");
            put(of(LangCategory.TRIM_MATERIAL, "gold"), "Gold");
            put(of(LangCategory.TRIM_MATERIAL, "netherite"), "Netherite");
            put(of(LangCategory.TRIM_MATERIAL, "jade"), "Jade");
            put(of(LangCategory.TRIM_MATERIAL, "tin"), "Tin");
            put(of(LangCategory.TRIM_MATERIAL, "lead"), "Lead");
            put(of(LangCategory.TRIM_MATERIAL, "silver"), "Silver");
            put(of(LangCategory.TRIM_MATERIAL, "bronze"), "Bronze");
            put(of(LangCategory.TRIM_MATERIAL, "steel"), "Steel");
            put(of(LangCategory.TRIM_MATERIAL, "crude"), "Crude");
            put(of(LangCategory.TRIM_MATERIAL, "burzum_steel"), "Búrzum-Steel");
            put(of(LangCategory.TRIM_MATERIAL, "edhel_steel"), "Edhel-Steel");
            put(of(LangCategory.TRIM_MATERIAL, "khazad_steel"), "Khazâd-Steel");
            put(of(LangCategory.TRIM_MATERIAL, "morgul_steel"), "Morgul Steel");
            put(of(LangCategory.TRIM_MATERIAL, "mithril"), "Mithril");

            put(of(LangCategory.FACTION, "example.fallback"), "Example...");
            put(of(LangCategory.FACTION, "misty_mountains_goblins.fallback"), "Misty Mts. Goblins");

            put(of(LangCategory.SPAWN, "coordinates_base.dynamic"), "[x,z] ");
            put(of(LangCategory.SPAWN, "coordinates_base_values.dynamic"), "%s, %s");
            put(of(LangCategory.SPAWN, "coordinates_base.custom"), "[x,y,z] ");
            put(of(LangCategory.SPAWN, "coordinates_base_values.custom"), "%s, %s, %s");

            put(of(LangCategory.SPAWN, "none"), "None");

            put(of(LangCategory.TOOLTIP, "race_stats.attribute_header"), "Attributes : ");
            put(of(LangCategory.TOOLTIP, "race_stats.no_attribute_change"), "Nothing changes!");

            put(of(LangCategory.TOOLTIP, "arkenstone_lore_0"), "The fairest of gems found beneath");
            put(of(LangCategory.TOOLTIP, "arkenstone_lore_1"), "the lonely mountain.");

            put(of(LangCategory.TOOLTIP, "dagamarth_lore_0"), "A storied blade forged by Narvi and Celebrimbor,");
            put(of(LangCategory.TOOLTIP, "dagamarth_lore_1"), "thought lost.");
            put(of(LangCategory.TOOLTIP, "herugrim_lore_0"), "A sword set with green jewels, passed down through");
            put(of(LangCategory.TOOLTIP, "herugrim_lore_1"), "the line of Eorl for generations.");
            put(of(LangCategory.TOOLTIP, "nazgul_sword_lore_0"), "Cold as death,");
            put(of(LangCategory.TOOLTIP, "nazgul_sword_lore_1"), "with a wicked point.");

            put(of(LangCategory.TOOLTIP, "mace_of_sauron_lore_0"), "Wielded by the Dark Lord,");
            put(of(LangCategory.TOOLTIP, "mace_of_sauron_lore_1"), "this weapon has claimed many lives.");
            put(of(LangCategory.TOOLTIP, "hammer_of_helm_hammerhand_lore_0"), "Once wielded by a King of Rohan,");
            put(of(LangCategory.TOOLTIP, "hammer_of_helm_hammerhand_lore_1"), "who fell in the Deep.");

            put(of(LangCategory.TOOLTIP, "anguirel_lore_0"), "A unique black blade forged in");
            put(of(LangCategory.TOOLTIP, "anguirel_lore_1"), "elder days from a meteorite.");
            put(of(LangCategory.TOOLTIP, "glamdring_lore_0"), "The Foe-Hammer, mate of Orcrist,");
            put(of(LangCategory.TOOLTIP, "glamdring_lore_1"), "once borne by the King of Gondolin.");
            put(of(LangCategory.TOOLTIP, "long_forgotten_longsword_lore_0"), "A mysterious melted and shattered");
            put(of(LangCategory.TOOLTIP, "long_forgotten_longsword_lore_1"), "longsword.");
            put(of(LangCategory.TOOLTIP, "longsword_of_elder_kings_lore_0"), "A well-crafted blade,");
            put(of(LangCategory.TOOLTIP, "longsword_of_elder_kings_lore_1"), "said to have belonged to the King of Nargothrond.");
            put(of(LangCategory.TOOLTIP, "narsil_lore_0"), "The Red and White Flame, borne by the King of the Dúnedain.");
            put(of(LangCategory.TOOLTIP, "narsil_lore_1"), "It was broken upon the slopes of Mount Doom.");
            put(of(LangCategory.TOOLTIP, "noldorin_longsword_lore_0"), "Such blades were once borne into battle by");
            put(of(LangCategory.TOOLTIP, "noldorin_longsword_lore_1"), "the Noldor in ages past.");
            put(of(LangCategory.TOOLTIP, "orcrist_lore_0"), "The Goblin-Cleaver, mate of Glamdring,");
            put(of(LangCategory.TOOLTIP, "orcrist_lore_1"), "once used by Thorin Oakenshield.");

            put(of(LangCategory.TOOLTIP, "barrow_blade_lore_0"), "Leaf-shaped and set with fiery jewels,");
            put(of(LangCategory.TOOLTIP, "barrow_blade_lore_1"), "these were made for the princes of Cardolan.");
            put(of(LangCategory.TOOLTIP, "morgul_knife_lore_0"), "A knife forged with wicked sorcery,");
            put(of(LangCategory.TOOLTIP, "morgul_knife_lore_1"), "often carried by the Ringwraiths.");
            put(of(LangCategory.TOOLTIP, "sting_lore_0"), "An elvish knife, made in Gondolin long ago,");
            put(of(LangCategory.TOOLTIP, "sting_lore_1"), "carried by Bilbo Baggins of the Shire.");

            put(of(LangCategory.TOOLTIP, "aeglos_lore_0"), "Called \"Snow-Point\" this legendary spear was carried into battle by");
            put(of(LangCategory.TOOLTIP, "aeglos_lore_1"), "King Gil-galad through many ages.");

            put(of(LangCategory.TOOLTIP, "cuthann_lore_0"), "The Shield of the Moon, mate to Anorthann,");
            put(of(LangCategory.TOOLTIP, "cuthann_lore_1"), "forged long ago in Nargothrond.");
            put(of(LangCategory.TOOLTIP, "anorthann_lore_0"), "The Shield of the Sun, mate to Cúthann,");
            put(of(LangCategory.TOOLTIP, "anorthann_lore_1"), "forged long ago in Nargothrond.");
            put(of(LangCategory.TOOLTIP, "shield_of_durins_guard_lore_0"), "A mithril-trimmed shield bearing the proud");
            put(of(LangCategory.TOOLTIP, "shield_of_durins_guard_lore_1"), "icon of Durin's line.");
            put(of(LangCategory.TOOLTIP, "shield_of_the_king_under_the_mountain_lore_0"), "An iconic shield emblazoned with a");
            put(of(LangCategory.TOOLTIP, "shield_of_the_king_under_the_mountain_lore_1"), "gilded raven.");

            put(of(LangCategory.TOOLTIP, "helmet_of_helm_hammerhand_lore_0"), "Helm of a mighty King of Rohan,");
            put(of(LangCategory.TOOLTIP, "helmet_of_helm_hammerhand_lore_1"), "sign of great power.");

            put(of(LangCategory.DESCRIPTION, "gondor.description_0"), "A last bastion for the Men of the West, the Kings of Gondor long stood watch over the neighbouring darkness. Though their line is broken, the stalwart Gondorians stand strong and fight to keep Mordor at bay.");
            put(of(LangCategory.DESCRIPTION, "mordor.description_0"), "In the land of Mordor, where the shadows lie. An arid wasteland of ash and dust lay around Mount Doom. The Black Legions await the orders of The Dark Lord in Barad-dûr with it's watchful Great Eye. The Nazgûls are seeking the One Ring and ready for the upcoming war");
            put(of(LangCategory.DESCRIPTION, "longbeards.erebor.description_0"), "Erebor, or the Lonely Mountain, is the Dwarven kingdom of Durin’s Folk. Reclaimed from Smaug by Thorin Oakenshield, it is now a prosperous center of wealth and craftsmanship, ruled by King Dáin Ironfoot. It forms a crucial alliance with the neighboring kingdom of Dale.");
            put(of(LangCategory.DESCRIPTION, "longbeards.description_0"), "The Longbeards, descended from Durin the Deathless, are the most noble of the Dwarven clans. Renowned for their mastery of stone and metal, their realms include Erebor and Khazad-dûm. They are most loyal to their kin and traditions.");
            put(of(LangCategory.DESCRIPTION, "dale.description_0"), "Dale is a flourishing kingdom of Men located near Erebor, rebuilt after its destruction by Smaug. Known for skilled archers and trade, it is ruled by Bard's descendants and maintains close ties with the Dwarves of Erebor.");
            put(of(LangCategory.DESCRIPTION, "lothlorien.description_0"), "Lothlórien, the Golden Wood, is an enchanted Elven realm ruled by Galadriel and Celeborn. Sheltered by powerful enchantments and nestled within the golden mallorn trees, it is one of the last strongholds of the Elves in Middle-earth, renowned for its beauty and serenity.");
            put(of(LangCategory.DESCRIPTION, "rohan.description_0"), "Rohan, also called the Riddermark, is a kingdom of Men renowned for its skilled horse-lords and cavalry. Located north of Gondor, it is ruled by the line of Eorl. Though once strong allies with Gondor, Rohan now faces growing internal and external threats.");
            put(of(LangCategory.DESCRIPTION, "isengard.description_0"), "Once a fortress of Númenor, the keys of the black tower of Orthanc passed to the White Wizard Saruman in the Third Age. Tempted by dark power, he weaves deceit and plots war from his seat in Isengard upon the neighbouring free peoples.");
            put(of(LangCategory.DESCRIPTION, "hobgoblin_tribes.gundabad.description_0"), "The Goblins of the Misty Mountains are a warlike race of Orcs inhabiting the caves and tunnels beneath the mountains. Once scattered, they now rebuild their strength, preying on travelers and allying with Sauron’s forces.");
            put(of(LangCategory.DESCRIPTION, "goblin_town.description_0"), "Goblin-Town was a dwelling of the Northern Orcs in the Misty Mountains.  A network of branching caves and tunnels carved by the goblins with many entrances like the Front Porch. They often left their homes to raid the Anduin and plunder homes.");
            put(of(LangCategory.DESCRIPTION, "moria.description_0"), "Khazad-Dûm, mansion of the Longbeards, spanning from the east range to the west. However, the dwarves dug too deep. Now in ruin, the ancient halls are home to Goblins, Trolls, and nameless things. With them the city earned a new name, Moria.");
            put(of(LangCategory.DESCRIPTION, "shire.description_0"), "The Shire is a peaceful land inhabited by Hobbits, located in the northwest of Middle-earth. Known for its pastoral beauty and the simple, unadventurous lives of its people. It remains largely untouched by the turmoil of the outside world.");
            put(of(LangCategory.DESCRIPTION, "woodland_realm.description_0"), "Eryn Galen, or Greenwood was once a vibrant forest that housed many creatures, the Sylvan elves of the woodland realm reigning as the greatest among them. Now, through foul sorcery the land has been twisted into Mirkwood, where spiders and orcs lurk beneath its canopy");

            put(of(LangCategory.COMMAND, "fail"), "Command couldn't be sent properly");

            put(of(LangCategory.COMMAND, "faction.banner.success"), "Successfully fetched the <%s> banner");
            put(of(LangCategory.COMMAND, "faction.banner.fail_error"), "Couldn't find the faction <%s>");
            put(of(LangCategory.COMMAND, "faction.banner.fail_id"), "An error occured while fetching the banner for <%s>");

            put(of(LangCategory.COMMAND, "join.faction.join.success"), "%s successfully joined the %s Faction");

            put(of(LangCategory.COMMAND, "clear.faction.success"), "Successfully cleared your faction data");
            put(of(LangCategory.COMMAND, "clear.player.faction.success"), "Successfully cleared %s faction data");

            put(of(LangCategory.COMMAND, "open_target.onboarding.success"), "Successfully opened the onboarding screen for %s.");
            put(of(LangCategory.COMMAND, "open_target.onboarding.error"), "Impossible to open the onboarding screen for %s, their faction is already chosen.");
            put(of(LangCategory.COMMAND, "open.onboarding.error"), "Impossible to open the onboarding screen, you already have your chosen faction.");

            put(of(LangCategory.COMMAND, "get.faction.success"), "Your initial faction is %s");
            put(of(LangCategory.COMMAND, "get.faction.no_faction"), "You have no initial faction");
            put(of(LangCategory.COMMAND, "get.player.faction.success"), "%s initial faction is %s");
            put(of(LangCategory.COMMAND, "get.player.faction.no_faction"), "%s have no initial faction");

            put(of(LangCategory.COMMAND, "get.spawn.overworld.success"), "Your overworld spawn is [%s, %s, %s](xyz)");
            put(of(LangCategory.COMMAND, "get.spawn.overworld.no_spawn"), "You have no overworld spawn assigned, default is at [%s, %s, %s](xyz)");
            put(of(LangCategory.COMMAND, "get.player.spawn.overworld.success"), "%s overworld spawn is [%s, %s, %s](xyz)");
            put(of(LangCategory.COMMAND, "get.player.spawn.overworld.no_spawn"), "%s have no overworld spawn assigned, default is at [%s, %s, %s](xyz)");

            put(of(LangCategory.COMMAND, "get.spawn.middle_earth.success"), "Your Middle-earth spawn is <%s> at [%s, %s, %s](xyz)");
            put(of(LangCategory.COMMAND, "get.spawn.middle_earth.no_spawn"), "No Middle-earth spawn assigned");
            put(of(LangCategory.COMMAND, "get.player.spawn.middle_earth.success"), "%s Middle-earth spawn is <%s> at [%s, %s, %s](xyz)");
            put(of(LangCategory.COMMAND, "get.player.spawn.middle_earth.no_spawn"), "%s have no Middle-earth spawn assigned");

            put(of(LangCategory.COMMAND, "set.spawn.overworld.success"), "Your new Overworld return spawn is at [%s, %s, %s](xyz)");
            put(of(LangCategory.COMMAND, "set.player.spawn.overworld.success"), "%s new Overworld return spawn is at [%s, %s, %s](xyz)");

            put(of(LangCategory.COMMAND, "set.spawn.middle_earth.success"), "Your Middle-earth spawn have been set to <%s> at [%s, %s, %s](xyz)");
            put(of(LangCategory.COMMAND, "set.spawn.middle_earth.no_faction"), "You cannot assign yourself a Middle-earth spawn without having joined an initial faction");
            put(of(LangCategory.COMMAND, "set.spawn.middle_earth.no_spawn_found"), "Couldn't find the spawn <%s>");
            put(of(LangCategory.COMMAND, "set.player.spawn.middle_earth.success"), "%s Middle-earth spawn have been set to <%s> at [%s, %s, %s](xyz)");
            put(of(LangCategory.COMMAND, "set.player.spawn.middle_earth.no_faction"), "You cannot assign a Middle-earth spawn to %s when they have not joined an initial faction");

            put(of(LangCategory.COMMAND, "reset.spawn.overworld.success"), "Your Overworld return spawn is reset to [%s, %s, %s](xyz)");
            put(of(LangCategory.COMMAND, "reset.player.spawn.overworld.success"), "%s Overworld return spawn is reset to [%s, %s, %s](xyz)");

            put(of(LangCategory.COMMAND, "reset.spawn.middle_earth.success"), "Your Middle-earth spawn have been reset to <%s> at [%s, %s, %s](xyz)");
            put(of(LangCategory.COMMAND, "reset.spawn.middle_earth.no_faction"), "You cannot reset your Middle-earth spawn when you have not joined an initial faction");
            put(of(LangCategory.COMMAND, "reset.player.spawn.middle_earth.success"), "%s Middle-earth spawn have been reset to <%s> at [%s, %s, %s](xyz)");
            put(of(LangCategory.COMMAND, "reset.player.spawn.middle_earth.no_faction"), "You cannot reset the Middle-earth spawn of %s when they have not joined an initial faction");

            put(of(LangCategory.COMMAND, "teleport.spawn.middle_earth.success"), "You got teleported to your Middle-earth spawn <%s>");
            put(of(LangCategory.COMMAND, "teleport.spawn.middle_earth.no_spawn"), "You have no Middle-earth spawn assigned");
            put(of(LangCategory.COMMAND, "teleport.player.spawn.middle_earth.success"), "%s got teleported to their Middle-earth spawn <%s> at [%s, %s, %s](xyz)");
            put(of(LangCategory.COMMAND, "teleport.player.spawn.middle_earth.no_spawn"), "%s have no Middle-earth spawn assigned");

            put(of(LangCategory.COMMAND, "teleport.spawn.overworld.success"), "You got teleported to your Overworld return spawn");
            put(of(LangCategory.COMMAND, "teleport.spawn.overworld.error"), "There was an error while teleporting you to the Overworld return spawn");
            put(of(LangCategory.COMMAND, "teleport.player.spawn.overworld.success"), "%s got teleported to their Overworld return spawn at [%s, %s, %s](xyz)");
            put(of(LangCategory.COMMAND, "teleport.player.spawn.overworld.no_spawn"), "There was an error while teleporting %s to their Overworld return spawn");

            put(of(LangCategory.COMMAND, "teleport.to.spawn.middle_earth.success"), "You got teleported to <%s> at [%s, %s, %s](xyz)");
            put(of(LangCategory.COMMAND, "teleport.to.spawn.middle_earth.error"), "You couldn't be teleported to <%s>");
            put(of(LangCategory.COMMAND, "teleport.player.to.spawn.middle_earth.success"), "%s got teleported to <%s> at [%s, %s, %s](xyz)");
            put(of(LangCategory.COMMAND, "teleport.player.to.spawn.middle_earth.error"), "%s couldn't be teleported to <%s>");

            put(of(LangCategory.COMMAND, "race.set.success"), "Your race is set to %s");
            put(of(LangCategory.COMMAND, "race.set.fail"), "Failed to set race to %s");
            put(of(LangCategory.COMMAND, "race.set.target.success"), "%s race is set to %s");
            put(of(LangCategory.COMMAND, "race.set.target.fail"), "Failed to set race to %s");

            put(of(LangCategory.COMMAND, "race.get.success"), "Your race is %s");
            put(of(LangCategory.COMMAND, "race.get.fail"), "You don't have a race");
            put(of(LangCategory.COMMAND, "race.get.target.success"), "%s race is %s");
            put(of(LangCategory.COMMAND, "race.get.target.fail"), "%s don't have a race");

            put(of(LangCategory.COMMAND, "race.reset.success"), "Your race is reset");
            put(of(LangCategory.COMMAND, "race.reset.fail"), "Failed to reset your race");
            put(of(LangCategory.COMMAND, "race.reset.target.success"), "%s race is reset");
            put(of(LangCategory.COMMAND, "race.reset.target.fail"), "Failed to reset race for %s");

            put(of(LangCategory.COMMAND, "back_attachment.success"), "Back Attachment successfully applied, ");
            put(of(LangCategory.COMMAND, "back_attachment.fail"), "Failed to apply Back Attachment.");

            put(of(LangCategory.COMMAND, "helmet_attachment.success"), "Helmet Attachment successfully applied, ");
            put(of(LangCategory.COMMAND, "helmet_attachment.fail"), "Failed to apply Helmet Attachment.");

            put(of(LangCategory.COMMAND, "back_attachment.hand_empty"), "Hand Empty. Take a Middle-earth Mod chestplate in your main hand to apply a Back Attachment.");
            put(of(LangCategory.COMMAND, "helmet_attachment.hand_empty"), "Hand Empty. Take a Middle-earth Mod helmet in your main hand to apply a Helmet Attachment.");

            put(of(LangCategory.COMMAND, "back_attachment.wrong_item"), "Wrong Item type. Take a Middle-earth Mod chestplate in your main hand to apply a Back Attachment.");
            put(of(LangCategory.COMMAND, "helmet_attachment.wrong_item"), "Wrong Item type. Take a Middle-earth Mod helmet in your main hand to apply a Helmet Attachment.");

            put(of(LangCategory.ALERT, "large_door.blocked"), "Something seems to prevent the door from moving.");

            put(of(LangCategory.ALERT, "hood_down"), "Hood down.");
            put(of(LangCategory.ALERT, "hood_up"), "Hood up.");

            put(of(LangCategory.ALERT, "seat.occupied"), "This seat is occupied.");
            put(of(LangCategory.ALERT, "seat.space_not_empty"), "Block above the seat is not empty.");

            put(of(LangCategory.EVENT, "join.faction.success"), "You have joined %s");
            put(of(LangCategory.EVENT, "leave.faction.success"), "You have left %s");

            put(of(LangCategory.EXCEPTION, "no_faction.target"), "%s have no faction");
            put(of(LangCategory.EXCEPTION, "no_faction.source"), "You have no faction");
            put(of(LangCategory.EXCEPTION, "faction_identifier"), "%s is not a valid faction identifier");
            put(of(LangCategory.EXCEPTION, "spawn_identifier"), "%s is not a valid spawn identifier");
            put(of(LangCategory.EXCEPTION, "identical_faction.target"), "%s already joined %s");
            put(of(LangCategory.EXCEPTION, "identical_faction.source"), "You already joined %s");

            put(of(LangCategory.UI, "onboarding_selection.title"), "Onboarding Screen");
            put(of(LangCategory.UI, "continue_character"), "Continue");
            put(of(LangCategory.UI, "reset_character"), "Reset Affiliation");

            put(of(LangCategory.UI, "return_confirmation.title"), "Onboarding Screen");
            put(of(LangCategory.UI, "return_confirmation.continue_character.title"), "Return Button");
            put(of(LangCategory.UI, "return_confirmation.continue_character.content"), "Go to Overworld");

            put(of(LangCategory.UI, "map_screen.button.fullscreen_toggle"), "Toggle Fullscreen Button");
            put(of(LangCategory.UI, "map_screen.button.map_overlay_toggle"), "Map Overlay Toggle Button");
            put(of(LangCategory.UI, "map_screen.button.recenter_on_player"), "Recenter on Player Button");
            put(of(LangCategory.UI, "map_screen.button.zoom_in"), "Zoom In Button");
            put(of(LangCategory.UI, "map_screen.button.zoom_out"), "Zoom Out Button");
            put(of(LangCategory.UI, "map_screen.tooltip.coordinates_title"), "Coordinates");
            put(of(LangCategory.UI, "map_screen.tooltip.coordinates_label"), "[x,z] ");
            put(of(LangCategory.UI, "map_screen.tooltip.coordinates_content"), "%s, %s");
            put(of(LangCategory.UI, "map_screen.tooltip.biome_label"), "[Biome] ");
            put(of(LangCategory.UI, "map_screen.tooltip.biome_content"), "%s");
            put(of(LangCategory.UI, "map_screen.tooltip.teleport_keybind"), "[%s] to teleport");
            put(of(LangCategory.UI, "map_screen.map_title_text"), "Middle-earth Map");

            put(of(LangCategory.UI, "structure_manager.label_selected_id"), "Selected : %s");
            put(of(LangCategory.UI, "structure_manager.label_runtime_id"), "Runtime : %s");
            put(of(LangCategory.UI, "structure_manager.label_enable_status"), "Enabled : %s");
            put(of(LangCategory.UI, "structure_manager.button_kill_all"), "Kill All");
            put(of(LangCategory.UI, "structure_manager.button_spawn_all"), "Spawn All");

            put(of(LangCategory.SCREEN, "faction_selection_screen"), "Faction Selection");
            put(of(LangCategory.SCREEN, "button.faction_randomizer"), "Randomize Faction");
            put(of(LangCategory.SCREEN, "button.full_randomizer"), "Fully Randomize");
            put(of(LangCategory.SCREEN, "button.focus_current"), "Focus Toggle");
            put(of(LangCategory.SCREEN, "button.zoom_out"), "Zoom In");
            put(of(LangCategory.SCREEN, "button.zoom_in"), "Zoom Out");
            put(of(LangCategory.SCREEN, "button.confirm"), "Confirm");
            put(of(LangCategory.SCREEN, "information.subfaction"), "Subfaction , ");
            put(of(LangCategory.SCREEN, "information.races"), "Race");
            put(of(LangCategory.SCREEN, "information.races.many"), "Races");
            put(of(LangCategory.SCREEN, "information.description"), "Description");

            put(of(LangCategory.SCREEN, "playerbook.title"), "Middle-earth");
            put(of(LangCategory.SCREEN, "playerbook.description"), "This mod is about the famous universe of Middle-earth.\n" +
                    "You will find a brand new dimension with custom blocks, items, entities, factions, structures and more!");
            put(of(LangCategory.SCREEN, "playerbook.chapters"), "Chapters");
            put(of(LangCategory.SCREEN, "playerbook.navigate_to"), "Navigate to");
            put(of(LangCategory.SCREEN, "playerbook.chapter_getting_started"), "Getting Started");
            put(of(LangCategory.SCREEN, "playerbook.chapter_mining"), "Mining");
            put(of(LangCategory.SCREEN, "playerbook.chapter_smithing"), "Smithing");
            put(of(LangCategory.SCREEN, "playerbook.chapter_enchanting"), "Enchanting");
            put(of(LangCategory.SCREEN, "playerbook.chapter_mounts"), "Mounts");
            put(of(LangCategory.SCREEN, "playerbook.chapter_dungeons"), "Dungeons");

            put(of(LangCategory.SCREEN, ".playerbook.getting_started_desc"), "To enter Middle-earth and thus begin your adventure, you shall first craft a starlight phial.\n " +
                    "Once the phial is crafted, fill it with water and then combine with a glowing item such as glow berries, glowstone or glow ink to finally craft the starlight phial.");
            put(of(LangCategory.SCREEN, "playerbook.getting_started_desc_right"), "Use it and you'll be brought to the onboarding process to Middle-earth.");

            put(of(LangCategory.SCREEN, "playerbook.mining_desc"), "Basic resources like coal, tin and copper can be found almost anywhere near the surface, " +
                    "but valuable ores and gems are only found at deeper levels.\n\n From shallowest to deepest, " +
                    "the strata of the world consists of layers of Stone, Deepslate, Núrgon, and Medgon.");
            put(of(LangCategory.SCREEN, "playerbook.mining_desc_right"), "");
            put(of(LangCategory.SCREEN, "playerbook.tools"), "Tools");
            put(of(LangCategory.SCREEN, "playerbook.mining_tools_desc"), "You must Smith stronger tools to extract resources from the rock in deeper layers.\n\n " +
                    "Deepslate minerals can be obtained using Stone Tools or better, Núrgon ores require at least Bronze Tools, and Steel Tools are needed to mine Medgon. ");
            put(of(LangCategory.SCREEN, "playerbook.mining_tools_desc_right"), "Read the chapter about Smithing to learn how to get better tools.");
            put(of(LangCategory.SCREEN, "playerbook.cave_monsters"), "Cave Monsters");
            put(of(LangCategory.SCREEN, "playerbook.mining_cave_monster_desc"), "Delving deep is not without danger —  miners tell of roving bands of Wild Goblins patrolling the dark reaches of Middle-earth, " +
                    "preying on the unwary. Some tales even say fouler and darker things stalk the roots of the world, lying in wait… go not into the deep unprepared.");
            put(of(LangCategory.SCREEN, "playerbook.mining_cave_monster_desc_right"), "The dark depths hide many a glimmering prize, but caution is to be exercised!");

            put(of(LangCategory.SCREEN, "playerbook.smithing_desc"), "Better equipment will be essential for survival in the dangerous wilds of the world.\n\n " +
                    "Smithing allows you to create new deadly weapons, more complex armour, and efficient tools to delve more deeply.");
            put(of(LangCategory.SCREEN, "playerbook.smithing_desc_right"), "To work metals with more finesse than crafting, you’ll need a Forge.\n\n " +
                    "You can craft it with any cobbled stones, a furnace and bricks.\n To get clay for bricks, either find a lush cave or convert mud to clay");
            put(of(LangCategory.SCREEN, "playerbook.copper_alloys"), "Copper Alloys");
            put(of(LangCategory.SCREEN, "playerbook.smithing_copper_alloys_smithing_desc"), "Bronze and Crude are simple alloys, harder and more durable than stone and copper.\n\n " +
                    "Copper alloy tools will enable you to mine deeper than before, entering the Nurgon layer of the world and exploiting the riches found there.\n" +
                    "Make sure to toggle the forge to alloying mode.");
            put(of(LangCategory.SCREEN, "playerbook.smithing_copper_alloys_smithing_desc_right"), "");
            put(of(LangCategory.SCREEN, "playerbook.casting"), "Casting");
            put(of(LangCategory.SCREEN, "playerbook.smithing_casting_desc"), "All molten metals can be cast into Nuggets, Ingots, Rods, Plates, and Large Rods. " +
                    "Different casted parts will require different amounts of molten metal to create.\n\n " +
                    "A forge’s crucible can hold up to 16 ingots-worth of molten liquid metal");
            put(of(LangCategory.SCREEN, "playerbook.smithing_casting_desc_right"), "");
            put(of(LangCategory.SCREEN, "playerbook.shaping_anvil"), "Shaping Anvil");
            put(of(LangCategory.SCREEN, "playerbook.smithing_shaping_anvil_desc"), "Smithing Anvils are a handy solution to tackle the next step in smithing.\n " +
                    "The Stone Anvil is the most basic type of smithing anvil — create one by laying down a base of three logs of any type under six stones of any kind.");
            put(of(LangCategory.SCREEN, "playerbook.smithing_shaping_anvil_desc_right"), "You’ll need a Smithing Hammer to work your casted parts over the Smithing Anvil");
            put(of(LangCategory.SCREEN, "playerbook.smithing_shaping_anvil_1_desc"), "Now you have all the tools needed for an amateur smith. Put a Hot Large Metal Rod onto the stone anvil.\n\n " +
                    "Next, shape it into a Pickaxe Head. Using the Smithing Hammer, hit the large rod at a stable tempo.");
            put(of(LangCategory.SCREEN, "playerbook.smithing_shaping_anvil_1_desc_right"), "Casted parts can only be worked into new shapes on the smithing anvil while they are hot, " +
                    "and will also cool down over time while on the anvil.\n\n You can reheat cooled parts in a low-heat forge without resetting their progress.");
            put(of(LangCategory.SCREEN, "playerbook.quenching"), "Quenching");
            put(of(LangCategory.SCREEN, "playerbook.smithing_quenching_desc"), "Once the large rod is fully shaped into the pickaxe head, it must be cool to the touch before being worked further. " +
                    "You must quench it in a filled Cauldron; cooling it instantly.");
            put(of(LangCategory.SCREEN, "playerbook.smithing_quenching_desc_right"), "Both the cauldron and bucket can be crafted using Tin, which can be found in caves near the surface.");
            put(of(LangCategory.SCREEN, "playerbook.artisan_table"), "Artisan Table");
            put(of(LangCategory.SCREEN, "playerbook.smithing_artisan_table_desc"), "The Artisan Table is the workstation of choice for armourers, blacksmiths and craftspeople across many realms, " +
                    "and will be required to piece together smithing components into a final product.");
            put(of(LangCategory.SCREEN, "playerbook.smithing_artisan_table_desc_right"), "");
            put(of(LangCategory.SCREEN, "playerbook.smithing_artisan_table_1_desc"), "Place your bronze pickaxe head and two sticks in the Artisan Table, " +
                    "and you are now ready to explore even farther underground using your new Bronze Pickaxe.\n\n " +
                    "Although simple tools and weapons can also be crafted in a normal crafting grid.");
            put(of(LangCategory.SCREEN, "playerbook.smithing_artisan_table_1_desc_right"), "Crafting equipment in the Artisan Table bestows a noticeable bonus in durability.");

            put(of(LangCategory.SCREEN, "playerbook.enchanting_desc"), "The greatest artisans of past ages were known to have honed their craft so wonderfully, " +
                    "resulting in storied blades and armour with extraordinary properties.\n\n" +
                    "First, craft the inscription table as follows.");
            put(of(LangCategory.SCREEN, "playerbook.enchanting_desc_right"), "");
            put(of(LangCategory.SCREEN, "playerbook.inscription_table"), "Inscription Table");
            put(of(LangCategory.SCREEN, "playerbook.inscription_table_desc"), "To use the inscription table, you will need gems, a chisel and the item that you wish to enchant. " +
                    "Each gem has unique words associated with it, you can explore each combination of words until you find a valid enchant.");
            put(of(LangCategory.SCREEN, "playerbook.inscription_table_desc_right"), "Here's an example of the Unbreaking enchantment inscription;");

            put(of(LangCategory.SCREEN, "playerbook.mounts_desc"), "The roads of middle earth are long and winding so consider a mount to assist with your travels.\n\n " +
                    "Such companions come in all shapes and sizes, each having their own unique advantages and disadvantages.");
            put(of(LangCategory.SCREEN, "playerbook.mounts_desc_right"), "Many of those beasts can only be tamed by certain races.\n\n " +
                    "In Middle-earth, you cannot craft golden food such as golden apples to breed horses. Instead you must craft a Sack of Horsefeed, from a hay bale, a lettuce and an apple.");
            put(of(LangCategory.SCREEN, "playerbook.broadhoof_goat"), "Broadhoof Goat");
            put(of(LangCategory.SCREEN, "playerbook.mount_broadhoof_goat_desc"), "The Broadhoof Goat is a great mount for Dwarves that can easily climb mountains.\n" +
                    "You can find them in mountains where Dwarves live, but beware of these fluffy climbers; they can be aggressive if provoked. " +
                    "They may charge with their horns against enemies and jump very high.");
            put(of(LangCategory.SCREEN, "playerbook.mount_broadhoof_goat_desc_right"), "To tame them, you must be a dwarf and offer them wheat.");
            put(of(LangCategory.SCREEN, "playerbook.great_horn"), "Great Horn");
            put(of(LangCategory.SCREEN, "playerbook.mount_great_horn_desc"), "The Great Horn lives in the forests of Anduin and northern Wastelands.\n" +
                    "It's a majestic creature that will flee everyone but elves. " +
                    "Indeed, as an elf, you may tame it with various items, such as clovers, sweet berries, mushrooms, and more.");
            put(of(LangCategory.SCREEN, "playerbook.mount_great_horn_desc_right"), "Great Horns can leap\n        and pierce.");
            put(of(LangCategory.SCREEN, "playerbook.warg"), "Warg");
            put(of(LangCategory.SCREEN, "playerbook.mount_warg_desc"), "The wicked wolves are bred for war and hunting. They attack all good-aligned entities and can be ridden by " +
                    "orcs, snagas, goblins, and uruks.\n To tame this wicked beast, simply give some raw meat.");
            put(of(LangCategory.SCREEN, "playerbook.mount_warg_desc_right"), "Wargs can leap and take down other riders from their mounts.");
            put(of(LangCategory.SCREEN, "playerbook.cave_troll"), "Cave Troll");
            put(of(LangCategory.SCREEN, "playerbook.mount_cave_troll_desc"), "The Cave Troll, a fearsome creature that dwells deep in caves. " +
                    "It attacks everyone when hungry, but if you're evil aligned, you may attempt to tame it with chains while it sleeps.\n " +
                    "The Cave Troll can bear up to three riders on his back and be commanded with a bone.");
            put(of(LangCategory.SCREEN, "playerbook.mount_cave_troll_desc_right"), "It can smash and charge.");

            put(of(LangCategory.SCREEN, "playerbook.dungeons_desc"), "Throughout your adventures in middle earth you may encounter a variety of dungeons with great riches to behold.\n " +
                    "Before entering, you should prepare yourself for whatever unfriendly creatures you may find inside, for such bounty is not left unguarded. ");
            put(of(LangCategory.SCREEN, "playerbook.dungeons_desc_right"), "In each dungeon, you shall find trial spawners, that give keys upon defeating the wave of enemies.\n " +
                    "The trial key is used for the treasure vaults that may be found deeper in the dungeon.");
            put(of(LangCategory.SCREEN, "playerbook.brigands"), "Brigands");
            put(of(LangCategory.SCREEN, "playerbook.dungeon_brigands_desc"), "Brigands' dungeons can be found all around Anduin’s Vale.\n " +
                    "They hold stolen riches from neighboring factions and hide below ruined towers.");
            put(of(LangCategory.SCREEN, "playerbook.dungeon_brigands_desc_right"), "");
            put(of(LangCategory.SCREEN, "playerbook.spider_burrows"), "Spider Burrows");
            put(of(LangCategory.SCREEN, "playerbook.dungeon_spider_burrows_desc"), "The spiders of Shelob have infested the Mirkwood forest.\n " +
                    "Traveling in that corrupted forest is very dangerous, however you may find forgotten traveler's riches, if you ever return alive.");
            put(of(LangCategory.SCREEN, "playerbook.dungeon_spider_burrows_desc_right"), "");

            put(of(LangCategory.WIDGET, "spawn_tooltip_title"), "Spawns");
            put(of(LangCategory.WIDGET, "marker.selected_title_container.before"), "[");
            put(of(LangCategory.WIDGET, "marker.selected_title_container.after"), "]");
            put(of(LangCategory.WIDGET, "marker.margin_front"), "   ");
            put(of(LangCategory.WIDGET, "marker.more"), "More ...");

            put(of(LangCategory.UI, "search.label"), "Search...");
            put(of(LangCategory.UI, "search.toggle_button"), "Search Bar Toggle");
            put(of(LangCategory.UI, "search.screen_click_button"), "Screen Click Button");


            put(of(LangCategory.KEY, "category", MiddleEarth.MOD_ID), "Middle-earth");
            put(of(LangCategory.KEY, "hood_state_toggle"), "Toggle Worn Hood state");
            put(of(LangCategory.KEY, "map_teleport"), "Map Teleport");
            put(of(LangCategory.KEY, "map_fullscreen_toggle"), "Toggle Map Fullscreen");

            put("modmenu.nameTranslation" + MiddleEarth.MOD_ID, "Middle-earth");
            put("modmenu.descriptionTranslation" + MiddleEarth.MOD_ID, "Join the legendary world of Tolkien's universe from The Lord of The Rings to the Hobbit and more!");
            put("modmenu.summaryTranslation" + MiddleEarth.MOD_ID, "Join the legendary world of Tolkien's universe from The Lord of The Rings to the Hobbit and more!");

            put(of(LangCategory.EMI, "forge"), "Forge");
            put(of(LangCategory.EMI, "anvil_shaping"), "Shaping Anvil");
            put(of(LangCategory.EMI, "artisan_table"), "Artisan Table");

            put(of(LangCategory.TAG, "item", "anvil_items"), "Anvil Items");
            put(of(LangCategory.TAG, "item", "bones"), "Bones");
            put(of(LangCategory.TAG, "item", "cloaks"), "Cloaks");
            put(of(LangCategory.TAG, "item", "cooked_poultry"), "Cooked Poultry");
            put(of(LangCategory.TAG, "item", "dyeable"), "Dyeable");
            put(of(LangCategory.TAG, "item", "feathers"), "Feathers");
            put(of(LangCategory.TAG, "item", "glowy"), "Glowing Items");
            put(of(LangCategory.TAG, "item", "ingot_shaping"), "Ingot Shaping");
            put(of(LangCategory.TAG, "item", "lead_ores"), "Lead Ores");
            put(of(LangCategory.TAG, "item", "mithril_ores"), "Mithril Ores");
            put(of(LangCategory.TAG, "item", "mod_planks"), "Middle-earth Planks");
            put(of(LangCategory.TAG, "item", "mod_stripped_logs"), "Middle-earth Stripped Logs");
            put(of(LangCategory.TAG, "item", "mushroom_stew_ingredient"), "Mushroom Stew Ingredient");
            put(of(LangCategory.TAG, "item", "nugget_shaping"), "Nugget Shaping");
            put(of(LangCategory.TAG, "item", "raw_poultry"), "Raw Poultry");
            put(of(LangCategory.TAG, "item", "shard"), "Shard");
            put(of(LangCategory.TAG, "item", "shingles"), "Shingles");
            put(of(LangCategory.TAG, "item", "silver_ores"), "Silver Ores");
            put(of(LangCategory.TAG, "item", "thatchy"), "Thatch");
            put(of(LangCategory.TAG, "item", "tin"), "Tin");
            put(of(LangCategory.TAG, "item", "tin_ores"), "Tin Ores");
            put(of(LangCategory.TAG, "item", "warg_food"), "Warg Food");
            put(of(LangCategory.TAG, "item", "worm"), "Worm");

            put(of(LangCategory.PAINTING, "author.boenndal"), "Boenndal");
            put(of(LangCategory.PAINTING, "author.scosher"), "Scosher");

            put(of(LangCategory.PAINTING, "dwarf_portrait.title"), "Dwarf Portrait");
            put(of(LangCategory.PAINTING, "elf_portrait.title"), "Elf Portrait");
            put(of(LangCategory.PAINTING, "hobbit_portrait.title"), "Hobbit Portrait");
            put(of(LangCategory.PAINTING, "human_portrait.title"), "Human Portrait");
            put(of(LangCategory.PAINTING, "orc_portrait.title"), "Orc Portrait");
            put(of(LangCategory.PAINTING, "note_board.title"), "Note Board");
            put(of(LangCategory.PAINTING, "pan_board.title"), "Pan Board");
            put(of(LangCategory.PAINTING, "rohirric_tapestry.title"), "Rohirric Tapestry");
            put(of(LangCategory.PAINTING, "small_mirror.title"), "Small Mirror");
            put(of(LangCategory.PAINTING, "tall_mirror.title"), "Tall Mirror");
            put(of(LangCategory.PAINTING, "prancing_pony.title"), "Prancing Pony");
            put(of(LangCategory.PAINTING, "tool_board.title"), "Tool Board");
            put(of(LangCategory.PAINTING, "gondorian_tapestry.title"), "Gondorian Tapestry");
            put(of(LangCategory.PAINTING, "dwarven_plaque.title"), "Dwarven Plaque");

            put(of(LangCategory.NPC_DATA, "npc"), "NPC");

            put(of(LangCategory.INSCRIPTION, "linking_dash"), "-");
            put(of(LangCategory.INSCRIPTION, "level"), "%d Level");
            put(of(LangCategory.INSCRIPTION, "levels"), "%d Levels");

            put("attribute.name.powdered_snow_immunity", "Powdered Snow Immunity");
            put("attribute.name.climbing_strength", "Climbing Strength");
            put("attribute.name.detection_range", "Detection Range");
            put("attribute.name.width_scale", "Width Scale");

            put("attribute.modifiers." + RacePools.RaceAttributes.TOTAL_DAMAGE_MODIFIER.getPath(), "Add Total Damage Multiplied");
            put("attribute.modifiers.creative_mode_block_range", "Creative Mode Block Range");
            put("attribute.modifiers.creative_mode_entity_range", "Creative Mode Entity Range");
        }
    };

    public static String of(LangCategory langCategory, String otherPrefix, String value) {
        return MiddleEarth.rawTranslationKey(langCategory.Prefix + "." + otherPrefix, value);
    }
    public static String of(LangCategory langCategory, String value) {
        return MiddleEarth.rawTranslationKey(langCategory, value);
    }
    public static String ofRaw(LangCategory langCategory, String value) {
        return MiddleEarth.rawTranslationKey(langCategory, value);
    }
}
