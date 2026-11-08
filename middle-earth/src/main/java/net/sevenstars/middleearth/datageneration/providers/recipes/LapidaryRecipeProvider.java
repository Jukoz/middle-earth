package net.sevenstars.middleearth.datageneration.providers.recipes;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.sevenstars.middleearth.block.registration.StoneBlockSetRegistryME;
import net.sevenstars.middleearth.block.registration.WoodBlockSetRegistryME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.utils.SmithingTrimPatternsME;
import net.sevenstars.middleearth.recipe.lapidary.LapidaryKnowledge;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.ImpossibleCriterion;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

/**
 * The recipes of the lost lapidary arts: everything on the way to the Masterwork and Relic Lapidary Tools. Their ids
 * are under lapidary/masterwork/ or lapidary/relic/ (see {@link LapidaryKnowledge}): they need
 * the scroll and never unlock by themselves (an impossible criterion), only by reading the scroll.
 * <p>
 * Made from what Middle-earth and vanilla already have: gem glass is the only new material. Metal rods and plates
 * are ME's generic rod and armor plate; the metal is their trim, so the ingredients match the trim component.
 */
public class LapidaryRecipeProvider extends FabricRecipeProvider {
    private static final String MASTERWORK = "masterwork";
    private static final String RELIC = "relic";

    public LapidaryRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return "LapidaryRecipes";
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            final RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);
            final AdvancementCriterion<?> never = Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions());

            @Override
            public void generate() {
                // ---- Masterwork ----
                gemGlass(MASTERWORK, ResourceItemsME.CITRINE_GLASS, BlockRegistryME.CITRINE_BLOCK);
                gemGlass(MASTERWORK, ResourceItemsME.AMETHYST_GLASS, Items.AMETHYST_BLOCK);
                gemGlass(MASTERWORK, ResourceItemsME.RED_AGATE_GLASS, BlockRegistryME.RED_AGATE_BLOCK);
                gemGlass(MASTERWORK, ResourceItemsME.QUARTZ_GLASS, Items.QUARTZ_BLOCK);
                lenses(MASTERWORK, ResourceItemsME.REFINED_FOCUSING_LENSES, "gold", ResourceItemsME.CITRINE_GLASS,
                    ResourceItemsME.AMETHYST_GLASS, ResourceItemsME.RED_AGATE_GLASS, ResourceItemsME.QUARTZ_GLASS);
                shaped(MASTERWORK, ResourceItemsME.ENHANCED_GRINDING_WHEEL).pattern("NSN").pattern("SRS").pattern("NSN")
                    .input('N', ResourceItemsME.KHAZAD_STEEL_NUGGET).input('S', StoneBlockSetRegistryME.NURGON_SET.smoothBlocks.base())
                    .input('R', metal(ResourceItemsME.ROD, "steel"))
                    .criterion("never", never).offerTo(exporter, id(MASTERWORK, "enhanced_grinding_wheel"));
                shaped(MASTERWORK, ResourceItemsME.REFRACTION_LAMP).pattern("ESE").pattern("RGR").pattern("ERE")
                    .input('E', metal(ResourceItemsME.ARMOR_PLATE, "edhel_steel")).input('S', metal(ResourceItemsME.ARMOR_PLATE, "silver"))
                    .input('R', metal(ResourceItemsME.ROD, "edhel_steel")).input('G', Items.GLASS)
                    .criterion("never", never).offerTo(exporter, id(MASTERWORK, "refraction_lamp"));
                shaped(MASTERWORK, ResourceItemsME.WATER_PUMP).pattern("BNB").pattern("PHP").pattern("PLP")
                    .input('B', metal(ResourceItemsME.ROD, "bronze")).input('N', ResourceItemsME.BRONZE_NUGGET)
                    .input('P', ingredientFromTag(ItemTags.PLANKS)).input('H', Items.HONEYCOMB).input('L', Items.LEATHER)
                    .criterion("never", never).offerTo(exporter, id(MASTERWORK, "water_pump"));
                toolSet(MASTERWORK, ResourceItemsME.MASTERWORK_LAPIDARY_TOOLS, ResourceItemsME.REFINED_FOCUSING_LENSES,
                    ResourceItemsME.ENHANCED_GRINDING_WHEEL, ResourceItemsME.REFRACTION_LAMP, ResourceItemsME.WATER_PUMP);

                // ---- Relic ----
                gemGlass(RELIC, ResourceItemsME.ADAMANT_GLASS, BlockRegistryME.ADAMANT_BLOCK);
                gemGlass(RELIC, ResourceItemsME.RUBY_GLASS, BlockRegistryME.RUBY_BLOCK);
                gemGlass(RELIC, ResourceItemsME.SAPPHIRE_GLASS, BlockRegistryME.SAPPHIRE_BLOCK);
                gemGlass(RELIC, ResourceItemsME.BERYL_GLASS, BlockRegistryME.BERYL_BLOCK);
                lenses(RELIC, ResourceItemsME.EXQUISITE_FOCUSING_LENSES, "mithril", ResourceItemsME.ADAMANT_GLASS,
                    ResourceItemsME.RUBY_GLASS, ResourceItemsME.SAPPHIRE_GLASS, ResourceItemsME.BERYL_GLASS);
                shaped(RELIC, ResourceItemsME.KHAZAD_GRINDING_WHEEL).pattern("NMN").pattern("ORO").pattern("NMN")
                    .input('N', ResourceItemsME.MITHRIL_NUGGET).input('M', StoneBlockSetRegistryME.MEDGON_SET.smoothBlocks.base())
                    .input('O', Items.OBSIDIAN).input('R', metal(ResourceItemsME.ROD, "mithril"))
                    .criterion("never", never).offerTo(exporter, id(RELIC, "khazad_grinding_wheel"));
                shaped(RELIC, ResourceItemsME.EDHEL_BEAM_LAMP).pattern("PPP").pattern("RAR").pattern("PRP")
                    .input('P', metal(ResourceItemsME.ARMOR_PLATE, "mithril")).input('R', metal(ResourceItemsME.ROD, "mithril"))
                    .input('A', ResourceItemsME.ADAMANT_GLASS)
                    .criterion("never", never).offerTo(exporter, id(RELIC, "edhel_beam_lamp"));
                shaped(RELIC, ResourceItemsME.HOBBIT_WORK_PUMP).pattern("SNS").pattern("MHM").pattern("TLB")
                    .input('S', metal(ResourceItemsME.ROD, "silver")).input('N', ResourceItemsME.SILVER_NUGGET)
                    .input('M', WoodBlockSetRegistryME.MALLORN_SET.planksBlocks.base()).input('H', Items.HONEYCOMB)
                    .input('T', metal(ResourceItemsME.ROD, "steel")).input('L', Items.LEATHER).input('B', Items.SLIME_BALL)
                    .criterion("never", never).offerTo(exporter, id(RELIC, "hobbit_work_pump"));
                toolSet(RELIC, ResourceItemsME.RELIC_LAPIDARY_TOOLS, ResourceItemsME.EXQUISITE_FOCUSING_LENSES,
                    ResourceItemsME.KHAZAD_GRINDING_WHEEL, ResourceItemsME.EDHEL_BEAM_LAMP, ResourceItemsME.HOBBIT_WORK_PUMP);
            }

            /** Focusing lenses: the four gem glasses held in loops bent from three rods of the metal. */
            private void lenses(String art, Item lenses, String metalName, Item a, Item b, Item c, Item d) {
                shaped(art, lenses).pattern("ARB").pattern(" R ").pattern("CRD")
                    .input('A', a).input('B', b).input('C', c).input('D', d).input('R', metal(ResourceItemsME.ROD, metalName))
                    .criterion("never", never).offerTo(exporter, id(art, idOf(lenses)));
            }

            private String id(String art, String name) {
                return Identifier.of(MiddleEarth.MOD_ID, LapidaryKnowledge.RECIPE_FOLDER + art + "/" + name).toString();
            }

            private ShapedRecipeJsonBuilder shaped(String art, ItemConvertible output) {
                return ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.MISC, output, 1).group("lapidary_" + art);
            }

            /** A generic rod or plate of one metal (the metal is its trim material). */
            private Ingredient metal(Item shape, String metalName) {
                boolean vanilla = metalName.equals("iron") || metalName.equals("gold") || metalName.equals("copper");
                RegistryKey<ArmorTrimMaterial> material = RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
                    vanilla ? Identifier.ofVanilla(metalName) : Identifier.of(MiddleEarth.MOD_ID, metalName));
                ArmorTrim trim = new ArmorTrim(registries.getOrThrow(RegistryKeys.TRIM_MATERIAL).getOrThrow(material),
                    registries.getOrThrow(RegistryKeys.TRIM_PATTERN).getOrThrow(SmithingTrimPatternsME.SMITHING_PART));
                return DefaultCustomIngredients.components(Ingredient.ofItem(shape),
                    ComponentChanges.builder().add(DataComponentTypes.TRIM, trim).build());
            }

            /** Gem glass: the crystal block among glowstone dust, two pieces. */
            private void gemGlass(String art, Item glass, ItemConvertible crystal) {
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.MISC, glass, 2).group("lapidary_" + art)
                    .pattern(" G ").pattern("GBG").pattern(" G ")
                    .input('G', Items.GLOWSTONE_DUST).input('B', crystal)
                    .criterion("never", never).offerTo(exporter, id(art, idOf(glass)));
            }

            /** A tool set: its four parts. */
            private void toolSet(String art, Item tools, Item lenses, Item wheel, Item lamp, Item pump) {
                shaped(art, tools).pattern(" L ").pattern("WAP")
                    .input('L', lenses).input('W', wheel).input('A', lamp).input('P', pump)
                    .criterion("never", never).offerTo(exporter, id(art, idOf(tools)));
            }

            private String idOf(Item item) {
                return net.minecraft.registry.Registries.ITEM.getId(item).getPath();
            }
        };
    }
}
