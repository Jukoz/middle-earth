package net.sevenstars.middleearth.resources.datas.texture_presets;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.texture_presets.entities.TextureElementData;
import net.sevenstars.middleearth.resources.datas.texture_presets.entities.TexturePresetData;
import net.sevenstars.middleearth.resources.datas.common.NpcTextureType;
import net.sevenstars.middleearth.utils.IdentifierUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TexturePresetDatas {
    public static final Codec<TexturePresetDatas> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        NbtCompound.CODEC.fieldOf("categories").forGetter(TexturePresetDatas::getNbt)
    ).apply(instance, TexturePresetDatas::new));

    HashMap<EntityCategories, List<TexturePresetData>> presetsByCategory;

    public TexturePresetDatas(NbtCompound categories) {
        if(categories == null) return;
        presetsByCategory = new HashMap<>();

        for(EntityCategories category : EntityCategories.values()){
            var optList = categories.getList(category.name());
            if(optList.isEmpty()) continue;;
            NbtList nbtListPresets = optList.get();
            List<TexturePresetData> dataPresetList = new ArrayList<>();
            for(int i = 0; i < nbtListPresets.size(); i++){
                TexturePresetData fetchedPreset = new TexturePresetData(nbtListPresets.getCompound(i).get());
                dataPresetList.add(fetchedPreset);
            }
            presetsByCategory.put(category, dataPresetList);
        }
    }

    public TexturePresetDatas(HashMap<EntityCategories, List<TexturePresetData>> sourceDatas) {
        presetsByCategory = sourceDatas;
    }

    public static Identifier buildId(Identifier pattern, Identifier material) {
        if(pattern == null || material == null)
            return null;
        return IdentifierUtil.build(pattern.getPath() + "_" + material.getPath());
    }

    public static Identifier buildAddonId(Identifier pattern, Identifier material) {
        return IdentifierUtil.build(pattern.getPath() + "_addon_" + material.getPath());
    }

    public static ClothingData getClothing(Identity textureIdentity) {
        return textureIdentity.preset.getClothingData();
    }


    public NbtCompound getNbt() {
        NbtCompound newNbt = new NbtCompound();
        for(EntityCategories category : presetsByCategory.keySet()){
            List<TexturePresetData> presets = presetsByCategory.get(category);
            if(presets != null && !presets.isEmpty()){
                NbtList newNbtList = new NbtList();
                for (TexturePresetData preset : presets) {
                    newNbtList.add(preset.getNbt());
                }
                newNbt.put(category.name(), newNbtList);
            }
        }
        return newNbt;
    }

    public static Identifier getRawMaterial(Identity identity, NpcTextureType npcTextureType) {
        List<TextureElementData> materials = identity.preset.getMaterials(npcTextureType);
        if(materials == null || materials.isEmpty())
            return null;
        Random random = new Random();
        int materialIndex = random.nextInt(materials.size());
        return materials.get(materialIndex).getId();
    }

    public static Identifier getRawPattern(Identity identity, NpcTextureType npcTextureType) {
        List<TextureElementData> patterns = identity.preset.getPatterns(npcTextureType);
        if (patterns == null || patterns.isEmpty())
            return null;
        Random random = new Random();
        Identifier value = patterns.get(random.nextInt(patterns.size())).getId();
        if(value.equals(TexturePresetData.EMPTY_VALUE_KEY))
            return null;
        return value;
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

    public record Identity(EntityCategories category, TexturePresetData preset){
        public static Identity create(TexturePresetDatas data, EntityCategories entityCategories){
            if(!data.presetsByCategory.containsKey(entityCategories))
                return null;

            // TODO : get the right preset based on weight
            List<TexturePresetData> presets = data.presetsByCategory.get(entityCategories);
            presets = feedSharedToPresets(data, presets);

            Random random = new Random();
            return new Identity(entityCategories, presets.get(random.nextInt(presets.size())));
        }

        public static Identity create(TexturePresetDatas data){
            EntityCategories entityCategories = data.getRandomCategory();
            if(!data.presetsByCategory.containsKey(entityCategories))
                return null;

            // TODO : get the right preset based on weight
            List<TexturePresetData> presets = data.presetsByCategory.get(entityCategories);
            presets = feedSharedToPresets(data, presets);

            Random random = new Random();
            return new Identity(entityCategories, presets.get(random.nextInt(presets.size())));
        }

        private static List<TexturePresetData> feedSharedToPresets(TexturePresetDatas data, List<TexturePresetData> presets) {
            if(data.presetsByCategory.containsKey(EntityCategories.SHARED)){
                var shared = data.presetsByCategory.get(EntityCategories.SHARED).getFirst();
                if(shared != null){
                    for (TexturePresetData preset : presets) {
                        preset.withMaterialValues(NpcTextureType.SKIN, shared.getMaterials(NpcTextureType.SKIN));
                        preset.withPatternValues(NpcTextureType.BODY, shared.getPatterns(NpcTextureType.BODY));
                        preset.withPatternValues(NpcTextureType.HEAD, shared.getPatterns(NpcTextureType.HEAD));
                        preset.withPatternValues(NpcTextureType.EAR, shared.getPatterns(NpcTextureType.EAR));
                        preset.withPatternValues(NpcTextureType.NOSE, shared.getPatterns(NpcTextureType.NOSE));
                        preset.withPatternValues(NpcTextureType.SCAR, shared.getPatterns(NpcTextureType.SCAR));
                        preset.withPatternValues(NpcTextureType.FEET, shared.getPatterns(NpcTextureType.FEET));

                        preset.withMaterialValues(NpcTextureType.HAIR, shared.getMaterials(NpcTextureType.HAIR));
                        preset.withPatternValues(NpcTextureType.HAIR, shared.getPatterns(NpcTextureType.HAIR));
                        preset.withPatternValues(NpcTextureType.BEARD, shared.getPatterns(NpcTextureType.BEARD));
                        preset.withPatternValues(NpcTextureType.EYEBROW, shared.getPatterns(NpcTextureType.EYEBROW));
                        preset.withEmissiveEyes(shared.haveEmissiveEyes());

                        preset.withMaterialValues(NpcTextureType.EYE, shared.getMaterials(NpcTextureType.EYE));
                        preset.withPatternValues(NpcTextureType.EYE, shared.getPatterns(NpcTextureType.EYE));

                        preset.withMaterialValues(NpcTextureType.CLOTHE_PRESETS, shared.getMaterials(NpcTextureType.CLOTHE_PRESETS));
                        preset.withPatternValues(NpcTextureType.CLOTHE_PRESETS, shared.getPatterns(NpcTextureType.CLOTHE_PRESETS));
                    }
                }
            }
            return presets;
        }
    }
}
