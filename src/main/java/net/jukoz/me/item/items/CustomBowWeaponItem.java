package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomBowWeaponItem extends BowItem {
    private final MutableText faction;
    private final MutableText subFaction;
    public CustomBowWeaponItem(Settings settings) {
        super(settings);
        this.faction = null;
        this.subFaction = null;
    }

    public CustomBowWeaponItem(MutableText faction, Settings settings) {
        super(settings);
        this.faction = faction;
        this.subFaction = null;
    }

    public CustomBowWeaponItem(MutableText faction, MutableText subFaction, Settings settings) {
        super(settings);
        this.faction = faction;
        this.subFaction = subFaction;
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.of(""));
        if (Screen.hasShiftDown()) {
            if(this.faction != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(this.faction));
            }
            if (this.subFaction != null) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(this.subFaction));
            }
            tooltip.add(Text.of(""));
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
}
