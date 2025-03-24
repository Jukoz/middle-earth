package net.sevenstars.middleearth.resources.datas.attributes;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.Random;

public class AttributePoolElement {
    private Identifier identifier;
    private Double value;
    private Double valueMax;
    private Boolean isBuffReversed;

    public AttributePoolElement(){
        this.isBuffReversed = false;
    }
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

        newElement.withIdentifier(Identifier.of(nbtCompound.getString("id")));

        if(nbtCompound.contains("value"))
            newElement.withDefineValue(nbtCompound.getDouble("value"));
        else if(nbtCompound.contains("min") && nbtCompound.contains("max"))
            newElement.withMinMaxValue(nbtCompound.getDouble("min"), nbtCompound.getDouble("max"));

        if(nbtCompound.contains("buffReversed") && nbtCompound.getBoolean("buffReversed"))
            newElement.withBuffReversed();

        return newElement;
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

        if(this.isBuffReversed != null && this.isBuffReversed)
            nbtCompound.putBoolean("buffReversed", true);

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

    public AttributePoolElement withBuffReversed() {
        this.isBuffReversed = true;
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
    public boolean isBuffReversed(){
        return this.isBuffReversed != null && this.isBuffReversed;
    }


}
