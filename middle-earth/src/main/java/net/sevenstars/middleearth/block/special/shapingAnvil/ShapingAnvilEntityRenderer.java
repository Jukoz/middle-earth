package net.sevenstars.middleearth.block.special.shapingAnvil;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;

public class ShapingAnvilEntityRenderer implements BlockEntityRenderer<ShapingAnvilBlockEntity> {

    private final BlockEntityRendererProvider.Context context;

    public ShapingAnvilEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    private int getLightLevel(Level world, BlockPos pos) {
        int bLight = world.getBrightness(LightLayer.BLOCK, pos);
        int sLight = world.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

    @Override
    public void render(ShapingAnvilBlockEntity entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        ItemStack stack = entity.getRenderStack(entity);

        if(stack.isEmpty()) return;

        matrices.pushPose();
        matrices.translate(0.5f, 1.025f, 0.5f);
        matrices.scale(0.75f, 0.75f, 0.75f);

        matrices.mulPose(Axis.XP.rotation((float) Math.toRadians(90)));

        if (stack.getItem() instanceof BlockItem){
            switch (entity.getBlockState().getValue(AbstractShapingAnvilBlock.FACING)) {
                case NORTH -> matrices.mulPose(Axis.ZP.rotation((float) Math.toRadians(270)));
                case EAST -> matrices.mulPose(Axis.ZP.rotation((float) Math.toRadians(180)));
                case SOUTH -> matrices.mulPose(Axis.ZP.rotation((float) Math.toRadians(90)));
                case WEST -> matrices.mulPose(Axis.ZP.rotation((float) Math.toRadians(0)));
            }
        } else {
            switch (entity.getBlockState().getValue(AbstractShapingAnvilBlock.FACING)) {
                case NORTH -> matrices.mulPose(Axis.ZP.rotation((float) Math.toRadians(225)));
                case EAST -> matrices.mulPose(Axis.ZP.rotation((float) Math.toRadians(135)));
                case SOUTH -> matrices.mulPose(Axis.ZP.rotation((float) Math.toRadians(45)));
                case WEST -> matrices.mulPose(Axis.ZP.rotation((float) Math.toRadians(315)));
            }
        }

        int currentLight = getLightLevel(entity.getLevel(), entity.getBlockPos());

        this.context.getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, currentLight, OverlayTexture.NO_OVERLAY,
                matrices, vertexConsumers, entity.getLevel(), 1);

        matrices.popPose();
    }
}
