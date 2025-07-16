package net.sevenstars.middleearth.gui.structuremanager;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

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
    public StructureManagerScreenData(BlockPos pos, Identifier id, boolean isActive){
        this.pos = pos;
        setActive(isActive);
        setCurrentId(id);
    }

    static {
        PACKET_CODEC = PacketCodec.tuple(
                BlockPos.PACKET_CODEC, StructureManagerScreenData::getPos,
                Identifier.PACKET_CODEC, StructureManagerScreenData::getCurrentId,
                PacketCodecs.BOOLEAN, StructureManagerScreenData::getIsActive,
                StructureManagerScreenData::new
        );
    }
}
