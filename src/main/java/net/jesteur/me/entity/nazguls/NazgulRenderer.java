package net.jesteur.me.entity.orcs.mordor;

import com.google.common.collect.Maps;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jesteur.me.entity.elves.galadhrim.GaladhrimElfVariant;
import net.jesteur.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class MordorOrcRenderer extends BipedEntityRenderer<MordorOrcEntity, MordorOrcModel<MordorOrcEntity>> {
    private static final String PATH = "textures/entities/orcs/mordor/";

    public MordorOrcRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MordorOrcModel<>(ctx.getPart(ModEntityModelLayers.ORC)), 0.5f);
    }

    @Override
    public Identifier getTexture(MordorOrcEntity entity) {
        return new Identifier(MiddleEarth.MOD_ID, LOCATION_BY_VARIANT.get(entity.getVariant()));
    }

    public static final Map<MordorOrcVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MordorOrcVariant.class), (resourceLocation) -> {
                resourceLocation.put(MordorOrcVariant.LIGHT_BROWN_RED,
                        PATH + "orc1.png");
                resourceLocation.put(MordorOrcVariant.PALE_BLUE_YELLOW,
                        PATH + "orc2.png");
                resourceLocation.put(MordorOrcVariant.PALE_GREY_ORANGE,
                        PATH + "orc3.png");
            });

    @Override
    public void render(MordorOrcEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(0.75f, 0.75f, 0.75f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
