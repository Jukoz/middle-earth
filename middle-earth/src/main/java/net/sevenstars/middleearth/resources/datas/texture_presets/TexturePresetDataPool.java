package net.sevenstars.middleearth.resources.datas.texture_presets;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.api.dtos.WeightedPool;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;

import java.util.HashMap;
import java.util.Random;

public class TexturePresetDataPool {
    public static final Codec<TexturePresetDataPool> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        CompoundTag.CODEC.fieldOf("categories").forGetter(TexturePresetDataPool::getNbt)
    ).apply(instance, TexturePresetDataPool::new));

    HashMap<EntityCategories, WeightedPool<WeightedTexturePresetHolder>> presetsByCategory;

    public TexturePresetDataPool(CompoundTag categories) {
        if(categories == null) return;
        presetsByCategory = new HashMap<>();

        for(EntityCategories category : EntityCategories.values()){
            if (!(categories.get(category.name()) instanceof ListTag nbtListPresets))
                continue;
            WeightedPool<WeightedTexturePresetHolder> dataPresetList = new WeightedPool<>();
            for(int i = 0; i < nbtListPresets.size(); i++){
                if (nbtListPresets.get(i) instanceof CompoundTag preset) {
                    dataPresetList.add(new WeightedTexturePresetHolder(preset));
                }
            }
            presetsByCategory.put(category, dataPresetList);
        }
    }

    public TexturePresetDataPool(HashMap<EntityCategories, WeightedPool<WeightedTexturePresetHolder>> sourceDatas) {
        presetsByCategory = sourceDatas;
    }

    public static ResourceLocation buildId(ResourceLocation pattern, ResourceLocation material) {
        if(pattern == null || material == null)
            return null;
        return MiddleEarth.of(pattern.getPath() + "_" + material.getPath());
    }

    public static ResourceLocation buildAddonId(ResourceLocation pattern, ResourceLocation material) {
        return MiddleEarth.of(pattern.getPath() + "_addon_" + material.getPath());
    }

    public static ClothingSelection getClothing(Identity textureIdentity) {
        return textureIdentity.preset.getClothingData();
    }


    public CompoundTag getNbt() {
        CompoundTag newNbt = new CompoundTag();
        for(EntityCategories category : presetsByCategory.keySet()){
            WeightedPool<WeightedTexturePresetHolder> presets = presetsByCategory.get(category);
            if(presets != null && !presets.isEmpty()){
                ListTag newNbtList = new ListTag();
                for (WeightedTexturePresetHolder preset : presets.elements) {
                    newNbtList.add(preset.getNbt());
                }
                newNbt.put(category.name(), newNbtList);
            }
        }
        return newNbt;
    }

    public static ResourceLocation getRawMaterial(Identity identity, CharacterMaterialTypes materialType) {
        var weightedItem = identity.preset.getMaterials(materialType).getRandom();
        if(weightedItem == null)
            return null;
        return weightedItem.getItem();
    }

    public static ResourceLocation getRawPattern(Identity identity, CharacterPatternTypes patternType) {
        var weightedItem = identity.preset.getPatterns(patternType).getRandom();
        if(weightedItem == null)
            return null;
        if(weightedItem.isSame(TexturePreset.EMPTY_VALUE_KEY.getItem()))
            return null;
        return weightedItem.getItem();
    }

    public Boolean haveEmissiveEyes(Identity identity) {
        return identity.preset.haveEmissiveEyes();
    }

    public EntityCategories getRandomCategory() {
        Random random = new Random();
        boolean containsMale = this.presetsByCategory.containsKey(EntityCategories.MALE);
        boolean containsFemale = this.presetsByCategory.containsKey(EntityCategories.FEMALE);
        // Shared is not a valid sex
        if(containsMale && containsFemale)
            return (random.nextBoolean()) ? EntityCategories.MALE : EntityCategories.FEMALE;
        else if(containsMale)
            return EntityCategories.MALE;
        else if(containsFemale)
            return EntityCategories.FEMALE;

        return EntityCategories.MALE;
    }

    public boolean hasCategory(EntityCategories category) {
        return this.presetsByCategory.containsKey(category);
    }

    public record Identity(EntityCategories category, WeightedTexturePresetHolder preset){
        public static Identity create(TexturePresetDataPool data, EntityCategories entityCategories){
            if(!data.presetsByCategory.containsKey(entityCategories))
                return null;

            WeightedPool<WeightedTexturePresetHolder> presets = data.presetsByCategory.get(entityCategories);
            presets = feedSharedToPresets(data, presets);

            if (presets.isEmpty()) {
                return null;
            }
            Random random = new Random();
            return new Identity(entityCategories, presets.get(random.nextInt(presets.size())));
        }

        public static Identity create(TexturePresetDataPool data){
            EntityCategories entityCategories = data.getRandomCategory();
            if(!data.presetsByCategory.containsKey(entityCategories))
                return null;

            WeightedPool<WeightedTexturePresetHolder> presets = data.presetsByCategory.get(entityCategories);
            presets = feedSharedToPresets(data, presets);

            return new Identity(entityCategories, presets.getRandom());
        }

        private static WeightedPool<WeightedTexturePresetHolder> feedSharedToPresets(TexturePresetDataPool data, WeightedPool<WeightedTexturePresetHolder> sourcePresets) {
            WeightedPool<WeightedTexturePresetHolder> copyOfSourcePresets = new WeightedPool<>();

            if(data.presetsByCategory.containsKey(EntityCategories.SHARED)){
                var sharedPresets = data.presetsByCategory.get(EntityCategories.SHARED);
                if(sharedPresets != null){
                    for (WeightedTexturePresetHolder sourcePreset : sourcePresets.elements) {
                        WeightedTexturePresetHolder presetToFill = sourcePreset.copy();
                        for (WeightedTexturePresetHolder sharePreset : sharedPresets.elements) {
                            presetToFill.withMaterials(CharacterMaterialTypes.SKIN, sharePreset.getMaterials(CharacterMaterialTypes.SKIN));
                            presetToFill.withPatterns(CharacterPatternTypes.BODY, sharePreset.getPatterns(CharacterPatternTypes.BODY));
                            presetToFill.withPatterns(CharacterPatternTypes.HEAD, sharePreset.getPatterns(CharacterPatternTypes.HEAD));
                            presetToFill.withPatterns(CharacterPatternTypes.EAR, sharePreset.getPatterns(CharacterPatternTypes.EAR));
                            presetToFill.withPatterns(CharacterPatternTypes.NOSE, sharePreset.getPatterns(CharacterPatternTypes.NOSE));
                            presetToFill.withPatterns(CharacterPatternTypes.SCAR, sharePreset.getPatterns(CharacterPatternTypes.SCAR));
                            presetToFill.withPatterns(CharacterPatternTypes.FEET, sharePreset.getPatterns(CharacterPatternTypes.FEET));

                            presetToFill.withMaterials(CharacterMaterialTypes.HAIR, sharePreset.getMaterials(CharacterMaterialTypes.HAIR));
                            presetToFill.withPatterns(CharacterPatternTypes.HAIR, sharePreset.getPatterns(CharacterPatternTypes.HAIR));
                            presetToFill.withPatterns(CharacterPatternTypes.BEARD, sharePreset.getPatterns(CharacterPatternTypes.BEARD));
                            presetToFill.withPatterns(CharacterPatternTypes.EYEBROW, sharePreset.getPatterns(CharacterPatternTypes.EYEBROW));
                            presetToFill.withEmissiveEyes(sharePreset.haveEmissiveEyes());

                            presetToFill.withMaterials(CharacterMaterialTypes.EYE, sharePreset.getMaterials(CharacterMaterialTypes.EYE));
                            presetToFill.withPatterns(CharacterPatternTypes.EYE, sharePreset.getPatterns(CharacterPatternTypes.EYE));

                            if(sharePreset.getClothes().isFilled())
                                presetToFill.withClothes(sharePreset.getClothes());
                        }
                        copyOfSourcePresets.add(presetToFill);
                    }
                }
            }
            return copyOfSourcePresets;
        }
    }
}
