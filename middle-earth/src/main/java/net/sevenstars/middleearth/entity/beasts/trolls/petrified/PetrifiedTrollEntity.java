package net.sevenstars.middleearth.entity.beasts.trolls.petrified;

import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.item.items.CustomSpawnEggItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PetrifiedTrollEntity extends MobEntity {

    public PetrifiedTrollEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        this.setAiDisabled(true);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 30.0);
    }

    @Override
    public void tick() {
        super.tick();
        this.setBodyYaw(this.getYaw());
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        if (this.getWorld().isClient) return false;
        Entity entity = source.getAttacker();
        if(entity instanceof PlayerEntity playerEntity) {
            if(playerEntity.isCreative()) {
                super.setHealth(0);
                return true;
            }
            ItemStack itemStack = playerEntity.getMainHandStack();
            if(itemStack.getComponents().contains(DataComponentTypes.TOOL) && !this.getWorld().isClient) {
                // TODO test it
                TagKey tagKey = TagKey.of(RegistryKeys.ITEM, Identifier.of("pickaxes"));
                if(itemStack.isIn(tagKey)) {
                    return super.damage(world, source, 10.0f);
                }
            }
        }
        return false;
    }

    @Override
    public void onDamaged(DamageSource damageSource) {
    }

    @Override
    @Nullable
    public ItemStack getPickBlockStack() {
        CustomSpawnEggItem statue = CustomSpawnEggItem.forEntity(this.getType());
        if (statue == null) {
            return null;
        }
        return new ItemStack(statue);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_STONE_BREAK;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_STONE_BREAK;
    }
}
