package net.sevenstars.middleearth.entity.npcs.routine;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.sevenstars.middleearth.features.farming.FarmingItems;
import net.sevenstars.middleearth.item.FoodItemsME;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import net.sevenstars.middleearth.resources.datas.npc_jobs.NpcJob;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

// Every input is something other jobs carry home, so the trades live off each other
public final class TradeRecipes {
    private record Recipe(Set<Item> inputs, int count, Item output) {}

    public record Order(BlockPos from, Item input, int count, Item output) {}

    // No inputs means nothing to do, unless lenient (a smith still mends tools)
    // Cooking is a furnace recipe type that counts for inputs of that kind
    private record Trade(List<Recipe> recipes, @Nullable RecipeType<? extends AbstractCookingRecipe> cooking, Predicate<ItemStack> cookable, boolean lenient) {}

    private static final Map<Identifier, Trade> TRADES = new HashMap<>();

    private TradeRecipes() {}

    static {
        Item wildWheat = NatureBlockRegistryME.WILD_WHEAT.asItem();
        List<Recipe> kitchen = List.of(
                new Recipe(Set.of(Items.BROWN_MUSHROOM, Items.RED_MUSHROOM, NatureBlockRegistryME.WHITE_MUSHROOM.asItem()), 2, Items.MUSHROOM_STEW),
                new Recipe(Set.of(Items.BEETROOT, NatureBlockRegistryME.WILD_BEETROOT.asItem()), 3, Items.BEETROOT_SOUP),
                new Recipe(Set.of(Items.CARROT, FarmingItems.ONION, FarmingItems.LEEK, FoodItemsME.LETTUCE, FarmingItems.GARLIC, FoodItemsME.TOMATO,
                        FoodItemsME.BELL_PEPPER, FoodItemsME.CUCUMBER, NatureBlockRegistryME.WILD_CARROT.asItem(), NatureBlockRegistryME.WILD_ONION.asItem(),
                        NatureBlockRegistryME.WILD_LEEK.asItem(), NatureBlockRegistryME.WILD_LETTUCE.asItem(), NatureBlockRegistryME.WILD_GARLIC.asItem(),
                        NatureBlockRegistryME.WILD_TOMATO.asItem(), NatureBlockRegistryME.WILD_BELL_PEPPER.asItem(),
                        NatureBlockRegistryME.WILD_CUCUMBER.asItem()), 3, FoodItemsME.VEGETABLE_SOUP),
                new Recipe(Set.of(Items.COD, Items.SALMON), 2, FoodItemsME.FISH_STEW));
        List<Recipe> bakery = List.of(
                new Recipe(Set.of(Items.WHEAT, wildWheat), 3, Items.BREAD),
                new Recipe(Set.of(Items.SWEET_BERRIES, FoodItemsME.STRAWBERRIES, FoodItemsME.TOUGH_BERRIES), 3, FoodItemsME.BERRY_PIE));
        List<Recipe> pottery = List.of(
                new Recipe(Set.of(Items.CLAY_BALL), 1, Items.BRICK),
                new Recipe(Set.of(Items.CLAY), 1, Items.TERRACOTTA));

        // Smoker recipes count for a kitchen too, covering fish and game
        add(NpcJobs.COOK, new Trade(kitchen, RecipeType.SMOKING, stack -> true, false));
        add(NpcJobs.INNKEEPER, new Trade(kitchen, RecipeType.SMOKING, stack -> true, false));
        add(NpcJobs.BAKER, new Trade(bakery, null, stack -> false, false));
        // Ore from miners. Ore only, or the armoury's swords get melted down
        add(NpcJobs.SMITH, new Trade(List.of(), RecipeType.BLASTING,
                stack -> stack.isIn(ConventionalItemTags.ORES) || stack.isIn(ConventionalItemTags.RAW_MATERIALS), true));
        add(NpcJobs.POTTER, new Trade(pottery, null, stack -> false, true));
    }

    private static void add(RegistryKey<NpcJob> job, Trade trade) {
        TRADES.put(job.getValue(), trade);
    }

    private static boolean worksFromStock(@Nullable Identifier job) {
        return job != null && TRADES.containsKey(job);
    }

    public static boolean needsStock(@Nullable Identifier job) {
        return worksFromStock(job) && !TRADES.get(job).lenient();
    }

    // Picked at random so a kitchen with fish and mushrooms doesn't only cook fish
    public static @Nullable Order order(ServerWorld world, SiteStock stock, @Nullable Identifier job, BlockPos home, int range, Random random) {
        if(!worksFromStock(job))
            return null;
        Trade trade = TRADES.get(job);
        List<Order> possible = new ArrayList<>();
        for(SiteStock.Holding holding : stock.near(world, home, range)) {
            for(Recipe recipe : trade.recipes()) {
                if(recipe.inputs().contains(holding.item()) && holding.count() >= recipe.count())
                    possible.add(new Order(holding.place(), holding.item(), recipe.count(), recipe.output()));
            }
            if(trade.cooking() != null && trade.cookable().test(holding.item().getDefaultStack())) {
                Item cooked = cookedOf(world, trade.cooking(), holding.item());
                if(cooked != null)
                    possible.add(new Order(holding.place(), holding.item(), 1, cooked));
            }
        }
        return possible.isEmpty() ? null : possible.get(random.nextInt(possible.size()));
    }

    private static <T extends AbstractCookingRecipe> @Nullable Item cookedOf(ServerWorld world, RecipeType<T> type, Item raw) {
        SingleStackRecipeInput input = new SingleStackRecipeInput(new ItemStack(raw));
        return world.getRecipeManager().getFirstMatch(type, input, world)
                .map(entry -> entry.value().craft(input, world.getRegistryManager()).getItem()).orElse(null);
    }
}
