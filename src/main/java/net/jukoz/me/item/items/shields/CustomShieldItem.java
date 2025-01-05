package net.jukoz.me.item.items.shields;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.MEEquipmentTooltip;
import net.jukoz.me.item.utils.ModShieldTypes;
import net.jukoz.me.utils.ModFactions;
import net.jukoz.me.utils.ModSubFactions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class CustomShieldItem extends ShieldItem implements MEEquipmentTooltip {
    public final ModFactions faction;
    public final ModSubFactions subFaction;
    public final ModShieldTypes type;

    public CustomShieldItem(ModShieldTypes type, ModFactions faction) {
        super(new Item.Settings().maxCount(1).maxDamage(type.durability));
        this.type = type;
        this.faction = faction;
        this.subFaction = null;
    }

    public CustomShieldItem(ModShieldTypes type, ModSubFactions subFaction) {
        super(new Item.Settings().maxCount(1).maxDamage(type.durability));
        this.type = type;
        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
    }

    @Override
    public List<Text> getAdditionalShiftLines(ItemStack stack) {
        List<Text> list = new ArrayList<>(List.of());

        list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + this.type.name));

        return list;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        appendBaseTooltip(tooltip, stack, this.faction, this.subFaction);
        super.appendTooltip(stack, context, tooltip, type);
    }
}
