package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.OreRockSets;
import net.jukoz.me.datageneration.content.tags.*;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModFoodItems;
import net.jukoz.me.item.ModResourceItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        var bones = getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier(MiddleEarth.MOD_ID, "bones")));
        var feathers = getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier(MiddleEarth.MOD_ID, "feathers")));
        var cloaks = getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier(MiddleEarth.MOD_ID, "cloaks")));
        var warg_food = getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier(MiddleEarth.MOD_ID, "warg_food")));

        TagKey<Item> iron_ores = TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft", "iron_ores"));
        TagKey<Item> gold_ores = TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft", "gold_ores"));
        TagKey<Item> copper_ores = TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft", "copper_ores"));
        TagKey<Item> coal_ores = TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft", "coal_ores"));

        TagKey<Item> saplings = TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft", "saplings"));
        TagKey<Item> wooden_slabs = TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft", "wooden_slabs"));
        TagKey<Item> logs_that_burn = TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft", "logs_that_burn"));
        TagKey<Item> stone_crafting_materials = TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft", "stone_crafting_materials"));
        TagKey<Item> stone_tool_materials = TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft", "stone_tool_materials"));

        TagKey<Item> tin_ores = TagKey.of(RegistryKeys.ITEM, new Identifier("me", "tin_ores"));
        TagKey<Item> lead_ores = TagKey.of(RegistryKeys.ITEM, new Identifier("me", "lead_ores"));
        TagKey<Item> silver_ores = TagKey.of(RegistryKeys.ITEM, new Identifier("me", "silver_ores"));
        TagKey<Item> mithril_ores = TagKey.of(RegistryKeys.ITEM, new Identifier("me", "mithril_ores"));
        TagKey<Item> shingles = TagKey.of(RegistryKeys.ITEM, new Identifier("me", "shingles"));

        TagKey<Item> mod_stripped_logs = TagKey.of(RegistryKeys.ITEM, new Identifier("me", "mod_stripped_logs"));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier("me", "mod_planks"))).add(Planks.getItemPlanks().toArray(new Item[0]));


        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft", "planks"))).add(Planks.getItemPlanks().toArray(new Item[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft", "logs"))).add(Logs.getItemPlanks().toArray(new Item[0]));


        warg_food.add(Items.RABBIT);
        warg_food.add(Items.CHICKEN);
        warg_food.add(Items.PORKCHOP);
        warg_food.add(Items.BEEF);
        warg_food.add(Items.MUTTON);
        warg_food.add(ModFoodItems.RAW_HORSE);
        warg_food.add(ModFoodItems.RAW_DUCK);
        warg_food.add(ModFoodItems.RAW_GOOSE);
        warg_food.add(ModFoodItems.RAW_SWAN);
        warg_food.add(ModFoodItems.RAW_VENISON);

        bones.add(Items.BONE);
        bones.add(ModResourceItems.ORC_BONE);
        bones.add(ModResourceItems.WARG_BONE);

        feathers.add(ModResourceItems.DUCK_FEATHER);
        feathers.add(ModResourceItems.SWAN_FEATHER);
        feathers.add(Items.FEATHER);

        cloaks.add(ModEquipmentItems.CLOAK);
        cloaks.add(ModEquipmentItems.TUNIC_CLOAK);
        cloaks.add(ModEquipmentItems.FUR_CLOAK);
        cloaks.add(ModEquipmentItems.CHAINMAIL_FUR_CLOAK);
        cloaks.add(ModEquipmentItems.NAZGUL_CLOAK);
        cloaks.add(ModEquipmentItems.CLOAK_HOOD);
        cloaks.add(ModEquipmentItems.FUR_CLOAK_HOOD);
        cloaks.add(ModEquipmentItems.NAZGUL_CLOAK_HOOD);

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

        WoodenSlabs.woodenSlabs.forEach(block -> {
            getOrCreateTagBuilder(wooden_slabs).add(block.asItem());
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

        Stones.stones.forEach(stone -> {
            getOrCreateTagBuilder(stone_crafting_materials).add(stone.asItem());
            if(!stone.asItem().toString().contains("jadeite")){
                getOrCreateTagBuilder(stone_tool_materials).add(stone.asItem());
            }
        });
    }
}
