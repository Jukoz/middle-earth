package net.sevenstars.middleearth.item.items.shields;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.MEEquipmentTooltip;
import net.sevenstars.middleearth.item.utils.ModShieldTypes;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CustomShieldItem extends ShieldItem implements MEEquipmentTooltip {
    public final ModFactions faction;
    public final ModSubFactions subFaction;
    public final ModShieldTypes type;
    public final static HashSet<CustomShieldItem> instances = new HashSet<>();

    public CustomShieldItem(ModShieldTypes type, ModFactions faction) {
        super(new Settings().maxCount(1).maxDamage(type.durability));
        this.type = type;
        this.faction = faction;
        this.subFaction = null;
        instances.add(this);
    }

    public CustomShieldItem(ModShieldTypes type, ModSubFactions subFaction) {
        super(new Settings().maxCount(1).maxDamage(type.durability));
        this.type = type;
        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
        instances.add(this);
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
