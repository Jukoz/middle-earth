package net.sevenstars.of_beasts_and_wild_things.compat.farm.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Chicken;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalKind;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalVariantClientState;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalVariantHolder;

public final class VariantChickenRenderer extends ChickenRenderer {
    private final ChickenModel<Chicken> normalModel;
    private final ChickenModel<Chicken> coldModel;

    public VariantChickenRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.normalModel = this.model;
        this.coldModel = new ChickenModel<>(context.bakeLayer(FarmAnimalVariantModels.COLD_CHICKEN));
    }

    @Override
    public void render(
            Chicken chicken,
            float entityYaw,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight
    ) {
        ResourceLocation variant = ((FarmAnimalVariantHolder) chicken).wildThings$getFarmVariant();
        ChickenModel<Chicken> previousModel = this.model;
        try {
            this.model = FarmAnimalVariantClientState.visualModel(FarmAnimalKind.CHICKEN, variant)
                    == FarmAnimalVariantClientState.VisualModel.COLD ? this.coldModel : this.normalModel;
            ((VanillaFarmAnimalRendererBridge) (Object) this).wildThings$renderBase(
                    chicken, entityYaw, partialTick, poseStack, buffer, packedLight
            );
        } finally {
            this.model = previousModel;
        }
    }

    @Override
    public ResourceLocation getTextureLocation(Chicken chicken) {
        return FarmAnimalVariantClientState.texture(
                FarmAnimalKind.CHICKEN,
                ((FarmAnimalVariantHolder) chicken).wildThings$getFarmVariant()
        );
    }
}
