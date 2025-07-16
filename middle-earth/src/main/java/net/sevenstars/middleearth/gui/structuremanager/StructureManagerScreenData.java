package net.sevenstars.middleearth.gui.structuremanager;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class StructureManagerScreenData{
    private BlockPos pos;
    private Identifier currentId;
    private boolean isActive;

    public static final PacketCodec<? super RegistryByteBuf, StructureManagerScreenData> PACKET_CODEC;

    public BlockPos getPos() {
        return this.pos;
    }
    public Identifier getCurrentId() {
        return this.currentId;
    }

    private Optional<Identifier> getCurrentIdOptional() {
        return Optional.ofNullable(this.currentId);
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setCurrentId(Identifier currentId) {
        this.currentId = currentId;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public StructureManagerScreenData(){

    }
    public StructureManagerScreenData(BlockPos pos, boolean isActive, Optional<Identifier> id){
        this.pos = pos;
        setActive(isActive);
        if(id.isPresent())
            setCurrentId(id.get());
        else
            id = null;
    }

    static {
        PACKET_CODEC = PacketCodec.tuple(
                BlockPos.PACKET_CODEC, StructureManagerScreenData::getPos,
                PacketCodecs.BOOLEAN, StructureManagerScreenData::getIsActive,
                PacketCodecs.optional(Identifier.PACKET_CODEC), StructureManagerScreenData::getCurrentIdOptional,
                StructureManagerScreenData::new
        );
    }
}
