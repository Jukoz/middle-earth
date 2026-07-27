package net.sevenstars.middleearth.entity.npcs.initializer;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.data.NpcInitializationData;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.EggItemsME;
import net.sevenstars.middleearth.item.dataComponents.FactionDataComponent;
import net.sevenstars.middleearth.item.dataComponents.RaceDataComponent;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

public class NpcSpawnEggHelper {
    public static ItemStack getSpawnEgg(NpcType npcType, HolderLookup.Provider wrapper) {
        if(npcType == null)
            return ItemStack.EMPTY;
        return buildItemStackBasedOnNpcData(npcType, wrapper);
    }

    public static ItemStack getSpawnEgg(Level world, ResourceLocation identifier) {
        NpcType npcType = world.registryAccess().registryOrThrow(DynamicRegistriesME.NPC_TYPE).get(identifier);
        HolderLookup.Provider wrapperLookup = world.registryAccess();
        return buildItemStackBasedOnNpcData(npcType, wrapperLookup);
    }

    private static ItemStack buildItemStackBasedOnNpcData(NpcType npcType, HolderLookup.Provider wrapper) {
        if(npcType == null)
            return ItemStack.EMPTY;

        ResourceLocation itemId = MiddleEarth.append(npcType.getId(), "_spawn_egg");
        ItemStack itemStack = new ItemStack(EggItemsME.NPC_SPAWN_EGG);

        CompoundTag compoundData = new CompoundTag();
        compoundData.putString("id", MiddleEarth.of("npc").toString());

        NpcInitializationData npcInitializationData = new NpcInitializationData(npcType.getId(), false);

        RegistryOps<Tag> ops = RegistryOps.create(
                NbtOps.INSTANCE,
                wrapper
        );

        Tag element = NpcInitializationData.CODEC
                .encodeStart(ops, npcInitializationData)
                .getOrThrow();
        compoundData.put(NpcEntity.KeyStrings.INITIALIZATION_DATA, element);

        itemStack.set(DataComponents.ENTITY_DATA, CustomData.of(compoundData));
        itemStack.set(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(npcType.getFactionIdentifier()));
        itemStack.set(DataComponentTypesME.RACE_DATA, new RaceDataComponent(npcType.getRace()));
        itemStack.set(DataComponents.ITEM_NAME, Component.translatable(itemId.toLanguageKey("item")));
        itemStack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(getCustomModelData(npcType.getId())));

        return itemStack;
    }

    public static int getCustomModelData(ResourceLocation npcTypeId) {
        return (npcTypeId.toString().hashCode() & 0x007fffff) + 1;
    }
}
