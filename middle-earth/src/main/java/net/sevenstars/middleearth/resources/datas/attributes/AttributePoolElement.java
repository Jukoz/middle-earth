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
    private Identifier modifierIdentifier;
    private Double modifierValue;
    private String modifierType;
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

        newElement.withIdentifier(Identifier.of(nbtCompound.getString("id").get()));

        if(nbtCompound.contains("value"))
            newElement.withDefineValue(nbtCompound.getDouble("value").get());
        else if(nbtCompound.contains("min") && nbtCompound.contains("max"))
            newElement.withMinMaxValue(nbtCompound.getDouble("min").get(), nbtCompound.getDouble("max").get());

        if(nbtCompound.contains("buffReversed") && nbtCompound.getBoolean("buffReversed").get())
            newElement.withBuffReversed();

        if(nbtCompound.contains("modifierId")){
            newElement.withModifier(
                    Identifier.of(nbtCompound.getString("modifierId").get()),
                    nbtCompound.getDouble("modifierValue").get(),
                    nbtCompound.getString("modifierType").get());
        }

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

        if(this.modifierIdentifier != null && this.modifierValue != null && this.modifierType != null){
            nbtCompound.putString("modifierId", this.modifierIdentifier.toString());
            nbtCompound.putDouble("modifierValue", this.modifierValue);
            nbtCompound.putString("modifierType", this.modifierType.toString());
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

    public AttributePoolElement withModifier(Identifier identifier, double value) {
        withModifier(identifier, value, "ADD_MULTIPLIED_TOTAL");
        return this;
    }

    public AttributePoolElement withModifier(Identifier identifier, double value, String type) {
        this.modifierIdentifier = identifier;
        this.modifierValue = value;
        this.modifierType = type;
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

    public boolean hasModifier(){
        return this.modifierIdentifier != null && this.modifierValue != null;
    }

    public Identifier getModifierIdentifier(){
        return this.modifierIdentifier;
    }

    public Double getModifierValue(){
        return this.modifierValue;
    }

    public String getModifierType(){
        return this.modifierType;
    }

    public boolean isBuffReversed(){
        return this.isBuffReversed != null && this.isBuffReversed;
    }
}
