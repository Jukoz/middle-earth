package net.sevenstars.middleearth.datageneration.providers.models;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.RangeDispatchItemModel;
import net.minecraft.client.render.item.model.SelectItemModel;
import net.minecraft.client.render.item.property.numeric.CrossbowPullProperty;
import net.minecraft.client.render.item.property.numeric.UseDurationProperty;
import net.minecraft.client.render.item.property.select.ChargeTypeProperty;
import net.minecraft.client.render.item.property.select.DisplayContextProperty;
import net.minecraft.client.render.item.property.select.TrimMaterialProperty;
import net.minecraft.client.render.item.tint.DyeTintSource;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.equipment.trim.ArmorTrimAssets;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.equipment.trim.ArmorTrimMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.ModBlocks;
import net.sevenstars.middleearth.datageneration.content.CustomItemModels;
import net.sevenstars.middleearth.datageneration.content.models.*;
import net.sevenstars.middleearth.item.ModResourceItems;
import net.sevenstars.middleearth.item.ModWeaponItems;

import java.util.*;

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

    private static final List<ItemModelGenerator.TrimMaterial> TRIM_MATERIALS = List.of(
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.QUARTZ, ArmorTrimMaterials.QUARTZ),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.IRON, ArmorTrimMaterials.IRON),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.NETHERITE, ArmorTrimMaterials.NETHERITE),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.REDSTONE, ArmorTrimMaterials.REDSTONE),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.COPPER, ArmorTrimMaterials.COPPER),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.GOLD, ArmorTrimMaterials.GOLD),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.EMERALD, ArmorTrimMaterials.EMERALD),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.DIAMOND, ArmorTrimMaterials.DIAMOND),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.LAPIS, ArmorTrimMaterials.LAPIS),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.AMETHYST, ArmorTrimMaterials.AMETHYST),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.RESIN, ArmorTrimMaterials.RESIN));


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
            registerWeaponBigItemModels(itemModelGenerator, item);
        }

        for (Item item : SimpleBigItemModel.bigBows) {
            registerBigBowItemModels(itemModelGenerator, item);
        }

        for (Item item : SimpleBowItemModel.items) {
            registerBow(itemModelGenerator, item);
        }

        for (Item item : SimpleCrossbowItemModel.items) {
            registerCrossbow(itemModelGenerator, item);
        }

        for (Item item : SimpleSpearModel.items) {
            registerSpearModels(itemModelGenerator, item);
        }

        for (Item item : SimpleBigItemModel.genericItems) {
            registerGenericBigModels(itemModelGenerator, item);
        }

        for (Item item: ModWeaponItems.shields){
            registerShield(itemModelGenerator, item);
        }

        //TODO to find solution for those
        for (Item item : HotMetalsModel.items) {
            //itemModelGenerator.register(item, "_hot", Models.GENERATED);
        }

        for (Item item : HotMetalsModel.ingots) {
            //Models.GENERATED.upload(ModelIds.getItemSubModelId(item, "_hot"), TextureMap.layer0(Identifier.of(MiddleEarth.MOD_ID, "item/ingot_hot")), itemModelGenerator.writer);
        }

        for (Item item : HotMetalsModel.nuggets) {
            //Models.GENERATED.upload(ModelIds.getItemSubModelId(item, "_hot"), TextureMap.layer0(Identifier.of(MiddleEarth.MOD_ID, "item/nugget_hot")), itemModelGenerator.writer);
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

        registerPalettedItem(ModResourceItems.ROD, itemModelGenerator);
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
        registerPalettedItem(ModResourceItems.SHIELD_PLATE, itemModelGenerator);
    }

    public final void registerWeaponBigItemModels(ItemModelGenerator itemModelGenerator, Item item) {
        ItemModel.Unbaked unbakedHand = ItemModels.basic(itemModelGenerator.upload(item, CustomItemModels.BIG_WEAPON));
        ItemModel.Unbaked unbakedInventory = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_inventory", Models.GENERATED));
        itemModelGenerator.output.accept(item, createModelWithInHandVariant(unbakedInventory, unbakedHand));
    }

    public final void registerGenericBigModels(ItemModelGenerator itemModelGenerator, Item item) {
        ItemModel.Unbaked unbakedHand = ItemModels.basic(ModelIds.getItemModelId(item));
        ItemModel.Unbaked unbakedInventory = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_inventory", Models.GENERATED));
        itemModelGenerator.output.accept(item, createModelWithInHandVariant(unbakedInventory, unbakedHand));
    }

    public final void registerSpearModels(ItemModelGenerator itemModelGenerator, Item item) {
        ItemModel.Unbaked unbakedHand = ItemModels.basic(ModelIds.getItemModelId(item));
        ItemModel.Unbaked unbakedInventory = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_inventory", Models.GENERATED));
        ItemModel.Unbaked unbakedHolding = ItemModels.basic(ModelIds.getItemModelId(item).withSuffixedPath("_holding"));

        itemModelGenerator.output.accept(item, ItemModels.select(new DisplayContextProperty(), unbakedHand,
                ItemModels.switchCase(List.of(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED), unbakedInventory)));
    }

    public final void registerShield(ItemModelGenerator itemModelGenerator, Item item) {
        ItemModel.Unbaked unbaked = ItemModels.basic(ModelIds.getItemModelId(item));
        ItemModel.Unbaked unbaked2 = ItemModels.basic(ModelIds.getItemSubModelId(item, "_blocking"));
        itemModelGenerator.registerCondition(item, ItemModels.usingItemProperty(), unbaked2, unbaked);
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

    public final void registerBow(ItemModelGenerator itemModelGenerator, Item item) {
        ItemModel.Unbaked unbaked = ItemModels.basic(itemModelGenerator.upload(item, CustomItemModels.BOW));
        ItemModel.Unbaked unbaked2 = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_pulling_0", Models.BOW));
        ItemModel.Unbaked unbaked3 = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_pulling_1", Models.BOW));
        ItemModel.Unbaked unbaked4 = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_pulling_2", Models.BOW));
        itemModelGenerator.output.accept(item, ItemModels.condition(ItemModels.usingItemProperty(), ItemModels.rangeDispatch(new UseDurationProperty(false), 0.05F, unbaked2, new RangeDispatchItemModel.Entry[]{ItemModels.rangeDispatchEntry(unbaked3, 0.65F), ItemModels.rangeDispatchEntry(unbaked4, 0.9F)}), unbaked));
    }

    public final void registerCrossbow(ItemModelGenerator itemModelGenerator, Item item) {
        ItemModel.Unbaked unbaked = ItemModels.basic(itemModelGenerator.upload(item, CustomItemModels.CROSSBOW));
        ItemModel.Unbaked unbaked2 = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_pulling_0", Models.CROSSBOW));
        ItemModel.Unbaked unbaked3 = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_pulling_1", Models.CROSSBOW));
        ItemModel.Unbaked unbaked4 = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_pulling_2", Models.CROSSBOW));
        ItemModel.Unbaked unbaked5 = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_arrow", Models.CROSSBOW));
        ItemModel.Unbaked unbaked6 = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_firework", Models.CROSSBOW));
        itemModelGenerator.output.accept(item, ItemModels.select(new ChargeTypeProperty(), ItemModels.condition(ItemModels.usingItemProperty(), ItemModels.rangeDispatch(new CrossbowPullProperty(), unbaked2, new RangeDispatchItemModel.Entry[]{ItemModels.rangeDispatchEntry(unbaked3, 0.58F), ItemModels.rangeDispatchEntry(unbaked4, 1.0F)}), unbaked), new SelectItemModel.SwitchCase[]{ItemModels.switchCase(CrossbowItem.ChargeType.ARROW, unbaked5), ItemModels.switchCase(CrossbowItem.ChargeType.ROCKET, unbaked6)}));
    }


    //TODO might need a rework cause of new tint thingy
    public final void registerDyeableArmor(Item armor, ItemModelGenerator itemModelGenerator) {
        Identifier identifier = ModelIds.getItemModelId(armor);
        Identifier identifier2 = TextureMap.getId(armor);
        Identifier identifier3 = TextureMap.getSubId(armor, "_overlay");

        Models.GENERATED_TWO_LAYERS.upload(identifier, TextureMap.layered(identifier2, identifier3), itemModelGenerator.modelCollector);
        ItemModel.Unbaked unbaked2 = ItemModels.tinted(identifier, new DyeTintSource(-6265536));

        itemModelGenerator.output.accept(armor,  unbaked2);
    }

    //TODO doesn't work to fix
    public final void registerPalettedItem(Item item, ItemModelGenerator itemModelGenerator) {
        Identifier identifierItem = Identifier.of(MiddleEarth.MOD_ID, "item/" + Registries.ITEM.getId(item).getPath());

        Identifier identifier2 = TextureMap.getId(item);

        List<SelectItemModel.SwitchCase<RegistryKey<ArmorTrimMaterial>>> list = new ArrayList<>(TRIM_MATERIALS.size());
        ItemModelGenerator.TrimMaterial trimMaterial;
        ItemModel.Unbaked unbaked;

        for(Iterator<ItemModelGenerator.TrimMaterial> var9 = TRIM_MATERIALS.iterator(); var9.hasNext(); list.add(ItemModels.switchCase(trimMaterial.materialKey(), unbaked))) {
            trimMaterial = var9.next();
            Identifier identifier4 = identifierItem.withSuffixedPath("_" + trimMaterial.assets().base().suffix() + "_trim");

            itemModelGenerator.uploadArmor(identifier4, identifier2,
                    Identifier.of(MiddleEarth.MOD_ID, "trims/" + identifierItem.getPath().replaceAll("item", "items") + "_" + trimMaterial.assets().base().suffix() + "_trim"));
            unbaked = ItemModels.basic(identifier4);
        }

        ItemModel.Unbaked unbaked2;
        Models.GENERATED.upload(identifierItem, TextureMap.layer0(identifier2), itemModelGenerator.modelCollector);
        unbaked2 = ItemModels.basic(identifierItem);

        itemModelGenerator.output.accept(item, ItemModels.select(new TrimMaterialProperty(), unbaked2, list));
    }
}
