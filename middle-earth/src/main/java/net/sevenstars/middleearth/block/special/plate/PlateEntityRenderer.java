package net.sevenstars.middleearth.block.special.plate;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.block.special.forge.ForgeBlock;

public class PlateEntityRenderer implements BlockEntityRenderer<PlateBlockEntity> {

    private final ItemRenderer itemRenderer;

    public PlateEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(PlateBlockEntity entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        ItemStack stack = entity.getTheItem();

        if(stack.isEmpty()) return;

        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        ResourceLocation modelId = PlateFoodModels.getPlateIdentifier(itemId);
        boolean is3D = !itemId.equals(modelId);

        matrices.pushPose();
        Direction direction = entity.getBlockState().getValue(ForgeBlock.FACING);

        long seed = Mth.getSeed(entity.getBlockPos().getX(), 0, entity.getBlockPos().getZ());
        double xOffset = ((seed & 15L) / 15.0 - 0.5) * 0.15;
        double zOffset = (((seed >> 4 & 15L) / 15.0) - 0.5) * 0.15;
        double rotOffset = (((seed >> 5 & 15L) / 15.0) - 0.5) * 35;

        if(!is3D) {
            matrices.translate(0.5f + xOffset, 0.085f, 0.5f + zOffset);
            matrices.scale(0.65f, 0.65f, 0.65f);
            matrices.mulPose(Axis.XP.rotation((float) Math.toRadians(90)));
            switch (direction) {
                case NORTH -> matrices.mulPose(Axis.ZP.rotation((float) Math.toRadians(0)));
                case EAST -> matrices.mulPose(Axis.ZP.rotation((float) Math.toRadians(90)));
                case SOUTH -> matrices.mulPose(Axis.ZP.rotation((float) Math.toRadians(180)));
                case WEST -> matrices.mulPose(Axis.ZP.rotation((float) Math.toRadians(270)));
            }
            matrices.mulPose(Axis.ZP.rotation((float) Math.toRadians(rotOffset)));
        } else {
            matrices.translate(0.5f + xOffset, 0.56f, 0.5f + zOffset);
            matrices.scale(1f, 1f, 1f);
            switch (direction) {
                case NORTH -> matrices.mulPose(Axis.YP.rotation((float) Math.toRadians(0)));
                case EAST -> matrices.mulPose(Axis.YP.rotation((float) Math.toRadians(90)));
                case SOUTH -> matrices.mulPose(Axis.YP.rotation((float) Math.toRadians(180)));
                case WEST -> matrices.mulPose(Axis.YP.rotation((float) Math.toRadians(270)));
            }
            matrices.mulPose(Axis.YP.rotation((float) Math.toRadians(rotOffset)));
        }

        int currentLight = getLightLevel(entity.getLevel(), entity.getBlockPos(), direction);

        if (is3D) {
            BakedModel model = Minecraft.getInstance().getModelManager()
                    .getModel(ModelResourceLocation.standalone(modelId.withPrefix("item/")));
            if (model != Minecraft.getInstance().getModelManager().getMissingModel()) {
                this.itemRenderer.render(stack, ItemDisplayContext.FIXED, false, matrices, vertexConsumers,
                        currentLight, OverlayTexture.NO_OVERLAY, model);
            } else {
                this.itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, currentLight,
                        OverlayTexture.NO_OVERLAY, matrices, vertexConsumers, entity.getLevel(), 1);
            }
        } else {
            this.itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, currentLight,
                    OverlayTexture.NO_OVERLAY, matrices, vertexConsumers, entity.getLevel(), 1);
        }

        matrices.popPose();
    }

    private int getLightLevel(Level world, BlockPos pos, Direction direction) {
        int bLight = world.getBrightness(LightLayer.BLOCK, pos.relative(direction).above());
        int sLight = world.getBrightness(LightLayer.SKY, pos.relative(direction).above());
        return LightTexture.pack(bLight, sLight);
    }
}
