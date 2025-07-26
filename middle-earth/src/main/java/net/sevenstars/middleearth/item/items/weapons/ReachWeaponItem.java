package net.sevenstars.middleearth.item.items.weapons;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.ModWeaponTypes;
import net.sevenstars.middleearth.item.utils.WeaponSettingsME;

public class ReachWeaponItem extends Item {

    public static final Identifier ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(MiddleEarth.MOD_ID, "entity_interaction_range");

    public ModWeaponTypes type;

    public ReachWeaponItem(ToolMaterial toolMaterial, ModWeaponTypes type, Item.Settings settings) {
        super(WeaponSettingsME.createWeaponSettings(toolMaterial, settings, type));
        this.type = type;
    }

    @Override
    public boolean canMine(ItemStack stack, BlockState state, World world, BlockPos pos, LivingEntity user) {
        return !user.isInCreativeMode();
    }

    @Override
    public Text getName(ItemStack stack) {
        if(Registries.ITEM.getId(this).getPath().contains("_noble")
                || Registries.ITEM.getId(this).getPath().contains("_elite")
                || Registries.ITEM.getId(this).getPath().contains("uruk_hai")
                || Registries.ITEM.getId(this).getPath().contains("heyday")
                || Registries.ITEM.getId(this).getPath().contains("numenorean")){
            return Text.translatable(this.getTranslationKey()).formatted(Formatting.GOLD);
        }
        return super.getName(stack);
    }
}
