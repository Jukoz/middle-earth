package net.jukoz.me.item.items.armor;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.HoodDataComponent;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.item.utils.MEEquipmentTooltip;
import net.jukoz.me.item.utils.armor.ExtendedArmorMaterial;
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

public class CustomHelmetItem extends ArmorItem implements MEEquipmentTooltip {

    public ModFactions faction;
    public ModSubFactions subFaction;

    private ExtendedArmorMaterial material;

    public CustomHelmetItem(ExtendedArmorMaterial material, Settings settings, ModFactions faction) {
        super(material.material(), ArmorItem.Type.HELMET, settings.maxCount(1).maxDamage(Type.HELMET.getMaxDamage(material.durabilityModifier())));

        this.material = material;
        this.faction = faction;
        this.subFaction = null;
    }

    public CustomHelmetItem(ExtendedArmorMaterial material, Settings settings, ModSubFactions subFaction) {
        super(material.material(), ArmorItem.Type.HELMET, settings.maxCount(1).maxDamage(Type.HELMET.getMaxDamage(material.durabilityModifier())));

        this.material = material;
        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
    }

    @Override
    public List<Text> getAdditionalShiftLines(ItemStack stack) {
        List<Text> list = new java.util.ArrayList<>(List.of());
        list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".tier_" + this.material.tier().toString().toLowerCase()));

        return list;
    }

    @Override
    public List<Text> getAdditionalAltLines(ItemStack stack) {
        List<Text> list = new java.util.ArrayList<>(List.of());
        HoodDataComponent hoodDataComponent = stack.get(ModDataComponentTypes.HOOD_DATA);
        CustomDyeableDataComponent dyeDataComponent = stack.get(ModDataComponentTypes.DYE_DATA);

        if(dyeDataComponent != null){
            list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dyeable").append(": " + String.format("#%06X", (0xFFFFFF & CustomDyeableDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR)))));
        }
        if (hoodDataComponent != null) {
            list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + hoodDataComponent.hood().getName()));
        }

        return list;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        appendBaseTooltip(tooltip, stack, this.faction, this.subFaction);
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
