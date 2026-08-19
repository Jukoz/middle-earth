package net.sevenstars.middleearth.entity.npcs.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcRenderedPart;
import net.sevenstars.middleearth.resources.datas.texture_presets.SimplifiedTexturePreset;

public class NpcTextureData {
    private ResourceLocation simplifiedSkin;
    private ResourceLocation simplifiedEar;
    private ResourceLocation simplifiedFeet;
    private ResourceLocation simplifiedHair;
    private ResourceLocation simplifiedNose;

    private ResourceLocation bodyTexture;
    private ResourceLocation feetTexture;
    private ResourceLocation headTexture;
    private ResourceLocation earTexture;
    private ResourceLocation noseTexture;
    private ResourceLocation eyeTexture;
    private ResourceLocation eyeEmissiveTexture;
    private ResourceLocation eyebrowTexture;
    private ResourceLocation scarTexture;
    private ResourceLocation beardTexture;
    private ResourceLocation beardAddonTexture;
    private ResourceLocation hairTexture;
    private ResourceLocation hairAddonTexture;
    private ResourceLocation clothingBaseTexture;
    private ResourceLocation clothingOverTexture;
    private ResourceLocation clothingExtraTexture;

    private Boolean eyeIsEmissive;

    public static final Codec<NpcTextureData> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
        CompoundTag.CODEC.fieldOf("dynamic").forGetter(NpcTextureData::writeDynamic),
        CompoundTag.CODEC.fieldOf("simplified").forGetter(NpcTextureData::writeSimplified)
    ).apply(instance, NpcTextureData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, NpcTextureData> PACKET_CODEC;

    public NpcTextureData(CompoundTag dynamic, CompoundTag simplified)
    {
        this.simplifiedSkin = MiddleEarth.fetchId(getString(simplified, "skin"));
        this.simplifiedEar = MiddleEarth.fetchId(getString(simplified, "ear"));
        this.simplifiedFeet = MiddleEarth.fetchId(getString(simplified, "feet"));
        this.simplifiedHair = MiddleEarth.fetchId(getString(simplified, "hair"));
        this.simplifiedNose = MiddleEarth.fetchId(getString(simplified, "nose"));

        this.bodyTexture = MiddleEarth.fetchId(getString(dynamic, NpcRenderedPart.BODY.getField()));
        this.headTexture = MiddleEarth.fetchId(getString(dynamic, NpcRenderedPart.HEAD.getField()));
        this.feetTexture = MiddleEarth.fetchId(getString(dynamic, NpcRenderedPart.FEET.getField()));

        this.earTexture = MiddleEarth.fetchId(getString(dynamic, NpcRenderedPart.EAR.getField()));
        this.noseTexture = MiddleEarth.fetchId(getString(dynamic, NpcRenderedPart.NOSE.getField()));

        this.scarTexture = MiddleEarth.fetchId(getString(dynamic, NpcRenderedPart.SCAR.getField()));
        this.eyeTexture = MiddleEarth.fetchId(getString(dynamic, NpcRenderedPart.EYE.getField()));
        this.eyeEmissiveTexture = MiddleEarth.fetchId(getString(dynamic, NpcRenderedPart.EYE_EMISSIVE.getField()));
        this.eyeIsEmissive = dynamic.getBoolean(NpcRenderedPart.EYE_EMISSIVE_TOGGLE.getField());

        this.hairTexture = MiddleEarth.fetchId(getString(dynamic, NpcRenderedPart.HAIR.getField()));
        this.hairAddonTexture = MiddleEarth.fetchId(getString(dynamic, NpcRenderedPart.HAIR_ADDON.getField()));

        this.eyebrowTexture = MiddleEarth.fetchId(getString(dynamic, NpcRenderedPart.EYEBROW.getField()));

        this.beardTexture = MiddleEarth.fetchId(getString(dynamic, NpcRenderedPart.BEARD.getField()));
        this.beardAddonTexture = MiddleEarth.fetchId(getString(dynamic, NpcRenderedPart.BEARD_ADDON.getField()));

        this.clothingBaseTexture = MiddleEarth.fetchId(getString(dynamic, NpcRenderedPart.CLOTHING_BASE.getField()));
        this.clothingOverTexture = MiddleEarth.fetchId(getString(dynamic, NpcRenderedPart.CLOTHING_OVER.getField()));
        this.clothingExtraTexture = MiddleEarth.fetchId(getString(dynamic, NpcRenderedPart.CLOTHING_EXTRA.getField()));
    }

    private static String getString(CompoundTag tag, String key) {
        return tag.contains(key, Tag.TAG_STRING) ? tag.getString(key) : null;
    }

    private CompoundTag writeSimplified() {
        CompoundTag nbt = new CompoundTag();
        if(simplifiedSkin != null)
            nbt.putString("skin", simplifiedSkin.toString());
        if(simplifiedEar != null)
            nbt.putString("ear", simplifiedEar.toString());
        if(simplifiedFeet != null)
            nbt.putString("feet", simplifiedFeet.toString());
        if(simplifiedHair != null)
            nbt.putString("hair", simplifiedHair.toString());
        if(simplifiedNose != null)
            nbt.putString("nose", simplifiedNose.toString());
        return nbt;
    }

    private CompoundTag writeDynamic() {
        CompoundTag nbt = new CompoundTag();
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

    public NpcTextureData() {
        this.eyeIsEmissive = false;
    }

    public void withSimplifiedPreset(SimplifiedTexturePreset preset) {
        if(preset == null)
            return;

        this.simplifiedSkin = preset.base;
        this.simplifiedEar = preset.ear;
        this.simplifiedFeet = preset.feet;
        this.simplifiedHair = preset.hair;
        this.simplifiedNose = preset.nose;
    }

    public NpcTextureData withSkinTexture(ResourceLocation texture){
        this.bodyTexture = texture;
        return this;
    }
    public NpcTextureData withHeadTexture(ResourceLocation texture){
        this.headTexture = texture;
        return this;
    }
    public NpcTextureData withFeetTexture(ResourceLocation texture){
        this.feetTexture = texture;
        return this;
    }
    public NpcTextureData withScarTexture(ResourceLocation texture){
        this.scarTexture = texture;
        return this;
    }
    public NpcTextureData withEarTexture(ResourceLocation texture){
        this.earTexture = texture;
        return this;
    }
    public NpcTextureData withNoseTexture(ResourceLocation texture){
        this.noseTexture = texture;
        return this;
    }

    public NpcTextureData withEyeTexture(ResourceLocation eyeTexture, ResourceLocation eyeEmissiveTexture, Boolean isEmissive){
        this.eyeTexture = eyeTexture;
        this.eyeEmissiveTexture = eyeEmissiveTexture;
        this.eyeIsEmissive = isEmissive;
        return this;
    }

    public NpcTextureData withHairTexture(ResourceLocation texture){
        this.hairTexture = texture;
        return this;
    }

    public NpcTextureData withHairAddonTexture(ResourceLocation texture){
        this.hairAddonTexture = texture;
        return this;
    }

    public NpcTextureData withEyebrowTexture(ResourceLocation texture){
        this.eyebrowTexture = texture;
        return this;
    }

    public NpcTextureData withBeardTexture(ResourceLocation texture){
        this.beardTexture = texture;
        return this;
    }

    public NpcTextureData withBeardAddonTexture(ResourceLocation texture){
        this.beardAddonTexture = texture;
        return this;
    }
    public NpcTextureData withClothingTexture(ResourceLocation textureBase, ResourceLocation textureOver, ResourceLocation textureExtra){
        this.clothingBaseTexture = textureBase;
        this.clothingOverTexture = textureOver;
        this.clothingExtraTexture = textureExtra;
        return this;
    }

    public ResourceLocation get(NpcRenderedPart part){
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
        PACKET_CODEC = StreamCodec.composite(
                ByteBufCodecs.COMPOUND_TAG, NpcTextureData::writeDynamic,
                ByteBufCodecs.COMPOUND_TAG, NpcTextureData::writeSimplified,
                NpcTextureData::new);
    }

    public boolean needToBeRefreshed() {
        return get(NpcRenderedPart.BODY) == null;
    }

    public ResourceLocation getSimplifiedSkin() {
        return this.simplifiedSkin;
    }

    public ResourceLocation getSimplifiedEar() {
        return this.simplifiedEar;
    }

    public ResourceLocation getSimplifiedFeet() {
        return this.simplifiedFeet;
    }

    public ResourceLocation getSimplifiedHair() {
        return this.simplifiedHair;
    }

    public ResourceLocation getSimplifiedNose() {
        return this.simplifiedNose;
    }
}
