package net.sevenstars.api.dtos;

import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

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
        this.elements = elements;
    }
    public WeightedPool(T elements){
        this.elements = List.of(elements);
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

    public NbtList getNbt() {
        NbtList nbtList = new NbtList();
        for(int i = 0; i < this.elements.size(); i++){
            nbtList.add(i, this.elements.get(i).getNbt());
        }
        return nbtList;
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
