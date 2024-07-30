package net.jukoz.me.block.special.forge;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.*;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ForgeBlockEntityRenderer implements BlockEntityRenderer<ForgeBlockEntity> {
    private final float OFFSET = 0.25f;
    private final List<Vector2f> POSITIONS = new ArrayList<>(Arrays.asList(
            new Vector2f(-OFFSET,-OFFSET),
            new Vector2f(OFFSET, -OFFSET),
            new Vector2f(-OFFSET,OFFSET),
            new Vector2f(OFFSET, OFFSET)));

    public ForgeBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(ForgeBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        List<ItemStack> inputs = new ArrayList<>();
        Inventory inventory = (Inventory) entity;
        inputs.add(inventory.getStack(1));
        inputs.add(inventory.getStack(2));
        inputs.add(inventory.getStack(3));
        inputs.add(inventory.getStack(4));

        matrices.push();
        matrices.translate(0.5f, 1.15f, 0.5f);
        matrices.scale(0.5f, 0.5f, 0.5f);

        matrices.multiply(RotationAxis.POSITIVE_X.rotation((float) Math.toRadians(-90)));
        switch (entity.getCachedState().get(ForgeBlock.FACING)) {
            case NORTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(180)));
            case EAST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(90)));
            case SOUTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(0)));
            case WEST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(270)));
        }

        int currentLight = getLightLevel(entity.getWorld(), entity.getPos());

        //if(entity.isCooking()) { // Keep for later
        //    BlockState lavaState = Blocks.LAVA.getDefaultState().with(FluidBlock.LEVEL, 0);
        //    BakedModel bakedModel = MinecraftClient.getInstance().getBlockRenderManager().getModel(lavaState);
        //    blockRenderer.render(matrices.peek(), vertexConsumers.getBuffer(TexturedRenderLayers.getEntitySolid()), null, bakedModel,
        //            1.0f, 1.0f, 1.0f, currentLight, OverlayTexture.DEFAULT_UV);
        //}
        if(!inputs.isEmpty() && inputs.size() <= POSITIONS.size()) {
            for(int i = 0; i < inputs.size(); i++) {
                Vector2f position = POSITIONS.get(i);
                matrices.translate(position.getX(), 0, position.getY());

                itemRenderer.renderItem(inputs.get(i), ModelTransformationMode.FIXED, currentLight, OverlayTexture.DEFAULT_UV,
                        matrices, vertexConsumers, entity.getWorld(), 1);

                matrices.translate(-position.getX(), 0, -position.getY());
            }
        }

        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
