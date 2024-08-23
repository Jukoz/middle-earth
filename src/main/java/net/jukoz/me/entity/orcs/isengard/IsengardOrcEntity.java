package net.jukoz.me.entity.orcs.isengard;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.humans.rohan.RohanHumanEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IsengardOrcEntity extends NpcEntity {
    public IsengardOrcEntity(EntityType<? extends NpcEntity> entityType, World world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if(name.contains("snaga")){
            this.setRank(RANK.MILITIA);
            this.setBow(Items.BOW);
        } else if (name.contains("warrior")) {
            this.setRank(RANK.SOLDIER);
            this.setBow(Items.BOW);
        }
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData);
        Random random = world.getRandom();
        this.initEquipment(random, difficulty);
        return entityData;
    }

    public static DefaultAttributeContainer.Builder setSoldierAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        int i = 2;
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, GondorHumanEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, RohanHumanEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, LongbeardDwarfEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, ShireHobbitEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, BanditHumanEntity.class, true));
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
        switch (this.getRank()){
            case MILITIA -> militiaEquipment(random);
            case SOLDIER -> soldierEquipment(random);
        }
    }

    private void militiaEquipment(Random random){
        int[] colors = {
                0x5c544b,
                0x574a3b,
                0x484138,
                0x3b3630
        };
        ItemStack leatherHelmet = new ItemStack(Items.LEATHER_HELMET);
        ItemStack leatherChestplate = new ItemStack(Items.LEATHER_CHESTPLATE);
        ItemStack leatherLeggings = new ItemStack(Items.LEATHER_LEGGINGS);
        ItemStack leatherBoots = new ItemStack(Items.LEATHER_BOOTS);
        DyedColorComponent.setColor(leatherHelmet, List.of(DyeItem.byColor(DyeColor.byId(colors[0]))));
        DyedColorComponent.setColor(leatherChestplate, List.of(DyeItem.byColor(DyeColor.byId(colors[1]))));
        DyedColorComponent.setColor(leatherLeggings, List.of(DyeItem.byColor(DyeColor.byId(colors[2]))));
        DyedColorComponent.setColor(leatherBoots, List.of(DyeItem.byColor(DyeColor.byId(colors[3]))));

        if(random.nextFloat() >= 0.30f){
            equipStack(EquipmentSlot.HEAD, leatherHelmet);
        }
        equipStack(EquipmentSlot.CHEST, leatherChestplate);
        if(random.nextFloat() >= 0.50f){
            equipStack(EquipmentSlot.LEGS, leatherLeggings);
        }
        equipStack(EquipmentSlot.FEET, leatherBoots);

        float val3 = random.nextFloat();
        if(val3 >= 0.7f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else if (val3 > 0.3f) {
            //equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GUNDABAD_SPEAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        } else {
            //equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GUNDABAD_DAGGER));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }
    }

    private void soldierEquipment(Random random){
        float val = random.nextFloat();
        if(val >= 0.30f){
            //equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.MISTY_GOBLIN_MAIL_HELMET));
        }
        //equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.MISTY_GOBLIN_MAIL_CHESTPLATE));
        //equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.MISTY_GOBLIN_MAIL_LEGGINGS));
        //equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.MISTY_GOBLIN_MAIL_BOOTS));


        float val3 = random.nextFloat();
        if(val3 >= 0.6f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else if (val3 > 0.3f) {
            //equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GUNDABAD_SPEAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.MORDOR_SHIELD));
        } else {
            //equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GUNDABAD_SCIMITAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.MORDOR_SHIELD));
        }
    }

    public IsengardOrcVariant getVariant() {
        return IsengardOrcVariant.byId(this.getId());
    }
}
