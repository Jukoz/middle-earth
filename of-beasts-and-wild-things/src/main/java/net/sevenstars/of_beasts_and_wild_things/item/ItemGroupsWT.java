package net.sevenstars.of_beasts_and_wild_things.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;

import java.util.LinkedList;
import java.util.List;

public class ItemGroupsWT {
    public static final List<ItemStack> BLOCKS_CONTENTS = new LinkedList<>();
    public static final List<ItemStack> ITEMS_CONTENTS = new LinkedList<>();
    public static final List<ItemStack> SPAWN_EGGS_CONTENTS = new LinkedList<>();

    public static final ItemGroup WILD_THINGS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + OfBeastsAndWildThings.MOD_ID + ".wild_things"))
            .icon(() -> new ItemStack(EggItemsWT.DEER_SPAWN_EGG))
            .entries((displayContext, entries) -> {
                for (ItemStack item : BLOCKS_CONTENTS) {
                    entries.add(item);
                }
                for (ItemStack item : ITEMS_CONTENTS) {
                    entries.add(item);
                }
                for (ItemStack item : SPAWN_EGGS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, OfBeastsAndWildThings.of("wild_things"), WILD_THINGS);
    }

}
