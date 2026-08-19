package net.sevenstars.middleearth.gui.structuremanager.structurenest;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public class StructureNestScreenData {
    private BlockPos pos;
    private ResourceLocation structureManagerId;
    private ResourceLocation structureNestId;
    private int spawnRadius;
    private boolean isEnabled;

    public static final StreamCodec<? super RegistryFriendlyByteBuf, StructureNestScreenData> PACKET_CODEC;

    public BlockPos getPos() {
        return this.pos;
    }
    public int getSpawnRadius() {
        return this.spawnRadius;
    }
    public boolean getIsEnabled() {
        return this.isEnabled;
    }
    public ResourceLocation getStructureManagerId() {
        return this.structureManagerId;
    }
    public ResourceLocation getStructureNestId() {
        return this.structureNestId;
    }
    private Optional<ResourceLocation> getStructureManagerIdOptional() {
        return Optional.ofNullable(this.structureManagerId);
    }
    private Optional<ResourceLocation> getStructureNestIdOptional() {
        return Optional.ofNullable(this.structureNestId);
    }

    public void setStructureManagerId(ResourceLocation structureManagerId) {
        this.structureManagerId = structureManagerId;
    }
    public void setStructureNestId(ResourceLocation structureNestId) {
        this.structureNestId = structureNestId;
    }


    public StructureNestScreenData(BlockPos pos, Optional<ResourceLocation> structureManagerId, Optional<ResourceLocation> structureNestId, int spawnRadius, boolean isEnabled){
        this.pos = pos;
        structureManagerId.ifPresentOrElse(x -> setStructureManagerId(x), () -> setStructureManagerId(null));
        structureNestId.ifPresentOrElse(x -> setStructureNestId(x), () -> setStructureNestId(null));
        this.spawnRadius = spawnRadius;
        this.isEnabled = isEnabled;
    }

    static {
        PACKET_CODEC = StreamCodec.composite(
                BlockPos.STREAM_CODEC, StructureNestScreenData::getPos,
                ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), StructureNestScreenData::getStructureManagerIdOptional,
                ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), StructureNestScreenData::getStructureNestIdOptional,
                ByteBufCodecs.INT, StructureNestScreenData::getSpawnRadius,
                ByteBufCodecs.BOOL, StructureNestScreenData::getIsEnabled,
                StructureNestScreenData::new
        );
    }

    public void toggleActiveState() {
        this.isEnabled = !this.isEnabled;
    }
}
