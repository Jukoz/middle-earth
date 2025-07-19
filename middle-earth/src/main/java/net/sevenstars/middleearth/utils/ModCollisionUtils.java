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
    // Default ray length used for collision detection
    public static final double DEFAULT_RAY_LENGTH = 0.2;

    // Offset applied to the origin of the rays to ensure they are slightly outside the bounding box
    public static final double ORIGIN_OFFSET = 0.01;

    // Angle spread for rayCasting, which controls how much the rays deviate from the center of the face
    public static final double ANGLE_SPREAD = 0.1;

    /**
     * Check for block collisions from multiple directions (all 6 faces)
     * Generates rays from all faces of the bounding box to detect collisions.
     *
     * @param world         the world in which the collision occurs
     * @param box           the bounding box of the entity or projectile
     * @param velocity      the velocity of the entity/projectile
     * @param contextEntity the entity that is performing the collision check (used for collision context)
     * @param onCollision   a callback function to handle the hit result if a collision occurs
     * @return true if any collision is detected, false otherwise
     */
    public static boolean checkBlockFanCollision(
            World world,
            Box box,
            Vec3d velocity,
            Entity contextEntity,
            Consumer<HitResult> onCollision) {
        // If the velocity is zero but the entity might still be colliding with something, check its position
        if (velocity.lengthSquared() == 0 && !world.isSpaceEmpty(contextEntity)) {
            return true; // Handle the collision, or do whatever logic is necessary for a stationary entity
        }

        // Generate rays for all 6 faces of the bounding box
        List<Pair<Vec3d, Vec3d>> rays = getFanRaysFromBoxFaces(box, velocity);

        // Check each ray for collisions
        for (Pair<Vec3d, Vec3d> ray : rays) {
            Vec3d origin = ray.getLeft();
            Vec3d dir = ray.getRight();
            Vec3d end = origin.add(dir.multiply(DEFAULT_RAY_LENGTH));

            // Perform the raycast to check for a collision
            HitResult hit = world.raycast(new RaycastContext(origin,
                    end,
                    RaycastContext.ShapeType.OUTLINE,
                    // We use OUTLINE shape for detecting blocks
                    RaycastContext.FluidHandling.NONE,
                    // No fluid handling
                    contextEntity));

            // If a collision is detected, invoke the callback and return true
            if (hit.getType() != HitResult.Type.MISS) {
                onCollision.accept(hit);  // Call the onCollision callback with the hit result
                return true;
            }
        }

        return false;  // Return false if no collision is detected
    }

    /**
     * Generate rays from all six faces of the bounding box for collision detection.
     *
     * @param box      the bounding box of the entity/projectile
     * @param velocity the velocity of the entity/projectile
     * @return a list of rays to check for collisions
     */
    public static List<Pair<Vec3d, Vec3d>> getFanRaysFromBoxFaces(Box box, Vec3d velocity) {
        List<Pair<Vec3d, Vec3d>> rays = new ArrayList<>();

        // Loop through all 6 directions (faces)
        for (Direction faceDir : Direction.values()) {
            rays.addAll(getFaceRays(box, faceDir, velocity));  // Generate rays for each face
        }

        return rays;
    }

    /**
     * Generate rays from a single face with an angle spreading.
     *
     * @param box      the bounding box of the entity/projectile
     * @param faceDir  the direction of the face to generate rays from
     * @param velocity the velocity of the entity/projectile
     * @return a list of rays originating from the specified face
     */
    private static List<Pair<Vec3d, Vec3d>> getFaceRays(
            Box box,
            Direction faceDir,
            Vec3d velocity) {
        Vec3d normal = Vec3d.of(faceDir.getVector()).normalize();
        Vec3d center = box.getCenter();
        double minX = box.minX, minY = box.minY, minZ = box.minZ;
        double maxX = box.maxX, maxY = box.maxY, maxZ = box.maxZ;

        // Choose the corner points and center on the face
        List<Vec3d> facePoints = getFacePoints(faceDir, minX, minY, minZ, maxX, maxY, maxZ, center);

        List<Pair<Vec3d, Vec3d>> rays = new ArrayList<>();

        // Generate rays by applying a slight outward offset and angle bias
        for (Vec3d point : facePoints) {
            Vec3d origin = point.add(normal.multiply(ORIGIN_OFFSET));  // Apply outward offset
            Vec3d diagonalBias = point.subtract(center).normalize().multiply(ANGLE_SPREAD);  // Apply angle bias
            Vec3d direction = velocity.normalize().add(diagonalBias).normalize();  // Final ray direction

            rays.add(new Pair<>(origin, direction));  // Add the ray to the list
        }

        return rays;
    }

    /**
     * Generate points on the face of the box based on the direction of the face.
     * This method returns the corners and center of each face, based on the direction of the face.
     *
     * @param faceDir the direction of the face (X, Y, or Z axis)
     * @param minX    minimum X coordinate of the box
     * @param minY    minimum Y coordinate of the box
     * @param minZ    minimum Z coordinate of the box
     * @param maxX    maximum X coordinate of the box
     * @param maxY    maximum Y coordinate of the box
     * @param maxZ    maximum Z coordinate of the box
     * @param center  the center of the box
     * @return a list of points on the face of the box
     */
    private static List<Vec3d> getFacePoints(
            Direction faceDir,
            double minX,
            double minY,
            double minZ,
            double maxX,
            double maxY,
            double maxZ,
            Vec3d center) {
        return switch (faceDir.getAxis()) {
            case X -> // X-axis (East-West direction)
                    List.of(new Vec3d(faceDir == Direction.EAST ? maxX : minX, minY, minZ),
                            new Vec3d(faceDir == Direction.EAST ? maxX : minX, minY, maxZ),
                            new Vec3d(faceDir == Direction.EAST ? maxX : minX, maxY, minZ),
                            new Vec3d(faceDir == Direction.EAST ? maxX : minX, maxY, maxZ),
                            new Vec3d(faceDir == Direction.EAST ? maxX : minX, center.y, center.z));
            case Y -> // Y-axis (Up-Down direction)
                    List.of(new Vec3d(minX, faceDir == Direction.UP ? maxY : minY, minZ),
                            new Vec3d(maxX, faceDir == Direction.UP ? maxY : minY, minZ),
                            new Vec3d(minX, faceDir == Direction.UP ? maxY : minY, maxZ),
                            new Vec3d(maxX, faceDir == Direction.UP ? maxY : minY, maxZ),
                            new Vec3d(center.x, faceDir == Direction.UP ? maxY : minY, center.z));
            case Z -> // Z-axis (North-South direction)
                    List.of(new Vec3d(minX, minY, faceDir == Direction.SOUTH ? maxZ : minZ),
                            new Vec3d(maxX, minY, faceDir == Direction.SOUTH ? maxZ : minZ),
                            new Vec3d(minX, maxY, faceDir == Direction.SOUTH ? maxZ : minZ),
                            new Vec3d(maxX, maxY, faceDir == Direction.SOUTH ? maxZ : minZ),
                            new Vec3d(center.x,
                                    center.y,
                                    faceDir == Direction.SOUTH ? maxZ : minZ));
        };
    }
}
