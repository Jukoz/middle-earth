package net.sevenstars.middleearth.item.items.weapons;

import net.sevenstars.middleearth.item.utils.ModWeaponTypes;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;
import net.minecraft.item.ToolMaterial;

public class CustomSpearWeaponItem extends ReachWeaponItem {
    private static final float BASE_STRENGTH = 0.75f;
    private static final float CHARGE_STRENGTH = 1.75f;
    private static final int STRENGTH_CHARGE_TIME = 20;

    public float pullProgress = 0;
    public ModFactions faction;
    public ModSubFactions subFaction;
    public ModWeaponTypes type;

    public CustomSpearWeaponItem(ToolMaterial toolMaterial) {
        super(toolMaterial, ModWeaponTypes.SPEAR);
    }

    public CustomSpearWeaponItem(ToolMaterial toolMaterial, ModFactions faction) {
        super(toolMaterial, faction, ModWeaponTypes.SPEAR);
    }

    public CustomSpearWeaponItem(ToolMaterial toolMaterial, ModSubFactions subFaction) {
        super(toolMaterial, subFaction, ModWeaponTypes.SPEAR);
    }

    public float getAttackDamage() {
        return this.getMaterial().getAttackDamage();
    }

    /*public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
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
        return 48000;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity playerEntity = (PlayerEntity)user;
        int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
        if (i < 7) {
            return;
        }
        if(i > STRENGTH_CHARGE_TIME) i = STRENGTH_CHARGE_TIME;
        pullProgress = (float) i / STRENGTH_CHARGE_TIME;

        if (!world.isClient) {
            float damage = getAttackDamage() * pullProgress;
            if(getAttackDamage() >= 2.0) {
                damage += 10.0f;
            }
            SpearEntity spearEntity = new SpearEntity(world, stack, user, damage);
            spearEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, BASE_STRENGTH + (CHARGE_STRENGTH * pullProgress), 1.0f);
            world.spawnEntity(spearEntity);
            world.playSoundFromEntity(null, spearEntity, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 1.0f, 0.7f);

            if (!playerEntity.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }

        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
    }*/
}
