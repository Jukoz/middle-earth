package net.sevenstars.middleearth.datageneration.providers.models;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.RangeDispatchItemModel;
import net.minecraft.client.render.item.model.SelectItemModel;
import net.minecraft.client.render.item.property.bool.BrokenProperty;
import net.minecraft.client.render.item.property.bool.UsingItemProperty;
import net.minecraft.client.render.item.property.numeric.CrossbowPullProperty;
import net.minecraft.client.render.item.property.numeric.UseDurationProperty;
import net.minecraft.client.render.item.property.select.*;
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
import net.sevenstars.middleearth.datageneration.content.CustomItemModels;
import net.sevenstars.middleearth.datageneration.content.models.*;
import net.sevenstars.middleearth.item.EggItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.items.PipeItem;
import net.sevenstars.middleearth.item.items.weapons.CustomLongswordWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.HotComponentProperty;
import net.sevenstars.middleearth.item.items.weapons.SneakAttackProperty;
import net.sevenstars.middleearth.item.utils.SmithingTrimMaterialsME;
import net.sevenstars.middleearth.registries.content.npcs.NpcRegistry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

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
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.RESIN, ArmorTrimMaterials.RESIN),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.of("jade"), SmithingTrimMaterialsME.JADE),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.of("lead"), SmithingTrimMaterialsME.LEAD),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.of("tin"), SmithingTrimMaterialsME.TIN),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.of("bronze"), SmithingTrimMaterialsME.BRONZE),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.of("crude"), SmithingTrimMaterialsME.CRUDE),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.of("silver"), SmithingTrimMaterialsME.SILVER),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.of("steel"), SmithingTrimMaterialsME.STEEL),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.of("khazad_steel"), SmithingTrimMaterialsME.KHAZAD_STEEL),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.of("edhel_steel"), SmithingTrimMaterialsME.EDHEL_STEEL),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.of("burzum_steel"), SmithingTrimMaterialsME.BURZUM_STEEL),
            new ItemModelGenerator.TrimMaterial(ArmorTrimAssets.of("mithril"), SmithingTrimMaterialsME.MITHRIL)
    );


    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        for (Item item : SimpleItemModel.items) {
            itemModelGenerator.register(item, Models.GENERATED);
        }

        for (Item item : SimpleHandheldItemModel.items) {
            itemModelGenerator.register(item, Models.HANDHELD);
        }

        for (Item item : SimpleHandheldItemModel.daggers) {
            registerDaggerItemModels(itemModelGenerator, item);
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

        for (Item item : WeaponItemsME.shields) {
            registerShield(itemModelGenerator, item);
        }

        for (SimpleArtefactModels.Artefact artefact : SimpleArtefactModels.artefacts) {
            registerArtefact(itemModelGenerator, artefact.artefact(), artefact.dualModel());
        }

        for (Item item : HotMetalsModel.ingots) {
            registerHotIngotsItem(item, itemModelGenerator);
        }

        for (Item item : HotMetalsModel.nuggets) {
            registerHotNuggetItem(item, itemModelGenerator);
        }

        // Dyeables needs to be done manually (because of layers)

        SimpleDyeableItemModel.items.forEach(item -> {
            registerDyeableArmor(item, itemModelGenerator);
        });

        registerPalettedItem(ResourceItemsME.ROD, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.LARGE_ROD, itemModelGenerator);

        registerPalettedItem(ResourceItemsME.PICKAXE_HEAD, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.AXE_HEAD, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.SHOVEL_HEAD, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.HOE_HEAD, itemModelGenerator);

        registerPalettedItem(ResourceItemsME.BLADE, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.SHORT_BLADE, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.LONG_BLADE, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.SWORD_HILT, itemModelGenerator);

        registerPalettedItem(ResourceItemsME.MAIL_RING, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.MAIL, itemModelGenerator);

        registerPalettedItem(ResourceItemsME.SCALE, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.SCALE_MAIL, itemModelGenerator);

        registerPalettedItem(ResourceItemsME.ARMOR_PLATE, itemModelGenerator);

        registerPalettedItem(ResourceItemsME.HELMET_PLATE, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.SHIELD_BORDER, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.SHIELD_PLATE, itemModelGenerator);

        List<SelectItemModel.SwitchCase> models = new ArrayList<>(List.of());

        NpcRegistry.allNpcDatas.forEach(npcDataRegistryKey -> {
            String id = npcDataRegistryKey.getValue().getPath().replaceAll("npc_data.middle-earth.", "").replaceAll("\\.", "_") + "_spawn_egg";

            var item = ItemModels.switchCase(id,
                    ItemModels.basic(Models.GENERATED.upload(MiddleEarth.of("item/" + id),
                            TextureMap.layer0(MiddleEarth.of( "item/" + id)),
                            itemModelGenerator.modelCollector
                    )));

            if(!models.contains(item))
                models.add(item);
        });
        ItemModel.Unbaked fallbackModel = ItemModels.basic(itemModelGenerator.upload(EggItemsME.NPC_SPAWN_EGG, Models.GENERATED));

        String randomNpcEggId = "npc_random_spawn_egg";
        var randomNpcEgg = ItemModels.switchCase(randomNpcEggId,
            ItemModels.basic(Models.GENERATED.upload(MiddleEarth.ofPath( "item", randomNpcEggId),
                    TextureMap.layer0(MiddleEarth.ofPath( "item", randomNpcEggId)),
                    itemModelGenerator.modelCollector
            )));

        if(!models.contains(randomNpcEgg))
            models.add(randomNpcEgg);

        itemModelGenerator.output.accept(EggItemsME.NPC_SPAWN_EGG,
                new SelectItemModel.Unbaked(new SelectItemModel.UnbakedSwitch(new CustomModelDataStringProperty(0), models), Optional.of(fallbackModel)));
    }

    public final void registerDaggerItemModels(ItemModelGenerator itemModelGenerator, Item item) {
        ItemModel.Unbaked unbakedHand = ItemModels.basic(itemModelGenerator.upload(item, Models.HANDHELD));
        ItemModel.Unbaked unbakedHandStrike = ItemModels.basic(CustomItemModels.DAGGER_STRIKE.upload(ModelIds.getItemSubModelId(item, "_strike"),
                TextureMap.layer0(TextureMap.getId(item)), itemModelGenerator.modelCollector));
        //ItemModels.basic(itemModelGenerator.registerSubModel(item, "_strike", CustomItemModels.DAGGER_STRIKE));

        itemModelGenerator.output.accept(item, ItemModels.condition(new SneakAttackProperty(), unbakedHandStrike, unbakedHand));
    }

    public final void registerWeaponBigItemModels(ItemModelGenerator itemModelGenerator, Item item) {
        ItemModel.Unbaked unbakedHand;
        if (Registries.ITEM.getId(item).getPath().contains("staff")){
            unbakedHand = ItemModels.basic(itemModelGenerator.upload(item, CustomItemModels.BIG_WEAPON_STAFF));
        } else {
            unbakedHand = ItemModels.basic(itemModelGenerator.upload(item, CustomItemModels.BIG_WEAPON));
        }
        ItemModel.Unbaked unbakedInventory = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_inventory", Models.GENERATED));

        if (item instanceof CustomLongswordWeaponItem longswordWeaponItem){
            ItemModel.Unbaked unbakedHandBlocking = ItemModels.basic(CustomItemModels.BIG_WEAPON_BLOCKING.upload(ModelIds.getItemSubModelId(item, "_blocking"), TextureMap.layer0(TextureMap.getId(item)), itemModelGenerator.modelCollector));
            itemModelGenerator.output.accept(longswordWeaponItem, ItemModels.condition(new UsingItemProperty(),
                    ItemModels.select(new DisplayContextProperty(), unbakedHandBlocking,
                            ItemModels.switchCase(List.of(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED), unbakedInventory)),
                    ItemModels.select(new DisplayContextProperty(), unbakedHand,
                            ItemModels.switchCase(List.of(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED), unbakedInventory))));
        } else {
            itemModelGenerator.output.accept(item, createModelWithInHandVariant(unbakedInventory, unbakedHand));
        }
    }

    public final void registerGenericBigModels(ItemModelGenerator itemModelGenerator, Item item) {
        if (item instanceof PipeItem) {
            registerPipeItemModels(itemModelGenerator, item);
            return;
        }

        ItemModel.Unbaked unbakedHand = ItemModels.basic(ModelIds.getItemModelId(item));
        ItemModel.Unbaked unbakedInventory = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_inventory", Models.GENERATED));

        itemModelGenerator.output.accept(item, createModelWithInHandVariant(unbakedInventory, unbakedHand));
    }

    public final void registerPipeItemModels(ItemModelGenerator itemModelGenerator, Item item) {
        String path = Registries.ITEM.getId(item).getPath();
        ItemModel.Unbaked unbakedHand = ItemModels.basic(ModelIds.getItemModelId(item));
        ItemModel.Unbaked unbakedSmokingHand = ItemModels.basic(Identifier.of(MiddleEarth.MOD_ID, "item/smoking_" + path));
        ItemModel.Unbaked unbakedInventory = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_inventory", Models.GENERATED));

        itemModelGenerator.output.accept(item, ItemModels.select(new DisplayContextProperty(),
                ItemModels.condition(ItemModels.usingItemProperty(), unbakedSmokingHand, unbakedHand),
                ItemModels.switchCase(List.of(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED), unbakedInventory)));
    }

    //TODO longsword artefact blocking datagen
    public final void registerArtefact(ItemModelGenerator itemModelGenerator, Item item, Boolean dualModel) {
        if (dualModel) {
            ItemModel.Unbaked unbakedHand = ItemModels.basic(itemModelGenerator.upload(item, CustomItemModels.BIG_WEAPON));
            ItemModel.Unbaked unbakedInventory = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_inventory", Models.GENERATED));
            ItemModel.Unbaked unbakedHandBlocking = ItemModels.basic(CustomItemModels.BIG_WEAPON_BLOCKING.upload(ModelIds.getItemSubModelId(item, "_blocking"), TextureMap.layer0(TextureMap.getId(item)), itemModelGenerator.modelCollector));

            ItemModel.Unbaked unbakedBrokenHand = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_broken", CustomItemModels.BIG_WEAPON));
            ItemModel.Unbaked unbakedBrokenInventory = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_broken_inventory", Models.GENERATED));
            ItemModel.Unbaked unbakedBrokenHandBlocking = ItemModels.basic(CustomItemModels.BIG_WEAPON_BLOCKING.upload(
                    ModelIds.getItemSubModelId(item, "_broken_blocking"), TextureMap.layer0(TextureMap.getId(item)), itemModelGenerator.modelCollector));

            itemModelGenerator.output.accept(item, ItemModels.condition(new BrokenProperty(),
                    ItemModels.select(new DisplayContextProperty(), ItemModels.condition(new UsingItemProperty(), unbakedBrokenHandBlocking, unbakedBrokenHand),
                            ItemModels.switchCase(List.of(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED), unbakedBrokenInventory)),
                    ItemModels.select(new DisplayContextProperty(), ItemModels.condition(new UsingItemProperty(), unbakedHandBlocking, unbakedHand),
                            ItemModels.switchCase(List.of(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED), unbakedInventory))));
        } else {
            ItemModel.Unbaked unbaked = ItemModels.basic(itemModelGenerator.upload(item, Models.HANDHELD));
            ItemModel.Unbaked unbakedBroken = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_broken", Models.HANDHELD));

            itemModelGenerator.registerCondition(item, new BrokenProperty(), unbakedBroken, unbaked);
        }

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

    public final Identifier registerSubModelWithSingletonTexture(Item item, String suffix, Model model, BiConsumer<Identifier, ModelSupplier> modelCollector) {
        return model.upload(ModelIds.getItemSubModelId(item, suffix), TextureMap.layer0(TextureMap.getId(item)), modelCollector);
    }

    //TODO might need a rework cause of new tint thingy
    public final void registerDyeableArmor(Item armor, ItemModelGenerator itemModelGenerator) {
        Identifier identifier = ModelIds.getItemModelId(armor);
        Identifier identifier2 = TextureMap.getId(armor);
        Identifier identifier3 = TextureMap.getSubId(armor, "_overlay");

        Models.GENERATED_TWO_LAYERS.upload(identifier, TextureMap.layered(identifier2, identifier3), itemModelGenerator.modelCollector);
        ItemModel.Unbaked unbaked2 = ItemModels.tinted(identifier, new DyeTintSource(-6265536));

        itemModelGenerator.output.accept(armor, unbaked2);
    }

    public final void registerPalettedItem(Item item, ItemModelGenerator itemModelGenerator) {
        Identifier identifierItem = Identifier.of(MiddleEarth.MOD_ID, "item/" + Registries.ITEM.getId(item).getPath());

        Identifier identifier2 = TextureMap.getId(item);

        List<SelectItemModel.SwitchCase<RegistryKey<ArmorTrimMaterial>>> list = new ArrayList<>(TRIM_MATERIALS.size());
        ItemModelGenerator.TrimMaterial trimMaterial;
        ItemModel.Unbaked unbaked;

        for (Iterator<ItemModelGenerator.TrimMaterial> var9 = TRIM_MATERIALS.iterator(); var9.hasNext(); list.add(ItemModels.switchCase(trimMaterial.materialKey(), unbaked))) {
            trimMaterial = var9.next();
            Identifier identifier4 = identifierItem.withSuffixedPath("_" + trimMaterial.assets().base().suffix() + "_trim");

            itemModelGenerator.uploadArmor(identifier4, identifier2,
                    Identifier.of(MiddleEarth.MOD_ID, "trims/" + identifierItem.getPath().replaceAll("item", "items") + "_trim" + "_" + trimMaterial.assets().base().suffix()));
            unbaked = ItemModels.basic(identifier4);
        }

        ItemModel.Unbaked unbaked2;
        Models.GENERATED.upload(identifierItem, TextureMap.layer0(identifier2), itemModelGenerator.modelCollector);
        unbaked2 = ItemModels.basic(identifierItem);

        ItemModel.Unbaked unbakedHotItem = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_hot", Models.GENERATED));

        itemModelGenerator.output.accept(item, ItemModels.condition(new HotComponentProperty(), unbakedHotItem, ItemModels.select(new TrimMaterialProperty(), unbaked2, list)));
    }

    public final void registerHotIngotsItem(Item item, ItemModelGenerator itemModelGenerator) {
        ItemModel.Unbaked unbakedItem = ItemModels.basic(itemModelGenerator.upload(item, Models.GENERATED));
        String idPath = "ingot_hot";
        if(item == ResourceItemsME.BRONZE_INGOT) {
            idPath = "medium_ingot_hot";
        } else if(item == ResourceItemsME.TIN_INGOT) {
            idPath = "cube_ingot_hot";
        } else if(item == ResourceItemsME.LEAD_INGOT) {
            idPath = "tall_small_ingot_hot";
        } else if(item == ResourceItemsME.EDHEL_STEEL_INGOT || item == ResourceItemsME.MITHRIL_INGOT) {
            idPath = "small_ingot_hot";
        } else if(item == ResourceItemsME.KHAZAD_STEEL_INGOT) {
            idPath = "tall_ingot_hot";
        } else if(item == ResourceItemsME.BURZUM_STEEL_INGOT) {
            idPath = "thick_ingot_hot";
        }

        Identifier textureId = MiddleEarth.ofPath( "item", idPath);
        ItemModel.Unbaked unbakedHotItem = ItemModels.basic(Models.GENERATED.upload(ModelIds.getItemSubModelId(item, "_hot"),
                TextureMap.layer0(textureId), itemModelGenerator.modelCollector));

        itemModelGenerator.output.accept(item, ItemModels.condition(new HotComponentProperty(), unbakedHotItem, unbakedItem));
    }

    public final void registerHotNuggetItem(Item item, ItemModelGenerator itemModelGenerator) {
        ItemModel.Unbaked unbakedItem = ItemModels.basic(itemModelGenerator.upload(item, Models.GENERATED));
        ItemModel.Unbaked unbakedHotItem = ItemModels.basic(Models.GENERATED.upload(ModelIds.getItemSubModelId(item, "_hot"),
                TextureMap.layer0(MiddleEarth.ofPath( "item", "nugget_hot")), itemModelGenerator.modelCollector));

        itemModelGenerator.output.accept(item, ItemModels.condition(new HotComponentProperty(), unbakedHotItem, unbakedItem));
    }

    public final void registerHotItem(Item item, ItemModelGenerator itemModelGenerator) {
        ItemModel.Unbaked unbakedItem = ItemModels.basic(itemModelGenerator.upload(item, Models.GENERATED));
        ItemModel.Unbaked unbakedHotItem = ItemModels.basic(itemModelGenerator.registerSubModel(item, "_hot", Models.GENERATED));

        itemModelGenerator.output.accept(item, ItemModels.condition(new HotComponentProperty(), unbakedHotItem, unbakedItem));
    }

}
