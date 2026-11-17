package net.sevenstars.middleearth.item.items.rings;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.RingForgingDataComponent;
import net.sevenstars.middleearth.registries.content.rings.RingCurseRegistry;

/**
 * The single worn ring: a saved per-player attachment synced to every client (so others see the band),
 * plus the wear rules (finished and cool only, Binding can't come off outside creative) and the death drop.
 * It shows as the extra slot {@link #SCREEN_SLOT} in the player's inventory screen.
 */
public final class RingSlot {
    public static final int SCREEN_SLOT = 46;
    public static final Identifier EMPTY_SPRITE = Identifier.of(MiddleEarth.MOD_ID, "container/slot/ring");
    public static final Identifier FRAME_SPRITE = Identifier.of(MiddleEarth.MOD_ID, "container/slot/ring_frame");

    public static final AttachmentType<ItemStack> WORN = AttachmentRegistry.<ItemStack>builder()
            .persistent(ItemStack.OPTIONAL_CODEC)
            .copyOnDeath()
            .syncWith(ItemStack.OPTIONAL_PACKET_CODEC, AttachmentSyncPredicate.all())
            .buildAndRegister(Identifier.of(MiddleEarth.MOD_ID, "worn_ring"));

    private RingSlot() {
    }

    public static void register() {
    }

    public static ItemStack get(PlayerEntity player) {
        ItemStack worn = player.getAttached(WORN);
        return worn == null ? ItemStack.EMPTY : worn;
    }

    /** Always stores a fresh stack so the change is seen and synced. */
    public static void set(PlayerEntity player, ItemStack stack) {
        if (stack.isEmpty()) {
            player.removeAttached(WORN);
        } else {
            player.setAttached(WORN, stack.copy());
        }
    }

    public static boolean canWear(ItemStack stack, RegistryWrapper.WrapperLookup lookup) {
        return stack.getItem() instanceof RingItem && !RingItem.isHot(stack) && RingItem.isFinished(stack, lookup);
    }

    public static boolean isBound(ItemStack stack) {
        RingForgingDataComponent forging = stack.get(DataComponentTypesME.RING_FORGING);
        return forging != null && forging.curses().contains(RingCurseRegistry.BINDING);
    }

    public static boolean canRemove(PlayerEntity player, ItemStack worn) {
        return worn.isEmpty() || player.isCreative() || !isBound(worn);
    }

    public static void playEquipSound(PlayerEntity player) {
        if (player.getWorld().isClient) {
            return;
        }
        player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_ARMOR_EQUIP_GOLD,
                player.getSoundCategory(), 0.6F, 1.4F);
        player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME,
                player.getSoundCategory(), 0.5F, 1.6F);
    }

    public static void bound(PlayerEntity player) {
        player.sendMessage(Text.translatable("message." + MiddleEarth.MOD_ID + ".ring.bound"), true);
    }

    /** Drops the worn ring with the rest of the inventory unless keepInventory is on. */
    public static void dropOnDeath(PlayerEntity player, ServerWorld world) {
        ItemStack worn = get(player);
        if (worn.isEmpty() || world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
            return;
        }
        set(player, ItemStack.EMPTY);
        player.dropItem(worn, true, false);
    }
}
