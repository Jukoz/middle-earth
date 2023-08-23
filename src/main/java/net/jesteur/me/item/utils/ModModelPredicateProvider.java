package net.jesteur.me.item.utils;

import net.jesteur.me.item.ModWeaponItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ModModelPredicateProvider {
    public static void registerBowModel() {
        registerBow(ModWeaponItems.DALISH_BOW);
        registerBow(ModWeaponItems.GONDOR_BOW);
        registerBow(ModWeaponItems.LORIEN_BOW);
        registerBow(ModWeaponItems.UMBAR_BOW);
    }
    public static void registerBow(Item bow) {



        ModelPredicateProviderRegistry.register(bow, new Identifier("pull"),
                (stack, world, entity, seed) -> {
                   if(entity == null) return 0.0f;
                   else if (entity.getActiveItem() != stack) return 0.0f;
                   return (float)(stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 20.0f;
                });

        ModelPredicateProviderRegistry.register(bow, new Identifier("pulling"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f);
    }
}
