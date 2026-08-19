package net.sevenstars.middleearth;

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

class AttributeCompatibilityContractTest {
    @Test
    void playerAttributeSnapshotFiltersAttributesBeforeResolvingInstances() throws IOException {
        MethodCalls snapshot = readMethodCalls(
                "net/sevenstars/middleearth/resources/datas/attributes/AttributePoolElement.class",
                "createAttributeNbtListFromPlayer"
        );

        int supportCheck = snapshot.indexOf(
                "net/minecraft/world/entity/ai/attributes/AttributeSupplier",
                "hasAttribute"
        );
        int instanceLookup = snapshot.indexOf(
                "net/minecraft/world/entity/player/Player",
                "getAttribute"
        );
        assertTrue(supportCheck >= 0 && supportCheck < instanceLookup,
                "Unsupported registry attributes must be filtered before resolving runtime instances");
    }

    @Test
    void resetSkipsAttributesMissingFromTheEntityTypeDefaults() throws IOException {
        MethodCalls reset = readMethodCalls(
                "net/sevenstars/middleearth/resources/datas/attributes/AttributePool.class",
                "reverse"
        );

        int supportCheck = reset.indexOf(
                "net/minecraft/world/entity/ai/attributes/AttributeSupplier",
                "hasAttribute"
        );
        int instanceLookup = reset.indexOf(
                "net/minecraft/world/entity/LivingEntity",
                "getAttribute"
        );
        assertTrue(supportCheck >= 0 && supportCheck < instanceLookup,
                "Attribute reset must not touch synthetic attributes exposed by compatibility mods");
    }

    @Test
    void defaultValueLookupHasARegistryDefaultFallback() throws IOException {
        MethodCalls lookup = readMethodCalls(
                "net/sevenstars/middleearth/resources/datas/attributes/AttributePool.class",
                "getDefaultAttributeValue"
        );

        assertTrue(lookup.hasCall(
                "net/minecraft/world/entity/ai/attributes/AttributeSupplier",
                "hasAttribute"
        ));
        assertTrue(lookup.hasCall(
                "net/minecraft/world/entity/ai/attributes/Attribute",
                "getDefaultValue"
        ));
        assertTrue(lookup.hasCall(
                "net/minecraft/world/entity/ai/attributes/AttributeSupplier",
                "getBaseValue"
        ));
    }

    private static MethodCalls readMethodCalls(String resource, String methodName)
            throws IOException {
        InputStream stream = AttributeCompatibilityContractTest.class
                .getClassLoader()
                .getResourceAsStream(resource);
        if (stream == null) {
            throw new IOException("Missing compiled class " + resource);
        }
        try (stream) {
            MethodCalls result = new MethodCalls();
            new ClassReader(stream).accept(new ClassVisitor(Opcodes.ASM9) {
                @Override
                public MethodVisitor visitMethod(
                        int access,
                        String name,
                        String descriptor,
                        String signature,
                        String[] exceptions
                ) {
                    if (!name.equals(methodName)) {
                        return null;
                    }
                    return new MethodVisitor(Opcodes.ASM9) {
                        @Override
                        public void visitMethodInsn(
                                int opcode,
                                String owner,
                                String name,
                                String descriptor,
                                boolean isInterface
                        ) {
                            result.calls.add(new Call(owner, name));
                        }
                    };
                }
            }, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
            return result;
        }
    }

    private record Call(String owner, String name) {
    }

    private static final class MethodCalls {
        private final List<Call> calls = new ArrayList<>();

        private boolean hasCall(String owner, String name) {
            return indexOf(owner, name) >= 0;
        }

        private int indexOf(String owner, String name) {
            for (int index = 0; index < calls.size(); index++) {
                Call call = calls.get(index);
                if (call.owner().equals(owner) && call.name().equals(name)) {
                    return index;
                }
            }
            return -1;
        }
    }
}
