package net.sevenstars.middleearth.block.special.plate;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@Environment(value= EnvType.CLIENT)
public class PlateEntityRenderer implements BlockEntityRenderer<PlateBlockEntity, PlateEntityRenderer.State> {
    private final ItemModelManager itemModelManager;

    public PlateEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.itemModelManager = context.itemModelManager();
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void updateRenderState(PlateBlockEntity entity, State state, float tickProgress, Vec3d cameraPos,
                                  ModelCommandRenderer.@Nullable CrumblingOverlayCommand crumblingOverlay) {
        BlockEntityRenderer.super.updateRenderState(entity, state, tickProgress, cameraPos, crumblingOverlay);
        state.facing = entity.getCachedState().get(PlateBlock.FACING);
        state.is3D = false;

        ItemStack stack = entity.getStack();
        if (stack.isEmpty()) {
            state.item.clear();
            return;
        }

        Identifier oldId = stack.get(DataComponentTypes.ITEM_MODEL);
        Identifier modelId = PlateFoodModels.getPlateIdentifier(oldId);
        ItemStack copyStack = stack.copy();
        copyStack.set(DataComponentTypes.ITEM_MODEL, modelId);

        state.is3D = oldId != modelId;
        this.itemModelManager.clearAndUpdate(state.item, copyStack, ItemDisplayContext.FIXED, entity.getWorld(), null, 1);
        state.itemLight = getLightLevel(entity.getWorld(), entity.getPos(), state.facing);
    }

    @Override
    public void render(State state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        if (state.item.isEmpty()) return;

        boolean is3D = state.is3D;

        matrices.push();
        Direction direction = state.facing;

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

        state.item.render(matrices, queue, state.itemLight, OverlayTexture.DEFAULT_UV, 0);

        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos, Direction direction) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos.offset(direction).up());
        int sLight = world.getLightLevel(LightType.SKY, pos.offset(direction).up());
        return LightmapTextureManager.pack(bLight, sLight);
    }

    public static class State extends BlockEntityRenderState {
        public final ItemRenderState item = new ItemRenderState();
        public Direction facing = Direction.NORTH;
        public boolean is3D;
        public int itemLight;
    }
}
