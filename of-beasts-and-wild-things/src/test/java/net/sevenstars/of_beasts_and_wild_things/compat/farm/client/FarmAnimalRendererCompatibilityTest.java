package net.sevenstars.of_beasts_and_wild_things.compat.farm.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class FarmAnimalRendererCompatibilityTest {
    private static final String RENDER_DESCRIPTOR_SUFFIX =
            "FFLcom/mojang/blaze3d/vertex/PoseStack;"
                    + "Lnet/minecraft/client/renderer/MultiBufferSource;I)V";

    @Test
    void chickenRendererBypassesInjectedParentRenderBridge() throws IOException {
        assertVariantRenderer(
                "net/sevenstars/of_beasts_and_wild_things/compat/farm/client/VariantChickenRenderer",
                "net/minecraft/world/entity/animal/Chicken",
                "net/minecraft/client/renderer/entity/ChickenRenderer"
        );
    }

    @Test
    void cowRendererBypassesInjectedParentRenderBridge() throws IOException {
        assertVariantRenderer(
                "net/sevenstars/of_beasts_and_wild_things/compat/farm/client/VariantCowRenderer",
                "net/minecraft/world/entity/animal/Cow",
                "net/minecraft/client/renderer/entity/CowRenderer"
        );
    }

    @Test
    void pigRendererOwnsItsModelAndBypassesInjectedParentRenderBridge() throws IOException {
        assertVariantRenderer(
                "net/sevenstars/of_beasts_and_wild_things/compat/farm/client/VariantPigRenderer",
                "net/minecraft/world/entity/animal/Pig",
                "net/minecraft/client/renderer/entity/PigRenderer"
        );
    }

    @Test
    void baseBridgeCallsLivingEntityRendererDirectly() throws IOException {
        assertBaseBridge("net/sevenstars/of_beasts_and_wild_things/mixin/client/MobRendererBaseMixin");
    }

    private static void assertVariantRenderer(
            String className,
            String entityClass,
            String parentRenderer
    ) throws IOException {
        List<Invocation> calls = invocations(
                className,
                "render",
                "(L" + entityClass + ";" + RENDER_DESCRIPTOR_SUFFIX
        );

        assertTrue(calls.stream().anyMatch(call ->
                call.opcode() == Opcodes.INVOKEINTERFACE
                        && call.owner().equals(
                                "net/sevenstars/of_beasts_and_wild_things/compat/farm/client/"
                                        + "VanillaFarmAnimalRendererBridge"
                        )
                        && call.name().equals("wildThings$renderBase")
        ));
        assertFalse(calls.stream().anyMatch(call ->
                call.opcode() == Opcodes.INVOKESPECIAL
                        && call.owner().equals(parentRenderer)
                        && call.name().equals("render")
        ));
    }

    private static void assertBaseBridge(String className) throws IOException {
        List<Invocation> calls = invocations(
                className,
                "wildThings$renderBase",
                "(Lnet/minecraft/world/entity/LivingEntity;" + RENDER_DESCRIPTOR_SUFFIX
        );

        assertTrue(calls.stream().anyMatch(call ->
                call.opcode() == Opcodes.INVOKESPECIAL
                        && call.owner().equals("net/minecraft/client/renderer/entity/LivingEntityRenderer")
                        && call.name().equals("render")
        ));
    }

    private static List<Invocation> invocations(
            String className,
            String methodName,
            String methodDescriptor
    ) throws IOException {
        String resource = "/" + className + ".class";
        try (InputStream input = FarmAnimalRendererCompatibilityTest.class.getResourceAsStream(resource)) {
            assertNotNull(input, resource);
            List<Invocation> calls = new ArrayList<>();
            new ClassReader(input).accept(new ClassVisitor(Opcodes.ASM9) {
                @Override
                public MethodVisitor visitMethod(
                        int access,
                        String name,
                        String descriptor,
                        String signature,
                        String[] exceptions
                ) {
                    if (!methodName.equals(name) || !methodDescriptor.equals(descriptor)) {
                        return null;
                    }
                    return new MethodVisitor(Opcodes.ASM9) {
                        @Override
                        public void visitMethodInsn(
                                int opcode,
                                String owner,
                                String invokedName,
                                String invokedDescriptor,
                                boolean isInterface
                        ) {
                            calls.add(new Invocation(opcode, owner, invokedName, invokedDescriptor));
                        }
                    };
                }
            }, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
            return calls;
        }
    }

    private record Invocation(int opcode, String owner, String name, String descriptor) {
    }
}
