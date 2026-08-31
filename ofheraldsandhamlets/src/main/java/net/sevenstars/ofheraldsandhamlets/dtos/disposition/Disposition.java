package net.sevenstars.ofheraldsandhamlets.dtos.disposition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.ofheraldsandhamlets.registries.DynamicRegistriesHH;

import java.util.Map;
import java.util.Set;

public class Disposition {
    public static final Codec<Disposition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("identifier").forGetter(Disposition::getId)
    ).apply(instance, Disposition::new));
    public static final PacketCodec<ByteBuf, Disposition> PACKET_CODEC = PacketCodecs.codec(CODEC);

    private Identifier id;
    public Disposition(Identifier newId) {
        this.id = newId;
    }

    private Identifier getId() {
        return id;
    }

    // [LOOKUP]
    public static Set<Map.Entry<RegistryKey<Disposition>, Disposition>> fetchAllEntries(World world) {
        return world.getRegistryManager().getOrThrow(DynamicRegistriesHH.DISPOSITION).getEntrySet();
    }
}
