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
import net.sevenstars.middleearth.item.ModResourceItems;
import net.sevenstars.middleearth.item.ModToolItems;
import net.sevenstars.middleearth.item.ModWeaponItems;
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
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.IRON, ModWeaponItems.IRON_DAGGER.getDefaultStack(), false, Disposition.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.IRON, ModWeaponItems.IRON_SPEAR.getDefaultStack(), false, Disposition.NEUTRAL);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.GOLD, Items.GOLDEN_SWORD.getDefaultStack(), false, Disposition.NEUTRAL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.GOLD, ModWeaponItems.GOLDEN_DAGGER.getDefaultStack(), false, Disposition.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.GOLD, ModWeaponItems.GOLDEN_SPEAR.getDefaultStack(), false, Disposition.NEUTRAL);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.NETHERITE, Items.NETHERITE_SWORD.getDefaultStack(), false, Disposition.NEUTRAL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.NETHERITE, ModWeaponItems.NETHERITE_DAGGER.getDefaultStack(), false, Disposition.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.NETHERITE, ModWeaponItems.NETHERITE_SPEAR.getDefaultStack(), false, Disposition.NEUTRAL);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BRONZE, ModWeaponItems.BRONZE_SWORD.getDefaultStack(), false, Disposition.NEUTRAL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BRONZE, ModWeaponItems.BRONZE_DAGGER.getDefaultStack(), false, Disposition.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BRONZE, ModWeaponItems.BRONZE_SPEAR.getDefaultStack(), false, Disposition.NEUTRAL);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.CRUDE, ModWeaponItems.CRUDE_FALCHION.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.CRUDE, ModWeaponItems.CRUDE_DAGGER.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.CRUDE, ModWeaponItems.CRUDE_SPEAR.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.CRUDE, ModWeaponItems.CRUDE_LONGBLADE.getDefaultStack(), false, Disposition.EVIL);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_SWORD.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_NOBLE_SWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_SWORD.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_NOBLE_SWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.DALISH_SWORD.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.DALISH_NOBLE_SWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.DALISH_HEYDAY_SWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.STEEL_SWORD.getDefaultStack(), false, Disposition.NEUTRAL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.BLACK_NUMENOREAN_SWORD.getDefaultStack(), true, Disposition.EVIL);

                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_DAGGER.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_NOBLE_DAGGER.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_DAGGER.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_NOBLE_DAGGER.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.DALISH_DAGGER.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.DALISH_HEYDAY_DAGGER.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.DALISH_NOBLE_DAGGER.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.BLACK_NUMENOREAN_DAGGER.getDefaultStack(), true, Disposition.EVIL);

                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_LONGSWORD.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_NOBLE_LONGSWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_LONGSWORD.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_NOBLE_LONGSWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.DALISH_LONGSWORD.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.DALISH_HEYDAY_LONGSWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.DALISH_HEYDAY_SCIMITAR.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.DALISH_NOBLE_LONGSWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.BLACK_NUMENOREAN_LONGSWORD.getDefaultStack(), true, Disposition.EVIL);

                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_AXE.getDefaultStack(), false, Optional.empty(), Disposition.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), Disposition.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_AXE.getDefaultStack(), false, Optional.empty(), Disposition.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), Disposition.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.DALISH_AXE.getDefaultStack(), false, Optional.empty(), Disposition.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.DALISH_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), Disposition.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.DALISH_HEYDAY_AXE.getDefaultStack(), true, Optional.empty(), Disposition.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.BLACK_NUMENOREAN_AXE.getDefaultStack(), true, Optional.empty(), Disposition.EVIL);

                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_SPEAR.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_NOBLE_SPEAR.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_FOUNTAIN_GUARD_SPEAR.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_SPEAR.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_NOBLE_SPEAR.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.DALISH_SPEAR.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.DALISH_HEYDAY_SPEAR.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.DALISH_NOBLE_SPEAR.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.STEEL_SPEAR.getDefaultStack(), false, Disposition.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, ModWeaponItems.BLACK_NUMENOREAN_SPEAR.getDefaultStack(), true, Disposition.EVIL);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_SWORD.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_NOBLE_SWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.KHAZAD_STEEL_SWORD.getDefaultStack(), false, Disposition.GOOD);

                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_DAGGER.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_NOBLE_DAGGER.getDefaultStack(), true, Disposition.GOOD);

                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_LONGSWORD.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_NOBLE_LONGSWORD.getDefaultStack(), true, Disposition.GOOD);

                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_AXE.getDefaultStack(), false, Optional.empty(), Disposition.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), Disposition.GOOD);

                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.KHAZAD_STEEL_SPEAR.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_SPEAR.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_NOBLE_SPEAR.getDefaultStack(), true, Disposition.GOOD);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_SWORD.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_NOBLE_SWORD.getDefaultStack(), true, Disposition.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.EDHEL_STEEL_SWORD.getDefaultStack(), false, Disposition.GOOD);

                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_DAGGER.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_NOBLE_DAGGER.getDefaultStack(), true, Disposition.GOOD);

                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_GLAIVE.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_NOBLE_GLAIVE.getDefaultStack(), true, Disposition.GOOD);

                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_AXE.getDefaultStack(), false, Optional.empty(), Disposition.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), Disposition.GOOD);

                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.EDHEL_STEEL_SPEAR.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_SPEAR.getDefaultStack(), false, Disposition.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_NOBLE_SPEAR.getDefaultStack(), true, Disposition.GOOD);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.BURZUM_STEEL_SWORD.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ORC_SWORD.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_FALCHION.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_ELITE_CLEAVER.getDefaultStack(), true, Disposition.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ISENGARD_ORC_CLEAVER.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.URUK_HAI_FALCHION.getDefaultStack(), true, Disposition.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.GUNDABAD_FALCHION.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.GUNDABAD_ELITE_CLEAVER.getDefaultStack(), true, Disposition.EVIL);

                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ORC_KNIFE.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_DAGGER.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_ELITE_DAGGER.getDefaultStack(), true, Disposition.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ISENGARD_ORC_DAGGER.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.URUK_HAI_KNIFE.getDefaultStack(), true, Disposition.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.GUNDABAD_SHANK.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.GUNDABAD_ELITE_DAGGER.getDefaultStack(), true, Disposition.EVIL);

                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ORC_BROADBLADE.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_SCIMITAR.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_ELITE_WARBLADE.getDefaultStack(), true, Disposition.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ISENGARD_ORC_WARBLADE.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.URUK_HAI_WARBLADE.getDefaultStack(), true, Disposition.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.GUNDABAD_WARBLADE.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.GUNDABAD_ELITE_SCIMITAR.getDefaultStack(), true, Disposition.EVIL);

                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ORC_AXE.getDefaultStack(), false, Optional.empty(), Disposition.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_AXE.getDefaultStack(), false, Optional.empty(), Disposition.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_ELITE_AXE.getDefaultStack(), true, Optional.empty(), Disposition.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ISENGARD_ORC_AXE.getDefaultStack(), false, Optional.empty(), Disposition.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.URUK_HAI_AXE.getDefaultStack(), true, Optional.empty(), Disposition.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.GUNDABAD_AXE.getDefaultStack(), false, Optional.empty(), Disposition.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.GUNDABAD_ELITE_BATTLEAXE.getDefaultStack(), true, Optional.empty(), Disposition.EVIL);

                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.BURZUM_STEEL_SPEAR.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ORC_SPEAR.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_SPEAR.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_ELITE_SPEAR.getDefaultStack(), true, Disposition.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ISENGARD_ORC_SPEAR.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.URUK_HAI_SPEAR.getDefaultStack(), true, Disposition.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.GUNDABAD_SPEAR.getDefaultStack(), false, Disposition.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.GUNDABAD_ELITE_SPEAR.getDefaultStack(), true, Disposition.EVIL);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, ModWeaponItems.GONDORIAN_BOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableLongbowRecipe(itemLookup, recipeExporter, ModWeaponItems.GONDORIAN_LONGBOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableNobleLongbowRecipe(itemLookup, recipeExporter, ModWeaponItems.GONDORIAN_NOBLE_LONGBOW.getDefaultStack(), Disposition.GOOD);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, ModWeaponItems.ROHIRRIC_BOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableNobleBowRecipe(itemLookup, recipeExporter, ModWeaponItems.ROHIRRIC_NOBLE_BOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableLongbowRecipe(itemLookup, recipeExporter, ModWeaponItems.ROHIRRIC_LONGBOW.getDefaultStack(), Disposition.GOOD);

                createArtisanTableLongbowRecipe(itemLookup, recipeExporter, ModWeaponItems.DALISH_LONGBOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableNobleLongbowRecipe(itemLookup, recipeExporter, ModWeaponItems.DALISH_HEYDAY_LONGBOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableNobleLongbowRecipe(itemLookup, recipeExporter, ModWeaponItems.DALISH_NOBLE_LONGBOW.getDefaultStack(), Disposition.GOOD);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, ModWeaponItems.LORIEN_BOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableLongbowRecipe(itemLookup, recipeExporter, ModWeaponItems.LORIEN_LONGBOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableNobleLongbowRecipe(itemLookup, recipeExporter, ModWeaponItems.LORIEN_NOBLE_LONGBOW.getDefaultStack(), Disposition.GOOD);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, ModWeaponItems.EREBOR_BOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableNobleBowRecipe(itemLookup, recipeExporter, ModWeaponItems.EREBOR_NOBLE_BOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableCrossbowRecipe(itemLookup, recipeExporter, ModWeaponItems.EREBOR_CROSSBOW.getDefaultStack(), Disposition.GOOD);
                createArtisanTableNobleCrossbowRecipe(itemLookup, recipeExporter, ModWeaponItems.EREBOR_NOBLE_CROSSBOW.getDefaultStack(), Disposition.GOOD);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, ModWeaponItems.ORCISH_BOW.getDefaultStack(), Disposition.EVIL);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, ModWeaponItems.MORDOR_BOW.getDefaultStack(), Disposition.EVIL);
                createArtisanTableNobleLongbowRecipe(itemLookup, recipeExporter, ModWeaponItems.MORDOR_ELITE_LONGBOW.getDefaultStack(), Disposition.EVIL);

                createArtisanTableNobleBowRecipe(itemLookup, recipeExporter, ModWeaponItems.URUK_HAI_BOW.getDefaultStack(), Disposition.EVIL);
                createArtisanTableNobleCrossbowRecipe(itemLookup, recipeExporter, ModWeaponItems.URUK_HAI_CROSSBOW.getDefaultStack(), Disposition.EVIL);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, ModWeaponItems.GUNDABAD_BOW.getDefaultStack(), Disposition.EVIL);
                createArtisanTableCrossbowRecipe(itemLookup, recipeExporter, ModWeaponItems.GUNDABAD_CROSSBOW.getDefaultStack(), Disposition.EVIL);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, Items.BOW.getDefaultStack(), Disposition.NEUTRAL);
                createArtisanTableCrossbowRecipe(itemLookup, recipeExporter, Items.CROSSBOW.getDefaultStack(), Disposition.NEUTRAL);
                //endregion

                //region TOOLS
                createToolSet(itemLookup, exporter, MetalTypes.BRONZE, ModToolItems.BRONZE_PICKAXE.getDefaultStack(),
                        ModToolItems.BRONZE_AXE.getDefaultStack(),
                        ModToolItems.BRONZE_SHOVEL.getDefaultStack(),
                        ModToolItems.BRONZE_HOE.getDefaultStack(),
                        Optional.empty(), Disposition.NEUTRAL);

                createToolSet(itemLookup, exporter, MetalTypes.CRUDE, ModToolItems.CRUDE_PICKAXE.getDefaultStack(),
                        ModToolItems.CRUDE_AXE.getDefaultStack(),
                        ModToolItems.CRUDE_SHOVEL.getDefaultStack(),
                        ModToolItems.CRUDE_HOE.getDefaultStack(),
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

                createToolSet(itemLookup, exporter, MetalTypes.STEEL, ModToolItems.STEEL_PICKAXE.getDefaultStack(),
                        ModToolItems.STEEL_AXE.getDefaultStack(),
                        ModToolItems.STEEL_SHOVEL.getDefaultStack(),
                        ModToolItems.STEEL_HOE.getDefaultStack(),
                        Optional.empty(), Disposition.NEUTRAL);

                createToolSet(itemLookup, exporter, MetalTypes.KHAZAD_STEEL, ModToolItems.KHAZAD_STEEL_PICKAXE.getDefaultStack(),
                        ModToolItems.KHAZAD_STEEL_AXE.getDefaultStack(),
                        ModToolItems.KHAZAD_STEEL_SHOVEL.getDefaultStack(),
                        ModToolItems.KHAZAD_STEEL_HOE.getDefaultStack(),
                        Optional.empty(), Disposition.GOOD);

                createToolSet(itemLookup, exporter, MetalTypes.EDHEL_STEEL, ModToolItems.EDHEL_STEEL_PICKAXE.getDefaultStack(),
                        ModToolItems.EDHEL_STEEL_AXE.getDefaultStack(),
                        ModToolItems.EDHEL_STEEL_SHOVEL.getDefaultStack(),
                        ModToolItems.EDHEL_STEEL_HOE.getDefaultStack(),
                        Optional.empty(), Disposition.GOOD);

                createToolSet(itemLookup, exporter, MetalTypes.BURZUM_STEEL, ModToolItems.BURZUM_STEEL_PICKAXE.getDefaultStack(),
                        ModToolItems.BURZUM_STEEL_AXE.getDefaultStack(),
                        ModToolItems.BURZUM_STEEL_SHOVEL.getDefaultStack(),
                        ModToolItems.BURZUM_STEEL_HOE.getDefaultStack(),
                        Optional.empty(), Disposition.EVIL);

                createToolSet(itemLookup, exporter, MetalTypes.MITHRIL, ModToolItems.MITHRIL_PICKAXE.getDefaultStack(),
                        ModToolItems.MITHRIL_AXE.getDefaultStack(),
                        ModToolItems.MITHRIL_SHOVEL.getDefaultStack(),
                        ModToolItems.MITHRIL_HOE.getDefaultStack(),
                        Optional.of(MetalTypes.STEEL), Disposition.NEUTRAL);

                createToolSet(itemLookup, exporter, MetalTypes.MITHRIL, ModToolItems.MITHRIL_PICKAXE.getDefaultStack(),
                        ModToolItems.MITHRIL_AXE.getDefaultStack(),
                        ModToolItems.MITHRIL_SHOVEL.getDefaultStack(),
                        ModToolItems.MITHRIL_HOE.getDefaultStack(),
                        Optional.of(MetalTypes.KHAZAD_STEEL), Disposition.NEUTRAL);

                createToolSet(itemLookup, exporter, MetalTypes.MITHRIL, ModToolItems.MITHRIL_PICKAXE.getDefaultStack(),
                        ModToolItems.MITHRIL_AXE.getDefaultStack(),
                        ModToolItems.MITHRIL_SHOVEL.getDefaultStack(),
                        ModToolItems.MITHRIL_HOE.getDefaultStack(),
                        Optional.of(MetalTypes.EDHEL_STEEL), Disposition.NEUTRAL);

                createToolSet(itemLookup, exporter, MetalTypes.MITHRIL, ModToolItems.MITHRIL_PICKAXE.getDefaultStack(),
                        ModToolItems.MITHRIL_AXE.getDefaultStack(),
                        ModToolItems.MITHRIL_SHOVEL.getDefaultStack(),
                        ModToolItems.MITHRIL_HOE.getDefaultStack(),
                        Optional.of(MetalTypes.BURZUM_STEEL), Disposition.NEUTRAL);

                createToolSet(itemLookup, exporter, MetalTypes.NETHERITE, Items.NETHERITE_PICKAXE.getDefaultStack(),
                        Items.NETHERITE_AXE.getDefaultStack(),
                        Items.NETHERITE_SHOVEL.getDefaultStack(),
                        Items.NETHERITE_HOE.getDefaultStack(),
                        Optional.empty(), Disposition.NEUTRAL);
                //endregion

                //region SHIELDS
                ItemStack ironShieldBorder = new ItemStack(ModResourceItems.SHIELD_BORDER);
                ironShieldBorder.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                        Identifier.of(MetalTypes.IRON.getName()))), getPattern()));

                ItemStack bronzeShieldBorder = new ItemStack(ModResourceItems.SHIELD_BORDER);
                bronzeShieldBorder.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                        Identifier.of(MiddleEarth.MOD_ID, MetalTypes.BRONZE.getName()))), getPattern()));

                ItemStack crudeShieldBorder = new ItemStack(ModResourceItems.SHIELD_BORDER);
                crudeShieldBorder.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                        Identifier.of(MiddleEarth.MOD_ID, MetalTypes.CRUDE.getName()))), getPattern()));

                ItemStack steelShieldPlate = new ItemStack(ModResourceItems.SHIELD_PLATE);
                steelShieldPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                        Identifier.of(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName()))), getPattern()));

                ItemStack edhelSteelShieldPlate = new ItemStack(ModResourceItems.SHIELD_PLATE);
                edhelSteelShieldPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                        Identifier.of(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName()))), getPattern()));

                ItemStack khazadSteelShieldPlate = new ItemStack(ModResourceItems.SHIELD_PLATE);
                khazadSteelShieldPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                        Identifier.of(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName()))), getPattern()));

                ItemStack burzumSteelShieldPlate = new ItemStack(ModResourceItems.SHIELD_PLATE);
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
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(Items.SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.ROUND_SHIELD.getDefaultStack(), "medium_shield", Disposition.NEUTRAL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.HEATER_SHIELD.getDefaultStack(), "medium_shield", Disposition.NEUTRAL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.HEATER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.KITE_SHIELD.getDefaultStack(), "medium_shield", Disposition.NEUTRAL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.KITE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.GONDORIAN_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.GONDOR_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.BLACK_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.GONDORIAN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.GONDORIAN_TOWER_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.GONDOR_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.BLACK_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.GONDORIAN_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.GONDORIAN_KINGS_GUARD_TOWER_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.GONDOR_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.BLACK_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.GONDORIAN_KINGS_GUARD_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.LAST_ALLIANCE_HEIRLOOM_TOWER_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.GONDOR_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.BLACK_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.LAST_ALLIANCE_HEIRLOOM_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.GONDORIAN_HERO_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.LEATHER)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.LEATHER)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.GONDORIAN_HERO_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.GONDORIAN_KNIGHT_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(ModResourceItems.GONDOR_BANNER_PATTERN)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.GONDORIAN_KNIGHT_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.GONDORIAN_ORNAMENTED_KNIGHT_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(ModResourceItems.GONDOR_BANNER_PATTERN)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.GREEN_DYE)
                        .input(Items.GOLD_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.GONDORIAN_ORNAMENTED_KNIGHT_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.ROHIRRIC_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.WHITE_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.GREEN_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.ROHIRRIC_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.ROHIRRIC_BUCKING_HORSE_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.ROHAN_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.GREEN_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.ROHIRRIC_BUCKING_HORSE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.ROHIRRIC_GALLOPING_HORSE_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.ROHAN_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.GREEN_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.ROHIRRIC_GALLOPING_HORSE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.ROHIRRIC_HORSE_HEAD_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.ROHAN_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.GREEN_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.ROHIRRIC_HORSE_HEAD_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.ROHIRRIC_PLAINSMAN_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.YELLOW_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.GREEN_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.ROHIRRIC_PLAINSMAN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.ROHIRRIC_TWIN_HORSES_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.ROHAN_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.GREEN_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.ROHIRRIC_TWIN_HORSES_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.ROHIRRIC_EORLING_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(Items.YELLOW_DYE)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.ROHIRRIC_EORLING_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.ROHIRRIC_ORNAMENTED_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(ModResourceItems.ROHAN_BANNER_PATTERN)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.ROHIRRIC_ORNAMENTED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.ROHIRRIC_ROYAL_GUARD_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(Items.YELLOW_DYE)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.ROHIRRIC_ROYAL_GUARD_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.DALISH_BLUE_OVAL_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.BLUE_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.BLUE_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.BLUE_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.DALISH_BLUE_OVAL_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.DALISH_BARDING_OVAL_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.BLUE_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GOLD_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.DALISH_BARDING_OVAL_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.DALISH_BLUE_BRACED_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.BLUE_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.BLUE_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.DALISH_BLUE_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.DALISH_BARDING_BRACED_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GOLD_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.DALISH_BARDING_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.DALISH_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(Items.WHITE_DYE)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.DALISH_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.DALISH_BARDING_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(Items.BLUE_DYE)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.DALISH_BARDING_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.DALISH_ROYAL_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LIGHT_BLUE_DYE)
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.DALISH_ROYAL_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.DALISH_ROYAL_ROUND_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LIGHT_BLUE_DYE)
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.DALISH_ROYAL_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.DALISH_HEYDAY_ROUND_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(Items.ORANGE_DYE)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(steelShieldPlate.getItem()), steelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ModResourceItems.STEEL_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.DALISH_HEYDAY_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.LORIEN_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.LEATHER)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.LEATHER)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.LORIEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.LORIEN_LAURELS_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.YELLOW_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.LEATHER)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.LORIEN_LAURELS_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.LORIEN_MALLORN_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.YELLOW_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.LEATHER)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.LORIEN_MALLORN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.GALADHRIM_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ModResourceItems.EDHEL_STEEL_NUGGET)
                        .input(Items.YELLOW_DYE)
                        .input(ModResourceItems.EDHEL_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelShieldPlate.getItem()), edhelSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.EDHEL_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ModResourceItems.EDHEL_STEEL_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.GALADHRIM_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.GALADHRIM_LORD_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ModResourceItems.EDHEL_STEEL_NUGGET)
                        .input(Items.YELLOW_DYE)
                        .input(ModResourceItems.EDHEL_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(edhelSteelShieldPlate.getItem()), edhelSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.GALADHRIM_LORD_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.EREBOR_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.BRONZE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeShieldBorder.getItem()), bronzeShieldBorder.getComponentChanges()))
                        .input(Items.LEATHER)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.EREBOR_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.EREBOR_CROSS_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.BRONZE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeShieldBorder.getItem()), bronzeShieldBorder.getComponentChanges()))
                        .input(Items.LEATHER)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.EREBOR_CROSS_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.EREBOR_PLATED_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.BRONZE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.BRONZE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeShieldBorder.getItem()), bronzeShieldBorder.getComponentChanges()))
                        .input(ModResourceItems.BRONZE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.EREBOR_PLATED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.EREBOR_ORNAMENTED_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.BRONZE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeShieldBorder.getItem()), bronzeShieldBorder.getComponentChanges()))
                        .input(ModResourceItems.BRONZE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.EREBOR_ORNAMENTED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.EREBOR_REINFORCED_SHIELD.getDefaultStack(), "medium_shield", Disposition.GOOD)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.BRONZE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(bronzeShieldBorder.getItem()), bronzeShieldBorder.getComponentChanges()))
                        .input(ModResourceItems.BRONZE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.EREBOR_REINFORCED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.EREBOR_BUCKLER_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(ModResourceItems.BRONZE_INGOT)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.EREBOR_BUCKLER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.EREBOR_TOWER_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(ModResourceItems.KHAZAD_STEEL_INGOT)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.EREBOR_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.EREBOR_REINFORCED_TOWER_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(ModResourceItems.KHAZAD_STEEL_INGOT)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(ModResourceItems.KHAZAD_STEEL_INGOT)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.EREBOR_REINFORCED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.EREBOR_ORNAMENTED_TOWER_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.EREBOR_ORNAMENTED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.RAVENHILL_TOWER_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(ModResourceItems.KHAZAD_STEEL_INGOT)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.RAVENHILL_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.RAVENHILL_REINFORCED_TOWER_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(ModResourceItems.KHAZAD_STEEL_INGOT)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(ModResourceItems.KHAZAD_STEEL_INGOT)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.RAVENHILL_REINFORCED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.RAVENHILL_ORNAMENTED_TOWER_SHIELD.getDefaultStack(), "heavy_shield", Disposition.GOOD)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ModResourceItems.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.RAVENHILL_ORNAMENTED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.MORDOR_WOODEN_SHIELD.getDefaultStack(), "light_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.CRUDE_INGOT)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.CRUDE_INGOT),
                                conditionsFromItem(ModResourceItems.CRUDE_INGOT))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.MORDOR_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.MORDOR_PAINTED_WOODEN_SHIELD.getDefaultStack(), "light_shield", Disposition.EVIL)
                        .input(ModWeaponItems.MORDOR_WOODEN_SHIELD)
                        .input(ModResourceItems.MORDOR_BANNER_PATTERN)
                        .criterion(hasItem(ModWeaponItems.MORDOR_WOODEN_SHIELD),
                                conditionsFromItem(ModWeaponItems.MORDOR_WOODEN_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.MORDOR_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.MORDOR_ROUND_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.CRUDE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.CRUDE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentChanges()))
                        .input(ModResourceItems.CRUDE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.MORDOR_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.MORDOR_PAINTED_ROUND_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.MORDOR_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.CRUDE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentChanges()))
                        .input(ModResourceItems.CRUDE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.MORDOR_BANNER_PATTERN),
                                conditionsFromItem(ModResourceItems.MORDOR_BANNER_PATTERN))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.MORDOR_PAINTED_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.MORDOR_BLACK_ROUND_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(ModWeaponItems.MORDOR_PAINTED_ROUND_SHIELD)
                        .input(Items.BLACK_DYE)
                        .criterion(hasItem(ModWeaponItems.MORDOR_PAINTED_ROUND_SHIELD),
                                conditionsFromItem(ModWeaponItems.MORDOR_PAINTED_ROUND_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.MORDOR_BLACK_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.MORDOR_BRACED_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.CRUDE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentChanges()))
                        .input(ModResourceItems.CRUDE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.MORDOR_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.MORDOR_PAINTED_BRACED_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(ModWeaponItems.MORDOR_BRACED_SHIELD)
                        .input(ModResourceItems.MORDOR_BANNER_PATTERN)
                        .criterion(hasItem(ModWeaponItems.MORDOR_BRACED_SHIELD),
                                conditionsFromItem(ModWeaponItems.MORDOR_BRACED_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.MORDOR_PAINTED_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.MORDOR_BLACK_BRACED_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(ModWeaponItems.MORDOR_PAINTED_BRACED_SHIELD)
                        .input(Items.BLACK_DYE)
                        .criterion(hasItem(ModWeaponItems.MORDOR_PAINTED_BRACED_SHIELD),
                                conditionsFromItem(ModWeaponItems.MORDOR_PAINTED_BRACED_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.MORDOR_BLACK_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.MORDOR_LARGE_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.CRUDE_INGOT)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentChanges()))
                        .input(ModResourceItems.CRUDE_INGOT)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.MORDOR_LARGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.MORDOR_PAINTED_LARGE_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(ModWeaponItems.MORDOR_LARGE_SHIELD)
                        .input(ModResourceItems.MORDOR_BANNER_PATTERN)
                        .criterion(hasItem(ModWeaponItems.MORDOR_LARGE_SHIELD),
                                conditionsFromItem(ModWeaponItems.MORDOR_LARGE_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.MORDOR_PAINTED_LARGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.MORDOR_BLACK_LARGE_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(ModWeaponItems.MORDOR_PAINTED_LARGE_SHIELD)
                        .input(Items.BLACK_DYE)
                        .criterion(hasItem(ModWeaponItems.MORDOR_PAINTED_LARGE_SHIELD),
                                conditionsFromItem(ModWeaponItems.MORDOR_PAINTED_LARGE_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.MORDOR_BLACK_LARGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.GONDORIAN_CONVERTED_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(ModWeaponItems.GONDORIAN_SHIELD)
                        .input(ModResourceItems.MORDOR_BANNER_PATTERN)
                        .criterion(hasItem(ModWeaponItems.GONDORIAN_SHIELD),
                                conditionsFromItem(ModWeaponItems.GONDORIAN_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.GONDORIAN_CONVERTED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.MORDOR_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", Disposition.EVIL)
                        .input(ModResourceItems.BURZUM_STEEL_NUGGET)
                        .input(ModResourceItems.BURZUM_STEEL_NUGGET)
                        .input(ModResourceItems.BURZUM_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ModResourceItems.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.MORDOR_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.MORDOR_PAINTED_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", Disposition.EVIL)
                        .input(ModWeaponItems.MORDOR_HEAVY_SHIELD)
                        .input(ModResourceItems.MORDOR_BANNER_PATTERN)
                        .criterion(hasItem(ModWeaponItems.MORDOR_HEAVY_SHIELD),
                                conditionsFromItem(ModWeaponItems.MORDOR_HEAVY_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.MORDOR_PAINTED_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.BLACK_NUMENOREAN_TOWER_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.RED_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(ironShieldBorder.getItem()), ironShieldBorder.getComponentChanges()))
                        .input(Items.BLACK_DYE)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.BLACK_NUMENOREAN_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.URUK_HAI_HEATER_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(ModResourceItems.CRUDE_NUGGET)
                        .input(ModResourceItems.CRUDE_INGOT)
                        .input(ModResourceItems.CRUDE_NUGGET)
                        .input(ModResourceItems.CRUDE_INGOT)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentChanges()))
                        .input(ModResourceItems.CRUDE_INGOT)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.URUK_HAI_HEATER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.URUK_HAI_SHIELD.getDefaultStack(), "heavy_shield", Disposition.EVIL)
                        .input(ModResourceItems.BURZUM_STEEL_NUGGET)
                        .input(ModResourceItems.BURZUM_STEEL_NUGGET)
                        .input(ModResourceItems.BURZUM_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ModResourceItems.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.URUK_HAI_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.URUK_HAI_WHITE_HAND_SHIELD.getDefaultStack(), "heavy_shield", Disposition.EVIL)
                        .input(ModWeaponItems.URUK_HAI_SHIELD)
                        .input(ModResourceItems.ISENGARD_BANNER_PATTERN)
                        .criterion(hasItem(ModWeaponItems.URUK_HAI_SHIELD),
                                conditionsFromItem(ModWeaponItems.URUK_HAI_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.URUK_HAI_WHITE_HAND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.URUK_HAI_WHITE_PALMPRINT_SHIELD.getDefaultStack(), "heavy_shield", Disposition.EVIL)
                        .input(ModWeaponItems.URUK_HAI_SHIELD)
                        .input(ModResourceItems.ISENGARD_BANNER_PATTERN)
                        .criterion(hasItem(ModWeaponItems.URUK_HAI_SHIELD),
                                conditionsFromItem(ModWeaponItems.URUK_HAI_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.URUK_HAI_WHITE_PALMPRINT_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.URUK_HAI_S_RUNE_SHIELD.getDefaultStack(), "heavy_shield", Disposition.EVIL)
                        .input(ModWeaponItems.URUK_HAI_SHIELD)
                        .input(ModResourceItems.ISENGARD_BANNER_PATTERN)
                        .criterion(hasItem(ModWeaponItems.URUK_HAI_SHIELD),
                                conditionsFromItem(ModWeaponItems.URUK_HAI_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.URUK_HAI_S_RUNE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.URUK_HAI_SIEGE_SHIELD.getDefaultStack(), "heavy_shield", Disposition.EVIL)
                        .input(ModResourceItems.BURZUM_STEEL_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentChanges()))
                        .input(ModResourceItems.BURZUM_STEEL_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentChanges()))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.BURZUM_STEEL_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentChanges()))
                        .input(ModResourceItems.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.URUK_HAI_SIEGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.GUNDABAD_WOODEN_SHIELD.getDefaultStack(), "light_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.CRUDE_INGOT)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.CRUDE_INGOT),
                                conditionsFromItem(ModResourceItems.CRUDE_INGOT))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.GUNDABAD_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.GUNDABAD_PAINTED_WOODEN_SHIELD.getDefaultStack(), "light_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.CRUDE_INGOT),
                                conditionsFromItem(ModResourceItems.CRUDE_INGOT))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.GUNDABAD_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.GUNDABAD_GREAT_EYE_PAINTED_WOODEN_SHIELD.getDefaultStack(), "light_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.CRUDE_INGOT),
                                conditionsFromItem(ModResourceItems.CRUDE_INGOT))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.GUNDABAD_GREAT_EYE_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.GUNDABAD_PEAKS_PAINTED_WOODEN_SHIELD.getDefaultStack(), "light_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.CRUDE_INGOT),
                                conditionsFromItem(ModResourceItems.CRUDE_INGOT))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.GUNDABAD_PEAKS_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.GUNDABAD_REINFORCED_SHIELD.getDefaultStack(), "medium_shield", Disposition.EVIL)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.CRUDE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.CRUDE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentChanges()))
                        .input(ModResourceItems.CRUDE_INGOT)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(ModResourceItems.SHIELD_BORDER),
                                conditionsFromItem(ModResourceItems.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.GUNDABAD_REINFORCED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModWeaponItems.GUNDABAD_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", Disposition.EVIL)
                        .input(ModResourceItems.BURZUM_STEEL_NUGGET)
                        .input(ModResourceItems.BURZUM_STEEL_NUGGET)
                        .input(ModResourceItems.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentChanges()))
                        .input(Items.LEATHER)
                        .input(ModResourceItems.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ModResourceItems.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(ModResourceItems.SHIELD_PLATE),
                                conditionsFromItem(ModResourceItems.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(ModWeaponItems.GUNDABAD_HEAVY_SHIELD).getPath() + "_artisan");

                //endregion

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModToolItems.PIPE.getDefaultStack(), "pipe", Disposition.NEUTRAL)
                        .input(Items.STICK)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter, Registries.ITEM.getId(ModToolItems.PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModToolItems.CLAY_PIPE.getDefaultStack(), "pipe", Disposition.NEUTRAL)
                        .input(Items.STICK)
                        .input(Items.TERRACOTTA)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(Items.TERRACOTTA)
                        .input(Items.TERRACOTTA)
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter, Registries.ITEM.getId(ModToolItems.CLAY_PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModToolItems.RIVERBEND_PIPE.getDefaultStack(), "pipe", Disposition.NEUTRAL)
                        .input(Items.STICK)
                        .input(ModResourceItems.BRONZE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.BRONZE_NUGGET)
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter, Registries.ITEM.getId(ModToolItems.RIVERBEND_PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModToolItems.BRIMMINGBEND_PIPE.getDefaultStack(), "pipe", Disposition.NEUTRAL)
                        .input(Items.STICK)
                        .input(ModResourceItems.BRONZE_NUGGET)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(ModResourceItems.BRONZE_NUGGET)
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter, Registries.ITEM.getId(ModToolItems.BRIMMINGBEND_PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ModToolItems.LONGBOTTOM_PIPE.getDefaultStack(), "pipe", Disposition.NEUTRAL)
                        .input(Items.STICK)
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("wooden_slabs")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .input(TagKey.of(RegistryKeys.ITEM, Identifier.of("planks")))
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter, Registries.ITEM.getId(ModToolItems.LONGBOTTOM_PIPE).getPath() + "_artisan");

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

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "sword", disposition)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(blade.getItem()), blade.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(swordHilt.getItem()), swordHilt.getComponentChanges()))
                .input(Items.STICK)
                .criterion(hasItem(blade.getItem()),
                        conditionsFromItem(blade.getItem(), itemLookup))
                .offerTo(exporter);
    }

    private void createArtisanTableLongswordRecipe(RegistryEntryLookup<Item> itemLookup, RecipeExporter exporter, MetalTypes metal, ItemStack output, boolean noble, Disposition disposition) {
        ItemStack longBlade = new ItemStack(ModResourceItems.LONG_BLADE);
        longBlade.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        ItemStack swordHilt = new ItemStack(ModResourceItems.SWORD_HILT);

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
        ItemStack shortBlade = new ItemStack(ModResourceItems.SHORT_BLADE);
        shortBlade.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        ItemStack swordHilt = new ItemStack(ModResourceItems.SWORD_HILT);

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
        ItemStack blade = new ItemStack(ModResourceItems.SHORT_BLADE);
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
            ItemStack rod = new ItemStack(ModResourceItems.ROD);
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
        ItemStack rod = new ItemStack(ModResourceItems.ROD);
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
        ItemStack rod = new ItemStack(ModResourceItems.ROD);
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
        ItemStack rod = new ItemStack(ModResourceItems.ROD);
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
        ItemStack rod = new ItemStack(ModResourceItems.ROD);
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
        ItemStack pickaxeHead = new ItemStack(ModResourceItems.PICKAXE_HEAD);
        pickaxeHead.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        if (rodMetal.isPresent()){
            ItemStack rod = new ItemStack(ModResourceItems.ROD);
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
        ItemStack axeHead = new ItemStack(ModResourceItems.AXE_HEAD);
        axeHead.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        if (!noble){
            if (rodMetal.isPresent()){
                ItemStack rod = new ItemStack(ModResourceItems.ROD);
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
            ItemStack rod = new ItemStack(ModResourceItems.ROD);
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
        ItemStack shovelHead = new ItemStack(ModResourceItems.SHOVEL_HEAD);
        shovelHead.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        if (rodMetal.isPresent()){
            ItemStack rod = new ItemStack(ModResourceItems.ROD);
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
        ItemStack hoeHead = new ItemStack(ModResourceItems.HOE_HEAD);
        hoeHead.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        if (rodMetal.isPresent()){
            ItemStack rod = new ItemStack(ModResourceItems.ROD);
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