package net.sevenstars.middleearth.item.items.weapons.ranged;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.MEEquipmentTooltip;
import net.sevenstars.middleearth.item.utils.ModRangedWeaponTypes;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CustomBowWeaponItem extends BowItem implements MEEquipmentTooltip {
    private final ModFactions faction;
    private final ModSubFactions subFaction;
    public ModRangedWeaponTypes type;

    public CustomBowWeaponItem(ModRangedWeaponTypes type) {
        super(new Settings().maxDamage(type.durability));
        this.faction = null;
        this.subFaction = null;
        this.type = type;
    }

    public CustomBowWeaponItem(ModFactions faction, ModRangedWeaponTypes type) {
        super(new Settings().maxDamage(type.durability));
        this.faction = faction;
        this.subFaction = null;
        this.type = type;
    }

    public CustomBowWeaponItem(ModSubFactions subFaction, ModRangedWeaponTypes type) {
        super(new Settings().maxDamage(type.durability));
        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
        this.type = type;
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

    @Override
    public Text getName(ItemStack stack) {
        if(Registries.ITEM.getId(this).getPath().contains("_noble")
                || Registries.ITEM.getId(this).getPath().contains("_elite")
                || Registries.ITEM.getId(this).getPath().contains("uruk_hai")
                || Registries.ITEM.getId(this).getPath().contains("heyday")
                || Registries.ITEM.getId(this).getPath().contains("numenorean")){
            return Text.translatable(this.getTranslationKey(stack)).formatted(Formatting.GOLD);
        }
        return super.getName(stack);
    }
}
