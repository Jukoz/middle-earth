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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.compat.REICommonPluginME;
import net.sevenstars.middleearth.gui.forge.ForgeAlloyingScreen;

import java.util.LinkedList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class AlloyingCategory implements DisplayCategory<AlloyingDisplay> {
    public static final Identifier TEXTURE = MiddleEarth.of('/', "textures", "gui", "forge_rei.png");

    @Override
    public CategoryIdentifier<? extends AlloyingDisplay> getCategoryIdentifier() {
        return REICommonPluginME.FORGE_CATEGORY;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("screen." + MiddleEarth.MOD_ID + ".forge");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModDecorativeBlocks.FORGE.asItem().getDefaultStack());
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
            Slot slot = Widgets.createSlot(new Point(startPoint.x + 41 + 18*x, startPoint.y + 16));
            if(display.getInputEntries().size() > x) slot.markOutput().entries(display.getInputEntries().get(x));
            widgets.add(slot);
        }

        widgets.add(Widgets.createTexturedWidget(TEXTURE,startPoint.x + 66, startPoint.y + 44, 218, 14, 20, 15));
        int storedLiquid = (int) (Math.min(1.0f, (float)display.amount / 576) * ForgeAlloyingScreen.LIQUID_HEIGHT);
        widgets.add(Widgets.createTexturedWidget(TEXTURE,startPoint.x + 106, startPoint.y + 75 - storedLiquid, 211, 76 - storedLiquid, 20, storedLiquid));

        widgets.add(Widgets.createLabel(new Point(startPoint.x + 77, startPoint.y + 5),
                Text.translatable("trim_material." + MiddleEarth.MOD_ID + "." + display.output)));

        return widgets;
    }
}
