package net.sevenstars.middleearth.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ModCollisionUtils {

    public static final double DEFAULT_RAY_LENGTH = 0.2;
    public static final double ORIGIN_OFFSET = 0.01;
    public static final double ANGLE_SPREAD = 0.1;

    // Check for block collisions from multiple directions (all 6 faces)
    public static boolean checkBlockFanCollision(
            World world,
            Box box,
            Vec3d velocity,
            Entity contextEntity,
            Consumer<HitResult> onCollision
    ) {
        if (velocity.lengthSquared() == 0) return false;

        // Generate rays for all 6 faces of the bounding box
        List<Pair<Vec3d, Vec3d>> rays = getFanRaysFromBoxFaces(box, velocity);

        for (Pair<Vec3d, Vec3d> ray : rays) {
            Vec3d origin = ray.getLeft();
            Vec3d dir = ray.getRight();
            Vec3d end = origin.add(dir.multiply(DEFAULT_RAY_LENGTH));

            HitResult hit = world.raycast(new RaycastContext(
                    origin,
                    end,
                    RaycastContext.ShapeType.OUTLINE,
                    RaycastContext.FluidHandling.NONE,
                    contextEntity
            ));

            if (hit.getType() != HitResult.Type.MISS) {
                onCollision.accept(hit);  // Call the onCollision callback with the hit result
                return true;
            }
        }

        return false;
    }

    // Generate rays from all six faces of the bounding box
    public static List<Pair<Vec3d, Vec3d>> getFanRaysFromBoxFaces(Box box, Vec3d velocity) {
        List<Pair<Vec3d, Vec3d>> rays = new ArrayList<>();

        // Loop through all 6 directions (faces)
        for (Direction faceDir : Direction.values()) {
            rays.addAll(getFaceRays(box, faceDir, velocity));  // Handle all faces uniformly
        }

        return rays;
    }

    // Get rays from a single face with angle spreading
    private static List<Pair<Vec3d, Vec3d>> getFaceRays(Box box, Direction faceDir, Vec3d velocity) {
        Vec3d normal = Vec3d.of(faceDir.getVector()).normalize();
        Vec3d center = box.getCenter();
        double minX = box.minX, minY = box.minY, minZ = box.minZ;
        double maxX = box.maxX, maxY = box.maxY, maxZ = box.maxZ;

        // Choose the corner points and center on the face
        List<Vec3d> facePoints = switch (faceDir.getAxis()) {
            case X -> List.of(
                    new Vec3d(faceDir == Direction.EAST ? maxX : minX, minY, minZ),
                    new Vec3d(faceDir == Direction.EAST ? maxX : minX, minY, maxZ),
                    new Vec3d(faceDir == Direction.EAST ? maxX : minX, maxY, minZ),
                    new Vec3d(faceDir == Direction.EAST ? maxX : minX, maxY, maxZ),
                    new Vec3d(faceDir == Direction.EAST ? maxX : minX, center.y, center.z)
            );
            case Y -> List.of(
                    new Vec3d(minX, faceDir == Direction.UP ? maxY : minY, minZ),
                    new Vec3d(maxX, faceDir == Direction.UP ? maxY : minY, minZ),
                    new Vec3d(minX, faceDir == Direction.UP ? maxY : minY, maxZ),
                    new Vec3d(maxX, faceDir == Direction.UP ? maxY : minY, maxZ),
                    new Vec3d(center.x, faceDir == Direction.UP ? maxY : minY, center.z)
            );
            case Z -> List.of(
                    new Vec3d(minX, minY, faceDir == Direction.SOUTH ? maxZ : minZ),
                    new Vec3d(maxX, minY, faceDir == Direction.SOUTH ? maxZ : minZ),
                    new Vec3d(minX, maxY, faceDir == Direction.SOUTH ? maxZ : minZ),
                    new Vec3d(maxX, maxY, faceDir == Direction.SOUTH ? maxZ : minZ),
                    new Vec3d(center.x, center.y, faceDir == Direction.SOUTH ? maxZ : minZ)
            );
        };

        List<Pair<Vec3d, Vec3d>> rays = new ArrayList<>();

        for (Vec3d point : facePoints) {
            // Apply a slight outward offset
            Vec3d origin = point.add(normal.multiply(ORIGIN_OFFSET));

            // Apply angle bias to create a fan of rays
            Vec3d diagonalBias = point.subtract(center).normalize().multiply(ANGLE_SPREAD);
            Vec3d direction = velocity.normalize().add(diagonalBias).normalize();

            rays.add(new Pair<>(origin, direction));
        }

        return rays;
    }
}
