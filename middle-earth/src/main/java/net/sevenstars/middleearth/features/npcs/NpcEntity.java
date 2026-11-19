package net.sevenstars.middleearth.features.npcs;

import net.sevenstars.middleearth.block.special.structureManager.features.NestHost;
import net.sevenstars.middleearth.dev.NpcDebug;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.sevenstars.middleearth.features.npcs.pathing.NpcMoveControl;
import net.sevenstars.middleearth.features.npcs.pathing.NpcNavigation;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityAttributesME;
import net.sevenstars.middleearth.entity.TrackedDataHandlerRegistryME;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.features.npcs.jobs.NpcJob;
import net.sevenstars.middleearth.features.npcs.ai.NpcBrain;
import net.sevenstars.middleearth.features.npcs.data.NpcData;
import net.sevenstars.middleearth.features.npcs.data.NpcInitializationData;
import net.sevenstars.middleearth.features.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.features.npcs.initializer.NpcEntityInitializer;
import net.sevenstars.middleearth.features.npcs.initializer.NpcSpawnEggHelper;
import net.sevenstars.middleearth.features.npcs.relations.NpcAlarm;
import net.sevenstars.middleearth.features.npcs.relations.NpcRelations;
import net.sevenstars.middleearth.features.npcs.routine.NpcRoutine;
import net.sevenstars.middleearth.features.npcs.routine.PrisonCell;
import net.sevenstars.middleearth.features.npcs.jobs.NpcJobs;
import net.sevenstars.middleearth.features.npcs.renderer.NpcRenderedPart;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.minecraft.item.Item;
import net.sevenstars.middleearth.item.items.shields.CustomSiegeShieldItem;
import net.sevenstars.middleearth.item.items.weapons.ReachWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomLongbowWeaponItem;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.features.npcs.classes.NpcClass;
import net.sevenstars.middleearth.features.npcs.classes.data.LootData;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.utils.ItemTagsME;
import net.sevenstars.middleearth.utils.SpawnUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class NpcEntity extends PathAwareEntity implements CrossbowUser {

    public static class KeyStrings {
        public static final String DATA = "NpcData";
        public static final String INITIALIZATION_DATA = "InitializationData";
        public static final String TEXTURE_DATA = "TextureData";
        public static final String PARTY_LEADER = "PartyLeader";
        public static final String TRAVEL_HEADING = "TravelHeading";
        public static final String TRAVEL_DESTINATION = "TravelDestination";
        public static final String ROUTINE_HOME = "RoutineHome";
        public static final String JOB = "Role";
        public static final String PRISON_CELL = "PrisonCell";
        public static final String ESCORTING = "Escorting";
        public static final String ASSIGNED_BED = "AssignedBed";
        public static final String BED_STAND = "BedStand";
        public static final String STORE = "Store";
        public static final String LINKS = "Links";
        public static final String ROUTINE_STOWED_ITEM = "RoutineStowedItem";
        public static final String CONFISCATED_MAIN = "ConfiscatedMainHand";
        public static final String CONFISCATED_OFF = "ConfiscatedOffHand";
        public static final String ROUTINE_STOWED_MAIN_HAND = "RoutineStowedMainHand";
    }

    // [TRACKED DATA AND CONSTANTS]
    private static final TrackedData<NpcData> NPC_DATA;
    private static final TrackedData<NpcInitializationData> NPC_INITIALIZATION_DATA;
    private static final TrackedData<NpcTextureData> NPC_TEXTURE_DATA;
    // Tracked so the client can show it in the name
    private static final TrackedData<String> JOB = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> USING_ITEM = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> TALK = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final TrackedData<Boolean> CROSSBOW_CHARGING = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    static {
        NPC_DATA = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistryME.NPC_DATA);
        NPC_INITIALIZATION_DATA = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistryME.NPC_INITIALIZATION_DATA);
        NPC_TEXTURE_DATA = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistryME.NPC_TEXTURE_DATA);
    }

    public enum AttackType {
        MELEE,
        BOW,
        CROSSBOW
    }

    public enum Talk {
        NONE(false, -1),
        SAY(true, 0),
        ASK(true, 1),
        EXCLAIM(true, 2),
        LAUGH(true, 3),
        NOD(false, 4);

        public static final List<Talk> SPOKEN = List.of(SAY, SAY, ASK, EXCLAIM, LAUGH);
        private static final Talk[] VALUES = values();
        private final boolean spoken;
        // Column in the speech bubble texture
        private final int bubble;

        Talk(boolean spoken, int bubble) {
            this.spoken = spoken;
            this.bubble = bubble;
        }

        public int bubble() {
            return bubble;
        }

        public boolean isSpoken() {
            return spoken;
        }
    }

    // Narrower than a board leaves in its block
    private static final float MAXIMUM_WIDTH = 0.8f;

    // In ticks
    private static final int MANAGER_CHECK_INTERVAL = 100;
    private static final int WEAPON_CHECK_INTERVAL = 10;
    private static final int TARGET_CHECK_INTERVAL = 20;
    private static final int DAY_TICKS = 24000;

    // In days
    private static final int ELF_SLEEP_INTERVAL = 4;

    // In blocks
    private static final int WAKE_RANGE = 16;
    private static final double FOLLOW_RANGE = 32.0;

    // In blocks
    private static final int HOME_STAND_RANGE = 5;
    // Rising faster than this means it's climbing out of water
    private static final double CLIMBING_OUT = 0.1;

    private static final double BASE_ATTACK_DAMAGE = 1.0;
    private static final float KNOCKBACK_SCALE = 0.5f;
    private static final double KNOCKBACK_SELF_SLOWDOWN = 0.6;
    // Blocks, feet to feet. Races set their own; weapons add to it
    private static final double DEFAULT_REACH = 1.75;
    private static final float MOUNTED_REACH_BONUS = 0.5f;
    private static final float CONTACT_REACH = 0.25f;

    private static final EquipmentSlot[] ARMOUR_SLOTS = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    // In ticks
    private static final int BOW_ATTACK_INTERVAL = 20;
    private static final int BOW_ATTACK_INTERVAL_HARD = 30;
    // Times the weapon's power, in blocks a tick. A full vanilla bow is 3
    private static final float BASE_ARROW_SPEED = 1.6F;
    private static final float CROSSBOW_ARROW_POWER = 1.25f;
    private static final float NO_INACCURACY = 0f;

    // From the middle of the shoulders, lower and behind
    private static final double PASSENGER_OFFSET_Y = -0.3;
    private static final double PASSENGER_OFFSET_Z = -0.25;

    private static final int FALLBACK_EXPERIENCE = 1;
    private static final String LOOT_TABLE_FOLDER = "entities/";

    // [FIELDS]
    private final NpcHousehold household = new NpcHousehold();
    private final NpcParty party = new NpcParty(this);
    private final NpcPrison prison = new NpcPrison(this);
    private final NpcAlarm alarm = new NpcAlarm(this);
    private final HatchOpener hatches = new HatchOpener(this);

    private boolean onOuting = false;
    private boolean farming = false;
    private @Nullable BlockPos invitedTo = null;
    private long invitedUntil = 0;
    private @Nullable ItemStack routineStowedItem = null;
    private boolean routineStowedMainHand = false;
    private @Nullable Vec3d sleptFrom = null;
    // Age it fell silent (server) and last changed activity (client)
    private int talkUntil;
    private int talkSince;

    private @Nullable LivingEntity huntTarget = null;
    private @Nullable EntityType<?> huntKill = null;
    private int huntEndAge = 0;

    private boolean passingDoor = false;

    private String cachedJobText = "";
    private @Nullable Identifier cachedJobId = null;
    private @Nullable NpcJob cachedJob = null;
    private boolean jobLookedUp = false;
    private @Nullable Identifier cachedFactionId = null;
    private @Nullable Faction cachedFaction = null;
    private boolean managerPresent = true;
    private int managerCheckAge = 0;
    private boolean dataInitialized = false;

    // Built on first use, server only
    private @Nullable NpcRoutine dayRoutine = null;
    private AttackType attackType = AttackType.MELEE;
    private int bowAttackInterval = BOW_ATTACK_INTERVAL;

    // [CONSTRUCTION AND REGISTRATION]
    public NpcEntity(EntityType<NpcEntity> entityType, World world) {
        super(entityType, world);
        this.updateAttackType();
        this.navigation.setCanOpenDoors(true);
        this.navigation.setCanSwim(true);
        this.moveControl = new NpcMoveControl(this);
        // Vanilla only makes fire costly. Npcs should never walk into fire, campfires or braziers
        setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1);
        setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, -1);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.FOLLOW_RANGE, FOLLOW_RANGE)
                .add(EntityAttributes.ATTACK_DAMAGE, BASE_ATTACK_DAMAGE)
                .add(EntityAttributes.ENTITY_INTERACTION_RANGE, DEFAULT_REACH);
    }

    public static boolean canSpawn(EntityType<NpcEntity> type, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        return SpawnUtil.canSpawn(blockPos, serverWorldAccess, spawnReason);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(NPC_INITIALIZATION_DATA, new NpcInitializationData());
        builder.add(NPC_DATA, new NpcData());
        builder.add(NPC_TEXTURE_DATA, new NpcTextureData());
        builder.add(JOB, "");
        builder.add(CROSSBOW_CHARGING, false);
        builder.add(USING_ITEM, 0);
        builder.add(TALK, 0);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        if(TALK.equals(data))
            talkSince = age;
    }

    public void talk(Talk talk, int ticks) {
        dataTracker.set(TALK, talk.ordinal());
        talkUntil = age + ticks;
    }

    public Talk getTalk() {
        int talk = dataTracker.get(TALK);
        return talk >= 0 && talk < Talk.VALUES.length ? Talk.VALUES[talk] : Talk.NONE;
    }

    public float getTalkAge(float tickDelta) {
        return age - talkSince + tickDelta;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public int getBowAttackInterval() {
        return bowAttackInterval;
    }

    public void updateAttackType() {
        if (this.getEntityWorld() == null || this.getEntityWorld().isClient())
            return;
        ItemStack itemStack = this.getMainHandStack();
        AttackType wanted = AttackType.MELEE;
        if (itemStack.getItem() instanceof BowItem) {
            boolean hard = this.getEntityWorld().getDifficulty() == Difficulty.HARD;
            this.bowAttackInterval = hard ? BOW_ATTACK_INTERVAL_HARD : BOW_ATTACK_INTERVAL;
            wanted = AttackType.BOW;
        } else if (itemStack.isOf(Items.CROSSBOW) || itemStack.isIn(ItemTagsME.CROSSBOW)) {
            wanted = AttackType.CROSSBOW;
        }
        if(wanted == this.attackType)
            return;
        // Ending the attack drops the crossbow's target, so put it back
        LivingEntity target = getTarget();
        NpcBrain.stopAttack(this);
        this.attackType = wanted;
        if(target != null && target.isAlive() && getTarget() == null)
            setTarget(target);
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return getTargetInBrain();
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        getBrain().remember(MemoryModuleType.ATTACK_TARGET, target);
    }

    @Override
    protected Brain.Profile<NpcEntity> createBrainProfile() {
        return NpcBrain.createProfile();
    }

    // Also runs from the constructor, before there's a brain
    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        Brain<NpcEntity> replaced = getBrain();
        if(replaced != null && getEntityWorld() instanceof ServerWorld world)
            replaced.stopAllTasks(world, this);
        return NpcBrain.create(createBrainProfile().deserialize(dynamic), getEntityWorld());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<NpcEntity> getBrain() {
        return (Brain<NpcEntity>) super.getBrain();
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new NpcNavigation(this, world);
    }

    // Capped so tall elves fit beside ladders and shutters
    @Override
    protected EntityDimensions getBaseDimensions(EntityPose pose) {
        EntityDimensions base = super.getBaseDimensions(pose);
        float width = base.width() * getScale();
        return width > MAXIMUM_WIDTH ? base.scaled(MAXIMUM_WIDTH / width, 1) : base;
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData) {
        this.saveSpawnReason(spawnReason);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
    }

    // [SAVE AND LOAD]
    @Override
    public void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.put(KeyStrings.DATA, NpcData.CODEC, this.retrieveNpcData());
        view.put(KeyStrings.INITIALIZATION_DATA, NpcInitializationData.CODEC, this.retrieveNpcInitializationData());
        view.put(KeyStrings.TEXTURE_DATA, NpcTextureData.CODEC, this.retrieveNpcTextureData());
        household.write(view);
        party.write(view);
        prison.write(view);
        view.putNullable(KeyStrings.JOB, Identifier.CODEC, this.getJobId());
        view.putNullable(KeyStrings.ROUTINE_STOWED_ITEM, ItemStack.OPTIONAL_CODEC, this.routineStowedItem);
        view.putBoolean(KeyStrings.ROUTINE_STOWED_MAIN_HAND, this.routineStowedMainHand);
    }

    @Override
    public void readCustomData(ReadView view) {
        super.readCustomData(view);
        LegacyNpcSave.Saved found = new LegacyNpcSave.Saved(
                view.read(KeyStrings.DATA, NpcData.CODEC).orElse(new NpcData()),
                view.read(KeyStrings.INITIALIZATION_DATA, NpcInitializationData.CODEC).orElse(new NpcInitializationData()),
                view.read(KeyStrings.TEXTURE_DATA, NpcTextureData.CODEC).orElse(new NpcTextureData()));
        household.read(view);
        party.read(view);
        prison.read(view);
        this.setJob(view.read(KeyStrings.JOB, Identifier.CODEC).orElse(null));
        this.routineStowedItem = view.read(KeyStrings.ROUTINE_STOWED_ITEM, ItemStack.OPTIONAL_CODEC).orElse(null);
        this.routineStowedMainHand = view.getBoolean(KeyStrings.ROUTINE_STOWED_MAIN_HAND, false);
        clearRoutineProp();

        LegacyNpcSave.Saved saved = LegacyNpcSave.migrate(this, view, found);

        this.dataTracker.set(NPC_DATA, saved.data());
        this.dataTracker.set(NPC_INITIALIZATION_DATA, saved.initialization());
        this.dataTracker.set(NPC_TEXTURE_DATA, saved.texture());

        saved.initialization().tryToInitialize(this);

        this.updateAttackType();
    }

    // [IDENTITY AND INITIALISATION]
    public void saveNpcTextureData(NpcTextureData npcTextureData) {
        this.dataTracker.set(NPC_TEXTURE_DATA, npcTextureData);
    }
    public NpcTextureData retrieveNpcTextureData() {
        return this.dataTracker.get(NPC_TEXTURE_DATA);
    }

    public void giveUpInitializing() {
        dataInitialized = true;
    }

    public boolean hasTextureData(){
        return retrieveNpcTextureData().get(NpcRenderedPart.BODY) != null;
    }

    public void saveNpcInitializationData(NpcInitializationData npcInitializationData) {
        this.dataInitialized = false;
        this.dataTracker.set(NPC_INITIALIZATION_DATA, npcInitializationData);
    }
    public NpcInitializationData retrieveNpcInitializationData() {
        return this.dataTracker.get(NPC_INITIALIZATION_DATA);
    }
    public void prepare() {
        NpcInitializationData npcInitializationData = retrieveNpcInitializationData();
        boolean result = npcInitializationData.tryToInitialize(this);
        if(result)
            resetInitializationData();
    }

    public void resetInitializationData() {
        this.dataTracker.set(NPC_INITIALIZATION_DATA, new NpcInitializationData());
    }

    public void prepareNpcIdentifier(Identifier npcClassIdentifier){
        NpcInitializationData newNpcInitializationData = this.retrieveNpcInitializationData().withNpcClass(npcClassIdentifier);
        this.saveNpcInitializationData(newNpcInitializationData);
    }

    public void saveNpcData(NpcData npcData) {
        this.dataTracker.set(NPC_DATA, npcData);
    }
    public NpcData retrieveNpcData() {
        return this.dataTracker.get(NPC_DATA);
    }
    public NpcClass getNpcClass(){
        return retrieveNpcData().getNpcClass();
    }
    public void saveNpcClass(RegistryEntry<NpcClass> npcClass){
        NpcData newNpcData = this.retrieveNpcData().withNpcClass(npcClass);
        this.saveNpcData(newNpcData);
    }

    public EntityCategories getNpcCategory() {
        return retrieveNpcData().getCategory();
    }
    public void saveCategory(EntityCategories category) {
        NpcData newNpcData = this.retrieveNpcData().withCategory(category);
        this.saveNpcData(newNpcData);
    }

    private void saveSpawnReason(SpawnReason spawnReason) {
        NpcData newNpcData = this.retrieveNpcData().withSpawnReason(spawnReason);
        this.saveNpcData(newNpcData);
    }

    public Identifier getNpcClassIdentifier(){
        return retrieveNpcData().getNpcClassId();
    }

    public @Nullable Faction getFaction(){
        NpcData data = retrieveNpcData();
        if(data == null)
            return null;
        Identifier factionId = data.getFaction();
        if(factionId == null)
            return null;
        // Hot path: runs on every enemy check, registry lookups are too slow
        if(factionId.equals(cachedFactionId))
            return cachedFaction;
        try {
            cachedFaction = FactionLookup.getFactionById(getEntityWorld(), factionId);
        } catch (FactionIdentifierException e) {
            cachedFaction = null;
        }
        cachedFactionId = factionId;
        return cachedFaction;
    }

    public Identifier getFactionIdentifier(){
        Faction faction = getFaction();
        if(faction == null) return null;
        return faction.getId();
    }

    public boolean isEvil() {
        Faction faction = getFaction();
        return faction != null && faction.getDisposition() == DispositionType.EVIL;
    }

    public boolean isHostileToFaction(Identifier otherFaction) {
        Faction faction = getFaction();
        return faction != null && faction.isHostileToward(otherFaction);
    }

    public boolean isElf() {
        NpcClass npcClass = getNpcClass();
        return npcClass != null && RaceRegistry.ELF.getValue().equals(npcClass.getRace());
    }

    public void tryToInitializeData(){
        if(dataInitialized)
            return;
        // Commands spawn at the origin first
        if(Objects.equals(getBlockPos(), BlockPos.ORIGIN))
            return;

        this.prepare();

        World world = getEntityWorld();
        if(world.isClient())
            return;

        if(world instanceof ServerWorld serverWorld){
            if(NpcEntityInitializer.shouldInitialize(serverWorld, this)){
                NpcEntityInitializer.initializeNpcEntity(serverWorld, this);
            } else {
                dataInitialized = true;
            }
        }
    }

    @Override
    protected Text getDefaultName() {
        if(this.getNpcClassIdentifier() == null) {
            return Text.translatable("npc_type."+ MiddleEarth.MOD_ID +".npc");
        }
        NpcJob job = getJob();
        Identifier faction = getFactionIdentifier();
        if(job != null && job.replacesName() && faction != null)
            return Text.translatable(getJobId().toTranslationKey("npc_role"), Text.translatable(faction.toTranslationKey("faction")));
        return Text.translatable(this.getNpcClassIdentifier().toTranslationKey("npc_type"));
    }

    @Override
    public @Nullable ItemStack getPickBlockStack() {
        if(getNpcClass() == null)
            return ItemStack.EMPTY;
        return NpcSpawnEggHelper.getSpawnEgg(getEntityWorld(), getNpcClass().getId());
    }

    // [JOB AND ROUTINE]
    public void setJob(@Nullable Identifier job) {
        this.dataTracker.set(JOB, job == null ? "" : job.toString());
    }

    // After class gear. Some jobs swap the hand item for a tool
    public void takeJob(Identifier job) {
        setJob(job);
        if(isPrisoner()) {
            prison.disarm();
            return;
        }
        NpcJob taken = getJob();
        if(taken == null || !taken.replacesName())
            return;
        Optional<Item> tool = taken.otherTool().isPresent() && getRandom().nextBoolean() ? taken.otherTool() : taken.tool();
        equipStack(EquipmentSlot.MAINHAND, tool.map(Item::getDefaultStack).orElse(ItemStack.EMPTY));
        taken.sidearm().ifPresent(weapon -> equipStack(EquipmentSlot.OFFHAND, weapon.getDefaultStack()));
        if(taken.unarmoured()) {
            for(EquipmentSlot slot : ARMOUR_SLOTS)
                equipStack(slot, ItemStack.EMPTY);
        }
        updateAttackType();
    }

    public @Nullable Identifier getJobId() {
        String job = this.dataTracker.get(JOB);
        // Hot path, so don't reparse the id each time
        if(!job.equals(cachedJobText)) {
            cachedJobText = job;
            cachedJobId = job.isEmpty() ? null : Identifier.tryParse(job);
            cachedJob = null;
            jobLookedUp = false;
        }
        return cachedJobId;
    }

    public @Nullable NpcJob getJob() {
        Identifier id = getJobId();
        if(id == null)
            return null;
        if(!jobLookedUp) {
            cachedJob = getEntityWorld().getRegistryManager().getOptional(DynamicRegistriesME.NPC_JOB).map(jobs -> jobs.get(id)).orElse(null);
            jobLookedUp = true;
        }
        return cachedJob;
    }

    public @Nullable RoutineData getRoutine() {
        NpcJob job = getJob();
        if(job != null && job.routine().isPresent())
            return job.routine().get();
        NpcClass npcClass = getNpcClass();
        return npcClass == null ? null : npcClass.getRoutine();
    }

    public NpcRoutine getDayRoutine() {
        if(dayRoutine == null)
            dayRoutine = new NpcRoutine(this, NpcBrain.WALK_SPEED);
        return dayRoutine;
    }

    public boolean isTimid() {
        RoutineData routine = getRoutine();
        return routine != null && routine.isTimid();
    }

    public boolean keepsBounds() {
        RoutineData routine = getRoutine();
        return routine != null && routine.keepsBounds();
    }

    public boolean isNightShift() {
        return (getUuid().hashCode() & 1) != 0;
    }

    public boolean isRestTime() {
        RoutineData routine = getRoutine();
        if(routine == null || !routine.getPeriod().isResting(getEntityWorld().getTimeOfDay(), isNightShift()))
            return false;
        // Elves lie down only every few days, each on its own night
        long day = getEntityWorld().getTimeOfDay() / DAY_TICKS;
        return !isElf() || Math.floorMod(getUuid().hashCode() + day, ELF_SLEEP_INTERVAL) == 0;
    }

    public void showRoutineProp(ItemStack prop) {
        if(routineStowedItem == null) {
            // An off-hand prop doesn't show beside a two-handed weapon
            Item mainHand = getMainHandStack().getItem();
            routineStowedMainHand = mainHand instanceof CustomLongbowWeaponItem || mainHand instanceof CustomSiegeShieldItem
                    || (mainHand instanceof ReachWeaponItem reachWeapon && reachWeapon.type.twoHanded);
            routineStowedItem = getEquippedStack(routinePropSlot()).copy();
        }
        equipStack(routinePropSlot(), prop);
    }

    public void clearRoutineProp() {
        if(routineStowedItem == null)
            return;
        equipStack(routinePropSlot(), routineStowedItem);
        routineStowedItem = null;
    }

    public boolean hasRoutineProp() {
        return routineStowedItem != null;
    }

    private EquipmentSlot routinePropSlot() {
        return routineStowedMainHand ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
    }

    public boolean isOnOuting() {
        return onOuting;
    }

    public void setOnOuting(boolean onOuting) {
        this.onOuting = onOuting;
        if(!onOuting)
            party.clearEscort();
    }

    public boolean isFarming() {
        return farming;
    }

    public void setFarming(boolean farming) {
        this.farming = farming;
    }

    public void invite(BlockPos seat, long until) {
        this.invitedTo = seat;
        this.invitedUntil = until;
    }

    public boolean hasInvitation(long time) {
        return invitedTo != null && time < invitedUntil;
    }

    public @Nullable BlockPos takeInvitation(long time) {
        BlockPos seat = hasInvitation(time) ? invitedTo : null;
        invitedTo = null;
        return seat;
    }

    // [HOUSEHOLD AND MANAGER]
    public void assignHost(BlockPos host){
        NpcData newNpcData = this.retrieveNpcData().withStructureManagerPos(host);
        this.saveNpcData(newNpcData);
    }

    public BlockPos getStructureManagerHostPos() {
        return this.retrieveNpcData().getStructureManagerPos();
    }

    public void setHousehold(@Nullable BlockPos bed, @Nullable BlockPos bedStand, @Nullable BlockPos store, List<BlockPos> links) {
        household.set(bed, bedStand, store, links);
    }

    public List<BlockPos> getLinks() {
        return household.getLinks();
    }

    public @Nullable BlockPos getAssignedBed() {
        return household.getAssignedBed();
    }

    public @Nullable BlockPos getBedStand() {
        return household.getBedStand();
    }

    public @Nullable BlockPos getStore() {
        return household.getStore();
    }

    public @Nullable BlockPos getRoutineHome() {
        return household.getRoutineHome();
    }

    public void setRoutineHome(@Nullable BlockPos routineHome) {
        household.setRoutineHome(routineHome);
    }

    public NpcHousehold getHousehold() {
        return household;
    }

    @Override
    public boolean isPersistent() {
        BlockPos structureManagerPos = getStructureManagerHostPos();
        if(structureManagerPos == null)
            return super.isPersistent();
        // Skip unloaded chunks, reading the block entity would load them
        if(age >= managerCheckAge) {
            managerCheckAge = age + MANAGER_CHECK_INTERVAL;
            if(getEntityWorld().isChunkLoaded(structureManagerPos.getX() >> 4, structureManagerPos.getZ() >> 4))
                managerPresent = NestHost.isHost(getEntityWorld(), structureManagerPos);
        }
        if(managerPresent)
            return true;
        return super.isPersistent();
    }

    // [PARTY, TRAVEL AND ESCORT]
    public @Nullable UUID getPartyLeader() {
        return party.getLeader();
    }

    public void setPartyLeader(@Nullable UUID partyLeader) {
        party.setLeader(partyLeader);
    }

    // In degrees
    public @Nullable Float getTravelHeading() {
        return party.getTravelHeading();
    }

    public void setTravelHeading(@Nullable Float travelHeading) {
        party.setTravelHeading(travelHeading);
    }

    public @Nullable BlockPos getTravelDestination() {
        return party.getTravelDestination();
    }

    public void setTravelDestination(@Nullable BlockPos travelDestination) {
        party.setTravelDestination(travelDestination);
    }

    public @Nullable UUID getEscort() {
        return party.getEscort();
    }

    public void escort(NpcEntity ward) {
        party.escort(ward);
    }

    public boolean isEscorting() {
        return party.isEscorting();
    }

    public void endEscort() {
        party.endEscort();
    }

    public NpcParty getParty() {
        return party;
    }

    // [PRISON]
    public boolean isPrisoner() {
        return NpcJobs.PRISONER.getValue().equals(getJobId());
    }

    public @Nullable PrisonCell getPrisonCell() {
        return prison.getCell();
    }

    public void setPrisonCell(@Nullable PrisonCell prisonCell) {
        prison.setCell(prisonCell);
    }

    public boolean isConfined() {
        return prison.isConfined();
    }

    public boolean tryEscape() {
        return prison.tryEscape();
    }

    public boolean isEscaping() {
        return prison.isEscaping();
    }

    public NpcPrison getPrison() {
        return prison;
    }

    // [RELATIONS AND ALARM]
    @Override
    public boolean canTarget(LivingEntity target) {
        return NpcRelations.isEnemy(this, target) && super.canTarget(target);
    }

    @Override
    protected boolean isInSameTeam(Entity other) {
        return NpcRelations.isFriendly(this, other);
    }

    public void bearGrudge(LivingEntity offender) {
        alarm.bearGrudge(offender);
    }

    public boolean hasGrudgeAgainst(Entity other) {
        return alarm.hasGrudgeAgainst(other);
    }

    @Override
    public void setAttacker(@Nullable LivingEntity attacker) {
        super.setAttacker(attacker);
        if(attacker != null)
            bearGrudge(attacker);
    }

    public boolean fleesFrom(LivingEntity threat) {
        return alarm.fleesFrom(threat);
    }

    // Nowhere left to run near home
    public boolean isAtHome() {
        BlockPos routineHome = getRoutineHome();
        return routineHome != null && getStructureManagerHostPos() != null && routineHome.isWithinDistance(getEntityPos(), HOME_STAND_RANGE);
    }

    public void callGuardsOnce(LivingEntity threat) {
        alarm.callGuardsOnce(threat);
    }

    public void callGuards(@Nullable LivingEntity threat) {
        alarm.callGuards(threat);
    }

    public NpcAlarm getAlarm() {
        return alarm;
    }

    @Override
    public boolean isInvulnerableTo(ServerWorld world, DamageSource source) {
        if (source.isOf(DamageTypes.IN_WALL) && this.hasVehicle()) {
            return true;
        }
        // Stray arrows from hunters don't hurt non-enemies
        if(NpcRelations.isStrayHunterShot(this, source.getAttacker()))
            return true;
        return super.isInvulnerableTo(world, source);
    }

    // [HUNT]
    public @Nullable LivingEntity getHuntTarget() {
        return huntTarget;
    }

    public void startHunt(LivingEntity prey, int duration) {
        this.huntTarget = prey;
        this.huntKill = null;
        this.huntEndAge = age + duration;
        setTarget(prey);
    }

    public @Nullable EntityType<?> takeHuntKill() {
        EntityType<?> kill = huntKill;
        huntKill = null;
        return kill;
    }

    private void tickHunt() {
        if(huntTarget.isAlive() && age < huntEndAge)
            return;
        if(huntTarget.isDead())
            huntKill = huntTarget.getType();
        if(getTarget() == huntTarget)
            setTarget(null);
        huntTarget = null;
    }

    // [COMBAT]
    @Override
    public boolean isInAttackRange(LivingEntity entity) {
        float reach = (float) this.getAttributeValue(EntityAttributes.ENTITY_INTERACTION_RANGE);
        if(this.getVehicle() != null || entity.getVehicle() != null)
            reach += MOUNTED_REACH_BONUS;
        reach = Math.max(reach, (this.getWidth() + entity.getWidth()) / 2 + CONTACT_REACH);
        return this.distanceTo(entity) <= reach;
    }

    @Override
    public boolean tryAttack(ServerWorld world, Entity target) {
        this.getEntityWorld().sendEntityStatus(this, EntityStatuses.PLAY_ATTACK_SOUND);
        float damage = (float) this.getAttributeValue(EntityAttributes.ATTACK_DAMAGE);
        ItemStack itemStack = this.getWeaponStack();
        DamageSource damageSource = Optional.ofNullable(itemStack.getItem().getDamageSource(this)).orElse(this.getDamageSources().mobAttack(this));

        float enchantmentDamage = EnchantmentHelper.getDamage(world, itemStack, target, damageSource, damage);
        float finalDamage = damage + itemStack.getItem().getBonusAttackDamage(target, enchantmentDamage, damageSource);

        if(hasVehicle() && getVehicle() instanceof AbstractBeastEntity mountEntity){
            mountEntity.tryAttack((ServerWorld) target.getEntityWorld(), target);
        }

        boolean targetDamaged = target.damage(world, damageSource, finalDamage);
        if (targetDamaged) {
            if (target instanceof LivingEntity livingTarget) {
                float knockback = this.getAttackKnockbackAgainst(target, damageSource);
                if (knockback > 0.0f) {
                    float yaw = this.getYaw() * MathHelper.RADIANS_PER_DEGREE;
                    livingTarget.takeKnockback(knockback * KNOCKBACK_SCALE, MathHelper.sin(yaw), -MathHelper.cos(yaw));
                    this.setVelocity(this.getVelocity().multiply(KNOCKBACK_SELF_SLOWDOWN, 1.0, KNOCKBACK_SELF_SLOWDOWN));
                }
                itemStack.postHit(livingTarget, this);
            }
            EnchantmentHelper.onTargetDamaged(world, target, damageSource);
            this.onAttacking(target);
            this.playAttackSound();
        }
        return targetDamaged;
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

    // Spread 0 never misses; skeletons use 2 on hard to 10 on easy
    public void shootAt(LivingEntity target, float pullProgress, float powerModifier, float inaccuracy) {
        ItemStack weapon = this.getMainHandStack();
        ItemStack projectileStack = this.getProjectileType(weapon);
        if(projectileStack.isEmpty()) projectileStack = new ItemStack(Items.ARROW, 1);

        PersistentProjectileEntity projectile = this.createArrowProjectile(projectileStack, pullProgress, weapon);
        float speed = BASE_ARROW_SPEED * powerModifier;
        Vec3d aim = ArrowAim.solve(projectile.getEntityPos(), target, speed, projectile.getFinalGravity());

        if (this.getEntityWorld() instanceof ServerWorld serverWorld) {
            ProjectileEntity.spawnWithVelocity(
                    projectile,
                    serverWorld,
                    projectileStack,
                    aim.x,
                    aim.y,
                    aim.z,
                    speed,
                    Math.max(0f, inaccuracy)
            );
        }

        this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));

        clearActiveItem();
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        this.shootAt(target, 1, pullProgress, NO_INACCURACY);
    }

    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier, @Nullable ItemStack shotFrom) {
        return ProjectileUtil.createArrowProjectile(this, arrow, damageModifier, shotFrom);
    }

    public boolean isAiming() {
        return this.getItemUseTime() > 0;
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

    @Override
    public boolean canUseRangedWeapon(ItemStack stack) {
        return true;
    }

    public void shootCrossbowAt(LivingEntity target) {
        this.shootAt(target, 1, CROSSBOW_ARROW_POWER, NO_INACCURACY);
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

    // [TICK AND MOVEMENT]
    @Override
    protected void mobTick(ServerWorld world) {
        tryToInitializeData();
        NpcBrain.updateActivities(this);
        getBrain().tick(world, this);
        if(getTalk() != Talk.NONE && (age >= talkUntil || getTarget() != null || !getDayRoutine().isInCompany()))
            talk(Talk.NONE, 0);
        if(huntTarget != null)
            tickHunt();
        hatches.tick(world);
        climbOutOfWater();
        prison.tick(world);
        // Never drops a target set from outside: a dead or no-longer-enemy one blocks the rest
        LivingEntity target = getTarget();
        if(target != null && (!target.isAlive() || age % TARGET_CHECK_INTERVAL == 0 && (!canTarget(target) || isOutOfReach(target))))
            setTarget(null);
        super.mobTick(world);
        if(age % WEAPON_CHECK_INTERVAL == 0)
            WeaponSwitch.tick(this);
        if(NpcDebug.ENABLED)
            NpcDebug.label(this);

        if(hasVehicle()){
            rotate(getVehicle().getYaw(), false, getVehicle().getPitch(), false);
        }
    }

    // A sleeping target is a raid's, which reaches it through walls
    private boolean isOutOfReach(LivingEntity target) {
        return !target.isSleeping() && squaredDistanceTo(target) > getAttributeValue(EntityAttributes.FOLLOW_RANGE) * getAttributeValue(EntityAttributes.FOLLOW_RANGE);
    }

    @Override
    public void remove(RemovalReason reason) {
        if(getEntityWorld() instanceof ServerWorld world)
            hatches.closeAll(world);
        super.remove(reason);
    }

    private void climbOutOfWater() {
        if(!isTouchingWater() || !horizontalCollision || getVelocity().y > CLIMBING_OUT)
            return;
        Path path = getNavigation().getCurrentPath();
        if(path == null || path.isFinished() || path.getCurrentNode().y <= getBlockY())
            return;
        Vec3d velocity = getVelocity();
        setVelocity(velocity.x, getJumpVelocity(), velocity.z);
    }

    public void lieDown(BlockPos bed) {
        this.sleptFrom = getEntityPos();
        sleep(bed);
    }

    // Vanilla drops a waking sleeper on the bench or table beside the bed, with no way down
    @Override
    public void wakeUp() {
        super.wakeUp();
        BlockPos bedStand = getBedStand();
        Vec3d back = sleptFrom != null ? sleptFrom : bedStand != null ? Vec3d.ofBottomCenter(bedStand) : null;
        sleptFrom = null;
        if(back != null && !getEntityWorld().isClient() && back.squaredDistanceTo(getEntityPos()) < WAKE_RANGE * WAKE_RANGE)
            refreshPositionAndAngles(back.x, back.y, back.z, getYaw(), getPitch());
    }

    public void setPassingDoor(boolean passingDoor) {
        this.passingDoor = passingDoor;
    }

    // Nobody shoves a prisoner out of its cell
    @Override
    public boolean isPushable() {
        return !isConfined() && super.isPushable();
    }

    // Two npcs side by side in a doorway shove each other and neither gets through
    @Override
    public void pushAwayFrom(Entity entity) {
        if(entity instanceof NpcEntity other && (passingDoor || other.passingDoor))
            return;
        super.pushAwayFrom(entity);
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
    protected Vec3d getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        Vec3d pos = super.getPassengerAttachmentPos(passenger, dimensions, scaleFactor);

        Vec3d offset = new Vec3d(0.0, PASSENGER_OFFSET_Y * scaleFactor, PASSENGER_OFFSET_Z * scaleFactor);
        return pos.add(offset.rotateY(-this.getYaw() * MathHelper.RADIANS_PER_DEGREE));
    }

    @Override
    public boolean shouldControlVehicles() {
        return true;
    }

    @Override
    protected boolean couldAcceptPassenger() {
        return !this.hasVehicle();
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    public float getWidthScale() {
        EntityAttributeInstance scale = getAttributeInstance(EntityAttributesME.WIDTH_SCALE);
        return scale == null ? 1.0f : (float) scale.getValue();
    }

    // [DEATH AND LOOT]
    @Override
    public void onDeath(DamageSource source) {
        if(getEntityWorld() instanceof ServerWorld world)
            party.onLeaderDeath(world);
        if(getVehicle() != null && getVehicle() instanceof LivingEntity vehicleEntity && vehicleEntity.getControllingPassenger() == this){
            vehicleEntity.equipStack(EquipmentSlot.SADDLE, Items.AIR.getDefaultStack());
            vehicleEntity.equipStack(EquipmentSlot.BODY, Items.AIR.getDefaultStack());
            vehicleEntity.removeAllPassengers();
            if(vehicleEntity instanceof AbstractHorseEntity abstractHorseEntity){
                abstractHorseEntity.setTame(false);
                abstractHorseEntity.resetLoveTicks();
                abstractHorseEntity.setSprinting(false);
                abstractHorseEntity.setOwner(null);
            }
            if(vehicleEntity instanceof AbstractBeastEntity abstractBeastEntity){
                abstractBeastEntity.resetTameness();
            }
        }
        BlockPos structureManagerPos = getStructureManagerHostPos();
        NestHost.signalGone(getEntityWorld(), structureManagerPos, this);
        super.onDeath(source);
    }

    private boolean canDropLoot(DamageSource damageSource){
        if(damageSource == null || !(damageSource.getAttacker() instanceof PlayerEntity player))
            return false;
        PlayerData data = StateSaverAndLoader.getPlayerState(player);
        if(data == null || data.getFaction() == null)
            return true;
        try{
            return FactionLookup.getFactionById(getEntityWorld(), data.getFaction()).isHostileToward(this.getFactionIdentifier());
        } catch (FactionIdentifierException e){
            return true;
        }
    }

    @Override
    protected void drop(ServerWorld world, DamageSource damageSource) {
        if(!canDropLoot(damageSource))
            return;
        super.drop(world, damageSource);
    }

    @Override
    protected int getExperienceToDrop(ServerWorld world) {
        LootData lootData = retrieveLootData();
        if(lootData == null)
            return FALLBACK_EXPERIENCE;
        return lootData.getExperience(world);
    }

    // [Loot Data]
    public LootData retrieveLootData(){
        NpcClass npcClass = retrieveNpcData().getNpcClass();
        if(npcClass == null)
            return null;
        return npcClass.getLootData();
    }

    @Override
    protected void dropLoot(ServerWorld world, DamageSource damageSource, boolean causedByPlayer) {
        if(getNpcClass() == null)
            return;
        RegistryKey<LootTable> lootTableRegistryKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, getNpcClass().getId().withPrefixedPath(LOOT_TABLE_FOLDER));
        LootTable lootTable = world.getServer().getReloadableRegistries().getLootTable(lootTableRegistryKey);

        if (lootTable != null) {
            LootWorldContext.Builder builder = (new LootWorldContext.Builder(world)).add(LootContextParameters.THIS_ENTITY, this)
                    .add(LootContextParameters.ORIGIN, this.getEntityPos())
                    .add(LootContextParameters.DAMAGE_SOURCE, damageSource)
                    .addOptional(LootContextParameters.ATTACKING_ENTITY, damageSource.getAttacker())
                    .addOptional(LootContextParameters.DIRECT_ATTACKING_ENTITY, damageSource.getSource());
            PlayerEntity playerEntity = this.getAttackingPlayer();
            if (playerEntity != null) {
                builder = builder.add(LootContextParameters.LAST_DAMAGE_PLAYER, playerEntity).luck(playerEntity.getLuck());
            }

            LootWorldContext lootWorldContext = builder.build(LootContextTypes.ENTITY);
            lootTable.generateLoot(lootWorldContext, this.getLootTableSeed(), (stack) -> this.dropStack(world, stack));
        }
    }

    @Override
    protected void dropEquipment(ServerWorld world, DamageSource source, boolean causedByPlayer) {
    }

}
