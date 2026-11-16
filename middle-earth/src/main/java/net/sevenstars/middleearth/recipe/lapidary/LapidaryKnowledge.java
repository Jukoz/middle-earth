package net.sevenstars.middleearth.recipe.lapidary;

import net.sevenstars.middleearth.MiddleEarth;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

/** Per-player lapidary tool sets learned from scrolls, gating the lapidary recipes. */
public final class LapidaryKnowledge {
    public static final String RECIPE_FOLDER = "lapidary/";
    public static final String SHARED = "shared";

    public static final AttachmentType<List<String>> LEARNED = AttachmentRegistry
        .<List<String>>builder()
        .persistent(Codec.STRING.listOf())
        .copyOnDeath()
        .initializer(List::of)
        .syncWith(PacketCodecs.STRING.collect(PacketCodecs.toList()), AttachmentSyncPredicate.targetOnly())
        .buildAndRegister(Identifier.of(MiddleEarth.MOD_ID, "lapidary_knowledge"));

    private LapidaryKnowledge() {
    }

    public static void register() {
    }

    public static boolean knows(PlayerEntity player, LapidaryTools tools) {
        List<String> learned = player.getAttached(LEARNED);
        return learned != null && learned.contains(tools.id());
    }

    public static @Nullable String art(RegistryKey<Recipe<?>> recipe) {
        Identifier id = recipe.getValue();
        if (!id.getNamespace().equals(MiddleEarth.MOD_ID) || !id.getPath().startsWith(RECIPE_FOLDER)) {
            return null;
        }
        String rest = id.getPath().substring(RECIPE_FOLDER.length());
        int slash = rest.indexOf('/');
        return slash < 0 ? null : rest.substring(0, slash);
    }

    public static boolean canMake(@Nullable PlayerEntity player, RegistryKey<Recipe<?>> recipe) {
        String art = art(recipe);
        if (art == null) {
            return true;
        }
        if (player == null) {
            return false;
        }
        if (art.equals(SHARED)) {
            for (LapidaryTools tools : LapidaryTools.values()) {
                if (knows(player, tools)) {
                    return true;
                }
            }
            return false;
        }
        for (LapidaryTools tools : LapidaryTools.values()) {
            if (art.equals(tools.id())) {
                return knows(player, tools);
            }
        }
        return false;
    }

    public static boolean isLocked(RegistryKey<Recipe<?>> recipe) {
        return art(recipe) != null;
    }

    public static boolean learn(ServerPlayerEntity player, LapidaryTools tools) {
        boolean known = knows(player, tools);
        if (!known) {
            List<String> learned = new ArrayList<>(player.getAttachedOrCreate(LEARNED));
            learned.add(tools.id());
            player.setAttached(LEARNED, List.copyOf(learned));
        }
        if (player.getServer() != null) {
            List<RecipeEntry<?>> recipes = new ArrayList<>();
            for (RecipeEntry<?> entry : player.getServer().getRecipeManager().values()) {
                String art = art(entry.id());
                if (art != null && (art.equals(SHARED) || art.equals(tools.id()))) {
                    recipes.add(entry);
                }
            }
            player.unlockRecipes(recipes);
            AdvancementEntry advancement = player.getServer().getAdvancementLoader().get(advancementId(tools));
            if (advancement != null) {
                AdvancementProgress progress = player.getAdvancementTracker().getProgress(advancement);
                for (String criterion : progress.getUnobtainedCriteria()) {
                    player.getAdvancementTracker().grantCriterion(advancement, criterion);
                }
            }
        }
        return !known;
    }

    public static Set<LapidaryTools> learned(PlayerEntity player) {
        Set<LapidaryTools> set = EnumSet.noneOf(LapidaryTools.class);
        for (LapidaryTools tools : LapidaryTools.values()) {
            if (knows(player, tools)) {
                set.add(tools);
            }
        }
        return set;
    }

    public static Identifier advancementId(LapidaryTools tools) {
        return Identifier.of(MiddleEarth.MOD_ID, "lapidary/tools/" + tools.id());
    }
}
