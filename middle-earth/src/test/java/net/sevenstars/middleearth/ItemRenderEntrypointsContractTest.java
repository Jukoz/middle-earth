package net.sevenstars.middleearth;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ItemRenderEntrypointsContractTest {
    private static final String ITEM_RENDERER = "net/minecraft/client/renderer/entity/ItemRenderer";
    private static final String GET_MODEL = "(Lnet/minecraft/world/item/ItemStack;"
            + "Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;I)"
            + "Lnet/minecraft/client/resources/model/BakedModel;";

    @Test
    void contextSpecificMixinsMatchMinecraftCallSites() throws IOException {
        assertCallSite("GuiGraphicsMixin", "net/minecraft/client/gui/GuiGraphics",
                "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;"
                        + "Lnet/minecraft/world/item/ItemStack;IIII)V");
        assertCallSite("ItemEntityRendererMixin", "net/minecraft/client/renderer/entity/ItemEntityRenderer",
                "render(Lnet/minecraft/world/entity/item/ItemEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;"
                        + "Lnet/minecraft/client/renderer/MultiBufferSource;I)V");
        assertCallSite("ItemRendererMixin", ITEM_RENDERER,
                "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;"
                        + "Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;"
                        + "Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V");
    }

    private static void assertCallSite(String mixin, String targetClass, String targetMethod) throws IOException {
        List<String> selectors = new ArrayList<>();
        readClass("net/sevenstars/middleearth/mixin/client/" + mixin,
                new ClassVisitor(Opcodes.ASM9) {
                    @Override
                    public MethodVisitor visitMethod(int access, String name, String descriptor,
                                                     String signature, String[] exceptions) {
                        return new MethodVisitor(Opcodes.ASM9) {
                            @Override
                            public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                                if (!descriptor.endsWith("/ModifyExpressionValue;")) return null;
                                return new AnnotationVisitor(Opcodes.ASM9) {
                                    @Override
                                    public AnnotationVisitor visitArray(String name) {
                                        if (!name.equals("method")) return null;
                                        return new AnnotationVisitor(Opcodes.ASM9) {
                                            @Override
                                            public void visit(String name, Object value) {
                                                selectors.add((String) value);
                                            }
                                        };
                                    }
                                };
                            }
                        };
                    }
                });
        assertEquals(List.of(targetMethod), selectors,
                "Model selection must stay at context-specific call sites, never ItemRenderer.getModel");

        List<String> calls = new ArrayList<>();
        readClass(targetClass, new ClassVisitor(Opcodes.ASM9) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor,
                                             String signature, String[] exceptions) {
                if (!(name + descriptor).equals(targetMethod)) return null;
                return new MethodVisitor(Opcodes.ASM9) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name,
                                                String descriptor, boolean isInterface) {
                        if (owner.equals(ITEM_RENDERER) && name.equals("getModel") && descriptor.equals(GET_MODEL)) {
                            calls.add(name);
                        }
                    }
                };
            }
        });
        assertEquals(1, calls.size(), targetClass + "." + targetMethod);
    }

    private static void readClass(String name, ClassVisitor visitor) throws IOException {
        try (InputStream input = ItemRenderEntrypointsContractTest.class.getResourceAsStream("/" + name + ".class")) {
            assertNotNull(input, name);
            new ClassReader(input).accept(visitor, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        }
    }
}
