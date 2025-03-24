package net.sevenstars.middleearth.entity.npcs;

import com.mojang.serialization.DataResult;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
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
import net.sevenstars.middleearth.entity.ModTrackedDataHandlerRegistry;
import net.sevenstars.middleearth.entity.npcs.data.NpcData;
import net.sevenstars.middleearth.entity.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.resources.MiddleEarthFactions;
import net.sevenstars.middleearth.resources.MiddleEarthNpcTexturePatterns;
import net.sevenstars.middleearth.resources.MiddleEarthRaces;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;
import net.sevenstars.middleearth.resources.datas.races.data.RaceTextureData;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTexturePattern;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Random;


public class NpcEntity extends PassiveEntity {
    // Data to use
    private static final TrackedData<NpcData> DATA;
    private static final TrackedData<NpcTextureData> TEXTURE_DATA;

    public NpcEntity(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        DynamicRegistryManager manager = world.getRegistryManager();

        var generatedData = generateNpcData(manager);
        setNpcData(generatedData);

        var race = generatedData.getRaceValue(world);
        RaceTextureData.Identity textureIdentity = RaceTextureData.Identity.create(race.getRaceTextureData(), generatedData.category);

        NpcTextureData generatedTextureData = generateSkinTextureData(new NpcTextureData(), textureIdentity);
        generatedTextureData = generateEyeTextureData(generatedTextureData, textureIdentity, race.getRaceTextureData().haveEmissiveEyes(textureIdentity));
        generatedTextureData = generateHairTextureData(generatedTextureData, textureIdentity, manager);
        generatedTextureData = generateClothingTextureData(generatedTextureData, textureIdentity);

        setNpcTextureData(generatedTextureData);

        race.applyNpcAttributes(this);

        return super.initialize(world, difficulty, spawnReason, entityData);
    }
    private NpcData generateNpcData(DynamicRegistryManager manager) {
        var factions = List.of(
                MiddleEarthFactions.GONDOR,
                MiddleEarthFactions.LONGBEARDS_EREBOR,
                MiddleEarthFactions.SHIRE,
                MiddleEarthFactions.MORDOR
        );

        Faction faction = factions.get(random.nextBetween(0, factions.size() - 1));
        faction = manager.getOrThrow(MiddleEarthFactions.KEY).get(faction.getId());
        Race race = manager.getOrThrow(MiddleEarthRaces.KEY).get(faction.getRaces(getWorld()).get(random.nextBetween(0, faction.getRaces(getWorld()).size() - 1)).getId());
        return generateNpcData(manager, faction.getId(), race.getId());
    }
    private NpcData generateNpcData(DynamicRegistryManager manager, Identifier factionId, Identifier raceId) {
        Faction chosenFaction = manager.getOrThrow(MiddleEarthFactions.KEY).get(factionId);
        if(chosenFaction == null)
            return null;

        Race chosenRace = manager.getOrThrow(MiddleEarthRaces.KEY).get(raceId);
        if(chosenRace == null)
            return null;

        EntityCategory category = (new Random()).nextBoolean() ? EntityCategory.MALE : EntityCategory.FEMALE;

        return new NpcData(chosenFaction.getId(), chosenRace.getId(), category);
    }

    private NpcTextureData generateSkinTextureData(NpcTextureData npcTextureData, RaceTextureData.Identity textureIdentity) {
        Identifier materialId = RaceTextureData.getRawMaterial(textureIdentity, NpcTextureType.SKIN);

        Identifier skinPatternId = RaceTextureData.getRawPattern(textureIdentity, NpcTextureType.SKIN);
        Identifier earPatternId = RaceTextureData.getRawPattern(textureIdentity, NpcTextureType.EAR);
        Identifier nosePatternId = RaceTextureData.getRawPattern(textureIdentity, NpcTextureType.NOSE);

        npcTextureData = npcTextureData.withSkinTexture(RaceTextureData.buildId(skinPatternId, materialId));

        if(earPatternId != null){
            npcTextureData = npcTextureData.withEarTexture(RaceTextureData.buildId(earPatternId, materialId));
        }
        if(nosePatternId != null){
            npcTextureData = npcTextureData.withNoseTexture(RaceTextureData.buildId(nosePatternId, materialId));
        }

        return npcTextureData;
    }

    private NpcTextureData generateEyeTextureData(NpcTextureData npcTextureData, RaceTextureData.Identity textureIdentity, boolean haveEmissiveEyes) {
        Identifier materialId = RaceTextureData.getRawMaterial(textureIdentity, NpcTextureType.EYE);
        Identifier patternId = RaceTextureData.getRawPattern(textureIdentity, NpcTextureType.EYE);

        npcTextureData = npcTextureData.withEyeTexture(RaceTextureData.buildId(patternId, materialId), haveEmissiveEyes);

        return npcTextureData;
    }

    private NpcTextureData generateHairTextureData(NpcTextureData npcTextureData, RaceTextureData.Identity textureIdentity, DynamicRegistryManager manager) {
        Identifier globalHairMaterialId = RaceTextureData.getRawMaterial(textureIdentity, NpcTextureType.HAIR);

        // Hair
        Identifier hairPatternId = RaceTextureData.getRawPattern(textureIdentity, NpcTextureType.HAIR);
        Optional<RegistryEntry.Reference<NpcTexturePattern>> foundHairPattern = MiddleEarthNpcTexturePatterns.get(manager, NpcTextureType.HAIR, hairPatternId);
        if(foundHairPattern.isPresent() && foundHairPattern.get().value() instanceof NpcTexturePattern pattern){
            npcTextureData = npcTextureData.withHairTexture(RaceTextureData.buildId(hairPatternId, globalHairMaterialId));
            if(pattern.hasAddonRawValue()){
                npcTextureData = npcTextureData.withHairAddonTexture(RaceTextureData.buildAddonId(hairPatternId, globalHairMaterialId));
            }
        }
        // Eyebrow
        Identifier eyebrowPatternId = RaceTextureData.getRawPattern(textureIdentity, NpcTextureType.EYEBROW);
        Optional<RegistryEntry.Reference<NpcTexturePattern>> foundEyebrowPattern = MiddleEarthNpcTexturePatterns.get(manager, NpcTextureType.EYEBROW, eyebrowPatternId);
        if(foundEyebrowPattern.isPresent()){
            npcTextureData = npcTextureData.withEyebrowTexture(RaceTextureData.buildId(eyebrowPatternId, globalHairMaterialId));
        }
        // Beard
        Identifier beardPatternId = RaceTextureData.getRawPattern(textureIdentity, NpcTextureType.BEARD);
        Optional<RegistryEntry.Reference<NpcTexturePattern>> foundBeardPattern = MiddleEarthNpcTexturePatterns.get(manager, NpcTextureType.BEARD, beardPatternId);
        if(foundBeardPattern.isPresent() && foundBeardPattern.get().value() instanceof NpcTexturePattern pattern){
            npcTextureData = npcTextureData.withBeardTexture(RaceTextureData.buildId(beardPatternId, globalHairMaterialId));
            if(pattern.hasAddonRawValue()){
                npcTextureData = npcTextureData.withBeardAddonTexture(RaceTextureData.buildAddonId(beardPatternId, globalHairMaterialId));
            }
        }
        return npcTextureData;
    }
    private NpcTextureData generateClothingTextureData(NpcTextureData npcTextureData, RaceTextureData.Identity textureIdentity) {
        npcTextureData = npcTextureData.withClothingTexture(RaceTextureData.getTextureWithMaterial(textureIdentity, NpcTextureType.CLOTHING));

        return npcTextureData;
    }


    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new LookAtEntityGoal(this, PlayerEntity.class, 15.0f));
    }

    // region NBT & DataTrackers
    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);

        builder.add(DATA, new NpcData());
        builder.add(TEXTURE_DATA, new NpcTextureData());
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        DataResult<NbtElement> npcData = NpcData.CODEC.encodeStart(NbtOps.INSTANCE, this.getNpcData());
        if(npcData.isSuccess()){
            nbt.put("NpcData", npcData.getOrThrow());
        }

        DataResult<NbtElement> npcTextureData = NpcTextureData.CODEC.encodeStart(NbtOps.INSTANCE, this.getNpcTextureData());
        if(npcTextureData.isSuccess()){
            nbt.put("NpcTextureData", npcTextureData.getOrThrow());
        }
    }


    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("NpcData", 10)) {
            DataResult<NpcData> dataResult = NpcData.CODEC.parse(NbtOps.INSTANCE, nbt.get("NpcData"));
            if(dataResult.isSuccess()){
                setNpcData(dataResult.getOrThrow());
            }
        }
        if (nbt.contains("NpcTextureData", 10)) {
            DataResult<NpcTextureData> dataResult = NpcTextureData.CODEC.parse(NbtOps.INSTANCE, nbt.get("NpcTextureData"));
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

    public void setNpcTextureData(NpcTextureData npcTextureData) {
        this.dataTracker.set(TEXTURE_DATA, npcTextureData);
    }

    public NpcTextureData getNpcTextureData() {
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
}
