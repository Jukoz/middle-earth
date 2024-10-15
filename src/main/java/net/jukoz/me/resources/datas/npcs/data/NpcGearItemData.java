package net.jukoz.me.resources.datas.npcs.data;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.dataComponents.CapeDataComponent;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.item.dataComponents.HoodDataComponent;
import net.jukoz.me.item.utils.armor.capes.ModCapes;
import net.jukoz.me.item.utils.armor.hoods.ModHoodStates;
import net.jukoz.me.item.utils.armor.hoods.ModHoods;
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
    private ModCapes cape = null;
    private ModHoods hood = null;
    private boolean isDown = false;

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

    public NpcGearItemData withCape(ModCapes cape) {
        this.cape = cape;
        return this;
    }
    public NpcGearItemData withHood(ModHoods hood) {
        this.hood = hood;
        if(this.hood.getConstantState() != null){
            this.isDown = this.hood.getConstantState() == ModHoodStates.DOWN;
        }
        return this;
    }
    public NpcGearItemData withHood(ModHoods hood, boolean isDown) {
        withHood(hood);
        if(this.hood.getConstantState() == null) {
            this.isDown = isDown;
        }
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
            if(tags.contains(ItemTags.DYEABLE))
                itemStack.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(this.color, true));
            else if(itemStack.isIn(ModTags.DYEABLE))
                itemStack.set(ModDataComponentTypes.DYE_DATA, new CustomDyeableDataComponent(this.color));
        }
        if(cape != null)
            itemStack.set(ModDataComponentTypes.CAPE_DATA, new CapeDataComponent(cape));
        else if(hood != null){
            if(this.hood.getConstantState() != null){
                this.isDown = this.hood.getConstantState() == ModHoodStates.DOWN;
            }
            itemStack.set(ModDataComponentTypes.HOOD_DATA, new HoodDataComponent(isDown, hood));
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

        if(gearItemData.cape != null)
            nbt.putString("cape", gearItemData.cape.getName().toLowerCase());

        if(gearItemData.hood != null){
            nbt.putString("hood", gearItemData.hood.getName().toLowerCase());
            nbt.putBoolean("hood_is_down", gearItemData.isDown);
        }

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
        if(nbt.get("cape") != null){
            npcGearItemData.cape = ModCapes.valueOf(nbt.getString("cape").toUpperCase());
        }
        if(nbt.get("hood") != null){
            npcGearItemData.hood = ModHoods.valueOf(nbt.getString("hood").toUpperCase());
            npcGearItemData.isDown = nbt.getBoolean("hood_is_down");
        }
        return npcGearItemData;
    }
}
