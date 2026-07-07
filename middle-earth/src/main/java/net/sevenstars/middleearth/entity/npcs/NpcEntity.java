package net.sevenstars.middleearth.entity.npcs;

import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.entity.EntityAttributesME;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.snow.SnowTrollEntity;
import net.sevenstars.middleearth.entity.goals.*;
import net.sevenstars.middleearth.entity.npcs.data.NpcEntityDataHolder;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityTextureData;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcRenderedPart;
import net.sevenstars.middleearth.entity.npcs.initializer.NpcEntityInitializer;
import net.sevenstars.middleearth.entity.npcs.initializer.NpcSpawnEggHelper;
import net.sevenstars.middleearth.entity.spider.larva.ShelobiteLarvaEntity;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerEntity;
import net.sevenstars.middleearth.entity.spider.spawn.SpawnOfShelobEntity;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomLongbowWeaponItem;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.runtime.CombatArchetypeRuntimeData;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.common.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.utils.ItemTagsME;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class NpcEntity extends PassiveEntity implements EquipmentHolder, CrossbowUser {
    // Data to use
    NpcEntityDataHolder entityDataHolder;
    private static final TrackedData<Integer> USING_ITEM = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> CROSSBOW_CHARGING = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private final CustomBowAttackGoal bowAttackGoal = new CustomBowAttackGoal<>(this, 1.0, 20, 16.0F);
    private final NpcCrossBowAttackGoal crossBowAttackGoal = new NpcCrossBowAttackGoal<>(this, 1.0, 11.0F);
    private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2, false) {
        @Override
        public void stop() {
            super.stop();
            NpcEntity.this.setAttacking(false);
        }

        @Override
        public void start() {
            super.start();
            NpcEntity.this.setAttacking(true);
        }
    };

    public final AnimationState walkingState = new AnimationState();
    public final AnimationState idleState = new AnimationState();
    public final AnimationState aimingState = new AnimationState();
    public final AnimationState attackState = new AnimationState();
    public final AnimationState swingState = new AnimationState();

    public NpcEntity(EntityType<NpcEntity> entityType, World world) {
        super(entityType, world);
        this.entityDataHolder = new NpcEntityDataHolder(this);
        this.updateAttackType();
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.ATTACK_DAMAGE, 1.0);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        //this.targetSelector.add(2, new TargetEvilBeastsGoal(this));
        this.targetSelector.add(3, new TargetPlayerDiplomacyGoal(this));
        this.targetSelector.add(4, new TargetNPCDiplomacyGoal(this));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, SnowTrollEntity.class, true));
        this.targetSelector.add(7, new ActiveTargetGoal<>(this, SpawnOfShelobEntity.class, true));
        this.targetSelector.add(8, new ActiveTargetGoal<>(this, ShelobiteScuttlerEntity.class, true));
        this.targetSelector.add(9, new ActiveTargetGoal<>(this, ShelobiteLarvaEntity.class, true));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        if(entityDataHolder == null)
            entityDataHolder = new NpcEntityDataHolder(this);

        super.initDataTracker(builder);
        entityDataHolder.initDataTracker(builder);
        builder.add(CROSSBOW_CHARGING, false);
        builder.add(USING_ITEM, 0);
    }

    @Override
    public void writeData(WriteView view) {
        super.writeData(view);
        this.writeEntityData(view);
    }

    @Override
    public boolean canUseRangedWeapon(RangedWeaponItem weapon) {
        return true;
    }

    @Override
    public void readData(ReadView view) {
        super.readData(view);
        this.readEntityData(view);
    }

    @Override
    public @Nullable ItemStack getPickBlockStack() {
        return NpcSpawnEggHelper.getSpawnEgg(getWorld(), getNpcData().getId());
    }

    private void writeEntityData(WriteView view){
        entityDataHolder.writeEntityData( view);
    }

    private void readEntityData(ReadView view) {
        entityDataHolder.readEntityData(view);
        this.updateAttackType();
    }

    public void updateAttackType() {
        if (this.getWorld() != null && !this.getWorld().isClient) {
            this.goalSelector.remove(this.meleeAttackGoal);
            this.goalSelector.remove(this.bowAttackGoal);
            ItemStack itemStack = this.getMainHandStack();
            if (itemStack.isOf(Items.BOW) || itemStack.isIn(ItemTagsME.BOW)) {
                int i = 30;
                if (this.getWorld().getDifficulty() != Difficulty.HARD) {
                    i = 20;
                }
                this.bowAttackGoal.setAttackInterval(i);
                this.goalSelector.add(4, this.bowAttackGoal);
            } if (itemStack.isOf(Items.CROSSBOW) || itemStack.isIn(ItemTagsME.CROSSBOW)) {
                this.goalSelector.add(4, this.crossBowAttackGoal);
            } else {
                this.goalSelector.add(4, this.meleeAttackGoal);
            }
        }
    }

    public void updateTargetGoals() {
        //if (this.getWorld() != null && !this.getWorld().isClient) {
        //    if(getFaction().getDisposition() == DispositionType.EVIL) {
        //        this.targetSelector.add(10, new ActiveTargetGoal<>(this, BroadhoofGoatEntity.class, true));
        //        this.targetSelector.add(10, new ActiveTargetGoal<>(this, GreatHornEntity.class, true));
        //    } else {
        //        this.targetSelector.add(5, new ActiveTargetGoal<>(this, CaveTrollEntity.class, true));
        //        this.targetSelector.add(5, new ActiveTargetGoal<>(this, StoneTrollEntity.class, true));
        //        this.targetSelector.add(6, new ActiveTargetGoal<>(this, WargEntity.class, true));
        //    }
        //}
    }

    public static boolean canSpawn(EntityType<NpcEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isSolidBlock(world, pos.mutableCopy().up())
                && !world.getBlockState(pos.down()).isIn(BlockTags.LOGS);
    }

    //region [DATA TRANSFER]
    // SETTERS
    public void assignStructureManager(StructureManagerBlockEntity blockEntity){
        entityDataHolder.assignStructureManager(blockEntity);
    }
    public void assignBed(BedBlockEntity bedBlockEntity){
        entityDataHolder.assignBed(bedBlockEntity);
    }
    public void setFighting(boolean state){
        entityDataHolder.setFighting(state);
    }
    public void setInitializationTick() {
        entityDataHolder.setInitializationTick(this.getWorld().getTickOrder());
    }
    public void setBlocking(boolean blockingState){
        entityDataHolder.setBlockingState(blockingState);
    }
    public void setNpcData(Identifier npcDataIdentifier){
        entityDataHolder.setNpcData(npcDataIdentifier);
    }
    public void setNpcData(NpcData npcData){
        entityDataHolder.setNpcData(npcData);
        CombatArchetypeRuntimeData combatArchetypeRuntimeData = npcData.getCombatArchetypeRuntime();
        entityDataHolder.setCombatRuntimeData(combatArchetypeRuntimeData);
        entityDataHolder.setFactionId(npcData.getFactionIdentifier());
        entityDataHolder.setNpcCategory(npcData.getNpcTextureData(getWorld()).getRandomCategory().name());

        /*switch (combatArchetypeRuntimeData.getArchetype()){
            case MELEE -> NpcBrain.setMeleeActivities((Brain<NpcEntity>) this.brain, this, (MeleeCombatArchetypeRuntimeData) combatArchetypeRuntimeData);
            case RANGED ->  NpcBrain.setRangedActivities((Brain<NpcEntity>) this.brain, this, (RangedCombatArchetypeRuntimeData) combatArchetypeRuntimeData);
        }*/
    }

    public void setNpcTextureData(NpcEntityTextureData npcEntityTextureData){
        entityDataHolder.setNpcTextureData(npcEntityTextureData);
    }

    // GETTERS
    public Identifier getNpcDataIdentifier(){
        NpcData npcData = entityDataHolder.getNpcData();
        if(npcData == null)
            return null;
        return npcData.getId();
    }
    public NpcData getNpcData(){
        return entityDataHolder.getNpcData();
    }
    public boolean getFighting(){
        return entityDataHolder.getFighting();
    }
    public Identifier getFactionIdentifier(){
        return entityDataHolder.getFactionId();
    }
    public Long getInitializationTick() {
        return entityDataHolder.getInitializationTick();
    }
    public CombatArchetypeRuntimeData getCombatRuntimeData(){
        return entityDataHolder.getCombatRuntimeData();
    }
    @Override
    public boolean isBlocking() {
        return entityDataHolder.isBlocking();
    }
    public EntityCategories getNpcCategory() {
        return entityDataHolder.getNpcCategory();
    }
    public BlockPos getStructureManagerHostPos() {
        return entityDataHolder.getStructureManagerPos();
    }
    public BlockPos getAssignedBedPos() {
        return entityDataHolder.getAssignedBedPos();
    }

    public NpcEntityTextureData getNpcTextureData() {
        return this.entityDataHolder.getNpcTextureData();
    }
    //endregion

    public void tryToInitializeData(){
        if(Objects.equals(getBlockPos(), new BlockPos(0, 0, 0))) // 0,0,0 is what's used for commands, needs to be delayed
            return;

        World world = getWorld();
        if(world.isClient)
            return;

        if(world instanceof ServerWorld serverWorld){
            if(NpcEntityInitializer.shouldInitialize(serverWorld, this)){
                NpcEntityInitializer.initializeNpcEntity(serverWorld, this);
            }
        }
    }

    public void initializeForCurrentNpcData() {
        if(this.getNpcData() == null)
            return;
        World world = getWorld();
        if(world.isClient)
            return;
        if(world instanceof ServerWorld serverWorld){
            NpcEntityInitializer.initializeNpcForCurrentData(this, serverWorld, getNpcData().getId());
        }
    }

    @Override
    protected void mobTick(ServerWorld world) {
        tryToInitializeData();
        CombatArchetypeRuntimeData runtimeData = getCombatRuntimeData();
        if(runtimeData != null)
            runtimeData.tick(this, world);

        /*Profiler profiler = Profilers.get();
        profiler.push("npcBrain");
        this.getBrain().tick(world, this);
        profiler.pop();
        profiler.push("npcActivityUpdate");
        NpcBrain.updateActivities(this);
        profiler.pop();*/
        super.mobTick(world);

        if(hasVehicle()){
            rotate(getVehicle().getYaw(), getVehicle().getPitch());
        }
    }

    @Override
    public boolean isUsingItem() {
        boolean value = super.isUsingItem();
        if(!value) {
            return this.dataTracker.get(USING_ITEM) > 0;
        }
        return value;
    }

    public void setNpcFlag(int mask, boolean value) {
        setLivingFlag(mask, value);

        if(value) {
            int i = this.dataTracker.get(USING_ITEM) + 1;
            this.dataTracker.set(USING_ITEM, i);
        } else {
            this.dataTracker.set(USING_ITEM, 0);
        }
    }

    /*protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return NpcBrain.create(dynamic);
    }

    public Brain<NpcEntity> getBrain() {
        return (Brain<NpcEntity>) super.getBrain();
    }*/

    public float getFightingMovementSpeed(){
        double currentSpeed = this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED);
        return (float) (currentSpeed);
    }

    public boolean isFighting() {
        boolean isFighting = getFighting();

        this.setSprinting(isFighting);
        if(this.hasVehicle() && getVehicle() instanceof AbstractHorseEntity abstractHorseEntity){
            abstractHorseEntity.setSprinting(isFighting);
        }
        return isFighting;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if(!this.getWorld().isClient) {

        } else {
            setupAnimationStates();
        }

    }

    private void setupAnimationStates() {
        if(this.forwardSpeed > 0) {
            this.walkingState.startIfNotRunning(this.age);
        } else {
            this.idleState.startIfNotRunning(this.age);
        }

        int bowPullProgress = this.getItemUseTime();
        if(bowPullProgress > 0) {
            this.aimingState.startIfNotRunning(this.age);
        }
    }

    @Override
    public boolean shouldControlVehicles() {
        return true;
    }

    @Override
    protected boolean couldAcceptPassenger() {
        return false;
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        // Overrides vanilla init equipment (gold sets???)
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        if(getVehicle() != null && getVehicle() instanceof LivingEntity vehicleEntity){
            vehicleEntity.equipStack(EquipmentSlot.SADDLE, Items.AIR.getDefaultStack());
            vehicleEntity.equipStack(EquipmentSlot.BODY, Items.AIR.getDefaultStack());
            if(vehicleEntity instanceof AbstractHorseEntity abstractHorseEntity){
                abstractHorseEntity.setTame(false);
            }
            if(vehicleEntity instanceof AbstractBeastEntity abstractBeastEntity){
                abstractBeastEntity.setTameness(0);
            }
        }
        super.onDeath(damageSource);
        if(entityDataHolder.getStructureManagerPos() != null){
            StructureManagerBlockEntity.triggerDeathSignal(getStructureManagerHostPos(), this);
        }
    }

    @Override
    protected void dropLoot(ServerWorld world, DamageSource damageSource, boolean causedByPlayer) {
        boolean canDropLoot = false;
        if(causedByPlayer){
            if(damageSource.getAttacker() instanceof PlayerEntity player){
                PlayerData data = StateSaverAndLoader.getPlayerState(player);
                if(data == null)
                    canDropLoot = true;
                else if(data.getFaction() == null)
                    canDropLoot = true;
                else
                    canDropLoot = data.getFaction().compareTo(getFactionIdentifier()) != 0;
            }
        }

        if(!canDropLoot)
            return;

        RegistryKey<LootTable> lootTableRegistryKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, getNpcData().getId().withPrefixedPath("entities/"));
        LootTable lootTable = world.getServer().getReloadableRegistries().getLootTable(lootTableRegistryKey);

        if (lootTable != null) {
            LootWorldContext.Builder builder = (new LootWorldContext.Builder(world)).add(LootContextParameters.THIS_ENTITY, this)
                    .add(LootContextParameters.ORIGIN, this.getPos())
                    .add(LootContextParameters.DAMAGE_SOURCE, damageSource)
                    .addOptional(LootContextParameters.ATTACKING_ENTITY, damageSource.getAttacker())
                    .addOptional(LootContextParameters.DIRECT_ATTACKING_ENTITY, damageSource.getSource());
            PlayerEntity playerEntity = this.getAttackingPlayer();
            if (causedByPlayer && playerEntity != null) {
                builder = builder.add(LootContextParameters.LAST_DAMAGE_PLAYER, playerEntity).luck(playerEntity.getLuck());
            }

            LootWorldContext lootWorldContext = builder.build(LootContextTypes.ENTITY);
            lootTable.generateLoot(lootWorldContext, this.getLootTableSeed(), (stack) -> this.dropStack(world, stack));
        }
    }

    @Override
    public boolean isPersistent() {
        //if(getBrain() == null) return super.isPersistent();
        if(getStructureManagerHostPos() != null) return true;
        //if(getBrain().getOptionalMemory(MemoryModulesME.STRUCTURE_MANAGER_HOST_POS).isPresent())
        //    return getWorld().getBlockEntity(getBrain().getOptionalMemory(MemoryModulesME.STRUCTURE_MANAGER_HOST_POS).get()) != null;
        return super.isPersistent();
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
    }

    protected Faction getFaction(){
        Identifier factionId = getFactionIdentifier();
        if(factionId == null)
            return null;
        try {
            return FactionLookup.getFactionById(getWorld(), factionId);
        } catch (FactionIdentifierException e) {
            return null;
        }
    }

    @Override
    public boolean isInAttackRange(LivingEntity entity) {
        float reach = 1.75f;
        try{
            Optional<Double> damageOpt = Optional.of(this.getAttributeValue(EntityAttributes.ENTITY_INTERACTION_RANGE));
            reach = damageOpt.get().floatValue();
        } catch (Exception ignored){}

        if(this.getVehicle() != null || entity.getVehicle() != null) {
            reach += 0.5f;
        }

        return this.distanceTo(entity) <= reach;
    }

    @Override
    public boolean tryAttack(ServerWorld world, Entity target) {
        if(hasVehicle() && getVehicle() instanceof AbstractBeastEntity mountEntity){
            return mountEntity.tryAttack((ServerWorld) target.getWorld(), target);
        }

        this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_ATTACK_SOUND);
        boolean bl;
        float damage = 1.0f;
        try{
            Optional<Double> damageOpt = Optional.of(this.getAttributeValue(EntityAttributes.ATTACK_DAMAGE));
            damage = damageOpt.get().floatValue();
        } catch (Exception ignored){}
        ItemStack itemStack = this.getWeaponStack();
        DamageSource damageSource = Optional.ofNullable(itemStack.getItem().getDamageSource(this)).orElse(this.getDamageSources().mobAttack(this));

        var enchantmentDamage = EnchantmentHelper.getDamage(world, itemStack, target, damageSource, damage);
        var bonusDamage =  itemStack.getItem().getBonusAttackDamage(target, enchantmentDamage, damageSource);
        var finalDamage = damage + bonusDamage;

        bl = target.damage(world, damageSource, finalDamage);
        if (bl) {
            LivingEntity livingEntity;
            float g = this.getAttackKnockbackAgainst(target, damageSource);
            if (g > 0.0f && target instanceof LivingEntity) {
                livingEntity = (LivingEntity)target;
                livingEntity.takeKnockback(g * 0.5f, MathHelper.sin(this.getYaw() * ((float)Math.PI / 180)), -MathHelper.cos(this.getYaw() * ((float)Math.PI / 180)));
                this.setVelocity(this.getVelocity().multiply(0.6, 1.0, 0.6));
            }
            if (target instanceof LivingEntity) {
                livingEntity = (LivingEntity)target;
                itemStack.postHit(livingEntity, this);
            }
            EnchantmentHelper.onTargetDamaged(world, target, damageSource);
            this.onAttacking(target);
            this.playAttackSound();
        }
        return bl;
    }

    @Override
    public void tickRiding() {
        super.tickRiding();
        Entity entity = this.getControllingVehicle();
        if (entity instanceof PathAwareEntity pathAwareEntity) {
            this.bodyYaw = pathAwareEntity.bodyYaw;
        }
    }

    @Override
    protected Text getDefaultName() {
        if(this.getNpcDataIdentifier() == null) {
            return Text.translatable("npc_data."+ MiddleEarth.MOD_ID +".npc");
        }
        return Text.translatable(this.getNpcDataIdentifier().toTranslationKey("npc_data"));
    }

    @Override
    protected void takeShieldHit(ServerWorld world, LivingEntity attacker) {
        super.takeShieldHit(world, attacker);
        ItemStack itemStack = this.getBlockingItem();
        BlocksAttacksComponent blocksAttacksComponent = itemStack != null ? itemStack.get(DataComponentTypes.BLOCKS_ATTACKS) : null;
        float f = attacker.getWeaponDisableBlockingForSeconds();
        if (f > 0.0f && blocksAttacksComponent != null) {
            blocksAttacksComponent.applyShieldCooldown(world, this, f, itemStack);
        }
    }

    /*public Optional<LivingEntity> getHurtBy() {
        return this.getBrain()
                .getOptionalRegisteredMemory(MemoryModuleType.HURT_BY)
                .map(DamageSource::getAttacker)
                .filter(attacker -> attacker instanceof LivingEntity)
                .map(livingAttacker -> (LivingEntity)livingAttacker);
    }*/

    @Override
    public boolean canTarget(LivingEntity target) {
        return shouldTarget(this, target) && super.canTarget(target);
    }

    public static boolean shouldTarget(NpcEntity npcEntity, LivingEntity target){
        if(target instanceof SnailEntity || target instanceof HostileEntity || target instanceof SnowTrollEntity)
            return true;
        Faction faction = npcEntity.getFaction();
        if(faction != null){
            if(target instanceof PlayerEntity player && player.canTakeDamage()){
                var playerFaction = StateSaverAndLoader.getPlayerState(player).getFaction();
                if(playerFaction == null)
                    return true;
                if(faction.isHostileToward(playerFaction))
                    return true;
            }

            if(target instanceof NpcEntity targetNpcEntity){
                if(targetNpcEntity.hasVehicle() && targetNpcEntity.getVehicle() instanceof AbstractHorseEntity)
                    return false;

                Faction targetFaction = targetNpcEntity.getFaction();
                if(targetFaction == null || faction.isHostileToward(targetFaction.getId()))
                    return true;
                else if(targetFaction.getFactionType() == FactionType.SUBFACTION){
                    if(faction.isHostileToward(targetFaction.getParentFaction(npcEntity.getWorld()).getId()))
                        return true;
                }
            }

            if(target instanceof AbstractHorseEntity abstractHorseEntity){
                if(abstractHorseEntity.hasPassengers()){
                    var entityList = abstractHorseEntity.getPassengersDeep();
                    for(Entity entity : entityList){
                        if(entity instanceof NpcEntity targetNpcEntity){
                            Faction targetFaction = targetNpcEntity.getFaction();
                            if(targetFaction == null || faction.isHostileToward(targetFaction.getId()))
                                return true;
                            else if(targetFaction.getFactionType() == FactionType.SUBFACTION){
                                if(faction.isHostileToward(targetFaction.getParentFaction(npcEntity.getWorld()).getId()))
                                    return true;
                            }
                        }
                    }
                    if(abstractHorseEntity instanceof AbstractBeastEntity abstractBeastEntity){
                        if(abstractBeastEntity.getDisposition() != faction.getDisposition()){
                            return true;
                        }
                    }
                }
            }

            // Beasts/mobs (spiders, trolls, etc, other data set.)
        }
        return false;
    }

    public int getTickAttackSpeedCooldown(){
        if(!this.getAttributes().hasAttribute(EntityAttributes.ATTACK_SPEED))
            return 1;
        return (int)this.getAttributes().getValue(EntityAttributes.ATTACK_SPEED);
    }

    public boolean hasTextureData(){
        return getNpcTextureData().get(NpcRenderedPart.BODY) != null;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    protected void dropEquipment(ServerWorld world, DamageSource source, boolean causedByPlayer) {
        // No drop allowed
    }

    //public void releaseTicketFor(MemoryModuleType<GlobalPos> destination) {
    //    this.releaseTicketFor(MemoryModuleType.HOME);
    //}

    public float getWidthScale() {
        try{
            return (float) this.getAttributeValue(EntityAttributesME.WIDTH_SCALE);
        }
        catch (Exception ignored){
            return 1.0f;
        }
    }

    /*@Override
    public boolean isInAttackRange(LivingEntity entity) {
        CombatArchetypeRuntimeData runtimeData = getCombatRuntimeData();
        if(runtimeData == null)
            return false;
        return runtimeData.getCombatArchetypeData().isInOptimalRange(this, entity.getBlockPos());
    }*/

    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier, @Nullable ItemStack shotFrom) {
        return ProjectileUtil.createArrowProjectile(this, arrow, damageModifier, shotFrom);
    }

    public boolean isAiming() {
        int i = this.getItemUseTime();
        return i > 0;
    }

    public void aim() {
        var currentItem = getActiveItem();
        if(currentItem.isEmpty()){
            clearActiveItem();
            setCurrentHand(Hand.MAIN_HAND);
        }
    }

    public void stopAiming() {
        var currentItem = getActiveItem();
        if(currentItem.isEmpty()){
            this.clearActiveItem();
            return;
        }
        this.clearActiveItem();
    }

    public boolean isReadyToShoot() {
        return getMainHandStack() != null;
        //int i = this.getItemUseTime();
        //if (i >= 20) {
        //    return true;
        //}
        //return false;
    }

    public void shootAt(LivingEntity livingEntity) {
        try{
            this.shootAt(livingEntity, BowItem.getPullProgress(getItemUseTime()), 2f);
        } catch (IllegalArgumentException e){
            this.shootAt(livingEntity, CustomLongbowWeaponItem.getPullProgressLongbow(getItemUseTime()), 3f);
        }
    }

    private void shootAt(LivingEntity target, float pullProgress, float powerModifier) {
        if(!isReadyToShoot())
            return;
        ItemStack shotFromItem = this.getMainHandStack();
        ItemStack itemStack2 = this.getProjectileType(shotFromItem);
        PersistentProjectileEntity persistentProjectileEntity = this.createArrowProjectile(itemStack2, pullProgress, shotFromItem);
        double xDifference = target.getX() - this.getX();
        double heightDifference = target.getBodyY(0.3f) - persistentProjectileEntity.getY();
        double zDifference = target.getZ() - this.getZ();
        double angle = Math.sqrt(xDifference * xDifference + zDifference * zDifference);
        World var15 = this.getWorld();
        if (var15 instanceof ServerWorld serverWorld) {
            ProjectileEntity.spawnWithVelocity(persistentProjectileEntity, serverWorld, itemStack2, xDifference, heightDifference + angle * (double)0.2F, zDifference, 1.6F * powerModifier, (float)(14 - serverWorld.getDifficulty().getId() * 4));
        }

        this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        stopAiming();
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        this.shootAt(target, 1, pullProgress);
    }

    public void shootCrossbowAt(LivingEntity target) {
        this.shootAt(target, 1, 1.25f);
    }

    public boolean isCharging() {
        return this.dataTracker.get(CROSSBOW_CHARGING);
    }

    @Override
    public void setCharging(boolean charging) {
        this.dataTracker.set(CROSSBOW_CHARGING, charging);
    }

    @Override
    public void postShoot() {
        this.dataTracker.set(CROSSBOW_CHARGING, false);
    }

    static {
        NpcEntityDataHolder.initialize();
    }
}
