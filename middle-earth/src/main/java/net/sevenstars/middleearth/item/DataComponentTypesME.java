package net.sevenstars.middleearth.item;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatColor;
import net.sevenstars.middleearth.item.dataComponents.*;
import net.sevenstars.middleearth.registries.RegistryAliasesME;

import java.util.function.UnaryOperator;

public class DataComponentTypesME {

    /**
     * Middle-earth mod Data Components registry
     */
    public static final DataComponentType<CooldownDataComponent> COOLDOWN = register("cooldown", (builder) -> {
        return builder.persistent(CooldownDataComponent.CODEC).networkSynchronized(CooldownDataComponent.PACKET_CODEC);
    });

    public static final DataComponentType<TemperatureDataComponent> TEMPERATURE_DATA = register("temperature", (builder) -> {
        return builder.persistent(TemperatureDataComponent.CODEC).networkSynchronized(TemperatureDataComponent.PACKET_CODEC);
    });

    public static final DataComponentType<ArmorVariantDataComponent> ARMOR_VARIANT_DATA = register("armor_variant", (builder) -> {
        return builder.persistent(ArmorVariantDataComponent.CODEC).networkSynchronized(ArmorVariantDataComponent.PACKET_CODEC);
    });

    public static final DataComponentType<ArtisanDataComponent> ARTISAN_DATA = register("artisan", (builder) -> {
        return builder.persistent(ArtisanDataComponent.CODEC).networkSynchronized(ArtisanDataComponent.PACKET_CODEC);
    });

    public static final DataComponentType<SeasonDataComponent> SEASON_DATA = register("season", (builder) -> {
        return builder.persistent(SeasonDataComponent.CODEC).networkSynchronized(SeasonDataComponent.PACKET_CODEC);
    });

    public static final DataComponentType<SneakAttackDataComponent> SNEAK_ATTACK_DATA = register("sneak_attack", (builder) -> {
        return builder.persistent(SneakAttackDataComponent.CODEC).networkSynchronized(SneakAttackDataComponent.PACKET_CODEC);
    });

    public static final DataComponentType<FactionDataComponent> FACTION_DATA = register("faction", (builder) -> {
        return builder.persistent(FactionDataComponent.CODEC).networkSynchronized(FactionDataComponent.PACKET_CODEC);
    });

    public static final DataComponentType<RaceDataComponent> RACE_DATA = register("race", (builder) -> {
        return builder.persistent(RaceDataComponent.CODEC).networkSynchronized(RaceDataComponent.PACKET_CODEC);
    });

    public static final DataComponentType<ArmorTierDataComponent> ARMOR_TIER_DATA = register("armor_tier", (builder) -> {
        return builder.persistent(ArmorTierDataComponent.CODEC).networkSynchronized(ArmorTierDataComponent.PACKET_CODEC);
    });

    public static final DataComponentType<WeaponTypeDataComponent> WEAPON_TYPE_DATA = register("weapon_type", (builder) -> {
        return builder.persistent(WeaponTypeDataComponent.CODEC).networkSynchronized(WeaponTypeDataComponent.PACKET_CODEC);
    });

    public static final DataComponentType<Boolean> WEAPON_ACTIVE = register("weapon_active", builder -> {
        return builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL);
    });

    public static final DataComponentType<BlockAuthorDataComponent> BLOCK_AUTHOR_DATA = register("block_author", (builder) -> {
        return builder.persistent(BlockAuthorDataComponent.CODEC).networkSynchronized(BlockAuthorDataComponent.PACKET_CODEC);
    });

    public static final DataComponentType<BackAttachmentDataComponent> BACK_ATTACHMENT_DATA = register("back_attachment", (builder) -> {
        return builder.persistent(BackAttachmentDataComponent.CODEC).networkSynchronized(BackAttachmentDataComponent.PACKET_CODEC);
    });

    public static final DataComponentType<HelmetAttachmentDataComponent> HELMET_ATTACHMENT_DATA = register("helmet_attachment", (builder) -> {
        return builder.persistent(HelmetAttachmentDataComponent.CODEC).networkSynchronized(HelmetAttachmentDataComponent.PACKET_CODEC);
    });

    public static final DataComponentType<MountArmorAddonComponent> MOUNT_ARMOR_DATA = register("mount_armor_addon", (builder) -> {
        return builder.persistent(MountArmorAddonComponent.CODEC).networkSynchronized(MountArmorAddonComponent.PACKET_CODEC);
    });

    public static final DataComponentType<BroadhoofGoatColor> GOAT_VARIANT = register(
            "broadhoof_goat/variant", builder -> builder.persistent(BroadhoofGoatColor.CODEC).networkSynchronized(BroadhoofGoatColor.PACKET_CODEC)
    );

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(BuiltInRegistries.DATA_COMPONENT_TYPE, id));
        return RegistrationBridge.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, id), ((DataComponentType.Builder)builderOperator.apply(DataComponentType.builder())).build());
    }

    public static void registerModComponentTypes() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Component Types Items for " + MiddleEarth.MOD_ID);
    }
}
