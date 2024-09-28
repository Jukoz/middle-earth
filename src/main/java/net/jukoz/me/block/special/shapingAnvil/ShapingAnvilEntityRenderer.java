package net.jukoz.me.block.special.shapingAnvil;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.*;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class ShapingAnvilEntityRenderer implements BlockEntityRenderer<ShapingAnvilBlockEntity> {

    public ShapingAnvilEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(ShapingAnvilBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        ItemStack stack = entity.getRenderStack();

        if(stack.isEmpty()) return;

        matrices.push();
        matrices.translate(0.5f, 1.0f, 0.5f);
        matrices.scale(0.75f, 0.75f, 0.75f);

        matrices.multiply(RotationAxis.POSITIVE_X.rotation((float) Math.toRadians(-90)));
        switch (entity.getCachedState().get(ShapingAnvilBlock.FACING)) {
            case NORTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(225)));
            case EAST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(135)));
            case SOUTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(45)));
            case WEST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(315)));
        }

        int currentLight = getLightLevel(entity.getWorld(), entity.getPos());

        itemRenderer.renderItem(stack, ModelTransformationMode.FIXED, currentLight, OverlayTexture.DEFAULT_UV,
                matrices, vertexConsumers, entity.getWorld(), 1);

        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}