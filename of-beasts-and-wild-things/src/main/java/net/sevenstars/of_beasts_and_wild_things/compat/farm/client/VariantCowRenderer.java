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
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalVariantModel;

public final class VariantCowRenderer extends CowRenderer {
    private final CowModel<Cow> normalModel;
    private final CowModel<Cow> coldModel;

    public VariantCowRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.normalModel = this.model;
        this.coldModel = new CowModel<>(context.bakeLayer(FarmAnimalVariantModels.COLD_COW));
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
        this.model = FarmAnimalVariantClientState.model(FarmAnimalKind.COW, variant)
                == FarmAnimalVariantModel.COLD ? this.coldModel : this.normalModel;
        ((VanillaFarmAnimalRendererBridge) (Object) this).wildThings$renderBase(
                cow, entityYaw, partialTick, poseStack, buffer, packedLight
        );
    }

    @Override
    public ResourceLocation getTextureLocation(Cow cow) {
        return FarmAnimalVariantClientState.texture(
                FarmAnimalKind.COW,
                ((FarmAnimalVariantHolder) cow).wildThings$getFarmVariant()
        );
    }
}
