package net.sevenstars.middleearth.datageneration.content;

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import java.util.Optional;

public class CustomItemModels {
    public static final ModelTemplate BIG_WEAPON = new ModelTemplate(Optional.of(
            MiddleEarth.of("item/big_weapon")), Optional.empty(), TextureSlot.LAYER0);
    public static final ModelTemplate BIG_WEAPON_STAFF = new ModelTemplate(Optional.of(
            MiddleEarth.of("item/big_weapon_staff")), Optional.empty(), TextureSlot.LAYER0);
    public static final ModelTemplate BIG_WEAPON_BLOCKING = new ModelTemplate(Optional.of(
            MiddleEarth.of("item/big_weapon_blocking")), Optional.of("_blocking"), TextureSlot.LAYER0);
    public static final ModelTemplate BOW = new ModelTemplate(Optional.of(
            MiddleEarth.of("item/bow")), Optional.empty(), TextureSlot.LAYER0);
    public static final ModelTemplate LONGBOW = new ModelTemplate(Optional.of(
            MiddleEarth.of("item/longbow")), Optional.empty(), TextureSlot.LAYER0);
    public static final ModelTemplate DAGGER_STRIKE = new ModelTemplate(Optional.of(
            MiddleEarth.of("item/dagger_strike")), Optional.empty(), TextureSlot.LAYER0);

    public static final ModelTemplate CROSSBOW = new ModelTemplate(Optional.of(
            ResourceLocation.parse("item/crossbow")), Optional.empty(), TextureSlot.LAYER0);
}
