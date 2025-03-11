package net.sevenstars.of_beasts_and_wild_things.datageneration.content;

import net.minecraft.client.data.Model;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class CustomItemModels {
    public static final Model TEMPLATE_SPAWN_EGG = new Model(Optional.of(
            Identifier.of("minecraft", "item/template_spawn_egg")), Optional.empty());

}
