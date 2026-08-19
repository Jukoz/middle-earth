package net.sevenstars.middleearth.block.special.structureManager.features;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpatialIdentityIndexTest {
    @Test
    void oldOwnerCannotUnregisterSamePositionReplacement() {
        SpatialIdentityIndex<Object> index = new SpatialIdentityIndex<>();
        SpatialIdentityIndex.Position position =
                new SpatialIdentityIndex.Position(64, 70, -1);
        Object oldOwner = new Object();
        Object replacement = new Object();

        index.register(position, oldOwner);
        index.register(position, replacement);
        index.unregister(position, oldOwner);

        SpatialIdentityIndex.Registration<Object> closest =
                index.findClosest(
                        position.x(), position.y(), position.z(),
                        0,
                        registration -> true
                );
        assertNotNull(closest);
        assertSame(replacement, closest.owner());
        assertEquals(1, index.size());
    }

    @Test
    void exactRegistrationRemovalCannotDeleteReplacement() {
        SpatialIdentityIndex<Object> index = new SpatialIdentityIndex<>();
        SpatialIdentityIndex.Position position =
                new SpatialIdentityIndex.Position(0, 0, 0);
        Object oldOwner = new Object();
        Object replacement = new Object();

        index.register(position, oldOwner);
        SpatialIdentityIndex.Registration<Object> oldRegistration =
                index.findClosest(
                        position.x(), position.y(), position.z(),
                        0,
                        registration -> true
                );
        assertNotNull(oldRegistration);
        index.register(position, replacement);
        index.remove(oldRegistration);

        SpatialIdentityIndex.Registration<Object> closest =
                index.findClosest(
                        position.x(), position.y(), position.z(),
                        0,
                        registration -> true
                );
        assertNotNull(closest);
        assertSame(replacement, closest.owner());
    }

    @Test
    void radiusChecksRemainExactAndTreatNegativeAsZero() {
        SpatialIdentityIndex<Object> index = new SpatialIdentityIndex<>();
        SpatialIdentityIndex.Position origin =
                new SpatialIdentityIndex.Position(0, 0, 0);
        index.register(new SpatialIdentityIndex.Position(3, 4, 0), new Object());

        assertTrue(index.containsWithin(origin.x(), origin.y(), origin.z(), 5));
        assertFalse(index.containsWithin(origin.x(), origin.y(), origin.z(), 4));
        assertFalse(index.containsWithin(origin.x(), origin.y(), origin.z(), -1));

        index.register(origin, new Object());
        assertTrue(index.containsWithin(origin.x(), origin.y(), origin.z(), -1));
    }

    @Test
    void concurrentReadsAndWritesCompleteWithoutLosingFinalOwners()
            throws Exception {
        SpatialIdentityIndex<Object> index = new SpatialIdentityIndex<>();
        int workerCount = 8;
        int positionsPerWorker = 256;
        ExecutorService executor = Executors.newFixedThreadPool(workerCount);
        CountDownLatch start = new CountDownLatch(1);
        List<Object> finalOwners = new ArrayList<>(workerCount);

        for (int worker = 0; worker < workerCount; worker++) {
            int workerId = worker;
            Object finalOwner = new Object();
            finalOwners.add(finalOwner);
            executor.submit(() -> {
                start.await();
                for (int offset = 0; offset < positionsPerWorker; offset++) {
                    SpatialIdentityIndex.Position position =
                            new SpatialIdentityIndex.Position(
                                    workerId * 1024 + offset,
                                    64,
                                    -workerId * 1024 - offset
                            );
                    Object oldOwner = new Object();
                    index.register(position, oldOwner);
                    index.containsWithin(
                            position.x(), position.y(), position.z(), 64
                    );
                    index.register(position, finalOwner);
                    index.unregister(position, oldOwner);
                }
                return null;
            });
        }

        start.countDown();
        executor.shutdown();
        assertTrue(executor.awaitTermination(20, TimeUnit.SECONDS));
        assertEquals(workerCount * positionsPerWorker, index.size());

        for (int worker = 0; worker < workerCount; worker++) {
            SpatialIdentityIndex.Position lastPosition =
                    new SpatialIdentityIndex.Position(
                            worker * 1024 + positionsPerWorker - 1,
                            64,
                            -worker * 1024 - positionsPerWorker + 1
                    );
            SpatialIdentityIndex.Registration<Object> closest =
                    index.findClosest(
                            lastPosition.x(),
                            lastPosition.y(),
                            lastPosition.z(),
                            0,
                            registration -> true
                    );
            assertNotNull(closest);
            assertSame(finalOwners.get(worker), closest.owner());
        }
    }
}
