package net.jesteur.me.mixin;

import net.jesteur.me.MiddleEarth;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithHead;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jesteur.me.entity.equipments.CloakHoodEntityModel;
import net.jesteur.me.item.ModEquipmentItems;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(HeadFeatureRenderer.class)
public class HeadFeatureRendererMixin <T extends LivingEntity, M extends EntityModel<T> & ModelWithHead> extends FeatureRenderer<T, M> {
    private static final Identifier CLOAK_HOOD_TEXTURE = new Identifier(MiddleEarth.MOD_ID, "textures/models/armor/cloak_hood.png");
    @Shadow
    @Final
    private float scaleX;
    @Shadow
    @Final
    private float scaleY;
    @Shadow
    @Final
    private float scaleZ;

    private final CloakHoodEntityModel cloakHoodModel = new CloakHoodEntityModel(CloakHoodEntityModel.getTexturedModelData().createModel());

    public HeadFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemStack itemStack = entity.getEquippedStack(EquipmentSlot.HEAD);
        if (itemStack.getItem() == ModEquipmentItems.CLOAK_HOOD) {
            matrices.push();
            matrices.scale(this.scaleX, this.scaleY, this.scaleZ);
            ((ModelWithHead) ((HeadFeatureRenderer) (Object) this).getContextModel()).getHead().rotate(matrices);
            matrices.scale(1.19F, 1.19F, 1.19F);

            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, this.cloakHoodModel.getLayer(CLOAK_HOOD_TEXTURE), false, itemStack.hasGlint());

            this.cloakHoodModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            matrices.pop();
        }
    }
}
