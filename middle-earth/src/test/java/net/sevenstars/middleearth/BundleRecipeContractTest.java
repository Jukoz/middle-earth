package net.sevenstars.middleearth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BundleRecipeContractTest {
    @Test
    void packagedDyesUseTheMatchingColorAndComponentPreservingSerializer() throws IOException {
        try (ZipFile jar = new ZipFile(TestArtifacts.playerJar().toFile())) {
            for (String color : List.of("yellow", "brown", "green", "blue", "gray", "light_gray",
                    "white", "lime", "light_blue", "red", "black")) {
                JsonObject recipe = json(jar, "data/middle-earth/recipe/" + color + "_bundle.json");
                assertEquals("middle-earth:bundle_dye", recipe.get("type").getAsString());
                assertEquals("middle-earth:" + color + "_bundle",
                        recipe.getAsJsonObject("result").get("id").getAsString());
                var ingredients = recipe.getAsJsonArray("ingredients");
                assertEquals(2, ingredients.size());
                assertEquals("minecraft:bundle", ingredients.get(0).getAsJsonObject().get("item").getAsString());
                assertEquals("minecraft:" + color + "_dye",
                        ingredients.get(1).getAsJsonObject().get("item").getAsString());
            }
            JsonObject plain = json(jar, "data/minecraft/recipe/bundle.json");
            assertEquals("minecraft:leather", plain.getAsJsonObject("key").getAsJsonObject("L")
                    .get("item").getAsString());
            assertNull(jar.getEntry("data/middle-earth/recipe/bundle.json"), "Duplicate plain-bundle recipe");
        }
    }

    @Test
    void assemblingUsesMinecraftComponentPreservingItemConversion() throws IOException {
        List<String> calls = new ArrayList<>();
        try (InputStream input = getClass().getResourceAsStream(
                "/net/sevenstars/middleearth/recipe/BundleDyeRecipe.class")) {
            assertNotNull(input);
            new ClassReader(input).accept(new ClassVisitor(Opcodes.ASM9) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor,
                                                 String signature, String[] exceptions) {
                    if (!name.equals("assemble") || (access & Opcodes.ACC_BRIDGE) != 0) return null;
                    return new MethodVisitor(Opcodes.ASM9) {
                        @Override
                        public void visitMethodInsn(int opcode, String owner, String name,
                                                    String descriptor, boolean isInterface) {
                            calls.add(owner + "." + name + descriptor);
                        }
                    };
                }
            }, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        }
        assertTrue(calls.contains("net/minecraft/world/item/ItemStack.transmuteCopy"
                + "(Lnet/minecraft/world/level/ItemLike;I)Lnet/minecraft/world/item/ItemStack;"));
    }

    private static JsonObject json(ZipFile jar, String path) throws IOException {
        ZipEntry entry = jar.getEntry(path);
        assertNotNull(entry, path);
        try (var reader = new InputStreamReader(jar.getInputStream(entry), StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }
}
