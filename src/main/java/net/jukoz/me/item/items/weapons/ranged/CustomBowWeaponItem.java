package net.jukoz.me.item.items.weapons.ranged;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.MEEquipmentTooltip;
import net.jukoz.me.item.utils.ModRangedWeaponTypes;
import net.jukoz.me.item.utils.ModWeaponTypes;
import net.jukoz.me.utils.ModFactions;
import net.jukoz.me.utils.ModSubFactions;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CustomBowWeaponItem extends BowItem implements MEEquipmentTooltip {
    private final ModFactions faction;
    private final ModSubFactions subFaction;
    public ModRangedWeaponTypes type = ModRangedWeaponTypes.BOW;

    public CustomBowWeaponItem(Settings settings) {
        super(settings);
        this.faction = null;
        this.subFaction = null;
    }

    public CustomBowWeaponItem(ModFactions faction, Settings settings) {
        super(settings);
        this.faction = faction;
        this.subFaction = null;
    }

    public CustomBowWeaponItem(ModSubFactions subFaction, Settings settings) {
        super(settings);
        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
    }

    public Predicate<ItemStack> getProjectiles() {
        return BOW_PROJECTILES;
    }

    @Override
    public List<Text> getAdditionalShiftLines(ItemStack stack) {
        List<Text> list = new ArrayList<>(List.of());

        list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".weapon_type").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + this.type.name)));

        return list;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        appendBaseTooltip(tooltip, stack, this.faction, this.subFaction);
        super.appendTooltip(stack, context, tooltip, type);
    }
}
