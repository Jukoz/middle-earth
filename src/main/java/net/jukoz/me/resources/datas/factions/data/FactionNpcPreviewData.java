package net.jukoz.me.resources.datas.factions.data;

import it.unimi.dsi.fastutil.Hash;
import net.fabricmc.fabric.api.util.NbtType;
import net.jukoz.me.resources.datas.races.Race;
import net.jukoz.me.resources.datas.races.RaceLookup;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class FactionNpcPreviewData{
    public static class PreviewData {
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
        public PreviewData(Item head, Item chest, Item legs, Item feet, Item mainHand, Item offHand){
            data = new HashMap<>();
            data.put(EquipmentSlot.HEAD, new ItemStack(head));
            data.put(EquipmentSlot.CHEST, new ItemStack(chest));
            data.put(EquipmentSlot.LEGS, new ItemStack(legs));
            data.put(EquipmentSlot.FEET, new ItemStack(feet));
            data.put(EquipmentSlot.MAINHAND, new ItemStack(mainHand));
            data.put(EquipmentSlot.OFFHAND, new ItemStack(offHand));
        }

        public ItemStack get(EquipmentSlot slot) {
            if(!data.containsKey(slot))
                return new ItemStack(Items.AIR);
            return data.get(slot);
        }
    }
    private HashMap<Race, PreviewData> previewGearsByRace;


    public FactionNpcPreviewData(HashMap<Race, PreviewData> previews){
        this.previewGearsByRace = previews;
    }


    public FactionNpcPreviewData(Optional<NbtCompound> optionalPreviewGearNbt) {
        if(optionalPreviewGearNbt.isEmpty()){
            return;
        }
        this.previewGearsByRace = new HashMap<>();
        NbtCompound compound = optionalPreviewGearNbt.get();

        NbtList list = compound.getList("previews", NbtType.COMPOUND);
        for(int i = 0; i < list.size(); i++){
            NbtCompound nbt = list.getCompound(i);
            assignItems(
                    RaceLookup.getRaceFromString(nbt.getString("race")),
                    getItem(nbt.getString(EquipmentSlot.HEAD.asString())),
                    getItem(nbt.getString(EquipmentSlot.CHEST.asString())),
                    getItem(nbt.getString(EquipmentSlot.LEGS.asString())),
                    getItem(nbt.getString(EquipmentSlot.FEET.asString())),
                    getItem(nbt.getString(EquipmentSlot.MAINHAND.asString())),
                    getItem(nbt.getString(EquipmentSlot.OFFHAND.asString()))
            );
        }
    }

    private void assignItems(Race race, Item head, Item chest, Item legs, Item feet, Item mainHand, Item offHand){
        PreviewData previewData = new PreviewData(head, chest, legs, feet, mainHand, offHand);
        this.previewGearsByRace.put(race, previewData);
    }

    private static Item getItem(String itemId){
        return Registries.ITEM.get(Identifier.of(itemId));
    }

    public PreviewData getPreviewData(Race race) {
        if(this.previewGearsByRace.containsKey(race))
            return this.previewGearsByRace.get(race);
        return null;
    }

    public ItemStack get(Race race, EquipmentSlot slot){
        if(this.previewGearsByRace.containsKey(race)){
            PreviewData preview = this.previewGearsByRace.get(race);
            if(preview != null)
                return preview.get(slot);
        }
        return null;
    }

    public Optional<NbtCompound> getNbt() {
        if(this.previewGearsByRace == null || this.previewGearsByRace.isEmpty())
            return Optional.empty();

        NbtList list = new NbtList();
        for(Race race : this.previewGearsByRace.keySet()){
            NbtCompound nbt = new NbtCompound();
            PreviewData preview = this.previewGearsByRace.get(race);
            nbt.putString("race", race.getId().toString());
            for(EquipmentSlot slot : preview.data.keySet()){
                nbt.putString(slot.name().toLowerCase(), preview.get(slot).getItem().toString());
            }
            list.add(nbt);
        }
        NbtCompound nbt = new NbtCompound();
        nbt.put("previews", list);
        return Optional.of(nbt);
    }
}
