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
import net.sevenstars.middleearth.item.ModEquipmentItems;
import net.sevenstars.middleearth.item.ModResourceItems;
import net.sevenstars.middleearth.item.utils.ModSmithingTrimPatterns;
import net.sevenstars.middleearth.resources.datas.Disposition;

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
        return getArmorTrimPatternsRegistry().getOrThrow(ModSmithingTrimPatterns.SMITHING_PART);
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

                    ItemStack goldArmorPlate = new ItemStack(ModResourceItems.ARMOR_PLATE);
                    goldArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MetalTypes.GOLD.getName()))), getPattern()));

                    ItemStack steelArmorPlate = new ItemStack(ModResourceItems.ARMOR_PLATE);
                    steelArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName()))), getPattern()));
                    ItemStack steelHelmetPlate = new ItemStack(ModResourceItems.HELMET_PLATE);
                    steelHelmetPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName()))), getPattern()));
                    ItemStack steelChainmail = new ItemStack(ModResourceItems.MAIL);
                    steelChainmail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName()))), getPattern()));
                    ItemStack steelScaleMail = new ItemStack(ModResourceItems.SCALE_MAIL);
                    steelScaleMail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName()))), getPattern()));

                    ItemStack edhelSteelArmorPlate = new ItemStack(ModResourceItems.ARMOR_PLATE);
                    edhelSteelArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName()))), getPattern()));
                    ItemStack edhelSteelHelmetPlate = new ItemStack(ModResourceItems.HELMET_PLATE);
                    edhelSteelHelmetPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName()))), getPattern()));
                    ItemStack edhelSteelChainmail = new ItemStack(ModResourceItems.MAIL);
                    edhelSteelChainmail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName()))), getPattern()));
                    ItemStack edhelSteelScaleMail = new ItemStack(ModResourceItems.SCALE_MAIL);
                    edhelSteelScaleMail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName()))), getPattern()));

                    ItemStack khazadSteelArmorPlate = new ItemStack(ModResourceItems.ARMOR_PLATE);
                    khazadSteelArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName()))), getPattern()));
                    ItemStack khazadSteelHelmetPlate = new ItemStack(ModResourceItems.HELMET_PLATE);
                    khazadSteelHelmetPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName()))), getPattern()));
                    ItemStack khazadSteelChainmail = new ItemStack(ModResourceItems.MAIL);
                    khazadSteelChainmail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName()))), getPattern()));
                    ItemStack khazadSteelScaleMail = new ItemStack(ModResourceItems.SCALE_MAIL);
                    khazadSteelScaleMail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName()))), getPattern()));

                    ItemStack burzumSteelArmorPlate = new ItemStack(ModResourceItems.ARMOR_PLATE);
                    burzumSteelArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName()))), getPattern()));
                    ItemStack burzumSteelHelmetPlate = new ItemStack(ModResourceItems.HELMET_PLATE);
                    burzumSteelHelmetPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName()))), getPattern()));
                    ItemStack burzumSteelChainmail = new ItemStack(ModResourceItems.MAIL);
                    burzumSteelChainmail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName()))), getPattern()));
                    ItemStack burzumSteelScaleMail = new ItemStack(ModResourceItems.SCALE_MAIL);
                    burzumSteelScaleMail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName()))), getPattern()));

                    ItemStack ironArmorPlate = new ItemStack(ModResourceItems.ARMOR_PLATE);
                    ironArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MetalTypes.IRON.getName()))), getPattern()));
                    ItemStack ironHelmetPlate = new ItemStack(ModResourceItems.HELMET_PLATE);
                    ironHelmetPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MetalTypes.IRON.getName()))), getPattern()));
                    ItemStack ironChainmail = new ItemStack(ModResourceItems.MAIL);
                    ironChainmail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MetalTypes.IRON.getName()))), getPattern()));

                    ItemStack bronzeArmorPlate = new ItemStack(ModResourceItems.ARMOR_PLATE);
                    bronzeArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                            Identifier.of(MiddleEarth.MOD_ID, MetalTypes.BRONZE.getName()))), getPattern()));

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.SHIRRIFF_HAT.getDefaultStack(), "hat", Disposition.GOOD)
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
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.STRAW_HAT.getDefaultStack(), "hat", Disposition.NEUTRAL)
                            .input(Items.STRING)
                            .input(ModResourceItems.STRAW)
                            .input(Items.STRING)
                            .input(ModResourceItems.STRAW)
                            .input(ModResourceItems.STRAW)
                            .criterion(hasItem(ModResourceItems.STRAW),
                                    conditionsFromItem(ModResourceItems.STRAW))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.WOVEN_HAT.getDefaultStack(), "hat", Disposition.NEUTRAL)
                            .input(Items.STRING)
                            .input(Items.WHITE_WOOL)
                            .input(Items.STRING)
                            .input(Items.STRING)
                            .input(Items.STRING)
                            .criterion(hasItem(Items.WHITE_WOOL),
                                    conditionsFromItem(Items.WHITE_WOOL))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BYCOCKET.getDefaultStack(), "hat", Disposition.NEUTRAL)
                            .input(Items.LEATHER)
                            .input(Items.WHITE_WOOL)
                            .input(Items.LEATHER)
                            .input(Items.STRING)
                            .input(Items.STRING)
                            .criterion(hasItem(Items.WHITE_WOOL),
                                    conditionsFromItem(Items.WHITE_WOOL))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.WANDERER_HAT.getDefaultStack(), "hat", Disposition.NEUTRAL)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ARMING_COAT.getDefaultStack(), "chestplate", Disposition.NEUTRAL)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ARMING_SKIRT.getDefaultStack(), "leggings", Disposition.NEUTRAL)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.SHOES.getDefaultStack(), "boots", Disposition.NEUTRAL)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.WORK_SHOES.getDefaultStack(), "boots", Disposition.NEUTRAL)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    //T2
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LEATHER_SKULLCAP.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GAMBESON_CAP.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                            .input(Items.LEATHER)
                            .input(Items.WHITE_WOOL)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GAMBESON_COWL.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                            .input(Items.LEATHER)
                            .input(Items.WHITE_WOOL)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.KETTLE_HAT.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                            .input(Items.IRON_NUGGET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .input(Items.IRON_NUGGET)
                            .input(Items.IRON_NUGGET)
                            .input(Items.IRON_NUGGET)
                            .criterion(hasItem(ironArmorPlate.getItem()),
                                    conditionsFromItem(ironArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GAMBESON.getDefaultStack(), "chestplate", Disposition.NEUTRAL)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(Items.WHITE_WOOL)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LEATHER_VEST.getDefaultStack(), "chestplate", Disposition.NEUTRAL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LEATHER_SCALE_VEST.getDefaultStack(), "chestplate", Disposition.NEUTRAL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.STURDY_BOOTS.getDefaultStack(), "boots", Disposition.NEUTRAL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.TRAVELLING_BOOTS.getDefaultStack(), "boots", Disposition.NEUTRAL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.HIGH_CUT_BOOTS.getDefaultStack(), "boots", Disposition.NEUTRAL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);
                    //T3
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MAIL_COIF.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.CLOSED_MAIL_COIF.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.KETTLE_HAT_WITH_COIF.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                            .input(ModEquipmentItems.MAIL_COIF)
                            .input(ModEquipmentItems.KETTLE_HAT)
                            .criterion(hasItem(ModEquipmentItems.MAIL_COIF),
                                    conditionsFromItem(ModEquipmentItems.MAIL_COIF))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.KETTLE_HAT_WITH_CLOSED_COIF.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                            .input(ModEquipmentItems.CLOSED_MAIL_COIF)
                            .input(ModEquipmentItems.KETTLE_HAT)
                            .criterion(hasItem(ModEquipmentItems.CLOSED_MAIL_COIF),
                                    conditionsFromItem(ModEquipmentItems.CLOSED_MAIL_COIF))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.OPEN_FACE_HELMET.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MAIL_HAUBERK.getDefaultStack(), "chestplate", Disposition.NEUTRAL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MAIL_SHIRT.getDefaultStack(), "chestplate", Disposition.NEUTRAL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MAIL_SKIRT.getDefaultStack(), "leggings", Disposition.NEUTRAL)
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
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.SALLET.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    //HOODS
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.HOOD.getDefaultStack(), "hood", Disposition.NEUTRAL)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.STRING)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.TALL_HOOD.getDefaultStack(), "hood", Disposition.NEUTRAL)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.STRING)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BLACK_FUR_HOOD.getDefaultStack(), "hood", Disposition.NEUTRAL)
                            .input(ModResourceItems.FUR)
                            .input(ModEquipmentItems.HOOD)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(Items.STRING)
                            .input(ModResourceItems.FUR)
                            .criterion(hasItem(ModEquipmentItems.HOOD),
                                    conditionsFromItem(ModEquipmentItems.HOOD))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BROWN_FUR_HOOD.getDefaultStack(), "hood", Disposition.NEUTRAL)
                            .input(ModResourceItems.FUR)
                            .input(ModEquipmentItems.HOOD)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(Items.STRING)
                            .input(ModResourceItems.FUR)
                            .criterion(hasItem(ModEquipmentItems.HOOD),
                                    conditionsFromItem(ModEquipmentItems.HOOD))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GRAY_FUR_HOOD.getDefaultStack(), "hood", Disposition.NEUTRAL)
                            .input(ModResourceItems.FUR)
                            .input(ModEquipmentItems.HOOD)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(Items.STRING)
                            .input(ModResourceItems.FUR)
                            .criterion(hasItem(ModEquipmentItems.HOOD),
                                    conditionsFromItem(ModEquipmentItems.HOOD))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.TAN_FUR_HOOD.getDefaultStack(), "hood", Disposition.NEUTRAL)
                            .input(ModResourceItems.FUR)
                            .input(ModEquipmentItems.HOOD)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(Items.STRING)
                            .input(ModResourceItems.FUR)
                            .criterion(hasItem(ModEquipmentItems.HOOD),
                                    conditionsFromItem(ModEquipmentItems.HOOD))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.WHITE_FUR_HOOD.getDefaultStack(), "hood", Disposition.NEUTRAL)
                            .input(ModResourceItems.FUR)
                            .input(ModEquipmentItems.HOOD)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(Items.STRING)
                            .input(ModResourceItems.FUR)
                            .criterion(hasItem(ModEquipmentItems.HOOD),
                                    conditionsFromItem(ModEquipmentItems.HOOD))
                            .offerTo(exporter);

                    //CAPES
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.CAPE.getDefaultStack(), "cape", Disposition.NEUTRAL)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.STRING)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.SURCOAT.getDefaultStack(), "cape", Disposition.NEUTRAL)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.STRING)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.STRING)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.CLOAK.getDefaultStack(), "cape", Disposition.NEUTRAL)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.STRING)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.WANDERER_ROBES.getDefaultStack(), "cape", Disposition.NEUTRAL)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);


                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BLACK_FUR_CLOAK.getDefaultStack(), "cape", Disposition.NEUTRAL)
                            .input(Items.STRING)
                            .input(ModEquipmentItems.BLACK_FUR)
                            .input(Items.STRING)
                            .input(Items.STRING)
                            .input(ModEquipmentItems.CLOAK)
                            .input(Items.STRING)
                            .criterion(hasItem(ModEquipmentItems.CLOAK),
                                    conditionsFromItem(ModEquipmentItems.CLOAK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BROWN_FUR_CLOAK.getDefaultStack(), "cape", Disposition.NEUTRAL)
                            .input(Items.STRING)
                            .input(ModEquipmentItems.BROWN_FUR)
                            .input(Items.STRING)
                            .input(Items.STRING)
                            .input(ModEquipmentItems.CLOAK)
                            .input(Items.STRING)
                            .criterion(hasItem(ModEquipmentItems.CLOAK),
                                    conditionsFromItem(ModEquipmentItems.CLOAK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GRAY_FUR_CLOAK.getDefaultStack(), "cape", Disposition.NEUTRAL)
                            .input(Items.STRING)
                            .input(ModEquipmentItems.GRAY_FUR)
                            .input(Items.STRING)
                            .input(Items.STRING)
                            .input(ModEquipmentItems.CLOAK)
                            .input(Items.STRING)
                            .criterion(hasItem(ModEquipmentItems.CLOAK),
                                    conditionsFromItem(ModEquipmentItems.CLOAK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.TAN_FUR_CLOAK.getDefaultStack(), "cape", Disposition.NEUTRAL)
                            .input(Items.STRING)
                            .input(ModEquipmentItems.TAN_FUR)
                            .input(Items.STRING)
                            .input(Items.STRING)
                            .input(ModEquipmentItems.CLOAK)
                            .input(Items.STRING)
                            .criterion(hasItem(ModEquipmentItems.CLOAK),
                                    conditionsFromItem(ModEquipmentItems.CLOAK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.WHITE_FUR_CLOAK.getDefaultStack(), "cape", Disposition.NEUTRAL)
                            .input(Items.STRING)
                            .input(ModEquipmentItems.WHITE_FUR)
                            .input(Items.STRING)
                            .input(Items.STRING)
                            .input(ModEquipmentItems.CLOAK)
                            .input(Items.STRING)
                            .criterion(hasItem(ModEquipmentItems.CLOAK),
                                    conditionsFromItem(ModEquipmentItems.CLOAK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BLACK_FUR.getDefaultStack(), "cape", Disposition.NEUTRAL)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(Items.STRING)
                            .input(ModResourceItems.FUR)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BROWN_FUR.getDefaultStack(), "cape", Disposition.NEUTRAL)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(Items.STRING)
                            .input(ModResourceItems.FUR)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GRAY_FUR.getDefaultStack(), "cape", Disposition.NEUTRAL)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(Items.STRING)
                            .input(ModResourceItems.FUR)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.TAN_FUR.getDefaultStack(), "cape", Disposition.NEUTRAL)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(Items.STRING)
                            .input(ModResourceItems.FUR)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.WHITE_FUR.getDefaultStack(), "cape", Disposition.NEUTRAL)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(Items.STRING)
                            .input(ModResourceItems.FUR)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);
                    //endregion

                    //region GONDOR
                    //T2
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    //T3
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CABASSET_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_MAIL_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ModEquipmentItems.GONDORIAN_MAIL_HAUBERK)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.GONDORIAN_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.GONDORIAN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_LEATHER_CUIRASS.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ModEquipmentItems.GONDORIAN_MAIL_HAUBERK)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.GONDORIAN_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.GONDORIAN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_TABBARD.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.GONDORIAN_MAIL_HAUBERK)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.GONDORIAN_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.GONDORIAN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_MAIL_COAT.getDefaultStack(), "leggings", Disposition.GOOD)
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
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_SOLDIER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_SOLDIER_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.GONDORIAN_MAIL_HAUBERK)
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.GONDORIAN_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.GONDORIAN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    //T5 PLATE
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_PLATE_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(ModEquipmentItems.GONDORIAN_SOLDIER_CHESTPLATE)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.GONDORIAN_SOLDIER_CHESTPLATE),
                                    conditionsFromItem(ModEquipmentItems.GONDORIAN_SOLDIER_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.GONDORIAN_MAIL_COAT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.GONDORIAN_MAIL_COAT),
                                    conditionsFromItem(ModEquipmentItems.GONDORIAN_MAIL_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_PLATE_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelArmorPlate.getItem()),
                                    conditionsFromItem(steelArmorPlate.getItem()))
                            .offerTo(exporter);

                    //T5 CAPTAIN
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CAPTAIN_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.GONDORIAN_PLATE_HELMET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.GONDORIAN_PLATE_HELMET),
                                    conditionsFromItem(ModEquipmentItems.GONDORIAN_PLATE_HELMET))
                            .offerTo(exporter);

                    //T5 KING'S GUARD
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_KINGS_GUARD_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(Items.FEATHER)
                            .input(ModEquipmentItems.GONDORIAN_PLATE_HELMET)
                            .input(Items.FEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.GONDORIAN_PLATE_HELMET),
                                    conditionsFromItem(ModEquipmentItems.GONDORIAN_PLATE_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_KINGS_GUARD_CHESTKPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE)
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE),
                                    conditionsFromItem(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_KINGS_GUARD_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS),
                                    conditionsFromItem(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_KINGS_GUARD_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(goldArmorPlate.getItem()),
                                    conditionsFromItem(goldArmorPlate.getItem()))
                            .offerTo(exporter);

                    //T5 CITADEL GUARD
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CITADEL_GUARD_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.GONDORIAN_PLATE_HELMET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.GONDORIAN_PLATE_HELMET),
                                    conditionsFromItem(ModEquipmentItems.GONDORIAN_PLATE_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CITADEL_GUARD_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE)
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE),
                                    conditionsFromItem(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CITADEL_GUARD_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS),
                                    conditionsFromItem(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CITADEL_GUARD_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(goldArmorPlate.getItem()),
                                    conditionsFromItem(goldArmorPlate.getItem()))
                            .offerTo(exporter);

                    //T5 FOUNTAIN GUARD
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(ModResourceItems.MITHRIL_NUGGET)
                            .input(ModEquipmentItems.GONDORIAN_CAPTAIN_HELMET)
                            .input(ModResourceItems.MITHRIL_NUGGET)
                            .input(ModResourceItems.SWAN_FEATHER)
                            .input(ModResourceItems.SWAN_FEATHER)
                            .criterion(hasItem(ModEquipmentItems.GONDORIAN_CAPTAIN_HELMET),
                                    conditionsFromItem(ModEquipmentItems.GONDORIAN_CAPTAIN_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE)
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE),
                                    conditionsFromItem(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS),
                                    conditionsFromItem(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(goldArmorPlate.getItem()),
                                    conditionsFromItem(goldArmorPlate.getItem()))
                            .offerTo(exporter);

                    //HOODS
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CITADEL_GUARD_HOOD.getDefaultStack(), "hood", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    //CAPES
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CAPTAIN_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.IRON_NUGGET)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(Items.WHITE_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.WHITE_DYE)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_HERO_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.IRON_NUGGET)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.WHITE_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.WHITE_DYE)
                            .input(Items.BLACK_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLACK_DYE)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_KINGS_GUARD_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.GOLD_NUGGET)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(Items.WHITE_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.WHITE_DYE)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CITADEL_GUARD_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.GOLD_NUGGET)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(Items.YELLOW_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.YELLOW_DYE)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.GOLD_NUGGET)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLACK_DYE)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);
                    //endregion

                    //region ROHAN
                    //T1
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_REINFORCED_COAT.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.ARMING_COAT)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.ARMING_COAT),
                                    conditionsFromItem(ModEquipmentItems.ARMING_COAT))
                            .offerTo(exporter);

                    //T2
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_LEATHER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_REINFORCED_LEATHER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.IRON_NUGGET)
                            .input(Items.IRON_NUGGET)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_ORNAMENTED_LEATHER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.BRONZE_NUGGET)
                            .input(ModResourceItems.BRONZE_NUGGET)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_LEATHER_VEST.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.ARMING_COAT)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModEquipmentItems.ARMING_COAT),
                                    conditionsFromItem(ModEquipmentItems.ARMING_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.ARMING_COAT)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.ARMING_COAT),
                                    conditionsFromItem(ModEquipmentItems.ARMING_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_GAMBESON.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.GAMBESON)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModEquipmentItems.GAMBESON),
                                    conditionsFromItem(ModEquipmentItems.GAMBESON))
                            .offerTo(exporter);

                    //T3
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_MILITIA_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .criterion(hasItem(bronzeArmorPlate.getItem()),
                                    conditionsFromItem(bronzeArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_BRACED_MILITIA_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .criterion(hasItem(ironArmorPlate.getItem()),
                                    conditionsFromItem(ironArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_REINFORCED_MILITIA_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .criterion(hasItem(bronzeArmorPlate.getItem()),
                                    conditionsFromItem(bronzeArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_ORNAMENTED_MILITIA_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .criterion(hasItem(bronzeArmorPlate.getItem()),
                                    conditionsFromItem(bronzeArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_REINFORCED_LEATHER_VEST.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.MAIL_HAUBERK)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModEquipmentItems.MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_REINFORCED_LEATHER_SCALE_VEST.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.MAIL_HAUBERK)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_BRACED_MAIL_SHIRT.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.MAIL_HAUBERK)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.MAIL_HAUBERK))
                            .offerTo(exporter);

                    ///T4
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_SOLDIER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_ORNAMENTED_SOLDIER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_ROYAL_GUARD_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_ORNAMENTED_SCALE_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.GREEN_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.GREEN_DYE)
                            .criterion(hasItem(ModEquipmentItems.MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_SCALE_JACKET.getDefaultStack(), "leggings", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.MAIL_HAUBERK))
                            .offerTo(exporter);

                    //T5 EORLING MARSHAL
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EORLING_MARSHAL_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EORLING_MARSHAL_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EORLING_MARSHAL_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.ROHIRRIC_SCALE_JACKET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.ROHIRRIC_SCALE_JACKET),
                                    conditionsFromItem(ModEquipmentItems.ROHIRRIC_SCALE_JACKET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EORLING_MARSHAL_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(goldArmorPlate.getItem()),
                                    conditionsFromItem(goldArmorPlate.getItem()))
                            .offerTo(exporter);

                    //T5 HORSE LORD
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.HORSE_LORD_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.HORSE_LORD_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.HORSE_LORD_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.ROHIRRIC_SCALE_JACKET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.ROHIRRIC_SCALE_JACKET),
                                    conditionsFromItem(ModEquipmentItems.ROHIRRIC_SCALE_JACKET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.HORSE_LORD_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(goldArmorPlate.getItem()),
                                    conditionsFromItem(goldArmorPlate.getItem()))
                            .offerTo(exporter);

                    //CAPES
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.BRONZE_NUGGET)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.GREEN_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.GREEN_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_ROYAL_GUARD_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.BRONZE_NUGGET)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.YELLOW_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.YELLOW_DYE)
                            .input(Items.GREEN_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.GREEN_DYE)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EORLING_MARSHAL_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.STEEL_NUGGET)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.GREEN_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.GREEN_DYE)
                            .input(Items.YELLOW_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.WHITE_DYE)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.HORSE_LORD_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.BRONZE_NUGGET)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLUE_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLUE_DYE)
                            .input(Items.RED_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.RED_DYE)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);
                    //endregion

                    //region DALE

                    //T1
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DALISH_ARMING_COAT_BLACK_FUR.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FUR)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DALISH_ARMING_COAT_BROWN_FUR.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FUR)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DALISH_ARMING_COAT_TAN_FUR.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FUR)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HEYDAY_ARMING_COAT.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.ORANGE_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.ORANGE_DYE)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    //T2
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DALISH_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    //T3
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HELMET_BLACK_FUR.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(ModResourceItems.FUR)
                            .input(ModEquipmentItems.DALISH_HELMET)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .criterion(hasItem(ModEquipmentItems.DALISH_HELMET),
                                    conditionsFromItem(ModEquipmentItems.DALISH_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HELMET_BROWN_FUR.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(ModResourceItems.FUR)
                            .input(ModEquipmentItems.DALISH_HELMET)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .criterion(hasItem(ModEquipmentItems.DALISH_HELMET),
                                    conditionsFromItem(ModEquipmentItems.DALISH_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HELMET_TAN_FUR.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(ModResourceItems.FUR)
                            .input(ModEquipmentItems.DALISH_HELMET)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .criterion(hasItem(ModEquipmentItems.DALISH_HELMET),
                                    conditionsFromItem(ModEquipmentItems.DALISH_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DALISH_MAIL_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.MAIL_HAUBERK)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DALISH_MAIL_COAT.getDefaultStack(), "leggings", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .input(ModEquipmentItems.MAIL_SKIRT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.MAIL_HAUBERK))
                            .offerTo(exporter);

                    //T4
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DALISH_BURGONET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DALISH_SCALE_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
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
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HEYDAY_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.DALISH_HELMET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .criterion(hasItem(ModEquipmentItems.DALISH_HELMET),
                                    conditionsFromItem(ModEquipmentItems.DALISH_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HEYDAY_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.DALISH_SCALE_HAUBERK)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.ORANGE_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.ORANGE_DYE)
                            .criterion(hasItem(ModEquipmentItems.DALISH_SCALE_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.DALISH_SCALE_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HEYDAY_MAIL_COAT.getDefaultStack(), "leggings", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.DALISH_MAIL_COAT)
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.DALISH_MAIL_COAT),
                                    conditionsFromItem(ModEquipmentItems.DALISH_MAIL_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HEYDAY_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelArmorPlate.getItem()),
                                    conditionsFromItem(steelArmorPlate.getItem()))
                            .offerTo(exporter);

                    //T5 BARDING
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BARDING_SOLDIER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BARDING_SOLDIER_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(ModEquipmentItems.DALISH_SCALE_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.DALISH_SCALE_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.DALISH_SCALE_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BARDING_MAIL_SKIRT.getDefaultStack(), "leggings", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.DALISH_MAIL_COAT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.DALISH_MAIL_COAT),
                                    conditionsFromItem(ModEquipmentItems.DALISH_MAIL_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BARDING_PLATED_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelArmorPlate.getItem()),
                                    conditionsFromItem(steelArmorPlate.getItem()))
                            .offerTo(exporter);

                    //T5 BARDING SERGEANT
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BARDING_SERGEANT_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(Items.FEATHER)
                            .input(ModEquipmentItems.BARDING_SOLDIER_HELMET)
                            .input(Items.FEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.BARDING_SOLDIER_HELMET),
                                    conditionsFromItem(ModEquipmentItems.BARDING_SOLDIER_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BARDING_SERGEANT_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.BARDING_SOLDIER_CHESTPLATE)
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.BARDING_SERGEANT_CHESTPLATE),
                                    conditionsFromItem(ModEquipmentItems.BARDING_SERGEANT_CHESTPLATE))
                            .offerTo(exporter);

                    //CAPES
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BARDING_SURCOAT.getDefaultStack(), "cape", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.STRING)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.PURPLE_DYE)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BARDING_SERGEANT_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.STRING)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.ORANGE_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.ORANGE_DYE)
                            .input(Items.PURPLE_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.PURPLE_DYE)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HEYDAY_CLOAK.getDefaultStack(), "cape", Disposition.GOOD)
                            .input(ModResourceItems.BRONZE_NUGGET)
                            .input(Items.STRING)
                            .input(ModResourceItems.BRONZE_NUGGET)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.RED_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.RED_DYE)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);
//endregion

                    //region BLACK NUMENOREANS

                    //endregion

                    //endregion

                    //region ELVES

                    //region GENERIC

                    //T1
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ELVEN_ARMING_COAT.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ELVEN_ARMING_SKIRT.getDefaultStack(), "leggings", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ELVEN_OPEN_ARMING_SKIRT.getDefaultStack(), "leggings", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    //T2
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ELVEN_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    //T3
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ELVEN_MAIL_COIF.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ELVEN_MAIL_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ELVEN_MAIL_SKIRT.getDefaultStack(), "leggings", Disposition.GOOD)
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
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_DIADEM.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(ModResourceItems.BRONZE_NUGGET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.BRONZE_NUGGET)
                            .criterion(hasItem(bronzeArmorPlate.getItem()),
                                    conditionsFromItem(bronzeArmorPlate.getItem()))
                            .offerTo(exporter);

                    //T2
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_LEATHER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_ARMING_COAT.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.ELVEN_ARMING_COAT)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.ELVEN_ARMING_COAT),
                                    conditionsFromItem(ModEquipmentItems.ELVEN_ARMING_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_ARMING_SKIRT.getDefaultStack(), "leggings", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(ModEquipmentItems.ELVEN_ARMING_SKIRT)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModEquipmentItems.ELVEN_ARMING_SKIRT),
                                    conditionsFromItem(ModEquipmentItems.ELVEN_ARMING_SKIRT))
                            .offerTo(exporter);

                    //T3
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_MAIL_COIF_DIADEM.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(ModEquipmentItems.ELVEN_MAIL_COIF)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .input(ModEquipmentItems.LORIEN_DIADEM)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.ELVEN_MAIL_COIF),
                                    conditionsFromItem(ModEquipmentItems.ELVEN_MAIL_COIF))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_SHORT_MAIL_COIF_DIADEM.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(ModEquipmentItems.ELVEN_MAIL_COIF)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .input(ModEquipmentItems.LORIEN_DIADEM)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.ELVEN_MAIL_COIF),
                                    conditionsFromItem(ModEquipmentItems.ELVEN_MAIL_COIF))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_MAIL_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ModEquipmentItems.ELVEN_MAIL_HAUBERK)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.ELVEN_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.ELVEN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_MARCHWARDEN_MAIL_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.ELVEN_MAIL_HAUBERK)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.ELVEN_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.ELVEN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    //T4
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_SOLDIER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelHelmetPlate.getItem()), edhelSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(edhelSteelHelmetPlate.getItem()),
                                    conditionsFromItem(edhelSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_SOLDIER_MAIL_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.LORIEN_MAIL_HAUBERK)
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.LORIEN_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.LORIEN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_SOLDIER_SCALE_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(edhelSteelScaleMail.getItem()),
                                    conditionsFromItem(edhelSteelScaleMail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_SCALE_COAT.getDefaultStack(), "leggings", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .criterion(hasItem(edhelSteelScaleMail.getItem()),
                                    conditionsFromItem(edhelSteelScaleMail.getItem()))
                            .offerTo(exporter);

                    //T5 GALADHRIM
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelHelmetPlate.getItem()), edhelSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(edhelSteelHelmetPlate.getItem()),
                                    conditionsFromItem(edhelSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .input(ModEquipmentItems.LORIEN_SOLDIER_SCALE_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.LORIEN_SOLDIER_SCALE_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.LORIEN_SOLDIER_SCALE_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.LORIEN_SCALE_COAT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.LORIEN_SCALE_COAT),
                                    conditionsFromItem(ModEquipmentItems.LORIEN_SCALE_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(edhelSteelArmorPlate.getItem()),
                                    conditionsFromItem(edhelSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    //T5 GALADHRIM LORD
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_LORD_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.GALADHRIM_HELMET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.GALADHRIM_HELMET),
                                    conditionsFromItem(ModEquipmentItems.GALADHRIM_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_LORD_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.GALADHRIM_CHESTPLATE)
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.GALADHRIM_CHESTPLATE),
                                    conditionsFromItem(ModEquipmentItems.GALADHRIM_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_LORD_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.GALADHRIM_LEGGINGS)
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.GALADHRIM_LEGGINGS),
                                    conditionsFromItem(ModEquipmentItems.GALADHRIM_LEGGINGS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_LORD_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(edhelSteelHelmetPlate.getItem()),
                                    conditionsFromItem(edhelSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    //HOODS
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_MARCHWARDEN_HOOD.getDefaultStack(), "hood", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.GRAY_DYE)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_HOOD.getDefaultStack(), "hood", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLUE_DYE)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    //CAPES
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_MARCHWARDEN_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.BRONZE_NUGGET)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.GRAY_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.GRAY_DYE)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                            .input(Items.STRING)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.STRING)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLUE_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLUE_DYE)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_LORD_SURCOAT.getDefaultStack(), "cape", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.WHITE_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.EDHEL_STEEL_NUGGET)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);
                    //endregion

                    //endregion

                    //region DWARVES

                    //region GENERIC
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DWARVEN_MINER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(Items.CANDLE)
                            .input(Items.LEATHER)
                            .input(Items.CANDLE)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DWARVEN_GAMBESON.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(Items.WHITE_WOOL)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DWARVEN_MAIL_COIF.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DWARVEN_MAIL_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DWARVEN_MINER_GAMBESON.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(ModEquipmentItems.DWARVEN_MAIL_HAUBERK)
                            .input(ModEquipmentItems.DWARVEN_GAMBESON)
                            .criterion(hasItem(ModEquipmentItems.DWARVEN_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.DWARVEN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DWARVEN_MAIL_COAT.getDefaultStack(), "leggings", Disposition.GOOD)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DWARVEN_MAIL_CHAUSSES.getDefaultStack(), "boots", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DWARVEN_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .input(Items.IRON_INGOT)
                            .input(Items.IRON_INGOT)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.IRON_INGOT),
                                    conditionsFromItem(Items.IRON_INGOT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DWARVEN_SCALE_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DWARVEN_SCALE_COAT.getDefaultStack(), "leggings", Disposition.GOOD)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DWARVEN_REINFORCED_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .input(ModResourceItems.KHAZAD_STEEL_INGOT)
                            .input(ModResourceItems.KHAZAD_STEEL_INGOT)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.IRON_INGOT),
                                    conditionsFromItem(Items.IRON_INGOT))
                            .offerTo(exporter);
                    //endregion

                    //region EREBOR
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LONGBEARD_SEGMENTED_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(bronzeArmorPlate.getItem()),
                                    conditionsFromItem(bronzeArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LONGBEARD_LEATHER_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.BRONZE_NUGGET)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LONGBEARD_WANDERER_COAT.getDefaultStack(), "chestplate", Disposition.GOOD)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LONGBEARD_LEATHER_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.BRONZE_NUGGET)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LONGBEARD_PARTISAN_OUTFIT.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.DWARVEN_MAIL_HAUBERK)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(Items.IRON_NUGGET)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.DWARVEN_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.DWARVEN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LONGBEARD_REINFORCED_LEATHER_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ModEquipmentItems.DWARVEN_MAIL_HAUBERK)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.BRONZE_NUGGET)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.DWARVEN_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.DWARVEN_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_LEATHER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(Items.IRON_INGOT)
                            .input(Items.LEATHER)
                            .input(Items.IRON_INGOT)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_BRACED_LEATHER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(Items.IRON_INGOT)
                            .input(Items.LEATHER)
                            .input(Items.IRON_INGOT)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_NASAL_LEATHER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(Items.IRON_INGOT)
                            .input(Items.LEATHER)
                            .input(Items.IRON_INGOT)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_LEATHER_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.BRONZE_NUGGET)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_MAIL_COIF.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_GILDED_MAIL_COIF.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(ModResourceItems.BRONZE_NUGGET)
                            .input(ModEquipmentItems.EREBOR_MAIL_COIF)
                            .input(ModResourceItems.BRONZE_NUGGET)
                            .input(ModResourceItems.BRONZE_NUGGET)
                            .input(ModResourceItems.BRONZE_NUGGET)
                            .criterion(hasItem(ModEquipmentItems.EREBOR_MAIL_COIF),
                                    conditionsFromItem(ModEquipmentItems.EREBOR_MAIL_COIF))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .input(Items.IRON_NUGGET)
                            .input(Items.IRON_NUGGET)
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_MAIL_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_PADDED_MAIL_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(ModEquipmentItems.EREBOR_MAIL_HAUBERK)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.BRONZE_INGOT)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.EREBOR_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.EREBOR_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_MAIL_COAT.getDefaultStack(), "leggings", Disposition.GOOD)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_MAIL_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_MAIL_CHAUSSES.getDefaultStack(), "boots", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.IRON_INGOT)
                            .input(Items.IRON_INGOT)
                            .criterion(hasItem(Items.IRON_INGOT),
                                    conditionsFromItem(Items.IRON_INGOT))
                            .offerTo(exporter);

                    //t4
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RAVENHILL_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelHelmetPlate.getItem()), khazadSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(khazadSteelHelmetPlate.getItem()),
                                    conditionsFromItem(khazadSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RAVENHILL_GILDED_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(ModEquipmentItems.EREBOR_GILDED_MAIL_COIF)
                            .input(ModEquipmentItems.RAVENHILL_HELMET)
                            .criterion(hasItem(ModEquipmentItems.RAVENHILL_HELMET),
                                    conditionsFromItem(ModEquipmentItems.RAVENHILL_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_SCALE_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_LONG_COAT.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .input(ModEquipmentItems.EREBOR_MAIL_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModEquipmentItems.EREBOR_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.EREBOR_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_BRAWLER_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.EREBOR_MAIL_HAUBERK)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(Items.BLUE_DYE)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.EREBOR_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.EREBOR_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_REINFORCED_LEATHER_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(ModEquipmentItems.EREBOR_MAIL_HAUBERK)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.EREBOR_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.EREBOR_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_REINFORCED_COAT.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.EREBOR_MAIL_HAUBERK)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(Items.BLUE_DYE)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.EREBOR_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.EREBOR_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RAVENHILL_REINFORCED_MAIL_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .input(ModEquipmentItems.EREBOR_MAIL_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.EREBOR_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.EREBOR_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_SCALE_COAT.getDefaultStack(), "leggings", Disposition.GOOD)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_SCALE_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RAVENHILL_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(khazadSteelArmorPlate.getItem()),
                                    conditionsFromItem(khazadSteelArmorPlate.getItem()))
                            .offerTo(exporter);
                    //t5
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_PLATE_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelHelmetPlate.getItem()), khazadSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(khazadSteelHelmetPlate.getItem()),
                                    conditionsFromItem(khazadSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .input(ModEquipmentItems.EREBOR_SCALE_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.EREBOR_SCALE_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.EREBOR_SCALE_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_PLATE_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.EREBOR_SCALE_LEGGINGS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelScaleMail.getItem()), khazadSteelScaleMail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.EREBOR_SCALE_LEGGINGS),
                                    conditionsFromItem(ModEquipmentItems.EREBOR_SCALE_LEGGINGS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_PLATE_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(khazadSteelArmorPlate.getItem()),
                                    conditionsFromItem(khazadSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_GUARD_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.EREBOR_PLATE_HELMET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.EREBOR_PLATE_HELMET),
                                    conditionsFromItem(ModEquipmentItems.EREBOR_PLATE_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_GATEWARDEN_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.EREBOR_PLATE_HELMET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.BRONZE_INGOT)
                            .input(ModResourceItems.BRONZE_INGOT)
                            .criterion(hasItem(ModEquipmentItems.EREBOR_PLATE_HELMET),
                                    conditionsFromItem(ModEquipmentItems.EREBOR_PLATE_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_GATEWARDEN_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.EREBOR_PLATE_CHESTPLATE)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.BRONZE_INGOT)
                            .input(Items.BLUE_DYE)
                            .input(ModResourceItems.BRONZE_INGOT)
                            .criterion(hasItem(ModEquipmentItems.EREBOR_PLATE_CHESTPLATE),
                                    conditionsFromItem(ModEquipmentItems.EREBOR_PLATE_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_GATEWARDEN_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.EREBOR_PLATE_LEGGINGS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModEquipmentItems.EREBOR_PLATE_LEGGINGS),
                                    conditionsFromItem(ModEquipmentItems.EREBOR_PLATE_LEGGINGS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_GATEWARDEN_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(khazadSteelArmorPlate.getItem()),
                                    conditionsFromItem(khazadSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RAVENHILL_WATCHWARDEN_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.EREBOR_PLATE_HELMET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.EREBOR_PLATE_HELMET),
                                    conditionsFromItem(ModEquipmentItems.EREBOR_PLATE_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RAVENHILL_WATCHWARDEN_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.EREBOR_PLATE_CHESTPLATE)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.BRONZE_INGOT)
                            .input(Items.GRAY_DYE)
                            .input(ModResourceItems.BRONZE_INGOT)
                            .criterion(hasItem(ModEquipmentItems.EREBOR_PLATE_CHESTPLATE),
                                    conditionsFromItem(ModEquipmentItems.EREBOR_PLATE_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RAVENHILL_WATCHWARDEN_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.EREBOR_PLATE_LEGGINGS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModEquipmentItems.EREBOR_PLATE_LEGGINGS),
                                    conditionsFromItem(ModEquipmentItems.EREBOR_PLATE_LEGGINGS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RAVENHILL_WATCHWARDEN_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(khazadSteelArmorPlate.getItem()),
                                    conditionsFromItem(khazadSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_CAPTAIN_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(Items.GOAT_HORN)
                            .input(ModEquipmentItems.EREBOR_GUARD_HELMET)
                            .input(Items.GOAT_HORN)
                            .criterion(hasItem(ModEquipmentItems.EREBOR_GUARD_HELMET),
                                    conditionsFromItem(ModEquipmentItems.EREBOR_GUARD_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RAVENHILL_SENTINEL_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                            .input(Items.FEATHER)
                            .input(ModEquipmentItems.RAVENHILL_WATCHWARDEN_HELMET)
                            .input(Items.FEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.RAVENHILL_WATCHWARDEN_HELMET),
                                    conditionsFromItem(ModEquipmentItems.RAVENHILL_WATCHWARDEN_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RAVENHILL_SENTINEL_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModEquipmentItems.RAVENHILL_WATCHWARDEN_CHESTPLATE)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModEquipmentItems.RAVENHILL_WATCHWARDEN_CHESTPLATE),
                                    conditionsFromItem(ModEquipmentItems.RAVENHILL_WATCHWARDEN_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RAVENHILL_SENTINEL_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.RAVENHILL_WATCHWARDEN_LEGGINGS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModEquipmentItems.RAVENHILL_WATCHWARDEN_LEGGINGS),
                                    conditionsFromItem(ModEquipmentItems.RAVENHILL_WATCHWARDEN_LEGGINGS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RAVENHILL_SENTINEL_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(khazadSteelArmorPlate.getItem()),
                                    conditionsFromItem(khazadSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    //capes
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.EREBOR_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.STRING)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLUE_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLUE_DYE)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RAVENHILL_SENTINEL_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.STRING)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.GRAY_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.GRAY_DYE)
                            .input(Items.YELLOW_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.YELLOW_DYE)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    //endregion

                    //endregion

                    //region ORCS

                    //region GENERIC
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_LEATHER_STRAP.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_SANDALS.getDefaultStack(), "boots", Disposition.EVIL)
                            .input(Items.STICK)
                            .input(Items.STICK)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_ORCISH_MAIL_COIF.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_LEATHER_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_LEATHER_SCALE_VEST.getDefaultStack(), "chestplate", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_ORCISH_MAIL_HAUBERK.getDefaultStack(), "chestplate", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_ORCISH_MAIL_SHIRT.getDefaultStack(), "chestplate", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_LEATHER_SKIRT.getDefaultStack(), "leggings", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_STRIP_LEATHER_SKIRT.getDefaultStack(), "leggings", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_LEG_BRACER.getDefaultStack(), "leggings", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_ORCISH_MAIL_COAT.getDefaultStack(), "leggings", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT.getDefaultStack(), "leggings", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_ORCISH_REINFORCED_STRIP_LEATHER_SKIRT.getDefaultStack(), "leggings", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_BLACK_FUR_BOOTS.getDefaultStack(), "boots", Disposition.EVIL)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModResourceItems.FUR),
                                    conditionsFromItem(ModResourceItems.FUR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_BROWN_FUR_BOOTS.getDefaultStack(), "boots", Disposition.EVIL)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModResourceItems.FUR),
                                    conditionsFromItem(ModResourceItems.FUR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_GRAY_FUR_BOOTS.getDefaultStack(), "boots", Disposition.EVIL)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModResourceItems.FUR),
                                    conditionsFromItem(ModResourceItems.FUR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_TAN_FUR_BOOTS.getDefaultStack(), "boots", Disposition.EVIL)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModResourceItems.FUR),
                                    conditionsFromItem(ModResourceItems.FUR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_WHITE_FUR_BOOTS.getDefaultStack(), "boots", Disposition.EVIL)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModResourceItems.FUR),
                                    conditionsFromItem(ModResourceItems.FUR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_BRACED_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_MAIL_COIF.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_MAIL_HAUBERK.getDefaultStack(), "chestplate", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_MAIL_SHIRT.getDefaultStack(), "chestplate", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_MAIL_COAT.getDefaultStack(), "leggings", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_REINFORCED_LEATHER_SKIRT.getDefaultStack(), "leggings", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_REINFORCED_STRIP_LEATHER_SKIRT.getDefaultStack(), "leggings", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_SALLET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_BELLY_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_SCALE_VEST.getDefaultStack(), "chestplate", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_REINFORCED_LEG_BRACER.getDefaultStack(), "leggings", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_BRACED_SANDALS.getDefaultStack(), "boots", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges())).input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)

                            .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                    conditionsFromItem(burzumSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_CAPE.getDefaultStack(), "cape", Disposition.EVIL)
                            .input(ModResourceItems.FABRIC)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_LONG_CAPE.getDefaultStack(), "cape", Disposition.EVIL)
                            .input(ModResourceItems.FABRIC)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_SHOULDERS.getDefaultStack(), "cape", Disposition.EVIL)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.RED_DYE)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE.getDefaultStack(), "cape", Disposition.EVIL)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(ModResourceItems.FUR)
                            .input(Items.STRING)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(Items.STRING)
                            .criterion(hasItem(ModResourceItems.FUR),
                                    conditionsFromItem(ModResourceItems.FUR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_BROWN_FUR_SURCOAT_WITH_BONE.getDefaultStack(), "cape", Disposition.EVIL)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(ModResourceItems.FUR)
                            .input(Items.STRING)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(Items.STRING)
                            .criterion(hasItem(ModResourceItems.FUR),
                                    conditionsFromItem(ModResourceItems.FUR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_GRAY_FUR_SURCOAT_WITH_BONE.getDefaultStack(), "cape", Disposition.EVIL)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(ModResourceItems.FUR)
                            .input(Items.STRING)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(Items.STRING)
                            .criterion(hasItem(ModResourceItems.FUR),
                                    conditionsFromItem(ModResourceItems.FUR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_TAN_FUR_SURCOAT_WITH_BONE.getDefaultStack(), "cape", Disposition.EVIL)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(ModResourceItems.FUR)
                            .input(Items.STRING)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(Items.STRING)
                            .criterion(hasItem(ModResourceItems.FUR),
                                    conditionsFromItem(ModResourceItems.FUR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORCISH_WHITE_FUR_SURCOAT_WITH_BONE.getDefaultStack(), "cape", Disposition.EVIL)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(ModResourceItems.FUR)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(ModResourceItems.FUR)
                            .input(Items.STRING)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(Items.STRING)
                            .criterion(hasItem(ModResourceItems.FUR),
                                    conditionsFromItem(ModResourceItems.FUR))
                            .offerTo(exporter);
                    //endregion

                    //region MORDOR
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_MORDOR_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_KETTLE_HAT.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_MORDOR_KETTLE_HAT.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_NASAL_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_MORDOR_NASAL_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_MORDOR_KETTLE_HAT_WITH_COIF.getDefaultStack(), "helmet", Disposition.EVIL)
                            .input(ModEquipmentItems.ORCISH_MAIL_COIF)
                            .input(ModEquipmentItems.MORDOR_KETTLE_HAT)
                            .criterion(hasItem(ModEquipmentItems.ORCISH_MAIL_COIF),
                                    conditionsFromItem(ModEquipmentItems.ORCISH_MAIL_COIF))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_KETTLE_HAT_WITH_COIF.getDefaultStack(), "helmet", Disposition.EVIL)
                            .input(ModEquipmentItems.ORCISH_MAIL_COIF)
                            .input(ModEquipmentItems.MORDOR_KETTLE_HAT)
                            .criterion(hasItem(ModEquipmentItems.ORCISH_MAIL_COIF),
                                    conditionsFromItem(ModEquipmentItems.ORCISH_MAIL_COIF))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_LEATHER_CUIRASS.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_PAINTED_LEATHER_CUIRASS.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.RED_DYE)
                            .input(ModEquipmentItems.MORDOR_LEATHER_CUIRASS)
                            .input(Items.RED_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModEquipmentItems.MORDOR_LEATHER_CUIRASS),
                                    conditionsFromItem(ModEquipmentItems.MORDOR_LEATHER_CUIRASS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_CREST_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_MORDOR_CREST_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_MANDIBLE_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_MORDOR_MANDIBLE_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_SALLET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_MORDOR_SALLET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_CUIRASS.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.MORDOR_LEATHER_CUIRASS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModEquipmentItems.MORDOR_LEATHER_CUIRASS),
                                    conditionsFromItem(ModEquipmentItems.MORDOR_LEATHER_CUIRASS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_MORDOR_CUIRASS.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.MORDOR_LEATHER_CUIRASS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModEquipmentItems.MORDOR_LEATHER_CUIRASS),
                                    conditionsFromItem(ModEquipmentItems.MORDOR_LEATHER_CUIRASS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_GORGET_HAUBERK.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(ModEquipmentItems.ORCISH_MAIL_HAUBERK)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.ORCISH_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.ORCISH_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_MORDOR_GORGET_HAUBERK.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(ModEquipmentItems.ORCISH_MAIL_HAUBERK)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.ORCISH_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.ORCISH_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_REINFORCED_COAT.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.MORDOR_LEATHER_CUIRASS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModEquipmentItems.MORDOR_LEATHER_CUIRASS),
                                    conditionsFromItem(ModEquipmentItems.MORDOR_LEATHER_CUIRASS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_MORDOR_REINFORCED_COAT.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.MORDOR_LEATHER_CUIRASS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModEquipmentItems.MORDOR_LEATHER_CUIRASS),
                                    conditionsFromItem(ModEquipmentItems.MORDOR_LEATHER_CUIRASS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(ModEquipmentItems.ORCISH_MAIL_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.ORCISH_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.ORCISH_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_MORDOR_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(ModEquipmentItems.ORCISH_MAIL_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.ORCISH_MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.ORCISH_MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_PLATE_BOOTS.getDefaultStack(), "boots", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                    conditionsFromItem(burzumSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_MORDOR_PLATE_BOOTS.getDefaultStack(), "boots", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                    conditionsFromItem(burzumSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_GREAT_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_MORDOR_GREAT_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_SNOUT_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.RUSTED_MORDOR_SNOUT_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_DEGRADED_GONDORIAN_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(Items.RED_DYE)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE),
                                    conditionsFromItem(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_PAINTED_CUIRASS.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(Items.RED_DYE)
                            .input(ModEquipmentItems.MORDOR_CUIRASS)
                            .input(Items.RED_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.RED_DYE)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModEquipmentItems.MORDOR_CUIRASS),
                                    conditionsFromItem(ModEquipmentItems.MORDOR_CUIRASS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_SCALE_COAT.getDefaultStack(), "leggings", Disposition.EVIL)
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
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BLACK_URUK_PLATE_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BLACK_URUK_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(ModEquipmentItems.MORDOR_CHESTPLATE)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.MORDOR_CHESTPLATE),
                                    conditionsFromItem(ModEquipmentItems.MORDOR_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BLACK_URUK_PLATE_LEGGINGS.getDefaultStack(), "leggings", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.ORCISH_MAIL_COAT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.ORCISH_MAIL_COAT),
                                    conditionsFromItem(ModEquipmentItems.ORCISH_MAIL_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BLACK_URUK_PLATE_BOOTS.getDefaultStack(), "boots", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                    conditionsFromItem(burzumSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BLACK_URUK_FACE_PLATE_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BLACK_URUK_COMMANDER_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .input(Items.SKELETON_SKULL)
                            .input(ModEquipmentItems.BLACK_URUK_PLATE_HELMET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.RED_DYE)
                            .input(Items.RED_DYE)
                            .criterion(hasItem(ModEquipmentItems.BLACK_URUK_PLATE_HELMET),
                                    conditionsFromItem(ModEquipmentItems.BLACK_URUK_PLATE_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BLACK_URUK_COMMANDER_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.RED_DYE)
                            .input(ModEquipmentItems.BLACK_URUK_PLATE_CHESTPLATE)
                            .input(Items.RED_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.RED_DYE)
                            .input(ModResourceItems.FABRIC)
                            .criterion(hasItem(ModEquipmentItems.BLACK_URUK_PLATE_CHESTPLATE),
                                    conditionsFromItem(ModEquipmentItems.BLACK_URUK_PLATE_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_BLACK_NUMENOREAN_PLATE_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_BLACK_NUMENOREAN_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.MAIL_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_BLACK_NUMENOREAN_PLATE_LEGGINGS.getDefaultStack(), "leggings", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.MAIL_SKIRT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.MAIL_SKIRT),
                                    conditionsFromItem(ModEquipmentItems.MAIL_SKIRT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_BLACK_NUMENOREAN_PLATE_BOOTS.getDefaultStack(), "boots", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelArmorPlate.getItem()),
                                    conditionsFromItem(steelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.MORDOR_BLACK_NUMENOREAN_CAPE.getDefaultStack(), "cape", Disposition.EVIL)
                            .input(ModResourceItems.STEEL_NUGGET)
                            .input(Items.STRING)
                            .input(ModResourceItems.STEEL_NUGGET)
                            .input(Items.BLACK_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(Items.RED_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.RED_DYE)
                            .criterion(hasItem(ModResourceItems.FABRIC),
                                    conditionsFromItem(ModResourceItems.FABRIC))
                            .offerTo(exporter);

                    //endregion

                    //region ISENGARD
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_LEATHER_SCOUT_CAP.getDefaultStack(), "helmet", Disposition.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_LEATHER_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_PAINTED_LEATHER_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .input(ModEquipmentItems.URUK_HAI_LEATHER_CHESTPLATE)
                            .input(ModResourceItems.ISENGARD_BANNER_PATTERN)
                            .criterion(hasItem(ModEquipmentItems.URUK_HAI_LEATHER_CHESTPLATE),
                                    conditionsFromItem(ModEquipmentItems.URUK_HAI_LEATHER_CHESTPLATE))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_LEATHER_HAUBERK.getDefaultStack(), "chestplate", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_BOOTS.getDefaultStack(), "boots", Disposition.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_LIGHT_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_PAINTED_LIGHT_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .input(ModEquipmentItems.URUK_HAI_LIGHT_HELMET)
                            .input(ModResourceItems.ISENGARD_BANNER_PATTERN)
                            .criterion(hasItem(ModEquipmentItems.URUK_HAI_LIGHT_HELMET),
                                    conditionsFromItem(ModEquipmentItems.URUK_HAI_LIGHT_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_REINFORCED_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_PAINTED_REINFORCED_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .input(ModEquipmentItems.URUK_HAI_REINFORCED_HELMET)
                            .input(ModResourceItems.ISENGARD_BANNER_PATTERN)
                            .criterion(hasItem(ModEquipmentItems.URUK_HAI_REINFORCED_HELMET),
                                    conditionsFromItem(ModEquipmentItems.URUK_HAI_REINFORCED_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_MAIL_HAUBERK.getDefaultStack(), "chestplate", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_MAIL_COAT.getDefaultStack(), "leggings", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_MAIL_SKIRT.getDefaultStack(), "leggings", Disposition.EVIL)
                            .input(Items.LEATHER)
                            .input(ModEquipmentItems.URUK_HAI_MAIL_COAT)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.URUK_HAI_MAIL_COAT),
                                    conditionsFromItem(ModEquipmentItems.URUK_HAI_MAIL_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_SOLDIER_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_PAINTED_SOLDIER_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .input(ModEquipmentItems.URUK_HAI_SOLDIER_HELMET)
                            .input(ModResourceItems.ISENGARD_BANNER_PATTERN)
                            .criterion(hasItem(ModEquipmentItems.URUK_HAI_SOLDIER_HELMET),
                                    conditionsFromItem(ModEquipmentItems.URUK_HAI_SOLDIER_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_CUIRASS.getDefaultStack(), "chestplate", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_PLATE_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(ModEquipmentItems.URUK_HAI_CUIRASS)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.URUK_HAI_CUIRASS),
                                    conditionsFromItem(ModEquipmentItems.URUK_HAI_CUIRASS))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_PLATE_LEGGINGS.getDefaultStack(), "leggings", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.URUK_HAI_MAIL_COAT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.URUK_HAI_MAIL_COAT),
                                    conditionsFromItem(ModEquipmentItems.URUK_HAI_MAIL_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_PLATE_BOOTS.getDefaultStack(), "boots", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                    conditionsFromItem(burzumSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_PAINTED_PLATE_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .input(ModEquipmentItems.URUK_HAI_PLATE_HELMET)
                            .input(ModResourceItems.ISENGARD_BANNER_PATTERN)
                            .criterion(hasItem(ModEquipmentItems.URUK_HAI_PLATE_HELMET),
                                    conditionsFromItem(ModEquipmentItems.URUK_HAI_PLATE_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_SAPPER_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_PAINTED_SAPPER_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .input(ModEquipmentItems.URUK_HAI_SAPPER_HELMET)
                            .input(ModResourceItems.ISENGARD_BANNER_PATTERN)
                            .criterion(hasItem(ModEquipmentItems.URUK_HAI_SAPPER_HELMET),
                                    conditionsFromItem(ModEquipmentItems.URUK_HAI_SAPPER_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_BERSERKER_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_PAINTED_BERSERKER_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .input(ModEquipmentItems.URUK_HAI_BERSERKER_HELMET)
                            .input(ModResourceItems.ISENGARD_BANNER_PATTERN)
                            .criterion(hasItem(ModEquipmentItems.URUK_HAI_BERSERKER_HELMET),
                                    conditionsFromItem(ModEquipmentItems.URUK_HAI_BERSERKER_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_COMMANDER_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.URUK_HAI_PLATE_HELMET)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.URUK_HAI_PLATE_HELMET),
                                    conditionsFromItem(ModEquipmentItems.URUK_HAI_PLATE_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.URUK_HAI_PAINTED_COMMANDER_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .input(ModEquipmentItems.URUK_HAI_COMMANDER_HELMET)
                            .input(ModResourceItems.ISENGARD_BANNER_PATTERN)
                            .criterion(hasItem(ModEquipmentItems.URUK_HAI_COMMANDER_HELMET),
                                    conditionsFromItem(ModEquipmentItems.URUK_HAI_COMMANDER_HELMET))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORTHANC_GUARD_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORTHANC_GUARD_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.MAIL_HAUBERK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.MAIL_HAUBERK),
                                    conditionsFromItem(ModEquipmentItems.MAIL_HAUBERK))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORTHANC_GUARD_LEGGINGS.getDefaultStack(), "leggings", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.MAIL_SKIRT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.MAIL_SKIRT),
                                    conditionsFromItem(ModEquipmentItems.MAIL_SKIRT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORTHANC_GUARD_BOOTS.getDefaultStack(), "boots", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelArmorPlate.getItem()),
                                    conditionsFromItem(steelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORTHANC_COMMANDER_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .input(Items.FEATHER)
                            .input(ModEquipmentItems.ORTHANC_GUARD_HELMET)
                            .input(Items.FEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ORTHANC_GUARD_CAPE.getDefaultStack(), "cape", Disposition.EVIL)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.STRING)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LIGHT_GRAY_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLACK_DYE)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.BLACK_DYE)
                            .criterion(hasItem(steelHelmetPlate.getItem()),
                                    conditionsFromItem(steelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    //endregion

                    //region MISTIES
                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_BONE_PAULDRON.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .input(Items.LEATHER)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_SEEKER_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT.getDefaultStack(), "chestplate", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_LEATHER_LEGGINGS.getDefaultStack(), "leggings", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_LACED_BOOTS.getDefaultStack(), "boots", Disposition.EVIL)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_SKULLCAP_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ironHelmetPlate.getItem()),
                                    conditionsFromItem(ironHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_MAIL_COIF.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_MAIL_HAUBERK.getDefaultStack(), "chestplate", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_MAIL_COAT.getDefaultStack(), "leggings", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_SOLDIER_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_SCREECHER_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .input(Items.LEATHER)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT),
                                    conditionsFromItem(ModEquipmentItems.GUNDABAD_LEATHER_SCALE_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_REINFORCED_LEATHER_VEST.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(ModEquipmentItems.ORCISH_LEATHER_SCALE_VEST)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.ORCISH_LEATHER_SCALE_VEST),
                                    conditionsFromItem(ModEquipmentItems.ORCISH_LEATHER_SCALE_VEST))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_CRESTED_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(ModEquipmentItems.GUNDABAD_REINFORCED_LEATHER_VEST)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.GUNDABAD_REINFORCED_LEATHER_VEST),
                                    conditionsFromItem(ModEquipmentItems.GUNDABAD_REINFORCED_LEATHER_VEST))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_HOBGOBLIN_MAIL_COAT.getDefaultStack(), "leggings", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.GUNDABAD_MAIL_COAT)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.ORCISH_MAIL_COAT),
                                    conditionsFromItem(ModEquipmentItems.ORCISH_MAIL_COAT))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATED_BOOTS.getDefaultStack(), "boots", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                    conditionsFromItem(burzumSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_LARGE_CREST_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_LONG_HORN_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_SMALL_HORN_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_CAPTAIN_HELMET.getDefaultStack(), "helmet", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelHelmetPlate.getItem()), burzumSteelHelmetPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                    conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_TROPHY_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.EVIL)
                            .input(Items.SKELETON_SKULL)
                            .input(Items.SKELETON_SKULL)
                            .input(Items.STICK)
                            .input(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE)
                            .input(Items.STICK)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .criterion(hasItem(ModEquipmentItems.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE),
                                    conditionsFromItem(ModEquipmentItems.GUNDABAD_MAIL_HAUBERK))
                            .offerTo(exporter);

                    //endregion

                    //endregion

                    //MOUNT ARMORS

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_HORSE_ARMOR.getDefaultStack(), "mount_armor", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(steelArmorPlate.getItem()),
                                    conditionsFromItem(steelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_HORSE_ARMOR.getDefaultStack(), "mount_armor", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(steelArmorPlate.getItem()),
                                    conditionsFromItem(steelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HORSE_ARMOR.getDefaultStack(), "mount_armor", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                            .criterion(hasItem(steelArmorPlate.getItem()),
                                    conditionsFromItem(steelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_HORSE_ARMOR.getDefaultStack(), "mount_armor", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(edhelSteelArmorPlate.getItem()),
                                    conditionsFromItem(edhelSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BROADHOOF_GOAT_PADDED_ARMOR.getDefaultStack(), "mount_armor", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .criterion(hasItem(Items.LEATHER),
                                    conditionsFromItem(Items.LEATHER))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR.getDefaultStack(), "mount_armor", Disposition.GOOD)
                            .input(Items.LEATHER)
                            .input(Items.GOLD_INGOT)
                            .input(ModEquipmentItems.BROADHOOF_GOAT_PADDED_ARMOR)
                            .input(Items.GOLD_INGOT)
                            .input(Items.LEATHER)
                            .input(ModResourceItems.FABRIC)
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.BROADHOOF_GOAT_PADDED_ARMOR),
                                    conditionsFromItem(ModEquipmentItems.BROADHOOF_GOAT_PADDED_ARMOR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.BROADHOOF_GOAT_PLATE_ARMOR.getDefaultStack(), "mount_armor", Disposition.GOOD)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .input(ModResourceItems.FABRIC)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelArmorPlate.getItem()), khazadSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelChainmail.getItem()), khazadSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(khazadSteelArmorPlate.getItem()),
                                    conditionsFromItem(khazadSteelArmorPlate.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.WARG_LEATHER_ARMOR.getDefaultStack(), "mount_armor", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.WARG_REINFORCED_LEATHER_ARMOR.getDefaultStack(), "mount_armor", Disposition.EVIL)
                            .input(Items.LEATHER)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(ModEquipmentItems.WARG_LEATHER_ARMOR)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(Items.LEATHER)
                            .input(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bones")))
                            .input(Items.LEATHER)
                            .criterion(hasItem(ModEquipmentItems.WARG_LEATHER_ARMOR),
                                    conditionsFromItem(ModEquipmentItems.WARG_LEATHER_ARMOR))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.WARG_MORDOR_MAIL_ARMOR.getDefaultStack(), "mount_armor", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.WARG_MORDOR_PLATE_ARMOR.getDefaultStack(), "mount_armor", Disposition.EVIL)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .input(ModEquipmentItems.WARG_MORDOR_MAIL_ARMOR)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelArmorPlate.getItem()), burzumSteelArmorPlate.getComponentChanges()))
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .input(Items.LEATHER)
                            .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelChainmail.getItem()), burzumSteelChainmail.getComponentChanges()))
                            .criterion(hasItem(ironChainmail.getItem()),
                                    conditionsFromItem(ironChainmail.getItem()))
                            .offerTo(exporter);

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.WARG_ISENGARD_PLATE_ARMOR.getDefaultStack(), "mount_armor", Disposition.EVIL)
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

                    ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModEquipmentItems.WARG_GUNDABAD_PLATE_ARMOR.getDefaultStack(), "mount_armor", Disposition.EVIL)
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