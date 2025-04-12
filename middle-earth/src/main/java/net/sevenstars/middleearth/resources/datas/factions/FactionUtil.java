package net.sevenstars.middleearth.resources.datas.factions;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.commands.CommandUtils;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.exceptions.IdenticalFactionException;
import net.sevenstars.middleearth.exceptions.NoFactionException;
import net.sevenstars.middleearth.exceptions.SpawnIdentifierException;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;
import net.sevenstars.middleearth.utils.ModColors;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class FactionUtil {


    public static boolean updateFaction(ServerPlayerEntity player, @Nullable Faction faction, @Nullable Identifier spawnId) throws IdenticalFactionException, SpawnIdentifierException, FactionIdentifierException, NoFactionException {
        if(!assertUpdateFactionValues(player, faction, spawnId))
            return false;

        Faction previousFaction = PlayerDataService.getPlayerFaction(player, player.getWorld());

        // [CLEAR] If the next faction is null
        if(faction == null){
            return clearFaction(player);
        }

        // [REPLACE] If previous faction is not null and next faction is not null
        if(previousFaction != null){
            sendOnLeaveCommand(player, previousFaction);
            // Send leaving message to affected player
            MutableText targetText = Text.translatable("event.me.leave.faction.success", previousFaction.getFullName());
            player.sendMessage(targetText.withColor(ModColors.WARNING.color));
        }

        // [JOIN] Add new affiliation data
        PlayerDataService.setNewFactionInformation(player, player.getWorld(), faction.getId(), spawnId);
        sendOnJoinCommand(player, faction);

        // Send join message to affected player
        MutableText targetText = Text.translatable("event.me.join.faction.success", faction.getFullName());
        player.sendMessage(targetText.withColor(ModColors.SUCCESS.color));

        sendOnFactionJoinMessage(player);
        return true;
    }

    private static boolean assertUpdateFactionValues(ServerPlayerEntity player, Faction faction, Identifier spawnId) throws IdenticalFactionException, SpawnIdentifierException {
        // Verify player
        if(player == null) return false;

        // Verify faction
        // Fetch player datas
        Faction previousFaction = PlayerDataService.getPlayerFaction(player, player.getWorld());

        if(previousFaction == null)
            return true;

        // If there is no faction update, return true
        if(previousFaction.getId() == faction.getId() || spawnId == PlayerDataService.getPlayerSpawnData(player, player.getWorld()).getIdentifier()) {
            throw new IdenticalFactionException();
        };

        // Verify spawnId
        if(getSpawnId(faction, spawnId) == null)
            return false;

        return true;
    }

    private static Identifier getSpawnId(Faction faction, Identifier spawnId) throws SpawnIdentifierException {
        SpawnDataHandler spawnDataHandler = faction.getSpawnData();
        if(spawnDataHandler != null){
            if(spawnId == null)
                spawnId = spawnDataHandler.getDefaultSpawn();

            boolean spawnExistInFaction = spawnDataHandler.getAllSpawnIdentifiers().contains(spawnId);

            if(spawnId == null || !spawnExistInFaction){
                throw new SpawnIdentifierException();
            }
        }
        return spawnId;
    }

    private static void sendOnJoinCommand(ServerPlayerEntity player, Faction faction) {
        if(player == null || faction == null) return;

        Optional<List<String>> joinCommand = faction.getJoinCommands();
        if(joinCommand.isEmpty()) return;

        List<String> commands = joinCommand.orElse(null);

        if(commands.isEmpty()) return;
        CommandUtils.sendAllCommands(player, commands);
    }

    private static void sendOnLeaveCommand(ServerPlayerEntity player, Faction previousFaction) {
        if(player == null || previousFaction == null) return;

        Optional<List<String>> leaveCommand = previousFaction.getLeaveCommands();
        if(leaveCommand.isEmpty()) return;

        List<String> commands = leaveCommand.orElse(null);

        if(commands.isEmpty()) return;
        CommandUtils.sendAllCommands(player, commands);
    }

    public static void sendOnFactionJoinMessage(PlayerEntity player) {
        Faction faction = PlayerDataService.getPlayerFaction(player, player.getWorld());
        if(faction == null){
            MiddleEarth.LOGGER.logError("Couldn't find faction");
            return;
        }


        MutableText targetText = Text.translatable("event.me.join.faction.success", faction.getFullName());
        ((ServerPlayerEntity) player).networkHandler.sendPacket(
            new TitleS2CPacket(Text.of(""))
        );
        ((ServerPlayerEntity) player).networkHandler.sendPacket(
                new SubtitleS2CPacket(targetText.withColor(ModColors.SUCCESS.color))
        );

    }

    public static boolean clearFaction(ServerPlayerEntity player) throws FactionIdentifierException, NoFactionException {
        Faction faction = PlayerDataService.getPlayerFaction(player, player.getWorld());
        if(faction == null)
            throw new NoFactionException();
        if(PlayerDataService.clearPlayerData(player)){
            sendOnLeaveCommand(player, faction);
            MutableText targetText = Text.translatable("event.me.leave.faction.success", faction.getFullName());
            player.sendMessage(targetText.withColor(ModColors.WARNING.color));
            return true;
        }
        return false;
    }

    /**
     * Simply used to explore spawn points.
     * @param target player to teleport
     * @param spawnId spawn identifier the player need to teleport to
     * @return if the process was a success or not
     */
    public static boolean forceTeleportToSpawnMiddleEarthId(ServerPlayerEntity target, Identifier spawnId){
        BlockPos spawnBlockPos = null;
        spawnBlockPos = getSpawnBlockPos(target.getWorld(), spawnId);
        if(spawnBlockPos == null)
            return false;
        ModDimensions.teleportPlayerToMe(target, new Vec3d(spawnBlockPos.getX(), spawnBlockPos.getY(), spawnBlockPos.getZ()), false, false);
        return true;
    }

    public static BlockPos getSpawnBlockPos(World world, Identifier spawnId) {
        BlockPos spawnBlockPos = null;
        for(Faction faction: FactionLookup.getAllFactions(world)){
            SpawnDataHandler spawnDataHandler = faction.getSpawnData();
            if(spawnDataHandler != null)
                spawnBlockPos = spawnDataHandler.getSpawnBlockPos(spawnId);
            if(spawnBlockPos != null) {
                return spawnBlockPos;
            }

            if(faction.getSubFactions() != null){
                for(Identifier subfactionId : faction.getSubFactions()){
                    try {
                        Faction subFaction = null;
                        subFaction = FactionLookup.getFactionById(world, subfactionId);
                        SpawnDataHandler subFacspawnDataHandler = subFaction.getSpawnData();
                        if(subFacspawnDataHandler != null)
                            spawnBlockPos = subFacspawnDataHandler.getSpawnBlockPos(spawnId);
                        if(spawnBlockPos != null) {
                            return spawnBlockPos;
                        }
                    } catch (FactionIdentifierException e) {
                        return null;
                    }
                }
            }
        }
        return  null;
    }
}
