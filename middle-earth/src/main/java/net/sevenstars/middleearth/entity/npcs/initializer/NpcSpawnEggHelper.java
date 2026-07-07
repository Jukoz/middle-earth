package net.sevenstars.middleearth.entity.npcs.initializer;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.EggItemsME;
import net.sevenstars.middleearth.item.dataComponents.FactionDataComponent;
import net.sevenstars.middleearth.item.dataComponents.RaceDataComponent;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;

import java.util.List;

public class NpcSpawnEggHelper {
    public static ItemStack getSpawnEgg(NpcData npcData) {
        if(npcData == null)
            return ItemStack.EMPTY;
        return buildItemStackBasedOnNpcData(npcData);
    }

    public static ItemStack getSpawnEgg(World world, Identifier identifier) {
        NpcData npcData = world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC).get(identifier);
        return buildItemStackBasedOnNpcData(npcData);
    }

    private static ItemStack buildItemStackBasedOnNpcData(NpcData npcData) {
        if(npcData == null)
            return ItemStack.EMPTY;

        Identifier itemId = MiddleEarth.append(npcData.getId(), "_spawn_egg");
        ItemStack itemStack = new ItemStack(EggItemsME.NPC_SPAWN_EGG);

        NbtCompound compound = new NbtCompound();
        compound.putString("id", MiddleEarth.of("npc").toString());
        compound.putString("NpcDataId", npcData.getId().toString());

        itemStack.set(DataComponentTypes.ENTITY_DATA, NbtComponent.of(compound));
        itemStack.set(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(npcData.getFactionIdentifier()));
        itemStack.set(DataComponentTypesME.RACE_DATA, new RaceDataComponent(npcData.getRace()));
        itemStack.set(DataComponentTypes.ITEM_NAME, Text.translatable(itemId.toTranslationKey("item")));
        itemStack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(
                List.of(),
                List.of(),
                List.of(npcData.getId().getPath().replaceAll("\\.", "_") + "_spawn_egg"),
                List.of()));

        return itemStack;
    }
}
