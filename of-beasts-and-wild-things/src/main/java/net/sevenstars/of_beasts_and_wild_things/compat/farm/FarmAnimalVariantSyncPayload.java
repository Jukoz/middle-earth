package net.sevenstars.of_beasts_and_wild_things.compat.farm;

import io.netty.handler.codec.DecoderException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;

public record FarmAnimalVariantSyncPayload(List<FarmAnimalVariantDefinition.Synced> definitions)
        implements CustomPacketPayload {
    private static final int MAX_DEFINITIONS = 4096;

    public static final Type<FarmAnimalVariantSyncPayload> TYPE =
            new Type<>(OfBeastsAndWildThings.of("farm_animal_variants"));
    public static final StreamCodec<RegistryFriendlyByteBuf, FarmAnimalVariantSyncPayload> STREAM_CODEC =
            StreamCodec.ofMember(FarmAnimalVariantSyncPayload::encode, FarmAnimalVariantSyncPayload::decode);

    public FarmAnimalVariantSyncPayload {
        definitions = List.copyOf(definitions);
    }

    private void encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeVarInt(this.definitions.size());
        for (FarmAnimalVariantDefinition.Synced definition : this.definitions) {
            buffer.writeEnum(definition.kind());
            buffer.writeResourceLocation(definition.id());
            buffer.writeResourceLocation(definition.assetId());
            buffer.writeEnum(definition.model());
            buffer.writeBoolean(definition.legacyVisualFallback());
        }
    }

    private static FarmAnimalVariantSyncPayload decode(RegistryFriendlyByteBuf buffer) {
        int size = buffer.readVarInt();
        if (size < 0 || size > MAX_DEFINITIONS) {
            throw new DecoderException("Invalid farm animal variant definition count: " + size);
        }
        List<FarmAnimalVariantDefinition.Synced> definitions = new ArrayList<>(size);
        for (int index = 0; index < size; index++) {
            definitions.add(new FarmAnimalVariantDefinition.Synced(
                    buffer.readEnum(FarmAnimalKind.class),
                    buffer.readResourceLocation(),
                    buffer.readResourceLocation(),
                    buffer.readEnum(FarmAnimalVariantModel.class),
                    buffer.readBoolean()
            ));
        }
        return new FarmAnimalVariantSyncPayload(definitions);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
