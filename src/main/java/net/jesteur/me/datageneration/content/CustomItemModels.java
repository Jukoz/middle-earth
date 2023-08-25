package net.jesteur.me.datageneration.content;

import net.jesteur.me.MiddleEarth;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class CustomItemModels {
    public static final Model BIG_WEAPON = new Model(Optional.of(
            new Identifier(MiddleEarth.MOD_ID, "item/big_weapon")), Optional.empty(), TextureKey.LAYER0);
}
