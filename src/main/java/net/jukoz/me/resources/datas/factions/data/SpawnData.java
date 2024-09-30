package net.jukoz.me.resources.datas.factions.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.utils.IdentifierUtil;
import net.jukoz.me.world.dimension.ModDimensions;
import net.jukoz.me.world.map.MiddleEarthMapUtils;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2d;
import org.joml.Vector3d;
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
        this.identifier = IdentifierUtil.getIdentifierFromString(identifier);
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
        NbtCompound coordinateCompound = compound.getCompound("coordinates");
        double x = coordinateCompound.getDouble("x");
        double y = coordinateCompound.getDouble("y");
        double z = coordinateCompound.getDouble("z");
        Vec3d coordinate = new Vec3d(x, y, z);
        boolean isDynamic = compound.getBoolean("dynamic");

        return new SpawnData(compound.getString("id"), coordinate, isDynamic);
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

    private String getIdentifierValue() {
        return this.identifier.toString();
    }

    public Vec3d getCoordinates() {
        return coordinates;
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
