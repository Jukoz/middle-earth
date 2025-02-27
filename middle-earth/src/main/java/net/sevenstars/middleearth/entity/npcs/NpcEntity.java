package net.sevenstars.middleearth.entity.npcs;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.npcs.features.hair.HairTypes;
import net.sevenstars.middleearth.resources.MiddleEarthNpcTexturePatterns;
import net.sevenstars.middleearth.resources.MiddleEarthRaces;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.data.NpcTextureData;
import net.sevenstars.middleearth.resources.datas.races.data.NpcTextureDataCategory;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTexturePattern;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;
import net.sevenstars.middleearth.utils.IdentifierUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;


public class NpcEntity extends PassiveEntity {
    private static final TrackedData<Byte> BEARD_TYPE; // 0 = none
    //private static final TrackedData<String> RACE;
    private static final TrackedData<String> SKIN_TEXTURE;
    private static final TrackedData<String> EYE_TEXTURE;
    private static final TrackedData<String> EYEBROW_TEXTURE;
    private static final TrackedData<String> HAIR_TEXTURE;
    private static final TrackedData<String> HAIR_ADDON;
    private static final TrackedData<String> BEARD_TEXTURE;
    private static final TrackedData<String> BEARD_ADDON;
    private static final TrackedData<String> CLOTHING_TEXTURE;
    private static final TrackedData<Boolean> EMISSIVE_EYES;

    private Race race;

    public NpcEntity(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);


        if(world.isClient)
            return;

        DynamicRegistryManager manager = world.getRegistryManager();

        this.race = manager.getOrThrow(MiddleEarthRaces.KEY).get(MiddleEarthRaces.DWARF.getId());

        NpcTextureDataCategory category = (new Random()).nextBoolean() ? NpcTextureDataCategory.MALE : NpcTextureDataCategory.FEMALE;



        NpcTextureData npcTextureData = this.race.getNpcTextureDataValue();
        NpcTextureData.Identity npcTextureDataIdentity = NpcTextureData.Identity.create(npcTextureData, category);

        if(npcTextureDataIdentity == null){
            // TODO : crash?
            return;
        }

        if(getSkinTextureIdentifier() == null){
            Identifier id = npcTextureData.getTextureWithMaterial(npcTextureDataIdentity, NpcTextureType.SKIN);
            this.dataTracker.set(SKIN_TEXTURE, id.toString());
        }
        if(getEyeTextureIdentifier() == null){
            Identifier id = npcTextureData.getTextureWithMaterial(npcTextureDataIdentity, NpcTextureType.EYE);
            this.dataTracker.set(EYE_TEXTURE, id.toString());
            this.dataTracker.set(EMISSIVE_EYES, npcTextureData.haveEmissiveEyes(npcTextureDataIdentity));
        }
        if(getHairTextureIdentifier() == null || getEyebrowTextureIdentifier() == null){
            this.dataTracker.set(HAIR_TEXTURE, "");
            this.dataTracker.set(HAIR_ADDON, "");
            this.dataTracker.set(EYEBROW_TEXTURE, "");
            this.dataTracker.set(BEARD_TEXTURE, "");
            this.dataTracker.set(BEARD_ADDON, "");

            // Resets hair
            Identifier globalHairMaterialId = npcTextureData.getRawMaterial(npcTextureDataIdentity, NpcTextureType.HAIR);

            Identifier hairPatternId = npcTextureData.getRawPattern(npcTextureDataIdentity, NpcTextureType.HAIR);
            Identifier eyebrowPatternId = npcTextureData.getRawPattern(npcTextureDataIdentity, NpcTextureType.EYEBROW);
            Identifier beardPatternId = npcTextureData.getRawPattern(npcTextureDataIdentity, NpcTextureType.BEARD);


            if(globalHairMaterialId == null || hairPatternId == null || eyebrowPatternId == null){
                // TODO : Exception management
                return;
            }

            // Hair
            Optional<RegistryEntry.Reference<NpcTexturePattern>> foundHairPattern = MiddleEarthNpcTexturePatterns.get(manager, NpcTextureType.HAIR, hairPatternId);
            if(foundHairPattern.isPresent() && foundHairPattern.get().value() instanceof NpcTexturePattern pattern){
                Identifier hairTexturePattern = IdentifierUtil.create(hairPatternId.getPath() + "_" + globalHairMaterialId.getPath());

                this.dataTracker.set(HAIR_TEXTURE, hairTexturePattern.toString());
                if(pattern.hasAddonRawValue()){
                    Identifier addonId = IdentifierUtil.create(hairPatternId.getPath()  + "_addon_" + globalHairMaterialId.getPath());
                    this.dataTracker.set(HAIR_ADDON, addonId.toString());
                } else {
                    this.dataTracker.set(HAIR_ADDON, "");
                }
            } else {
                this.dataTracker.set(HAIR_TEXTURE, "");
                this.dataTracker.set(HAIR_ADDON, "");
            }
            // Eyebrows
            Optional<RegistryEntry.Reference<NpcTexturePattern>> foundEyebrowPattern = MiddleEarthNpcTexturePatterns.get(manager, NpcTextureType.EYEBROW, eyebrowPatternId);
            if(foundEyebrowPattern.isPresent()){
                Identifier eyebrowTexturePattern = IdentifierUtil.create(eyebrowPatternId.getPath() + "_" + globalHairMaterialId.getPath());
                this.dataTracker.set(EYEBROW_TEXTURE, eyebrowTexturePattern.toString());
            } else {
                this.dataTracker.set(EYEBROW_TEXTURE, "");
            }
            // Beard
            Optional<RegistryEntry.Reference<NpcTexturePattern>> foundBeardPattern = MiddleEarthNpcTexturePatterns.get(manager, NpcTextureType.BEARD, beardPatternId);
            if(foundBeardPattern.isPresent() && foundBeardPattern.get().value() instanceof NpcTexturePattern pattern){
                Identifier beardTexturePattern = IdentifierUtil.create(beardPatternId.getPath() + "_" + globalHairMaterialId.getPath());

                this.dataTracker.set(BEARD_TEXTURE, beardTexturePattern.toString());
                if(pattern.hasAddonRawValue()){
                    Identifier addonId = IdentifierUtil.create(beardPatternId.getPath()  + "_addon_" + globalHairMaterialId.getPath());
                    this.dataTracker.set(BEARD_ADDON, addonId.toString());
                } else {
                    this.dataTracker.set(BEARD_ADDON, "");
                }
            } else {
                this.dataTracker.set(BEARD_TEXTURE, "");
                this.dataTracker.set(BEARD_ADDON, "");
            }
        }
        if(getClothingTextureIdentifier() == null){
            Identifier id = npcTextureData.getTextureWithMaterial(npcTextureDataIdentity, NpcTextureType.CLOTHING);
            this.dataTracker.set(CLOTHING_TEXTURE, id.toString());
        }
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
        builder.add(BEARD_TYPE, (byte)0);
       // builder.add(RACE, (race != null) ? race.getId().toString() : "");
        builder.add(SKIN_TEXTURE, "");
        builder.add(EYE_TEXTURE, "");
        builder.add(EYEBROW_TEXTURE, "");
        builder.add(HAIR_TEXTURE, "");
        builder.add(HAIR_ADDON, "");
        builder.add(BEARD_TEXTURE, "");
        builder.add(BEARD_ADDON, "");
        builder.add(CLOTHING_TEXTURE, "");
        builder.add(EMISSIVE_EYES, false);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putByte("BeardType", this.getBeardType());
        nbt.putString("SkinTexture",  this.getSkinTextureValue());
        nbt.putString("EyeTexture",  this.getEyeTextureValue());
        nbt.putString("EyebrowTexture",  this.getEyebrowTextureValue());
        nbt.putString("HairTexture",  this.getHairTextureValue());
        nbt.putString("HairAddon",  this.getHairAddon());
        nbt.putString("BeardTexture",  this.getBeardTextureValue());
        nbt.putString("BeardAddon",  this.getBeardAddon());
        nbt.putString("ClothingTexture",  this.getClothingTextureValue());
        nbt.putBoolean("EmissiveEyes",  this.getEmissiveEyes());
    }


    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(BEARD_TYPE, nbt.getByte("BeardType"));
        this.dataTracker.set(SKIN_TEXTURE, nbt.getString("SkinTexture"));
        this.dataTracker.set(EYE_TEXTURE, nbt.getString("EyeTexture"));
        this.dataTracker.set(EYEBROW_TEXTURE, nbt.getString("EyebrowTexture"));
        this.dataTracker.set(HAIR_TEXTURE, nbt.getString("HairTexture"));
        this.dataTracker.set(HAIR_ADDON, nbt.getString("HairAddon"));
        this.dataTracker.set(BEARD_TEXTURE, nbt.getString("BeardTexture"));
        this.dataTracker.set(BEARD_ADDON, nbt.getString("BeardAddon"));
        this.dataTracker.set(CLOTHING_TEXTURE, nbt.getString("ClothingTexture"));
        this.dataTracker.set(EMISSIVE_EYES, nbt.getBoolean("EmissiveEyes"));
    }

    public Byte getBeardType() {
        return this.dataTracker.get(BEARD_TYPE);
    }
    public HairTypes getBeardTypeValue() {
        return HairTypes.values()[this.dataTracker.get(BEARD_TYPE)];
    }
    public String getSkinTextureValue() {
        return this.dataTracker.get(SKIN_TEXTURE);
    }
    public String getEyeTextureValue() {
        return this.dataTracker.get(EYE_TEXTURE);
    }
    public String getEyebrowTextureValue() {
        return this.dataTracker.get(EYEBROW_TEXTURE);
    }
    public String getHairTextureValue() {
        return this.dataTracker.get(HAIR_TEXTURE);
    }
    private String getHairAddon() {
        return this.dataTracker.get(HAIR_ADDON);
    }

    public String getBeardTextureValue() {
        return this.dataTracker.get(BEARD_TEXTURE);
    }
    private String getBeardAddon() {
        return this.dataTracker.get(BEARD_ADDON);
    }
    public String getClothingTextureValue() {
        return this.dataTracker.get(CLOTHING_TEXTURE);
    }
    public Boolean getEmissiveEyes() {
        return this.dataTracker.get(EMISSIVE_EYES);
    }

    public Identifier getSkinTextureIdentifier() {
        if(Objects.equals(this.dataTracker.get(SKIN_TEXTURE), ""))
            return null;
        return IdentifierUtil.getIdentifierFromString(this.dataTracker.get(SKIN_TEXTURE));
    }
    public Identifier getEyeTextureIdentifier() {
        if(Objects.equals(this.dataTracker.get(EYE_TEXTURE), ""))
            return null;
        return IdentifierUtil.getIdentifierFromString(this.dataTracker.get(EYE_TEXTURE));
    }
    public Identifier getEyebrowTextureIdentifier() {
        if(Objects.equals(this.dataTracker.get(EYEBROW_TEXTURE), ""))
            return null;
        return IdentifierUtil.getIdentifierFromString(this.dataTracker.get(EYEBROW_TEXTURE));
    }
    public Identifier getHairTextureIdentifier() {
        if(Objects.equals(this.dataTracker.get(HAIR_TEXTURE), ""))
            return null;
        return IdentifierUtil.getIdentifierFromString(this.dataTracker.get(HAIR_TEXTURE));
    }
    public Identifier getHairAddonTextureIdentifier() {
        if(Objects.equals(this.dataTracker.get(HAIR_ADDON), "")){
            return null;
        }
        return IdentifierUtil.getIdentifierFromString(this.dataTracker.get(HAIR_ADDON));
    }

    public Identifier getBeardTextureIdentifier() {
        if(Objects.equals(this.dataTracker.get(BEARD_TEXTURE), ""))
            return null;
        return IdentifierUtil.getIdentifierFromString(this.dataTracker.get(BEARD_TEXTURE));
    }

    public Identifier getBeardAddonTextureIdentifier() {
        if(Objects.equals(this.dataTracker.get(BEARD_ADDON), "")){
            return null;
        }
        return IdentifierUtil.getIdentifierFromString(this.dataTracker.get(BEARD_ADDON));
    }

    public Identifier getClothingTextureIdentifier() {
        if(Objects.equals(this.dataTracker.get(CLOTHING_TEXTURE), ""))
            return null;
        return IdentifierUtil.getIdentifierFromString(this.dataTracker.get(CLOTHING_TEXTURE));
    }
    static {
        BEARD_TYPE = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.BYTE);
        SKIN_TEXTURE = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.STRING);
        EYE_TEXTURE = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.STRING);
        EYEBROW_TEXTURE = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.STRING);
        HAIR_TEXTURE = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.STRING);
        HAIR_ADDON = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.STRING);
        BEARD_TEXTURE = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.STRING);
        BEARD_ADDON = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.STRING);
        CLOTHING_TEXTURE = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.STRING);
        EMISSIVE_EYES = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
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
