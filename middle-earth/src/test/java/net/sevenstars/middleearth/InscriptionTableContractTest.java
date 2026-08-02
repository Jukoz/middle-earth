package net.sevenstars.middleearth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class InscriptionTableContractTest {
    private static final Path MAIN_JAVA = Path.of("src/main/java");
    private static final Path MAIN_RESOURCES = Path.of("src/main/resources");

    @Test
    void emptySlotSpritesUseTheMinecraft1211BlockAtlasContract() throws IOException {
        String screen = source("gui/inscriptiontable/InscriptionTableScreen.java");
        assertTrue(screen.contains("item/empty_slot_emerald"));
        assertTrue(screen.contains("item/empty_slot_lapis_lazuli"));
        assertFalse(screen.contains("withDefaultNamespace(\"container/slot/emerald\")"));
        assertFalse(screen.contains("withDefaultNamespace(\"container/slot/lapis_lazuli\")"));

        JsonArray sources = JsonParser.parseString(Files.readString(
                MAIN_RESOURCES.resolve("assets/minecraft/atlases/blocks.json")
        )).getAsJsonObject().getAsJsonArray("sources");
        Map<String, String> slotAliases = new HashMap<>();
        for (var element : sources) {
            JsonObject source = element.getAsJsonObject();
            if (source.has("sprite")
                    && source.get("sprite").getAsString().contains(":container/slot/")) {
                slotAliases.put(
                        source.get("sprite").getAsString(),
                        source.get("resource").getAsString()
                );
            }
        }

        assertEquals(Map.of(
                "middle-earth:container/slot/adamant", "middle-earth:gui/sprites/container/slot/adamant",
                "middle-earth:container/slot/ruby", "middle-earth:gui/sprites/container/slot/ruby",
                "middle-earth:container/slot/sapphire", "middle-earth:gui/sprites/container/slot/sapphire",
                "middle-earth:container/slot/chisel", "middle-earth:gui/sprites/container/slot/chisel"
        ), slotAliases);
    }

    @Test
    void replacingTheThirdWordSendsTheWordActuallyRemoved() throws IOException {
        String screen = source("gui/inscriptiontable/InscriptionTableScreen.java");
        int remove = screen.indexOf("String removedWord = this.selectedWords.removeLast();");
        int send = screen.indexOf(
                "this.sendWordUpdate(false, removedWord)",
                remove
        );
        assertTrue(remove >= 0 && send > remove);
        assertFalse(screen.contains(
                "this.sendWordUpdate(false, this.selectedWords.getLast())"
        ));
    }

    @Test
    void finalDurabilityUseConsumesFiniteInputsAndLeavesMithrilInfinite() throws IOException {
        String handler = source("gui/inscriptiontable/InscriptionTableScreenHandler.java");
        assertTrue(handler.contains("if (!stackChisel.is(ToolItemsME.MITHRIL_CHISEL))"));
        assertTrue(handler.contains("damageOrConsumeInput(0, stackCatalyst)"));
        assertTrue(handler.contains("damageOrConsumeInput(1, stackChisel)"));
        assertTrue(handler.contains("int nextDamage = stack.getDamageValue() + 1;"));
        assertTrue(handler.contains("if (nextDamage >= stack.getMaxDamage())"));
        assertFalse(handler.contains("getDamageValue() == stackChisel.getMaxDamage()"));
    }

    @Test
    void confirmationRevalidatesTheCurrentServerRecipeAndInputs() throws IOException {
        String handler = source("gui/inscriptiontable/InscriptionTableScreenHandler.java");
        assertTrue(handler.contains("RecipeHolder<InscriptionRecipe> recipe = findSelectedRecipe();"));
        assertTrue(handler.contains("this.world.getRecipeManager().getRecipesFor("));
        assertTrue(handler.contains("new SingleRecipeInput(this.input.getItem(1))"));
        assertTrue(handler.contains("words.containsAll(recipe.value().inputWords)"));
        assertTrue(handler.contains("recipe.value().inputWords.equals(this.selectedWords)"));
        assertTrue(handler.contains("canEnchant(this.input.getItem(2), recipe.value().enchant, recipe.value().level)"));

        String confirmation = source("network/packets/C2S/InscriptionConfirmationPacket.java");
        assertTrue(confirmation.contains("player.containerMenu instanceof InscriptionTableScreenHandler"));
        assertTrue(confirmation.contains("screenHandler.stillValid(player)"));
        assertTrue(confirmation.contains("screenHandler.canConfirmSelection()"));
    }

    @Test
    void slotChangesDiscardSelectionsBeforeLoadingCurrentRecipeChoices() throws IOException {
        String handler = source("gui/inscriptiontable/InscriptionTableScreenHandler.java");
        int updateInput = handler.indexOf("private void updateInput(Container inventory)");
        int loadRecipes = handler.indexOf("this.world.getRecipeManager().getRecipesFor(", updateInput);
        int recomputeWords = handler.indexOf("updateWords(false, \"\", true);", loadRecipes);
        assertTrue(updateInput >= 0 && loadRecipes > updateInput && recomputeWords > loadRecipes);
        assertTrue(handler.contains("this.selectionRevision.set(this.selectionRevision.get() + 1);"));
        assertTrue(handler.contains("this.selectedWords.clear();"));

        String screen = source("gui/inscriptiontable/InscriptionTableScreen.java");
        assertTrue(screen.contains("selectionRevision != this.lastSelectionRevision"));
        assertTrue(screen.contains("this.clearLocalSelection();"));
    }

    @Test
    void inscriptionPacketsAreBoundToTheCurrentMenuAndSelectionRevision() throws IOException {
        String wordPacket = source("network/packets/C2S/InscriptionWordUpdatePacket.java");
        String confirmation = source("network/packets/C2S/InscriptionConfirmationPacket.java");
        String infoPacket = source("network/packets/S2C/InscriptionEnchantInfoPacket.java");
        String clientHandler = source("network/ModClientNetworkHandler.java");
        String screen = source("gui/inscriptiontable/InscriptionTableScreen.java");

        for (String packet : new String[] {wordPacket, confirmation}) {
            assertTrue(packet.contains("screenHandler.containerId == this.containerId"));
            assertTrue(packet.contains("screenHandler.getSelectionRevision() == this.selectionRevision"));
        }
        assertTrue(infoPacket.contains("ByteBufCodecs.INT, p -> p.containerId"));
        assertTrue(infoPacket.contains("ByteBufCodecs.INT, p -> p.selectionRevision"));
        assertTrue(clientHandler.contains("screenHandler.containerId == packet.containerId()"));
        assertTrue(clientHandler.contains("screenHandler.updateAvailableWords(packet.selectionRevision(), packet.words())"));
        assertTrue(screen.contains("if (wordIndex < 0 || wordIndex >= currentWords.size())"));
    }

    @Test
    void availableWordsFollowTheCurrentCatalystChiselAndExactPrefix() throws IOException {
        String handler = source("gui/inscriptiontable/InscriptionTableScreenHandler.java");
        assertTrue(handler.contains("for (RecipeHolder<InscriptionRecipe> recipe : this.outputRecipes)"));
        assertTrue(handler.contains("!words.containsAll(recipeWords)"));
        assertTrue(handler.contains("!hasSelectedPrefix(recipeWords)"));
        assertTrue(handler.contains("allowedWords.add(recipeWords.get(this.selectedWords.size()))"));
        assertFalse(handler.contains("getAllRecipesFor(RecipesME.INSCRIPTION_TABLE)"));
    }

    @Test
    void creativeModePreservesInputsAndInventoryRoutesAreBounded() throws IOException {
        String handler = source("gui/inscriptiontable/InscriptionTableScreenHandler.java");
        int enchantItem = handler.indexOf("public void enchantItem()");
        int survivalConsumption = handler.indexOf("if (!this.player.hasInfiniteMaterials())", enchantItem);
        int catalystConsumption = handler.indexOf("if (stackCatalyst.is(Items.LAPIS_LAZULI))", survivalConsumption);
        int experienceConsumption = handler.indexOf("this.player.giveExperienceLevels(-cost);", catalystConsumption);
        int applyEnchantment = handler.indexOf("stack.enchant(recipe.value().enchant, recipe.value().level);", experienceConsumption);
        assertTrue(enchantItem >= 0
                && survivalConsumption > enchantItem
                && catalystConsumption > survivalConsumption
                && experienceConsumption > catalystConsumption
                && applyEnchantment > experienceConsumption);
        assertTrue(handler.contains("if (slot < 0 || slot >= this.slots.size())"));
        assertTrue(handler.contains("this.clearContainer(player, this.input);"));

        int targetSlot = handler.indexOf("this.addSlot(new Slot(this.input, 2, 180, 48)");
        int targetStackLimit = handler.indexOf("public int getMaxStackSize()", targetSlot);
        assertTrue(targetSlot >= 0 && targetStackLimit > targetSlot);
    }

    @Test
    void allActiveVanillaChiselTierProgressionsAreMonotonic() throws IOException {
        String recipes = source("datageneration/providers/recipes/InscriptionRecipeProvider.java");
        String activeRecipes = recipes
                .replaceAll("(?s)/\\*.*?\\*/", "")
                .replaceAll("(?m)//.*$", "");

        Matcher matcher = Pattern.compile(
                "getEnchantment\\(Enchantments\\.([A-Z0-9_]+)\\),\\s*(\\d+),\\s*\\d+\\)"
                        + "\\s*\\.chisel\\(ItemTagsME\\.([A-Z]+)_CHISELS\\)",
                Pattern.DOTALL
        ).matcher(activeRecipes);
        Map<String, Map<Integer, String>> tiersByEnchantment = new HashMap<>();
        int recipeCount = 0;
        while (matcher.find()) {
            tiersByEnchantment
                    .computeIfAbsent(matcher.group(1), ignored -> new TreeMap<>())
                    .put(Integer.parseInt(matcher.group(2)), matcher.group(3));
            recipeCount++;
        }

        assertEquals(69, recipeCount);
        assertEquals(20, tiersByEnchantment.values().stream().filter(tiers -> tiers.size() > 1).count());
        assertEquals(
                Map.of(1, "EARLY", 2, "MID", 3, "MID"),
                tiersByEnchantment.get("UNBREAKING")
        );

        for (var enchantment : tiersByEnchantment.entrySet()) {
            if (enchantment.getValue().size() < 2) {
                continue;
            }
            int previousRank = 0;
            for (var tierAtLevel : enchantment.getValue().entrySet()) {
                int currentRank = tierRank(tierAtLevel.getValue());
                assertTrue(
                        currentRank >= previousRank,
                        enchantment.getKey() + " becomes easier at level " + tierAtLevel.getKey()
                );
                previousRank = currentRank;
            }
        }
    }

    private static int tierRank(String tier) {
        return switch (tier) {
            case "EARLY" -> 1;
            case "MID" -> 2;
            case "LATE" -> 3;
            default -> throw new IllegalArgumentException("Unknown chisel tier: " + tier);
        };
    }

    private static String source(String relativePath) throws IOException {
        return Files.readString(MAIN_JAVA.resolve("net/sevenstars/middleearth").resolve(relativePath));
    }
}
