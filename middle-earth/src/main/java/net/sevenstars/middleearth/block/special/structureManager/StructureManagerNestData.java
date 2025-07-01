package net.sevenstars.middleearth.block.special.structureManager;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.utils.IdentifierUtil;

public class StructureManagerNestData {
    public static final Codec<StructureManagerNestData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("id").forGetter(StructureManagerNestData::getId),
            Codec.INT.fieldOf("respawn_tick_progress").forGetter(StructureManagerNestData::getRespawnTickProgress)
    ).apply(instance, StructureManagerNestData::new));

    private final Identifier id;
    private int respawnTickProgress;

    public StructureManagerNestData() {
        this.id = IdentifierUtil.getIdentifierFromString("null");
        respawnTickProgress = 0;
    }

    private StructureManagerNestData(Identifier id, int respawnTickDelay) {
        this.id = id;
        this.respawnTickProgress = respawnTickDelay;
    }

    public Identifier getId() {
        return this.id;
    }

    public int getRespawnTickProgress() {
        return this.respawnTickProgress;
    }

    public void setRespawnTickProgress(int value){
        this.respawnTickProgress = value;
    }
}
