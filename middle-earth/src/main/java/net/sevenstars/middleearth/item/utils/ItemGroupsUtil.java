package net.sevenstars.middleearth.item.utils;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.CustomModelData;
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
    private static final Comparator<Holder<NpcType>> NPC_DATA_COMPARATOR = Comparator.comparing(Holder::value, Comparator.comparing(NpcType::getId));

    public static void addNpcEggs(CreativeModeTab.Output entries, HolderLookup.RegistryLookup<NpcType> registryWrapper, Predicate<Holder<NpcType>> filter, HolderLookup.Provider lookup, CreativeModeTab.TabVisibility stackVisibility) {
        ResourceLocation randomSpawnEggId = MiddleEarth.of("npc_random_spawn_egg");

        ItemStack randomNpcSpawnEgg = new ItemStack(EggItemsME.NPC_SPAWN_EGG);

        CompoundTag compoundData = new CompoundTag();
        compoundData.putString("id", MiddleEarth.of("npc").toString());

        NpcInitializationData npcInitializationData = new NpcInitializationData(null, true);

        RegistryOps<Tag> ops = RegistryOps.create(
                NbtOps.INSTANCE,
                lookup
        );

        Tag element = NpcInitializationData.CODEC
                .encodeStart(ops, npcInitializationData)
                .getOrThrow();
        compoundData.put(NpcEntity.KeyStrings.INITIALIZATION_DATA, element);

        randomNpcSpawnEgg.set(DataComponents.ENTITY_DATA, CustomData.of(compoundData));
        randomNpcSpawnEgg.set(DataComponents.ITEM_NAME, Component.translatable(randomSpawnEggId.toLanguageKey("item")));
        randomNpcSpawnEgg.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(0));

        entries.accept(randomNpcSpawnEgg);

        registryWrapper.listElements().filter(filter).sorted(NPC_DATA_COMPARATOR).forEach(reference -> {
            ItemStack spawnEgg = NpcSpawnEggHelper.getSpawnEgg(reference.value(), lookup);
            entries.accept(spawnEgg, stackVisibility);
        });
    }

    private static ItemStack addTrim(Item partItem, HolderLookup.Provider wrapper, ResourceKey<TrimMaterial> reference) {
        ItemStack item = partItem.getDefaultInstance();
        Holder.Reference<TrimMaterial> material = wrapper.lookup(Registries.TRIM_MATERIAL).orElseThrow().getOrThrow(reference);
        Holder.Reference<TrimPattern> pattern = wrapper.lookup(Registries.TRIM_PATTERN).orElseThrow().get(ResourceKey.create(Registries.TRIM_PATTERN, MiddleEarth.of( "smithing_part"))).orElse(null);
        item.set(DataComponents.TRIM, new ArmorTrim(material , pattern));
        return item;
    }

    public static Collection<ItemStack> processResourceItem(ItemStack itemStack, CreativeModeTab.ItemDisplayParameters displayContext) {
        if(PART_LIST.contains(itemStack.getItem())) {
            return processSmithingMaterial(itemStack.getItem(), displayContext);
        }
        return List.of(itemStack);
    }

    private static Collection<ItemStack> processSmithingMaterial(Item item, CreativeModeTab.ItemDisplayParameters displayContext) {
        HolderLookup.Provider wrapper = displayContext.holders();
        List<ItemStack> trimSet = new ArrayList<>();
        for(ResourceKey<TrimMaterial> trimKey : METAL_LIST){
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

    private static final List<ResourceKey<TrimMaterial>> METAL_LIST = List.of(
            SmithingTrimMaterialsME.TIN,
            TrimMaterials.COPPER,
            SmithingTrimMaterialsME.BRONZE,
            SmithingTrimMaterialsME.CRUDE,
            TrimMaterials.IRON,
            SmithingTrimMaterialsME.SILVER,
            SmithingTrimMaterialsME.LEAD,
            TrimMaterials.GOLD,
            SmithingTrimMaterialsME.STEEL,
            SmithingTrimMaterialsME.EDHEL_STEEL,
            SmithingTrimMaterialsME.KHAZAD_STEEL,
            SmithingTrimMaterialsME.BURZUM_STEEL,
            SmithingTrimMaterialsME.MITHRIL
    );

    public static Collection<ItemStack> addFactionBanners(HolderLookup.Provider lookup) {
        List<ItemStack> bannerList = new ArrayList<>();
        List<Holder.Reference<Faction>> factions = lookup.lookupOrThrow(DynamicRegistriesME.FACTION).listElements().toList();
        for(Holder.Reference<Faction> factionReference : factions){
            ItemStack bannerItem = factionReference.value().getBannerItem(lookup);
            if(!bannerItem.isEmpty())
                bannerList.add(bannerItem);
        }
        return bannerList;
    }
}
