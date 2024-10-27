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
import net.jukoz.me.item.utils.ModSmithingTrimMaterials;
import net.jukoz.me.item.utils.ModSmithingTrimPatterns;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.item.trim.ArmorTrimMaterials;
import net.minecraft.item.trim.ArmorTrimPattern;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ArtisanTableRecipeProvider extends RecipeProvider {

    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup;

    public ArtisanTableRecipeProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture) {
        super(output, registryLookupFuture);

        this.registryLookup = registryLookupFuture;
    }

    @Override
    public String getName() {
        return "ArtisantableRecipes";
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
        createArtisanTableSwordRecipe(exporter, MetalTypes.IRON, Items.IRON_SWORD.getDefaultStack(), false);

        createArtisanTableSpearRecipe(exporter, MetalTypes.IRON, ModWeaponItems.IRON_SPEAR.getDefaultStack(), false);

        createArtisanTableSwordRecipe(exporter, MetalTypes.GOLD, Items.GOLDEN_SWORD.getDefaultStack(), false);

        createArtisanTableSpearRecipe(exporter, MetalTypes.GOLD, ModWeaponItems.GOLDEN_SPEAR.getDefaultStack(), false);

        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_SWORD.getDefaultStack(), false);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_NOBLE_SWORD.getDefaultStack(), true);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_SWORD.getDefaultStack(), false);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_NOBLE_SWORD.getDefaultStack(), true);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_SWORD.getDefaultStack(), false);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_NOBLE_SWORD.getDefaultStack(), true);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.STEEL_SWORD.getDefaultStack(), false);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.BLACK_NUMENOREAN_SWORD.getDefaultStack(), true);

        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_DAGGER.getDefaultStack(), false);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_NOBLE_DAGGER.getDefaultStack(), true);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_DAGGER.getDefaultStack(), false);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_NOBLE_DAGGER.getDefaultStack(), true);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_DAGGER.getDefaultStack(), false);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_NOBLE_DAGGER.getDefaultStack(), true);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.BLACK_NUMENOREAN_DAGGER.getDefaultStack(), true);

        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_LONGSWORD.getDefaultStack(), false);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_NOBLE_LONGSWORD.getDefaultStack(), true);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_LONGSWORD.getDefaultStack(), false);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_NOBLE_LONGSWORD.getDefaultStack(), true);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_LONGSWORD.getDefaultStack(), false);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_NOBLE_LONGSWORD.getDefaultStack(), true);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.BLACK_NUMENOREAN_LONGSWORD.getDefaultStack(), true);

        createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_AXE.getDefaultStack(), false);
        createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_NOBLE_AXE.getDefaultStack(), true);
        createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_AXE.getDefaultStack(), false);
        createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_NOBLE_AXE.getDefaultStack(), true);
        createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_AXE.getDefaultStack(), false);
        createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_NOBLE_AXE.getDefaultStack(), true);
        createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.BLACK_NUMENOREAN_AXE.getDefaultStack(), true);

        createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_SPEAR.getDefaultStack(), false);
        createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_NOBLE_SPEAR.getDefaultStack(), true);
        createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_SPEAR.getDefaultStack(), false);
        createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_NOBLE_SPEAR.getDefaultStack(), true);
        createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_SPEAR.getDefaultStack(), false);
        createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_NOBLE_SPEAR.getDefaultStack(), true);
        createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.STEEL_SPEAR.getDefaultStack(), false);

        createArtisanTableSwordRecipe(exporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_SWORD.getDefaultStack(), false);
        createArtisanTableSwordRecipe(exporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_NOBLE_SWORD.getDefaultStack(), true);
        createArtisanTableSwordRecipe(exporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.KHAZAD_STEEL_SWORD.getDefaultStack(), false);

        createArtisanTableSwordRecipe(exporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_SWORD.getDefaultStack(), false);
        createArtisanTableSwordRecipe(exporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_NOBLE_SWORD.getDefaultStack(), true);
        createArtisanTableSwordRecipe(exporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.EDHEL_STEEL_SWORD.getDefaultStack(), false);

        createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ORC_SWORD.getDefaultStack(), false);
        createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_FALCHION.getDefaultStack(), false);
        createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_ELITE_CLEAVER.getDefaultStack(), true);
        createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.BURZUM_STEEL_SWORD.getDefaultStack(), false);

        //region TOOLS
        createArtisanTablePickaxeRecipe(exporter, MetalTypes.IRON, Items.IRON_PICKAXE.getDefaultStack());
        createArtisanTableAxeRecipe(exporter, MetalTypes.IRON, Items.IRON_AXE.getDefaultStack(), false);
        createArtisanTableShovelRecipe(exporter, MetalTypes.IRON, Items.IRON_SHOVEL.getDefaultStack());
        createArtisanTableHoeRecipe(exporter, MetalTypes.IRON, Items.IRON_HOE.getDefaultStack());

        createArtisanTablePickaxeRecipe(exporter, MetalTypes.IRON, Items.GOLDEN_PICKAXE.getDefaultStack());
        createArtisanTableAxeRecipe(exporter, MetalTypes.IRON, Items.GOLDEN_AXE.getDefaultStack(), false);
        createArtisanTableShovelRecipe(exporter, MetalTypes.IRON, Items.GOLDEN_SHOVEL.getDefaultStack());
        createArtisanTableHoeRecipe(exporter, MetalTypes.IRON, Items.GOLDEN_HOE.getDefaultStack());

        createArtisanTablePickaxeRecipe(exporter, MetalTypes.STEEL, ModToolItems.STEEL_PICKAXE.getDefaultStack());
        createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, ModToolItems.STEEL_AXE.getDefaultStack(), false);
        createArtisanTableShovelRecipe(exporter, MetalTypes.STEEL, ModToolItems.STEEL_SHOVEL.getDefaultStack());
        createArtisanTableHoeRecipe(exporter, MetalTypes.STEEL, ModToolItems.STEEL_HOE.getDefaultStack());

        createArtisanTablePickaxeRecipe(exporter, MetalTypes.KHAZAD_STEEL, ModToolItems.KHAZAD_STEEL_PICKAXE.getDefaultStack());
        createArtisanTableAxeRecipe(exporter, MetalTypes.KHAZAD_STEEL, ModToolItems.KHAZAD_STEEL_AXE.getDefaultStack(), false);
        createArtisanTableShovelRecipe(exporter, MetalTypes.KHAZAD_STEEL, ModToolItems.KHAZAD_STEEL_SHOVEL.getDefaultStack());
        createArtisanTableHoeRecipe(exporter, MetalTypes.KHAZAD_STEEL, ModToolItems.KHAZAD_STEEL_HOE.getDefaultStack());

        createArtisanTablePickaxeRecipe(exporter, MetalTypes.EDHEL_STEEL, ModToolItems.EDHEL_STEEL_PICKAXE.getDefaultStack());
        createArtisanTableAxeRecipe(exporter, MetalTypes.EDHEL_STEEL, ModToolItems.EDHEL_STEEL_AXE.getDefaultStack(), false);
        createArtisanTableShovelRecipe(exporter, MetalTypes.EDHEL_STEEL, ModToolItems.EDHEL_STEEL_SHOVEL.getDefaultStack());
        createArtisanTableHoeRecipe(exporter, MetalTypes.EDHEL_STEEL, ModToolItems.EDHEL_STEEL_HOE.getDefaultStack());

        createArtisanTablePickaxeRecipe(exporter, MetalTypes.EDHEL_STEEL, ModToolItems.BURZUM_STEEL_PICKAXE.getDefaultStack());
        createArtisanTableAxeRecipe(exporter, MetalTypes.EDHEL_STEEL, ModToolItems.BURZUM_STEEL_AXE.getDefaultStack(), false);
        createArtisanTableShovelRecipe(exporter, MetalTypes.EDHEL_STEEL, ModToolItems.BURZUM_STEEL_SHOVEL.getDefaultStack());
        createArtisanTableHoeRecipe(exporter, MetalTypes.EDHEL_STEEL, ModToolItems.BURZUM_STEEL_HOE.getDefaultStack());

        createArtisanTablePickaxeRecipe(exporter, MetalTypes.MITHRIL, ModToolItems.MITHRIL_PICKAXE.getDefaultStack());
        createArtisanTableAxeRecipe(exporter, MetalTypes.MITHRIL, ModToolItems.MITHRIL_AXE.getDefaultStack(), false);
        createArtisanTableShovelRecipe(exporter, MetalTypes.MITHRIL, ModToolItems.MITHRIL_SHOVEL.getDefaultStack());
        createArtisanTableHoeRecipe(exporter, MetalTypes.MITHRIL, ModToolItems.MITHRIL_HOE.getDefaultStack());
        //endregion

        //region ARMOR
        /*createArtisanTableHelmetRecipe(exporter, List.of(steelArmorPlate, steelHelmetPlate, steelArmorPlate,
                steelArmorPlate, steelArmorPlate), ModEquipmentItems.GONDORIAN_PLATE_HELMET.getDefaultStack());*/
        //endregion
    }

    private void createArtisanTableSwordRecipe(RecipeExporter exporter, MetalTypes metal, ItemStack output, boolean noble) {
        ItemStack blade = new ItemStack(ModResourceItems.BLADE);
        blade.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        ItemStack swordHilt = new ItemStack(ModResourceItems.SWORD_HILT);

        if (!noble) {
            swordHilt.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    getMetalIdentifier(metal))), getPattern()));
        } else {
            swordHilt.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    Identifier.of(MetalTypes.GOLD.getName()))), getPattern()));
        }

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "sword")
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(blade.getItem()), blade.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(swordHilt.getItem()), swordHilt.getComponentChanges()))
                .input(Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(blade.getItem()),
                        FabricRecipeProvider.conditionsFromItem(blade.getItem()))
                .offerTo(exporter);
    }

    private void createArtisanTableSpearRecipe(RecipeExporter exporter, MetalTypes metal, ItemStack output, boolean noble) {
        ItemStack blade = new ItemStack(ModResourceItems.SHORT_BLADE);
        blade.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        if (!noble) {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "spear")
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(blade.getItem()), blade.getComponentChanges()))
                    .input(Items.STICK)
                    .input(Items.STICK)
                    .criterion(FabricRecipeProvider.hasItem(blade.getItem()),
                            FabricRecipeProvider.conditionsFromItem(blade.getItem()))
                    .offerTo(exporter);
        } else {
            ItemStack rod = new ItemStack(ModResourceItems.ROD);
            rod.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    Identifier.of(MetalTypes.GOLD.getName()))), getPattern()));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "spear")
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(blade.getItem()), blade.getComponentChanges()))
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                    .input(Items.STICK)
                    .criterion(FabricRecipeProvider.hasItem(blade.getItem()),
                            FabricRecipeProvider.conditionsFromItem(blade.getItem()))
                    .offerTo(exporter);
        }
    }

    private void createArtisanTablePickaxeRecipe(RecipeExporter exporter, MetalTypes metal, ItemStack output) {
        ItemStack pickaxeHead = new ItemStack(ModResourceItems.PICKAXE_HEAD);
        pickaxeHead.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "pickaxe")
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(pickaxeHead.getItem()), pickaxeHead.getComponentChanges()))
                .input(Items.STICK)
                .input(Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(pickaxeHead.getItem()),
                        FabricRecipeProvider.conditionsFromItem(pickaxeHead.getItem()))
                .offerTo(exporter);
    }

    private void createArtisanTableAxeRecipe(RecipeExporter exporter, MetalTypes metal, ItemStack output, boolean noble) {
        ItemStack pickaxeHead = new ItemStack(ModResourceItems.AXE_HEAD);
        pickaxeHead.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        if (!noble){
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "axe")
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(pickaxeHead.getItem()), pickaxeHead.getComponentChanges()))
                    .input(Items.STICK)
                    .input(Items.STICK)
                    .criterion(FabricRecipeProvider.hasItem(pickaxeHead.getItem()),
                            FabricRecipeProvider.conditionsFromItem(pickaxeHead.getItem()))
                    .offerTo(exporter);
        } else {
            ItemStack rod = new ItemStack(ModResourceItems.ROD);
            rod.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    Identifier.of(MetalTypes.GOLD.getName()))), getPattern()));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "axe")
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(pickaxeHead.getItem()), pickaxeHead.getComponentChanges()))
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                    .input(Items.STICK)
                    .criterion(FabricRecipeProvider.hasItem(pickaxeHead.getItem()),
                            FabricRecipeProvider.conditionsFromItem(pickaxeHead.getItem()))
                    .offerTo(exporter);
        }
    }

    private void createArtisanTableShovelRecipe(RecipeExporter exporter, MetalTypes metal, ItemStack output) {
        ItemStack pickaxeHead = new ItemStack(ModResourceItems.SHOVEL_HEAD);
        pickaxeHead.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "shovel")
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(pickaxeHead.getItem()), pickaxeHead.getComponentChanges()))
                .input(Items.STICK)
                .input(Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(pickaxeHead.getItem()),
                        FabricRecipeProvider.conditionsFromItem(pickaxeHead.getItem()))
                .offerTo(exporter);
    }

    private void createArtisanTableHoeRecipe(RecipeExporter exporter, MetalTypes metal, ItemStack output) {
        ItemStack pickaxeHead = new ItemStack(ModResourceItems.SHOVEL_HEAD);
        pickaxeHead.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "hoe")
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(pickaxeHead.getItem()), pickaxeHead.getComponentChanges()))
                .input(Items.STICK)
                .input(Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(pickaxeHead.getItem()),
                        FabricRecipeProvider.conditionsFromItem(pickaxeHead.getItem()))
                .offerTo(exporter);
    }

    private void createArtisanTableHelmetRecipe(RecipeExporter exporter, List<ItemStack> inputs, ItemStack output) {
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "helmet")
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(inputs.get(0).getItem()), inputs.get(0).getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(inputs.get(1).getItem()), inputs.get(1).getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(inputs.get(2).getItem()), inputs.get(2).getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(inputs.get(3).getItem()), inputs.get(3).getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(inputs.get(4).getItem()), inputs.get(4).getComponentChanges()))
                .criterion(FabricRecipeProvider.hasItem(inputs.get(0).getItem()),
                        FabricRecipeProvider.conditionsFromItem(inputs.get(0).getItem()))
                .offerTo(exporter);
    }
}