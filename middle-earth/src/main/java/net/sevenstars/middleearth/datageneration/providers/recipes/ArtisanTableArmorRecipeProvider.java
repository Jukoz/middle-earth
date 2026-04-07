package net.sevenstars.middleearth.datageneration.providers.recipes;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.impl.recipe.ingredient.builtin.ComponentsIngredient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.equipment.trim.ArmorTrimPattern;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.forge.MetalTypes;
import net.sevenstars.middleearth.datageneration.custom.ArtisanTableRecipeJsonBuilder;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.utils.SmithingTrimPatternsME;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;

import java.util.concurrent.CompletableFuture;

public class ArtisanTableArmorRecipeProvider extends RecipeProvider {

    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup;

    public ArtisanTableArmorRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);

        this.registryLookup = registriesFuture;
    }

    @Override
    public String getName() {
        return "ArtisanTableArmorRecipes";
    }

    public RegistryWrapper.Impl<ArmorTrimMaterial> getArmorTrimMaterialsRegistry(){
        RegistryWrapper.Impl<ArmorTrimMaterial> armorTrimMaterialsRegistry;

        try {
            armorTrimMaterialsRegistry = this.registryLookup.get().getOrThrow(RegistryKeys.TRIM_MATERIAL);
        } catch (Exception ignored) {
            throw new IllegalStateException("Data generation without registries failed!");
        }
        return armorTrimMaterialsRegistry;
    }

    public RegistryWrapper.Impl<ArmorTrimPattern> getArmorTrimPatternsRegistry(){
        RegistryWrapper.Impl<ArmorTrimPattern> armorTrimPatternsRegistry;

        try {
            armorTrimPatternsRegistry = this.registryLookup.get().getOrThrow(RegistryKeys.TRIM_PATTERN);
        } catch (Exception ignored) {
            throw new IllegalStateException("Data generation without registries failed!");
        }
        return armorTrimPatternsRegistry;
    }

    public RegistryEntry<ArmorTrimPattern> getPattern(){
        return getArmorTrimPatternsRegistry().getOrThrow(SmithingTrimPatternsME.SMITHING_PART);
    }

    public Identifier getMetalIdentifier(MetalTypes metal){
        if (metal.isVanilla()){
            return Identifier.of(metal.getName());
        } else {
            return Identifier.of(MiddleEarth.MOD_ID, metal.getName());
        }
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);

                    ItemStack goldArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
                    goldArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MetalTypes.GOLD.getName()))), getPattern()));

                    ItemStack steelArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
                    steelArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName()))), getPattern()));
                    ItemStack steelHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
                    steelHelmetPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName()))), getPattern()));
                    ItemStack steelChainmail = new ItemStack(ResourceItemsME.MAIL);
                    steelChainmail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName()))), getPattern()));
                    ItemStack steelScaleMail = new ItemStack(ResourceItemsME.SCALE_MAIL);
                    steelScaleMail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName()))), getPattern()));

                    ItemStack edhelSteelArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
                    edhelSteelArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName()))), getPattern()));
                    ItemStack edhelSteelHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
                    edhelSteelHelmetPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName()))), getPattern()));
                    ItemStack edhelSteelChainmail = new ItemStack(ResourceItemsME.MAIL);
                    edhelSteelChainmail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName()))), getPattern()));
                    ItemStack edhelSteelScaleMail = new ItemStack(ResourceItemsME.SCALE_MAIL);
                    edhelSteelScaleMail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName()))), getPattern()));

                    ItemStack khazadSteelArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
                    khazadSteelArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName()))), getPattern()));
                    ItemStack khazadSteelHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
                    khazadSteelHelmetPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName()))), getPattern()));
                    ItemStack khazadSteelChainmail = new ItemStack(ResourceItemsME.MAIL);
                    khazadSteelChainmail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName()))), getPattern()));
                    ItemStack khazadSteelScaleMail = new ItemStack(ResourceItemsME.SCALE_MAIL);
                    khazadSteelScaleMail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName()))), getPattern()));

                    ItemStack burzumSteelArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
                    burzumSteelArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName()))), getPattern()));
                    ItemStack burzumSteelHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
                    burzumSteelHelmetPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName()))), getPattern()));
                    ItemStack burzumSteelChainmail = new ItemStack(ResourceItemsME.MAIL);
                    burzumSteelChainmail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName()))), getPattern()));
                    ItemStack burzumSteelScaleMail = new ItemStack(ResourceItemsME.SCALE_MAIL);
                    burzumSteelScaleMail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName()))), getPattern()));

                    ItemStack ironArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
                    ironArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MetalTypes.IRON.getName()))), getPattern()));
                    ItemStack ironHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
                    ironHelmetPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MetalTypes.IRON.getName()))), getPattern()));
                    ItemStack ironChainmail = new ItemStack(ResourceItemsME.MAIL);
                    ironChainmail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MetalTypes.IRON.getName()))), getPattern()));

                    ItemStack bronzeArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
                    bronzeArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.BRONZE.getName()))), getPattern()));

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.SHIRRIFF_HAT.getDefaultStack(), "hat", DispositionType.GOOD)
                            .input(Items.STRING)
                            .input(Items.LEATHER)
                            .input(Items.FEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    //region MEN

                    //region GENERIC

                    //T1
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.STRAW_HAT.getDefaultStack(), "hat", DispositionType.NEUTRAL)
                            .input(Items.STRING)
                            .input(ResourceItemsME.STRAW)
                            .input(Items.STRING)
                            .input(ResourceItemsME.STRAW)
                            .input(ResourceItemsME.STRAW)
                            .criterion(hasItem(ResourceItemsME.STRAW),
                                    conditionsFromItem(ResourceItemsME.STRAW))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOVEN_HAT.getDefaultStack(), "hat", DispositionType.NEUTRAL)
                            .input(Items.STRING)
                            .input(Items.WHITE_WOOL)
                            .input(Items.STRING)
                            .input(Items.STRING)
                            .input(Items.STRING)
                            .criterion(hasItem(Items.WHITE_WOOL),
                                    conditionsFromItem(Items.WHITE_WOOL))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BYCOCKET.getDefaultStack(), "hat", DispositionType.NEUTRAL)
                            .input(Items.LEATHER)
                            .input(Items.WHITE_WOOL)
                            .input(Items.LEATHER)
                            .input(Items.STRING)
                            .input(Items.STRING)
                            .criterion(hasItem(Items.WHITE_WOOL),
                                    conditionsFromItem(Items.WHITE_WOOL))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WANDERER_HAT.getDefaultStack(), "hat", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ARMING_COAT.getDefaultStack(), "chestplate", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ARMING_SKIRT.getDefaultStack(), "leggings", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.SHOES.getDefaultStack(), "boots", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WORK_SHOES.getDefaultStack(), "boots", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    //T2
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LEATHER_SKULLCAP.getDefaultStack(), "helmet", DispositionType.NEUTRAL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GAMBESON_CAP.getDefaultStack(), "helmet", DispositionType.NEUTRAL)
                            .input(Items.LEATHER)
                            .input(Items.WHITE_WOOL)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GAMBESON_COWL.getDefaultStack(), "helmet", DispositionType.NEUTRAL)
                            .input(Items.LEATHER)
                            .input(Items.WHITE_WOOL)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.KETTLE_HAT.getDefaultStack(), "helmet", DispositionType.NEUTRAL)
                            .input(Items.IRON_NUGGET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .input(Items.IRON_NUGGET)
                            .input(Items.IRON_NUGGET)
                            .input(Items.IRON_NUGGET)
                            .criterion(hasItem(ironArmorPlate.getItem()),
                                    conditionsFromItem(ironArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GAMBESON.getDefaultStack(), "chestplate", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(Items.WHITE_WOOL)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LEATHER_VEST.getDefaultStack(), "chestplate", DispositionType.NEUTRAL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LEATHER_SCALE_VEST.getDefaultStack(), "chestplate", DispositionType.NEUTRAL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.STURDY_BOOTS.getDefaultStack(), "boots", DispositionType.NEUTRAL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.TRAVELLING_BOOTS.getDefaultStack(), "boots", DispositionType.NEUTRAL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.HIGH_CUT_BOOTS.getDefaultStack(), "boots", DispositionType.NEUTRAL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);
                    //T3
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MAIL_COIF.getDefaultStack(), "helmet", DispositionType.NEUTRAL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.CLOSED_MAIL_COIF.getDefaultStack(), "helmet", DispositionType.NEUTRAL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.KETTLE_HAT_WITH_COIF.getDefaultStack(), "helmet", DispositionType.NEUTRAL)
                            .input(EquipmentItemsME.MAIL_COIF)
                            .input(EquipmentItemsME.KETTLE_HAT)
                            .criterion(hasItem(EquipmentItemsME.MAIL_COIF),
                                    conditionsFromItem(EquipmentItemsME.MAIL_COIF))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.KETTLE_HAT_WITH_CLOSED_COIF.getDefaultStack(), "helmet", DispositionType.NEUTRAL)
                            .input(EquipmentItemsME.CLOSED_MAIL_COIF)
                            .input(EquipmentItemsME.KETTLE_HAT)
                            .criterion(hasItem(EquipmentItemsME.CLOSED_MAIL_COIF),
                                    conditionsFromItem(EquipmentItemsME.CLOSED_MAIL_COIF))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.OPEN_FACE_HELMET.getDefaultStack(), "helmet", DispositionType.NEUTRAL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.NEUTRAL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MAIL_SHIRT.getDefaultStack(), "chestplate", DispositionType.NEUTRAL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MAIL_SKIRT.getDefaultStack(), "leggings", DispositionType.NEUTRAL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    //T4
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.SALLET.getDefaultStack(), "helmet", DispositionType.NEUTRAL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    //HOODS
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.HOOD.getDefaultStack(), "helmetAttachment", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.TALL_HOOD.getDefaultStack(), "helmetAttachment", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_FUR_HOOD.getDefaultStack(), "helmetAttachment", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FUR)
                            .input(EquipmentItemsME.HOOD)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FUR)
                            .criterion(hasItem(EquipmentItemsME.HOOD),
                                    conditionsFromItem(EquipmentItemsME.HOOD))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BROWN_FUR_HOOD.getDefaultStack(), "helmetAttachment", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FUR)
                            .input(EquipmentItemsME.HOOD)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FUR)
                            .criterion(hasItem(EquipmentItemsME.HOOD),
                                    conditionsFromItem(EquipmentItemsME.HOOD))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GRAY_FUR_HOOD.getDefaultStack(), "helmetAttachment", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FUR)
                            .input(EquipmentItemsME.HOOD)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FUR)
                            .criterion(hasItem(EquipmentItemsME.HOOD),
                                    conditionsFromItem(EquipmentItemsME.HOOD))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.TAN_FUR_HOOD.getDefaultStack(), "helmetAttachment", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FUR)
                            .input(EquipmentItemsME.HOOD)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FUR)
                            .criterion(hasItem(EquipmentItemsME.HOOD),
                                    conditionsFromItem(EquipmentItemsME.HOOD))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WHITE_FUR_HOOD.getDefaultStack(), "helmetAttachment", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FUR)
                            .input(EquipmentItemsME.HOOD)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FUR)
                            .criterion(hasItem(EquipmentItemsME.HOOD),
                                    conditionsFromItem(EquipmentItemsME.HOOD))
                            .offerTo(exporter);

                    //CAPES
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.CAPE.getDefaultStack(), "backAttachment", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.SURCOAT.getDefaultStack(), "backAttachment", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.CLOAK.getDefaultStack(), "backAttachment", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WANDERER_ROBES.getDefaultStack(), "backAttachment", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);


                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_FUR_CLOAK.getDefaultStack(), "backAttachment", DispositionType.NEUTRAL)
                            .input(Items.STRING)
                            .input(EquipmentItemsME.BLACK_FUR)
                            .input(Items.STRING)
                            .input(Items.STRING)
                            .input(EquipmentItemsME.CLOAK)
                            .input(Items.STRING)
                            .criterion(hasItem(EquipmentItemsME.CLOAK),
                                    conditionsFromItem(EquipmentItemsME.CLOAK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BROWN_FUR_CLOAK.getDefaultStack(), "backAttachment", DispositionType.NEUTRAL)
                            .input(Items.STRING)
                            .input(EquipmentItemsME.BROWN_FUR)
                            .input(Items.STRING)
                            .input(Items.STRING)
                            .input(EquipmentItemsME.CLOAK)
                            .input(Items.STRING)
                            .criterion(hasItem(EquipmentItemsME.CLOAK),
                                    conditionsFromItem(EquipmentItemsME.CLOAK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GRAY_FUR_CLOAK.getDefaultStack(), "backAttachment", DispositionType.NEUTRAL)
                            .input(Items.STRING)
                            .input(EquipmentItemsME.GRAY_FUR)
                            .input(Items.STRING)
                            .input(Items.STRING)
                            .input(EquipmentItemsME.CLOAK)
                            .input(Items.STRING)
                            .criterion(hasItem(EquipmentItemsME.CLOAK),
                                    conditionsFromItem(EquipmentItemsME.CLOAK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.TAN_FUR_CLOAK.getDefaultStack(), "backAttachment", DispositionType.NEUTRAL)
                            .input(Items.STRING)
                            .input(EquipmentItemsME.TAN_FUR)
                            .input(Items.STRING)
                            .input(Items.STRING)
                            .input(EquipmentItemsME.CLOAK)
                            .input(Items.STRING)
                            .criterion(hasItem(EquipmentItemsME.CLOAK),
                                    conditionsFromItem(EquipmentItemsME.CLOAK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WHITE_FUR_CLOAK.getDefaultStack(), "backAttachment", DispositionType.NEUTRAL)
                            .input(Items.STRING)
                            .input(EquipmentItemsME.WHITE_FUR)
                            .input(Items.STRING)
                            .input(Items.STRING)
                            .input(EquipmentItemsME.CLOAK)
                            .input(Items.STRING)
                            .criterion(hasItem(EquipmentItemsME.CLOAK),
                                    conditionsFromItem(EquipmentItemsME.CLOAK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_FUR.getDefaultStack(), "backAttachment", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FUR)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BROWN_FUR.getDefaultStack(), "backAttachment", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FUR)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GRAY_FUR.getDefaultStack(), "backAttachment", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FUR)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.TAN_FUR.getDefaultStack(), "backAttachment", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FUR)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WHITE_FUR.getDefaultStack(), "backAttachment", DispositionType.NEUTRAL)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FUR)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);
                    //endregion

                    //region GONDOR
                    //T2
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    //T3
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CABASSET_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_LEATHER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_LEATHER_CUIRASS.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_TABBARD.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    //T4
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_SOLDIER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_SOLDIER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK)
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    //T5 PLATE
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_PLATE_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(EquipmentItemsME.GONDORIAN_SOLDIER_CHESTPLATE)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.GONDORIAN_SOLDIER_CHESTPLATE),
                                    conditionsFromItem(EquipmentItemsME.GONDORIAN_SOLDIER_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.GONDORIAN_MAIL_COAT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.GONDORIAN_MAIL_COAT),
                                    conditionsFromItem(EquipmentItemsME.GONDORIAN_MAIL_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_PLATE_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelArmorPlate.getItem()),
                                    conditionsFromItem(steelArmorPlate.getItem()))
                            .offerTo(exporter);

                    //T5 CAPTAIN
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CAPTAIN_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.GONDORIAN_PLATE_HELMET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_HELMET),
                                    conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_HELMET))
                            .offerTo(exporter);

                    //T5 KING'S GUARD
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_KINGS_GUARD_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(Items.FEATHER)
                            .input(EquipmentItemsME.GONDORIAN_PLATE_HELMET)
                            .input(Items.FEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_HELMET),
                                    conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_KINGS_GUARD_CHESTKPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE)
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE),
                                    conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_KINGS_GUARD_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS),
                                    conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_KINGS_GUARD_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(goldArmorPlate.getItem()),
                                    conditionsFromItem(goldArmorPlate.getItem()))
                            .offerTo(exporter);

                    //T5 CITADEL GUARD
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CITADEL_GUARD_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.GONDORIAN_PLATE_HELMET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_HELMET),
                                    conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CITADEL_GUARD_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE)
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE),
                                    conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CITADEL_GUARD_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS),
                                    conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CITADEL_GUARD_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(goldArmorPlate.getItem()),
                                    conditionsFromItem(goldArmorPlate.getItem()))
                            .offerTo(exporter);

                    //T5 FOUNTAIN GUARD
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(ResourceItemsME.MITHRIL_NUGGET)
                            .input(EquipmentItemsME.GONDORIAN_CAPTAIN_HELMET)
                            .input(ResourceItemsME.MITHRIL_NUGGET)
                            .input(ResourceItemsME.SWAN_FEATHER)
                            .input(ResourceItemsME.SWAN_FEATHER)
                            .criterion(hasItem(EquipmentItemsME.GONDORIAN_CAPTAIN_HELMET),
                                    conditionsFromItem(EquipmentItemsME.GONDORIAN_CAPTAIN_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE)
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE),
                                    conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS),
                                    conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(goldArmorPlate.getItem()),
                                    conditionsFromItem(goldArmorPlate.getItem()))
                            .offerTo(exporter);

                    //HOODS
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CITADEL_GUARD_HOOD.getDefaultStack(), "helmetAttachment", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    //CAPES
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CAPTAIN_CAPE.getDefaultStack(), "backAttachment", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.IRON_NUGGET)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(Items.WHITE_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.WHITE_DYE)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_HERO_CAPE.getDefaultStack(), "backAttachment", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.IRON_NUGGET)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.WHITE_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.WHITE_DYE)
                            .input(Items.BLACK_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLACK_DYE)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_KINGS_GUARD_CAPE.getDefaultStack(), "backAttachment", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.GOLD_NUGGET)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(Items.WHITE_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.WHITE_DYE)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CITADEL_GUARD_CAPE.getDefaultStack(), "backAttachment", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.GOLD_NUGGET)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(Items.YELLOW_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.YELLOW_DYE)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_CAPE.getDefaultStack(), "backAttachment", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.GOLD_NUGGET)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLACK_DYE)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);
                    //endregion

                    //region ROHAN
                    //T1
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_REINFORCED_COAT.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.ARMING_COAT)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.ARMING_COAT),
                                    conditionsFromItem(EquipmentItemsME.ARMING_COAT))
                            .offerTo(exporter);

                    //T2
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_LEATHER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_REINFORCED_LEATHER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.IRON_NUGGET)
                            .input(Items.IRON_NUGGET)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_ORNAMENTED_LEATHER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.BRONZE_NUGGET)
                            .input(ResourceItemsME.BRONZE_NUGGET)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_LEATHER_VEST.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.ARMING_COAT)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(EquipmentItemsME.ARMING_COAT),
                                    conditionsFromItem(EquipmentItemsME.ARMING_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.ARMING_COAT)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.ARMING_COAT),
                                    conditionsFromItem(EquipmentItemsME.ARMING_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_GAMBESON.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.GAMBESON)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(EquipmentItemsME.GAMBESON),
                                    conditionsFromItem(EquipmentItemsME.GAMBESON))
                            .offerTo(exporter);

                    //T3
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_MILITIA_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .criterion(hasItem(bronzeArmorPlate.getItem()),
                                    conditionsFromItem(bronzeArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_BRACED_MILITIA_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .criterion(hasItem(ironArmorPlate.getItem()),
                                    conditionsFromItem(ironArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_REINFORCED_MILITIA_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .criterion(hasItem(bronzeArmorPlate.getItem()),
                                    conditionsFromItem(bronzeArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_ORNAMENTED_MILITIA_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .criterion(hasItem(bronzeArmorPlate.getItem()),
                                    conditionsFromItem(bronzeArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_REINFORCED_LEATHER_VEST.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.MAIL_HAUBERK)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_REINFORCED_LEATHER_SCALE_VEST.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.MAIL_HAUBERK)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_BRACED_MAIL_SHIRT.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.MAIL_HAUBERK)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                            .offerTo(exporter);

                    ///T4
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_SOLDIER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_ORNAMENTED_SOLDIER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_ROYAL_GUARD_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_ORNAMENTED_SCALE_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.GREEN_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.GREEN_DYE)
                            .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_SCALE_JACKET.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                            .offerTo(exporter);

                    //T5 EORLING MARSHAL
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EORLING_MARSHAL_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EORLING_MARSHAL_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EORLING_MARSHAL_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.ROHIRRIC_SCALE_JACKET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.ROHIRRIC_SCALE_JACKET),
                                    conditionsFromItem(EquipmentItemsME.ROHIRRIC_SCALE_JACKET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EORLING_MARSHAL_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(goldArmorPlate.getItem()),
                                    conditionsFromItem(goldArmorPlate.getItem()))
                            .offerTo(exporter);

                    //T5 HORSE LORD
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.HORSE_LORD_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.HORSE_LORD_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.HORSE_LORD_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.ROHIRRIC_SCALE_JACKET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.ROHIRRIC_SCALE_JACKET),
                                    conditionsFromItem(EquipmentItemsME.ROHIRRIC_SCALE_JACKET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.HORSE_LORD_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(goldArmorPlate.getItem()),
                                    conditionsFromItem(goldArmorPlate.getItem()))
                            .offerTo(exporter);

                    //CAPES
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_CAPE.getDefaultStack(), "backAttachment", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.BRONZE_NUGGET)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.GREEN_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.GREEN_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_ROYAL_GUARD_CAPE.getDefaultStack(), "backAttachment", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.BRONZE_NUGGET)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.YELLOW_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.YELLOW_DYE)
                            .input(Items.GREEN_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.GREEN_DYE)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EORLING_MARSHAL_CAPE.getDefaultStack(), "backAttachment", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.STEEL_NUGGET)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.GREEN_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.GREEN_DYE)
                            .input(Items.YELLOW_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.WHITE_DYE)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.HORSE_LORD_CAPE.getDefaultStack(), "backAttachment", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.BRONZE_NUGGET)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLUE_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLUE_DYE)
                            .input(Items.RED_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.RED_DYE)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);
                    //endregion

                    //region DALE

                    //T1
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_ARMING_COAT_BLACK_FUR.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FUR)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_ARMING_COAT_BROWN_FUR.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FUR)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_ARMING_COAT_TAN_FUR.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FUR)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HEYDAY_ARMING_COAT.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.ORANGE_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.ORANGE_DYE)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    //T2
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    //T3
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HELMET_BLACK_FUR.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(ResourceItemsME.FUR)
                            .input(EquipmentItemsME.DALISH_HELMET)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .criterion(hasItem(EquipmentItemsME.DALISH_HELMET),
                                    conditionsFromItem(EquipmentItemsME.DALISH_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HELMET_BROWN_FUR.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(ResourceItemsME.FUR)
                            .input(EquipmentItemsME.DALISH_HELMET)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .criterion(hasItem(EquipmentItemsME.DALISH_HELMET),
                                    conditionsFromItem(EquipmentItemsME.DALISH_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HELMET_TAN_FUR.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(ResourceItemsME.FUR)
                            .input(EquipmentItemsME.DALISH_HELMET)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .criterion(hasItem(EquipmentItemsME.DALISH_HELMET),
                                    conditionsFromItem(EquipmentItemsME.DALISH_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.MAIL_HAUBERK)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .input(EquipmentItemsME.MAIL_SKIRT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                            .offerTo(exporter);

                    //T4
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_BURGONET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_SCALE_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    //T4 HEYDAY
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HEYDAY_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.DALISH_HELMET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .criterion(hasItem(EquipmentItemsME.DALISH_HELMET),
                                    conditionsFromItem(EquipmentItemsME.DALISH_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HEYDAY_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.DALISH_SCALE_HAUBERK)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.ORANGE_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.ORANGE_DYE)
                            .criterion(hasItem(EquipmentItemsME.DALISH_SCALE_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HEYDAY_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.DALISH_MAIL_COAT)
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.DALISH_MAIL_COAT),
                                    conditionsFromItem(EquipmentItemsME.DALISH_MAIL_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HEYDAY_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelArmorPlate.getItem()),
                                    conditionsFromItem(steelArmorPlate.getItem()))
                            .offerTo(exporter);

                    //T5 BARDING
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BARDING_SOLDIER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BARDING_SOLDIER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(EquipmentItemsME.DALISH_SCALE_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.DALISH_SCALE_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BARDING_MAIL_SKIRT.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.DALISH_MAIL_COAT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.DALISH_MAIL_COAT),
                                    conditionsFromItem(EquipmentItemsME.DALISH_MAIL_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BARDING_PLATED_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelArmorPlate.getItem()),
                                    conditionsFromItem(steelArmorPlate.getItem()))
                            .offerTo(exporter);

                    //T5 BARDING SERGEANT
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BARDING_SERGEANT_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(Items.FEATHER)
                            .input(EquipmentItemsME.BARDING_SOLDIER_HELMET)
                            .input(Items.FEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.BARDING_SOLDIER_HELMET),
                                    conditionsFromItem(EquipmentItemsME.BARDING_SOLDIER_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BARDING_SERGEANT_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.BARDING_SOLDIER_CHESTPLATE)
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.BARDING_SERGEANT_CHESTPLATE),
                                    conditionsFromItem(EquipmentItemsME.BARDING_SERGEANT_CHESTPLATE))
                            .offerTo(exporter);

                    //CAPES
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BARDING_SURCOAT.getDefaultStack(), "backAttachment", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.PURPLE_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BARDING_SERGEANT_CAPE.getDefaultStack(), "backAttachment", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.ORANGE_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.ORANGE_DYE)
                            .input(Items.PURPLE_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.PURPLE_DYE)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HEYDAY_CLOAK.getDefaultStack(), "backAttachment", DispositionType.GOOD)
                            .input(ResourceItemsME.BRONZE_NUGGET)
                            .input(Items.STRING)
                            .input(ResourceItemsME.BRONZE_NUGGET)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.RED_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.RED_DYE)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);
//endregion

                    //region BLACK NUMENOREANS

                    //endregion

                    //endregion

                    //region ELVES

                    //region GENERIC

                    //T1
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_ARMING_COAT.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_ARMING_SKIRT.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_OPEN_ARMING_SKIRT.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    //T2
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    //T3
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_MAIL_COIF.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_MAIL_SKIRT.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    //endregion

                    //region LOTHLORIEN
                    //T1
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_DIADEM.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(ResourceItemsME.BRONZE_NUGGET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.BRONZE_NUGGET)
                            .criterion(hasItem(bronzeArmorPlate.getItem()),
                                    conditionsFromItem(bronzeArmorPlate.getItem()))
                            .offerTo(exporter);

                    //T2
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_LEATHER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_ARMING_COAT.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.ELVEN_ARMING_COAT)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.ELVEN_ARMING_COAT),
                                    conditionsFromItem(EquipmentItemsME.ELVEN_ARMING_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_ARMING_SKIRT.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(EquipmentItemsME.ELVEN_ARMING_SKIRT)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(EquipmentItemsME.ELVEN_ARMING_SKIRT),
                                    conditionsFromItem(EquipmentItemsME.ELVEN_ARMING_SKIRT))
                            .offerTo(exporter);

                    //T3
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_MAIL_COIF_DIADEM.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(EquipmentItemsME.ELVEN_MAIL_COIF)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .input(EquipmentItemsME.LORIEN_DIADEM)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.ELVEN_MAIL_COIF),
                                    conditionsFromItem(EquipmentItemsME.ELVEN_MAIL_COIF))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_SHORT_MAIL_COIF_DIADEM.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(EquipmentItemsME.ELVEN_MAIL_COIF)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .input(EquipmentItemsME.LORIEN_DIADEM)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.ELVEN_MAIL_COIF),
                                    conditionsFromItem(EquipmentItemsME.ELVEN_MAIL_COIF))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(EquipmentItemsME.ELVEN_MAIL_HAUBERK)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.ELVEN_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.ELVEN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_MARCHWARDEN_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.ELVEN_MAIL_HAUBERK)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.ELVEN_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.ELVEN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    //T4
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_SOLDIER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelHelmetPlate.getItem()), edhelSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(edhelSteelHelmetPlate.getItem()),
                                    conditionsFromItem(edhelSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_SOLDIER_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.LORIEN_MAIL_HAUBERK)
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.LORIEN_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.LORIEN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(edhelSteelScaleMail.getItem()),
                                    conditionsFromItem(edhelSteelScaleMail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_SCALE_COAT.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .criterion(hasItem(edhelSteelScaleMail.getItem()),
                                    conditionsFromItem(edhelSteelScaleMail.getItem()))
                            .offerTo(exporter);

                    //T5 GALADHRIM
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelHelmetPlate.getItem()), edhelSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(edhelSteelHelmetPlate.getItem()),
                                    conditionsFromItem(edhelSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .input(EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.LORIEN_SCALE_COAT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.LORIEN_SCALE_COAT),
                                    conditionsFromItem(EquipmentItemsME.LORIEN_SCALE_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(edhelSteelArmorPlate.getItem()),
                                    conditionsFromItem(edhelSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    //T5 GALADHRIM LORD
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_LORD_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.GALADHRIM_HELMET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.GALADHRIM_HELMET),
                                    conditionsFromItem(EquipmentItemsME.GALADHRIM_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_LORD_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.GALADHRIM_CHESTPLATE)
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.GALADHRIM_CHESTPLATE),
                                    conditionsFromItem(EquipmentItemsME.GALADHRIM_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_LORD_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.GALADHRIM_LEGGINGS)
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.GALADHRIM_LEGGINGS),
                                    conditionsFromItem(EquipmentItemsME.GALADHRIM_LEGGINGS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_LORD_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(edhelSteelHelmetPlate.getItem()),
                                    conditionsFromItem(edhelSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    //HOODS
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_MARCHWARDEN_HOOD.getDefaultStack(), "helmetAttachment", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.GRAY_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_HOOD.getDefaultStack(), "helmetAttachment", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLUE_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    //CAPES
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_MARCHWARDEN_CAPE.getDefaultStack(), "backAttachment", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.BRONZE_NUGGET)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.GRAY_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.GRAY_DYE)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_CAPE.getDefaultStack(), "backAttachment", DispositionType.GOOD)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLUE_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLUE_DYE)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_LORD_SURCOAT.getDefaultStack(), "backAttachment", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.WHITE_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);
                    //endregion

                    //endregion

                    //region DWARVES

                    //region GENERIC
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MINER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(Items.CANDLE)
                            .input(Items.LEATHER)
                            .input(Items.CANDLE)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_GAMBESON.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(Items.WHITE_WOOL)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MAIL_COIF.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MINER_GAMBESON.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(EquipmentItemsME.DWARVEN_MAIL_HAUBERK)
                            .input(EquipmentItemsME.DWARVEN_GAMBESON)
                            .criterion(hasItem(EquipmentItemsME.DWARVEN_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.DWARVEN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MAIL_CHAUSSES.getDefaultStack(), "boots", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .input(Items.IRON_INGOT)
                            .input(Items.IRON_INGOT)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.IRON_INGOT),
                                    conditionsFromItem(Items.IRON_INGOT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_SCALE_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .criterion(hasItem(khazadSteelScaleMail.getItem()),
                                    conditionsFromItem(khazadSteelScaleMail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_SCALE_COAT.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .criterion(hasItem(khazadSteelScaleMail.getItem()),
                                    conditionsFromItem(khazadSteelScaleMail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_REINFORCED_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                            .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.IRON_INGOT),
                                    conditionsFromItem(Items.IRON_INGOT))
                            .offerTo(exporter);
                    //endregion

                    //region EREBOR
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LONGBEARD_SEGMENTED_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(bronzeArmorPlate.getItem()),
                                    conditionsFromItem(bronzeArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LONGBEARD_LEATHER_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.BRONZE_NUGGET)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LONGBEARD_WANDERER_COAT.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.IRON_NUGGET)
                            .input(Items.LEATHER)
                            .input(Items.IRON_NUGGET)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LONGBEARD_LEATHER_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.BRONZE_NUGGET)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LONGBEARD_PARTISAN_OUTFIT.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.DWARVEN_MAIL_HAUBERK)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(Items.IRON_NUGGET)
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.DWARVEN_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.DWARVEN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LONGBEARD_REINFORCED_LEATHER_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(EquipmentItemsME.DWARVEN_MAIL_HAUBERK)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.BRONZE_NUGGET)
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.DWARVEN_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.DWARVEN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_LEATHER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(Items.IRON_INGOT)
                            .input(Items.LEATHER)
                            .input(Items.IRON_INGOT)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_BRACED_LEATHER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(Items.IRON_INGOT)
                            .input(Items.LEATHER)
                            .input(Items.IRON_INGOT)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_NASAL_LEATHER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(Items.IRON_INGOT)
                            .input(Items.LEATHER)
                            .input(Items.IRON_INGOT)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_LEATHER_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.BRONZE_NUGGET)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_MAIL_COIF.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_GILDED_MAIL_COIF.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(ResourceItemsME.BRONZE_NUGGET)
                            .input(EquipmentItemsME.EREBOR_MAIL_COIF)
                            .input(ResourceItemsME.BRONZE_NUGGET)
                            .input(ResourceItemsME.BRONZE_NUGGET)
                            .input(ResourceItemsME.BRONZE_NUGGET)
                            .criterion(hasItem(EquipmentItemsME.EREBOR_MAIL_COIF),
                                    conditionsFromItem(EquipmentItemsME.EREBOR_MAIL_COIF))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .input(Items.IRON_NUGGET)
                            .input(Items.IRON_NUGGET)
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_PADDED_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(EquipmentItemsME.EREBOR_MAIL_HAUBERK)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.BRONZE_INGOT)
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_MAIL_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_MAIL_CHAUSSES.getDefaultStack(), "boots", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.IRON_INGOT)
                            .input(Items.IRON_INGOT)
                            .criterion(hasItem(Items.IRON_INGOT),
                                    conditionsFromItem(Items.IRON_INGOT))
                            .offerTo(exporter);

                    //t4
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelHelmetPlate.getItem()), khazadSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(khazadSteelHelmetPlate.getItem()),
                                    conditionsFromItem(khazadSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_GILDED_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(EquipmentItemsME.EREBOR_GILDED_MAIL_COIF)
                            .input(EquipmentItemsME.RAVENHILL_HELMET)
                            .criterion(hasItem(EquipmentItemsME.RAVENHILL_HELMET),
                                    conditionsFromItem(EquipmentItemsME.RAVENHILL_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_SCALE_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .criterion(hasItem(khazadSteelScaleMail.getItem()),
                                    conditionsFromItem(khazadSteelScaleMail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_LONG_COAT.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .input(EquipmentItemsME.EREBOR_MAIL_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.EREBOR_MAIL_HAUBERK)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(Items.BLUE_DYE)
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_REINFORCED_LEATHER_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(EquipmentItemsME.EREBOR_MAIL_HAUBERK)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_REINFORCED_COAT.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.EREBOR_MAIL_HAUBERK)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(Items.BLUE_DYE)
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_REINFORCED_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .input(EquipmentItemsME.EREBOR_MAIL_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_SCALE_COAT.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .criterion(hasItem(khazadSteelScaleMail.getItem()),
                                    conditionsFromItem(khazadSteelScaleMail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_SCALE_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .criterion(hasItem(khazadSteelScaleMail.getItem()),
                                    conditionsFromItem(khazadSteelScaleMail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(khazadSteelArmorPlate.getItem()),
                                    conditionsFromItem(khazadSteelArmorPlate.getItem()))
                            .offerTo(exporter);
                    //t5
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_PLATE_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelHelmetPlate.getItem()), khazadSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(khazadSteelHelmetPlate.getItem()),
                                    conditionsFromItem(khazadSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .input(EquipmentItemsME.EREBOR_SCALE_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.EREBOR_SCALE_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.EREBOR_SCALE_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_PLATE_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.EREBOR_SCALE_LEGGINGS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.EREBOR_SCALE_LEGGINGS),
                                    conditionsFromItem(EquipmentItemsME.EREBOR_SCALE_LEGGINGS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_PLATE_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(khazadSteelArmorPlate.getItem()),
                                    conditionsFromItem(khazadSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_GUARD_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.EREBOR_PLATE_HELMET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.EREBOR_PLATE_HELMET),
                                    conditionsFromItem(EquipmentItemsME.EREBOR_PLATE_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_GATEWARDEN_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.EREBOR_PLATE_HELMET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.BRONZE_INGOT)
                            .input(ResourceItemsME.BRONZE_INGOT)
                            .criterion(hasItem(EquipmentItemsME.EREBOR_PLATE_HELMET),
                                    conditionsFromItem(EquipmentItemsME.EREBOR_PLATE_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_GATEWARDEN_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.BRONZE_INGOT)
                            .input(Items.BLUE_DYE)
                            .input(ResourceItemsME.BRONZE_INGOT)
                            .criterion(hasItem(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE),
                                    conditionsFromItem(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_GATEWARDEN_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.EREBOR_PLATE_LEGGINGS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(EquipmentItemsME.EREBOR_PLATE_LEGGINGS),
                                    conditionsFromItem(EquipmentItemsME.EREBOR_PLATE_LEGGINGS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_GATEWARDEN_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(khazadSteelArmorPlate.getItem()),
                                    conditionsFromItem(khazadSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_WATCHWARDEN_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.EREBOR_PLATE_HELMET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.EREBOR_PLATE_HELMET),
                                    conditionsFromItem(EquipmentItemsME.EREBOR_PLATE_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_WATCHWARDEN_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.BRONZE_INGOT)
                            .input(Items.GRAY_DYE)
                            .input(ResourceItemsME.BRONZE_INGOT)
                            .criterion(hasItem(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE),
                                    conditionsFromItem(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_WATCHWARDEN_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.EREBOR_PLATE_LEGGINGS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(EquipmentItemsME.EREBOR_PLATE_LEGGINGS),
                                    conditionsFromItem(EquipmentItemsME.EREBOR_PLATE_LEGGINGS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_WATCHWARDEN_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(khazadSteelArmorPlate.getItem()),
                                    conditionsFromItem(khazadSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_CAPTAIN_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(Items.GOAT_HORN)
                            .input(EquipmentItemsME.EREBOR_GUARD_HELMET)
                            .input(Items.GOAT_HORN)
                            .criterion(hasItem(EquipmentItemsME.EREBOR_GUARD_HELMET),
                                    conditionsFromItem(EquipmentItemsME.EREBOR_GUARD_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_SENTINEL_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD)
                            .input(Items.FEATHER)
                            .input(EquipmentItemsME.RAVENHILL_WATCHWARDEN_HELMET)
                            .input(Items.FEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.RAVENHILL_WATCHWARDEN_HELMET),
                                    conditionsFromItem(EquipmentItemsME.RAVENHILL_WATCHWARDEN_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_SENTINEL_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(EquipmentItemsME.RAVENHILL_WATCHWARDEN_CHESTPLATE)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(EquipmentItemsME.RAVENHILL_WATCHWARDEN_CHESTPLATE),
                                    conditionsFromItem(EquipmentItemsME.RAVENHILL_WATCHWARDEN_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_SENTINEL_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.RAVENHILL_WATCHWARDEN_LEGGINGS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(EquipmentItemsME.RAVENHILL_WATCHWARDEN_LEGGINGS),
                                    conditionsFromItem(EquipmentItemsME.RAVENHILL_WATCHWARDEN_LEGGINGS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_SENTINEL_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(khazadSteelArmorPlate.getItem()),
                                    conditionsFromItem(khazadSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    //capes
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_CAPE.getDefaultStack(), "backAttachment", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLUE_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLUE_DYE)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_SENTINEL_CAPE.getDefaultStack(), "backAttachment", DispositionType.GOOD)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.GRAY_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.GRAY_DYE)
                            .input(Items.YELLOW_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.YELLOW_DYE)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    //endregion

                    //endregion

                    //region ORCS

                    //region GENERIC
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LEATHER_STRAP.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_SANDALS.getDefaultStack(), "boots", DispositionType.EVIL)
                            .input(Items.STICK)
                            .input(Items.STICK)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LEATHER_SKIRT.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LEG_BRACER.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.IRON_NUGGET)
                            .input(Items.LEATHER)
                            .input(Items.IRON_NUGGET)
                            .input(Items.IRON_NUGGET)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_REINFORCED_STRIP_LEATHER_SKIRT.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BLACK_FUR_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ResourceItemsME.FUR),
                                    conditionsFromItem(ResourceItemsME.FUR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ResourceItemsME.FUR),
                                    conditionsFromItem(ResourceItemsME.FUR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_GRAY_FUR_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ResourceItemsME.FUR),
                                    conditionsFromItem(ResourceItemsME.FUR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_TAN_FUR_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ResourceItemsME.FUR),
                                    conditionsFromItem(ResourceItemsME.FUR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_WHITE_FUR_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ResourceItemsME.FUR),
                                    conditionsFromItem(ResourceItemsME.FUR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BRACED_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_MAIL_COIF.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_MAIL_SHIRT.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_REINFORCED_LEATHER_SKIRT.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_REINFORCED_STRIP_LEATHER_SKIRT.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_SALLET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BELLY_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelScaleMail.getItem()), burzumSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelScaleMail.getItem()), burzumSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelScaleMail.getItem()), burzumSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelScaleMail.getItem()), burzumSteelScaleMail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelScaleMail.getItem()),
                                    conditionsFromItem(burzumSteelScaleMail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_SCALE_VEST.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelScaleMail.getItem()), burzumSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelScaleMail.getItem()), burzumSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelScaleMail.getItem()), burzumSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelScaleMail.getItem()), burzumSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelScaleMail.getItem()), burzumSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelScaleMail.getItem()), burzumSteelScaleMail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelScaleMail.getItem()),
                                    conditionsFromItem(burzumSteelScaleMail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_REINFORCED_LEG_BRACER.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                    conditionsFromItem(burzumSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BRACED_SANDALS.getDefaultStack(), "boots", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges())).input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)

                            .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                    conditionsFromItem(burzumSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_CAPE.getDefaultStack(), "backAttachment", DispositionType.EVIL)
                            .input(ResourceItemsME.FABRIC)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LONG_CAPE.getDefaultStack(), "backAttachment", DispositionType.EVIL)
                            .input(ResourceItemsME.FABRIC)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_SHOULDERS.getDefaultStack(), "backAttachment", DispositionType.EVIL)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.RED_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE.getDefaultStack(), "backAttachment", DispositionType.EVIL)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(ResourceItemsME.FUR)
                            .input(Items.STRING)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(Items.STRING)
                            .criterion(hasItem(ResourceItemsME.FUR),
                                    conditionsFromItem(ResourceItemsME.FUR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BROWN_FUR_SURCOAT_WITH_BONE.getDefaultStack(), "backAttachment", DispositionType.EVIL)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(ResourceItemsME.FUR)
                            .input(Items.STRING)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(Items.STRING)
                            .criterion(hasItem(ResourceItemsME.FUR),
                                    conditionsFromItem(ResourceItemsME.FUR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_GRAY_FUR_SURCOAT_WITH_BONE.getDefaultStack(), "backAttachment", DispositionType.EVIL)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(ResourceItemsME.FUR)
                            .input(Items.STRING)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(Items.STRING)
                            .criterion(hasItem(ResourceItemsME.FUR),
                                    conditionsFromItem(ResourceItemsME.FUR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_TAN_FUR_SURCOAT_WITH_BONE.getDefaultStack(), "backAttachment", DispositionType.EVIL)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(ResourceItemsME.FUR)
                            .input(Items.STRING)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(Items.STRING)
                            .criterion(hasItem(ResourceItemsME.FUR),
                                    conditionsFromItem(ResourceItemsME.FUR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_WHITE_FUR_SURCOAT_WITH_BONE.getDefaultStack(), "backAttachment", DispositionType.EVIL)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(ResourceItemsME.FUR)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(ResourceItemsME.FUR)
                            .input(Items.STRING)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(Items.STRING)
                            .criterion(hasItem(ResourceItemsME.FUR),
                                    conditionsFromItem(ResourceItemsME.FUR))
                            .offerTo(exporter);
                    //endregion

                    //region MORDOR
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_KETTLE_HAT.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_NASAL_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_NASAL_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT_WITH_COIF.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .input(EquipmentItemsME.ORCISH_MAIL_COIF)
                            .input(EquipmentItemsME.MORDOR_KETTLE_HAT)
                            .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_COIF),
                                    conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_COIF))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_KETTLE_HAT_WITH_COIF.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .input(EquipmentItemsME.ORCISH_MAIL_COIF)
                            .input(EquipmentItemsME.MORDOR_KETTLE_HAT)
                            .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_COIF),
                                    conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_COIF))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_LEATHER_CUIRASS.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_PAINTED_LEATHER_CUIRASS.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.RED_DYE)
                            .input(EquipmentItemsME.MORDOR_LEATHER_CUIRASS)
                            .input(Items.RED_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS),
                                    conditionsFromItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_CREST_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_CREST_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_MANDIBLE_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_MANDIBLE_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_SALLET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_SALLET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_CUIRASS.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.MORDOR_LEATHER_CUIRASS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS),
                                    conditionsFromItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_CUIRASS.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.MORDOR_LEATHER_CUIRASS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS),
                                    conditionsFromItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_GORGET_HAUBERK.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_GORGET_HAUBERK.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_REINFORCED_COAT.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.MORDOR_LEATHER_CUIRASS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS),
                                    conditionsFromItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_REINFORCED_COAT.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.MORDOR_LEATHER_CUIRASS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS),
                                    conditionsFromItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_PLATE_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                    conditionsFromItem(burzumSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_PLATE_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                    conditionsFromItem(burzumSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_GREAT_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_GREAT_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_SNOUT_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_SNOUT_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_DEGRADED_GONDORIAN_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(Items.RED_DYE)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE),
                                    conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_PAINTED_CUIRASS.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(Items.RED_DYE)
                            .input(EquipmentItemsME.MORDOR_CUIRASS)
                            .input(Items.RED_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.RED_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(EquipmentItemsME.MORDOR_CUIRASS),
                                    conditionsFromItem(EquipmentItemsME.MORDOR_CUIRASS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_SCALE_COAT.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelScaleMail.getItem()), burzumSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelScaleMail.getItem()), burzumSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelScaleMail.getItem()), burzumSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelScaleMail.getItem()), burzumSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelScaleMail.getItem()), burzumSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelScaleMail.getItem()), burzumSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelScaleMail.getItem()), burzumSteelScaleMail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelScaleMail.getItem()),
                                    conditionsFromItem(burzumSteelScaleMail.getItem()))
                            .offerTo(exporter);

                    //T5
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_URUK_PLATE_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_URUK_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(EquipmentItemsME.MORDOR_CHESTPLATE)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.MORDOR_CHESTPLATE),
                                    conditionsFromItem(EquipmentItemsME.MORDOR_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_URUK_PLATE_LEGGINGS.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.ORCISH_MAIL_COAT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_COAT),
                                    conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_URUK_PLATE_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                    conditionsFromItem(burzumSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_URUK_FACE_PLATE_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_URUK_COMMANDER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .input(Items.SKELETON_SKULL)
                            .input(EquipmentItemsME.BLACK_URUK_PLATE_HELMET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.RED_DYE)
                            .input(Items.RED_DYE)
                            .criterion(hasItem(EquipmentItemsME.BLACK_URUK_PLATE_HELMET),
                                    conditionsFromItem(EquipmentItemsME.BLACK_URUK_PLATE_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_URUK_COMMANDER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.RED_DYE)
                            .input(EquipmentItemsME.BLACK_URUK_PLATE_CHESTPLATE)
                            .input(Items.RED_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.RED_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .criterion(hasItem(EquipmentItemsME.BLACK_URUK_PLATE_CHESTPLATE),
                                    conditionsFromItem(EquipmentItemsME.BLACK_URUK_PLATE_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.MAIL_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_LEGGINGS.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.MAIL_SKIRT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.MAIL_SKIRT),
                                    conditionsFromItem(EquipmentItemsME.MAIL_SKIRT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelArmorPlate.getItem()),
                                    conditionsFromItem(steelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_CAPE.getDefaultStack(), "backAttachment", DispositionType.EVIL)
                            .input(ResourceItemsME.STEEL_NUGGET)
                            .input(Items.STRING)
                            .input(ResourceItemsME.STEEL_NUGGET)
                            .input(Items.BLACK_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(Items.RED_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.RED_DYE)
                            .criterion(hasItem(ResourceItemsME.FABRIC),
                                    conditionsFromItem(ResourceItemsME.FABRIC))
                            .offerTo(exporter);

                    //endregion

                    //region ISENGARD
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_LEATHER_SCOUT_CAP.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_LEATHER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .input(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE)
                            .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                            .criterion(hasItem(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE),
                                    conditionsFromItem(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_LEATHER_HAUBERK.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_LIGHT_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_LIGHT_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .input(EquipmentItemsME.URUK_HAI_LIGHT_HELMET)
                            .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                            .criterion(hasItem(EquipmentItemsME.URUK_HAI_LIGHT_HELMET),
                                    conditionsFromItem(EquipmentItemsME.URUK_HAI_LIGHT_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_REINFORCED_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_REINFORCED_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .input(EquipmentItemsME.URUK_HAI_REINFORCED_HELMET)
                            .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                            .criterion(hasItem(EquipmentItemsME.URUK_HAI_REINFORCED_HELMET),
                                    conditionsFromItem(EquipmentItemsME.URUK_HAI_REINFORCED_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_MAIL_SKIRT.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(EquipmentItemsME.URUK_HAI_MAIL_COAT)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.URUK_HAI_MAIL_COAT),
                                    conditionsFromItem(EquipmentItemsME.URUK_HAI_MAIL_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_SOLDIER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_SOLDIER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .input(EquipmentItemsME.URUK_HAI_SOLDIER_HELMET)
                            .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                            .criterion(hasItem(EquipmentItemsME.URUK_HAI_SOLDIER_HELMET),
                                    conditionsFromItem(EquipmentItemsME.URUK_HAI_SOLDIER_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_CUIRASS.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.STRING)
                            .input(Items.LEATHER)
                            .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                    conditionsFromItem(burzumSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PLATE_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(EquipmentItemsME.URUK_HAI_CUIRASS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.URUK_HAI_CUIRASS),
                                    conditionsFromItem(EquipmentItemsME.URUK_HAI_CUIRASS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PLATE_LEGGINGS.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.URUK_HAI_MAIL_COAT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.URUK_HAI_MAIL_COAT),
                                    conditionsFromItem(EquipmentItemsME.URUK_HAI_MAIL_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PLATE_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                    conditionsFromItem(burzumSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_PLATE_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .input(EquipmentItemsME.URUK_HAI_PLATE_HELMET)
                            .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                            .criterion(hasItem(EquipmentItemsME.URUK_HAI_PLATE_HELMET),
                                    conditionsFromItem(EquipmentItemsME.URUK_HAI_PLATE_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_SAPPER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_SAPPER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .input(EquipmentItemsME.URUK_HAI_SAPPER_HELMET)
                            .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                            .criterion(hasItem(EquipmentItemsME.URUK_HAI_SAPPER_HELMET),
                                    conditionsFromItem(EquipmentItemsME.URUK_HAI_SAPPER_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_BERSERKER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_BERSERKER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .input(EquipmentItemsME.URUK_HAI_BERSERKER_HELMET)
                            .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                            .criterion(hasItem(EquipmentItemsME.URUK_HAI_BERSERKER_HELMET),
                                    conditionsFromItem(EquipmentItemsME.URUK_HAI_BERSERKER_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_COMMANDER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.URUK_HAI_PLATE_HELMET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.URUK_HAI_PLATE_HELMET),
                                    conditionsFromItem(EquipmentItemsME.URUK_HAI_PLATE_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_COMMANDER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .input(EquipmentItemsME.URUK_HAI_COMMANDER_HELMET)
                            .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                            .criterion(hasItem(EquipmentItemsME.URUK_HAI_COMMANDER_HELMET),
                                    conditionsFromItem(EquipmentItemsME.URUK_HAI_COMMANDER_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORTHANC_GUARD_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORTHANC_GUARD_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.MAIL_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                    conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORTHANC_GUARD_LEGGINGS.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.MAIL_SKIRT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.MAIL_SKIRT),
                                    conditionsFromItem(EquipmentItemsME.MAIL_SKIRT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORTHANC_GUARD_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelArmorPlate.getItem()),
                                    conditionsFromItem(steelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORTHANC_COMMANDER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .input(Items.FEATHER)
                            .input(EquipmentItemsME.ORTHANC_GUARD_HELMET)
                            .input(Items.FEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORTHANC_GUARD_CAPE.getDefaultStack(), "backAttachment", DispositionType.EVIL)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.STRING)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LIGHT_GRAY_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.BLACK_DYE)
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    //endregion

                    //region MISTIES
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_BONE_PAULDRON.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_SEEKER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_LACED_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_SKULLCAP_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_MAIL_COIF.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_SOLDIER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_SCREECHER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT),
                                    conditionsFromItem(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST),
                                    conditionsFromItem(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_CRESTED_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST),
                                    conditionsFromItem(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_HOBGOBLIN_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.GUNDABAD_MAIL_COAT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_COAT),
                                    conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATED_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                    conditionsFromItem(burzumSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_LARGE_CREST_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_LONG_HORN_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_SMALL_HORN_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_CAPTAIN_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    //endregion

                    //endregion

                    //MOUNT ARMORS

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_HORSE_ARMOR.getDefaultStack(), "mount_armor", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(steelArmorPlate.getItem()),
                                    conditionsFromItem(steelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_HORSE_ARMOR.getDefaultStack(), "mount_armor", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(steelArmorPlate.getItem()),
                                    conditionsFromItem(steelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HORSE_ARMOR.getDefaultStack(), "mount_armor", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(steelArmorPlate.getItem()),
                                    conditionsFromItem(steelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_HORSE_ARMOR.getDefaultStack(), "mount_armor", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(edhelSteelArmorPlate.getItem()),
                                    conditionsFromItem(edhelSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR.getDefaultStack(), "mount_armor", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR.getDefaultStack(), "mount_armor", DispositionType.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.GOLD_INGOT)
                            .input(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR)
                            .input(Items.GOLD_INGOT)
                            .input(Items.LEATHER)
                            .input(ResourceItemsME.FABRIC)
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR),
                                    conditionsFromItem(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BROADHOOF_GOAT_PLATE_ARMOR.getDefaultStack(), "mount_armor", DispositionType.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(ResourceItemsME.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(khazadSteelArmorPlate.getItem()),
                                    conditionsFromItem(khazadSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WARG_LEATHER_ARMOR.getDefaultStack(), "mount_armor", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WARG_REINFORCED_LEATHER_ARMOR.getDefaultStack(), "mount_armor", DispositionType.EVIL)
                            .input(Items.LEATHER)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(EquipmentItemsME.WARG_LEATHER_ARMOR)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(Items.LEATHER)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(Items.LEATHER)
                            .criterion(hasItem(EquipmentItemsME.WARG_LEATHER_ARMOR),
                                    conditionsFromItem(EquipmentItemsME.WARG_LEATHER_ARMOR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WARG_MORDOR_MAIL_ARMOR.getDefaultStack(), "mount_armor", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WARG_MORDOR_PLATE_ARMOR.getDefaultStack(), "mount_armor", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(EquipmentItemsME.WARG_MORDOR_MAIL_ARMOR)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WARG_ISENGARD_PLATE_ARMOR.getDefaultStack(), "mount_armor", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WARG_GUNDABAD_PLATE_ARMOR.getDefaultStack(), "mount_armor", DispositionType.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);
            }
        };
    }
}