package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.impl.recipe.ingredient.builtin.ComponentsIngredient;
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
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;

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

    @Override
    public void generate(RecipeExporter exporter) {
        RegistryWrapper.Impl<ArmorTrimMaterial> armorTrimMaterialsRegistry;

        try {
            armorTrimMaterialsRegistry = registryLookup.get().getWrapperOrThrow(RegistryKeys.TRIM_MATERIAL);
        } catch (Exception ignored) {
            throw new IllegalStateException("Data generation without registries failed!");
        }

        RegistryWrapper.Impl<ArmorTrimPattern> armorTrimPatternsRegistry;

        try {
            armorTrimPatternsRegistry = registryLookup.get().getWrapperOrThrow(RegistryKeys.TRIM_PATTERN);
        } catch (Exception ignored) {
            throw new IllegalStateException("Data generation without registries failed!");
        }

        RegistryEntry<ArmorTrimPattern> pattern = armorTrimPatternsRegistry
                .getOrThrow(ModSmithingTrimPatterns.SMITHING_PART);

        ArmorTrim bronzeTrim = new ArmorTrim(armorTrimMaterialsRegistry.getOrThrow(ModSmithingTrimMaterials.BRONZE), pattern);
        ArmorTrim ironTrim = new ArmorTrim(armorTrimMaterialsRegistry.getOrThrow(ArmorTrimMaterials.IRON), pattern);
        ArmorTrim goldTrim = new ArmorTrim(armorTrimMaterialsRegistry.getOrThrow(ArmorTrimMaterials.GOLD), pattern);

        ArmorTrim steelTrim = new ArmorTrim(armorTrimMaterialsRegistry.getOrThrow(ModSmithingTrimMaterials.STEEL), pattern);
        ArmorTrim khazadSteelTrim = new ArmorTrim(armorTrimMaterialsRegistry.getOrThrow(ModSmithingTrimMaterials.KHAZAD_STEEL), pattern);
        ArmorTrim edhelSteelTrim = new ArmorTrim(armorTrimMaterialsRegistry.getOrThrow(ModSmithingTrimMaterials.EDHEL_STEEL), pattern);
        ArmorTrim burzumSteelTrim = new ArmorTrim(armorTrimMaterialsRegistry.getOrThrow(ModSmithingTrimMaterials.BURZUM_STEEL), pattern);
        
        ArmorTrim mithrilTrim = new ArmorTrim(armorTrimMaterialsRegistry.getOrThrow(ModSmithingTrimMaterials.MITHRIL), pattern);

        ItemStack ironBlade = new ItemStack(ModResourceItems.BLADE);
        ironBlade.set(DataComponentTypes.TRIM, ironTrim);
        ItemStack ironShortBlade = new ItemStack(ModResourceItems.SHORT_BLADE);
        ironShortBlade.set(DataComponentTypes.TRIM, ironTrim);
        ItemStack ironLongBlade = new ItemStack(ModResourceItems.LONG_BLADE);
        ironLongBlade.set(DataComponentTypes.TRIM, ironTrim);
        ItemStack ironAxeHead = new ItemStack(ModResourceItems.AXE_HEAD);
        ironAxeHead.set(DataComponentTypes.TRIM, ironTrim);

        ItemStack ironPickaxeHead = new ItemStack(ModResourceItems.PICKAXE_HEAD);
        ironPickaxeHead.set(DataComponentTypes.TRIM, ironTrim);
        ItemStack ironShovelHead = new ItemStack(ModResourceItems.SHOVEL_HEAD);
        ironShovelHead.set(DataComponentTypes.TRIM, ironTrim);
        ItemStack ironHoeHead = new ItemStack(ModResourceItems.HOE_HEAD);
        ironHoeHead.set(DataComponentTypes.TRIM, ironTrim);

        ItemStack ironSwordHilt = new ItemStack(ModResourceItems.SWORD_HILT);
        ironSwordHilt.set(DataComponentTypes.TRIM, ironTrim);

        ItemStack goldBlade = new ItemStack(ModResourceItems.BLADE);
        goldBlade.set(DataComponentTypes.TRIM, goldTrim);
        ItemStack goldShortBlade = new ItemStack(ModResourceItems.SHORT_BLADE);
        goldShortBlade.set(DataComponentTypes.TRIM, goldTrim);
        ItemStack goldLongBlade = new ItemStack(ModResourceItems.LONG_BLADE);
        goldLongBlade.set(DataComponentTypes.TRIM, goldTrim);
        ItemStack goldAxeHead = new ItemStack(ModResourceItems.AXE_HEAD);
        goldAxeHead.set(DataComponentTypes.TRIM, goldTrim);

        ItemStack goldPickaxeHead = new ItemStack(ModResourceItems.PICKAXE_HEAD);
        goldPickaxeHead.set(DataComponentTypes.TRIM, goldTrim);
        ItemStack goldShovelHead = new ItemStack(ModResourceItems.SHOVEL_HEAD);
        goldShovelHead.set(DataComponentTypes.TRIM, goldTrim);
        ItemStack goldHoeHead = new ItemStack(ModResourceItems.HOE_HEAD);
        goldHoeHead.set(DataComponentTypes.TRIM, goldTrim);

        ItemStack goldSwordHilt = new ItemStack(ModResourceItems.SWORD_HILT);
        goldSwordHilt.set(DataComponentTypes.TRIM, goldTrim);

        ItemStack goldRod = new ItemStack(ModResourceItems.ROD);
        goldRod.set(DataComponentTypes.TRIM, goldTrim);

        ItemStack steelBlade = new ItemStack(ModResourceItems.BLADE);
        steelBlade.set(DataComponentTypes.TRIM, steelTrim);
        ItemStack steelShortBlade = new ItemStack(ModResourceItems.SHORT_BLADE);
        steelShortBlade.set(DataComponentTypes.TRIM, steelTrim);
        ItemStack steelLongBlade = new ItemStack(ModResourceItems.LONG_BLADE);
        steelLongBlade.set(DataComponentTypes.TRIM, steelTrim);
        ItemStack steelAxeHead = new ItemStack(ModResourceItems.AXE_HEAD);
        steelAxeHead.set(DataComponentTypes.TRIM, steelTrim);

        ItemStack steelPickaxeHead = new ItemStack(ModResourceItems.PICKAXE_HEAD);
        steelPickaxeHead.set(DataComponentTypes.TRIM, steelTrim);
        ItemStack steelShovelHead = new ItemStack(ModResourceItems.SHOVEL_HEAD);
        steelShovelHead.set(DataComponentTypes.TRIM, steelTrim);
        ItemStack steelHoeHead = new ItemStack(ModResourceItems.HOE_HEAD);
        steelHoeHead.set(DataComponentTypes.TRIM, steelTrim);

        ItemStack steelSwordHilt = new ItemStack(ModResourceItems.SWORD_HILT);
        steelSwordHilt.set(DataComponentTypes.TRIM, steelTrim);

        ItemStack steelArmorPlate = new ItemStack(ModResourceItems.ARMOR_PLATE);
        steelArmorPlate.set(DataComponentTypes.TRIM, steelTrim);
        ItemStack steelHelmetPlate = new ItemStack(ModResourceItems.HELMET_PLATE);
        steelHelmetPlate.set(DataComponentTypes.TRIM, steelTrim);

        ItemStack khazadSteelBlade = new ItemStack(ModResourceItems.BLADE);
        khazadSteelBlade.set(DataComponentTypes.TRIM, khazadSteelTrim);
        ItemStack khazadSteelShortBlade = new ItemStack(ModResourceItems.SHORT_BLADE);
        khazadSteelShortBlade.set(DataComponentTypes.TRIM, khazadSteelTrim);
        ItemStack khazadSteelLongBlade = new ItemStack(ModResourceItems.LONG_BLADE);
        khazadSteelLongBlade.set(DataComponentTypes.TRIM, khazadSteelTrim);
        ItemStack khazadSteelAxeHead = new ItemStack(ModResourceItems.AXE_HEAD);
        khazadSteelAxeHead.set(DataComponentTypes.TRIM, khazadSteelTrim);

        ItemStack khazadSteelPickaxeHead = new ItemStack(ModResourceItems.PICKAXE_HEAD);
        khazadSteelPickaxeHead.set(DataComponentTypes.TRIM, khazadSteelTrim);
        ItemStack khazadSteelShovelHead = new ItemStack(ModResourceItems.SHOVEL_HEAD);
        khazadSteelShovelHead.set(DataComponentTypes.TRIM, khazadSteelTrim);
        ItemStack khazadSteelHoeHead = new ItemStack(ModResourceItems.HOE_HEAD);
        khazadSteelHoeHead.set(DataComponentTypes.TRIM, khazadSteelTrim);

        ItemStack khazadSteelSwordHilt = new ItemStack(ModResourceItems.SWORD_HILT);
        khazadSteelSwordHilt.set(DataComponentTypes.TRIM, khazadSteelTrim);

        ItemStack edhelSteelBlade = new ItemStack(ModResourceItems.BLADE);
        edhelSteelBlade.set(DataComponentTypes.TRIM, edhelSteelTrim);
        ItemStack edhelSteelShortBlade = new ItemStack(ModResourceItems.SHORT_BLADE);
        edhelSteelShortBlade.set(DataComponentTypes.TRIM, edhelSteelTrim);
        ItemStack edhelSteelLongBlade = new ItemStack(ModResourceItems.LONG_BLADE);
        edhelSteelLongBlade.set(DataComponentTypes.TRIM, edhelSteelTrim);
        ItemStack edhelSteelAxeHead = new ItemStack(ModResourceItems.AXE_HEAD);
        edhelSteelAxeHead.set(DataComponentTypes.TRIM, edhelSteelTrim);

        ItemStack edhelSteelPickaxeHead = new ItemStack(ModResourceItems.PICKAXE_HEAD);
        edhelSteelPickaxeHead.set(DataComponentTypes.TRIM, edhelSteelTrim);
        ItemStack edhelSteelShovelHead = new ItemStack(ModResourceItems.SHOVEL_HEAD);
        edhelSteelShovelHead.set(DataComponentTypes.TRIM, edhelSteelTrim);
        ItemStack edhelSteelHoeHead = new ItemStack(ModResourceItems.HOE_HEAD);
        edhelSteelHoeHead.set(DataComponentTypes.TRIM, edhelSteelTrim);

        ItemStack edhelSteelSwordHilt = new ItemStack(ModResourceItems.SWORD_HILT);
        edhelSteelSwordHilt.set(DataComponentTypes.TRIM, edhelSteelTrim);

        ItemStack burzumSteelBlade = new ItemStack(ModResourceItems.BLADE);
        burzumSteelBlade.set(DataComponentTypes.TRIM, burzumSteelTrim);
        ItemStack burzumSteelShortBlade = new ItemStack(ModResourceItems.SHORT_BLADE);
        burzumSteelShortBlade.set(DataComponentTypes.TRIM, burzumSteelTrim);
        ItemStack burzumSteelLongBlade = new ItemStack(ModResourceItems.LONG_BLADE);
        burzumSteelLongBlade.set(DataComponentTypes.TRIM, burzumSteelTrim);
        ItemStack burzumSteelAxeHead = new ItemStack(ModResourceItems.AXE_HEAD);
        burzumSteelAxeHead.set(DataComponentTypes.TRIM, burzumSteelTrim);

        ItemStack burzumSteelPickaxeHead = new ItemStack(ModResourceItems.PICKAXE_HEAD);
        burzumSteelPickaxeHead.set(DataComponentTypes.TRIM, burzumSteelTrim);
        ItemStack burzumSteelShovelHead = new ItemStack(ModResourceItems.SHOVEL_HEAD);
        burzumSteelShovelHead.set(DataComponentTypes.TRIM, burzumSteelTrim);
        ItemStack burzumSteelHoeHead = new ItemStack(ModResourceItems.HOE_HEAD);
        burzumSteelHoeHead.set(DataComponentTypes.TRIM, burzumSteelTrim);

        ItemStack burzumSteelSwordHilt = new ItemStack(ModResourceItems.SWORD_HILT);
        burzumSteelSwordHilt.set(DataComponentTypes.TRIM, burzumSteelTrim);

        ItemStack mithrilPickaxeHead = new ItemStack(ModResourceItems.PICKAXE_HEAD);
        mithrilPickaxeHead.set(DataComponentTypes.TRIM, mithrilTrim);
        ItemStack mithrilAxeHead = new ItemStack(ModResourceItems.AXE_HEAD);
        mithrilAxeHead.set(DataComponentTypes.TRIM, mithrilTrim);
        ItemStack mithrilShovelHead = new ItemStack(ModResourceItems.SHOVEL_HEAD);
        mithrilShovelHead.set(DataComponentTypes.TRIM, mithrilTrim);
        ItemStack mithrilHoeHead = new ItemStack(ModResourceItems.HOE_HEAD);
        mithrilHoeHead.set(DataComponentTypes.TRIM, mithrilTrim);

        ItemStack stick = new ItemStack(Items.STICK);

        createArtisanTableSwordRecipe(exporter, List.of(ironBlade, ironSwordHilt, stick), Items.IRON_SWORD.getDefaultStack());
        createArtisanTablePickaxeRecipe(exporter, List.of(ironPickaxeHead, stick, stick), Items.IRON_PICKAXE.getDefaultStack());
        createArtisanTableAxeRecipe(exporter, List.of(ironAxeHead, stick, stick), Items.IRON_AXE.getDefaultStack());
        createArtisanTableShovelRecipe(exporter, List.of(ironShovelHead, stick, stick), Items.IRON_SHOVEL.getDefaultStack());
        createArtisanTableHoeRecipe(exporter, List.of(ironHoeHead, stick, stick), Items.IRON_HOE.getDefaultStack());

        createArtisanTableSpearRecipe(exporter, List.of(ironBlade, stick, stick), ModWeaponItems.IRON_SPEAR.getDefaultStack());

        createArtisanTableSwordRecipe(exporter, List.of(goldBlade, goldSwordHilt, stick), Items.GOLDEN_SWORD.getDefaultStack());
        createArtisanTablePickaxeRecipe(exporter, List.of(goldPickaxeHead, stick, stick), Items.GOLDEN_PICKAXE.getDefaultStack());
        createArtisanTableAxeRecipe(exporter, List.of(goldAxeHead, stick, stick), Items.GOLDEN_AXE.getDefaultStack());
        createArtisanTableShovelRecipe(exporter, List.of(goldShovelHead, stick, stick), Items.GOLDEN_SHOVEL.getDefaultStack());
        createArtisanTableHoeRecipe(exporter, List.of(goldHoeHead, stick, stick), Items.GOLDEN_HOE.getDefaultStack());

        createArtisanTableSpearRecipe(exporter, List.of(goldBlade, stick, stick), ModWeaponItems.GOLDEN_SPEAR.getDefaultStack());

        createArtisanTableSwordRecipe(exporter, List.of(steelBlade, steelSwordHilt, stick), ModWeaponItems.GONDORIAN_SWORD.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(steelBlade, steelSwordHilt, stick), ModWeaponItems.ROHIRRIC_SWORD.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(steelBlade, steelSwordHilt, stick), ModWeaponItems.DALISH_SWORD.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(steelBlade, steelSwordHilt, stick), ModWeaponItems.STEEL_SWORD.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(steelBlade, steelSwordHilt, stick), ModWeaponItems.BLACK_NUMENOREAN_SWORD.getDefaultStack());

        createArtisanTableSwordRecipe(exporter, List.of(steelShortBlade, steelSwordHilt, stick), ModWeaponItems.GONDORIAN_DAGGER.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(steelShortBlade, steelSwordHilt, stick), ModWeaponItems.ROHIRRIC_DAGGER.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(steelShortBlade, steelSwordHilt, stick), ModWeaponItems.DALISH_DAGGER.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(steelShortBlade, steelSwordHilt, stick), ModWeaponItems.BLACK_NUMENOREAN_DAGGER.getDefaultStack());

        createArtisanTableSwordRecipe(exporter, List.of(steelLongBlade, steelSwordHilt, stick), ModWeaponItems.GONDORIAN_LONGSWORD.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(steelLongBlade, steelSwordHilt, stick), ModWeaponItems.ROHIRRIC_LONGSWORD.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(steelLongBlade, steelSwordHilt, stick), ModWeaponItems.DALISH_LONGSWORD.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(steelLongBlade, steelSwordHilt, stick), ModWeaponItems.BLACK_NUMENOREAN_LONGSWORD.getDefaultStack());

        createArtisanTableAxeRecipe(exporter, List.of(steelAxeHead, stick, stick), ModWeaponItems.GONDORIAN_AXE.getDefaultStack());
        createArtisanTableAxeRecipe(exporter, List.of(steelAxeHead, stick, stick), ModWeaponItems.ROHIRRIC_AXE.getDefaultStack());
        createArtisanTableAxeRecipe(exporter, List.of(steelAxeHead, stick, stick), ModWeaponItems.DALISH_AXE.getDefaultStack());
        createArtisanTableAxeRecipe(exporter, List.of(steelAxeHead, stick, stick), ModToolItems.STEEL_AXE.getDefaultStack());
        createArtisanTableAxeRecipe(exporter, List.of(steelAxeHead, stick, stick), ModWeaponItems.BLACK_NUMENOREAN_AXE.getDefaultStack());

        createArtisanTableSpearRecipe(exporter, List.of(steelShortBlade, stick, stick), ModWeaponItems.GONDORIAN_SPEAR.getDefaultStack());
        createArtisanTableSpearRecipe(exporter, List.of(steelShortBlade, stick, stick), ModWeaponItems.ROHIRRIC_SPEAR.getDefaultStack());
        createArtisanTableSpearRecipe(exporter, List.of(steelShortBlade, stick, stick), ModWeaponItems.DALISH_SPEAR.getDefaultStack());
        createArtisanTableSpearRecipe(exporter, List.of(steelShortBlade, stick, stick), ModWeaponItems.STEEL_SPEAR.getDefaultStack());


        createArtisanTableNobleAxeRecipe(exporter, List.of(steelAxeHead, goldRod, stick), ModWeaponItems.GONDORIAN_NOBLE_AXE.getDefaultStack());
        createArtisanTableNobleAxeRecipe(exporter, List.of(steelAxeHead, goldRod, stick), ModWeaponItems.ROHIRRIC_NOBLE_AXE.getDefaultStack());
        createArtisanTableNobleAxeRecipe(exporter, List.of(steelAxeHead, goldRod, stick), ModWeaponItems.DALISH_NOBLE_AXE.getDefaultStack());

        createArtisanTableNobleSpearRecipe(exporter, List.of(steelShortBlade, goldRod, stick), ModWeaponItems.GONDORIAN_NOBLE_SPEAR.getDefaultStack());
        createArtisanTableNobleSpearRecipe(exporter, List.of(steelShortBlade, goldRod, stick), ModWeaponItems.ROHIRRIC_NOBLE_SPEAR.getDefaultStack());
        createArtisanTableNobleSpearRecipe(exporter, List.of(steelShortBlade, goldRod, stick), ModWeaponItems.DALISH_NOBLE_SPEAR.getDefaultStack());

        createArtisanTableSwordRecipe(exporter, List.of(steelBlade, goldSwordHilt, stick), ModWeaponItems.GONDORIAN_NOBLE_SWORD.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(steelBlade, goldSwordHilt, stick), ModWeaponItems.ROHIRRIC_NOBLE_SWORD.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(steelBlade, goldSwordHilt, stick), ModWeaponItems.DALISH_NOBLE_SWORD.getDefaultStack());

        createArtisanTableSwordRecipe(exporter, List.of(steelShortBlade, goldSwordHilt, stick), ModWeaponItems.GONDORIAN_NOBLE_DAGGER.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(steelShortBlade, goldSwordHilt, stick), ModWeaponItems.ROHIRRIC_NOBLE_DAGGER.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(steelShortBlade, goldSwordHilt, stick), ModWeaponItems.DALISH_NOBLE_DAGGER.getDefaultStack());

        createArtisanTableSwordRecipe(exporter, List.of(steelLongBlade, goldSwordHilt, stick), ModWeaponItems.GONDORIAN_NOBLE_LONGSWORD.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(steelLongBlade, goldSwordHilt, stick), ModWeaponItems.ROHIRRIC_NOBLE_LONGSWORD.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(steelLongBlade, goldSwordHilt, stick), ModWeaponItems.DALISH_NOBLE_LONGSWORD.getDefaultStack());

        createArtisanTableSwordRecipe(exporter, List.of(khazadSteelBlade, khazadSteelSwordHilt, stick), ModWeaponItems.EREBOR_SWORD.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(khazadSteelBlade, goldSwordHilt, stick), ModWeaponItems.EREBOR_NOBLE_SWORD.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(khazadSteelBlade, khazadSteelSwordHilt, stick), ModWeaponItems.KHAZAD_STEEL_SWORD.getDefaultStack());

        createArtisanTableSwordRecipe(exporter, List.of(edhelSteelBlade, edhelSteelSwordHilt, stick), ModWeaponItems.LORIEN_SWORD.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(edhelSteelBlade, goldSwordHilt, stick), ModWeaponItems.LORIEN_NOBLE_SWORD.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(edhelSteelBlade, edhelSteelSwordHilt, stick), ModWeaponItems.EDHEL_STEEL_SWORD.getDefaultStack());

        createArtisanTableSwordRecipe(exporter, List.of(burzumSteelBlade, burzumSteelSwordHilt, stick), ModWeaponItems.ORC_SWORD.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(burzumSteelBlade, burzumSteelSwordHilt, stick), ModWeaponItems.MORDOR_FALCHION.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(burzumSteelBlade, goldSwordHilt, stick), ModWeaponItems.MORDOR_ELITE_CLEAVER.getDefaultStack());
        createArtisanTableSwordRecipe(exporter, List.of(burzumSteelBlade, burzumSteelSwordHilt, stick), ModWeaponItems.BURZUM_STEEL_SWORD.getDefaultStack());

        createArtisanTablePickaxeRecipe(exporter, List.of(steelPickaxeHead, stick, stick), ModToolItems.STEEL_PICKAXE.getDefaultStack());
        createArtisanTableShovelRecipe(exporter, List.of(steelShovelHead, stick, stick), ModToolItems.STEEL_SHOVEL.getDefaultStack());
        createArtisanTableHoeRecipe(exporter, List.of(steelHoeHead, stick, stick), ModToolItems.STEEL_HOE.getDefaultStack());

        createArtisanTablePickaxeRecipe(exporter, List.of(khazadSteelPickaxeHead, stick, stick), ModToolItems.KHAZAD_STEEL_PICKAXE.getDefaultStack());
        createArtisanTableShovelRecipe(exporter, List.of(khazadSteelShovelHead, stick, stick), ModToolItems.KHAZAD_STEEL_AXE.getDefaultStack());
        createArtisanTableHoeRecipe(exporter, List.of(khazadSteelHoeHead, stick, stick), ModToolItems.KHAZAD_STEEL_HOE.getDefaultStack());

        createArtisanTablePickaxeRecipe(exporter, List.of(edhelSteelPickaxeHead, stick, stick), ModToolItems.EDHEL_STEEL_PICKAXE.getDefaultStack());
        createArtisanTableShovelRecipe(exporter, List.of(edhelSteelShovelHead, stick, stick), ModToolItems.EDHEL_STEEL_SHOVEL.getDefaultStack());
        createArtisanTableHoeRecipe(exporter, List.of(edhelSteelHoeHead, stick, stick), ModToolItems.EDHEL_STEEL_HOE.getDefaultStack());

        createArtisanTablePickaxeRecipe(exporter, List.of(burzumSteelPickaxeHead, stick, stick), ModToolItems.BURZUM_STEEL_PICKAXE.getDefaultStack());
        createArtisanTableShovelRecipe(exporter, List.of(burzumSteelShovelHead, stick, stick), ModToolItems.BURZUM_STEEL_SHOVEL.getDefaultStack());
        createArtisanTableHoeRecipe(exporter, List.of(burzumSteelHoeHead, stick, stick), ModToolItems.BURZUM_STEEL_HOE.getDefaultStack());

        createArtisanTablePickaxeRecipe(exporter, List.of(mithrilPickaxeHead, stick, stick), ModToolItems.MITHRIL_PICKAXE.getDefaultStack());
        createArtisanTableAxeRecipe(exporter, List.of(mithrilAxeHead, stick, stick), ModToolItems.MITHRIL_AXE.getDefaultStack());
        createArtisanTableShovelRecipe(exporter, List.of(mithrilShovelHead, stick, stick), ModToolItems.MITHRIL_SHOVEL.getDefaultStack());
        createArtisanTableHoeRecipe(exporter, List.of(mithrilHoeHead, stick, stick), ModToolItems.MITHRIL_HOE.getDefaultStack());

        createArtisanTableHelmetRecipe(exporter, List.of(steelArmorPlate, steelHelmetPlate, steelArmorPlate,
                steelArmorPlate, steelArmorPlate), ModEquipmentItems.GONDORIAN_PLATE_HELMET.getDefaultStack());

    }

    private void createArtisanTableSwordRecipe(RecipeExporter exporter, List<ItemStack> inputs, ItemStack output) {
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "sword")
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(inputs.get(0).getItem()), inputs.get(0).getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(inputs.get(1).getItem()), inputs.get(1).getComponentChanges()))
                .input(Ingredient.ofItems(inputs.get(2).getItem()))
                .criterion(FabricRecipeProvider.hasItem(inputs.get(0).getItem()),
                        FabricRecipeProvider.conditionsFromItem(inputs.get(0).getItem()))
                .offerTo(exporter);
    }

    private void createArtisanTableAxeRecipe(RecipeExporter exporter, List<ItemStack> inputs, ItemStack output) {
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "axe")
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(inputs.get(0).getItem()), inputs.get(0).getComponentChanges()))
                .input(Ingredient.ofItems(inputs.get(1).getItem()))
                .input(Ingredient.ofItems(inputs.get(2).getItem()))
                .criterion(FabricRecipeProvider.hasItem(inputs.get(0).getItem()),
                        FabricRecipeProvider.conditionsFromItem(inputs.get(0).getItem()))
                .offerTo(exporter);
    }

    private void createArtisanTableNobleAxeRecipe(RecipeExporter exporter, List<ItemStack> inputs, ItemStack output) {
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "axe")
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(inputs.get(0).getItem()), inputs.get(0).getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(inputs.get(1).getItem()), inputs.get(1).getComponentChanges()))
                .input(Ingredient.ofItems(inputs.get(2).getItem()))
                .criterion(FabricRecipeProvider.hasItem(inputs.get(0).getItem()),
                        FabricRecipeProvider.conditionsFromItem(inputs.get(0).getItem()))
                .offerTo(exporter);
    }

    private void createArtisanTableSpearRecipe(RecipeExporter exporter, List<ItemStack> inputs, ItemStack output) {
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "spear")
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(inputs.get(0).getItem()), inputs.get(0).getComponentChanges()))
                .input(Ingredient.ofItems(inputs.get(1).getItem()))
                .input(Ingredient.ofItems(inputs.get(2).getItem()))
                .criterion(FabricRecipeProvider.hasItem(inputs.get(0).getItem()),
                        FabricRecipeProvider.conditionsFromItem(inputs.get(0).getItem()))
                .offerTo(exporter);
    }

    private void createArtisanTableNobleSpearRecipe(RecipeExporter exporter, List<ItemStack> inputs, ItemStack output) {
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "spear")
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(inputs.get(0).getItem()), inputs.get(0).getComponentChanges()))
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(inputs.get(1).getItem()), inputs.get(1).getComponentChanges()))
                .input(Ingredient.ofItems(inputs.get(2).getItem()))
                .criterion(FabricRecipeProvider.hasItem(inputs.get(0).getItem()),
                        FabricRecipeProvider.conditionsFromItem(inputs.get(0).getItem()))
                .offerTo(exporter);
    }

    private void createArtisanTablePickaxeRecipe(RecipeExporter exporter, List<ItemStack> inputs, ItemStack output) {
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "pickaxe")
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(inputs.get(0).getItem()), inputs.get(0).getComponentChanges()))
                .input(Ingredient.ofItems(inputs.get(1).getItem()))
                .input(Ingredient.ofItems(inputs.get(2).getItem()))
                .criterion(FabricRecipeProvider.hasItem(inputs.get(0).getItem()),
                        FabricRecipeProvider.conditionsFromItem(inputs.get(0).getItem()))
                .offerTo(exporter);
    }

    private void createArtisanTableShovelRecipe(RecipeExporter exporter, List<ItemStack> inputs, ItemStack output) {
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "shovel")
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(inputs.get(0).getItem()), inputs.get(0).getComponentChanges()))
                .input(Ingredient.ofItems(inputs.get(1).getItem()))
                .input(Ingredient.ofItems(inputs.get(2).getItem()))
                .criterion(FabricRecipeProvider.hasItem(inputs.get(0).getItem()),
                        FabricRecipeProvider.conditionsFromItem(inputs.get(0).getItem()))
                .offerTo(exporter);
    }

    private void createArtisanTableHoeRecipe(RecipeExporter exporter, List<ItemStack> inputs, ItemStack output) {
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "hoe")
                .componentInput(new ComponentsIngredient(Ingredient.ofItems(inputs.get(0).getItem()), inputs.get(0).getComponentChanges()))
                .input(Ingredient.ofItems(inputs.get(1).getItem()))
                .input(Ingredient.ofItems(inputs.get(2).getItem()))
                .criterion(FabricRecipeProvider.hasItem(inputs.get(0).getItem()),
                        FabricRecipeProvider.conditionsFromItem(inputs.get(0).getItem()))
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