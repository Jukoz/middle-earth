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
import net.sevenstars.middleearth.block.registration.WoodBlockSets;
import net.sevenstars.middleearth.block.special.forge.MetalTypes;
import net.sevenstars.middleearth.datageneration.content.tags.ArmorTags;
import net.sevenstars.middleearth.datageneration.custom.ArtisanTableRecipeJsonBuilder;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.utils.SmithingTrimPatternsME;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;

import java.util.concurrent.CompletableFuture;

public class ArtisanTableGenericArmorRecipeProvider extends RecipeProvider {
    private static final int XP_T2 = 1;
    private static final int XP_T3 = 4;
    private static final int XP_T4 = 9;
    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup;

    public ArtisanTableGenericArmorRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);

        this.registryLookup = registriesFuture;
    }

    @Override
    public String getName() {
        return "ArtisanTableGenericArmorRecipes";
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
        ItemStack goldArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        goldArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MetalTypes.GOLD.getName()))), getPattern()));

        ItemStack silverArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        silverArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MiddleEarth.MOD_ID, MetalTypes.SILVER.getName()))), getPattern()));

        ItemStack bronzeChainmail = new ItemStack(ResourceItemsME.MAIL);
        bronzeChainmail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MiddleEarth.MOD_ID, MetalTypes.BRONZE.getName()))), getPattern()));
        ItemStack crudeChainmail = new ItemStack(ResourceItemsME.MAIL);
        crudeChainmail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MiddleEarth.MOD_ID, MetalTypes.CRUDE.getName()))), getPattern()));


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
        ItemStack crudeArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        crudeArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MiddleEarth.MOD_ID, MetalTypes.CRUDE.getName()))), getPattern()));

        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.SHIRRIFF_HAT.getDefaultStack(), "hat", DispositionType.GOOD)
                        .input(Items.STRING)
                        .input(Items.LEATHER)
                        .input(Items.FEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

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
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.COOKING_POT_HELMET.getDefaultStack(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .input(Items.IRON_INGOT)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                        .input(Items.IRON_INGOT)
                        .input(Items.IRON_NUGGET)
                        .input(Items.IRON_NUGGET)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MAIL_COIF.getDefaultStack(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.CLOSED_MAIL_COIF.getDefaultStack(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.KETTLE_HAT_WITH_COIF.getDefaultStack(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .input(EquipmentItemsME.MAIL_COIF)
                        .input(EquipmentItemsME.KETTLE_HAT)
                        .criterion(hasItem(EquipmentItemsME.MAIL_COIF),
                                conditionsFromItem(EquipmentItemsME.MAIL_COIF))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.KETTLE_HAT_WITH_CLOSED_COIF.getDefaultStack(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .input(EquipmentItemsME.CLOSED_MAIL_COIF)
                        .input(EquipmentItemsME.KETTLE_HAT)
                        .criterion(hasItem(EquipmentItemsME.CLOSED_MAIL_COIF),
                                conditionsFromItem(EquipmentItemsME.CLOSED_MAIL_COIF))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.OPEN_FACE_HELMET.getDefaultStack(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WINGED_HELMET.getDefaultStack(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .input(TagKey.of(RegistryKeys.ITEM, MiddleEarth.of("feathers")))
                        .input(EquipmentItemsME.OPEN_FACE_HELMET)
                        .input(TagKey.of(RegistryKeys.ITEM, MiddleEarth.of("feathers")))
                        .criterion(hasItem(EquipmentItemsME.OPEN_FACE_HELMET),
                                conditionsFromItem(EquipmentItemsME.OPEN_FACE_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.NEUTRAL, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MAIL_SHIRT.getDefaultStack(), "chestplate", DispositionType.NEUTRAL, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MAIL_SKIRT.getDefaultStack(), "leggings", DispositionType.NEUTRAL, XP_T3)
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
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.SALLET.getDefaultStack(), "helmet", DispositionType.NEUTRAL, XP_T4)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                        .criterion(hasItem(steelHelmetPlate.getItem()),
                                conditionsFromItem(steelHelmetPlate.getItem()))
                        .offerTo(exporter);

                //HOODS
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.HOOD.getDefaultStack(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.TALL_HOOD.getDefaultStack(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_FUR_HOOD.getDefaultStack(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.HOOD)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(EquipmentItemsME.HOOD),
                                conditionsFromItem(EquipmentItemsME.HOOD))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BROWN_FUR_HOOD.getDefaultStack(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.HOOD)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(EquipmentItemsME.HOOD),
                                conditionsFromItem(EquipmentItemsME.HOOD))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GRAY_FUR_HOOD.getDefaultStack(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.HOOD)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(EquipmentItemsME.HOOD),
                                conditionsFromItem(EquipmentItemsME.HOOD))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.TAN_FUR_HOOD.getDefaultStack(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.HOOD)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(EquipmentItemsME.HOOD),
                                conditionsFromItem(EquipmentItemsME.HOOD))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WHITE_FUR_HOOD.getDefaultStack(), "helmet_attachment", DispositionType.NEUTRAL)
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
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.CAPE.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
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
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.SHOULDER_CAPE_LEFT.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.SHOULDER_CAPE_RIGHT.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.SURCOAT.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.CLOAK.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WANDERER_ROBES.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LEAF_CAPE.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .input(Items.STRING)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("leaves")))
                        .criterion(hasItem(Items.STRING),
                                conditionsFromItem(Items.STRING))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LEAF_LITTER_CAPE.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
                        .input(Items.LEAF_LITTER)
                        .input(Items.STRING)
                        .input(Items.LEAF_LITTER)
                        .input(Items.LEAF_LITTER)
                        .input(Items.LEAF_LITTER)
                        .input(Items.LEAF_LITTER)
                        .input(Items.LEAF_LITTER)
                        .input(Items.LEAF_LITTER)
                        .input(Items.LEAF_LITTER)
                        .criterion(hasItem(Items.LEAF_LITTER),
                                conditionsFromItem(Items.LEAF_LITTER))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MIRK_LEAF_CAPE.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(Items.STRING)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .criterion(hasItem(WoodBlockSets.MIRKWOOD_SET.leaves),
                                conditionsFromItem(WoodBlockSets.MIRKWOOD_SET.leaves))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MIRK_BARK_CAPE.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(Items.STRING)
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .criterion(hasItem(WoodBlockSets.MIRKWOOD_SET.logBlocks.log()),
                                conditionsFromItem(WoodBlockSets.MIRKWOOD_SET.logBlocks.log()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.AUTUMN_LEAF_CAPE.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
                        .input(TagKey.of(RegistryKeys.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(Items.STRING)
                        .input(TagKey.of(RegistryKeys.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(TagKey.of(RegistryKeys.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(TagKey.of(RegistryKeys.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(TagKey.of(RegistryKeys.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(TagKey.of(RegistryKeys.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(TagKey.of(RegistryKeys.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(TagKey.of(RegistryKeys.ITEM, MiddleEarth.of("autumn_leaves")))
                        .criterion(hasItem(Items.STRING),
                                conditionsFromItem(Items.STRING))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_FUR_CLOAK.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.BLACK_FUR)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.CLOAK)
                        .input(Items.STRING)
                        .criterion(hasItem(EquipmentItemsME.CLOAK),
                                conditionsFromItem(EquipmentItemsME.CLOAK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BROWN_FUR_CLOAK.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.BROWN_FUR)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.CLOAK)
                        .input(Items.STRING)
                        .criterion(hasItem(EquipmentItemsME.CLOAK),
                                conditionsFromItem(EquipmentItemsME.CLOAK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GRAY_FUR_CLOAK.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.GRAY_FUR)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.CLOAK)
                        .input(Items.STRING)
                        .criterion(hasItem(EquipmentItemsME.CLOAK),
                                conditionsFromItem(EquipmentItemsME.CLOAK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.TAN_FUR_CLOAK.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.TAN_FUR)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.CLOAK)
                        .input(Items.STRING)
                        .criterion(hasItem(EquipmentItemsME.CLOAK),
                                conditionsFromItem(EquipmentItemsME.CLOAK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WHITE_FUR_CLOAK.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.WHITE_FUR)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.CLOAK)
                        .input(Items.STRING)
                        .criterion(hasItem(EquipmentItemsME.CLOAK),
                                conditionsFromItem(EquipmentItemsME.CLOAK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_FUR.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BROWN_FUR.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GRAY_FUR.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.TAN_FUR.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WHITE_FUR.getDefaultStack(), "back_attachment", DispositionType.NEUTRAL)
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

                //region ELVES

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
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BRONZED_ELVEN_MAIL_COIF.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T2)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .criterion(hasItem(bronzeChainmail.getItem()),
                                conditionsFromItem(bronzeChainmail.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BRONZED_ELVEN_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T2)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .criterion(hasItem(bronzeChainmail.getItem()),
                                conditionsFromItem(bronzeChainmail.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BRONZED_ELVEN_MAIL_SKIRT.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T2)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeChainmail.getItem()), bronzeChainmail.getComponentChanges()))
                        .criterion(hasItem(bronzeChainmail.getItem()),
                                conditionsFromItem(bronzeChainmail.getItem()))
                        .offerTo(exporter);

                //T3
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_MAIL_COIF.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T3)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_MAIL_SKIRT.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BRONZED_ELVEN_PADDED_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.BRONZED_ELVEN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BRONZED_ELVEN_GORGET_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.BRONZED_ELVEN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_SILVER_PADDED_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.BRONZED_ELVEN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_SILVER_GORGET_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.BRONZED_ELVEN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                //endregion

                //region DWARVES

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MINER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.CANDLE)
                        .input(Items.LEATHER)
                        .input(Items.CANDLE)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_GAMBESON.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T2)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MAIL_COIF.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T3)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MINER_GAMBESON.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(EquipmentItemsME.DWARVEN_MAIL_HAUBERK)
                        .input(EquipmentItemsME.DWARVEN_GAMBESON)
                        .criterion(hasItem(EquipmentItemsME.DWARVEN_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.DWARVEN_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MAIL_CHAUSSES.getDefaultStack(), "boots", DispositionType.GOOD, XP_T3)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD, XP_T3)
                        .input(Items.IRON_INGOT)
                        .input(Items.IRON_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.IRON_INGOT),
                                conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_SCALE_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T4)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_SCALE_COAT.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T4)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_REINFORCED_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD, XP_T4)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.IRON_INGOT),
                                conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter);
                //endregion

                //region ORCS
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T2)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T2)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T2)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T2)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T2)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);


                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LEATHER_SKIRT.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T2)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LEG_BRACER.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T2)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .criterion(hasItem(crudeChainmail.getItem()),
                                conditionsFromItem(crudeChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T2)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(crudeChainmail.getItem()),
                                conditionsFromItem(crudeChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_REINFORCED_STRIP_LEATHER_SKIRT.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T2)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeChainmail.getItem()), crudeChainmail.getComponentChanges()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(crudeChainmail.getItem()),
                                conditionsFromItem(crudeChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BLACK_FUR_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.FUR),
                                conditionsFromItem(ResourceItemsME.FUR))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.FUR),
                                conditionsFromItem(ResourceItemsME.FUR))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_GRAY_FUR_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.FUR),
                                conditionsFromItem(ResourceItemsME.FUR))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_TAN_FUR_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.FUR),
                                conditionsFromItem(ResourceItemsME.FUR))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_WHITE_FUR_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.FUR),
                                conditionsFromItem(ResourceItemsME.FUR))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BRACED_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_MAIL_COIF.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_MAIL_SHIRT.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_REINFORCED_LEATHER_SKIRT.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_REINFORCED_STRIP_LEATHER_SKIRT.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_SALLET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T4)
                        .input(ResourceItemsME.BURZUM_STEEL_INGOT)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                        .input(ResourceItemsME.BURZUM_STEEL_INGOT)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                        .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BELLY_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T4)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_SCALE_VEST.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T4)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_REINFORCED_LEG_BRACER.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T4)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BRACED_SANDALS.getDefaultStack(), "boots", DispositionType.EVIL, XP_T4)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges())).input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)

                        .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                conditionsFromItem(burzumSteelArmorPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_CAPE.getDefaultStack(), "back_attachment", DispositionType.EVIL)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LONG_CAPE.getDefaultStack(), "back_attachment", DispositionType.EVIL)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_SHOULDERS.getDefaultStack(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE.getDefaultStack(), "back_attachment", DispositionType.EVIL)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BROWN_FUR_SURCOAT_WITH_BONE.getDefaultStack(), "back_attachment", DispositionType.EVIL)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_GRAY_FUR_SURCOAT_WITH_BONE.getDefaultStack(), "back_attachment", DispositionType.EVIL)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_TAN_FUR_SURCOAT_WITH_BONE.getDefaultStack(), "back_attachment", DispositionType.EVIL)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_WHITE_FUR_SURCOAT_WITH_BONE.getDefaultStack(), "back_attachment", DispositionType.EVIL)
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

                //region MOUNT ARMORS

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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GREAT_HORN_LIGHT_ARMOR.getDefaultStack(), "mount_armor", DispositionType.GOOD)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GREAT_HORN_LIGHT_GRAY_ARMOR.getDefaultStack(), "mount_armor", DispositionType.GOOD)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.CYAN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GREAT_HORN_LIGHT_GREEN_ARMOR.getDefaultStack(), "mount_armor", DispositionType.GOOD)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GREAT_HORN_PLATE_ARMOR.getDefaultStack(), "mount_armor", DispositionType.GOOD)
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
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GREAT_HORN_ORNAMENTED_PLATE_ARMOR.getDefaultStack(), "mount_armor", DispositionType.GOOD)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_NUGGET)
                        .criterion(hasItem(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR),
                                conditionsFromItem(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GREAT_HORN_GREEN_PLATE_ARMOR.getDefaultStack(), "mount_armor", DispositionType.GOOD)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GOLD_NUGGET)
                        .input(Items.GREEN_DYE)
                        .input(Items.GOLD_NUGGET)
                        .criterion(hasItem(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR),
                                conditionsFromItem(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR))
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
                //endregion
            }
        };
    }
}