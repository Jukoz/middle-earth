package net.sevenstars.middleearth.block.special.beamlamp;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;
import net.sevenstars.middleearth.light.BeamLights;
import net.sevenstars.middleearth.light.LampDazzle;
import org.jetbrains.annotations.Nullable;

/** Keeps a placed lamp's beam spot lit: re-aims every few ticks, so a block put in the way moves the light. */
public class BeamLampBlockEntity extends BlockEntity {
    private static final int AIM_TICKS = 5;

    /** The light this lamp keeps, if any (not saved: it lights again within a few ticks of loading). */
    private @Nullable BlockPos spot;
    /** The lights it keeps along its beam, where walls are near. */
    private List<BlockPos> path = List.of();

    public BeamLampBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistryME.BEAM_LAMP, pos, state);
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, BeamLampBlockEntity lamp) {
        if (!(world instanceof ServerWorld server) || Math.floorMod(world.getTime() + pos.hashCode(), AIM_TICKS) != 0
            || !(state.getBlock() instanceof BeamLampBlock block)) {
            return;
        }
        BlockPos target = null;
        List<BlockPos> way = List.of();
        if (state.get(BeamLampBlock.LIT)) {
            Vec3d direction = BeamLampBlock.direction(state);
            Vec3d from = BeamLampBlock.lens(pos, state).add(direction.multiply(0.3));
            target = BeamLights.beamSpot(world, from, direction, block.kind().range());
            way = BeamLights.beamPath(world, from, direction, block.kind().range());
        }
        if (state.get(BeamLampBlock.LIT) && Math.floorMod(world.getTime() + pos.hashCode(), LampDazzle.CHECK_TICKS * AIM_TICKS) == 0) {
            Vec3d direction = BeamLampBlock.direction(state);
            LampDazzle.beam(server, null, BeamLampBlock.lens(pos, state), direction, block.kind().range());
        }
        if (lamp.spot != null && !lamp.spot.equals(target)) {
            BeamLights.remove(server, lamp.spot);
        }
        lamp.path = BeamLights.movePath(server, lamp.path, way, block.kind().spotLevel() - BeamLights.PATH_DIMMER,
            target == null ? List.of() : List.of(target));
        lamp.spot = target != null && BeamLights.place(server, target, block.kind().spotLevel()) ? target : null;
    }
}
