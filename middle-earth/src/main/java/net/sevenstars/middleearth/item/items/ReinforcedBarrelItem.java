package net.sevenstars.middleearth.item.items;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import net.sevenstars.middleearth.entity.EntitiesME;

public final class ReinforcedBarrelItem extends Item {
    public ReinforcedBarrelItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        HitResult hit = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
        if (hit.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(stack);
        }

        Entity barrel = (Entity) (Object) EntitiesME.REINFORCED_BARREL.create(level);
        if (barrel == null) {
            return InteractionResultHolder.fail(stack);
        }
        barrel.moveTo(hit.getLocation(), player.getYRot(), 0.0F);
        if (!level.noCollision(barrel, barrel.getBoundingBox())) {
            return InteractionResultHolder.fail(stack);
        }

        if (!level.isClientSide) {
            level.addFreshEntity(barrel);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, hit.getLocation());
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
