package net.sevenstars.middleearth.resources.datas.texture_presets;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.sevenstars.api.dtos.WeightedItem;
import net.sevenstars.api.dtos.WeightedPool;
import net.sevenstars.middleearth.registries.SimplifiedTexturesME;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.api.dtos.WeightedIdentifier;

import java.util.*;

public class WeightedTexturePresetHolder extends WeightedItem<TexturePreset> {
    public WeightedTexturePresetHolder(){
        this.weight = 1;
        this.item = new TexturePreset();
    }

    @Override
    public WeightedTexturePresetHolder withWeight(int newWeight) {
        this.weight = newWeight;
        return this;
    }


    public WeightedTexturePresetHolder(NbtCompound compound) {
        super(compound);

        this.item = new TexturePreset(compound);
    }

    @Override
    public NbtElement getNbt(){
        NbtElement nbt = super.getNbt();
        if(nbt == null){
            nbt = new NbtCompound();
        }
        return item.getNbt(nbt.asCompound().get());
    }

    public WeightedTexturePresetHolder withEmissiveEyes(boolean value){
        this.item.withEmissiveEyes(value);
        return this;
    }

    public WeightedTexturePresetHolder withClothes(List<WeightedClothingPresetHolder> characterClothePresets){
        this.item.withClothes(characterClothePresets);
        return this;
    }

    public WeightedTexturePresetHolder clearClothes(){
        this.item.clearClothes();
        return this;
    }

    public WeightedTexturePresetHolder withPatterns(CharacterPatternTypes type, List<WeightedIdentifier> patterns){
        this.item.withPatterns(type, patterns);
        return this;
    }

    public WeightedTexturePresetHolder withPatterns(CharacterPatternTypes type, WeightedPool<WeightedIdentifier> patterns){
        this.item.withPatterns(type, patterns);
        return this;
    }

    public WeightedTexturePresetHolder clearPatterns(CharacterPatternTypes type) {
        this.item.clearPatterns(type);
        return this;
    }
    public WeightedTexturePresetHolder withMaterials(CharacterMaterialTypes type, List<WeightedIdentifier> materials){
        this.item.withMaterials(type, materials);
        return this;
    }
    public WeightedTexturePresetHolder withMaterials(CharacterMaterialTypes type, WeightedPool<WeightedIdentifier> materials){
        this.item.withMaterials(type, materials);
        return this;
    }

    public WeightedTexturePresetHolder clearMaterials(CharacterMaterialTypes type) {
        this.item.clearMaterials(type);
        return this;
    }

    public void addToPattern(CharacterPatternTypes patternType, WeightedIdentifier value) {
        this.item.addToPattern(patternType, value);
    }
    private void clearAllPatterns(CharacterPatternTypes patternType) {
        this.item.clearPatterns(patternType);
    }

    public void addToMaterial(CharacterMaterialTypes materialType, WeightedIdentifier value) {
        this.item.addToMaterial(materialType, value);

    }
    private void clearAllMaterials(CharacterMaterialTypes materialType) {
        this.item.clearMaterials(materialType);
    }

    public WeightedPool<WeightedIdentifier> getPatterns(CharacterPatternTypes patternTypes) {
        return this.item.getPatterns(patternTypes);
    }
    public WeightedPool<WeightedIdentifier> getMaterials(CharacterMaterialTypes materialType) {
        return this.item.getMaterials(materialType);
    }

    public Boolean haveEmissiveEyes() {
        return this.item.haveEmissiveEyes();
    }

    public WeightedTexturePresetHolder copy() {
        var copiedNpcTextureDataPreset = new WeightedTexturePresetHolder();
        for(WeightedIdentifier value : getMaterials(CharacterMaterialTypes.SKIN).elements)
            copiedNpcTextureDataPreset.addToMaterial(CharacterMaterialTypes.SKIN, value);
        for(WeightedIdentifier value : getPatterns(CharacterPatternTypes.HEAD).elements)
            copiedNpcTextureDataPreset.addToPattern(CharacterPatternTypes.HEAD, value);
        for(WeightedIdentifier value : getPatterns(CharacterPatternTypes.BODY).elements)
            copiedNpcTextureDataPreset.addToPattern(CharacterPatternTypes.BODY, value);
        for(WeightedIdentifier value : getPatterns(CharacterPatternTypes.FEET).elements)
            copiedNpcTextureDataPreset.addToPattern(CharacterPatternTypes.FEET, value);
        for(WeightedIdentifier value : getPatterns(CharacterPatternTypes.NOSE).elements)
            copiedNpcTextureDataPreset.addToPattern(CharacterPatternTypes.NOSE, value);
        for(WeightedIdentifier value : getPatterns(CharacterPatternTypes.EAR).elements)
            copiedNpcTextureDataPreset.addToPattern(CharacterPatternTypes.EAR, value);

        for(WeightedIdentifier value : getMaterials(CharacterMaterialTypes.EYE).elements)
            copiedNpcTextureDataPreset.addToMaterial(CharacterMaterialTypes.EYE, value);
        for(WeightedIdentifier value : getPatterns(CharacterPatternTypes.EYE).elements)
            copiedNpcTextureDataPreset.addToPattern(CharacterPatternTypes.EYE, value);

        for(WeightedIdentifier value : getMaterials(CharacterMaterialTypes.HAIR).elements)
            copiedNpcTextureDataPreset.addToMaterial(CharacterMaterialTypes.HAIR, value);
        for(WeightedIdentifier value : getPatterns(CharacterPatternTypes.EYEBROW).elements)
            copiedNpcTextureDataPreset.addToPattern(CharacterPatternTypes.EYEBROW, value);
        for(WeightedIdentifier value : getPatterns(CharacterPatternTypes.HAIR).elements)
            copiedNpcTextureDataPreset.addToPattern(CharacterPatternTypes.HAIR, value);
        for(WeightedIdentifier value : getPatterns(CharacterPatternTypes.BEARD).elements)
            copiedNpcTextureDataPreset.addToPattern(CharacterPatternTypes.BEARD, value);

        copiedNpcTextureDataPreset.item.characterClothePresets.addAll(this.item.characterClothePresets.elements);

        return copiedNpcTextureDataPreset;
    }

    public Identifier getSimplifiedSkin(){
        return SimplifiedTexturesME.HUMAN;
    }
    public Identifier getSimplifiedEar(){
        return SimplifiedTexturesME.HUMAN_EAR;
    }
    public Identifier getSimplifiedFeet(){
        return SimplifiedTexturesME.HUMAN_FEET;
    }
    public Identifier getSimplifiedHair(){
        return SimplifiedTexturesME.HUMAN_HAIR;
    }
    public Identifier getSimplifiedNose(){
        return SimplifiedTexturesME.HUMAN_NOSE;
    }

    public ClothingSelection getClothingData() {
        if(item.characterClothePresets.isEmpty()){
            //TODO : add clothes to all : MiddleEarth.LOGGER.logDebugMsg("Couldn't find clothes for " + this.getNbt());
            return new ClothingSelection(null, null, null);
        }
        WeightedClothingPresetHolder clothePreset = item.characterClothePresets.getRandom();

        Identifier baseId = clothePreset.getRandomBase();
        Identifier overId = clothePreset.getRandomOver();
        Identifier extraId = clothePreset.getRandomExtra();

        return new ClothingSelection(baseId, overId, extraId);
    }
}
