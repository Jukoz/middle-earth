package net.sevenstars.of_beasts_and_wild_things.compat.farm.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cow;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalKind;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalVariantClientState;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalVariantHolder;

public final class VariantCowRenderer extends CowRenderer {
    private final CowModel<Cow> legacyModel;
    private final CowModel<Cow> normalModel;
    private final CowModel<Cow> coldModel;
    private final CowModel<Cow> warmModel;

    public VariantCowRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.legacyModel = this.model;
        this.normalModel = new BackportedCowModel<>(context.bakeLayer(FarmAnimalVariantModels.NORMAL_COW));
        this.coldModel = new BackportedCowModel<>(context.bakeLayer(FarmAnimalVariantModels.COLD_COW));
        this.warmModel = new BackportedCowModel<>(context.bakeLayer(FarmAnimalVariantModels.WARM_COW));
    }

    @Override
    public void render(
            Cow cow,
            float entityYaw,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight
    ) {
        ResourceLocation variant = ((FarmAnimalVariantHolder) cow).wildThings$getFarmVariant();
        CowModel<Cow> previousModel = this.model;
        try {
            this.model = switch (FarmAnimalVariantClientState.visualModel(FarmAnimalKind.COW, variant)) {
                case LEGACY -> this.legacyModel;
                case NORMAL -> this.normalModel;
                case COLD -> this.coldModel;
                case WARM -> this.warmModel;
            };
            ((VanillaFarmAnimalRendererBridge) (Object) this).wildThings$renderBase(
                    cow, entityYaw, partialTick, poseStack, buffer, packedLight
            );
        } finally {
            this.model = previousModel;
        }
    }

    @Override
    public ResourceLocation getTextureLocation(Cow cow) {
        return FarmAnimalVariantClientState.texture(
                FarmAnimalKind.COW,
                ((FarmAnimalVariantHolder) cow).wildThings$getFarmVariant()
        );
    }
}
