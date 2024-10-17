package net.jukoz.me.recipe;

import net.jukoz.me.MiddleEarth;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static TagKey<Item> DYEABLE = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "dyeable"));
}
