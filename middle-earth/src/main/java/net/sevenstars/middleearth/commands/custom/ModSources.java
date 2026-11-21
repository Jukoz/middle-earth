package net.sevenstars.middleearth.commands.custom;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

// The mod's own resource folder, which edits are written back to when the game runs from sources
record ModSources(Path root) {
    private static final String SOURCE_RESOURCES = "src/main/resources";
    private static final String SOURCES_PROPERTY = "middleearth.structedit.sources";

    static String propertyName() {
        return SOURCES_PROPERTY;
    }

    // -Dmiddleearth.structedit.sources if set (e.g. a datapack), else the mod's sources. Null if neither
    static @Nullable ModSources find() {
        String named = System.getProperty(SOURCES_PROPERTY);
        if(named != null && !named.isBlank())
            return new ModSources(Path.of(named));
        Path resources = FabricLoader.getInstance().getGameDir().resolve("..").resolve(SOURCE_RESOURCES).normalize();
        return FabricLoader.getInstance().isDevelopmentEnvironment() && isOwnSources(resources) ? new ModSources(resources) : null;
    }

    // A project running the mod from its jar has sources that aren't the mod's
    private static boolean isOwnSources(Path resources) {
        return Files.isDirectory(resources.resolve("data").resolve(MiddleEarth.MOD_ID).resolve("structure"));
    }

    static String shown(Path file) {
        Path game = FabricLoader.getInstance().getGameDir().toAbsolutePath().normalize();
        Path project = game.resolve("..").resolve("..").normalize();
        Path whole = file.toAbsolutePath().normalize();
        return (whole.startsWith(project) ? project.relativize(whole) : whole).toString();
    }

    Path targetOf(Identifier template) {
        return root.resolve("data").resolve(template.getNamespace()).resolve("structure").resolve(template.getPath() + ".nbt");
    }

    // Replaces the mod's copy of a template with the saved one, and gives its path
    Path copyTemplate(Identifier id, Path saved) throws IOException {
        Path target = targetOf(id);
        Files.createDirectories(target.getParent());
        Files.copy(saved, target, StandardCopyOption.REPLACE_EXISTING);
        return target;
    }

    Path structureFile(Identifier id) throws IOException {
        return PiecePools.within(root, root.resolve("data").resolve(id.getNamespace()).resolve("worldgen").resolve("structure").resolve(id.getPath() + ".json"));
    }

    // False if the settings file to copy was empty
    boolean writeStructure(Path file, Path settingsFile, Identifier startPool) throws IOException, JsonParseException {
        JsonObject settings = PiecePools.GSON.fromJson(Files.readString(settingsFile), JsonObject.class);
        if(settings == null)
            return false;
        settings.addProperty(StructureAssembly.START_POOL_KEY, startPool.toString());
        Files.createDirectories(file.getParent());
        Files.writeString(file, PiecePools.GSON.toJson(settings));
        return true;
    }
}
