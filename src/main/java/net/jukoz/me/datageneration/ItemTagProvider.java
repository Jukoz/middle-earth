package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.OreRockSets;
import net.jukoz.me.datageneration.content.models.HotMetalsModel;
import net.jukoz.me.datageneration.content.models.SimpleDyeableItemModel;
import net.jukoz.me.datageneration.content.tags.*;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModFoodItems;
import net.jukoz.me.item.ModResourceItems;
import net.jukoz.me.recipe.ModTags;
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
        var bones = getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")));
        var feathers = getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "feathers")));
        var cloaks = getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "cloaks")));
        var warg_food = getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "warg_food")));
        var warg_armor = getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "warg_armor")));
        var broadhoof_goat_armor = getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "broadhoof_goat_armor")));
        var dyeable = getOrCreateTagBuilder(ModTags.DYEABLE);

        TagKey<Item> iron_ores = TagKey.of(RegistryKeys.ITEM, Identifier.of("iron_ores"));
        TagKey<Item> gold_ores = TagKey.of(RegistryKeys.ITEM, Identifier.of("gold_ores"));
        TagKey<Item> copper_ores = TagKey.of(RegistryKeys.ITEM, Identifier.of("copper_ores"));
        TagKey<Item> coal_ores = TagKey.of(RegistryKeys.ITEM, Identifier.of("coal_ores"));

        TagKey<Item> saplings = TagKey.of(RegistryKeys.ITEM, Identifier.of("saplings"));
        TagKey<Item> wooden_slabs = TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs"));
        TagKey<Item> wooden_vertical_slabs = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "wooden_vertical_slabs"));
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
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "mod_planks"))).add(Planks.getItemPlanks().toArray(new Item[0]));


        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks"))).add(Planks.getItemPlanks().toArray(new Item[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, Identifier.of("logs"))).add(Logs.getItemPlanks().toArray(new Item[0]));


        warg_food.add(Items.RABBIT);
        warg_food.add(Items.CHICKEN);
        warg_food.add(Items.PORKCHOP);
        warg_food.add(Items.BEEF);
        warg_food.add(Items.MUTTON);
        warg_food.add(ModFoodItems.RAW_HORSE);
        warg_food.add(ModFoodItems.RAW_SWAN);
        warg_food.add(ModFoodItems.RAW_VENISON);

        warg_armor.add(ModEquipmentItems.WARG_MORDOR_PLATE_ARMOR);
        warg_armor.add(ModEquipmentItems.WARG_GUNDABAD_PLATE_ARMOR);
        warg_armor.add(ModEquipmentItems.WARG_ISENGARD_PLATE_ARMOR);
        warg_armor.add(ModEquipmentItems.WARG_MORDOR_MAIL_ARMOR);
        warg_armor.add(ModEquipmentItems.WARG_LEATHER_ARMOR);
        warg_armor.add(ModEquipmentItems.WARG_REINFORCED_LEATHER_ARMOR);

        broadhoof_goat_armor.add(ModEquipmentItems.BROADHOOF_GOAT_PLATE_ARMOR);
        broadhoof_goat_armor.add(ModEquipmentItems.BROADHOOF_GOAT_PADDED_ARMOR);
        broadhoof_goat_armor.add(ModEquipmentItems.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR);

        bones.add(Items.BONE);
        bones.add(ModResourceItems.DIRTY_BONE);

        feathers.add(ModResourceItems.SWAN_FEATHER);
        feathers.add(Items.FEATHER);

        ModEquipmentItems.hoods.forEach(cloaks::add);
        ModEquipmentItems.capes.forEach(cloaks::add);

        cloaks.add(ModEquipmentItems.NAZGUL_CLOAK_HOOD);

        dyeable.add(ModEquipmentItems.BROADHOOF_GOAT_PADDED_ARMOR);
        dyeable.add(ModEquipmentItems.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR);

        dyeable.add(ModEquipmentItems.WARG_LEATHER_ARMOR);
        dyeable.add(ModEquipmentItems.WARG_REINFORCED_LEATHER_ARMOR);

        for (OreRockSets.OreRockSet set : OreRockSets.sets) {
            if(set.coal_ore() != null) {
                getOrCreateTagBuilder(coal_ores)
                        .add(set.coal_ore().asItem());
            }
            if(set.copper_ore() != null) {
                getOrCreateTagBuilder(copper_ores)
                        .add(set.copper_ore().asItem());
            }
            if(set.tin_ore() != null) {
                getOrCreateTagBuilder(tin_ores)
                        .add(set.tin_ore().asItem());
            }
            if(set.lead_ore() != null) {
                getOrCreateTagBuilder(lead_ores)
                        .add(set.lead_ore().asItem());
            }
            if(set.silver_ore() != null) {
                getOrCreateTagBuilder(silver_ores)
                        .add(set.silver_ore().asItem());
            }
            if(set.gold_ore() != null) {
                getOrCreateTagBuilder(gold_ores)
                        .add(set.gold_ore().asItem());
            }
            if(set.iron_ore() != null) {
                getOrCreateTagBuilder(iron_ores)
                        .add(set.iron_ore().asItem());
            }
            if(set.mithril_ore() != null) {
                getOrCreateTagBuilder(mithril_ores)
                        .add(set.mithril_ore().asItem());
            }
        }

        SimpleDyeableItemModel.items.forEach(dyeable::add);

        WoodenSlabs.woodenSlabs.forEach(block -> {
            getOrCreateTagBuilder(wooden_slabs).add(block.asItem());
        });

        WoodenVerticalSlabs.woodenVericalSlabs.forEach(block -> {
            getOrCreateTagBuilder(wooden_vertical_slabs).add(block.asItem());
        });

        ModdedStrippedLogs.strippedLogs.forEach(block -> {
            getOrCreateTagBuilder(mod_stripped_logs).add(block.asItem());
        });

        Shingles.shingles.forEach(block -> {
            getOrCreateTagBuilder(shingles).add(block.asItem());
        });

        Saplings.saplings.forEach(sapling -> {
            getOrCreateTagBuilder(saplings).add(sapling.asItem());
        });

        LogsThatBurn.logsThatBurn.forEach(log -> {
            getOrCreateTagBuilder(logs_that_burn).add(log.asItem());
        });

        HotMetalsModel.ingots.forEach(ingot -> {
            getOrCreateTagBuilder(ingot_shaping).add(ingot);
        });

        HotMetalsModel.nuggets.forEach(nugget -> {
            getOrCreateTagBuilder(nugget_shaping).add(nugget);
        });

        Stones.stones.forEach(stone -> {
            getOrCreateTagBuilder(stone_crafting_materials).add(stone.asItem());
            if(!stone.asItem().toString().contains("jadeite")){
                getOrCreateTagBuilder(stone_tool_materials).add(stone.asItem());
            }
        });
    }
}
