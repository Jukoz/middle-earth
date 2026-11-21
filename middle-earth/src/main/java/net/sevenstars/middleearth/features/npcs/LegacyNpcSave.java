package net.sevenstars.middleearth.features.npcs;

import com.mojang.serialization.Codec;
import net.minecraft.registry.Registry;
import net.minecraft.storage.ReadView;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.features.npcs.classes.NpcClass;
import net.sevenstars.middleearth.features.npcs.classes.NpcRegistry;
import net.sevenstars.middleearth.features.npcs.data.NpcData;
import net.sevenstars.middleearth.features.npcs.data.NpcInitializationData;
import net.sevenstars.middleearth.features.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

import java.util.Optional;

// Reads saves from before npc classes were registered data
final class LegacyNpcSave {
    record Saved(NpcData data, NpcInitializationData initialization, NpcTextureData texture) {}

    private LegacyNpcSave() {}

    static Saved migrate(NpcEntity npc, ReadView view, Saved saved) {
        NpcData data = saved.data();
        NpcInitializationData initialization = saved.initialization();
        NpcTextureData texture = saved.texture();

        Identifier savedClass = view.getOptionalReadView(NpcEntity.KeyStrings.DATA).flatMap(old -> old.read("Type", Identifier.CODEC)).orElse(null);
        NpcRegistry.Folded folded = savedClass == null ? null : NpcRegistry.FOLDED.get(savedClass);
        if(folded != null) {
            ReadView old = view.getOptionalReadView(NpcEntity.KeyStrings.DATA).orElseThrow();
            Registry<NpcClass> classes = npc.getEntityWorld().getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_CLASS);
            NpcClass base = classes.get(folded.npcClass().getValue());
            data = new NpcData(Optional.ofNullable(base).map(classes::getEntry), old.read("Category", Codec.STRING),
                    old.read("SpawnReason", Codec.STRING), old.read("StructureManagerPos", BlockPos.CODEC));
            if(npc.getJobId() == null)
                npc.setJob(folded.job().getValue());
        }

        String npcDataId = view.read("NpcDataId", Codec.STRING).orElse(null);
        if(npcDataId != null) {
            initialization = initialization.withNpcClass(MiddleEarth.ofId(npcDataId));
            data = new NpcData();
            texture = new NpcTextureData();
        }
        return new Saved(data, initialization, texture);
    }
}
