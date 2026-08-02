package net.sevenstars.middleearth.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.compat.recipeviewer.DynamicCraftingRecipeDisplay;
import net.sevenstars.middleearth.compat.recipeviewer.RecipeViewerHelper;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableInputsShape;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreen;
import net.sevenstars.middleearth.gui.artisantable.InputType;
import net.sevenstars.middleearth.gui.forge.ForgeAlloyingScreen;
import net.sevenstars.middleearth.recipe.AlloyingRecipe;
import net.sevenstars.middleearth.recipe.AnvilShapingRecipe;
import net.sevenstars.middleearth.recipe.ArtisanRecipe;
import net.sevenstars.middleearth.recipe.BackAttachmentRecipe;
import net.sevenstars.middleearth.recipe.BackAttachmentRemovalRecipe;
import net.sevenstars.middleearth.recipe.CustomItemDecorationRecipe;
import net.sevenstars.middleearth.recipe.HelmetAttachmentRecipe;
import net.sevenstars.middleearth.recipe.HelmetAttachmentRemovalRecipe;
import net.sevenstars.middleearth.recipe.MountArmorAddonRemovalRecipe;
import net.sevenstars.middleearth.recipe.MountArmorSideSkullAddonRecipe;
import net.sevenstars.middleearth.recipe.MountArmorTopSkullAddonRecipe;
import net.sevenstars.middleearth.recipe.RecipesME;
import net.sevenstars.middleearth.recipe.inscription.InscriptionRecipe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JeiPlugin
public final class MiddleEarthJeiPlugin implements IModPlugin {
    private static final ResourceLocation PLUGIN_ID = MiddleEarth.of("jei_plugin");
    private static final Map<ResourceLocation, DrawText> DRAW_TEXT = new HashMap<>();

    public static final RecipeType<RecipeHolder<ArtisanRecipe>> ARTISAN_TABLE =
            RecipeType.createRecipeHolderType(MiddleEarth.of("artisan_table"));
    public static final RecipeType<RecipeHolder<AlloyingRecipe>> FORGE =
            RecipeType.createRecipeHolderType(MiddleEarth.of("forge"));
    public static final RecipeType<RecipeHolder<AnvilShapingRecipe>> ANVIL_SHAPING =
            RecipeType.createRecipeHolderType(MiddleEarth.of("anvil_shaping"));
    public static final RecipeType<RecipeHolder<InscriptionRecipe>> INSCRIPTION_TABLE =
            RecipeType.createRecipeHolderType(MiddleEarth.of("inscription_table"));

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper gui = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
                new ArtisanCategory(gui),
                new ForgeCategory(gui),
                new AnvilCategory(gui),
                new InscriptionCategory(gui)
        );
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        var crafting = registration.getCraftingCategory();
        crafting.addExtension(BackAttachmentRecipe.class, new DynamicCraftingExtension<>());
        crafting.addExtension(BackAttachmentRemovalRecipe.class, new DynamicCraftingExtension<>());
        crafting.addExtension(HelmetAttachmentRecipe.class, new DynamicCraftingExtension<>());
        crafting.addExtension(HelmetAttachmentRemovalRecipe.class, new DynamicCraftingExtension<>());
        crafting.addExtension(MountArmorAddonRemovalRecipe.class, new DynamicCraftingExtension<>());
        crafting.addExtension(MountArmorSideSkullAddonRecipe.class, new DynamicCraftingExtension<>());
        crafting.addExtension(MountArmorTopSkullAddonRecipe.class, new DynamicCraftingExtension<>());
        crafting.addExtension(CustomItemDecorationRecipe.class, new DynamicCraftingExtension<>());
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            return;
        }

        RecipeManager recipes = minecraft.level.getRecipeManager();
        List<RecipeHolder<ArtisanRecipe>> artisanRecipes = recipes.getAllRecipesFor(RecipesME.ARTISAN_TABLE);
        List<RecipeHolder<AlloyingRecipe>> forgeRecipes = recipes.getAllRecipesFor(RecipesME.FORGE);
        List<RecipeHolder<AnvilShapingRecipe>> anvilRecipes = recipes.getAllRecipesFor(RecipesME.ANVIL_SHAPING);
        List<RecipeHolder<InscriptionRecipe>> inscriptionRecipes = recipes.getAllRecipesFor(RecipesME.INSCRIPTION_TABLE);

        registration.addRecipes(ARTISAN_TABLE, artisanRecipes);
        registration.addRecipes(FORGE, forgeRecipes);
        registration.addRecipes(ANVIL_SHAPING, anvilRecipes);
        registration.addRecipes(INSCRIPTION_TABLE, inscriptionRecipes);

        cacheDrawText(artisanRecipes, forgeRecipes, anvilRecipes, inscriptionRecipes);
        long dynamicCraftingCount = recipes.getAllRecipesFor(net.minecraft.world.item.crafting.RecipeType.CRAFTING)
                .stream()
                .map(RecipeHolder::value)
                .filter(DynamicCraftingRecipeDisplay::supports)
                .count();
        MiddleEarth.LOGGER.logInfoMsg((
                "JEI recipe viewer registered: artisan=%d, inscription=%d, anvil=%d, forge=%d, dynamic_crafting=%d"
        ).formatted(artisanRecipes.size(), inscriptionRecipes.size(), anvilRecipes.size(),
                forgeRecipes.size(), dynamicCraftingCount));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalysts(ARTISAN_TABLE,
                ModDecorativeBlocks.ARTISAN_TABLE,
                ModDecorativeBlocks.ORCISH_ARTISAN_TABLE);
        registration.addRecipeCatalysts(FORGE, ModDecorativeBlocks.FORGE);
        registration.addRecipeCatalysts(ANVIL_SHAPING,
                ModDecorativeBlocks.TREATED_ANVIL,
                ModDecorativeBlocks.DWARVEN_TREATED_ANVIL,
                ModDecorativeBlocks.ELVEN_TREATED_ANVIL,
                ModDecorativeBlocks.ORCISH_TREATED_ANVIL);
        registration.addRecipeCatalysts(INSCRIPTION_TABLE, ModDecorativeBlocks.INSCRIPTION_TABLE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(ArtisanTableScreen.class, 75, 30, 20, 30, ARTISAN_TABLE);
        registration.addRecipeClickArea(ForgeAlloyingScreen.class, 75, 30, 20, 30, FORGE);
    }

    private static void cacheDrawText(List<RecipeHolder<ArtisanRecipe>> artisanRecipes,
                                      List<RecipeHolder<AlloyingRecipe>> forgeRecipes,
                                      List<RecipeHolder<AnvilShapingRecipe>> anvilRecipes,
                                      List<RecipeHolder<InscriptionRecipe>> inscriptionRecipes) {
        DRAW_TEXT.clear();
        for (RecipeHolder<ArtisanRecipe> holder : artisanRecipes) {
            DRAW_TEXT.put(holder.id(), new DrawText(Component.translatable(
                    "screen." + MiddleEarth.MOD_ID + ".artisan_table." + holder.value().category), null));
        }
        for (RecipeHolder<AlloyingRecipe> holder : forgeRecipes) {
            DRAW_TEXT.put(holder.id(), new DrawText(
                    Component.translatable("recipe." + MiddleEarth.MOD_ID + ".metal_units",
                            holder.value().getAmount()),
                    Component.translatable("recipe." + MiddleEarth.MOD_ID + ".ingot_equivalent",
                            RecipeViewerHelper.getIngotEquivalent(holder.value()))));
        }
        for (RecipeHolder<AnvilShapingRecipe> holder : anvilRecipes) {
            DRAW_TEXT.put(holder.id(), new DrawText(Component.translatable(
                    "recipe." + MiddleEarth.MOD_ID + ".hammer_strikes", holder.value().getAmount()), null));
        }
        for (RecipeHolder<InscriptionRecipe> holder : inscriptionRecipes) {
            DRAW_TEXT.put(holder.id(), new DrawText(
                    Component.literal(String.join(" / ", holder.value().inputWords)),
                    Component.translatable("recipe." + MiddleEarth.MOD_ID + ".level_cost",
                            holder.value().levelCost)));
        }
    }

    private static final class DynamicCraftingExtension<R extends CraftingRecipe>
            implements ICraftingCategoryExtension<R> {
        private DynamicCraftingRecipeDisplay.Display display;

        @Override
        public void setRecipe(RecipeHolder<R> holder, IRecipeLayoutBuilder builder,
                              ICraftingGridHelper craftingGridHelper, IFocusGroup focuses) {
            DynamicCraftingRecipeDisplay.Display display = this.display(holder.value());
            builder.setShapeless();
            craftingGridHelper.createAndSetInputs(builder, display.inputs(), display.width(), display.height());
            craftingGridHelper.createAndSetOutputs(builder, display.outputs());
        }

        @Override
        public int getWidth(RecipeHolder<R> holder) {
            return this.display(holder.value()).width();
        }

        @Override
        public int getHeight(RecipeHolder<R> holder) {
            return this.display(holder.value()).height();
        }

        private DynamicCraftingRecipeDisplay.Display display(R recipe) {
            if (this.display == null) {
                this.display = DynamicCraftingRecipeDisplay.create(recipe);
            }
            return this.display;
        }
    }

    private record DrawText(Component primary, Component secondary) {
    }

    private abstract static class HolderCategory<R extends Recipe<?>>
            implements IRecipeCategory<RecipeHolder<R>> {
        private final RecipeType<RecipeHolder<R>> recipeType;
        private final Component title;
        private final IDrawable icon;
        private final int width;
        private final int height;

        private HolderCategory(RecipeType<RecipeHolder<R>> recipeType, Component title,
                               IDrawable icon, int width, int height) {
            this.recipeType = recipeType;
            this.title = title;
            this.icon = icon;
            this.width = width;
            this.height = height;
        }

        @Override
        public RecipeType<RecipeHolder<R>> getRecipeType() {
            return this.recipeType;
        }

        @Override
        public Component getTitle() {
            return this.title;
        }

        @Override
        public IDrawable getIcon() {
            return this.icon;
        }

        @Override
        public int getWidth() {
            return this.width;
        }

        @Override
        public int getHeight() {
            return this.height;
        }

        @Override
        public ResourceLocation getRegistryName(RecipeHolder<R> recipe) {
            return recipe.id();
        }
    }

    private static final class ArtisanCategory extends HolderCategory<ArtisanRecipe> {
        private ArtisanCategory(IGuiHelper gui) {
            super(ARTISAN_TABLE,
                    Component.translatable("container." + MiddleEarth.MOD_ID + ".artisan_table"),
                    gui.createDrawableItemLike(ModDecorativeBlocks.ARTISAN_TABLE), 146, 70);
        }

        @Override
        public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<ArtisanRecipe> holder,
                              IFocusGroup focuses) {
            ArtisanRecipe recipe = holder.value();
            ArtisanTableInputsShape shape = ArtisanTableInputsShape.getShape(recipe.category);
            int ingredientIndex = 0;
            if (shape != null) {
                for (int y = 0; y < 3; y++) {
                    for (int x = 0; x < 3; x++) {
                        if (shape.getInputType(x, y) != InputType.NONE
                                && ingredientIndex < recipe.inputs.size()) {
                            builder.addInputSlot(5 + 18 * x, 8 + 18 * y)
                                    .addIngredients(recipe.inputs.get(ingredientIndex++));
                        }
                    }
                }
            }
            builder.addOutputSlot(123, 26).addItemStack(recipe.getOutput());
        }

        @Override
        public void draw(RecipeHolder<ArtisanRecipe> holder, IRecipeSlotsView slots,
                         GuiGraphics graphics, double mouseX, double mouseY) {
            DrawText text = DRAW_TEXT.get(holder.id());
            if (text != null) {
                graphics.drawString(Minecraft.getInstance().font, text.primary(), 65, 8, 0xFF404040, false);
            }
        }
    }

    private static final class ForgeCategory extends HolderCategory<AlloyingRecipe> {
        private final IDrawable arrow;

        private ForgeCategory(IGuiHelper gui) {
            super(FORGE, Component.translatable("screen." + MiddleEarth.MOD_ID + ".forge"),
                    gui.createDrawableItemLike(ModDecorativeBlocks.FORGE), 138, 64);
            this.arrow = gui.getRecipeArrow();
        }

        @Override
        public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<AlloyingRecipe> holder,
                              IFocusGroup focuses) {
            List<net.minecraft.world.item.crafting.Ingredient> inputs = holder.value().getIngredients();
            for (int index = 0; index < inputs.size(); index++) {
                builder.addInputSlot(3 + 18 * index, 7).addIngredients(inputs.get(index));
            }
            ItemStack output = RecipeViewerHelper.getAlloyOutput(holder.value());
            if (!output.isEmpty()) {
                builder.addOutputSlot(117, 7).addItemStack(output);
            }
        }

        @Override
        public void draw(RecipeHolder<AlloyingRecipe> holder, IRecipeSlotsView slots,
                         GuiGraphics graphics, double mouseX, double mouseY) {
            this.arrow.draw(graphics, 80, 7);
            DrawText text = DRAW_TEXT.get(holder.id());
            if (text != null) {
                graphics.drawString(Minecraft.getInstance().font,
                        text.primary(), 4, 34, 0xFF404040, false);
                graphics.drawString(Minecraft.getInstance().font,
                        text.secondary(), 4, 46, 0xFF404040, false);
            }
        }
    }

    private static final class AnvilCategory extends HolderCategory<AnvilShapingRecipe> {
        private final IDrawable arrow;

        private AnvilCategory(IGuiHelper gui) {
            super(ANVIL_SHAPING, Component.translatable("emi.category." + MiddleEarth.MOD_ID + ".anvil_shaping"),
                    gui.createDrawableItemLike(ModDecorativeBlocks.TREATED_ANVIL), 94, 50);
            this.arrow = gui.getRecipeArrow();
        }

        @Override
        public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<AnvilShapingRecipe> holder,
                              IFocusGroup focuses) {
            builder.addInputSlot(3, 7).addIngredients(holder.value().getIngredient());
            builder.addOutputSlot(73, 7).addItemStack(holder.value().getOutput());
        }

        @Override
        public void draw(RecipeHolder<AnvilShapingRecipe> holder, IRecipeSlotsView slots,
                         GuiGraphics graphics, double mouseX, double mouseY) {
            this.arrow.draw(graphics, 35, 7);
            DrawText text = DRAW_TEXT.get(holder.id());
            if (text != null) {
                graphics.drawString(Minecraft.getInstance().font,
                        text.primary(), 4, 36, 0xFF404040, false);
            }
        }
    }

    private static final class InscriptionCategory extends HolderCategory<InscriptionRecipe> {
        private final IDrawable arrow;

        private InscriptionCategory(IGuiHelper gui) {
            super(INSCRIPTION_TABLE, Component.translatable("block." + MiddleEarth.MOD_ID + ".inscription_table"),
                    gui.createDrawableItemLike(ModDecorativeBlocks.INSCRIPTION_TABLE), 138, 66);
            this.arrow = gui.getRecipeArrow();
        }

        @Override
        public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<InscriptionRecipe> holder,
                              IFocusGroup focuses) {
            InscriptionRecipe recipe = holder.value();
            builder.addInputSlot(3, 7).addIngredients(RecipeViewerHelper.getInscriptionCatalyst(recipe));
            builder.addInputSlot(27, 7).addIngredients(recipe.inputChisel);
            builder.addOutputSlot(117, 7).addItemStack(RecipeViewerHelper.getInscriptionOutput(recipe));
        }

        @Override
        public void draw(RecipeHolder<InscriptionRecipe> holder, IRecipeSlotsView slots,
                         GuiGraphics graphics, double mouseX, double mouseY) {
            this.arrow.draw(graphics, 75, 7);
            DrawText text = DRAW_TEXT.get(holder.id());
            if (text != null) {
                graphics.drawString(Minecraft.getInstance().font,
                        text.primary(), 4, 34, 0xFF404040, false);
                graphics.drawString(Minecraft.getInstance().font,
                        text.secondary(), 4, 48, 0xFF404040, false);
            }
        }
    }
}
