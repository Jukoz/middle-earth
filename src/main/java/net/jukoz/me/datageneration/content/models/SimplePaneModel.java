package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimplePaneModel {
    public record Pane(Block glass, Block pane) {}
    public static List<Pane> panes = new ArrayList<>() {
        {
            add(new Pane(ModBlocks.LEAD_GLASS, ModBlocks.LEAD_GLASS_PANE));
            add(new Pane(ModBlocks.BLUE_STAINED_LEAD_GLASS, ModBlocks.BLUE_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModBlocks.BLACK_STAINED_LEAD_GLASS, ModBlocks.BLACK_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModBlocks.BROWN_STAINED_LEAD_GLASS, ModBlocks.BROWN_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModBlocks.CYAN_STAINED_LEAD_GLASS, ModBlocks.CYAN_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModBlocks.GRAY_STAINED_LEAD_GLASS, ModBlocks.GRAY_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModBlocks.GREEN_STAINED_LEAD_GLASS, ModBlocks.GREEN_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModBlocks.LIGHT_BLUE_STAINED_LEAD_GLASS, ModBlocks.LIGHT_BLUE_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModBlocks.LIGHT_GRAY_STAINED_LEAD_GLASS, ModBlocks.LIGHT_GRAY_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModBlocks.LIME_STAINED_LEAD_GLASS, ModBlocks.LIME_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModBlocks.MAGENTA_STAINED_LEAD_GLASS, ModBlocks.MAGENTA_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModBlocks.ORANGE_STAINED_LEAD_GLASS, ModBlocks.ORANGE_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModBlocks.PINK_STAINED_LEAD_GLASS, ModBlocks.PINK_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModBlocks.PURPLE_STAINED_LEAD_GLASS, ModBlocks.PURPLE_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModBlocks.RED_STAINED_LEAD_GLASS, ModBlocks.RED_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModBlocks.WHITE_STAINED_LEAD_GLASS, ModBlocks.WHITE_STAINED_LEAD_GLASS_PANE));
            add(new Pane(ModBlocks.YELLOW_STAINED_LEAD_GLASS, ModBlocks.YELLOW_STAINED_LEAD_GLASS_PANE));
        }
    };
}
