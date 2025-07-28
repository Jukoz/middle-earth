package net.sevenstars.middleearth.gui.structuremanager;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class StructureManagerScreenData{
    private BlockPos pos;
    private Identifier structureManagerIdentifier;
    private boolean isActive;
    private boolean toInitialize;

    public static final PacketCodec<? super RegistryByteBuf, StructureManagerScreenData> PACKET_CODEC;

    public BlockPos getPos() {
        return this.pos;
    }
    public Identifier getStructureManagerIdentifier() {
        return this.structureManagerIdentifier;
    }
    private Optional<Identifier> getStructureManagerIdentifierOptional() {
        return Optional.ofNullable(this.structureManagerIdentifier);
    }

    public boolean getIsActive() {
        return this.isActive;
    }
    public boolean getToInitialize() {
        return this.toInitialize;
    }

    public void setStructureManagerIdentifier(Identifier structureManagerIdentifier) {
        this.structureManagerIdentifier = structureManagerIdentifier;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    public void setToInitialize(boolean toInitialize) {
        this.toInitialize = toInitialize;
    }

    public StructureManagerScreenData(BlockPos pos, boolean isActive, boolean toInitialize, Optional<Identifier> structureManagerId){
        this.pos = pos;
        setActive(isActive);
        setToInitialize(toInitialize);
        structureManagerId.ifPresentOrElse(x -> setStructureManagerIdentifier(x), () -> setStructureManagerIdentifier(null));
    }

    static {
        PACKET_CODEC = PacketCodec.tuple(
                BlockPos.PACKET_CODEC, StructureManagerScreenData::getPos,
                PacketCodecs.BOOLEAN, StructureManagerScreenData::getIsActive,
                PacketCodecs.BOOLEAN, StructureManagerScreenData::getToInitialize,
                PacketCodecs.optional(Identifier.PACKET_CODEC), StructureManagerScreenData::getStructureManagerIdentifierOptional,
                StructureManagerScreenData::new
        );
    }
}
