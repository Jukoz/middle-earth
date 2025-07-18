package net.sevenstars.middleearth.datageneration.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.OreRockSets;
import net.sevenstars.middleearth.datageneration.content.models.HotMetalsModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleDyeableItemModel;
import net.sevenstars.middleearth.datageneration.content.tags.*;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.FoodItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.recipe.ModTags;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        var bones = valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")));
        var feathers = valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "feathers")));
        var cloaks = valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "cloaks")));
        var warg_food = valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "warg_food")));
        var warg_armor = valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "warg_armor")));
        var broadhoof_goat_armor = valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "broadhoof_goat_armor")));
        var dyeable = valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of("dyeable")));

        TagKey<Item> iron_ores = TagKey.of(RegistryKeys.ITEM, Identifier.of("iron_ores"));
        TagKey<Item> gold_ores = TagKey.of(RegistryKeys.ITEM, Identifier.of("gold_ores"));
        TagKey<Item> copper_ores = TagKey.of(RegistryKeys.ITEM, Identifier.of("copper_ores"));
        TagKey<Item> coal_ores = TagKey.of(RegistryKeys.ITEM, Identifier.of("coal_ores"));

        TagKey<Item> saplings = TagKey.of(RegistryKeys.ITEM, Identifier.of("saplings"));
        TagKey<Item> wooden_slabs = TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs"));
        TagKey<Item> wooden_vertical_slabs = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "wooden_vertical_slabs"));
        TagKey<Item> wooden_fences = TagKey.of(RegistryKeys.ITEM, Identifier.of( "wooden_fences"));
        TagKey<Item> logs_that_burn = TagKey.of(RegistryKeys.ITEM, Identifier.of("logs_that_burn"));
        TagKey<Item> stone_crafting_materials = TagKey.of(RegistryKeys.ITEM, Identifier.of("stone_crafting_materials"));
        TagKey<Item> stone_tool_materials = TagKey.of(RegistryKeys.ITEM, Identifier.of("stone_tool_materials"));

        TagKey<Item> ingot_shaping = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "ingot_shaping"));
        TagKey<Item> nugget_shaping = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "nugget_shaping"));

        TagKey<Item> tin_ores = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "tin_ores"));
        TagKey<Item> lead_ores = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "lead_ores"));
        TagKey<Item> silver_ores = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "silver_ores"));
        TagKey<Item> mithril_ores = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "mithril_ores"));
        TagKey<Item> shingles = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "shingles"));

        TagKey<Item> mod_stripped_logs = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "mod_stripped_logs"));
        valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "mod_planks"))).add(Planks.getItemPlanks().toArray(new Item[0]));

        valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks"))).add(Planks.getItemPlanks().toArray(new Item[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of("logs"))).add(Logs.getItemPlanks().toArray(new Item[0]));

        valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of("enchantable/sword"))).add(WeaponEnchants.swords.toArray(new Item[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of("enchantable/sharp_weapon"))).add(WeaponEnchants.sharpWeapons.toArray(new Item[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of("enchantable/bow"))).add(Bows.bows.toArray(new Item[0]));
        valueLookupBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of("enchantable/crossbow"))).add(Crossbows.crossbows.toArray(new Item[0]));

        warg_food.add(Items.RABBIT);
        warg_food.add(Items.CHICKEN);
        warg_food.add(Items.PORKCHOP);
        warg_food.add(Items.BEEF);
        warg_food.add(Items.MUTTON);
        warg_food.add(FoodItemsME.RAW_HORSE);
        warg_food.add(FoodItemsME.RAW_SWAN);
        warg_food.add(FoodItemsME.RAW_VENISON);

        warg_armor.add(EquipmentItemsME.WARG_MORDOR_PLATE_ARMOR);
        warg_armor.add(EquipmentItemsME.WARG_GUNDABAD_PLATE_ARMOR);
        warg_armor.add(EquipmentItemsME.WARG_ISENGARD_PLATE_ARMOR);
        warg_armor.add(EquipmentItemsME.WARG_MORDOR_MAIL_ARMOR);
        warg_armor.add(EquipmentItemsME.WARG_LEATHER_ARMOR);
        warg_armor.add(EquipmentItemsME.WARG_REINFORCED_LEATHER_ARMOR);

        broadhoof_goat_armor.add(EquipmentItemsME.BROADHOOF_GOAT_PLATE_ARMOR);
        broadhoof_goat_armor.add(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR);
        broadhoof_goat_armor.add(EquipmentItemsME.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR);

        bones.add(Items.BONE);
        bones.add(ResourceItemsME.DIRTY_BONE);
        bones.add(ResourceItemsME.FANG);

        feathers.add(ResourceItemsME.SWAN_FEATHER);
        feathers.add(Items.FEATHER);

        EquipmentItemsME.helmeAtttachments.forEach(cloaks::add);
        EquipmentItemsME.backAttachments.forEach(cloaks::add);

        dyeable.add(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR);
        dyeable.add(EquipmentItemsME.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR);

        dyeable.add(EquipmentItemsME.WARG_LEATHER_ARMOR);
        dyeable.add(EquipmentItemsME.WARG_REINFORCED_LEATHER_ARMOR);

        for (OreRockSets.OreRockSet set : OreRockSets.sets) {
            if(set.coal_ore() != null) {
                valueLookupBuilder(coal_ores)
                        .add(set.coal_ore().asItem());
            }
            if(set.copper_ore() != null) {
                valueLookupBuilder(copper_ores)
                        .add(set.copper_ore().asItem());
            }
            if(set.tin_ore() != null) {
                valueLookupBuilder(tin_ores)
                        .add(set.tin_ore().asItem());
            }
            if(set.lead_ore() != null) {
                valueLookupBuilder(lead_ores)
                        .add(set.lead_ore().asItem());
            }
            if(set.silver_ore() != null) {
                valueLookupBuilder(silver_ores)
                        .add(set.silver_ore().asItem());
            }
            if(set.gold_ore() != null) {
                valueLookupBuilder(gold_ores)
                        .add(set.gold_ore().asItem());
            }
            if(set.iron_ore() != null) {
                valueLookupBuilder(iron_ores)
                        .add(set.iron_ore().asItem());
            }
            if(set.mithril_ore() != null) {
                valueLookupBuilder(mithril_ores)
                        .add(set.mithril_ore().asItem());
            }
        }

        SimpleDyeableItemModel.items.forEach(dyeable::add);

        WoodenSlabs.woodenSlabs.forEach(block -> {
            valueLookupBuilder(wooden_slabs).add(block.asItem());
        });

        WoodenVerticalSlabs.woodenVericalSlabs.forEach(block -> {
            valueLookupBuilder(wooden_vertical_slabs).add(block.asItem());
        });

        Fences.fences.forEach(block -> {
            valueLookupBuilder(wooden_fences).add(block.asItem());
        });

        ModdedStrippedLogs.strippedLogs.forEach(block -> {
            valueLookupBuilder(mod_stripped_logs).add(block.asItem());
        });

        Shingles.shingles.forEach(block -> {
            valueLookupBuilder(shingles).add(block.asItem());
        });

        Saplings.saplings.forEach(sapling -> {
            valueLookupBuilder(saplings).add(sapling.asItem());
        });

        LogsThatBurn.logsThatBurn.forEach(log -> {
            valueLookupBuilder(logs_that_burn).add(log.asItem());
        });

        HotMetalsModel.ingots.forEach(ingot -> {
            valueLookupBuilder(ingot_shaping).add(ingot);
        });

        HotMetalsModel.nuggets.forEach(nugget -> {
            valueLookupBuilder(nugget_shaping).add(nugget);
        });

        Stones.stones.forEach(stone -> {
            valueLookupBuilder(stone_crafting_materials).add(stone.asItem());
            if(!stone.asItem().toString().contains("jadeite")){
                valueLookupBuilder(stone_tool_materials).add(stone.asItem());
            }
        });
    }
}
