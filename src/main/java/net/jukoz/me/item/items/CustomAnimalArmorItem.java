package net.jukoz.me.item.items;

import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.jukoz.me.MiddleEarth;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class CustomAnimalArmorItem extends ArmorItem {
    private final Identifier entityTexture;
    @Nullable
    private final Identifier overlayTexture;
    private final Type type;

    public CustomAnimalArmorItem(RegistryEntry<ArmorMaterial> material, Type type, boolean hasOverlay, Item.Settings settings) {
        super(material, ArmorItem.Type.BODY, settings);
        this.type = type;
        Identifier identifier = new Identifier(MiddleEarth.MOD_ID, type.textureIdFunction.apply(material.getKey().orElseThrow().getValue()).getPath());
        this.entityTexture = identifier.withSuffixedPath(".png");
        this.overlayTexture = hasOverlay ? identifier.withSuffixedPath("_overlay.png") : null;
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
        WARG(id -> id.withPath("textures/entities/warg/feature/warg_armor"), SoundEvents.ENTITY_ITEM_BREAK);

        final Function<Identifier, Identifier> textureIdFunction;
        final SoundEvent breakSound;

        private Type(Function<Identifier, Identifier> textureIdFunction, SoundEvent breakSound) {
            this.textureIdFunction = textureIdFunction;
            this.breakSound = breakSound;
        }
    }
}
