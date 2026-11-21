package net.sevenstars.middleearth.commands.custom;

import net.minecraft.server.MinecraftServer;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

class WorldFreeze {
    private static final Set<UUID> SINGLES = new HashSet<>();
    private static boolean froze;

    static void beginSingle(MinecraftServer server, UUID builder) {
        SINGLES.add(builder);
        pause(server);
    }

    static boolean endSingle(MinecraftServer server, UUID builder) {
        boolean ended = SINGLES.remove(builder);
        pause(server);
        return ended;
    }

    // The world is frozen while pieces are edited, so nothing grows, flows or burns. Someone else's freeze is left alone
    // A frozen game isn't saved, so loaded structures are frozen again on server start
    static void pause(MinecraftServer server) {
        boolean editing = !SINGLES.isEmpty() || SessionStore.anySpaced();
        if(editing && !server.getTickManager().isFrozen()) {
            server.getTickManager().setFrozen(true);
            froze = true;
            StructureEditing.log("the world stands still while pieces are edited");
        } else if(!editing && froze) {
            server.getTickManager().setFrozen(false);
            froze = false;
            StructureEditing.log("the world runs again");
        }
    }

    static void clear() {
        SINGLES.clear();
        froze = false;
    }
}
