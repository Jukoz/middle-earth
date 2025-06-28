package net.sevenstars.middleearth.resources.datas.structure_datas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.utils.IdentifierUtil;

public class StructureNestData {
    public static final Codec<StructureNestData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(StructureNestData::getIdString)
    ).apply(instance, StructureNestData::new));

    private final Identifier id;


    public StructureNestData(String id) {
        this.id = IdentifierUtil.getIdentifierFromString(id);
    }

    public StructureNestData(Identifier id) {
        this.id = id;
    }

    private String getIdString() {
        return this.id.toString();
    }
    public Identifier getId() {
        return this.id;
    }
}
