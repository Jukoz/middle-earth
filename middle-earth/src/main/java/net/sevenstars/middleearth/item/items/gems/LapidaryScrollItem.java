package net.sevenstars.middleearth.item.items.gems;

import net.sevenstars.middleearth.recipe.lapidary.LapidaryKnowledge;
import net.sevenstars.middleearth.recipe.lapidary.LapidaryTools;

import java.util.function.Consumer;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/**
 * A scroll of lost lapidary knowledge. Reading it (use) teaches the player to make that tool set and everything
 * on the way to it; the scroll is not used up, so it can be passed on, or bound into the Player Book's Gem
 * Studies to keep the page.
 */
public class LapidaryScrollItem extends Item {
    private final LapidaryTools tools;

    public LapidaryScrollItem(Settings settings, LapidaryTools tools) {
        super(settings);
        this.tools = tools;
    }

    public LapidaryTools tools() {
        return tools;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (user instanceof ServerPlayerEntity player) {
            boolean learned = LapidaryKnowledge.learn(player, tools);
            player.sendMessage(Text.translatable(learned ? "message.middle-earth.lapidary_scroll.learned"
                : "message.middle-earth.lapidary_scroll.known",
                Text.translatable(tools.translationKey())).formatted(learned ? Formatting.GOLD : Formatting.GRAY), true);
            world.playSound(null, player.getBlockPos(), learned ? SoundEvents.UI_TOAST_CHALLENGE_COMPLETE
                : SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, learned ? 0.6F : 1.0F, 1.0F);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent,
                              Consumer<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, tooltip, type);
        tooltip.accept(Text.translatable("tooltip.middle-earth.lapidary_scroll.teaches",
            Text.translatable(tools.translationKey())).formatted(Formatting.GRAY));
        tooltip.accept(Text.translatable("tooltip.middle-earth.lapidary_scroll.read").formatted(Formatting.DARK_GRAY));
    }
}
