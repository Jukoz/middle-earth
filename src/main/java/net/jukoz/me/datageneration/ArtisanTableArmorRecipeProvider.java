package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.impl.recipe.ingredient.builtin.ComponentsIngredient;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.forge.MetalTypes;
import net.jukoz.me.datageneration.custom.ArtisanTableRecipeJsonBuilder;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModResourceItems;
import net.jukoz.me.item.ModToolItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.utils.ModSmithingTrimPatterns;
import net.jukoz.me.resources.datas.Disposition;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.item.trim.ArmorTrimPattern;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ArtisanTableArmorRecipeProvider extends RecipeProvider {

    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup;

    public ArtisanTableArmorRecipeProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture) {
        super(output, registryLookupFuture);

        this.registryLookup = registryLookupFuture;
    }

    @Override
    public String getName() {
        return "ArtisanTableArmorRecipes";
    }

    public RegistryWrapper.Impl<ArmorTrimMaterial> getArmorTrimMaterialsRegistry(){
        RegistryWrapper.Impl<ArmorTrimMaterial> armorTrimMaterialsRegistry;

        try {
            armorTrimMaterialsRegistry = registryLookup.get().getWrapperOrThrow(RegistryKeys.TRIM_MATERIAL);
        } catch (Exception ignored) {
            throw new IllegalStateException("Data generation without registries failed!");
        }
        return armorTrimMaterialsRegistry;
    }

    public RegistryWrapper.Impl<ArmorTrimPattern> getArmorTrimPatternsRegistry(){
        RegistryWrapper.Impl<ArmorTrimPattern> armorTrimPatternsRegistry;

        try {
            armorTrimPatternsRegistry = registryLookup.get().getWrapperOrThrow(RegistryKeys.TRIM_PATTERN);
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
    public void generate(RecipeExporter exporter) {
        ItemStack goldArmorPlate = new ItemStack(ModResourceItems.ARMOR_PLATE);
        goldArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MetalTypes.GOLD.getName()))), getPattern()));
        
        ItemStack steelArmorPlate = new ItemStack(ModResourceItems.ARMOR_PLATE);
        steelArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName()))), getPattern()));
        ItemStack steelHelmetPlate = new ItemStack(ModResourceItems.HELMET_PLATE);
        steelHelmetPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName()))), getPattern()));
        ItemStack steelChainmail = new ItemStack(ModResourceItems.CHAINMAIL);
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
        ItemStack edhelSteelChainmail = new ItemStack(ModResourceItems.CHAINMAIL);
        edhelSteelChainmail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName()))), getPattern()));
        ItemStack edhelSteelScaleMail = new ItemStack(ModResourceItems.SCALE_MAIL);
        edhelSteelScaleMail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName()))), getPattern()));

        ItemStack ironArmorPlate = new ItemStack(ModResourceItems.ARMOR_PLATE);
        ironArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MetalTypes.IRON.getName()))), getPattern()));
        ItemStack ironHelmetPlate = new ItemStack(ModResourceItems.HELMET_PLATE);
        ironHelmetPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MetalTypes.IRON.getName()))), getPattern()));
        ItemStack ironChainmail = new ItemStack(ModResourceItems.CHAINMAIL);
        ironChainmail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MetalTypes.IRON.getName()))), getPattern()));

        ItemStack bronzeArmorPlate = new ItemStack(ModResourceItems.ARMOR_PLATE);
        bronzeArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MiddleEarth.MOD_ID, MetalTypes.BRONZE.getName()))), getPattern()));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.SHIRRIFF_HAT.getDefaultStack(), "helmet", Disposition.GOOD)
                .input(Items.STRING)
                .input(Items.LEATHER)
                .input(Items.FEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(Items.LEATHER),
                        FabricRecipeProvider.conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.SHIRRIFF_HAT).getPath() + "_artisan"));

        //region MEN

        //region GENERIC

        //T1
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.STRAW_HAT.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                .input(Items.STRING)
                .input(ModResourceItems.STRAW)
                .input(Items.STRING)
                .input(ModResourceItems.STRAW)
                .input(ModResourceItems.STRAW)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.STRAW),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.STRAW))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.STRAW_HAT).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.WOVEN_HAT.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                .input(Items.STRING)
                .input(Items.WHITE_WOOL)
                .input(Items.STRING)
                .input(Items.STRING)
                .input(Items.STRING)
                .criterion(FabricRecipeProvider.hasItem(Items.WHITE_WOOL),
                        FabricRecipeProvider.conditionsFromItem(Items.WHITE_WOOL))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.WOVEN_HAT).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.BYCOCKET.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                .input(Items.LEATHER)
                .input(Items.WHITE_WOOL)
                .input(Items.LEATHER)
                .input(Items.STRING)
                .input(Items.STRING)
                .criterion(FabricRecipeProvider.hasItem(Items.WHITE_WOOL),
                        FabricRecipeProvider.conditionsFromItem(Items.WHITE_WOOL))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.BYCOCKET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ARMING_COAT.getDefaultStack(), "chestplate", Disposition.NEUTRAL)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ARMING_COAT).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ARMING_SKIRT.getDefaultStack(), "leggings", Disposition.NEUTRAL)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ARMING_SKIRT).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.SHOES.getDefaultStack(), "boots", Disposition.NEUTRAL)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.SHOES).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.WORK_SHOES.getDefaultStack(), "boots", Disposition.NEUTRAL)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.WORK_SHOES).getPath() + "_artisan"));

        //T2
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.LEATHER_SKULLCAP.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(Items.LEATHER),
                        FabricRecipeProvider.conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.LEATHER_SKULLCAP).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GAMBESON_CAP.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                .input(Items.LEATHER)
                .input(Items.WHITE_WOOL)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(Items.LEATHER),
                        FabricRecipeProvider.conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GAMBESON_CAP).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GAMBESON_COWL.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                .input(Items.LEATHER)
                .input(Items.WHITE_WOOL)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(Items.LEATHER),
                        FabricRecipeProvider.conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GAMBESON_COWL).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GAMBESON.getDefaultStack(), "chestplate", Disposition.NEUTRAL)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .input(Items.WHITE_WOOL)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(Items.LEATHER),
                        FabricRecipeProvider.conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GAMBESON).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.LEATHER_VEST.getDefaultStack(), "chestplate", Disposition.NEUTRAL)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(Items.LEATHER),
                        FabricRecipeProvider.conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.LEATHER_VEST).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.LEATHER_SCALE_VEST.getDefaultStack(), "chestplate", Disposition.NEUTRAL)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(Items.LEATHER),
                        FabricRecipeProvider.conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.LEATHER_SCALE_VEST).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.STURDY_BOOTS.getDefaultStack(), "boots", Disposition.NEUTRAL)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(Items.LEATHER),
                        FabricRecipeProvider.conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.STURDY_BOOTS).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.TRAVELLING_BOOTS.getDefaultStack(), "boots", Disposition.NEUTRAL)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(Items.LEATHER),
                        FabricRecipeProvider.conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.TRAVELLING_BOOTS).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.HIGH_CUT_BOOTS.getDefaultStack(), "boots", Disposition.NEUTRAL)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(Items.LEATHER),
                        FabricRecipeProvider.conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.HIGH_CUT_BOOTS).getPath() + "_artisan"));
        //T3
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.CHAIN_COIF.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ironChainmail.getItem()),
                        FabricRecipeProvider.conditionsFromItem(ironChainmail.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.CHAIN_COIF).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.KETTLE_HAT.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                .input(ModEquipmentItems.CHAIN_COIF)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ironChainmail.getItem()),
                        FabricRecipeProvider.conditionsFromItem(ironChainmail.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.KETTLE_HAT).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.OPEN_FACE_HELMET.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ironChainmail.getItem()),
                        FabricRecipeProvider.conditionsFromItem(ironChainmail.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.OPEN_FACE_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.CHAIN_HAUBERK.getDefaultStack(), "chestplate", Disposition.NEUTRAL)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ironChainmail.getItem()),
                        FabricRecipeProvider.conditionsFromItem(ironChainmail.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.CHAIN_HAUBERK).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.CHAIN_SKIRT.getDefaultStack(), "leggings", Disposition.NEUTRAL)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ironChainmail.getItem()),
                        FabricRecipeProvider.conditionsFromItem(ironChainmail.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.CHAIN_SKIRT).getPath() + "_artisan"));

        //T4
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.SALLET.getDefaultStack(), "helmet", Disposition.NEUTRAL)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(steelHelmetPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(steelHelmetPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.SALLET).getPath() + "_artisan"));

        //HOODS
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.HOOD.getDefaultStack(), "hood", Disposition.NEUTRAL)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(Items.STRING)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.HOOD).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.BLACK_FUR_HOOD.getDefaultStack(), "hood", Disposition.NEUTRAL)
                .input(ModResourceItems.FUR)
                .input(ModEquipmentItems.HOOD)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(Items.STRING)
                .input(ModResourceItems.FUR)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.HOOD),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.HOOD))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.BLACK_FUR_HOOD).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.BROWN_FUR_HOOD.getDefaultStack(), "hood", Disposition.NEUTRAL)
                .input(ModResourceItems.FUR)
                .input(ModEquipmentItems.HOOD)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(Items.STRING)
                .input(ModResourceItems.FUR)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.HOOD),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.HOOD))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.BROWN_FUR_HOOD).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GRAY_FUR_HOOD.getDefaultStack(), "hood", Disposition.NEUTRAL)
                .input(ModResourceItems.FUR)
                .input(ModEquipmentItems.HOOD)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(Items.STRING)
                .input(ModResourceItems.FUR)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.HOOD),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.HOOD))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GRAY_FUR_HOOD).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.TAN_FUR_HOOD.getDefaultStack(), "hood", Disposition.NEUTRAL)
                .input(ModResourceItems.FUR)
                .input(ModEquipmentItems.HOOD)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(Items.STRING)
                .input(ModResourceItems.FUR)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.HOOD),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.HOOD))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.TAN_FUR_HOOD).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.WHITE_FUR_HOOD.getDefaultStack(), "hood", Disposition.NEUTRAL)
                .input(ModResourceItems.FUR)
                .input(ModEquipmentItems.HOOD)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(Items.STRING)
                .input(ModResourceItems.FUR)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.HOOD),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.HOOD))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.WHITE_FUR_HOOD).getPath() + "_artisan"));

        //CAPES
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.CAPE.getDefaultStack(), "cape", Disposition.NEUTRAL)
                .input(ModResourceItems.FABRIC)
                .input(Items.STRING)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.CAPE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.SURCOAT.getDefaultStack(), "cape", Disposition.NEUTRAL)
                .input(ModResourceItems.FABRIC)
                .input(Items.STRING)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(Items.STRING)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.SURCOAT).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.CLOAK.getDefaultStack(), "cape", Disposition.NEUTRAL)
                .input(ModResourceItems.FABRIC)
                .input(Items.STRING)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.CLOAK).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.BLACK_FUR_CLOAK.getDefaultStack(), "cape", Disposition.NEUTRAL)
                .input(Items.STRING)
                .input(ModEquipmentItems.BLACK_FUR)
                .input(Items.STRING)
                .input(Items.STRING)
                .input(ModEquipmentItems.CLOAK)
                .input(Items.STRING)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.CLOAK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.CLOAK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.BLACK_FUR_CLOAK).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.BROWN_FUR_CLOAK.getDefaultStack(), "cape", Disposition.NEUTRAL)
                .input(Items.STRING)
                .input(ModEquipmentItems.BROWN_FUR)
                .input(Items.STRING)
                .input(Items.STRING)
                .input(ModEquipmentItems.CLOAK)
                .input(Items.STRING)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.CLOAK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.CLOAK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.BROWN_FUR_CLOAK).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GRAY_FUR_CLOAK.getDefaultStack(), "cape", Disposition.NEUTRAL)
                .input(Items.STRING)
                .input(ModEquipmentItems.GRAY_FUR)
                .input(Items.STRING)
                .input(Items.STRING)
                .input(ModEquipmentItems.CLOAK)
                .input(Items.STRING)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.CLOAK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.CLOAK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GRAY_FUR_CLOAK).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.TAN_FUR_CLOAK.getDefaultStack(), "cape", Disposition.NEUTRAL)
                .input(Items.STRING)
                .input(ModEquipmentItems.TAN_FUR)
                .input(Items.STRING)
                .input(Items.STRING)
                .input(ModEquipmentItems.CLOAK)
                .input(Items.STRING)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.CLOAK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.CLOAK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.TAN_FUR_CLOAK).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.WHITE_FUR_CLOAK.getDefaultStack(), "cape", Disposition.NEUTRAL)
                .input(Items.STRING)
                .input(ModEquipmentItems.WHITE_FUR)
                .input(Items.STRING)
                .input(Items.STRING)
                .input(ModEquipmentItems.CLOAK)
                .input(Items.STRING)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.CLOAK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.CLOAK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.WHITE_FUR_CLOAK).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.BLACK_FUR.getDefaultStack(), "cape", Disposition.NEUTRAL)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(Items.STRING)
                .input(ModResourceItems.FUR)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.BLACK_FUR).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.BROWN_FUR.getDefaultStack(), "cape", Disposition.NEUTRAL)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(Items.STRING)
                .input(ModResourceItems.FUR)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.BROWN_FUR).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GRAY_FUR.getDefaultStack(), "cape", Disposition.NEUTRAL)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(Items.STRING)
                .input(ModResourceItems.FUR)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GRAY_FUR).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.TAN_FUR.getDefaultStack(), "cape", Disposition.NEUTRAL)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(Items.STRING)
                .input(ModResourceItems.FUR)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.TAN_FUR).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.WHITE_FUR.getDefaultStack(), "cape", Disposition.NEUTRAL)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(Items.STRING)
                .input(ModResourceItems.FUR)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.WHITE_FUR).getPath() + "_artisan"));
        //endregion

        //region GONDOR
        //T2
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(Items.LEATHER),
                        FabricRecipeProvider.conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_BOOTS).getPath() + "_artisan"));

        //T3
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CABASSET_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ironHelmetPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(ironHelmetPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_CABASSET_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CHAIN_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ironChainmail.getItem()),
                        FabricRecipeProvider.conditionsFromItem(ironChainmail.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_CHAIN_HAUBERK).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(ModEquipmentItems.GONDORIAN_CHAIN_HAUBERK)
                .input(Items.LEATHER)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                .input(Items.LEATHER)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GONDORIAN_CHAIN_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GONDORIAN_CHAIN_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_LEATHER_CHESTPLATE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_LEATHER_CUIRASS.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(ModEquipmentItems.GONDORIAN_CHAIN_HAUBERK)
                .input(Items.LEATHER)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                .input(Items.LEATHER)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GONDORIAN_CHAIN_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GONDORIAN_CHAIN_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_LEATHER_CUIRASS).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_TABBARD.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.GONDORIAN_CHAIN_HAUBERK)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GONDORIAN_CHAIN_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GONDORIAN_CHAIN_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_TABBARD).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CHAIN_COAT.getDefaultStack(), "leggings", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ironChainmail.getItem()),
                        FabricRecipeProvider.conditionsFromItem(ironChainmail.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_CHAIN_COAT).getPath() + "_artisan"));

        //T4
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_SOLDIER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(steelHelmetPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(steelHelmetPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_SOLDIER_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_SOLDIER_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.GONDORIAN_CHAIN_HAUBERK)
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GONDORIAN_CHAIN_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GONDORIAN_CHAIN_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_SOLDIER_CHESTPLATE).getPath() + "_artisan"));

        //T5 PLATE
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_PLATE_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(steelHelmetPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(steelHelmetPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_PLATE_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .input(ModEquipmentItems.GONDORIAN_SOLDIER_CHESTPLATE)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GONDORIAN_SOLDIER_CHESTPLATE),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GONDORIAN_SOLDIER_CHESTPLATE))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .input(ModEquipmentItems.GONDORIAN_CHAIN_COAT)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GONDORIAN_CHAIN_COAT),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GONDORIAN_CHAIN_COAT))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_PLATE_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(steelArmorPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(steelArmorPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_PLATE_BOOTS).getPath() + "_artisan"));

        //T5 CAPTAIN
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CAPTAIN_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .input(ModEquipmentItems.GONDORIAN_PLATE_HELMET)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GONDORIAN_PLATE_HELMET),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GONDORIAN_PLATE_HELMET))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_CAPTAIN_HELMET).getPath() + "_artisan"));

        //T5 KING'S GUARD
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_KINGS_GUARD_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .input(Items.FEATHER)
                .input(ModEquipmentItems.GONDORIAN_PLATE_HELMET)
                .input(Items.FEATHER)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GONDORIAN_PLATE_HELMET),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GONDORIAN_PLATE_HELMET))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_KINGS_GUARD_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_KINGS_GUARD_CHESTKPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE)
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_KINGS_GUARD_CHESTKPLATE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_KINGS_GUARD_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .input(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_KINGS_GUARD_LEGGINGS).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_KINGS_GUARD_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(goldArmorPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(goldArmorPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_KINGS_GUARD_BOOTS).getPath() + "_artisan"));

        //T5 CITADEL GUARD
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CITADEL_GUARD_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .input(ModEquipmentItems.GONDORIAN_PLATE_HELMET)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GONDORIAN_PLATE_HELMET),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GONDORIAN_PLATE_HELMET))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CITADEL_GUARD_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE)
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_CHESTPLATE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CITADEL_GUARD_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .input(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_LEGGINGS).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CITADEL_GUARD_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(goldArmorPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(goldArmorPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_BOOTS).getPath() + "_artisan"));
        
        //T5 FOUNTAIN GUARD
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .input(ModResourceItems.MITHRIL_NUGGET)
                .input(ModEquipmentItems.GONDORIAN_CAPTAIN_HELMET)
                .input(ModResourceItems.MITHRIL_NUGGET)
                .input(ModResourceItems.SWAN_FEATHER)
                .input(ModResourceItems.SWAN_FEATHER)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GONDORIAN_CAPTAIN_HELMET),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GONDORIAN_CAPTAIN_HELMET))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE)
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_LEGGINGS).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(goldArmorPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(goldArmorPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_BOOTS).getPath() + "_artisan"));

        //HOODS
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CITADEL_GUARD_HOOD.getDefaultStack(), "hood", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(Items.BLACK_DYE)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_HOOD).getPath() + "_artisan"));

        //CAPES
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CAPTAIN_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(Items.IRON_NUGGET)
                .input(ModResourceItems.FABRIC)
                .input(Items.BLACK_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.BLACK_DYE)
                .input(Items.WHITE_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.WHITE_DYE)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_CAPTAIN_CAPE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_HERO_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(Items.IRON_NUGGET)
                .input(ModResourceItems.FABRIC)
                .input(Items.WHITE_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.WHITE_DYE)
                .input(Items.BLACK_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.BLACK_DYE)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_HERO_CAPE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_KINGS_GUARD_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(Items.GOLD_NUGGET)
                .input(ModResourceItems.FABRIC)
                .input(Items.BLACK_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.BLACK_DYE)
                .input(Items.WHITE_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.WHITE_DYE)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_KINGS_GUARD_CAPE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_CITADEL_GUARD_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(Items.GOLD_NUGGET)
                .input(ModResourceItems.FABRIC)
                .input(Items.BLACK_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.BLACK_DYE)
                .input(Items.YELLOW_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.YELLOW_DYE)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_CAPE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(Items.GOLD_NUGGET)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(Items.BLACK_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.BLACK_DYE)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_CAPE).getPath() + "_artisan"));
        //endregion

        //region ROHAN
        //T1
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_REINFORCED_COAT.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.ARMING_COAT)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.ARMING_COAT),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.ARMING_COAT))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_REINFORCED_COAT).getPath() + "_artisan"));

        //T2
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_LEATHER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(Items.LEATHER),
                        FabricRecipeProvider.conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_LEATHER_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_REINFORCED_LEATHER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.IRON_NUGGET)
                .input(Items.IRON_NUGGET)
                .criterion(FabricRecipeProvider.hasItem(Items.LEATHER),
                        FabricRecipeProvider.conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_REINFORCED_LEATHER_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_ORNAMENTED_LEATHER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(ModResourceItems.BRONZE_NUGGET)
                .input(ModResourceItems.BRONZE_NUGGET)
                .criterion(FabricRecipeProvider.hasItem(Items.LEATHER),
                        FabricRecipeProvider.conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_ORNAMENTED_LEATHER_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_LEATHER_VEST.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.ARMING_COAT)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.ARMING_COAT),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.ARMING_COAT))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_LEATHER_VEST).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.ARMING_COAT)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.ARMING_COAT),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.ARMING_COAT))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_LEATHER_SCALE_VEST).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_GAMBESON.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.GAMBESON)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GAMBESON),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GAMBESON))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_GAMBESON).getPath() + "_artisan"));

        //T3
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_MILITIA_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .input(Items.LEATHER)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                .input(Items.LEATHER)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(bronzeArmorPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(bronzeArmorPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_MILITIA_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_BRACED_MILITIA_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .input(Items.LEATHER)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                .input(Items.LEATHER)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ironArmorPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(ironArmorPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_BRACED_MILITIA_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_REINFORCED_MILITIA_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .input(Items.LEATHER)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                .input(Items.LEATHER)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(bronzeArmorPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(bronzeArmorPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_REINFORCED_MILITIA_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_ORNAMENTED_MILITIA_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .input(Items.LEATHER)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                .input(Items.LEATHER)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(bronzeArmorPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(bronzeArmorPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_ORNAMENTED_MILITIA_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_MAIL_SHIRT.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.CHAIN_HAUBERK)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.CHAIN_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.CHAIN_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_MAIL_SHIRT).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_MAIL_SHIRT_OPEN.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.CHAIN_HAUBERK)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.CHAIN_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.CHAIN_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_MAIL_SHIRT_OPEN).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_MAIL_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.CHAIN_HAUBERK)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.CHAIN_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.CHAIN_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_MAIL_HAUBERK).getPath() + "_artisan"));

        ///T4
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_SOLDIER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(steelHelmetPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(steelHelmetPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_SOLDIER_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_ORNAMENTED_SOLDIER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(steelHelmetPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(steelHelmetPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_ORNAMENTED_SOLDIER_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_ROYAL_GUARD_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(steelHelmetPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(steelHelmetPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_ROYAL_GUARD_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.CHAIN_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.CHAIN_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_ORNAMENTED_SCALE_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK)
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.CHAIN_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.CHAIN_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_ORNAMENTED_SCALE_HAUBERK).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_SCALE_JACKET.getDefaultStack(), "leggings", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.CHAIN_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.CHAIN_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_SCALE_JACKET).getPath() + "_artisan"));

        //T5 EORLING MARSHAL
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.EORLING_MARSHAL_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(steelHelmetPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(steelHelmetPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.EORLING_MARSHAL_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.EORLING_MARSHAL_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .input(ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.EORLING_MARSHAL_CHESTPLATE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.EORLING_MARSHAL_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .input(ModEquipmentItems.ROHIRRIC_SCALE_JACKET)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.ROHIRRIC_SCALE_JACKET),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.ROHIRRIC_SCALE_JACKET))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.EORLING_MARSHAL_LEGGINGS).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.EORLING_MARSHAL_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(goldArmorPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(goldArmorPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.EORLING_MARSHAL_BOOTS).getPath() + "_artisan"));

        //T5 HORSE LORD
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.HORSE_LORD_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(steelHelmetPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(steelHelmetPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.HORSE_LORD_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.HORSE_LORD_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .input(ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.ROHIRRIC_SCALE_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.HORSE_LORD_CHESTPLATE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.HORSE_LORD_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .input(ModEquipmentItems.ROHIRRIC_SCALE_JACKET)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.ROHIRRIC_SCALE_JACKET),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.ROHIRRIC_SCALE_JACKET))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.HORSE_LORD_LEGGINGS).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.HORSE_LORD_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(goldArmorPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(goldArmorPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.HORSE_LORD_BOOTS).getPath() + "_artisan"));

        //CAPES
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.BRONZE_NUGGET)
                .input(ModResourceItems.FABRIC)
                .input(Items.GREEN_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.GREEN_DYE)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_CAPE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ROHIRRIC_ROYAL_GUARD_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.BRONZE_NUGGET)
                .input(ModResourceItems.FABRIC)
                .input(Items.YELLOW_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.YELLOW_DYE)
                .input(Items.GREEN_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.GREEN_DYE)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ROHIRRIC_ROYAL_GUARD_CAPE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.EORLING_MARSHAL_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.STEEL_NUGGET)
                .input(ModResourceItems.FABRIC)
                .input(Items.GREEN_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.GREEN_DYE)
                .input(Items.YELLOW_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.WHITE_DYE)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.EORLING_MARSHAL_CAPE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.HORSE_LORD_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.BRONZE_NUGGET)
                .input(ModResourceItems.FABRIC)
                .input(Items.BLUE_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.BLUE_DYE)
                .input(Items.RED_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.RED_DYE)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.HORSE_LORD_CAPE).getPath() + "_artisan"));
        //endregion

        //region DALE

        //T1
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.DALISH_ARMING_COAT_BLACK_FUR.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FUR)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.DALISH_ARMING_COAT_BLACK_FUR).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.DALISH_ARMING_COAT_BROWN_FUR.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FUR)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.DALISH_ARMING_COAT_BROWN_FUR).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.DALISH_ARMING_COAT_TAN_FUR.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FUR)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.DALISH_ARMING_COAT_TAN_FUR).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HEYDAY_ARMING_COAT.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(Items.ORANGE_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.ORANGE_DYE)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.DALISH_HEYDAY_ARMING_COAT).getPath() + "_artisan"));

        //T2
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.DALISH_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(Items.LEATHER),
                        FabricRecipeProvider.conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.DALISH_BOOTS).getPath() + "_artisan"));

        //T3
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironHelmetPlate.getItem()), ironHelmetPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironArmorPlate.getItem()), ironArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ironHelmetPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(ironHelmetPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.DALISH_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HELMET_BLACK_FUR.getDefaultStack(), "helmet", Disposition.GOOD)
                .input(ModResourceItems.FUR)
                .input(ModEquipmentItems.DALISH_HELMET)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.DALISH_HELMET),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.DALISH_HELMET))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.DALISH_HELMET_BLACK_FUR).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HELMET_BROWN_FUR.getDefaultStack(), "helmet", Disposition.GOOD)
                .input(ModResourceItems.FUR)
                .input(ModEquipmentItems.DALISH_HELMET)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.DALISH_HELMET),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.DALISH_HELMET))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.DALISH_HELMET_BROWN_FUR).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HELMET_TAN_FUR.getDefaultStack(), "helmet", Disposition.GOOD)
                .input(ModResourceItems.FUR)
                .input(ModEquipmentItems.DALISH_HELMET)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.DALISH_HELMET),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.DALISH_HELMET))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.DALISH_HELMET_TAN_FUR).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.DALISH_CHAIN_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.CHAIN_HAUBERK)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.CHAIN_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.CHAIN_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.DALISH_CHAIN_HAUBERK).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.DALISH_CHAIN_COAT.getDefaultStack(), "leggings", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .input(ModEquipmentItems.CHAIN_SKIRT)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.CHAIN_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.CHAIN_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.DALISH_CHAIN_COAT).getPath() + "_artisan"));

        //T4
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.DALISH_BURGONET.getDefaultStack(), "helmet", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(steelHelmetPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(steelHelmetPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.DALISH_BURGONET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.DALISH_SCALE_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelScaleMail.getItem()), steelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ironHelmetPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(ironHelmetPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.DALISH_SCALE_HAUBERK).getPath() + "_artisan"));

        //T4 HEYDAY
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HEYDAY_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .input(ModEquipmentItems.DALISH_HELMET)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .input(ModResourceItems.FUR)
                .input(ModResourceItems.FUR)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.DALISH_HELMET),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.DALISH_HELMET))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.DALISH_HEYDAY_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HEYDAY_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.DALISH_SCALE_HAUBERK)
                .input(ModResourceItems.FABRIC)
                .input(Items.ORANGE_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.ORANGE_DYE)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.DALISH_SCALE_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.DALISH_SCALE_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.DALISH_HEYDAY_CHESTPLATE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HEYDAY_CHAIN_COAT.getDefaultStack(), "leggings", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.DALISH_CHAIN_COAT)
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.DALISH_CHAIN_COAT),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.DALISH_CHAIN_COAT))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.DALISH_HEYDAY_CHAIN_COAT).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.DALISH_HEYDAY_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(steelArmorPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(steelArmorPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.DALISH_HEYDAY_BOOTS).getPath() + "_artisan"));

        //T5 BARDING
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.BARDING_SOLDIER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelHelmetPlate.getItem()), steelHelmetPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(steelHelmetPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(steelHelmetPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.BARDING_SOLDIER_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.BARDING_SOLDIER_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .input(ModEquipmentItems.DALISH_SCALE_HAUBERK)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.DALISH_SCALE_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.DALISH_SCALE_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.BARDING_SOLDIER_CHESTPLATE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.BARDING_CHAIN_SKIRT.getDefaultStack(), "leggings", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .input(ModEquipmentItems.DALISH_CHAIN_COAT)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.DALISH_CHAIN_COAT),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.DALISH_CHAIN_COAT))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.BARDING_CHAIN_SKIRT).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.BARDING_PLATED_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelArmorPlate.getItem()), steelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(steelArmorPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(steelArmorPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.BARDING_PLATED_BOOTS).getPath() + "_artisan"));

        //T5 BARDING SERGEANT
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.BARDING_SERGEANT_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .input(Items.FEATHER)
                .input(ModEquipmentItems.BARDING_SOLDIER_HELMET)
                .input(Items.FEATHER)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.BARDING_SOLDIER_HELMET),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.BARDING_SOLDIER_HELMET))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.BARDING_SERGEANT_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.BARDING_SERGEANT_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.BARDING_SERGEANT_CHESTPLATE)
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelChainmail.getItem()), steelChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.BARDING_SERGEANT_CHESTPLATE),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.BARDING_SERGEANT_CHESTPLATE))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.BARDING_SERGEANT_CHESTPLATE).getPath() + "_artisan"));

        //CAPES
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.BARDING_SURCOAT.getDefaultStack(), "cape", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(Items.STRING)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(Items.PURPLE_DYE)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.BARDING_SURCOAT).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.BARDING_SERGEANT_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(Items.STRING)
                .input(ModResourceItems.FABRIC)
                .input(Items.ORANGE_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.ORANGE_DYE)
                .input(Items.PURPLE_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.PURPLE_DYE)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.BARDING_SERGEANT_CAPE).getPath() + "_artisan"));
        //endregion
        
        //region BLACK NUMENOREANS

        //endregion

        //endregion

        //region ELVES

        //region GENERIC

        //T1
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ELVEN_ARMING_COAT.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ELVEN_ARMING_COAT).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ELVEN_ARMING_SKIRT.getDefaultStack(), "leggings", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ELVEN_ARMING_SKIRT).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ELVEN_OPEN_ARMING_SKIRT.getDefaultStack(), "leggings", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ELVEN_OPEN_ARMING_SKIRT).getPath() + "_artisan"));

        //T2
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ELVEN_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(Items.LEATHER),
                        FabricRecipeProvider.conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ELVEN_BOOTS).getPath() + "_artisan"));

        //T3
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ELVEN_CHAIN_COIF.getDefaultStack(), "helmet", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ironChainmail.getItem()),
                        FabricRecipeProvider.conditionsFromItem(ironChainmail.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ELVEN_CHAIN_COIF).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ELVEN_CHAIN_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ironChainmail.getItem()),
                        FabricRecipeProvider.conditionsFromItem(ironChainmail.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ELVEN_CHAIN_HAUBERK).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.ELVEN_CHAIN_SKIRT.getDefaultStack(), "leggings", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ironChainmail.getItem()),
                        FabricRecipeProvider.conditionsFromItem(ironChainmail.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.ELVEN_CHAIN_SKIRT).getPath() + "_artisan"));

        //endregion

        //region LOTHLORIEN
        //T1
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_DIADEM.getDefaultStack(), "helmet", Disposition.GOOD)
                .input(ModResourceItems.BRONZE_NUGGET)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeArmorPlate.getItem()), bronzeArmorPlate.getComponentChanges()))
                .input(ModResourceItems.BRONZE_NUGGET)
                .criterion(FabricRecipeProvider.hasItem(bronzeArmorPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(bronzeArmorPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.LORIEN_DIADEM).getPath() + "_artisan"));

        //T2
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_LEATHER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(Items.LEATHER),
                        FabricRecipeProvider.conditionsFromItem(Items.LEATHER))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.LORIEN_LEATHER_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_ARMING_COAT.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.ELVEN_ARMING_COAT)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.ELVEN_ARMING_COAT),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.ELVEN_ARMING_COAT))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.LORIEN_ARMING_COAT).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_ARMING_SKIRT.getDefaultStack(), "leggings", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(ModEquipmentItems.ELVEN_ARMING_SKIRT)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.ELVEN_ARMING_SKIRT),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.ELVEN_ARMING_SKIRT))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.LORIEN_ARMING_SKIRT).getPath() + "_artisan"));

        //T3
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_CHAIN_COIF_DIADEM.getDefaultStack(), "helmet", Disposition.GOOD)
                .input(ModEquipmentItems.ELVEN_CHAIN_COIF)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .input(ModEquipmentItems.LORIEN_DIADEM)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.ELVEN_CHAIN_COIF),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.ELVEN_CHAIN_COIF))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.LORIEN_CHAIN_COIF_DIADEM).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_SHORT_CHAIN_COIF_DIADEM.getDefaultStack(), "helmet", Disposition.GOOD)
                .input(ModEquipmentItems.ELVEN_CHAIN_COIF)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .input(ModEquipmentItems.LORIEN_DIADEM)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.ELVEN_CHAIN_COIF),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.ELVEN_CHAIN_COIF))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.LORIEN_SHORT_CHAIN_COIF_DIADEM).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_CHAIN_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(ModEquipmentItems.ELVEN_CHAIN_HAUBERK)
                .input(Items.LEATHER)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironChainmail.getItem()), ironChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.ELVEN_CHAIN_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.ELVEN_CHAIN_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.LORIEN_CHAIN_HAUBERK).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_MARCHWARDEN_CHAIN_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.ELVEN_CHAIN_HAUBERK)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.ELVEN_CHAIN_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.ELVEN_CHAIN_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.LORIEN_MARCHWARDEN_CHAIN_HAUBERK).getPath() + "_artisan"));

        //T4
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_SOLDIER_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelHelmetPlate.getItem()), edhelSteelHelmetPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(edhelSteelHelmetPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(edhelSteelHelmetPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.LORIEN_SOLDIER_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_SOLDIER_CHAIN_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.LORIEN_CHAIN_HAUBERK)
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.LORIEN_CHAIN_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.LORIEN_CHAIN_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.LORIEN_SOLDIER_CHAIN_HAUBERK).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_SOLDIER_SCALE_HAUBERK.getDefaultStack(), "chestplate", Disposition.GOOD)
                .input(Items.LEATHER)
                .input(Items.LEATHER)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(edhelSteelScaleMail.getItem()),
                        FabricRecipeProvider.conditionsFromItem(edhelSteelScaleMail.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.LORIEN_SOLDIER_SCALE_HAUBERK).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_SCALE_COAT.getDefaultStack(), "leggings", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(edhelSteelScaleMail.getItem()),
                        FabricRecipeProvider.conditionsFromItem(edhelSteelScaleMail.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.LORIEN_SCALE_COAT).getPath() + "_artisan"));

        //T5 GALADHRIM
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelHelmetPlate.getItem()), edhelSteelHelmetPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(edhelSteelHelmetPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(edhelSteelHelmetPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GALADHRIM_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                .input(ModEquipmentItems.LORIEN_SOLDIER_SCALE_HAUBERK)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.LORIEN_SOLDIER_SCALE_HAUBERK),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.LORIEN_SOLDIER_SCALE_HAUBERK))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GALADHRIM_CHESTPLATE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .input(ModEquipmentItems.LORIEN_SCALE_COAT)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelScaleMail.getItem()), edhelSteelScaleMail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.LORIEN_SCALE_COAT),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.LORIEN_SCALE_COAT))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GALADHRIM_LEGGINGS).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(edhelSteelArmorPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(edhelSteelArmorPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GALADHRIM_BOOTS).getPath() + "_artisan"));

        //T5 GALADHRIM LORD
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_LORD_HELMET.getDefaultStack(), "helmet", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .input(ModEquipmentItems.GALADHRIM_HELMET)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GALADHRIM_HELMET),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GALADHRIM_HELMET))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GALADHRIM_LORD_HELMET).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_LORD_CHESTPLATE.getDefaultStack(), "chestplate", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.GALADHRIM_CHESTPLATE)
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GALADHRIM_CHESTPLATE),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GALADHRIM_CHESTPLATE))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GALADHRIM_LORD_CHESTPLATE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_LORD_LEGGINGS.getDefaultStack(), "leggings", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModEquipmentItems.GALADHRIM_LEGGINGS)
                .input(ModResourceItems.FABRIC)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelChainmail.getItem()), edhelSteelChainmail.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(ModEquipmentItems.GALADHRIM_LEGGINGS),
                        FabricRecipeProvider.conditionsFromItem(ModEquipmentItems.GALADHRIM_LEGGINGS))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GALADHRIM_LORD_LEGGINGS).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_LORD_BOOTS.getDefaultStack(), "boots", Disposition.GOOD)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(goldArmorPlate.getItem()), goldArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelArmorPlate.getItem()), edhelSteelArmorPlate.getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(edhelSteelHelmetPlate.getItem()),
                        FabricRecipeProvider.conditionsFromItem(edhelSteelHelmetPlate.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GALADHRIM_LORD_BOOTS).getPath() + "_artisan"));

        //HOODS
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_MARCHWARDEN_HOOD.getDefaultStack(), "hood", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(Items.GRAY_DYE)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.LORIEN_MARCHWARDEN_HOOD).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_HOOD.getDefaultStack(), "hood", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(Items.BLUE_DYE)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GALADHRIM_HOOD).getPath() + "_artisan"));

        //CAPES
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.LORIEN_MARCHWARDEN_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.BRONZE_NUGGET)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(Items.GRAY_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.GRAY_DYE)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.LORIEN_MARCHWARDEN_CAPE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_CAPE.getDefaultStack(), "cape", Disposition.GOOD)
                .input(Items.STRING)
                .input(ModResourceItems.FABRIC)
                .input(Items.STRING)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(Items.BLUE_DYE)
                .input(ModResourceItems.FABRIC)
                .input(Items.BLUE_DYE)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GALADHRIM_CAPE).getPath() + "_artisan"));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ModEquipmentItems.GALADHRIM_LORD_SURCOAT.getDefaultStack(), "cape", Disposition.GOOD)
                .input(ModResourceItems.FABRIC)
                .input(Items.WHITE_DYE)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(Items.LEATHER)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.FABRIC)
                .input(ModResourceItems.EDHEL_STEEL_NUGGET)
                .input(ModResourceItems.FABRIC)
                .criterion(FabricRecipeProvider.hasItem(ModResourceItems.FABRIC),
                        FabricRecipeProvider.conditionsFromItem(ModResourceItems.FABRIC))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(ModEquipmentItems.GALADHRIM_LORD_SURCOAT).getPath() + "_artisan"));
        //endregion

        //endregion

        //region DWARVES

        //region GENERIC

        //endregion

        //region EREBOR

        //endregion

        //endregion

        //region ORCS

        //region GENERIC

        //endregion

        //region MORDOR

        //endregion

        //region ISENGARD

        //endregion

        //region MISTIES

        //endregion

        //endregion

    }
}