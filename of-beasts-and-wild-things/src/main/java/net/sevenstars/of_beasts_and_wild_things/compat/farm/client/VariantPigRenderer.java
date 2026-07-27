package net.sevenstars.of_beasts_and_wild_things.compat.farm.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Pig;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalKind;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalVariantClientState;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalVariantHolder;

public final class VariantPigRenderer extends PigRenderer {
    private final PigModel<Pig> legacyModel;
    private final PigModel<Pig> normalModel;
    private final PigModel<Pig> coldModel;

    public VariantPigRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.legacyModel = this.model;
        this.normalModel = new PigModel<>(context.bakeLayer(FarmAnimalVariantModels.NORMAL_PIG));
        this.coldModel = new PigModel<>(context.bakeLayer(FarmAnimalVariantModels.COLD_PIG));
    }

    @Override
    public void render(
            Pig pig,
            float entityYaw,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight
    ) {
        ResourceLocation variant = ((FarmAnimalVariantHolder) pig).wildThings$getFarmVariant();
        PigModel<Pig> previousModel = this.model;
        try {
            this.model = switch (FarmAnimalVariantClientState.visualModel(FarmAnimalKind.PIG, variant)) {
                case LEGACY -> this.legacyModel;
                case COLD -> this.coldModel;
                case NORMAL, WARM -> this.normalModel;
            };
            ((VanillaFarmAnimalRendererBridge) (Object) this).wildThings$renderBase(
                    pig, entityYaw, partialTick, poseStack, buffer, packedLight
            );
        } finally {
            this.model = previousModel;
        }
    }

    @Override
    public ResourceLocation getTextureLocation(Pig pig) {
        return FarmAnimalVariantClientState.texture(
                FarmAnimalKind.PIG,
                ((FarmAnimalVariantHolder) pig).wildThings$getFarmVariant()
        );
    }
}
