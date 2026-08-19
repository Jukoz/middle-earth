package net.sevenstars.middleearth.datageneration.providers.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.registration.OreRockSets;
import net.sevenstars.middleearth.block.registration.StoneBlockSets;
import net.sevenstars.middleearth.datageneration.content.models.HotMetalsModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleDyeableItemModel;
import net.sevenstars.middleearth.datageneration.content.tags.*;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.FoodItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.utils.ItemTagsME;
import net.sevenstars.of_beasts_and_wild_things.item.ItemsWT;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends ItemTagsProvider {

    public ItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture,
                           CompletableFuture<TagsProvider.TagLookup<net.minecraft.world.level.block.Block>> blockTags,
                           ExistingFileHelper existingFileHelper) {
        super(output, registriesFuture, blockTags, MiddleEarth.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        var bones = tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")));
        var feathers = tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "feathers")));
        var cloaks = tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "cloaks")));
        var warg_food = tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "warg_food")));
        var warg_armor = tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "warg_armor")));
        var broadhoof_goat_armor = tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "broadhoof_goat_armor")));
        var great_horn_armor = tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "great_horn_armor")));
        var dyeable = tag(TagKey.create(Registries.ITEM, ResourceLocation.parse("dyeable")));
        var chains = tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "chains")));
        var troll_weapons = tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "troll_weapons")));
        var troll_food = tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "troll_food")));

        var characterHideHair = tag(ItemTagsME.CHARACTER_HELMET_HIDE_HAIR);
        var characterHideBeard = tag(ItemTagsME.CHARACTER_HELMET_HIDE_BEARD);
        var characterShowEars = tag(ItemTagsME.CHARACTER_HELMET_SHOW_EARS);

        TagKey<Item> iron_ores = TagKey.create(Registries.ITEM, ResourceLocation.parse("iron_ores"));
        TagKey<Item> gold_ores = TagKey.create(Registries.ITEM, ResourceLocation.parse("gold_ores"));
        TagKey<Item> copper_ores = TagKey.create(Registries.ITEM, ResourceLocation.parse("copper_ores"));
        TagKey<Item> coal_ores = TagKey.create(Registries.ITEM, ResourceLocation.parse("coal_ores"));

        TagKey<Item> saplings = TagKey.create(Registries.ITEM, ResourceLocation.parse("saplings"));
        TagKey<Item> wooden_slabs = TagKey.create(Registries.ITEM, ResourceLocation.parse("wooden_slabs"));
        TagKey<Item> wooden_vertical_slabs = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "wooden_vertical_slabs"));
        TagKey<Item> wooden_fences = TagKey.create(Registries.ITEM, ResourceLocation.parse( "wooden_fences"));
        TagKey<Item> logs_that_burn = TagKey.create(Registries.ITEM, ResourceLocation.parse("logs_that_burn"));
        TagKey<Item> stone_crafting_materials = TagKey.create(Registries.ITEM, ResourceLocation.parse("stone_crafting_materials"));
        TagKey<Item> stone_tool_materials = TagKey.create(Registries.ITEM, ResourceLocation.parse("stone_tool_materials"));
        TagKey<Item> leaves = TagKey.create(Registries.ITEM, ResourceLocation.parse("leaves"));

        TagKey<Item> ingot_shaping = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "ingot_shaping"));
        TagKey<Item> nugget_shaping = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "nugget_shaping"));

        TagKey<Item> tin_ores = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "tin_ores"));
        TagKey<Item> lead_ores = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "lead_ores"));
        TagKey<Item> silver_ores = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "silver_ores"));
        TagKey<Item> mithril_ores = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "mithril_ores"));
        TagKey<Item> shingles = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "shingles"));

        TagKey<Item> mod_stripped_logs = TagKey.create(Registries.ITEM, MiddleEarth.of("mod_stripped_logs"));
        TagKey<Item> stripped_logs = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "stripped_logs"));
        tag(TagKey.create(Registries.ITEM, MiddleEarth.of("mod_planks"))).add(Planks.getItemPlanksWithoutVanilla().toArray(new Item[0]));

        tag(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks"))).add(Planks.getItemPlanks().toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.parse("wooden_slabs"))).add(Planks.getItemPlanksSlabs().toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.parse("logs"))).add(Logs.getItemLogs().toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.parse("logs_that_burn"))).add(Logs.getItemLogs().toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.parse("leaves"))).add(LeavesSets.getItemLeaves().toArray(new Item[0]));

        tag(TagKey.create(Registries.ITEM, ResourceLocation.parse("axes"))).add(WeaponEnchants.axes.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, MiddleEarth.of("daggers"))).add(WeaponEnchants.daggers.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.parse("swords"))).add(WeaponEnchants.swords.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, MiddleEarth.of("spears"))).add(WeaponItemsME.spears.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "enchantable/weapon"))).add(WeaponEnchants.weapons.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "enchantable/sword"))).add(WeaponEnchants.sharpWeapons.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "enchantable/bow"))).add(Bows.bows.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/ranged_weapon")))
                .add(Bows.bows.toArray(new Item[0]))
                .add(Crossbows.crossbows.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/bow"))).add(Bows.bows.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/bows"))).add(Bows.bows.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/crossbow"))).add(Crossbows.crossbows.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "enchantable/crossbow"))).add(Crossbows.crossbows.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "enchantable/durability"))).add(ArmorTags.armors.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "enchantable/durability"))).add(WeaponEnchants.weapons.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "enchantable/durability"))).add(ToolItemsME.smithingHammers.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "enchantable/durability"))).add(Crossbows.crossbows.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "enchantable/vanishing"))).add(ArmorTags.armors.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "enchantable/vanishing"))).add(WeaponEnchants.weapons.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "enchantable/vanishing"))).add(ToolItemsME.smithingHammers.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/shields"))).add(WeaponItemsME.shields.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/shield"))).add(WeaponItemsME.shields.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "enchantable/equippable"))).add(ArmorTags.armors.toArray(new Item[0]));

        ArmorTags.basicArmors.addAll(List.of(new Item[]{Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS}));
        ArmorTags.mediumArmors.addAll(List.of(new Item[]{Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS,
                Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS}));
        ArmorTags.sturdyArmors.addAll(List.of(new Item[]{Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS, Items.TURTLE_HELMET}));
        ArmorTags.heavyArmors.addAll(List.of(new Item[]{Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS,
                Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS}));

        ArmorTags.incompleteArmors.addAll(List.of(Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS,
                Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS,
                Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS,
                Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS, Items.TURTLE_HELMET));

        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "enchantable/armor"))).add(ArmorTags.armors.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath( "minecraft", "enchantable/head_armor"))).add(ArmorTags.headArmors.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath( "minecraft", "enchantable/chest_armor"))).add(ArmorTags.chestArmors.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath( "minecraft", "enchantable/leg_armor"))).add(ArmorTags.legArmors.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath( "minecraft", "enchantable/foot_armor"))).add(ArmorTags.footArmors.toArray(new Item[0]));

        tag(TagKey.create(Registries.ITEM, MiddleEarth.ofPath( "enchantable", "basic_armor"))).add(ArmorTags.basicArmors.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, MiddleEarth.ofPath( "enchantable", "light_armor"))).add(ArmorTags.lightArmors.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, MiddleEarth.ofPath( "enchantable", "medium_armor"))).add(ArmorTags.mediumArmors.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, MiddleEarth.ofPath( "enchantable", "sturdy_armor"))).add(ArmorTags.sturdyArmors.toArray(new Item[0]));

        tag(TagKey.create(Registries.ITEM, MiddleEarth.ofPath( "enchantable", "heavy_armor"))).add(ArmorTags.heavyArmors.toArray(new Item[0]));
        tag(TagKey.create(Registries.ITEM, MiddleEarth.ofPath( "enchantable", "incomplete_armors"))).add(ArmorTags.incompleteArmors.toArray(new Item[0]));

        ArrayList<Item> upToArmor = (ArrayList<Item>) ArmorTags.basicArmors;
        upToArmor.addAll(ArmorTags.lightArmors);

        ArrayList<Item> lightChest = new ArrayList<>();
        ArrayList<Item> lightLegging = new ArrayList<>();
        for(Item chestItem : EquipmentItemsME.armorPiecesListChestplates) {
            if(upToArmor.contains(chestItem)) {
                lightChest.add(chestItem);
            }
        }
        for(Item legItem : EquipmentItemsME.armorPiecesListLeggings) {
            if(upToArmor.contains(legItem)) {
                lightLegging.add(legItem);
            }
        }
        tag(TagKey.create(Registries.ITEM, MiddleEarth.ofPath( "enchantable", "light_chest"))).add(lightChest.toArray(Item[]::new));
        tag(TagKey.create(Registries.ITEM, MiddleEarth.ofPath( "enchantable", "light_leg"))).add(lightLegging.toArray(Item[]::new));

        upToArmor.addAll(ArmorTags.mediumArmors);

        ArrayList<Item> mediumBoots = new ArrayList<>();
        for(Item bootItem : EquipmentItemsME.armorPiecesListBoots) {
            if(upToArmor.contains(bootItem)) {
                mediumBoots.add(bootItem);
            }
        }
        tag(TagKey.create(Registries.ITEM, MiddleEarth.ofPath( "enchantable", "medium_foot"))).add(mediumBoots.toArray(Item[]::new));

        warg_food.add(Items.RABBIT);
        warg_food.add(Items.CHICKEN);
        warg_food.add(Items.PORKCHOP);
        warg_food.add(Items.BEEF);
        warg_food.add(Items.MUTTON);
        warg_food.add(FoodItemsME.RAW_HORSE);
        warg_food.add(ItemsWT.RAW_VENISON);

        warg_armor.add(EquipmentItemsME.WARG_MORDOR_PLATE_ARMOR);
        warg_armor.add(EquipmentItemsME.WARG_GUNDABAD_PLATE_ARMOR);
        warg_armor.add(EquipmentItemsME.WARG_ISENGARD_PLATE_ARMOR);
        warg_armor.add(EquipmentItemsME.WARG_MORDOR_MAIL_ARMOR);
        warg_armor.add(EquipmentItemsME.WARG_LEATHER_ARMOR);
        warg_armor.add(EquipmentItemsME.WARG_REINFORCED_LEATHER_ARMOR);

        broadhoof_goat_armor.add(EquipmentItemsME.BROADHOOF_GOAT_PLATE_ARMOR);
        broadhoof_goat_armor.add(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR);
        broadhoof_goat_armor.add(EquipmentItemsME.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR);

        great_horn_armor.add(EquipmentItemsME.GREAT_HORN_LIGHT_ARMOR);
        great_horn_armor.add(EquipmentItemsME.GREAT_HORN_LIGHT_GRAY_ARMOR);
        great_horn_armor.add(EquipmentItemsME.GREAT_HORN_LIGHT_GREEN_ARMOR);
        great_horn_armor.add(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR);
        great_horn_armor.add(EquipmentItemsME.GREAT_HORN_ORNAMENTED_PLATE_ARMOR);
        great_horn_armor.add(EquipmentItemsME.GREAT_HORN_GREEN_PLATE_ARMOR);

        bones.add(Items.BONE);
        bones.add(ResourceItemsME.DIRTY_BONE);
        bones.add(ResourceItemsME.FANG);

        feathers.add(ResourceItemsME.SWAN_FEATHER);
        feathers.add(Items.FEATHER);

        EquipmentItemsME.helmetAttachments.forEach(cloaks::add);
        EquipmentItemsME.backAttachments.forEach(cloaks::add);

        dyeable.add(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR);
        dyeable.add(EquipmentItemsME.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR);

        dyeable.add(EquipmentItemsME.WARG_LEATHER_ARMOR);
        dyeable.add(EquipmentItemsME.WARG_REINFORCED_LEATHER_ARMOR);

        dyeable.add(EquipmentItemsME.GREAT_HORN_LIGHT_ARMOR);
        dyeable.add(EquipmentItemsME.GREAT_HORN_LIGHT_GRAY_ARMOR);
        dyeable.add(EquipmentItemsME.GREAT_HORN_LIGHT_GREEN_ARMOR);

        chains.add(Items.CHAIN);
        chains.add(ModDecorativeBlocks.BRONZE_CHAIN.asItem());
        chains.add(ModDecorativeBlocks.BRONZE_BROAD_CHAIN.asItem());
        chains.add(ModDecorativeBlocks.CRUDE_CHAIN.asItem());
        chains.add(ModDecorativeBlocks.CRUDE_BROAD_CHAIN.asItem());
        chains.add(ModDecorativeBlocks.SPIKY_CHAIN.asItem());

        troll_weapons.add(WeaponItemsME.TROLL_MACE);
        troll_weapons.add(WeaponItemsME.MACE_OF_SAURON);

        troll_food.add(FoodItemsME.RAW_HORSE);
        troll_food.add(FoodItemsME.COOKED_HORSE);
        troll_food.add(ItemsWT.RAW_VENISON);
        troll_food.add(ItemsWT.COOKED_VENISON);
        troll_food.add(FoodItemsME.COOKED_MEAT_SKEWER);
        troll_food.add(Items.PORKCHOP);
        troll_food.add(Items.COOKED_PORKCHOP);
        troll_food.add(Items.MUTTON);
        troll_food.add(Items.COOKED_MUTTON);
        troll_food.add(Items.BEEF);
        troll_food.add(Items.COOKED_BEEF);
        troll_food.add(Items.CHICKEN);
        troll_food.add(Items.COOKED_CHICKEN);
        troll_food.add(Items.ROTTEN_FLESH);
        troll_food.add(Items.MUSHROOM_STEW);
        troll_food.add(Items.BROWN_MUSHROOM);
        troll_food.add(Items.RED_MUSHROOM);

        // SHOW Ears
        characterShowEars.add(EquipmentItemsME.LORIEN_DIADEM);
        characterShowEars.add(EquipmentItemsME.KETTLE_HAT);
        characterShowEars.add(EquipmentItemsME.MORDOR_KETTLE_HAT);
        characterShowEars.add(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT);
        characterShowEars.add(EquipmentItemsME.DOL_GULDUR_JAILER_COLLAR);
        characterShowEars.add(EquipmentItemsME.WEATHERED_DOL_GULDUR_JAILER_COLLAR);

        characterShowEars.add(EquipmentItemsME.MORIA_GOBLIN_MANDIBLE_HELMET);
        characterShowEars.add(EquipmentItemsME.MORIA_GOBLIN_SCREECHER_HELMET);
        characterShowEars.add(EquipmentItemsME.MORIA_GOBLIN_CAPTAIN_HELMET);

        // HIDE Hairs
        characterHideHair.add(EquipmentItemsME.MAIL_COIF);
        characterHideHair.add(EquipmentItemsME.CLOSED_MAIL_COIF);

        characterHideHair.add(EquipmentItemsME.HOOD);
        characterHideHair.add(EquipmentItemsME.TALL_HOOD);
        characterHideHair.add(EquipmentItemsME.TAN_FUR_HOOD);
        characterHideHair.add(EquipmentItemsME.BLACK_FUR_HOOD);
        characterHideHair.add(EquipmentItemsME.GRAY_FUR_HOOD);
        characterHideHair.add(EquipmentItemsME.BROWN_FUR_HOOD);
        characterHideHair.add(EquipmentItemsME.WHITE_FUR_HOOD);

        characterHideHair.add(EquipmentItemsME.ELVEN_MAIL_COIF);
        characterHideHair.add(EquipmentItemsME.LORIEN_MAIL_COIF_DIADEM);

        characterHideHair.add(EquipmentItemsME.DWARVEN_MAIL_COIF);
        characterHideHair.add(EquipmentItemsME.EREBOR_MAIL_COIF);
        characterHideHair.add(EquipmentItemsME.EREBOR_GILDED_MAIL_COIF);

        characterHideHair.add(EquipmentItemsME.ORCISH_MAIL_COIF);
        characterHideHair.add(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF);

        // HIDE Beards
        characterHideBeard.add(EquipmentItemsME.CLOSED_MAIL_COIF);
        characterHideBeard.add(EquipmentItemsME.KETTLE_HAT_WITH_CLOSED_COIF);

        for (OreRockSets.OreRockSet set : OreRockSets.sets) {
            if(set.coal_ore() != null) {
                tag(coal_ores)
                        .add(set.coal_ore().asItem());
            }
            if(set.copper_ore() != null) {
                tag(copper_ores)
                        .add(set.copper_ore().asItem());
            }
            if(set.tin_ore() != null) {
                tag(tin_ores)
                        .add(set.tin_ore().asItem());
            }
            if(set.lead_ore() != null) {
                tag(lead_ores)
                        .add(set.lead_ore().asItem());
            }
            if(set.silver_ore() != null) {
                tag(silver_ores)
                        .add(set.silver_ore().asItem());
            }
            if(set.gold_ore() != null) {
                tag(gold_ores)
                        .add(set.gold_ore().asItem());
            }
            if(set.iron_ore() != null) {
                tag(iron_ores)
                        .add(set.iron_ore().asItem());
            }
            if(set.mithril_ore() != null) {
                tag(mithril_ores)
                        .add(set.mithril_ore().asItem());
            }
        }

        SimpleDyeableItemModel.items.forEach(dyeable::add);

        WoodenSlabs.woodenSlabs.forEach(block -> {
            tag(wooden_slabs).add(block.asItem());
        });

        WoodenVerticalSlabs.woodenVericalSlabs.forEach(block -> {
            tag(wooden_vertical_slabs).add(block.asItem());
        });

        Fences.fences.forEach(block -> {
            tag(wooden_fences).add(block.asItem());
        });

        ModdedStrippedLogs.strippedLogs.forEach(block -> {
            tag(mod_stripped_logs).add(block.asItem());
        });

        ModdedStrippedLogs.strippedLogs.forEach(block -> {
            tag(stripped_logs).add(block.asItem());
        });

        Shingles.shingles.forEach(block -> {
            tag(shingles).add(block.asItem());
        });

        Saplings.saplings.forEach(sapling -> {
            tag(saplings).add(sapling.asItem());
        });

        LogsThatBurn.logsThatBurn.forEach(log -> {
            tag(logs_that_burn).add(log.asItem());
        });

        HotMetalsModel.ingots.forEach(ingot -> {
            tag(ingot_shaping).add(ingot);
        });

        HotMetalsModel.nuggets.forEach(nugget -> {
            tag(nugget_shaping).add(nugget);
        });

        StoneBlockSets.stoneSetsList.forEach(stone -> {
            if(stone.cobblestoneBlocks != null) {
                tag(stone_crafting_materials).add(stone.cobblestoneBlocks.base().asItem());
                tag(stone_tool_materials).add(stone.cobblestoneBlocks.base().asItem());
            }
        });
        tag(stone_crafting_materials).add(Blocks.BLACKSTONE.asItem());
        tag(stone_tool_materials).add(Blocks.BLACKSTONE.asItem());
    }
}
