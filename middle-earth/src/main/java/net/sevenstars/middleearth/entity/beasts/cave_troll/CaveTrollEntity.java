package net.sevenstars.middleearth.entity.beasts.cave_troll;

import com.mojang.serialization.Dynamic;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.RaceType;

import java.util.List;
import java.util.Optional;

public class CaveTrollEntity extends AbstractBeastEntity {
    public LootTable scavengeLootTable;
    public LootWorldContext lootWorldContext;
    public static final TrackedData<Boolean> SCAVENGING = DataTracker.registerData(CaveTrollEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public final AnimationState sleepingAnimationState = new AnimationState();
    public final AnimationState chaseAnimationState = new AnimationState();
    public final AnimationState scavengingAnimationState = new AnimationState();

    public CaveTrollEntity(EntityType<? extends AbstractBeastEntity> entityType, World world) {
        super(entityType, world);

        if(scavengeLootTable == null && !world.isClient()) {
            if(world instanceof ServerWorld serverWorld) {

                LootTable lootTable = serverWorld.getServer().getReloadableRegistries().getLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MiddleEarth.MOD_ID, "gameplay/cave_troll_scavenging.json")));

                if(lootTable != null) {
                    scavengeLootTable = lootTable;

                    lootWorldContext = new LootWorldContext.Builder(serverWorld)
                            .add(LootContextParameters.THIS_ENTITY, this)
                            .add(LootContextParameters.ORIGIN, this.getPos())
                            .build(LootContextTypes.CHEST);
                }

            }
        }
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.MOVEMENT_SPEED, 0.1f)
                .add(EntityAttributes.MAX_HEALTH, 120.0)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.8)
                .add(EntityAttributes.ATTACK_SPEED, 0.65)
                .add(EntityAttributes.FOLLOW_RANGE, 28.0)
                .add(EntityAttributes.ATTACK_DAMAGE, 10.0)
                .add(EntityAttributes.STEP_HEIGHT, 1.25)
                .add(EntityAttributes.FOLLOW_RANGE, 15.0);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SCAVENGING, false);
    }

    @Override
    protected void mobTick(ServerWorld world) {
        Profiler profiler = Profilers.get();
        profiler.push("caveTrollBrain");
        this.getBrain().tick(world, this);
        profiler.swap("caveTrollActivityUpdate");
        CaveTrollBrain.updateActivities(this);
        profiler.pop();

        super.mobTick(world);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        this.openInventory(player);
        return super.interactMob(player, hand);
    }

    @Override
    public void tickMovement() {
        if(this.getWorld().isClient) {
            setupAnimationStates();
        }
        else {
            if(this.getTargetInBrain() != null && !this.isSprinting()) {
                this.setSprinting(true);
            }
            else if(this.getTargetInBrain() == null && this.isSprinting()) {
                this.setSprinting(false);
            }
        }

        super.tickMovement();
    }

    public boolean isScavenging() {
        return this.dataTracker.get(SCAVENGING);
    }

    public void setScavenging(boolean isDigging) {
        this.dataTracker.set(SCAVENGING, isDigging);
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return CaveTrollBrain.create(this, dynamic);
    }

    public Brain<CaveTrollEntity> getBrain() {
        return (Brain<CaveTrollEntity>)super.getBrain();
    }

    @Override
    protected void setupAnimationStates() {
        if(this.isScavenging()) {
            this.scavengingAnimationState.startIfNotRunning(this.age);
        }
        else {
            this.scavengingAnimationState.stop();
        }
    }

    @Override
    protected Disposition getDisposition() {
        return null;
    }

    @Override
    protected List<RaceType> getRaceType() {
        return null;
    }

    @Override
    public boolean isCommandItem(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBondingItem(ItemStack itemStack) {
        return false;
    }

    public static boolean shouldTarget(LivingEntity target) {
        return target instanceof NpcEntity || target instanceof PlayerEntity;
    }
}
