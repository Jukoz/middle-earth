package net.sevenstars.middleearth.item.utils;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.block.registration.StoneBlockSets;
import net.sevenstars.middleearth.block.registration.WoodBlockSets;
import net.sevenstars.middleearth.item.*;
import net.sevenstars.middleearth.item.dataComponents.FactionDataComponent;
import net.sevenstars.middleearth.resources.FactionsME;
import net.sevenstars.middleearth.resources.NpcME;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.pools.EreborNpcDataPool;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class ModItemGroups {

    private static final Comparator<RegistryEntry<NpcData>> NPC_DATA_COMPARATOR = Comparator.comparing(RegistryEntry::value, Comparator.comparing(NpcData::getId));

    private static void addNpcEggs(ItemGroup.Entries entries, RegistryWrapper.WrapperLookup registries, RegistryWrapper.Impl<NpcData> registryWrapper, Predicate<RegistryEntry<NpcData>> filter, ItemGroup.StackVisibility stackVisibility) {
        RegistryOps<NbtElement> registryOps = registries.getOps(NbtOps.INSTANCE);
        registryWrapper.streamEntries().filter(filter).sorted(NPC_DATA_COMPARATOR).forEach(reference -> {
            ItemStack itemStack = new ItemStack(EggItemsME.NPC_SPAWN_EGG);
            NbtCompound compound = new NbtCompound();
            compound.putString("NpcDataId", reference.value().getId().toString());
            itemStack.set(DataComponentTypes.ENTITY_DATA, NbtComponent.of(compound));
            itemStack.set(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue()));
            itemStack.set(DataComponentTypes.ITEM_NAME, Text.translatable(reference.value().getName()));
            entries.add(itemStack, stackVisibility);
        });
    }


    public static final List<ItemStack> STONE_BLOCKS_CONTENTS = new LinkedList<>();
    public static final ItemGroup STONE_BLOCKS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".stone_blocks"))
            .icon(() -> new ItemStack(StoneBlockSets.CALCITE_SET.brickBlocks.base().asItem()))
            .entries((displayContext, entries) -> {
                for (ItemStack item : STONE_BLOCKS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> WOOD_BLOCKS_CONTENTS = new LinkedList<>();
    public static final ItemGroup WOOD_BLOCKS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".wood_blocks"))
            .icon(() -> new ItemStack(WoodBlockSets.WILLOW_SET.logBlocks.log().asItem()))
            .entries((displayContext, entries) -> {
                for (ItemStack item : WOOD_BLOCKS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> MISC_BLOCKS_CONTENTS = new LinkedList<>();
    public static final ItemGroup MISC_BLOCKS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".misc_blocks"))
            .icon(() -> new ItemStack(ModBlocks.STRAW_BLOCK.asItem()))
            .entries((displayContext, entries) -> {
                for (ItemStack item : MISC_BLOCKS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> DECORATIVES_BLOCKS_CONTENT = new LinkedList<>();
    public static final ItemGroup DECORATIVES_BLOCKS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".decorative_blocks"))
            .icon(() -> new ItemStack(DecorativeItemsME.DWARVEN_LANTERN))
            .entries((displayContext, entries) -> {
                for (ItemStack item : DECORATIVES_BLOCKS_CONTENT) {
                    entries.add(item);
                };
            })
            .build();

    public static final List<ItemStack> NATURE_BLOCKS_CONTENTS = new LinkedList<>();
    public static final ItemGroup NATURE_BLOCKS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".nature_blocks"))
            .icon(() -> new ItemStack(ModNatureBlocks.HEATHER.asItem()))
            .entries((displayContext, entries) -> {
                for (ItemStack item : NATURE_BLOCKS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> FOOD_CONTENTS = new LinkedList<>();
    public static final ItemGroup FOOD = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".food_items"))
            .icon(() -> new ItemStack(FoodItemsME.LEMBAS))
            .entries((displayContext, entries) -> {
                for (ItemStack item : FOOD_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> WEAPONS_CONTENTS = new LinkedList<>();
    public static final ItemGroup WEAPONS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".weapon_items"))
            .icon(() -> new ItemStack(WeaponItemsME.GONDORIAN_SWORD))
            .entries((displayContext, entries) -> {
                for (ItemStack item : WEAPONS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> EQUIPMENT_CONTENTS = new LinkedList<>();
    public static final ItemGroup EQUIPMENT = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".equipment_items"))
            .icon(() -> new ItemStack(EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_HELMET))
            .entries((displayContext, entries) -> {
                for (ItemStack item : EQUIPMENT_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> TOOLS_CONTENTS = new LinkedList<>();
    public static final ItemGroup TOOLS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".tool_items"))
            .icon(() -> new ItemStack(ToolItemsME.KHAZAD_STEEL_PICKAXE))
            .entries((displayContext, entries) -> {
                for (ItemStack item : TOOLS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> RESOURCES_CONTENTS = new LinkedList<>();
    public static final ItemGroup RESOURCES = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".resource_items"))
            .icon(() -> new ItemStack(ResourceItemsME.MITHRIL_INGOT))
            .entries((displayContext, entries) -> {
                for (ItemStack item : RESOURCES_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> SPAWN_EGGS_CONTENTS = new LinkedList<>();
    public static final ItemGroup SPAWN_EGGS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".spawn_egg_items"))
            .icon(() -> new ItemStack(EggItemsME.DEER_SPAWN_EGG))
            .entries((displayContext, entries) -> {
                for (ItemStack item : SPAWN_EGGS_CONTENTS) {
                    entries.add(item);
                };
                ItemStack itemStack = new ItemStack(EggItemsME.NPC_SPAWN_EGG);

                NbtCompound compound = new NbtCompound();
                //itemStack.set(DataComponentTypes.ENTITY_DATA, NbtComponent.of(compound));
                compound.putString("id", "middle-earth:npc");
                compound.putBoolean("Glowing", true);
                compound.put("NpcDataId", Identifier.CODEC, EreborNpcDataPool.EREBOR_GATEWARDEN.getId());
                itemStack.set(DataComponentTypes.ENTITY_DATA, NbtComponent.of(compound));

                itemStack.set(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(FactionsME.LONGBEARDS_EREBOR.getValue()));
                itemStack.set(DataComponentTypes.ITEM_NAME, Text.translatable(FactionsME.LONGBEARDS_EREBOR.getValue().toTranslationKey("faction")));
                entries.add(itemStack);

                displayContext.lookup().getOptional(NpcME.KEY)
                        .ifPresent(registryWrapper -> addNpcEggs(
                                entries,
                                displayContext.lookup(),
                                registryWrapper,
                                registryEntry -> true,
                                ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS));
            })
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "stone_blocks"), STONE_BLOCKS);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "wood_blocks"), WOOD_BLOCKS);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "misc_blocks"), MISC_BLOCKS);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "decorative"), DECORATIVES_BLOCKS);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "nature_blocks"), NATURE_BLOCKS);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "food_items"), FOOD);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "weapon_items"), WEAPONS);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "equipment_items"), EQUIPMENT);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "tool_items"), TOOLS);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "resource_items"), RESOURCES);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "spawn_egg_items"), SPAWN_EGGS);
    }

}
