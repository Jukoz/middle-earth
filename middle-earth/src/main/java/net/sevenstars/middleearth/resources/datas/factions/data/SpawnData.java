package net.sevenstars.middleearth.resources.datas.factions.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.sevenstars.middleearth.world.map.MiddleEarthMapConfigs;
import net.sevenstars.middleearth.world.map.MiddleEarthMapUtils;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2d;
import org.joml.Vector3i;

public class SpawnData {
    public static final Codec<SpawnData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(SpawnData::getIdentifierValue),
            Vec3d.CODEC.fieldOf("coordinates").forGetter(SpawnData::getCoordinates),
            Codec.BOOL.fieldOf("dynamic").forGetter(SpawnData::isDynamic))
            .apply(instance, SpawnData::new));

    private Identifier identifier;
    private Vec3d coordinates;
    private boolean isDynamic = false;

    /**
     * Constructor from codec
     * @param identifier
     * @param coordinates
     * @param isDynamic
     */
    public SpawnData(String identifier, Vec3d coordinates, Boolean isDynamic) {
        this.identifier = MiddleEarth.fetchId(identifier);
        this.isDynamic = isDynamic;

        if(isDynamic)
            this.coordinates = new Vec3d(coordinates.x, 0, coordinates.z);
        else
            this.coordinates = coordinates;
    }

    public SpawnData(Identifier identifier, Vector2d coordinate){
        this(identifier, new Vec3d(coordinate.x, 0, coordinate.y));
        isDynamic = true;
    }

    public SpawnData(Identifier identifier, Vec3d coordinate){
        this.identifier = identifier;
        this.coordinates = coordinate;
    }

    public static SpawnData deserialize(NbtCompound compound) {
        NbtCompound coordinateCompound = compound.getCompound("coordinates").get();
        double x = coordinateCompound.getDouble("x").get();
        double y = coordinateCompound.getDouble("y").get();
        double z = coordinateCompound.getDouble("z").get();
        Vec3d coordinate = new Vec3d(x, y, z);
        boolean isDynamic = compound.getBoolean("dynamic").get();

        return new SpawnData(compound.getString("id").get(), coordinate, isDynamic);
    }

    public static NbtCompound serialize(SpawnData spawnData) {
        NbtCompound serializedCompound = new NbtCompound();

        NbtCompound coordinateCompound = new NbtCompound();
        coordinateCompound.putDouble("x", spawnData.coordinates.x);
        coordinateCompound.putDouble("y", spawnData.coordinates.y);
        coordinateCompound.putDouble("z", spawnData.coordinates.z);

        serializedCompound.put("coordinates", coordinateCompound);
        serializedCompound.putString("id", spawnData.getIdentifierValue());
        serializedCompound.putBoolean("dynamic", spawnData.isDynamic());
        return serializedCompound;
    }

    public Identifier getIdentifier(){
        return identifier;
    }

    public MutableText getFullName(){
        return Text.translatable("spawn." + identifier.toTranslationKey());
    }



    private String getIdentifierValue() {
        return this.identifier.toString();
    }

    public Vec3d getCoordinates() {
        return coordinates;
    }

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

    public boolean isDynamic() {
        return isDynamic;
    }

    public BlockPos getBlockPos() {
        BlockPos blockPos = new BlockPos((int) coordinates.x, (int) coordinates.y, (int) coordinates.z);
        if(isDynamic){
            Vector2d coords = MiddleEarthMapUtils.getInstance().getWorldCoordinateFromInitialMap(coordinates.x, coordinates.z);
            Vector3i spawnCoordinates =  ModDimensions.getDimensionHeight((int) coords.x, (int) coords.y);
            blockPos = new BlockPos(spawnCoordinates.x, spawnCoordinates.y, spawnCoordinates.z);
        }
        return blockPos;
    }
}
