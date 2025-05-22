package net.sevenstars.middleearth.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.enchantments.MEEnchantmentEffects;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.races.RaceUtil;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.world.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

public class ModEvents {
    public static void register(){
        ServerPlayConnectionEvents.JOIN.register((serverPlayNetworkHandler, packetSender, minecraftServer) -> {
            ServerPlayerEntity player = serverPlayNetworkHandler.getPlayer();
            MiddleEarthHeightMap.setSeed(player.getServerWorld().getSeed());

            PlayerData data = StateSaverAndLoader.getPlayerState(player);
            if(data == null)
                return;
            if(data != null && data.getRace() != null){
                RaceUtil.reset(player);
                boolean isInMiddleEarth = ModDimensions.isInMiddleEarth(player.getWorld());
                if(isInMiddleEarth){
                    RaceUtil.initializeRace(player);
                } else if(ModServerConfigs.ENABLE_KEEP_RACE_ON_DIMENSION_SWAP){
                    RaceUtil.initializeRace(player);
                }
            }
        });

        //TODO vertical facing mining
        //TODO tool breakable only
        //TODO add all hardness together
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            ItemStack stack = Objects.requireNonNull(playerEntity.getStackInHand(playerEntity.getActiveHand()));
            RegistryEntry<Enchantment> enchantmentRegistryEntry = world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getOptional(MEEnchantmentEffects.BROAD_MINING).orElseThrow();
            boolean hasEnchant = stack.getEnchantments().getEnchantments().contains(enchantmentRegistryEntry);
            int level = EnchantmentHelper.getLevel(enchantmentRegistryEntry, stack);

            if (hasEnchant){
                if (!playerEntity.isCreative()){
                    if (level == 1){
                        level1Break(world, playerEntity, blockPos);
                    } else if (level == 2){
                        level2Break(world, playerEntity, blockPos);
                    } else if (level == 3){
                        level3Break(world, playerEntity, blockPos);
                    }
                }
            }
        });
    }

    private static void level1Break(World world, PlayerEntity player, BlockPos blockPos){
        BlockPos blockPosUp = blockPos.up();
        BlockPos blockPosDown = blockPos.down();

        breakAndDamage(world, player, blockPosUp);
        breakAndDamage(world, player, blockPosDown);
    }

    private static void level2Break(World world, PlayerEntity player, BlockPos blockPos){
        BlockPos blockPosUp = blockPos.up();
        BlockPos blockPosDown = blockPos.down();

        breakAndDamage(world, player, blockPosUp);

        breakAndDamage(world, player, blockPos.offset(player.getFacing().rotateYClockwise()));
        breakAndDamage(world, player, blockPos.offset(player.getFacing().rotateYCounterclockwise()));

        breakAndDamage(world, player, blockPosDown);
    }

    private static void level3Break(World world, PlayerEntity player, BlockPos blockPos){
        BlockPos blockPosUp = blockPos.up();
        BlockPos blockPosDown = blockPos.down();

        breakAndDamage(world, player, blockPosUp);
        breakAndDamage(world, player, blockPosUp.offset(player.getFacing().rotateYClockwise()));
        breakAndDamage(world, player, blockPosUp.offset(player.getFacing().rotateYCounterclockwise()));

        breakAndDamage(world, player, blockPos.offset(player.getFacing().rotateYClockwise()));
        breakAndDamage(world, player, blockPos.offset(player.getFacing().rotateYCounterclockwise()));

        breakAndDamage(world, player, blockPosDown);
        breakAndDamage(world, player, blockPosDown.offset(player.getFacing().rotateYClockwise()));
        breakAndDamage(world, player, blockPosDown.offset(player.getFacing().rotateYCounterclockwise()));
    }

    private static void breakAndDamage(World world, PlayerEntity player, BlockPos blockpos){
        if (!world.getBlockState(blockpos).isAir()) {
            world.breakBlock(blockpos, true, player);
            player.getStackInHand(player.getActiveHand()).damage(1, player);
        }
    }
}
