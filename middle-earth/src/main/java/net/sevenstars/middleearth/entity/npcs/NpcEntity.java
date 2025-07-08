package net.sevenstars.middleearth.entity.npcs;

import com.mojang.serialization.DataResult;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentHolder;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.entity.ModTrackedDataHandlerRegistry;
import net.sevenstars.middleearth.entity.npcs.data.NpcEntityData;
import net.sevenstars.middleearth.entity.npcs.data.NpcEntityTextureData;
import net.sevenstars.middleearth.resources.FactionsME;
import net.sevenstars.middleearth.resources.NpcTexturePatternsME;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.NpcDataLookup;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTexturePattern;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;


public class NpcEntity extends PassiveEntity implements EquipmentHolder {
    // Data to use
    private static final TrackedData<NpcEntityData> DATA;
    private static final TrackedData<NpcEntityTextureData> TEXTURE_DATA;

    public NpcEntity(EntityType<NpcEntity> entityType, World world) {
        super(entityType, world);
        initializeData(world);
    }

    public static NpcEntity create(World world){
        var entity = new NpcEntity(ModEntities.NPC, world);

        entity.initializeData(world);
        return entity;
    }

    private void initializeData(World world) {
        DynamicRegistryManager manager = world.getRegistryManager();

        var generatedData = generateNpcData(manager);
        setNpcData(generatedData);

        var npcData = generatedData.getNpcDataValue(world);
        var race = generatedData.getRaceValue(world);

        NpcTextureData.Identity textureIdentity = NpcTextureData.Identity.create(npcData.getNpcTextureData(), generatedData.category);

        NpcEntityTextureData generatedTextureData = generateSkinTextureData(new NpcEntityTextureData(), textureIdentity);
        generatedTextureData = generateEyeTextureData(generatedTextureData, textureIdentity, npcData.getNpcTextureData().haveEmissiveEyes(textureIdentity));
        generatedTextureData = generateHairTextureData(generatedTextureData, textureIdentity, manager);
        generatedTextureData = generateClothingTextureData(generatedTextureData, textureIdentity);

        setNpcTextureData(generatedTextureData);

        race.applyNpcAttributes(this);
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

    private NpcEntityData generateNpcData(DynamicRegistryManager manager) {
        Faction faction = (random.nextInt(2) == 1) ? FactionsME.GONDOR : FactionsME.MORDOR;
        var civilianNpcDatas = NpcDataLookup.getAllNpcDatas(getWorld(), faction.getAllNpcDatas().get(NpcRank.MILITIA));
        return generateNpcData(manager, faction.getId(), civilianNpcDatas.get(random.nextInt(civilianNpcDatas.size())));
    }
    public NpcEntityData generateNpcData(DynamicRegistryManager manager, Identifier factionId, NpcData npcData) {
        Faction chosenFaction = manager.getOrThrow(FactionsME.KEY).get(factionId);
        if(chosenFaction == null)
            return null;

        npcData.applyAttributes(this);

        EntityCategory category = (new Random()).nextBoolean() ? EntityCategory.MALE : EntityCategory.FEMALE;

        return new NpcEntityData(chosenFaction.getId(), npcData.getId(), category);
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


    // region NBT & DataTrackers
    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);

        builder.add(DATA, new NpcEntityData());
        builder.add(TEXTURE_DATA, new NpcEntityTextureData());
    }

    //TODO crab to fix
    /*@Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        DataResult<NbtElement> npcData = NpcEntityData.CODEC.encodeStart(NbtOps.INSTANCE, this.getNpcData());
        if(npcData.isSuccess()){
            view.put("NpcData", npcData.getOrThrow());
        }

        DataResult<NbtElement> npcTextureData = NpcEntityTextureData.CODEC.encodeStart(NbtOps.INSTANCE, this.getNpcTextureData());
        if(npcTextureData.isSuccess()){
            view.put("NpcTextureData", npcTextureData.getOrThrow());
        }
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        if (view.contains("NpcData")) {
            DataResult<NpcEntityData> dataResult = NpcEntityData.CODEC.parse(NbtOps.INSTANCE, view.get("NpcData"));
            if(dataResult.isSuccess()){
                setNpcData(dataResult.getOrThrow());
            }
        }
        if (view.contains("NpcTextureData")) {
            DataResult<NpcEntityTextureData> dataResult = NpcEntityTextureData.CODEC.parse(NbtOps.INSTANCE, view.get("NpcTextureData"));
            if(dataResult.isSuccess()){
                setNpcTextureData(dataResult.getOrThrow());
            }
        }
    }*/

    public void setNpcData(NpcEntityData npcEntityData) {
        this.dataTracker.set(DATA, npcEntityData);
    }

    public NpcEntityData getNpcData() {
        return this.dataTracker.get(DATA);
    }

    public void setNpcTextureData(NpcEntityTextureData npcEntityTextureData) {
        this.dataTracker.set(TEXTURE_DATA, npcEntityTextureData);
    }

    public NpcEntityTextureData getNpcTextureData() {
        return this.dataTracker.get(TEXTURE_DATA);
    }

    static {
        DATA = DataTracker.registerData(NpcEntity.class, ModTrackedDataHandlerRegistry.NPC_ENTITY_DATA);
        TEXTURE_DATA = DataTracker.registerData(NpcEntity.class, ModTrackedDataHandlerRegistry.NPC_ENTITY_TEXTURE_DATA);
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
}
