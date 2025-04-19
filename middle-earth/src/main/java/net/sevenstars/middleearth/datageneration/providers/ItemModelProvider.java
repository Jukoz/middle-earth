package net.sevenstars.middleearth.datageneration.providers;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.RangeDispatchItemModel;
import net.minecraft.client.render.item.model.SelectItemModel;
import net.minecraft.client.render.item.property.numeric.UseDurationProperty;
import net.minecraft.client.render.item.property.select.DisplayContextProperty;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.equipment.trim.ArmorTrimMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.block.ModBlocks;
import net.sevenstars.middleearth.datageneration.content.CustomItemModels;
import net.sevenstars.middleearth.datageneration.content.models.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static net.minecraft.client.data.ItemModelGenerator.createModelWithInHandVariant;

public class ItemModelProvider extends FabricModelProvider {

    public ItemModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "ItemModelProvider";
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    }

    public static final Identifier TRIM_TYPE = Identifier.of("trim_type");
    private static final List<TrimMaterial> TRIM_MATERIALS = List.of(
            new TrimMaterial("quartz", ArmorTrimMaterials.QUARTZ, Map.of()),
            new TrimMaterial("iron", ArmorTrimMaterials.IRON, Map.of(EquipmentAssetKeys.IRON, "iron_darker")),
            new TrimMaterial("netherite", ArmorTrimMaterials.NETHERITE, Map.of(EquipmentAssetKeys.NETHERITE, "netherite_darker")),
            new TrimMaterial("redstone", ArmorTrimMaterials.REDSTONE, Map.of()),
            new TrimMaterial("copper", ArmorTrimMaterials.COPPER, Map.of()),
            new TrimMaterial("gold", ArmorTrimMaterials.GOLD, Map.of(EquipmentAssetKeys.GOLD, "gold_darker")),
            new TrimMaterial("emerald", ArmorTrimMaterials.EMERALD, Map.of()),
            new TrimMaterial("diamond", ArmorTrimMaterials.DIAMOND, Map.of(EquipmentAssetKeys.DIAMOND, "diamond_darker")),
            new TrimMaterial("lapis", ArmorTrimMaterials.LAPIS, Map.of()),
            new TrimMaterial("amethyst", ArmorTrimMaterials.AMETHYST, Map.of()),
            new TrimMaterial("resin", ArmorTrimMaterials.RESIN, Map.of()));

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (SimpleWallModel.Wall wall : SimpleWallModel.blocks) {
            Identifier id = Registries.BLOCK.getId(wall.wall());
            itemModelGenerator.register(wall.wall().asItem(), new Model(Optional.of(id.withPath("block/" + id.getPath() + "_inventory")), Optional.empty()));
        }

        for (SimpleWallModel.Wall wall : SimpleWallModel.vanillaWalls) {
            Identifier id = Registries.BLOCK.getId(wall.wall());
            itemModelGenerator.register(wall.wall().asItem(), new Model(Optional.of(id.withPath("block/" + id.getPath() + "_inventory")), Optional.empty()));
        }

        for (Item item : SimpleItemModel.items) {
            itemModelGenerator.register(item, Models.GENERATED);
        }

        for (Item item : SimpleHandheldItemModel.items) {
            itemModelGenerator.register(item, Models.HANDHELD);
        }

        for (Item item : SimpleDoorInventoryModel.items) {
            itemModelGenerator.register(item, Models.GENERATED);
        }

        for (Item item : SimpleBigItemModel.items) {
            registerBigItemModels(itemModelGenerator, item);
        }

        for (Item item : SimpleBigItemModel.bigBows) {
            registerBigBowItemModels(itemModelGenerator, item);
        }

        for (Item item : SimpleBigItemModel.genericItems) {
            //itemModelGenerator.register(item, "_inventory", Models.HANDHELD);
        }

        for (Item item : HotMetalsModel.items) {
            //itemModelGenerator.register(item, "_hot", Models.GENERATED);
        }

        for (Item item : HotMetalsModel.ingots) {
            //Models.GENERATED.upload(ModelIds.getItemSubModelId(item, "_hot"), TextureMap.layer0(Identifier.of(MiddleEarth.MOD_ID, "item/ingot_hot")), itemModelGenerator.writer);
        }

        for (Item item : HotMetalsModel.nuggets) {
            //Models.GENERATED.upload(ModelIds.getItemSubModelId(item, "_hot"), TextureMap.layer0(Identifier.of(MiddleEarth.MOD_ID, "item/nugget_hot")), itemModelGenerator.writer);
        }

        for (Item item : SimpleSpearModel.items) {
            //itemModelGenerator.register(item, "_inventory", Models.HANDHELD);
        }

        for (Item item : SimpleBowItemModel.items) {
            for (int i = 0; i < 3; i++) {
                //itemModelGenerator.register(item, "_pulling_" + i, CustomItemModels.BOW);
            }
        }

        for (Item item : SimpleCrossbowItemModel.items) {
            for (int i = 0; i < 3; i++) {
                //itemModelGenerator.register(item, "_pulling_" + i, CustomItemModels.CROSSBOW);
            }
            //itemModelGenerator.register(item, "_charged", CustomItemModels.CROSSBOW);
        }

        for (Item item : SimpleSpawnEggItemModel.items) {
            itemModelGenerator.register(item, CustomItemModels.TEMPLATE_SPAWN_EGG);
        }

        // Dyeables needs to be done manually (because of layers)

        SimpleDyeableItemModel.items.forEach(item -> {
            registerDyeableArmor(item, itemModelGenerator);
        });

        // CLUSTERS
        itemModelGenerator.register(ModBlocks.QUARTZ_CLUSTER.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SMALL_QUARTZ_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.MEDIUM_QUARTZ_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.LARGE_QUARTZ_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.RED_AGATE_CLUSTER.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SMALL_RED_AGATE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.MEDIUM_RED_AGATE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.LARGE_RED_AGATE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.CITRINE_CLUSTER.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SMALL_CITRINE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.MEDIUM_CITRINE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.LARGE_CITRINE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.GLOWSTONE_CLUSTER.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SMALL_GLOWSTONE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.MEDIUM_GLOWSTONE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.LARGE_GLOWSTONE_BUD.asItem(), Models.GENERATED);

       /* registerPalettedItem(ModResourceItems.ROD, itemModelGenerator);
        registerPalettedItem(ModResourceItems.LARGE_ROD, itemModelGenerator);

        registerPalettedItem(ModResourceItems.PICKAXE_HEAD, itemModelGenerator);
        registerPalettedItem(ModResourceItems.AXE_HEAD, itemModelGenerator);
        registerPalettedItem(ModResourceItems.SHOVEL_HEAD, itemModelGenerator);
        registerPalettedItem(ModResourceItems.HOE_HEAD, itemModelGenerator);

        registerPalettedItem(ModResourceItems.BLADE, itemModelGenerator);
        registerPalettedItem(ModResourceItems.SHORT_BLADE, itemModelGenerator);
        registerPalettedItem(ModResourceItems.LONG_BLADE, itemModelGenerator);
        registerPalettedItem(ModResourceItems.SWORD_HILT, itemModelGenerator);

        registerPalettedItem(ModResourceItems.MAIL_RING, itemModelGenerator);
        registerPalettedItem(ModResourceItems.MAIL, itemModelGenerator);

        registerPalettedItem(ModResourceItems.SCALE, itemModelGenerator);
        registerPalettedItem(ModResourceItems.SCALE_MAIL, itemModelGenerator);

        registerPalettedItem(ModResourceItems.ARMOR_PLATE, itemModelGenerator);

        registerPalettedItem(ModResourceItems.HELMET_PLATE, itemModelGenerator);
        registerPalettedItem(ModResourceItems.SHIELD_BORDER, itemModelGenerator);
        registerPalettedItem(ModResourceItems.SHIELD_PLATE, itemModelGenerator);*/
    }

    public final void registerBigItemModels(ItemModelGenerator itemModelGenerator, Item item) {
        ItemModel.Unbaked unbakedHand = ItemModels.basic(itemModelGenerator.upload(item, CustomItemModels.BIG_WEAPON));
        ItemModel.Unbaked unbakedInventory = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_inventory", Models.HANDHELD));
        itemModelGenerator.output.accept(item, createModelWithInHandVariant(unbakedInventory, unbakedHand));
    }

    public final void registerBigBowItemModels(ItemModelGenerator itemModelGenerator, Item item) {
        ItemModel.Unbaked unbakedHand = ItemModels.basic(itemModelGenerator.upload(item, CustomItemModels.LONGBOW));
        ItemModel.Unbaked unbakedHand2 = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_pulling_0", CustomItemModels.LONGBOW));
        ItemModel.Unbaked unbakedHand3 = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_pulling_1", CustomItemModels.LONGBOW));
        ItemModel.Unbaked unbakedHand4 = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_pulling_2", CustomItemModels.LONGBOW));

        ItemModel.Unbaked unbakedIventory = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_inventory", Models.GENERATED));
        ItemModel.Unbaked unbakedIventory2 = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_pulling_0_inventory", Models.GENERATED));
        ItemModel.Unbaked unbakedIventory3 = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_pulling_1_inventory", Models.GENERATED));
        ItemModel.Unbaked unbakedIventory4 = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_pulling_2_inventory", Models.GENERATED));


        itemModelGenerator.output.accept(item, ItemModels.select(new DisplayContextProperty(), ItemModels.condition(ItemModels.usingItemProperty(),
                ItemModels.rangeDispatch(
                        new UseDurationProperty(false), 0.05F, unbakedHand2,
                        ItemModels.rangeDispatchEntry(unbakedHand3, 0.65F),
                        ItemModels.rangeDispatchEntry(unbakedHand4, 0.9F)), unbakedHand), ItemModels.switchCase(List.of(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED), ItemModels.condition(ItemModels.usingItemProperty(),
                ItemModels.rangeDispatch(
                        new UseDurationProperty(false), 0.05F, unbakedIventory2,
                        ItemModels.rangeDispatchEntry(unbakedIventory3, 0.65F),
                        ItemModels.rangeDispatchEntry(unbakedIventory4, 0.9F)), unbakedIventory))));

    }

    public final void registerDyeableArmor(Item armor, ItemModelGenerator itemModelGenerator) {
        Identifier identifier = ModelIds.getItemModelId(armor);
        Identifier identifier2 = TextureMap.getId(armor);
        Identifier identifier3 = TextureMap.getSubId(armor, "_overlay");
        Models.GENERATED_TWO_LAYERS.upload(identifier, TextureMap.layered(identifier2, identifier3), itemModelGenerator.modelCollector);
    }

    /*public final void registerPalettedItem(Item item, ItemModelGenerator itemModelGenerator) {
        Identifier identifierItem = Identifier.of(MiddleEarth.MOD_ID, "item/" + Registries.ITEM.getId(item).getPath());

        Identifier identifier2 = TextureMap.getId(item);

        Models.GENERATED.upload(identifierItem, TextureMap.layer0(identifierItem), itemModelGenerator.modelCollector, (id, textures) -> this.registerPalettedItemJson(item, id, textures, itemModelGenerator));
        for (TrimMaterial trimMaterial : TRIM_MATERIALS) {

            String string;
            if (trimMaterial.name.contains("iron")) {
                string = trimMaterial.name + "_darker";
            } else {
                string = trimMaterial.name;
            }

            Identifier identifier4 = itemModelGenerator.suffixTrim(identifierItem, string);
            String string2 = Registries.ITEM.getId(item).getPath() + "_trim_" + string;
            Identifier identifier5 = Identifier.of(MiddleEarth.MOD_ID, string2).withPrefixedPath("trims/items/");

            itemModelGenerator.uploadArmor(identifier4, identifier2, identifier5);
        }
    }

    public final JsonObject registerPalettedItemJson(Item item, Identifier id, Map<TextureKey, Identifier> textures, ItemModelGenerator itemModelGenerator) {
        Identifier identifierItem = Identifier.of(MiddleEarth.MOD_ID, "item/" + Registries.ITEM.getId(item).getPath());

        JsonObject jsonObject = Models.GENERATED_TWO_LAYERS.createJson(identifierItem, textures);
        JsonArray jsonArray = new JsonArray();
        for (TrimMaterial trimMaterial : TRIM_MATERIALS) {
            JsonObject jsonObject2 = new JsonObject();
            JsonObject jsonObject3 = new JsonObject();
            jsonObject3.addProperty(TRIM_TYPE.getPath(), Float.valueOf(trimMaterial.itemModelIndex()));
            jsonObject2.add("predicate", jsonObject3);
            String string;
            if (trimMaterial.name.contains("iron")) {
                string = trimMaterial.name + "_darker";
            } else {
                string = trimMaterial.name;
            }
            jsonObject2.addProperty("model", itemModelGenerator.suffixTrim(id, string).toString());
            jsonArray.add(jsonObject2);
        }

        jsonObject.add("overrides", jsonArray);

        return jsonObject;
    }*/

    private static record TrimMaterial(String name, RegistryKey<ArmorTrimMaterial> materialKey,
                                       Map<RegistryKey<EquipmentAsset>, String> overrideArmorMaterials) {

        TrimMaterial(String name, RegistryKey<ArmorTrimMaterial> materialKey, Map<RegistryKey<EquipmentAsset>, String> overrideArmorMaterials) {
            this.name = name;
            this.materialKey = materialKey;
            this.overrideArmorMaterials = overrideArmorMaterials;
        }

        public String texture(RegistryKey<EquipmentAsset> equipmentKey) {
            return (String) this.overrideArmorMaterials.getOrDefault(equipmentKey, this.name);
        }

        public String name() {
            return this.name;
        }

        public RegistryKey<ArmorTrimMaterial> materialKey() {
            return this.materialKey;
        }

        public Map<RegistryKey<EquipmentAsset>, String> overrideArmorMaterials() {
            return this.overrideArmorMaterials;
        }
    }

    record ItemTrimMaterial(String name, float itemModelIndex,
                            Map<RegistryEntry<ArmorMaterial>, String> overrideArmorMaterials) {

        public String getAppliedName(RegistryEntry<ArmorMaterial> armorMaterial) {
            return this.overrideArmorMaterials.getOrDefault(armorMaterial, this.name);
        }
    }
}
