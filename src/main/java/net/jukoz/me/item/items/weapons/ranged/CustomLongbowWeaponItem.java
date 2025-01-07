package net.jukoz.me.item.items.weapons.ranged;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.MEEquipmentTooltip;
import net.jukoz.me.item.utils.ModRangedWeaponTypes;
import net.jukoz.me.utils.ModFactions;
import net.jukoz.me.utils.ModSubFactions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CustomLongbowWeaponItem extends BowItem implements MEEquipmentTooltip{
    private final ModFactions faction;
    private final ModSubFactions subFaction;
    public ModRangedWeaponTypes type;

    public static final int RANGE = 25;

    public CustomLongbowWeaponItem(ModRangedWeaponTypes type) {
        super(new Item.Settings().maxDamage(type.durability));
        this.faction = null;
        this.subFaction = null;
        this.type = type;
    }

    public CustomLongbowWeaponItem(ModFactions faction, ModRangedWeaponTypes type) {
        super(new Item.Settings().maxDamage(type.durability));
        this.faction = faction;
        this.subFaction = null;
        this.type = type;
    }

    public CustomLongbowWeaponItem(ModSubFactions subFaction, ModRangedWeaponTypes type) {
        super(new Item.Settings().maxDamage(type.durability));
        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
        this.type = type;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            ItemStack itemStack = playerEntity.getProjectileType(stack);
            if (!itemStack.isEmpty()) {
                int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
                float f = getPullProgressLongbow(i);
                if (!((double)f < 0.1)) {
                    List<ItemStack> list = load(stack, itemStack, playerEntity);
                    if (world instanceof ServerWorld) {
                        ServerWorld serverWorld = (ServerWorld)world;
                        if (!list.isEmpty()) {
                            this.shootAll(serverWorld, playerEntity, playerEntity.getActiveHand(), stack, list, f * 5.0F, 1.0F, f == 1.0F, (LivingEntity)null);
                        }
                    }

                    world.playSound((PlayerEntity)null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                }
            }
        }
    }

    public static float getPullProgressLongbow(int useTicks) {
        float f = (float)useTicks / 20.0F;
        f = (f * f + f * 2.0F) / 4.5F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public List<Text> getAdditionalShiftLines(ItemStack stack) {
        List<Text> list = new ArrayList<>(List.of());

        list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".weapon_type").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + this.type.name)));

        return list;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        appendBaseTooltip(tooltip, stack, this.faction, this.subFaction);
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public int getRange() {
        return 25;
    }
}
