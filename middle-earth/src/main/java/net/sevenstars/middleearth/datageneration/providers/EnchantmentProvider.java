package net.sevenstars.middleearth.datageneration.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.concurrent.CompletableFuture;

public class EnchantmentProvider extends FabricDynamicRegistryProvider {

    public EnchantmentProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {

        /*register(entries, MEEnchantmentEffects.MINING_ENCHANT, Enchantment.builder(
                                Enchantment.definition(
                                        wrapperLookup.getOrThrow(RegistryKeys.ITEM).getOrThrow(ItemTags.PICKAXES),
                                        // this is the "weight" or probability of our enchantment showing up in the table
                                        10,
                                        // the maximum level of the enchantment
                                        3,
                                        // base cost for level 1 of the enchantment, and min levels required for something higher
                                        Enchantment.leveledCost(1, 10),
                                        // same fields as above but for max cost
                                        Enchantment.leveledCost(1, 15),
                                        // anvil cost
                                        5,
                                        // valid slots
                                        AttributeModifierSlot.HAND
                                )
                        )
                        .addEffect(
                                EnchantmentEffectComponentTypes.HIT_BLOCK,
                                new MiningEnchantmentEffect(EnchantmentLevelBasedValue.linear(0.4f, 0.2f))
                        )
        );*/
    }

    private void register(Entries entries, RegistryKey<Enchantment> key, Enchantment.Builder builder, ResourceCondition... resourceConditions) {
        entries.add(key, builder.build(key.getValue()), resourceConditions);
    }

    @Override
    public String getName() {
        return MiddleEarth.MOD_ID + " Enchantments";
    }
}
