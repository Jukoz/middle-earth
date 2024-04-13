package net.jukoz.me.item.utils;

import com.mojang.serialization.Codec;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public record CustomArmorMaterial (Map<ArmorItem.Type, Integer> defense, int enchantability, RegistryEntry<SoundEvent> equipSound, Supplier<Ingredient> repairIngredient, List<ArmorMaterial.Layer> layers, float toughness, float knockbackResistance) {
    public static final Codec<RegistryEntry<ArmorMaterial>> CODEC = Registries.ARMOR_MATERIAL.getEntryCodec();

    public int getProtection(ArmorItem.Type type) {
        return this.defense.getOrDefault(type, 0);
    }

    public static final class Layer {
        private final Identifier id;
        private final String suffix;
        private final boolean dyeable;
        private final Identifier layer2Texture;
        private final Identifier layer1Texture;
        private final Identifier layer0Texture;

        public Layer(Identifier id, String suffix, boolean dyeable) {
            this.id = id;
            this.suffix = suffix;
            this.dyeable = dyeable;
            this.layer2Texture = this.getTextureId(2);
            this.layer1Texture = this.getTextureId(1);
            this.layer0Texture = this.getTextureId(0);
        }

        public Layer(Identifier id) {
            this(id, "", false);
        }

        private Identifier getTextureId(int layer) {
            switch (layer){
                case 0 : return this.id.withPath(path -> "textures/models/armor/" + this.id.getPath() + "_layer_" + 0 + this.suffix + ".png");
                case 1 : return this.id.withPath(path -> "textures/models/armor/" + this.id.getPath() + "_layer_" + 1 + this.suffix + ".png");
                case 2 : return this.id.withPath(path -> "textures/models/armor/" + this.id.getPath() + "_layer_" + 2 + this.suffix + ".png");
                default: return this.id.withPath(path -> "textures/models/armor/" + this.id.getPath() + "_layer_" + 0 + this.suffix + ".png");
            }
        }

        public Identifier getTexture(int layer) {
            switch (layer){
                case 0 : return layer0Texture;
                case 1 : return layer1Texture;
                case 2 : return layer2Texture;
                default: return layer0Texture;
            }
        }

        public boolean isDyeable() {
            return this.dyeable;
        }
    }
}
