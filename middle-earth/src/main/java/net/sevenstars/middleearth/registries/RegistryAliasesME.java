package net.sevenstars.middleearth.registries;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class RegistryAliasesME {
    public record Alias(Registry<?> registry, String name) {}
    public record ManualAlias(Registry<?> registry, String oldName, String newName) {}

    public static List<Alias> aliases = new ArrayList<>(){
        {
        }
    };

    public static List<ManualAlias> manualAliases = new ArrayList<>(){
        {
            add(new ManualAlias(BuiltInRegistries.ITEM, "strawberry", "strawberries"));

            add(new ManualAlias(BuiltInRegistries.ITEM, "hanging_cobweb", "hanging_webs"));
            add(new ManualAlias(BuiltInRegistries.BLOCK, "hanging_cobweb", "hanging_webs"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "mirkwood_vines_plant", "mirkwood_vines"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "mossy_stone_brick_vertical_slab", "mossy_stone_brick_vertical_slab"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "mossy_stone_brick_vertical_slab", "mossy_stone_brick_vertical_slab"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "polished_blackstone_brick_vertical_slab", "polished_blackstone_brick_vertical_slab"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "polished_blackstone_brick_vertical_slab", "polished_blackstone_brick_vertical_slab"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "deepslate_brick_vertical_slab", "deepslate_brick_vertical_slab"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "deepslate_brick_vertical_slab", "deepslate_brick_vertical_slab"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "tuff_brick_vertical_slab", "tuff_brick_vertical_slab"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "tuff_brick_vertical_slab", "tuff_brick_vertical_slab"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "brick_vertical_slab", "brick_vertical_slab"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "brick_vertical_slab", "brick_vertical_slab"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "mud_brick_vertical_slab", "mud_brick_vertical_slab"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "mud_brick_vertical_slab", "mud_brick_vertical_slab"));
            
            add(new ManualAlias(BuiltInRegistries.BLOCK, "ashen_stone", "ashenstone"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "ashen_stone", "ashenstone"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "ashen_stone_slab", "ashenstone_slab"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "ashen_stone_slab", "ashenstone_slab"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "ashen_stone_vertical_slab", "ashenstone_vertical_slab"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "ashen_stone_vertical_slab", "ashenstone_vertical_slab"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "ashen_stone_stairs", "ashenstone_stairs"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "ashen_stone_stairs", "ashenstone_stairs"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "ashen_stone_wall", "ashenstone_wall"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "ashen_stone_wall", "ashenstone_wall"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "ashen_stone_button", "ashenstone_button"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "ashen_stone_button", "ashenstone_button"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "ashen_stone_rocks", "ashenstone_rocks"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "ashen_stone_rocks", "ashenstone_rocks"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "ashen_stone_stool", "ashenstone_stool"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "ashen_stone_stool", "ashenstone_stool"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "ashen_stone_chair", "ashenstone_chair"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "ashen_stone_chair", "ashenstone_chair"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "ashen_stone_table", "ashenstone_table"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "ashen_stone_table", "ashenstone_table"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "ashen_stone_trapdoor", "ashenstone_trapdoor"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "ashen_stone_trapdoor", "ashenstone_trapdoor"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "ashen_cobblestone_slab", "cobbled_ashenstone_slab"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "ashen_cobblestone_slab", "cobbled_ashenstone_slab"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "ashen_cobblestone_vertical_slab", "cobbled_ashenstone_vertical_slab"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "ashen_cobblestone_vertical_slab", "cobbled_ashenstone_vertical_slab"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "ashen_cobblestone_wall", "cobbled_ashenstone_wall"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "ashen_cobblestone_wall", "cobbled_ashenstone_wall"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "ashen_cobblestone_stairs", "cobbled_ashenstone_stairs"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "ashen_cobblestone_stairs", "cobbled_ashenstone_stairs"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "polished_ashen_stone_slab", "polished_ashenstone_slab"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "polished_ashen_stone_slab", "polished_ashenstone_slab"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "polished_ashen_stone_vertical_slab", "polished_ashenstone_vertical_slab"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "polished_ashen_stone_vertical_slab", "polished_ashenstone_vertical_slab"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "polished_ashen_stone_stairs", "polished_ashenstone_stairs"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "polished_ashen_stone_stairs", "polished_ashenstone_stairs"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "polished_ashen_stone_wall", "polished_ashenstone_wall"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "polished_ashen_stone_wall", "polished_ashenstone_wall"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "wattle_and_brick_cross", "wattle_and_brick_cross"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "wattle_and_brick_cross", "wattle_and_brick_cross"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "wattle_and_brick_window", "wattle_and_brick_window"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "wattle_and_brick_window", "wattle_and_brick_window"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "wattle_and_brick_window_pane", "wattle_and_brick_window_pane"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "wattle_and_brick_window_pane", "wattle_and_brick_window_pane"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "wattle_and_brick_pillar", "wattle_and_brick_pillar"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "wattle_and_brick_pillar", "wattle_and_brick_pillar"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "wattle_and_brick_left", "wattle_and_brick_left"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "wattle_and_brick_left", "wattle_and_brick_left"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "wattle_and_brick_right", "wattle_and_brick_right"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "wattle_and_brick_right", "wattle_and_brick_right"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "wattle_and_brick_diamond", "wattle_and_brick_diamond"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "wattle_and_brick_diamond", "wattle_and_brick_diamond"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "mud_brick_round_window", "mud_brick_round_window"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "mud_brick_round_window", "mud_brick_round_window"));

            add(new ManualAlias(BuiltInRegistries.BLOCK, "mud_brick_round_window_pane", "mud_brick_round_window_pane"));
            add(new ManualAlias(BuiltInRegistries.ITEM, "mud_brick_round_window_pane", "mud_brick_round_window_pane"));
        }
    };
}
