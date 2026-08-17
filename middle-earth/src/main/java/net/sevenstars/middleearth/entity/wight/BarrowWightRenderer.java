package net.sevenstars.middleearth.entity.wight;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

@Environment(EnvType.CLIENT)
public class BarrowWightRenderer extends MobEntityRenderer<BarrowWightEntity, BarrowWightRenderState, BarrowWightModel> {
    private static final String PATH = "textures/entities/barrow_wights/";
    private static final String NOBLE_TEXTURE = PATH + "noble.png";
    private static final String PRIEST_TEXTURE = PATH + "priest.png";

    public BarrowWightRenderer(EntityRendererFactory.Context context) {
        this(context, 0.4F, EntityModelLayersME.BARROW_WIGHT);
    }

    @Override
    public BarrowWightRenderState createRenderState() {
        return new BarrowWightRenderState();
    }

    protected BarrowWightRenderer(EntityRendererFactory.Context ctx, float shadowRadius, EntityModelLayer layer) {
        super(ctx, new BarrowWightModel(ctx.getPart(layer)), shadowRadius);
        this.addFeature(new HeldItemFeatureRenderer<>(this));
    }

    @Override
    public Identifier getTexture(BarrowWightRenderState state) {
        if(state.isEnchanter) {
            return MiddleEarth.of(PRIEST_TEXTURE);
        }
        return MiddleEarth.of(NOBLE_TEXTURE);
    }

    public void updateRenderState(BarrowWightEntity barrowWightEntity, BarrowWightRenderState state, float f) {
        super.updateRenderState(barrowWightEntity, state, f);
        state.idleAnimationState.copyFrom(barrowWightEntity.idleAnimation);
        state.walkAnimationState.copyFrom(barrowWightEntity.walkingAnimation);
        state.attackAnimationState.copyFrom(barrowWightEntity.attackAnimation);
        state.screamAnimationState.copyFrom(barrowWightEntity.screamAnimation);
        state.incantationAnimationState.copyFrom(barrowWightEntity.incantationAnimation);

        state.isEnchanter = barrowWightEntity instanceof BarrowWightEnchanterEntity;

        // Resolve left & right hand item state (super call is buggy idk why)
        this.itemModelResolver.updateForLivingEntity(state.rightHandItemState, barrowWightEntity.getStackInArm(Arm.RIGHT),
                ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, barrowWightEntity);
        this.itemModelResolver.updateForLivingEntity(state.leftHandItemState, barrowWightEntity.getStackInArm(Arm.LEFT),
                ItemDisplayContext.THIRD_PERSON_LEFT_HAND, barrowWightEntity);
    }

    @Override
    public void render(BarrowWightRenderState livingBarrowWightRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(livingBarrowWightRenderState, matrixStack, vertexConsumerProvider, i);
    }
}
