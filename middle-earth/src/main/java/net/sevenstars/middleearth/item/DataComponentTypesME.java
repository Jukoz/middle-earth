package net.sevenstars.middleearth.item;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatColor;
import net.sevenstars.middleearth.item.dataComponents.*;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.gems.GemType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.registries.RegistryAliasesME;

import java.util.function.UnaryOperator;

public class DataComponentTypesME {

    /**
     * Middle-earth mod Data Components registry
     */
    public static final ComponentType<CooldownDataComponent> COOLDOWN = register("cooldown", (builder) -> {
        return builder.codec(CooldownDataComponent.CODEC).packetCodec(CooldownDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<TemperatureDataComponent> TEMPERATURE_DATA = register("temperature", (builder) -> {
        return builder.codec(TemperatureDataComponent.CODEC).packetCodec(TemperatureDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<ArmorVariantDataComponent> ARMOR_VARIANT_DATA = register("armor_variant", (builder) -> {
        return builder.codec(ArmorVariantDataComponent.CODEC).packetCodec(ArmorVariantDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<ArtisanDataComponent> ARTISAN_DATA = register("artisan", (builder) -> {
        return builder.codec(ArtisanDataComponent.CODEC).packetCodec(ArtisanDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<SeasonDataComponent> SEASON_DATA = register("season", (builder) -> {
        return builder.codec(SeasonDataComponent.CODEC).packetCodec(SeasonDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<SneakAttackDataComponent> SNEAK_ATTACK_DATA = register("sneak_attack", (builder) -> {
        return builder.codec(SneakAttackDataComponent.CODEC).packetCodec(SneakAttackDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<FactionDataComponent> FACTION_DATA = register("faction", (builder) -> {
        return builder.codec(FactionDataComponent.CODEC).packetCodec(FactionDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<RaceDataComponent> RACE_DATA = register("race", (builder) -> {
        return builder.codec(RaceDataComponent.CODEC).packetCodec(RaceDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<ArmorTierDataComponent> ARMOR_TIER_DATA = register("armor_tier", (builder) -> {
        return builder.codec(ArmorTierDataComponent.CODEC).packetCodec(ArmorTierDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<WeaponTypeDataComponent> WEAPON_TYPE_DATA = register("weapon_type", (builder) -> {
        return builder.codec(WeaponTypeDataComponent.CODEC).packetCodec(WeaponTypeDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<BlockAuthorDataComponent> BLOCK_AUTHOR_DATA = register("block_author", (builder) -> {
        return builder.codec(BlockAuthorDataComponent.CODEC).packetCodec(BlockAuthorDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<BackAttachmentDataComponent> BACK_ATTACHMENT_DATA = register("back_attachment", (builder) -> {
        return builder.codec(BackAttachmentDataComponent.CODEC).packetCodec(BackAttachmentDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<HelmetAttachmentDataComponent> HELMET_ATTACHMENT_DATA = register("helmet_attachment", (builder) -> {
        return builder.codec(HelmetAttachmentDataComponent.CODEC).packetCodec(HelmetAttachmentDataComponent.PACKET_CODEC);
    });

    public static final ComponentType<MountArmorAddonComponent> MOUNT_ARMOR_DATA = register("mount_armor_addon", (builder) -> {
        return builder.codec(MountArmorAddonComponent.CODEC).packetCodec(MountArmorAddonComponent.PACKET_CODEC);
    });

    public static final ComponentType<BroadhoofGoatColor> GOAT_VARIANT = register(
            "broadhoof_goat/variant", builder -> builder.codec(BroadhoofGoatColor.CODEC).packetCodec(BroadhoofGoatColor.PACKET_CODEC)
    );

    /** Which gem type a gem item is made of (raw gems carry it by default, cut gems per stack). */
    public static final ComponentType<RegistryKey<GemType>> GEM_TYPE = register("gem_type", builder -> builder
            .codec(RegistryKey.createCodec(DynamicRegistriesME.GEM_TYPE))
            .packetCodec(RegistryKey.createPacketCodec(DynamicRegistriesME.GEM_TYPE)));

    /** How well this raw gem was studied at the Lapidary Table; absent = unstudied (cannot be cut yet). */
    public static final ComponentType<net.sevenstars.middleearth.recipe.lapidary.StudyGrade> STUDY_GRADE = register("study_grade", builder -> builder
            .codec(net.sevenstars.middleearth.recipe.lapidary.StudyGrade.CODEC)
            .packetCodec(net.sevenstars.middleearth.recipe.lapidary.StudyGrade.PACKET_CODEC));

    /**
     * A cut gem's hidden quality value, 0-100, from its faceting runs (see
     * {@link net.sevenstars.middleearth.recipe.lapidary.GemQuality}); absent = never faceted (counts as 0).
     */
    public static final ComponentType<Integer> GEM_QUALITY = register("gem_quality", builder -> builder
            .codec(com.mojang.serialization.Codec.intRange(0, 100))
            .packetCodec(net.minecraft.network.codec.PacketCodecs.VAR_INT));

    /** The best gem notes bound into a Player Book, per gem type (its Gem Studies chapter). */
    public static final ComponentType<java.util.Map<Identifier, net.sevenstars.middleearth.recipe.lapidary.StudyGrade>> GEM_STUDIES = register("gem_studies", builder -> builder
            .codec(com.mojang.serialization.Codec.unboundedMap(Identifier.CODEC, net.sevenstars.middleearth.recipe.lapidary.StudyGrade.CODEC))
            .packetCodec(net.minecraft.network.codec.PacketCodecs.map(java.util.HashMap::new, Identifier.PACKET_CODEC, net.sevenstars.middleearth.recipe.lapidary.StudyGrade.PACKET_CODEC)
                    .<java.util.Map<Identifier, net.sevenstars.middleearth.recipe.lapidary.StudyGrade>>xmap(java.util.Map::copyOf, java.util.HashMap::new)));

    /** The lapidary scrolls bound into a Player Book ("masterwork", "relic"): pages in its Gem Studies chapter. */
    public static final ComponentType<java.util.List<String>> LAPIDARY_PAGES = register("lapidary_pages", builder -> builder
            .codec(com.mojang.serialization.Codec.STRING.listOf())
            .packetCodec(net.minecraft.network.codec.PacketCodecs.STRING.collect(net.minecraft.network.codec.PacketCodecs.toList())));

    /** Whether a lamp or lantern carried in the off hand is lit (lanterns: lit unless set). */
    public static final ComponentType<Boolean> LAMP_ON = register("lamp_on", builder -> builder
            .codec(com.mojang.serialization.Codec.BOOL)
            .packetCodec(net.minecraft.network.codec.PacketCodecs.BOOLEAN));

    /** The lenses fitted in Glasses or a Dwarven Monocle: 1 Refined, 2 Exquisite (see LapidaryEyewear). */
    public static final ComponentType<Integer> EYEWEAR_LENSES = register("eyewear_lenses", builder -> builder
            .codec(com.mojang.serialization.Codec.intRange(1, 2))
            .packetCodec(net.minecraft.network.codec.PacketCodecs.VAR_INT));

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.DATA_COMPONENT_TYPE, id));
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MiddleEarth.MOD_ID, id), ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
    }

    public static void registerModComponentTypes() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Component Types Items for " + MiddleEarth.MOD_ID);
    }
}
