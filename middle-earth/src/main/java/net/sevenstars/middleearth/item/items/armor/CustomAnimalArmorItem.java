package net.sevenstars.middleearth.item.items.armor;

import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.entry.RegistryEntry;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.ModDataComponentTypes;
import net.sevenstars.middleearth.item.dataComponents.CustomDyeableDataComponent;
import net.sevenstars.middleearth.item.dataComponents.MountArmorAddonComponent;
import net.sevenstars.middleearth.item.utils.MEEquipmentTooltip;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CustomAnimalArmorItem extends ArmorItem implements MEEquipmentTooltip {
    public ModFactions faction;
    public ModSubFactions subFaction;
    private final Identifier entityTexture;
    @Nullable
    private final Identifier overlayTexture;
    private final Type type;

    private ExtendedArmorMaterial material;

    public CustomAnimalArmorItem(ExtendedArmorMaterial material, String suffix, Type type, boolean hasOverlay, Settings settings, ModFactions faction) {
        super(material.material().value(), EquipmentType.BODY, settings);
        this.material = material;
        this.type = type;
        Identifier identifier = Identifier.of(MiddleEarth.MOD_ID, type.textureIdFunction.apply(material.material().getKey().orElseThrow().getValue()).getPath());
        identifier = suffix != null ? identifier.withSuffixedPath(suffix) : identifier;
        this.entityTexture = identifier.withSuffixedPath(".png");
        this.overlayTexture = hasOverlay ? identifier.withSuffixedPath("_overlay.png") : null;

        this.faction = faction;
        this.subFaction = null;
    }

    public CustomAnimalArmorItem(ExtendedArmorMaterial material, String suffix, Type type, boolean hasOverlay, Settings settings, ModSubFactions subFaction) {
        super(material.material().value(), EquipmentType.BODY, settings);
        this.material = material;
        this.type = type;
        Identifier identifier = Identifier.of(MiddleEarth.MOD_ID, type.textureIdFunction.apply(material.material().getKey().orElseThrow().getValue()).getPath());
        identifier = suffix != null ? identifier.withSuffixedPath(suffix) : identifier;
        this.entityTexture = identifier.withSuffixedPath(".png");
        this.overlayTexture = hasOverlay ? identifier.withSuffixedPath("_overlay.png") : null;

        this.subFaction = subFaction;
        this.faction = subFaction.getParent();
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
        MountArmorAddonComponent mountArmorAddonComponent = stack.get(ModDataComponentTypes.MOUNT_ARMOR_DATA);
        CustomDyeableDataComponent dyeDataComponent = stack.get(ModDataComponentTypes.DYE_DATA);

        if(dyeDataComponent != null){
            list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".color").append(": " + String.format("#%06X", (0xFFFFFF & CustomDyeableDataComponent.getColor(stack, CustomDyeableDataComponent.DEFAULT_COLOR)))).formatted(Formatting.GRAY));
        }
        if(mountArmorAddonComponent != null && mountArmorAddonComponent.topArmorAddon()) {
            list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mount_armor_addon_top"));
        }
        if(mountArmorAddonComponent != null && mountArmorAddonComponent.sideArmorAddon()) {
            list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mount_armor_addon_side"));
        }

        return list;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        appendBaseTooltip(tooltip, stack, this.faction, this.subFaction);
        super.appendTooltip(stack, context, tooltip, type);
    }

    public Identifier getEntityTexture() {
        return this.entityTexture;
    }

    @Nullable
    public Identifier getOverlayTexture() {
        return this.overlayTexture;
    }

    public Type getArmorType() {
        return this.type;
    }

    @Override
    public SoundEvent getBreakSound() {
        return this.type.breakSound;
    }

    public static enum Type {
        WARG((id) -> {
            return id.withPath((path) -> {
                return "textures/entities/warg/feature/warg_armor_" + path;
            });
        }, SoundEvents.ENTITY_ITEM_BREAK),
        BROADHOOF_GOAT((id) -> {
            return id.withPath((path) -> {
                return "textures/entities/broadhoof_goat/feature/broadhoof_goat_armor_" + path;
            });
        }, SoundEvents.ENTITY_ITEM_BREAK);
        final Function<Identifier, Identifier> textureIdFunction;
        final SoundEvent breakSound;

        private Type(Function<Identifier, Identifier> textureIdFunction, SoundEvent breakSound) {
            this.textureIdFunction = textureIdFunction;
            this.breakSound = breakSound;
        }
    }
}
