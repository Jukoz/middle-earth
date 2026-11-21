package net.sevenstars.middleearth.features.npcs.initializer;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.TypedEntityData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.features.npcs.data.NpcInitializationData;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.EggItemsME;
import net.sevenstars.middleearth.item.dataComponents.FactionDataComponent;
import net.sevenstars.middleearth.item.dataComponents.RaceDataComponent;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.features.npcs.classes.NpcClass;

import java.util.List;

public class NpcSpawnEggHelper {
    private static final String SPAWN_EGG_SUFFIX = "_spawn_egg";
    private static final String ENTITY_ID_KEY = "id";
    private static final String NPC_ENTITY = "npc";

    public static ItemStack getSpawnEgg(NpcClass npcClass, RegistryWrapper.WrapperLookup wrapper) {
        if(npcClass == null)
            return ItemStack.EMPTY;
        return buildItemStackBasedOnNpcData(npcClass, wrapper);
    }

    public static ItemStack getSpawnEgg(World world, Identifier identifier) {
        NpcClass npcClass = world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_CLASS).get(identifier);
        RegistryWrapper.WrapperLookup wrapperLookup = world.getRegistryManager();
        return buildItemStackBasedOnNpcData(npcClass, wrapperLookup);
    }

    private static ItemStack buildItemStackBasedOnNpcData(NpcClass npcClass, RegistryWrapper.WrapperLookup wrapper) {
        if(npcClass == null)
            return ItemStack.EMPTY;

        Identifier itemId = MiddleEarth.appendSuffix(npcClass.getId(), SPAWN_EGG_SUFFIX);
        ItemStack itemStack = new ItemStack(EggItemsME.NPC_SPAWN_EGG);

        NbtCompound compoundData = new NbtCompound();
        compoundData.putString(ENTITY_ID_KEY, MiddleEarth.id(NPC_ENTITY).toString());

        NpcInitializationData npcInitializationData = new NpcInitializationData(npcClass.getId(), false);

        RegistryOps<NbtElement> ops = RegistryOps.of(
                NbtOps.INSTANCE,
                wrapper
        );

        NbtElement element = NpcInitializationData.CODEC
                .encodeStart(ops, npcInitializationData)
                .getOrThrow();
        compoundData.put(NpcEntity.KeyStrings.INITIALIZATION_DATA, element);

        itemStack.set(DataComponentTypes.ENTITY_DATA, TypedEntityData.create(EntitiesME.NPC, compoundData));
        itemStack.set(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(npcClass.getFactionIdentifier()));
        itemStack.set(DataComponentTypesME.RACE_DATA, new RaceDataComponent(npcClass.getRace()));
        itemStack.set(DataComponentTypes.ITEM_NAME, Text.translatable(itemId.toTranslationKey("item")));
        itemStack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(
                List.of(),
                List.of(),
                List.of(npcClass.getId().getPath().replaceAll("\\.", "_") + SPAWN_EGG_SUFFIX),
                List.of()));

        return itemStack;
    }
}
