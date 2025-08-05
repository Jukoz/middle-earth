package net.sevenstars.of_beasts_and_wild_things.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.block.ModBlocks;

import java.util.LinkedList;
import java.util.List;

public class ModItemGroups {


    public static final List<ItemStack> BLOCKS_CONTENTS = new LinkedList<>();
    public static final List<ItemStack> ITEMS_CONTENTS = new LinkedList<>();
    public static final List<ItemStack> SPAWN_EGGS_CONTENTS = new LinkedList<>();

    public static final ItemGroup BLOCKS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + OfBeastsAndWildThings.MOD_ID + ".blocks"))
            .icon(() -> new ItemStack(ModBlocks.BIRD_NEST))
            .entries((displayContext, entries) -> {
                for (ItemStack item : BLOCKS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final ItemGroup ITEMS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + OfBeastsAndWildThings.MOD_ID + ".items"))
            .icon(() -> new ItemStack(ModItems.SWAN_FEATHER))
            .entries((displayContext, entries) -> {
                for (ItemStack item : ITEMS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final ItemGroup SPAWN_EGGS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + OfBeastsAndWildThings.MOD_ID + ".spawn_egg_items"))
            .icon(() -> new ItemStack(ModEggItems.DEER_SPAWN_EGG))
            .entries((displayContext, entries) -> {
                for (ItemStack item : SPAWN_EGGS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, Identifier.of(OfBeastsAndWildThings.MOD_ID, "blocks"), BLOCKS);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(OfBeastsAndWildThings.MOD_ID, "items"), ITEMS);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(OfBeastsAndWildThings.MOD_ID, "spawn_egg_items"), SPAWN_EGGS);
    }

}
