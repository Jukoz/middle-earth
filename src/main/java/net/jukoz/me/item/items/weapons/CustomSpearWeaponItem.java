package net.jukoz.me.item.items.weapons;

import net.jukoz.me.entity.projectile.spear.SpearEntity;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.utils.ModWeaponTypes;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class CustomSpearWeaponItem extends ReachWeaponItem {
    public MutableText faction;
    public MutableText subFaction;
    public ModWeaponTypes type;

    private static final float BASE_STRENGTH = 0.75f;
    private static final float CHARGE_STRENGTH = 1f;
    private static final int STRENGTH_CHARGE_TIME = 20; // 1s charge for full strength

    public CustomSpearWeaponItem(ToolMaterial toolMaterial) {
        super(toolMaterial, ModWeaponTypes.SPEAR);
    }

    public CustomSpearWeaponItem(ToolMaterial toolMaterial, MutableText faction) {
        super(toolMaterial, faction, ModWeaponTypes.SPEAR);
    }

    public CustomSpearWeaponItem(ToolMaterial toolMaterial, MutableText faction, MutableText subFaction) {
        super(toolMaterial, faction, subFaction, ModWeaponTypes.SPEAR);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity playerEntity)) {
            return;
        }
        int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
        if (i < 7) {
            return;
        }
        if(i > STRENGTH_CHARGE_TIME) i = STRENGTH_CHARGE_TIME;
        float percentage = (float) i / STRENGTH_CHARGE_TIME;

        if (!world.isClient) {

            SpearEntity spearEntity = new SpearEntity(world, this, user, 2 * percentage); //TODO change damage
            spearEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, BASE_STRENGTH + (CHARGE_STRENGTH * percentage), 1.0f);

            world.spawnEntity(spearEntity);
            world.playSoundFromEntity(null, spearEntity, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 1.0f, 0.7f);
            if (!playerEntity.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
    }
}
