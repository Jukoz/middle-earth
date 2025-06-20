package net.sevenstars.middleearth.resources.datas.structure_datas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.utils.IdentifierUtil;

public class StructureData {
    public static final Codec<StructureData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(StructureData::getIdString),
            Codec.STRING.fieldOf("faction_id").forGetter(StructureData::getFactionIdString)
          ).apply(instance, StructureData::new));

    private final Identifier id;
    private final Identifier factionId;


    public StructureData(String id, String factionId) {
        this.id = IdentifierUtil.getIdentifierFromString(id);
        this.factionId = IdentifierUtil.getIdentifierFromString(factionId);
    }

    public StructureData(Identifier id, Identifier factionId) {
        this.id = id;
        this.factionId = factionId;
    }

    private String getIdString() {
        return this.id.toString();
    }
    public Identifier getId() {
        return this.id;
    }
    private String getFactionIdString() {
        return this.factionId.toString();
    }
    public Identifier getFactionId() {
        return this.factionId;
    }
}
