package net.sevenstars.middleearth.block.special.plate;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.forge.ForgeBlock;

@Environment(value= EnvType.CLIENT)
public class PlateEntityRenderer implements BlockEntityRenderer<PlateBlockEntity> {

    private final ItemRenderer itemRenderer;

    public PlateEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(PlateBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        ItemStack stack = entity.getStack();

        if(stack.isEmpty()) return;

        Identifier oldId = stack.get(DataComponentTypes.ITEM_MODEL);
        Identifier modelId = PlateFoodModels.getPlateIdentifier(oldId);
        ItemStack copyStack = stack.copy();
        copyStack.set(DataComponentTypes.ITEM_MODEL, modelId);

        boolean is3D = oldId != modelId;

        matrices.push();
        Direction direction = entity.getCachedState().get(ForgeBlock.FACING);

        if(!is3D) {
            matrices.translate(0.5f, 0.08f, 0.5f);
            matrices.scale(0.65f, 0.65f, 0.65f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotation((float) Math.toRadians(90)));
            switch (direction) {
                case NORTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(0)));
                case EAST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(90)));
                case SOUTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(180)));
                case WEST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) Math.toRadians(270)));
            }
        } else {
            matrices.translate(0.5f, 0.55f, 0.5f);
            matrices.scale(1f, 1f, 1f);
            switch (direction) {
                case NORTH -> matrices.multiply(RotationAxis.POSITIVE_Y.rotation((float) Math.toRadians(0)));
                case EAST -> matrices.multiply(RotationAxis.POSITIVE_Y.rotation((float) Math.toRadians(90)));
                case SOUTH -> matrices.multiply(RotationAxis.POSITIVE_Y.rotation((float) Math.toRadians(180)));
                case WEST -> matrices.multiply(RotationAxis.POSITIVE_Y.rotation((float) Math.toRadians(270)));
            }
        }




        int currentLight = getLightLevel(entity.getWorld(), entity.getPos(), direction);

        this.itemRenderer.renderItem(copyStack, ItemDisplayContext.FIXED, currentLight, OverlayTexture.DEFAULT_UV,
                matrices, vertexConsumers, entity.getWorld(), 1);

        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos, Direction direction) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos.offset(direction).up());
        int sLight = world.getLightLevel(LightType.SKY, pos.offset(direction).up());
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
