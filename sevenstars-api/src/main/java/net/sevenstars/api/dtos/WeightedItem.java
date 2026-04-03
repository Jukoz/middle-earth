package net.sevenstars.api.dtos;

import net.minecraft.nbt.NbtCompound;

public abstract class WeightedItem<T> {
    protected int weight;
    protected T item;

    public WeightedItem(){
        this.item = null;
        this.weight = 1;
    }

    public WeightedItem(T item, int weight){
        this.item = item;
        this.weight = weight;
    }

    public WeightedItem(T item){
        this(item, 1);
    }

    public T getItem() {
        return item;
    }

    public int getWeight(){
        return weight;
    }

    public abstract NbtCompound getNbt();

    public boolean isSame(T differentItem) {
        return this.item == differentItem;
    }
}
