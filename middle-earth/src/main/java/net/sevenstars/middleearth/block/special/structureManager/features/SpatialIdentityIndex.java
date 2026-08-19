package net.sevenstars.middleearth.block.special.structureManager.features;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

final class SpatialIdentityIndex<T> {
    static final int CELL_SIZE = 64;

    private final ConcurrentHashMap<Long, ConcurrentHashMap<Position, Registration<T>>>
            cells = new ConcurrentHashMap<>();

    void register(Position position, T owner) {
        long cellKey = cellKey(position);
        cells.compute(cellKey, (key, registrations) -> {
            ConcurrentHashMap<Position, Registration<T>> updated =
                    registrations == null ? new ConcurrentHashMap<>() : registrations;
            updated.put(position, new Registration<>(position, owner));
            return updated;
        });
    }

    void unregister(Position position, T owner) {
        long cellKey = cellKey(position);
        cells.computeIfPresent(cellKey, (key, registrations) -> {
            registrations.computeIfPresent(position, (registeredPosition, registration) ->
                    registration.owner() == owner ? null : registration
            );
            return registrations.isEmpty() ? null : registrations;
        });
    }

    boolean containsWithin(int centerX, int centerY, int centerZ, int radius) {
        int safeRadius = Math.max(0, radius);
        double radiusSquared = (double) safeRadius * safeRadius;
        CellBounds bounds = bounds(centerX, centerZ, safeRadius);

        if (shouldScanAllCells(bounds)) {
            for (Map<Position, Registration<T>> registrations : cells.values()) {
                if (containsWithin(
                        registrations,
                        centerX,
                        centerY,
                        centerZ,
                        radiusSquared
                )) {
                    return true;
                }
            }
            return false;
        }

        for (int cellX = bounds.minX(); cellX <= bounds.maxX(); cellX++) {
            for (int cellZ = bounds.minZ(); cellZ <= bounds.maxZ(); cellZ++) {
                Map<Position, Registration<T>> registrations =
                        cells.get(cellKey(cellX, cellZ));
                if (registrations != null
                        && containsWithin(
                                registrations,
                                centerX,
                                centerY,
                                centerZ,
                                radiusSquared
                        )) {
                    return true;
                }
            }
        }
        return false;
    }

    Registration<T> findClosest(
            int centerX,
            int centerY,
            int centerZ,
            int radius,
            Predicate<Registration<T>> filter
    ) {
        int safeRadius = Math.max(0, radius);
        double radiusSquared = (double) safeRadius * safeRadius;
        CellBounds bounds = bounds(centerX, centerZ, safeRadius);
        Registration<T> closest = null;
        double closestDistance = Double.MAX_VALUE;

        if (shouldScanAllCells(bounds)) {
            for (Map<Position, Registration<T>> registrations : cells.values()) {
                for (Registration<T> registration : registrations.values()) {
                    double distance = registration.position().distanceSquared(
                            centerX, centerY, centerZ
                    );
                    if (distance <= radiusSquared
                            && distance < closestDistance
                            && filter.test(registration)) {
                        closest = registration;
                        closestDistance = distance;
                    }
                }
            }
            return closest;
        }

        for (int cellX = bounds.minX(); cellX <= bounds.maxX(); cellX++) {
            for (int cellZ = bounds.minZ(); cellZ <= bounds.maxZ(); cellZ++) {
                Map<Position, Registration<T>> registrations =
                        cells.get(cellKey(cellX, cellZ));
                if (registrations == null) {
                    continue;
                }
                for (Registration<T> registration : registrations.values()) {
                    double distance = registration.position().distanceSquared(
                            centerX, centerY, centerZ
                    );
                    if (distance <= radiusSquared
                            && distance < closestDistance
                            && filter.test(registration)) {
                        closest = registration;
                        closestDistance = distance;
                    }
                }
            }
        }
        return closest;
    }

    void remove(Registration<T> registration) {
        long cellKey = cellKey(registration.position());
        cells.computeIfPresent(cellKey, (key, registrations) -> {
            registrations.remove(registration.position(), registration);
            return registrations.isEmpty() ? null : registrations;
        });
    }

    int size() {
        return cells.values().stream().mapToInt(Map::size).sum();
    }

    private boolean shouldScanAllCells(CellBounds bounds) {
        long queryCellCount =
                ((long) bounds.maxX() - bounds.minX() + 1)
                        * ((long) bounds.maxZ() - bounds.minZ() + 1);
        return queryCellCount > Math.max(16L, (long) cells.size() * 4L);
    }

    private static <T> boolean containsWithin(
            Map<Position, Registration<T>> registrations,
            int centerX,
            int centerY,
            int centerZ,
            double radiusSquared
    ) {
        for (Registration<T> registration : registrations.values()) {
            if (registration.position().distanceSquared(
                    centerX, centerY, centerZ
            ) <= radiusSquared) {
                return true;
            }
        }
        return false;
    }

    private static CellBounds bounds(int centerX, int centerZ, int radius) {
        return new CellBounds(
                cellCoordinate((long) centerX - radius),
                cellCoordinate((long) centerX + radius),
                cellCoordinate((long) centerZ - radius),
                cellCoordinate((long) centerZ + radius)
        );
    }

    private static int cellCoordinate(long blockCoordinate) {
        return (int) Math.floorDiv(blockCoordinate, CELL_SIZE);
    }

    private static long cellKey(Position position) {
        return cellKey(
                Math.floorDiv(position.x(), CELL_SIZE),
                Math.floorDiv(position.z(), CELL_SIZE)
        );
    }

    private static long cellKey(int cellX, int cellZ) {
        return (cellX & 0xffffffffL) | ((long) cellZ << 32);
    }

    static final class Registration<T> {
        private final Position position;
        private final T owner;

        Registration(Position position, T owner) {
            this.position = position;
            this.owner = owner;
        }

        Position position() {
            return position;
        }

        T owner() {
            return owner;
        }
    }

    private record CellBounds(int minX, int maxX, int minZ, int maxZ) {
    }

    record Position(int x, int y, int z) {
        double distanceSquared(int otherX, int otherY, int otherZ) {
            long deltaX = (long) x - otherX;
            long deltaY = (long) y - otherY;
            long deltaZ = (long) z - otherZ;
            return (double) deltaX * deltaX
                    + (double) deltaY * deltaY
                    + (double) deltaZ * deltaZ;
        }
    }
}
