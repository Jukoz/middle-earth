package net.sevenstars.middleearth.resources.datas.attributes;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class AttributePool {
    private List<AttributePoolElement> pool;

    public AttributePool(){
        pool = new ArrayList<>();
    }

    public AttributePool addElement(AttributePoolElement element){
        pool.add(element);
        return this;
    }

    public AttributePool(NbtCompound compound) {
        if(compound == null) return;
        if(compound.contains("pool")){
            pool = new ArrayList<>();
            var list =  compound.getList("pool");
            for(int i = 0; i < list.get().size(); i++){
                pool.add(AttributePoolElement.createFromNbt(list.get().getCompound(i).get()));
            }
        }
    }

    public NbtCompound getNbt() {
        NbtCompound nbt = new NbtCompound();
        NbtList list = new NbtList();

        for(var element : pool){
            list.add(element.createNbt());
        }

        nbt.put("pool", list);
        return nbt;
    }

    public boolean apply(LivingEntity entity){
        for(var element : pool){
            var attributeInstance = entity.getAttributeInstance(Registries.ATTRIBUTE.getEntry(element.getIdentifier()).get());
            if(attributeInstance != null){
                attributeInstance.setBaseValue(element.getValue());
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean reverse(LivingEntity entity){
        for(var identifier : Registries.ATTRIBUTE.getIds()){
            var attributeInstance = entity.getAttributeInstance(Registries.ATTRIBUTE.getEntry(identifier).get());
            if(attributeInstance != null){
                var defaultAttribute = Registries.ATTRIBUTE.get(identifier);
                if(defaultAttribute != null)
                    attributeInstance.setBaseValue(defaultAttribute.getDefaultValue());
            }
        }
        return true;
    }

    public boolean isBuffReversed(Identifier id){
        return pool.stream().anyMatch(item -> item.getIdentifier().equals(id) && item.isBuffReversed());
    }

    public Double getEntityCurrentAttributeValue(LivingEntity entity, Identifier id) {
        return entity.getAttributeInstance(Registries.ATTRIBUTE.getEntry(id).get()).getValue();
    }

    public List<AttributePoolElement> getPool() {
        return pool;
    }
}
