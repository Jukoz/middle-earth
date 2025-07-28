package net.sevenstars.middleearth.entity.npcs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentHolder;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.entity.ModTrackedDataHandlerRegistry;
import net.sevenstars.middleearth.entity.npcs.data.NpcEntityTextureData;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.resources.FactionsME;
import net.sevenstars.middleearth.resources.NpcTexturePatternsME;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.NpcDataLookup;
import net.sevenstars.middleearth.resources.datas.npcs.NpcUtil;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTexturePattern;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;


public class NpcEntity extends PassiveEntity implements EquipmentHolder {
    // Data to use
    private static final TrackedData<String> CATEGORY;
    private static final TrackedData<String> FACTION_ID;
    private static final TrackedData<String> NPC_DATA_ID;
    private static final TrackedData<NpcEntityTextureData> TEXTURE_DATA;
    private static final TrackedData<BlockPos> STRUCTURE_MANAGER_HOST_POS;

    public Faction factionCache;
    public NpcData npcDataCache;

    public NpcEntity(EntityType<NpcEntity> entityType, World world) {
        super(entityType, world);
        this.createRandom(world);
    }

    public static NpcEntity create(World world, BlockPos pos){
        var npcEntity = new NpcEntity(ModEntities.NPC, world);
        npcEntity.setPosition(pos.toCenterPos());
        return npcEntity;
    }
    public NpcEntity withCategory(EntityCategory category){
        setNpcCategory(category);
        return this;
    }
    public NpcEntity withFaction(Identifier identifier){
        try{
            this.factionCache = FactionLookup.getFactionById(getWorld(), identifier);
            setFactionId(identifier);
        } catch (FactionIdentifierException ignored) {

        }
        return this;
    }
    public NpcEntity withNpcData(Identifier identifier){
        this.npcDataCache = NpcDataLookup.getNpcData(getWorld(), identifier);
        if(this.npcDataCache != null){
            setNpcDataId(identifier);
            applyNpcData(this.npcDataCache);
        }
        return this;
    }

    private void applyNpcData(NpcData data){
        World world = getWorld();
        if(world.isClient)
            return;
        DynamicRegistryManager dynamicRegistryManager = world.getRegistryManager();

        // set attributes
        data.applyAttributes(this);
        // set textures
        NpcTextureData.Identity textureIdentity = NpcTextureData.Identity.create(data.getNpcTextureData(), getNpcCategory());
        var npcTextureData = data.getNpcTextureData();

        var generatedTextureData = generateSkinTextureData(new NpcEntityTextureData(), textureIdentity);
        generatedTextureData = generateEyeTextureData(generatedTextureData, textureIdentity, data.getNpcTextureData().haveEmissiveEyes(textureIdentity));
        generatedTextureData = generateHairTextureData(generatedTextureData, textureIdentity, dynamicRegistryManager);
        generatedTextureData = generateClothingTextureData(generatedTextureData, textureIdentity);
        setNpcTextureData(generatedTextureData);

        // set gear
        NpcUtil.equipAll(this, data.getGear());
    }

    private NpcEntity createRandom(World world){
        var random = getWorld().getRandom();

        EntityCategory category = random.nextBoolean() ? EntityCategory.MALE : EntityCategory.FEMALE;
        try {
            Faction faction = FactionLookup.getFactionById(getWorld(), FactionsME.GONDOR.getValue());
            var npcDataIds = faction.getAllNpcDatas().get(NpcRank.SOLDIER);
            Identifier npcDataId = (npcDataIds == null)
                    ? null
                    : npcDataIds.get(random.nextInt(npcDataIds.size()));
            if(npcDataId != null)
                this.withCategory(category)
                        .withFaction(faction.getId())
                        .withNpcData(npcDataId);
        }
        catch (FactionIdentifierException e){
/*
            this.withCategory(category)
                    .withFaction(FactionsME.GONDOR.getId())
                    .withNpcData(FactionsME.GONDOR.getAllNpcDatas().get(NpcRank.SOLDIER).getFirst());
 */
        }
        return this;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);

        builder.add(CATEGORY, "");
        builder.add(FACTION_ID, "");
        builder.add(NPC_DATA_ID, "");
        builder.add(TEXTURE_DATA, new NpcEntityTextureData());
        builder.add(STRUCTURE_MANAGER_HOST_POS, getBlockPos());
    }
    @Override
    public void writeData(WriteView view) {
        super.writeData(view);
        view.put("StructureManagerHostPos", BlockPos.CODEC, dataTracker.get(STRUCTURE_MANAGER_HOST_POS));
        view.put("EntityCategory", Codec.STRING, dataTracker.get(CATEGORY));
        view.put("FactionId", Codec.STRING, dataTracker.get(FACTION_ID));
        view.put("NpcDataId", Codec.STRING, dataTracker.get(NPC_DATA_ID));

        //view.put("NpcTextureData", NpcTextureData.CODEC, dataTracker.get(TEXTURE_DATA));
    }

    @Override
    public void readData(ReadView view) {
        super.readData(view);
        if(view.read("StructureManagerHostPos", BlockPos.CODEC).isPresent())
            this.dataTracker.set(STRUCTURE_MANAGER_HOST_POS, view.read("StructureManagerHostPos", BlockPos.CODEC).get());
        this.dataTracker.set(CATEGORY, view.read("EntityCategory", Codec.STRING).get());
        this.dataTracker.set(FACTION_ID, view.read("FactionId", Codec.STRING).get());
        this.dataTracker.set(NPC_DATA_ID, view.read("NpcDataId", Codec.STRING).get());

        //this.dataTracker.set("NpcTextureData", NpcTextureData.CODEC, dataTracker.get(TEXTURE_DATA));
    }

    @Override
    protected void mobTick(ServerWorld world) {
        Profiler profiler = Profilers.get();
        profiler.push("npcBrain");
        this.getBrain().tick(world, this);
        profiler.pop();
        profiler.push("npcActivityUpdate");
        NpcBrain.updateActivities(this);
        profiler.pop();
        super.mobTick(world);
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return NpcBrain.create(this, dynamic);
    }

    public Brain<NpcEntity> getBrain() {
        return (Brain<NpcEntity>)super.getBrain();
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData);

        return entityData;
    }

    @Override
    protected void initEquipment(net.minecraft.util.math.random.Random random, LocalDifficulty localDifficulty) {
        // TODO : add stuff here
    }

    @Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.putString("faction_id", (this.factionCache == null) ? null : this.factionCache.getId().toString());
        view.putString("npc_data_id", (this.npcDataCache == null) ? null : this.npcDataCache.getId().toString());
        view.putString("npc_category", this.getNpcCategory().name());
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        this.setFaction(Identifier.of(view.getString("faction_id", null)));
        this.setNpcDataId(Identifier.of(view.getString("npc_data_id", null)));
        this.setNpcCategory(EntityCategory.valueOf(view.getString("npc_category", EntityCategory.MALE.name())));
    }

    public void setFaction(Identifier id){
        if(id == null)
            return;
        try{
            this.factionCache = FactionLookup.getFactionById(getWorld(), id);
        } catch (FactionIdentifierException e) {
            // Default faction?
        }
    }

    private NpcEntityTextureData generateSkinTextureData(NpcEntityTextureData npcTextureData, NpcTextureData.Identity textureIdentity) {
        Identifier materialId = NpcTextureData.getRawMaterial(textureIdentity, NpcTextureType.SKIN);
        Identifier bodyPatternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.BODY);
        Identifier headPatternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.HEAD);
        Identifier earPatternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.EAR);
        Identifier nosePatternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.NOSE);
        Identifier scarPatternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.SCAR);

        npcTextureData = npcTextureData.withSkinTexture(NpcTextureData.buildId(bodyPatternId, materialId));
        npcTextureData = npcTextureData.withHeadTexture(NpcTextureData.buildId(headPatternId, materialId));

        if(scarPatternId != null){
            npcTextureData = npcTextureData.withScarTexture(NpcTextureData.buildId(scarPatternId, materialId));
        }
        if(earPatternId != null){
            npcTextureData = npcTextureData.withEarTexture(NpcTextureData.buildId(earPatternId, materialId));
        }
        if(nosePatternId != null){
            npcTextureData = npcTextureData.withNoseTexture(NpcTextureData.buildId(nosePatternId, materialId));
        }

        return npcTextureData;
    }

    private NpcEntityTextureData generateEyeTextureData(NpcEntityTextureData npcEntityTextureData, NpcTextureData.Identity textureIdentity, boolean haveEmissiveEyes) {
        Identifier materialId = NpcTextureData.getRawMaterial(textureIdentity, NpcTextureType.EYE);
        Identifier patternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.EYE);

        npcEntityTextureData = npcEntityTextureData.withEyeTexture(NpcTextureData.buildId(patternId, materialId), haveEmissiveEyes);

        return npcEntityTextureData;
    }

    private NpcEntityTextureData generateHairTextureData(NpcEntityTextureData npcEntityTextureData, NpcTextureData.Identity textureIdentity, DynamicRegistryManager manager) {
        Identifier globalHairMaterialId = NpcTextureData.getRawMaterial(textureIdentity, NpcTextureType.HAIR);

        // Hair
        Identifier hairPatternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.HAIR);
        Optional<RegistryEntry.Reference<NpcTexturePattern>> foundHairPattern = NpcTexturePatternsME.get(manager, NpcTextureType.HAIR, hairPatternId);
        if(foundHairPattern.isPresent() && foundHairPattern.get().value() instanceof NpcTexturePattern pattern){
            npcEntityTextureData = npcEntityTextureData.withHairTexture(NpcTextureData.buildId(hairPatternId, globalHairMaterialId));
            if(pattern.hasAddonRawValue()){
                npcEntityTextureData = npcEntityTextureData.withHairAddonTexture(NpcTextureData.buildAddonId(hairPatternId, globalHairMaterialId));
            }
        }
        // Eyebrow
        Identifier eyebrowPatternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.EYEBROW);
        Optional<RegistryEntry.Reference<NpcTexturePattern>> foundEyebrowPattern = NpcTexturePatternsME.get(manager, NpcTextureType.EYEBROW, eyebrowPatternId);
        if(foundEyebrowPattern.isPresent()){
            npcEntityTextureData = npcEntityTextureData.withEyebrowTexture(NpcTextureData.buildId(eyebrowPatternId, globalHairMaterialId));
        }
        // Beard
        Identifier beardPatternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.BEARD);
        Optional<RegistryEntry.Reference<NpcTexturePattern>> foundBeardPattern = NpcTexturePatternsME.get(manager, NpcTextureType.BEARD, beardPatternId);
        if(foundBeardPattern.isPresent() && foundBeardPattern.get().value() instanceof NpcTexturePattern pattern){
            npcEntityTextureData = npcEntityTextureData.withBeardTexture(NpcTextureData.buildId(beardPatternId, globalHairMaterialId));
            if(pattern.hasAddonRawValue()){
                npcEntityTextureData = npcEntityTextureData.withBeardAddonTexture(NpcTextureData.buildAddonId(beardPatternId, globalHairMaterialId));
            }
        }
        return npcEntityTextureData;
    }
    private NpcEntityTextureData generateClothingTextureData(NpcEntityTextureData npcEntityTextureData, NpcTextureData.Identity textureIdentity) {
        npcEntityTextureData = npcEntityTextureData.withClothingTexture(NpcTextureData.getTextureWithMaterial(textureIdentity, NpcTextureType.CLOTHING));

        return npcEntityTextureData;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        StructureManagerBlockEntity.triggerDeathSignal(this.dataTracker.get(STRUCTURE_MANAGER_HOST_POS), this);
    }

    @Override
    public boolean isPersistent() {
        return getWorld().getBlockEntity(getStructureManagerHostPos()) != null;
    }

    public void setFactionId(Identifier factionId) {
        try{
            this.factionCache = FactionLookup.getFactionById(getWorld(), factionId);
            this.dataTracker.set(FACTION_ID, factionId.toString());
        } catch (FactionIdentifierException e) {
            this.factionCache = null;
        }
    }
    public Identifier getFactionId()
    {
        return Identifier.of(this.dataTracker.get(FACTION_ID));
    }

    public void setNpcDataId(Identifier npcDataId) {
        this.dataTracker.set(NPC_DATA_ID, npcDataId.toString());
        if(npcDataId != null)
            this.npcDataCache = NpcDataLookup.getNpcData(getWorld(), npcDataId);
    }
    public Identifier getNpcDataId()
    {
        return Identifier.of(this.dataTracker.get(NPC_DATA_ID));
    }
    public void setNpcCategory(EntityCategory entityCategory) {
        this.dataTracker.set(CATEGORY, entityCategory.name());
    }
    public EntityCategory getNpcCategory()
    {
        return EntityCategory.valueOf(this.dataTracker.get(CATEGORY));
    }

    public void setNpcTextureData(NpcEntityTextureData npcEntityTextureData) {
        this.dataTracker.set(TEXTURE_DATA, npcEntityTextureData);
    }
    public void setStructureManagerHost(BlockPos blockPos) {
        this.dataTracker.set(STRUCTURE_MANAGER_HOST_POS, blockPos);
    }
    public BlockPos getStructureManagerHostPos() {
        return this.dataTracker.get(STRUCTURE_MANAGER_HOST_POS);
    }

    public NpcEntityTextureData getNpcTextureData() {
        return this.dataTracker.get(TEXTURE_DATA);
    }

    static {
        FACTION_ID = DataTracker.registerData(NpcEntity.class, ModTrackedDataHandlerRegistry.FACTION_ID);
        NPC_DATA_ID = DataTracker.registerData(NpcEntity.class, ModTrackedDataHandlerRegistry.NPC_DATA_ID);
        CATEGORY = DataTracker.registerData(NpcEntity.class, ModTrackedDataHandlerRegistry.CATEGORY);
        TEXTURE_DATA = DataTracker.registerData(NpcEntity.class, ModTrackedDataHandlerRegistry.NPC_ENTITY_TEXTURE_DATA);
        STRUCTURE_MANAGER_HOST_POS = DataTracker.registerData(NpcEntity.class, ModTrackedDataHandlerRegistry.STRUCTURE_MANAGER_HOST_POS);
    }
    // endregion


    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createLivingAttributes()
                .add(EntityAttributes.MAX_HEALTH, 8.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.FOLLOW_RANGE, 35.0);
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
}
