package net.sevenstars.middleearth.datageneration.providers.recipes;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.impl.recipe.ingredient.builtin.ComponentsIngredient;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.equipment.trim.ArmorTrimPattern;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.forge.MetalTypes;
import net.sevenstars.middleearth.datageneration.custom.ArtisanTableRecipeJsonBuilder;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.ModSmithingTrimPatterns;
import net.sevenstars.middleearth.resources.datas.Disposition;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.data.recipe.RecipeGenerator.conditionsFromPredicates;
import static net.minecraft.data.recipe.RecipeGenerator.hasItem;

public class ArtisanTableHandheldRecipeProvider extends RecipeProvider {

    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup;

    public ArtisanTableHandheldRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);

        this.registryLookup = registriesFuture;
    }

    @Override
    public String getName() {
        return "ArtisanTableHandheldRecipes";
    }

    public RegistryWrapper.Impl<ArmorTrimMaterial> getArmorTrimMaterialsRegistry(){
        RegistryWrapper.Impl<ArmorTrimMaterial> armorTrimMaterialsRegistry;

        try {
            armorTrimMaterialsRegistry = registryLookup.get().getOrThrow(RegistryKeys.TRIM_MATERIAL);
        } catch (Exception ignored) {
            throw new IllegalStateException("Data generation without registries failed!");
        }
        return armorTrimMaterialsRegistry;
    }

    public RegistryWrapper.Impl<ArmorTrimPattern> getArmorTrimPatternsRegistry(){
        RegistryWrapper.Impl<ArmorTrimPattern> armorTrimPatternsRegistry;

        try {
            armorTrimPatternsRegistry = registryLookup.get().getOrThrow(RegistryKeys.TRIM_PATTERN);
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

                //region WEAPONS
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.IRON, Items.IRON_SWORD.getDefaultStack(), false, Disposition.NEUTRAL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.IRON, WeaponItemsME.IRON_DAGGER.getDefaultStack(), false, Disposition.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.IRON, WeaponItemsME.IRON_SPEAR.getDefaultStack(), false, Disposition.NEUTRAL);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.GOLD, Items.GOLDEN_SWORD.getDefaultStack(), false, Disposition.NEUTRAL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.GOLD, WeaponItemsME.GOLDEN_DAGGER.getDefaultStack(), false, Disposition.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.GOLD, WeaponItemsME.GOLDEN_SPEAR.getDefaultStack(), false, Disposition.NEUTRAL);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.NETHERITE, Items.NETHERITE_SWORD.getDefaultStack(), false, Disposition.NEUTRAL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.NETHERITE, WeaponItemsME.NETHERITE_DAGGER.getDefaultStack(), false, Disposition.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.NETHERITE, WeaponItemsME.NETHERITE_SPEAR.getDefaultStack(), false, Disposition.NEUTRAL);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BRONZE, WeaponItemsME.BRONZE_SWORD.getDefaultStack(), false, Disposition.NEUTRAL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BRONZE, WeaponItemsME.BRONZE_DAGGER.getDefaultStack(), false, Disposition.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BRONZE, WeaponItemsME.BRONZE_SPEAR.getDefaultStack(), false, Disposition.NEUTRAL);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.CRUDE, WeaponItemsME.CRUDE_FALCHION.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.CRUDE, WeaponItemsME.CRUDE_DAGGER.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.CRUDE, WeaponItemsME.CRUDE_SPEAR.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.CRUDE, WeaponItemsME.CRUDE_LONGBLADE.getDefaultStack(), false, Disposition.EVIL);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_SWORD.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_SWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_SWORD.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_SWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_SWORD.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_SWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_SWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.STEEL_SWORD.getDefaultStack(), false, Disposition.NEUTRAL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_SWORD.getDefaultStack(), true, Disposition.EVIL);

                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_DAGGER.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_DAGGER.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_DAGGER.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_DAGGER.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_DAGGER.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_DAGGER.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_DAGGER.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_DAGGER.getDefaultStack(), true, Disposition.EVIL);

                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_LONGSWORD.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_LONGSWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_LONGSWORD.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_LONGSWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_LONGSWORD.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_LONGSWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_SCIMITAR.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_LONGSWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_LONGSWORD.getDefaultStack(), true, Disposition.EVIL);

                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_AXE.getDefaultStack(), false, Optional.empty(), Disposition.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), Disposition.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_AXE.getDefaultStack(), false, Optional.empty(), Disposition.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), Disposition.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_AXE.getDefaultStack(), false, Optional.empty(), Disposition.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), Disposition.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_AXE.getDefaultStack(), true, Optional.empty(), Disposition.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_AXE.getDefaultStack(), true, Optional.empty(), Disposition.EVIL);

                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_SPEAR.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_SPEAR.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_FOUNTAIN_GUARD_SPEAR.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_SPEAR.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_SPEAR.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_SPEAR.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_SPEAR.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_SPEAR.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.STEEL_SPEAR.getDefaultStack(), false, Disposition.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_SPEAR.getDefaultStack(), true, Disposition.EVIL);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_SWORD.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_SWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.KHAZAD_STEEL_SWORD.getDefaultStack(), false, Disposition.GOOD);

                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_DAGGER.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_DAGGER.getDefaultStack(), true, Disposition.GOOD);

                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_LONGSWORD.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_LONGSWORD.getDefaultStack(), true, Disposition.GOOD);

                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_AXE.getDefaultStack(), false, Optional.empty(), Disposition.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), Disposition.GOOD);

                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.KHAZAD_STEEL_SPEAR.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_SPEAR.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_SPEAR.getDefaultStack(), true, Disposition.GOOD);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_SWORD.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_SWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.EDHEL_STEEL_SWORD.getDefaultStack(), false, Disposition.GOOD);

                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_DAGGER.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_DAGGER.getDefaultStack(), true, Disposition.GOOD);

                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_GLAIVE.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_GLAIVE.getDefaultStack(), true, Disposition.GOOD);

                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_AXE.getDefaultStack(), false, Optional.empty(), Disposition.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), Disposition.GOOD);

                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.EDHEL_STEEL_SPEAR.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_SPEAR.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_SPEAR.getDefaultStack(), true, Disposition.GOOD);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.BURZUM_STEEL_SWORD.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_SWORD.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_FALCHION.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_CLEAVER.getDefaultStack(), true, Disposition.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_CLEAVER.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_FALCHION.getDefaultStack(), true, Disposition.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_FALCHION.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_CLEAVER.getDefaultStack(), true, Disposition.EVIL);

                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_KNIFE.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_DAGGER.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_DAGGER.getDefaultStack(), true, Disposition.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_DAGGER.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_KNIFE.getDefaultStack(), true, Disposition.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_SHANK.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_DAGGER.getDefaultStack(), true, Disposition.EVIL);

                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_BROADBLADE.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_SCIMITAR.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_WARBLADE.getDefaultStack(), true, Disposition.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_WARBLADE.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_WARBLADE.getDefaultStack(), true, Disposition.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_WARBLADE.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_SCIMITAR.getDefaultStack(), true, Disposition.EVIL);

                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_AXE.getDefaultStack(), false, Optional.empty(), Disposition.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_AXE.getDefaultStack(), false, Optional.empty(), Disposition.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_AXE.getDefaultStack(), true, Optional.empty(), Disposition.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_AXE.getDefaultStack(), false, Optional.empty(), Disposition.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_AXE.getDefaultStack(), true, Optional.empty(), Disposition.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_AXE.getDefaultStack(), false, Optional.empty(), Disposition.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_BATTLEAXE.getDefaultStack(), true, Optional.empty(), Disposition.EVIL);

                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.BURZUM_STEEL_SPEAR.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_SPEAR.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_SPEAR.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_SPEAR.getDefaultStack(), true, Disposition.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_SPEAR.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_SPEAR.getDefaultStack(), true, Disposition.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_SPEAR.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_SPEAR.getDefaultStack(), true, Disposition.EVIL);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, WeaponItemsME.GONDORIAN_BOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.GONDORIAN_LONGBOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableNobleLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.GONDORIAN_NOBLE_LONGBOW.getDefaultStack(), Disposition.GOOD);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, WeaponItemsME.ROHIRRIC_BOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableNobleBowRecipe(itemLookup, recipeExporter, WeaponItemsME.ROHIRRIC_NOBLE_BOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.ROHIRRIC_LONGBOW.getDefaultStack(), Disposition.GOOD);

                createArtisanTableLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.DALISH_LONGBOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableNobleLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.DALISH_HEYDAY_LONGBOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableNobleLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.DALISH_NOBLE_LONGBOW.getDefaultStack(), Disposition.GOOD);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, WeaponItemsME.LORIEN_BOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.LORIEN_LONGBOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableNobleLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.LORIEN_NOBLE_LONGBOW.getDefaultStack(), Disposition.GOOD);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, WeaponItemsME.EREBOR_BOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableNobleBowRecipe(itemLookup, recipeExporter, WeaponItemsME.EREBOR_NOBLE_BOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableCrossbowRecipe(itemLookup, recipeExporter, WeaponItemsME.EREBOR_CROSSBOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableNobleCrossbowRecipe(itemLookup, recipeExporter, WeaponItemsME.EREBOR_NOBLE_CROSSBOW.getDefaultStack(), Disposition.GOOD);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, WeaponItemsME.ORCISH_BOW.getDefaultStack(), Disposition.EVIL);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, WeaponItemsME.MORDOR_BOW.getDefaultStack(), Disposition.EVIL);
                createArtisanTableNobleLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.MORDOR_ELITE_LONGBOW.getDefaultStack(), Disposition.EVIL);

                createArtisanTableNobleBowRecipe(itemLookup, recipeExporter, WeaponItemsME.URUK_HAI_BOW.getDefaultStack(), Disposition.EVIL);
                createArtisanTableNobleCrossbowRecipe(itemLookup, recipeExporter, WeaponItemsME.URUK_HAI_CROSSBOW.getDefaultStack(), Disposition.EVIL);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, WeaponItemsME.GUNDABAD_BOW.getDefaultStack(), Disposition.EVIL);
                createArtisanTableCrossbowRecipe(itemLookup, recipeExporter, WeaponItemsME.GUNDABAD_CROSSBOW.getDefaultStack(), Disposition.EVIL);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, Items.BOW.getDefaultStack(), Disposition.NEUTRAL);
                createArtisanTableCrossbowRecipe(itemLookup, recipeExporter, Items.CROSSBOW.getDefaultStack(), Disposition.NEUTRAL);
                //endregion

                //region TOOLS
                createToolSet(itemLookup, exporter, MetalTypes.BRONZE, ToolItemsME.BRONZE_PICKAXE.getDefaultStack(),
                        ToolItemsME.BRONZE_AXE.getDefaultStack(),
                        ToolItemsME.BRONZE_SHOVEL.getDefaultStack(),
                        ToolItemsME.BRONZE_HOE.getDefaultStack(),
                        Optional.empty(), Disposition.NEUTRAL);

                createToolSet(itemLookup, exporter, MetalTypes.CRUDE, ToolItemsME.CRUDE_PICKAXE.getDefaultStack(),
                        ToolItemsME.CRUDE_AXE.getDefaultStack(),
                        ToolItemsME.CRUDE_SHOVEL.getDefaultStack(),
                        ToolItemsME.CRUDE_HOE.getDefaultStack(),
                        Optional.empty(), Disposition.EVIL);

                createToolSet(itemLookup, exporter, MetalTypes.IRON, Items.IRON_PICKAXE.getDefaultStack(),
                        Items.IRON_AXE.getDefaultStack(),
                        Items.IRON_SHOVEL.getDefaultStack(),
                        Items.IRON_HOE.getDefaultStack(),
                        Optional.empty(), Disposition.NEUTRAL);

                createToolSet(itemLookup, exporter, MetalTypes.GOLD, Items.GOLDEN_PICKAXE.getDefaultStack(),
                        Items.GOLDEN_AXE.getDefaultStack(),
                        Items.GOLDEN_SHOVEL.getDefaultStack(),
                        Items.GOLDEN_HOE.getDefaultStack(),
                        Optional.empty(), Disposition.NEUTRAL);

                createToolSet(itemLookup, exporter, MetalTypes.STEEL, ToolItemsME.STEEL_PICKAXE.getDefaultStack(),
                        ToolItemsME.STEEL_AXE.getDefaultStack(),
                        ToolItemsME.STEEL_SHOVEL.getDefaultStack(),
                        ToolItemsME.STEEL_HOE.getDefaultStack(),
                        Optional.empty(), Disposition.NEUTRAL);

                createToolSet(itemLookup, exporter, MetalTypes.KHAZAD_STEEL, ToolItemsME.KHAZAD_STEEL_PICKAXE.getDefaultStack(),
                        ToolItemsME.KHAZAD_STEEL_AXE.getDefaultStack(),
                        ToolItemsME.KHAZAD_STEEL_SHOVEL.getDefaultStack(),
                        ToolItemsME.KHAZAD_STEEL_HOE.getDefaultStack(),
                        Optional.empty(), Disposition.GOOD);

                createToolSet(itemLookup, exporter, MetalTypes.EDHEL_STEEL, ToolItemsME.EDHEL_STEEL_PICKAXE.getDefaultStack(),
                        ToolItemsME.EDHEL_STEEL_AXE.getDefaultStack(),
                        ToolItemsME.EDHEL_STEEL_SHOVEL.getDefaultStack(),
                        ToolItemsME.EDHEL_STEEL_HOE.getDefaultStack(),
                        Optional.empty(), Disposition.GOOD);

                createToolSet(itemLookup, exporter, MetalTypes.BURZUM_STEEL, ToolItemsME.BURZUM_STEEL_PICKAXE.getDefaultStack(),
                        ToolItemsME.BURZUM_STEEL_AXE.getDefaultStack(),
                        ToolItemsME.BURZUM_STEEL_SHOVEL.getDefaultStack(),
                        ToolItemsME.BURZUM_STEEL_HOE.getDefaultStack(),
                        Optional.empty(), Disposition.EVIL);

                createToolSet(itemLookup, exporter, MetalTypes.MITHRIL, ToolItemsME.MITHRIL_PICKAXE.getDefaultStack(),
                        ToolItemsME.MITHRIL_AXE.getDefaultStack(),
                        ToolItemsME.MITHRIL_SHOVEL.getDefaultStack(),
                        ToolItemsME.MITHRIL_HOE.getDefaultStack(),
                        Optional.of(MetalTypes.STEEL), Disposition.NEUTRAL);

                createToolSet(itemLookup, exporter, MetalTypes.MITHRIL, ToolItemsME.MITHRIL_PICKAXE.getDefaultStack(),
                        ToolItemsME.MITHRIL_AXE.getDefaultStack(),
                        ToolItemsME.MITHRIL_SHOVEL.getDefaultStack(),
                        ToolItemsME.MITHRIL_HOE.getDefaultStack(),
                        Optional.of(MetalTypes.KHAZAD_STEEL), Disposition.NEUTRAL);

                createToolSet(itemLookup, exporter, MetalTypes.MITHRIL, ToolItemsME.MITHRIL_PICKAXE.getDefaultStack(),
                        ToolItemsME.MITHRIL_AXE.getDefaultStack(),
                        ToolItemsME.MITHRIL_SHOVEL.getDefaultStack(),
                        ToolItemsME.MITHRIL_HOE.getDefaultStack(),
                        Optional.of(MetalTypes.EDHEL_STEEL), Disposition.NEUTRAL);

                createToolSet(itemLookup, exporter, MetalTypes.MITHRIL, ToolItemsME.MITHRIL_PICKAXE.getDefaultStack(),
                        ToolItemsME.MITHRIL_AXE.getDefaultStack(),
                        ToolItemsME.MITHRIL_SHOVEL.getDefaultStack(),
                        ToolItemsME.MITHRIL_HOE.getDefaultStack(),
                        Optional.of(MetalTypes.BURZUM_STEEL), Disposition.NEUTRAL);

                createToolSet(itemLookup, exporter, MetalTypes.NETHERITE, Items.NETHERITE_PICKAXE.getDefaultStack(),
                        Items.NETHERITE_AXE.getDefaultStack(),
                        Items.NETHERITE_SHOVEL.getDefaultStack(),
                        Items.NETHERITE_HOE.getDefaultStack(),
                        Optional.empty(), Disposition.NEUTRAL);
                //endregion

                //region SHIELDS
                ItemStack ironShieldBorder = new ItemStack(ResourceItemsME.SHIELD_BORDER);
                ironShieldBorder.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                        Identifier.of(MetalTypes.IRON.getName()))), getPattern()));

                ItemStack bronzeShieldBorder = new ItemStack(ResourceItemsME.SHIELD_BORDER);
                bronzeShieldBorder.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                        Identifier.of(MiddleEarth.MOD_ID, MetalTypes.BRONZE.getName()))), getPattern()));

                ItemStack crudeShieldBorder = new ItemStack(ResourceItemsME.SHIELD_BORDER);
                crudeShieldBorder.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                        Identifier.of(MiddleEarth.MOD_ID, MetalTypes.CRUDE.getName()))), getPattern()));

                ItemStack steelShieldPlate = new ItemStack(ResourceItemsME.SHIELD_PLATE);
                steelShieldPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                        Identifier.of(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName()))), getPattern()));

                ItemStack edhelSteelShieldPlate = new ItemStack(ResourceItemsME.SHIELD_PLATE);
                edhelSteelShieldPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                        Identifier.of(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName()))), getPattern()));

                ItemStack khazadSteelShieldPlate = new ItemStack(ResourceItemsME.SHIELD_PLATE);
                khazadSteelShieldPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                        Identifier.of(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName()))), getPattern()));

                ItemStack burzumSteelShieldPlate = new ItemStack(ResourceItemsME.SHIELD_PLATE);
                burzumSteelShieldPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                        Identifier.of(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName()))), getPattern()));

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, Items.SHIELD.getDefaultStack(), "medium_shield", Disposition.NEUTRAL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(Items.SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROUND_SHIELD.getDefaultStack(), "medium_shield", Disposition.NEUTRAL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.HEATER_SHIELD.getDefaultStack(), "medium_shield", Disposition.NEUTRAL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.HEATER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.KITE_SHIELD.getDefaultStack(), "medium_shield", Disposition.NEUTRAL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.KITE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.BLACK_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GONDORIAN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_TOWER_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.BLACK_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GONDORIAN_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_KINGS_GUARD_TOWER_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.BLACK_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GONDORIAN_KINGS_GUARD_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.LAST_ALLIANCE_HEIRLOOM_TOWER_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.BLACK_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.LAST_ALLIANCE_HEIRLOOM_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_HERO_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.LEATHER)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.LEATHER)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GONDORIAN_HERO_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_KNIGHT_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GONDORIAN_KNIGHT_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_ORNAMENTED_KNIGHT_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.GREEN_DYE)
                        .input(Items.GOLD_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GONDORIAN_ORNAMENTED_KNIGHT_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.WHITE_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.GREEN_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROHIRRIC_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_BUCKING_HORSE_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.GREEN_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROHIRRIC_BUCKING_HORSE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_GALLOPING_HORSE_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.GREEN_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROHIRRIC_GALLOPING_HORSE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_HORSE_HEAD_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.GREEN_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROHIRRIC_HORSE_HEAD_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_PLAINSMAN_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.YELLOW_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.GREEN_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROHIRRIC_PLAINSMAN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_TWIN_HORSES_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.GREEN_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROHIRRIC_TWIN_HORSES_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_EORLING_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROHIRRIC_EORLING_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_ORNAMENTED_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROHIRRIC_ORNAMENTED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_ROYAL_GUARD_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROHIRRIC_ROYAL_GUARD_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_BLUE_OVAL_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.BLUE_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.BLUE_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.BLUE_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DALISH_BLUE_OVAL_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_BARDING_OVAL_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.BLUE_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GOLD_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DALISH_BARDING_OVAL_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_BLUE_BRACED_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.BLUE_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.BLUE_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DALISH_BLUE_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_BARDING_BRACED_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GOLD_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DALISH_BARDING_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.WHITE_DYE)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DALISH_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_BARDING_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.BLUE_DYE)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DALISH_BARDING_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_ROYAL_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LIGHT_BLUE_DYE)
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DALISH_ROYAL_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_ROYAL_ROUND_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LIGHT_BLUE_DYE)
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DALISH_ROYAL_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_HEYDAY_ROUND_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.ORANGE_DYE)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DALISH_HEYDAY_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.LORIEN_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.LEATHER)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.LEATHER)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.LORIEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.LORIEN_LAURELS_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.YELLOW_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.LEATHER)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.LORIEN_LAURELS_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.LORIEN_MALLORN_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.YELLOW_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.LEATHER)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.LORIEN_MALLORN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GALADHRIM_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelShieldPlate.getItem()), edhelSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GALADHRIM_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GALADHRIM_LORD_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelShieldPlate.getItem()), edhelSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GALADHRIM_LORD_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeShieldBorder.getItem()), bronzeShieldBorder.getComponentChanges()))
                        .input(Items.LEATHER)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.EREBOR_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_CROSS_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeShieldBorder.getItem()), bronzeShieldBorder.getComponentChanges()))
                        .input(Items.LEATHER)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.EREBOR_CROSS_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_PLATED_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeShieldBorder.getItem()), bronzeShieldBorder.getComponentChanges()))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.EREBOR_PLATED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_ORNAMENTED_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeShieldBorder.getItem()), bronzeShieldBorder.getComponentChanges()))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.EREBOR_ORNAMENTED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_REINFORCED_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeShieldBorder.getItem()), bronzeShieldBorder.getComponentChanges()))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.EREBOR_REINFORCED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_BUCKLER_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.EREBOR_BUCKLER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_TOWER_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.EREBOR_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_REINFORCED_TOWER_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.EREBOR_REINFORCED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_ORNAMENTED_TOWER_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.EREBOR_ORNAMENTED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RAVENHILL_TOWER_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.RAVENHILL_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RAVENHILL_REINFORCED_TOWER_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.RAVENHILL_REINFORCED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RAVENHILL_ORNAMENTED_TOWER_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.RAVENHILL_ORNAMENTED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_WOODEN_SHIELD.getDefaultStack(), "light_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_WOODEN_SHIELD.getDefaultStack(), "light_shield", Disposition.EVIL)
                        .input(WeaponItemsME.MORDOR_WOODEN_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .criterion(hasItem(WeaponItemsME.MORDOR_WOODEN_SHIELD),
                                conditionsFromItem(WeaponItemsME.MORDOR_WOODEN_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_ROUND_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentChanges()))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentChanges()))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.MORDOR_BANNER_PATTERN),
                                conditionsFromItem(ResourceItemsME.MORDOR_BANNER_PATTERN))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_BLACK_ROUND_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD)
                        .input(Items.BLACK_DYE)
                        .criterion(hasItem(WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD),
                                conditionsFromItem(WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_BLACK_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_BRACED_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentChanges()))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(WeaponItemsME.MORDOR_BRACED_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .criterion(hasItem(WeaponItemsME.MORDOR_BRACED_SHIELD),
                                conditionsFromItem(WeaponItemsME.MORDOR_BRACED_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_BLACK_BRACED_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD)
                        .input(Items.BLACK_DYE)
                        .criterion(hasItem(WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD),
                                conditionsFromItem(WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_BLACK_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_LARGE_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentChanges()))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_LARGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(WeaponItemsME.MORDOR_LARGE_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .criterion(hasItem(WeaponItemsME.MORDOR_LARGE_SHIELD),
                                conditionsFromItem(WeaponItemsME.MORDOR_LARGE_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_BLACK_LARGE_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD)
                        .input(Items.BLACK_DYE)
                        .criterion(hasItem(WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD),
                                conditionsFromItem(WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_BLACK_LARGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_CONVERTED_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(WeaponItemsME.GONDORIAN_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .criterion(hasItem(WeaponItemsME.GONDORIAN_SHIELD),
                                conditionsFromItem(WeaponItemsME.GONDORIAN_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GONDORIAN_CONVERTED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", Disposition.EVIL)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", Disposition.EVIL)
                        .input(WeaponItemsME.MORDOR_HEAVY_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .criterion(hasItem(WeaponItemsME.MORDOR_HEAVY_SHIELD),
                                conditionsFromItem(WeaponItemsME.MORDOR_HEAVY_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_PAINTED_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.BLACK_NUMENOREAN_TOWER_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.RED_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.BLACK_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.BLACK_NUMENOREAN_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_HEATER_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentChanges()))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.URUK_HAI_HEATER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_SHIELD.getDefaultStack(), "heavy_shield", Disposition.EVIL)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.URUK_HAI_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_WHITE_HAND_SHIELD.getDefaultStack(), "heavy_shield", Disposition.EVIL)
                        .input(WeaponItemsME.URUK_HAI_SHIELD)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .criterion(hasItem(WeaponItemsME.URUK_HAI_SHIELD),
                                conditionsFromItem(WeaponItemsME.URUK_HAI_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.URUK_HAI_WHITE_HAND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_WHITE_PALMPRINT_SHIELD.getDefaultStack(), "heavy_shield", Disposition.EVIL)
                        .input(WeaponItemsME.URUK_HAI_SHIELD)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .criterion(hasItem(WeaponItemsME.URUK_HAI_SHIELD),
                                conditionsFromItem(WeaponItemsME.URUK_HAI_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.URUK_HAI_WHITE_PALMPRINT_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_S_RUNE_SHIELD.getDefaultStack(), "heavy_shield", Disposition.EVIL)
                        .input(WeaponItemsME.URUK_HAI_SHIELD)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .criterion(hasItem(WeaponItemsME.URUK_HAI_SHIELD),
                                conditionsFromItem(WeaponItemsME.URUK_HAI_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.URUK_HAI_S_RUNE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_SIEGE_SHIELD.getDefaultStack(), "heavy_shield", Disposition.EVIL)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentChanges()))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentChanges()))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.URUK_HAI_SIEGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_WOODEN_SHIELD.getDefaultStack(), "light_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GUNDABAD_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_PAINTED_WOODEN_SHIELD.getDefaultStack(), "light_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GUNDABAD_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_GREAT_EYE_PAINTED_WOODEN_SHIELD.getDefaultStack(), "light_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GUNDABAD_GREAT_EYE_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_PEAKS_PAINTED_WOODEN_SHIELD.getDefaultStack(), "light_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GUNDABAD_PEAKS_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_REINFORCED_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentChanges()))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GUNDABAD_REINFORCED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", Disposition.EVIL)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentChanges()))
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GUNDABAD_HEAVY_SHIELD).getPath() + "_artisan");

                //endregion

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ToolItemsME.PIPE.getDefaultStack(), "pipe", Disposition.NEUTRAL)
                        .input(Items.STICK)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter, Registries.ITEM.getId(ToolItemsME.PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ToolItemsME.CLAY_PIPE.getDefaultStack(), "pipe", Disposition.NEUTRAL)
                        .input(Items.STICK)
                        .input(Items.TERRACOTTA)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(Items.TERRACOTTA)
                        .input(Items.TERRACOTTA)
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter, Registries.ITEM.getId(ToolItemsME.CLAY_PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ToolItemsME.RIVERBEND_PIPE.getDefaultStack(), "pipe", Disposition.NEUTRAL)
                        .input(Items.STICK)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter, Registries.ITEM.getId(ToolItemsME.RIVERBEND_PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ToolItemsME.BRIMMINGBEND_PIPE.getDefaultStack(), "pipe", Disposition.NEUTRAL)
                        .input(Items.STICK)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter, Registries.ITEM.getId(ToolItemsME.BRIMMINGBEND_PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ToolItemsME.LONGBOTTOM_PIPE.getDefaultStack(), "pipe", Disposition.NEUTRAL)
                        .input(Items.STICK)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter, Registries.ITEM.getId(ToolItemsME.LONGBOTTOM_PIPE).getPath() + "_artisan");

            }
        };
    }

    private void createToolSet(RegistryEntryLookup<Item> itemLookup, RecipeExporter exporter, MetalTypes metal, ItemStack outputPickaxe, ItemStack outputAxe, ItemStack outputShovel, ItemStack outputHoe, Optional<MetalTypes> rodMetal, Disposition disposition) {
        createArtisanTablePickaxeRecipe(itemLookup, exporter, metal, outputPickaxe, rodMetal, disposition);
        createArtisanTableAxeRecipe(itemLookup, exporter, metal, outputAxe, false, rodMetal, disposition);
        createArtisanTableShovelRecipe(itemLookup, exporter, metal, outputShovel, rodMetal, disposition);
        createArtisanTableHoeRecipe(itemLookup, exporter, metal, outputHoe, rodMetal, disposition);
    }

    private void createArtisanTableSwordRecipe(RegistryEntryLookup<Item> itemLookup, RecipeExporter exporter, MetalTypes metal, ItemStack output, boolean noble, Disposition disposition) {
        ItemStack blade = new ItemStack(ResourceItemsME.BLADE);
        blade.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        ItemStack swordHilt = new ItemStack(ResourceItemsME.SWORD_HILT);

        if (!noble) {
            swordHilt.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    getMetalIdentifier(metal))), getPattern()));
        } else {
            swordHilt.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    Identifier.of(MetalTypes.GOLD.getName()))), getPattern()));
        }

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "sword", disposition)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(blade.getItem()), blade.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(swordHilt.getItem()), swordHilt.getComponentChanges()))
                .input(Items.STICK)
                .criterion(hasItem(blade.getItem()),
                        conditionsFromItem(blade.getItem(), itemLookup))
                .offerTo(exporter);
    }

    private void createArtisanTableLongswordRecipe(RegistryEntryLookup<Item> itemLookup, RecipeExporter exporter, MetalTypes metal, ItemStack output, boolean noble, Disposition disposition) {
        ItemStack longBlade = new ItemStack(ResourceItemsME.LONG_BLADE);
        longBlade.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        ItemStack swordHilt = new ItemStack(ResourceItemsME.SWORD_HILT);

        if (!noble) {
            swordHilt.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    getMetalIdentifier(metal))), getPattern()));
        } else {
            swordHilt.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    Identifier.of(MetalTypes.GOLD.getName()))), getPattern()));
        }

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "sword", disposition)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(longBlade.getItem()), longBlade.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(swordHilt.getItem()), swordHilt.getComponentChanges()))
                .input(Items.STICK)
                .criterion(hasItem(longBlade.getItem()),
                        conditionsFromItem(longBlade.getItem(), itemLookup))
                .offerTo(exporter);
    }

    private void createArtisanTableDaggerRecipe(RegistryEntryLookup<Item> itemLookup, RecipeExporter exporter, MetalTypes metal, ItemStack output, boolean noble, Disposition disposition) {
        ItemStack shortBlade = new ItemStack(ResourceItemsME.SHORT_BLADE);
        shortBlade.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        ItemStack swordHilt = new ItemStack(ResourceItemsME.SWORD_HILT);

        if (!noble) {
            swordHilt.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    getMetalIdentifier(metal))), getPattern()));
        } else {
            swordHilt.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    Identifier.of(MetalTypes.GOLD.getName()))), getPattern()));
        }

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "sword", disposition)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(shortBlade.getItem()), shortBlade.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(swordHilt.getItem()), swordHilt.getComponentChanges()))
                .input(Items.STICK)
                .criterion(hasItem(shortBlade.getItem()),
                        conditionsFromItem(shortBlade.getItem(), itemLookup))
                .offerTo(exporter);
    }

    private void createArtisanTableSpearRecipe(RegistryEntryLookup<Item> itemLookup, RecipeExporter exporter, MetalTypes metal, ItemStack output, boolean noble, Disposition disposition) {
        ItemStack blade = new ItemStack(ResourceItemsME.SHORT_BLADE);
        blade.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        if (!noble) {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "spear", disposition)
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(blade.getItem()), blade.getComponentChanges()))
                    .input(Items.STICK)
                    .input(Items.STICK)
                    .criterion(hasItem(blade.getItem()),
                            conditionsFromItem(blade.getItem(), itemLookup))
                    .offerTo(exporter);
        } else {
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            rod.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    Identifier.of(MetalTypes.GOLD.getName()))), getPattern()));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "spear", disposition)
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(blade.getItem()), blade.getComponentChanges()))
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                    .input(Items.STICK)
                    .criterion(hasItem(blade.getItem()),
                            conditionsFromItem(blade.getItem(), itemLookup))
                    .offerTo(exporter);
        }
    }

    private void createArtisanTableBowRecipe(RegistryEntryLookup<Item> itemLookup, RecipeExporter exporter, ItemStack output, Disposition disposition) {
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "bow", disposition)
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Items.STICK)
                .input(Items.STRING)
                .criterion(hasItem(Items.STRING),
                        conditionsFromItem(Items.STRING, itemLookup))
                .offerTo(exporter);
    }

    private void createArtisanTableNobleBowRecipe(RegistryEntryLookup<Item> itemLookup, RecipeExporter exporter, ItemStack output, Disposition disposition) {
        ItemStack rod = new ItemStack(ResourceItemsME.ROD);
        rod.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MetalTypes.GOLD.getName()))), getPattern()));
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "bow", disposition)
                .input(Items.STICK)
                .input(Items.STRING)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                .input(Items.STRING)
                .input(Items.STICK)
                .input(Items.STRING)
                .criterion(hasItem(Items.STRING),
                        conditionsFromItem(Items.STRING, itemLookup))
                .offerTo(exporter);
    }

    private void createArtisanTableLongbowRecipe(RegistryEntryLookup<Item> itemLookup, RecipeExporter exporter, ItemStack output, Disposition disposition) {
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "bow", disposition)
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_fences")))
                .input(Items.STRING)
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_fences")))
                .input(Items.STRING)
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_fences")))
                .input(Items.STRING)
                .criterion(hasItem(Items.STRING),
                        conditionsFromItem(Items.STRING, itemLookup))
                .offerTo(exporter);
    }

    private void createArtisanTableNobleLongbowRecipe(RegistryEntryLookup<Item> itemLookup, RecipeExporter exporter, ItemStack output, Disposition disposition) {
        ItemStack rod = new ItemStack(ResourceItemsME.ROD);
        rod.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MetalTypes.GOLD.getName()))), getPattern()));
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "bow", disposition)
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_fences")))
                .input(Items.STRING)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                .input(Items.STRING)
                .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_fences")))
                .input(Items.STRING)
                .criterion(hasItem(Items.STRING),
                        conditionsFromItem(Items.STRING, itemLookup))
                .offerTo(exporter);
    }

    private void createArtisanTableCrossbowRecipe(RegistryEntryLookup<Item> itemLookup, RecipeExporter exporter, ItemStack output, Disposition disposition) {
        ItemStack rod = new ItemStack(ResourceItemsME.ROD);
        rod.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MetalTypes.IRON.getName()))), getPattern()));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "crossbow", disposition)
                .input(Items.STICK)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Blocks.TRIPWIRE_HOOK)
                .input(Items.STRING)
                .input(Items.STICK)
                .criterion(hasItem(Items.STRING),
                        conditionsFromItem(Items.STRING, itemLookup))
                .offerTo(exporter);
    }

    private void createArtisanTableNobleCrossbowRecipe(RegistryEntryLookup<Item> itemLookup, RecipeExporter exporter, ItemStack output, Disposition disposition) {
        ItemStack rod = new ItemStack(ResourceItemsME.ROD);
        rod.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MetalTypes.GOLD.getName()))), getPattern()));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "crossbow", disposition)
                .input(Items.STICK)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Blocks.TRIPWIRE_HOOK)
                .input(Items.STRING)
                .input(Items.STICK)
                .criterion(hasItem(Items.STRING),
                        conditionsFromItem(Items.STRING, itemLookup))
                .offerTo(exporter);
    }

    private void createArtisanTablePickaxeRecipe(RegistryEntryLookup<Item> itemLookup, RecipeExporter exporter, MetalTypes metal, ItemStack output, Optional<MetalTypes> rodMetal, Disposition disposition) {
        ItemStack pickaxeHead = new ItemStack(ResourceItemsME.PICKAXE_HEAD);
        pickaxeHead.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        if (rodMetal.isPresent()){
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            rod.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    getMetalIdentifier(rodMetal.get()))), getPattern()));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "pickaxe", disposition)
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(pickaxeHead.getItem()), pickaxeHead.getComponentChanges()))
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                    .criterion(hasItem(pickaxeHead.getItem()),
                            conditionsFromItem(pickaxeHead.getItem(), itemLookup))
                    .offerTo(exporter, Registries.ITEM.getId(output.getItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan");
        } else {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "pickaxe", disposition)
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(pickaxeHead.getItem()), pickaxeHead.getComponentChanges()))
                    .input(Items.STICK)
                    .input(Items.STICK)
                    .criterion(hasItem(pickaxeHead.getItem()),
                            conditionsFromItem(pickaxeHead.getItem(), itemLookup))
                    .offerTo(exporter);
        }
    }

    private void createArtisanTableAxeRecipe(RegistryEntryLookup<Item> itemLookup, RecipeExporter exporter, MetalTypes metal, ItemStack output, boolean noble, Optional<MetalTypes> rodMetal, Disposition disposition) {
        ItemStack axeHead = new ItemStack(ResourceItemsME.AXE_HEAD);
        axeHead.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        if (!noble){
            if (rodMetal.isPresent()){
                ItemStack rod = new ItemStack(ResourceItemsME.ROD);
                rod.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                        getMetalIdentifier(rodMetal.get()))), getPattern()));
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "axe", disposition)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(axeHead.getItem()), axeHead.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                        .criterion(hasItem(axeHead.getItem()),
                                conditionsFromItem(axeHead.getItem(), itemLookup))
                        .offerTo(exporter, Registries.ITEM.getId(output.getItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan");
            } else {
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "axe", disposition)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(axeHead.getItem()), axeHead.getComponentChanges()))
                        .input(Items.STICK)
                        .input(Items.STICK)
                        .criterion(hasItem(axeHead.getItem()),
                                conditionsFromItem(axeHead.getItem(), itemLookup))
                        .offerTo(exporter);
            }
        } else {
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            rod.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    Identifier.of(MetalTypes.GOLD.getName()))), getPattern()));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "axe", disposition)
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(axeHead.getItem()), axeHead.getComponentChanges()))
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                    .input(Items.STICK)
                    .criterion(hasItem(axeHead.getItem()),
                            conditionsFromItem(axeHead.getItem(), itemLookup))
                    .offerTo(exporter);
        }
    }

    private void createArtisanTableShovelRecipe(RegistryEntryLookup<Item> itemLookup, RecipeExporter exporter, MetalTypes metal, ItemStack output, Optional<MetalTypes> rodMetal, Disposition disposition) {
        ItemStack shovelHead = new ItemStack(ResourceItemsME.SHOVEL_HEAD);
        shovelHead.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        if (rodMetal.isPresent()){
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            rod.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    getMetalIdentifier(rodMetal.get()))), getPattern()));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "shovel", disposition)
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(shovelHead.getItem()), shovelHead.getComponentChanges()))
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                    .criterion(hasItem(shovelHead.getItem()),
                            conditionsFromItem(shovelHead.getItem(), itemLookup))
                    .offerTo(exporter, Registries.ITEM.getId(output.getItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan");
        } else {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "shovel", disposition)
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(shovelHead.getItem()), shovelHead.getComponentChanges()))
                    .input(Items.STICK)
                    .input(Items.STICK)
                    .criterion(hasItem(shovelHead.getItem()),
                            conditionsFromItem(shovelHead.getItem(), itemLookup))
                    .offerTo(exporter);
        }
    }

    private void createArtisanTableHoeRecipe(RegistryEntryLookup<Item> itemLookup, RecipeExporter exporter, MetalTypes metal, ItemStack output, Optional<MetalTypes> rodMetal, Disposition disposition) {
        ItemStack hoeHead = new ItemStack(ResourceItemsME.HOE_HEAD);
        hoeHead.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        if (rodMetal.isPresent()){
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            rod.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    getMetalIdentifier(rodMetal.get()))), getPattern()));
            
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "hoe", disposition)
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(hoeHead.getItem()), hoeHead.getComponentChanges()))
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                    .criterion(hasItem(hoeHead.getItem()),
                            conditionsFromItem(hoeHead.getItem(), itemLookup))
                    .offerTo(exporter, Registries.ITEM.getId(output.getItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan");
        } else {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "hoe", disposition)
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(hoeHead.getItem()), hoeHead.getComponentChanges()))
                    .input(Items.STICK)
                    .input(Items.STICK)
                    .criterion(hasItem(hoeHead.getItem()),
                            conditionsFromItem(hoeHead.getItem(), itemLookup))
                    .offerTo(exporter);
        }
    }

    public AdvancementCriterion<InventoryChangedCriterion.Conditions> conditionsFromItem(ItemConvertible item, RegistryEntryLookup<Item> itemLookup) {
        return conditionsFromPredicates(ItemPredicate.Builder.create().items(itemLookup, new ItemConvertible[]{item}));
    }
}