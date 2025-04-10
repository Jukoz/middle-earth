package net.sevenstars.middleearth.datageneration.content;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranslationEntries {

    public static List<Block> blockEntries = new ArrayList<>() {
    };

    public static List<Item> itemEntries = new ArrayList<>() {
    };

    public static List<EntityType<?>> entityEntries = new ArrayList<>() {
    };

    public static List<String> biomeEntries = new ArrayList<>() {
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

            put("item."+ MiddleEarth.MOD_ID +".round_shield.black", "Black Round Shield");
            put("item."+ MiddleEarth.MOD_ID +".round_shield.blue", "Blue Round Shield");
            put("item."+ MiddleEarth.MOD_ID +".round_shield.brown", "Brown Round Shield");
            put("item."+ MiddleEarth.MOD_ID +".round_shield.cyan", "Cyan Round Shield");
            put("item."+ MiddleEarth.MOD_ID +".round_shield.gray", "Gray Round Shield");
            put("item."+ MiddleEarth.MOD_ID +".round_shield.green", "Green Round Shield");
            put("item."+ MiddleEarth.MOD_ID +".round_shield.light_blue", "Light Blue Round Shield");
            put("item."+ MiddleEarth.MOD_ID +".round_shield.light_gray", "Light Gray Round Shield");
            put("item."+ MiddleEarth.MOD_ID +".round_shield.lime", "Lime Round Shield");
            put("item."+ MiddleEarth.MOD_ID +".round_shield.magenta", "Magenta Round Shield");
            put("item."+ MiddleEarth.MOD_ID +".round_shield.orange", "Orange Round Shield");
            put("item."+ MiddleEarth.MOD_ID +".round_shield.pink", "Pink Round Shield");
            put("item."+ MiddleEarth.MOD_ID +".round_shield.purple", "Purple Round Shield");
            put("item."+ MiddleEarth.MOD_ID +".round_shield.red", "Red Round Shield");
            put("item."+ MiddleEarth.MOD_ID +".round_shield.white", "White Round Shield");
            put("item."+ MiddleEarth.MOD_ID +".round_shield.yellow", "Yellow Round Shield");


            put("item."+ MiddleEarth.MOD_ID +".heater_shield.black", "Black Heater Shield");
            put("item."+ MiddleEarth.MOD_ID +".heater_shield.blue", "Blue Heater Shield");
            put("item."+ MiddleEarth.MOD_ID +".heater_shield.brown", "Brown Heater Shield");
            put("item."+ MiddleEarth.MOD_ID +".heater_shield.cyan", "Cyan Heater Shield");
            put("item."+ MiddleEarth.MOD_ID +".heater_shield.gray", "Gray Heater Shield");
            put("item."+ MiddleEarth.MOD_ID +".heater_shield.green", "Green Heater Shield");
            put("item."+ MiddleEarth.MOD_ID +".heater_shield.light_blue", "Light Blue Heater Shield");
            put("item."+ MiddleEarth.MOD_ID +".heater_shield.light_gray", "Light Gray Heater Shield");
            put("item."+ MiddleEarth.MOD_ID +".heater_shield.lime", "Lime Heater Shield");
            put("item."+ MiddleEarth.MOD_ID +".heater_shield.magenta", "Magenta Heater Shield");
            put("item."+ MiddleEarth.MOD_ID +".heater_shield.orange", "Orange Heater Shield");
            put("item."+ MiddleEarth.MOD_ID +".heater_shield.pink", "Pink Heater Shield");
            put("item."+ MiddleEarth.MOD_ID +".heater_shield.purple", "Purple Heater Shield");
            put("item."+ MiddleEarth.MOD_ID +".heater_shield.red", "Red Heater Shield");
            put("item."+ MiddleEarth.MOD_ID +".heater_shield.white", "White Heater Shield");
            put("item."+ MiddleEarth.MOD_ID +".heater_shield.yellow", "Yellow Heater Shield");


            put("item."+ MiddleEarth.MOD_ID +".kite_shield.black", "Black Kite Shield");
            put("item."+ MiddleEarth.MOD_ID +".kite_shield.blue", "Blue Kite Shield");
            put("item."+ MiddleEarth.MOD_ID +".kite_shield.brown", "Brown Kite Shield");
            put("item."+ MiddleEarth.MOD_ID +".kite_shield.cyan", "Cyan Kite Shield");
            put("item."+ MiddleEarth.MOD_ID +".kite_shield.gray", "Gray Kite Shield");
            put("item."+ MiddleEarth.MOD_ID +".kite_shield.green", "Green Kite Shield");
            put("item."+ MiddleEarth.MOD_ID +".kite_shield.light_blue", "Light Blue Kite Shield");
            put("item."+ MiddleEarth.MOD_ID +".kite_shield.light_gray", "Light Gray Kite Shield");
            put("item."+ MiddleEarth.MOD_ID +".kite_shield.lime", "Lime Kite Shield");
            put("item."+ MiddleEarth.MOD_ID +".kite_shield.magenta", "Magenta Kite Shield");
            put("item."+ MiddleEarth.MOD_ID +".kite_shield.orange", "Orange Kite Shield");
            put("item."+ MiddleEarth.MOD_ID +".kite_shield.pink", "Pink Kite Shield");
            put("item."+ MiddleEarth.MOD_ID +".kite_shield.purple", "Purple Kite Shield");
            put("item."+ MiddleEarth.MOD_ID +".kite_shield.red", "Red Kite Shield");
            put("item."+ MiddleEarth.MOD_ID +".kite_shield.white", "White Kite Shield");
            put("item."+ MiddleEarth.MOD_ID +".kite_shield.yellow", "Yellow Kite Shield");

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
            put("block."+ MiddleEarth.MOD_ID +".bellows", "Bellows");
            put("block."+ MiddleEarth.MOD_ID +".treated_anvil", "Treated Anvil");
            put("block."+ MiddleEarth.MOD_ID +".dwarven_treated_anvil", "Dwarven Treated Anvil");
            put("block."+ MiddleEarth.MOD_ID +".elven_treated_anvil", "Elven Treated Anvil");
            put("block."+ MiddleEarth.MOD_ID +".orcish_treated_anvil", "Orcish Treated Anvil");
            put("block."+ MiddleEarth.MOD_ID +".artisan_table", "Artisan Table");

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
            put("item."+ MiddleEarth.MOD_ID +".anvil_banner_pattern.desc", "Anvil Icons");
            put("item."+ MiddleEarth.MOD_ID +".bell_banner_pattern.desc", "Bell Icons");
            put("item."+ MiddleEarth.MOD_ID +".bow_banner_pattern.desc", "Bow Icons");
            put("item."+ MiddleEarth.MOD_ID +".dwarf_crown_banner_pattern.desc", "Dwarf Crown Icons");
            put("item."+ MiddleEarth.MOD_ID +".dragon_banner_pattern.desc", "A great Dragon");
            put("item."+ MiddleEarth.MOD_ID +".pipeweed_banner_pattern.desc", "A delicacy from the shire");
            put("item."+ MiddleEarth.MOD_ID +".snail_banner_pattern.desc", "The Slimy Overlord");

            put("block."+ MiddleEarth.MOD_ID +".faction_banner", "%s Banner");

            //TODO try to automate
            put("block."+ MiddleEarth.MOD_ID +".banner.tree.black", "Black Tree");
            put("block."+ MiddleEarth.MOD_ID +".banner.tree.blue", "Blue Tree");
            put("block."+ MiddleEarth.MOD_ID +".banner.tree.brown", "Brown Tree");
            put("block."+ MiddleEarth.MOD_ID +".banner.tree.cyan", "Cyan Tree");
            put("block."+ MiddleEarth.MOD_ID +".banner.tree.gray", "Gray Tree");
            put("block."+ MiddleEarth.MOD_ID +".banner.tree.green", "Green Tree");
            put("block."+ MiddleEarth.MOD_ID +".banner.tree.light_blue", "Light Blue Tree");
            put("block."+ MiddleEarth.MOD_ID +".banner.tree.light_gray", "Light Gray Tree");
            put("block."+ MiddleEarth.MOD_ID +".banner.tree.lime", "Lime Tree");
            put("block."+ MiddleEarth.MOD_ID +".banner.tree.magenta", "Magenta Tree");
            put("block."+ MiddleEarth.MOD_ID +".banner.tree.orange", "Orange Tree");
            put("block."+ MiddleEarth.MOD_ID +".banner.tree.pink", "Pink Tree");
            put("block."+ MiddleEarth.MOD_ID +".banner.tree.purple", "Purple Tree");
            put("block."+ MiddleEarth.MOD_ID +".banner.tree.red", "Red Tree");
            put("block."+ MiddleEarth.MOD_ID +".banner.tree.white", "White Tree");
            put("block."+ MiddleEarth.MOD_ID +".banner.tree.yellow", "Yellow Tree");

            put("block."+ MiddleEarth.MOD_ID +".banner.horse.black", "Black Horse");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse.blue", "Blue Horse");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse.brown", "Brown Horse");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse.cyan", "Cyan Horse");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse.gray", "Gray Horse");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse.green", "Green Horse");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse.light_blue", "Light Blue Horse");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse.light_gray", "Light Gray Horse");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse.lime", "Lime Horse");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse.magenta", "Magenta Horse");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse.orange", "Orange Horse");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse.pink", "Pink Horse");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse.purple", "Purple Horse");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse.red", "Red Horse");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse.white", "White Horse");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse.yellow", "Yellow Horse");

            put("block."+ MiddleEarth.MOD_ID +".banner.horse_head.black", "Black Horse Head");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse_head.blue", "Blue Horse Head");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse_head.brown", "Brown Horse Head");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse_head.cyan", "Cyan Horse Head");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse_head.gray", "Gray Horse Head");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse_head.green", "Green Horse Head");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse_head.light_blue", "Light Blue Horse Head");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse_head.light_gray", "Light Gray Horse Head");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse_head.lime", "Lime Horse Head");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse_head.magenta", "Magenta Horse Head");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse_head.orange", "Orange Horse Head");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse_head.pink", "Pink Horse Head");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse_head.purple", "Purple Horse Head");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse_head.red", "Red Horse Head");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse_head.white", "White Horse Head");
            put("block."+ MiddleEarth.MOD_ID +".banner.horse_head.yellow", "Yellow Horse Head");

            put("block."+ MiddleEarth.MOD_ID +".banner.goldenwood.black", "Black Goldenwood");
            put("block."+ MiddleEarth.MOD_ID +".banner.goldenwood.blue", "Blue Goldenwood");
            put("block."+ MiddleEarth.MOD_ID +".banner.goldenwood.brown", "Brown Goldenwood");
            put("block."+ MiddleEarth.MOD_ID +".banner.goldenwood.cyan", "Cyan Goldenwood");
            put("block."+ MiddleEarth.MOD_ID +".banner.goldenwood.gray", "Gray Goldenwood");
            put("block."+ MiddleEarth.MOD_ID +".banner.goldenwood.green", "Green Goldenwood");
            put("block."+ MiddleEarth.MOD_ID +".banner.goldenwood.light_blue", "Light Blue Goldenwood");
            put("block."+ MiddleEarth.MOD_ID +".banner.goldenwood.light_gray", "Light Gray Goldenwood");
            put("block."+ MiddleEarth.MOD_ID +".banner.goldenwood.lime", "Lime Goldenwood");
            put("block."+ MiddleEarth.MOD_ID +".banner.goldenwood.magenta", "Magenta Goldenwood");
            put("block."+ MiddleEarth.MOD_ID +".banner.goldenwood.orange", "Orange Goldenwood");
            put("block."+ MiddleEarth.MOD_ID +".banner.goldenwood.pink", "Pink Goldenwood");
            put("block."+ MiddleEarth.MOD_ID +".banner.goldenwood.purple", "Purple Goldenwood");
            put("block."+ MiddleEarth.MOD_ID +".banner.goldenwood.red", "Red Goldenwood");
            put("block."+ MiddleEarth.MOD_ID +".banner.goldenwood.white", "White Goldenwood");
            put("block."+ MiddleEarth.MOD_ID +".banner.goldenwood.yellow", "Yellow Goldenwood");

            put("block."+ MiddleEarth.MOD_ID +".banner.painted_eye.black", "Black Painted Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.painted_eye.blue", "Blue Painted Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.painted_eye.brown", "Brown Painted Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.painted_eye.cyan", "Cyan Painted Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.painted_eye.gray", "Gray Painted Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.painted_eye.green", "Green Painted Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.painted_eye.light_blue", "Light Blue Painted Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.painted_eye.light_gray", "Light Gray Painted Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.painted_eye.lime", "Lime Painted Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.painted_eye.magenta", "Magenta Painted Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.painted_eye.orange", "Orange Painted Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.painted_eye.pink", "Pink Painted Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.painted_eye.purple", "Purple Painted Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.painted_eye.red", "Red Painted Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.painted_eye.white", "White Painted Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.painted_eye.yellow", "Yellow Painted Eye Pattern");

            put("block."+ MiddleEarth.MOD_ID +".banner.eye_of_sauron.black", "Black Eye Of Sauron Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.eye_of_sauron.blue", "Blue Eye Of Sauron Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.eye_of_sauron.brown", "Brown Eye Of Sauron Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.eye_of_sauron.cyan", "Cyan Eye Of Sauron Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.eye_of_sauron.gray", "Gray Eye Of Sauron Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.eye_of_sauron.green", "Green Eye Of Sauron Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.eye_of_sauron.light_blue", "Light Blue Eye Of Sauron Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.eye_of_sauron.light_gray", "Light Gray Eye Of Sauron Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.eye_of_sauron.lime", "Lime Eye Of Sauron Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.eye_of_sauron.magenta", "Magenta Eye Of Sauron Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.eye_of_sauron.orange", "Orange Eye Of Sauron Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.eye_of_sauron.pink", "Pink Eye Of Sauron Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.eye_of_sauron.purple", "Purple Eye Of Sauron Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.eye_of_sauron.red", "Red Eye Of Sauron Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.eye_of_sauron.white", "White Eye Of Sauron Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.eye_of_sauron.yellow", "Yellow Eye Of Sauron Pattern");

            put("block."+ MiddleEarth.MOD_ID +".banner.great_eye.black", "Black Great Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.great_eye.blue", "Blue Great Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.great_eye.brown", "Brown Great Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.great_eye.cyan", "Cyan Great Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.great_eye.gray", "Gray Great Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.great_eye.green", "Green Great Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.great_eye.light_blue", "Light Blue Great Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.great_eye.light_gray", "Light Gray Great Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.great_eye.lime", "Lime Great Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.great_eye.magenta", "Magenta Great Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.great_eye.orange", "Orange Great Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.great_eye.pink", "Pink Great Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.great_eye.purple", "Purple Great Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.great_eye.red", "Red Great Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.great_eye.white", "White Great Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.great_eye.yellow", "Yellow Great Eye Pattern");

            put("block."+ MiddleEarth.MOD_ID +".banner.evil_eye.black", "Black Evil Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_eye.blue", "Blue Evil Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_eye.brown", "Brown Evil Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_eye.cyan", "Cyan Evil Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_eye.gray", "Gray Evil Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_eye.green", "Green Evil Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_eye.light_blue", "Light Blue Evil Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_eye.light_gray", "Light Gray Evil Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_eye.lime", "Lime Evil Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_eye.magenta", "Magenta Evil Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_eye.orange", "Orange Evil Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_eye.pink", "Pink Evil Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_eye.purple", "Purple Evil Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_eye.red", "Red Evil Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_eye.white", "White Evil Eye Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_eye.yellow", "Yellow Evil Eye Pattern");

            put("block."+ MiddleEarth.MOD_ID +".banner.evil_peaks.black", "Black Evil Peaks Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_peaks.blue", "Blue Evil Peaks Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_peaks.brown", "Brown Evil Peaks Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_peaks.cyan", "Cyan Evil Peaks Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_peaks.gray", "Gray Evil Peaks Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_peaks.green", "Green Evil Peaks Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_peaks.light_blue", "Light Blue Evil Peaks Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_peaks.light_gray", "Light Gray Evil Peaks Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_peaks.lime", "Lime Evil Peaks Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_peaks.magenta", "Magenta Evil Peaks Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_peaks.orange", "Orange Evil Peaks Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_peaks.pink", "Pink Evil Peaks Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_peaks.purple", "Purple Evil Peaks Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_peaks.red", "Red Evil Peaks Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_peaks.white", "White Evil Peaks Pattern");
            put("block."+ MiddleEarth.MOD_ID +".banner.evil_peaks.yellow", "Yellow Evil Peaks Pattern");

            put("block."+ MiddleEarth.MOD_ID +".banner.hand.black", "Black Hand");
            put("block."+ MiddleEarth.MOD_ID +".banner.hand.blue", "Blue Hand");
            put("block."+ MiddleEarth.MOD_ID +".banner.hand.brown", "Brown Hand");
            put("block."+ MiddleEarth.MOD_ID +".banner.hand.cyan", "Cyan Hand");
            put("block."+ MiddleEarth.MOD_ID +".banner.hand.gray", "Gray Hand");
            put("block."+ MiddleEarth.MOD_ID +".banner.hand.green", "Green Hand");
            put("block."+ MiddleEarth.MOD_ID +".banner.hand.light_blue", "Light Blue Hand");
            put("block."+ MiddleEarth.MOD_ID +".banner.hand.light_gray", "Light Gray Hand");
            put("block."+ MiddleEarth.MOD_ID +".banner.hand.lime", "Lime Hand");
            put("block."+ MiddleEarth.MOD_ID +".banner.hand.magenta", "Magenta Hand");
            put("block."+ MiddleEarth.MOD_ID +".banner.hand.orange", "Orange Hand");
            put("block."+ MiddleEarth.MOD_ID +".banner.hand.pink", "Pink Hand");
            put("block."+ MiddleEarth.MOD_ID +".banner.hand.purple", "Purple Hand");
            put("block."+ MiddleEarth.MOD_ID +".banner.hand.red", "Red Hand");
            put("block."+ MiddleEarth.MOD_ID +".banner.hand.white", "White Hand");
            put("block."+ MiddleEarth.MOD_ID +".banner.hand.yellow", "Yellow Hand");

            put("block."+ MiddleEarth.MOD_ID +".banner.dragon.black", "Black Dragon");
            put("block."+ MiddleEarth.MOD_ID +".banner.dragon.blue", "Blue Dragon");
            put("block."+ MiddleEarth.MOD_ID +".banner.dragon.brown", "Brown Dragon");
            put("block."+ MiddleEarth.MOD_ID +".banner.dragon.cyan", "Cyan Dragon");
            put("block."+ MiddleEarth.MOD_ID +".banner.dragon.gray", "Gray Dragon");
            put("block."+ MiddleEarth.MOD_ID +".banner.dragon.green", "Green Dragon");
            put("block."+ MiddleEarth.MOD_ID +".banner.dragon.light_blue", "Light Blue Dragon");
            put("block."+ MiddleEarth.MOD_ID +".banner.dragon.light_gray", "Light Gray Dragon");
            put("block."+ MiddleEarth.MOD_ID +".banner.dragon.lime", "Lime Dragon");
            put("block."+ MiddleEarth.MOD_ID +".banner.dragon.magenta", "Magenta Dragon");
            put("block."+ MiddleEarth.MOD_ID +".banner.dragon.orange", "Orange Dragon");
            put("block."+ MiddleEarth.MOD_ID +".banner.dragon.pink", "Pink Dragon");
            put("block."+ MiddleEarth.MOD_ID +".banner.dragon.purple", "Purple Dragon");
            put("block."+ MiddleEarth.MOD_ID +".banner.dragon.red", "Red Dragon");
            put("block."+ MiddleEarth.MOD_ID +".banner.dragon.white", "White Dragon");
            put("block."+ MiddleEarth.MOD_ID +".banner.dragon.yellow", "Yellow Dragon");

            put("block."+ MiddleEarth.MOD_ID +".banner.pipe.black", "Black Pipe");
            put("block."+ MiddleEarth.MOD_ID +".banner.pipe.blue", "Blue Pipe");
            put("block."+ MiddleEarth.MOD_ID +".banner.pipe.brown", "Brown Pipe");
            put("block."+ MiddleEarth.MOD_ID +".banner.pipe.cyan", "Cyan Pipe");
            put("block."+ MiddleEarth.MOD_ID +".banner.pipe.gray", "Gray Pipe");
            put("block."+ MiddleEarth.MOD_ID +".banner.pipe.green", "Green Pipe");
            put("block."+ MiddleEarth.MOD_ID +".banner.pipe.light_blue", "Light Blue Pipe");
            put("block."+ MiddleEarth.MOD_ID +".banner.pipe.light_gray", "Light Gray Pipe");
            put("block."+ MiddleEarth.MOD_ID +".banner.pipe.lime", "Lime Pipe");
            put("block."+ MiddleEarth.MOD_ID +".banner.pipe.magenta", "Magenta Pipe");
            put("block."+ MiddleEarth.MOD_ID +".banner.pipe.orange", "Orange Pipe");
            put("block."+ MiddleEarth.MOD_ID +".banner.pipe.pink", "Pink Pipe");
            put("block."+ MiddleEarth.MOD_ID +".banner.pipe.purple", "Purple Pipe");
            put("block."+ MiddleEarth.MOD_ID +".banner.pipe.red", "Red Pipe");
            put("block."+ MiddleEarth.MOD_ID +".banner.pipe.white", "White Pipe");
            put("block."+ MiddleEarth.MOD_ID +".banner.pipe.yellow", "Yellow Pipe");

            put("block."+ MiddleEarth.MOD_ID +".banner.mallorn.black", "Black Mallorn");
            put("block."+ MiddleEarth.MOD_ID +".banner.mallorn.blue", "Blue Mallorn");
            put("block."+ MiddleEarth.MOD_ID +".banner.mallorn.brown", "Brown Mallorn");
            put("block."+ MiddleEarth.MOD_ID +".banner.mallorn.cyan", "Cyan Mallorn");
            put("block."+ MiddleEarth.MOD_ID +".banner.mallorn.gray", "Gray Mallorn");
            put("block."+ MiddleEarth.MOD_ID +".banner.mallorn.green", "Green Mallorn");
            put("block."+ MiddleEarth.MOD_ID +".banner.mallorn.light_blue", "Light Blue Mallorn");
            put("block."+ MiddleEarth.MOD_ID +".banner.mallorn.light_gray", "Light Gray Mallorn");
            put("block."+ MiddleEarth.MOD_ID +".banner.mallorn.lime", "Lime Mallorn");
            put("block."+ MiddleEarth.MOD_ID +".banner.mallorn.magenta", "Magenta Mallorn");
            put("block."+ MiddleEarth.MOD_ID +".banner.mallorn.orange", "Orange Mallorn");
            put("block."+ MiddleEarth.MOD_ID +".banner.mallorn.pink", "Pink Mallorn");
            put("block."+ MiddleEarth.MOD_ID +".banner.mallorn.purple", "Purple Mallorn");
            put("block."+ MiddleEarth.MOD_ID +".banner.mallorn.red", "Red Mallorn");
            put("block."+ MiddleEarth.MOD_ID +".banner.mallorn.white", "White Mallorn");
            put("block."+ MiddleEarth.MOD_ID +".banner.mallorn.yellow", "Yellow Mallorn");

            put("block."+ MiddleEarth.MOD_ID +".banner.star_and_leaf.black", "Black Star And Leaf");
            put("block."+ MiddleEarth.MOD_ID +".banner.star_and_leaf.blue", "Blue Star And Leaf");
            put("block."+ MiddleEarth.MOD_ID +".banner.star_and_leaf.brown", "Brown Star And Leaf");
            put("block."+ MiddleEarth.MOD_ID +".banner.star_and_leaf.cyan", "Cyan Star And Leaf");
            put("block."+ MiddleEarth.MOD_ID +".banner.star_and_leaf.gray", "Gray Star And Leaf");
            put("block."+ MiddleEarth.MOD_ID +".banner.star_and_leaf.green", "Green Star And Leaf");
            put("block."+ MiddleEarth.MOD_ID +".banner.star_and_leaf.light_blue", "Light Blue Star And Leaf");
            put("block."+ MiddleEarth.MOD_ID +".banner.star_and_leaf.light_gray", "Light Gray Star And Leaf");
            put("block."+ MiddleEarth.MOD_ID +".banner.star_and_leaf.lime", "Lime Star And Leaf");
            put("block."+ MiddleEarth.MOD_ID +".banner.star_and_leaf.magenta", "Magenta Star And Leaf");
            put("block."+ MiddleEarth.MOD_ID +".banner.star_and_leaf.orange", "Orange Star And Leaf");
            put("block."+ MiddleEarth.MOD_ID +".banner.star_and_leaf.pink", "Pink Star And Leaf");
            put("block."+ MiddleEarth.MOD_ID +".banner.star_and_leaf.purple", "Purple Star And Leaf");
            put("block."+ MiddleEarth.MOD_ID +".banner.star_and_leaf.red", "Red Star And Leaf");
            put("block."+ MiddleEarth.MOD_ID +".banner.star_and_leaf.white", "White Star And Leaf");
            put("block."+ MiddleEarth.MOD_ID +".banner.star_and_leaf.yellow", "Yellow Star And Leaf");

            put("block."+ MiddleEarth.MOD_ID +".banner.cloth.black", "Black Cloth");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth.blue", "Blue Cloth");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth.brown", "Brown Cloth");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth.cyan", "Cyan Cloth");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth.gray", "Gray Cloth");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth.green", "Green Cloth");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth.light_blue", "Light Blue Cloth");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth.light_gray", "Light Gray Cloth");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth.lime", "Lime Cloth");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth.magenta", "Magenta Cloth");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth.orange", "Orange Cloth");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth.pink", "Pink Cloth");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth.purple", "Purple Cloth");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth.red", "Red Cloth");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth.white", "White Cloth");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth.yellow", "Yellow Cloth");

            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient.black", "Black Cloth Gradient");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient.blue", "Blue Cloth Gradient");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient.brown", "Brown Cloth Gradient");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient.cyan", "Cyan Cloth Gradient");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient.gray", "Gray Cloth Gradient");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient.green", "Green Cloth Gradient");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient.light_blue", "Light Blue Cloth Gradient");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient.light_gray", "Light Gray Cloth Gradient");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient.lime", "Lime Cloth Gradient");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient.magenta", "Magenta Cloth Gradient");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient.orange", "Orange Cloth Gradient");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient.pink", "Pink Cloth Gradient");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient.purple", "Purple Cloth Gradient");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient.red", "Red Cloth Gradient");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient.white", "White Cloth Gradient");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient.yellow", "Yellow Cloth Gradient");

            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient_up.black", "Black Cloth Gradient Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient_up.blue", "Blue Cloth Gradient Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient_up.brown", "Brown Cloth Gradient Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient_up.cyan", "Cyan Cloth Gradient Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient_up.gray", "Gray Cloth Gradient Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient_up.green", "Green Cloth Gradient Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient_up.light_blue", "Light Blue Cloth Gradient Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient_up.light_gray", "Light Gray Cloth Gradient Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient_up.lime", "Lime Cloth Gradient Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient_up.magenta", "Magenta Cloth Gradient Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient_up.orange", "Orange Cloth Gradient Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient_up.pink", "Pink Cloth Gradient Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient_up.purple", "Purple Cloth Gradient Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient_up.red", "Red Cloth Gradient Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient_up.white", "White Cloth Gradient Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.cloth_gradient_up.yellow", "Yellow Cloth Gradient Up");

            put("block."+ MiddleEarth.MOD_ID +".banner.veil.black", "Black Veil");
            put("block."+ MiddleEarth.MOD_ID +".banner.veil.blue", "Blue Veil");
            put("block."+ MiddleEarth.MOD_ID +".banner.veil.brown", "Brown Veil");
            put("block."+ MiddleEarth.MOD_ID +".banner.veil.cyan", "Cyan Veil");
            put("block."+ MiddleEarth.MOD_ID +".banner.veil.gray", "Gray Veil");
            put("block."+ MiddleEarth.MOD_ID +".banner.veil.green", "Green Veil");
            put("block."+ MiddleEarth.MOD_ID +".banner.veil.light_blue", "Light Blue Veil");
            put("block."+ MiddleEarth.MOD_ID +".banner.veil.light_gray", "Light Gray Veil");
            put("block."+ MiddleEarth.MOD_ID +".banner.veil.lime", "Lime Veil");
            put("block."+ MiddleEarth.MOD_ID +".banner.veil.magenta", "Magenta Veil");
            put("block."+ MiddleEarth.MOD_ID +".banner.veil.orange", "Orange Veil");
            put("block."+ MiddleEarth.MOD_ID +".banner.veil.pink", "Pink Veil");
            put("block."+ MiddleEarth.MOD_ID +".banner.veil.purple", "Purple Veil");
            put("block."+ MiddleEarth.MOD_ID +".banner.veil.red", "Red Veil");
            put("block."+ MiddleEarth.MOD_ID +".banner.veil.white", "White Veil");
            put("block."+ MiddleEarth.MOD_ID +".banner.veil.yellow", "Yellow Veil");

            put("block."+ MiddleEarth.MOD_ID +".banner.anvil.black", "Black Anvil");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil.blue", "Blue Anvil");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil.brown", "Brown Anvil");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil.cyan", "Cyan Anvil");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil.gray", "Gray Anvil");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil.green", "Green Anvil");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil.light_blue", "Light Blue Anvil");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil.light_gray", "Light Gray Anvil");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil.lime", "Lime Anvil");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil.magenta", "Magenta Anvil");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil.orange", "Orange Anvil");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil.pink", "Pink Anvil");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil.purple", "Purple Anvil");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil.red", "Red Anvil");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil.white", "White Anvil");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil.yellow", "Yellow Anvil");

            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_bottom.black", "Black Anvil Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_bottom.blue", "Blue Anvil Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_bottom.brown", "Brown Anvil Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_bottom.cyan", "Cyan Anvil Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_bottom.gray", "Gray Anvil Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_bottom.green", "Green Anvil Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_bottom.light_blue", "Light Blue Anvil Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_bottom.light_gray", "Light Gray Anvil Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_bottom.lime", "Lime Anvil Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_bottom.magenta", "Magenta Anvil Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_bottom.orange", "Orange Anvil Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_bottom.pink", "Pink Anvil Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_bottom.purple", "Purple Anvil Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_bottom.red", "Red Anvil Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_bottom.white", "White Anvil Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_bottom.yellow", "Yellow Anvil Bottom");

            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer.black", "Black Anvil Hammer");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer.blue", "Blue Anvil Hammer");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer.brown", "Brown Anvil Hammer");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer.cyan", "Cyan Anvil Hammer");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer.gray", "Gray Anvil Hammer");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer.green", "Green Anvil Hammer");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer.light_blue", "Light Blue Anvil Hammer");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer.light_gray", "Light Gray Anvil Hammer");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer.lime", "Lime Anvil Hammer");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer.magenta", "Magenta Anvil Hammer");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer.orange", "Orange Anvil Hammer");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer.pink", "Pink Anvil Hammer");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer.purple", "Purple Anvil Hammer");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer.red", "Red Anvil Hammer");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer.white", "White Anvil Hammer");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer.yellow", "Yellow Anvil Hammer");

            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_bottom.black", "Black Anvil Hammer Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_bottom.blue", "Blue Anvil Hammer Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_bottom.brown", "Brown Anvil Hammer Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_bottom.cyan", "Cyan Anvil Hammer Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_bottom.gray", "Gray Anvil Hammer Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_bottom.green", "Green Anvil Hammer Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_bottom.light_blue", "Light Blue Anvil Hammer Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_bottom.light_gray", "Light Gray Anvil Hammer Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_bottom.lime", "Lime Anvil Hammer Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_bottom.magenta", "Magenta Anvil Hammer Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_bottom.orange", "Orange Anvil Hammer Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_bottom.pink", "Pink Anvil Hammer Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_bottom.purple", "Purple Anvil Hammer Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_bottom.red", "Red Anvil Hammer Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_bottom.white", "White Anvil Hammer Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_bottom.yellow", "Yellow Anvil Hammer Bottom");

            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_top.black", "Black Anvil Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_top.blue", "Blue Anvil Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_top.brown", "Brown Anvil Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_top.cyan", "Cyan Anvil Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_top.gray", "Gray Anvil Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_top.green", "Green Anvil Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_top.light_blue", "Light Blue Anvil Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_top.light_gray", "Light Gray Anvil Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_top.lime", "Lime Anvil Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_top.magenta", "Magenta Anvil Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_top.orange", "Orange Anvil Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_top.pink", "Pink Anvil Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_top.purple", "Purple Anvil Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_top.red", "Red Anvil Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_top.white", "White Anvil Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_top.yellow", "Yellow Anvil Top");

            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_top.black", "Black Anvil Hammer Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_top.blue", "Blue Anvil Hammer Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_top.brown", "Brown Anvil Hammer Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_top.cyan", "Cyan Anvil Hammer Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_top.gray", "Gray Anvil Hammer Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_top.green", "Green Anvil Hammer Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_top.light_blue", "Light Blue Anvil Hammer Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_top.light_gray", "Light Gray Anvil Hammer Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_top.lime", "Lime Anvil Hammer Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_top.magenta", "Magenta Anvil Hammer Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_top.orange", "Orange Anvil Hammer Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_top.pink", "Pink Anvil Hammer Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_top.purple", "Purple Anvil Hammer Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_top.red", "Red Anvil Hammer Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_top.white", "White Anvil Hammer Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.anvil_hammer_top.yellow", "Yellow Anvil Hammer Top");

            put("block."+ MiddleEarth.MOD_ID +".banner.bell.black", "Black Bell");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell.blue", "Blue Bell");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell.brown", "Brown Bell");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell.cyan", "Cyan Bell");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell.gray", "Gray Bell");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell.green", "Green Bell");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell.light_blue", "Light Blue Bell");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell.light_gray", "Light Gray Bell");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell.lime", "Lime Bell");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell.magenta", "Magenta Bell");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell.orange", "Orange Bell");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell.pink", "Pink Bell");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell.purple", "Purple Bell");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell.red", "Red Bell");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell.white", "White Bell");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell.yellow", "Yellow Bell");

            put("block."+ MiddleEarth.MOD_ID +".banner.bell_top.black", "Black Bell Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_top.blue", "Blue Bell Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_top.brown", "Brown Bell Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_top.cyan", "Cyan Bell Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_top.gray", "Gray Bell Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_top.green", "Green Bell Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_top.light_blue", "Light Blue Bell Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_top.light_gray", "Light Gray Bell Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_top.lime", "Lime Bell Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_top.magenta", "Magenta Bell Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_top.orange", "Orange Bell Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_top.pink", "Pink Bell Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_top.purple", "Purple Bell Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_top.red", "Red Bell Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_top.white", "White Bell Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_top.yellow", "Yellow Bell Top");

            put("block."+ MiddleEarth.MOD_ID +".banner.bell_bottom.black", "Black Bell Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_bottom.blue", "Blue Bell Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_bottom.brown", "Brown Bell Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_bottom.cyan", "Cyan Bell Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_bottom.gray", "Gray Bell Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_bottom.green", "Green Bell Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_bottom.light_blue", "Light Blue Bell Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_bottom.light_gray", "Light Gray Bell Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_bottom.lime", "Lime Bell Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_bottom.magenta", "Magenta Bell Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_bottom.orange", "Orange Bell Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_bottom.pink", "Pink Bell Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_bottom.purple", "Purple Bell Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_bottom.red", "Red Bell Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_bottom.white", "White Bell Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bell_bottom.yellow", "Yellow Bell Bottom");

            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown.black", "Black Dwarf Crown");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown.blue", "Blue Dwarf Crown");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown.brown", "Brown Dwarf Crown");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown.cyan", "Cyan Dwarf Crown");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown.gray", "Gray Dwarf Crown");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown.green", "Green Dwarf Crown");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown.light_blue", "Light Blue Dwarf Crown");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown.light_gray", "Light Gray Dwarf Crown");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown.lime", "Lime Dwarf Crown");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown.magenta", "Magenta Dwarf Crown");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown.orange", "Orange Dwarf Crown");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown.pink", "Pink Dwarf Crown");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown.purple", "Purple Dwarf Crown");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown.red", "Red Dwarf Crown");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown.white", "White Dwarf Crown");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown.yellow", "Yellow Dwarf Crown");

            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_top.black", "Black Dwarf Crown Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_top.blue", "Blue Dwarf Crown Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_top.brown", "Brown Dwarf Crown Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_top.cyan", "Cyan Dwarf Crown Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_top.gray", "Gray Dwarf Crown Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_top.green", "Green Dwarf Crown Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_top.light_blue", "Light Blue Dwarf Crown Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_top.light_gray", "Light Gray Dwarf Crown Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_top.lime", "Lime Dwarf Crown Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_top.magenta", "Magenta Dwarf Crown Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_top.orange", "Orange Dwarf Crown Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_top.pink", "Pink Dwarf Crown Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_top.purple", "Purple Dwarf Crown Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_top.red", "Red Dwarf Crown Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_top.white", "White Dwarf Crown Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_top.yellow", "Yellow Dwarf Crown Top");

            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_bottom.black", "Black Dwarf Crown Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_bottom.blue", "Blue Dwarf Crown Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_bottom.brown", "Brown Dwarf Crown Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_bottom.cyan", "Cyan Dwarf Crown Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_bottom.gray", "Gray Dwarf Crown Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_bottom.green", "Green Dwarf Crown Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_bottom.light_blue", "Light Blue Dwarf Crown Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_bottom.light_gray", "Light Gray Dwarf Crown Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_bottom.lime", "Lime Dwarf Crown Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_bottom.magenta", "Magenta Dwarf Crown Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_bottom.orange", "Orange Dwarf Crown Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_bottom.pink", "Pink Dwarf Crown Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_bottom.purple", "Purple Dwarf Crown Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_bottom.red", "Red Dwarf Crown Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_bottom.white", "White Dwarf Crown Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.dwarf_crown_bottom.yellow", "Yellow Dwarf Crown Bottom");

            put("block."+ MiddleEarth.MOD_ID +".banner.bow.black", "Black Bow");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow.blue", "Blue Bow");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow.brown", "Brown Bow");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow.cyan", "Cyan Bow");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow.gray", "Gray Bow");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow.green", "Green Bow");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow.light_blue", "Light Blue Bow");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow.light_gray", "Light Gray Bow");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow.lime", "Lime Bow");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow.magenta", "Magenta Bow");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow.orange", "Orange Bow");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow.pink", "Pink Bow");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow.purple", "Purple Bow");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow.red", "Red Bow");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow.white", "White Bow");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow.yellow", "Yellow Bow");

            put("block."+ MiddleEarth.MOD_ID +".banner.bow_down.black", "Black Bow Down");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_down.blue", "Blue Bow Down");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_down.brown", "Brown Bow Down");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_down.cyan", "Cyan Bow Down");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_down.gray", "Gray Bow Down");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_down.green", "Green Bow Down");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_down.light_blue", "Light Blue Bow Down");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_down.light_gray", "Light Gray Bow Down");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_down.lime", "Lime Bow Down");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_down.magenta", "Magenta Bow Down");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_down.orange", "Orange Bow Down");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_down.pink", "Pink Bow Down");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_down.purple", "Purple Bow Down");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_down.red", "Red Bow Down");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_down.white", "White Bow Down");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_down.yellow", "Yellow Bow Down");

            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_bottom.black", "Black Bow Flat Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_bottom.blue", "Blue Bow Flat Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_bottom.brown", "Brown Bow Flat Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_bottom.cyan", "Cyan Bow Flat Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_bottom.gray", "Gray Bow Flat Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_bottom.green", "Green Bow Flat Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_bottom.light_blue", "Light Blue Bow Flat Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_bottom.light_gray", "Light Gray Bow Flat Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_bottom.lime", "Lime Bow Flat Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_bottom.magenta", "Magenta Bow Flat Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_bottom.orange", "Orange Bow Flat Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_bottom.pink", "Pink Bow Flat Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_bottom.purple", "Purple Bow Flat Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_bottom.red", "Red Bow Flat Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_bottom.white", "White Bow Flat Bottom");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_bottom.yellow", "Yellow Bow Flat Bottom");

            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_center.black", "Black Bow Flat Center");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_center.blue", "Blue Bow Flat Center");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_center.brown", "Brown Bow Flat Center");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_center.cyan", "Cyan Bow Flat Center");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_center.gray", "Gray Bow Flat Center");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_center.green", "Green Bow Flat Center");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_center.light_blue", "Light Blue Bow Flat Center");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_center.light_gray", "Light Gray Bow Flat Center");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_center.lime", "Lime Bow Flat Center");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_center.magenta", "Magenta Bow Flat Center");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_center.orange", "Orange Bow Flat Center");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_center.pink", "Pink Bow Flat Center");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_center.purple", "Purple Bow Flat Center");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_center.red", "Red Bow Flat Center");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_center.white", "White Bow Flat Center");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_center.yellow", "Yellow Bow Flat Center");

            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_top.black", "Black Bow Flat Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_top.blue", "Blue Bow Flat Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_top.brown", "Brown Bow Flat Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_top.cyan", "Cyan Bow Flat Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_top.gray", "Gray Bow Flat Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_top.green", "Green Bow Flat Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_top.light_blue", "Light Blue Bow Flat Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_top.light_gray", "Light Gray Bow Flat Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_top.lime", "Lime Bow Flat Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_top.magenta", "Magenta Bow Flat Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_top.orange", "Orange Bow Flat Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_top.pink", "Pink Bow Flat Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_top.purple", "Purple Bow Flat Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_top.red", "Red Bow Flat Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_top.white", "White Bow Flat Top");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_flat_top.yellow", "Yellow Bow Flat Top");

            put("block."+ MiddleEarth.MOD_ID +".banner.bow_long.black", "Black Bow Long");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_long.blue", "Blue Bow Long");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_long.brown", "Brown Bow Long");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_long.cyan", "Cyan Bow Long");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_long.gray", "Gray Bow Long");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_long.green", "Green Bow Long");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_long.light_blue", "Light Blue Bow Long");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_long.light_gray", "Light Gray Bow Long");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_long.lime", "Lime Bow Long");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_long.magenta", "Magenta Bow Long");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_long.orange", "Orange Bow Long");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_long.pink", "Pink Bow Long");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_long.purple", "Purple Bow Long");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_long.red", "Red Bow Long");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_long.white", "White Bow Long");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_long.yellow", "Yellow Bow Long");

            put("block."+ MiddleEarth.MOD_ID +".banner.bow_up.black", "Black Bow Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_up.blue", "Blue Bow Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_up.brown", "Brown Bow Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_up.cyan", "Cyan Bow Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_up.gray", "Gray Bow Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_up.green", "Green Bow Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_up.light_blue", "Light Blue Bow Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_up.light_gray", "Light Gray Bow Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_up.lime", "Lime Bow Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_up.magenta", "Magenta Bow Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_up.orange", "Orange Bow Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_up.pink", "Pink Bow Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_up.purple", "Purple Bow Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_up.red", "Red Bow Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_up.white", "White Bow Up");
            put("block."+ MiddleEarth.MOD_ID +".banner.bow_up.yellow", "Yellow Bow Up");

            put("block."+ MiddleEarth.MOD_ID +".banner.small_circle.black", "Black Small Circle");
            put("block."+ MiddleEarth.MOD_ID +".banner.small_circle.blue", "Blue Small Circle");
            put("block."+ MiddleEarth.MOD_ID +".banner.small_circle.brown", "Brown Small Circle");
            put("block."+ MiddleEarth.MOD_ID +".banner.small_circle.cyan", "Cyan Small Circle");
            put("block."+ MiddleEarth.MOD_ID +".banner.small_circle.gray", "Gray Small Circle");
            put("block."+ MiddleEarth.MOD_ID +".banner.small_circle.green", "Green Small Circle");
            put("block."+ MiddleEarth.MOD_ID +".banner.small_circle.light_blue", "Light Blue Small Circle");
            put("block."+ MiddleEarth.MOD_ID +".banner.small_circle.light_gray", "Light Gray Small Circle");
            put("block."+ MiddleEarth.MOD_ID +".banner.small_circle.lime", "Lime Small Circle");
            put("block."+ MiddleEarth.MOD_ID +".banner.small_circle.magenta", "Magenta Small Circle");
            put("block."+ MiddleEarth.MOD_ID +".banner.small_circle.orange", "Orange Small Circle");
            put("block."+ MiddleEarth.MOD_ID +".banner.small_circle.pink", "Pink Small Circle");
            put("block."+ MiddleEarth.MOD_ID +".banner.small_circle.purple", "Purple Small Circle");
            put("block."+ MiddleEarth.MOD_ID +".banner.small_circle.red", "Red Small Circle");
            put("block."+ MiddleEarth.MOD_ID +".banner.small_circle.white", "White Small Circle");
            put("block."+ MiddleEarth.MOD_ID +".banner.small_circle.yellow", "Yellow Small Circle");

            put("block."+ MiddleEarth.MOD_ID +".banner.snail.black", "Black Snail");
            put("block."+ MiddleEarth.MOD_ID +".banner.snail.blue", "Blue Snail");
            put("block."+ MiddleEarth.MOD_ID +".banner.snail.brown", "Brown Snail");
            put("block."+ MiddleEarth.MOD_ID +".banner.snail.cyan", "Cyan Snail");
            put("block."+ MiddleEarth.MOD_ID +".banner.snail.gray", "Gray Snail");
            put("block."+ MiddleEarth.MOD_ID +".banner.snail.green", "Green Snail");
            put("block."+ MiddleEarth.MOD_ID +".banner.snail.light_blue", "Light Blue Snail");
            put("block."+ MiddleEarth.MOD_ID +".banner.snail.light_gray", "Light Gray Snail");
            put("block."+ MiddleEarth.MOD_ID +".banner.snail.lime", "Lime Snail");
            put("block."+ MiddleEarth.MOD_ID +".banner.snail.magenta", "Magenta Snail");
            put("block."+ MiddleEarth.MOD_ID +".banner.snail.orange", "Orange Snail");
            put("block."+ MiddleEarth.MOD_ID +".banner.snail.pink", "Pink Snail");
            put("block."+ MiddleEarth.MOD_ID +".banner.snail.purple", "Purple Snail");
            put("block."+ MiddleEarth.MOD_ID +".banner.snail.red", "Red Snail");
            put("block."+ MiddleEarth.MOD_ID +".banner.snail.white", "White Snail");
            put("block."+ MiddleEarth.MOD_ID +".banner.snail.yellow", "Yellow Snail");

            put("sounds."+ MiddleEarth.MOD_ID +".bellows_push", "Bellows blowing");
            put("sounds."+ MiddleEarth.MOD_ID +".nazgul_fade", "Nazgûl fading");
            put("sounds."+ MiddleEarth.MOD_ID +".nazgul_scream", "Nazgûl screaming");
            put("sounds."+ MiddleEarth.MOD_ID +".pipe_refill", "Pipe refills");
            put("sounds."+ MiddleEarth.MOD_ID +".pipe_exhale", "Pipe exhales");
            put("sounds."+ MiddleEarth.MOD_ID +".pipe_ignite", "Pipe ignites");

            put("screen."+ MiddleEarth.MOD_ID +".forge", "Forge");
            put("container."+ MiddleEarth.MOD_ID +".artisan_table", "Artisan Table");
            put("container."+ MiddleEarth.MOD_ID +".small_crate", "Small Crate");
            put("container."+ MiddleEarth.MOD_ID +".thin_barrel", "Thin Barrel");
            put("screen."+ MiddleEarth.MOD_ID +".reinforced_chest", "Reinforced Chest");
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
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.hood", "Hood");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.cape", "Cape");
            put("screen."+ MiddleEarth.MOD_ID +".artisan_table.pipe", "Pipe");

            put("tooltip."+ MiddleEarth.MOD_ID +".shift", "Hold §3SHIFT§f to see information.");
            put("tooltip."+ MiddleEarth.MOD_ID +".alt", "Hold §3ALT§f to see custom parts information.");
            put("tooltip."+ MiddleEarth.MOD_ID +".weapon_type", "§eType, §f");
            put("tooltip."+ MiddleEarth.MOD_ID +".faction", "§6Faction: §f");
            put("tooltip."+ MiddleEarth.MOD_ID +".sub_faction", "§7Sub Faction: §f");
            put("tooltip."+ MiddleEarth.MOD_ID +".customizations", "§6Custom Parts: §f");
            put("tooltip."+ MiddleEarth.MOD_ID +".reach", "Reach: ");
            put("tooltip."+ MiddleEarth.MOD_ID +".dyed", "Dyed: ");
            put("tooltip."+ MiddleEarth.MOD_ID +".artisan", "Artisan: ");
            put("tooltip."+ MiddleEarth.MOD_ID +".blocks_range", " Blocks");
            put("tooltip."+ MiddleEarth.MOD_ID +".backstab", "§7+50% damage when backstabbing");
            put("tooltip."+ MiddleEarth.MOD_ID +".door_size", "Door size: ");

            put("tooltip."+ MiddleEarth.MOD_ID +".tier_clothing", "§fClothing");
            put("tooltip."+ MiddleEarth.MOD_ID +".tier_basic", "§fBasic Armor");
            put("tooltip."+ MiddleEarth.MOD_ID +".tier_light", "§aLight Armor");
            put("tooltip."+ MiddleEarth.MOD_ID +".tier_medium", "§9Medium Armor");
            put("tooltip."+ MiddleEarth.MOD_ID +".tier_sturdy", "§5Sturdy Armor");
            put("tooltip."+ MiddleEarth.MOD_ID +".tier_heavy", "§cHeavy Armor");
            put("tooltip."+ MiddleEarth.MOD_ID +".tier_mithril", "§bMithril");

            put("tooltip."+ MiddleEarth.MOD_ID +".artefact", "Artefact");
            put("tooltip."+ MiddleEarth.MOD_ID +".broken", "Broken");

            put("tooltip."+ MiddleEarth.MOD_ID +".sword", "Sword");
            put("tooltip."+ MiddleEarth.MOD_ID +".axe", "Axe");
            put("tooltip."+ MiddleEarth.MOD_ID +".dagger", "Dagger");
            put("tooltip."+ MiddleEarth.MOD_ID +".spear", "Spear");
            put("tooltip."+ MiddleEarth.MOD_ID +".longsword", "Longsword");

            put("tooltip."+ MiddleEarth.MOD_ID +".bow", "Bow");
            put("tooltip."+ MiddleEarth.MOD_ID +".longbow", "Longbow");
            put("tooltip."+ MiddleEarth.MOD_ID +".crossbow", "Crossbow");

            put("tooltip."+ MiddleEarth.MOD_ID +".light_shield", "§fLight Shield");
            put("tooltip."+ MiddleEarth.MOD_ID +".medium_shield", "§9Medium Shield");
            put("tooltip."+ MiddleEarth.MOD_ID +".heavy_shield", "§cHeavy Shield");

            put("tooltip."+ MiddleEarth.MOD_ID +".generic", "Generic");

            put("tooltip."+ MiddleEarth.MOD_ID +".dwarven", "Dwarven");
            put("tooltip."+ MiddleEarth.MOD_ID +".elven", "Elven");
            put("tooltip."+ MiddleEarth.MOD_ID +".orc", "Orc");

            put("tooltip."+ MiddleEarth.MOD_ID +".shire", "Shire");
            put("tooltip."+ MiddleEarth.MOD_ID +".gondor", "Gondor");
            put("tooltip."+ MiddleEarth.MOD_ID +".rohan", "Rohan");
            put("tooltip."+ MiddleEarth.MOD_ID +".longbeards", "Longbeards");
            put("tooltip."+ MiddleEarth.MOD_ID +".erebor", "Erebor");
            put("tooltip."+ MiddleEarth.MOD_ID +".lothlorien", "Lothlórien");
            put("tooltip."+ MiddleEarth.MOD_ID +".mordor", "Mordor");
            put("tooltip."+ MiddleEarth.MOD_ID +".mordor_black_numenoreans", "Mordor Black Númenóreans");
            put("tooltip."+ MiddleEarth.MOD_ID +".misty_mountains_goblins", "Misty Mountains Goblins");
            put("tooltip."+ MiddleEarth.MOD_ID +".gundabad", "Gundabad");
            put("tooltip."+ MiddleEarth.MOD_ID +".moria_goblins", "Moria Goblins");
            put("tooltip."+ MiddleEarth.MOD_ID +".isengard", "Isengard");
            put("tooltip."+ MiddleEarth.MOD_ID +".anduin", "Anduin Vale");
            put("tooltip."+ MiddleEarth.MOD_ID +".umbar", "Umbar");
            put("tooltip."+ MiddleEarth.MOD_ID +".dunland", "Dunland");
            put("tooltip."+ MiddleEarth.MOD_ID +".dale", "Dale");

            put("tooltip."+ MiddleEarth.MOD_ID +".color", "Color");
            put("tooltip."+ MiddleEarth.MOD_ID +".impaled_skulls", "Impaled Skulls");
            put("tooltip."+ MiddleEarth.MOD_ID +".skull", "Skull");
            put("tooltip."+ MiddleEarth.MOD_ID +".pouch", "Pouch");
            put("tooltip."+ MiddleEarth.MOD_ID +".feather", "Feather");

            put("tooltip."+ MiddleEarth.MOD_ID +".mount_armor_addon_top", "Top Armor Addon, Enabled");
            put("tooltip."+ MiddleEarth.MOD_ID +".mount_armor_addon_side", "Side Armor Addon, Enabled");

            put("tooltip."+ MiddleEarth.MOD_ID +".forge_output_mode0", "Not enough Metal.");
            put("tooltip."+ MiddleEarth.MOD_ID +".forge_output_mode1", "1 Nugget");
            put("tooltip."+ MiddleEarth.MOD_ID +".forge_output_mode2", "1 Ingot");
            put("tooltip."+ MiddleEarth.MOD_ID +".forge_output_mode3", "2 Ingots");
            put("tooltip."+ MiddleEarth.MOD_ID +".forge_output_mode4", "3 Ingots");

            put("tooltip."+ MiddleEarth.MOD_ID +".anvil_hammer", "Left click the anvil with a");
            put("tooltip."+ MiddleEarth.MOD_ID +".anvil_hammer_2", "Smithing Hammer to shape the metal.");

            put("tooltip."+ MiddleEarth.MOD_ID +".forge_mode_alloying", "Alloying Mode");
            put("tooltip."+ MiddleEarth.MOD_ID +".forge_mode_heating", "Heating Mode");
            put("tooltip."+ MiddleEarth.MOD_ID +".forge_mode_heating_switch", "To go into alloying mode, add bellows to the");
            put("tooltip."+ MiddleEarth.MOD_ID +".forge_mode_heating_switch_2", "sides of the forge containing a small vent.");
            put("tooltip."+ MiddleEarth.MOD_ID +".ingots_number", "Ingots");
            put("tooltip."+ MiddleEarth.MOD_ID +".nuggets_number", "Nuggets");

            //TODO try to automate
            put("tooltip."+ MiddleEarth.MOD_ID +".liquid_copper", "Liquid Copper");
            put("tooltip."+ MiddleEarth.MOD_ID +".liquid_tin", "Liquid Tin");
            put("tooltip."+ MiddleEarth.MOD_ID +".liquid_lead", "Liquid Lead");
            put("tooltip."+ MiddleEarth.MOD_ID +".liquid_silver", "Liquid Silver");
            put("tooltip."+ MiddleEarth.MOD_ID +".liquid_iron", "Liquid Iron");
            put("tooltip."+ MiddleEarth.MOD_ID +".liquid_gold", "Liquid Gold");
            put("tooltip."+ MiddleEarth.MOD_ID +".liquid_mithril", "Liquid Mithril");
            put("tooltip."+ MiddleEarth.MOD_ID +".liquid_netherite", "Liquid Netherite");

            put("tooltip."+ MiddleEarth.MOD_ID +".liquid_bronze", "Liquid Bronze");
            put("tooltip."+ MiddleEarth.MOD_ID +".liquid_crude", "Liquid Crude Metal");
            put("tooltip."+ MiddleEarth.MOD_ID +".liquid_steel", "Liquid Steel");
            put("tooltip."+ MiddleEarth.MOD_ID +".liquid_khazad_steel", "Liquid Khazâd-Steel");
            put("tooltip."+ MiddleEarth.MOD_ID +".liquid_edhel_steel", "Liquid Edhel-Steel");
            put("tooltip."+ MiddleEarth.MOD_ID +".liquid_burzum_steel", "Liquid Búrzum-Steel");

            put("tooltip."+ MiddleEarth.MOD_ID +".temp_1", "Warm");
            put("tooltip."+ MiddleEarth.MOD_ID +".temp_2", "Hot");
            put("tooltip."+ MiddleEarth.MOD_ID +".temp_3", "Very Hot");
            put("tooltip."+ MiddleEarth.MOD_ID +".temp_4", "Searing Hot");
            put("tooltip."+ MiddleEarth.MOD_ID +".temp_5", "Blazing Hot");

            //TODO try to automate
            put("trim_pattern."+ MiddleEarth.MOD_ID +".smithing_part", "Smithing Part");
            put("trim_material."+ MiddleEarth.MOD_ID +".jade", "Jade");
            put("trim_material."+ MiddleEarth.MOD_ID +".tin", "Tin");
            put("trim_material."+ MiddleEarth.MOD_ID +".lead", "Lead");
            put("trim_material."+ MiddleEarth.MOD_ID +".silver", "silver");
            put("trim_material."+ MiddleEarth.MOD_ID +".bronze", "Bronze");
            put("trim_material."+ MiddleEarth.MOD_ID +".steel", "Steel");
            put("trim_material."+ MiddleEarth.MOD_ID +".crude", "Crude");
            put("trim_material."+ MiddleEarth.MOD_ID +".burzum_steel", "Búrzum-Steel");
            put("trim_material."+ MiddleEarth.MOD_ID +".edhel_steel", "Edhel-Steel");
            put("trim_material."+ MiddleEarth.MOD_ID +".khazad_steel", "Khazâd-Steel");
            put("trim_material."+ MiddleEarth.MOD_ID +".morgul_steel", "Morgul Steel");
            put("trim_material."+ MiddleEarth.MOD_ID +".mithril", "Mithril");

            //TODO try to automate
            put("tooltip."+ MiddleEarth.MOD_ID +".hood", "Hood");
            put("tooltip."+ MiddleEarth.MOD_ID +".tall_hood", "Tall Hood");

            put("tooltip."+ MiddleEarth.MOD_ID +".black_fur_hood", "Black Fur Hood");
            put("tooltip."+ MiddleEarth.MOD_ID +".brown_fur_hood", "Brown Fur Hood");
            put("tooltip."+ MiddleEarth.MOD_ID +".gray_fur_hood", "Gray Fur Hood");
            put("tooltip."+ MiddleEarth.MOD_ID +".tan_fur_hood", "Tan Fur Hood");
            put("tooltip."+ MiddleEarth.MOD_ID +".white_fur_hood", "White Fur Hood");

            put("tooltip."+ MiddleEarth.MOD_ID +".cape", "Cape");

            put("tooltip."+ MiddleEarth.MOD_ID +".black_fur_cloak", "Black Fur Cloak");
            put("tooltip."+ MiddleEarth.MOD_ID +".brown_fur_cloak", "Brown Fur Cloak");
            put("tooltip."+ MiddleEarth.MOD_ID +".gray_fur_cloak", "Gray Fur Cloak");
            put("tooltip."+ MiddleEarth.MOD_ID +".tan_fur_cloak", "Tan Fur Cloak");
            put("tooltip."+ MiddleEarth.MOD_ID +".white_fur_cloak", "White Fur Cloak");

            put("tooltip."+ MiddleEarth.MOD_ID +".black_fur", "Black Fur");
            put("tooltip."+ MiddleEarth.MOD_ID +".brown_fur", "Brown Fur");
            put("tooltip."+ MiddleEarth.MOD_ID +".gray_fur", "Gray Fur");
            put("tooltip."+ MiddleEarth.MOD_ID +".tan_fur", "Tan Fur");
            put("tooltip."+ MiddleEarth.MOD_ID +".white_fur", "White Fur");

            put("tooltip."+ MiddleEarth.MOD_ID +".cloak", "Cloak");

            put("tooltip."+ MiddleEarth.MOD_ID +".surcoat", "Surcoat");

            put("tooltip."+ MiddleEarth.MOD_ID +".wanderer_robes", "Wanderer Robes");

            put("tooltip."+ MiddleEarth.MOD_ID +".gondorian_captain_cape", "Gondorian Captain Cape");
            put("tooltip."+ MiddleEarth.MOD_ID +".gondorian_hero_cape", "Gondorian Hero Cape");
            put("tooltip."+ MiddleEarth.MOD_ID +".gondorian_kings_guard_cape", "Gondorian King's Guard Cape");
            put("tooltip."+ MiddleEarth.MOD_ID +".gondorian_citadel_guard_hood", "Gondorian Citadel Guard Hood");
            put("tooltip."+ MiddleEarth.MOD_ID +".gondorian_citadel_guard_cape", "Gondorian Citadel Guard Cape");
            put("tooltip."+ MiddleEarth.MOD_ID +".gondorian_fountain_guard_cape", "Gondorian Fountain Guard Cape");

            put("tooltip."+ MiddleEarth.MOD_ID +".rohirric_cape", "Rohirric Cape");
            put("tooltip."+ MiddleEarth.MOD_ID +".rohirric_royal_guard_cape", "Rohirric Royal Guard Cape");
            put("tooltip."+ MiddleEarth.MOD_ID +".eorling_marshal_cape", "Eorling Marshal Cape");
            put("tooltip."+ MiddleEarth.MOD_ID +".horse_lord_cape", "Horse Lord Cape");

            put("tooltip."+ MiddleEarth.MOD_ID +".barding_surcoat", "Barding Surcoat");
            put("tooltip."+ MiddleEarth.MOD_ID +".dalish_heyday_cloak", "Dalish Heyday Cloak");
            put("tooltip."+ MiddleEarth.MOD_ID +".barding_sergeant_cape", "Barding Sergeant Cape");

            put("tooltip."+ MiddleEarth.MOD_ID +".erebor_cape", "Erebor Cape");
            put("tooltip."+ MiddleEarth.MOD_ID +".ravenhill_sentinel_cape", "Ravenhill Sentinel Cape");

            put("tooltip."+ MiddleEarth.MOD_ID +".lorien_marchwarden_hood", "Lórien Marchwarden Hood");

            put("tooltip."+ MiddleEarth.MOD_ID +".lorien_marchwarden_cape", "Lórien Marchwarden Cape");
            put("tooltip."+ MiddleEarth.MOD_ID +".galadhrim_cape", "Galadhrim Cape");
            put("tooltip."+ MiddleEarth.MOD_ID +".galadhrim_hood", "Galadhrim Hood");
            put("tooltip."+ MiddleEarth.MOD_ID +".galadhrim_lord_surcoat", "Galadhrim Lord Surcoat");

            put("tooltip."+ MiddleEarth.MOD_ID +".orcish_cape", "Orcish Cape");
            put("tooltip."+ MiddleEarth.MOD_ID +".orcish_long_cape", "Orcish Long Cape");
            put("tooltip."+ MiddleEarth.MOD_ID +".orcish_shoulders", "Orcish Shoulders");

            put("tooltip."+ MiddleEarth.MOD_ID +".orcish_black_fur_surcoat_with_bone", "Orcish Black Fur Surcoat With Bone");
            put("tooltip."+ MiddleEarth.MOD_ID +".orcish_brown_fur_surcoat_with_bone", "Orcish Brown Fur Surcoat With Bone");
            put("tooltip."+ MiddleEarth.MOD_ID +".orcish_gray_fur_surcoat_with_bone", "Orcish Gray Fur Surcoat With Bone");
            put("tooltip."+ MiddleEarth.MOD_ID +".orcish_tan_fur_surcoat_with_bone", "Orcish Tan Fur Surcoat With Bone");
            put("tooltip."+ MiddleEarth.MOD_ID +".orcish_white_fur_surcoat_with_bone", "Orcish White Fur Surcoat With Bone");

            put("tooltip."+ MiddleEarth.MOD_ID +".nazgul_hood", "Nazgûl Hood");

            put("tooltip."+ MiddleEarth.MOD_ID +".mordor_black_numenorean_cape", "Mordor Black Númenórean Cape");
            put("tooltip."+ MiddleEarth.MOD_ID +".nazgul_robes", "Nazgûl Robes");

            put("tooltip."+ MiddleEarth.MOD_ID +".orthanc_guard_cape", "Orthanc Guard Cape");

            //TODO try to automate
            put("disposition."+ MiddleEarth.MOD_ID +".good", "Good");
            put("disposition."+ MiddleEarth.MOD_ID +".neutral", "Neutral");
            put("disposition."+ MiddleEarth.MOD_ID +".evil", "Evil");

            put("faction."+ MiddleEarth.MOD_ID +".gondor", "Gondor");
            put("faction."+ MiddleEarth.MOD_ID +".rohan", "Rohan");
            put("faction."+ MiddleEarth.MOD_ID +".dale", "Dale");
            put("faction."+ MiddleEarth.MOD_ID +".longbeards", "Longbeards");
            put("faction."+ MiddleEarth.MOD_ID +".longbeards.erebor", "Erebor");
            put("faction."+ MiddleEarth.MOD_ID +".lothlorien", "Lothlórien");
            put("faction."+ MiddleEarth.MOD_ID +".mordor", "Mordor");
            put("faction."+ MiddleEarth.MOD_ID +".misty_mountains_goblins", "Misty Mountains Goblins");
            put("faction."+ MiddleEarth.MOD_ID +".isengard", "Isengard");
            put("faction."+ MiddleEarth.MOD_ID +".shire", "Shire");
            put("faction."+ MiddleEarth.MOD_ID +".bandit", "Bandit");

            put("faction."+ MiddleEarth.MOD_ID +".example.fallback", "Example...");
            put("faction."+ MiddleEarth.MOD_ID +".misty_mountains_goblins.fallback", "Misty Mts. Goblins");

            put("spawn."+ MiddleEarth.MOD_ID +".coordinates_base.dynamic", "[x,z] ");
            put("spawn."+ MiddleEarth.MOD_ID +".coordinates_base_values.dynamic", "%s, %s");
            put("spawn."+ MiddleEarth.MOD_ID +".coordinates_base.custom", "[x,y,z] ");
            put("spawn."+ MiddleEarth.MOD_ID +".coordinates_base_values.custom", "%s, %s, %s");

            put("spawn."+ MiddleEarth.MOD_ID +".none", "None");

            put("spawn."+ MiddleEarth.MOD_ID +".gondor.minas_tirith", "Minas Tirith");
            put("spawn."+ MiddleEarth.MOD_ID +".gondor.anorien", "Anorien");
            put("spawn."+ MiddleEarth.MOD_ID +".gondor.ithilien", "Ithilien");
            put("spawn."+ MiddleEarth.MOD_ID +".gondor.lossarnach", "Lossarnach");
            put("spawn."+ MiddleEarth.MOD_ID +".gondor.pelargir", "Pelargir");
            put("spawn."+ MiddleEarth.MOD_ID +".gondor.lebennin", "Lebennin");
            put("spawn."+ MiddleEarth.MOD_ID +".gondor.ringlo_vale", "Ringlo Vale");
            put("spawn."+ MiddleEarth.MOD_ID +".gondor.lamedon", "Lamedon");
            put("spawn."+ MiddleEarth.MOD_ID +".gondor.dol_amroth", "Dol Amroth");
            put("spawn."+ MiddleEarth.MOD_ID +".gondor.morthond_vale", "Morthond Vale");
            put("spawn."+ MiddleEarth.MOD_ID +".rohan.edoras", "Edoras");
            put("spawn."+ MiddleEarth.MOD_ID +".rohan.helms_deep", "Helm's Deep");
            put("spawn."+ MiddleEarth.MOD_ID +".rohan.westemnet", "Westemnet");
            put("spawn."+ MiddleEarth.MOD_ID +".rohan.aldburg", "Aldburg");
            put("spawn."+ MiddleEarth.MOD_ID +".rohan.eastemnet", "Eastemnet");
            put("spawn."+ MiddleEarth.MOD_ID +".rohan.the_wold", "The Wold");
            put("spawn."+ MiddleEarth.MOD_ID +".dale.capital", "Dale Capital");
            put("spawn."+ MiddleEarth.MOD_ID +".dale.esgaroth", "Esgaroth");
            put("spawn."+ MiddleEarth.MOD_ID +".longbeards.erebor.ravenhill", "Ravenhill");
            put("spawn."+ MiddleEarth.MOD_ID +".longbeards.erebor.iron_hills", "Iron Hills");
            put("spawn."+ MiddleEarth.MOD_ID +".longbeards.erebor.iron_hills_spring", "Iron Hills Spring");
            put("spawn."+ MiddleEarth.MOD_ID +".lothlorien.cerin_amroth", "Cerin Amroth");
            put("spawn."+ MiddleEarth.MOD_ID +".mordor.gorgoroth", "Gorgoroth");
            put("spawn."+ MiddleEarth.MOD_ID +".mordor.black_gates", "Black Gates");
            put("spawn."+ MiddleEarth.MOD_ID +".mordor.minas_morgul", "Minas Morgul");
            put("spawn."+ MiddleEarth.MOD_ID +".mordor.nurn", "Nurn");
            put("spawn."+ MiddleEarth.MOD_ID +".mordor.dol_guldur", "Dol Guldur");
            put("spawn."+ MiddleEarth.MOD_ID +".misty_mountains_goblins.gundabad", "Gundabad");
            put("spawn."+ MiddleEarth.MOD_ID +".misty_mountains_goblins.grey_mountains", "Grey Mountains");
            put("spawn."+ MiddleEarth.MOD_ID +".misty_mountains_goblins.goblin_town", "Goblin Town");
            put("spawn."+ MiddleEarth.MOD_ID +".misty_mountains_goblins.moria", "Moria");
            put("spawn."+ MiddleEarth.MOD_ID +".isengard.orthanc", "Orthanc");
            put("spawn."+ MiddleEarth.MOD_ID +".shire.hobbiton", "Hobbitton");
            put("spawn."+ MiddleEarth.MOD_ID +".shire.willowbottom", "Willowbottom");

            put("race."+ MiddleEarth.MOD_ID +".dwarf", "Dwarf");
            put("race."+ MiddleEarth.MOD_ID +".elf", "Elf");
            put("race."+ MiddleEarth.MOD_ID +".human", "Human");
            put("race."+ MiddleEarth.MOD_ID +".hobbit", "Hobbit");
            put("race."+ MiddleEarth.MOD_ID +".orc", "Orc");
            put("race."+ MiddleEarth.MOD_ID +".uruk", "Uruk");

            put("race_tooltip."+ MiddleEarth.MOD_ID +".attribute_header", "Attributes , ");

            put("tooltip."+ MiddleEarth.MOD_ID +".arkenstone_lore_0", "The fairest of gems found beneath,");
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

            put("tooltip."+ MiddleEarth.MOD_ID +".aeglos_lore_0", "Called \"Snow-Point\"); this legendary spear was carried into battle by");
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
            put("description."+ MiddleEarth.MOD_ID +".misty_mountains_goblins.description_0", "The Goblins of the Misty Mountains are a warlike race of Orcs inhabiting the caves and tunnels beneath the mountains. Once scattered, they now rebuild their strength, preying on travelers and allying with Sauron’s forces.");
            put("description."+ MiddleEarth.MOD_ID +".shire.description_0", "The Shire is a peaceful land inhabited by Hobbits, located in the northwest of Middle-earth. Known for its pastoral beauty and the simple, unadventurous lives of its people. It remains largely untouched by the turmoil of the outside world.");

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

            put("command."+ MiddleEarth.MOD_ID +".cape.success", "Cape successfully applied, ");
            put("command."+ MiddleEarth.MOD_ID +".cape.fail", "Failed to apply cape.");

            put("command."+ MiddleEarth.MOD_ID +".hood.success", "Hood successfully applied, ");
            put("command."+ MiddleEarth.MOD_ID +".hood.fail", "Failed to apply hood.");

            put("command."+ MiddleEarth.MOD_ID +".cape.hand_empty", "Hand Empty. Take a Middle-earth Mod chestplate in your main hand to apply a cape.");
            put("command."+ MiddleEarth.MOD_ID +".hood.hand_empty", "Hand Empty. Take a Middle-earth Mod helmet in your main hand to apply a hood.");

            put("command."+ MiddleEarth.MOD_ID +".cape.wrong_item", "Wrong Item type. Take a Middle-earth Mod chestplate in your main hand to apply a cape.");
            put("command."+ MiddleEarth.MOD_ID +".hood.wrong_item", "Wrong Item type. Take a Middle-earth Mod helmet in your main hand to apply a hood.");

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

            put("screen."+ MiddleEarth.MOD_ID +".faction_selection_screen", "Faction Selection");
            put("screen."+ MiddleEarth.MOD_ID +".button.faction_randomizer", "Randomize Faction");
            put("screen."+ MiddleEarth.MOD_ID +".button.spawn_randomizer", "Randomize Spawn");
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

            put("painting."+ MiddleEarth.MOD_ID +".dwarf_portrait.author", "Boenndal");
            put("painting."+ MiddleEarth.MOD_ID +".dwarf_portrait.title", "Dwarf Portrait");

            put("painting."+ MiddleEarth.MOD_ID +".elf_portrait.author", "Scosher");
            put("painting."+ MiddleEarth.MOD_ID +".elf_portrait.title", "Elf Portrait");

            put("painting."+ MiddleEarth.MOD_ID +".hobbit_portrait.author", "Boenndal");
            put("painting."+ MiddleEarth.MOD_ID +".hobbit_portrait.title", "Hobbit Portrait");

            put("painting."+ MiddleEarth.MOD_ID +".human_portrait.author", "Boenndal");
            put("painting."+ MiddleEarth.MOD_ID +".human_portrait.title", "Human Portrait");

            put("painting."+ MiddleEarth.MOD_ID +".orc_portrait.author", "Boenndal");
            put("painting."+ MiddleEarth.MOD_ID +".orc_portrait.title", "Orc Portrait");

            put("painting."+ MiddleEarth.MOD_ID +".note_board.author", "Boenndal");
            put("painting."+ MiddleEarth.MOD_ID +".note_board.title", "Note Board");

            put("painting."+ MiddleEarth.MOD_ID +".pan_board.author", "Boenndal");
            put("painting."+ MiddleEarth.MOD_ID +".pan_board.title", "Pan Board");

            put("painting."+ MiddleEarth.MOD_ID +".rohirric_tapestry.author", "Boenndal");
            put("painting."+ MiddleEarth.MOD_ID +".rohirric_tapestry.title", "Rohirric Tapestry");

            put("painting."+ MiddleEarth.MOD_ID +".small_mirror.author", "Boenndal");
            put("painting."+ MiddleEarth.MOD_ID +".small_mirror.title", "Small Mirror");

            put("painting."+ MiddleEarth.MOD_ID +".tall_mirror.author", "Boenndal");
            put("painting."+ MiddleEarth.MOD_ID +".tall_mirror.title", "Tall Mirror");

            put("painting."+ MiddleEarth.MOD_ID +".prancing_pony.author", "Boenndal");
            put("painting."+ MiddleEarth.MOD_ID +".prancing_pony.title", "Prancing Pony");

            put("painting."+ MiddleEarth.MOD_ID +".tool_board.author", "Boenndal");
            put("painting."+ MiddleEarth.MOD_ID +".tool_board.title", "Tool Board");
        }
    };
}
