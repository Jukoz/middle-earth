package net.sevenstars.ofhamletandheroes.dtos.spawn;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;

public class Spawn {
    public static final Codec<Spawn> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Vec3d.CODEC.fieldOf("coordinates").forGetter(Spawn::getCoordinates),
            Codec.BOOL.fieldOf("dynamic").forGetter(Spawn::isDynamic),
            RegistryKey.createCodec(RegistryKeys.DIMENSION_TYPE).fieldOf("dimension_type").forGetter(Spawn::getDimensionType))
            .apply(instance, Spawn::new));

    private Vec3d coordinates;
    private boolean isDynamic = false;
    private RegistryKey<DimensionType> dimensionType;

    public Spawn(Vec3d coordinates, Boolean isDynamic, RegistryKey<DimensionType> dimensionType) {
        this.isDynamic = isDynamic;
        this.dimensionType = dimensionType;

        if(isDynamic)
            this.coordinates = new Vec3d(coordinates.x, 0, coordinates.z);
        else
            this.coordinates = coordinates;
    }

    public Spawn(Vec3d coordinate, RegistryKey<DimensionType> dimensionType){
        this.coordinates = coordinate;
        this.dimensionType = dimensionType;
    }

    public Vec3d getCoordinates() {
        return coordinates;
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public RegistryKey<DimensionType> getDimensionType() {
        return dimensionType;
    }

    /* TODO : Remove it from this dto
    public Vector3i getWorldCoordinates() {
        Vector3i worldCoordinates = new Vector3i((int) coordinates.x, (int) coordinates.y, (int) coordinates.z);
        if(isDynamic) {
            int ratio = (MiddleEarthMapConfigs.FULL_MAP_SIZE / MiddleEarthMapConfigs.REGION_SIZE);
            worldCoordinates.x = worldCoordinates.x * ratio;
            worldCoordinates.z = worldCoordinates.z * ratio;
        }
        return worldCoordinates;
    }

    public BlockPos getWorldCoordinateBlockPos() {
        BlockPos worldCoordinates = new BlockPos((int) coordinates.x, (int) coordinates.y, (int) coordinates.z);
        if(isDynamic) {
            int ratio = (MiddleEarthMapConfigs.FULL_MAP_SIZE / MiddleEarthMapConfigs.REGION_SIZE);
            worldCoordinates = new BlockPos((int) coordinates.x * ratio, (int) coordinates.y, (int) coordinates.z * ratio);
        }
        return worldCoordinates;
    }

    public BlockPos getBlockPos() {
        BlockPos blockPos = new BlockPos((int) coordinates.x, (int) coordinates.y, (int) coordinates.z);
        if(isDynamic){
            Vector2d coords = MiddleEarthMapUtils.getInstance().getWorldCoordinateFromInitialMap(coordinates.x, coordinates.z);
            Vector3i spawnCoordinates =  DimensionRegistryME.getDimensionHeight((int) coords.x, (int) coords.y);
            blockPos = new BlockPos(spawnCoordinates.x, spawnCoordinates.y, spawnCoordinates.z);
        }
        return blockPos;
    }
     */
}
