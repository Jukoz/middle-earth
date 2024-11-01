package net.jukoz.me.item.utils;

import net.jukoz.me.datageneration.content.models.SimpleSpearModel;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.items.weapons.artefacts.ArtefactCustomGlowingDaggerWeaponItem;
import net.jukoz.me.item.items.weapons.artefacts.ArtefactCustomGlowingLongswordWeaponItem;
import net.jukoz.me.item.items.weapons.artefacts.ArtefactCustomLongswordWeaponItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ModModelPredicateProvider {

    public static void registerAllPredicates(){
        registerShieldModel();
        registerSpearModel();
        registerBowModel();
        registerArtefactModels();
        registerGlowingArtefactModels();
    }

    private static void registerBowModel() {
        //registerBow(ModWeaponItems.DALISH_BOW);
        registerBow(ModWeaponItems.GONDORIAN_BOW);
        registerBow(ModWeaponItems.LORIEN_BOW);
        registerBow(ModWeaponItems.ROHIRRIC_BOW);
        //registerBow(ModWeaponItems.UMBAR_BOW);
    }

    private static void registerArtefactModels() {
        registerArtefact(ModWeaponItems.NARSIL);
        registerArtefact(ModWeaponItems.MORGUL_KNIFE);
    }

    private static void registerGlowingArtefactModels() {
        registerGlowingArtefact(ModWeaponItems.GLAMDRING);
        registerGlowingArtefact(ModWeaponItems.ORCRIST);
        registerGlowingArtefact(ModWeaponItems.STING);
    }

    private static void registerBow(Item bow) {
        ModelPredicateProviderRegistry.register(bow, Identifier.of("pull"),
                (stack, world, entity, seed) -> {
                   if(entity == null) return 0.0f;
                   else if (entity.getActiveItem() != stack) return 0.0f;
                   return (float)(stack.getMaxUseTime(entity) - entity.getItemUseTimeLeft()) / 20.0f;
                });

        ModelPredicateProviderRegistry.register(bow, Identifier.of("pulling"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f);
    }

    private static void registerShieldModel() {
        ModEquipmentItems.shields.forEach(ModModelPredicateProvider::registerShield);
    }

    private static void registerSpearModel() {
        SimpleSpearModel.items.forEach(ModModelPredicateProvider::registerSpear);
    }

    private static void registerShield(Item shield) {
        ModelPredicateProviderRegistry.register(shield, Identifier.of("blocking"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
    }

    private static void registerSpear(Item spear) {
        ModelPredicateProviderRegistry.register(spear, Identifier.of("holding"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
    }

    private static void registerArtefact(Item artefact) {
        ModelPredicateProviderRegistry.register(artefact, Identifier.of("broken"),
                (stack, world, entity, seed) -> ArtefactCustomLongswordWeaponItem.isUsable(stack) ? 0.0F : 1.0F);
    }

    private static void registerGlowingArtefact(Item artefact) {
        if (artefact instanceof ArtefactCustomGlowingLongswordWeaponItem item){
            ModelPredicateProviderRegistry.register(item, Identifier.of("glowing"),
                    (stack, world, entity, seed) -> ArtefactCustomGlowingLongswordWeaponItem.isGlowing(world, entity) ? 1.0F : 0.0F);
        }
        if (artefact instanceof ArtefactCustomGlowingDaggerWeaponItem item){
            ModelPredicateProviderRegistry.register(item, Identifier.of("glowing"),
                    (stack, world, entity, seed) -> ArtefactCustomGlowingDaggerWeaponItem.isGlowing(world, entity) ? 1.0F : 0.0F);
        }
    }
}
