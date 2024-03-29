package net.jukoz.me.item.utils;

import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ModModelPredicateProvider {

    public static void registerAllPredicates(){
        registerShieldModel();
        registerBowModel();
    }

    private static void registerBowModel() {
        registerBow(ModWeaponItems.DALISH_BOW);
        registerBow(ModWeaponItems.GONDOR_BOW);
        registerBow(ModWeaponItems.LORIEN_BOW);
        registerBow(ModWeaponItems.ROHIRRIC_BOW);
        registerBow(ModWeaponItems.UMBAR_BOW);
    }
    private static void registerBow(Item bow) {
        ModelPredicateProviderRegistry.register(bow, new Identifier("pull"),
                (stack, world, entity, seed) -> {
                   if(entity == null) return 0.0f;
                   else if (entity.getActiveItem() != stack) return 0.0f;
                   return (float)(stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 20.0f;
                });

        ModelPredicateProviderRegistry.register(bow, new Identifier("pulling"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f);
    }

    private static void registerShieldModel() {
        registerShield(ModEquipmentItems.GONDORIAN_SHIELD);
        registerShield(ModEquipmentItems.ROHIRRIC_SHIELD);
        registerShield(ModEquipmentItems.LONGBEARD_SHIELD);
        registerShield(ModEquipmentItems.LORIEN_SHIELD);
        registerShield(ModEquipmentItems.MORDOR_SHIELD);
        registerShield(ModEquipmentItems.MISTY_MOUNTAINS_SHIELD);
    }

    private static void registerShield(Item shield) {
        ModelPredicateProviderRegistry.register(shield, new Identifier("blocking"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
    }
}
