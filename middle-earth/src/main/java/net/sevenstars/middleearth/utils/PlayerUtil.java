package net.sevenstars.middleearth.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlayerUtil {
    private static final TagKey<Block> CLIMBING_UNALLOWED_BLOCKS = TagKey.of(RegistryKeys.BLOCK, MiddleEarth.id("climbing_attribute_unallowed_blocks"));

    public static boolean isAgainstWall(PlayerEntity entity) {
        Box boundingBox = entity.getBoundingBox();
        World world = entity.getEntityWorld();
        double testMovementAmount = 0.0001;

        Vec3d testMovement = new Vec3d(testMovementAmount, 0.0, testMovementAmount);
        List<VoxelShape> collisions = world.getEntityCollisions(entity, boundingBox.stretch(testMovement));
        Vec3d result = Entity.adjustMovementForCollisions(entity, testMovement, boundingBox, world, collisions);
        if (!result.equals(testMovement)) {
            return checkIfBlockIsAllowed(world, entity);
        //World world = entity.getWorld();
        //// Shrunk vertically so the floor and ceiling don't count as a wall
        //Box reach = entity.getBoundingBox().expand(0.01, -0.01, 0.01);
        //VoxelShape reachShape = VoxelShapes.cuboid(reach);
        //ShapeContext context = ShapeContext.of(entity);
        //BlockPos.Mutable pos = new BlockPos.Mutable();
        //// Starts one block lower to catch fences and walls, which are taller than a block
        //for (int x = MathHelper.floor(reach.minX); x <= MathHelper.floor(reach.maxX); x++) {
        //    for (int y = MathHelper.floor(reach.minY) - 1; y <= MathHelper.floor(reach.maxY); y++) {
        //        for (int z = MathHelper.floor(reach.minZ); z <= MathHelper.floor(reach.maxZ); z++) {
        //            pos.set(x, y, z);
        //            BlockState state = world.getBlockState(pos);
        //            if (state.isIn(CLIMBING_UNALLOWED_BLOCKS)) {
        //                continue;
        //            }
        //            VoxelShape shape = state.getCollisionShape(world, pos, context);
        //            if (!shape.isEmpty() && VoxelShapes.matchesAnywhere(shape.offset(x, y, z), reachShape, BooleanBiFunction.AND)) {
        //                return true;
        //            }
        //        }
        //    }
        }
        return false;
    }

    public static boolean isOfRace(@NotNull PlayerEntity entity, @NotNull RaceType type){
        PlayerData data = StateSaverAndLoader.getPlayerState(entity);
        if(data != null && data.getRace() != null){
            Race race = RaceLookup.getRace(entity.getEntityWorld(), data.getRace());
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
            Race race = RaceLookup.getRace(entity.getEntityWorld(), data.getRace());
            if(race != null){
                RaceType raceType = race.getRaceType();
                return types.contains(raceType);
            }
        }
        return false;
    }

    public static Faction fetchFaction(@NotNull PlayerEntity entity){
        PlayerData data = StateSaverAndLoader.getPlayerState(entity);
        if(data != null && data.getFaction() != null){
            try {
                return FactionLookup.getFactionById(entity.getEntityWorld(), data.getFaction());
            } catch (FactionIdentifierException e) {
                return null;
            }
        }
        return null;
    }
    public static SpawnData fetchSpawn(@NotNull PlayerEntity entity){
        PlayerData data = StateSaverAndLoader.getPlayerState(entity);
        if(data != null && data.getFaction() != null && data.getSpawn() != null){
            try {
                Faction faction = FactionLookup.getFactionById(entity.getEntityWorld(), data.getFaction());
                return faction.getSpawnData().findSpawn(data.getSpawn());
            } catch (FactionIdentifierException e) {
                return null;
            }
        }
        return null;
    }
}
