package net.sevenstars.middleearth.world;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.WorldPreset;
import net.minecraft.world.rule.GameRule;
import net.minecraft.world.rule.GameRuleCategory;
import net.minecraft.world.rule.GameRules;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ServerConfigME;
import net.sevenstars.middleearth.network.packets.server2client.PacketForceOnboardingScreen;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StartInMiddleEarth {
    public static final Identifier PRESET_ID = Identifier.of(MiddleEarth.MOD_ID, "middle_earth");
    public static final RegistryKey<WorldPreset> PRESET_KEY = RegistryKey.of(RegistryKeys.WORLD_PRESET, PRESET_ID);

    public static GameRule<Boolean> RULE;

    private static final Map<UUID, HeldPosition> awaitingPlayers = new HashMap<>();

    private record HeldPosition(RegistryKey<World> world, Vec3d pos) {}

    public static void register() {
        RULE = GameRuleBuilder.forBoolean(false)
                .category(GameRuleCategory.SPAWNING)
                .buildAndRegister(Identifier.of(MiddleEarth.MOD_ID, "start_in_middle_earth"));

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) ->
                !(entity instanceof ServerPlayerEntity player && isAwaitingOnboarding(player))
                        || source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY));

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> awaitingPlayers.remove(handler.getPlayer().getUuid()));
        ServerTickEvents.END_SERVER_TICK.register(StartInMiddleEarth::holdAwaitingPlayers);
    }

    // No server yet, the world is still being created
    public static void enableIfPreset(GameRules rules, boolean isPreset) {
        if(isPreset)
            rules.setValue(RULE, true, null);
    }

    public static boolean isEnabled(MinecraftServer server) {
        return server.getOverworld().getGameRules().getValue(RULE);
    }

    public static void onJoin(ServerPlayerEntity player) {
        MinecraftServer server = player.getEntityWorld().getServer();
        if(!isEnabled(server) || PlayerDataService.playerPassedOnboarding(player))
            return;
        awaitingPlayers.put(player.getUuid(), new HeldPosition(player.getEntityWorld().getRegistryKey(), player.getEntityPos()));
        ServerPlayNetworking.send(player, new PacketForceOnboardingScreen(ServerConfigME.DELAY_ON_TELEPORT_CONFIRMATION, player, true));
    }

    public static boolean isAwaitingOnboarding(ServerPlayerEntity player) {
        return awaitingPlayers.containsKey(player.getUuid());
    }

    private static void holdAwaitingPlayers(MinecraftServer server) {
        if(awaitingPlayers.isEmpty()) return;
        awaitingPlayers.entrySet().removeIf(entry -> {
            ServerPlayerEntity player = server.getPlayerManager().getPlayer(entry.getKey());
            HeldPosition held = entry.getValue();
            // A different dimension means they've already been teleported in
            if(player == null || !player.getEntityWorld().getRegistryKey().equals(held.world())
                    || PlayerDataService.playerPassedOnboarding(player))
                return true;
            if(player.getEntityPos().squaredDistanceTo(held.pos()) > 0.25)
                player.requestTeleport(held.pos().x, held.pos().y, held.pos().z);
            return false;
        });
    }
}
