package net.sevenstars.middleearth.entity.npcs;

import net.sevenstars.middleearth.dev.NpcDebug;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.state.property.Properties;
import net.sevenstars.middleearth.entity.npcs.pathing.NpcMoveControl;
import net.sevenstars.middleearth.entity.npcs.pathing.NpcNavigation;
import net.sevenstars.middleearth.entity.npcs.pathing.NpcPathNodeMaker;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.Uuids;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.entity.EntityAttributesME;
import net.sevenstars.middleearth.entity.TrackedDataHandlerRegistryME;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.goals.*;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.npc_jobs.NpcJob;
import net.sevenstars.middleearth.entity.npcs.data.NpcData;
import net.sevenstars.middleearth.entity.npcs.data.NpcInitializationData;
import net.sevenstars.middleearth.entity.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.entity.npcs.initializer.NpcEntityInitializer;
import net.sevenstars.middleearth.entity.npcs.initializer.NpcSpawnEggHelper;
import net.sevenstars.middleearth.entity.npcs.relations.NpcRelations;
import net.sevenstars.middleearth.entity.npcs.routine.PrisonCell;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSite;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSites;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import net.sevenstars.middleearth.entity.npcs.relations.Relation;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcRenderedPart;
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
import net.sevenstars.middleearth.registries.content.npcclasses.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.npc_classes.NpcClass;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.LootData;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.utils.ItemTagsME;
import net.sevenstars.middleearth.utils.SpawnUtil;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class NpcEntity extends PathAwareEntity implements CrossbowUser {
    public static class KeyStrings {
        public static final String DATA = "NpcData";
        public static final String INITIALIZATION_DATA = "InitializationData";
        public static final String TEXTURE_DATA = "TextureData";
        public static final String IS_FIGHTING = "IsFighting";
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
    private static final TrackedData<NpcData> NPC_DATA;
    private static final TrackedData<NpcInitializationData> NPC_INITIALIZATION_DATA;
    private static final TrackedData<NpcTextureData> NPC_TEXTURE_DATA;
    private static final TrackedData<Boolean> IS_FIGHTING;

    // Of a body whatever its scale, a little less than the room a board leaves in its block
    private static final float MAXIMUM_WIDTH = 0.8f;
    // In ticks
    private static final int MANAGER_CHECK_INTERVAL = 100;
    private static final int HATCH_CHECK_INTERVAL = 4;
    private static final int BOW_ATTACK_INTERVAL = 20;
    private static final int BOW_ATTACK_INTERVAL_HARD = 30;
    private static final int DAY_TICKS = 24000;
    // In days
    private static final int ELF_SLEEP_INTERVAL = 4;
    // In blocks
    private static final int WAKE_RANGE = 16;
    private static final int PARTY_RANGE = 64;
    // In ticks, a second
    private static final int PRISON_CHECK_INTERVAL = 20;
    private static final double HATCH_OPEN_DISTANCE = 3;
    private static final double HATCH_CLOSE_DISTANCE = 3.5;
    private static final int ALARM_RANGE = 24;
    private static final int ALARM_HEIGHT_RANGE = 8;
    private static final int GUARDS_TO_FLEE = 3;
    private static final EquipmentSlot[] ARMOUR_SLOTS = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    private static final int WEAPON_CHECK_INTERVAL = 10;
    private static final int TARGET_CHECK_INTERVAL = 20;
    // Common folk come to help a neighbour close by, not across the village
    private static final int HELP_RANGE = 16;
    private static final float BOW_ATTACK_RANGE = 16.0F;
    private static final float CROSSBOW_ATTACK_RANGE = 11.0F;
    // In blocks, from the npc's feet to its target's. A race sets its own, and the weapon in hand adds to it
    private static final double DEFAULT_REACH = 1.75;
    private static final float MOUNTED_REACH_BONUS = 0.5f;
    // Past touching, which is as close as two can stand
    private static final float CONTACT_REACH = 0.25f;
    // In ticks, from the last blow. Vanilla forgets an attacker after 5 seconds, which ends a fight with someone neutral halfway
    private static final int GRUDGE_TICKS = 20 * 60;
    // Upward speed of an npc already on its way out of the water, which is not lifted again
    private static final double CLIMBING_OUT = 0.1;
    // In blocks
    private static final double FOLLOW_RANGE = 32.0;
    private static final float LOOK_RANGE = 8.0F;
    private static final double BASE_ATTACK_DAMAGE = 1.0;
    // Of its walking speed
    private static final double WALK_SPEED = 0.8;
    private static final double FLEE_SPEED = 1.15;
    private static final double PRISONER_SPEED = 0.6;
    private static final int ATTACK_PRIORITY = 4;
    private static final double BOW_ATTACK_SPEED = 1.0;
    private static final double MELEE_ATTACK_SPEED = 1.2;
    // Where somebody carried sits, from the middle of the npc's shoulders: a little lower and behind
    private static final double PASSENGER_OFFSET_Y = -0.3;
    private static final double PASSENGER_OFFSET_Z = -0.25;
    private static final float KNOCKBACK_SCALE = 0.5f;
    private static final double KNOCKBACK_SELF_SLOWDOWN = 0.6;
    private static final int FALLBACK_EXPERIENCE = 1;
    private static final String LOOT_TABLE_FOLDER = "entities/";
    // Vanilla arrows leave a bow at 3 blocks a tick at full draw, which is BASE_ARROW_SPEED times the power
    private static final float BASE_ARROW_SPEED = 1.6F;
    private static final float CROSSBOW_ARROW_POWER = 1.25f;
    private static final float NO_INACCURACY = 0f;
    private static final double ARROW_AIR_DRAG = 0.99;
    private static final double MAX_ARROW_FLIGHT_TICKS = 200;
    // In ticks of flight, how finely the arcs are tried, and how often the one found is halved to close in on it
    private static final double AIM_STEP = 0.5;
    private static final int AIM_BISECTIONS = 30;

    // The id of the npc's job, empty for none. Tracked so that the client can name the npc for it
    private static final TrackedData<String> JOB = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> USING_ITEM = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> CROSSBOW_CHARGING = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private @Nullable UUID partyLeader = null;
    private @Nullable Float travelHeading = null;
    private @Nullable BlockPos travelDestination = null;
    private @Nullable BlockPos routineHome = null;
    // Where a prisoner is held. Out of it, the prisoner is free
    private @Nullable PrisonCell prisonCell = null;
    // Given out by the structure manager of its nest
    private boolean onOuting = false;
    private @Nullable UUID threatFaced = null;
    private ItemStack confiscatedMain = ItemStack.EMPTY;
    private ItemStack confiscatedOff = ItemStack.EMPTY;
    private boolean fleesThreat = false;
    // Out tending the fields, the one errand that crosses them
    private boolean farming = false;
    // Where a visitor to its household asked it to sit, and until when the visitor is there
    private @Nullable BlockPos invitedTo = null;
    private long invitedUntil = 0;
    private @Nullable UUID escort = null;
    // Saved, or an escort loaded in the middle of an outing would follow its ward about for good
    private boolean escorting = false;
    private @Nullable BlockPos assignedBed = null;
    private @Nullable Vec3d sleptFrom = null;
    private @Nullable BlockPos openedHatch = null;
    private @Nullable BlockPos bedStand = null;
    private @Nullable BlockPos store = null;
    // The places its bed was tied to by the builder, for the work to go to first
    private List<BlockPos> links = List.of();
    // What the hand held before a routine put something else in it
    private @Nullable ItemStack routineStowedItem = null;
    private boolean routineStowedMainHand = false;
    private @Nullable LivingEntity huntTarget = null;
    private @Nullable EntityType<?> huntKill = null;
    private int huntEndAge = 0;
    private String cachedJobText = "";
    private @Nullable Identifier cachedJobId = null;
    private @Nullable NpcJob cachedJob = null;
    private @Nullable Identifier cachedFactionId = null;
    private @Nullable Faction cachedFaction = null;
    // Who wronged this npc, until when in world ticks. Kept by the npc alone, and not saved
    private final Map<UUID, Long> grudges = new HashMap<>();
    private boolean passingDoor = false;
    private boolean dataInitialized = false;
    private boolean managerPresent = true;
    private int managerCheckAge = 0;

    // The one of the three attack goals below that fits the weapon in hand
    private @Nullable Goal attackGoal = null;
    // Set by initGoals, which the game calls before the fields of this class are given their values: with a value of its own
    // here, the goal would be lost again
    private @Nullable NpcRoutineGoal routineGoal;
    private final CustomBowAttackGoal<NpcEntity> bowAttackGoal = new CustomBowAttackGoal<>(this, BOW_ATTACK_SPEED, BOW_ATTACK_INTERVAL, BOW_ATTACK_RANGE);
    private final NpcCrossBowAttackGoal<NpcEntity> crossBowAttackGoal = new NpcCrossBowAttackGoal<>(this, BOW_ATTACK_SPEED, CROSSBOW_ATTACK_RANGE);
    private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, MELEE_ATTACK_SPEED, false) {
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

    public NpcEntity(EntityType<NpcEntity> entityType, World world) {
        super(entityType, world);
        this.updateAttackType();
        this.navigation.setCanOpenDoors(true);
        this.moveControl = new NpcMoveControl(this);
        // The game only makes a walk through fire costly. Nobody walks into a fire, a lit campfire or a brazier
        setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1);
        setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, -1);
    }

    // A tall elf grew wider than the room beside a ladder or an open shutter, and caught on every board in its hall
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

    // [IsFighting]
    public void setFighting(boolean state){
        this.dataTracker.set(IS_FIGHTING, state);
    }
    public boolean getFighting(){
        return this.dataTracker.get(IS_FIGHTING);
    }
    // [NpcTextureData]
    public void saveNpcTextureData(NpcTextureData npcTextureData) {
        this.dataTracker.set(NPC_TEXTURE_DATA, npcTextureData);
    }
    public NpcTextureData retrieveNpcTextureData() {
        return this.dataTracker.get(NPC_TEXTURE_DATA);
    }
    public boolean hasTextureData(){
        return retrieveNpcTextureData().get(NpcRenderedPart.BODY) != null;
    }
    // [NpcInitializationData]
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
    // [NpcClassIdentifier]
    public void prepareNpcIdentifier(Identifier npcClassIdentifier){
        NpcInitializationData newNpcInitializationData = this.retrieveNpcInitializationData().withNpcClass(npcClassIdentifier);
        this.saveNpcInitializationData(newNpcInitializationData);
    }
    // [NpcData]
    public void saveNpcData(NpcData npcData) {
        this.dataTracker.set(NPC_DATA, npcData);
    }
    public NpcData retrieveNpcData() {
        return this.dataTracker.get(NPC_DATA);
    }
    // [NpcClass]
    public NpcClass getNpcClass(){
        return retrieveNpcData().getNpcClass();
    }
    public void saveNpcClass(RegistryEntry<NpcClass> npcClass){
        NpcData newNpcData = this.retrieveNpcData().withNpcClass(npcClass);
        this.saveNpcData(newNpcData);
    }
    // [Loot Data]
    public LootData retrieveLootData(){
        NpcClass npcClass = retrieveNpcData().getNpcClass();
        if(npcClass == null)
            return null;
        return npcClass.getLootData();
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
    private void saveSpawnReason(SpawnReason spawnReason) {
        NpcData newNpcData = this.retrieveNpcData().withSpawnReason(spawnReason);
        this.saveNpcData(newNpcData);
    }
    // [StructureManager]
    public void assignStructureManager(StructureManagerBlockEntity blockEntity){
        NpcData newNpcData = this.retrieveNpcData().withStructureManagerPos(blockEntity.getPos());
        this.saveNpcData(newNpcData);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.FOLLOW_RANGE, FOLLOW_RANGE)
                .add(EntityAttributes.ATTACK_DAMAGE, BASE_ATTACK_DAMAGE)
                // Held weapons only add their reach to an attribute the npc has
                .add(EntityAttributes.ENTITY_INTERACTION_RANGE, DEFAULT_REACH);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(3, new NpcFleeHomeGoal(this, FLEE_SPEED));
        this.goalSelector.add(4, new NpcPrisonerGoal(this, PRISONER_SPEED));
        this.routineGoal = new NpcRoutineGoal(this, WALK_SPEED);
        this.goalSelector.add(5, this.routineGoal);
        this.goalSelector.add(5, new NpcWandererBedGoal(this, WALK_SPEED));
        this.goalSelector.add(6, new NpcTravelGoal(this, WALK_SPEED));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, LOOK_RANGE));
        this.goalSelector.add(7, new LookAroundGoal(this));
        // Common folk strike back unless they have run for the guards
        this.targetSelector.add(1, new RevengeGoal(this) {
            @Override
            public boolean canStart() {
                return (!isTimid() || getAttacker() == null || !fleesFrom(getAttacker())) && super.canStart();
            }
        });
        this.targetSelector.add(3, new NpcTargetEnemyGoal(this));
        this.targetSelector.add(4, new NpcDoorInteractGoal(this, true));
        this.targetSelector.add(4, new NpcModDoorGoal(this));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(NPC_INITIALIZATION_DATA, new NpcInitializationData());
        builder.add(NPC_DATA, new NpcData());
        builder.add(NPC_TEXTURE_DATA, new NpcTextureData());
        builder.add(IS_FIGHTING, false);
        builder.add(JOB, "");
        builder.add(CROSSBOW_CHARGING, false);
        builder.add(USING_ITEM, 0);
    }

    @Override
    public void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.put(KeyStrings.DATA, NpcData.CODEC, this.retrieveNpcData());
        view.put(KeyStrings.INITIALIZATION_DATA, NpcInitializationData.CODEC, this.retrieveNpcInitializationData());
        view.put(KeyStrings.TEXTURE_DATA, NpcTextureData.CODEC, this.retrieveNpcTextureData());
        view.put(KeyStrings.IS_FIGHTING, Codec.BOOL, this.getFighting());
        view.putNullable(KeyStrings.PARTY_LEADER, Uuids.INT_STREAM_CODEC, this.partyLeader);
        view.putNullable(KeyStrings.TRAVEL_HEADING, Codec.FLOAT, this.travelHeading);
        view.putNullable(KeyStrings.TRAVEL_DESTINATION, BlockPos.CODEC, this.travelDestination);
        view.putNullable(KeyStrings.ROUTINE_HOME, BlockPos.CODEC, this.routineHome);
        view.putNullable(KeyStrings.JOB, Identifier.CODEC, this.getJobId());
        view.putNullable(KeyStrings.PRISON_CELL, PrisonCell.CODEC, this.prisonCell);
        view.putBoolean(KeyStrings.ESCORTING, this.escorting);
        view.putNullable(KeyStrings.ASSIGNED_BED, BlockPos.CODEC, this.assignedBed);
        view.putNullable(KeyStrings.BED_STAND, BlockPos.CODEC, this.bedStand);
        view.putNullable(KeyStrings.STORE, BlockPos.CODEC, this.store);
        view.put(KeyStrings.LINKS, BlockPos.CODEC.listOf(), this.links);
        view.putNullable(KeyStrings.ROUTINE_STOWED_ITEM, ItemStack.OPTIONAL_CODEC, this.routineStowedItem);
        view.put(KeyStrings.CONFISCATED_MAIN, ItemStack.OPTIONAL_CODEC, this.confiscatedMain);
        view.put(KeyStrings.CONFISCATED_OFF, ItemStack.OPTIONAL_CODEC, this.confiscatedOff);
        view.putBoolean(KeyStrings.ROUTINE_STOWED_MAIN_HAND, this.routineStowedMainHand);
    }

    @Override
    public void readCustomData(ReadView view) {
        super.readCustomData(view);
        NpcData foundNpcData = view.read(KeyStrings.DATA, NpcData.CODEC).orElse(new NpcData());
        NpcInitializationData foundNpcInitializationData = view.read(KeyStrings.INITIALIZATION_DATA, NpcInitializationData.CODEC).orElse(new NpcInitializationData());
        NpcTextureData foundNpcTextureData = view.read(KeyStrings.TEXTURE_DATA, NpcTextureData.CODEC).orElse(new NpcTextureData());
        boolean isFighting = view.read(KeyStrings.IS_FIGHTING, Codec.BOOL).orElse(false);
        this.partyLeader = view.read(KeyStrings.PARTY_LEADER, Uuids.INT_STREAM_CODEC).orElse(null);
        this.travelHeading = view.read(KeyStrings.TRAVEL_HEADING, Codec.FLOAT).orElse(null);
        this.travelDestination = view.read(KeyStrings.TRAVEL_DESTINATION, BlockPos.CODEC).orElse(null);
        this.routineHome = view.read(KeyStrings.ROUTINE_HOME, BlockPos.CODEC).orElse(null);
        this.setJob(view.read(KeyStrings.JOB, Identifier.CODEC).orElse(null));
        this.prisonCell = view.read(KeyStrings.PRISON_CELL, PrisonCell.CODEC).orElse(null);
        this.escorting = view.getBoolean(KeyStrings.ESCORTING, false);
        this.assignedBed = view.read(KeyStrings.ASSIGNED_BED, BlockPos.CODEC).orElse(null);
        this.bedStand = view.read(KeyStrings.BED_STAND, BlockPos.CODEC).orElse(null);
        this.store = view.read(KeyStrings.STORE, BlockPos.CODEC).orElse(null);
        this.links = view.read(KeyStrings.LINKS, BlockPos.CODEC.listOf()).orElse(List.of());
        // The routine itself is not saved, so its prop is put away again
        this.routineStowedItem = view.read(KeyStrings.ROUTINE_STOWED_ITEM, ItemStack.OPTIONAL_CODEC).orElse(null);
        this.confiscatedMain = view.read(KeyStrings.CONFISCATED_MAIN, ItemStack.OPTIONAL_CODEC).orElse(ItemStack.EMPTY);
        this.confiscatedOff = view.read(KeyStrings.CONFISCATED_OFF, ItemStack.OPTIONAL_CODEC).orElse(ItemStack.EMPTY);
        this.routineStowedMainHand = view.getBoolean(KeyStrings.ROUTINE_STOWED_MAIN_HAND, false);
        clearRoutineProp();

        Identifier savedClass = view.getOptionalReadView(KeyStrings.DATA).flatMap(data -> data.read("Type", Identifier.CODEC)).orElse(null);
        NpcRegistry.Folded folded = savedClass == null ? null : NpcRegistry.FOLDED.get(savedClass);
        if(folded != null) {
            // Its gear stays as it was saved, only the class it is and the job it has change
            ReadView data = view.getOptionalReadView(KeyStrings.DATA).orElseThrow();
            Registry<NpcClass> classes = getWorld().getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_CLASS);
            NpcClass base = classes.get(folded.npcClass().getValue());
            foundNpcData = new NpcData(Optional.ofNullable(base).map(classes::getEntry), data.read("Category", Codec.STRING),
                    data.read("SpawnReason", Codec.STRING), data.read("StructureManagerPos", BlockPos.CODEC));
            if(getJobId() == null)
                setJob(folded.job().getValue());
        }

        String npcDataId = view.read("NpcDataId", Codec.STRING).orElse(null);
        if(npcDataId != null){
            foundNpcInitializationData = foundNpcInitializationData.withNpcClass(MiddleEarth.ofId(npcDataId));
            foundNpcData = new NpcData();
            foundNpcTextureData = new  NpcTextureData();
            isFighting = false;
        }

        this.dataTracker.set(NPC_DATA, foundNpcData);
        this.dataTracker.set(NPC_INITIALIZATION_DATA, foundNpcInitializationData);
        this.dataTracker.set(NPC_TEXTURE_DATA, foundNpcTextureData);
        this.dataTracker.set(IS_FIGHTING, isFighting);

        foundNpcInitializationData.tryToInitialize(this);

        this.updateAttackType();
    }

    @Override
    public boolean canUseRangedWeapon(ItemStack stack) {
        return true;
    }

    @Override
    public @Nullable ItemStack getPickBlockStack() {
        if(getNpcClass() == null)
            return ItemStack.EMPTY;
        return NpcSpawnEggHelper.getSpawnEgg(getWorld(), getNpcClass().getId());
    }
    public void updateAttackType() {
        if (this.getWorld() == null || this.getWorld().isClient)
            return;
        ItemStack itemStack = this.getMainHandStack();
        Goal wanted = this.meleeAttackGoal;
        if (itemStack.isOf(Items.BOW) || itemStack.isIn(ItemTagsME.BOW)) {
            boolean hard = this.getWorld().getDifficulty() == Difficulty.HARD;
            this.bowAttackGoal.setAttackInterval(hard ? BOW_ATTACK_INTERVAL_HARD : BOW_ATTACK_INTERVAL);
            wanted = this.bowAttackGoal;
        } else if (itemStack.isOf(Items.CROSSBOW) || itemStack.isIn(ItemTagsME.CROSSBOW)) {
            wanted = this.crossBowAttackGoal;
        }
        if(wanted == this.attackGoal)
            return;
        // Taking a running goal away stops it, and the crossbow goal lets go of its target as it stops. The fight goes on
        // with the other weapon
        LivingEntity target = getTarget();
        if(this.attackGoal != null)
            this.goalSelector.remove(this.attackGoal);
        this.attackGoal = wanted;
        this.goalSelector.add(ATTACK_PRIORITY, wanted);
        if(target != null && target.isAlive() && getTarget() == null)
            setTarget(target);
    }

    //region [DATA TRANSFER]
    public Identifier getNpcClassIdentifier(){
        return retrieveNpcData().getNpcClassId();
    }
    public Identifier getFactionIdentifier(){
        Faction faction = getFaction();
        if(faction == null) return null;
        return faction.getId();
    }
    public @Nullable UUID getPartyLeader() {
        return partyLeader;
    }

    public void setPartyLeader(@Nullable UUID partyLeader) {
        this.partyLeader = partyLeader;
    }

    // Nobody walks on behind a dead leader. The next in line takes the party and the way it was going
    private void handOverParty(ServerWorld world) {
        NpcEntity heir = null;
        for(NpcEntity follower : world.getEntitiesByClass(NpcEntity.class, getBoundingBox().expand(PARTY_RANGE), other -> other.isAlive() && getUuid().equals(other.getPartyLeader()))) {
            if(heir == null) {
                heir = follower;
                follower.setPartyLeader(null);
                follower.setTravelHeading(travelHeading);
                follower.setTravelDestination(travelDestination);
            } else {
                follower.setPartyLeader(heir.getUuid());
            }
        }
    }

    // In degrees
    public @Nullable Float getTravelHeading() {
        return travelHeading;
    }

    public void setTravelHeading(@Nullable Float travelHeading) {
        this.travelHeading = travelHeading;
    }

    public @Nullable BlockPos getTravelDestination() {
        return travelDestination;
    }

    public void setTravelDestination(@Nullable BlockPos travelDestination) {
        this.travelDestination = travelDestination;
    }

    public boolean isEvil() {
        Faction faction = getFaction();
        return faction != null && faction.getDisposition() == DispositionType.EVIL;
    }

    public boolean isHostileToFaction(Identifier otherFaction) {
        Faction faction = getFaction();
        return faction != null && faction.isHostileToward(otherFaction);
    }

    public void lieDown(BlockPos bed) {
        this.sleptFrom = getPos();
        sleep(bed);
    }

    // The game leaves a waking sleeper on whatever is next to the bed, a bench or a table as often as the floor,
    // and from there many never find a way down again
    @Override
    public void wakeUp() {
        super.wakeUp();
        Vec3d back = sleptFrom != null ? sleptFrom : bedStand != null ? Vec3d.ofBottomCenter(bedStand) : null;
        sleptFrom = null;
        if(back != null && !getWorld().isClient && back.squaredDistanceTo(getPos()) < WAKE_RANGE * WAKE_RANGE)
            refreshPositionAndAngles(back.x, back.y, back.z, getYaw(), getPitch());
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new NpcNavigation(this, world);
    }

    // Opens the gate or trapdoor of a loft the path leads through, and shuts it again once past
    private void tickHatch(ServerWorld world) {
        if(openedHatch != null && !openedHatch.isWithinDistance(getPos(), HATCH_CLOSE_DISTANCE)) {
            BlockState opened = world.isChunkLoaded(openedHatch.getX() >> 4, openedHatch.getZ() >> 4) ? world.getBlockState(openedHatch) : null;
            if(opened != null && NpcPathNodeMaker.isHatch(opened) && opened.get(Properties.OPEN))
                world.setBlockState(openedHatch, opened.with(Properties.OPEN, false));
            openedHatch = null;
        }
        Path path = getNavigation().getCurrentPath();
        if(path == null || path.isFinished())
            return;
        for(int i = path.getCurrentNodeIndex(); i < Math.min(path.getCurrentNodeIndex() + 2, path.getLength()); i++) {
            BlockPos node = path.getNodePos(i);
            for(BlockPos pos : new BlockPos[]{node, node.up()}) {
                if(!pos.isWithinDistance(getPos(), HATCH_OPEN_DISTANCE))
                    continue;
                BlockState state = world.getBlockState(pos);
                if(NpcPathNodeMaker.isHatch(state) && !state.get(Properties.OPEN)) {
                    world.setBlockState(pos, state.with(Properties.OPEN, true));
                    openedHatch = pos;
                }
            }
        }
    }

    public void setHousehold(@Nullable BlockPos bed, @Nullable BlockPos bedStand, @Nullable BlockPos store, List<BlockPos> links) {
        this.assignedBed = bed;
        this.bedStand = bedStand;
        this.store = store;
        this.links = links;
    }

    public List<BlockPos> getLinks() {
        return links;
    }

    public @Nullable BlockPos getAssignedBed() {
        return assignedBed;
    }

    public @Nullable BlockPos getBedStand() {
        return bedStand;
    }

    public @Nullable BlockPos getStore() {
        return store;
    }

    public @Nullable BlockPos getRoutineHome() {
        return routineHome;
    }

    public void setRoutineHome(@Nullable BlockPos routineHome) {
        this.routineHome = routineHome;
    }

    public void showRoutineProp(ItemStack prop) {
        if(routineStowedItem == null) {
            // Nothing shows in the off hand next to a two handed weapon, so that weapon is put away
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

    public boolean isTimid() {
        RoutineData routine = getRoutine();
        return routine != null && routine.isTimid();
    }

    // A timid npc meets a threat one way and keeps to it, or it would run, turn and run again. It runs to call the guards
    // where there are enough of them to come, and otherwise stands and fights with the other common folk about
    public boolean fleesFrom(LivingEntity threat) {
        if(!threat.getUuid().equals(threatFaced)) {
            threatFaced = threat.getUuid();
            fleesThreat = getWorld() instanceof ServerWorld world && countGuards(world) >= GUARDS_TO_FLEE;
            if(!fleesThreat && getWorld() instanceof ServerWorld world)
                callCommonFolk(world, threat);
        }
        return fleesThreat;
    }

    private int countGuards(ServerWorld world) {
        int count = 0;
        for(NpcEntity other : neighbours(world)) {
            if(!other.isTimid() && NpcRelations.of(other, this).isFriendly())
                count++;
        }
        return count;
    }

    private void callCommonFolk(ServerWorld world, LivingEntity threat) {
        for(NpcEntity other : world.getEntitiesByClass(NpcEntity.class, getBoundingBox().expand(HELP_RANGE, ALARM_HEIGHT_RANGE, HELP_RANGE),
                other -> other != this && other.isAlive() && other.isTimid() && other.getTarget() == null)) {
            if(!NpcRelations.of(other, this).isFriendly() || !other.canTarget(threat))
                continue;
            other.threatFaced = threat.getUuid();
            other.fleesThreat = false;
            if(other.isSleeping())
                other.wakeUp();
            other.setTarget(threat);
        }
    }

    // Those about who could come: the npcs near by, and everybody living in the same place
    private Set<NpcEntity> neighbours(ServerWorld world) {
        Set<NpcEntity> guards = new LinkedHashSet<>(world.getEntitiesByClass(NpcEntity.class, getBoundingBox().expand(ALARM_RANGE, ALARM_HEIGHT_RANGE, ALARM_RANGE), other -> other != this && other.isAlive()));
        BlockPos structureManagerPos = getStructureManagerHostPos();
        // Reading a block entity in an unloaded chunk would load that chunk
        if(structureManagerPos != null && world.isChunkLoaded(structureManagerPos.getX() >> 4, structureManagerPos.getZ() >> 4)
                && world.getBlockEntity(structureManagerPos) instanceof StructureManagerBlockEntity manager) {
            for(UUID owner : manager.residents()) {
                if(world.getEntity(owner) instanceof NpcEntity resident && resident != this && resident.isAlive())
                    guards.add(resident);
            }
        }
        return guards;
    }

    // Sends the fighters after whatever frightened this npc: those within earshot, and wherever they are, those with a bed in its structure
    public void callGuards(@Nullable LivingEntity threat) {
        if(threat == null || !(getWorld() instanceof ServerWorld world))
            return;
        for(NpcEntity guard : neighbours(world)) {
            // Allies and friends come to help, and whoever answers takes up the grudge against the threat
            if(guard.isTimid() || guard.getTarget() != null || !NpcRelations.of(guard, this).isFriendly())
                continue;
            guard.bearGrudge(threat);
            if(guard.canTarget(threat)) {
                if(guard.isSleeping())
                    guard.wakeUp();
                guard.setTarget(threat);
            }
        }
    }

    public boolean isNightShift() {
        return (getUuid().hashCode() & 1) != 0;
    }

    // Out beyond the walls on a routine, which is when an escort comes along
    public boolean isOnOuting() {
        return onOuting;
    }

    public void setOnOuting(boolean onOuting) {
        this.onOuting = onOuting;
        if(!onOuting)
            this.escort = null;
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

    public boolean isFarming() {
        return farming;
    }

    public void setFarming(boolean farming) {
        this.farming = farming;
    }

    public @Nullable UUID getEscort() {
        return escort;
    }

    // Follows its ward as a party member follows its leader, for as long as the outing lasts
    public void escort(NpcEntity ward) {
        ward.escort = getUuid();
        this.escorting = true;
        setPartyLeader(ward.getUuid());
    }

    public boolean isEscorting() {
        return escorting;
    }

    public void endEscort() {
        if(escorting)
            setPartyLeader(null);
        escorting = false;
    }

    // TODO: Verify this patch works and doesn't look goofy
    private void climbOutOfWater() {
        if(!isTouchingWater() || !horizontalCollision || getVelocity().y > CLIMBING_OUT)
            return;
        Path path = getNavigation().getCurrentPath();
        if(path == null || path.isFinished() || path.getCurrentNode().y <= getBlockY())
            return;
        Vec3d velocity = getVelocity();
        setVelocity(velocity.x, getJumpVelocity(), velocity.z);
    }

    public void setJob(@Nullable Identifier job) {
        this.dataTracker.set(JOB, job == null ? "" : job.toString());
    }

    // Given once, after the gear of the class is on: a job named for its trade swaps what is in the hand for its tool
    public void takeJob(Identifier job) {
        setJob(job);
        if(isPrisoner()) {
            disarm();
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

    public boolean isPrisoner() {
        return NpcJobs.PRISONER.getValue().equals(getJobId());
    }

    public @Nullable PrisonCell getPrisonCell() {
        return prisonCell;
    }

    public void setPrisonCell(@Nullable PrisonCell prisonCell) {
        this.prisonCell = prisonCell;
    }

    // A prisoner that has a cell stays in it
    public boolean isConfined() {
        return prisonCell != null && isPrisoner();
    }

    // A prisoner holds no weapon while it is held: what is in its hands is kept from it, and given back when it gets free
    private void disarm() {
        ItemStack main = getMainHandStack();
        ItemStack off = getOffHandStack();
        if(main.isEmpty() && off.isEmpty())
            return;
        if(!main.isEmpty() && confiscatedMain.isEmpty())
            confiscatedMain = main.copy();
        if(!off.isEmpty() && confiscatedOff.isEmpty())
            confiscatedOff = off.copy();
        equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        updateAttackType();
    }

    private void rearm() {
        if(!confiscatedMain.isEmpty())
            equipStack(EquipmentSlot.MAINHAND, confiscatedMain);
        if(!confiscatedOff.isEmpty())
            equipStack(EquipmentSlot.OFFHAND, confiscatedOff);
        confiscatedMain = ItemStack.EMPTY;
        confiscatedOff = ItemStack.EMPTY;
        updateAttackType();
    }

    private void tickPrison(ServerWorld world) {
        if(!isPrisoner()) {
            prisonCell = null;
            return;
        }
        if(!prisonCell.contains(getBlockPos()))
            free(world);
    }

    // Out of its cell, the prisoner belongs to nobody but its own people, and runs from the place that held it
    private void free(ServerWorld world) {
        PrisonCell cell = prisonCell;
        BlockPos managerPos = getStructureManagerHostPos();
        double centerX = cell.centerX();
        double centerZ = cell.centerZ();
        if(managerPos != null && world.isChunkLoaded(managerPos.getX() >> 4, managerPos.getZ() >> 4)) {
            RoutineSite site = RoutineSites.forManager(world, managerPos);
            if(site != null) {
                centerX = site.getCenterX() + 0.5;
                centerZ = site.getCenterZ() + 0.5;
            }
            StructureManagerBlockEntity.triggerDeathSignal(managerPos, this);
        }
        prisonCell = null;
        setJob(null);
        rearm();
        saveNpcData(retrieveNpcData().withoutStructureManagerPos());
        routineHome = null;
        assignedBed = null;
        bedStand = null;
        store = null;
        links = List.of();
        getNavigation().stop();
        double awayX = getX() - centerX;
        double awayZ = getZ() - centerZ;
        float heading = awayX == 0 && awayZ == 0 ? random.nextFloat() * 360f
                : (float) (MathHelper.atan2(-awayX, awayZ) * MathHelper.DEGREES_PER_RADIAN);
        setTravelDestination(null);
        setTravelHeading(heading);
    }

    public @Nullable Identifier getJobId() {
        String job = this.dataTracker.get(JOB);
        // Asked for whenever friend is told from foe, the id is too slow to read from its text every time
        if(!job.equals(cachedJobText)) {
            cachedJobText = job;
            cachedJobId = job.isEmpty() ? null : Identifier.tryParse(job);
            cachedJob = null;
        }
        return cachedJobId;
    }

    public @Nullable NpcJob getJob() {
        Identifier id = getJobId();
        if(id == null)
            return null;
        if(cachedJob == null)
            cachedJob = getWorld().getRegistryManager().getOptional(DynamicRegistriesME.NPC_JOB).map(jobs -> jobs.get(id)).orElse(null);
        return cachedJob;
    }

    // What the npc does with its day: its job's say where it has one, its class's otherwise
    public @Nullable RoutineData getRoutine() {
        NpcJob job = getJob();
        if(job != null && job.routine().isPresent())
            return job.routine().get();
        NpcClass npcClass = getNpcClass();
        return npcClass == null ? null : npcClass.getRoutine();
    }

    public boolean keepsBounds() {
        RoutineData routine = getRoutine();
        return routine != null && routine.keepsBounds();
    }

    public boolean isElf() {
        NpcClass npcClass = getNpcClass();
        return npcClass != null && RaceRegistry.ELF.getValue().equals(npcClass.getRace());
    }

    public boolean isRestTime() {
        RoutineData routine = getRoutine();
        if(routine == null || !routine.getPeriod().isResting(getWorld().getTimeOfDay(), isNightShift()))
            return false;
        // Elves rest in waking dreams, and lie down only now and then, each on its own night
        long day = getWorld().getTimeOfDay() / DAY_TICKS;
        return !isElf() || Math.floorMod(getUuid().hashCode() + day, ELF_SLEEP_INTERVAL) == 0;
    }

    public @Nullable LivingEntity getHuntTarget() {
        return huntTarget;
    }

    public void startHunt(LivingEntity prey, int duration) {
        this.huntTarget = prey;
        this.huntKill = null;
        this.huntEndAge = age + duration;
        setTarget(prey);
    }

    // The kind of animal the last hunt brought down, once
    public @Nullable EntityType<?> takeHuntKill() {
        EntityType<?> kill = huntKill;
        huntKill = null;
        return kill;
    }

    // The fighting goals never let go of a target by themselves
    private void tickHunt() {
        if(huntTarget.isAlive() && age < huntEndAge)
            return;
        if(huntTarget.isDead())
            huntKill = huntTarget.getType();
        if(getTarget() == huntTarget)
            setTarget(null);
        huntTarget = null;
    }

    public BlockPos getStructureManagerHostPos() {
        return this.retrieveNpcData().getStructureManagerPos();
    }
    //endregion

    public void tryToInitializeData(){
        if(dataInitialized)
            return;
        // Commands spawn at the origin first, so the npc waits until it has been moved
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
    public boolean isInvulnerableTo(ServerWorld world, DamageSource source) {
        if (source.isOf(DamageTypes.IN_WALL) && this.hasVehicle()) {
            return true;
        }
        // A hunter's stray arrow
        if(source.getAttacker() instanceof NpcEntity hunter && hunter != this && hunter.huntTarget != null && NpcRelations.of(this, hunter) != Relation.ENEMY)
            return true;
        return super.isInvulnerableTo(world, source);
    }

    @Override
    protected void mobTick(ServerWorld world) {
        tryToInitializeData();
        if(huntTarget != null)
            tickHunt();
        if(age % HATCH_CHECK_INTERVAL == 0)
            tickHatch(world);
        climbOutOfWater();
        if(age % PRISON_CHECK_INTERVAL == 0 && isPrisoner())
            disarm();
        if(prisonCell != null && age % PRISON_CHECK_INTERVAL == 0)
            tickPrison(world);
        // A target set from outside the targeting goals is never dropped by them. A dead one blocks everything else, and so does
        // one that is no enemy any more, as when the grudge a guard was called out for has run its time
        LivingEntity target = getTarget();
        if(target != null && (!target.isAlive() || age % TARGET_CHECK_INTERVAL == 0 && !canTarget(target)))
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

    public @Nullable NpcRoutineGoal getRoutineGoal() {
        return routineGoal;
    }

    @Override
    protected Vec3d getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        Vec3d pos = super.getPassengerAttachmentPos(passenger, dimensions, scaleFactor);

        Vec3d offset = new Vec3d(0.0, PASSENGER_OFFSET_Y * scaleFactor, PASSENGER_OFFSET_Z * scaleFactor);
        return pos.add(offset.rotateY(-this.getYaw() * MathHelper.RADIANS_PER_DEGREE));
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

    public boolean isFighting() {
        boolean isFighting = getFighting();

        this.setSprinting(isFighting);
        if(this.hasVehicle() && getVehicle() instanceof AbstractHorseEntity abstractHorseEntity){
            abstractHorseEntity.setSprinting(isFighting);
        }
        return isFighting;
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
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        // Gear comes from the npc class, not vanilla's random armour
    }

    @Override
    public void onDeath(DamageSource source) {
        if(getWorld() instanceof ServerWorld world && partyLeader == null && travelHeading != null)
            handOverParty(world);
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
        if(structureManagerPos != null){
            StructureManagerBlockEntity.triggerDeathSignal(structureManagerPos, this);
        }
        super.onDeath(source);
    }

    private boolean canDropLoot(DamageSource damageSource, boolean causedByPlayer){
        if(!causedByPlayer || damageSource == null || !(damageSource.getAttacker() instanceof PlayerEntity player))
            return false;
        PlayerData data = StateSaverAndLoader.getPlayerState(player);
        if(data == null || data.getFaction() == null)
            return true;
        try{
            return FactionLookup.getFactionById(getWorld(), data.getFaction()).isHostileToward(this.getFactionIdentifier());
        } catch (FactionIdentifierException e){
            return true;
        }
    }

    @Override
    protected void drop(ServerWorld world, DamageSource damageSource) {
        if(!canDropLoot(damageSource, damageSource.getAttacker() != null && damageSource.getAttacker().isPlayer()))
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

    @Override
    protected void dropLoot(ServerWorld world, DamageSource damageSource, boolean causedByPlayer) {
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
    public boolean isPersistent() {
        BlockPos structureManagerPos = getStructureManagerHostPos();
        if(structureManagerPos == null)
            return super.isPersistent();
        // Asked every tick, and reading a block entity in an unloaded chunk would load that chunk
        if(age >= managerCheckAge) {
            managerCheckAge = age + MANAGER_CHECK_INTERVAL;
            if(getWorld().isChunkLoaded(structureManagerPos.getX() >> 4, structureManagerPos.getZ() >> 4))
                managerPresent = getWorld().getBlockEntity(structureManagerPos) instanceof StructureManagerBlockEntity;
        }
        if(managerPresent)
            return true;
        return super.isPersistent();
    }

    public @Nullable Faction getFaction(){
        NpcData data = retrieveNpcData();
        if(data == null)
            return null;
        Identifier factionId = data.getFaction();
        if(factionId == null)
            return null;
        // Asked for every npc in sight when looking for enemies, the registry is too slow for that
        if(factionId == cachedFactionId)
            return cachedFaction;
        try {
            cachedFaction = FactionLookup.getFactionById(getWorld(), factionId);
        } catch (FactionIdentifierException e) {
            cachedFaction = null;
        }
        cachedFactionId = factionId;
        return cachedFaction;
    }

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
        this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_ATTACK_SOUND);
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
    public void tickRiding() {
        super.tickRiding();
        Entity entity = this.getControllingVehicle();
        if (entity instanceof PathAwareEntity pathAwareEntity) {
            this.bodyYaw = pathAwareEntity.bodyYaw;
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
    protected void takeShieldHit(ServerWorld world, LivingEntity attacker) {
        super.takeShieldHit(world, attacker);
        ItemStack itemStack = this.getBlockingItem();
        BlocksAttacksComponent blocksAttacksComponent = itemStack != null ? itemStack.get(DataComponentTypes.BLOCKS_ATTACKS) : null;
        float f = attacker.getWeaponDisableBlockingForSeconds();
        if (f > 0.0f && blocksAttacksComponent != null) {
            blocksAttacksComponent.applyShieldCooldown(world, this, f, itemStack);
        }
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        return NpcRelations.of(this, target) == Relation.ENEMY && super.canTarget(target);
    }

    public void setPassingDoor(boolean passingDoor) {
        this.passingDoor = passingDoor;
    }

    // Two npcs side by side in a doorway push each other into the frame, and neither gets through
    @Override
    public void pushAwayFrom(Entity entity) {
        if(entity instanceof NpcEntity other && (passingDoor || other.passingDoor))
            return;
        super.pushAwayFrom(entity);
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    protected void dropEquipment(ServerWorld world, DamageSource source, boolean causedByPlayer) {
    }

    public float getWidthScale() {
        try{
            return (float) this.getAttributeValue(EntityAttributesME.WIDTH_SCALE);
        }
        catch (Exception ignored){
            return 1.0f;
        }
    }

    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier, @Nullable ItemStack shotFrom) {
        return ProjectileUtil.createArrowProjectile(this, arrow, damageModifier, shotFrom);
    }

    public boolean isAiming() {
        return this.getItemUseTime() > 0;
    }

    // Aimed to hit the middle of the target's body, leading it by how it moves now. The inaccuracy is the game's own spread
    // of a shot, added on top: 0 never misses, and skeletons shoot with 2 on hard to 10 on easy
    public void shootAt(LivingEntity target, float pullProgress, float powerModifier, float inaccuracy) {
        ItemStack weapon = this.getMainHandStack();
        ItemStack projectileStack = this.getProjectileType(weapon);
        if(projectileStack.isEmpty()) projectileStack = new ItemStack(Items.ARROW, 1);

        PersistentProjectileEntity projectile = this.createArrowProjectile(projectileStack, pullProgress, weapon);
        float speed = BASE_ARROW_SPEED * powerModifier;
        Vec3d aim = solveArrowAim(projectile.getPos(), target, speed, projectile.getFinalGravity());

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

    // Follows the arrow as the game moves it each tick: it moves, loses a hundredth of its speed, then falls by its gravity.
    // Of the arcs that reach the target the flattest is taken, and out of range the shot that carries furthest
    private static Vec3d solveArrowAim(Vec3d origin, LivingEntity target, double speed, double gravity) {
        Vec3d targetStart = new Vec3d(target.getX(), target.getBodyY(0.5), target.getZ());
        Vec3d targetMovement = target.getMovement();

        double bestTicks = -1;
        double previousTicks = 0;
        double closestTicks = 1;
        double closestSpeedGap = Double.MAX_VALUE;
        for (double ticks = AIM_STEP; ticks <= MAX_ARROW_FLIGHT_TICKS; ticks += AIM_STEP) {
            double speedGap = requiredLaunchVelocity(origin, targetStart, targetMovement, gravity, ticks).length() - speed;
            if (speedGap <= 0) {
                // The needed speed falls with flight time before it rises again, so the first crossing is the flat shot
                double low = previousTicks, high = ticks;
                for (int i = 0; i < AIM_BISECTIONS; i++) {
                    double mid = (low + high) / 2;
                    if (requiredLaunchVelocity(origin, targetStart, targetMovement, gravity, mid).length() <= speed) high = mid;
                    else low = mid;
                }
                bestTicks = high;
                break;
            }
            if (speedGap < closestSpeedGap) {
                closestSpeedGap = speedGap;
                closestTicks = ticks;
            }
            previousTicks = ticks;
        }

        double ticks = bestTicks > 0 ? bestTicks : closestTicks;
        return requiredLaunchVelocity(origin, targetStart, targetMovement, gravity, ticks);
    }

    // What the arrow has to leave the bow with to be where the target will be after that many ticks
    private static Vec3d requiredLaunchVelocity(Vec3d origin, Vec3d targetStart, Vec3d targetMovement, double gravity, double ticks) {
        // After n ticks: offset = v0 * k(n) - gravity / (1 - drag) * (n - k(n)) on y, where k(n) = (1 - drag^n) / (1 - drag).
        double travel = (1 - Math.pow(ARROW_AIR_DRAG, ticks)) / (1 - ARROW_AIR_DRAG);
        double fall = gravity / (1 - ARROW_AIR_DRAG) * (ticks - travel);
        Vec3d offset = targetStart.add(targetMovement.multiply(ticks)).subtract(origin);
        return new Vec3d(offset.x / travel, (offset.y + fall) / travel, offset.z / travel);
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        this.shootAt(target, 1, pullProgress, NO_INACCURACY);
    }

    public void shootCrossbowAt(LivingEntity target) {
        this.shootAt(target, 1, CROSSBOW_ARROW_POWER, NO_INACCURACY);
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
    protected boolean isInSameTeam(Entity other) {
        return NpcRelations.of(this, other).isFriendly();
    }

    // Personal: kept by the npc that was wronged, and by those it called to its help. A friendly npc's blow is a stray one,
    // but a friendly beast that turns on the npc is fought like anyone else
    public void bearGrudge(LivingEntity offender) {
        if(offender == this || getWorld().isClient)
            return;
        if(offender instanceof NpcEntity && NpcRelations.standing(this, offender).isFriendly())
            return;
        long now = getWorld().getTime();
        grudges.values().removeIf(until -> until < now);
        grudges.put(offender.getUuid(), now + GRUDGE_TICKS);
    }

    public boolean hasGrudgeAgainst(Entity other) {
        Long until = grudges.get(other.getUuid());
        return until != null && until >= getWorld().getTime();
    }

    @Override
    public void setAttacker(@Nullable LivingEntity attacker) {
        super.setAttacker(attacker);
        if(attacker != null)
            bearGrudge(attacker);
    }

    public static boolean canSpawn(EntityType<NpcEntity> type, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        return SpawnUtil.canSpawn(blockPos, serverWorldAccess, spawnReason);
    }

    static {
        NPC_DATA = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistryME.NPC_DATA);
        NPC_INITIALIZATION_DATA = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistryME.NPC_INITIALIZATION_DATA);
        NPC_TEXTURE_DATA = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistryME.NPC_TEXTURE_DATA);
        IS_FIGHTING = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
}
