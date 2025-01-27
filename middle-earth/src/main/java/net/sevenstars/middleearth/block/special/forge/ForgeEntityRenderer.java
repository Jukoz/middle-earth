package net.sevenstars.middleearth.block.special.forge;

import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class ForgeEntityRenderer implements BlockEntityRenderer<ForgeBlockEntity> {

    private final BlockEntityRendererFactory.Context context;

    public ForgeEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.context = context;
    }

    @Override
    public void render(ForgeBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {

        ItemStack stack = entity.getRenderStack(entity);

        if(stack.isEmpty()) return;

        matrices.push();
        matrices.translate(0.5f, 1.025f, 0.5f);
        matrices.scale(0.65f, 0.65f, 0.65f);

        matrices.multiply(RotationAxis.POSITIVE_X.rotation((float) Math.toRadians(90)));

        Direction direction = entity.getCachedState().get(ForgeBlock.FACING);

        switch (direction) {
            case NORTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(225)));
            case EAST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(135)));
            case SOUTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(45)));
            case WEST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(315)));
        }

        int currentLight = getLightLevel(entity.getWorld(), entity.getPos(), direction);

        this.context.getItemRenderer().renderItem(stack, ModelTransformationMode.FIXED, currentLight, OverlayTexture.DEFAULT_UV,
                matrices, vertexConsumers, entity.getWorld(), 1);

        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos, Direction direction) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos.offset(direction).up());
        int sLight = world.getLightLevel(LightType.SKY, pos.offset(direction).up());
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
