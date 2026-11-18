package net.sevenstars.middleearth.entity.npcs.routine;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import net.sevenstars.of_beasts_and_wild_things.item.ItemsWT;

public final class RoutineGame {
    private static final int PEN_REACH = 8;

    private RoutineGame() {}

    public static boolean isPenned(AnimalEntity animal) {
        World world = animal.getEntityWorld();
        BlockPos at = animal.getBlockPos();
        for(Direction side : Direction.Type.HORIZONTAL) {
            boolean fenced = false;
            for(int step = 1; step <= PEN_REACH && !fenced; step++) {
                BlockPos pos = at.offset(side, step);
                for(BlockPos check : new BlockPos[]{pos, pos.up()}) {
                    BlockState state = world.getBlockState(check);
                    if(state.isIn(BlockTags.FENCES) || state.isIn(BlockTags.FENCE_GATES) || state.isIn(BlockTags.WALLS))
                        fenced = true;
                }
            }
            if(!fenced)
                return false;
        }
        return true;
    }

    public static Item meatOf(EntityType<?> type) {
        if(type == EntityType.COW)
            return Items.BEEF;
        if(type == EntityType.PIG)
            return Items.PORKCHOP;
        if(type == EntityType.SHEEP)
            return Items.MUTTON;
        if(type == EntityType.CHICKEN)
            return Items.CHICKEN;
        if(type == EntitiesWT.DEER)
            return ItemsWT.RAW_VENISON;
        if(type == EntitiesWT.PHEASANT)
            return ItemsWT.RAW_POULTRY;
        return Items.RABBIT;
    }

    public static boolean isHorseLike(EntityType<?> type) {
        return type == EntityType.DONKEY || type == EntityType.MULE || Registries.ENTITY_TYPE.getId(type).getPath().contains("horse");
    }
}
