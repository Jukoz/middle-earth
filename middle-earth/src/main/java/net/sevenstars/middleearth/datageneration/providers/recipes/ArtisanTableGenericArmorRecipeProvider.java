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
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
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
    private final CompletableFuture<HolderLookup.Provider> registryLookup;

    public ArtisanTableGenericArmorRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
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

        ItemStack bronzeChainmail = new ItemStack(ResourceItemsME.MAIL);
        bronzeChainmail.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.BRONZE.getName()))), getPattern()));
        ItemStack crudeChainmail = new ItemStack(ResourceItemsME.MAIL);
        crudeChainmail.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.CRUDE.getName()))), getPattern()));


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
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.SHIRRIFF_HAT.getDefaultInstance(), "hat", DispositionType.GOOD)
                        .input(Items.STRING)
                        .input(Items.LEATHER)
                        .input(Items.FEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                //region GENERIC

                //T1
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BAGGY_HAT.getDefaultInstance(), "hat", DispositionType.NEUTRAL)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.CHAPERON.getDefaultInstance(), "hat", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC), has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GLASSES.getDefaultInstance(), "hat", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.GLASS)
                        .input(Items.GLASS)
                        .unlockedBy(getHasName(ResourceItemsME.BRONZE_INGOT), has(ResourceItemsME.BRONZE_INGOT))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MONOCLE.getDefaultInstance(), "hat", DispositionType.GOOD)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.GLASS)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.BRONZE_INGOT), has(ResourceItemsME.BRONZE_INGOT))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.STRAW_HAT.getDefaultInstance(), "hat", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(ResourceItemsME.STRAW)
                        .input(Items.STRING)
                        .input(ResourceItemsME.STRAW)
                        .input(ResourceItemsME.STRAW)
                        .unlockedBy(getHasName(ResourceItemsME.STRAW),
                                has(ResourceItemsME.STRAW))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WOVEN_HAT.getDefaultInstance(), "hat", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(Items.WHITE_WOOL)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .unlockedBy(getHasName(Items.WHITE_WOOL),
                                has(Items.WHITE_WOOL))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BYCOCKET.getDefaultInstance(), "hat", DispositionType.NEUTRAL)
                        .input(Items.LEATHER)
                        .input(Items.WHITE_WOOL)
                        .input(Items.LEATHER)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .unlockedBy(getHasName(Items.WHITE_WOOL),
                                has(Items.WHITE_WOOL))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BEEKEEPER_MASK.getDefaultInstance(), "hat", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.STRAW)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC), has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WANDERER_HAT.getDefaultInstance(), "hat", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ARMING_COAT.getDefaultInstance(), "chestplate", DispositionType.NEUTRAL)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ARMING_SKIRT.getDefaultInstance(), "leggings", DispositionType.NEUTRAL)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.SHOES.getDefaultInstance(), "boots", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WORK_SHOES.getDefaultInstance(), "boots", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                //T2
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LEATHER_SKULLCAP.getDefaultInstance(), "helmet", DispositionType.NEUTRAL)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GAMBESON_CAP.getDefaultInstance(), "helmet", DispositionType.NEUTRAL)
                        .input(Items.LEATHER)
                        .input(Items.WHITE_WOOL)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GAMBESON_COWL.getDefaultInstance(), "helmet", DispositionType.NEUTRAL)
                        .input(Items.LEATHER)
                        .input(Items.WHITE_WOOL)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.KETTLE_HAT.getDefaultInstance(), "helmet", DispositionType.NEUTRAL)
                        .input(Items.IRON_NUGGET)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .input(Items.IRON_NUGGET)
                        .input(Items.IRON_NUGGET)
                        .input(Items.IRON_NUGGET)
                        .unlockedBy(getHasName(ironArmorPlate.getItem()),
                                has(ironArmorPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GAMBESON.getDefaultInstance(), "chestplate", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(Items.WHITE_WOOL)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACKSMITH_APRON.getDefaultInstance(), "chestplate", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .input(ResourceItemsME.TIN_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.TIN_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LEATHER_VEST.getDefaultInstance(), "chestplate", DispositionType.NEUTRAL)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LEATHER_SCALE_VEST.getDefaultInstance(), "chestplate", DispositionType.NEUTRAL)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.STURDY_BOOTS.getDefaultInstance(), "boots", DispositionType.NEUTRAL)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.TRAVELLING_BOOTS.getDefaultInstance(), "boots", DispositionType.NEUTRAL)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.HIGH_CUT_BOOTS.getDefaultInstance(), "boots", DispositionType.NEUTRAL)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);
                //T3
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.COOKING_POT_HELMET.getDefaultInstance(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .input(Items.IRON_INGOT)
                        .componentInput(DataComponentIngredient.of(false, ironHelmetPlate.getComponents(), ironHelmetPlate.getItem()))
                        .input(Items.IRON_INGOT)
                        .input(Items.IRON_NUGGET)
                        .input(Items.IRON_NUGGET)
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MAIL_COIF.getDefaultInstance(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.CLOSED_MAIL_COIF.getDefaultInstance(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.KETTLE_HAT_WITH_COIF.getDefaultInstance(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .input(EquipmentItemsME.MAIL_COIF)
                        .input(EquipmentItemsME.KETTLE_HAT)
                        .unlockedBy(getHasName(EquipmentItemsME.MAIL_COIF),
                                has(EquipmentItemsME.MAIL_COIF))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.KETTLE_HAT_WITH_CLOSED_COIF.getDefaultInstance(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .input(EquipmentItemsME.CLOSED_MAIL_COIF)
                        .input(EquipmentItemsME.KETTLE_HAT)
                        .unlockedBy(getHasName(EquipmentItemsME.CLOSED_MAIL_COIF),
                                has(EquipmentItemsME.CLOSED_MAIL_COIF))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.OPEN_FACE_HELMET.getDefaultInstance(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironHelmetPlate.getComponents(), ironHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WINGED_HELMET.getDefaultInstance(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("feathers")))
                        .input(EquipmentItemsME.OPEN_FACE_HELMET)
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("feathers")))
                        .unlockedBy(getHasName(EquipmentItemsME.OPEN_FACE_HELMET),
                                has(EquipmentItemsME.OPEN_FACE_HELMET))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.NEUTRAL, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MAIL_SHIRT.getDefaultInstance(), "chestplate", DispositionType.NEUTRAL, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MAIL_SKIRT.getDefaultInstance(), "leggings", DispositionType.NEUTRAL, XP_T3)
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
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.SALLET.getDefaultInstance(), "helmet", DispositionType.NEUTRAL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelHelmetPlate.getComponents(), steelHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelScaleMail.getComponents(), steelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(steelHelmetPlate.getItem()),
                                has(steelHelmetPlate.getItem()))
                        .save(output);

                //HOODS
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.HOOD.getDefaultInstance(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.TALL_HOOD.getDefaultInstance(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_FUR_HOOD.getDefaultInstance(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.HOOD)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(EquipmentItemsME.HOOD),
                                has(EquipmentItemsME.HOOD))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BROWN_FUR_HOOD.getDefaultInstance(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.HOOD)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(EquipmentItemsME.HOOD),
                                has(EquipmentItemsME.HOOD))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GRAY_FUR_HOOD.getDefaultInstance(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.HOOD)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(EquipmentItemsME.HOOD),
                                has(EquipmentItemsME.HOOD))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.TAN_FUR_HOOD.getDefaultInstance(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.HOOD)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(EquipmentItemsME.HOOD),
                                has(EquipmentItemsME.HOOD))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WHITE_FUR_HOOD.getDefaultInstance(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.HOOD)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(EquipmentItemsME.HOOD),
                                has(EquipmentItemsME.HOOD))
                        .save(output);

                //CAPES
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.CAPE.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
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
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.SHOULDER_CAPE_LEFT.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.SHOULDER_CAPE_RIGHT.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.SURCOAT.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.CLOAK.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WANDERER_ROBES.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LEAF_CAPE.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("leaves")))
                        .input(Items.STRING)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("leaves")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("leaves")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("leaves")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("leaves")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("leaves")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("leaves")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.parse("leaves")))
                        .unlockedBy(getHasName(Items.STRING),
                                has(Items.STRING))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LEAF_LITTER_CAPE.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ModNatureBlocks.LEAF_LITTER)
                        .input(Items.STRING)
                        .input(ModNatureBlocks.LEAF_LITTER)
                        .input(ModNatureBlocks.LEAF_LITTER)
                        .input(ModNatureBlocks.LEAF_LITTER)
                        .input(ModNatureBlocks.LEAF_LITTER)
                        .input(ModNatureBlocks.LEAF_LITTER)
                        .input(ModNatureBlocks.LEAF_LITTER)
                        .input(ModNatureBlocks.LEAF_LITTER)
                        .unlockedBy(getHasName(ModNatureBlocks.LEAF_LITTER),
                                has(ModNatureBlocks.LEAF_LITTER))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MIRK_LEAF_CAPE.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(Items.STRING)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .unlockedBy(getHasName(WoodBlockSets.MIRKWOOD_SET.leaves),
                                has(WoodBlockSets.MIRKWOOD_SET.leaves))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.MIRK_BARK_CAPE.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(Items.STRING)
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .unlockedBy(getHasName(WoodBlockSets.MIRKWOOD_SET.logBlocks.log()),
                                has(WoodBlockSets.MIRKWOOD_SET.logBlocks.log()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.AUTUMN_LEAF_CAPE.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(Items.STRING)
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("autumn_leaves")))
                        .unlockedBy(getHasName(Items.STRING),
                                has(Items.STRING))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_FUR_CLOAK.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.BLACK_FUR)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.CLOAK)
                        .input(Items.STRING)
                        .unlockedBy(getHasName(EquipmentItemsME.CLOAK),
                                has(EquipmentItemsME.CLOAK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BROWN_FUR_CLOAK.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.BROWN_FUR)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.CLOAK)
                        .input(Items.STRING)
                        .unlockedBy(getHasName(EquipmentItemsME.CLOAK),
                                has(EquipmentItemsME.CLOAK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GRAY_FUR_CLOAK.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.GRAY_FUR)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.CLOAK)
                        .input(Items.STRING)
                        .unlockedBy(getHasName(EquipmentItemsME.CLOAK),
                                has(EquipmentItemsME.CLOAK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.TAN_FUR_CLOAK.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.TAN_FUR)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.CLOAK)
                        .input(Items.STRING)
                        .unlockedBy(getHasName(EquipmentItemsME.CLOAK),
                                has(EquipmentItemsME.CLOAK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WHITE_FUR_CLOAK.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.WHITE_FUR)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.CLOAK)
                        .input(Items.STRING)
                        .unlockedBy(getHasName(EquipmentItemsME.CLOAK),
                                has(EquipmentItemsME.CLOAK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BLACK_FUR.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BROWN_FUR.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GRAY_FUR.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.TAN_FUR.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WHITE_FUR.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);
                //endregion

                //region ELVES

                //T1
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_ARMING_COAT.getDefaultInstance(), "chestplate", DispositionType.GOOD)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_ARMING_SKIRT.getDefaultInstance(), "leggings", DispositionType.GOOD)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_OPEN_ARMING_SKIRT.getDefaultInstance(), "leggings", DispositionType.GOOD)
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

                //T2
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_BOOTS.getDefaultInstance(), "boots", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BRONZED_ELVEN_MAIL_COIF.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T2)
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .unlockedBy(getHasName(bronzeChainmail.getItem()),
                                has(bronzeChainmail.getItem()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BRONZED_ELVEN_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T2)
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .unlockedBy(getHasName(bronzeChainmail.getItem()),
                                has(bronzeChainmail.getItem()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BRONZED_ELVEN_MAIL_SKIRT.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T2)
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, bronzeChainmail.getComponents(), bronzeChainmail.getItem()))
                        .unlockedBy(getHasName(bronzeChainmail.getItem()),
                                has(bronzeChainmail.getItem()))
                        .save(output);

                //T3
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_MAIL_COIF.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_MAIL_SKIRT.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BRONZED_ELVEN_PADDED_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.BRONZED_ELVEN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BRONZED_ELVEN_GORGET_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.BRONZED_ELVEN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_SILVER_PADDED_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.BRONZED_ELVEN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_SILVER_GORGET_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.BRONZED_ELVEN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                //endregion

                //region DWARVES

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MINER_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.CANDLE)
                        .input(Items.LEATHER)
                        .input(Items.CANDLE)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_GAMBESON.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(Items.WHITE_WOOL)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MAIL_COIF.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MINER_GAMBESON.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(EquipmentItemsME.DWARVEN_MAIL_HAUBERK)
                        .input(EquipmentItemsME.DWARVEN_GAMBESON)
                        .unlockedBy(getHasName(EquipmentItemsME.DWARVEN_MAIL_HAUBERK),
                                has(EquipmentItemsME.DWARVEN_MAIL_HAUBERK))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MAIL_COAT.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MAIL_CHAUSSES.getDefaultInstance(), "boots", DispositionType.GOOD, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_BOOTS.getDefaultInstance(), "boots", DispositionType.GOOD, XP_T3)
                        .input(Items.IRON_INGOT)
                        .input(Items.IRON_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.IRON_INGOT),
                                has(Items.IRON_INGOT))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_SCALE_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T4)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_SCALE_COAT.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T4)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_REINFORCED_BOOTS.getDefaultInstance(), "boots", DispositionType.GOOD, XP_T4)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.IRON_INGOT),
                                has(Items.IRON_INGOT))
                        .save(output);
                //endregion

                //region ORCS
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LEATHER_STRAP.getDefaultInstance(), "chestplate", DispositionType.EVIL)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_SANDALS.getDefaultInstance(), "boots", DispositionType.EVIL)
                        .input(Items.STICK)
                        .input(Items.STICK)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T2)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T2)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T2)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T2)
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T2)
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);


                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LEATHER_SKIRT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T2)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LEG_BRACER.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_MAIL_COAT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T2)
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .unlockedBy(getHasName(crudeChainmail.getItem()),
                                has(crudeChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T2)
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(crudeChainmail.getItem()),
                                has(crudeChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_REINFORCED_STRIP_LEATHER_SKIRT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T2)
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, crudeChainmail.getComponents(), crudeChainmail.getItem()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(crudeChainmail.getItem()),
                                has(crudeChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BLACK_FUR_BOOTS.getDefaultInstance(), "boots", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.FUR),
                                has(ResourceItemsME.FUR))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS.getDefaultInstance(), "boots", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.FUR),
                                has(ResourceItemsME.FUR))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_GRAY_FUR_BOOTS.getDefaultInstance(), "boots", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.FUR),
                                has(ResourceItemsME.FUR))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_TAN_FUR_BOOTS.getDefaultInstance(), "boots", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.FUR),
                                has(ResourceItemsME.FUR))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_WHITE_FUR_BOOTS.getDefaultInstance(), "boots", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.FUR),
                                has(ResourceItemsME.FUR))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BRACED_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironHelmetPlate.getComponents(), ironHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironHelmetPlate.getComponents(), ironHelmetPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironArmorPlate.getComponents(), ironArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironHelmetPlate.getItem()),
                                has(ironHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_MAIL_COIF.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_MAIL_SHIRT.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_MAIL_COAT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_REINFORCED_LEATHER_SKIRT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_REINFORCED_STRIP_LEATHER_SKIRT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T3)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_SALLET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T4)
                        .input(ResourceItemsME.BURZUM_STEEL_INGOT)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelHelmetPlate.getComponents(), burzumSteelHelmetPlate.getItem()))
                        .input(ResourceItemsME.BURZUM_STEEL_INGOT)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(burzumSteelHelmetPlate.getItem()),
                                has(burzumSteelHelmetPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BELLY_PLATE_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T4)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .unlockedBy(getHasName(burzumSteelScaleMail.getItem()),
                                has(burzumSteelScaleMail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_SCALE_VEST.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T4)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelScaleMail.getComponents(), burzumSteelScaleMail.getItem()))
                        .unlockedBy(getHasName(burzumSteelScaleMail.getItem()),
                                has(burzumSteelScaleMail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_REINFORCED_LEG_BRACER.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T4)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(burzumSteelArmorPlate.getItem()),
                                has(burzumSteelArmorPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BRACED_SANDALS.getDefaultInstance(), "boots", DispositionType.EVIL, XP_T4)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem())).input(Items.LEATHER)
                        .input(Items.LEATHER)

                        .unlockedBy(getHasName(burzumSteelArmorPlate.getItem()),
                                has(burzumSteelArmorPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_CAPE.getDefaultInstance(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FABRIC)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LONG_CAPE.getDefaultInstance(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FABRIC)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_SHOULDERS.getDefaultInstance(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .unlockedBy(getHasName(ResourceItemsME.FABRIC),
                                has(ResourceItemsME.FABRIC))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE.getDefaultInstance(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .input(Items.STRING)
                        .unlockedBy(getHasName(ResourceItemsME.FUR),
                                has(ResourceItemsME.FUR))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BROWN_FUR_SURCOAT_WITH_BONE.getDefaultInstance(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .input(Items.STRING)
                        .unlockedBy(getHasName(ResourceItemsME.FUR),
                                has(ResourceItemsME.FUR))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_GRAY_FUR_SURCOAT_WITH_BONE.getDefaultInstance(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .input(Items.STRING)
                        .unlockedBy(getHasName(ResourceItemsME.FUR),
                                has(ResourceItemsME.FUR))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_TAN_FUR_SURCOAT_WITH_BONE.getDefaultInstance(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .input(Items.STRING)
                        .unlockedBy(getHasName(ResourceItemsME.FUR),
                                has(ResourceItemsME.FUR))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_WHITE_FUR_SURCOAT_WITH_BONE.getDefaultInstance(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .input(Items.STRING)
                        .unlockedBy(getHasName(ResourceItemsME.FUR),
                                has(ResourceItemsME.FUR))
                        .save(output);
                //endregion

                //region MOUNT ARMORS

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_HORSE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(steelArmorPlate.getItem()),
                                has(steelArmorPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_HORSE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(steelArmorPlate.getItem()),
                                has(steelArmorPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HORSE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelArmorPlate.getComponents(), steelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, steelChainmail.getComponents(), steelChainmail.getItem()))
                        .unlockedBy(getHasName(steelArmorPlate.getItem()),
                                has(steelArmorPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_HORSE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .unlockedBy(getHasName(edhelSteelArmorPlate.getItem()),
                                has(edhelSteelArmorPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GREAT_HORN_LIGHT_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GREAT_HORN_LIGHT_GRAY_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.CYAN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GREAT_HORN_LIGHT_GREEN_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GREAT_HORN_PLATE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelArmorPlate.getComponents(), edhelSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, edhelSteelChainmail.getComponents(), edhelSteelChainmail.getItem()))
                        .unlockedBy(getHasName(edhelSteelArmorPlate.getItem()),
                                has(edhelSteelArmorPlate.getItem()))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GREAT_HORN_ORNAMENTED_PLATE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR),
                                has(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR))
                        .save(output);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.GREAT_HORN_GREEN_PLATE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GOLD_NUGGET)
                        .input(Items.GREEN_DYE)
                        .input(Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR),
                                has(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER),
                                has(Items.LEATHER))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_INGOT)
                        .input(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR)
                        .input(Items.GOLD_INGOT)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR),
                                has(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.BROADHOOF_GOAT_PLATE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(DataComponentIngredient.of(false, khazadSteelChainmail.getComponents(), khazadSteelChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelArmorPlate.getComponents(), khazadSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, khazadSteelChainmail.getComponents(), khazadSteelChainmail.getItem()))
                        .unlockedBy(getHasName(khazadSteelArmorPlate.getItem()),
                                has(khazadSteelArmorPlate.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WARG_LEATHER_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.EVIL)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WARG_REINFORCED_LEATHER_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.EVIL)
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .input(EquipmentItemsME.WARG_LEATHER_ARMOR)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")))
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(EquipmentItemsME.WARG_LEATHER_ARMOR),
                                has(EquipmentItemsME.WARG_LEATHER_ARMOR))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WARG_MORDOR_MAIL_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.EVIL)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .componentInput(DataComponentIngredient.of(false, ironChainmail.getComponents(), ironChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WARG_MORDOR_PLATE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.EVIL)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(EquipmentItemsME.WARG_MORDOR_MAIL_ARMOR)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WARG_ISENGARD_PLATE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.EVIL)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, EquipmentItemsME.WARG_GUNDABAD_PLATE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.EVIL)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelArmorPlate.getComponents(), burzumSteelArmorPlate.getItem()))
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .input(Items.LEATHER)
                        .componentInput(DataComponentIngredient.of(false, burzumSteelChainmail.getComponents(), burzumSteelChainmail.getItem()))
                        .unlockedBy(getHasName(ironChainmail.getItem()),
                                has(ironChainmail.getItem()))
                        .save(output);
                //endregion
    }
}
