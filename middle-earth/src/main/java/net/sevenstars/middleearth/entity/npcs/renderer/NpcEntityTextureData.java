package net.sevenstars.middleearth.entity.npcs.renderer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;

public class NpcEntityTextureData {
    private Identifier bodyTexture;
    private Identifier feetTexture;
    private Identifier headTexture;
    private Identifier earTexture;
    private Identifier noseTexture;
    private Identifier eyeTexture;
    private Identifier eyeEmissiveTexture;
    private Identifier eyebrowTexture;
    private Identifier scarTexture;
    private Identifier beardTexture;
    private Identifier beardAddonTexture;
    private Identifier hairTexture;
    private Identifier hairAddonTexture;
    private Identifier clothingBaseTexture;
    private Identifier clothingOverTexture;
    private Identifier clothingExtraTexture;

    private Boolean eyeIsEmissive;

    public static final Codec<NpcEntityTextureData> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
        NbtCompound.CODEC.fieldOf("data").forGetter(NpcEntityTextureData::writeNbt)
    ).apply(instance, NpcEntityTextureData::new));

    public static final PacketCodec<RegistryByteBuf, NpcEntityTextureData> PACKET_CODEC;

    public NpcEntityTextureData(NbtCompound compound)
    {
        this.bodyTexture = getId(compound.getString(NpcRenderedPart.BODY.getField(), null));
        this.headTexture = getId(compound.getString(NpcRenderedPart.HEAD.getField(), null));
        this.feetTexture = getId(compound.getString(NpcRenderedPart.FEET.getField(), null));

        this.earTexture = getId(compound.getString(NpcRenderedPart.EAR.getField(), null));
        this.noseTexture = getId(compound.getString(NpcRenderedPart.NOSE.getField(), null));

        this.scarTexture = getId(compound.getString(NpcRenderedPart.SCAR.getField(), null));
        this.eyeTexture = getId(compound.getString(NpcRenderedPart.EYE.getField(), null));
        this.eyeEmissiveTexture = getId(compound.getString(NpcRenderedPart.EYE_EMISSIVE.getField(), null));
        this.eyeIsEmissive = compound.getBoolean(NpcRenderedPart.EYE_EMISSIVE_TOGGLE.getField(), false);

        this.hairTexture = getId(compound.getString(NpcRenderedPart.HAIR.getField(), null));
        this.hairAddonTexture = getId(compound.getString(NpcRenderedPart.HAIR_ADDON.getField(), null));

        this.eyebrowTexture = getId(compound.getString(NpcRenderedPart.EYEBROW.getField(), null));

        this.beardTexture = getId(compound.getString(NpcRenderedPart.BEARD.getField(), null));
        this.beardAddonTexture = getId(compound.getString(NpcRenderedPart.BEARD_ADDON.getField(), null));

        this.clothingBaseTexture = getId(compound.getString(NpcRenderedPart.CLOTHING_BASE.getField(), null));
        this.clothingOverTexture = getId(compound.getString(NpcRenderedPart.CLOTHING_OVER.getField(), null));
        this.clothingExtraTexture = getId(compound.getString(NpcRenderedPart.CLOTHING_EXTRA.getField(), null));
    }

    private NbtCompound writeNbt() {
        NbtCompound nbt = new NbtCompound();
        if(bodyTexture != null)
            nbt.putString(NpcRenderedPart.BODY.getField(), bodyTexture.toString());
        if(headTexture != null)
            nbt.putString(NpcRenderedPart.HEAD.getField(), headTexture.toString());
        if(scarTexture != null)
            nbt.putString(NpcRenderedPart.SCAR.getField(), scarTexture.toString());
        if(earTexture != null)
            nbt.putString(NpcRenderedPart.EAR.getField(), earTexture.toString());
        if(noseTexture != null)
            nbt.putString(NpcRenderedPart.NOSE.getField(), noseTexture.toString());
        if(feetTexture != null)
            nbt.putString(NpcRenderedPart.FEET.getField(), feetTexture.toString());

        if(eyeTexture != null)
            nbt.putString(NpcRenderedPart.EYE.getField(), eyeTexture.toString());
        if(eyeEmissiveTexture != null)
            nbt.putString(NpcRenderedPart.EYE_EMISSIVE.getField(), eyeEmissiveTexture.toString());
        if(eyeIsEmissive != null)
            nbt.putBoolean(NpcRenderedPart.EYE_EMISSIVE_TOGGLE.getField(), eyeIsEmissive);

        if(hairTexture != null){
            nbt.putString(NpcRenderedPart.HAIR.getField(), hairTexture.toString());
            if(hairAddonTexture != null)
                nbt.putString(NpcRenderedPart.HAIR_ADDON.getField(), hairAddonTexture.toString());
        }

        if(eyebrowTexture != null)
            nbt.putString(NpcRenderedPart.EYEBROW.getField(), eyebrowTexture.toString());

        if(beardTexture != null){
            nbt.putString(NpcRenderedPart.BEARD.getField(), beardTexture.toString());
            if(beardAddonTexture != null)
                nbt.putString(NpcRenderedPart.BEARD_ADDON.getField(), beardAddonTexture.toString());
        }

        if(clothingBaseTexture != null)
            nbt.putString(NpcRenderedPart.CLOTHING_BASE.getField(), clothingBaseTexture.toString());
        if(clothingOverTexture != null)
            nbt.putString(NpcRenderedPart.CLOTHING_OVER.getField(), clothingOverTexture.toString());
        if(clothingExtraTexture != null)
            nbt.putString(NpcRenderedPart.CLOTHING_EXTRA.getField(), clothingExtraTexture.toString());

        return nbt;
    }

    private Identifier getId(String id){
        if(id == null)
            return null;
        return Identifier.of(id);
    }



    public NpcEntityTextureData() {
        this.eyeIsEmissive = false;
    }

    public NpcEntityTextureData withSkinTexture(Identifier texture){
        this.bodyTexture = texture;
        return this;
    }
    public NpcEntityTextureData withHeadTexture(Identifier texture){
        this.headTexture = texture;
        return this;
    }
    public NpcEntityTextureData withFeetTexture(Identifier texture){
        this.feetTexture = texture;
        return this;
    }
    public NpcEntityTextureData withScarTexture(Identifier texture){
        this.scarTexture = texture;
        return this;
    }
    public NpcEntityTextureData withEarTexture(Identifier texture){
        this.earTexture = texture;
        return this;
    }
    public NpcEntityTextureData withNoseTexture(Identifier texture){
        this.noseTexture = texture;
        return this;
    }

    public NpcEntityTextureData withEyeTexture(Identifier eyeTexture, Identifier eyeEmissiveTexture, Boolean isEmissive){
        this.eyeTexture = eyeTexture;
        this.eyeEmissiveTexture = eyeEmissiveTexture;
        this.eyeIsEmissive = isEmissive;
        return this;
    }

    public NpcEntityTextureData withHairTexture(Identifier texture){
        this.hairTexture = texture;
        return this;
    }

    public NpcEntityTextureData withHairAddonTexture(Identifier texture){
        this.hairAddonTexture = texture;
        return this;
    }

    public NpcEntityTextureData withEyebrowTexture(Identifier texture){
        this.eyebrowTexture = texture;
        return this;
    }

    public NpcEntityTextureData withBeardTexture(Identifier texture){
        this.beardTexture = texture;
        return this;
    }

    public NpcEntityTextureData withBeardAddonTexture(Identifier texture){
        this.beardAddonTexture = texture;
        return this;
    }
    public NpcEntityTextureData withClothingTexture(Identifier textureBase, Identifier textureOver, Identifier textureExtra){
        this.clothingBaseTexture = textureBase;
        this.clothingOverTexture = textureOver;
        this.clothingExtraTexture = textureOver;
        return this;
    }

    public Identifier get(NpcRenderedPart part){
        return switch (part){
            case BODY -> bodyTexture;
            case HEAD -> headTexture;
            case FEET -> feetTexture;
            case EAR -> earTexture;
            case NOSE -> noseTexture;
            case SCAR -> scarTexture;
            case EYE -> eyeTexture;
            case EYE_EMISSIVE -> eyeEmissiveTexture;
            case HAIR -> hairTexture;
            case HAIR_ADDON -> hairAddonTexture;
            case EYEBROW -> eyebrowTexture;
            case BEARD -> beardTexture;
            case BEARD_ADDON -> beardAddonTexture;
            case CLOTHING_BASE -> clothingBaseTexture;
            case CLOTHING_OVER -> clothingOverTexture;
            case CLOTHING_EXTRA -> clothingExtraTexture;
            default -> null;
        };
    }

    public Boolean isEyeEmissive() {
        return this.eyeIsEmissive;
    }
    static {
        PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.NBT_COMPOUND, NpcEntityTextureData::writeNbt,
                NpcEntityTextureData::new);
    }

    public boolean needToBeRefreshed() {
        return get(NpcRenderedPart.BODY) == null;
    }
}
