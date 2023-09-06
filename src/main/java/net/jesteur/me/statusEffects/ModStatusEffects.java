package net.jesteur.me.statusEffects;

import net.jesteur.me.MiddleEarth;
import net.jesteur.me.entity.trolls.snow.SnowTrollEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModStatusEffects {
    public static final StatusEffect HALLUCINATION = new Hallucination();

    public static void registerStatusEffects(){
        Registry.register(Registries.STATUS_EFFECT, new Identifier(MiddleEarth.MOD_ID, "hallucination"), HALLUCINATION);
    }
}
