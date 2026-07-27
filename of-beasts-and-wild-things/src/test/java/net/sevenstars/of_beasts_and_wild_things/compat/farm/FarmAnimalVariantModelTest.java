package net.sevenstars.of_beasts_and_wild_things.compat.farm;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class FarmAnimalVariantModelTest {
    private static final String MODEL_CLASS =
            "/net/sevenstars/of_beasts_and_wild_things/compat/farm/FarmAnimalVariantModel.class";
    private static final String CLIENT_STATE_CLASS =
            "/net/sevenstars/of_beasts_and_wild_things/compat/farm/FarmAnimalVariantClientState.class";
    private static final String MOB_VARIANT_MIXIN_CLASS =
            "/net/sevenstars/of_beasts_and_wild_things/mixin/farm/MobFarmAnimalVariantMixin.class";
    private static final String SNAPSHOT_CLASS =
            "/net/sevenstars/of_beasts_and_wild_things/compat/farm/FarmAnimalVariants$Snapshot.class";
    private static final String MODEL_INTERNAL_NAME =
            "net/sevenstars/of_beasts_and_wild_things/compat/farm/FarmAnimalVariantModel";
    private static final String KIND_INTERNAL_NAME =
            "net/sevenstars/of_beasts_and_wild_things/compat/farm/FarmAnimalKind";
    private static final String PARSE_DESCRIPTOR =
            "(L" + KIND_INTERNAL_NAME + ";Ljava/lang/String;)L" + MODEL_INTERNAL_NAME + ";";

    @Test
    void exposesEveryModelSupportedByTheUpstreamVariantSchema() throws IOException {
        Set<String> enumConstants = new HashSet<>();
        visitClass(MODEL_CLASS, new ClassVisitor(Opcodes.ASM9) {
            @Override
            public org.objectweb.asm.FieldVisitor visitField(
                    int access,
                    String name,
                    String descriptor,
                    String signature,
                    Object value
            ) {
                if ((access & Opcodes.ACC_ENUM) != 0) {
                    enumConstants.add(name);
                }
                return null;
            }
        });

        assertTrue(enumConstants.containsAll(Set.of("NORMAL", "COLD", "WARM")));
    }

    @Test
    void keepsWarmModelParsingRestrictedToCows() throws IOException {
        Set<String> referencedFields = new HashSet<>();
        boolean[] parseMethodFound = {false};
        visitClass(MODEL_CLASS, new ClassVisitor(Opcodes.ASM9) {
            @Override
            public MethodVisitor visitMethod(
                    int access,
                    String name,
                    String descriptor,
                    String signature,
                    String[] exceptions
            ) {
                if (!name.equals("parse") || !descriptor.equals(PARSE_DESCRIPTOR)) {
                    return null;
                }
                parseMethodFound[0] = true;
                return new MethodVisitor(Opcodes.ASM9) {
                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                        if (opcode == Opcodes.GETSTATIC) {
                            referencedFields.add(owner + "." + name);
                        }
                    }
                };
            }
        });

        assertTrue(parseMethodFound[0], PARSE_DESCRIPTOR);
        assertTrue(referencedFields.contains(MODEL_INTERNAL_NAME + ".WARM"));
        assertTrue(referencedFields.contains(KIND_INTERNAL_NAME + ".COW"));
    }

    @Test
    void textureLookupDoesNotAllocateResourceLocationsPerRender() throws IOException {
        boolean[] createsResourceLocation = {false};
        visitClass(CLIENT_STATE_CLASS, new ClassVisitor(Opcodes.ASM9) {
            @Override
            public MethodVisitor visitMethod(
                    int access,
                    String name,
                    String descriptor,
                    String signature,
                    String[] exceptions
            ) {
                if (!name.equals("texture")) {
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
                        if (owner.equals("net/minecraft/resources/ResourceLocation")
                                && name.equals("fromNamespaceAndPath")) {
                            createsResourceLocation[0] = true;
                        }
                    }
                };
            }
        });

        assertFalse(createsResourceLocation[0]);
    }

    @Test
    void entityVariantGetterUsesNativeResourceLocationData() throws IOException {
        Set<String> resourceLocationCalls = new HashSet<>();
        visitClass(MOB_VARIANT_MIXIN_CLASS, new ClassVisitor(Opcodes.ASM9) {
            @Override
            public MethodVisitor visitMethod(
                    int access,
                    String name,
                    String descriptor,
                    String signature,
                    String[] exceptions
            ) {
                if (!name.equals("wildThings$getFarmVariant")) {
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
                        if (owner.equals("net/minecraft/resources/ResourceLocation")) {
                            resourceLocationCalls.add(name);
                        }
                    }
                };
            }
        });

        assertFalse(resourceLocationCalls.contains("tryParse"));
        assertFalse(resourceLocationCalls.contains("parse"));
    }

    @Test
    void baselineDefinitionsSelectTheUpstreamModelsAndAssetNames() throws IOException {
        Set<String> referencedFields = new HashSet<>();
        Set<String> concatenationRecipes = new HashSet<>();
        visitClass(SNAPSHOT_CLASS, new ClassVisitor(Opcodes.ASM9) {
            @Override
            public MethodVisitor visitMethod(
                    int access,
                    String name,
                    String descriptor,
                    String signature,
                    String[] exceptions
            ) {
                if (!name.equals("baseline") && !name.equals("baselineDefinition")) {
                    return null;
                }
                return new MethodVisitor(Opcodes.ASM9) {
                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                        if (opcode == Opcodes.GETSTATIC) {
                            referencedFields.add(owner + "." + name);
                        }
                    }

                    @Override
                    public void visitInvokeDynamicInsn(
                            String name,
                            String descriptor,
                            org.objectweb.asm.Handle bootstrapMethodHandle,
                            Object... bootstrapMethodArguments
                    ) {
                        for (Object argument : bootstrapMethodArguments) {
                            if (argument instanceof String recipe) {
                                concatenationRecipes.add(recipe);
                            }
                        }
                    }
                };
            }
        });

        assertTrue(referencedFields.contains(MODEL_INTERNAL_NAME + ".NORMAL"));
        assertTrue(referencedFields.contains(MODEL_INTERNAL_NAME + ".COLD"));
        assertTrue(referencedFields.contains(MODEL_INTERNAL_NAME + ".WARM"));
        assertTrue(referencedFields.contains(KIND_INTERNAL_NAME + ".COW"));
        assertTrue(concatenationRecipes.contains("entity/\u0001/temperate_\u0001"));
        assertTrue(concatenationRecipes.contains("entity/\u0001/\u0001_\u0001"));
    }

    private static void visitClass(String resource, ClassVisitor visitor) throws IOException {
        try (InputStream input = FarmAnimalVariantModelTest.class.getResourceAsStream(resource)) {
            assertNotNull(input, resource);
            new ClassReader(input).accept(visitor, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        }
    }
}
