package net.sevenstars.middleearth.entity.npcs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentUser;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.entity.EntityAttributesME;
import net.sevenstars.middleearth.entity.TrackedDataHandlerRegistryME;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.TrollEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.snow.SnowTrollEntity;
import net.sevenstars.middleearth.entity.goals.*;
import net.sevenstars.middleearth.entity.npcs.data.NpcData;
import net.sevenstars.middleearth.entity.npcs.data.NpcInitializationData;
import net.sevenstars.middleearth.entity.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.entity.npcs.initializer.NpcEntityInitializer;
import net.sevenstars.middleearth.entity.npcs.initializer.NpcSpawnEggHelper;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcRenderedPart;
import net.sevenstars.middleearth.entity.spider.Pouncer;
import net.sevenstars.middleearth.entity.spider.larva.ShelobiteLarvaEntity;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerEntity;
import net.sevenstars.middleearth.entity.spider.spawn.SpawnOfShelobEntity;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomLongbowWeaponItem;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;
import net.sevenstars.middleearth.resources.datas.npc_types.data.LootData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.utils.ItemTagsME;
import net.sevenstars.middleearth.utils.SpawnUtil;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntity;
import org.jetbrains.annotations.Nullable;

public class NpcEntity extends PathfinderMob implements EquipmentUser, CrossbowAttackMob {
    public static class KeyStrings {
        public static final String DATA = "NpcData";
        public static final String INITIALIZATION_DATA = "InitializationData";
        public static final String TEXTURE_DATA = "TextureData";
        public static final String IS_FIGHTING = "IsFighting";
    }
    // [TrackedDatas]
    private static final EntityDataAccessor<NpcData> NPC_DATA;
    private static final EntityDataAccessor<NpcInitializationData> NPC_INITIALIZATION_DATA;
    private static final EntityDataAccessor<NpcTextureData> NPC_TEXTURE_DATA;
    private static final EntityDataAccessor<Boolean> IS_FIGHTING;

    private static final EntityDataAccessor<Integer> USING_ITEM = SynchedEntityData.defineId(NpcEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CROSSBOW_CHARGING = SynchedEntityData.defineId(NpcEntity.class, EntityDataSerializers.BOOLEAN);

    private final CustomBowAttackGoal bowAttackGoal = new CustomBowAttackGoal<>(this, 1.0, 20, 16.0F);
    private final NpcCrossBowAttackGoal crossBowAttackGoal = new NpcCrossBowAttackGoal<>(this, 1.0, 11.0F);
    private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2, false) {
        @Override
        public void stop() {
            super.stop();
            NpcEntity.this.setAggressive(false);
        }

        @Override
        public void start() {
            super.start();
            NpcEntity.this.setAggressive(true);
        }
    };

    public final AnimationState walkingState = new AnimationState();
    public final AnimationState idleState = new AnimationState();
    public final AnimationState aimingState = new AnimationState();
    public final AnimationState attackState = new AnimationState();
    public final AnimationState swingState = new AnimationState();

    public NpcEntity(EntityType<NpcEntity> entityType, Level world) {
        super(entityType, world);
        this.updateAttackType();
        if (this.navigation instanceof GroundPathNavigation groundNavigation) {
            groundNavigation.setCanOpenDoors(true);
        }
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, SpawnGroupData entityData) {
        this.saveSpawnReason(spawnReason);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    // [IsFighting]
    public void setFighting(boolean state){
        this.entityData.set(IS_FIGHTING, state);
    }
    public boolean getFighting(){
        return this.entityData.get(IS_FIGHTING);
    }
    // [IsBlocking] // TODO
    public void setBlocking(boolean state){
        //this.dataTracker.set(IS_FIGHTING, state);
    }
    public boolean getBlocking(){
        return false; //this.dataTracker.get(IS_FIGHTING);
    }
    // [NpcTextureData]
    public void saveNpcTextureData(NpcTextureData npcTextureData) {
        this.entityData.set(NPC_TEXTURE_DATA, npcTextureData);
    }
    public NpcTextureData retrieveNpcTextureData() {
        return this.entityData.get(NPC_TEXTURE_DATA);
    }
    public boolean hasTextureData(){
        return retrieveNpcTextureData().get(NpcRenderedPart.BODY) != null;
    }
    public boolean shouldRefreshVisuals() {
        return this.retrieveNpcTextureData().needToBeRefreshed();
    }
    // [NpcInitializationData]
    public void saveNpcInitializationData(NpcInitializationData npcInitializationData) {
        this.entityData.set(NPC_INITIALIZATION_DATA, npcInitializationData);
    }
    public NpcInitializationData retrieveNpcInitializationData() {
        return this.entityData.get(NPC_INITIALIZATION_DATA);
    }
    public void prepare() {
        NpcInitializationData npcInitializationData = retrieveNpcInitializationData();
        boolean result = npcInitializationData.tryToInitialize(this);
        if(result)
            resetInitializationData();
    }

    public void resetInitializationData() {
        this.entityData.set(NPC_INITIALIZATION_DATA, new NpcInitializationData());
    }
    // [NpcTypeIdentifier]
    public void prepareNpcIdentifier(ResourceLocation npcTypeIdentifier){
        NpcInitializationData newNpcInitializationData = this.retrieveNpcInitializationData().withType(npcTypeIdentifier);
        this.saveNpcInitializationData(newNpcInitializationData);
    }
    // [NpcData]
    public void saveNpcData(NpcData npcData) {
        this.entityData.set(NPC_DATA, npcData);
    }
    public NpcData retrieveNpcData() {
        return this.entityData.get(NPC_DATA);
    }
    // [NpcType]
    public NpcType getNpcType(){
        return retrieveNpcData().getNpcType();
    }
    public void saveNpcType(Holder<NpcType> npcType){
        NpcData newNpcData = this.retrieveNpcData().withType(npcType);
        this.saveNpcData(newNpcData);
    }
    // [Loot Data]
    public LootData retrieveLootData(){
        NpcType npcType = retrieveNpcData().getNpcType();
        if(npcType == null)
            return null;
        return npcType.getLootData();
    }
    // [Category]
    public EntityCategories getNpcCategory() {
        return retrieveNpcData().getCategory();
    }
    public void saveCategory(EntityCategories category) {
        NpcData newNpcData = this.retrieveNpcData().withCategory(category);
        this.saveNpcData(newNpcData);
    }
    // [SpawnReason]
    private void saveSpawnReason(MobSpawnType spawnReason) {
        NpcData newNpcData = this.retrieveNpcData().withSpawnReason(spawnReason);
        this.saveNpcData(newNpcData);
    }
    public MobSpawnType getSpawnReason() {
        return this.retrieveNpcData().getSpawnReason();
    }
    // [StructureManager]
    public void assignStructureManager(StructureManagerBlockEntity blockEntity){
        assignStructureManager(blockEntity.getBlockPos());
    }

    public void assignStructureManager(BlockPos managerPos) {
        NpcData newNpcData = this.retrieveNpcData().withStructureManagerPos(managerPos);
        this.saveNpcData(newNpcData);
    }

    public void clearStructureManager() {
        NpcData newNpcData = this.retrieveNpcData().withoutStructureManagerPos();
        this.saveNpcData(newNpcData);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 32.0)
                .add(Attributes.ATTACK_DAMAGE, 1.0);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new TargetPlayerDiplomacyGoal(this));
        this.targetSelector.addGoal(4, new NpcDoorInteractGoal(this, true));
        this.targetSelector.addGoal(5, new TargetNPCDiplomacyGoal(this));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, SnowTrollEntity.class, true));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, SpawnOfShelobEntity.class, true));
        this.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, ShelobiteScuttlerEntity.class, true));
        this.targetSelector.addGoal(9, new NearestAttackableTargetGoal<>(this, ShelobiteLarvaEntity.class, true));
        this.targetSelector.addGoal(10, new NearestAttackableTargetGoal<>(this, AbstractHorse.class, true));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(NPC_INITIALIZATION_DATA, new NpcInitializationData());
        builder.define(NPC_DATA, new NpcData());
        builder.define(NPC_TEXTURE_DATA, new NpcTextureData());
        builder.define(IS_FIGHTING, false);
        builder.define(CROSSBOW_CHARGING, false);
        builder.define(USING_ITEM, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag view) {
        super.addAdditionalSaveData(view);
        RegistryOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, this.level().registryAccess());
        NpcData.CODEC.encodeStart(ops, this.retrieveNpcData())
                .result().ifPresent(tag -> view.put(KeyStrings.DATA, tag));
        NpcInitializationData.CODEC.encodeStart(ops, this.retrieveNpcInitializationData())
                .result().ifPresent(tag -> view.put(KeyStrings.INITIALIZATION_DATA, tag));
        NpcTextureData.CODEC.encodeStart(ops, this.retrieveNpcTextureData())
                .result().ifPresent(tag -> view.put(KeyStrings.TEXTURE_DATA, tag));
        view.putBoolean(KeyStrings.IS_FIGHTING, this.getFighting());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag view) {
        super.readAdditionalSaveData(view);
        RegistryOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, this.level().registryAccess());
        NpcData foundNpcData = view.contains(KeyStrings.DATA)
                ? NpcData.CODEC.parse(ops, view.get(KeyStrings.DATA)).result().orElse(new NpcData())
                : new NpcData();
        NpcInitializationData foundNpcInitializationData = view.contains(KeyStrings.INITIALIZATION_DATA)
                ? NpcInitializationData.CODEC.parse(ops, view.get(KeyStrings.INITIALIZATION_DATA)).result().orElse(new NpcInitializationData())
                : new NpcInitializationData();
        NpcTextureData foundNpcTextureData = view.contains(KeyStrings.TEXTURE_DATA)
                ? NpcTextureData.CODEC.parse(ops, view.get(KeyStrings.TEXTURE_DATA)).result().orElse(new NpcTextureData())
                : new NpcTextureData();
        boolean isFighting = view.getBoolean(KeyStrings.IS_FIGHTING);

        String npcDataId = view.contains("NpcDataId", Tag.TAG_STRING) ? view.getString("NpcDataId") : null;
        if(npcDataId != null){
            foundNpcInitializationData = foundNpcInitializationData.withType(MiddleEarth.fetchId(npcDataId));
            foundNpcData = new NpcData();
            foundNpcTextureData = new  NpcTextureData();
            isFighting = false;
        }

        this.entityData.set(NPC_DATA, foundNpcData);
        this.entityData.set(NPC_INITIALIZATION_DATA, foundNpcInitializationData);
        this.entityData.set(NPC_TEXTURE_DATA, foundNpcTextureData);
        this.entityData.set(IS_FIGHTING, isFighting);

        foundNpcInitializationData.tryToInitialize(this);

        this.updateAttackType();
    }

    @Override
    public boolean canFireProjectileWeapon(ProjectileWeaponItem weapon) {
        return true;
    }

    @Override
    public @Nullable ItemStack getPickResult() {
        if(getNpcType() == null)
            return ItemStack.EMPTY;
        return NpcSpawnEggHelper.getSpawnEgg(level(), getNpcType().getId());
    }
    public void updateAttackType() {
        if (this.level() != null && !this.level().isClientSide) {
            this.goalSelector.removeGoal(this.meleeAttackGoal);
            this.goalSelector.removeGoal(this.bowAttackGoal);
            ItemStack itemStack = this.getMainHandItem();
            if (itemStack.is(Items.BOW) || itemStack.is(ItemTagsME.BOW)) {
                int i = 30;
                if (this.level().getDifficulty() != Difficulty.HARD) {
                    i = 20;
                }
                this.bowAttackGoal.setAttackInterval(i);
                this.goalSelector.addGoal(4, this.bowAttackGoal);
            } if (itemStack.is(Items.CROSSBOW) || itemStack.is(ItemTagsME.CROSSBOW)) {
                this.goalSelector.addGoal(4, this.crossBowAttackGoal);
            } else {
                this.goalSelector.addGoal(4, this.meleeAttackGoal);
            }
        }
    }

    public void updateTargetGoals() {
    }

    //region [DATA TRANSFER]
    // GETTERS
    public ResourceLocation getNpcTypeIdentifier(){
        return retrieveNpcData().getNpcTypeId();
    }
    public ResourceLocation getFactionIdentifier(){
        Faction faction = getFaction();
        if(faction == null) return null;
        return faction.getId();
    }
    public BlockPos getStructureManagerHostPos() {
        return this.retrieveNpcData().getStructureManagerPos();
    }
    //endregion

    public void tryToInitializeData(){
        if(blockPosition().equals(BlockPos.ZERO)) // 0,0,0 is what's used for commands, needs to be delayed
            return;
        if(getNpcType() != null && hasTextureData())
            return;

        this.prepare();

        Level world = level();
        if(world.isClientSide)
            return;

        if(world instanceof ServerLevel serverWorld){
            if(NpcEntityInitializer.shouldInitialize(serverWorld, this)){
                NpcEntityInitializer.initializeNpcEntity(serverWorld, this);
            }
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.is(DamageTypes.IN_WALL) && this.isPassenger()) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    protected void customServerAiStep() {
        tryToInitializeData();
        //CombatArchetypeRuntimeData runtimeData = getCombatRuntimeData();
       // if(runtimeData != null)
           // runtimeData.tick(this, world);

        /*Profiler profiler = Profilers.get();
        profiler.push("npcBrain");
        this.getBrain().tick(world, this);
        profiler.pop();
        profiler.push("npcActivityUpdate");
        NpcBrain.updateActivities(this);
        profiler.pop();*/
        super.customServerAiStep();

        if(isPassenger()){
            this.setRot(getVehicle().getYRot(), getVehicle().getXRot());
        }
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        Vec3 pos = super.getPassengerAttachmentPoint(passenger, dimensions, scaleFactor);
        float yaw = this.getYRot() * Mth.DEG_TO_RAD;
        double backwards = -0.25 * scaleFactor;
        return pos.add(-backwards * Mth.sin(yaw), -0.3 * scaleFactor, backwards * Mth.cos(yaw));
    }

    @Override
    public boolean isUsingItem() {
        boolean value = super.isUsingItem();
        if(!value) {
            return this.entityData.get(USING_ITEM) > 0;
        }
        return value;
    }

    public void setNpcFlag(int mask, boolean value) {
        setLivingEntityFlag(mask, value);

        if(value) {
            int i = this.entityData.get(USING_ITEM) + 1;
            this.entityData.set(USING_ITEM, i);
        } else {
            this.entityData.set(USING_ITEM, 0);
        }
    }

    public float getFightingMovementSpeed(){
        double currentSpeed = this.getAttributeValue(Attributes.MOVEMENT_SPEED);
        return (float) (currentSpeed);
    }

    public boolean isFighting() {
        boolean isFighting = getFighting();

        this.setSprinting(isFighting);
        if(this.isPassenger() && getVehicle() instanceof AbstractHorse abstractHorseEntity){
            abstractHorseEntity.setSprinting(isFighting);
        }
        return isFighting;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if(!this.level().isClientSide) {

        } else {
            setupAnimationStates();
        }

    }

    private void setupAnimationStates() {
        if(this.zza > 0) {
            this.walkingState.startIfStopped(this.tickCount);
        } else {
            this.idleState.startIfStopped(this.tickCount);
        }

        int bowPullProgress = this.getTicksUsingItem();
        if(bowPullProgress > 0) {
            this.aimingState.startIfStopped(this.tickCount);
        }

    }

    @Override
    public boolean canControlVehicle() {
        return true;
    }

    @Override
    protected boolean couldAcceptPassenger() {
        return !this.isPassenger();
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance localDifficulty) {
        // Overrides vanilla init equipment (gold sets???)
    }

    @Override
    public void die(DamageSource source) {
        if(getVehicle() != null && getVehicle() instanceof LivingEntity vehicleEntity && vehicleEntity.getControllingPassenger() == this){
            vehicleEntity.setItemSlot(EquipmentSlot.BODY, Items.AIR.getDefaultInstance());
            vehicleEntity.ejectPassengers();
            if(vehicleEntity instanceof AbstractHorse abstractHorseEntity){
                abstractHorseEntity.getSlot(AbstractHorse.EQUIPMENT_SLOT_OFFSET).set(ItemStack.EMPTY);
                abstractHorseEntity.setTamed(false);
                abstractHorseEntity.resetLove();
                abstractHorseEntity.setSprinting(false);
                abstractHorseEntity.setOwnerUUID(null);
            }
            if(vehicleEntity instanceof AbstractBeastEntity abstractBeastEntity){
                abstractBeastEntity.resetTameness();
            }
        }
        super.die(source);
    }

    private boolean canDropLoot(DamageSource damageSource, boolean causedByPlayer){
        boolean canDropLoot = false;
        if(!causedByPlayer)
            return canDropLoot;
        if(damageSource == null)
            return canDropLoot;

        if(damageSource.getEntity() instanceof Player player){
            PlayerData data = StateSaverAndLoader.getPlayerStateReadOnly(player);
            if(data == null)
                canDropLoot = true;
            else if(data.getFaction() == null)
                canDropLoot = true;
            else{
                try{
                    Faction faction = FactionLookup.getFactionById(level(), data.getFaction());
                    if(faction.isHostileToward(this.getFactionIdentifier()))
                        canDropLoot = true;
                } catch (FactionIdentifierException e){
                    canDropLoot = true;
                }
            }
        }

        return canDropLoot;
    }

    @Override
    protected void dropAllDeathLoot(ServerLevel world, DamageSource damageSource) {
        if(!canDropLoot(damageSource, damageSource.getEntity() instanceof Player))
            return;
        super.dropAllDeathLoot(world, damageSource);
    }

    @Override
    protected int getBaseExperienceReward() {
        LootData lootData = retrieveLootData();
        if(lootData == null || !(level() instanceof ServerLevel world))
            return 1;
        return lootData.getExperience(world);
    }

    @Override
    protected void dropFromLootTable(DamageSource damageSource, boolean causedByPlayer) {
        if (!(level() instanceof ServerLevel world) || getNpcType() == null) {
            return;
        }
        ResourceKey<LootTable> lootTableRegistryKey = ResourceKey.create(Registries.LOOT_TABLE, getNpcType().getId().withPrefix("entities/"));
        LootTable lootTable = world.getServer().reloadableRegistries().getLootTable(lootTableRegistryKey);

        if (lootTable != null) {
            LootParams.Builder builder = (new LootParams.Builder(world)).withParameter(LootContextParams.THIS_ENTITY, this)
                    .withParameter(LootContextParams.ORIGIN, this.position())
                    .withParameter(LootContextParams.DAMAGE_SOURCE, damageSource)
                    .withOptionalParameter(LootContextParams.ATTACKING_ENTITY, damageSource.getEntity())
                    .withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, damageSource.getDirectEntity());
            Player playerEntity = causedByPlayer ? this.lastHurtByPlayer : null;
            if (playerEntity != null) {
                builder = builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, playerEntity).withLuck(playerEntity.getLuck());
            }

            LootParams lootWorldContext = builder.create(LootContextParamSets.ENTITY);
            lootTable.getRandomItems(lootWorldContext, this.getLootTableSeed(), this::spawnAtLocation);
        }
    }

    @Override
    public boolean isPersistenceRequired() {
        BlockPos structureManagerPos = getStructureManagerHostPos();
        if(structureManagerPos != null)
            return true;
        return super.isPersistenceRequired();
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
    }

    protected Faction getFaction(){
        NpcData data = retrieveNpcData();
        if(data == null)
            return null;
        ResourceLocation factionId = data.getFaction();
        if(factionId == null)
            return null;
        try {
            return FactionLookup.getFactionById(level(), factionId);
        } catch (FactionIdentifierException e) {
            return null;
        }
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity entity) {
        float reach = 1.75f;
        AttributeInstance interactionRange = this.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
        if (interactionRange != null) {
            reach = (float)interactionRange.getValue();
        }

        if(this.getVehicle() != null || entity.getVehicle() != null) {
            reach += 0.5f;
        }

        return this.distanceTo(entity) <= reach;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (!(this.level() instanceof ServerLevel world)) {
            return false;
        }
        this.level().broadcastEntityEvent(this, EntityEvent.START_ATTACKING);
        boolean targetDamaged;
        float damage = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        ItemStack itemStack = this.getWeaponItem();
        DamageSource damageSource = this.damageSources().mobAttack(this);

        var enchantmentDamage = EnchantmentHelper.modifyDamage(world, itemStack, target, damageSource, damage);
        var bonusDamage =  itemStack.getItem().getAttackDamageBonus(target, enchantmentDamage, damageSource);
        var finalDamage = enchantmentDamage + bonusDamage;

        if(isPassenger() && getVehicle() instanceof AbstractBeastEntity mountEntity){
            mountEntity.doHurtTarget(target);
        }

        targetDamaged = target.hurt(damageSource, finalDamage);
        if (targetDamaged) {
            float g = this.getKnockback(target, damageSource);
            if (g > 0.0f && target instanceof LivingEntity livingEntity) {
                livingEntity.knockback(g * 0.5f, Mth.sin(this.getYRot() * ((float)Math.PI / 180)), -Mth.cos(this.getYRot() * ((float)Math.PI / 180)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }
            if (target instanceof LivingEntity livingEntity) {
                var item = itemStack.getItem();
                if (item.hurtEnemy(itemStack, livingEntity, this)) {
                    item.postHurtEnemy(itemStack, livingEntity, this);
                }
            }
            EnchantmentHelper.doPostAttackEffects(world, target, damageSource);
            this.setLastHurtMob(target);
            this.playAttackSound();

        }
        return targetDamaged;
    }

    @Override
    public void rideTick() {
        super.rideTick();
        Entity entity = this.getControlledVehicle();
        if (entity instanceof PathfinderMob pathAwareEntity) {
            this.yBodyRot = pathAwareEntity.yBodyRot;
        }
    }

    @Override
    protected Component getTypeName() {
        if(this.getNpcTypeIdentifier() == null) {
            return Component.translatable("npc_type."+ MiddleEarth.MOD_ID +".npc");
        }
        return Component.translatable(this.getNpcTypeIdentifier().toLanguageKey("npc_type"));
    }

    @Override
    protected void blockUsingShield(LivingEntity attacker) {
        super.blockUsingShield(attacker);
        if (attacker.canDisableShield()) {
            this.stopUsingItem();
            this.level().broadcastEntityEvent(this, (byte)30);
        }
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return shouldTarget(this, target) && super.canAttack(target);
    }

    public static boolean shouldTarget(NpcEntity npcEntity, LivingEntity target){
        // TODO : datadriven
        if(target instanceof SnailEntity || target instanceof Monster || target instanceof SnowTrollEntity || target instanceof Pouncer)
            return true;
        if(!npcEntity.isAlliedTo(target)){
            return true;
        }
        return false;
    }

    public int getTickAttackSpeedCooldown(){
        if(!this.getAttributes().hasAttribute(Attributes.ATTACK_SPEED))
            return 1;
        return (int)this.getAttributes().getValue(Attributes.ATTACK_SPEED);
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel world, DamageSource source, boolean causedByPlayer) {
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

    protected AbstractArrow createArrowProjectile(ItemStack arrow, float damageModifier, @Nullable ItemStack shotFrom) {
        return ProjectileUtil.getMobArrow(this, arrow, damageModifier, shotFrom);
    }

    public boolean isAiming() {
        int i = this.getTicksUsingItem();
        return i > 0;
    }

    public void aim() {
        var currentItem = getUseItem();
        if(currentItem.isEmpty()){
            stopUsingItem();
            startUsingItem(InteractionHand.MAIN_HAND);
        }
    }

    public void stopAiming() {
        var currentItem = getUseItem();
        if(currentItem.isEmpty()){
            this.stopUsingItem();
            return;
        }
        this.stopUsingItem();
    }

    public boolean isReadyToShoot() {
        return getMainHandItem() != null;
        //int i = this.getItemUseTime();
        //if (i >= 20) {
        //    return true;
        //}
        //return false;
    }

    private void shootAt(LivingEntity target, float pullProgress, float powerModifier) {
        if (!isReadyToShoot())
            return;

        ItemStack weapon = this.getMainHandItem();
        ItemStack projectileStack = this.getProjectile(weapon);

        AbstractArrow projectile = this.createArrowProjectile(projectileStack, pullProgress, weapon);

        double distanceX = target.getX() - this.getX();
        double distanceZ = target.getZ() - this.getZ();
        double horizontalDistance = Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);

        double distanceY = target.getY(0.3F) - projectile.getY();

        float scale = this.getScale();
        float velocity = 1.6F * powerModifier;

        // Reduce the vanilla arc compensation for larger entities and faster arrows.
        double arcCompensation = horizontalDistance * (0.2F / (scale * powerModifier));

        if (this.level() instanceof ServerLevel serverWorld) {
            projectile.shoot(
                    distanceX,
                    distanceY + arcCompensation,
                    distanceZ,
                    velocity,
                    (float) (14 - serverWorld.getDifficulty().getId() * 4));
            serverWorld.addFreshEntity(projectile);
        }

        this.playSound(
                SoundEvents.ARROW_SHOOT,
                1.0F,
                1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F)
        );

        stopAiming();
    }

    @Override
    public void performRangedAttack(LivingEntity target, float pullProgress) {
        this.shootAt(target, 1, pullProgress);
    }

    public void shootAt(LivingEntity livingEntity) {
        try{
            this.shootAt(livingEntity, BowItem.getPowerForTime(getTicksUsingItem()), 2f);
        } catch (IllegalArgumentException e){
            this.shootAt(livingEntity, CustomLongbowWeaponItem.getPullProgressLongbow(getTicksUsingItem()), 3f);
        }
    }
    public void shootCrossbowAt(LivingEntity target) {
        this.shootAt(target, 1, 1.25f);
    }

    public boolean isCharging() {
        return this.entityData.get(CROSSBOW_CHARGING);
    }

    @Override
    public void setChargingCrossbow(boolean charging) {
        this.entityData.set(CROSSBOW_CHARGING, charging);
    }

    @Override
    public void onCrossbowAttackPerformed() {
        this.entityData.set(CROSSBOW_CHARGING, false);
    }

    @Override
    public boolean isAlliedTo(Entity other) {
        if(other instanceof NpcEntity npc){
            return !isHostileToward(npc);
        }
        else if(other instanceof Player player){
            return !isHostileTowardPlayer(player);
        }
        return !isHostileToward(other);
    }

    private boolean isHostileTowardPlayer(Player player) {
        if(player.level().isClientSide || !player.canBeSeenAsEnemy())
            return false;
        PlayerData playerData = StateSaverAndLoader.getPlayerStateReadOnly(player);
        if(playerData == null || playerData.getFaction() == null)
            return true;
        Faction ownFaction = getFaction();
        if(ownFaction == null)
            return true;
        if(ownFaction.isHostileToward(playerData.getFaction()))
            return true;
        return false;
    }

    private boolean isHostileToward(Entity other) {
        if(other instanceof SnailEntity || other instanceof Monster || other instanceof TrollEntity || other instanceof Pouncer)
            return true;

        if(!other.isVehicle())
            return false;

        if(other.getControllingPassenger() instanceof NpcEntity npc && isHostileToward(npc)) {
            return true;
        }
        return false;
    }

    private boolean isHostileToward(NpcEntity npc) {
        Faction ownFaction = getFaction();
        if(ownFaction == null)
            return true;
        ResourceLocation otherNpcFaction = npc.getFactionIdentifier();
        if(otherNpcFaction == null)
            return true;
        if(ownFaction.isHostileToward(otherNpcFaction))
            return true;
        return false;
    }

    public static boolean canSpawn(EntityType<NpcEntity> type, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos blockPos, RandomSource random) {
        return SpawnUtil.canSpawn(blockPos, serverWorldAccess, spawnReason);
    }

    static {
        NPC_DATA = SynchedEntityData.defineId(NpcEntity.class, TrackedDataHandlerRegistryME.NPC_DATA);
        NPC_INITIALIZATION_DATA = SynchedEntityData.defineId(NpcEntity.class, TrackedDataHandlerRegistryME.NPC_INITIALIZATION_DATA);
        NPC_TEXTURE_DATA = SynchedEntityData.defineId(NpcEntity.class, TrackedDataHandlerRegistryME.NPC_TEXTURE_DATA);
        IS_FIGHTING = SynchedEntityData.defineId(NpcEntity.class, EntityDataSerializers.BOOLEAN);
    }
}
