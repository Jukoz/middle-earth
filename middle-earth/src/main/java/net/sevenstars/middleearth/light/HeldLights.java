package net.sevenstars.middleearth.light;

import java.util.IdentityHashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.block.special.beamlamp.BeamLampKind;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import org.jetbrains.annotations.Nullable;

/**
 * Which items give off light in the off hand, and how. A light joins with one line, e.g.
 * {@code HeldLights.register(DecorativeItemsME.SILVER_LANTERN, HeldLight.Glow.lantern(15));}; the light then
 * follows its holder on every client and server ({@link BeamLights}).
 * <p>
 * Torches and candles are always lit and flicker; lanterns and lamps are shaded and lit again by using them from
 * the off hand ({@link LanternToggling}).
 */
public final class HeldLights {
    /** A candle carried in the hand: brighter than one placed (3), so it lights the way, yet short of a torch (14). */
    private static final int CANDLE_LEVEL = 10;

    private static final Map<Item, HeldLight> LIGHTS = new IdentityHashMap<>();

    private HeldLights() {
    }

    public static void register(ItemConvertible item, HeldLight light) {
        LIGHTS.put(item.asItem(), light);
    }

    /** The lights Middle-earth itself registers. */
    public static void registerDefaults() {
        for (BeamLampKind kind : BeamLampKind.values()) {
            register(kind.item(), kind.held());
        }
        // Lanterns and lamps: steady, shaded at will.
        for (Item lantern : new Item[]{Items.LANTERN, Items.SOUL_LANTERN, DecorativeItemsME.SILVER_LANTERN,
            DecorativeItemsME.LEAD_LANTERN, DecorativeItemsME.ELVEN_LANTERN, DecorativeItemsME.DWARVEN_LANTERN,
            DecorativeItemsME.TREATED_STEEL_LANTERN}) {
            register(lantern, HeldLight.Glow.lantern(placedLevel(lantern)));
        }
        // Held by the side.
        for (Item lamp : new Item[]{DecorativeItemsME.CRUDE_LANTERN, DecorativeItemsME.CRYSTAL_LAMP}) {
            register(lamp, HeldLight.Glow.lamp(placedLevel(lamp)));
        }
        register(DecorativeBlockRegistryME.CERAMIC_LAMP, HeldLight.Glow.oilLamp(placedLevel(DecorativeBlockRegistryME.CERAMIC_LAMP.asItem())));
        // Torches and candles: always lit, flicker.
        register(Items.TORCH, HeldLight.Glow.flame(placedLevel(Items.TORCH)));
        register(Items.SOUL_TORCH, HeldLight.Glow.flame(placedLevel(Items.SOUL_TORCH)));
        register(DecorativeItemsME.TORCH_OF_ORTHANC, HeldLight.Glow.flame(placedLevel(DecorativeItemsME.TORCH_OF_ORTHANC)));
        for (Item candle : new Item[]{Items.CANDLE, Items.WHITE_CANDLE, Items.ORANGE_CANDLE, Items.MAGENTA_CANDLE,
            Items.LIGHT_BLUE_CANDLE, Items.YELLOW_CANDLE, Items.LIME_CANDLE, Items.PINK_CANDLE, Items.GRAY_CANDLE,
            Items.LIGHT_GRAY_CANDLE, Items.CYAN_CANDLE, Items.PURPLE_CANDLE, Items.BLUE_CANDLE, Items.BROWN_CANDLE,
            Items.GREEN_CANDLE, Items.RED_CANDLE, Items.BLACK_CANDLE}) {
            register(candle, HeldLight.Glow.flame(CANDLE_LEVEL));
        }
        for (Block holder : new Block[]{DecorativeBlockRegistryME.CANDLESTICK, DecorativeBlockRegistryME.SKULL_CANDLE}) {
            register(holder, HeldLight.Glow.flame(placedLevel(holder.asItem())));
        }
        // Placed, the candle holder is dim (6); carried, it is a candle held up like the candlestick.
        register(DecorativeBlockRegistryME.CANDLE_HOLDER, HeldLight.Glow.flame(placedLevel(DecorativeBlockRegistryME.CANDLESTICK.asItem())));
    }

    /** The light an item's block gives when placed and lit: its brightest state. */
    private static int placedLevel(Item item) {
        if (!(item instanceof BlockItem blockItem)) {
            return 0;
        }
        return blockItem.getBlock().getStateManager().getStates().stream().mapToInt(BlockState::getLuminance).max().orElse(0);
    }

    public static @Nullable HeldLight of(ItemStack stack) {
        return stack.isEmpty() ? null : LIGHTS.get(stack.getItem());
    }

    /** Whether a toggled lamp is switched on (lanterns are, until shaded; the focused lamps carry their own default). */
    public static boolean isOn(ItemStack stack) {
        return stack.getOrDefault(DataComponentTypesME.LAMP_ON, true);
    }

    /** The light this stack gives off right now, or null. */
    public static @Nullable HeldLight active(ItemStack stack) {
        HeldLight light = of(stack);
        if (light == null || (light.toggled() && !isOn(stack))) {
            return null;
        }
        return light;
    }

    /** The light a player holds in the off hand right now, or null. */
    public static @Nullable HeldLight held(PlayerEntity player) {
        if (player.isSpectator()) {
            return null;
        }
        HeldLight light = active(player.getOffHandStack());
        return light != null ? light : worn(player);
    }

    /** Two candles worn on a helmet: always lit, flickering; brighter than one carried candle. */
    private static final HeldLight.Glow CANDLE_HELMET = HeldLight.Glow.flame(12);

    /** A light worn on the head (the Dwarven Miner and Goblin Town Tunneler helmets' candles), or null. */
    public static @Nullable HeldLight worn(PlayerEntity player) {
        ItemStack head = player.getEquippedStack(net.minecraft.entity.EquipmentSlot.HEAD);
        return head.isOf(net.sevenstars.middleearth.item.EquipmentItemsME.DWARVEN_MINER_HELMET)
            || head.isOf(net.sevenstars.middleearth.item.EquipmentItemsME.GOBLIN_TOWN_TUNNELER_HELMET) ? CANDLE_HELMET : null;
    }
}
