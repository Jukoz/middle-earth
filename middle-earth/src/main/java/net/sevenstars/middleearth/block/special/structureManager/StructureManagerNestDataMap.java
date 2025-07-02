package net.sevenstars.middleearth.block.special.structureManager;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;

import java.util.List;

public record StructureManagerNestDataMap(List<StructureManagerNestData> datas){
    public static final Codec<StructureManagerNestDataMap> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(StructureManagerNestData.CODEC, 0, 10).fieldOf("datas").forGetter(StructureManagerNestDataMap::datas)
    ).apply(instance, StructureManagerNestDataMap::new));

    public void addNest(StructureManagerNestData structureManagerNestData) {
        this.datas.add(structureManagerNestData);
    }

    public void alertDeath(LivingEntity entity) {
        String entityUuid = entity.getUuidAsString();
        for(StructureManagerNestData data : datas){
            if(data.getEntityUuidStrings().contains(entityUuid)){
                data.removeEntity(entity);
            }
        }
    }
}
