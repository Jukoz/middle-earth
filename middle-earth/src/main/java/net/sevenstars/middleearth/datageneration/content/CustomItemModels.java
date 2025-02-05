package net.sevenstars.middleearth.datageneration.content;

import net.minecraft.client.data.Model;
import net.minecraft.client.data.TextureKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class CustomItemModels {
    public static final Model BIG_WEAPON = new Model(Optional.of(
            Identifier.of(MiddleEarth.MOD_ID, "item/big_weapon")), Optional.empty(), TextureKey.LAYER0);
    public static final Model BOW = new Model(Optional.of(
            Identifier.of(MiddleEarth.MOD_ID, "item/bow")), Optional.empty(), TextureKey.LAYER0);
    public static final Model LONGBOW = new Model(Optional.of(
            Identifier.of(MiddleEarth.MOD_ID, "item/longbow")), Optional.empty(), TextureKey.LAYER0);

    public static final Model CROSSBOW = new Model(Optional.of(
            Identifier.of("item/crossbow")), Optional.empty(), TextureKey.LAYER0);

    public static final Model TEMPLATE_SPAWN_EGG = new Model(Optional.of(
            Identifier.of("minecraft", "item/template_spawn_egg")), Optional.empty());
}
