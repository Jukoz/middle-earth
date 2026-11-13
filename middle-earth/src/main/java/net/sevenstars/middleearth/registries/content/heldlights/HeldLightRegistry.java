package net.sevenstars.middleearth.registries.content.heldlights;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.light.HeldLight;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.held_lights.HeldLightData;
import net.sevenstars.middleearth.resources.datas.held_lights.HeldLightData.Display;
import net.sevenstars.middleearth.resources.datas.held_lights.HeldLightData.Flames;
import net.sevenstars.middleearth.resources.datas.held_lights.HeldLightData.Slot;
import org.joml.Vector3f;

import java.util.List;
import java.util.Optional;

/**
 * Middle-earth's own held lights, written out as {@code held_light} entries by data generation. Grips, displays and
 * flames match the held models (models/item/held); keep them in step.
 */
public class HeldLightRegistry {
    private static final RegistryKey<Registry<HeldLightData>> KEY = DynamicRegistriesME.HELD_LIGHT;

    /** A candle carried in the hand: brighter than one placed (3), so it lights the way, yet short of a torch (14). */
    private static final int CANDLE_LEVEL = 10;
    /** Two candles worn on a helmet: brighter than one carried candle. */
    private static final int CANDLE_HELMET_LEVEL = 12;
    private static final int PLACED = HeldLight.Glow.PLACED;
    private static final Identifier SOUL_FIRE = Identifier.ofVanilla("soul_fire_flame");

    public static void bootstrap(Registerable<HeldLightData> context) {
        RegistryEntryLookup<HeldLightData> lookup = context.getRegistryLookup(KEY);

        // Lanterns: steady, shaded at will, carried by their handles (grip: a pixel under the handle's top).
        lantern(context, lookup, Items.LANTERN, 10);
        lantern(context, lookup, Items.SOUL_LANTERN, 10);
        lantern(context, lookup, DecorativeItemsME.TREATED_STEEL_LANTERN, 9);
        lantern(context, lookup, DecorativeItemsME.SILVER_LANTERN, 14);
        lantern(context, lookup, DecorativeItemsME.ELVEN_LANTERN, 14);
        lantern(context, lookup, DecorativeItemsME.LEAD_LANTERN, 15);
        // Its handle is a loop: held through its middle, its top bar in the fist.
        lantern(context, lookup, DecorativeItemsME.DWARVEN_LANTERN, 13);

        // Lamps without a handle: held up by the side.
        for (Item lamp : new Item[]{DecorativeItemsME.CRUDE_LANTERN, DecorativeItemsME.CRYSTAL_LAMP}) {
            register(context, lookup, name(lamp), held(List.of(id(lamp)), HeldLight.Glow.lamp(PLACED),
                HeldLightData.DEFAULT_GRIP, Display.DEFAULT, Optional.empty()));
        }
        // An oil lamp: its flame at the spout, held by its handle.
        register(context, lookup, name(DecorativeBlockRegistryME.CERAMIC_LAMP), held(List.of(id(DecorativeBlockRegistryME.CERAMIC_LAMP)),
            HeldLight.Glow.oilLamp(PLACED), new Vector3f(8, 2.5F, 12.5F), new Display(0.6F, 1, 0.6F, 0),
            flames(false, null, 8, 6, 3)));

        // Torches: always lit, flickering, held up.
        register(context, lookup, "torch", held(List.of(id(Items.TORCH)), HeldLight.Glow.flame(PLACED),
            new Vector3f(8, 3, 8), new Display(0.85F, 3, 0.9F, 0), flames(false, null, 8, 11.5F, 8)));
        register(context, lookup, "soul_torch", held(List.of(id(Items.SOUL_TORCH)), HeldLight.Glow.flame(PLACED),
            new Vector3f(8, 3, 8), new Display(0.85F, 3, 0.9F, 0), flames(false, SOUL_FIRE, 8, 11.5F, 8)));
        register(context, lookup, name(DecorativeItemsME.TORCH_OF_ORTHANC), held(List.of(id(DecorativeItemsME.TORCH_OF_ORTHANC)),
            HeldLight.Glow.flame(PLACED), HeldLightData.DEFAULT_GRIP, Display.DEFAULT, Optional.empty()));

        // Candles: every candle, brighter carried than placed.
        register(context, lookup, "candles", held(List.of("#minecraft:candles"), HeldLight.Glow.flame(CANDLE_LEVEL),
            new Vector3f(8, 0.5F, 8), new Display(0.9F, 5, 1, 0), flames(true, null, 8, 8.2F, 8)));
        register(context, lookup, name(DecorativeBlockRegistryME.CANDLESTICK), held(List.of(id(DecorativeBlockRegistryME.CANDLESTICK)),
            HeldLight.Glow.flame(PLACED), new Vector3f(8, 4, 8), new Display(0.6F, 1, 0.6F, 0), flames(true, null, 8, 17.2F, 8)));
        // Held by its loop (turned 45 degrees on the model, at the north-west corner), the loop turned to the hand.
        // Placed, it is dim (6); carried, it is a candle held up like the candlestick.
        register(context, lookup, name(DecorativeBlockRegistryME.CANDLE_HOLDER), held(List.of(id(DecorativeBlockRegistryME.CANDLE_HOLDER)),
            HeldLight.Glow.flame(placedLevel(DecorativeBlockRegistryME.CANDLESTICK.asItem())), new Vector3f(4.59F, 2.5F, 4.59F),
            new Display(0.6F, 1, 0.6F, 135), flames(true, null, 8, 12.2F, 8)));
        register(context, lookup, name(DecorativeBlockRegistryME.SKULL_CANDLE), held(List.of(id(DecorativeBlockRegistryME.SKULL_CANDLE)),
            HeldLight.Glow.flame(PLACED), new Vector3f(10, 0, 7), new Display(0.5F, 1, 0.55F, -67.5F), flames(true, null, 8, 16.2F, 8)));

        // Candles worn on helmets: the Dwarven Miner Helmet's two before the brow, the Goblin Town Tunneler's two
        // stubs on its crown (pixels from the head's pivot).
        worn(context, lookup, EquipmentItemsME.DWARVEN_MINER_HELMET, new Vector3f(-5.5F, -11, -6.5F), new Vector3f(5.5F, -11, -6.5F));
        worn(context, lookup, EquipmentItemsME.GOBLIN_TOWN_TUNNELER_HELMET, new Vector3f(1.4F, -13, 1.5F), new Vector3f(-1.6F, -14, -0.6F));
    }

    private static void lantern(Registerable<HeldLightData> context, RegistryEntryLookup<HeldLightData> lookup, Item item, float grip) {
        register(context, lookup, name(item), held(List.of(id(item)), HeldLight.Glow.lantern(PLACED), grip(grip), Display.DEFAULT, Optional.empty()));
    }

    private static void worn(Registerable<HeldLightData> context, RegistryEntryLookup<HeldLightData> lookup, Item helmet, Vector3f... wicks) {
        register(context, lookup, name(helmet), new HeldLightData(List.of(id(helmet)), Slot.HEAD, HeldLight.Glow.flame(CANDLE_HELMET_LEVEL),
            HeldLightData.DEFAULT_GRIP, HeldLightData.DEFAULT_LENS, Display.DEFAULT, Optional.of(new Flames(List.of(wicks), Flames.DEFAULT_PARTICLE, Optional.empty(), true)),
            Optional.empty()));
    }

    private static HeldLightData held(List<String> items, HeldLight light, Vector3f grip, Display display, Optional<Flames> flames) {
        return new HeldLightData(items, Slot.OFFHAND, light, grip, HeldLightData.DEFAULT_LENS, display, flames, Optional.empty());
    }

    private static Vector3f grip(float height) {
        return new Vector3f(8, height, 8);
    }

    private static Optional<Flames> flames(boolean small, Identifier particle, float x, float y, float z) {
        return Optional.of(new Flames(List.of(new Vector3f(x, y, z)), particle != null ? particle : Flames.DEFAULT_PARTICLE,
            Optional.empty(), small));
    }

    private static String id(ItemConvertible item) {
        return Registries.ITEM.getId(item.asItem()).toString();
    }

    private static String name(ItemConvertible item) {
        return Registries.ITEM.getId(item.asItem()).getPath();
    }

    /** The light an item's block gives when placed and lit: its brightest state. */
    private static int placedLevel(Item item) {
        if (!(item instanceof BlockItem blockItem)) {
            return 0;
        }
        return blockItem.getBlock().getStateManager().getStates().stream().mapToInt(BlockState::getLuminance).max().orElse(0);
    }

    private static void register(Registerable<HeldLightData> context, RegistryEntryLookup<HeldLightData> lookup,
                                 String name, HeldLightData element) {
        DynamicRegistriesME.register(context, lookup, DynamicRegistriesME.of(KEY, MiddleEarth.id(name)), element);
    }
}
