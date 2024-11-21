package net.jukoz.me.item.items.armor;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.item.dataComponents.HoodDataComponent;
import net.jukoz.me.item.utils.armor.ModArmorMaterials;
import net.jukoz.me.utils.ModFactions;
import net.jukoz.me.utils.ModSubFactions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class HoodHelmetItem extends ArmorItem {
    public ModFactions faction;
    public ModSubFactions subFaction;

    public HoodHelmetItem(Settings settings, ModFactions faction) {
        super(ModArmorMaterials.LEATHER_T0.material(), Type.HELMET, settings
                .maxCount(1)
                .maxDamage(Type.CHESTPLATE.getMaxDamage(ModArmorMaterials.LEATHER_T0.durabilityModifier())));
        this.faction = faction;
        this.subFaction = null;
    }

    public HoodHelmetItem(Settings settings, ModSubFactions subFaction) {
        super(ModArmorMaterials.LEATHER_T0.material(), Type.HELMET, settings
                .maxCount(1)
                .maxDamage(Type.CHESTPLATE.getMaxDamage(ModArmorMaterials.LEATHER_T0.durabilityModifier())));
        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        ProfileComponent profileComponent = stack.get(DataComponentTypes.PROFILE);

        tooltip.add(Text.of(""));
        HoodDataComponent hoodDataComponent = stack.get(ModDataComponentTypes.HOOD_DATA);
        CustomDyeableDataComponent dyeDataComponent = stack.get(ModDataComponentTypes.DYE_DATA);

        if(Screen.hasShiftDown()){
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + faction.getName())));
            if(subFaction != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + subFaction.getName())));
            }
            if (profileComponent != null && profileComponent.name().isPresent()) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".artisan").append(profileComponent.name().get()).formatted(Formatting.GRAY));
            }
            tooltip.add(Text.of(""));
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }

        if(Screen.hasAltDown()){
            tooltip.add(Text.of(""));
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".customizations"));

            if(dyeDataComponent != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dyeable").append(": " + String.format("#%06X", (0xFFFFFF & CustomDyeableDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR)))));
            }
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + hoodDataComponent.hood().getName()));


        }else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".alt"));
        }

        super.appendTooltip(stack, context, tooltip, type);
    }

    public static void toggleHoodState(ServerPlayerEntity player, ItemStack stack){
        HoodDataComponent hoodDataComponent = stack.get(ModDataComponentTypes.HOOD_DATA);
        if (hoodDataComponent != null){
            if (hoodDataComponent.down() && hoodDataComponent.getHood().getConstantState() == null) {
                stack.set(ModDataComponentTypes.HOOD_DATA, new HoodDataComponent(false, hoodDataComponent.hood()));
                player.sendMessage(Text.translatable("alert." + MiddleEarth.MOD_ID + ".hood_up"), true);
            } else if (!hoodDataComponent.down() && hoodDataComponent.getHood().getConstantState() == null){
                stack.set(ModDataComponentTypes.HOOD_DATA, new HoodDataComponent(true, hoodDataComponent.hood()));
                player.sendMessage(Text.translatable("alert." + MiddleEarth.MOD_ID + ".hood_down"), true);
            }
        }
    }
}
