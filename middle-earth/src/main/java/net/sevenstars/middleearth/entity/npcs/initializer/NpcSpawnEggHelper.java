package net.sevenstars.middleearth.entity.npcs.initializer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.data.NpcData;
import net.sevenstars.middleearth.entity.npcs.data.NpcInitializationData;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.EggItemsME;
import net.sevenstars.middleearth.item.dataComponents.FactionDataComponent;
import net.sevenstars.middleearth.item.dataComponents.RaceDataComponent;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.List;

public class NpcSpawnEggHelper {
    public static ItemStack getSpawnEgg(NpcType npcType) {
        if(npcType == null)
            return ItemStack.EMPTY;
        return buildItemStackBasedOnNpcData(npcType);
    }

    public static ItemStack getSpawnEgg(World world, Identifier identifier) {
        NpcType npcType = world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_TYPE).get(identifier);
        return buildItemStackBasedOnNpcData(npcType);
    }

    private static ItemStack buildItemStackBasedOnNpcData(NpcType npcType) {
        if(npcType == null)
            return ItemStack.EMPTY;

        Identifier itemId = MiddleEarth.append(npcType.getId(), "_spawn_egg");
        ItemStack itemStack = new ItemStack(EggItemsME.NPC_SPAWN_EGG);

        NbtCompound compoundData = new NbtCompound();
        compoundData.putString("id", MiddleEarth.of("npc").toString());

        NpcInitializationData npcInitializationData = new NpcInitializationData(npcType.getId(), false);
        DynamicRegistryManager manager = MinecraftClient.getInstance().getServer().getRegistryManager();

        RegistryOps<NbtElement> ops = RegistryOps.of(
                NbtOps.INSTANCE,
                manager
        );

        NbtElement element = NpcInitializationData.CODEC
                .encodeStart(ops, npcInitializationData)
                .getOrThrow();
        compoundData.put(NpcEntity.KeyStrings.NPC_INITIALIZATION_DATA, element);

        itemStack.set(DataComponentTypes.ENTITY_DATA, NbtComponent.of(compoundData));
        itemStack.set(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(npcType.getFactionIdentifier()));
        itemStack.set(DataComponentTypesME.RACE_DATA, new RaceDataComponent(npcType.getRace()));
        itemStack.set(DataComponentTypes.ITEM_NAME, Text.translatable(itemId.toTranslationKey("item")));
        itemStack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(
                List.of(),
                List.of(),
                List.of(npcType.getId().getPath().replaceAll("\\.", "_") + "_spawn_egg"),
                List.of()));

        return itemStack;
    }
}
