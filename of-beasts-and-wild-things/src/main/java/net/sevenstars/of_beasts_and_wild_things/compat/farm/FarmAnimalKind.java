package net.sevenstars.of_beasts_and_wild_things.compat.farm;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import org.jetbrains.annotations.Nullable;

public enum FarmAnimalKind {
    CHICKEN("chicken_variant", "textures/entity/chicken.png"),
    COW("cow_variant", "textures/entity/cow/cow.png"),
    PIG("pig_variant", "textures/entity/pig/pig.png");

    private final String dataDirectory;
    private final ResourceLocation defaultTexture;

    FarmAnimalKind(String dataDirectory, String defaultTexture) {
        this.dataDirectory = dataDirectory;
        this.defaultTexture = ResourceLocation.withDefaultNamespace(defaultTexture);
    }

    public String dataDirectory() {
        return this.dataDirectory;
    }

    public ResourceLocation defaultTexture() {
        return this.defaultTexture;
    }

    @Nullable
    public static FarmAnimalKind of(Entity entity) {
        if (entity instanceof Chicken) {
            return CHICKEN;
        }
        if (entity instanceof Cow) {
            return COW;
        }
        if (entity instanceof Pig) {
            return PIG;
        }
        return null;
    }
}
