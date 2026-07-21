package net.sevenstars.middleearth.resources.datas.npc_types.data;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.sevenstars.api.dtos.WeightedItem;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;

import java.util.List;

public class WeightedItemData extends WeightedItem<GearItemData> {
    public WeightedItemData() {
        this.item = new GearItemData();
    }

    public WeightedItemData(Item item) {
        this.item = new GearItemData(item);
    }

    public WeightedItemData(Identifier itemIdentifier) {
        this.item = new GearItemData(itemIdentifier);
    }

    public WeightedItemData(NbtElement element){
        super(element);
        if(element == null)
            return;
        if(element.asString().isPresent())
            this.item = new GearItemData(Identifier.of(element.asString().get()));
        else if(element.asCompound().isPresent())
            this.item = new GearItemData(element.asCompound().get());
    }

    public static WeightedItemData create() {
        return new WeightedItemData(Items.AIR);
    }
    public static WeightedItemData create(Item item) {
        return new WeightedItemData(item);
    }
    public static WeightedItemData create(Identifier itemIdentifier) {
        return new WeightedItemData(itemIdentifier);
    }
    public WeightedItemData withWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public WeightedItemData withColor(int color) {
        this.item.withColor(color);
        return this;
    }

    public WeightedItemData withColors(List<Integer> colors) {
        this.item.withColors(colors);
        return this;
    }

    public WeightedItemData withoutCape() {
        this.item.withoutCape();
        return this;
    }
    public WeightedItemData withCape(BackAttachmentsME cape, int color) {
        this.item.withCape(cape, color);
        return this;
    }
    public WeightedItemData withCape(BackAttachmentsME cape, List<Integer> colors) {
        this.item.withCape(cape, colors);
        return this;
    }

    public WeightedItemData withCape(BackAttachmentsME cape) {
        this.item.withCape(cape);
        return this;
    }

    public WeightedItemData withoutHood() {
        this.item.withoutHood();
        return this;
    }
    public WeightedItemData withHood(HelmetAttachmentsME hood, int color) {
        this.item.withHood(hood, color);
        return this;
    }
    public WeightedItemData withHood(HelmetAttachmentsME hood, List<Integer> colors) {
        this.item.withHood(hood, colors);
        return this;
    }
    public WeightedItemData withHood(HelmetAttachmentsME hood) {
        this.item.withHood(hood);
        return this;
    }

    public WeightedItemData withHood(HelmetAttachmentsME hood, boolean isDown) {
        this.item.withHood(hood, isDown);
        return this;
    }

    @Override
    public NbtElement getNbt() {
        NbtElement nbt = super.getNbt();
        if(nbt == null)
            nbt = new NbtCompound();
        nbt = this.item.getNbt(nbt.asCompound().get());
        return nbt;
    }

}
