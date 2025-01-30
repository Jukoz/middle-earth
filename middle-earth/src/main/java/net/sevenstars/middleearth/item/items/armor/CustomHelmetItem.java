package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.item.equipment.EquipmentType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.ModDataComponentTypes;
import net.sevenstars.middleearth.item.dataComponents.CustomDyeableDataComponent;
import net.sevenstars.middleearth.item.dataComponents.HoodDataComponent;
import net.sevenstars.middleearth.item.utils.MEEquipmentTooltip;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import net.sevenstars.middleearth.item.utils.armor.ModDyeablePieces;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class CustomHelmetItem extends ArmorItem implements MEEquipmentTooltip {

    public ModFactions faction;
    public ModSubFactions subFaction;

    private ExtendedArmorMaterial material;

    public CustomHelmetItem(ExtendedArmorMaterial material, Settings settings, ModFactions faction) {
        super(material.material().value(), EquipmentType.HELMET, settings.maxCount(1));


        this.material = material;
        this.faction = faction;
        this.subFaction = null;
    }

    public CustomHelmetItem(ExtendedArmorMaterial material, Settings settings, ModSubFactions subFaction) {
        super(material.material().value(), EquipmentType.HELMET, settings.maxCount(1));

        this.material = material;
        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
    }

    @Override
    public List<Text> getAdditionalShiftLines(ItemStack stack) {
        List<Text> list = new ArrayList<>(List.of());
        list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".tier_" + this.material.tier().toString().toLowerCase()));

        return list;
    }

    @Override
    public List<Text> getAdditionalAltLines(ItemStack stack) {
        List<Text> list = new ArrayList<>(List.of());
        HoodDataComponent hoodDataComponent = stack.get(ModDataComponentTypes.HOOD_DATA);
        CustomDyeableDataComponent dyeDataComponent = stack.get(ModDataComponentTypes.DYE_DATA);

        if(dyeDataComponent != null){
            list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".color").append(": " + String.format(MEEquipmentTooltip.COLOR_PREFIX, (0xFFFFFF & CustomDyeableDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR)))).formatted(Formatting.GRAY));
        }
        if (hoodDataComponent != null) {
            if (ModDyeablePieces.dyeableHoods.containsKey(hoodDataComponent.hood())){
                list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + hoodDataComponent.hood().getName())
                        .append(" (")
                        .append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".color")
                                .append(": " + String.format("#%06X", (0xFFFFFF & HoodDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR))))
                                .append(")")).formatted(Formatting.GRAY));
            } else {
                list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + hoodDataComponent.hood().getName()).formatted(Formatting.GRAY));
            }
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
                stack.set(ModDataComponentTypes.HOOD_DATA, new HoodDataComponent(false, hoodDataComponent.hood(), hoodDataComponent.hoodColor()));
                player.sendMessage(Text.translatable("alert." + MiddleEarth.MOD_ID + ".hood_up"), true);
            } else if (!hoodDataComponent.down() && hoodDataComponent.getHood().getConstantState() == null){
                stack.set(ModDataComponentTypes.HOOD_DATA, new HoodDataComponent(true, hoodDataComponent.hood(), hoodDataComponent.hoodColor()));
                player.sendMessage(Text.translatable("alert." + MiddleEarth.MOD_ID + ".hood_down"), true);
            }
        }
    }
}
