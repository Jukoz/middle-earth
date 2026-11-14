package net.sevenstars.middleearth.dev;

// For working on npc routines: what each npc is doing over its head, its walks drawn in the world, why it does nothing,
// a record of each task it gives up with the blocks around it, and /npcpath. Off unless the game is started with
// -Dme.npcDebug=true
public class NpcDebug {
    public static final boolean ENABLED = Boolean.getBoolean("me.npcDebug");
}
