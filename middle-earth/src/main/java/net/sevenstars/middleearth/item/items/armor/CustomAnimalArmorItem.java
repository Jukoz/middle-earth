package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class CustomAnimalArmorItem extends Item {
    private final Identifier entityTexture;
    @Nullable
    private final Identifier overlayTexture;
    private final Type type;

    private ExtendedArmorMaterial material;

    public CustomAnimalArmorItem(ExtendedArmorMaterial material, String suffix, Type type, boolean hasOverlay, Settings settings) {
        super(settings.horseArmor(material.material()));
        this.material = material;
        this.type = type;
        Identifier identifier = Identifier.of(MiddleEarth.MOD_ID, type.textureIdFunction.apply(material.material().assetId().getRegistry()).getPath());
        identifier = suffix != null ? identifier.withSuffixedPath(suffix) : identifier;
        this.entityTexture = identifier.withSuffixedPath(".png");
        this.overlayTexture = hasOverlay ? identifier.withSuffixedPath("_overlay.png") : null;
    }

    /*@Override
    public List<Text> getAdditionalAltLines(ItemStack stack) {
        List<Text> list = new ArrayList<>(List.of());
        MountArmorAddonComponent mountArmorAddonComponent = stack.get(DataComponentTypesME.MOUNT_ARMOR_DATA);
        DyedColorComponent dyeDataComponent = stack.get(DataComponentTypes.DYED_COLOR);

        if(dyeDataComponent != null){
            list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".color").append(": " + String.format("#%06X", (0xFFFFFF & DyedColorComponent.getColor(stack, DyedColorComponent.DEFAULT_COLOR)))).formatted(Formatting.GRAY));
        }
        if(mountArmorAddonComponent != null && mountArmorAddonComponent.topArmorAddon()) {
            list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mount_armor_addon_top"));
        }
        if(mountArmorAddonComponent != null && mountArmorAddonComponent.sideArmorAddon()) {
            list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mount_armor_addon_side"));
        }

        return list;
    }*/

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

    //TODO TOM to fix or refactor
    /*@Override
    public SoundEvent getBreakSound() {
        return this.type.breakSound;
    }*/

    public static enum Type {
        WARG((id) -> {
            return id.withPath((path) -> {
                return "textures/entities/warg/feature/warg_armor_" + path;
            });
        }/*, SoundEvents.ENTITY_ITEM_BREAK*/),
        BROADHOOF_GOAT((id) -> {
            return id.withPath((path) -> {
                return "textures/entities/broadhoof_goat/feature/broadhoof_goat_armor_" + path;
            });
        }/*, SoundEvents.ENTITY_ITEM_BREAK*/);
        final Function<Identifier, Identifier> textureIdFunction;
        //final SoundEvent breakSound;

        private Type(Function<Identifier, Identifier> textureIdFunction/*, SoundEvent breakSound*/) {
            this.textureIdFunction = textureIdFunction;
            //this.breakSound = breakSound;
        }
    }
}
