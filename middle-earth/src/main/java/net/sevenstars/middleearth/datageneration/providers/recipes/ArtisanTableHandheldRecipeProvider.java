package net.sevenstars.middleearth.datageneration.providers.recipes;

import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.forge.MetalTypes;
import net.sevenstars.middleearth.datageneration.custom.ArtisanTableRecipeJsonBuilder;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.SmithingTrimPatternsME;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;


public class ArtisanTableHandheldRecipeProvider extends RecipeProvider {
    private final int XP_MEDIUM_SHIELD = 2;
    private final int XP_HEAVY_SHIELD = 4;
    private final CompletableFuture<HolderLookup.Provider> registryLookup;

    public ArtisanTableHandheldRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);

        this.registryLookup = registriesFuture;
    }

    public HolderLookup.RegistryLookup<TrimMaterial> getArmorTrimMaterialsRegistry(){
        HolderLookup.RegistryLookup<TrimMaterial> armorTrimMaterialsRegistry;

        try {
            armorTrimMaterialsRegistry = registryLookup.get().lookupOrThrow(Registries.TRIM_MATERIAL);
        } catch (Exception ignored) {
            throw new IllegalStateException("Data generation without registries failed!");
        }
        return armorTrimMaterialsRegistry;
    }

    public HolderLookup.RegistryLookup<TrimPattern> getArmorTrimPatternsRegistry(){
        HolderLookup.RegistryLookup<TrimPattern> armorTrimPatternsRegistry;

        try {
            armorTrimPatternsRegistry = registryLookup.get().lookupOrThrow(Registries.TRIM_PATTERN);
        } catch (Exception ignored) {
            throw new IllegalStateException("Data generation without registries failed!");
        }
        return armorTrimPatternsRegistry;
    }

    public Holder<TrimPattern> getPattern(){
        return getArmorTrimPatternsRegistry().getOrThrow(SmithingTrimPatternsME.SMITHING_PART);
    }
    
    public ResourceLocation getMetalIdentifier(MetalTypes metal){
        if (metal.isVanilla()){
            return ResourceLocation.parse(metal.getName());
        } else {
            return ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, metal.getName());
        }
    }

    @Override
    protected void buildRecipes(RecipeOutput output, HolderLookup.Provider registries) {
        HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

                //region WEAPONS
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.IRON, Items.IRON_SWORD.getDefaultInstance(), false, DispositionType.NEUTRAL);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.IRON, WeaponItemsME.IRON_DAGGER.getDefaultInstance(), false, DispositionType.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.IRON, WeaponItemsME.IRON_SPEAR.getDefaultInstance(), false, DispositionType.NEUTRAL);

                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.GOLD, Items.GOLDEN_SWORD.getDefaultInstance(), false, DispositionType.NEUTRAL);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.GOLD, WeaponItemsME.GOLDEN_DAGGER.getDefaultInstance(), false, DispositionType.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.GOLD, WeaponItemsME.GOLDEN_SPEAR.getDefaultInstance(), false, DispositionType.NEUTRAL);

                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.NETHERITE, Items.NETHERITE_SWORD.getDefaultInstance(), false, DispositionType.NEUTRAL);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.NETHERITE, WeaponItemsME.NETHERITE_DAGGER.getDefaultInstance(), false, DispositionType.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.NETHERITE, WeaponItemsME.NETHERITE_SPEAR.getDefaultInstance(), false, DispositionType.NEUTRAL);

                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.BRONZE, WeaponItemsME.BRONZE_SWORD.getDefaultInstance(), false, DispositionType.NEUTRAL);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.BRONZE, WeaponItemsME.BRONZE_DAGGER.getDefaultInstance(), false, DispositionType.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.BRONZE, WeaponItemsME.BRONZE_SPEAR.getDefaultInstance(), false, DispositionType.NEUTRAL);

                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.CRUDE, WeaponItemsME.CRUDE_FALCHION.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.CRUDE, WeaponItemsME.CRUDE_DAGGER.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.CRUDE, WeaponItemsME.CRUDE_SPEAR.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.CRUDE, WeaponItemsME.CRUDE_LONGBLADE.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.CRUDE, Items.BONE, WeaponItemsME.GOBLIN_TOWN_FALCHION.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.CRUDE, Items.BONE, WeaponItemsME.GOBLIN_TOWN_SHANK.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.CRUDE, Items.BONE, WeaponItemsME.GOBLIN_TOWN_SCIMITAR.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.CRUDE, Items.BONE, WeaponItemsME.GOBLIN_TOWN_SPEAR.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.CRUDE, Items.BONE, WeaponItemsME.GOBLIN_TOWN_AXE.getDefaultInstance(), false, Optional.empty(), DispositionType.EVIL);

                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_SWORD.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_SWORD.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_SWORD.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_SWORD.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.DALISH_SWORD.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_SWORD.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_SWORD.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.STEEL_SWORD.getDefaultInstance(), false, DispositionType.NEUTRAL);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_SWORD.getDefaultInstance(), true, DispositionType.EVIL);

                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_DAGGER.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_DAGGER.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_DAGGER.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_DAGGER.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.DALISH_DAGGER.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_DAGGER.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_DAGGER.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_DAGGER.getDefaultInstance(), true, DispositionType.EVIL);

                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_LONGSWORD.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_LONGSWORD.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_LONGSWORD.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_LONGSWORD.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.DALISH_LONGSWORD.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_LONGSWORD.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_SCIMITAR.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_LONGSWORD.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_LONGSWORD.getDefaultInstance(), true, DispositionType.EVIL);

                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_AXE.getDefaultInstance(), false, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_AXE.getDefaultInstance(), true, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_AXE.getDefaultInstance(), false, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_AXE.getDefaultInstance(), true, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.DALISH_AXE.getDefaultInstance(), false, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_AXE.getDefaultInstance(), true, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_AXE.getDefaultInstance(), true, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_AXE.getDefaultInstance(), true, Optional.empty(), DispositionType.EVIL);

                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_SPEAR.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_SPEAR.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_FOUNTAIN_GUARD_SPEAR.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_SPEAR.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_SPEAR.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.DALISH_SPEAR.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_SPEAR.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_SPEAR.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.STEEL_SPEAR.getDefaultInstance(), false, DispositionType.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_SPEAR.getDefaultInstance(), true, DispositionType.EVIL);

                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_SWORD.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_SWORD.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.KHAZAD_STEEL, WeaponItemsME.KHAZAD_STEEL_SWORD.getDefaultInstance(), false, DispositionType.GOOD);

                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_DAGGER.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_DAGGER.getDefaultInstance(), true, DispositionType.GOOD);

                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_LONGSWORD.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_LONGSWORD.getDefaultInstance(), true, DispositionType.GOOD);

                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_AXE.getDefaultInstance(), false, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_AXE.getDefaultInstance(), true, Optional.empty(), DispositionType.GOOD);

                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.KHAZAD_STEEL, WeaponItemsME.KHAZAD_STEEL_SPEAR.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_SPEAR.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_SPEAR.getDefaultInstance(), true, DispositionType.GOOD);

                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_SWORD.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_SWORD.getDefaultInstance(), true, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.EDHEL_STEEL_SWORD.getDefaultInstance(), false, DispositionType.GOOD);

                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_DAGGER.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_DAGGER.getDefaultInstance(), true, DispositionType.GOOD);

                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_GLAIVE.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_GLAIVE.getDefaultInstance(), true, DispositionType.GOOD);

                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_AXE.getDefaultInstance(), false, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_AXE.getDefaultInstance(), true, Optional.empty(), DispositionType.GOOD);

                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.EDHEL_STEEL_SPEAR.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_SPEAR.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_SPEAR.getDefaultInstance(), true, DispositionType.GOOD);

                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_SWORD.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_NOBLE_SWORD.getDefaultInstance(), true, DispositionType.GOOD);

                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_DAGGER.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_NOBLE_DAGGER.getDefaultInstance(), true, DispositionType.GOOD);

                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_LONGSWORD.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_NOBLE_LONGSWORD.getDefaultInstance(), true, DispositionType.GOOD);

                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_AXE.getDefaultInstance(), false, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_NOBLE_AXE.getDefaultInstance(), true, Optional.empty(), DispositionType.GOOD);

                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_SPEAR.getDefaultInstance(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_NOBLE_SPEAR.getDefaultInstance(), true, DispositionType.GOOD);


                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.BURZUM_STEEL_SWORD.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_SWORD.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_FALCHION.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_CLEAVER.getDefaultInstance(), true, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.DOL_GULDUR_MACHETE.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_CLEAVER.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_FALCHION.getDefaultInstance(), true, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_FALCHION.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_CLEAVER.getDefaultInstance(), true, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORIA_GOBLIN_FALCHION.getDefaultInstance(), true, DispositionType.EVIL);

                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_KNIFE.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_DAGGER.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_DAGGER.getDefaultInstance(), true, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_DAGGER.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_KNIFE.getDefaultInstance(), true, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_SHANK.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_DAGGER.getDefaultInstance(), true, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORIA_GOBLIN_SHANK.getDefaultInstance(), true, DispositionType.EVIL);

                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_BROADBLADE.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_SCIMITAR.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_WARBLADE.getDefaultInstance(), true, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_WARBLADE.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_WARBLADE.getDefaultInstance(), true, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_WARBLADE.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_SCIMITAR.getDefaultInstance(), true, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORIA_GOBLIN_HOOKBLADE.getDefaultInstance(), true, DispositionType.EVIL);

                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_AXE.getDefaultInstance(), false, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_AXE.getDefaultInstance(), false, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_AXE.getDefaultInstance(), true, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.DOL_GULDUR_AXE.getDefaultInstance(), false, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_AXE.getDefaultInstance(), false, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_AXE.getDefaultInstance(), true, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_AXE.getDefaultInstance(), false, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_BATTLEAXE.getDefaultInstance(), true, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORIA_GOBLIN_HOOKAXE.getDefaultInstance(), true, Optional.empty(), DispositionType.EVIL);

                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.BURZUM_STEEL_SPEAR.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_SPEAR.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_SPEAR.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_SPEAR.getDefaultInstance(), true, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_SPEAR.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_SPEAR.getDefaultInstance(), true, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_SPEAR.getDefaultInstance(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_SPEAR.getDefaultInstance(), true, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORIA_GOBLIN_SPEAR.getDefaultInstance(), true, DispositionType.EVIL);

                createArtisanTableBowRecipe(itemLookup, output, WeaponItemsME.GONDORIAN_BOW.getDefaultInstance(), DispositionType.GOOD);
                createArtisanTableLongbowRecipe(itemLookup, output, WeaponItemsME.GONDORIAN_LONGBOW.getDefaultInstance(), DispositionType.GOOD);
                createArtisanTableNobleLongbowRecipe(itemLookup, output, WeaponItemsME.GONDORIAN_NOBLE_LONGBOW.getDefaultInstance(), DispositionType.GOOD);

                createArtisanTableBowRecipe(itemLookup, output, WeaponItemsME.ROHIRRIC_BOW.getDefaultInstance(), DispositionType.GOOD);
                createArtisanTableNobleBowRecipe(itemLookup, output, WeaponItemsME.ROHIRRIC_NOBLE_BOW.getDefaultInstance(), DispositionType.GOOD);
                createArtisanTableLongbowRecipe(itemLookup, output, WeaponItemsME.ROHIRRIC_LONGBOW.getDefaultInstance(), DispositionType.GOOD);

                createArtisanTableLongbowRecipe(itemLookup, output, WeaponItemsME.DALISH_LONGBOW.getDefaultInstance(), DispositionType.GOOD);
                createArtisanTableNobleLongbowRecipe(itemLookup, output, WeaponItemsME.DALISH_HEYDAY_LONGBOW.getDefaultInstance(), DispositionType.GOOD);
                createArtisanTableNobleLongbowRecipe(itemLookup, output, WeaponItemsME.DALISH_NOBLE_LONGBOW.getDefaultInstance(), DispositionType.GOOD);

                createArtisanTableBowRecipe(itemLookup, output, WeaponItemsME.LORIEN_BOW.getDefaultInstance(), DispositionType.GOOD);
                createArtisanTableLongbowRecipe(itemLookup, output, WeaponItemsME.LORIEN_LONGBOW.getDefaultInstance(), DispositionType.GOOD);
                createArtisanTableNobleLongbowRecipe(itemLookup, output, WeaponItemsME.LORIEN_NOBLE_LONGBOW.getDefaultInstance(), DispositionType.GOOD);

                createArtisanTableBowRecipe(itemLookup, output, WeaponItemsME.WOODLAND_REALM_BOW.getDefaultInstance(), DispositionType.GOOD);
                createArtisanTableLongbowRecipe(itemLookup, output, WeaponItemsME.WOODLAND_REALM_LONGBOW.getDefaultInstance(), DispositionType.GOOD);
                createArtisanTableNobleBowRecipe(itemLookup, output, WeaponItemsME.WOODLAND_REALM_NOBLE_BOW.getDefaultInstance(), DispositionType.GOOD);
                createArtisanTableNobleLongbowRecipe(itemLookup, output, WeaponItemsME.WOODLAND_REALM_NOBLE_LONGBOW.getDefaultInstance(), DispositionType.GOOD);

                createArtisanTableBowRecipe(itemLookup, output, WeaponItemsME.EREBOR_BOW.getDefaultInstance(), DispositionType.GOOD);
                createArtisanTableNobleBowRecipe(itemLookup, output, WeaponItemsME.EREBOR_NOBLE_BOW.getDefaultInstance(), DispositionType.GOOD);
                createArtisanTableCrossbowRecipe(itemLookup, output, WeaponItemsME.EREBOR_CROSSBOW.getDefaultInstance(), DispositionType.GOOD);
                createArtisanTableNobleCrossbowRecipe(itemLookup, output, WeaponItemsME.EREBOR_NOBLE_CROSSBOW.getDefaultInstance(), DispositionType.GOOD);

                createArtisanTableBowRecipe(itemLookup, output, WeaponItemsME.ORCISH_BOW.getDefaultInstance(), DispositionType.EVIL);

                createArtisanTableBowRecipe(itemLookup, output, WeaponItemsME.MORDOR_BOW.getDefaultInstance(), DispositionType.EVIL);
                createArtisanTableNobleLongbowRecipe(itemLookup, output, WeaponItemsME.MORDOR_ELITE_LONGBOW.getDefaultInstance(), DispositionType.EVIL);

                createArtisanTableNobleBowRecipe(itemLookup, output, WeaponItemsME.URUK_HAI_BOW.getDefaultInstance(), DispositionType.EVIL);
                createArtisanTableNobleCrossbowRecipe(itemLookup, output, WeaponItemsME.URUK_HAI_CROSSBOW.getDefaultInstance(), DispositionType.EVIL);

                createArtisanTableBowRecipe(itemLookup, output, WeaponItemsME.GUNDABAD_BOW.getDefaultInstance(), DispositionType.EVIL);
                createArtisanTableLongbowRecipe(itemLookup, output, WeaponItemsME.GUNDABAD_LONGBOW.getDefaultInstance(), DispositionType.EVIL);
                createArtisanTableCrossbowRecipe(itemLookup, output, WeaponItemsME.GOBLIN_CROSSBOW.getDefaultInstance(), DispositionType.EVIL);
                createArtisanTableBowRecipe(itemLookup, output, WeaponItemsME.GOBLIN_TOWN_BOW.getDefaultInstance(), DispositionType.EVIL);
                createArtisanTableBowRecipe(itemLookup, output, WeaponItemsME.MORIA_GOBLIN_BOW.getDefaultInstance(), DispositionType.EVIL);

                createArtisanTableBowRecipe(itemLookup, output, Items.BOW.getDefaultInstance(), DispositionType.NEUTRAL);
                createArtisanTableCrossbowRecipe(itemLookup, output, Items.CROSSBOW.getDefaultInstance(), DispositionType.NEUTRAL);
                //endregion

                //region TOOLS
                createToolSet(itemLookup, output, MetalTypes.BRONZE, ToolItemsME.BRONZE_PICKAXE.getDefaultInstance(),
                        ToolItemsME.BRONZE_AXE.getDefaultInstance(),
                        ToolItemsME.BRONZE_SHOVEL.getDefaultInstance(),
                        ToolItemsME.BRONZE_HOE.getDefaultInstance(),
                        Optional.empty(), DispositionType.NEUTRAL);

                createToolSet(itemLookup, output, MetalTypes.CRUDE, ToolItemsME.CRUDE_PICKAXE.getDefaultInstance(),
                        ToolItemsME.CRUDE_AXE.getDefaultInstance(),
                        ToolItemsME.CRUDE_SHOVEL.getDefaultInstance(),
                        ToolItemsME.CRUDE_HOE.getDefaultInstance(),
                        Optional.empty(), DispositionType.EVIL);

                createToolSet(itemLookup, output, MetalTypes.IRON, Items.IRON_PICKAXE.getDefaultInstance(),
                        Items.IRON_AXE.getDefaultInstance(),
                        Items.IRON_SHOVEL.getDefaultInstance(),
                        Items.IRON_HOE.getDefaultInstance(),
                        Optional.empty(), DispositionType.NEUTRAL);

                createToolSet(itemLookup, output, MetalTypes.GOLD, Items.GOLDEN_PICKAXE.getDefaultInstance(),
                        Items.GOLDEN_AXE.getDefaultInstance(),
                        Items.GOLDEN_SHOVEL.getDefaultInstance(),
                        Items.GOLDEN_HOE.getDefaultInstance(),
                        Optional.empty(), DispositionType.NEUTRAL);

                createToolSet(itemLookup, output, MetalTypes.STEEL, ToolItemsME.STEEL_PICKAXE.getDefaultInstance(),
                        ToolItemsME.STEEL_AXE.getDefaultInstance(),
                        ToolItemsME.STEEL_SHOVEL.getDefaultInstance(),
                        ToolItemsME.STEEL_HOE.getDefaultInstance(),
                        Optional.empty(), DispositionType.NEUTRAL);

                createToolSet(itemLookup, output, MetalTypes.KHAZAD_STEEL, ToolItemsME.KHAZAD_STEEL_PICKAXE.getDefaultInstance(),
                        ToolItemsME.KHAZAD_STEEL_AXE.getDefaultInstance(),
                        ToolItemsME.KHAZAD_STEEL_SHOVEL.getDefaultInstance(),
                        ToolItemsME.KHAZAD_STEEL_HOE.getDefaultInstance(),
                        Optional.empty(), DispositionType.GOOD);

                createToolSet(itemLookup, output, MetalTypes.EDHEL_STEEL, ToolItemsME.EDHEL_STEEL_PICKAXE.getDefaultInstance(),
                        ToolItemsME.EDHEL_STEEL_AXE.getDefaultInstance(),
                        ToolItemsME.EDHEL_STEEL_SHOVEL.getDefaultInstance(),
                        ToolItemsME.EDHEL_STEEL_HOE.getDefaultInstance(),
                        Optional.empty(), DispositionType.GOOD);

                createToolSet(itemLookup, output, MetalTypes.BURZUM_STEEL, ToolItemsME.BURZUM_STEEL_PICKAXE.getDefaultInstance(),
                        ToolItemsME.BURZUM_STEEL_AXE.getDefaultInstance(),
                        ToolItemsME.BURZUM_STEEL_SHOVEL.getDefaultInstance(),
                        ToolItemsME.BURZUM_STEEL_HOE.getDefaultInstance(),
                        Optional.empty(), DispositionType.EVIL);

                createToolSet(itemLookup, output, MetalTypes.MITHRIL, ToolItemsME.MITHRIL_PICKAXE.getDefaultInstance(),
                        ToolItemsME.MITHRIL_AXE.getDefaultInstance(),
                        ToolItemsME.MITHRIL_SHOVEL.getDefaultInstance(),
                        ToolItemsME.MITHRIL_HOE.getDefaultInstance(),
                        Optional.of(MetalTypes.STEEL), DispositionType.NEUTRAL);

                createToolSet(itemLookup, output, MetalTypes.MITHRIL, ToolItemsME.MITHRIL_PICKAXE.getDefaultInstance(),
                        ToolItemsME.MITHRIL_AXE.getDefaultInstance(),
                        ToolItemsME.MITHRIL_SHOVEL.getDefaultInstance(),
                        ToolItemsME.MITHRIL_HOE.getDefaultInstance(),
                        Optional.of(MetalTypes.KHAZAD_STEEL), DispositionType.NEUTRAL);

                createToolSet(itemLookup, output, MetalTypes.MITHRIL, ToolItemsME.MITHRIL_PICKAXE.getDefaultInstance(),
                        ToolItemsME.MITHRIL_AXE.getDefaultInstance(),
                        ToolItemsME.MITHRIL_SHOVEL.getDefaultInstance(),
                        ToolItemsME.MITHRIL_HOE.getDefaultInstance(),
                        Optional.of(MetalTypes.EDHEL_STEEL), DispositionType.NEUTRAL);

                createToolSet(itemLookup, output, MetalTypes.MITHRIL, ToolItemsME.MITHRIL_PICKAXE.getDefaultInstance(),
                        ToolItemsME.MITHRIL_AXE.getDefaultInstance(),
                        ToolItemsME.MITHRIL_SHOVEL.getDefaultInstance(),
                        ToolItemsME.MITHRIL_HOE.getDefaultInstance(),
                        Optional.of(MetalTypes.BURZUM_STEEL), DispositionType.NEUTRAL);

                createToolSet(itemLookup, output, MetalTypes.NETHERITE, Items.NETHERITE_PICKAXE.getDefaultInstance(),
                        Items.NETHERITE_AXE.getDefaultInstance(),
                        Items.NETHERITE_SHOVEL.getDefaultInstance(),
                        Items.NETHERITE_HOE.getDefaultInstance(),
                        Optional.empty(), DispositionType.NEUTRAL);

                createArtisanTableChiselRecipe(itemLookup, output, MetalTypes.IRON, Items.IRON_NUGGET, ToolItemsME.IRON_CHISEL.getDefaultInstance());
                createArtisanTableChiselRecipe(itemLookup, output, MetalTypes.STEEL, Items.GOLD_NUGGET, ToolItemsME.STEEL_CHISEL.getDefaultInstance());
                createArtisanTableChiselRecipe(itemLookup, output, MetalTypes.KHAZAD_STEEL, Items.GOLD_NUGGET, ToolItemsME.STEEL_CHISEL.getDefaultInstance());
                createArtisanTableChiselRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, Items.GOLD_NUGGET, ToolItemsME.STEEL_CHISEL.getDefaultInstance());
                createArtisanTableChiselRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, Items.GOLD_NUGGET, ToolItemsME.STEEL_CHISEL.getDefaultInstance());
                createArtisanTableChiselRecipe(itemLookup, output, MetalTypes.MITHRIL, ResourceItemsME.MITHRIL_NUGGET, ToolItemsME.MITHRIL_CHISEL.getDefaultInstance());
                        
                //endregion

                //region SHIELDS
                ItemStack ironShieldBorder = new ItemStack(ResourceItemsME.SHIELD_BORDER);
                ironShieldBorder.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                        ResourceLocation.parse(MetalTypes.IRON.getName()))), getPattern()));

                ItemStack bronzeShieldBorder = new ItemStack(ResourceItemsME.SHIELD_BORDER);
                bronzeShieldBorder.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.BRONZE.getName()))), getPattern()));

                ItemStack crudeShieldBorder = new ItemStack(ResourceItemsME.SHIELD_BORDER);
                crudeShieldBorder.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.CRUDE.getName()))), getPattern()));

                ItemStack steelShieldPlate = new ItemStack(ResourceItemsME.SHIELD_PLATE);
                steelShieldPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName()))), getPattern()));

                ItemStack edhelSteelShieldPlate = new ItemStack(ResourceItemsME.SHIELD_PLATE);
                edhelSteelShieldPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName()))), getPattern()));

                ItemStack khazadSteelShieldPlate = new ItemStack(ResourceItemsME.SHIELD_PLATE);
                khazadSteelShieldPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName()))), getPattern()));

                ItemStack burzumSteelShieldPlate = new ItemStack(ResourceItemsME.SHIELD_PLATE);
                burzumSteelShieldPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                        ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName()))), getPattern()));

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, Items.SHIELD.getDefaultInstance(), "medium_shield", DispositionType.NEUTRAL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(Items.SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROUND_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.NEUTRAL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.HEATER_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.NEUTRAL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.HEATER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.KITE_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.NEUTRAL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.KITE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.BLACK_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GONDORIAN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_TOWER_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.BLACK_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GONDORIAN_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_KINGS_GUARD_TOWER_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.BLACK_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GONDORIAN_KINGS_GUARD_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.LAST_ALLIANCE_HEIRLOOM_TOWER_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.BLACK_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.LAST_ALLIANCE_HEIRLOOM_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_HERO_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GONDORIAN_HERO_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_KNIGHT_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, steelShieldPlate.getComponents(), steelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GONDORIAN_KNIGHT_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_ORNAMENTED_KNIGHT_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, steelShieldPlate.getComponents(), steelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.GREEN_DYE)
                        .input(Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GONDORIAN_ORNAMENTED_KNIGHT_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.WHITE_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.GREEN_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROHIRRIC_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_BUCKING_HORSE_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.GREEN_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROHIRRIC_BUCKING_HORSE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_GALLOPING_HORSE_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.GREEN_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROHIRRIC_GALLOPING_HORSE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_HORSE_HEAD_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.GREEN_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROHIRRIC_HORSE_HEAD_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_PLAINSMAN_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.YELLOW_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.GREEN_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROHIRRIC_PLAINSMAN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_TWIN_HORSES_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.GREEN_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROHIRRIC_TWIN_HORSES_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_EORLING_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, steelShieldPlate.getComponents(), steelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROHIRRIC_EORLING_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_ORNAMENTED_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, steelShieldPlate.getComponents(), steelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROHIRRIC_ORNAMENTED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_ROYAL_GUARD_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, steelShieldPlate.getComponents(), steelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROHIRRIC_ROYAL_GUARD_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_BLUE_OVAL_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.BLUE_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.BLUE_DYE)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.BLUE_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DALISH_BLUE_OVAL_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_BARDING_OVAL_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.BLUE_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.GOLD_NUGGET)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DALISH_BARDING_OVAL_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_BLUE_BRACED_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.BLUE_DYE)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.BLUE_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DALISH_BLUE_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_BARDING_BRACED_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.GOLD_NUGGET)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DALISH_BARDING_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_HEAVY_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.WHITE_DYE)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, steelShieldPlate.getComponents(), steelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DALISH_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_BARDING_HEAVY_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.BLUE_DYE)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, steelShieldPlate.getComponents(), steelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DALISH_BARDING_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_ROYAL_HEAVY_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LIGHT_BLUE_DYE)
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, steelShieldPlate.getComponents(), steelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DALISH_ROYAL_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_ROYAL_ROUND_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LIGHT_BLUE_DYE)
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, steelShieldPlate.getComponents(), steelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DALISH_ROYAL_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_HEYDAY_ROUND_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.ORANGE_DYE)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, steelShieldPlate.getComponents(), steelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DALISH_HEYDAY_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.LORIEN_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.LORIEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.LORIEN_LAURELS_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.YELLOW_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.LORIEN_LAURELS_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.LORIEN_MALLORN_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.YELLOW_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.LORIEN_MALLORN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GALADHRIM_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelShieldPlate.getComponents(), edhelSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GALADHRIM_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GALADHRIM_LORD_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelShieldPlate.getComponents(), edhelSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GALADHRIM_LORD_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_BUCKLER_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.COPPER_INGOT)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, bronzeShieldBorder.getComponents(), bronzeShieldBorder.getItem()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_BUCKLER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_SCOUT_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_SCOUT_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_SCOUT_BRONZE_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_SCOUT_BRONZE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GALADHRIM_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.BROWN_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelShieldPlate.getComponents(), edhelSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_HEAVY_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_HEAVY_GREEN_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelShieldPlate.getComponents(), edhelSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_HEAVY_GREEN_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_HEAVY_BLUE_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.BLUE_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelShieldPlate.getComponents(), edhelSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_HEAVY_BLUE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_HEAVY_ORNAMENTED_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.BROWN_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelShieldPlate.getComponents(), edhelSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.GOLD_INGOT)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_HEAVY_ORNAMENTED_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_HEAVY_ORNAMENTED_GREEN_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelShieldPlate.getComponents(), edhelSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.GOLD_INGOT)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_HEAVY_ORNAMENTED_GREEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_GLADE_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelShieldPlate.getComponents(), edhelSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.GOLD_INGOT)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_GLADE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_NIGHTSHADE_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelShieldPlate.getComponents(), edhelSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_NIGHTSHADE_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_NIGHTSHADE_ORNAMENTED_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelShieldPlate.getComponents(), edhelSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.GOLD_INGOT)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_NIGHTSHADE_ORNAMENTED_SHIELD).getPath() + "_artisan");




                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, bronzeShieldBorder.getComponents(), bronzeShieldBorder.getItem()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.EREBOR_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_CROSS_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, bronzeShieldBorder.getComponents(), bronzeShieldBorder.getItem()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.EREBOR_CROSS_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_PLATED_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .componentInput(DataComponentIngredient.of(false, bronzeShieldBorder.getComponents(), bronzeShieldBorder.getItem()))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.EREBOR_PLATED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_ORNAMENTED_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .componentInput(DataComponentIngredient.of(false, bronzeShieldBorder.getComponents(), bronzeShieldBorder.getItem()))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.EREBOR_ORNAMENTED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_REINFORCED_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .componentInput(DataComponentIngredient.of(false, bronzeShieldBorder.getComponents(), bronzeShieldBorder.getItem()))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.EREBOR_REINFORCED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_BUCKLER_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelShieldPlate.getComponents(), khazadSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.EREBOR_BUCKLER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_TOWER_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelShieldPlate.getComponents(), khazadSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.EREBOR_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_REINFORCED_TOWER_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelShieldPlate.getComponents(), khazadSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.EREBOR_REINFORCED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_ORNAMENTED_TOWER_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelShieldPlate.getComponents(), khazadSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.EREBOR_ORNAMENTED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RAVENHILL_TOWER_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelShieldPlate.getComponents(), khazadSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.RAVENHILL_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RAVENHILL_REINFORCED_TOWER_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelShieldPlate.getComponents(), khazadSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.RAVENHILL_REINFORCED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RAVENHILL_ORNAMENTED_TOWER_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelShieldPlate.getComponents(), khazadSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.RAVENHILL_ORNAMENTED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_WOODEN_SHIELD.getDefaultInstance(), "light_shield", DispositionType.EVIL)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_WOODEN_SHIELD.getDefaultInstance(), "light_shield", DispositionType.EVIL)
                        .input(WeaponItemsME.MORDOR_WOODEN_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .unlockedBy(getHasName(WeaponItemsME.MORDOR_WOODEN_SHIELD),
                                has(WeaponItemsME.MORDOR_WOODEN_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_ROUND_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(DataComponentIngredient.of(false, crudeShieldBorder.getComponents(), crudeShieldBorder.getItem()))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(DataComponentIngredient.of(false, crudeShieldBorder.getComponents(), crudeShieldBorder.getItem()))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.MORDOR_BANNER_PATTERN),
                                has(ResourceItemsME.MORDOR_BANNER_PATTERN))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_BLACK_ROUND_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD)
                        .input(Items.BLACK_DYE)
                        .unlockedBy(getHasName(WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD),
                                has(WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_BLACK_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_BRACED_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(DataComponentIngredient.of(false, crudeShieldBorder.getComponents(), crudeShieldBorder.getItem()))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(WeaponItemsME.MORDOR_BRACED_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .unlockedBy(getHasName(WeaponItemsME.MORDOR_BRACED_SHIELD),
                                has(WeaponItemsME.MORDOR_BRACED_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_BLACK_BRACED_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD)
                        .input(Items.BLACK_DYE)
                        .unlockedBy(getHasName(WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD),
                                has(WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_BLACK_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_LARGE_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .componentInput(DataComponentIngredient.of(false, crudeShieldBorder.getComponents(), crudeShieldBorder.getItem()))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_LARGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(WeaponItemsME.MORDOR_LARGE_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .unlockedBy(getHasName(WeaponItemsME.MORDOR_LARGE_SHIELD),
                                has(WeaponItemsME.MORDOR_LARGE_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_BLACK_LARGE_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD)
                        .input(Items.BLACK_DYE)
                        .unlockedBy(getHasName(WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD),
                                has(WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_BLACK_LARGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DOL_GULDUR_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(DataComponentIngredient.of(false, crudeShieldBorder.getComponents(), crudeShieldBorder.getItem()))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DOL_GULDUR_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DOL_GULDUR_PAVISE.getDefaultInstance(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelShieldPlate.getComponents(), burzumSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DOL_GULDUR_PAVISE).getPath() + "_artisan");


                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_CONVERTED_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(WeaponItemsME.GONDORIAN_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .unlockedBy(getHasName(WeaponItemsME.GONDORIAN_SHIELD),
                                has(WeaponItemsME.GONDORIAN_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GONDORIAN_CONVERTED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_HEAVY_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelShieldPlate.getComponents(), burzumSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_HEAVY_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(WeaponItemsME.MORDOR_HEAVY_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .unlockedBy(getHasName(WeaponItemsME.MORDOR_HEAVY_SHIELD),
                                has(WeaponItemsME.MORDOR_HEAVY_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_PAINTED_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.BLACK_NUMENOREAN_TOWER_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.RED_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(DataComponentIngredient.of(false, ironShieldBorder.getComponents(), ironShieldBorder.getItem()))
                        .input(Items.BLACK_DYE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.BLACK_NUMENOREAN_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_HEATER_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .componentInput(DataComponentIngredient.of(false, crudeShieldBorder.getComponents(), crudeShieldBorder.getItem()))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.URUK_HAI_HEATER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelShieldPlate.getComponents(), burzumSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.URUK_HAI_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_WHITE_HAND_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(WeaponItemsME.URUK_HAI_SHIELD)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .unlockedBy(getHasName(WeaponItemsME.URUK_HAI_SHIELD),
                                has(WeaponItemsME.URUK_HAI_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.URUK_HAI_WHITE_HAND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_WHITE_PALMPRINT_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(WeaponItemsME.URUK_HAI_SHIELD)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .unlockedBy(getHasName(WeaponItemsME.URUK_HAI_SHIELD),
                                has(WeaponItemsME.URUK_HAI_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.URUK_HAI_WHITE_PALMPRINT_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_S_RUNE_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(WeaponItemsME.URUK_HAI_SHIELD)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .unlockedBy(getHasName(WeaponItemsME.URUK_HAI_SHIELD),
                                has(WeaponItemsME.URUK_HAI_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.URUK_HAI_S_RUNE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_SIEGE_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelShieldPlate.getComponents(), burzumSteelShieldPlate.getItem()))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelShieldPlate.getComponents(), burzumSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelShieldPlate.getComponents(), burzumSteelShieldPlate.getItem()))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.URUK_HAI_SIEGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_WOODEN_SHIELD.getDefaultInstance(), "light_shield", DispositionType.EVIL)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GUNDABAD_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_PAINTED_WOODEN_SHIELD.getDefaultInstance(), "light_shield", DispositionType.EVIL)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GUNDABAD_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_GREAT_EYE_PAINTED_WOODEN_SHIELD.getDefaultInstance(), "light_shield", DispositionType.EVIL)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GUNDABAD_GREAT_EYE_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_PEAKS_PAINTED_WOODEN_SHIELD.getDefaultInstance(), "light_shield", DispositionType.EVIL)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GUNDABAD_PEAKS_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_REINFORCED_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(DataComponentIngredient.of(false, crudeShieldBorder.getComponents(), crudeShieldBorder.getItem()))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GUNDABAD_REINFORCED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_HEAVY_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelShieldPlate.getComponents(), burzumSteelShieldPlate.getItem()))
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GUNDABAD_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORIA_GOBLINS_BUCKLER_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(DataComponentIngredient.of(false, crudeShieldBorder.getComponents(), crudeShieldBorder.getItem()))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORIA_GOBLINS_BUCKLER_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORIA_GOBLINS_HEAVY_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelShieldPlate.getComponents(), burzumSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORIA_GOBLINS_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RUINED_DWARVEN_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, crudeShieldBorder.getComponents(), crudeShieldBorder.getItem()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.RUINED_DWARVEN_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RUINED_DWARVEN_CROSS_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, crudeShieldBorder.getComponents(), crudeShieldBorder.getItem()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.RUINED_DWARVEN_CROSS_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RUINED_DWARVEN_ORNAMENTED_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, crudeShieldBorder.getComponents(), crudeShieldBorder.getItem()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.RUINED_DWARVEN_ORNAMENTED_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RUINED_DWARVEN_REINFORCED_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, crudeShieldBorder.getComponents(), crudeShieldBorder.getItem()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.RUINED_DWARVEN_REINFORCED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RUINED_DWARVEN_ORNAMENTED_TOWER_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(WeaponItemsME.RUINED_DWARVEN_ORNAMENTED_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.RUINED_DWARVEN_ORNAMENTED_TOWER_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RUINED_DWARVEN_REINFORCED_TOWER_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(WeaponItemsME.RUINED_DWARVEN_REINFORCED_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.RUINED_DWARVEN_REINFORCED_TOWER_SHIELD).getPath() + "_artisan");



                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GOBLIN_TOWN_BONE_SHIELD.getDefaultInstance(), "light_shield", DispositionType.EVIL)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GOBLIN_TOWN_BONE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GOBLIN_TOWN_WOODEN_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GOBLIN_TOWN_WOODEN_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GOBLIN_TOWN_BONE_WOODEN_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(Items.BONE)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.BONE)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(WeaponItemsME.GOBLIN_TOWN_WOODEN_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.BONE)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GOBLIN_TOWN_BONE_WOODEN_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GOBLIN_TOWN_LEATHER_WOODEN_SHIELD.getDefaultInstance(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(WeaponItemsME.GOBLIN_TOWN_WOODEN_SHIELD)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GOBLIN_TOWN_LEATHER_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GOBLIN_TOWN_HEAVY_SHIELD.getDefaultInstance(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelShieldPlate.getComponents(), burzumSteelShieldPlate.getItem()))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GOBLIN_TOWN_HEAVY_SHIELD).getPath() + "_artisan");
                //endregion

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ToolItemsME.PIPE.getDefaultInstance(), "pipe", DispositionType.NEUTRAL)
                        .input(Items.STICK)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("wooden_slabs")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("wooden_slabs")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(Items.STICK),
                                has(Items.STICK))
                        .save(output, BuiltInRegistries.ITEM.getKey(ToolItemsME.PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ToolItemsME.CLAY_PIPE.getDefaultInstance(), "pipe", DispositionType.NEUTRAL)
                        .input(Items.STICK)
                        .input(Items.TERRACOTTA)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("wooden_slabs")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("wooden_slabs")))
                        .input(Items.TERRACOTTA)
                        .input(Items.TERRACOTTA)
                        .unlockedBy(getHasName(Items.STICK),
                                has(Items.STICK))
                        .save(output, BuiltInRegistries.ITEM.getKey(ToolItemsME.CLAY_PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ToolItemsME.RIVERBEND_PIPE.getDefaultInstance(), "pipe", DispositionType.NEUTRAL)
                        .input(Items.STICK)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("wooden_slabs")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("wooden_slabs")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .unlockedBy(getHasName(Items.STICK),
                                has(Items.STICK))
                        .save(output, BuiltInRegistries.ITEM.getKey(ToolItemsME.RIVERBEND_PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ToolItemsME.BRIMMINGBEND_PIPE.getDefaultInstance(), "pipe", DispositionType.NEUTRAL)
                        .input(Items.STICK)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("wooden_slabs")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("wooden_slabs")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .unlockedBy(getHasName(Items.STICK),
                                has(Items.STICK))
                        .save(output, BuiltInRegistries.ITEM.getKey(ToolItemsME.BRIMMINGBEND_PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ToolItemsME.LONGBOTTOM_PIPE.getDefaultInstance(), "pipe", DispositionType.NEUTRAL)
                        .input(Items.STICK)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("wooden_slabs")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("wooden_slabs")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("planks")))
                        .unlockedBy(getHasName(Items.STICK),
                                has(Items.STICK))
                        .save(output, BuiltInRegistries.ITEM.getKey(ToolItemsME.LONGBOTTOM_PIPE).getPath() + "_artisan");

    }

    private int getXpFor(MetalTypes metalTypes) {
        return switch (metalTypes) {
            default -> 0;
            case EMPTY -> 0;
            case COPPER, TIN -> 2;
            case BRONZE, CRUDE -> 3;
            case IRON, SILVER, LEAD -> 4;
            case STEEL, EDHEL_STEEL, KHAZAD_STEEL, BURZUM_STEEL -> 5;
            case GOLD, NETHERITE -> 7;
            case MITHRIL -> 12;
        };
    }

    private void createToolSet(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, ItemStack outputPickaxe, ItemStack outputAxe, ItemStack outputShovel, ItemStack outputHoe, Optional<MetalTypes> rodMetal, DispositionType dispositionType) {
        createArtisanTablePickaxeRecipe(itemLookup, exporter, metal, outputPickaxe, rodMetal, dispositionType);
        createArtisanTableAxeRecipe(itemLookup, exporter, metal, outputAxe, false, rodMetal, dispositionType);
        createArtisanTableShovelRecipe(itemLookup, exporter, metal, outputShovel, rodMetal, dispositionType);
        createArtisanTableHoeRecipe(itemLookup, exporter, metal, outputHoe, rodMetal, dispositionType);
    }

    private void createArtisanTableSwordRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, ItemStack output, boolean noble, DispositionType dispositionType) {
        createArtisanTableSwordRecipe(itemLookup, exporter, metal, Items.STICK, output, noble, dispositionType);
    }

    private void createArtisanTableSwordRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, Item stick, ItemStack output, boolean noble, DispositionType dispositionType) {
        ItemStack blade = new ItemStack(ResourceItemsME.BLADE);
        blade.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        ItemStack swordHilt = new ItemStack(ResourceItemsME.SWORD_HILT);
        int xp = getXpFor(metal);

        if (!noble) {
            swordHilt.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    getMetalIdentifier(metal))), getPattern()));
        } else {
            swordHilt.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    ResourceLocation.parse(MetalTypes.GOLD.getName()))), getPattern()));
            xp++;
        }

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "sword", dispositionType, xp)
                .componentInput(DataComponentIngredient.of(false, blade.getComponents(), blade.getItem()))
                .componentInput(DataComponentIngredient.of(false, swordHilt.getComponents(), swordHilt.getItem()))
                .input(stick)
                .unlockedBy(getHasName(blade.getItem()),
                        conditionsFromItem(blade.getItem(), itemLookup))
                .save(exporter);
    }

    private void createArtisanTableLongswordRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, ItemStack output, boolean noble, DispositionType dispositionType) {
        createArtisanTableLongswordRecipe(itemLookup, exporter, metal, Items.STICK, output, noble, dispositionType);
    }

    private void createArtisanTableLongswordRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, Item stick, ItemStack output, boolean noble, DispositionType dispositionType) {
        ItemStack longBlade = new ItemStack(ResourceItemsME.LONG_BLADE);
        longBlade.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        ItemStack swordHilt = new ItemStack(ResourceItemsME.SWORD_HILT);
        int xp = (int) (getXpFor(metal) * 1.5f);

        if (!noble) {
            swordHilt.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    getMetalIdentifier(metal))), getPattern()));
        } else {
            swordHilt.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    ResourceLocation.parse(MetalTypes.GOLD.getName()))), getPattern()));
            xp++;
        }

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "sword", dispositionType, xp)
                .componentInput(DataComponentIngredient.of(false, longBlade.getComponents(), longBlade.getItem()))
                .componentInput(DataComponentIngredient.of(false, swordHilt.getComponents(), swordHilt.getItem()))
                .input(stick)
                .unlockedBy(getHasName(longBlade.getItem()),
                        conditionsFromItem(longBlade.getItem(), itemLookup))
                .save(exporter);
    }

    private void createArtisanTableDaggerRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, ItemStack output, boolean noble, DispositionType dispositionType) {
        createArtisanTableDaggerRecipe(itemLookup, exporter, metal, Items.STICK, output, noble, dispositionType);
    }

    private void createArtisanTableDaggerRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, Item stick, ItemStack output, boolean noble, DispositionType dispositionType) {
        ItemStack shortBlade = new ItemStack(ResourceItemsME.SHORT_BLADE);
        shortBlade.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        ItemStack swordHilt = new ItemStack(ResourceItemsME.SWORD_HILT);
        int xp = (int) (getXpFor(metal) * 0.5f);

        if (!noble) {
            swordHilt.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    getMetalIdentifier(metal))), getPattern()));
        } else {
            swordHilt.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    ResourceLocation.parse(MetalTypes.GOLD.getName()))), getPattern()));
            xp++;
        }

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "sword", dispositionType, xp)
                .componentInput(DataComponentIngredient.of(false, shortBlade.getComponents(), shortBlade.getItem()))
                .componentInput(DataComponentIngredient.of(false, swordHilt.getComponents(), swordHilt.getItem()))
                .input(stick)
                .unlockedBy(getHasName(shortBlade.getItem()),
                        conditionsFromItem(shortBlade.getItem(), itemLookup))
                .save(exporter);
    }

    private void createArtisanTableSpearRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, ItemStack output, boolean noble, DispositionType dispositionType) {
        createArtisanTableSpearRecipe(itemLookup, exporter, metal, Items.STICK, output, noble, dispositionType);
    }

    private void createArtisanTableSpearRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, Item stick, ItemStack output, boolean noble, DispositionType dispositionType) {
        ItemStack blade = new ItemStack(ResourceItemsME.SHORT_BLADE);
        blade.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));
        int xp = (int) (getXpFor(metal) * 0.5f);

        if (!noble) {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "spear", dispositionType, xp)
                    .componentInput(DataComponentIngredient.of(false, blade.getComponents(), blade.getItem()))
                    .input(stick)
                    .input(stick)
                    .unlockedBy(getHasName(blade.getItem()),
                            conditionsFromItem(blade.getItem(), itemLookup))
                    .save(exporter);
        } else {
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            xp++;
            rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    ResourceLocation.parse(MetalTypes.GOLD.getName()))), getPattern()));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "spear", dispositionType, xp)
                    .componentInput(DataComponentIngredient.of(false, blade.getComponents(), blade.getItem()))
                    .componentInput(DataComponentIngredient.of(false, rod.getComponents(), rod.getItem()))
                    .input(stick)
                    .unlockedBy(getHasName(blade.getItem()),
                            conditionsFromItem(blade.getItem(), itemLookup))
                    .save(exporter);
        }
    }

    private void createArtisanTableBowRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, ItemStack output, DispositionType dispositionType) {
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "bow", dispositionType)
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Items.STICK)
                .input(Items.STRING)
                .unlockedBy(getHasName(Items.STRING),
                        conditionsFromItem(Items.STRING, itemLookup))
                .save(exporter);
    }

    private void createArtisanTableNobleBowRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, ItemStack output, DispositionType dispositionType) {
        ItemStack rod = new ItemStack(ResourceItemsME.ROD);
        rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.parse(MetalTypes.GOLD.getName()))), getPattern()));
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "bow", dispositionType, XP_MEDIUM_SHIELD)
                .input(Items.STICK)
                .input(Items.STRING)
                .componentInput(DataComponentIngredient.of(false, rod.getComponents(), rod.getItem()))
                .input(Items.STRING)
                .input(Items.STICK)
                .input(Items.STRING)
                .unlockedBy(getHasName(Items.STRING),
                        conditionsFromItem(Items.STRING, itemLookup))
                .save(exporter);
    }

    private void createArtisanTableLongbowRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, ItemStack output, DispositionType dispositionType) {
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "bow", dispositionType)
                .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("wooden_fences")))
                .input(Items.STRING)
                .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("wooden_fences")))
                .input(Items.STRING)
                .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("wooden_fences")))
                .input(Items.STRING)
                .unlockedBy(getHasName(Items.STRING),
                        conditionsFromItem(Items.STRING, itemLookup))
                .save(exporter);
    }

    private void createArtisanTableNobleLongbowRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, ItemStack output, DispositionType dispositionType) {
        ItemStack rod = new ItemStack(ResourceItemsME.ROD);
        rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.parse(MetalTypes.GOLD.getName()))), getPattern()));
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "bow", dispositionType, 2)
                .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("wooden_fences")))
                .input(Items.STRING)
                .componentInput(DataComponentIngredient.of(false, rod.getComponents(), rod.getItem()))
                .input(Items.STRING)
                .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("wooden_fences")))
                .input(Items.STRING)
                .unlockedBy(getHasName(Items.STRING),
                        conditionsFromItem(Items.STRING, itemLookup))
                .save(exporter);
    }

    private void createArtisanTableCrossbowRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, ItemStack output, DispositionType dispositionType) {
        ItemStack rod = new ItemStack(ResourceItemsME.ROD);
        rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.parse(MetalTypes.IRON.getName()))), getPattern()));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "crossbow", dispositionType, 2)
                .input(Items.STICK)
                .componentInput(DataComponentIngredient.of(false, rod.getComponents(), rod.getItem()))
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Blocks.TRIPWIRE_HOOK)
                .input(Items.STRING)
                .input(Items.STICK)
                .unlockedBy(getHasName(Items.STRING),
                        conditionsFromItem(Items.STRING, itemLookup))
                .save(exporter);
    }

    private void createArtisanTableNobleCrossbowRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, ItemStack output, DispositionType dispositionType) {
        ItemStack rod = new ItemStack(ResourceItemsME.ROD);
        rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.parse(MetalTypes.GOLD.getName()))), getPattern()));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "crossbow", dispositionType, 2)
                .input(Items.STICK)
                .componentInput(DataComponentIngredient.of(false, rod.getComponents(), rod.getItem()))
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Blocks.TRIPWIRE_HOOK)
                .input(Items.STRING)
                .input(Items.STICK)
                .unlockedBy(getHasName(Items.STRING),
                        conditionsFromItem(Items.STRING, itemLookup))
                .save(exporter);
    }

    private void createArtisanTablePickaxeRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, ItemStack output, Optional<MetalTypes> rodMetal, DispositionType dispositionType) {
        ItemStack pickaxeHead = new ItemStack(ResourceItemsME.PICKAXE_HEAD);
        pickaxeHead.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));
        int xp = (int) (getXpFor(metal) * 1.5f);

        if (rodMetal.isPresent()){
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    getMetalIdentifier(rodMetal.get()))), getPattern()));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "pickaxe", dispositionType, xp)
                    .componentInput(DataComponentIngredient.of(false, pickaxeHead.getComponents(), pickaxeHead.getItem()))
                    .componentInput(DataComponentIngredient.of(false, rod.getComponents(), rod.getItem()))
                    .componentInput(DataComponentIngredient.of(false, rod.getComponents(), rod.getItem()))
                    .unlockedBy(getHasName(pickaxeHead.getItem()),
                            conditionsFromItem(pickaxeHead.getItem(), itemLookup))
                    .save(exporter, BuiltInRegistries.ITEM.getKey(output.getItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan");
        } else {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "pickaxe", dispositionType, xp)
                    .componentInput(DataComponentIngredient.of(false, pickaxeHead.getComponents(), pickaxeHead.getItem()))
                    .input(Items.STICK)
                    .input(Items.STICK)
                    .unlockedBy(getHasName(pickaxeHead.getItem()),
                            conditionsFromItem(pickaxeHead.getItem(), itemLookup))
                    .save(exporter, BuiltInRegistries.ITEM.getKey(output.getItem()).getPath() + "_artisan");
        }
    }

    private void createArtisanTableAxeRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, ItemStack output, boolean noble, Optional<MetalTypes> rodMetal, DispositionType dispositionType) {
        createArtisanTableAxeRecipe(itemLookup, exporter, metal, Items.STICK, output, noble, rodMetal, dispositionType);
    }

    private void createArtisanTableAxeRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, Item stick, ItemStack output, boolean noble, Optional<MetalTypes> rodMetal, DispositionType dispositionType) {
        ItemStack axeHead = new ItemStack(ResourceItemsME.AXE_HEAD);
        axeHead.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));
        int xp = (int) (getXpFor(metal) * 1.5f);

        if (!noble){
            if (rodMetal.isPresent()){
                ItemStack rod = new ItemStack(ResourceItemsME.ROD);
                rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                        getMetalIdentifier(rodMetal.get()))), getPattern()));
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "axe", dispositionType, xp)
                        .componentInput(DataComponentIngredient.of(false, axeHead.getComponents(), axeHead.getItem()))
                        .componentInput(DataComponentIngredient.of(false, rod.getComponents(), rod.getItem()))
                        .componentInput(DataComponentIngredient.of(false, rod.getComponents(), rod.getItem()))
                        .unlockedBy(getHasName(axeHead.getItem()),
                                conditionsFromItem(axeHead.getItem(), itemLookup))
                        .save(exporter, BuiltInRegistries.ITEM.getKey(output.getItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan");
            } else {
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "axe", dispositionType, xp)
                        .componentInput(DataComponentIngredient.of(false, axeHead.getComponents(), axeHead.getItem()))
                        .input(stick)
                        .input(stick)
                        .unlockedBy(getHasName(axeHead.getItem()),
                                conditionsFromItem(axeHead.getItem(), itemLookup))
                        .save(exporter);
            }
        } else {
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    ResourceLocation.parse(MetalTypes.GOLD.getName()))), getPattern()));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "axe", dispositionType, xp + 1)
                    .componentInput(DataComponentIngredient.of(false, axeHead.getComponents(), axeHead.getItem()))
                    .componentInput(DataComponentIngredient.of(false, rod.getComponents(), rod.getItem()))
                    .input(stick)
                    .unlockedBy(getHasName(axeHead.getItem()),
                            conditionsFromItem(axeHead.getItem(), itemLookup))
                    .save(exporter);
        }
    }

    private void createArtisanTableShovelRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, ItemStack output, Optional<MetalTypes> rodMetal, DispositionType dispositionType) {
        ItemStack shovelHead = new ItemStack(ResourceItemsME.SHOVEL_HEAD);
        shovelHead.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));
        int xp = (int) (getXpFor(metal) * 0.5f);

        if (rodMetal.isPresent()){
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    getMetalIdentifier(rodMetal.get()))), getPattern()));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "shovel", dispositionType, xp)
                    .componentInput(DataComponentIngredient.of(false, shovelHead.getComponents(), shovelHead.getItem()))
                    .componentInput(DataComponentIngredient.of(false, rod.getComponents(), rod.getItem()))
                    .componentInput(DataComponentIngredient.of(false, rod.getComponents(), rod.getItem()))
                    .unlockedBy(getHasName(shovelHead.getItem()),
                            conditionsFromItem(shovelHead.getItem(), itemLookup))
                    .save(exporter, BuiltInRegistries.ITEM.getKey(output.getItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan");
        } else {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "shovel", dispositionType, xp)
                    .componentInput(DataComponentIngredient.of(false, shovelHead.getComponents(), shovelHead.getItem()))
                    .input(Items.STICK)
                    .input(Items.STICK)
                    .unlockedBy(getHasName(shovelHead.getItem()),
                            conditionsFromItem(shovelHead.getItem(), itemLookup))
                    .save(exporter);
        }
    }

    private void createArtisanTableHoeRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, ItemStack output, Optional<MetalTypes> rodMetal, DispositionType dispositionType) {
        ItemStack hoeHead = new ItemStack(ResourceItemsME.HOE_HEAD);
        hoeHead.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));
        int xp = getXpFor(metal);

        if (rodMetal.isPresent()){
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    getMetalIdentifier(rodMetal.get()))), getPattern()));
            
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "hoe", dispositionType, xp)
                    .componentInput(DataComponentIngredient.of(false, hoeHead.getComponents(), hoeHead.getItem()))
                    .componentInput(DataComponentIngredient.of(false, rod.getComponents(), rod.getItem()))
                    .componentInput(DataComponentIngredient.of(false, rod.getComponents(), rod.getItem()))
                    .unlockedBy(getHasName(hoeHead.getItem()),
                            conditionsFromItem(hoeHead.getItem(), itemLookup))
                    .save(exporter, BuiltInRegistries.ITEM.getKey(output.getItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan");
        } else {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "hoe", dispositionType, xp)
                    .componentInput(DataComponentIngredient.of(false, hoeHead.getComponents(), hoeHead.getItem()))
                    .input(Items.STICK)
                    .input(Items.STICK)
                    .unlockedBy(getHasName(hoeHead.getItem()),
                            conditionsFromItem(hoeHead.getItem(), itemLookup))
                    .save(exporter);
        }
    }

    private void createArtisanTableChiselRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, Item nugget, ItemStack output) {
        ItemStack shortBlade = new ItemStack(ResourceItemsME.SHORT_BLADE);
        shortBlade.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
            getMetalIdentifier(metal))), getPattern()));
        int xp = (int) (getXpFor(metal) * 0.5f);

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "chisel", DispositionType.NEUTRAL, xp)
                .componentInput(DataComponentIngredient.of(false, shortBlade.getComponents(), shortBlade.getItem()))
                .input(nugget)
                .input(Items.STICK)
                .unlockedBy(getHasName(shortBlade.getItem()),
                        conditionsFromItem(shortBlade.getItem(), itemLookup))
                    .save(exporter, BuiltInRegistries.ITEM.getKey(output.getItem()).getPath() + "_" + metal.getName() + "_artisan");
    }

    public Criterion<InventoryChangeTrigger.TriggerInstance> conditionsFromItem(ItemLike item, HolderGetter<Item> itemLookup) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(item));
    }
}
