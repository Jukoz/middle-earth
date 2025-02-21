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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.npcs.features.beards.BeardTypes;
import net.sevenstars.middleearth.resources.MiddleEarthRaces;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.data.NpcTextureData;
import net.sevenstars.middleearth.resources.datas.races.data.NpcTextureDataCategory;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;
import net.sevenstars.middleearth.utils.IdentifierUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


public class NpcEntity extends PassiveEntity {
    private static final TrackedData<Byte> BEARD_TYPE; // 0 = none
    //private static final TrackedData<String> RACE;
    private static final TrackedData<String> SKIN_TEXTURE;
    private static final TrackedData<String> EYE_TEXTURE;
    private static final TrackedData<String> HAIR_TEXTURE;
    private static final TrackedData<String> CLOTHING_TEXTURE;
    private static final TrackedData<Boolean> EMISSIVE_EYES;

    private Race race;

    public NpcEntity(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);

        this.race = MiddleEarthRaces.DWARF;

        NpcTextureData.Identity npcTextureDataIdentity = NpcTextureData.Identity.create(this.race.getNpcTextureDataValue(), NpcTextureDataCategory.COMMON);
        if(npcTextureDataIdentity == null){
            // TODO : crash?
            return;
        }

        NpcTextureData npcTextureData = this.race.getNpcTextureDataValue();

        if(Objects.equals(getSkinTextureValue(), "")){
            Identifier id = npcTextureData.getTexture(npcTextureDataIdentity, NpcTextureType.SKIN);
            this.dataTracker.set(SKIN_TEXTURE, id.toString());
        }
        if(Objects.equals(getEyeTextureValue(), "")){
            Identifier id = npcTextureData.getTexture(npcTextureDataIdentity, NpcTextureType.EYE);
            this.dataTracker.set(EYE_TEXTURE, id.toString());
            this.dataTracker.set(EMISSIVE_EYES, npcTextureData.haveEmissiveEyes(npcTextureDataIdentity));
        }
        if(Objects.equals(getHairTextureValue(), "")){
            Identifier id = npcTextureData.getTexture(npcTextureDataIdentity, NpcTextureType.HAIR);
            this.dataTracker.set(HAIR_TEXTURE, id.toString());
        }
        if(Objects.equals(getClothingTextureValue(), "")){
            Identifier id = npcTextureData.getTexture(npcTextureDataIdentity, NpcTextureType.CLOTHING);
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
        builder.add(HAIR_TEXTURE, "");
        builder.add(CLOTHING_TEXTURE, "");
        builder.add(EMISSIVE_EYES, false);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putByte("BeardType", this.getBeardType());
        nbt.putString("SkinTexture",  this.getSkinTextureValue());
        nbt.putString("EyeTexture",  this.getEyeTextureValue());
        nbt.putString("HairTexture",  this.getHairTextureValue());
        nbt.putString("ClothingTexture",  this.getClothingTextureValue());
        nbt.putBoolean("EmissiveEyes",  this.getEmissiveEyes());

    }
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(BEARD_TYPE, nbt.getByte("BeardType"));
        this.dataTracker.set(SKIN_TEXTURE, nbt.getString("SkinTexture"));
        this.dataTracker.set(EYE_TEXTURE, nbt.getString("EyeTexture"));
        this.dataTracker.set(HAIR_TEXTURE, nbt.getString("HairTexture"));
        this.dataTracker.set(CLOTHING_TEXTURE, nbt.getString("ClothingTexture"));
        this.dataTracker.set(EMISSIVE_EYES, nbt.getBoolean("EmissiveEyes"));
    }

    public Byte getBeardType() {
        return this.dataTracker.get(BEARD_TYPE);
    }
    public BeardTypes getBeardTypeValue() {
        return BeardTypes.values()[this.dataTracker.get(BEARD_TYPE)];
    }
    public String getSkinTextureValue() {
        return this.dataTracker.get(SKIN_TEXTURE);
    }
    public String getEyeTextureValue() {
        return this.dataTracker.get(EYE_TEXTURE);
    }
    public String getHairTextureValue() {
        return this.dataTracker.get(HAIR_TEXTURE);
    }
    public String getClothingTextureValue() {
        return this.dataTracker.get(CLOTHING_TEXTURE);
    }
    public Boolean getEmissiveEyes() {
        return this.dataTracker.get(EMISSIVE_EYES);
    }
    public Identifier getSkinTextureIdentifier() {
        return IdentifierUtil.getIdentifierFromString(this.dataTracker.get(SKIN_TEXTURE));
    }
    public Identifier getEyeTextureIdentifier() {
        return IdentifierUtil.getIdentifierFromString(this.dataTracker.get(EYE_TEXTURE));
    }
    public Identifier getHairTextureIdentifier() {
        return IdentifierUtil.getIdentifierFromString(this.dataTracker.get(HAIR_TEXTURE));
    }
    public Identifier getClothingTextureIdentifier() {
        return IdentifierUtil.getIdentifierFromString(this.dataTracker.get(CLOTHING_TEXTURE));
    }
    static {
        BEARD_TYPE = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.BYTE);
        SKIN_TEXTURE = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.STRING);
        EYE_TEXTURE = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.STRING);
        HAIR_TEXTURE = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.STRING);
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
