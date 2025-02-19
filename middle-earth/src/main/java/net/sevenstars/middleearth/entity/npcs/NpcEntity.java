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
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.features.beards.BeardTypes;
import net.sevenstars.middleearth.resources.MiddleEarthRaces;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.utils.IdentifierUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Random;


public class NpcEntity extends PassiveEntity {
    private static final TrackedData<Byte> BEARD_TYPE; // 0 = none
    //private static final TrackedData<String> RACE;
    private static final TrackedData<String> SKIN_TEXTURE;
    private static final TrackedData<String> EYE_TEXTURE;

    private Race race;

    public NpcEntity(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);

        this.race = MiddleEarthRaces.DWARF;

        if(Objects.equals(getSkinTextureValue(), "")){
            List<String> patterns = this.race.getSkinPatterns();
            List<String> materials = this.race.getSkinMaterials();
            Random random = new Random();
            int patternIndex = random.nextInt(patterns.size());
            int materialIndex = random.nextInt(materials.size());

            this.dataTracker.set(SKIN_TEXTURE, Identifier.of(MiddleEarth.MOD_ID, patterns.get(patternIndex) + "_" + materials.get(materialIndex)).toString());
        }
        if(Objects.equals(getEyeTextureValue(), "")){
            List<String> patterns = this.race.getEyePatterns();
            List<String> materials = this.race.getEyeMaterials();
            Random random = new Random();
            int patternIndex = random.nextInt(patterns.size());
            int materialIndex = random.nextInt(materials.size());

            this.dataTracker.set(EYE_TEXTURE, Identifier.of(MiddleEarth.MOD_ID, patterns.get(patternIndex) + "_" + materials.get(materialIndex)).toString());
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
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putByte("BeardType", this.getBeardType());
        nbt.putString("SkinTexture",  this.getSkinTextureValue());
        nbt.putString("EyeTexture",  this.getEyeTextureValue());
    }
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(BEARD_TYPE, nbt.getByte("BeardType"));
        this.dataTracker.set(SKIN_TEXTURE, nbt.getString("SkinTexture"));
        this.dataTracker.set(EYE_TEXTURE, nbt.getString("EyeTexture"));
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

    public Identifier getSkinTextureIdentifier() {
        return IdentifierUtil.getIdentifierFromString(this.dataTracker.get(SKIN_TEXTURE));
    }
    public Identifier getEyeTextureIdentifier() {
        return IdentifierUtil.getIdentifierFromString(this.dataTracker.get(EYE_TEXTURE));
    }
    public boolean haveEmissiveEyes() {
        return race.haveEmissiveEyes();
    }

    static {
        BEARD_TYPE = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.BYTE);
        SKIN_TEXTURE = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.STRING);
        EYE_TEXTURE = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.STRING);
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
