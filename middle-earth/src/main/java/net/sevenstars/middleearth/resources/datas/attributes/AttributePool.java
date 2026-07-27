package net.sevenstars.middleearth.resources.datas.attributes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
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

    public AttributePool(CompoundTag compound) {
        if(compound == null) return;
        if(compound.contains("pool")){
            pool = new ArrayList<>();
            ListTag list = compound.getList("pool", Tag.TAG_COMPOUND);
            for(int i = 0; i < list.size(); i++){
                pool.add(AttributePoolElement.createFromNbt(list.getCompound(i)));
            }
        }
    }

    public CompoundTag getNbt() {
        CompoundTag nbt = new CompoundTag();
        ListTag list = new ListTag();

        for(var element : pool){
            list.add(element.createNbt());
        }

        nbt.put("pool", list);
        return nbt;
    }

    public boolean apply(LivingEntity entity){
        boolean couldResolveOneAttribute = false;

        for(var element : pool){
            var optAttributeEntry = BuiltInRegistries.ATTRIBUTE.getHolder(element.getIdentifier());
            if(optAttributeEntry.isPresent()){
                var attributeEntry = optAttributeEntry.get();

                var attributeInstance = entity.getAttribute(attributeEntry);
                if(attributeInstance != null){
                    attributeInstance.removeModifiers();
                    attributeInstance.setBaseValue(element.getValue());
                    if(element.hasModifiers()){
                        for(AttributeModifierElement modifier : element.getModifiers()){
                            attributeInstance.addPermanentModifier(new AttributeModifier(modifier.getIdentifier(), modifier.getValue(), modifier.getOperation()));
                        }
                    }
                    couldResolveOneAttribute = true;
                }
            }
        }
        return couldResolveOneAttribute;
    }

    public static boolean reverse(LivingEntity entity){
        for (var identifier : BuiltInRegistries.ATTRIBUTE.keySet()) {
            Holder.Reference<Attribute> attribute = BuiltInRegistries.ATTRIBUTE.getHolder(identifier).orElse(null);
            if (attribute == null) {
                continue;
            }
            var attributeInstance = entity.getAttribute(attribute);
            if (attributeInstance == null) {
                continue;
            }

            double defaultBaseValue = getDefaultAttributeValue(identifier, entity);
            if(defaultBaseValue == -99)
                continue;

            attributeInstance.setBaseValue(defaultBaseValue);
            attributeInstance.removeModifiers();
        }
        return true;
    }

    public static double getDefaultAttributeValue(ResourceLocation identifier, LivingEntity entity) {
        var defaultAttribute = BuiltInRegistries.ATTRIBUTE.get(identifier);
        if (defaultAttribute == null) {
            return -99;
        }

        var defaultAttributeEntry = BuiltInRegistries.ATTRIBUTE.getHolder(identifier);
        if (defaultAttributeEntry.isEmpty()) {
            return -99;
        }

        var defaultAttributeContainer = DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) entity.getType());
        return defaultAttributeContainer.getBaseValue(defaultAttributeEntry.get());
    }
    public static double getDefaultAttributeModifiers(ResourceLocation identifier, LivingEntity entity) {
        var defaultAttribute = BuiltInRegistries.ATTRIBUTE.get(identifier);
        if (defaultAttribute == null) {
            return -99;
        }

        var defaultAttributeEntry = BuiltInRegistries.ATTRIBUTE.getHolder(identifier);
        if (defaultAttributeEntry.isEmpty()) {
            return -99;
        }

        var defaultAttributeContainer = DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) entity.getType());
        return defaultAttributeContainer.getBaseValue(defaultAttributeEntry.get());
    }

    public List<AttributePoolElement> getPool() {
        return pool;
    }
}
