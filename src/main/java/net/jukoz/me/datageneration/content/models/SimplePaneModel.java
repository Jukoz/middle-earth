package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModDecorativeBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimplePaneModel {
    public record Pane(Block glass, Block pane) {}
    public static List<Pane> panes = new ArrayList<>() {
        {
            add(new Pane(ModDecorativeBlocks.WOOD_FRAMED_WINDOW, ModDecorativeBlocks.WOOD_FRAMED_WINDOW_PANE));
            add(new Pane(ModDecorativeBlocks.WATTLE_AND_BRICK_WINDOW, ModDecorativeBlocks.WATTLE_AND_BRICK_WINDOW_PANE));
            add(new Pane(ModDecorativeBlocks.WATTLE_FRAMED_WINDOW, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW_PANE));
            add(new Pane(ModDecorativeBlocks.DARK_WATTLE_FRAMED_WINDOW, ModDecorativeBlocks.DARK_WATTLE_FRAMED_WINDOW_PANE));
            add(new Pane(ModDecorativeBlocks.BLACK_WATTLE_FRAMED_WINDOW, ModDecorativeBlocks.BLACK_WATTLE_FRAMED_WINDOW_PANE));
            add(new Pane(ModDecorativeBlocks.GREEN_WATTLE_FRAMED_WINDOW, ModDecorativeBlocks.GREEN_WATTLE_FRAMED_WINDOW_PANE));
            add(new Pane(ModDecorativeBlocks.RED_WATTLE_FRAMED_WINDOW, ModDecorativeBlocks.RED_WATTLE_FRAMED_WINDOW_PANE));
            add(new Pane(ModDecorativeBlocks.WHITE_WATTLE_FRAMED_WINDOW, ModDecorativeBlocks.WHITE_WATTLE_FRAMED_WINDOW_PANE));
            add(new Pane(ModDecorativeBlocks.WHITE_DAUB_HOBBIT_WINDOW, ModDecorativeBlocks.WHITE_DAUB_HOBBIT_WINDOW_PANE));
            add(new Pane(ModDecorativeBlocks.YELLOW_DAUB_HOBBIT_WINDOW, ModDecorativeBlocks.YELLOW_DAUB_HOBBIT_WINDOW_PANE));
            add(new Pane(ModDecorativeBlocks.MEDGON_CARVED_WINDOW, ModDecorativeBlocks.MEDGON_CARVED_WINDOW_PANE));
            add(new Pane(ModDecorativeBlocks.GONLUIN_CARVED_WINDOW, ModDecorativeBlocks.GONLUIN_CARVED_WINDOW_PANE));
            add(new Pane(ModDecorativeBlocks.MUD_BRICK_ROUND_WINDOW, ModDecorativeBlocks.MUD_BRICK_ROUND_WINDOW_PANE));

            add(new Pane(ModDecorativeBlocks.LEAD_GLASS, ModDecorativeBlocks.LEAD_GLASS_PANE));
            add(new Pane(ModDecorativeBlocks.BLUE_STAINED_LEAD_GLASS, ModDecorativeBlocks.BLUE_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModDecorativeBlocks.BLACK_STAINED_LEAD_GLASS, ModDecorativeBlocks.BLACK_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModDecorativeBlocks.BROWN_STAINED_LEAD_GLASS, ModDecorativeBlocks.BROWN_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModDecorativeBlocks.CYAN_STAINED_LEAD_GLASS, ModDecorativeBlocks.CYAN_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModDecorativeBlocks.GRAY_STAINED_LEAD_GLASS, ModDecorativeBlocks.GRAY_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModDecorativeBlocks.GREEN_STAINED_LEAD_GLASS, ModDecorativeBlocks.GREEN_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModDecorativeBlocks.LIGHT_BLUE_STAINED_LEAD_GLASS, ModDecorativeBlocks.LIGHT_BLUE_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModDecorativeBlocks.LIGHT_GRAY_STAINED_LEAD_GLASS, ModDecorativeBlocks.LIGHT_GRAY_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModDecorativeBlocks.LIME_STAINED_LEAD_GLASS, ModDecorativeBlocks.LIME_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModDecorativeBlocks.MAGENTA_STAINED_LEAD_GLASS, ModDecorativeBlocks.MAGENTA_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModDecorativeBlocks.ORANGE_STAINED_LEAD_GLASS, ModDecorativeBlocks.ORANGE_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModDecorativeBlocks.PINK_STAINED_LEAD_GLASS, ModDecorativeBlocks.PINK_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModDecorativeBlocks.PURPLE_STAINED_LEAD_GLASS, ModDecorativeBlocks.PURPLE_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModDecorativeBlocks.RED_STAINED_LEAD_GLASS, ModDecorativeBlocks.RED_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModDecorativeBlocks.WHITE_STAINED_LEAD_GLASS, ModDecorativeBlocks.WHITE_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModDecorativeBlocks.YELLOW_STAINED_LEAD_GLASS, ModDecorativeBlocks.YELLOW_STAINED_LEAD_GLASS_PANE));
        }
    };
}
