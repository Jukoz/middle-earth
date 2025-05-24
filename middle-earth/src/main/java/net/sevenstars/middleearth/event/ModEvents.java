package net.sevenstars.middleearth.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.enchantments.MEEnchantmentEffects;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.races.RaceUtil;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.world.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.world.dimension.ModDimensions;

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

        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            ItemStack stack = Objects.requireNonNull(playerEntity.getStackInHand(playerEntity.getActiveHand()));
            ToolComponent toolComponent = stack.get(DataComponentTypes.TOOL);
            RegistryEntry<Enchantment> enchantmentRegistryEntry = world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getOptional(MEEnchantmentEffects.HEWING).orElseThrow();
            boolean hasEnchant = stack.getEnchantments().getEnchantments().contains(enchantmentRegistryEntry);
            int level = EnchantmentHelper.getLevel(enchantmentRegistryEntry, stack);
            float hardness = blockState.getBlock().getHardness();

            if (hasEnchant) {
                if (!playerEntity.isCreative()) {
                    assert toolComponent != null;
                    if (toolComponent.isCorrectForDrops(blockState)){
                        if (playerEntity.getFacing() == Direction.DOWN || playerEntity.getFacing() == Direction.UP){
                            if (level == 1){
                                level1BreakVertical(world, playerEntity, blockPos, stack, hardness);
                            } else if (level == 2){
                                level2BreakVertical(world, playerEntity, blockPos, stack, hardness);
                            } else {
                                level3BreakVertical(world, playerEntity, blockPos, stack, hardness);
                            }
                        } else {
                            if (level == 1){
                                level1Break(world, playerEntity, blockPos, stack, hardness);
                            } else if (level == 2){
                                level2Break(world, playerEntity, blockPos, stack, hardness);
                            } else {
                                level3Break(world, playerEntity, blockPos, stack, hardness);
                            }
                        }
                    }
                }
            }
        });
    }

    private static void level1BreakVertical(World world, PlayerEntity player, BlockPos blockPos, ItemStack stack, float hardness){
        float yaw = player.getYaw();
        if((yaw >= -45 && yaw <= 45) || yaw <= -135 || yaw >= 135){
            breakAndDamage(world, player, blockPos.offset(Direction.NORTH), stack, hardness);
            breakAndDamage(world, player, blockPos.offset(Direction.SOUTH), stack, hardness);
        } else {
            breakAndDamage(world, player, blockPos.offset(Direction.WEST), stack, hardness);
            breakAndDamage(world, player, blockPos.offset(Direction.EAST), stack, hardness);
        }
    }

    private static void level2BreakVertical(World world, PlayerEntity player, BlockPos blockPos, ItemStack stack, float hardness){
        breakAndDamage(world, player, blockPos.offset(Direction.NORTH), stack, hardness);
        breakAndDamage(world, player, blockPos.offset(Direction.EAST), stack, hardness);
        breakAndDamage(world, player, blockPos.offset(Direction.SOUTH), stack, hardness);
        breakAndDamage(world, player, blockPos.offset(Direction.WEST), stack, hardness);
    }

    private static void level3BreakVertical(World world, PlayerEntity player, BlockPos blockPos, ItemStack stack, float hardness){
        BlockPos blockPosNorth = blockPos.offset(Direction.NORTH);
        BlockPos blockPosSouth = blockPos.offset(Direction.SOUTH);

        breakAndDamage(world, player, blockPosNorth, stack, hardness);
        breakAndDamage(world, player, blockPosNorth.offset(Direction.EAST), stack, hardness);
        breakAndDamage(world, player, blockPosNorth.offset(Direction.WEST), stack, hardness);

        breakAndDamage(world, player, blockPos.offset(Direction.EAST), stack, hardness);

        breakAndDamage(world, player, blockPosSouth, stack, hardness);
        breakAndDamage(world, player, blockPosSouth.offset(Direction.EAST), stack, hardness);
        breakAndDamage(world, player, blockPosSouth.offset(Direction.WEST), stack, hardness);

        breakAndDamage(world, player, blockPos.offset(Direction.WEST), stack, hardness);
    }

    private static void level1Break(World world, PlayerEntity player, BlockPos blockPos, ItemStack stack, float hardness){
        BlockPos blockPosUp = blockPos.up();
        BlockPos blockPosDown = blockPos.down();

        breakAndDamage(world, player, blockPosUp, stack, hardness);
        breakAndDamage(world, player, blockPosDown, stack, hardness);
    }

    private static void level2Break(World world, PlayerEntity player, BlockPos blockPos, ItemStack stack, float hardness){
        BlockPos blockPosUp = blockPos.up();
        BlockPos blockPosDown = blockPos.down();

        breakAndDamage(world, player, blockPosUp, stack, hardness);

        breakAndDamage(world, player, blockPos.offset(player.getFacing().rotateYClockwise()), stack, hardness);
        breakAndDamage(world, player, blockPos.offset(player.getFacing().rotateYCounterclockwise()), stack, hardness);

        breakAndDamage(world, player, blockPosDown, stack, hardness);
    }

    private static void level3Break(World world, PlayerEntity player, BlockPos blockPos, ItemStack stack, float hardness){
        BlockPos blockPosUp = blockPos.up();
        BlockPos blockPosDown = blockPos.down();

        breakAndDamage(world, player, blockPosUp, stack, hardness);
        breakAndDamage(world, player, blockPosUp.offset(player.getFacing().rotateYClockwise()), stack, hardness);
        breakAndDamage(world, player, blockPosUp.offset(player.getFacing().rotateYCounterclockwise()), stack, hardness);

        breakAndDamage(world, player, blockPos.offset(player.getFacing().rotateYClockwise()), stack, hardness);
        breakAndDamage(world, player, blockPos.offset(player.getFacing().rotateYCounterclockwise()), stack, hardness);

        breakAndDamage(world, player, blockPosDown, stack, hardness);
        breakAndDamage(world, player, blockPosDown.offset(player.getFacing().rotateYClockwise()), stack, hardness);
        breakAndDamage(world, player, blockPosDown.offset(player.getFacing().rotateYCounterclockwise()), stack, hardness);
    }

    //TODO make silk touch work
    private static void breakAndDamage(World world, PlayerEntity player, BlockPos blockpos, ItemStack stack, float hardness){
        ToolComponent toolComponent = stack.get(DataComponentTypes.TOOL);
        BlockState blockState = world.getBlockState(blockpos);
        if (!blockState.isAir()) {
            if (toolComponent.isCorrectForDrops(blockState)){
                if (blockState.getBlock().getHardness() <= hardness){
                    world.breakBlock(blockpos, true, player);
                    player.getStackInHand(player.getActiveHand()).damage(1, player);
                }
            }
        }
    }
}
