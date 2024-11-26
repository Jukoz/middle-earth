package net.jukoz.me.item.items.armor;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.CapeDataComponent;
import net.jukoz.me.item.dataComponents.CustomDyeableDataComponent;
import net.jukoz.me.item.dataComponents.MountArmorAddonComponent;
import net.jukoz.me.item.utils.armor.ExtendedArmorMaterial;
import net.jukoz.me.utils.ModFactions;
import net.jukoz.me.utils.ModSubFactions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class CustomAnimalArmorItem extends ArmorItem {
    public ModFactions faction;
    public ModSubFactions subFaction;
    private final Identifier entityTexture;
    @Nullable
    private final Identifier overlayTexture;
    private final Type type;

    private ExtendedArmorMaterial material;

    public CustomAnimalArmorItem(ExtendedArmorMaterial material, String suffix, Type type, boolean hasOverlay, Item.Settings settings, ModFactions faction) {
        super(material.material(), ArmorItem.Type.BODY, settings);
        this.material = material;
        this.type = type;
        Identifier identifier = Identifier.of(MiddleEarth.MOD_ID, type.textureIdFunction.apply(material.material().getKey().orElseThrow().getValue()).getPath());
        identifier = suffix != null ? identifier.withSuffixedPath(suffix) : identifier;
        this.entityTexture = identifier.withSuffixedPath(".png");
        this.overlayTexture = hasOverlay ? identifier.withSuffixedPath("_overlay.png") : null;

        this.faction = faction;
        this.subFaction = null;
    }

    public CustomAnimalArmorItem(ExtendedArmorMaterial material, String suffix, Type type, boolean hasOverlay, Item.Settings settings, ModSubFactions subFaction) {
        super(material.material(), ArmorItem.Type.BODY, settings);
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
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        ProfileComponent profileComponent = stack.get(DataComponentTypes.PROFILE);

        tooltip.add(Text.of(""));
        if(Screen.hasShiftDown()){
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + faction.getName())));
            if(subFaction != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + subFaction.getName())));
            }
            if (profileComponent != null && profileComponent.name().isPresent()) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".artisan").append(profileComponent.name().get()).formatted(Formatting.GRAY));
            }
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".tier_" + this.material.tier().toString().toLowerCase()));
            tooltip.add(Text.of(""));
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }

        MountArmorAddonComponent mountArmorAddonComponent = stack.get(ModDataComponentTypes.MOUNT_ARMOR_DATA);

        if(Screen.hasAltDown()){
            tooltip.add(Text.of(""));
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".customizations"));

            if (mountArmorAddonComponent != null) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".addon_enabled"));
            }
            else {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".addon_disabled"));
            }
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".alt"));
        }

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

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
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
