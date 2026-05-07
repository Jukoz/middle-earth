package net.sevenstars.middleearth.compat.artisantable;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.gui.artisantable.InputType;

import java.util.LinkedList;
import java.util.List;

public class ArtisanTableCategory implements DisplayCategory<ArtisanTableDisplay> {
    public static final Identifier TEXTURE = MiddleEarth.of('/', "textures", "gui", "artisan_table_rei.png");
    public static final CategoryIdentifier<ArtisanTableDisplay> ARTISAN_TABLE = CategoryIdentifier.of(MiddleEarth.MOD_ID, "artisan_table");

    @Override
    public CategoryIdentifier<? extends ArtisanTableDisplay> getCategoryIdentifier() {
        return ARTISAN_TABLE;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("container." + MiddleEarth.MOD_ID + ".artisan_table");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModDecorativeBlocks.ARTISAN_TABLE.asItem().getDefaultStack());
    }

    @Override
    public int getDisplayHeight() {
        return 83;
    }

    @Override
    public List<Widget> setupDisplay(ArtisanTableDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 87, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();

        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 175, 82)));

        int index = 0;
        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 3; x++) {
                if (index < display.getInputEntries().size()){
                    if(display.getArtisanTableInputShape() != null) {
                        InputType inputType = display.getArtisanTableInputShape().getInputType(x,y);
                        if (inputType != InputType.NONE){
                            widgets.add(Widgets.createSlot(new Point(startPoint.x + 9 + 18*x, startPoint.y + 16 + 18*y))
                                    .markOutput().entries(display.getInputEntries().get(index++)));
                        }
                    }
                }
            }
        }
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 128, startPoint.y + 34))
                .markOutput().entries(display.getOutputEntries().get((0))));

        widgets.add(Widgets.createLabel(new Point(startPoint.x + 44, startPoint.y + 5),
                Text.translatable("screen." + MiddleEarth.MOD_ID +".artisan_table." + display.getCategory())));

        return widgets;
    }
}
