package net.sevenstars.middleearth.datageneration.content;

import net.minecraft.client.data.Model;
import net.minecraft.client.data.TextureKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class CustomItemModels {
    public static final Model BIG_WEAPON = new Model(Optional.of(
            MiddleEarth.of("item/big_weapon")), Optional.empty(), TextureKey.LAYER0);
    public static final Model BIG_WEAPON_STAFF = new Model(Optional.of(
            MiddleEarth.of("item/big_weapon_staff")), Optional.empty(), TextureKey.LAYER0);
    public static final Model BIG_WEAPON_BLOCKING = new Model(Optional.of(
            MiddleEarth.of("item/big_weapon_blocking")), Optional.of("_blocking"), TextureKey.LAYER0);
    public static final Model BOW = new Model(Optional.of(
            MiddleEarth.of("item/bow")), Optional.empty(), TextureKey.LAYER0);
    public static final Model LONGBOW = new Model(Optional.of(
            MiddleEarth.of("item/longbow")), Optional.empty(), TextureKey.LAYER0);
    public static final Model DAGGER_STRIKE = new Model(Optional.of(
            MiddleEarth.of("item/dagger_strike")), Optional.empty(), TextureKey.LAYER0);

    public static final Model CROSSBOW = new Model(Optional.of(
            Identifier.of("item/crossbow")), Optional.empty(), TextureKey.LAYER0);
}
