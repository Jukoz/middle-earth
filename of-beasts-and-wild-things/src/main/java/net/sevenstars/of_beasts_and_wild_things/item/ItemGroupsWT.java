package net.sevenstars.of_beasts_and_wild_things.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.api.registries.RegistrationBridge;

import java.util.LinkedList;
import java.util.List;

public class ItemGroupsWT {
    public static final List<ItemStack> BLOCKS_CONTENTS = new LinkedList<>();
    public static final List<ItemStack> ITEMS_CONTENTS = new LinkedList<>();
    public static final List<ItemStack> SPAWN_EGGS_CONTENTS = new LinkedList<>();

    public static final CreativeModeTab WILD_THINGS = CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + OfBeastsAndWildThings.MOD_ID + ".wild_things"))
            .icon(() -> new ItemStack(EggItemsWT.DEER_SPAWN_EGG))
            .displayItems((displayContext, entries) -> {
                for (ItemStack item : BLOCKS_CONTENTS) {
                    entries.accept(item);
                }
                for (ItemStack item : ITEMS_CONTENTS) {
                    entries.accept(item);
                }
                for (ItemStack item : SPAWN_EGGS_CONTENTS) {
                    entries.accept(item);
                }
            })
            .build();

    public static void register() {
        RegistrationBridge.register(BuiltInRegistries.CREATIVE_MODE_TAB, OfBeastsAndWildThings.of("wild_things"), WILD_THINGS);
    }

}
