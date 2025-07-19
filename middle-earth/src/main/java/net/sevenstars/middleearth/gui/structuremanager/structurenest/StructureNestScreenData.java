package net.sevenstars.middleearth.gui.structuremanager.structurenest;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class StructureNestScreenData {
    private BlockPos pos;
    private Identifier structureManagerId;
    private Identifier structureNestId;
    private int spawnRadius;

    public static final PacketCodec<? super RegistryByteBuf, StructureNestScreenData> PACKET_CODEC;

    public BlockPos getPos() {
        return this.pos;
    }
    public int getSpawnRadius() {
        return this.spawnRadius;
    }
    public Identifier getStructureManagerId() {
        return this.structureManagerId;
    }
    public Identifier getStructureNestId() {
        return this.structureNestId;
    }
    private Optional<Identifier> getStructureManagerIdOptional() {
        return Optional.ofNullable(this.structureManagerId);
    }
    private Optional<Identifier> getStructureNestIdOptional() {
        return Optional.ofNullable(this.structureNestId);
    }

    public void setStructureManagerId(Identifier structureManagerId) {
        this.structureManagerId = structureManagerId;
    }
    public void setStructureNestId(Identifier structureNestId) {
        this.structureNestId = structureNestId;
    }


    public StructureNestScreenData(BlockPos pos, Optional<Identifier> structureManagerId, Optional<Identifier> structureNestId, int spawnRadius){
        this.pos = pos;
        structureManagerId.ifPresentOrElse(x -> setStructureManagerId(x), () -> setStructureManagerId(null));
        structureNestId.ifPresentOrElse(x -> setStructureNestId(x), () -> setStructureNestId(null));
        this.spawnRadius = spawnRadius;
    }

    static {
        PACKET_CODEC = PacketCodec.tuple(
                BlockPos.PACKET_CODEC, StructureNestScreenData::getPos,
                PacketCodecs.optional(Identifier.PACKET_CODEC), StructureNestScreenData::getStructureManagerIdOptional,
                PacketCodecs.optional(Identifier.PACKET_CODEC), StructureNestScreenData::getStructureNestIdOptional,
                PacketCodecs.INTEGER, StructureNestScreenData::getSpawnRadius,
                StructureNestScreenData::new
        );
    }
}
