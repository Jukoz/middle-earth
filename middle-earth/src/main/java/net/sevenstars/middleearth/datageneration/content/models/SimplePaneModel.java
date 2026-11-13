package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimplePaneModel {
    public record Pane(Block glass, Block pane) {}
    public static List<Pane> panes = new ArrayList<>() {
        {
            add(new Pane(DecorativeBlockRegistryME.WOOD_FRAMED_WINDOW, DecorativeBlockRegistryME.WOOD_FRAMED_WINDOW_PANE));
            add(new Pane(DecorativeBlockRegistryME.AGED_WOOD_WINDOW, DecorativeBlockRegistryME.AGED_WOOD_WINDOW_PANE));
            add(new Pane(DecorativeBlockRegistryME.SIMPLE_OAK_WINDOW, DecorativeBlockRegistryME.SIMPLE_OAK_WINDOW_PANE));
            add(new Pane(DecorativeBlockRegistryME.WATTLE_AND_BRICK_WINDOW, DecorativeBlockRegistryME.WATTLE_AND_BRICK_WINDOW_PANE));
            add(new Pane(DecorativeBlockRegistryME.WATTLE_FRAMED_WINDOW, DecorativeBlockRegistryME.WATTLE_FRAMED_WINDOW_PANE));
            add(new Pane(DecorativeBlockRegistryME.DARK_WATTLE_FRAMED_WINDOW, DecorativeBlockRegistryME.DARK_WATTLE_FRAMED_WINDOW_PANE));
            add(new Pane(DecorativeBlockRegistryME.BLACK_WATTLE_FRAMED_WINDOW, DecorativeBlockRegistryME.BLACK_WATTLE_FRAMED_WINDOW_PANE));
            add(new Pane(DecorativeBlockRegistryME.GREEN_WATTLE_FRAMED_WINDOW, DecorativeBlockRegistryME.GREEN_WATTLE_FRAMED_WINDOW_PANE));
            add(new Pane(DecorativeBlockRegistryME.RED_WATTLE_FRAMED_WINDOW, DecorativeBlockRegistryME.RED_WATTLE_FRAMED_WINDOW_PANE));
            add(new Pane(DecorativeBlockRegistryME.WHITE_WATTLE_FRAMED_WINDOW, DecorativeBlockRegistryME.WHITE_WATTLE_FRAMED_WINDOW_PANE));
            add(new Pane(DecorativeBlockRegistryME.WHITE_DAUB_HOBBIT_WINDOW, DecorativeBlockRegistryME.WHITE_DAUB_HOBBIT_WINDOW_PANE));
            add(new Pane(DecorativeBlockRegistryME.PLASTER_HOBBIT_WINDOW, DecorativeBlockRegistryME.PLASTER_HOBBIT_WINDOW_PANE));
            add(new Pane(DecorativeBlockRegistryME.PLASTER_ROUND_WINDOW, DecorativeBlockRegistryME.PLASTER_ROUND_WINDOW_PANE));
            add(new Pane(DecorativeBlockRegistryME.YELLOW_DAUB_HOBBIT_WINDOW, DecorativeBlockRegistryME.YELLOW_DAUB_HOBBIT_WINDOW_PANE));
            add(new Pane(DecorativeBlockRegistryME.MUD_BRICK_ROUND_WINDOW, DecorativeBlockRegistryME.MUD_BRICK_ROUND_WINDOW_PANE));
            add(new Pane(DecorativeBlockRegistryME.WHITE_DAUB_ROUND_WINDOW, DecorativeBlockRegistryME.WHITE_DAUB_ROUND_WINDOW_PANE));
            add(new Pane(DecorativeBlockRegistryME.YELLOW_DAUB_ROUND_WINDOW, DecorativeBlockRegistryME.YELLOW_DAUB_ROUND_WINDOW_PANE));

            add(new Pane(DecorativeBlockRegistryME.LEAD_GLASS, DecorativeBlockRegistryME.LEAD_GLASS_PANE));
            add(new Pane(DecorativeBlockRegistryME.BLUE_STAINED_LEAD_GLASS, DecorativeBlockRegistryME.BLUE_STAINED_LEAD_GLASS_PANE));
            add(new Pane(DecorativeBlockRegistryME.BLACK_STAINED_LEAD_GLASS, DecorativeBlockRegistryME.BLACK_STAINED_LEAD_GLASS_PANE));
            add(new Pane(DecorativeBlockRegistryME.BROWN_STAINED_LEAD_GLASS, DecorativeBlockRegistryME.BROWN_STAINED_LEAD_GLASS_PANE));
            add(new Pane(DecorativeBlockRegistryME.CYAN_STAINED_LEAD_GLASS, DecorativeBlockRegistryME.CYAN_STAINED_LEAD_GLASS_PANE));
            add(new Pane(DecorativeBlockRegistryME.GRAY_STAINED_LEAD_GLASS, DecorativeBlockRegistryME.GRAY_STAINED_LEAD_GLASS_PANE));
            add(new Pane(DecorativeBlockRegistryME.GREEN_STAINED_LEAD_GLASS, DecorativeBlockRegistryME.GREEN_STAINED_LEAD_GLASS_PANE));
            add(new Pane(DecorativeBlockRegistryME.LIGHT_BLUE_STAINED_LEAD_GLASS, DecorativeBlockRegistryME.LIGHT_BLUE_STAINED_LEAD_GLASS_PANE));
            add(new Pane(DecorativeBlockRegistryME.LIGHT_GRAY_STAINED_LEAD_GLASS, DecorativeBlockRegistryME.LIGHT_GRAY_STAINED_LEAD_GLASS_PANE));
            add(new Pane(DecorativeBlockRegistryME.LIME_STAINED_LEAD_GLASS, DecorativeBlockRegistryME.LIME_STAINED_LEAD_GLASS_PANE));
            add(new Pane(DecorativeBlockRegistryME.MAGENTA_STAINED_LEAD_GLASS, DecorativeBlockRegistryME.MAGENTA_STAINED_LEAD_GLASS_PANE));
            add(new Pane(DecorativeBlockRegistryME.ORANGE_STAINED_LEAD_GLASS, DecorativeBlockRegistryME.ORANGE_STAINED_LEAD_GLASS_PANE));
            add(new Pane(DecorativeBlockRegistryME.PINK_STAINED_LEAD_GLASS, DecorativeBlockRegistryME.PINK_STAINED_LEAD_GLASS_PANE));
            add(new Pane(DecorativeBlockRegistryME.PURPLE_STAINED_LEAD_GLASS, DecorativeBlockRegistryME.PURPLE_STAINED_LEAD_GLASS_PANE));
            add(new Pane(DecorativeBlockRegistryME.RED_STAINED_LEAD_GLASS, DecorativeBlockRegistryME.RED_STAINED_LEAD_GLASS_PANE));
            add(new Pane(DecorativeBlockRegistryME.WHITE_STAINED_LEAD_GLASS, DecorativeBlockRegistryME.WHITE_STAINED_LEAD_GLASS_PANE));
            add(new Pane(DecorativeBlockRegistryME.YELLOW_STAINED_LEAD_GLASS, DecorativeBlockRegistryME.YELLOW_STAINED_LEAD_GLASS_PANE));
        }
    };
}
