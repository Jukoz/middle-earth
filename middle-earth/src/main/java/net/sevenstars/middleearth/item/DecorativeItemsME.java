package net.sevenstars.middleearth.item;

import net.minecraft.component.DataComponentTypes;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.special.LargeDoorBlock;
import net.sevenstars.middleearth.block.utils.BlockAuthor;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.item.dataComponents.BlockAuthorDataComponent;
import net.sevenstars.middleearth.item.items.CustomSpawnEggItem;
import net.sevenstars.middleearth.item.items.DoorBlockItem;
import net.sevenstars.middleearth.item.items.ReinforcedScaffoldingItem;
import net.sevenstars.middleearth.item.items.WateringCanItem;
import net.sevenstars.middleearth.item.items.weapons.utils.ArtefactUtils;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;
import net.sevenstars.middleearth.item.items.VerticallyAttachableBlockItemME;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;
import net.sevenstars.middleearth.registries.RegistryAliasesME;

import java.util.List;
import java.util.function.Function;

public class DecorativeItemsME {

    /**
     * Middle-earth mod Decorative Items registry for blocks
     */

    public static final Item DWARVEN_LANTERN = registerItem("dwarven_lantern",
            (settings) -> new VerticallyAttachableBlockItemME(ModDecorativeBlocks.DWARVEN_LANTERN, ModDecorativeBlocks.WALL_DWARVEN_LANTERN, settings, new Direction[]{Direction.DOWN, Direction.UP}), new Item.Settings());
    public static final Item CRYSTAL_LAMP = registerItem("crystal_lamp",
            (settings) -> new VerticallyAttachableBlockItemME(ModDecorativeBlocks.CRYSTAL_LAMP, ModDecorativeBlocks.WALL_CRYSTAL_LAMP, settings, new Direction[]{Direction.DOWN, Direction.UP}), new Item.Settings());
    public static final Item SILVER_LANTERN = registerItem("silver_lantern",
            (settings) -> new VerticallyAttachableBlockItemME(ModDecorativeBlocks.SILVER_LANTERN, ModDecorativeBlocks.WALL_SILVER_LANTERN, settings, new Direction[]{Direction.DOWN, Direction.UP}), new Item.Settings());
    public static final Item ELVEN_LANTERN = registerItem("elven_lantern",
            (settings) -> new VerticallyAttachableBlockItemME(ModDecorativeBlocks.ELVEN_LANTERN, ModDecorativeBlocks.WALL_ELVEN_LANTERN, settings, new Direction[]{Direction.DOWN, Direction.UP}), new Item.Settings());
    public static final Item TREATED_STEEL_LANTERN = registerItem("treated_steel_lantern",
            (settings) -> new VerticallyAttachableBlockItemME(ModDecorativeBlocks.TREATED_STEEL_LANTERN, ModDecorativeBlocks.WALL_TREATED_STEEL_LANTERN, settings, new Direction[]{Direction.DOWN, Direction.UP}), new Item.Settings());
    public static final Item CRUDE_LANTERN = registerItem("crude_lantern",
            (settings) -> new VerticallyAttachableBlockItemME(ModDecorativeBlocks.CRUDE_LANTERN, ModDecorativeBlocks.WALL_CRUDE_LANTERN, settings, new Direction[]{Direction.DOWN, Direction.UP}), new Item.Settings());
    public static final Item LEAD_LANTERN = registerItem("lead_lantern",
            (settings) -> new VerticallyAttachableBlockItemME(ModDecorativeBlocks.LEAD_LANTERN, ModDecorativeBlocks.WALL_LEAD_LANTERN, settings, new Direction[]{Direction.DOWN, Direction.UP}), new Item.Settings());

    public static final Item SCONCE = registerItem("sconce",
            (settings) -> new VerticallyAttachableBlockItem(ModDecorativeBlocks.SCONCE, ModDecorativeBlocks.WALL_SCONCE, Direction.DOWN, settings), new Item.Settings());
    public static final Item GILDED_SCONCE = registerItem("gilded_sconce",
            (settings) -> new VerticallyAttachableBlockItem(ModDecorativeBlocks.GILDED_SCONCE, ModDecorativeBlocks.GILDED_WALL_SCONCE, Direction.DOWN, settings), new Item.Settings());
    public static final Item ORCISH_SCONCE = registerItem("orcish_sconce",
            (settings) -> new VerticallyAttachableBlockItem(ModDecorativeBlocks.ORCISH_SCONCE, ModDecorativeBlocks.ORCISH_WALL_SCONCE, Direction.DOWN, settings), new Item.Settings());

    public static final Item FORGE = registerItem("forge",
            (settings) -> new BlockItem(ModDecorativeBlocks.FORGE, settings), new Item.Settings());

    public static final Item STONE_ANVIL = registerItem("stone_anvil",
            (settings) -> new BlockItem(ModDecorativeBlocks.STONE_ANVIL, settings), new Item.Settings());

    public static final Item TREATED_ANVIL = registerItem("treated_anvil",
            (settings) -> new BlockItem(ModDecorativeBlocks.TREATED_ANVIL, settings), new Item.Settings());
    public static final Item DWARVEN_TREATED_ANVIL = registerItem("dwarven_treated_anvil",
            (settings) -> new BlockItem(ModDecorativeBlocks.DWARVEN_TREATED_ANVIL, settings), new Item.Settings());
    public static final Item ELVEN_TREATED_ANVIL = registerItem("elven_treated_anvil",
            (settings) -> new BlockItem(ModDecorativeBlocks.ELVEN_TREATED_ANVIL, settings), new Item.Settings());
    public static final Item ORCISH_TREATED_ANVIL = registerItem("orcish_treated_anvil",
            (settings) -> new BlockItem(ModDecorativeBlocks.ORCISH_TREATED_ANVIL, settings), new Item.Settings());
    public static final Item BELLOWS = registerItem("bellows",
            (settings) -> new BlockItem(ModDecorativeBlocks.BELLOWS, settings), new Item.Settings());

    public static final Item REINFORCED_SCAFFOLDING = registerItem("reinforced_scaffolding",
            (settings) -> new ReinforcedScaffoldingItem(ModDecorativeBlocks.REINFORCED_SCAFFOLDING, settings), new Item.Settings());

    public static final Item ARTISAN_TABLE = registerItem("artisan_table",
            (settings) -> new BlockItem(ModDecorativeBlocks.ARTISAN_TABLE, settings), new Item.Settings());
    public static final Item INSCRIPTION_TABLE = registerItem("inscription_table",
            (settings) -> new BlockItem(ModDecorativeBlocks.INSCRIPTION_TABLE, settings), new Item.Settings());

    public static final Item STRUCTURE_MANAGER = registerItem("structure_manager",
            (settings) -> new BlockItem(ModDecorativeBlocks.STRUCTURE_MANAGER, settings), new Item.Settings());
    public static final Item ORC_STRUCTURE_MANAGER = registerItem("orc_structure_manager",
            (settings) -> new BlockItem(ModDecorativeBlocks.ORC_STRUCTURE_MANAGER, settings), new Item.Settings());
    public static final Item STRUCTURE_NEST = registerItem("structure_nest",
            (settings) -> new BlockItem(ModDecorativeBlocks.STRUCTURE_NEST, settings), new Item.Settings());

    public static final Item SMALL_CRATE = registerItem("small_crate",
            (settings) -> new BlockItem(ModDecorativeBlocks.SMALL_CRATE, settings), new Item.Settings());
    public static final Item THIN_BARREL = registerItem("thin_barrel",
            (settings) -> new BlockItem(ModDecorativeBlocks.THIN_BARREL, settings), new Item.Settings());
    public static final Item LARCH_COFFER = registerItem("larch_coffer",
            (settings) -> new BlockItem(ModDecorativeBlocks.LARCH_COFFER, settings), new Item.Settings());
    public static final Item PINE_COFFER = registerItem("pine_coffer",
            (settings) -> new BlockItem(ModDecorativeBlocks.PINE_COFFER, settings), new Item.Settings());
    public static final Item SPRUCE_COFFER = registerItem("spruce_coffer",
            (settings) -> new BlockItem(ModDecorativeBlocks.SPRUCE_COFFER, settings), new Item.Settings());
    public static final Item FIR_COFFER = registerItem("fir_coffer",
            (settings) -> new BlockItem(ModDecorativeBlocks.FIR_COFFER, settings), new Item.Settings());
    public static final Item BEECH_COFFER = registerItem("beech_coffer",
            (settings) -> new BlockItem(ModDecorativeBlocks.BEECH_COFFER, settings), new Item.Settings());
    public static final Item CHESTNUT_COFFER = registerItem("chestnut_coffer",
            (settings) -> new BlockItem(ModDecorativeBlocks.CHESTNUT_COFFER, settings), new Item.Settings());
    public static final Item OAK_COFFER = registerItem("oak_coffer",
            (settings) -> new BlockItem(ModDecorativeBlocks.OAK_COFFER, settings), new Item.Settings());
    public static final Item WILLOW_COFFER = registerItem("willow_coffer",
            (settings) -> new BlockItem(ModDecorativeBlocks.WILLOW_COFFER, settings), new Item.Settings());
    public static final Item REINFORCED_CHEST = registerItem("reinforced_chest",
            (settings) -> new BlockItem(ModDecorativeBlocks.REINFORCED_CHEST, settings), new Item.Settings());
    public static final Item SACK = registerItem("sack",
            (settings) -> new BlockItem(ModDecorativeBlocks.SACK, settings), new Item.Settings());

    public static final Item WOOD_PILE = registerItem("wood_pile",
            (settings) -> new BlockItem(ModDecorativeBlocks.WOOD_PILE, settings), new Item.Settings());

    public static final Item BASALT_STATUE = registerItem("basalt_statue",
            (settings) -> new BlockItem(ModDecorativeBlocks.BASALT_STATUE, settings), new Item.Settings()
                    .component(DataComponentTypesME.BLOCK_AUTHOR_DATA, new BlockAuthorDataComponent(BlockAuthor.SCOSHER)));
    public static final Item CALCITE_STATUE = registerItem("calcite_statue",
            (settings) -> new BlockItem(ModDecorativeBlocks.CALCITE_STATUE, settings), new Item.Settings()
                    .component(DataComponentTypesME.BLOCK_AUTHOR_DATA, new BlockAuthorDataComponent(BlockAuthor.BOENNDAL)));
    public static final Item DIORITE_STATUE = registerItem("diorite_statue",
            (settings) -> new BlockItem(ModDecorativeBlocks.DIORITE_STATUE, settings), new Item.Settings()
                    .component(DataComponentTypesME.BLOCK_AUTHOR_DATA, new BlockAuthorDataComponent(BlockAuthor.COFFEE_VIKING)));
    public static final Item GABBRO_STATUE = registerItem("gabbro_statue",
            (settings) -> new BlockItem(ModDecorativeBlocks.GABBRO_STATUE, settings), new Item.Settings()
                    .component(DataComponentTypesME.BLOCK_AUTHOR_DATA, new BlockAuthorDataComponent(BlockAuthor.SINDAVAR)));
    public static final Item GALONN_STATUE = registerItem("galonn_statue",
            (settings) -> new BlockItem(ModDecorativeBlocks.GALONN_STATUE, settings), new Item.Settings()
                    .component(DataComponentTypesME.BLOCK_AUTHOR_DATA, new BlockAuthorDataComponent(BlockAuthor.SCOSHER)));
    public static final Item KHAGALABAN_STATUE = registerItem("khagalaban_statue",
            (settings) -> new BlockItem(ModDecorativeBlocks.KHAGALABAN_STATUE, settings), new Item.Settings()
                    .component(DataComponentTypesME.BLOCK_AUTHOR_DATA, new BlockAuthorDataComponent(BlockAuthor.BOENNDAL)));
    public static final Item MEDGON_SPIKE = registerItem("medgon_spike",
            (settings) -> new BlockItem(ModDecorativeBlocks.MEDGON_SPIKE, settings), new Item.Settings()
                    .component(DataComponentTypesME.BLOCK_AUTHOR_DATA, new BlockAuthorDataComponent(BlockAuthor.BOENNDAL)));
    public static final Item PUMICE_STATUE = registerItem("pumice_statue",
            (settings) -> new BlockItem(ModDecorativeBlocks.PUMICE_STATUE, settings), new Item.Settings()
                    .component(DataComponentTypesME.BLOCK_AUTHOR_DATA, new BlockAuthorDataComponent(BlockAuthor.SCOSHER)));
    public static final Item TUFF_STATUE = registerItem("tuff_statue",
            (settings) -> new BlockItem(ModDecorativeBlocks.TUFF_STATUE, settings), new Item.Settings()
                    .component(DataComponentTypesME.BLOCK_AUTHOR_DATA, new BlockAuthorDataComponent(BlockAuthor.BOENNDAL)));
    public static final Item ZIGILABAN_STATUE = registerItem("zigilaban_statue",
            (settings) -> new BlockItem(ModDecorativeBlocks.ZIGILABAN_STATUE, settings), new Item.Settings()
                    .component(DataComponentTypesME.BLOCK_AUTHOR_DATA, new BlockAuthorDataComponent(BlockAuthor.NAUTILUS)));

    public static final Item TALL_BLACK_PINE_DOOR = registerItem("tall_black_pine_door",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.TALL_BLACK_PINE_DOOR, settings), new Item.Settings().maxCount(16));
    public static final Item TALL_FIR_DOOR = registerItem("tall_fir_door",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.TALL_FIR_DOOR, settings), new Item.Settings().maxCount(16));

    public static final Item OAK_STABLE_DOOR = registerItem("oak_stable_door",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.OAK_STABLE_DOOR, settings), new Item.Settings().maxCount(16));
    public static final Item REINFORCED_SPRUCE_DOOR = registerItem("reinforced_spruce_door",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.REINFORCED_SPRUCE_DOOR, settings), new Item.Settings().maxCount(16));
    public static final Item REINFORCED_BLACK_PINE_DOOR = registerItem("reinforced_black_pine_door",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.REINFORCED_BLACK_PINE_DOOR, settings), new Item.Settings().maxCount(16));
    public static final Item SIMPLE_LARCH_GATE = registerItem("simple_larch_gate",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.SIMPLE_LARCH_GATE, settings), new Item.Settings().maxCount(16));
    public static final Item RICKETY_SIMPLE_LARCH_DOOR = registerItem("rickety_simple_larch_door",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.RICKETY_SIMPLE_LARCH_DOOR, settings), new Item.Settings().maxCount(16));
    public static final Item SPRUCE_STABLE_DOOR = registerItem("spruce_stable_door",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.SPRUCE_STABLE_DOOR, settings), new Item.Settings().maxCount(16));

    public static final Item LARGE_STURDY_DOOR = registerItem("large_sturdy_door",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.LARGE_STURDY_DOOR, settings), new Item.Settings().maxCount(16));

    public static final Item LARGE_BEECH_FENCE_GATE = registerItem("large_beech_fence_gate",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.LARGE_BEECH_FENCE_GATE, settings), new Item.Settings().maxCount(16));

    public static final Item LARCH_HOBBIT_DOOR = registerItem("larch_hobbit_door",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.LARCH_HOBBIT_DOOR, settings), new Item.Settings().maxCount(16));
    public static final Item SPRUCE_HOBBIT_DOOR = registerItem("spruce_hobbit_door",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.SPRUCE_HOBBIT_DOOR, settings), new Item.Settings().maxCount(16));
    public static final Item BLUE_HOBBIT_DOOR = registerItem("blue_hobbit_door",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.BLUE_HOBBIT_DOOR, settings), new Item.Settings().maxCount(16));
    public static final Item GREEN_HOBBIT_DOOR = registerItem("green_hobbit_door",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.GREEN_HOBBIT_DOOR, settings), new Item.Settings().maxCount(16));
    public static final Item LIGHT_BLUE_HOBBIT_DOOR = registerItem("light_blue_hobbit_door",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.LIGHT_BLUE_HOBBIT_DOOR, settings), new Item.Settings().maxCount(16));
    public static final Item RED_HOBBIT_DOOR = registerItem("red_hobbit_door",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.RED_HOBBIT_DOOR, settings), new Item.Settings().maxCount(16));
    public static final Item YELLOW_HOBBIT_DOOR = registerItem("yellow_hobbit_door",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.YELLOW_HOBBIT_DOOR, settings), new Item.Settings().maxCount(16));

    public static final Item GREAT_GONDORIAN_GATE = registerItem("great_gondorian_gate",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.GREAT_GONDORIAN_GATE, settings), new Item.Settings().maxCount(16));

    public static final Item GREAT_DWARVEN_GATE = registerItem("great_dwarven_gate",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.GREAT_DWARVEN_GATE, settings), new Item.Settings().maxCount(16));
    public static final Item HIDDEN_DWARVEN_DOOR = registerItem("hidden_dwarven_door",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.HIDDEN_DWARVEN_DOOR, settings), new Item.Settings().maxCount(16));
    public static final Item VARNISHED_DWARVEN_DOOR = registerItem("varnished_dwarven_door",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.VARNISHED_DWARVEN_DOOR, settings), new Item.Settings().maxCount(16));
    public static final Item RUINED_DWARVEN_DOOR = registerItem("ruined_dwarven_door",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.RUINED_DWARVEN_DOOR, settings), new Item.Settings().maxCount(16));

    public static final Item GREAT_ELVEN_GATE = registerItem("great_elven_gate",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.GREAT_ELVEN_GATE, settings), new Item.Settings().maxCount(16));

    public static final Item GREAT_ORCISH_GATE = registerItem("great_orcish_gate",
            (settings) -> new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.GREAT_ORCISH_GATE, settings), new Item.Settings().maxCount(16));

    public static final Item FIRE_OF_ORTHANC = registerItem("fire_of_orthanc",
            (settings) -> new BlockItem(ModDecorativeBlocks.FIRE_OF_ORTHANC, settings), new Item.Settings().maxCount(1).rarity(Rarity.RARE));
    public static final Item TORCH_OF_ORTHANC = registerItem("torch_of_orthanc",
            (settings) -> new BlockItem(ModDecorativeBlocks.TORCH_OF_ORTHANC, settings), new Item.Settings());
    public static final Item WATERING_CAN = registerItem("watering_can",
            (settings) -> new WateringCanItem(ModDecorativeBlocks.WATERING_CAN, settings), new Item.Settings());

    /*public static final Item CERAMIC_CROCKPOT = registerItem("ceramic_crockpot",
            (settings) -> new BlockItem(ModDecorativeBlocks.CERAMIC_CROCKPOT, settings), new Item.Settings());
    public static final Item CROCKPOT = registerItem("crockpot",
            (settings) -> new BlockItem(ModDecorativeBlocks.CROCKPOT, settings), new Item.Settings());*/
    public static final Item TAPPER = registerItem("tapper",
            (settings) -> new BlockItem(ModDecorativeBlocks.TAPPER, settings), new Item.Settings());

    public static final Item TROLL_STATUE = registerItem("troll_statue",
            (settings) -> new CustomSpawnEggItem(EntitiesME.PETRIFIED_TROLL, settings), new Item.Settings().maxCount(1));

    public static final Item ARKENSTONE = registerItem("arkenstone",
            (settings) -> new VerticallyAttachableBlockItem(ModDecorativeBlocks.ARKENSTONE, ModDecorativeBlocks.WALL_ARKENSTONE, Direction.DOWN, settings), new Item.Settings().rarity(Rarity.EPIC).fireproof()
                    .component(DataComponentTypes.LORE, ArtefactUtils.getArtefactLore("arkenstone")));


    private static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.DECORATIVES_BLOCKS_CONTENT.add(item.getDefaultStack());
        TranslationEntries.itemEntries.add(item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.ITEM, name));
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Decorative Items for " + MiddleEarth.MOD_ID);
    }
}
