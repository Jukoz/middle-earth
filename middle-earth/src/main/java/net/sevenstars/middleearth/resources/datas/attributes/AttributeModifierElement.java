package net.sevenstars.middleearth.resources.datas.attributes;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.sevenstars.middleearth.MiddleEarth;

public class AttributeModifierElement {
    private AttributeModifier modifier;

    public AttributeModifierElement(CompoundTag modifierNbt) {
        this.modifier = new AttributeModifier(
                MiddleEarth.fetchId(modifierNbt.getString("id")),
                modifierNbt.getDouble("value"),
                AttributeModifier.Operation.valueOf(modifierNbt.getString("operation"))
        );
    }

    public AttributeModifierElement(ResourceLocation identifier, double value, AttributeModifier.Operation operation) {
        this.modifier = new AttributeModifier(identifier, value, operation);
    }


    public CompoundTag toNbt() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("id", modifier.id().toString());
        nbt.putDouble("value", modifier.amount());
        nbt.putString("operation", modifier.operation().name());
        return nbt;
    }

    public ResourceLocation getIdentifier() {
        return modifier.id();
    }
    public double getValue() {
        return modifier.amount();
    }

    public AttributeModifier.Operation getOperation() {
        return modifier.operation();
    }

    public AttributeModifier getEntityAttributeModifier() {
        return modifier;
    }
}
