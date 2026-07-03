package net.sevenstars.middleearth.resources.datas.attributes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
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
    public AttributePool addElements(List<AttributePoolElement> elements){
        pool.addAll(elements);
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
        boolean couldResolveOneAttribute = false;

        for(var element : pool){
            var optAttributeEntry = Registries.ATTRIBUTE.getEntry(element.getIdentifier());
            if(optAttributeEntry.isPresent()){
                var attributeEntry = optAttributeEntry.get();

                var attributeInstance = entity.getAttributeInstance(attributeEntry);
                if(attributeInstance != null){
                    attributeInstance.clearModifiers();
                    attributeInstance.setBaseValue(element.getValue());
                    if(element.hasModifiers()){
                        for(AttributeModifierElement modifier : element.getModifiers()){
                            attributeInstance.addPersistentModifier(new EntityAttributeModifier(modifier.getIdentifier(), modifier.getValue(), modifier.getOperation()));
                        }
                    }
                    couldResolveOneAttribute = true;
                }
            }
        }
        return couldResolveOneAttribute;
    }

    public static boolean reverse(LivingEntity entity){
        for (var identifier : Registries.ATTRIBUTE.getIds()) {
            var attributeInstance = entity.getAttributeInstance(Registries.ATTRIBUTE.getEntry(identifier).get());
            if (attributeInstance == null) {
                continue;
            }

            double defaultBaseValue = getDefaultAttributeValue(identifier, entity);
            if(defaultBaseValue == -99)
                continue;

            attributeInstance.setBaseValue(defaultBaseValue);
            attributeInstance.clearModifiers();
        }
        return true;
    }

    public static double getDefaultAttributeValue(Identifier identifier, LivingEntity entity) {
        var defaultAttribute = Registries.ATTRIBUTE.get(identifier);
        if (defaultAttribute == null) {
            return -99;
        }

        var defaultAttributeEntry = Registries.ATTRIBUTE.getEntry(identifier);
        if (defaultAttributeEntry.isEmpty()) {
            return -99;
        }

        var defaultAttributeContainer = DefaultAttributeRegistry.get((EntityType<? extends LivingEntity>) entity.getType());
        return defaultAttributeContainer.getBaseValue(defaultAttributeEntry.get());
    }

    public List<AttributePoolElement> getPool() {
        return pool;
    }
}
