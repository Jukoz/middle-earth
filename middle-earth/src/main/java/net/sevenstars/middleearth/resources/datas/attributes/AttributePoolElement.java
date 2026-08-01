package net.sevenstars.middleearth.resources.datas.attributes;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.*;

public class AttributePoolElement {
    private Identifier identifier;
    private Double value;
    private Double valueMax;
    private List<AttributeModifierElement> modifiers;

    public static AttributePoolElement create(RegistryEntry<EntityAttribute> attributeEntry, double defineValue){
        return new AttributePoolElement()
                .withIdentifier(Identifier.of(attributeEntry.getIdAsString()))
                .withDefineValue(defineValue);
    }
    public static AttributePoolElement create(RegistryEntry<EntityAttribute> attributeEntry, double min, double max){
        return new AttributePoolElement()
                .withIdentifier(Identifier.of(attributeEntry.getIdAsString()))
                .withMinMaxValue(min, max);
    }

    public static AttributePoolElement createFromNbt(NbtCompound nbtCompound){
        var newElement = new AttributePoolElement();
        newElement.withIdentifier(Identifier.of(nbtCompound.getString("id").get()));

        if(nbtCompound.contains("value"))
            newElement.withDefineValue(nbtCompound.getDouble("value").get());
        else if(nbtCompound.contains("min") && nbtCompound.contains("max"))
            newElement.withMinMaxValue(nbtCompound.getDouble("min").get(), nbtCompound.getDouble("max").get());

        if(nbtCompound.contains("modifiers")){
            newElement.withModifiers(nbtCompound.getList("modifiers").get());
        }

        return newElement;
    }

    private void withModifiers(NbtList modifierList) {
        this.modifiers = new ArrayList<>();
        modifierList.forEach(modifierNbt -> {
            modifiers.add(new AttributeModifierElement(modifierNbt.asCompound().get()));
        });
    }

    public NbtCompound createNbt(){
        var nbtCompound = new NbtCompound();

        nbtCompound.putString("id", this.identifier.toString());
        if(this.valueMax == null)
            nbtCompound.putDouble("value", this.value);
        else{
            nbtCompound.putDouble("min", this.value);
            nbtCompound.putDouble("max", this.valueMax);
        }

        if(this.modifiers != null && !this.modifiers.isEmpty()){
            NbtList modifiersList = new NbtList();
            for(AttributeModifierElement modifier : this.modifiers){
                modifiersList.add(modifier.toNbt());
            }
            nbtCompound.put("modifiers", modifiersList);
        }
        return nbtCompound;
    }

    public AttributePoolElement withIdentifier(Identifier newIdentifier) {
        this.identifier = newIdentifier;
        return this;
    }

    public AttributePoolElement withDefineValue(Double newDefineValue) {
        this.value = newDefineValue;
        return this;
    }

    public AttributePoolElement withMinMaxValue(Double newMinValue, Double newMaxValue) {
        this.value = newMinValue;
        this.valueMax = newMaxValue;
        return this;
    }

    public AttributePoolElement withModifier(Identifier identifier, double value) {
        withModifier(identifier, value, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        return this;
    }

    public AttributePoolElement withModifier(Identifier identifier, double value, EntityAttributeModifier.Operation operation) {
        if(modifiers == null)
            modifiers = new ArrayList<>();

        modifiers.add(new AttributeModifierElement(identifier, value, operation));
        return this;
    }

    public Identifier getIdentifier(){
        return this.identifier;
    }

    public double getValue(){
        if(this.valueMax == null){
            return this.value;
        }
        Random random = new Random();
        return random.nextDouble(value, valueMax);
    }

    public boolean hasModifiers(){
        return this.modifiers != null && !this.modifiers.isEmpty();
    }

    public List<AttributeModifierElement> getModifiers() {
        if(modifiers == null)
            return new ArrayList<>();
        return modifiers;
    }

    public static NbtCompound createAttributeNbtListFromPlayer(PlayerEntity player) {
        NbtList attributeList = new NbtList();
        var registry = player.getWorld().getRegistryManager().getOptional(RegistryKeys.ATTRIBUTE).get();
        Collection<EntityAttributeInstance> attributes = new ArrayList<>();
        var entries = registry.getIndexedEntries();
        for(var entry : entries){
            EntityAttributeInstance instance = player.getAttributeInstance(entry);
            if(instance == null)
                continue;
            attributes.add(instance);
        }
        attributes.forEach(attribute -> {
            AttributePoolElement attributePoolElement = new AttributePoolElement();
            attributePoolElement.withIdentifier(MiddleEarth.fetchId(attribute.getAttribute().getIdAsString()));
            attributePoolElement.withDefineValue(attribute.getBaseValue());
            for(var modifier : attribute.getModifiers())
                attributePoolElement.withModifier(modifier.id(), modifier.value(), modifier.operation());
            attributeList.add(attributePoolElement.createNbt());
        });
        NbtCompound compound = new NbtCompound();
        compound.put("attributes", attributeList);
        return compound;
    }

    public static List<AttributePoolElement> obtainAttributeList(NbtCompound nbtCompound) {
        List<AttributePoolElement> attributePoolElementList = new ArrayList<>();

        NbtList nbtList = nbtCompound.getListOrEmpty("attributes");

        nbtList.forEach(attribute -> {
            attributePoolElementList.add(AttributePoolElement.createFromNbt(attribute.asCompound().get()));
        });
        return attributePoolElementList;
    }
}
