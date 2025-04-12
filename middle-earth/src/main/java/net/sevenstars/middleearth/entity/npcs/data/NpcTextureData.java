package net.sevenstars.middleearth.entity.npcs.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;

public class NpcTextureData {
    private Identifier skinTexture;
    private Identifier earTexture;
    private Identifier noseTexture;
    private Identifier eyeTexture;
    private Boolean eyeEmissive;
    private Identifier hairTexture;
    private Identifier hairAddonTexture;
    private Identifier eyebrowTexture;
    private Identifier beardTexture;
    private Identifier beardAddonTexture;
    private Identifier clothingTexture;


    public static final Codec<NpcTextureData> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
        NbtCompound.CODEC.fieldOf("data").forGetter(NpcTextureData::writeNbt)
    ).apply(instance, NpcTextureData::new));

    public static final PacketCodec<RegistryByteBuf, NpcTextureData> PACKET_CODEC;

    public NpcTextureData(NbtCompound compound) {
        this.skinTexture = Identifier.of(compound.getString("skin").get());

        if(compound.contains("ear"))
            this.earTexture = Identifier.of(compound.getString("ear").get());

        if(compound.contains("nose"))
            this.noseTexture = Identifier.of(compound.getString("nose").get());

        this.eyeTexture = Identifier.of(compound.getString("eye").get());
        this.eyeEmissive = compound.getBoolean("eye_emissive").get();

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

        this.clothingTexture = Identifier.of(compound.getString("clothing").get());
    }

    public NpcTextureData() {
        this.eyeEmissive = false;
    }

    public NpcTextureData withSkinTexture(Identifier texture){
        this.skinTexture = texture;
        return this;
    }

    public NpcTextureData withEarTexture(Identifier texture){
        this.earTexture = texture;
        return this;
    }
    public NpcTextureData withNoseTexture(Identifier texture){
        this.noseTexture = texture;
        return this;
    }
    public NpcTextureData withEyeTexture(Identifier texture, Boolean isEmissive){
        this.eyeTexture = texture;
        this.eyeEmissive = isEmissive;
        return this;
    }

    public NpcTextureData withHairTexture(Identifier texture){
        this.hairTexture = texture;
        return this;
    }

    public NpcTextureData withHairAddonTexture(Identifier texture){
        this.hairAddonTexture = texture;
        return this;
    }

    public NpcTextureData withEyebrowTexture(Identifier texture){
        this.eyebrowTexture = texture;
        return this;
    }

    public NpcTextureData withBeardTexture(Identifier texture){
        this.beardTexture = texture;
        return this;
    }

    public NpcTextureData withBeardAddonTexture(Identifier texture){
        this.beardAddonTexture = texture;
        return this;
    }
    public NpcTextureData withClothingTexture(Identifier texture){
        this.clothingTexture = texture;
        return this;
    }



    private NbtCompound writeNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("skin", skinTexture.toString());
        if(earTexture != null)
            nbt.putString("ear", earTexture.toString());
        if(noseTexture != null)
            nbt.putString("nose", noseTexture.toString());

        nbt.putString("eye", eyeTexture.toString());
        nbt.putBoolean("eye_emissive", eyeEmissive);

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

        nbt.putString("clothing", clothingTexture.toString());

        return nbt;
    }


    public Identifier getSkinTexture() {
        return this.skinTexture;
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
    public Boolean isEyeEmissive() {
        return this.eyeEmissive;
    }
    public Identifier getHairTexture() {
        return this.hairTexture;
    }
    public Identifier getHairAddonTexture() {
        return this.hairAddonTexture;
    }
    public Identifier getEyebrowTexture() {
        return this.eyebrowTexture;
    }
    public Identifier getBeardTexture() {
        return this.beardTexture;
    }
    public Identifier getBeardAddonTexture() {
        return this.beardAddonTexture;
    }
    public Identifier getClothingTexture() {
        return this.clothingTexture;
    }
    static {
        PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.NBT_COMPOUND, NpcTextureData::writeNbt,
                NpcTextureData::new);
    }
}
