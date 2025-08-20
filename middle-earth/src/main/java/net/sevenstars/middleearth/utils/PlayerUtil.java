package net.sevenstars.middleearth.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.RaceType;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlayerUtil {
    public static boolean isAgainstWall(PlayerEntity entity) {
        Box boundingBox = entity.getBoundingBox();
        World world = entity.getWorld();
        double testMovementAmount = 0.0001;

        Vec3d testMovement = new Vec3d(testMovementAmount, 0.0, testMovementAmount);
        List<VoxelShape> collisions = world.getEntityCollisions(entity, boundingBox.stretch(testMovement));
        Vec3d result = Entity.adjustMovementForCollisions(entity, testMovement, boundingBox, world, collisions);
        if (!result.equals(testMovement)) {
            return world.getBlockState(entity.getBlockPos().offset(entity.getHorizontalFacing())).isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, "climbing_attribute_allowed_blocks")));
        }

        testMovement = new Vec3d(-testMovementAmount, 0.0, -testMovementAmount);
        collisions = world.getEntityCollisions(entity, boundingBox.stretch(testMovement));
        result = Entity.adjustMovementForCollisions(entity, testMovement, boundingBox, world, collisions);
        if (!result.equals(testMovement)) {
            return world.getBlockState(entity.getBlockPos().offset(entity.getHorizontalFacing())).isIn(TagKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, "climbing_attribute_allowed_blocks")));
        }

        return false;
    }

    public static boolean isOfRace(@NotNull PlayerEntity entity, @NotNull RaceType type){
        PlayerData data = StateSaverAndLoader.getPlayerState(entity);
        if(data != null && data.getRace() != null){
            Race race = RaceLookup.getRace(entity.getWorld(), data.getRace());
            if(race != null){
                RaceType raceType = race.getRaceType();
                return raceType == type;
            }
        }
        return false;
    }

    public static boolean isOfRace(@NotNull PlayerEntity entity, @NotNull List<RaceType> types){
        PlayerData data = StateSaverAndLoader.getPlayerState(entity);
        if(data != null && data.getRace() != null){
            Race race = RaceLookup.getRace(entity.getWorld(), data.getRace());
            if(race != null){
                RaceType raceType = race.getRaceType();
                return types.contains(raceType);
            }
        }
        return false;
    }
}
