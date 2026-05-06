package net.sevenstars.middleearth.compat.forge;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;

import java.util.LinkedList;
import java.util.List;

public class AlloyingCategory implements DisplayCategory<AlloyingDisplay> {
    public static final Identifier TEXTURE = MiddleEarth.of('/', "textures", "gui", "forge.png");
    public static final CategoryIdentifier<AlloyingDisplay> FORGE = CategoryIdentifier.of(MiddleEarth.MOD_ID, "forge");

    @Override
    public CategoryIdentifier<? extends AlloyingDisplay> getCategoryIdentifier() {
        return FORGE;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("container." + MiddleEarth.MOD_ID + ".forge");
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
    public List<Widget> setupDisplay(AlloyingDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 87, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();

        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 175, 82)));

        for(int x = 0; x < 4; x++) {
            Slot slot = Widgets.createSlot(new Point(startPoint.x + 17 + 18*x, startPoint.y + 16));
            if(display.getInputEntries().size() > x) slot.markOutput().entries(display.getInputEntries().get(x));
            widgets.add(slot);
        }

        widgets.add(Widgets.createLabel(new Point(startPoint.x + 44, startPoint.y + 5),
                Text.translatable("trim_material." + MiddleEarth.MOD_ID + "." + display.output)));

        return widgets;
    }
}
