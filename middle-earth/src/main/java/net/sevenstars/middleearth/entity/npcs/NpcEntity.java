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
import net.minecraft.util.Identifier;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.entity.ModTrackedDataHandlerRegistry;
import net.sevenstars.middleearth.entity.npcs.data.NpcData;
import net.sevenstars.middleearth.resources.FactionsME;
import net.sevenstars.middleearth.resources.NpcTexturePatternsME;
import net.sevenstars.middleearth.resources.RacesME;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTexturePattern;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Random;


public class NpcEntity extends PassiveEntity implements EquipmentHolder {
    // Data to use
    private static final TrackedData<NpcData> DATA;
    private static final TrackedData<net.sevenstars.middleearth.entity.npcs.data.NpcTextureData> TEXTURE_DATA;

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

        var race = generatedData.getRaceValue(world);
        NpcTextureData.Identity textureIdentity = NpcTextureData.Identity.create(race.getRaceTextureData(), generatedData.category);

        net.sevenstars.middleearth.entity.npcs.data.NpcTextureData generatedTextureData = generateSkinTextureData(new net.sevenstars.middleearth.entity.npcs.data.NpcTextureData(), textureIdentity);
        generatedTextureData = generateEyeTextureData(generatedTextureData, textureIdentity, race.getRaceTextureData().haveEmissiveEyes(textureIdentity));
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

    private NpcData generateNpcData(DynamicRegistryManager manager) {
        var factions = List.of(
                FactionsME.GONDOR,
                FactionsME.LONGBEARDS_EREBOR,
                FactionsME.SHIRE,
                FactionsME.MORDOR
        );

        Faction faction = factions.get(random.nextBetween(0, factions.size() - 1));
        faction = manager.getOrThrow(FactionsME.KEY).get(faction.getId());
        List<Race> races = faction.getRaces(getWorld());
        Race randomRace = races.get(random.nextBetween(0, races.size()-1));
        Identifier raceId = manager.getOrThrow(RacesME.KEY).getEntry(randomRace).value().getId();
        return generateNpcData(manager, faction.getId(), raceId);
    }
    public NpcData generateNpcData(DynamicRegistryManager manager, Identifier factionId, Identifier raceId) {
        Faction chosenFaction = manager.getOrThrow(FactionsME.KEY).get(factionId);
        if(chosenFaction == null)
            return null;

        Race chosenRace = manager.getOrThrow(RacesME.KEY).get(raceId);
        if(chosenRace == null)
            return null;

        EntityCategory category = (new Random()).nextBoolean() ? EntityCategory.MALE : EntityCategory.FEMALE;

        return new NpcData(chosenFaction.getId(), chosenRace.getId(), category);
    }

    private net.sevenstars.middleearth.entity.npcs.data.NpcTextureData generateSkinTextureData(net.sevenstars.middleearth.entity.npcs.data.NpcTextureData npcTextureData, NpcTextureData.Identity textureIdentity) {
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

    private net.sevenstars.middleearth.entity.npcs.data.NpcTextureData generateEyeTextureData(net.sevenstars.middleearth.entity.npcs.data.NpcTextureData npcTextureData, NpcTextureData.Identity textureIdentity, boolean haveEmissiveEyes) {
        Identifier materialId = NpcTextureData.getRawMaterial(textureIdentity, NpcTextureType.EYE);
        Identifier patternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.EYE);

        npcTextureData = npcTextureData.withEyeTexture(NpcTextureData.buildId(patternId, materialId), haveEmissiveEyes);

        return npcTextureData;
    }

    private net.sevenstars.middleearth.entity.npcs.data.NpcTextureData generateHairTextureData(net.sevenstars.middleearth.entity.npcs.data.NpcTextureData npcTextureData, NpcTextureData.Identity textureIdentity, DynamicRegistryManager manager) {
        Identifier globalHairMaterialId = NpcTextureData.getRawMaterial(textureIdentity, NpcTextureType.HAIR);

        // Hair
        Identifier hairPatternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.HAIR);
        Optional<RegistryEntry.Reference<NpcTexturePattern>> foundHairPattern = NpcTexturePatternsME.get(manager, NpcTextureType.HAIR, hairPatternId);
        if(foundHairPattern.isPresent() && foundHairPattern.get().value() instanceof NpcTexturePattern pattern){
            npcTextureData = npcTextureData.withHairTexture(NpcTextureData.buildId(hairPatternId, globalHairMaterialId));
            if(pattern.hasAddonRawValue()){
                npcTextureData = npcTextureData.withHairAddonTexture(NpcTextureData.buildAddonId(hairPatternId, globalHairMaterialId));
            }
        }
        // Eyebrow
        Identifier eyebrowPatternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.EYEBROW);
        Optional<RegistryEntry.Reference<NpcTexturePattern>> foundEyebrowPattern = NpcTexturePatternsME.get(manager, NpcTextureType.EYEBROW, eyebrowPatternId);
        if(foundEyebrowPattern.isPresent()){
            npcTextureData = npcTextureData.withEyebrowTexture(NpcTextureData.buildId(eyebrowPatternId, globalHairMaterialId));
        }
        // Beard
        Identifier beardPatternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.BEARD);
        Optional<RegistryEntry.Reference<NpcTexturePattern>> foundBeardPattern = NpcTexturePatternsME.get(manager, NpcTextureType.BEARD, beardPatternId);
        if(foundBeardPattern.isPresent() && foundBeardPattern.get().value() instanceof NpcTexturePattern pattern){
            npcTextureData = npcTextureData.withBeardTexture(NpcTextureData.buildId(beardPatternId, globalHairMaterialId));
            if(pattern.hasAddonRawValue()){
                npcTextureData = npcTextureData.withBeardAddonTexture(NpcTextureData.buildAddonId(beardPatternId, globalHairMaterialId));
            }
        }
        return npcTextureData;
    }
    private net.sevenstars.middleearth.entity.npcs.data.NpcTextureData generateClothingTextureData(net.sevenstars.middleearth.entity.npcs.data.NpcTextureData npcTextureData, NpcTextureData.Identity textureIdentity) {
        npcTextureData = npcTextureData.withClothingTexture(NpcTextureData.getTextureWithMaterial(textureIdentity, NpcTextureType.CLOTHING));

        return npcTextureData;
    }


    // region NBT & DataTrackers
    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);

        builder.add(DATA, new NpcData());
        builder.add(TEXTURE_DATA, new net.sevenstars.middleearth.entity.npcs.data.NpcTextureData());

    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        DataResult<NbtElement> npcData = NpcData.CODEC.encodeStart(NbtOps.INSTANCE, this.getNpcData());
        if(npcData.isSuccess()){
            nbt.put("NpcData", npcData.getOrThrow());
        }

        DataResult<NbtElement> npcTextureData = net.sevenstars.middleearth.entity.npcs.data.NpcTextureData.CODEC.encodeStart(NbtOps.INSTANCE, this.getNpcTextureData());
        if(npcTextureData.isSuccess()){
            nbt.put("NpcTextureData", npcTextureData.getOrThrow());
        }
    }


    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("NpcData")) {
            DataResult<NpcData> dataResult = NpcData.CODEC.parse(NbtOps.INSTANCE, nbt.get("NpcData"));
            if(dataResult.isSuccess()){
                setNpcData(dataResult.getOrThrow());
            }
        }
        if (nbt.contains("NpcTextureData")) {
            DataResult<net.sevenstars.middleearth.entity.npcs.data.NpcTextureData> dataResult = net.sevenstars.middleearth.entity.npcs.data.NpcTextureData.CODEC.parse(NbtOps.INSTANCE, nbt.get("NpcTextureData"));
            if(dataResult.isSuccess()){
                setNpcTextureData(dataResult.getOrThrow());
            }
        }
    }

    public void setNpcData(NpcData npcData) {
        this.dataTracker.set(DATA, npcData);
    }

    public NpcData getNpcData() {
        return this.dataTracker.get(DATA);
    }

    public void setNpcTextureData(net.sevenstars.middleearth.entity.npcs.data.NpcTextureData npcTextureData) {
        this.dataTracker.set(TEXTURE_DATA, npcTextureData);
    }

    public net.sevenstars.middleearth.entity.npcs.data.NpcTextureData getNpcTextureData() {
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
