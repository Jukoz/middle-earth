package net.sevenstars.middleearth.datageneration.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Oxidizable;
import net.neoforged.neoforge.registries.datamaps.builtin.Strippable;
import net.neoforged.neoforge.registries.datamaps.builtin.Waxable;
import net.sevenstars.middleearth.block.utils.BlockDataMapCollector;

import java.util.concurrent.CompletableFuture;

public final class NeoForgeDataMapProvider extends DataMapProvider {
    public NeoForgeDataMapProvider(PackOutput output,
                                   CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        BlockDataMapCollector.fuels().forEach((item, ticks) -> {
            if (item != Items.AIR) {
                builder(NeoForgeDataMaps.FURNACE_FUELS).add(
                        BuiltInRegistries.ITEM.getKey(item),
                        new FurnaceFuel(ticks),
                        false
                );
            }
        });
        BlockDataMapCollector.compostables().forEach((item, chance) -> {
            if (item != Items.AIR) {
                builder(NeoForgeDataMaps.COMPOSTABLES).add(
                        BuiltInRegistries.ITEM.getKey(item),
                        new Compostable(chance),
                        false
                );
            }
        });
        BlockDataMapCollector.strippables().forEach((input, stripped) -> {
            if (input != Blocks.AIR && stripped != Blocks.AIR) {
                builder(NeoForgeDataMaps.STRIPPABLES).add(
                        BuiltInRegistries.BLOCK.getKey(input),
                        new Strippable(stripped),
                        false
                );
            }
        });
        BlockDataMapCollector.oxidizables().forEach((input, oxidized) -> {
            if (input != Blocks.AIR && oxidized != Blocks.AIR) {
                builder(NeoForgeDataMaps.OXIDIZABLES).add(
                        BuiltInRegistries.BLOCK.getKey(input),
                        new Oxidizable(oxidized),
                        false
                );
            }
        });
        BlockDataMapCollector.waxables().forEach((input, waxed) -> {
            if (input != Blocks.AIR && waxed != Blocks.AIR) {
                builder(NeoForgeDataMaps.WAXABLES).add(
                        BuiltInRegistries.BLOCK.getKey(input),
                        new Waxable(waxed),
                        false
                );
            }
        });
    }
}
