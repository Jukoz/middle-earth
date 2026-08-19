package net.sevenstars.middleearth.compat.artisantable;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.compat.REICommonPluginME;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableInputsShape;
import net.sevenstars.middleearth.gui.artisantable.InputType;

import java.util.LinkedList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public final class ArtisanTableCategory implements DisplayCategory<ArtisanTableDisplay> {
    private static final ResourceLocation TEXTURE =
            MiddleEarth.of('/', "textures", "gui", "artisan_table_rei.png");

    @Override
    public CategoryIdentifier<? extends ArtisanTableDisplay> getCategoryIdentifier() {
        return REICommonPluginME.ARTISAN_TABLE_CATEGORY;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container." + MiddleEarth.MOD_ID + ".artisan_table");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModDecorativeBlocks.ARTISAN_TABLE);
    }

    @Override
    public int getDisplayHeight() {
        return 83;
    }

    @Override
    public List<Widget> setupDisplay(ArtisanTableDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 87, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(
                TEXTURE,
                new Rectangle(startPoint.x, startPoint.y, 175, 82)
        ));

        int index = 0;
        ArtisanTableInputsShape inputShape = display.getArtisanTableInputShape();
        if (inputShape != null) {
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3 && index < display.getInputEntries().size(); x++) {
                    InputType inputType = inputShape.getInputType(x, y);
                    if (inputType != InputType.NONE) {
                        widgets.add(Widgets.createSlot(
                                        new Point(startPoint.x + 9 + 18 * x, startPoint.y + 16 + 18 * y)
                                )
                                .markInput()
                                .entries(display.getInputEntries().get(index++)));
                    }
                }
            }
        }

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 128, startPoint.y + 34))
                .markOutput()
                .entries(display.getOutputEntries().get(0)));
        widgets.add(Widgets.createLabel(
                new Point(startPoint.x + 63, startPoint.y + 5),
                Component.translatable(
                        "screen." + MiddleEarth.MOD_ID + ".artisan_table." + display.getCategory()
                )
        ));
        return widgets;
    }
}
