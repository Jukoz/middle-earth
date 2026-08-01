package net.sevenstars.middleearth.resources.datas.attributes;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

public class AttributeModifierElement {
    private EntityAttributeModifier modifier;

    public AttributeModifierElement(NbtCompound modifierNbt) {
        this.modifier = new EntityAttributeModifier(
                MiddleEarth.fetchId(modifierNbt.getString("id").get()),
                modifierNbt.getDouble("value").get(),
                EntityAttributeModifier.Operation.valueOf(modifierNbt.getString("operation").get())
        );
    }

    public AttributeModifierElement(Identifier identifier, double value, EntityAttributeModifier.Operation operation) {
        this.modifier = new EntityAttributeModifier(identifier, value, operation);
    }


    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("id", modifier.id().toString());
        nbt.putDouble("value", modifier.value());
        nbt.putString("operation", modifier.operation().name());
        return nbt;
    }

    public Identifier getIdentifier() {
        return modifier.id();
    }
    public double getValue() {
        return modifier.value();
    }

    public EntityAttributeModifier.Operation getOperation() {
        return modifier.operation();
    }

    public EntityAttributeModifier getEntityAttributeModifier() {
        return modifier;
    }
}
