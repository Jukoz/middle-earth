package net.sevenstars.middleearth.block.special.gems;

import com.mojang.serialization.Codec;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.StringIdentifiable;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.registries.content.gems.GemTypeRegistry;
import net.sevenstars.middleearth.resources.datas.gems.GemType;

/**
 * The built-in gem types as an enum, for places that need a fixed set (block state property, feature config).
 * Each maps to its {@link GemTypeRegistry} key.
 */
public enum GemKind implements StringIdentifiable {
    ADAMANT(GemTypeRegistry.ADAMANT),
    RUBY(GemTypeRegistry.RUBY),
    SAPPHIRE(GemTypeRegistry.SAPPHIRE),
    BERYL(GemTypeRegistry.BERYL),
    HADORITE(GemTypeRegistry.HADORITE),
    OPAL(GemTypeRegistry.OPAL),
    ONYX(GemTypeRegistry.ONYX),
    MOONSTONE(GemTypeRegistry.MOONSTONE);

    public static final Codec<GemKind> CODEC = StringIdentifiable.createCodec(GemKind::values);

    private final RegistryKey<GemType> key;

    GemKind(RegistryKey<GemType> key) {
        this.key = key;
    }

    public RegistryKey<GemType> key() {
        return key;
    }

    public Item rawGem() {
        return ResourceItemsME.RAW_GEMS.get(key);
    }

    @Override
    public String asString() {
        return key.getValue().getPath();
    }
}
