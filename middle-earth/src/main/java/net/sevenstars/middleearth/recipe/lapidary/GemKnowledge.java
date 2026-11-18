package net.sevenstars.middleearth.recipe.lapidary;

import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.recipe.RecipeSerializerRegistryME;

import java.util.HashMap;
import java.util.Map;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.gems.GemType;
import com.mojang.serialization.Codec;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

/** Per-player best study grade for each gem type, mirrored as Lapidary advancements. */
public final class GemKnowledge {
    public static final AttachmentType<Map<Identifier, StudyGrade>> KNOWLEDGE = AttachmentRegistry
        .<Map<Identifier, StudyGrade>>builder()
        .persistent(Codec.unboundedMap(Identifier.CODEC, StudyGrade.CODEC))
        .copyOnDeath()
        .initializer(Map::of)
        .syncWith(PacketCodecs.map(HashMap::new, Identifier.PACKET_CODEC, StudyGrade.PACKET_CODEC)
            .<Map<Identifier, StudyGrade>>xmap(map -> map, HashMap::new), AttachmentSyncPredicate.targetOnly())
        .buildAndRegister(Identifier.of(MiddleEarth.MOD_ID, "gem_knowledge"));

    private GemKnowledge() {
    }

    public static void register() {
    }

    public static StudyGrade best(PlayerEntity player, RegistryKey<GemType> gem) {
        Map<Identifier, StudyGrade> known = player.getAttached(KNOWLEDGE);
        return known == null ? null : known.get(gem.getValue());
    }

    public static boolean record(ServerPlayerEntity player, RegistryKey<GemType> gem, StudyGrade grade) {
        StudyGrade before = best(player, gem);
        grantAdvancements(player, gem, grade);
        if (before != null && before.atLeast(grade)) {
            return false;
        }
        Map<Identifier, StudyGrade> known = new HashMap<>(player.getAttachedOrCreate(KNOWLEDGE));
        known.put(gem.getValue(), grade);
        player.setAttached(KNOWLEDGE, Map.copyOf(known));
        return true;
    }

    public static Identifier advancementId(RegistryKey<GemType> gem, StudyGrade grade) {
        return Identifier.of(MiddleEarth.MOD_ID, "lapidary/" + gem.getValue().getPath() + "/" + grade.asString());
    }

    private static void grantAdvancements(ServerPlayerEntity player, RegistryKey<GemType> gem, StudyGrade grade) {
        if (player.getServer() == null || !gem.getValue().getNamespace().equals(MiddleEarth.MOD_ID)) {
            return;
        }
        for (StudyGrade reached : StudyGrade.values()) {
            if (!grade.atLeast(reached)) {
                break;
            }
            AdvancementEntry advancement = player.getServer().getAdvancementLoader().get(advancementId(gem, reached));
            if (advancement == null) {
                continue;
            }
            AdvancementProgress progress = player.getAdvancementTracker().getProgress(advancement);
            if (!progress.isDone()) {
                for (String criterion : progress.getUnobtainedCriteria()) {
                    player.getAdvancementTracker().grantCriterion(advancement, criterion);
                }
            }
        }
    }

}
