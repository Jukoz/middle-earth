package net.sevenstars.middleearth.entity.npcs;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.state.property.Properties;
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
import net.minecraft.entity.mob.HostileEntity;
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
import net.minecraft.util.Hand;
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
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.TrollEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.snow.SnowTrollEntity;
import net.sevenstars.middleearth.entity.goals.*;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.npc_roles.NpcRole;
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
import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;
import net.sevenstars.middleearth.resources.datas.npc_types.data.LootData;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.utils.ItemTagsME;
import net.sevenstars.middleearth.utils.SpawnUtil;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntity;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class NpcEntity extends PathAwareEntity implements EquipmentHolder, CrossbowUser {
    public static class KeyStrings {
        public static final String DATA = "NpcData";
        public static final String INITIALIZATION_DATA = "InitializationData";
        public static final String TEXTURE_DATA = "TextureData";
        public static final String IS_FIGHTING = "IsFighting";
        public static final String PARTY_LEADER = "PartyLeader";
        public static final String TRAVEL_HEADING = "TravelHeading";
        public static final String TRAVEL_DESTINATION = "TravelDestination";
        public static final String ROUTINE_HOME = "RoutineHome";
        public static final String ROLE = "Role";
        public static final String ESCORTING = "Escorting";
        public static final String ASSIGNED_BED = "AssignedBed";
        public static final String BED_STAND = "BedStand";
        public static final String STORE = "Store";
        public static final String HOUSEHOLD_KNOWN = "HouseholdKnown";
        public static final String ROUTINE_STOWED_ITEM = "RoutineStowedItem";
        public static final String ROUTINE_STOWED_MAIN_HAND = "RoutineStowedMainHand";
    }
    // [TrackedDatas]
    private static final TrackedData<NpcData> NPC_DATA;
    private static final TrackedData<NpcInitializationData> NPC_INITIALIZATION_DATA;
    private static final TrackedData<NpcTextureData> NPC_TEXTURE_DATA;
    private static final TrackedData<Boolean> IS_FIGHTING;

    // In ticks
    private static final int MANAGER_CHECK_INTERVAL = 100;
    private static final int WAKE_RANGE = 16;
    private static final int HATCH_CHECK_INTERVAL = 4;
    private static final int PARTY_RANGE = 64;
    private static final double HATCH_OPEN_DISTANCE = 3;
    private static final double HATCH_CLOSE_DISTANCE = 3.5;
    // In days
    private static final int ELF_SLEEP_INTERVAL = 4;
    private static final int ALARM_RANGE = 24;
    private static final int ALARM_HEIGHT_RANGE = 8;

    // The id of the npc's role, empty for none. Tracked so that the client can name the npc for it
    private static final TrackedData<String> ROLE = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> USING_ITEM = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> CROSSBOW_CHARGING = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private @Nullable UUID partyLeader = null;
    private @Nullable Float travelHeading = null;
    private @Nullable BlockPos travelDestination = null;
    private @Nullable BlockPos routineHome = null;
    // Upward speed of an npc already on its way out of the water, which is not lifted again
    private static final double CLIMBING_OUT = 0.1;
    // Given out by the structure manager of its nest
    private boolean householdKnown = false;
    private boolean onOuting = false;
    private @Nullable UUID escort = null;
    // Saved, or an escort loaded in the middle of an outing would follow its ward about for good
    private boolean escorting = false;
    private @Nullable BlockPos assignedBed = null;
    private @Nullable Vec3d sleptFrom = null;
    private @Nullable BlockPos openedHatch = null;
    private @Nullable BlockPos bedStand = null;
    private @Nullable BlockPos store = null;
    // What the hand held before a routine put something else in it
    private @Nullable ItemStack routineStowedItem = null;
    private boolean routineStowedMainHand = false;
    private @Nullable LivingEntity huntTarget = null;
    private @Nullable EntityType<?> huntKill = null;
    private int huntEndAge = 0;
    private @Nullable Identifier cachedFactionId = null;
    private @Nullable Faction cachedFaction = null;
    private boolean passingDoor = false;
    private boolean dataInitialized = false;
    private boolean managerPresent = true;
    private int managerCheckAge = 0;

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
        this.updateAttackType();
        this.navigation.setCanOpenDoors(true);
    }

    @Nullable
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
    // [IsBlocking] // TODO
    public void setBlocking(boolean state){
        //this.dataTracker.set(IS_FIGHTING, state);
    }
    public boolean getBlocking(){
        return false; //this.dataTracker.get(IS_FIGHTING);
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
    public boolean shouldRefreshVisuals() {
        return this.retrieveNpcTextureData().needToBeRefreshed();
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
    // [NpcTypeIdentifier]
    public void prepareNpcIdentifier(Identifier npcTypeIdentifier){
        NpcInitializationData newNpcInitializationData = this.retrieveNpcInitializationData().withType(npcTypeIdentifier);
        this.saveNpcInitializationData(newNpcInitializationData);
    }
    // [NpcData]
    public void saveNpcData(NpcData npcData) {
        this.dataTracker.set(NPC_DATA, npcData);
    }
    public NpcData retrieveNpcData() {
        return this.dataTracker.get(NPC_DATA);
    }
    // [NpcType]
    public NpcType getNpcType(){
        return retrieveNpcData().getNpcType();
    }
    public void saveNpcType(RegistryEntry<NpcType> npcType){
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
    private void saveSpawnReason(SpawnReason spawnReason) {
        NpcData newNpcData = this.retrieveNpcData().withSpawnReason(spawnReason);
        this.saveNpcData(newNpcData);
    }
    public SpawnReason getSpawnReason() {
        return this.retrieveNpcData().getSpawnReason();
    }
    // [StructureManager]
    public void assignStructureManager(StructureManagerBlockEntity blockEntity){
        NpcData newNpcData = this.retrieveNpcData().withStructureManagerPos(blockEntity.getPos());
        this.saveNpcData(newNpcData);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.ATTACK_DAMAGE, 1.0);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(3, new NpcFleeHomeGoal(this, 1.15));
        this.goalSelector.add(5, new NpcRoutineGoal(this, 0.8));
        this.goalSelector.add(6, new NpcTravelGoal(this, 0.8));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(3, new TargetPlayerDiplomacyGoal(this));
        this.targetSelector.add(4, new NpcDoorInteractGoal(this, true));
        this.targetSelector.add(4, new NpcModDoorGoal(this));
        this.targetSelector.add(5, new TargetNPCDiplomacyGoal(this));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, SnowTrollEntity.class, true));
        this.targetSelector.add(7, new ActiveTargetGoal<>(this, SpawnOfShelobEntity.class, true));
        this.targetSelector.add(8, new ActiveTargetGoal<>(this, ShelobiteScuttlerEntity.class, true));
        this.targetSelector.add(9, new ActiveTargetGoal<>(this, ShelobiteLarvaEntity.class, true));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(NPC_INITIALIZATION_DATA, new NpcInitializationData());
        builder.add(NPC_DATA, new NpcData());
        builder.add(NPC_TEXTURE_DATA, new NpcTextureData());
        builder.add(IS_FIGHTING, false);
        builder.add(ROLE, "");
        builder.add(CROSSBOW_CHARGING, false);
        builder.add(USING_ITEM, 0);
    }

    @Override
    public void writeData(WriteView view) {
        super.writeData(view);
        writeCustomData(view);
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
        view.putNullable(KeyStrings.ROLE, Identifier.CODEC, this.getRoleId());
        view.putBoolean(KeyStrings.ESCORTING, this.escorting);
        view.putBoolean(KeyStrings.HOUSEHOLD_KNOWN, this.householdKnown);
        view.putNullable(KeyStrings.ASSIGNED_BED, BlockPos.CODEC, this.assignedBed);
        view.putNullable(KeyStrings.BED_STAND, BlockPos.CODEC, this.bedStand);
        view.putNullable(KeyStrings.STORE, BlockPos.CODEC, this.store);
        view.putNullable(KeyStrings.ROUTINE_STOWED_ITEM, ItemStack.OPTIONAL_CODEC, this.routineStowedItem);
        view.putBoolean(KeyStrings.ROUTINE_STOWED_MAIN_HAND, this.routineStowedMainHand);
    }

    @Override
    public void readData(ReadView view) {
        super.readData(view);
        readCustomData(view);
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
        this.setRole(view.read(KeyStrings.ROLE, Identifier.CODEC).orElse(null));
        this.escorting = view.getBoolean(KeyStrings.ESCORTING, false);
        this.householdKnown = view.getBoolean(KeyStrings.HOUSEHOLD_KNOWN, false);
        this.assignedBed = view.read(KeyStrings.ASSIGNED_BED, BlockPos.CODEC).orElse(null);
        this.bedStand = view.read(KeyStrings.BED_STAND, BlockPos.CODEC).orElse(null);
        this.store = view.read(KeyStrings.STORE, BlockPos.CODEC).orElse(null);
        // The routine itself is not saved, so its prop is put away again
        this.routineStowedItem = view.read(KeyStrings.ROUTINE_STOWED_ITEM, ItemStack.OPTIONAL_CODEC).orElse(null);
        this.routineStowedMainHand = view.getBoolean(KeyStrings.ROUTINE_STOWED_MAIN_HAND, false);
        clearRoutineProp();

        Identifier savedType = view.getOptionalReadView(KeyStrings.DATA).flatMap(data -> data.read("Type", Identifier.CODEC)).orElse(null);
        NpcRegistry.Folded folded = savedType == null ? null : NpcRegistry.FOLDED.get(savedType);
        if(folded != null) {
            // Its gear stays as it was saved, only the type it is and the role it has change
            ReadView data = view.getOptionalReadView(KeyStrings.DATA).orElseThrow();
            Registry<NpcType> types = getWorld().getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_TYPE);
            NpcType base = types.get(folded.type().getValue());
            foundNpcData = new NpcData(Optional.ofNullable(base).map(types::getEntry), data.read("Category", Codec.STRING),
                    data.read("SpawnReason", Codec.STRING), data.read("StructureManagerPos", BlockPos.CODEC));
            if(getRoleId() == null)
                setRole(folded.role().getValue());
        }

        String npcDataId = view.read("NpcDataId", Codec.STRING).orElse(null);
        if(npcDataId != null){
            foundNpcInitializationData = foundNpcInitializationData.withType(MiddleEarth.ofId(npcDataId));
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
        if(getNpcType() == null)
            return ItemStack.EMPTY;
        return NpcSpawnEggHelper.getSpawnEgg(getEntityWorld(), getNpcType().getId());
    }
    public void updateAttackType() {
        if (this.getEntityWorld() != null && !this.getEntityWorld().isClient()) {
            this.goalSelector.remove(this.meleeAttackGoal);
            this.goalSelector.remove(this.bowAttackGoal);
            ItemStack itemStack = this.getMainHandStack();
            if (itemStack.isOf(Items.BOW) || itemStack.isIn(ItemTagsME.BOW)) {
                int i = 30;
                if (this.getEntityWorld().getDifficulty() != Difficulty.HARD) {
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
        //if (this.getEntityWorld() != null && !this.getEntityWorld().isClient()) {
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

    //region [DATA TRANSFER]
    // GETTERS
    public Identifier getNpcTypeIdentifier(){
        return retrieveNpcData().getNpcTypeId();
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

    public void setHousehold(@Nullable BlockPos bed, @Nullable BlockPos bedStand, @Nullable BlockPos store) {
        this.householdKnown = true;
        this.assignedBed = bed;
        this.bedStand = bedStand;
        this.store = store;
    }

    public boolean isHouseholdKnown() {
        return householdKnown;
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

    private EquipmentSlot routinePropSlot() {
        return routineStowedMainHand ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
    }

    // Sends the fighters after whatever frightened this npc: those within earshot, and wherever they are, those with a bed in its structure
    public void callGuards(@Nullable LivingEntity threat) {
        if(threat == null || !(getWorld() instanceof ServerWorld world))
            return;
        Set<NpcEntity> guards = new LinkedHashSet<>(world.getEntitiesByClass(NpcEntity.class, getBoundingBox().expand(ALARM_RANGE, ALARM_HEIGHT_RANGE, ALARM_RANGE), other -> other != this && other.isAlive()));
        BlockPos structureManagerPos = getStructureManagerHostPos();
        // Reading a block entity in an unloaded chunk would load that chunk
        if(structureManagerPos != null && world.isChunkLoaded(structureManagerPos.getX() >> 4, structureManagerPos.getZ() >> 4)
                && world.getBlockEntity(structureManagerPos) instanceof StructureManagerBlockEntity manager) {
            for(UUID owner : manager.bedOwners()) {
                if(world.getEntity(owner) instanceof NpcEntity resident && resident != this && resident.isAlive())
                    guards.add(resident);
            }
        }
        for(NpcEntity guard : guards) {
            RoutineData guardRoutine = guard.getRoutine();
            boolean fights = guardRoutine == null || !guardRoutine.isTimid();
            if(fights && guard.getTarget() == null && !isHostileToward(guard) && guard.canTarget(threat)) {
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

    public void setRole(@Nullable Identifier role) {
        this.dataTracker.set(ROLE, role == null ? "" : role.toString());
    }

    // Given once, after the gear of the type is on: a role named for its trade swaps what is in the hand for its tool
    public void takeRole(Identifier role) {
        setRole(role);
        NpcRole taken = getRole();
        if(taken == null || !taken.replacesName())
            return;
        equipStack(EquipmentSlot.MAINHAND, taken.tool().map(Item::getDefaultStack).orElse(ItemStack.EMPTY));
        updateAttackType();
    }

    public @Nullable Identifier getRoleId() {
        String role = this.dataTracker.get(ROLE);
        return role.isEmpty() ? null : Identifier.tryParse(role);
    }

    public @Nullable NpcRole getRole() {
        Identifier id = getRoleId();
        if(id == null)
            return null;
        return getWorld().getRegistryManager().getOptional(DynamicRegistriesME.NPC_ROLE).map(roles -> roles.get(id)).orElse(null);
    }

    // What the npc does with its day: its role's say where it has one, its type's otherwise
    public @Nullable RoutineData getRoutine() {
        NpcRole role = getRole();
        if(role != null && role.routine().isPresent())
            return role.routine().get();
        NpcType type = getNpcType();
        return type == null ? null : type.getRoutine();
    }

    public boolean keepsBounds() {
        RoutineData routine = getRoutine();
        return routine != null && routine.keepsBounds();
    }

    public boolean isElf() {
        NpcType type = getNpcType();
        return type != null && RaceRegistry.ELF.getValue().equals(type.getRace());
    }

    public boolean isRestTime() {
        RoutineData routine = getRoutine();
        if(routine == null || !routine.getPeriod().isResting(getWorld().getTimeOfDay(), isNightShift()))
            return false;
        // Elves rest in waking dreams, and lie down only now and then, each on its own night
        long day = getWorld().getTimeOfDay() / 24000;
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
        if(Objects.equals(getBlockPos(), new BlockPos(0, 0, 0))) // 0,0,0 is what's used for commands, needs to be delayed
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
        if(source.getAttacker() instanceof NpcEntity hunter && hunter != this && hunter.huntTarget != null && !isHostileToward(hunter))
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
        // A target set from outside the targeting goals is never dropped by them, and a dead one blocks everything else
        LivingEntity target = getTarget();
        if(target != null && !target.isAlive())
            setTarget(null);
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
        super.mobTick(world);

        if(hasVehicle()){
            rotate(getVehicle().getYaw(), false, getVehicle().getPitch(), false);
        }
    }

    @Override
    protected Vec3d getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        Vec3d pos = super.getPassengerAttachmentPos(passenger, dimensions, scaleFactor);

        Vec3d offset = new Vec3d(
                0.0,
                -0.3 * scaleFactor,
                -0.25 * scaleFactor
        );

        return pos.add(rotatePoint(offset, this.getYaw()));
    }

    private static Vec3d rotatePoint(Vec3d point, float yaw) {
        return point.rotateY(-yaw * ((float)Math.PI / 180F));
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

        if(!this.getEntityWorld().isClient()) {

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
        return !this.hasVehicle();
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        // Overrides vanilla init equipment (gold sets???)
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
        boolean canDropLoot = false;
        if(!causedByPlayer)
            return canDropLoot;
        if(damageSource == null)
            return canDropLoot;

        if(damageSource.getAttacker() instanceof PlayerEntity player){
            PlayerData data = StateSaverAndLoader.getPlayerState(player);
            if(data == null)
                canDropLoot = true;
            else if(data.getFaction() == null)
                canDropLoot = true;
            else{
                try{
                    Faction faction = FactionLookup.getFactionById(getEntityWorld(), data.getFaction());
                    if(faction.isHostileToward(this.getFactionIdentifier()))
                        canDropLoot = true;
                } catch (FactionIdentifierException e){
                    canDropLoot = true;
                }
            }
        }

        return canDropLoot;
    }

    protected void drop(ServerWorld world, DamageSource damageSource) {
        if(!canDropLoot(damageSource, damageSource.getAttacker() != null && damageSource.getAttacker().isPlayer()))
            return;
        super.drop(world, damageSource);
    }

    protected int getExperienceToDrop(ServerWorld world) {
        LootData lootData = retrieveLootData();
        if(lootData == null)
            return 1;
        return lootData.getExperience(world);
    }

    @Override
    protected void dropLoot(ServerWorld world, DamageSource damageSource, boolean causedByPlayer) {
        RegistryKey<LootTable> lootTableRegistryKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, getNpcType().getId().withPrefixedPath("entities/"));
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

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
    }

    protected Faction getFaction(){
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
        this.getEntityWorld().sendEntityStatus(this, EntityStatuses.PLAY_ATTACK_SOUND);
        boolean targetDamaged;
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

        if(hasVehicle() && getVehicle() instanceof AbstractBeastEntity mountEntity){
            mountEntity.tryAttack((ServerWorld) target.getEntityWorld(), target);
        }

        targetDamaged = target.damage(world, damageSource, finalDamage);
        if (targetDamaged) {
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
        if(this.getNpcTypeIdentifier() == null) {
            return Text.translatable("npc_type."+ MiddleEarth.MOD_ID +".npc");
        }
        NpcRole role = getRole();
        Identifier faction = getFactionIdentifier();
        if(role != null && role.replacesName() && faction != null)
            return Text.translatable(getRoleId().toTranslationKey("npc_role"), Text.translatable(faction.toTranslationKey("faction")));
        return Text.translatable(this.getNpcTypeIdentifier().toTranslationKey("npc_type"));
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
        return shouldTarget(this, target) && super.canTarget(target);
    }

    public static boolean shouldTarget(NpcEntity npcEntity, LivingEntity target){
        // TODO : datadriven
        if(target instanceof SnailEntity || target instanceof HostileEntity || target instanceof SnowTrollEntity || target instanceof Pouncer)
            return true;
        if(target == npcEntity.huntTarget)
            return true;
        if(!npcEntity.isInSameTeam(target)){
            return true;
        }
        return false;
    }

    public int getTickAttackSpeedCooldown(){
        if(!this.getAttributes().hasAttribute(EntityAttributes.ATTACK_SPEED))
            return 1;
        return (int)this.getAttributes().getValue(EntityAttributes.ATTACK_SPEED);
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

    private void shootAt(LivingEntity target, float pullProgress, float powerModifier) {
        if (!isReadyToShoot())
            return;

        ItemStack weapon = this.getMainHandStack();
        ItemStack projectileStack = this.getProjectileType(weapon);
        if(projectileStack.isEmpty()) projectileStack = new ItemStack(Items.ARROW, 1);

        PersistentProjectileEntity projectile = this.createArrowProjectile(projectileStack, pullProgress, weapon);

        double distanceX = target.getX() - this.getX();
        double distanceZ = target.getZ() - this.getZ();
        double horizontalDistance = Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);

        double distanceY = target.getBodyY(0.3F) - projectile.getY();

        float scale = this.getScale();
        float velocity = 1.6F * powerModifier;

        // Reduce the vanilla arc compensation for larger entities and faster arrows.
        double arcCompensation = horizontalDistance * (0.2F / (scale * powerModifier));

        if (this.getEntityWorld() instanceof ServerWorld serverWorld) {
            ProjectileEntity.spawnWithVelocity(
                    projectile,
                    serverWorld,
                    projectileStack,
                    distanceX,
                    distanceY + arcCompensation,
                    distanceZ,
                    velocity,
                    (float) (14 - serverWorld.getDifficulty().getId() * 4)
            );
        }

        this.playSound(
                SoundEvents.ENTITY_ARROW_SHOOT,
                1.0F,
                1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F)
        );

        stopAiming();
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        this.shootAt(target, 1, pullProgress);
    }

    public void shootAt(LivingEntity livingEntity) {
        try{
            this.shootAt(livingEntity, BowItem.getPullProgress(getItemUseTime()), 2f);
        } catch (IllegalArgumentException e){
            this.shootAt(livingEntity, CustomLongbowWeaponItem.getPullProgressLongbow(getItemUseTime()), 3f);
        }
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

    @Override
    protected boolean isInSameTeam(Entity other) {
        if(other instanceof NpcEntity npc){
            return !isHostileToward(npc);
        }
        else if(other instanceof PlayerEntity player){
            return !isHostileTowardPlayer(player);
        }
        return !isHostileToward(other);
    }

    private boolean isHostileTowardPlayer(PlayerEntity player) {
        if(player.getEntityWorld().isClient() || !player.canTakeDamage())
            return false;
        PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
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
        if(other instanceof SnailEntity || other instanceof HostileEntity || other instanceof TrollEntity || other instanceof Pouncer)
            return true;
        // A wild cave troll attacks every NPC
        if(other instanceof CaveTrollEntity caveTroll && !caveTroll.isTame())
            return true;

        if(!other.hasPassengers())
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
        Identifier otherNpcFaction = npc.getFactionIdentifier();
        if(otherNpcFaction == null)
            return true;
        if(ownFaction.isHostileToward(otherNpcFaction))
            return true;
        return false;
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
