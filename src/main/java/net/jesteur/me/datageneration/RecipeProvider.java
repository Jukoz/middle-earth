package net.jesteur.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.jesteur.me.datageneration.content.multi.WoodMulti;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.server.recipe.RecipeProvider {

    protected RecipeProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        for (WoodMulti.WoodRecord record : WoodMulti.woods) {
            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, record.wood(), 3)
                    .pattern("ll")
                    .pattern("ll")
                    .input('l', record.log())
                    .criterion(FabricRecipeProvider.hasItem(record.log()), FabricRecipeProvider.conditionsFromItem(record.log()))
                    .offerTo(exporter);

        }
    }
}
