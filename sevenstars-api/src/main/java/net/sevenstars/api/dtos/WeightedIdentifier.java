package net.sevenstars.api.dtos;

import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class WeightedIdentifier extends WeightedItem<Identifier> {
    public WeightedIdentifier(NbtElement element){
        super(element);

        if(element.asString().isPresent()){
            this.item = Identifier.of(element.asString().get());
        } else if(element.asCompound().isPresent()) {
            var potentialId = element.asCompound().get().getString("id");
            potentialId.ifPresent(integer -> this.item = Identifier.of(potentialId.get()));
        }
    }
    public WeightedIdentifier(Identifier value) {
        super(value);
    }
    public WeightedIdentifier(Identifier value, int i) {
        super(value, i);
    }
    public static WeightedIdentifier fromIdentifier(Identifier id){
        return new WeightedIdentifier(id, 1);
    }
    public static WeightedIdentifier fromKey(RegistryKey key){
        return new WeightedIdentifier(key.getValue(), 1);
    }
    public WeightedIdentifier withWeight(int weight){
        this.weight = weight;
        return this;
    }

    @Override
    public NbtElement getNbt(){
        NbtElement newNbt = super.getNbt();
        if(newNbt == null)
            return NbtString.of(item.toString());

        newNbt.asCompound().get().putString("id", this.item.toString());
        return newNbt;
    }
}
