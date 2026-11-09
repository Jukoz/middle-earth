package net.sevenstars.middleearth.client.renderer.light;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.beamlamp.BeamLampBlock;
import net.sevenstars.middleearth.block.special.beamlamp.BeamLampBlockEntity;
import net.sevenstars.middleearth.light.BeamLights;

/** A placed lamp's beam, from its lens to where it lands, while it is lit. */
@Environment(EnvType.CLIENT)
public class BeamLampRenderer implements BlockEntityRenderer<BeamLampBlockEntity> {
    public BeamLampRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(BeamLampBlockEntity lamp, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                       int light, int overlay, Vec3d cameraPos) {
        BlockState state = lamp.getCachedState();
        World world = lamp.getWorld();
        if (world == null || !(state.getBlock() instanceof BeamLampBlock block) || !state.get(BeamLampBlock.LIT)) {
            return;
        }
        Vec3d lens = BeamLampBlock.lens(lamp.getPos(), state);
        Vec3d direction = BeamLampBlock.direction(state);
        Vec3d end = BeamLights.beamEnd(world, lens.add(direction.multiply(0.3)), direction, block.kind().range());
        Vec3d origin = Vec3d.of(lamp.getPos());
        // From just inside the lens, so the light leaves the lamp itself rather than the air in front of it.
        Vec3d from = lens.subtract(direction.multiply(1.5 / 16));
        LampBeams.draw(matrices.peek().getPositionMatrix(), vertexConsumers.getBuffer(LampBeams.layer()),
            from.subtract(origin), end.subtract(origin), block.kind().color(), LampBeams.fadeLength(block.kind().range()));
        LampBeams.notePlaced(lamp.getPos(), new LampBeams.Beam(lens, end, block.kind().color()), world.getTime());
    }

    @Override
    public boolean rendersOutsideBoundingBox() {
        return true;
    }

    @Override
    public int getRenderDistance() {
        return 128;
    }
}
