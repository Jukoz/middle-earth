package net.sevenstars.middleearth.resources.datas.races.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NpcTextureData {
    HashMap<NpcTextureDataCategory, List<NpcTextureDataPreset>> presetsByCategory;

    public NpcTextureData(NbtCompound compound) {
        if(compound == null) return;
        presetsByCategory = new HashMap<>();

        for(NpcTextureDataCategory category : NpcTextureDataCategory.values()){
            NbtList nbtListPresets = compound.getList(category.name(), NbtElement.COMPOUND_TYPE);
            if(nbtListPresets != null){
                List<NpcTextureDataPreset> dataPresetList = new ArrayList<>();
                for(int i = 0; i < nbtListPresets.size(); i++){
                    NpcTextureDataPreset fetchedPreset = new NpcTextureDataPreset(nbtListPresets.getCompound(i));
                    dataPresetList.add(fetchedPreset);
                }
                presetsByCategory.put(category, dataPresetList);
            }
        }
    }
    public NpcTextureData(HashMap<NpcTextureDataCategory, List<NpcTextureDataPreset>> sourceDatas) {
        presetsByCategory = sourceDatas;
    }
}
