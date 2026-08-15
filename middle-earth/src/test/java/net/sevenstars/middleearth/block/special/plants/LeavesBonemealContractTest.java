package net.sevenstars.middleearth.block.special.plants;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class LeavesBonemealContractTest {
    private static final Path PLANTS = Path.of(
            "src/main/java/net/sevenstars/middleearth/block/special/plants"
    );
    private static final Path WOOD_SETS = Path.of(
            "src/main/java/net/sevenstars/middleearth/block/registration/"
                    + "WoodBlockSets.java"
    );

    @Test
    void leafVariantsUseTheVanillaBonemealContractAndPreserveState() throws IOException {
        String mallorn = Files.readString(PLANTS.resolve("ModLeavesBlock.java"));
        String holly = Files.readString(PLANTS.resolve("BerryHollyLeavesBlock.java"));
        String woodSets = Files.readString(WOOD_SETS);

        for (String source : new String[]{mallorn, holly}) {
            assertTrue(source.contains("implements BonemealableBlock"));
            assertTrue(source.contains("isValidBonemealTarget("));
            assertTrue(source.contains("isBonemealSuccess("));
            assertTrue(source.contains("performBonemeal("));
            assertTrue(source.contains("copySharedProperties(state,"));
            assertFalse(source.contains("useItemOn("));
            assertFalse(source.contains("Items.BONE_MEAL"));
        }

        assertTrue(mallorn.contains("source.getProperties()"));
        assertTrue(mallorn.contains("target.hasProperty(property)"));
        assertTrue(mallorn.contains(
                "target.setValue(property, source.getValue(property))"
        ));
        assertTrue(mallorn.contains(
                "ModNatureBlocks.FLOWERING_MALLORN_LEAVES.defaultBlockState()"
        ));
        assertTrue(holly.contains(
                "ModNatureBlocks.BERRY_HOLLY_LEAVES.defaultBlockState()"
        ));
        assertTrue(mallorn.contains("Block.UPDATE_ALL"));
        assertTrue(holly.contains("Block.UPDATE_ALL"));
        assertTrue(woodSets.contains("set.setName.equals(\"holly\")"));
        assertTrue(woodSets.contains("new BerryHollyLeavesBlock(0.01F, settings)"));
    }
}
