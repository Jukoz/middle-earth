package net.sevenstars.middleearth.entity.npcs.data;

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

    private Boolean eyeEmissive;

    public static final Codec<NpcEntityTextureData> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
        NbtCompound.CODEC.fieldOf("data").forGetter(NpcEntityTextureData::writeNbt)
    ).apply(instance, NpcEntityTextureData::new));

    public static final PacketCodec<RegistryByteBuf, NpcEntityTextureData> PACKET_CODEC;

    public NpcEntityTextureData(NbtCompound compound)
    {
        if(compound.contains("body"))
            this.bodyTexture = Identifier.of(compound.getString("body").get());
        if(compound.contains("head"))
            this.headTexture = Identifier.of(compound.getString("head").get());
        if(compound.contains("feet"))
            this.feetTexture = Identifier.of(compound.getString("feet").get());

        if(compound.contains("ear"))
            this.earTexture = Identifier.of(compound.getString("ear").get());

        if(compound.contains("nose"))
            this.noseTexture = Identifier.of(compound.getString("nose").get());
        if(compound.contains("scar"))
            this.scarTexture = Identifier.of(compound.getString("scar").get());

        if(compound.contains("eye"))
            this.eyeTexture = Identifier.of(compound.getString("eye").get());
        if(compound.contains("eye_emissive"))
            this.eyeEmissiveTexture = Identifier.of(compound.getString("eye_emissive").get());

        if(compound.contains("eye_is_emissive"))
            this.eyeEmissive = compound.getBoolean("eye_is_emissive").get();

        if(compound.contains("hair")){
            this.hairTexture = Identifier.of(compound.getString("hair").get());
            if(compound.contains("hair_addon")){
                this.hairAddonTexture = Identifier.of(compound.getString("hair_addon").get());
            }
        }

        if(compound.contains("eyebrow"))
            this.eyebrowTexture = Identifier.of(compound.getString("eyebrow").get());

        if(compound.contains("beard")){
            this.beardTexture = Identifier.of(compound.getString("beard").get());
            if(compound.contains("beard_addon")){
                this.beardAddonTexture = Identifier.of(compound.getString("beard_addon").get());
            }
        }

        if(compound.contains("clothing_base"))
            this.clothingBaseTexture = Identifier.of(compound.getString("clothing_base").get());

        if(compound.contains("clothing_over"))
            this.clothingOverTexture = Identifier.of(compound.getString("clothing_over").get());

        if(compound.contains("clothing_extra"))
            this.clothingExtraTexture = Identifier.of(compound.getString("clothing_extra").get());
    }

    public NpcEntityTextureData() {
        this.eyeEmissive = false;
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
        this.eyeEmissive = isEmissive;
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



    private NbtCompound writeNbt() {
        NbtCompound nbt = new NbtCompound();
        if(bodyTexture != null)
            nbt.putString("body", bodyTexture.toString());
        if(headTexture != null)
            nbt.putString("head", headTexture.toString());
        if(scarTexture != null)
            nbt.putString("scar", scarTexture.toString());
        if(earTexture != null)
            nbt.putString("ear", earTexture.toString());
        if(noseTexture != null)
            nbt.putString("nose", noseTexture.toString());
        if(feetTexture != null)
            nbt.putString("feet", feetTexture.toString());

        if(eyeTexture != null)
            nbt.putString("eye", eyeTexture.toString());
        if(eyeEmissiveTexture != null)
            nbt.putString("eye_emissive", eyeEmissiveTexture.toString());
        if(eyeEmissive != null)
            nbt.putBoolean("eye_is_emissive", eyeEmissive);

        if(hairTexture != null){
            nbt.putString("hair", hairTexture.toString());
            if(hairAddonTexture != null)
                nbt.putString("hair_addon", hairAddonTexture.toString());
        }

        if(eyebrowTexture != null)
            nbt.putString("eyebrow", eyebrowTexture.toString());

        if(beardTexture != null){
            nbt.putString("beard", beardTexture.toString());
            if(beardAddonTexture != null)
                nbt.putString("beard_addon", beardAddonTexture.toString());
        }

        if(clothingBaseTexture != null)
            nbt.putString("clothing_base", clothingBaseTexture.toString());
        if(clothingOverTexture != null)
            nbt.putString("clothing_over", clothingOverTexture.toString());
        if(clothingExtraTexture != null)
            nbt.putString("clothing_extra", clothingExtraTexture.toString());

        return nbt;
    }


    public Identifier getBodyTexture() {
        return this.bodyTexture;
    }
    public Identifier getFeetTexture() {
        return this.feetTexture;
    }
    public Identifier getHeadTexture() {
        return this.headTexture;
    }

    public Identifier getEarTexture() {
        return this.earTexture;
    }

    public Identifier getNoseTexture() {
        return this.noseTexture;
    }
    public Identifier getEyeTexture() {
        return this.eyeTexture;
    }
    public Identifier getEyeEmissiveTexture() {
        return this.eyeEmissiveTexture;
    }
    public Boolean isEyeEmissive() {
        return this.eyeEmissive;
    }
    public Identifier getHairTexture() {
        return this.hairTexture;
    }
    public Identifier getHairAddonTexture() {
        return this.hairAddonTexture;
    }
    public Identifier getEyebrowTexture() { return this.eyebrowTexture;}
    public Identifier getScarTexture() { return this.scarTexture; }
    public Identifier getBeardTexture() {
        return this.beardTexture;
    }
    public Identifier getBeardAddonTexture() {
        return this.beardAddonTexture;
    }
    public Identifier getClothingBaseTexture() { return this.clothingBaseTexture; }
    public Identifier getClothingOverTexture() {
        return this.clothingOverTexture;
    }
    public Identifier getClothingExtraTexture() { return this.clothingExtraTexture; }

    static {
        PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.NBT_COMPOUND, NpcEntityTextureData::writeNbt,
                NpcEntityTextureData::new);
    }
}
