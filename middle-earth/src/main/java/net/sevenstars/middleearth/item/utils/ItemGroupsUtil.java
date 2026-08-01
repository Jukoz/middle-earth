package net.sevenstars.middleearth.item.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.equipment.trim.ArmorTrimMaterials;
import net.minecraft.item.equipment.trim.ArmorTrimPattern;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.data.NpcInitializationData;
import net.sevenstars.middleearth.entity.npcs.initializer.NpcSpawnEggHelper;
import net.sevenstars.middleearth.item.EggItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class ItemGroupsUtil {
    private static final Comparator<RegistryEntry<NpcType>> NPC_DATA_COMPARATOR = Comparator.comparing(RegistryEntry::value, Comparator.comparing(NpcType::getId));

    public static void addNpcEggs(ItemGroup.Entries entries, RegistryWrapper.Impl<NpcType> registryWrapper, Predicate<RegistryEntry<NpcType>> filter, RegistryWrapper.WrapperLookup lookup, ItemGroup.StackVisibility stackVisibility) {
        Identifier randomSpawnEggId = MiddleEarth.of("npc_random_spawn_egg");

        ItemStack randomNpcSpawnEgg = new ItemStack(EggItemsME.NPC_SPAWN_EGG);

        NbtCompound compoundData = new NbtCompound();
        compoundData.putString("id", MiddleEarth.of("npc").toString());

        NpcInitializationData npcInitializationData = new NpcInitializationData(null, true);

        RegistryOps<NbtElement> ops = RegistryOps.of(
                NbtOps.INSTANCE,
                lookup
        );

        NbtElement element = NpcInitializationData.CODEC
                .encodeStart(ops, npcInitializationData)
                .getOrThrow();
        compoundData.put(NpcEntity.KeyStrings.INITIALIZATION_DATA, element);

        randomNpcSpawnEgg.set(DataComponentTypes.ENTITY_DATA, NbtComponent.of(compoundData));
        randomNpcSpawnEgg.set(DataComponentTypes.ITEM_NAME, Text.translatable(randomSpawnEggId.toTranslationKey("item")));
        randomNpcSpawnEgg.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(
                List.of(),
                List.of(),
                List.of(randomSpawnEggId.getPath().replaceAll("\\.", "_")),
                List.of()));

        entries.add(randomNpcSpawnEgg);

        registryWrapper.streamEntries().filter(filter).sorted(NPC_DATA_COMPARATOR).forEach(reference -> {
            ItemStack spawnEgg = NpcSpawnEggHelper.getSpawnEgg(reference.value(), lookup);
            entries.add(spawnEgg, stackVisibility);
        });
    }

    private static ItemStack addTrim(Item partItem, RegistryWrapper.WrapperLookup wrapper, RegistryKey<ArmorTrimMaterial> reference) {
        ItemStack item = partItem.getDefaultStack();
        RegistryEntry.Reference<ArmorTrimMaterial> material = wrapper.getOptional(RegistryKeys.TRIM_MATERIAL).orElseThrow().getOrThrow(reference);
        RegistryEntry.Reference<ArmorTrimPattern> pattern = wrapper.getOptional(RegistryKeys.TRIM_PATTERN).orElseThrow().getOptional(RegistryKey.of(RegistryKeys.TRIM_PATTERN, MiddleEarth.of( "smithing_part"))).orElse(null);
        item.set(DataComponentTypes.TRIM, new ArmorTrim(material , pattern));
        return item;
    }

    public static Collection<ItemStack> processResourceItem(ItemStack itemStack, ItemGroup.DisplayContext displayContext) {
        if(PART_LIST.contains(itemStack.getItem())) {
            return processSmithingMaterial(itemStack.getItem(), displayContext);
        }
        return List.of(itemStack);
    }

    private static Collection<ItemStack> processSmithingMaterial(Item item, ItemGroup.DisplayContext displayContext) {
        RegistryWrapper.WrapperLookup wrapper = displayContext.lookup();
        List<ItemStack> trimSet = new ArrayList<>();
        for(RegistryKey<ArmorTrimMaterial> trimKey : METAL_LIST){
            trimSet.add(addTrim(item, wrapper, trimKey));
        }
        return trimSet;
    }

    private static final List<Item> PART_LIST = List.of(
            ResourceItemsME.ROD,
            ResourceItemsME.LARGE_ROD,
            ResourceItemsME.PICKAXE_HEAD,
            ResourceItemsME.AXE_HEAD,
            ResourceItemsME.SHOVEL_HEAD,
            ResourceItemsME.HOE_HEAD,
            ResourceItemsME.SHORT_BLADE,
            ResourceItemsME.BLADE,
            ResourceItemsME.LONG_BLADE,
            ResourceItemsME.SWORD_HILT,
            ResourceItemsME.MAIL_RING,
            ResourceItemsME.MAIL,
            ResourceItemsME.SCALE,
            ResourceItemsME.SCALE_MAIL,
            ResourceItemsME.ARMOR_PLATE,
            ResourceItemsME.HELMET_PLATE,
            ResourceItemsME.SHIELD_BORDER,
            ResourceItemsME.SHIELD_PLATE
    );

    private static final List<RegistryKey<ArmorTrimMaterial>> METAL_LIST = List.of(
            SmithingTrimMaterialsME.TIN,
            ArmorTrimMaterials.COPPER,
            SmithingTrimMaterialsME.BRONZE,
            SmithingTrimMaterialsME.CRUDE,
            ArmorTrimMaterials.IRON,
            SmithingTrimMaterialsME.SILVER,
            SmithingTrimMaterialsME.LEAD,
            ArmorTrimMaterials.GOLD,
            SmithingTrimMaterialsME.STEEL,
            SmithingTrimMaterialsME.EDHEL_STEEL,
            SmithingTrimMaterialsME.KHAZAD_STEEL,
            SmithingTrimMaterialsME.BURZUM_STEEL,
            SmithingTrimMaterialsME.MITHRIL
    );

    public static Collection<ItemStack> addFactionBanners(RegistryWrapper.WrapperLookup lookup) {
        List<ItemStack> bannerList = new ArrayList<>();
        List<RegistryEntry.Reference<Faction>> factions = lookup.getOrThrow(DynamicRegistriesME.FACTION).streamEntries().toList();
        for(RegistryEntry.Reference<Faction> factionReference : factions){
            ItemStack bannerItem = factionReference.value().getBannerItem(lookup);
            if(!bannerItem.isEmpty())
                bannerList.add(bannerItem);
        }
        return bannerList;
    }
}
