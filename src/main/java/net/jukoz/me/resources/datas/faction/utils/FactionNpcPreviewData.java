package net.jukoz.me.resources.datas.faction.utils;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Optional;

public class FactionNpcPreviewData{
    private HashMap<EquipmentSlot, ItemStack> gears;

    /**
     * Faction Npc Preview Data constructor, for showcasing specific entities with armors and weapons
     * @param head Head equipment [Helmet]
     * @param chest Chest equipment [Chestplate]
     * @param legs Leg equipment [Leggings]
     * @param feet Feet equipment [Boots]
     * @param mainHand Item on the left of the entity (back view)
     * @param offHand Item on the right of the entity (front view)
     */
    public FactionNpcPreviewData(Item head, Item chest, Item legs, Item feet, Item mainHand, Item offHand){
        assignItems(head, chest, legs, feet, mainHand, offHand);
    }


    public FactionNpcPreviewData(Optional<NbtCompound> optionalPreviewGearNbt) {
        if(optionalPreviewGearNbt.isEmpty()){
            return;
        }
        NbtCompound compound = optionalPreviewGearNbt.get();
        assignItems(
                getItem(compound.getString(EquipmentSlot.HEAD.asString())),
                getItem(compound.getString(EquipmentSlot.CHEST.asString())),
                getItem(compound.getString(EquipmentSlot.LEGS.asString())),
                getItem(compound.getString(EquipmentSlot.FEET.asString())),
                getItem(compound.getString(EquipmentSlot.MAINHAND.asString())),
                getItem(compound.getString(EquipmentSlot.OFFHAND.asString()))
        );
    }

    private void assignItems(Item head, Item chest, Item legs, Item feet, Item mainHand, Item offHand){
        this.gears = new HashMap<>();
        this.gears.put(EquipmentSlot.HEAD, new ItemStack(head));
        this.gears.put(EquipmentSlot.CHEST, new ItemStack(chest));
        this.gears.put(EquipmentSlot.LEGS, new ItemStack(legs));
        this.gears.put(EquipmentSlot.FEET, new ItemStack(feet));
        this.gears.put(EquipmentSlot.MAINHAND, new ItemStack(mainHand));
        this.gears.put(EquipmentSlot.OFFHAND, new ItemStack(offHand));
    }

    private static Item getItem(String itemId){
        return Registries.ITEM.get(Identifier.of(itemId));
    }

    public ItemStack get(EquipmentSlot slot){
        if(gears == null || slot == null)
            return null;
        return gears.get(slot);
    }

    public Optional<NbtCompound> getNbt() {
        if(gears == null || gears.isEmpty())
            return Optional.empty();

        NbtCompound nbt = new NbtCompound();
        for(EquipmentSlot slot : gears.keySet()){
            nbt.putString(slot.name().toLowerCase(), gears.get(slot).getItem().toString());
        }
        return Optional.of(nbt);
    }
}
