package net.sevenstars.middleearth.datageneration.providers.recipes;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.custom.InscriptionRecipeJsonBuilder;
import net.sevenstars.middleearth.enchantments.EnchantmentsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.utils.ItemTagsME;

import java.util.concurrent.CompletableFuture;

public class InscriptionRecipeProvider extends RecipeProvider {
    private final CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup;

    public InscriptionRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);

        this.registryLookup = registriesFuture;
    }

    public RegistryWrapper.Impl<Enchantment> getEnchantmentRegistry() {
        RegistryWrapper.Impl<Enchantment> enchantmentImpl;

        try {
            enchantmentImpl = this.registryLookup.get().getOrThrow(RegistryKeys.ENCHANTMENT);
        } catch (Exception ignored) {
            throw new IllegalStateException("Data generation without registries failed!");
        }
        return enchantmentImpl;
    }

    public RegistryEntry<Enchantment> getEnchantment(RegistryKey<Enchantment> enchantmentRegistryKey) {
        return getEnchantmentRegistry().getOrThrow(enchantmentRegistryKey);
    }

    @Override
    public String getName() {
        return "InscriptionRecipes";
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);

                //region UNBREAKING
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.UNBREAKING), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("resilient")
                        .addWord("blessing")
                        .criterion(hasItem(Items.LAPIS_LAZULI),
                                conditionsFromItem(Items.LAPIS_LAZULI))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_unbreaking_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.UNBREAKING), 2, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("resilient")
                        .addWord("blessing")
                        .criterion(hasItem(Items.LAPIS_LAZULI),
                                conditionsFromItem(Items.LAPIS_LAZULI))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_unbreaking_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.UNBREAKING), 3, 9)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("resilient")
                        .addWord("blessing")
                        .criterion(hasItem(Items.LAPIS_LAZULI),
                                conditionsFromItem(Items.LAPIS_LAZULI))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_unbreaking_3")));
                //endregion

                //region THORNS
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.THORNS), 1, 4)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("fierce")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_thorns_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.THORNS), 2, 6)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("fierce")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_thorns_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.THORNS), 3, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("fierce")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_thorns_3")));
                //endregion

                //region PROJECTILE PROTECTION
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.PROJECTILE_PROTECTION), 1, 4)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("pierce")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_projectile_protection_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.PROJECTILE_PROTECTION), 2, 6)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("pierce")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_projectile_protection_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.PROJECTILE_PROTECTION), 3, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("pierce")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_projectile_protection_3")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.PROJECTILE_PROTECTION), 4, 10)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("pierce")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_projectile_protection_4")));
                //endregion

                //region FIRE PROTECTION
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.FIRE_PROTECTION), 1, 4)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("flame")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_fire_protection_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.FIRE_PROTECTION), 2, 6)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("flame")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_fire_protection_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.FIRE_PROTECTION), 3, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("flame")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_fire_protection_3")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.FIRE_PROTECTION), 4, 10)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("flame")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_fire_protection_4")));
                //endregion

                //region BLAST PROTECTION
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.BLAST_PROTECTION), 1, 4)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("flame")
                        .addWord("sturdy")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_blast_protection_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.BLAST_PROTECTION), 2, 6)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("flame")
                        .addWord("sturdy")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_blast_protection_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.BLAST_PROTECTION), 3, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("flame")
                        .addWord("sturdy")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_blast_protection_3")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.BLAST_PROTECTION), 4, 10)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("flame")
                        .addWord("sturdy")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_blast_protection_4")));
                //endregion

                //region AQUA AFFINITY
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.AQUA_AFFINITY), 1, 10)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("tidal")
                        .addWord("swift")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_aqua_affinity_1")));
                //endregion

                //region RESPIRATION
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.RESPIRATION), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("gifted")
                        .addWord("tidal")
                        .addWord("blessing")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_respiration_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.RESPIRATION), 2, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("gifted")
                        .addWord("tidal")
                        .addWord("blessing")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_respiration_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.RESPIRATION), 3, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("gifted")
                        .addWord("tidal")
                        .addWord("blessing")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_respiration_3")));
                //endregion

                //region FEATHER FALLING
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.FEATHER_FALLING), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("sturdy")
                        .addWord("resilient")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_feather_falling_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.FEATHER_FALLING), 2, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("sturdy")
                        .addWord("resilient")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_feather_falling_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.FEATHER_FALLING), 3, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("sturdy")
                        .addWord("resilient")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_feather_falling_3")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.FEATHER_FALLING), 4, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("sturdy")
                        .addWord("resilient")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_feather_falling_4")));
                //endregion

                //region DEPTH STRIDER
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.DEPTH_STRIDER), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("swift")
                        .addWord("tidal")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_depth_strider_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.DEPTH_STRIDER), 2, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("swift")
                        .addWord("tidal")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_depth_strider_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.DEPTH_STRIDER), 3, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("swift")
                        .addWord("tidal")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_depth_strider_3")));
                //endregion

                //region SHARPNESS
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SHARPNESS), 1, 3)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("forceful")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_sharpness_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SHARPNESS), 2, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("forceful")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_sharpness_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SHARPNESS), 3, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("forceful")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_sharpness_3")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SHARPNESS), 4, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("forceful")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_sharpness_4")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SHARPNESS), 5, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("forceful")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_sharpness_5")));
                //endregion

                //region KNOCKBACK
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.KNOCKBACK), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("forceful")
                        .addWord("edge")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_knockback_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.KNOCKBACK), 2, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("forceful")
                        .addWord("edge")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_knockback_2")));
                //endregion

                //region LOOTING
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.LOOTING), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("gifted")
                        .addWord("blessing")
                        .addWord("edge")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_looting_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.LOOTING), 2, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("gifted")
                        .addWord("blessing")
                        .addWord("edge")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_looting_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.LOOTING), 3, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("gifted")
                        .addWord("blessing")
                        .addWord("edge")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_looting_3")));
                //endregion

                //region SMITE
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SMITE), 1, 3)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("spirit")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_smite_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SMITE), 2, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("spirit")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_smite_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SMITE), 3, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("spirit")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_smite_3")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SMITE), 4, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("spirit")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_smite_4")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SMITE), 5, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("spirit")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_smite_5")));
                //endregion

                //region BANE OF ARTHROPODS
                /*
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.BANE_OF_ARTHROPODS), 1, 3)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("spider")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_bane_of_arthropods_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.BANE_OF_ARTHROPODS), 2, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("spider")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_bane_of_arthropods_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.BANE_OF_ARTHROPODS), 3, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("spider")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_bane_of_arthropods_3")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.BANE_OF_ARTHROPODS), 4, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("spider")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_bane_of_arthropods_4")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.BANE_OF_ARTHROPODS), 5, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("spider")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_bane_of_arthropods_5")));
                 */
                //endregion

                //region SWEEPING EDGE
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SWEEPING_EDGE), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("broad")
                        .addWord("edge")
                        .addWord("cutter")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_sweeping_edge_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SWEEPING_EDGE), 2, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("broad")
                        .addWord("edge")
                        .addWord("cutter")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_sweeping_edge_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SWEEPING_EDGE), 3, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("broad")
                        .addWord("edge")
                        .addWord("cutter")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_sweeping_edge_3")));
                //endregion

                //region POWER
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.POWER), 1, 3)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("fierce")
                        .addWord("point")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_power_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.POWER), 2, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("fierce")
                        .addWord("point")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_power_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.POWER), 3, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("fierce")
                        .addWord("point")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_power_3")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.POWER), 4, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("fierce")
                        .addWord("point")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_power_4")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.POWER), 5, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("fierce")
                        .addWord("point")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_power_5")));
                //endregion

                //region PUNCH
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.PUNCH), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("forceful")
                        .addWord("draw")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_punch_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.PUNCH), 2, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("forceful")
                        .addWord("draw")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_punch_2")));
                //endregion

                //region PIERCING
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.PIERCING), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("long")
                        .addWord("point")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_piercing_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.PIERCING), 2, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("long")
                        .addWord("point")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_piercing_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.PIERCING), 3, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("long")
                        .addWord("point")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_piercing_3")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.PIERCING), 4, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("long")
                        .addWord("point")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_piercing_4")));

                //endregion

                //region QUICK CHARGE
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.QUICK_CHARGE), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("swift")
                        .addWord("draw")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_quick_charge_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.QUICK_CHARGE), 2, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("swift")
                        .addWord("draw")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_quick_charge_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.QUICK_CHARGE), 3, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("swift")
                        .addWord("draw")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_quick_charge_3")));
                //endregion

                //region EFFICIENCY
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.EFFICIENCY), 1, 3)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("swift")
                        .addWord("collector")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_efficiency_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.EFFICIENCY), 2, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("swift")
                        .addWord("collector")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_efficiency_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.EFFICIENCY), 3, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("swift")
                        .addWord("collector")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_efficiency_3")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.EFFICIENCY), 4, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("swift")
                        .addWord("collector")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_efficiency_4")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.EFFICIENCY), 5, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("swift")
                        .addWord("collector")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_efficiency_5")));
                //endregion

                //region FORTUNE
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.FORTUNE), 1, 11)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("gifted")
                        .addWord("blessing")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_fortune_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.FORTUNE), 2, 13)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("gifted")
                        .addWord("blessing")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_fortune_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.FORTUNE), 3, 15)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("gifted")
                        .addWord("blessing")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_fortune_3")));
                //endregion

                //region SILK TOUCH
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(Enchantments.SILK_TOUCH), 1, 10)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("careful")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_silk_touch")));
                //endregion



                //region AILMENT PROTECTION
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.AILMENT_PROTECTION), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("sturdy")
                        .addWord("resilient")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_ailment_protection_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.AILMENT_PROTECTION), 2, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("sturdy")
                        .addWord("resilient")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_ailment_protection_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.AILMENT_PROTECTION), 3, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("sturdy")
                        .addWord("resilient")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_ailment_protection_3")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.AILMENT_PROTECTION), 4, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("sturdy")
                        .addWord("resilient")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_ailment_protection_4")));
                //endregion

                //region BANE OF GIANTS
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.BANE_OF_GIANTS), 1, 3)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("giant")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_bane_of_giants_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.BANE_OF_GIANTS), 2, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("giant")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_bane_of_giants_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.BANE_OF_GIANTS), 3, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("giant")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_bane_of_giants_3")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.BANE_OF_GIANTS), 4, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("giant")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_bane_of_giants_4")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.BANE_OF_GIANTS), 5, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("giant")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_bane_of_giants_5")));
                //endregion

                //region CELERITY
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.CELERITY), 1, 10)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("swift")
                        .addWord("edge")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_celerity")));
                //endregion

                //region GROUNDED
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.GROUNDED), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("sturdy")
                        .addWord("edge")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_grounded_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.GROUNDED), 2, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("sturdy")
                        .addWord("edge")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_grounded_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.GROUNDED), 3, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("sturdy")
                        .addWord("edge")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_grounded_3")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.GROUNDED), 4, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("sturdy")
                        .addWord("edge")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_grounded_4")));
                //endregion

                //region HEWING
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.HEWING), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("broad")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_hewing_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.HEWING), 2, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("broad")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_hewing_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.HEWING), 3, 9)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("broad")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_hewing_3")));
                //endregion

                //region HIGH STEP
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.HIGH_STEP), 1, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("long")
                        .addWord("traveller")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_high_step")));
                //endregion

                //region MINER_REACH
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.MINER_REACH), 1, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("long")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_miner_reach")));
                //endregion

                //region STEALTHY_TRAIL
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.STEALTHY_TRAIL), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("noiseless")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_stealthy_trail_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.STEALTHY_TRAIL), 2, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("noiseless")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_stealthy_trail_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.STEALTHY_TRAIL), 3, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("noiseless")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_stealthy_trail_3")));
                //endregion

                //region STALWART
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.STALWART), 1, 9)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("sturdy")
                        .addWord("point")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_stalwart")));
                //endregion

                //region STRIDE
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.STRIDE), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("swift")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_stride_1")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.STRIDE), 2, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("swift")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_stride_2")));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.STRIDE), 3, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("swift")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_stride_3")));
                //endregion

                //region VANTAGE
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(itemLookup, RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.VANTAGE), 1, 11)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("long")
                        .addWord("edge")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, String.valueOf(Identifier.of(MiddleEarth.MOD_ID, "inscription_vantage")));
                //endregion
            }
        };
    }

}
