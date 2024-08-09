package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.ModShieldTypes;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.List;

public class CustomShieldItem extends ShieldItem {
    private final MutableText faction;
    private final MutableText subFaction;
    private final ModShieldTypes type;

    public CustomShieldItem(ModShieldTypes type, MutableText faction) {
        super(new Item.Settings().maxCount(1).maxDamage(type.durability));
        this.type = type;
        this.faction = faction;
        this.subFaction = null;
    }

    public CustomShieldItem(ModShieldTypes type, MutableText faction, MutableText subFaction) {
        super(new Item.Settings().maxCount(1).maxDamage(type.durability));
        this.type = type;
        this.faction = faction;
        this.subFaction = subFaction;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of(""));
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(this.faction));
            if (this.subFaction != null) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(this.subFaction));
            }
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shield_type").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + this.type.name)));
            tooltip.add(Text.of(""));
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

}
