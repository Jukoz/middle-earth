package net.sevenstars.middleearth.features.smithing.block.forge;

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
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ForgeEntityRenderer implements BlockEntityRenderer<ForgeBlockEntity, ForgeEntityRenderer.State> {

    private final ItemModelManager itemModelManager;

    public ForgeEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.itemModelManager = context.itemModelManager();
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void updateRenderState(ForgeBlockEntity entity, State state, float tickProgress, Vec3d cameraPos,
                                  ModelCommandRenderer.@Nullable CrumblingOverlayCommand crumblingOverlay) {
        BlockEntityRenderer.super.updateRenderState(entity, state, tickProgress, cameraPos, crumblingOverlay);
        state.facing = entity.getCachedState().get(ForgeBlock.FACING);
        this.itemModelManager.clearAndUpdate(state.item, entity.getRenderStack(entity), ItemDisplayContext.FIXED, entity.getWorld(), null, 1);
        // The ingot lies on top of the forge, so it takes the light of the block in front of it and one up
        state.itemLight = entity.getWorld() == null ? LightmapTextureManager.MAX_LIGHT_COORDINATE
                : getLightLevel(entity.getWorld(), entity.getPos(), state.facing);
    }

    @Override
    public void render(State state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        if (state.item.isEmpty()) return;

        matrices.push();
        matrices.translate(0.5f, 1.025f, 0.5f);
        matrices.scale(0.65f, 0.65f, 0.65f);

        matrices.multiply(RotationAxis.POSITIVE_X.rotation((float) Math.toRadians(90)));

        switch (state.facing) {
            case NORTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(225)));
            case EAST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(135)));
            case SOUTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(45)));
            case WEST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(315)));
            default -> {}
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
        public int itemLight;
    }
}
