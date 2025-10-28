package net.sevenstars.middleearth.resources.datas.attributes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
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
        boolean couldResolveOneAttribute = false;
        for(var element : pool){
            var optAttributeEntry = Registries.ATTRIBUTE.getEntry(element.getIdentifier());
            if(optAttributeEntry.isPresent()){
                var attributeEntry = optAttributeEntry.get();

                var attributeInstance = entity.getAttributeInstance(attributeEntry);
                if(attributeInstance != null){
                    attributeInstance.setBaseValue(element.getValue());
                    if(element.hasModifier() && !attributeInstance.hasModifier(element.getModifierIdentifier())){
                        attributeInstance.addPersistentModifier(new EntityAttributeModifier(
                                element.getModifierIdentifier(),
                                element.getModifierValue(),
                                EntityAttributeModifier.Operation.valueOf(element.getModifierType())));
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

            var defaultAttribute = Registries.ATTRIBUTE.get(identifier);
            if (defaultAttribute == null) {
                continue;
            }

            var defaultAttributeEntry = Registries.ATTRIBUTE.getEntry(identifier);
            if (defaultAttributeEntry.isEmpty()) {
                continue;
            }

            var defaultAttributeContainer = DefaultAttributeRegistry.get((EntityType<? extends LivingEntity>) entity.getType());
            var defaultBaseValue = defaultAttributeContainer.getBaseValue(defaultAttributeEntry.get());

            attributeInstance.setBaseValue(defaultBaseValue);
            attributeInstance.clearModifiers();
        }
        return true;
    }

    public boolean isBuffReversed(Identifier id){
        return pool.stream().anyMatch(item -> item.getIdentifier().equals(id) && item.isBuffReversed());
    }

    public Double getEntityCurrentAttributeValue(LivingEntity entity, Identifier id) {
        if(Registries.ATTRIBUTE.getEntry(id).isPresent()){
            return entity.getAttributeInstance(Registries.ATTRIBUTE.getEntry(id).get()).getValue();
        }
        return null;
    }

    public List<AttributePoolElement> getPool() {
        return pool;
    }
}
