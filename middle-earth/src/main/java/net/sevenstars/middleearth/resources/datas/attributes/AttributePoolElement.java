package net.sevenstars.middleearth.resources.datas.attributes;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        withModifier(identifier, value, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, false);
        return this;
    }

    public AttributePoolElement withModifier(Identifier identifier, double value, EntityAttributeModifier.Operation operation, boolean isReversed) {
        if(modifiers == null)
            modifiers = new ArrayList<>();

        modifiers.add(new AttributeModifierElement(identifier, value, operation, isReversed));
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
        return modifiers;
    }
}
