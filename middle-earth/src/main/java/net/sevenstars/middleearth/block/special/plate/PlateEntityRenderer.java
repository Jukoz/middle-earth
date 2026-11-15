package net.sevenstars.middleearth.block.special.plate;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.forge.ForgeBlock;

@Environment(value= EnvType.CLIENT)
public class PlateEntityRenderer implements BlockEntityRenderer<PlateBlockEntity, BlockEntityRenderState> {
    private final ItemModelManager itemModelManager;

    public PlateEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.itemModelManager = context.itemModelManager();
    }

    @Override
    public void render(BlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        ItemStack stack = ItemStack.EMPTY; ///TODO FIX ME entity.getStack();

        if(stack.isEmpty()) return;

        Identifier oldId = stack.get(DataComponentTypes.ITEM_MODEL);
        Identifier modelId = PlateFoodModels.getPlateIdentifier(oldId);
        ItemStack copyStack = stack.copy();
        copyStack.set(DataComponentTypes.ITEM_MODEL, modelId);

        boolean is3D = oldId != modelId;

        matrices.push();
        Direction direction = state.blockState.get(PlateBlock.FACING);

        long seed = MathHelper.hashCode(state.pos.getX(), 0, state.pos.getZ());
        double xOffset = ((seed & 15L) / 15.0 - 0.5) * 0.15;
        double zOffset = (((seed >> 4 & 15L) / 15.0) - 0.5) * 0.15;
        double rotOffset = (((seed >> 5 & 15L) / 15.0) - 0.5) * 35;

        if(!is3D) {
            matrices.translate(0.5f + xOffset, 0.085f, 0.5f + zOffset);
            matrices.scale(0.65f, 0.65f, 0.65f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotation((float) Math.toRadians(90)));
            switch (direction) {
                case NORTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(0)));
                case EAST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(90)));
                case SOUTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(180)));
                case WEST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(270)));
            }
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(rotOffset)));
        } else {
            matrices.translate(0.5f + xOffset, 0.56f, 0.5f + zOffset);
            matrices.scale(1f, 1f, 1f);
            switch (direction) {
                case NORTH -> matrices.multiply(RotationAxis.POSITIVE_Y.rotation((float) Math.toRadians(0)));
                case EAST -> matrices.multiply(RotationAxis.POSITIVE_Y.rotation((float) Math.toRadians(90)));
                case SOUTH -> matrices.multiply(RotationAxis.POSITIVE_Y.rotation((float) Math.toRadians(180)));
                case WEST -> matrices.multiply(RotationAxis.POSITIVE_Y.rotation((float) Math.toRadians(270)));
            }
            matrices.multiply(RotationAxis.POSITIVE_Y.rotation((float) Math.toRadians(rotOffset)));
        }

        //this.itemRenderer.renderItem(copyStack, ItemDisplayContext.FIXED, currentLight, OverlayTexture.DEFAULT_UV,
        //        matrices, vertexConsumers, entity.getWorld(), 1);
        //ItemRenderState itemRenderState = state.itemRenderState;
        //if (itemRenderState != null) {
        //    this.renderItem(shelfBlockEntityRenderState, itemRenderState, matrixStack, orderedRenderCommandQueue, i, f);
        //}

        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos, Direction direction) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos.offset(direction).up());
        int sLight = world.getLightLevel(LightType.SKY, pos.offset(direction).up());
        return LightmapTextureManager.pack(bLight, sLight);
    }

    @Override
    public BlockEntityRenderState createRenderState() {
        return new BlockEntityRenderState();
    }
}
