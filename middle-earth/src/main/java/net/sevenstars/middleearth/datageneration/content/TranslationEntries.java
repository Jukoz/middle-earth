package net.sevenstars.middleearth.datageneration.content;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
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
    public static List<String> npcDataEntries = new ArrayList<>() {
    };
    public static List<StructureManagerData> structureManagerEntries = new ArrayList<>() {
    };
    public static List<String> spawnEntries = new ArrayList<>() {
    };

    public static Map<String, String> manualEntries = new HashMap<>() {
        {
            put("itemGroup." + MiddleEarth.MOD_ID + ".stone_blocks", "Middle-earth Stone Blocks");
            put("itemGroup." + MiddleEarth.MOD_ID + ".wood_blocks", "Middle-earth Wood Blocks");
            put("itemGroup." + MiddleEarth.MOD_ID + ".misc_blocks", "Middle-earth Misc Blocks");
            put("itemGroup." + MiddleEarth.MOD_ID + ".nature_blocks", "Middle-earth Nature Blocks");
            put("itemGroup." + MiddleEarth.MOD_ID + ".decorative_blocks", "Middle-earth Decorative Blocks");
            put("itemGroup." + MiddleEarth.MOD_ID + ".food_items", "Middle-earth Food");
            put("itemGroup." + MiddleEarth.MOD_ID + ".weapon_items", "Middle-earth Weapons");
            put("itemGroup." + MiddleEarth.MOD_ID + ".equipment_items", "Middle-earth Equipment");
            put("itemGroup." + MiddleEarth.MOD_ID + ".tool_items", "Middle-earth Tools");
            put("itemGroup." + MiddleEarth.MOD_ID + ".resource_items", "Middle-earth Resources");
            put("itemGroup." + MiddleEarth.MOD_ID + ".spawn_egg_items", "Middle-earth Spawn Eggs");

            put("advancements.middle_earth.root.title", "Long expected journey");
            put("advancements.middle_earth.root.description", "Enter Middle-earth!");
            put("advancements.middle_earth.enter_dale.title", "The Kingdom of Dale");
            put("advancements.middle_earth.enter_dale.description", "Enter the realm of Dale");
            put("advancements.middle_earth.enter_gondor.title", "The South-kingdom");
            put("advancements.middle_earth.enter_gondor.description", "Enter the last Kingdom of Númenor's legacy");
            put("advancements.middle_earth.enter_gundabad.title", "The Three Peaks");
            put("advancements.middle_earth.enter_gundabad.description", "Gundabad, the awakening mountain of Durin the Deathless, now infested by orcs.");
            put("advancements.middle_earth.enter_lothlorien.title", "The Golden Trees");
            put("advancements.middle_earth.enter_lothlorien.description", "Enter Lothlórien");
            put("advancements.middle_earth.enter_lonely_mountain.title", "Erebor");
            put("advancements.middle_earth.enter_lonely_mountain.description", "The capital of the dwarves");
            put("advancements.middle_earth.enter_mirkwood.title", "Stay on the path!");
            put("advancements.middle_earth.enter_mirkwood.description", "Enter the Mirkwood forest");
            put("advancements.middle_earth.enter_misty_mountains.title", "Far over...");
            put("advancements.middle_earth.enter_misty_mountains.description", "Enter the Misty Mountains");
            put("advancements.middle_earth.enter_mordor.title", "One does not simply...");
            put("advancements.middle_earth.enter_mordor.description", "Walk into Mordor");
            put("advancements.middle_earth.enter_nan_curunir.title", "The valley of the Wizard");
            put("advancements.middle_earth.enter_nan_curunir.description", "Enter the valley of Nan Curunír");
            put("advancements.middle_earth.enter_rohan.title", "Riddermark");
            put("advancements.middle_earth.enter_rohan.description", "Enter the Kingdom of the Rohirrim");
            put("advancements.middle_earth.enter_shire.title", "The quiet countryside");
            put("advancements.middle_earth.enter_shire.description", "Enter the Shire");
            put("advancements.middle_earth.arkenstone.title", "Heart of the Mountain");
            put("advancements.middle_earth.arkenstone.description", "Find the Arkenstone, the jewel of the Lonely Mountain!");
            put("advancements.middle_earth.lembas.title", "Elvish Bread");
            put("advancements.middle_earth.lembas.description", "One bite is enough to fill the stomach of a grown man!");
            put("advancements.middle_earth.strawberry.title", "Do you remember?");
            put("advancements.middle_earth.strawberry.description", "The taste of strawberries");
            put("advancements.middle_earth.mithril.title", "As light as feathers");
            put("advancements.middle_earth.mithril.description", "Mine some Mithril!");
            put("advancements.middle_earth.tin.title", "Fool's Iron");
            put("advancements.middle_earth.tin.description", "Tin again! Maybe I can find iron deeper");

            put("advancements.smithing.root.title", "Smithing");
            put("advancements.smithing.root.description", "All begins with the Forge!");
            put("advancements.smithing.bellows.title", "Not Hot Enough?");
            put("advancements.smithing.bellows.description", "Craft bellows to heat up your forge");
            put("advancements.smithing.artisan_table.title", "Artisan Work");
            put("advancements.smithing.artisan_table.description", "Craft the artisan table");
            put("advancements.smithing.bronze_ingot.title", "Bronze Age");
            put("advancements.smithing.bronze_ingot.description", "A bit of tin and some copper");
            put("advancements.smithing.crude_ingot.title", "Orcish Handiwork");
            put("advancements.smithing.crude_ingot.description", "Forge the cruel and wicked crude metal");
            put("advancements.smithing.khazad_steel.title", "Dwarven Mastery");
            put("advancements.smithing.khazad_steel.description", "Forge the renowned Khazâd-Steel");
            put("advancements.smithing.dwarven_treated_anvil.title", "Song of the Mountain Halls");
            put("advancements.smithing.dwarven_treated_anvil.description", "Craft a Dwarven anvil to create Dwarven items");
            put("advancements.smithing.edhel_steel.title", "Firstborn Artistry");
            put("advancements.smithing.edhel_steel.description", "Forge the delightful Edhel-Steel");
            put("advancements.smithing.elven_treated_anvil.title", "Ballad of Fëanor");
            put("advancements.smithing.elven_treated_anvil.description", "Craft an Elven anvil to create Elven items");
            put("advancements.smithing.steel.title", "Mannish Smithing");
            put("advancements.smithing.steel.description", "Forge the popular steel of middle-men");
            put("advancements.smithing.sturdy_storage.title", "Sturdy Storage");
            put("advancements.smithing.sturdy_storage.description", "Craft the reinforced chest");
            put("advancements.smithing.tin_cauldron.title", "Quenching the fire");
            put("advancements.smithing.tin_cauldron.description", "Craft a tin cauldron to cool down your smithing work");
            put("advancements.smithing.treated_anvil.title", "Forged in Fire");
            put("advancements.smithing.treated_anvil.description", "Craft a basic anvil to being smithing");
            put("advancements.smithing.burzum_steel.title", "Wicked Metalwork");
            put("advancements.smithing.burzum_steel.description", "Forge the mighty Búrzum-Steel");
            put("advancements.smithing.orcish_treated_anvil.title", "Touched by Shadow");
            put("advancements.smithing.orcish_treated_anvil.description", "Craft an Orcish anvil to create Orcish items");
            put("advancements.smithing.orcish_sconce.title", "Cruel Fire");
            put("advancements.smithing.orcish_sconce.description", "Craft an orcish sconce");
            put("advancements.smithing.crystal_lamp.title", "Light of Khazad-dûm");
            put("advancements.smithing.crystal_lamp.description", "Craft a crystal lamp");
            put("advancements.smithing.dwarven_lantern.title", "A light in the dark");
            put("advancements.smithing.dwarven_lantern.description", "Craft a dwarven lantern");
            put("advancements.smithing.elven_lantern.title", "Light of the Elves");
            put("advancements.smithing.elven_lantern.description", "Light your way like the Quendi");

            put("effect."+ MiddleEarth.MOD_ID +".hallucination", "Hallucination");
            put("effect."+ MiddleEarth.MOD_ID +".enshrouded", "Enshrouded");
            put("effect."+ MiddleEarth.MOD_ID +".restrained", "Restrained");

            put("enchantment."+ MiddleEarth.MOD_ID +".ailment_protection", "Ailment Protection");
            put("enchantment."+ MiddleEarth.MOD_ID +".bane_of_giants", "Bane of Giants");
            put("enchantment."+ MiddleEarth.MOD_ID +".beheading", "Beheading");
            put("enchantment."+ MiddleEarth.MOD_ID +".celerity", "Celerity");
            put("enchantment."+ MiddleEarth.MOD_ID +".first_strike", "First Strike");
            put("enchantment."+ MiddleEarth.MOD_ID +".grounded", "Grounded");
            put("enchantment."+ MiddleEarth.MOD_ID +".hewing", "Hewing");
            put("enchantment."+ MiddleEarth.MOD_ID +".high_step", "High Step");
            put("enchantment."+ MiddleEarth.MOD_ID +".miner_reach", "Miner Reach");
            put("enchantment."+ MiddleEarth.MOD_ID +".stealthy_trail", "Stealthy Trail");
            put("enchantment."+ MiddleEarth.MOD_ID +".stride", "Stride");
            put("enchantment."+ MiddleEarth.MOD_ID +".stalwart", "Stalwart");
            put("enchantment."+ MiddleEarth.MOD_ID +".tree_feller", "Tree Feller");
            put("enchantment."+ MiddleEarth.MOD_ID +".vantage", "Vantage");

            put("block."+ MiddleEarth.MOD_ID +".potted_beech_sapling", "Potted Beech Sapling");
            put("block."+ MiddleEarth.MOD_ID +".potted_chestnut_sapling", "Potted Chestnut Sapling");
            put("block."+ MiddleEarth.MOD_ID +".potted_fir_sapling", "Potted Fir Sapling");
            put("block."+ MiddleEarth.MOD_ID +".potted_holly_sapling", "Potted Holly Sapling");
            put("block."+ MiddleEarth.MOD_ID +".potted_larch_sapling", "Potted Larch Sapling");
            put("block."+ MiddleEarth.MOD_ID +".potted_lebethron_sapling", "Potted Lebethron Sapling");
            put("block."+ MiddleEarth.MOD_ID +".potted_white_lebethron_sapling", "Potted White Lebethron Sapling");
            put("block."+ MiddleEarth.MOD_ID +".potted_mallorn_sapling", "Potted Mallorn Sapling");
            put("block."+ MiddleEarth.MOD_ID +".potted_maple_sapling", "Potted Maple Sapling");
            put("block."+ MiddleEarth.MOD_ID +".potted_silver_maple_sapling", "Potted Silver Maple Sapling");
            put("block."+ MiddleEarth.MOD_ID +".potted_mirkwood_sapling", "Potted Mirkwood Sapling");
            put("block."+ MiddleEarth.MOD_ID +".potted_palm_sapling", "Potted Palm Sapling");
            put("block."+ MiddleEarth.MOD_ID +".potted_white_palm_sapling", "Potted White Palm Sapling");
            put("block."+ MiddleEarth.MOD_ID +".potted_pine_sapling", "Potted Pine Sapling");
            put("block."+ MiddleEarth.MOD_ID +".potted_black_pine_sapling", "Potted Black Pine Sapling");
            put("block."+ MiddleEarth.MOD_ID +".potted_willow_sapling", "Potted Willow Sapling");

            put("block."+ MiddleEarth.MOD_ID +".potted_green_shrub", "Potted Green Shrub");
            put("block."+ MiddleEarth.MOD_ID +".potted_mallos", "Potted Mallos");
            put("block."+ MiddleEarth.MOD_ID +".potted_yellow_flower", "Potted Yellow Flower");
            put("block."+ MiddleEarth.MOD_ID +".potted_yellow_trollius", "Potted Yellow Trollius");
            put("block."+ MiddleEarth.MOD_ID +".potted_tan_shrub", "Potted Tan Shrub");
            put("block."+ MiddleEarth.MOD_ID +".potted_green_jewel_cornflower", "Potted Green Jewel Cornflower");
            put("block."+ MiddleEarth.MOD_ID +".potted_scorched_shrub", "Potted Scorched Shrub");
            put("block."+ MiddleEarth.MOD_ID +".potted_frozen_shrub", "Potted Frozen Shrub");

            put("block."+ MiddleEarth.MOD_ID +".potted_cave_amanita", "Potted Cave Amanita");
            put("block."+ MiddleEarth.MOD_ID +".potted_deep_firecap", "Potted Deep Firecap");
            put("block."+ MiddleEarth.MOD_ID +".potted_ghostshroom", "Potted Ghostshroom");
            put("block."+ MiddleEarth.MOD_ID +".potted_morsel", "Potted Morsel");
            put("block."+ MiddleEarth.MOD_ID +".potted_sky_firecap", "Potted Sky Firecap");
            put("block."+ MiddleEarth.MOD_ID +".potted_trumpet_shroom", "Potted Trumpet Shroom");
            put("block."+ MiddleEarth.MOD_ID +".potted_tubeshroom", "Potted Tubeshroom");
            put("block."+ MiddleEarth.MOD_ID +".potted_violet_caps", "Potted Violet Caps");
            put("block."+ MiddleEarth.MOD_ID +".potted_white_mushroom", "Potted White Mushroom");
            put("block."+ MiddleEarth.MOD_ID +".potted_yellow_amanita", "Potted Yellow Amanita");

            put("block."+ MiddleEarth.MOD_ID +".sconce", "Sconce");
            put("block."+ MiddleEarth.MOD_ID +".gilded_sconce", "Gilded Sconce");
            put("block."+ MiddleEarth.MOD_ID +".orcish_sconce", "Orcish Sconce");

            put("block."+ MiddleEarth.MOD_ID +".duckweed", "Duckweed");
            put("block."+ MiddleEarth.MOD_ID +".lily_pads", "Lily Pads");
            put("block."+ MiddleEarth.MOD_ID +".small_lily_pads", "Small Lily Pads");
            put("block."+ MiddleEarth.MOD_ID +".small_flowering_lily_pads", "Small Flowering Lily Pads");

            put("block."+ MiddleEarth.MOD_ID +".strawberry_bush", "Strawberry Bush");
            put("block."+ MiddleEarth.MOD_ID +".tough_berry_bush", "Tough Berry Bush");

            put("block."+ MiddleEarth.MOD_ID +".glowworm_main", "Glowworm Main");

            put("block."+ MiddleEarth.MOD_ID +".floating_ice", "Floating Ice");

            put("block."+ MiddleEarth.MOD_ID +".dwarven_lantern", "Dwarven Lantern");
            put("block."+ MiddleEarth.MOD_ID +".crystal_lamp", "Crystal Lamp");
            put("block."+ MiddleEarth.MOD_ID +".silver_lantern", "Silver Lantern");
            put("block."+ MiddleEarth.MOD_ID +".elven_lantern", "Elven Lantern");

            put("block."+ MiddleEarth.MOD_ID +".forge", "Forge");
            put("block."+ MiddleEarth.MOD_ID +".dwarven_forge", "Dwarven Forge");
            put("block."+ MiddleEarth.MOD_ID +".elven_forge", "Elven Forge");
            put("block."+ MiddleEarth.MOD_ID +".orcish_forge", "Orcish Forge");
            put("block."+ MiddleEarth.MOD_ID +".bellows", "Bellows");
            put("block."+ MiddleEarth.MOD_ID +".treated_anvil", "Treated Anvil");
            put("block."+ MiddleEarth.MOD_ID +".dwarven_treated_anvil", "Dwarven Treated Anvil");
            put("block."+ MiddleEarth.MOD_ID +".elven_treated_anvil", "Elven Treated Anvil");
            put("block."+ MiddleEarth.MOD_ID +".orcish_treated_anvil", "Orcish Treated Anvil");
            put("block."+ MiddleEarth.MOD_ID +".artisan_table", "Artisan Table");
            put("block."+ MiddleEarth.MOD_ID +".orcish_artisan_table", "Orcish Artisan Table");
            put("block."+ MiddleEarth.MOD_ID +".inscription_table", "Inscription Table");

            put("block."+ MiddleEarth.MOD_ID +".structure_manager", "Structure Manager");

            put("block."+ MiddleEarth.MOD_ID +".larch_coffer", "Larch Coffer");
            put("block."+ MiddleEarth.MOD_ID +".pine_coffer", "Pine Coffer");
            put("block."+ MiddleEarth.MOD_ID +".spruce_coffer", "Spruce Coffer");
            put("block."+ MiddleEarth.MOD_ID +".fir_coffer", "Fir Coffer");
            put("block."+ MiddleEarth.MOD_ID +".beech_coffer", "Beech Coffer");
            put("block."+ MiddleEarth.MOD_ID +".chestnut_coffer", "Chestnut Coffer");
            put("block."+ MiddleEarth.MOD_ID +".oak_coffer", "Oak Coffer");
            put("block."+ MiddleEarth.MOD_ID +".willow_coffer", "Willow Coffer");
            put("block."+ MiddleEarth.MOD_ID +".reinforced_chest", "Reinforced Chest");
            put("block."+ MiddleEarth.MOD_ID +".small_crate", "Small Crate");
            put("block."+ MiddleEarth.MOD_ID +".thin_barrel", "Thin Barrel");

            put("block."+ MiddleEarth.MOD_ID +".fire_of_orthanc", "Fire of Orthanc");
            put("block."+ MiddleEarth.MOD_ID +".torch_of_orthanc", "Torch of Orthanc");

            put("block."+ MiddleEarth.MOD_ID +".wood_pile", "Wood Pile");

            put("block."+ MiddleEarth.MOD_ID +".tall_black_pine_door", "Tall Black Pine Door");

            put("block."+ MiddleEarth.MOD_ID +".oak_stable_door", "Oak Stable Door");
            put("block."+ MiddleEarth.MOD_ID +".reinforced_spruce_door", "Reinforced Spruce Door");
            put("block."+ MiddleEarth.MOD_ID +".reinforced_black_pine_door", "Reinforced Black Pine Door");
            put("block."+ MiddleEarth.MOD_ID +".simple_larch_gate", "Simple Larch Gate");
            put("block."+ MiddleEarth.MOD_ID +".rickety_simple_larch_door", "Rickety Simple Larch Door");
            put("block."+ MiddleEarth.MOD_ID +".spruce_stable_door", "Spruce Stable Door");

            put("block."+ MiddleEarth.MOD_ID +".large_sturdy_door", "Large Sturdy Door");

            put("block."+ MiddleEarth.MOD_ID +".large_beech_fence_gate", "Large Beech Fence Gate");

            put("block."+ MiddleEarth.MOD_ID +".larch_hobbit_door", "Larch Hobbit Door");
            put("block."+ MiddleEarth.MOD_ID +".spruce_hobbit_door", "Spruce Hobbit Door");

            put("block."+ MiddleEarth.MOD_ID +".blue_hobbit_door", "Blue Hobbit Door");
            put("block."+ MiddleEarth.MOD_ID +".green_hobbit_door", "Green Hobbit Door");
            put("block."+ MiddleEarth.MOD_ID +".light_blue_hobbit_door", "Light Blue Hobbit Door");
            put("block."+ MiddleEarth.MOD_ID +".red_hobbit_door", "Red Hobbit Door");
            put("block."+ MiddleEarth.MOD_ID +".yellow_hobbit_door", "Yellow Hobbit Door");

            put("block."+ MiddleEarth.MOD_ID +".great_gondorian_gate", "Great Gondorian Gate");

            put("block."+ MiddleEarth.MOD_ID +".great_dwarven_gate", "Great Dwarven Gate");
            put("block."+ MiddleEarth.MOD_ID +".varnished_dwarven_door", "Varnished Dwarven Door");
            put("block."+ MiddleEarth.MOD_ID +".ruined_dwarven_door", "Ruined Dwarven Door");
            put("block."+ MiddleEarth.MOD_ID +".hidden_dwarven_door", "Hidden Dwarven Door");

            put("block."+ MiddleEarth.MOD_ID +".great_elven_gate", "Great Elven Gate");

            put("block."+ MiddleEarth.MOD_ID +".great_orcish_gate", "Great Orcish Gate");

            put("item."+ MiddleEarth.MOD_ID +".gondor_banner_pattern.desc", "White Tree of Gondor");
            put("item."+ MiddleEarth.MOD_ID +".rohan_banner_pattern.desc", "Rohan Horse Head");
            put("item."+ MiddleEarth.MOD_ID +".lothlorien_banner_pattern.desc", "Tree of Lórien");
            put("item."+ MiddleEarth.MOD_ID +".mordor_banner_pattern.desc", "The Great Eye of Sauron");
            put("item."+ MiddleEarth.MOD_ID +".misty_mountains_orcs_banner_pattern.desc", "The symbols of the Orcs of the Misty Mountains");
            put("item."+ MiddleEarth.MOD_ID +".isengard_banner_pattern.desc", "The White Hand of Saruman");
            put("item."+ MiddleEarth.MOD_ID +".goblin_skull_banner_pattern.desc", "The Skull of a Goblin");
            put("item."+ MiddleEarth.MOD_ID +".anvil_banner_pattern.desc", "Anvil Icons");
            put("item."+ MiddleEarth.MOD_ID +".bell_banner_pattern.desc", "Bell Icons");
            put("item."+ MiddleEarth.MOD_ID +".bow_banner_pattern.desc", "Bow Icons");
            put("item."+ MiddleEarth.MOD_ID +".dwarf_crown_banner_pattern.desc", "Dwarf Crown Icons");
            put("item."+ MiddleEarth.MOD_ID +".great_horn_pattern.desc", "Elk and Stag");
            put("item."+ MiddleEarth.MOD_ID +".oak_leaf.desc", "Oak Leaf");
            put("item."+ MiddleEarth.MOD_ID +".antlers.desc", "Lost Antlers");
            put("item."+ MiddleEarth.MOD_ID +".dragon_banner_pattern.desc", "A great Dragon");
            put("item."+ MiddleEarth.MOD_ID +".pipeweed_banner_pattern.desc", "A delicacy from the shire");
            put("item."+ MiddleEarth.MOD_ID +".snail_banner_pattern.desc", "The Slimy Overlord");
            put("item."+ MiddleEarth.MOD_ID +".spider_banner_pattern.desc", "Spider of Mirkwood");

            put("block."+ MiddleEarth.MOD_ID +".faction_banner", "%s Banner");

            put("sounds."+ MiddleEarth.MOD_ID +".bellows_push", "Bellows blowing");
            put("sounds."+ MiddleEarth.MOD_ID +".chisel_hit", "Chisel hits");
            put("sounds."+ MiddleEarth.MOD_ID +".chisel_enchant", "Chisel enchants");
            put("sounds."+ MiddleEarth.MOD_ID +".nazgul_fade", "Nazgûl fading");
            put("sounds."+ MiddleEarth.MOD_ID +".nazgul_scream", "Nazgûl screaming");
            put("sounds."+ MiddleEarth.MOD_ID +".pipe_refill", "Pipe refills");
            put("sounds."+ MiddleEarth.MOD_ID +".pipe_exhale", "Pipe exhales");
            put("sounds."+ MiddleEarth.MOD_ID +".pipe_ignite", "Pipe ignites");
            put("sounds."+ MiddleEarth.MOD_ID +".orc_drum", "Orc drums");
            put("sounds."+ MiddleEarth.MOD_ID +".great_horn_idle", "Great Horn grunts");
            put("sounds."+ MiddleEarth.MOD_ID +".great_horn_hurt", "Great Horn hurts");
            put("sounds."+ MiddleEarth.MOD_ID +".great_horn_call", "Great Horn bugles");
            put("sounds."+ MiddleEarth.MOD_ID +".great_horn_death", "Great Horn dies");

            put("screen."+ MiddleEarth.MOD_ID +".forge", "Forge");
            put("screen."+ MiddleEarth.MOD_ID +".structure_manager", "Structure Manager");
            put("container."+ MiddleEarth.MOD_ID +".artisan_table", "Artisan Table");
            put("container."+ MiddleEarth.MOD_ID +".inscription_table", "Inscription Table");
            put("container."+ MiddleEarth.MOD_ID +".small_crate", "Small Crate");
            put("container."+ MiddleEarth.MOD_ID +".thin_barrel", "Thin Barrel");
            put("screen."+ MiddleEarth.MOD_ID +".larch_coffer", "Larch Coffer");
            put("screen."+ MiddleEarth.MOD_ID +".pine_coffer", "Pine Coffer");
            put("screen."+ MiddleEarth.MOD_ID +".spruce_coffer", "Spruce Coffer");
            put("screen."+ MiddleEarth.MOD_ID +".fir_coffer", "Fir Coffer");
            put("screen."+ MiddleEarth.MOD_ID +".beech_coffer", "Beech Coffer");
            put("screen."+ MiddleEarth.MOD_ID +".chestnut_coffer", "Chestnut Coffer");
            put("screen."+ MiddleEarth.MOD_ID +".oak_coffer", "Oak Coffer");
            put("screen."+ MiddleEarth.MOD_ID +".willow_coffer", "Willow Coffer");
            put("screen."+ MiddleEarth.MOD_ID +".reinforced_chest", "Reinforced Chest");
            put("screen."+ MiddleEarth.MOD_ID +".sack", "Sack");
            put("screen."+ MiddleEarth.MOD_ID +".wood_pile", "Wood Pile");
            put("screen."+ MiddleEarth.MOD_ID +".shaping_anvil", "Shaping Anvil");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.weapons", "Weapons");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.sword", "Sword");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.axe", "Axe");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.spear", "Spear");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.bow", "Bow");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.crossbow", "Crossbow");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.tools", "Tools");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.pickaxe", "Pickaxe");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.shovel", "Shovel");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.hoe", "Hoe");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.chisel", "Chisel");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.armors", "Armors");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.helmet", "Helmet");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.chestplate", "Chestplate");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.leggings", "Leggings");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.boots", "Boots");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.mount_armor", "Mount Armor");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.shields", "Shields");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.light_shield", "Light Shield");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.medium_shield", "Medium Shield");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.heavy_shield", "Heavy Shield");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.misc", "Misc");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.hat", "Hat");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.helmet_attachment", "Helmet Attachment");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.back_attachment", "Back Attachment");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.pipe", "Pipe");
            
            put("tooltip."+ MiddleEarth.MOD_ID +".type", "Type: ");
            put("tooltip."+ MiddleEarth.MOD_ID +".faction", "Faction: ");
            put("tooltip."+ MiddleEarth.MOD_ID +".sub_faction", "Sub Faction: ");
            put("tooltip."+ MiddleEarth.MOD_ID +".race", "Race: ");
            put("tooltip."+ MiddleEarth.MOD_ID +".customizations", "Custom Parts: ");
            put("tooltip."+ MiddleEarth.MOD_ID +".reach", "Reach: ");
            put("tooltip."+ MiddleEarth.MOD_ID +".dyed", "Dyed: ");
            put("tooltip."+ MiddleEarth.MOD_ID +".artisan", "Artisan: ");
            put("tooltip."+ MiddleEarth.MOD_ID +".author", "Author: ");
            put("tooltip."+ MiddleEarth.MOD_ID +".blocks_range", " Blocks");
            put("tooltip."+ MiddleEarth.MOD_ID +".backstab", "+50% damage when backstabbing");
            put("tooltip."+ MiddleEarth.MOD_ID +".door_size", "Door size: ");

            put("tooltip."+ MiddleEarth.MOD_ID +".tier_clothing", "Clothing");
            put("tooltip."+ MiddleEarth.MOD_ID +".tier_basic", "Basic Armor");
            put("tooltip."+ MiddleEarth.MOD_ID +".tier_light", "Light Armor");
            put("tooltip."+ MiddleEarth.MOD_ID +".tier_medium", "Medium Armor");
            put("tooltip."+ MiddleEarth.MOD_ID +".tier_sturdy", "Sturdy Armor");
            put("tooltip."+ MiddleEarth.MOD_ID +".tier_heavy", "Heavy Armor");
            put("tooltip."+ MiddleEarth.MOD_ID +".tier_mithril", "Mithril");

            put("tooltip."+ MiddleEarth.MOD_ID +".artefact", "Artefact");
            put("tooltip."+ MiddleEarth.MOD_ID +".broken", "Broken");

            put("tooltip."+ MiddleEarth.MOD_ID +".sword", "Sword");
            put("tooltip."+ MiddleEarth.MOD_ID +".axe", "Axe");
            put("tooltip."+ MiddleEarth.MOD_ID +".dagger", "Dagger");
            put("tooltip."+ MiddleEarth.MOD_ID +".spear", "Spear");
            put("tooltip."+ MiddleEarth.MOD_ID +".longsword", "Longsword");
            put("tooltip."+ MiddleEarth.MOD_ID +".troll_weapon", "Troll Weapon");

            put("tooltip."+ MiddleEarth.MOD_ID +".bow", "Bow");
            put("tooltip."+ MiddleEarth.MOD_ID +".longbow", "Longbow");
            put("tooltip."+ MiddleEarth.MOD_ID +".crossbow", "Crossbow");

            put("tooltip."+ MiddleEarth.MOD_ID +".light_shield", "Light Shield");
            put("tooltip."+ MiddleEarth.MOD_ID +".medium_shield", "Medium Shield");
            put("tooltip."+ MiddleEarth.MOD_ID +".heavy_shield", "Heavy Shield");

            put("tooltip."+ MiddleEarth.MOD_ID +".generic", "Generic");

            put("tooltip."+ MiddleEarth.MOD_ID +".dwarven", "Dwarven");
            put("tooltip."+ MiddleEarth.MOD_ID +".elven", "Elven");
            put("tooltip."+ MiddleEarth.MOD_ID +".orc", "Orc");

            put("tooltip."+ MiddleEarth.MOD_ID +".color", "Color");

            put("tooltip."+ MiddleEarth.MOD_ID +".mount_armor_addon_top", "Top Armor Addon, Enabled");
            put("tooltip."+ MiddleEarth.MOD_ID +".mount_armor_addon_side", "Side Armor Addon, Enabled");

            put("tooltip."+ MiddleEarth.MOD_ID +".forge_output_mode_await", "Select a cast");
            put("tooltip."+ MiddleEarth.MOD_ID +".forge_output_mode0", "Not enough Metal.");
            put("tooltip."+ MiddleEarth.MOD_ID +".forge_output_mode1", "1 Nugget");
            put("tooltip."+ MiddleEarth.MOD_ID +".forge_output_mode2", "1 Ingot");
            put("tooltip."+ MiddleEarth.MOD_ID +".forge_output_mode3", "2 Ingots");
            put("tooltip."+ MiddleEarth.MOD_ID +".forge_output_mode5", "3 Ingots");

            put("tooltip."+ MiddleEarth.MOD_ID +".anvil_hammer", "Left click the anvil with a");
            put("tooltip."+ MiddleEarth.MOD_ID +".anvil_hammer_2", "Smithing Hammer to shape the metal.");

            put("tooltip."+ MiddleEarth.MOD_ID +".forge_mode_switch_alloying", "Click to switch to Heating Mode");
            put("tooltip."+ MiddleEarth.MOD_ID +".forge_mode_switch_heating", "Click to switch to Alloying Mode");
            put("tooltip."+ MiddleEarth.MOD_ID +".ingots_number", "Ingots");
            put("tooltip."+ MiddleEarth.MOD_ID +".nuggets_number", "Nuggets");

            put("tooltip."+ MiddleEarth.MOD_ID +".temp_1", "Warm");
            put("tooltip."+ MiddleEarth.MOD_ID +".temp_2", "Hot");
            put("tooltip."+ MiddleEarth.MOD_ID +".temp_3", "Very Hot");
            put("tooltip."+ MiddleEarth.MOD_ID +".temp_4", "Searing Hot");
            put("tooltip."+ MiddleEarth.MOD_ID +".temp_5", "Blazing Hot");

            put("tooltip."+ MiddleEarth.MOD_ID +".biome", "Biome");

            //TODO try to automate
            put("trim_pattern."+ MiddleEarth.MOD_ID +".smithing_part", "Smithing Part");
            put("trim_material."+ MiddleEarth.MOD_ID +".copper", "Copper");
            put("trim_material."+ MiddleEarth.MOD_ID +".iron", "Iron");
            put("trim_material."+ MiddleEarth.MOD_ID +".gold", "Gold");
            put("trim_material."+ MiddleEarth.MOD_ID +".netherite", "Netherite");
            put("trim_material."+ MiddleEarth.MOD_ID +".jade", "Jade");
            put("trim_material."+ MiddleEarth.MOD_ID +".tin", "Tin");
            put("trim_material."+ MiddleEarth.MOD_ID +".lead", "Lead");
            put("trim_material."+ MiddleEarth.MOD_ID +".silver", "Silver");
            put("trim_material."+ MiddleEarth.MOD_ID +".bronze", "Bronze");
            put("trim_material."+ MiddleEarth.MOD_ID +".steel", "Steel");
            put("trim_material."+ MiddleEarth.MOD_ID +".crude", "Crude");
            put("trim_material."+ MiddleEarth.MOD_ID +".burzum_steel", "Búrzum-Steel");
            put("trim_material."+ MiddleEarth.MOD_ID +".edhel_steel", "Edhel-Steel");
            put("trim_material."+ MiddleEarth.MOD_ID +".khazad_steel", "Khazâd-Steel");
            put("trim_material."+ MiddleEarth.MOD_ID +".morgul_steel", "Morgul Steel");
            put("trim_material."+ MiddleEarth.MOD_ID +".mithril", "Mithril");

            put("faction."+ MiddleEarth.MOD_ID +".example.fallback", "Example...");
            put("faction."+ MiddleEarth.MOD_ID +".misty_mountains_goblins.fallback", "Misty Mts. Goblins");

            put("spawn."+ MiddleEarth.MOD_ID +".coordinates_base.dynamic", "[x,z] ");
            put("spawn."+ MiddleEarth.MOD_ID +".coordinates_base_values.dynamic", "%s, %s");
            put("spawn."+ MiddleEarth.MOD_ID +".coordinates_base.custom", "[x,y,z] ");
            put("spawn."+ MiddleEarth.MOD_ID +".coordinates_base_values.custom", "%s, %s, %s");

            put("spawn."+ MiddleEarth.MOD_ID +".none", "None");

            put("race_tooltip."+ MiddleEarth.MOD_ID +".attribute_header", "Attributes , ");

            put("tooltip."+ MiddleEarth.MOD_ID +".arkenstone_lore_0", "The fairest of gems found beneath");
            put("tooltip."+ MiddleEarth.MOD_ID +".arkenstone_lore_1", "the lonely mountain.");

            put("tooltip."+ MiddleEarth.MOD_ID +".dagamarth_lore_0", "A storied blade forged by Narvi and Celebrimbor,");
            put("tooltip."+ MiddleEarth.MOD_ID +".dagamarth_lore_1", "thought lost.");
            put("tooltip."+ MiddleEarth.MOD_ID +".herugrim_lore_0", "A sword set with green jewels, passed down through");
            put("tooltip."+ MiddleEarth.MOD_ID +".herugrim_lore_1", "the line of Eorl for generations.");
            put("tooltip."+ MiddleEarth.MOD_ID +".nazgul_sword_lore_0", "Cold as death,");
            put("tooltip."+ MiddleEarth.MOD_ID +".nazgul_sword_lore_1", "with a wicked point.");

            put("tooltip."+ MiddleEarth.MOD_ID +".mace_of_sauron_lore_0", "Wielded by the Dark Lord,");
            put("tooltip."+ MiddleEarth.MOD_ID +".mace_of_sauron_lore_1", "this weapon has claimed many lives.");
            put("tooltip."+ MiddleEarth.MOD_ID +".hammer_of_helm_hammerhand_lore_0", "Once wielded by a King of Rohan,");
            put("tooltip."+ MiddleEarth.MOD_ID +".hammer_of_helm_hammerhand_lore_1", "who fell in the Deep.");

            put("tooltip."+ MiddleEarth.MOD_ID +".anguirel_lore_0", "A unique black blade forged in");
            put("tooltip."+ MiddleEarth.MOD_ID +".anguirel_lore_1", "elder days from a meteorite.");
            put("tooltip."+ MiddleEarth.MOD_ID +".glamdring_lore_0", "The Foe-Hammer, mate of Orcrist,");
            put("tooltip."+ MiddleEarth.MOD_ID +".glamdring_lore_1", "once borne by the King of Gondolin.");
            put("tooltip."+ MiddleEarth.MOD_ID +".long_forgotten_longsword_lore_0", "A mysterious melted and shattered");
            put("tooltip."+ MiddleEarth.MOD_ID +".long_forgotten_longsword_lore_1", "longsword.");
            put("tooltip."+ MiddleEarth.MOD_ID +".longsword_of_elder_kings_lore_0", "A well-crafted blade,");
            put("tooltip."+ MiddleEarth.MOD_ID +".longsword_of_elder_kings_lore_1", "said to have belonged to the King of Nargothrond.");
            put("tooltip."+ MiddleEarth.MOD_ID +".narsil_lore_0", "The Red and White Flame, borne by the King of the Dúnedain.");
            put("tooltip."+ MiddleEarth.MOD_ID +".narsil_lore_1", "It was broken upon the slopes of Mount Doom.");
            put("tooltip."+ MiddleEarth.MOD_ID +".noldorin_longsword_lore_0", "Such blades were once borne into battle by");
            put("tooltip."+ MiddleEarth.MOD_ID +".noldorin_longsword_lore_1", "the Noldor in ages past.");
            put("tooltip."+ MiddleEarth.MOD_ID +".orcrist_lore_0", "The Goblin-Cleaver, mate of Glamdring,");
            put("tooltip."+ MiddleEarth.MOD_ID +".orcrist_lore_1", "once used by Thorin Oakenshield.");

            put("tooltip."+ MiddleEarth.MOD_ID +".barrow_blade_lore_0", "Leaf-shaped and set with fiery jewels,");
            put("tooltip."+ MiddleEarth.MOD_ID +".barrow_blade_lore_1", "these were made for the princes of Cardolan.");
            put("tooltip."+ MiddleEarth.MOD_ID +".morgul_knife_lore_0", "A knife forged with wicked sorcery,");
            put("tooltip."+ MiddleEarth.MOD_ID +".morgul_knife_lore_1", "often carried by the Ringwraiths.");
            put("tooltip."+ MiddleEarth.MOD_ID +".sting_lore_0", "An elvish knife, made in Gondolin long ago,");
            put("tooltip."+ MiddleEarth.MOD_ID +".sting_lore_1", "carried by Bilbo Baggins of the Shire.");

            put("tooltip."+ MiddleEarth.MOD_ID +".aeglos_lore_0", "Called \"Snow-Point\" this legendary spear was carried into battle by");
            put("tooltip."+ MiddleEarth.MOD_ID +".aeglos_lore_1", "King Gil-galad through many ages.");

            put("tooltip."+ MiddleEarth.MOD_ID +".cuthann_lore_0", "The Shield of the Moon, mate to Anorthann,");
            put("tooltip."+ MiddleEarth.MOD_ID +".cuthann_lore_1", "forged long ago in Nargothrond.");
            put("tooltip."+ MiddleEarth.MOD_ID +".anorthann_lore_0", "The Shield of the Sun, mate to Cúthann,");
            put("tooltip."+ MiddleEarth.MOD_ID +".anorthann_lore_1", "forged long ago in Nargothrond.");
            put("tooltip."+ MiddleEarth.MOD_ID +".shield_of_durins_guard_lore_0", "A mithril-trimmed shield bearing the proud");
            put("tooltip."+ MiddleEarth.MOD_ID +".shield_of_durins_guard_lore_1", "icon of Durin's line.");
            put("tooltip."+ MiddleEarth.MOD_ID +".shield_of_the_king_under_the_mountain_lore_0", "An iconic shield emblazoned with a");
            put("tooltip."+ MiddleEarth.MOD_ID +".shield_of_the_king_under_the_mountain_lore_1", "gilded raven.");

            put("tooltip."+ MiddleEarth.MOD_ID +".helmet_of_helm_hammerhand_lore_0", "Helm of a mighty King of Rohan,");
            put("tooltip."+ MiddleEarth.MOD_ID +".helmet_of_helm_hammerhand_lore_1", "sign of great power.");

            put("description."+ MiddleEarth.MOD_ID +".gondor.description_0", "A last bastion for the Men of the West, the Kings of Gondor long stood watch over the neighbouring darkness. Though their line is broken, the stalwart Gondorians stand strong and fight to keep Mordor at bay.");
            put("description."+ MiddleEarth.MOD_ID +".mordor.description_0", "In the land of Mordor, where the shadows lie. An arid wasteland of ash and dust lay around Mount Doom. The Black Legions await the orders of The Dark Lord in Barad-dûr with it's watchful Great Eye. The Nazgûls are seeking the One Ring and ready for the upcoming war");
            put("description."+ MiddleEarth.MOD_ID +".longbeards.erebor.description_0", "Erebor, or the Lonely Mountain, is the Dwarven kingdom of Durin’s Folk. Reclaimed from Smaug by Thorin Oakenshield, it is now a prosperous center of wealth and craftsmanship, ruled by King Dáin Ironfoot. It forms a crucial alliance with the neighboring kingdom of Dale.");
            put("description."+ MiddleEarth.MOD_ID +".longbeards.description_0", "The Longbeards, descended from Durin the Deathless, are the most noble of the Dwarven clans. Renowned for their mastery of stone and metal, their realms include Erebor and Khazad-dûm. They are most loyal to their kin and traditions.");
            put("description."+ MiddleEarth.MOD_ID +".dale.description_0", "Dale is a flourishing kingdom of Men located near Erebor, rebuilt after its destruction by Smaug. Known for skilled archers and trade, it is ruled by Bard's descendants and maintains close ties with the Dwarves of Erebor.");
            put("description."+ MiddleEarth.MOD_ID +".lothlorien.description_0", "Lothlórien, the Golden Wood, is an enchanted Elven realm ruled by Galadriel and Celeborn. Sheltered by powerful enchantments and nestled within the golden mallorn trees, it is one of the last strongholds of the Elves in Middle-earth, renowned for its beauty and serenity.");
            put("description."+ MiddleEarth.MOD_ID +".rohan.description_0", "Rohan, also called the Riddermark, is a kingdom of Men renowned for its skilled horse-lords and cavalry. Located north of Gondor, it is ruled by the line of Eorl. Though once strong allies with Gondor, Rohan now faces growing internal and external threats.");
            put("description."+ MiddleEarth.MOD_ID +".isengard.description_0", "Once a fortress of Númenor, the keys of the black tower of Orthanc passed to the White Wizard Saruman in the Third Age. Tempted by dark power, he weaves deceit and plots war from his seat in Isengard upon the neighbouring free peoples.");
            put("description."+ MiddleEarth.MOD_ID +".hobgoblin_tribes.gundabad.description_0", "The Goblins of the Misty Mountains are a warlike race of Orcs inhabiting the caves and tunnels beneath the mountains. Once scattered, they now rebuild their strength, preying on travelers and allying with Sauron’s forces.");
            put("description."+ MiddleEarth.MOD_ID +".moria.description_0", "Khazad-Dûm, mansion of the Longbeards, spanning from the east range to the west. However, the dwarves dug too deep. Now in ruin, the ancient halls are home to Goblins, Trolls, and nameless things. With them the city earned a new name, Moria.");
            put("description."+ MiddleEarth.MOD_ID +".shire.description_0", "The Shire is a peaceful land inhabited by Hobbits, located in the northwest of Middle-earth. Known for its pastoral beauty and the simple, unadventurous lives of its people. It remains largely untouched by the turmoil of the outside world.");
            put("description."+ MiddleEarth.MOD_ID +".woodland_realm.description_0", "Eryn Galen, or Greenwood was once a vibrant forest that housed many creatures, the Sylvan elves of the woodland realm reigning as the greatest among them. Now, through foul sorcery the land has been twisted into Mirkwood, where spiders and orcs lurk beneath its canopy");

            put("command."+ MiddleEarth.MOD_ID +".fail", "Command couldn't be sent properly");

            put("command."+ MiddleEarth.MOD_ID +".faction.banner.success", "Successfully fetched the <%s> banner");
            put("command."+ MiddleEarth.MOD_ID +".faction.banner.fail_error", "Couldn't find the faction <%s>");
            put("command."+ MiddleEarth.MOD_ID +".faction.banner.fail_id", "An error occured while fetching the banner for <%s>");

            put("command."+ MiddleEarth.MOD_ID +".join.faction.join.success", "%s successfully joined the %s Faction");

            put("command."+ MiddleEarth.MOD_ID +".clear.faction.success", "Successfully cleared your faction data");
            put("command."+ MiddleEarth.MOD_ID +".clear.player.faction.success", "Successfully cleared %s faction data");

            put("command."+ MiddleEarth.MOD_ID +".open_target.onboarding.success", "Successfully opened the onboarding screen for %s.");
            put("command."+ MiddleEarth.MOD_ID +".open_target.onboarding.error", "Impossible to open the onboarding screen for %s, their faction is already chosen.");
            put("command."+ MiddleEarth.MOD_ID +".open.onboarding.error", "Impossible to open the onboarding screen, you already have your chosen faction.");


            put("command."+ MiddleEarth.MOD_ID +".get.faction.success", "Your initial faction is %s");
            put("command."+ MiddleEarth.MOD_ID +".get.faction.no_faction", "You have no initial faction");
            put("command."+ MiddleEarth.MOD_ID +".get.player.faction.success", "%s initial faction is %s");
            put("command."+ MiddleEarth.MOD_ID +".get.player.faction.no_faction", "%s have no initial faction");

            put("command."+ MiddleEarth.MOD_ID +".get.spawn.overworld.success", "Your overworld spawn is [%s, %s, %s](xyz)");
            put("command."+ MiddleEarth.MOD_ID +".get.spawn.overworld.no_spawn", "You have no overworld spawn assigned, default is at [%s, %s, %s](xyz)");
            put("command."+ MiddleEarth.MOD_ID +".get.player.spawn.overworld.success", "%s overworld spawn is [%s, %s, %s](xyz)");
            put("command."+ MiddleEarth.MOD_ID +".get.player.spawn.overworld.no_spawn", "%s have no overworld spawn assigned, default is at [%s, %s, %s](xyz)");

            put("command."+ MiddleEarth.MOD_ID +".get.spawn.middle_earth.success", "Your Middle-earth spawn is <%s> at [%s, %s, %s](xyz)");
            put("command."+ MiddleEarth.MOD_ID +".get.spawn.middle_earth.no_spawn", "No Middle-earth spawn assigned");
            put("command."+ MiddleEarth.MOD_ID +".get.player.spawn.middle_earth.success", "%s Middle-earth spawn is <%s> at [%s, %s, %s](xyz)");
            put("command."+ MiddleEarth.MOD_ID +".get.player.spawn.middle_earth.no_spawn", "%s have no Middle-earth spawn assigned");

            put("command."+ MiddleEarth.MOD_ID +".set.spawn.overworld.success", "Your new Overworld return spawn is at [%s, %s, %s](xyz)");
            put("command."+ MiddleEarth.MOD_ID +".set.player.spawn.overworld.success", "%s new Overworld return spawn is at [%s, %s, %s](xyz)");

            put("command."+ MiddleEarth.MOD_ID +".set.spawn.middle_earth.success", "Your Middle-earth spawn have been set to <%s> at [%s, %s, %s](xyz)");
            put("command."+ MiddleEarth.MOD_ID +".set.spawn.middle_earth.no_faction", "You cannot assign yourself a Middle-earth spawn without having joined an initial faction");
            put("command."+ MiddleEarth.MOD_ID +".set.spawn.middle_earth.no_spawn_found", "Couldn't find the spawn <%s>");
            put("command."+ MiddleEarth.MOD_ID +".set.player.spawn.middle_earth.success", "%s Middle-earth spawn have been set to <%s> at [%s, %s, %s](xyz)");
            put("command."+ MiddleEarth.MOD_ID +".set.player.spawn.middle_earth.no_faction", "You cannot assign a Middle-earth spawn to %s when they have not joined an initial faction");

            put("command."+ MiddleEarth.MOD_ID +".reset.spawn.overworld.success", "Your Overworld return spawn is reset to [%s, %s, %s](xyz)");
            put("command."+ MiddleEarth.MOD_ID +".reset.player.spawn.overworld.success", "%s Overworld return spawn is reset to [%s, %s, %s](xyz)");

            put("command."+ MiddleEarth.MOD_ID +".reset.spawn.middle_earth.success", "Your Middle-earth spawn have been reset to <%s> at [%s, %s, %s](xyz)");
            put("command."+ MiddleEarth.MOD_ID +".reset.spawn.middle_earth.no_faction", "You cannot reset your Middle-earth spawn when you have not joined an initial faction");
            put("command."+ MiddleEarth.MOD_ID +".reset.player.spawn.middle_earth.success", "%s Middle-earth spawn have been reset to <%s> at [%s, %s, %s](xyz)");
            put("command."+ MiddleEarth.MOD_ID +".reset.player.spawn.middle_earth.no_faction", "You cannot reset the Middle-earth spawn of %s when they have not joined an initial faction");

            put("command."+ MiddleEarth.MOD_ID +".teleport.spawn.middle_earth.success", "You got teleported to your Middle-earth spawn <%s>");
            put("command."+ MiddleEarth.MOD_ID +".teleport.spawn.middle_earth.no_spawn", "You have no Middle-earth spawn assigned");
            put("command."+ MiddleEarth.MOD_ID +".teleport.player.spawn.middle_earth.success", "%s got teleported to their Middle-earth spawn <%s> at [%s, %s, %s](xyz)");
            put("command."+ MiddleEarth.MOD_ID +".teleport.player.spawn.middle_earth.no_spawn", "%s have no Middle-earth spawn assigned");

            put("command."+ MiddleEarth.MOD_ID +".teleport.spawn.overworld.success", "You got teleported to your Overworld return spawn");
            put("command."+ MiddleEarth.MOD_ID +".teleport.spawn.overworld.error", "There was an error while teleporting you to the Overworld return spawn");
            put("command."+ MiddleEarth.MOD_ID +".teleport.player.spawn.overworld.success", "%s got teleported to their Overworld return spawn at [%s, %s, %s](xyz)");
            put("command."+ MiddleEarth.MOD_ID +".teleport.player.spawn.overworld.no_spawn", "There was an error while teleporting %s to their Overworld return spawn");

            put("command."+ MiddleEarth.MOD_ID +".teleport.to.spawn.middle_earth.success", "You got teleported to <%s> at [%s, %s, %s](xyz)");
            put("command."+ MiddleEarth.MOD_ID +".teleport.to.spawn.middle_earth.error", "You couldn't be teleported to <%s>");
            put("command."+ MiddleEarth.MOD_ID +".teleport.player.to.spawn.middle_earth.success", "%s got teleported to <%s> at [%s, %s, %s](xyz)");
            put("command."+ MiddleEarth.MOD_ID +".teleport.player.to.spawn.middle_earth.error", "%s couldn't be teleported to <%s>");

            put("command."+ MiddleEarth.MOD_ID +".race.set.success", "Your race is set to %s");
            put("command."+ MiddleEarth.MOD_ID +".race.set.fail", "Failed to set race to %s");
            put("command."+ MiddleEarth.MOD_ID +".race.set.target.success", "%s race is set to %s");
            put("command."+ MiddleEarth.MOD_ID +".race.set.target.fail", "Failed to set race to %s");

            put("command."+ MiddleEarth.MOD_ID +".race.get.success", "Your race is %s");
            put("command."+ MiddleEarth.MOD_ID +".race.get.fail", "You don't have a race");
            put("command."+ MiddleEarth.MOD_ID +".race.get.target.success", "%s race is %s");
            put("command."+ MiddleEarth.MOD_ID +".race.get.target.fail", "%s don't have a race");

            put("command."+ MiddleEarth.MOD_ID +".race.reset.success", "Your race is reset");
            put("command."+ MiddleEarth.MOD_ID +".race.reset.fail", "Failed to reset your race");
            put("command."+ MiddleEarth.MOD_ID +".race.reset.target.success", "%s race is reset");
            put("command."+ MiddleEarth.MOD_ID +".race.reset.target.fail", "Failed to reset race for %s");

            put("command."+ MiddleEarth.MOD_ID +".back_attachment.success", "Back Attachment successfully applied, ");
            put("command."+ MiddleEarth.MOD_ID +".back_attachment.fail", "Failed to apply Back Attachment.");

            put("command."+ MiddleEarth.MOD_ID +".helmet_attachment.success", "Helmet Attachment successfully applied, ");
            put("command."+ MiddleEarth.MOD_ID +".helmet_attachment.fail", "Failed to apply Helmet Attachment.");

            put("command."+ MiddleEarth.MOD_ID +".back_attachment.hand_empty", "Hand Empty. Take a Middle-earth Mod chestplate in your main hand to apply a Back Attachment.");
            put("command."+ MiddleEarth.MOD_ID +".helmet_attachment.hand_empty", "Hand Empty. Take a Middle-earth Mod helmet in your main hand to apply a Helmet Attachment.");

            put("command."+ MiddleEarth.MOD_ID +".back_attachment.wrong_item", "Wrong Item type. Take a Middle-earth Mod chestplate in your main hand to apply a Back Attachment.");
            put("command."+ MiddleEarth.MOD_ID +".helmet_attachment.wrong_item", "Wrong Item type. Take a Middle-earth Mod helmet in your main hand to apply a Helmet Attachment.");

            put("alert."+ MiddleEarth.MOD_ID +".large_door.blocked", "Something seems to prevent the door from moving.");

            put("alert."+ MiddleEarth.MOD_ID +".hood_down", "Hood down.");
            put("alert."+ MiddleEarth.MOD_ID +".hood_up", "Hood up.");

            put("alert."+ MiddleEarth.MOD_ID +".seat.occupied", "This seat is occupied.");
            put("alert."+ MiddleEarth.MOD_ID +".seat.space_not_empty", "Block above the seat is not empty.");

            put("event."+ MiddleEarth.MOD_ID +".join.faction.success", "You have joined %s");
            put("event."+ MiddleEarth.MOD_ID +".leave.faction.success", "You have left %s");

            put("exception."+ MiddleEarth.MOD_ID +".no_faction.target", "%s have no faction");
            put("exception."+ MiddleEarth.MOD_ID +".no_faction.source", "You have no faction");
            put("exception."+ MiddleEarth.MOD_ID +".faction_identifier", "%s is not a valid faction identifier");
            put("exception."+ MiddleEarth.MOD_ID +".spawn_identifier", "%s is not a valid spawn identifier");
            put("exception."+ MiddleEarth.MOD_ID +".identical_faction.target", "%s already joined %s");
            put("exception."+ MiddleEarth.MOD_ID +".identical_faction.source", "You already joined %s");

            put("ui."+ MiddleEarth.MOD_ID +".onboarding_selection.title", "Onboarding Screen");
            put("ui."+ MiddleEarth.MOD_ID +".continue_character", "Continue");
            put("ui."+ MiddleEarth.MOD_ID +".reset_character", "Reset Affiliation");

            put("ui."+ MiddleEarth.MOD_ID +".return_confirmation.title", "Onboarding Screen");
            put("ui."+ MiddleEarth.MOD_ID +".return_confirmation.continue_character.title", "Return Button");
            put("ui."+ MiddleEarth.MOD_ID +".return_confirmation.continue_character.content", "Go to Overworld");

            put("ui."+ MiddleEarth.MOD_ID +".map_screen.button.fullscreen_toggle", "Toggle Fullscreen Button");
            put("ui."+ MiddleEarth.MOD_ID +".map_screen.button.map_overlay_toggle", "Map Overlay Toggle Button");
            put("ui."+ MiddleEarth.MOD_ID +".map_screen.button.recenter_on_player", "Recenter on Player Button");
            put("ui."+ MiddleEarth.MOD_ID +".map_screen.button.zoom_in", "Zoom In Button");
            put("ui."+ MiddleEarth.MOD_ID +".map_screen.button.zoom_out", "Zoom Out Button");
            put("ui."+ MiddleEarth.MOD_ID +".map_screen.tooltip.coordinates_title", "Coordinates");
            put("ui."+ MiddleEarth.MOD_ID +".map_screen.tooltip.coordinates_label", "[x,z] ");
            put("ui."+ MiddleEarth.MOD_ID +".map_screen.tooltip.coordinates_content", "%s, %s");
            put("ui."+ MiddleEarth.MOD_ID +".map_screen.tooltip.biome_label", "[Biome] ");
            put("ui."+ MiddleEarth.MOD_ID +".map_screen.tooltip.biome_content", "%s");
            put("ui."+ MiddleEarth.MOD_ID +".map_screen.tooltip.teleport_keybind", "[%s] to teleport");
            put("ui."+ MiddleEarth.MOD_ID +".map_screen.map_title_text", "Middle-earth Map");

            put("ui."+ MiddleEarth.MOD_ID +".structure_manager.label_selected_id", "Selected : %s");
            put("ui."+ MiddleEarth.MOD_ID +".structure_manager.label_runtime_id", "Runtime : %s");
            put("ui."+ MiddleEarth.MOD_ID +".structure_manager.label_enable_status", "Enabled : %s");
            put("ui."+ MiddleEarth.MOD_ID +".structure_manager.button_kill_all", "Kill All");
            put("ui."+ MiddleEarth.MOD_ID +".structure_manager.button_spawn_all", "Spawn All");

            put("screen."+ MiddleEarth.MOD_ID +".faction_selection_screen", "Faction Selection");
            put("screen."+ MiddleEarth.MOD_ID +".button.faction_randomizer", "Randomize Faction");
            put("screen."+ MiddleEarth.MOD_ID +".button.full_randomizer", "Fully Randomize");
            put("screen."+ MiddleEarth.MOD_ID +".button.focus_current", "Focus Toggle");
            put("screen."+ MiddleEarth.MOD_ID +".button.zoom_out", "Zoom In");
            put("screen."+ MiddleEarth.MOD_ID +".button.zoom_in", "Zoom Out");
            put("screen."+ MiddleEarth.MOD_ID +".button.confirm", "Confirm");
            put("screen."+ MiddleEarth.MOD_ID +".information.subfaction", "Subfaction , ");
            put("screen."+ MiddleEarth.MOD_ID +".information.races", "Race");
            put("screen."+ MiddleEarth.MOD_ID +".information.races.many", "Races");
            put("screen."+ MiddleEarth.MOD_ID +".information.description", "Description");


            put("widget."+ MiddleEarth.MOD_ID +".spawn_tooltip_title", "Spawns");
            put("widget."+ MiddleEarth.MOD_ID +".marker.selected_title_container.before", "[");
            put("widget."+ MiddleEarth.MOD_ID +".marker.selected_title_container.after", "]");
            put("widget."+ MiddleEarth.MOD_ID +".marker.margin_front", "   ");
            put("widget."+ MiddleEarth.MOD_ID +".marker.more", "More ...");

            put("ui."+ MiddleEarth.MOD_ID +".search.label", "Search...");
            put("ui."+ MiddleEarth.MOD_ID +".search.toggle_button", "Search Bar Toggle");
            put("ui."+ MiddleEarth.MOD_ID +".search.screen_click_button", "Screen Click Button");

            put("key.category."+ MiddleEarth.MOD_ID +".me", "Middle-earth");
            put("key."+ MiddleEarth.MOD_ID +".hood_state_toggle", "Toggle Worn Hood state");
            put("key."+ MiddleEarth.MOD_ID +".map_teleport", "Map Teleport");
            put("key."+ MiddleEarth.MOD_ID +".map_fullscreen_toggle", "Toggle Map Fullscreen");

            put("modmenu.nameTranslation.me", "Middle-earth");
            put("modmenu.descriptionTranslation.me", "Join the legendary world of Tolkien's universe from The Lord of The Rings to the Hobbit and more!");
            put("modmenu.summaryTranslation.me", "Join the legendary world of Tolkien's universe from The Lord of The Rings to the Hobbit and more!");

            put("emi.category."+ MiddleEarth.MOD_ID +".forge", "Forge");
            put("emi.category."+ MiddleEarth.MOD_ID +".anvil_shaping", "Shaping Anvil");
            put("emi.category."+ MiddleEarth.MOD_ID +".artisan_table", "Artisan Table");

            put("tag.item."+ MiddleEarth.MOD_ID +".anvil_items", "Anvil Items");
            put("tag.item."+ MiddleEarth.MOD_ID +".bones", "Bones");
            put("tag.item."+ MiddleEarth.MOD_ID +".cloaks", "Cloaks");
            put("tag.item."+ MiddleEarth.MOD_ID +".cooked_poultry", "Cooked Poultry");
            put("tag.item."+ MiddleEarth.MOD_ID +".dyeable", "Dyeable");
            put("tag.item."+ MiddleEarth.MOD_ID +".feathers", "Feathers");
            put("tag.item."+ MiddleEarth.MOD_ID +".glowy", "Glowing Items");
            put("tag.item."+ MiddleEarth.MOD_ID +".ingot_shaping", "Ingot Shaping");
            put("tag.item."+ MiddleEarth.MOD_ID +".lead_ores", "Lead Ores");
            put("tag.item."+ MiddleEarth.MOD_ID +".mithril_ores", "Mithril Ores");
            put("tag.item."+ MiddleEarth.MOD_ID +".mod_planks", "Middle-earth Planks");
            put("tag.item."+ MiddleEarth.MOD_ID +".mod_stripped_logs", "Middle-earth Stripped Logs");
            put("tag.item."+ MiddleEarth.MOD_ID +".mushroom_stew_ingredient", "Mushroom Stew Ingredient");
            put("tag.item."+ MiddleEarth.MOD_ID +".nugget_shaping", "Nugget Shaping");
            put("tag.item."+ MiddleEarth.MOD_ID +".raw_poultry", "Raw Poultry");
            put("tag.item."+ MiddleEarth.MOD_ID +".shard", "Shard");
            put("tag.item."+ MiddleEarth.MOD_ID +".shingles", "Shingles");
            put("tag.item."+ MiddleEarth.MOD_ID +".silver_ores", "Silver Ores");
            put("tag.item."+ MiddleEarth.MOD_ID +".thatchy", "Thatch");
            put("tag.item."+ MiddleEarth.MOD_ID +".tin", "Tin");
            put("tag.item."+ MiddleEarth.MOD_ID +".tin_ores", "Tin Ores");
            put("tag.item."+ MiddleEarth.MOD_ID +".warg_food", "Warg Food");
            put("tag.item."+ MiddleEarth.MOD_ID +".worm", "Worm");

            put("painting."+ MiddleEarth.MOD_ID +".author.boenndal", "Boenndal");
            put("painting."+ MiddleEarth.MOD_ID +".author.scosher", "Scosher");

            put("painting."+ MiddleEarth.MOD_ID +".dwarf_portrait.title", "Dwarf Portrait");
            put("painting."+ MiddleEarth.MOD_ID +".elf_portrait.title", "Elf Portrait");
            put("painting."+ MiddleEarth.MOD_ID +".hobbit_portrait.title", "Hobbit Portrait");
            put("painting."+ MiddleEarth.MOD_ID +".human_portrait.title", "Human Portrait");
            put("painting."+ MiddleEarth.MOD_ID +".orc_portrait.title", "Orc Portrait");
            put("painting."+ MiddleEarth.MOD_ID +".note_board.title", "Note Board");
            put("painting."+ MiddleEarth.MOD_ID +".pan_board.title", "Pan Board");
            put("painting."+ MiddleEarth.MOD_ID +".rohirric_tapestry.title", "Rohirric Tapestry");
            put("painting."+ MiddleEarth.MOD_ID +".small_mirror.title", "Small Mirror");
            put("painting."+ MiddleEarth.MOD_ID +".tall_mirror.title", "Tall Mirror");
            put("painting."+ MiddleEarth.MOD_ID +".prancing_pony.title", "Prancing Pony");
            put("painting."+ MiddleEarth.MOD_ID +".tool_board.title", "Tool Board");
            put("painting."+ MiddleEarth.MOD_ID +".gondorian_tapestry.title", "Gondorian Tapestry");
            put("painting."+ MiddleEarth.MOD_ID +".dwarven_plaque.title", "Dwarven Plaque");

            put("attribute.name.powdered_snow_immunity", "Powdered Snow Immunity");
            put("attribute.name.climbing_strength", "Climbing Strength");
            put("attribute.name.detection_range", "Detection Range");
            put("attribute.name.width_scale", "Width Scale");
        }
    };
}
