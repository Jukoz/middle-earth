package net.sevenstars.middleearth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class NpcProjectilePersistenceContractTest {
    @Test
    void rangedNpcsNeverCreateArrowsWithAnEmptyPickupStack() throws IOException {
        String npc = Files.readString(Path.of(
                "src/main/java/net/sevenstars/middleearth/entity/npcs/NpcEntity.java"
        ));

        assertTrue(npc.contains(
                "arrow.isEmpty() ? Items.ARROW.getDefaultInstance() : arrow"
        ));
        assertTrue(npc.contains(
                "ProjectileUtil.getMobArrow(this, ammunition, damageModifier, shotFrom)"
        ));
        assertTrue(npc.contains("return !getMainHandItem().isEmpty();"));
        assertFalse(npc.contains("return getMainHandItem() != null;"));
    }
}
