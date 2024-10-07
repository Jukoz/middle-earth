package net.jukoz.me.resources.datas.factions.data;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Optional;

public class NpcPreview {
    public final HashMap<EquipmentSlot, ItemStack> data;

    /**
     * Faction Npc Preview Data constructor, for showcasing specific entities with armors and weapons
     * @param head Head equipment [Helmet]
     * @param chest Chest equipment [Chestplate]
     * @param legs Leg equipment [Leggings]
     * @param feet Feet equipment [Boots]
     * @param mainHand Item on the left of the entity
     * @param offHand Item on the right of the entity
     */
    public NpcPreview(Item head, Item chest, Item legs, Item feet, Item mainHand, Item offHand){
        data = new HashMap<>();
        assignItems(head, chest, legs, feet, mainHand, offHand);
    }

    public NpcPreview(Optional<NbtCompound> optionalPreviewGearNbt) {
        this.data = new HashMap<>();

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

    public ItemStack get(EquipmentSlot slot) {
        if(!data.containsKey(slot))
            return new ItemStack(Items.AIR);
        return data.get(slot);
    }

    private void assignItems(Item head, Item chest, Item legs, Item feet, Item mainHand, Item offHand){
        data.put(EquipmentSlot.HEAD, new ItemStack((head == null) ? Items.AIR : head));
        data.put(EquipmentSlot.CHEST, new ItemStack((chest == null) ? Items.AIR : chest));
        data.put(EquipmentSlot.LEGS, new ItemStack((legs == null) ? Items.AIR : legs));
        data.put(EquipmentSlot.FEET, new ItemStack((feet == null) ? Items.AIR : feet));
        data.put(EquipmentSlot.MAINHAND, new ItemStack((mainHand == null) ? Items.AIR : mainHand));
        data.put(EquipmentSlot.OFFHAND, new ItemStack((offHand == null) ? Items.AIR : offHand));
    }

    private static Item getItem(String itemId){
        return Registries.ITEM.get(Identifier.of(itemId));
    }
}
