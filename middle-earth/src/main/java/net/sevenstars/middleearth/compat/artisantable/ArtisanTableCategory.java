package net.sevenstars.middleearth.compat.artisantable;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;

import java.util.LinkedList;
import java.util.List;

public class ArtisanTableCategory implements DisplayCategory<ArtisanTableDisplay> {
    public static final Identifier TEXTURE = MiddleEarth.of('/', "textures", "gui", "artisan_table.png");
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

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 31, startPoint.y + 14))
                .entries(display.getInputEntries().get(0)));
        if(display.getInputEntries().size() > 1){
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 12, startPoint.y + 34))
                    .entries(display.getInputEntries().get(1)));
        }
        if(display.getInputEntries().size() > 2){
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 31, startPoint.y + 34))
                    .entries(display.getInputEntries().get(2)));
        }
        if(display.getInputEntries().size() > 3){
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 50, startPoint.y + 34))
                    .entries(display.getInputEntries().get(3)));
        }
        if(display.getInputEntries().size() > 4){
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 21, startPoint.y + 53))
                    .entries(display.getInputEntries().get(4)));
        }
        if(display.getInputEntries().size() > 5){
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 41, startPoint.y + 53))
                    .entries(display.getInputEntries().get(5)));
        }

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 128, startPoint.y + 35))
                .markOutput().entries(display.getOutputEntries().get((0))));

        return widgets;
    }
}
