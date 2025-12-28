package net.sevenstars.middleearth.resources.datas.npcs.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;
import net.sevenstars.middleearth.resources.datas.races.data.TexturePresetData;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;
import net.sevenstars.middleearth.utils.IdentifierUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TexturePresets {
    public static final Codec<TexturePresets> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        NbtCompound.CODEC.fieldOf("categories").forGetter(TexturePresets::getNbt)
    ).apply(instance, TexturePresets::new));

    HashMap<EntityCategory, List<TexturePresetData>> presetsByCategory;

    public TexturePresets(NbtCompound categories) {
        if(categories == null) return;
        presetsByCategory = new HashMap<>();

        for(EntityCategory category : EntityCategory.values()){
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

    public TexturePresets(HashMap<EntityCategory, List<TexturePresetData>> sourceDatas) {
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

    public NbtCompound getNbt() {
        NbtCompound newNbt = new NbtCompound();
        for(EntityCategory category : presetsByCategory.keySet()){
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
        List<String> materials = identity.preset.getMaterials(npcTextureType);
        if(materials == null || materials.isEmpty())
            return null;
        Random random = new Random();
        int materialIndex = random.nextInt(materials.size());
        return Identifier.of(MiddleEarth.MOD_ID, materials.get(materialIndex));
    }

    public static Identifier getRawPattern(Identity identity, NpcTextureType npcTextureType) {
        List<String> patterns = identity.preset.getPatterns(npcTextureType);
        if(patterns == null || patterns.isEmpty())
            return null;
        Random random = new Random();
        String value = patterns.get(random.nextInt(patterns.size()));
        if(value.equals(TexturePresetData.EMPTY_VALUE_KEY))
            return null;
        return Identifier.of(MiddleEarth.MOD_ID, value);
    }

    public static Identifier getTextureWithMaterial(Identity identity, NpcTextureType npcTextureType) {
        List<String> patterns = identity.preset.getPatterns(npcTextureType);
        List<String> materials = identity.preset.getMaterials(npcTextureType);
        if(patterns == null || patterns.isEmpty() || materials == null || materials.isEmpty())
            return null;

        Random random = new Random();
        int patternIndex = random.nextInt(patterns.size());
        int materialIndex = random.nextInt(materials.size());
        return  Identifier.of(MiddleEarth.MOD_ID, patterns.get(patternIndex) + "_" + materials.get(materialIndex));
    }

    public Identifier getTextureWithMaterial(Identity identity, NpcTextureType npcTextureType, Identifier material) {
        List<String> patterns = identity.preset.getPatterns(npcTextureType);
        Random random = new Random();
        int patternIndex = random.nextInt(patterns.size());
        return Identifier.of(MiddleEarth.MOD_ID, patterns.get(patternIndex) + "_" + material.getPath());
    }


    public Boolean haveEmissiveEyes(Identity identity) {
        return identity.preset.haveEmissiveEyes();
    }

    public EntityCategory getRandomCategory() {
        Random random = new Random();
        boolean containsMale = this.presetsByCategory.containsKey(EntityCategory.MALE);
        boolean containsFemale = this.presetsByCategory.containsKey(EntityCategory.FEMALE);
        // Shared is not a valid sex
        if(containsMale && containsFemale)
            return (random.nextBoolean()) ? EntityCategory.MALE : EntityCategory.FEMALE;
        else if(containsMale)
            return EntityCategory.MALE;
        else if(containsFemale)
            return EntityCategory.FEMALE;

        return EntityCategory.MALE;
    }

    public record Identity(EntityCategory category, TexturePresetData preset){
        public static Identity create(TexturePresets data, EntityCategory entityCategory){
            if(!data.presetsByCategory.containsKey(entityCategory))
                return null;

            // TODO : get the right preset based on weight
            List<TexturePresetData> presets = data.presetsByCategory.get(entityCategory);
            presets = feedSharedToPresets(data, presets);

            Random random = new Random();
            return new Identity(entityCategory, presets.get(random.nextInt(presets.size())));
        }

        public static Identity create(TexturePresets data){
            EntityCategory entityCategory = data.getRandomCategory();
            if(!data.presetsByCategory.containsKey(entityCategory))
                return null;

            // TODO : get the right preset based on weight
            List<TexturePresetData> presets = data.presetsByCategory.get(entityCategory);
            presets = feedSharedToPresets(data, presets);

            Random random = new Random();
            return new Identity(entityCategory, presets.get(random.nextInt(presets.size())));
        }

        private static List<TexturePresetData> feedSharedToPresets(TexturePresets data, List<TexturePresetData> presets) {
            if(data.presetsByCategory.containsKey(EntityCategory.SHARED)){
                var shared = data.presetsByCategory.get(EntityCategory.SHARED).getFirst();
                if(shared != null){
                    for (TexturePresetData preset : presets) {
                        preset.withMaterialValues(NpcTextureType.SKIN, shared.getMaterials(NpcTextureType.SKIN));
                        preset.withPatternValues(NpcTextureType.BODY, shared.getPatterns(NpcTextureType.BODY));
                        preset.withPatternValues(NpcTextureType.HEAD, shared.getPatterns(NpcTextureType.HEAD));
                        preset.withPatternValues(NpcTextureType.EAR, shared.getPatterns(NpcTextureType.EAR));
                        preset.withPatternValues(NpcTextureType.NOSE, shared.getPatterns(NpcTextureType.NOSE));
                        preset.withPatternValues(NpcTextureType.SCAR, shared.getPatterns(NpcTextureType.SCAR));

                        preset.withMaterialValues(NpcTextureType.HAIR, shared.getMaterials(NpcTextureType.HAIR));
                        preset.withPatternValues(NpcTextureType.HAIR, shared.getPatterns(NpcTextureType.HAIR));
                        preset.withPatternValues(NpcTextureType.BEARD, shared.getPatterns(NpcTextureType.BEARD));
                        preset.withPatternValues(NpcTextureType.EYEBROW, shared.getPatterns(NpcTextureType.EYEBROW));
                        preset.withEmissiveEyes(shared.haveEmissiveEyes());

                        preset.withMaterialValues(NpcTextureType.EYE, shared.getMaterials(NpcTextureType.EYE));
                        preset.withPatternValues(NpcTextureType.EYE, shared.getPatterns(NpcTextureType.EYE));

                        preset.withMaterialValues(NpcTextureType.CLOTHING, shared.getMaterials(NpcTextureType.CLOTHING));
                        preset.withPatternValues(NpcTextureType.CLOTHING, shared.getPatterns(NpcTextureType.CLOTHING));
                    }
                }
            }
            return presets;
        }
    }
}
