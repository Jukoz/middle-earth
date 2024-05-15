package net.jukoz.me.item.items;

import dev.architectury.platform.Mod;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.model.equipment.chest.*;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.CapeDataComponent;
import net.jukoz.me.item.utils.ExtendedArmorMaterial;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipType;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CustomChestplateItem extends ArmorItem {
    private ExtendedArmorMaterial material;
    private List<Customizations> customsList;
    public ChestplateAddonModel additionModel;

    public CustomChestplateItem(ExtendedArmorMaterial material, Type type, Settings settings, List<Customizations> customsList, ChestplateAddonModel chestplateModel) {
        super(material.material(), type, settings.maxCount(1).maxDamage(Type.CHESTPLATE.getMaxDamage(material.durabilityModifier())));
        this.material = material;
        this.customsList = customsList;
        this.additionModel = chestplateModel;
    }

    public CustomChestplateItem(ExtendedArmorMaterial material, Type type, Settings settings) {
        super(material.material(), type, settings.maxCount(1).maxDamage(Type.CHESTPLATE.getMaxDamage(material.durabilityModifier())));
        this.material = material;
        this.customsList = null;
        this.additionModel = null;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of(""));
        if(Screen.hasShiftDown()){
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(material.faction()));
            if(material.subFaction() != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(material.subFaction()));
            }
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".tier" + this.material.tier()));
            tooltip.add(Text.of(""));
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }
        CapeDataComponent capeDataComponent = stack.get(ModDataComponentTypes.CAPE_DATA);

        if(this.customsList != null){
            if(Screen.hasAltDown()){
                tooltip.add(Text.of(""));
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".customizations"));
                /*this.customsList.forEach( custom ->{
                    if(custom.name.contains("dyeable")){
                        tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + custom.name).append(": " + String.format("#%06X", (0xFFFFFF & DyedColorComponent.getColor(stack, DyedColorComponent.DEFAULT_COLOR)))));
                    } else if (capeDataComponent != null){
                        if(capeDataComponent.enabled()){
                            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".cape").append(": Enabled"));
                        } else {
                            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".cape").append(": Disabled"));
                        }
                    } else {
                        tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + custom.name));
                    }
                });*/

                if (capeDataComponent != null) {
                    if (capeDataComponent.enabled()) {
                        tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".cape").append(": Enabled"));
                    } else {
                        tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".cape").append(": Disabled"));
                    }
                }
            }else {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".alt"));
            }
        }

        super.appendTooltip(stack, context, tooltip, type);
    }

    public List<Customizations> getCustomsList() {
        return customsList;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, @NotNull PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        CapeDataComponent capeDataComponent = stack.get(ModDataComponentTypes.CAPE_DATA);

        if(capeDataComponent != null){
            if(capeDataComponent.enabled()){
                stack.set(ModDataComponentTypes.CAPE_DATA, new CapeDataComponent(false));
            } else {
                stack.set(ModDataComponentTypes.CAPE_DATA, new CapeDataComponent(true));
            }
        } else {
            stack.set(ModDataComponentTypes.CAPE_DATA, new CapeDataComponent(true));
        }
        return super.use(world, user, hand);
    }

    public enum Customizations{
        DYEABLE("dyeable"),
        CAPE("cape"),
        IMPALED_SKULLS("impaled_skulls"),
        POUCH("pouch"),
        ;

        public final String name;
        Customizations(String name){
            this.name = name;
        }
    }
}
