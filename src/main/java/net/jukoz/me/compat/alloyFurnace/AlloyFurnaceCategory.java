package net.jukoz.me.compat.alloyFurnace;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import java.util.LinkedList;
import java.util.List;

public class AlloyFurnaceCategory implements DisplayCategory<BasicDisplay> {
    public static final Identifier TEXTURE = new Identifier(MiddleEarth.MOD_ID, "textures/gui/alloy_furnace_rei.png");
    public static final CategoryIdentifier<AlloyFurnaceDisplay> ALLOY_FURNACE = CategoryIdentifier.of(MiddleEarth.MOD_ID, "alloy_furnace");

    @Override
    public CategoryIdentifier<? extends BasicDisplay> getCategoryIdentifier() {
        return ALLOY_FURNACE;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("screen." + MiddleEarth.MOD_ID + ".alloy_furnace");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModDecorativeBlocks.ALLOY_FURNACE.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(BasicDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 87, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();

        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 175, 82)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 26, startPoint.y + 17))
                .entries(display.getInputEntries().get(0)));
        if(display.getInputEntries().size() > 1){
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 44, startPoint.y + 17))
                    .entries(display.getInputEntries().get(1)));
        }
        if(display.getInputEntries().size() > 2){
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 62, startPoint.y + 17))
                    .entries(display.getInputEntries().get(2)));
        }
        if(display.getInputEntries().size() > 3){
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 80, startPoint.y + 17))
                    .entries(display.getInputEntries().get(3)));
        }

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 129, startPoint.y + 35))
                .markOutput().entries(display.getOutputEntries().get((0))));

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 83;
    }
}
