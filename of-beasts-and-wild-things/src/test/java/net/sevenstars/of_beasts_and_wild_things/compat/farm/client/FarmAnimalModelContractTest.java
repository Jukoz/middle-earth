package net.sevenstars.of_beasts_and_wild_things.compat.farm.client;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class FarmAnimalModelContractTest {
    private static final String MODELS_CLASS =
            "/net/sevenstars/of_beasts_and_wild_things/compat/farm/client/FarmAnimalVariantModels.class";
    private static final String MODELS_INTERNAL_NAME =
            "net/sevenstars/of_beasts_and_wild_things/compat/farm/client/FarmAnimalVariantModels";
    private static final String LAYER_DEFINITION =
            "net/minecraft/client/model/geom/builders/LayerDefinition";
    private static final String CUBE_LIST_BUILDER =
            "net/minecraft/client/model/geom/builders/CubeListBuilder";
    private static final List<String> NORMAL_COW_TEXTURES = List.of(
            "choppy_black",
            "choppy_brown",
            "choppy_red",
            "milky_black",
            "milky_brown",
            "milky_red",
            "patchy_black",
            "patchy_brown",
            "patchy_red"
    );
    private static final List<String> COLD_COW_TEXTURES = List.of(
            "black_bull",
            "brown_bull",
            "red_bull",
            "tan_bull"
    );
    private static final List<String> PIG_TEXTURES = List.of(
            "dark",
            "dark_white_segment",
            "pale_spots"
    );
    private static final List<String> CHICKEN_TEXTURES = List.of("brown", "rooster");
    private static final List<String> VANILLA_VARIANT_TEXTURES = List.of(
            "chicken/cold_chicken",
            "chicken/temperate_chicken",
            "chicken/warm_chicken",
            "cow/cold_cow",
            "cow/temperate_cow",
            "cow/warm_cow",
            "pig/cold_pig",
            "pig/temperate_pig",
            "pig/warm_pig"
    );

    @Test
    void backportedLayersUseTheUpstreamTextureSheetDimensions() throws IOException {
        assertLayerTextureSize("normalCowLayer", 64, 64);
        assertLayerTextureSize("coldCowLayer", 64, 64);
        assertLayerTextureSize("warmCowLayer", 64, 64);
        assertLayerTextureSize("normalPigLayer", 64, 64);
        assertLayerTextureSize("coldPigLayer", 64, 64);
        assertLayerTextureSize("coldChickenLayer", 64, 32);
    }

    @Test
    void coldModelsReuseTheCorrectedModernBaseMeshes() throws IOException {
        assertTrue(invocations("coldCowLayer").stream().anyMatch(call ->
                call.owner().equals(MODELS_INTERNAL_NAME)
                        && call.name().equals("normalCowMesh")
        ));
        assertTrue(invocations("coldPigLayer").stream().anyMatch(call ->
                call.owner().equals(MODELS_INTERNAL_NAME)
                        && call.name().equals("normalPigMesh")
        ));
        assertTrue(invocations("normalCowMesh").stream().anyMatch(call ->
                call.owner().equals(CUBE_LIST_BUILDER) && call.name().equals("mirror")
        ));
        assertTrue(invocations("normalPigMesh").stream().anyMatch(call ->
                call.owner().equals(CUBE_LIST_BUILDER) && call.name().equals("mirror")
        ));
    }

    @Test
    void bundledTexturesMatchTheirSelectedModelAtlases() throws IOException {
        for (String texture : NORMAL_COW_TEXTURES) {
            assertTexture("/assets/wild-things/textures/entity/cow/" + texture + ".png", 64, 64);
        }
        for (String texture : COLD_COW_TEXTURES) {
            assertTexture("/assets/wild-things/textures/entity/cow/" + texture + ".png", 64, 64);
        }
        for (String texture : PIG_TEXTURES) {
            assertTexture("/assets/wild-things/textures/entity/pig/" + texture + ".png", 64, 64);
        }
        for (String texture : CHICKEN_TEXTURES) {
            assertTexture("/assets/wild-things/textures/entity/chicken/" + texture + ".png", 64, 32);
        }
        for (String texture : VANILLA_VARIANT_TEXTURES) {
            int expectedHeight = texture.startsWith("chicken/") ? 32 : 64;
            assertTexture("/assets/minecraft/textures/entity/" + texture + ".png", 64, expectedHeight);
        }
    }

    private static void assertLayerTextureSize(
            String methodName,
            int expectedWidth,
            int expectedHeight
    ) throws IOException {
        List<int[]> sizes = new ArrayList<>();
        visitMethod(methodName, new MethodVisitor(Opcodes.ASM9) {
            private final Deque<Integer> recentIntegers = new ArrayDeque<>(2);

            @Override
            public void visitInsn(int opcode) {
                if (opcode >= Opcodes.ICONST_M1 && opcode <= Opcodes.ICONST_5) {
                    remember(opcode - Opcodes.ICONST_0);
                }
            }

            @Override
            public void visitIntInsn(int opcode, int operand) {
                if (opcode == Opcodes.BIPUSH || opcode == Opcodes.SIPUSH) {
                    remember(operand);
                }
            }

            @Override
            public void visitLdcInsn(Object value) {
                if (value instanceof Integer integer) {
                    remember(integer);
                }
            }

            @Override
            public void visitMethodInsn(
                    int opcode,
                    String owner,
                    String name,
                    String descriptor,
                    boolean isInterface
            ) {
                if (owner.equals(LAYER_DEFINITION) && name.equals("create") && recentIntegers.size() == 2) {
                    Integer[] values = recentIntegers.toArray(Integer[]::new);
                    sizes.add(new int[]{values[0], values[1]});
                }
            }

            private void remember(int value) {
                if (this.recentIntegers.size() == 2) {
                    this.recentIntegers.removeFirst();
                }
                this.recentIntegers.addLast(value);
            }
        });
        assertEquals(1, sizes.size(), methodName);
        assertArrayEquals(new int[]{expectedWidth, expectedHeight}, sizes.getFirst(), methodName);
    }

    private static List<Invocation> invocations(String methodName) throws IOException {
        List<Invocation> calls = new ArrayList<>();
        visitMethod(methodName, new MethodVisitor(Opcodes.ASM9) {
            @Override
            public void visitMethodInsn(
                    int opcode,
                    String owner,
                    String name,
                    String descriptor,
                    boolean isInterface
            ) {
                calls.add(new Invocation(owner, name));
            }
        });
        return calls;
    }

    private static void visitMethod(String methodName, MethodVisitor visitor) throws IOException {
        try (InputStream input = FarmAnimalModelContractTest.class.getResourceAsStream(MODELS_CLASS)) {
            assertNotNull(input, MODELS_CLASS);
            new ClassReader(input).accept(new ClassVisitor(Opcodes.ASM9) {
                @Override
                public MethodVisitor visitMethod(
                        int access,
                        String name,
                        String descriptor,
                        String signature,
                        String[] exceptions
                ) {
                    return methodName.equals(name) ? visitor : null;
                }
            }, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        }
    }

    private static void assertTexture(String resource, int expectedWidth, int expectedHeight) throws IOException {
        try (InputStream input = FarmAnimalModelContractTest.class.getResourceAsStream(resource)) {
            assertNotNull(input, resource);
            BufferedImage image = ImageIO.read(input);
            assertNotNull(image, resource);
            assertEquals(expectedWidth, image.getWidth(), resource);
            assertEquals(expectedHeight, image.getHeight(), resource);
        }
    }

    private record Invocation(String owner, String name) {
    }
}
