package net.sevenstars.middleearth.entity.npcs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.Schedule;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import net.sevenstars.api.entity.ai.brain.MemoryModulesAPI;
import net.sevenstars.api.entity.ai.brain.SchedulesAPI;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.entity.ModEntityAttributes;
import net.sevenstars.middleearth.entity.ModTrackedDataHandlerRegistry;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.npcs.data.NpcEntityTextureData;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.common.FactionType;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class NpcEntity extends PassiveEntity implements EquipmentHolder {
    // Data to use
    private static final TrackedData<String> CATEGORY;
    private static final TrackedData<String> FACTION_ID;
    private static final TrackedData<String> NPC_DATA_ID;
    private static final TrackedData<Long> INITIALIZATION_TICK;
    private static final TrackedData<NpcEntityTextureData> TEXTURE_DATA;
    private static final TrackedData<Boolean> FIGHTING;
    private static final TrackedData<Boolean> BLOCKING;

    public NpcEntity(EntityType<NpcEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(INITIALIZATION_TICK, 0l);
        builder.add(CATEGORY, "");
        builder.add(FACTION_ID, "");
        builder.add(NPC_DATA_ID, "");
        builder.add(TEXTURE_DATA, new NpcEntityTextureData());
        builder.add(FIGHTING, false);
        builder.add(BLOCKING, false);
        assignStructureManager(null);
        assignBed(null);
    }

    @Override
    public void writeData(WriteView view) {
        super.writeData(view);
        this.writeEntityData(view);
    }

    @Override
    public void readData(ReadView view) {
        super.readData(view);
        this.readEntityData(view);
    }

    private void writeEntityData(WriteView view){
        view.put("NpcDataId", Codec.STRING, dataTracker.get(NPC_DATA_ID));
        view.put("EntityCategory", Codec.STRING, dataTracker.get(CATEGORY));
        view.put("NpcTextureData", NpcEntityTextureData.CODEC, dataTracker.get(TEXTURE_DATA));
        view.put("InitializationTick", Codec.LONG, dataTracker.get(INITIALIZATION_TICK));
    }

    private void readEntityData(ReadView view) {
        view.read("NpcDataId", Codec.STRING)
            .ifPresent(this::setNpcData);
        view.read("FactionId", Identifier.CODEC)
            .ifPresent(this::setFactionId);
        view.read("EntityCategory", Codec.STRING)
            .ifPresent(x -> {
                if(!x.isEmpty()){
                    setNpcCategory(EntityCategories.valueOf(x));
                }
            });
        view.read("NpcTextureData", NpcEntityTextureData.CODEC)
            .ifPresent(this::setNpcTextureData);
        view.read("InitializationTick", Codec.LONG)
                .ifPresent(x -> dataTracker.set(INITIALIZATION_TICK, x));

        tryToInitializeData();
    }

    public void setNpcData(String value) {
        if(value == null || value.isEmpty())
            return;
        this.dataTracker.set(NPC_DATA_ID, value);
    }

    public void setNpcData(Identifier npcDataId) {
        if(npcDataId == null)
            return;
        this.dataTracker.set(NPC_DATA_ID, npcDataId.toString());
    }

    public void setNpcData(NpcData npcData) {
        if(npcData != null){
            this.dataTracker.set(NPC_DATA_ID, npcData.getId().toString());
        }
    }

    public void setFactionId(Identifier factionId) {
        if(factionId == null)
            return;
        this.dataTracker.set(FACTION_ID, factionId.toString());
    }

    public void setNpcCategory(EntityCategories entityCategories) {
        if(entityCategories == null)
            return;
        this.dataTracker.set(CATEGORY, entityCategories.name());
    }

    public void setNpcTextureData(NpcEntityTextureData npcEntityTextureData) {
        if(npcEntityTextureData == null)
            return;
        this.dataTracker.set(TEXTURE_DATA, npcEntityTextureData);
    }

    public void assignStructureManager(StructureManagerBlockEntity blockEntity){
        if(getBrain() == null)
            return;
        boolean hasStructure = blockEntity != null;
        if(!hasStructure){
            this.getBrain().forget(MemoryModulesME.STRUCTURE_MANAGER_HOST_POS);
            return;
        }
        this.getBrain().remember(MemoryModulesME.STRUCTURE_MANAGER_HOST_POS, blockEntity.getPos());
    }

    public void assignBed(BedBlockEntity bedBlockEntity){
        if(getBrain() == null)
            return;
        if(bedBlockEntity == null){
            this.getBrain().forget(MemoryModulesME.ASSIGNED_BED_POS);
            return;
        }
        //this.getBrain().remember(MemoryModulesME.ASSIGNED_BED_POS, new GlobalPos(getWorld(), bedBlockEntity.getPos()));
    }

    public void setFighting(boolean state){
        this.dataTracker.set(FIGHTING, state);
    }

    public void tryToInitializeData(){
        if(Objects.equals(getBlockPos(), new BlockPos(0, 0, 0))) // 0,0,0 is what's used for commands, needs to be delayed
            return;

        World world = getWorld();
        if(!world.isClient && world instanceof ServerWorld serverWorld){
            if(NpcEntityInitializer.shouldInitialize(serverWorld, this)){
                NpcEntityInitializer.initializeNpcEntity(serverWorld, this);
            }
        }
    }

    @Override
    protected void mobTick(ServerWorld world) {
        tryToInitializeData();
        Profiler profiler = Profilers.get();
        profiler.push("npcBrain");
        this.getBrain().tick(world, this);
        profiler.pop();
        profiler.push("npcActivityUpdate");
        NpcBrain.updateActivities(this);
        profiler.pop();
        super.mobTick(world);

        if(hasVehicle()){
            rotate(getVehicle().getYaw(), getVehicle().getPitch());
        }
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return NpcBrain.create(this, dynamic);
    }

    public Brain<NpcEntity> getBrain() {
        return (Brain<NpcEntity>)super.getBrain();
    }

    public float getFightingMovementSpeed(){
        var currentSpeed = this.getAttributeValue(EntityAttributes.MOVEMENT_SPEED);
        return (float) (currentSpeed);
    }

    public boolean isFighting() {
        var memory = this.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        boolean isFighting;
        if (memory != null && memory.isPresent()) {
            isFighting = true;
        } else {
            isFighting = dataTracker.get(FIGHTING);
        }

        this.setSprinting(isFighting);
        if(this.hasVehicle() && getVehicle() instanceof AbstractHorseEntity abstractHorseEntity){
            abstractHorseEntity.setSprinting(isFighting);
        }
        return isFighting;
    }

    @Override
    public void tickMovement() {
        if(!this.getWorld().isClient) {
            this.setAttacking(this.getTarget() != null);
            if(this.isAttacking() && !this.isFighting()) {
                this.getBrain().setSchedule(Schedule.EMPTY);
                this.setFighting(true);
            } else if (!this.isAttacking() && this.isFighting()) {
                this.getBrain().setSchedule(SchedulesAPI.DEFAULT_SLEEP);
                this.getBrain().forget(MemoryModuleType.LOOK_TARGET);
                this.getBrain().forget(MemoryModuleType.WALK_TARGET);
                this.getBrain().forget(MemoryModulesAPI.DEFENDING_HOME);
                this.setFighting(false);
            }
        } else {
            setupAnimationStates();
        }

        super.tickMovement();
    }

    private void setupAnimationStates() {

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
    protected void initEquipment(net.minecraft.util.math.random.Random random, LocalDifficulty localDifficulty) {
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
        if(getBrain().getOptionalMemory(MemoryModulesME.STRUCTURE_MANAGER_HOST_POS).isPresent()){
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
                    canDropLoot = data.getFaction().compareTo(getFactionId()) != 0;
            }
        }

        if(!canDropLoot)
            return;

        RegistryKey<LootTable> lootTableRegistryKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, getNpcDataId().withPrefixedPath("entities/"));
        LootTable lootTable = world.getServer().getReloadableRegistries().getLootTable(lootTableRegistryKey);

        if (lootTable != null) {
            LootWorldContext.Builder builder = (new LootWorldContext.Builder(world)).add(LootContextParameters.THIS_ENTITY, this).add(LootContextParameters.ORIGIN, this.getPos()).add(LootContextParameters.DAMAGE_SOURCE, damageSource).addOptional(LootContextParameters.ATTACKING_ENTITY, damageSource.getAttacker()).addOptional(LootContextParameters.DIRECT_ATTACKING_ENTITY, damageSource.getSource());
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
        if(getBrain() == null) return super.isPersistent();

        if(getBrain().getOptionalMemory(MemoryModulesME.STRUCTURE_MANAGER_HOST_POS).isPresent())
            return getWorld().getBlockEntity(getBrain().getOptionalMemory(MemoryModulesME.STRUCTURE_MANAGER_HOST_POS).get()) != null;
        return super.isPersistent();
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
    }

    public Identifier getFactionId()
    {
        return Identifier.of(this.dataTracker.get(FACTION_ID));
    }

    public static Faction tryGetFaction(LivingEntity entity){
        if(entity instanceof NpcEntity npcEntity){
            return npcEntity.getFaction();
        }
        return null;
    }

    public static RaceType tryGettingRaceType(LivingEntity entity){
        if(entity instanceof NpcEntity npcEntity){
            return npcEntity.getRaceType();
        }
        return null;
    }

    protected Faction getFaction(){
        if(getFactionId() == null)
            return null;
        try {
            return FactionLookup.getFactionById(getWorld(), getFactionId());
        } catch (FactionIdentifierException e) {
            return null;
        }
    }

    protected RaceType getRaceType(){
        if(getNpcDataId() == null || getNpcData() == null)
            return null;
        Race race = RaceLookup.getRace(getWorld(), getNpcData().getRace());
        if(race == null)
            return null;
        return race.getRaceType();
    }

    public Long getInitializationTick() {
        return this.dataTracker.get(INITIALIZATION_TICK);
    }

    public Identifier getNpcDataId() {
        return Identifier.of(this.dataTracker.get(NPC_DATA_ID));
    }

    public NpcData getNpcData() {
        var id = getNpcDataId();
        if(id == null)
            return null;
        return getWorld().getRegistryManager().getOrThrow(DynamicRegistriesME.NPC).get(id);
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
            this.swingHand(Hand.MAIN_HAND);
            this.swingHand(Hand.OFF_HAND);
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
        return Text.translatable(this.getNpcDataId().toTranslationKey("npc_data"));
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

    public void setBlocking(boolean blockingState){
        var blockingItem = this.getBlockingItem();
        if(blockingItem == null){
            return;
        }
        this.dataTracker.set(BLOCKING, blockingState);
        this.getOffHandStack().getUseAction();
    }

    @Override
    public boolean isBlocking() {
        return true;
    }

    @Override
    public boolean isUsingItem() {
        return true;
    }

    private boolean getBlockingData(){
        return dataTracker.get(BLOCKING);
    }

    public Optional<LivingEntity> getHurtBy() {
        return this.getBrain()
                .getOptionalRegisteredMemory(MemoryModuleType.HURT_BY)
                .map(DamageSource::getAttacker)
                .filter(attacker -> attacker instanceof LivingEntity)
                .map(livingAttacker -> (LivingEntity)livingAttacker);
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        return shouldTarget(this, target) && super.canTarget(target);
    }

    public static boolean shouldTarget(NpcEntity npcEntity, LivingEntity target){
        Faction faction = npcEntity.getFaction();
        if(faction != null){
            if(target instanceof PlayerEntity player && player.canTakeDamage()){
                var playerFaction = StateSaverAndLoader.getPlayerState(player).getFaction();
                if(playerFaction == null)
                    return true;
                if(faction.getDiplomaticEnemies().contains(playerFaction))
                    return true;
            }

            if(target instanceof NpcEntity targetNpcEntity){
                if(targetNpcEntity.hasVehicle() && targetNpcEntity.getVehicle() instanceof AbstractHorseEntity)
                    return false;

                Faction targetFaction = targetNpcEntity.getFaction();
                if(targetFaction == null || faction.getDiplomaticEnemies().contains(targetFaction.getId()))
                    return true;
                else if(targetFaction.getFactionType() == FactionType.SUBFACTION){
                    if(faction.getDiplomaticEnemies().contains(targetFaction.getParentFaction(npcEntity.getWorld()).getId()))
                        return true;
                }
            }

            if(target instanceof AbstractHorseEntity abstractHorseEntity){
                if(abstractHorseEntity.hasPassengers()){
                    var entityList = abstractHorseEntity.getPassengersDeep();
                    for(Entity entity : entityList){
                        if(entity instanceof NpcEntity targetNpcEntity){
                            Faction targetFaction = targetNpcEntity.getFaction();
                            if(targetFaction == null || faction.getDiplomaticEnemies().contains(targetFaction.getId()))
                                return true;
                            else if(targetFaction.getFactionType() == FactionType.SUBFACTION){
                                if(faction.getDiplomaticEnemies().contains(targetFaction.getParentFaction(npcEntity.getWorld()).getId()))
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

    @Override
    public @Nullable LivingEntity getTarget() {
        return getTargetInBrain();
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        this.getBrain().remember(MemoryModuleType.ATTACK_TARGET, target);
    }

    public EntityCategories getNpcCategory() {
        var category = this.dataTracker.get(CATEGORY);
        if(category == null || category.isEmpty())
            return null;
        return EntityCategories.valueOf(category);
    }

    public BlockPos getStructureManagerHostPos() {
        if(getBrain().getOptionalMemory(MemoryModulesME.STRUCTURE_MANAGER_HOST_POS).isPresent()){
            return getBrain().getOptionalMemory(MemoryModulesME.STRUCTURE_MANAGER_HOST_POS).get();
        }
        return null;
    }

    public BlockPos getAssignedBedPos() {
        if(getBrain().getOptionalMemory(MemoryModulesME.ASSIGNED_BED_POS).isPresent()){
            return getBrain().getOptionalMemory(MemoryModulesME.ASSIGNED_BED_POS).get();
        }
        return null;
    }

    public NpcEntityTextureData getNpcTextureData() {
        return this.dataTracker.get(TEXTURE_DATA);
    }
    public boolean hasTextureData(){
        NpcEntityTextureData textureData = getNpcTextureData();
        return textureData.getBodyTexture() != null;
    }

    static {
        INITIALIZATION_TICK = DataTracker.registerData(NpcEntity.class, ModTrackedDataHandlerRegistry.INITIALIZATION_TICK);
        FACTION_ID = DataTracker.registerData(NpcEntity.class, ModTrackedDataHandlerRegistry.FACTION_ID);
        NPC_DATA_ID = DataTracker.registerData(NpcEntity.class, ModTrackedDataHandlerRegistry.NPC_DATA_ID);
        CATEGORY = DataTracker.registerData(NpcEntity.class, ModTrackedDataHandlerRegistry.CATEGORY);
        TEXTURE_DATA = DataTracker.registerData(NpcEntity.class, ModTrackedDataHandlerRegistry.NPC_ENTITY_TEXTURE_DATA);
        FIGHTING = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        BLOCKING = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.ATTACK_DAMAGE, 2.0)
                .add(ModEntityAttributes.WIDTH_SCALE, 1.0);
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

    public void releaseTicketFor(MemoryModuleType<GlobalPos> destination) {
        this.releaseTicketFor(MemoryModuleType.HOME);
    }

    public float getWidthScale() {
        try{
            return (float) this.getAttributeValue(ModEntityAttributes.WIDTH_SCALE);
        }
        catch (Exception ignored){
            return 1.0f;
        }
    }
}
