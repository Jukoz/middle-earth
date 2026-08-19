package net.sevenstars.middleearth.compat.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.fml.ModList;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.compat.recipeviewer.DynamicCraftingRecipeDisplay;
import net.sevenstars.middleearth.compat.recipeviewer.RecipeViewerHelper;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableInputsShape;
import net.sevenstars.middleearth.gui.artisantable.InputType;
import net.sevenstars.middleearth.recipe.AlloyingRecipe;
import net.sevenstars.middleearth.recipe.AnvilShapingRecipe;
import net.sevenstars.middleearth.recipe.ArtisanRecipe;
import net.sevenstars.middleearth.recipe.RecipesME;
import net.sevenstars.middleearth.recipe.inscription.InscriptionRecipe;

import java.util.List;

@EmiEntrypoint
public final class MiddleEarthEmiPlugin implements EmiPlugin {
    private static final EmiStack ARTISAN_TABLE_ICON = EmiStack.of(ModDecorativeBlocks.ARTISAN_TABLE);
    private static final EmiStack FORGE_ICON = EmiStack.of(ModDecorativeBlocks.FORGE);
    private static final EmiStack ANVIL_ICON = EmiStack.of(ModDecorativeBlocks.TREATED_ANVIL);
    private static final EmiStack INSCRIPTION_ICON = EmiStack.of(ModDecorativeBlocks.INSCRIPTION_TABLE);

    public static final EmiRecipeCategory ARTISAN_TABLE = new EmiRecipeCategory(
            MiddleEarth.of("artisan_table"), ARTISAN_TABLE_ICON,
            new EmiTexture(MiddleEarth.of("textures/gui/sprites/emi_sprite_sheet.png"), 0, 0, 16, 16));
    public static final EmiRecipeCategory FORGE = new EmiRecipeCategory(
            MiddleEarth.of("forge"), FORGE_ICON,
            new EmiTexture(MiddleEarth.of("textures/gui/sprites/emi_sprite_sheet.png"), 0, 16, 16, 16));
    public static final EmiRecipeCategory ANVIL_SHAPING = new EmiRecipeCategory(
            MiddleEarth.of("anvil_shaping"), ANVIL_ICON,
            new EmiTexture(MiddleEarth.of("textures/gui/sprites/emi_sprite_sheet.png"), 0, 32, 16, 16));
    public static final EmiRecipeCategory INSCRIPTION_TABLE = new EmiRecipeCategory(
            MiddleEarth.of("inscription_table"), INSCRIPTION_ICON);

    @Override
    public void register(EmiRegistry registry) {
        RecipeManager recipes = registry.getRecipeManager();
        List<RecipeHolder<ArtisanRecipe>> artisanRecipes = recipes.getAllRecipesFor(RecipesME.ARTISAN_TABLE);
        List<RecipeHolder<AlloyingRecipe>> forgeRecipes = recipes.getAllRecipesFor(RecipesME.FORGE);
        List<RecipeHolder<AnvilShapingRecipe>> anvilRecipes = recipes.getAllRecipesFor(RecipesME.ANVIL_SHAPING);
        List<RecipeHolder<InscriptionRecipe>> inscriptionRecipes = recipes.getAllRecipesFor(RecipesME.INSCRIPTION_TABLE);

        registry.addCategory(ARTISAN_TABLE);
        registry.addWorkstation(ARTISAN_TABLE, ARTISAN_TABLE_ICON);
        registry.addWorkstation(ARTISAN_TABLE, EmiStack.of(ModDecorativeBlocks.ORCISH_ARTISAN_TABLE));
        for (RecipeHolder<ArtisanRecipe> holder : artisanRecipes) {
            registry.addRecipe(new ArtisanEmiRecipe(holder));
        }

        registry.addCategory(FORGE);
        registry.addWorkstation(FORGE, FORGE_ICON);
        for (RecipeHolder<AlloyingRecipe> holder : forgeRecipes) {
            registry.addRecipe(new ForgeEmiRecipe(holder));
        }

        registry.addCategory(ANVIL_SHAPING);
        registry.addWorkstation(ANVIL_SHAPING, ANVIL_ICON);
        registry.addWorkstation(ANVIL_SHAPING, EmiStack.of(ModDecorativeBlocks.DWARVEN_TREATED_ANVIL));
        registry.addWorkstation(ANVIL_SHAPING, EmiStack.of(ModDecorativeBlocks.ELVEN_TREATED_ANVIL));
        registry.addWorkstation(ANVIL_SHAPING, EmiStack.of(ModDecorativeBlocks.ORCISH_TREATED_ANVIL));
        for (RecipeHolder<AnvilShapingRecipe> holder : anvilRecipes) {
            registry.addRecipe(new AnvilEmiRecipe(holder));
        }

        registry.addCategory(INSCRIPTION_TABLE);
        registry.addWorkstation(INSCRIPTION_TABLE, INSCRIPTION_ICON);
        for (RecipeHolder<InscriptionRecipe> holder : inscriptionRecipes) {
            registry.addRecipe(new InscriptionEmiRecipe(holder));
        }

        boolean importDynamicCraftingFromJei = ModList.get().isLoaded("jei");
        int dynamicCraftingCount = 0;
        for (RecipeHolder<CraftingRecipe> holder
                : recipes.getAllRecipesFor(net.minecraft.world.item.crafting.RecipeType.CRAFTING)) {
            if (DynamicCraftingRecipeDisplay.supports(holder.value())) {
                if (!importDynamicCraftingFromJei) {
                    registry.addRecipe(new DynamicCraftingEmiRecipe(holder));
                }
                dynamicCraftingCount++;
            }
        }
        MiddleEarth.LOGGER.logInfoMsg((
                "EMI recipe viewer registered: artisan=%d, inscription=%d, anvil=%d, forge=%d, dynamic_crafting=%d"
        ).formatted(artisanRecipes.size(), inscriptionRecipes.size(), anvilRecipes.size(),
                forgeRecipes.size(), dynamicCraftingCount));
    }

    private static final class DynamicCraftingEmiRecipe extends BasicEmiRecipe {
        private final RecipeHolder<CraftingRecipe> holder;
        private final EmiIngredient outputAlternatives;

        private DynamicCraftingEmiRecipe(RecipeHolder<CraftingRecipe> holder) {
            super(VanillaEmiRecipeCategories.CRAFTING, holder.id(), 118, 54);
            this.holder = holder;
            DynamicCraftingRecipeDisplay.Display display = DynamicCraftingRecipeDisplay.create(holder.value());
            this.inputs = display.inputs().stream()
                    .map(stacks -> EmiIngredient.of(stacks.stream().map(EmiStack::of).toList()))
                    .toList();
            this.outputs = display.outputs().stream().map(EmiStack::of).toList();
            this.outputAlternatives = EmiIngredient.of(this.outputs);
        }

        @Override
        public RecipeHolder<?> getBackingRecipe() {
            return this.holder;
        }

        @Override
        public boolean supportsRecipeTree() {
            return false;
        }

        @Override
        public void addWidgets(WidgetHolder widgets) {
            widgets.addTexture(EmiTexture.EMPTY_ARROW, 60, 18);
            widgets.addTexture(EmiTexture.SHAPELESS, 97, 0);
            for (int index = 0; index < 9; index++) {
                EmiIngredient ingredient = index < this.inputs.size()
                        ? this.inputs.get(index)
                        : EmiStack.EMPTY;
                widgets.addSlot(ingredient, (index % 3) * 18, (index / 3) * 18);
            }
            widgets.addSlot(this.outputAlternatives, 92, 14).large(true).recipeContext(this);
        }
    }

    private abstract static class HolderEmiRecipe<R extends Recipe<?>> extends BasicEmiRecipe {
        protected final RecipeHolder<R> holder;

        private HolderEmiRecipe(EmiRecipeCategory category, RecipeHolder<R> holder, int width, int height) {
            super(category, holder.id(), width, height);
            this.holder = holder;
        }

        @Override
        public RecipeHolder<?> getBackingRecipe() {
            return this.holder;
        }
    }

    private static final class ArtisanEmiRecipe extends HolderEmiRecipe<ArtisanRecipe> {
        private final ArtisanTableInputsShape shape;
        private final Component categoryLabel;

        private ArtisanEmiRecipe(RecipeHolder<ArtisanRecipe> holder) {
            super(ARTISAN_TABLE, holder, 146, 70);
            this.shape = ArtisanTableInputsShape.getShape(holder.value().category);
            this.inputs = holder.value().inputs.stream().map(EmiIngredient::of).toList();
            this.outputs = java.util.List.of(EmiStack.of(holder.value().getOutput()));
            this.categoryLabel = Component.translatable(
                    "screen." + MiddleEarth.MOD_ID + ".artisan_table." + holder.value().category);
        }

        @Override
        public void addWidgets(WidgetHolder widgets) {
            int ingredientIndex = 0;
            if (this.shape != null) {
                for (int y = 0; y < 3; y++) {
                    for (int x = 0; x < 3; x++) {
                        if (this.shape.getInputType(x, y) != InputType.NONE
                                && ingredientIndex < this.inputs.size()) {
                            widgets.addSlot(this.inputs.get(ingredientIndex++), 4 + 18 * x, 7 + 18 * y);
                        }
                    }
                }
            }
            widgets.addSlot(this.outputs.getFirst(), 123, 25).recipeContext(this);
            widgets.addText(this.categoryLabel, 64, 7, 0x404040, false);
        }
    }

    private static final class ForgeEmiRecipe extends HolderEmiRecipe<AlloyingRecipe> {
        private final Component unitsLabel;
        private final Component ingotEquivalentLabel;

        private ForgeEmiRecipe(RecipeHolder<AlloyingRecipe> holder) {
            super(FORGE, holder, 138, 64);
            this.inputs = holder.value().getIngredients().stream().map(EmiIngredient::of).toList();
            ItemStack output = RecipeViewerHelper.getAlloyOutput(holder.value());
            this.outputs = output.isEmpty() ? java.util.List.of() : java.util.List.of(EmiStack.of(output));
            this.unitsLabel = Component.translatable(
                    "recipe." + MiddleEarth.MOD_ID + ".metal_units", holder.value().getAmount());
            this.ingotEquivalentLabel = Component.translatable(
                    "recipe." + MiddleEarth.MOD_ID + ".ingot_equivalent",
                    RecipeViewerHelper.getIngotEquivalent(holder.value()));
        }

        @Override
        public void addWidgets(WidgetHolder widgets) {
            for (int index = 0; index < this.inputs.size(); index++) {
                widgets.addSlot(this.inputs.get(index), 2 + 18 * index, 6);
            }
            widgets.addTexture(EmiTexture.EMPTY_ARROW, 80, 7);
            if (!this.outputs.isEmpty()) {
                widgets.addSlot(this.outputs.getFirst(), 116, 6).recipeContext(this);
            }
            widgets.addText(this.unitsLabel, 3, 34, 0x404040, false);
            widgets.addText(this.ingotEquivalentLabel, 3, 46, 0x404040, false);
        }

        @Override
        public boolean supportsRecipeTree() {
            return false;
        }
    }

    private static final class AnvilEmiRecipe extends HolderEmiRecipe<AnvilShapingRecipe> {
        private final Component strikesLabel;

        private AnvilEmiRecipe(RecipeHolder<AnvilShapingRecipe> holder) {
            super(ANVIL_SHAPING, holder, 94, 50);
            this.inputs = java.util.List.of(EmiIngredient.of(holder.value().getIngredient()));
            this.outputs = java.util.List.of(EmiStack.of(holder.value().getOutput()));
            this.strikesLabel = Component.translatable(
                    "recipe." + MiddleEarth.MOD_ID + ".hammer_strikes", holder.value().getAmount());
        }

        @Override
        public void addWidgets(WidgetHolder widgets) {
            widgets.addSlot(this.inputs.getFirst(), 2, 6);
            widgets.addTexture(EmiTexture.EMPTY_ARROW, 35, 7);
            widgets.addSlot(this.outputs.getFirst(), 72, 6).recipeContext(this);
            widgets.addText(this.strikesLabel, 3, 36, 0x404040, false);
        }
    }

    private static final class InscriptionEmiRecipe extends HolderEmiRecipe<InscriptionRecipe> {
        private final Component wordsLabel;
        private final Component levelCostLabel;

        private InscriptionEmiRecipe(RecipeHolder<InscriptionRecipe> holder) {
            super(INSCRIPTION_TABLE, holder, 138, 66);
            InscriptionRecipe recipe = holder.value();
            this.inputs = java.util.List.of(
                    EmiIngredient.of(RecipeViewerHelper.getInscriptionCatalyst(recipe)),
                    EmiIngredient.of(recipe.inputChisel));
            this.outputs = java.util.List.of(EmiStack.of(RecipeViewerHelper.getInscriptionOutput(recipe)));
            this.wordsLabel = Component.literal(String.join(" / ", recipe.inputWords));
            this.levelCostLabel = Component.translatable(
                    "recipe." + MiddleEarth.MOD_ID + ".level_cost", recipe.levelCost);
        }

        @Override
        public void addWidgets(WidgetHolder widgets) {
            widgets.addSlot(this.inputs.get(0), 2, 6);
            widgets.addSlot(this.inputs.get(1), 26, 6);
            widgets.addTexture(EmiTexture.EMPTY_ARROW, 75, 7);
            widgets.addSlot(this.outputs.getFirst(), 116, 6).recipeContext(this);
            widgets.addText(this.wordsLabel, 3, 34, 0x404040, false);
            widgets.addText(this.levelCostLabel, 3, 48, 0x404040, false);
        }

        @Override
        public boolean supportsRecipeTree() {
            return false;
        }
    }
}
