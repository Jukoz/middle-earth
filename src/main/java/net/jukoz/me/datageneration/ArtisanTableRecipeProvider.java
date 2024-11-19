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

        //region WEAPONS
        createArtisanTableSwordRecipe(exporter, MetalTypes.IRON, Items.IRON_SWORD.getDefaultStack(), false, Disposition.NEUTRAL);

        createArtisanTableSpearRecipe(exporter, MetalTypes.IRON, ModWeaponItems.IRON_SPEAR.getDefaultStack(), false, Disposition.NEUTRAL);

        createArtisanTableSwordRecipe(exporter, MetalTypes.GOLD, Items.GOLDEN_SWORD.getDefaultStack(), false, Disposition.NEUTRAL);

        createArtisanTableSpearRecipe(exporter, MetalTypes.GOLD, ModWeaponItems.GOLDEN_SPEAR.getDefaultStack(), false, Disposition.NEUTRAL);

        createArtisanTableSwordRecipe(exporter, MetalTypes.BRONZE, ModWeaponItems.BRONZE_SWORD.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableDaggerRecipe(exporter, MetalTypes.BRONZE, ModWeaponItems.BRONZE_DAGGER.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableSpearRecipe(exporter, MetalTypes.BRONZE, ModWeaponItems.BRONZE_SPEAR.getDefaultStack(), false, Disposition.GOOD);

        createArtisanTableSwordRecipe(exporter, MetalTypes.CRUDE, ModWeaponItems.CRUDE_FALCHION.getDefaultStack(), false, Disposition.EVIL);
        createArtisanTableDaggerRecipe(exporter, MetalTypes.CRUDE, ModWeaponItems.CRUDE_DAGGER.getDefaultStack(), false, Disposition.EVIL);
        createArtisanTableSpearRecipe(exporter, MetalTypes.CRUDE, ModWeaponItems.CRUDE_SPEAR.getDefaultStack(), false, Disposition.EVIL);
        createArtisanTableLongswordRecipe(exporter, MetalTypes.CRUDE, ModWeaponItems.CRUDE_LONGBLADE.getDefaultStack(), false, Disposition.EVIL);

        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_SWORD.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_NOBLE_SWORD.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_SWORD.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_NOBLE_SWORD.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_SWORD.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_NOBLE_SWORD.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_HEYDAY_SWORD.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.STEEL_SWORD.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.BLACK_NUMENOREAN_SWORD.getDefaultStack(), true, Disposition.GOOD);

        createArtisanTableDaggerRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_DAGGER.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableDaggerRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_NOBLE_DAGGER.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableDaggerRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_DAGGER.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableDaggerRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_NOBLE_DAGGER.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableDaggerRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_DAGGER.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableDaggerRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_HEYDAY_DAGGER.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableDaggerRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_NOBLE_DAGGER.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableDaggerRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.BLACK_NUMENOREAN_DAGGER.getDefaultStack(), true, Disposition.EVIL);

        createArtisanTableLongswordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_LONGSWORD.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableLongswordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_NOBLE_LONGSWORD.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableLongswordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_LONGSWORD.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableLongswordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_NOBLE_LONGSWORD.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableLongswordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_LONGSWORD.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableLongswordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_HEYDAY_LONGSWORD.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableLongswordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_HEYDAY_SCIMITAR.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableLongswordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_NOBLE_LONGSWORD.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableLongswordRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.BLACK_NUMENOREAN_LONGSWORD.getDefaultStack(), true, Disposition.EVIL);

        createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_AXE.getDefaultStack(), false, Optional.empty(), Disposition.GOOD);
        createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), Disposition.GOOD);
        createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_AXE.getDefaultStack(), false, Optional.empty(), Disposition.GOOD);
        createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), Disposition.GOOD);
        createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_AXE.getDefaultStack(), false, Optional.empty(), Disposition.GOOD);
        createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), Disposition.GOOD);
        createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_HEYDAY_AXE.getDefaultStack(), true, Optional.empty(), Disposition.GOOD);
        createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.BLACK_NUMENOREAN_AXE.getDefaultStack(), true, Optional.empty(), Disposition.EVIL);

        createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_SPEAR.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_NOBLE_SPEAR.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.GONDORIAN_FOUNTAIN_GUARD_SPEAR.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_SPEAR.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.ROHIRRIC_NOBLE_SPEAR.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_SPEAR.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_HEYDAY_SPEAR.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.DALISH_NOBLE_SPEAR.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.STEEL_SPEAR.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, ModWeaponItems.BLACK_NUMENOREAN_SPEAR.getDefaultStack(), true, Disposition.EVIL);

        createArtisanTableSwordRecipe(exporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_SWORD.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableSwordRecipe(exporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_NOBLE_SWORD.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableSwordRecipe(exporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.KHAZAD_STEEL_SWORD.getDefaultStack(), false, Disposition.GOOD);

        createArtisanTableDaggerRecipe(exporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_DAGGER.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableDaggerRecipe(exporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_NOBLE_DAGGER.getDefaultStack(), true, Disposition.GOOD);

        createArtisanTableLongswordRecipe(exporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_LONGSWORD.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableLongswordRecipe(exporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_NOBLE_LONGSWORD.getDefaultStack(), true, Disposition.GOOD);

        createArtisanTableAxeRecipe(exporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_AXE.getDefaultStack(), false, Optional.empty(), Disposition.GOOD);
        createArtisanTableAxeRecipe(exporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), Disposition.GOOD);

        createArtisanTableSpearRecipe(exporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.KHAZAD_STEEL_SPEAR.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableSpearRecipe(exporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_SPEAR.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableSpearRecipe(exporter, MetalTypes.KHAZAD_STEEL, ModWeaponItems.EREBOR_NOBLE_SPEAR.getDefaultStack(), true, Disposition.GOOD);

        createArtisanTableSwordRecipe(exporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_SWORD.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableSwordRecipe(exporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_NOBLE_SWORD.getDefaultStack(), true, Disposition.GOOD);
        createArtisanTableSwordRecipe(exporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.EDHEL_STEEL_SWORD.getDefaultStack(), false, Disposition.GOOD);

        createArtisanTableDaggerRecipe(exporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_DAGGER.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableDaggerRecipe(exporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_NOBLE_DAGGER.getDefaultStack(), true, Disposition.GOOD);

        createArtisanTableLongswordRecipe(exporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_GLAIVE.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableLongswordRecipe(exporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_NOBLE_GLAIVE.getDefaultStack(), true, Disposition.GOOD);

        createArtisanTableAxeRecipe(exporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_AXE.getDefaultStack(), false, Optional.empty(), Disposition.GOOD);
        createArtisanTableAxeRecipe(exporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), Disposition.GOOD);

        createArtisanTableSpearRecipe(exporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.EDHEL_STEEL_SPEAR.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableSpearRecipe(exporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_SPEAR.getDefaultStack(), false, Disposition.GOOD);
        createArtisanTableSpearRecipe(exporter, MetalTypes.EDHEL_STEEL, ModWeaponItems.LORIEN_NOBLE_SPEAR.getDefaultStack(), true, Disposition.GOOD);

        createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.BURZUM_STEEL_SWORD.getDefaultStack(), false, Disposition.EVIL);
        createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ORC_SWORD.getDefaultStack(), false, Disposition.EVIL);
        createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_FALCHION.getDefaultStack(), false, Disposition.EVIL);
        createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_ELITE_CLEAVER.getDefaultStack(), true, Disposition.EVIL);
        createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ISENGARD_ORC_CLEAVER.getDefaultStack(), false, Disposition.EVIL);
        createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.URUK_HAI_FALCHION.getDefaultStack(), true, Disposition.EVIL);

        createArtisanTableDaggerRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ORC_KNIFE.getDefaultStack(), false, Disposition.EVIL);
        createArtisanTableDaggerRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_DAGGER.getDefaultStack(), false, Disposition.EVIL);
        createArtisanTableDaggerRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_ELITE_DAGGER.getDefaultStack(), true, Disposition.EVIL);
        createArtisanTableDaggerRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ISENGARD_ORC_DAGGER.getDefaultStack(), false, Disposition.EVIL);
        createArtisanTableDaggerRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.URUK_HAI_KNIFE.getDefaultStack(), true, Disposition.EVIL);

        createArtisanTableLongswordRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ORC_BROADBLADE.getDefaultStack(), false, Disposition.EVIL);
        createArtisanTableLongswordRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_SCIMITAR.getDefaultStack(), false, Disposition.EVIL);
        createArtisanTableLongswordRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_ELITE_WARBLADE.getDefaultStack(), true, Disposition.EVIL);
        createArtisanTableLongswordRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ISENGARD_ORC_WARBLADE.getDefaultStack(), false, Disposition.EVIL);
        createArtisanTableLongswordRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.URUK_HAI_WARBLADE.getDefaultStack(), true, Disposition.EVIL);

        createArtisanTableAxeRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ORC_AXE.getDefaultStack(), false, Optional.empty(), Disposition.EVIL);
        createArtisanTableAxeRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_AXE.getDefaultStack(), false, Optional.empty(), Disposition.EVIL);
        createArtisanTableAxeRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_ELITE_AXE.getDefaultStack(), true, Optional.empty(), Disposition.EVIL);
        createArtisanTableAxeRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ISENGARD_ORC_AXE.getDefaultStack(), false, Optional.empty(), Disposition.EVIL);
        createArtisanTableAxeRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.URUK_HAI_AXE.getDefaultStack(), true, Optional.empty(), Disposition.EVIL);

        createArtisanTableSpearRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.BURZUM_STEEL_SPEAR.getDefaultStack(), false, Disposition.EVIL);
        createArtisanTableSpearRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ORC_SPEAR.getDefaultStack(), false, Disposition.EVIL);
        createArtisanTableSpearRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_SPEAR.getDefaultStack(), false, Disposition.EVIL);
        createArtisanTableSpearRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.MORDOR_ELITE_SPEAR.getDefaultStack(), true, Disposition.EVIL);
        createArtisanTableSpearRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.ISENGARD_ORC_SPEAR.getDefaultStack(), false, Disposition.EVIL);
        createArtisanTableSpearRecipe(exporter, MetalTypes.BURZUM_STEEL, ModWeaponItems.URUK_HAI_SPEAR.getDefaultStack(), true, Disposition.EVIL);

        createArtisanTableBowRecipe(exporter, ModWeaponItems.GONDORIAN_BOW.getDefaultStack(), Disposition.GOOD);
        createArtisanTableBowRecipe(exporter, ModWeaponItems.ROHIRRIC_BOW.getDefaultStack(), Disposition.GOOD);
        createArtisanTableBowRecipe(exporter, ModWeaponItems.LORIEN_BOW.getDefaultStack(), Disposition.GOOD);
        createArtisanTableBowRecipe(exporter, Items.BOW.getDefaultStack(), Disposition.NEUTRAL);

        createArtisanTableCrossbowRecipe(exporter, Items.CROSSBOW.getDefaultStack(), Disposition.NEUTRAL);
        //endregion

        //region TOOLS
        createToolSet(exporter, MetalTypes.BRONZE, ModToolItems.BRONZE_PICKAXE.getDefaultStack(),
                ModToolItems.BRONZE_AXE.getDefaultStack(),
                ModToolItems.BRONZE_SHOVEL.getDefaultStack(),
                ModToolItems.BRONZE_HOE.getDefaultStack(),
                Optional.empty(), Disposition.GOOD);

        createToolSet(exporter, MetalTypes.CRUDE, ModToolItems.CRUDE_PICKAXE.getDefaultStack(),
                ModToolItems.CRUDE_AXE.getDefaultStack(),
                ModToolItems.CRUDE_SHOVEL.getDefaultStack(),
                ModToolItems.CRUDE_HOE.getDefaultStack(),
                Optional.empty(), Disposition.EVIL);

        createToolSet(exporter, MetalTypes.IRON, Items.IRON_PICKAXE.getDefaultStack(),
                Items.IRON_AXE.getDefaultStack(),
                Items.IRON_SHOVEL.getDefaultStack(),
                Items.IRON_HOE.getDefaultStack(),
                Optional.empty(), Disposition.NEUTRAL);

        createToolSet(exporter, MetalTypes.GOLD, Items.GOLDEN_PICKAXE.getDefaultStack(),
                Items.GOLDEN_AXE.getDefaultStack(),
                Items.GOLDEN_SHOVEL.getDefaultStack(),
                Items.GOLDEN_HOE.getDefaultStack(),
                Optional.empty(), Disposition.NEUTRAL);

        createToolSet(exporter, MetalTypes.STEEL, ModToolItems.STEEL_PICKAXE.getDefaultStack(),
                ModToolItems.STEEL_AXE.getDefaultStack(),
                ModToolItems.STEEL_SHOVEL.getDefaultStack(),
                ModToolItems.STEEL_HOE.getDefaultStack(),
                Optional.empty(), Disposition.GOOD);

        createToolSet(exporter, MetalTypes.KHAZAD_STEEL, ModToolItems.KHAZAD_STEEL_PICKAXE.getDefaultStack(),
                ModToolItems.KHAZAD_STEEL_AXE.getDefaultStack(),
                ModToolItems.KHAZAD_STEEL_SHOVEL.getDefaultStack(),
                ModToolItems.KHAZAD_STEEL_HOE.getDefaultStack(),
                Optional.empty(), Disposition.GOOD);

        createToolSet(exporter, MetalTypes.EDHEL_STEEL, ModToolItems.EDHEL_STEEL_PICKAXE.getDefaultStack(),
                ModToolItems.EDHEL_STEEL_AXE.getDefaultStack(),
                ModToolItems.EDHEL_STEEL_SHOVEL.getDefaultStack(),
                ModToolItems.EDHEL_STEEL_HOE.getDefaultStack(),
                Optional.empty(), Disposition.GOOD);

        createToolSet(exporter, MetalTypes.BURZUM_STEEL, ModToolItems.BURZUM_STEEL_PICKAXE.getDefaultStack(),
                ModToolItems.BURZUM_STEEL_AXE.getDefaultStack(),
                ModToolItems.BURZUM_STEEL_SHOVEL.getDefaultStack(),
                ModToolItems.BURZUM_STEEL_HOE.getDefaultStack(),
                Optional.empty(), Disposition.EVIL);

        createToolSet(exporter, MetalTypes.MITHRIL, ModToolItems.MITHRIL_PICKAXE.getDefaultStack(),
                ModToolItems.MITHRIL_AXE.getDefaultStack(),
                ModToolItems.MITHRIL_SHOVEL.getDefaultStack(),
                ModToolItems.MITHRIL_HOE.getDefaultStack(),
                Optional.of(MetalTypes.STEEL), Disposition.NEUTRAL);

        createToolSet(exporter, MetalTypes.MITHRIL, ModToolItems.MITHRIL_PICKAXE.getDefaultStack(),
                ModToolItems.MITHRIL_AXE.getDefaultStack(),
                ModToolItems.MITHRIL_SHOVEL.getDefaultStack(),
                ModToolItems.MITHRIL_HOE.getDefaultStack(),
                Optional.of(MetalTypes.KHAZAD_STEEL), Disposition.NEUTRAL);

        createToolSet(exporter, MetalTypes.MITHRIL, ModToolItems.MITHRIL_PICKAXE.getDefaultStack(),
                ModToolItems.MITHRIL_AXE.getDefaultStack(),
                ModToolItems.MITHRIL_SHOVEL.getDefaultStack(),
                ModToolItems.MITHRIL_HOE.getDefaultStack(),
                Optional.of(MetalTypes.EDHEL_STEEL), Disposition.NEUTRAL);

        createToolSet(exporter, MetalTypes.MITHRIL, ModToolItems.MITHRIL_PICKAXE.getDefaultStack(),
                ModToolItems.MITHRIL_AXE.getDefaultStack(),
                ModToolItems.MITHRIL_SHOVEL.getDefaultStack(),
                ModToolItems.MITHRIL_HOE.getDefaultStack(),
                Optional.of(MetalTypes.BURZUM_STEEL), Disposition.NEUTRAL);

        createToolSet(exporter, MetalTypes.NETHERITE, Items.NETHERITE_PICKAXE.getDefaultStack(),
                Items.NETHERITE_AXE.getDefaultStack(),
                Items.NETHERITE_SHOVEL.getDefaultStack(),
                Items.NETHERITE_HOE.getDefaultStack(),
                Optional.empty(), Disposition.NEUTRAL);
        //endregion

        //region ARMOR
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

        ItemStack ironArmorPlate = new ItemStack(ModResourceItems.ARMOR_PLATE);
        ironArmorPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MetalTypes.IRON.getName()))), getPattern()));
        ItemStack ironHelmetPlate = new ItemStack(ModResourceItems.HELMET_PLATE);
        ironHelmetPlate.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MetalTypes.IRON.getName()))), getPattern()));
        ItemStack ironChainmail = new ItemStack(ModResourceItems.CHAINMAIL);
        ironChainmail.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MetalTypes.IRON.getName()))), getPattern()));

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
        //endregion
    }

    private void createToolSet(RecipeExporter exporter, MetalTypes metal, ItemStack outputPickaxe, ItemStack outputAxe, ItemStack outputShovel, ItemStack outputHoe, Optional<MetalTypes> rodMetal, Disposition disposition) {
        createArtisanTablePickaxeRecipe(exporter, metal, outputPickaxe, rodMetal, disposition);
        createArtisanTableAxeRecipe(exporter, metal, outputAxe, false, rodMetal, disposition);
        createArtisanTableShovelRecipe(exporter, metal, outputShovel, rodMetal, disposition);
        createArtisanTableHoeRecipe(exporter, metal, outputHoe, rodMetal, disposition);
    }

    private void createArtisanTableSwordRecipe(RecipeExporter exporter, MetalTypes metal, ItemStack output, boolean noble, Disposition disposition) {
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

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "sword", disposition)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(blade.getItem()), blade.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(swordHilt.getItem()), swordHilt.getComponentChanges()))
                .input(Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(blade.getItem()),
                        FabricRecipeProvider.conditionsFromItem(blade.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(output.getItem()).getPath() + "_artisan"));
    }

    private void createArtisanTableLongswordRecipe(RecipeExporter exporter, MetalTypes metal, ItemStack output, boolean noble, Disposition disposition) {
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

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "sword", disposition)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(longBlade.getItem()), longBlade.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(swordHilt.getItem()), swordHilt.getComponentChanges()))
                .input(Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(longBlade.getItem()),
                        FabricRecipeProvider.conditionsFromItem(longBlade.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(output.getItem()).getPath() + "_artisan"));
    }

    private void createArtisanTableDaggerRecipe(RecipeExporter exporter, MetalTypes metal, ItemStack output, boolean noble, Disposition disposition) {
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

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "sword", disposition)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(shortBlade.getItem()), shortBlade.getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(swordHilt.getItem()), swordHilt.getComponentChanges()))
                .input(Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(shortBlade.getItem()),
                        FabricRecipeProvider.conditionsFromItem(shortBlade.getItem()))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(output.getItem()).getPath() + "_artisan"));
    }

    private void createArtisanTableSpearRecipe(RecipeExporter exporter, MetalTypes metal, ItemStack output, boolean noble, Disposition disposition) {
        ItemStack blade = new ItemStack(ModResourceItems.SHORT_BLADE);
        blade.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        if (!noble) {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "spear", disposition)
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(blade.getItem()), blade.getComponentChanges()))
                    .input(Items.STICK)
                    .input(Items.STICK)
                    .criterion(FabricRecipeProvider.hasItem(blade.getItem()),
                            FabricRecipeProvider.conditionsFromItem(blade.getItem()))
                    .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(output.getItem()).getPath() + "_artisan"));
        } else {
            ItemStack rod = new ItemStack(ModResourceItems.ROD);
            rod.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    Identifier.of(MetalTypes.GOLD.getName()))), getPattern()));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "spear", disposition)
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(blade.getItem()), blade.getComponentChanges()))
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                    .input(Items.STICK)
                    .criterion(FabricRecipeProvider.hasItem(blade.getItem()),
                            FabricRecipeProvider.conditionsFromItem(blade.getItem()))
                    .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(output.getItem()).getPath() + "_artisan"));
        }
    }

    private void createArtisanTableBowRecipe(RecipeExporter exporter, ItemStack output, Disposition disposition) {
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "bow", disposition)
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Items.STICK)
                .input(Items.STRING)
                .criterion(FabricRecipeProvider.hasItem(Items.STRING),
                        FabricRecipeProvider.conditionsFromItem(Items.STRING))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(output.getItem()).getPath() + "_artisan"));
    }

    private void createArtisanTableCrossbowRecipe(RecipeExporter exporter, ItemStack output, Disposition disposition) {
        ItemStack rod = new ItemStack(ModResourceItems.ROD);
        rod.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                Identifier.of(MetalTypes.IRON.getName()))), getPattern()));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "crossbow", disposition)
                .input(Items.STICK)
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Blocks.TRIPWIRE_HOOK)
                .input(Items.STRING)
                .input(Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.STRING),
                        FabricRecipeProvider.conditionsFromItem(Items.STRING))
                .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(output.getItem()).getPath() + "_artisan"));
    }

    private void createArtisanTablePickaxeRecipe(RecipeExporter exporter, MetalTypes metal, ItemStack output, Optional<MetalTypes> rodMetal, Disposition disposition) {
        ItemStack pickaxeHead = new ItemStack(ModResourceItems.PICKAXE_HEAD);
        pickaxeHead.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        if (rodMetal.isPresent()){
            ItemStack rod = new ItemStack(ModResourceItems.ROD);
            rod.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    getMetalIdentifier(rodMetal.get()))), getPattern()));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "pickaxe", disposition)
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(pickaxeHead.getItem()), pickaxeHead.getComponentChanges()))
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                    .criterion(FabricRecipeProvider.hasItem(pickaxeHead.getItem()),
                            FabricRecipeProvider.conditionsFromItem(pickaxeHead.getItem()))
                    .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(output.getItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan"));
        } else {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "pickaxe", disposition)
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(pickaxeHead.getItem()), pickaxeHead.getComponentChanges()))
                    .input(Items.STICK)
                    .input(Items.STICK)
                    .criterion(FabricRecipeProvider.hasItem(pickaxeHead.getItem()),
                            FabricRecipeProvider.conditionsFromItem(pickaxeHead.getItem()))
                    .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(output.getItem()).getPath() + "_artisan"));
        }
    }

    private void createArtisanTableAxeRecipe(RecipeExporter exporter, MetalTypes metal, ItemStack output, boolean noble, Optional<MetalTypes> rodMetal, Disposition disposition) {
        ItemStack axeHead = new ItemStack(ModResourceItems.AXE_HEAD);
        axeHead.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        if (!noble){
            if (rodMetal.isPresent()){
                ItemStack rod = new ItemStack(ModResourceItems.ROD);
                rod.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                        getMetalIdentifier(rodMetal.get()))), getPattern()));
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "axe", disposition)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(axeHead.getItem()), axeHead.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                        .criterion(FabricRecipeProvider.hasItem(axeHead.getItem()),
                                FabricRecipeProvider.conditionsFromItem(axeHead.getItem()))
                        .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(output.getItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan"));
            } else {
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "axe", disposition)
                        .componentInput(new ComponentsIngredient(Ingredient.ofItems(axeHead.getItem()), axeHead.getComponentChanges()))
                        .input(Items.STICK)
                        .input(Items.STICK)
                        .criterion(FabricRecipeProvider.hasItem(axeHead.getItem()),
                                FabricRecipeProvider.conditionsFromItem(axeHead.getItem()))
                        .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(output.getItem()).getPath() + "_artisan"));
            }
        } else {
            ItemStack rod = new ItemStack(ModResourceItems.ROD);
            rod.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    Identifier.of(MetalTypes.GOLD.getName()))), getPattern()));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "axe", disposition)
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(axeHead.getItem()), axeHead.getComponentChanges()))
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                    .input(Items.STICK)
                    .criterion(FabricRecipeProvider.hasItem(axeHead.getItem()),
                            FabricRecipeProvider.conditionsFromItem(axeHead.getItem()))
                    .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(output.getItem()).getPath() + "_artisan"));
        }
    }

    private void createArtisanTableShovelRecipe(RecipeExporter exporter, MetalTypes metal, ItemStack output, Optional<MetalTypes> rodMetal, Disposition disposition) {
        ItemStack shovelHead = new ItemStack(ModResourceItems.SHOVEL_HEAD);
        shovelHead.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        if (rodMetal.isPresent()){
            ItemStack rod = new ItemStack(ModResourceItems.ROD);
            rod.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    getMetalIdentifier(rodMetal.get()))), getPattern()));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "shovel", disposition)
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(shovelHead.getItem()), shovelHead.getComponentChanges()))
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                    .criterion(FabricRecipeProvider.hasItem(shovelHead.getItem()),
                            FabricRecipeProvider.conditionsFromItem(shovelHead.getItem()))
                    .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(output.getItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan"));
        } else {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "shovel", disposition)
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(shovelHead.getItem()), shovelHead.getComponentChanges()))
                    .input(Items.STICK)
                    .input(Items.STICK)
                    .criterion(FabricRecipeProvider.hasItem(shovelHead.getItem()),
                            FabricRecipeProvider.conditionsFromItem(shovelHead.getItem()))
                    .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(output.getItem()).getPath() + "_artisan"));
        }
    }

    private void createArtisanTableHoeRecipe(RecipeExporter exporter, MetalTypes metal, ItemStack output, Optional<MetalTypes> rodMetal, Disposition disposition) {
        ItemStack hoeHead = new ItemStack(ModResourceItems.HOE_HEAD);
        hoeHead.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        if (rodMetal.isPresent()){
            ItemStack rod = new ItemStack(ModResourceItems.ROD);
            rod.set(DataComponentTypes.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    getMetalIdentifier(rodMetal.get()))), getPattern()));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "hoe", disposition)
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(hoeHead.getItem()), hoeHead.getComponentChanges()))
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(rod.getItem()), rod.getComponentChanges()))
                    .criterion(FabricRecipeProvider.hasItem(hoeHead.getItem()),
                            FabricRecipeProvider.conditionsFromItem(hoeHead.getItem()))
                    .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(output.getItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan"));
        } else {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "hoe", disposition)
                    .componentInput(new ComponentsIngredient(Ingredient.ofItems(hoeHead.getItem()), hoeHead.getComponentChanges()))
                    .input(Items.STICK)
                    .input(Items.STICK)
                    .criterion(FabricRecipeProvider.hasItem(hoeHead.getItem()),
                            FabricRecipeProvider.conditionsFromItem(hoeHead.getItem()))
                    .offerTo(exporter, Identifier.of(MiddleEarth.MOD_ID, Registries.ITEM.getId(output.getItem()).getPath() + "_artisan"));
        }
    }
}