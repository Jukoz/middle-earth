package net.sevenstars.middleearth.block.special.shapingAnvil;

import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class ShapingAnvilEntityRenderer implements BlockEntityRenderer<ShapingAnvilBlockEntity> {

    private final BlockEntityRendererFactory.Context context;

    public ShapingAnvilEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.context = context;
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }

    @Override
    public void render(ShapingAnvilBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        ItemStack stack = entity.getRenderStack(entity);

        if(stack.isEmpty()) return;

        matrices.push();
        matrices.translate(0.5f, 1.025f, 0.5f);
        matrices.scale(0.75f, 0.75f, 0.75f);

        matrices.multiply(RotationAxis.POSITIVE_X.rotation((float) Math.toRadians(90)));

        if (stack.getItem() instanceof BlockItem){
            switch (entity.getCachedState().get(AbstractShapingAnvilBlock.FACING)) {
                case NORTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(270)));
                case EAST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(180)));
                case SOUTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(90)));
                case WEST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(0)));
            }
        } else {
            switch (entity.getCachedState().get(AbstractShapingAnvilBlock.FACING)) {
                case NORTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(225)));
                case EAST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(135)));
                case SOUTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(45)));
                case WEST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(315)));
            }
        }

        int currentLight = getLightLevel(entity.getWorld(), entity.getPos());

        this.context.getItemRenderer().renderItem(stack, ItemDisplayContext.FIXED, currentLight, OverlayTexture.DEFAULT_UV,
                matrices, vertexConsumers, entity.getWorld(), 1);

        matrices.pop();
    }
}
