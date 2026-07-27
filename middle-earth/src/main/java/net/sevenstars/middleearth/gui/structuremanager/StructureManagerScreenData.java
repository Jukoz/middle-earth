package net.sevenstars.middleearth.gui.structuremanager;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public class StructureManagerScreenData{
    private BlockPos pos;
    private ResourceLocation structureManagerIdentifier;
    private boolean isActive;
    private boolean toInitialize;

    public static final StreamCodec<? super RegistryFriendlyByteBuf, StructureManagerScreenData> PACKET_CODEC;

    public BlockPos getPos() {
        return this.pos;
    }
    public ResourceLocation getStructureManagerIdentifier() {
        return this.structureManagerIdentifier;
    }
    private Optional<ResourceLocation> getStructureManagerIdentifierOptional() {
        return Optional.ofNullable(this.structureManagerIdentifier);
    }

    public boolean getIsActive() {
        return this.isActive;
    }
    public boolean getToInitialize() {
        return this.toInitialize;
    }

    public void setStructureManagerIdentifier(ResourceLocation structureManagerIdentifier) {
        this.structureManagerIdentifier = structureManagerIdentifier;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    public void setToInitialize(boolean toInitialize) {
        this.toInitialize = toInitialize;
    }

    public StructureManagerScreenData(BlockPos pos, boolean isActive, boolean toInitialize, Optional<ResourceLocation> structureManagerId){
        this.pos = pos;
        setActive(isActive);
        setToInitialize(toInitialize);
        structureManagerId.ifPresentOrElse(this::setStructureManagerIdentifier, () -> setStructureManagerIdentifier(null));
    }

    static {
        PACKET_CODEC = StreamCodec.composite(
                BlockPos.STREAM_CODEC, StructureManagerScreenData::getPos,
                ByteBufCodecs.BOOL, StructureManagerScreenData::getIsActive,
                ByteBufCodecs.BOOL, StructureManagerScreenData::getToInitialize,
                ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), StructureManagerScreenData::getStructureManagerIdentifierOptional,
                StructureManagerScreenData::new
        );
    }
}
