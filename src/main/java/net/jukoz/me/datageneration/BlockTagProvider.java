package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.jukoz.me.block.OreRockSets;
import net.jukoz.me.datageneration.content.tags.*;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class BlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public BlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        var mineableAxe = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "mineable/axe")));
        var mineableHoe = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "mineable/hoe")));
        var mineablePickaxe = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "mineable/pickaxe")));
        //var mineableShears = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "mineable/shears")));
        var swordEfficient = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "sword_efficient")));

        var leaves = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "leaves")));
        mineableAxe.add(MineableAxe.blocks.toArray(new Block[0]));
        mineablePickaxe.add(MineablePickaxe.blocks.toArray(new Block[0]));

        leaves.add(LeavesSets.blocks.toArray(new Block[0]));
        mineableHoe.add(LeavesSets.blocks.toArray(new Block[0]));
        //mineableShears.add(LeavesSets.blocks.toArray(new Block[0]));
        swordEfficient.add(LeavesSets.blocks.toArray(new Block[0]));

        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "buttons"))).add(Buttons.buttons.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "fences"))).add(Fences.fences.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "fence_gates"))).add(FenceGates.fenceGates.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "logs"))).add(Logs.logs.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "pressure_plates"))).add(PressurePlates.pressurePlates.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "walls"))).add(Walls.walls.toArray(new Block[0]));
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "planks"))).add(Planks.planks.toArray(new Block[0]));

        //Ores
        TagKey<Block> iron_ores = TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "iron_ores"));
        TagKey<Block> gold_ores = TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "gold_ores"));
        TagKey<Block> copper_ores = TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "copper_ores"));
        TagKey<Block> coal_ores = TagKey.of(RegistryKeys.BLOCK, new Identifier("minecraft", "coal_ores"));

        TagKey<Block> tin_ores = TagKey.of(RegistryKeys.BLOCK, new Identifier("me", "tin_ores"));

        for (OreRockSets.OreRockSet set : OreRockSets.sets) {
            getOrCreateTagBuilder(iron_ores)
                    .add(set.iron_ore());
            getOrCreateTagBuilder(gold_ores)
                    .add(set.gold_ore());
            getOrCreateTagBuilder(iron_ores)
                    .add(set.iron_ore());
            getOrCreateTagBuilder(gold_ores)
                    .add(set.gold_ore());
            getOrCreateTagBuilder(coal_ores)
                    .add(set.coal_ore());
            getOrCreateTagBuilder(copper_ores)
                    .add(set.copper_ore());
            getOrCreateTagBuilder(tin_ores)
                    .add(set.tin_ore());
        }

        for (OreRockSets.CompleteOreRockSet set : OreRockSets.vanillaSets) {
            getOrCreateTagBuilder(tin_ores)
                    .add(set.tin_ore());
        }
    }
}
