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

/**
 * The lost arts of the lapidary: which tool sets a character has learned to make, from the Masterwork and Relic
 * scrolls. Kept on the player (saved, kept through death, synced to that player).
 * <p>
 * Every recipe on the way to a tool set lives under {@code middle-earth:lapidary/<art>/...}: {@code masterwork}
 * and {@code relic} need that scroll, {@code shared} needs either. Those recipes cannot be made without the
 * knowledge (crafting table, crafter, anvil, grindstone), and they never unlock in the recipe book by themselves:
 * reading a scroll unlocks them.
 */
public final class LapidaryKnowledge {
    public static final String RECIPE_FOLDER = "lapidary/";
    public static final String SHARED = "shared";

    /** The ids of the learned tool sets ("masterwork", "relic"). */
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
        // Loads the class, which registers the attachment.
    }

    public static boolean knows(PlayerEntity player, LapidaryTools tools) {
        List<String> learned = player.getAttached(LEARNED);
        return learned != null && learned.contains(tools.id());
    }

    /** The art a lapidary recipe belongs to ("masterwork", "relic", "shared"), or null for any other recipe. */
    public static @Nullable String art(RegistryKey<Recipe<?>> recipe) {
        Identifier id = recipe.getValue();
        if (!id.getNamespace().equals(MiddleEarth.MOD_ID) || !id.getPath().startsWith(RECIPE_FOLDER)) {
            return null;
        }
        String rest = id.getPath().substring(RECIPE_FOLDER.length());
        int slash = rest.indexOf('/');
        return slash < 0 ? null : rest.substring(0, slash);
    }

    /** Whether the player may make this recipe: anything that is not lapidary knowledge, or knowledge they have. */
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

    /** True for recipes that are lapidary knowledge (they may not be made by a crafter, which has no player). */
    public static boolean isLocked(RegistryKey<Recipe<?>> recipe) {
        return art(recipe) != null;
    }

    /**
     * The player learns a tool set: its recipes (and the shared ones) unlock in their recipe book, and the
     * advancement is granted.
     *
     * @return true if it was new to them
     */
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

    /** The tool sets the player has learned. */
    public static Set<LapidaryTools> learned(PlayerEntity player) {
        Set<LapidaryTools> set = EnumSet.noneOf(LapidaryTools.class);
        for (LapidaryTools tools : LapidaryTools.values()) {
            if (knows(player, tools)) {
                set.add(tools);
            }
        }
        return set;
    }

    /** Advancement id for a learned tool set, e.g. middle-earth:lapidary/tools/masterwork. */
    public static Identifier advancementId(LapidaryTools tools) {
        return Identifier.of(MiddleEarth.MOD_ID, "lapidary/tools/" + tools.id());
    }
}
