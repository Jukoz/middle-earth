package net.sevenstars.middleearth.datageneration.providers.recipes;

import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
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
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.forge.MetalTypes;
import net.sevenstars.middleearth.datageneration.content.tags.ArmorTags;
import net.sevenstars.middleearth.datageneration.custom.ArtisanTableRecipeJsonBuilder;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.utils.SmithingTrimPatternsME;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;

import java.util.concurrent.CompletableFuture;

public class ArtisanTableArmorRecipeProvider extends RecipeProvider {
    private static final int XP_T2 = 1;
    private static final int XP_T3 = 4;
    private static final int XP_T4 = 9;
    private static final int XP_T5 = 12;
    private static final int XP_STEEL_ITEM = 3;
    private static final int XP_NOBLE_ITEM = 5;

    private final CompletableFuture<HolderLookup.Provider> registryLookup;

    public ArtisanTableArmorRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);

        this.registryLookup = registriesFuture;
    }

    public HolderLookup.RegistryLookup<TrimMaterial> getArmorTrimMaterialsRegistry(){
        HolderLookup.RegistryLookup<TrimMaterial> armorTrimMaterialsRegistry;

        try {
            armorTrimMaterialsRegistry = this.registryLookup.get().lookupOrThrow(Registries.TRIM_MATERIAL);
        } catch (Exception ignored) {
            throw new IllegalStateException("Data generation without registries failed!");
        }
        return armorTrimMaterialsRegistry;
    }

    public HolderLookup.RegistryLookup<TrimPattern> getArmorTrimPatternsRegistry(){
        HolderLookup.RegistryLookup<TrimPattern> armorTrimPatternsRegistry;

        try {
            armorTrimPatternsRegistry = this.registryLookup.get().lookupOrThrow(Registries.TRIM_PATTERN);
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
        ItemStack goldArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        goldArmorPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.parse(MetalTypes.GOLD.getName()))), getPattern()));

        ItemStack silverArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        silverArmorPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.SILVER.getName()))), getPattern()));

        ItemStack steelArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        steelArmorPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName()))), getPattern()));
        ItemStack steelHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
        steelHelmetPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName()))), getPattern()));
        ItemStack steelChainmail = new ItemStack(ResourceItemsME.MAIL);
        steelChainmail.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName()))), getPattern()));
        ItemStack steelScaleMail = new ItemStack(ResourceItemsME.SCALE_MAIL);
        steelScaleMail.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName()))), getPattern()));

        ItemStack edhelSteelArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        edhelSteelArmorPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName()))), getPattern()));
        ItemStack edhelSteelHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
        edhelSteelHelmetPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName()))), getPattern()));
        ItemStack edhelSteelChainmail = new ItemStack(ResourceItemsME.MAIL);
        edhelSteelChainmail.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName()))), getPattern()));
        ItemStack edhelSteelScaleMail = new ItemStack(ResourceItemsME.SCALE_MAIL);
        edhelSteelScaleMail.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName()))), getPattern()));

        ItemStack khazadSteelArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        khazadSteelArmorPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName()))), getPattern()));
        ItemStack khazadSteelHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
        khazadSteelHelmetPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName()))), getPattern()));
        ItemStack khazadSteelChainmail = new ItemStack(ResourceItemsME.MAIL);
        khazadSteelChainmail.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName()))), getPattern()));
        ItemStack khazadSteelScaleMail = new ItemStack(ResourceItemsME.SCALE_MAIL);
        khazadSteelScaleMail.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName()))), getPattern()));

        ItemStack burzumSteelArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        burzumSteelArmorPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName()))), getPattern()));
        ItemStack burzumSteelHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
        burzumSteelHelmetPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName()))), getPattern()));
        ItemStack burzumSteelChainmail = new ItemStack(ResourceItemsME.MAIL);
        burzumSteelChainmail.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName()))), getPattern()));
        ItemStack burzumSteelScaleMail = new ItemStack(ResourceItemsME.SCALE_MAIL);
        burzumSteelScaleMail.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName()))), getPattern()));

        ItemStack ironArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        ironArmorPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.parse(MetalTypes.IRON.getName()))), getPattern()));
        ItemStack ironHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
        ironHelmetPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.parse(MetalTypes.IRON.getName()))), getPattern()));
        ItemStack ironChainmail = new ItemStack(ResourceItemsME.MAIL);
        ironChainmail.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.parse(MetalTypes.IRON.getName()))), getPattern()));

        ItemStack bronzeArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        bronzeArmorPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.BRONZE.getName()))), getPattern()));
        ItemStack crudeArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        crudeArmorPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.CRUDE.getName()))), getPattern()));

        HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

                //region MEN

                //region GONDOR
                //T2
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_BOOTS.getDefaultInstance(), "boots", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                //T3
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CABASSET_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironHelmetPlate.getComponents(), ironHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_LEATHER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK),
                                has(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_LEATHER_CUIRASS.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK),
                                has(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_TABBARD.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK),
                                has(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_MAIL_COAT.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                //T4
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_SOLDIER_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelHelmetPlate.getComponents(), steelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(steelHelmetPlate.getItem()),
                                has(steelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_SOLDIER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK),
                                has(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK))
                        .save(output);

                //T5 PLATE
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .input(EquipmentItemsME.GONDORIAN_SOLDIER_CHESTPLATE)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.GONDORIAN_SOLDIER_CHESTPLATE),
                                has(EquipmentItemsME.GONDORIAN_SOLDIER_CHESTPLATE))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .input(EquipmentItemsME.GONDORIAN_MAIL_COAT)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.GONDORIAN_MAIL_COAT),
                                has(EquipmentItemsME.GONDORIAN_MAIL_COAT))
                        .save(output);

                //T5 CAPTAIN
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CAPTAIN_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .input(EquipmentItemsME.GONDORIAN_PLATE_HELMET)
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.GONDORIAN_PLATE_HELMET),
                                has(EquipmentItemsME.GONDORIAN_PLATE_HELMET))
                        .save(output);

                //T5 KING'S GUARD
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_KINGS_GUARD_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T5)
                        .input(Items.FEATHER)
                        .input(EquipmentItemsME.GONDORIAN_PLATE_HELMET)
                        .input(Items.FEATHER)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.GONDORIAN_PLATE_HELMET),
                                has(EquipmentItemsME.GONDORIAN_PLATE_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_KINGS_GUARD_CHESTKPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE),
                                has(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_KINGS_GUARD_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .input(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS),
                                has(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS))
                        .save(output);

                //T5 CITADEL GUARD
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CITADEL_GUARD_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .input(EquipmentItemsME.GONDORIAN_PLATE_HELMET)
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.GONDORIAN_PLATE_HELMET),
                                has(EquipmentItemsME.GONDORIAN_PLATE_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CITADEL_GUARD_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T5)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE),
                                has(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CITADEL_GUARD_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .input(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS),
                                has(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CITADEL_GUARD_BOOTS.getDefaultInstance(), "boots", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .unlockedBy(getHasName(goldArmorPlate.getItem()),
                                has(goldArmorPlate.getItem()))
                        .save(output);

                //T5 FOUNTAIN GUARD
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T5)
                        .input(ResourceItemsME.MITHRIL_NUGGET)
                        .input(EquipmentItemsME.GONDORIAN_CAPTAIN_HELMET)
                        .input(ResourceItemsME.MITHRIL_NUGGET)
                        .input(ResourceItemsME.SWAN_FEATHER)
                        .input(ResourceItemsME.SWAN_FEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.GONDORIAN_CAPTAIN_HELMET),
                                has(EquipmentItemsME.GONDORIAN_CAPTAIN_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T5)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE),
                                has(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T5)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS),
                                has(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS))
                        .save(output);

                //HOODS
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CITADEL_GUARD_HOOD.getDefaultInstance(), "helmet_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                //CAPES
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CAPTAIN_CAPE.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.IRON_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(Items.WHITE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.WHITE_DYE)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_HERO_CAPE.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.IRON_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.WHITE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.WHITE_DYE)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_KINGS_GUARD_CAPE.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(Items.WHITE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.WHITE_DYE)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CITADEL_GUARD_CAPE.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.YELLOW_DYE)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_CAPE.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);
                //endregion

                //region ROHAN
                //T1
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_REINFORCED_COAT.getDefaultInstance(), "chestplate", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.ARMING_COAT)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.ARMING_COAT),
                                has(EquipmentItemsME.ARMING_COAT))
                        .save(output);

                //T2
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_LEATHER_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_REINFORCED_LEATHER_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.IRON_NUGGET)
                        .input(Items.IRON_NUGGET)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_ORNAMENTED_LEATHER_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_LEATHER_VEST.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.ARMING_COAT)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.ARMING_COAT),
                                has(EquipmentItemsME.ARMING_COAT))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.ARMING_COAT)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.ARMING_COAT),
                                has(EquipmentItemsME.ARMING_COAT))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_GAMBESON.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GAMBESON)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.GAMBESON),
                                has(EquipmentItemsME.GAMBESON))
                        .save(output);

                //T3
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_MILITIA_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .unlockedBy(getHasName(bronzeArmorPlate.getItem()),
                                has(bronzeArmorPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_BRACED_MILITIA_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .unlockedBy(getHasName(ironArmorPlate.getItem()),
                                has(ironArmorPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_REINFORCED_MILITIA_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .unlockedBy(getHasName(bronzeArmorPlate.getItem()),
                                has(bronzeArmorPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_ORNAMENTED_MILITIA_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .unlockedBy(getHasName(bronzeArmorPlate.getItem()),
                                has(bronzeArmorPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_REINFORCED_LEATHER_VEST.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.MAIL_HAUBERK),
                                has(EquipmentItemsME.MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_REINFORCED_LEATHER_SCALE_VEST.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.MAIL_HAUBERK),
                                has(EquipmentItemsME.MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_BRACED_MAIL_SHIRT.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.MAIL_HAUBERK),
                                has(EquipmentItemsME.MAIL_HAUBERK))
                        .save(output);

                ///T4
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_SOLDIER_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelHelmetPlate.getComponents(), steelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(steelHelmetPlate.getItem()),
                                has(steelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_ORNAMENTED_SOLDIER_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelHelmetPlate.getComponents(), steelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .unlockedBy(getHasName(steelHelmetPlate.getItem()),
                                has(steelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_ROYAL_GUARD_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelHelmetPlate.getComponents(), steelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .unlockedBy(getHasName(steelHelmetPlate.getItem()),
                                has(steelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.MAIL_HAUBERK),
                                has(EquipmentItemsME.MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_ORNAMENTED_SCALE_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GREEN_DYE)
                        .unlockedBy(getHasName(EquipmentItemsME.MAIL_HAUBERK),
                                has(EquipmentItemsME.MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_SCALE_JACKET.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.MAIL_HAUBERK),
                                has(EquipmentItemsME.MAIL_HAUBERK))
                        .save(output);

                //T5 EORLING MARSHAL
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EORLING_MARSHAL_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .input(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK),
                                has(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EORLING_MARSHAL_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .input(EquipmentItemsME.ROHIRRIC_SCALE_JACKET)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ROHIRRIC_SCALE_JACKET),
                                has(EquipmentItemsME.ROHIRRIC_SCALE_JACKET))
                        .save(output);

                //T5 HORSE LORD
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.HORSE_LORD_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T5)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .input(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK)
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK),
                                has(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.HORSE_LORD_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .input(EquipmentItemsME.ROHIRRIC_SCALE_JACKET)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ROHIRRIC_SCALE_JACKET),
                                has(EquipmentItemsME.ROHIRRIC_SCALE_JACKET))
                        .save(output);
                //CAPES
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_CAPE.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_ROYAL_GUARD_CAPE.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.YELLOW_DYE)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GREEN_DYE)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EORLING_MARSHAL_CAPE.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GREEN_DYE)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.WHITE_DYE)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.HORSE_LORD_CAPE.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLUE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLUE_DYE)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);
                //endregion

                //region DALE

                //T1
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_ARMING_COAT_BLACK_FUR.getDefaultInstance(), "chestplate", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_ARMING_COAT_BROWN_FUR.getDefaultInstance(), "chestplate", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_ARMING_COAT_TAN_FUR.getDefaultInstance(), "chestplate", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HEYDAY_ARMING_COAT.getDefaultInstance(), "chestplate", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.ORANGE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.ORANGE_DYE)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                //T2
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_BOOTS.getDefaultInstance(), "boots", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                //T3
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironHelmetPlate.getComponents(), ironHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HELMET_BLACK_FUR.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.DALISH_HELMET)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(EquipmentItemsME.DALISH_HELMET),
                                has(EquipmentItemsME.DALISH_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HELMET_BROWN_FUR.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.DALISH_HELMET)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(EquipmentItemsME.DALISH_HELMET),
                                has(EquipmentItemsME.DALISH_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HELMET_TAN_FUR.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.DALISH_HELMET)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(EquipmentItemsME.DALISH_HELMET),
                                has(EquipmentItemsME.DALISH_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.MAIL_HAUBERK),
                                has(EquipmentItemsME.MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_MAIL_COAT.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .input(EquipmentItemsME.MAIL_SKIRT)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.MAIL_HAUBERK),
                                has(EquipmentItemsME.MAIL_HAUBERK))
                        .save(output);

                //T4
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_BURGONET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelHelmetPlate.getComponents(), steelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(steelHelmetPlate.getItem()),
                                has(steelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_SCALE_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);

                //T4 HEYDAY
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HEYDAY_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .input(EquipmentItemsME.DALISH_HELMET)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(EquipmentItemsME.DALISH_HELMET),
                                has(EquipmentItemsME.DALISH_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HEYDAY_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T4)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.DALISH_SCALE_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.ORANGE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.ORANGE_DYE)
                        .unlockedBy(getHasName(EquipmentItemsME.DALISH_SCALE_HAUBERK),
                                has(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HEYDAY_MAIL_COAT.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T4)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.DALISH_MAIL_COAT)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.DALISH_MAIL_COAT),
                                has(EquipmentItemsME.DALISH_MAIL_COAT))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HEYDAY_BOOTS.getDefaultInstance(), "boots", DispositionType.GOOD, XP_T4)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .unlockedBy(getHasName(steelArmorPlate.getItem()),
                                has(steelArmorPlate.getItem()))
                        .save(output);

                //T5 BARDING
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BARDING_SOLDIER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .input(EquipmentItemsME.DALISH_SCALE_HAUBERK)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.DALISH_SCALE_HAUBERK),
                                has(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BARDING_MAIL_SKIRT.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .input(EquipmentItemsME.DALISH_MAIL_COAT)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.DALISH_MAIL_COAT),
                                has(EquipmentItemsME.DALISH_MAIL_COAT))
                        .save(output);

                //T5 BARDING SERGEANT
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BARDING_SERGEANT_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T5)
                        .input(Items.FEATHER)
                        .input(EquipmentItemsME.BARDING_SOLDIER_HELMET)
                        .input(Items.FEATHER)
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.BARDING_SOLDIER_HELMET),
                                has(EquipmentItemsME.BARDING_SOLDIER_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BARDING_SERGEANT_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.BARDING_SOLDIER_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.BARDING_SERGEANT_CHESTPLATE),
                                has(EquipmentItemsME.BARDING_SERGEANT_CHESTPLATE))
                        .save(output);

                //CAPES
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BARDING_SURCOAT.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.PURPLE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BARDING_SERGEANT_CAPE.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.ORANGE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.ORANGE_DYE)
                        .input(Items.PURPLE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.PURPLE_DYE)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HEYDAY_CLOAK.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.STRING)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);
                //endregion

                //endregion

                //region ELVES

                //region LOTHLORIEN
                //T1
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_DIADEM.getDefaultInstance(), "helmet", DispositionType.GOOD)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .unlockedBy(getHasName(bronzeArmorPlate.getItem()),
                                has(bronzeArmorPlate.getItem()))
                        .save(output);

                //T2
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_LEATHER_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_ARMING_COAT.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.ELVEN_ARMING_COAT)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.ELVEN_ARMING_COAT),
                                has(EquipmentItemsME.ELVEN_ARMING_COAT))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_ARMING_SKIRT.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.ELVEN_ARMING_SKIRT)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.ELVEN_ARMING_SKIRT),
                                has(EquipmentItemsME.ELVEN_ARMING_SKIRT))
                        .save(output);

                //T3
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_MAIL_COIF_DIADEM.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(EquipmentItemsME.ELVEN_MAIL_COIF)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .input(EquipmentItemsME.LORIEN_DIADEM)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ELVEN_MAIL_COIF),
                                has(EquipmentItemsME.ELVEN_MAIL_COIF))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_SHORT_MAIL_COIF_DIADEM.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(EquipmentItemsME.ELVEN_MAIL_COIF)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .input(EquipmentItemsME.LORIEN_DIADEM)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ELVEN_MAIL_COIF),
                                has(EquipmentItemsME.ELVEN_MAIL_COIF))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.ELVEN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ELVEN_MAIL_HAUBERK),
                                has(EquipmentItemsME.ELVEN_MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_MARCHWARDEN_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.ELVEN_MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.ELVEN_MAIL_HAUBERK),
                                has(EquipmentItemsME.ELVEN_MAIL_HAUBERK))
                        .save(output);

                //T4
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_SOLDIER_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelHelmetPlate.getComponents(), edhelSteelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .unlockedBy(getHasName(edhelSteelHelmetPlate.getItem()),
                                has(edhelSteelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_SOLDIER_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.LORIEN_MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.LORIEN_MAIL_HAUBERK),
                                has(EquipmentItemsME.LORIEN_MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T4)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .unlockedBy(getHasName(edhelSteelScaleMail.getItem()),
                                has(edhelSteelScaleMail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_SCALE_COAT.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .unlockedBy(getHasName(edhelSteelScaleMail.getItem()),
                                has(edhelSteelScaleMail.getItem()))
                        .save(output);

                //T5 GALADHRIM
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .input(EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK),
                                has(EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.LORIEN_SCALE_COAT)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.LORIEN_SCALE_COAT),
                                has(EquipmentItemsME.LORIEN_SCALE_COAT))
                        .save(output);

                //T5 EGLADIL & GALADHRIM LORD
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EGLADIL_SENTINEL_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, silverArmorPlate.getComponents(), silverArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, silverArmorPlate.getComponents(), silverArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GALADHRIM_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .input(ResourceItemsME.SILVER_INGOT)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.GALADHRIM_CHESTPLATE),
                                has(EquipmentItemsME.GALADHRIM_CHESTPLATE))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EGLADIL_SENTINEL_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T5)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GALADHRIM_LEGGINGS)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.GALADHRIM_LEGGINGS),
                                has(EquipmentItemsME.GALADHRIM_LEGGINGS))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_LORD_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GALADHRIM_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.GALADHRIM_CHESTPLATE),
                                has(EquipmentItemsME.GALADHRIM_CHESTPLATE))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_LORD_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T5)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GALADHRIM_LEGGINGS)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.GALADHRIM_LEGGINGS),
                                has(EquipmentItemsME.GALADHRIM_LEGGINGS))
                        .save(output);

                //HOODS
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_MARCHWARDEN_HOOD.getDefaultInstance(), "helmet_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GRAY_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_HOOD.getDefaultInstance(), "helmet_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLUE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                //CAPES
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_MARCHWARDEN_CAPE.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GRAY_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GRAY_DYE)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_CAPE.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLUE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLUE_DYE)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EGLADIL_SENTINEL_CAPE.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(Items.STRING)
                        .input(Items.GOLD_NUGGET)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLUE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLUE_DYE)
                        .unlockedBy(getHasName(Items.GOLD_NUGGET),
                                has(Items.GOLD_NUGGET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_LORD_SURCOAT.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.WHITE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_LORD_CLOAK.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(Items.STRING)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .unlockedBy(getHasName(ResourceItemsME.SILVER_NUGGET),
                                has(ResourceItemsME.SILVER_NUGGET))
                        .save(output);
                //endregion

                //region WOODLAND REALM
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_ARMING_COAT.getDefaultInstance(), "chestplate", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_LEATHER_ARMING_COAT.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT)
                        .input(Items.LEATHER)
                        .input(Items.STRING)
                        .input(Items.LEATHER)
                        .input(Items.STRING)
                        .unlockedBy(getHasName(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT),
                                has(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_LEATHER_BRONZED_ARMING_COAT.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT),
                                has(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_LEATHER_SILVER_ARMING_COAT.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT),
                                has(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_BRONZE_TRIMMED_RANGER_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.BRONZE_NUGGET),
                                has(ResourceItemsME.BRONZE_NUGGET))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_SILVER_TRIMMED_RANGER_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.SILVER_NUGGET),
                                has(ResourceItemsME.SILVER_NUGGET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_CAVALRY_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelHelmetPlate.getComponents(), edhelSteelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .unlockedBy(getHasName(edhelSteelHelmetPlate.getItem()),
                                has(edhelSteelHelmetPlate.getItem()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.BRONZED_ELVEN_MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.LORIEN_MAIL_HAUBERK),
                                has(EquipmentItemsME.LORIEN_MAIL_HAUBERK))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_SKIRT.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .unlockedBy(getHasName(edhelSteelScaleMail.getItem()),
                                has(edhelSteelScaleMail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_ROYAL_GUARD_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelHelmetPlate.getComponents(), edhelSteelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .unlockedBy(getHasName(edhelSteelHelmetPlate.getItem()),
                                has(edhelSteelHelmetPlate.getItem()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_SCALE_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.BRONZED_ELVEN_MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.LORIEN_MAIL_HAUBERK),
                                has(EquipmentItemsME.LORIEN_MAIL_HAUBERK))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_SCALE_SKIRT.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .unlockedBy(getHasName(edhelSteelScaleMail.getItem()),
                                has(edhelSteelScaleMail.getItem()))
                        .save(output);

                //T5 WLR
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_SOLDIER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .input(EquipmentItemsME.WOODLAND_REALM_SCALE_HAUBERK)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.WOODLAND_REALM_SCALE_HAUBERK),
                                has(EquipmentItemsME.WOODLAND_REALM_SCALE_HAUBERK))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_SOLDIER_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.WOODLAND_REALM_SCALE_SKIRT)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.WOODLAND_REALM_SCALE_SKIRT),
                                has(EquipmentItemsME.WOODLAND_REALM_SCALE_SKIRT))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WARDEN_OF_THE_GLADE_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .input(EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_HAUBERK)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_HAUBERK),
                                has(EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_HAUBERK))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WARDEN_OF_THE_GLADE_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_SKIRT)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_SKIRT),
                                has(EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_SKIRT))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WARRIOR_OF_THE_NIGHTSHADE_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .input(EquipmentItemsME.WOODLAND_REALM_SCALE_HAUBERK)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.WOODLAND_REALM_SCALE_HAUBERK),
                                has(EquipmentItemsME.WOODLAND_REALM_SCALE_HAUBERK))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WARRIOR_OF_THE_NIGHTSHADE_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.WOODLAND_REALM_SCALE_SKIRT)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelScaleMail.getComponents(), edhelSteelScaleMail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.WOODLAND_REALM_SCALE_SKIRT),
                                has(EquipmentItemsME.WOODLAND_REALM_SCALE_SKIRT))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_COMMANDER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.WOODLAND_REALM_SOLDIER_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, silverArmorPlate.getComponents(), silverArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.WOODLAND_REALM_SOLDIER_CHESTPLATE),
                                has(EquipmentItemsME.WOODLAND_REALM_SOLDIER_CHESTPLATE))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_COMMANDER_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T5)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.WOODLAND_REALM_SOLDIER_LEGGINGS)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.SILVER_INGOT)
                        .input(ResourceItemsME.SILVER_INGOT)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.WOODLAND_REALM_SOLDIER_LEGGINGS),
                                has(EquipmentItemsME.WOODLAND_REALM_SOLDIER_LEGGINGS))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_ROYAL_GUARD_CAPE.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_SOLDIER_CAPE.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WARDEN_OF_THE_GLADE_CAPE.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BROWN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WARRIOR_OF_NIGHTSHADE_CAPE.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);
                //endregion

                //endregion

                //region DWARVES

                //region EREBOR
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LONGBEARD_SEGMENTED_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T2)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(bronzeArmorPlate.getItem()),
                                has(bronzeArmorPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LONGBEARD_LEATHER_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LONGBEARD_WANDERER_COAT.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.IRON_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.IRON_NUGGET)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LONGBEARD_LEATHER_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LONGBEARD_PARTISAN_OUTFIT.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.DWARVEN_MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(Items.IRON_NUGGET)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.DWARVEN_MAIL_HAUBERK),
                                has(EquipmentItemsME.DWARVEN_MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LONGBEARD_REINFORCED_LEATHER_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.DWARVEN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.DWARVEN_MAIL_HAUBERK),
                                has(EquipmentItemsME.DWARVEN_MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_LEATHER_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.IRON_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.IRON_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_BRACED_LEATHER_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.IRON_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.IRON_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_NASAL_LEATHER_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.IRON_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.IRON_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_LEATHER_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_MAIL_COIF.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_GILDED_MAIL_COIF.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(EquipmentItemsME.EREBOR_MAIL_COIF)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .unlockedBy(getHasName(EquipmentItemsME.EREBOR_MAIL_COIF),
                                has(EquipmentItemsME.EREBOR_MAIL_COIF))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironHelmetPlate.getComponents(), ironHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .input(Items.IRON_NUGGET)
                        .input(Items.IRON_NUGGET)
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_PADDED_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.EREBOR_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.EREBOR_MAIL_HAUBERK),
                                has(EquipmentItemsME.EREBOR_MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_MAIL_COAT.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_MAIL_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_MAIL_CHAUSSES.getDefaultInstance(), "boots", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_BOOTS.getDefaultInstance(), "boots", DispositionType.GOOD, XP_T5)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.IRON_INGOT)
                        .input(Items.IRON_INGOT)
                        .unlockedBy(getHasName(Items.IRON_INGOT),
                                has(Items.IRON_INGOT))
                        .save(output);

                //t4
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelHelmetPlate.getComponents(), khazadSteelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelChainmail.getComponents(), khazadSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelChainmail.getComponents(), khazadSteelChainmail.getItem()))
                        .unlockedBy(getHasName(khazadSteelHelmetPlate.getItem()),
                                has(khazadSteelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_GILDED_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T4)
                        .input(EquipmentItemsME.EREBOR_GILDED_MAIL_COIF)
                        .input(EquipmentItemsME.RAVENHILL_HELMET)
                        .unlockedBy(getHasName(EquipmentItemsME.RAVENHILL_HELMET),
                                has(EquipmentItemsME.RAVENHILL_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_SCALE_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .unlockedBy(getHasName(khazadSteelScaleMail.getItem()),
                                has(khazadSteelScaleMail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_LONG_COAT.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelChainmail.getComponents(), khazadSteelChainmail.getItem()))
                        .input(EquipmentItemsME.EREBOR_MAIL_HAUBERK)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelChainmail.getComponents(), khazadSteelChainmail.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.EREBOR_MAIL_HAUBERK),
                                has(EquipmentItemsME.EREBOR_MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.EREBOR_MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(Items.BLUE_DYE)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.EREBOR_MAIL_HAUBERK),
                                has(EquipmentItemsME.EREBOR_MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_REINFORCED_LEATHER_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.EREBOR_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.EREBOR_MAIL_HAUBERK),
                                has(EquipmentItemsME.EREBOR_MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_REINFORCED_COAT.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.EREBOR_MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(Items.BLUE_DYE)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.EREBOR_MAIL_HAUBERK),
                                has(EquipmentItemsME.EREBOR_MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_REINFORCED_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelChainmail.getComponents(), khazadSteelChainmail.getItem()))
                        .input(EquipmentItemsME.EREBOR_MAIL_HAUBERK)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelChainmail.getComponents(), khazadSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelChainmail.getComponents(), khazadSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.EREBOR_MAIL_HAUBERK),
                                has(EquipmentItemsME.EREBOR_MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_SCALE_COAT.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .unlockedBy(getHasName(khazadSteelScaleMail.getItem()),
                                has(khazadSteelScaleMail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_SCALE_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .unlockedBy(getHasName(khazadSteelScaleMail.getItem()),
                                has(khazadSteelScaleMail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_BOOTS.getDefaultInstance(), "boots", DispositionType.GOOD, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(khazadSteelArmorPlate.getItem()),
                                has(khazadSteelArmorPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_PLATE_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .input(EquipmentItemsME.EREBOR_SCALE_HAUBERK)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.EREBOR_SCALE_HAUBERK),
                                has(EquipmentItemsME.EREBOR_SCALE_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_PLATE_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.EREBOR_SCALE_LEGGINGS)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelScaleMail.getComponents(), khazadSteelScaleMail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.EREBOR_SCALE_LEGGINGS),
                                has(EquipmentItemsME.EREBOR_SCALE_LEGGINGS))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_GUARD_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .input(EquipmentItemsME.EREBOR_PLATE_HELMET)
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.EREBOR_PLATE_HELMET),
                                has(EquipmentItemsME.EREBOR_PLATE_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_GATEWARDEN_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .input(EquipmentItemsME.EREBOR_PLATE_HELMET)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .unlockedBy(getHasName(EquipmentItemsME.EREBOR_PLATE_HELMET),
                                has(EquipmentItemsME.EREBOR_PLATE_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_GATEWARDEN_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(Items.BLUE_DYE)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .unlockedBy(getHasName(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE),
                                has(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_GATEWARDEN_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .input(EquipmentItemsME.EREBOR_PLATE_LEGGINGS)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.EREBOR_PLATE_LEGGINGS),
                                has(EquipmentItemsME.EREBOR_PLATE_LEGGINGS))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_WATCHWARDEN_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .input(EquipmentItemsME.EREBOR_PLATE_HELMET)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelChainmail.getComponents(), khazadSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelChainmail.getComponents(), khazadSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.EREBOR_PLATE_HELMET),
                                has(EquipmentItemsME.EREBOR_PLATE_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_WATCHWARDEN_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(Items.GRAY_DYE)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .unlockedBy(getHasName(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE),
                                has(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_WATCHWARDEN_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .input(EquipmentItemsME.EREBOR_PLATE_LEGGINGS)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.EREBOR_PLATE_LEGGINGS),
                                has(EquipmentItemsME.EREBOR_PLATE_LEGGINGS))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_CAPTAIN_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T5)
                        .input(Items.GOAT_HORN)
                        .input(EquipmentItemsME.EREBOR_GUARD_HELMET)
                        .input(Items.GOAT_HORN)
                        .unlockedBy(getHasName(EquipmentItemsME.EREBOR_GUARD_HELMET),
                                has(EquipmentItemsME.EREBOR_GUARD_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_SENTINEL_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T5)
                        .input(Items.FEATHER)
                        .input(EquipmentItemsME.RAVENHILL_WATCHWARDEN_HELMET)
                        .input(Items.FEATHER)
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.RAVENHILL_WATCHWARDEN_HELMET),
                                has(EquipmentItemsME.RAVENHILL_WATCHWARDEN_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_SENTINEL_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, goldArmorPlate.getComponents(), goldArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.RAVENHILL_WATCHWARDEN_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, bronzeArmorPlate.getComponents(), bronzeArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.RAVENHILL_WATCHWARDEN_CHESTPLATE),
                                has(EquipmentItemsME.RAVENHILL_WATCHWARDEN_CHESTPLATE))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_SENTINEL_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.RAVENHILL_WATCHWARDEN_LEGGINGS)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.RAVENHILL_WATCHWARDEN_LEGGINGS),
                                has(EquipmentItemsME.RAVENHILL_WATCHWARDEN_LEGGINGS))
                        .save(output);
                //capes
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_CAPE.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLUE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLUE_DYE)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_SENTINEL_CAPE.getDefaultInstance(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GRAY_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GRAY_DYE)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.YELLOW_DYE)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                //endregion

                //endregion

                //region ORCS

                //region MORDOR
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironHelmetPlate.getComponents(), ironHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironHelmetPlate.getComponents(), ironHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_KETTLE_HAT.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironHelmetPlate.getComponents(), ironHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironHelmetPlate.getComponents(), ironHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_NASAL_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironHelmetPlate.getComponents(), ironHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_NASAL_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironHelmetPlate.getComponents(), ironHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT_WITH_COIF.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .input(EquipmentItemsME.ORCISH_MAIL_COIF)
                        .input(EquipmentItemsME.MORDOR_KETTLE_HAT)
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_MAIL_COIF),
                                has(EquipmentItemsME.ORCISH_MAIL_COIF))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_KETTLE_HAT_WITH_COIF.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .input(EquipmentItemsME.ORCISH_MAIL_COIF)
                        .input(EquipmentItemsME.MORDOR_KETTLE_HAT)
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_MAIL_COIF),
                                has(EquipmentItemsME.ORCISH_MAIL_COIF))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_LEATHER_CUIRASS.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_PAINTED_LEATHER_CUIRASS.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(EquipmentItemsME.MORDOR_LEATHER_CUIRASS)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.MORDOR_LEATHER_CUIRASS),
                                has(EquipmentItemsME.MORDOR_LEATHER_CUIRASS))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_CREST_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelHelmetPlate.getComponents(), burzumSteelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(burzumSteelHelmetPlate.getItem()),
                                has(burzumSteelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_CREST_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelHelmetPlate.getComponents(), burzumSteelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(burzumSteelHelmetPlate.getItem()),
                                has(burzumSteelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_MANDIBLE_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelHelmetPlate.getComponents(), burzumSteelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(burzumSteelHelmetPlate.getItem()),
                                has(burzumSteelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_MANDIBLE_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelHelmetPlate.getComponents(), burzumSteelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(burzumSteelHelmetPlate.getItem()),
                                has(burzumSteelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_SALLET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelHelmetPlate.getComponents(), burzumSteelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(burzumSteelHelmetPlate.getItem()),
                                has(burzumSteelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_SALLET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelHelmetPlate.getComponents(), burzumSteelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(burzumSteelHelmetPlate.getItem()),
                                has(burzumSteelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_CUIRASS.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.MORDOR_LEATHER_CUIRASS)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.MORDOR_LEATHER_CUIRASS),
                                has(EquipmentItemsME.MORDOR_LEATHER_CUIRASS))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_CUIRASS.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.MORDOR_LEATHER_CUIRASS)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.MORDOR_LEATHER_CUIRASS),
                                has(EquipmentItemsME.MORDOR_LEATHER_CUIRASS))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_GORGET_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                has(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_GORGET_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                has(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_REINFORCED_COAT.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.MORDOR_LEATHER_CUIRASS)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.MORDOR_LEATHER_CUIRASS),
                                has(EquipmentItemsME.MORDOR_LEATHER_CUIRASS))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_REINFORCED_COAT.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.MORDOR_LEATHER_CUIRASS)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.MORDOR_LEATHER_CUIRASS),
                                has(EquipmentItemsME.MORDOR_LEATHER_CUIRASS))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                has(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                has(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_PLATE_BOOTS.getDefaultInstance(), "boots", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(burzumSteelArmorPlate.getItem()),
                                has(burzumSteelArmorPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_PLATE_BOOTS.getDefaultInstance(), "boots", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(burzumSteelArmorPlate.getItem()),
                                has(burzumSteelArmorPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_GREAT_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T4)
                        .input(ResourceItemsME.BURZUM_STEEL_INGOT)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelHelmetPlate.getComponents(), burzumSteelHelmetPlate.getItem()))
                        .input(ResourceItemsME.BURZUM_STEEL_INGOT)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(burzumSteelHelmetPlate.getItem()),
                                has(burzumSteelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_SNOUT_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T4)
                        .input(ResourceItemsME.BURZUM_STEEL_INGOT)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelHelmetPlate.getComponents(), burzumSteelHelmetPlate.getItem()))
                        .input(ResourceItemsME.BURZUM_STEEL_INGOT)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(burzumSteelHelmetPlate.getItem()),
                                has(burzumSteelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_DEGRADED_GONDORIAN_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(Items.RED_DYE)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE),
                                has(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_PAINTED_CUIRASS.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(Items.RED_DYE)
                        .input(EquipmentItemsME.MORDOR_CUIRASS)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.MORDOR_CUIRASS),
                                has(EquipmentItemsME.MORDOR_CUIRASS))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_SCALE_COAT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .unlockedBy(getHasName(burzumSteelScaleMail.getItem()),
                                has(burzumSteelScaleMail.getItem()))
                        .save(output);

                //T5
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_URUK_PLATE_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(EquipmentItemsME.MORDOR_CHESTPLATE)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.MORDOR_CHESTPLATE),
                                has(EquipmentItemsME.MORDOR_CHESTPLATE))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_URUK_PLATE_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.ORCISH_MAIL_COAT)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_MAIL_COAT),
                                has(EquipmentItemsME.ORCISH_MAIL_COAT))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_URUK_COMMANDER_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T5)
                        .input(Items.SKELETON_SKULL)
                        .input(EquipmentItemsME.BLACK_URUK_PLATE_HELMET)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.RED_DYE)
                        .input(Items.RED_DYE)
                        .unlockedBy(getHasName(EquipmentItemsME.BLACK_URUK_PLATE_HELMET),
                                has(EquipmentItemsME.BLACK_URUK_PLATE_HELMET))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_URUK_COMMANDER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.RED_DYE)
                        .input(EquipmentItemsME.BLACK_URUK_PLATE_CHESTPLATE)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.BLACK_URUK_PLATE_CHESTPLATE),
                                has(EquipmentItemsME.BLACK_URUK_PLATE_CHESTPLATE))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .input(EquipmentItemsME.MAIL_HAUBERK)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.MAIL_HAUBERK),
                                has(EquipmentItemsME.MAIL_HAUBERK))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .input(EquipmentItemsME.MAIL_SKIRT)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.MAIL_SKIRT),
                                has(EquipmentItemsME.MAIL_SKIRT))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_CAPE.getDefaultInstance(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.STRING)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);
                //endregion

                //region DOL GULDUR
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_JAILER_COLLAR.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T2)
                    .input(ResourceItemsME.CRUDE_INGOT)
                    .input(ResourceItemsME.CRUDE_INGOT)
                    .input(ResourceItemsME.CRUDE_INGOT)
                    .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                            has(ResourceItemsME.CRUDE_INGOT))
                    .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WEATHERED_DOL_GULDUR_JAILER_COLLAR.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T2)
                    .input(ResourceItemsME.CRUDE_INGOT)
                    .input(ResourceItemsME.CRUDE_INGOT)
                    .input(ResourceItemsME.CRUDE_INGOT)
                    .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                            has(ResourceItemsME.CRUDE_INGOT))
                    .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_EXECUTIONER_HOOD.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T2)
                    .input(Items.LEATHER)
                    .input(Items.LEATHER)
                    .input(Items.LEATHER)
                    .input(Items.LEATHER)
                    .input(Items.LEATHER)
                    .unlockedBy(getHasName(Items.LEATHER),
                            has(Items.LEATHER))
                    .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_EXECUTIONER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE),
                                has(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WEATHERED_DOL_GULDUR_EXECUTIONER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE),
                                has(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_PADDED_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE),
                                has(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_HUNTER_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelHelmetPlate.getComponents(), burzumSteelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(burzumSteelArmorPlate.getItem()),
                                has(burzumSteelArmorPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_RAIDER_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelHelmetPlate.getComponents(), burzumSteelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(burzumSteelArmorPlate.getItem()),
                                has(burzumSteelArmorPlate.getItem()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WEATHERED_DOL_GULDUR_RAIDER_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelHelmetPlate.getComponents(), burzumSteelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(burzumSteelArmorPlate.getItem()),
                                has(burzumSteelArmorPlate.getItem()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_RAIDER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                has(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WEATHERED_DOL_GULDUR_RAIDER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                has(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_RAIDER_COAT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .unlockedBy(getHasName(burzumSteelScaleMail.getItem()),
                                has(burzumSteelScaleMail.getItem()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_BOOTS.getDefaultInstance(), "boots", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(burzumSteelArmorPlate.getItem()),
                                has(burzumSteelArmorPlate.getItem()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WEATHERED_DOL_GULDUR_BOOTS.getDefaultInstance(), "boots", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(burzumSteelArmorPlate.getItem()),
                                has(burzumSteelArmorPlate.getItem()))
                        .save(output);

                //T5
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_MARAUDER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(EquipmentItemsME.ORCISH_SCALE_VEST)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_SCALE_VEST),
                                has(EquipmentItemsME.ORCISH_SCALE_VEST))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_MARAUDER_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.ORCISH_MAIL_COAT)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_MAIL_COAT),
                                has(EquipmentItemsME.ORCISH_MAIL_COAT))
                        .save(output);
                // Weathered
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WEATHERED_DOL_GULDUR_MARAUDER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(EquipmentItemsME.ORCISH_SCALE_VEST)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_SCALE_VEST),
                                has(EquipmentItemsME.ORCISH_SCALE_VEST))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WEATHERED_DOL_GULDUR_MARAUDER_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.ORCISH_MAIL_COAT)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_MAIL_COAT),
                                has(EquipmentItemsME.ORCISH_MAIL_COAT))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_STALKER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(EquipmentItemsME.DOL_GULDUR_RAIDER_CHESTPLATE)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.DOL_GULDUR_RAIDER_CHESTPLATE),
                                has(EquipmentItemsME.DOL_GULDUR_RAIDER_CHESTPLATE))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_STALKER_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.ORCISH_MAIL_COAT)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_MAIL_COAT),
                                has(EquipmentItemsME.ORCISH_MAIL_COAT))
                        .save(output);
                // Weathered
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WEATHERED_DOL_GULDUR_STALKER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(EquipmentItemsME.WEATHERED_DOL_GULDUR_RAIDER_CHESTPLATE)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.WEATHERED_DOL_GULDUR_RAIDER_CHESTPLATE),
                                has(EquipmentItemsME.WEATHERED_DOL_GULDUR_RAIDER_CHESTPLATE))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WEATHERED_DOL_GULDUR_STALKER_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.ORCISH_MAIL_COAT)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_MAIL_COAT),
                                has(EquipmentItemsME.ORCISH_MAIL_COAT))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_REAVER_SHOULDER_CAPE.getDefaultInstance(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(steelHelmetPlate.getItem()),
                                has(steelHelmetPlate.getItem()))
                        .save(output);
                //endregion

                //region ISENGARD
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_LEATHER_SCOUT_CAP.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_LEATHER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T2)
                        .input(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .unlockedBy(getHasName(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE),
                                has(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_LEATHER_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_BOOTS.getDefaultInstance(), "boots", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_LIGHT_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironHelmetPlate.getComponents(), ironHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_LIGHT_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .input(EquipmentItemsME.URUK_HAI_LIGHT_HELMET)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .unlockedBy(getHasName(EquipmentItemsME.URUK_HAI_LIGHT_HELMET),
                                has(EquipmentItemsME.URUK_HAI_LIGHT_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_REINFORCED_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironHelmetPlate.getComponents(), ironHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_REINFORCED_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .input(EquipmentItemsME.URUK_HAI_REINFORCED_HELMET)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .unlockedBy(getHasName(EquipmentItemsME.URUK_HAI_REINFORCED_HELMET),
                                has(EquipmentItemsME.URUK_HAI_REINFORCED_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_MAIL_COAT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_MAIL_SKIRT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T3)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.URUK_HAI_MAIL_COAT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.URUK_HAI_MAIL_COAT),
                                has(EquipmentItemsME.URUK_HAI_MAIL_COAT))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_SOLDIER_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelHelmetPlate.getComponents(), burzumSteelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(burzumSteelHelmetPlate.getItem()),
                                has(burzumSteelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_SOLDIER_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T4)
                        .input(EquipmentItemsME.URUK_HAI_SOLDIER_HELMET)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .unlockedBy(getHasName(EquipmentItemsME.URUK_HAI_SOLDIER_HELMET),
                                has(EquipmentItemsME.URUK_HAI_SOLDIER_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_CUIRASS.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.STRING)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(burzumSteelArmorPlate.getItem()),
                                has(burzumSteelArmorPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PLATE_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(EquipmentItemsME.URUK_HAI_CUIRASS)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.URUK_HAI_CUIRASS),
                                has(EquipmentItemsME.URUK_HAI_CUIRASS))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PLATE_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.URUK_HAI_MAIL_COAT)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.URUK_HAI_MAIL_COAT),
                                has(EquipmentItemsME.URUK_HAI_MAIL_COAT))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_PLATE_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T5)
                        .input(EquipmentItemsME.URUK_HAI_PLATE_HELMET)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .unlockedBy(getHasName(EquipmentItemsME.URUK_HAI_PLATE_HELMET),
                                has(EquipmentItemsME.URUK_HAI_PLATE_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_SAPPER_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T5)
                        .input(EquipmentItemsME.URUK_HAI_SAPPER_HELMET)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .unlockedBy(getHasName(EquipmentItemsME.URUK_HAI_SAPPER_HELMET),
                                has(EquipmentItemsME.URUK_HAI_SAPPER_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_BERSERKER_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T5)
                        .input(EquipmentItemsME.URUK_HAI_BERSERKER_HELMET)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .unlockedBy(getHasName(EquipmentItemsME.URUK_HAI_BERSERKER_HELMET),
                                has(EquipmentItemsME.URUK_HAI_BERSERKER_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_COMMANDER_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.URUK_HAI_PLATE_HELMET)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.URUK_HAI_PLATE_HELMET),
                                has(EquipmentItemsME.URUK_HAI_PLATE_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_COMMANDER_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T5)
                        .input(EquipmentItemsME.URUK_HAI_COMMANDER_HELMET)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .unlockedBy(getHasName(EquipmentItemsME.URUK_HAI_COMMANDER_HELMET),
                                has(EquipmentItemsME.URUK_HAI_COMMANDER_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORTHANC_GUARD_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .input(EquipmentItemsME.MAIL_HAUBERK)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.MAIL_HAUBERK),
                                has(EquipmentItemsME.MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORTHANC_GUARD_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .input(EquipmentItemsME.MAIL_SKIRT)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.MAIL_SKIRT),
                                has(EquipmentItemsME.MAIL_SKIRT))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORTHANC_COMMANDER_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T5)
                        .input(Items.FEATHER)
                        .input(EquipmentItemsME.ORTHANC_GUARD_HELMET)
                        .input(Items.FEATHER)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .unlockedBy(getHasName(steelHelmetPlate.getItem()),
                                has(steelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORTHANC_GUARD_CAPE.getDefaultInstance(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LIGHT_GRAY_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .unlockedBy(getHasName(steelHelmetPlate.getItem()),
                                has(steelHelmetPlate.getItem()))
                        .save(output);

                //endregion

                //region MISTIES
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_BONE_PAULDRON.getDefaultInstance(), "chestplate", DispositionType.EVIL)
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_SEEKER_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, crudeArmorPlate.getComponents(), crudeArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_LACED_BOOTS.getDefaultInstance(), "boots", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_SKULLCAP_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironHelmetPlate.getComponents(), ironHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_MAIL_COIF.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_MAIL_COAT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_SOLDIER_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelHelmetPlate.getComponents(), burzumSteelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(burzumSteelHelmetPlate.getItem()),
                                has(burzumSteelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_SCREECHER_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelHelmetPlate.getComponents(), burzumSteelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(burzumSteelHelmetPlate.getItem()),
                                has(burzumSteelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T4)
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT),
                                has(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST),
                                has(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST),
                                has(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_HOBGOBLIN_MAIL_COAT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.GUNDABAD_MAIL_COAT)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_MAIL_COAT),
                                has(EquipmentItemsME.ORCISH_MAIL_COAT))
                        .save(output);
                //endregion

                //region MORIA
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_SNAGA_NASAL_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_SNAGA_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_SNAGA_CUIRASS.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, crudeArmorPlate.getComponents(), crudeArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_SNAGA_BELLY_PLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, crudeArmorPlate.getComponents(), crudeArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_CLOTH_COAT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_NUGGET),
                                has(ResourceItemsME.CRUDE_NUGGET))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_SNAGA_SKIRT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_NUGGET),
                                has(ResourceItemsME.CRUDE_NUGGET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_CRUDE_BOOTS.getDefaultInstance(), "boots", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_CHARGER_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, crudeArmorPlate.getComponents(), crudeArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironHelmetPlate.getComponents(), ironHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeArmorPlate.getComponents(), crudeArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORIA_RUINED_DWARVEN_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, crudeArmorPlate.getComponents(), crudeArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironHelmetPlate.getComponents(), ironHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeArmorPlate.getComponents(), crudeArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                has(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORIA_RUINED_DWARVEN_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                has(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_BITER_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelHelmetPlate.getComponents(), burzumSteelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(burzumSteelHelmetPlate.getItem()),
                                has(burzumSteelHelmetPlate.getItem()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_MANDIBLE_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelHelmetPlate.getComponents(), burzumSteelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(burzumSteelHelmetPlate.getItem()),
                                has(burzumSteelHelmetPlate.getItem()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_REINFORCED_COAT.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE),
                                has(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE))
                        .save(output);

                //T5
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_SCREECHER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(EquipmentItemsME.MORIA_GOBLIN_REINFORCED_COAT)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.MORIA_GOBLIN_REINFORCED_COAT),
                                has(EquipmentItemsME.MORIA_GOBLIN_REINFORCED_COAT))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_SCREECHER_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.ORCISH_MAIL_COAT)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_MAIL_COAT),
                                has(EquipmentItemsME.ORCISH_MAIL_COAT))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_CHIEF_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(EquipmentItemsME.MORIA_GOBLIN_REINFORCED_COAT)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.MORIA_GOBLIN_REINFORCED_COAT),
                                has(EquipmentItemsME.MORIA_GOBLIN_REINFORCED_COAT))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_CHIEF_LEGGINGS.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.ORCISH_MAIL_COAT)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(EquipmentItemsME.ORCISH_MAIL_COAT),
                                has(EquipmentItemsME.ORCISH_MAIL_COAT))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_CHIEF_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T5)
                        .input(Items.GOAT_HORN)
                        .input(EquipmentItemsME.MORIA_GOBLIN_CAPTAIN_HELMET)
                        .input(Items.GOAT_HORN)
                        .unlockedBy(getHasName(EquipmentItemsME.MORIA_GOBLIN_CAPTAIN_HELMET),
                                has(EquipmentItemsME.MORIA_GOBLIN_CAPTAIN_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_KING_CROWN.getDefaultInstance(), "helmet", DispositionType.EVIL)
                        .input(ResourceItemsME.DIRTY_BONE)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.DIRTY_BONE)
                        .input(ResourceItemsME.DIRTY_BONE)
                        .input(ResourceItemsME.DIRTY_BONE)
                        .unlockedBy(getHasName(ResourceItemsME.DIRTY_BONE),
                                has(ResourceItemsME.DIRTY_BONE))
                        .save(output);
                //endregion

                //region GOBLIN TOWN
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_CAP.getDefaultInstance(), "helmet", DispositionType.EVIL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_BONE_MANDIBLE_CAP.getDefaultInstance(), "helmet", DispositionType.EVIL)
                        .input(Items.BONE)
                        .input(EquipmentItemsME.GOBLIN_TOWN_CAP)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .unlockedBy(getHasName(EquipmentItemsME.GOBLIN_TOWN_CAP),
                                has(EquipmentItemsME.GOBLIN_TOWN_CAP))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_CROSSBONES_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL)
                        .input(Items.BONE)
                        .input(EquipmentItemsME.GOBLIN_TOWN_CAP)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .unlockedBy(getHasName(EquipmentItemsME.GOBLIN_TOWN_CAP),
                                has(EquipmentItemsME.GOBLIN_TOWN_CAP))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_RIBCAGE.getDefaultInstance(), "chestplate", DispositionType.EVIL)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .unlockedBy(getHasName(Items.BONE),
                                has(Items.BONE))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_BONE_STRAP.getDefaultInstance(), "chestplate", DispositionType.EVIL)
                        .input(ResourceItemsME.FUR)
                        .input(Items.BONE)
                        .input(ResourceItemsME.FUR)
                        .input(Items.BONE)
                        .input(ResourceItemsME.FUR)
                        .input(Items.BONE)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(Items.BONE),
                                has(Items.BONE))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_LOINCLOTH.getDefaultInstance(), "leggings", DispositionType.EVIL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_SANDALS.getDefaultInstance(), "boots", DispositionType.EVIL)
                        .input(Items.STICK)
                        .input(Items.STICK)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, crudeArmorPlate.getComponents(), crudeArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeArmorPlate.getComponents(), crudeArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeArmorPlate.getComponents(), crudeArmorPlate.getItem()))
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(crudeArmorPlate.getItem()),
                                has(crudeArmorPlate.getItem()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_MANDIBLE_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, crudeArmorPlate.getComponents(), crudeArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeArmorPlate.getComponents(), crudeArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeArmorPlate.getComponents(), crudeArmorPlate.getItem()))
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(crudeArmorPlate.getItem()),
                                has(crudeArmorPlate.getItem()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_HEAVY_NASAL_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET),
                                has(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_SKULKER_GUARD_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .unlockedBy(getHasName(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET),
                                has(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_SKULL_CAP.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, crudeArmorPlate.getComponents(), crudeArmorPlate.getItem()))
                        .input(Items.SKELETON_SKULL)
                        .componentInput(DataComponentIngredient.of(false, crudeArmorPlate.getComponents(), crudeArmorPlate.getItem()))
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .unlockedBy(getHasName(Items.SKELETON_SKULL),
                                has(Items.SKELETON_SKULL))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_TUNNELER_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .input(Items.CANDLE)
                        .input(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET)
                        .input(Items.CANDLE)
                        .unlockedBy(getHasName(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET),
                                has(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_BONE_SCALE_COAT.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_NUGGET),
                                has(ResourceItemsME.CRUDE_NUGGET))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_CRUDE_SCALE_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .componentInput(DataComponentIngredient.of(false, crudeArmorPlate.getComponents(), crudeArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeArmorPlate.getComponents(), crudeArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_NUGGET),
                                has(ResourceItemsME.CRUDE_NUGGET))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_CARAPACE_HARNESS.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T2)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_NUGGET),
                                has(ResourceItemsME.CRUDE_NUGGET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_LEATHER_SKIRT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);


                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_BELLY_PLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
                        .input(Items.BONE)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, crudeArmorPlate.getComponents(), crudeArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(crudeArmorPlate.getItem()),
                                has(crudeArmorPlate.getItem()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_REINFORCED_CARAPACE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
                        .input(Items.LEATHER)
                        .input(Items.BONE)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(EquipmentItemsME.GOBLIN_TOWN_CARAPACE_HARNESS)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.BONE)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.GOBLIN_TOWN_CARAPACE_HARNESS),
                                has(EquipmentItemsME.GOBLIN_TOWN_CARAPACE_HARNESS))
                        .save(output);
                //endregion

                for(ArmorTags.RecipeItem recipe : ArmorTags.heavyHelmets) {
                    ItemStack helmetIngredient;
                    ItemStack lowerIngredient;
                    ItemStack mainIngredient;
                    switch(recipe.metalType()) {
                        case EDHEL_STEEL -> {
                            lowerIngredient = edhelSteelArmorPlate;
                            mainIngredient = edhelSteelArmorPlate;
                            helmetIngredient = edhelSteelHelmetPlate;
                        }
                        case KHAZAD_STEEL -> {
                            lowerIngredient = khazadSteelArmorPlate;
                            mainIngredient = khazadSteelArmorPlate;
                            helmetIngredient = khazadSteelHelmetPlate;
                        }
                        case BURZUM_STEEL -> {
                            lowerIngredient = burzumSteelArmorPlate;
                            mainIngredient = burzumSteelArmorPlate;
                            helmetIngredient = burzumSteelHelmetPlate;
                        }
                        default -> {
                            mainIngredient = steelArmorPlate;
                            lowerIngredient = steelArmorPlate;
                            helmetIngredient = steelHelmetPlate;
                        }
                    }

                    if(recipe.noble()) {
                        if(recipe.disposition().equals(DispositionType.GOOD)) {
                            lowerIngredient = Items.GOLD_INGOT.getDefaultInstance();
                            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, recipe.output().getDefaultInstance(), "helmet", recipe.disposition(), XP_T5)
                                    .componentInput(DataComponentIngredient.of(false, mainIngredient.getComponents(), mainIngredient.getItem()))
                                    .componentInput(DataComponentIngredient.of(false, helmetIngredient.getComponents(), helmetIngredient.getItem()))
                                    .componentInput(DataComponentIngredient.of(false, mainIngredient.getComponents(), mainIngredient.getItem()))
                                    .input(lowerIngredient.getItem())
                                    .input(lowerIngredient.getItem())
                                    .unlockedBy(getHasName(lowerIngredient.getItem()),
                                            has(lowerIngredient.getItem()))
                                    .save(output);
                        } else {
                            lowerIngredient = silverArmorPlate;
                            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, recipe.output().getDefaultInstance(), "helmet", recipe.disposition(), XP_T5)
                                    .componentInput(DataComponentIngredient.of(false, mainIngredient.getComponents(), mainIngredient.getItem()))
                                    .componentInput(DataComponentIngredient.of(false, helmetIngredient.getComponents(), helmetIngredient.getItem()))
                                    .componentInput(DataComponentIngredient.of(false, mainIngredient.getComponents(), mainIngredient.getItem()))
                                    .componentInput(DataComponentIngredient.of(false, lowerIngredient.getComponents(), lowerIngredient.getItem()))
                                    .componentInput(DataComponentIngredient.of(false, lowerIngredient.getComponents(), lowerIngredient.getItem()))
                                    .unlockedBy(getHasName(lowerIngredient.getItem()),
                                            has(lowerIngredient.getItem()))
                                    .save(output);
                        }
                    } else {
                        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, recipe.output().getDefaultInstance(), "helmet", recipe.disposition(), XP_T5)
                                .componentInput(DataComponentIngredient.of(false, mainIngredient.getComponents(), mainIngredient.getItem()))
                                .componentInput(DataComponentIngredient.of(false, helmetIngredient.getComponents(), helmetIngredient.getItem()))
                                .componentInput(DataComponentIngredient.of(false, mainIngredient.getComponents(), mainIngredient.getItem()))
                                .componentInput(DataComponentIngredient.of(false, lowerIngredient.getComponents(), lowerIngredient.getItem()))
                                .componentInput(DataComponentIngredient.of(false, lowerIngredient.getComponents(), lowerIngredient.getItem()))
                                .unlockedBy(getHasName(lowerIngredient.getItem()),
                                        has(lowerIngredient.getItem()))
                                .save(output);
                    }
                }

                for(ArmorTags.RecipeItem recipe : ArmorTags.heavyBoots) {
                    ItemStack upperIngredient;
                    ItemStack mainIngredient = switch(recipe.metalType()) {
                        case EDHEL_STEEL -> edhelSteelArmorPlate;
                        case KHAZAD_STEEL -> khazadSteelArmorPlate;
                        case BURZUM_STEEL -> burzumSteelArmorPlate;
                        default -> steelArmorPlate;
                    };
                    if(recipe.noble()) {
                        if(recipe.disposition().equals(DispositionType.GOOD)) {
                            upperIngredient = Items.GOLD_INGOT.getDefaultInstance();
                            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, recipe.output().getDefaultInstance(), "boots", recipe.disposition(), XP_T5)
                                    .input(upperIngredient.getItem())
                                    .input(upperIngredient.getItem())
                                    .componentInput(DataComponentIngredient.of(false, mainIngredient.getComponents(), mainIngredient.getItem()))
                                    .componentInput(DataComponentIngredient.of(false, mainIngredient.getComponents(), mainIngredient.getItem()))
                                    .unlockedBy(getHasName(upperIngredient.getItem()),
                                            has(upperIngredient.getItem()))
                                    .save(output);
                        } else {
                            upperIngredient = silverArmorPlate;
                            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, recipe.output().getDefaultInstance(), "boots", recipe.disposition(), XP_T5)
                                    .componentInput(DataComponentIngredient.of(false, upperIngredient.getComponents(), upperIngredient.getItem()))
                                    .componentInput(DataComponentIngredient.of(false, upperIngredient.getComponents(), upperIngredient.getItem()))
                                    .componentInput(DataComponentIngredient.of(false, mainIngredient.getComponents(), mainIngredient.getItem()))
                                    .componentInput(DataComponentIngredient.of(false, mainIngredient.getComponents(), mainIngredient.getItem()))
                                    .unlockedBy(getHasName(upperIngredient.getItem()),
                                            has(upperIngredient.getItem()))
                                    .save(output);
                        }
                    } else {
                        upperIngredient = mainIngredient;
                        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, recipe.output().getDefaultInstance(), "boots", recipe.disposition(), XP_T5)
                                .componentInput(DataComponentIngredient.of(false, upperIngredient.getComponents(), upperIngredient.getItem()))
                                .componentInput(DataComponentIngredient.of(false, upperIngredient.getComponents(), upperIngredient.getItem()))
                                .componentInput(DataComponentIngredient.of(false, mainIngredient.getComponents(), mainIngredient.getItem()))
                                .componentInput(DataComponentIngredient.of(false, mainIngredient.getComponents(), mainIngredient.getItem()))
                                .unlockedBy(getHasName(upperIngredient.getItem()),
                                        has(upperIngredient.getItem()))
                                .save(output);
                    }
                }
                //endregion
    }
}
