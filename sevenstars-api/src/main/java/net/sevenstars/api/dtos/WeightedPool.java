package net.sevenstars.api.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

public class WeightedPool<T extends WeightedItem> {
    final static Random RANDOM = new Random();

    public List<T> elements;

    public WeightedPool(){
        this.elements = new ArrayList<>();
    }

    public WeightedPool(Stream<List<T>> sets) {
        this.elements = new ArrayList<>();
        List<T> elements = sets.flatMap(List::stream).toList();
        this.elements.addAll(elements);
    }

    public WeightedPool(List<T> elements){
        this.elements = elements == null ? new ArrayList<>() : new ArrayList<>(elements);
    }
    public WeightedPool(T elements){
        this.elements = elements == null ? new ArrayList<>() : new ArrayList<>(List.of(elements));
    }

    public T get(int index){
        return this.elements.get(index);
    }

    public T getRandom(){
        int maximumWeight = elements.stream().mapToInt(x -> x.weight).sum();
        if(maximumWeight == 0)
            return null;

        float randomIndex = RANDOM.nextInt(maximumWeight);
        int currentWeight = 0;

        for (var element : elements) {
            currentWeight += element.weight;

            if (randomIndex < currentWeight) {
                return element;
            }
        }
        return null;
    }

    public boolean isEmpty(){
        return this.elements.isEmpty();
    }

    public boolean isFilled(){
        return !isEmpty();
    }

    public int size(){
        if(this.elements == null)
            return 0;
        return this.elements.size();
    }

    public ListTag getNbt() {
        ListTag nbtList = new ListTag();
        for(int i = 0; i < this.elements.size(); i++){
            nbtList.add(i, toCompoundListElement(this.elements.get(i).getNbt()));
        }
        return nbtList;
    }

    public static CompoundTag toCompoundListElement(Tag element) {
        if (element instanceof CompoundTag compound) {
            return compound;
        }
        if (element instanceof StringTag stringTag) {
            CompoundTag compound = new CompoundTag();
            compound.putString("id", stringTag.getAsString());
            return compound;
        }
        throw new IllegalArgumentException("Weighted list entries must serialize as a compound or identifier string");
    }

    public void add(T value){
        this.elements.add(value);
    }

    public void addAll(List<T> values){
        this.elements.addAll(values);
    }

    public void clear(){
        this.elements.clear();
    }
}
