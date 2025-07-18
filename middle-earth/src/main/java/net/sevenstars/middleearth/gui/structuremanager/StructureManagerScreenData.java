package net.sevenstars.middleearth.gui.structuremanager;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class StructureManagerScreenData{
    private BlockPos pos;
    private Identifier selectedId;
    private Identifier runtimeId;
    private boolean isActive;

    public static final PacketCodec<? super RegistryByteBuf, StructureManagerScreenData> PACKET_CODEC;

    public BlockPos getPos() {
        return this.pos;
    }
    public Identifier getSelectedId() {
        return this.selectedId;
    }
    public Identifier getRuntimeId() {
        return this.runtimeId;
    }
    private Optional<Identifier> getSelectedIdOptional() {
        return Optional.ofNullable(this.selectedId);
    }
    private Optional<Identifier> getRuntimeIdOptional() {
        return Optional.ofNullable(this.runtimeId);
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setSelectedId(Identifier selectedId) {
        this.selectedId = selectedId;
    }
    public void setRuntimeId(Identifier runtimeId) {
        this.runtimeId = runtimeId;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public StructureManagerScreenData(){

    }
    public StructureManagerScreenData(BlockPos pos, boolean isActive, Optional<Identifier> selectedId, Optional<Identifier> runtimeId){
        this.pos = pos;
        setActive(isActive);
        selectedId.ifPresentOrElse(x -> setSelectedId(x), () -> setSelectedId(null));
        runtimeId.ifPresentOrElse(x -> setRuntimeId(x), () -> setRuntimeId(null));
    }

    static {
        PACKET_CODEC = PacketCodec.tuple(
                BlockPos.PACKET_CODEC, StructureManagerScreenData::getPos,
                PacketCodecs.BOOLEAN, StructureManagerScreenData::getIsActive,
                PacketCodecs.optional(Identifier.PACKET_CODEC), StructureManagerScreenData::getSelectedIdOptional,
                PacketCodecs.optional(Identifier.PACKET_CODEC), StructureManagerScreenData::getRuntimeIdOptional,
                StructureManagerScreenData::new
        );
    }
}
