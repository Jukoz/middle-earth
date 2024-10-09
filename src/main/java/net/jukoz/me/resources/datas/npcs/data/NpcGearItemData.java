package net.jukoz.me.resources.datas.npcs.data;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.recipe.ModTags;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.List;

public class NpcGearItemData {
    private Item item;
    private Integer color = null;
    private Integer weight = null;

    public NpcGearItemData() {
        this.item = Items.AIR;
    }
    public NpcGearItemData(Item item) {
        this.item = item;
    }
    public NpcGearItemData(Identifier itemIdentifier) {
        this.item = getItemFromId(itemIdentifier);
    }

    public NpcGearItemData withWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public NpcGearItemData withColor(int color) {
        this.color = color;
        return this;
    }

    private static Item getItemFromId(Identifier itemId){
        return Registries.ITEM.get(itemId);
    }

    private static Identifier getIdentifierFromItem(Item item){
        return Registries.ITEM.getId(item);
    }

    public ItemStack getItem(){
        ItemStack itemStack = new ItemStack(this.item);
        if(this.color != null){
            List<TagKey<Item>> tags = itemStack.streamTags().toList();
            if(tags.contains(ItemTags.DYEABLE)){
                itemStack.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(this.color, true));
            }
            else if(itemStack.isIn(ModTags.DYEABLE)){
                itemStack.set(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(this.color, true));
            }
        }
        return itemStack;
    }

    public Integer getWeight(){
        Integer weight = this.weight;
        if(weight == null)
            weight = 1;
        return weight;
    }
    public Integer getColor(){
        return this.color;
    }
    public Identifier getItemIdentifier() {
        return getIdentifierFromItem(this.item);
    }

    public static NbtCompound createNbt(NpcGearItemData gearItemData){
        NbtCompound nbt = new NbtCompound();
        nbt.putString("id", gearItemData.getItemIdentifier().toString());

        Integer weight = gearItemData.weight;
        if(weight != null)
            nbt.putInt("weight", weight);

        Integer color = gearItemData.color;
        if(color != null)
            nbt.putInt("color", color);
        return nbt;
    }

    public static NpcGearItemData readNbt(NbtCompound nbt){
        Identifier id = Identifier.of(nbt.getString("id"));
        NpcGearItemData npcGearItemData = new NpcGearItemData(id);
        if(nbt.get("weight") != null){
            npcGearItemData.weight = nbt.getInt("weight");
        }
        if(nbt.get("color") != null){
            npcGearItemData.color = nbt.getInt("color");
        }
        return npcGearItemData;
    }
}
