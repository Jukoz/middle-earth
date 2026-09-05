package net.sevenstars.middleearth.resources.datas.factions;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.exceptions.IdenticalFactionException;
import net.sevenstars.middleearth.exceptions.NoFactionException;
import net.sevenstars.middleearth.exceptions.SpawnIdentifierException;
import net.sevenstars.middleearth.utils.ColorsME;
import net.sevenstars.ofhallsandheralds.dtos.Faction;
import net.sevenstars.ofhallsandheralds.registries.services.FactionService;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FactionUtil {


    public static boolean updateFaction(ServerPlayerEntity player, @Nullable RegistryEntry<Faction> newFaction, @Nullable Identifier spawnId) throws IdenticalFactionException, SpawnIdentifierException, FactionIdentifierException, NoFactionException {
        if(!assertUpdateFactionValues(player, newFaction, spawnId))
            return false;

        Optional<RegistryEntry<Faction>> previousFaction = FactionService.getPlayerFaction(player);

        // [CLEAR] If the next faction is null
        if(newFaction == null){
            FactionService.setFactionToPlayer(player, null);
            return false;
        }

        // [REPLACE] If previous faction is not null and next faction is not null
        if(previousFaction.isPresent()){
            //sendOnLeaveCommand(player, previousFaction);
            // Send leaving message to affected player
            MutableText targetText = Text.translatable("event.%s.leave.faction.success".formatted(MiddleEarth.MOD_ID), previousFaction.get().getKey());
            player.sendMessage(targetText.withColor(ColorsME.WARNING.color));
        }

        // [JOIN] Add new affiliation data
        /*

        if(spawnId == null)
            spawnId = faction.getSpawnData().getDefaultSpawn();
         */
        FactionService.setFactionToPlayer(player, newFaction);
        //PlayerDataService.setNewFactionInformation(player, player.getWorld(), faction.getKey(), spawnId);
        //sendOnJoinCommand(player, faction);

        // Send join message to affected player
        MutableText targetText = Text.translatable("event.%s.join.faction.success".formatted(MiddleEarth.MOD_ID), newFaction.getIdAsString());
        player.sendMessage(targetText.withColor(ColorsME.SUCCESS.color));

        //sendOnFactionJoinMessage(player);
        return true;
    }

    private static boolean assertUpdateFactionValues(ServerPlayerEntity player, RegistryEntry<Faction> newFaction, Identifier spawnId) throws IdenticalFactionException, SpawnIdentifierException {
        // Verify player
        if(player == null) return false;

        // Verify faction
        // Fetch player datas
        Optional<RegistryEntry<Faction>> previousFaction = FactionService.getPlayerFaction(player);

        if(previousFaction.isEmpty())
            return true;
/*
        // If there is no faction update, return true
        if(previousFaction.get().matchesKey(newFaction.getKey().orElseThrow())) {
            SpawnData spawnData = PlayerDataService.getPlayerSpawnData(player, player.getWorld());
            if(spawnData != null && spawnId != spawnData.getIdentifier())
                throw new IdenticalFactionException();
        };

        // Verify spawnId

        if(getSpawnId(newFaction, spawnId) == null)
            return false;
 */

        return true;
    }
/*
    private static Identifier getSpawnId(FactionOld faction, Identifier spawnId) throws SpawnIdentifierException {
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

    private static void sendOnJoinCommand(ServerPlayerEntity player, FactionOld faction) {
        if(player == null || faction == null) return;

        Optional<List<String>> joinCommand = faction.getJoinCommands();
        if(joinCommand.isEmpty()) return;

        List<String> commands = joinCommand.orElse(null);

        if(commands.isEmpty()) return;
        CommandUtils.sendAllCommands(player, commands);
    }

    private static void sendOnLeaveCommand(ServerPlayerEntity player, FactionOld previousFaction) {
        if(player == null || previousFaction == null) return;

        Optional<List<String>> leaveCommand = previousFaction.getLeaveCommands();
        if(leaveCommand.isEmpty()) return;

        List<String> commands = leaveCommand.orElse(null);

        if(commands.isEmpty()) return;
        CommandUtils.sendAllCommands(player, commands);
    }

    public static void sendOnFactionJoinMessage(PlayerEntity player) {
        FactionOld faction = PlayerDataService.getPlayerFaction(player, player.getWorld());
        if(faction == null){
            MiddleEarth.LOGGER.logError("Couldn't find faction");
            return;
        }


        MutableText targetText = Text.translatable("event.%s.join.faction.success".formatted(MiddleEarth.MOD_ID), faction.getFullName());
        ((ServerPlayerEntity) player).networkHandler.sendPacket(
            new TitleS2CPacket(Text.of(""))
        );
        ((ServerPlayerEntity) player).networkHandler.sendPacket(
                new SubtitleS2CPacket(targetText.withColor(ColorsME.SUCCESS.color))
        );

    }

    public static boolean clearFaction(ServerPlayerEntity player) throws FactionIdentifierException, NoFactionException {
        FactionOld faction = PlayerDataService.getPlayerFaction(player, player.getWorld());
        if(faction == null)
            throw new NoFactionException();
        if(PlayerDataService.clearPlayerData(player)){
            sendOnLeaveCommand(player, faction);
            MutableText targetText = Text.translatable("event.%s.leave.faction.success".formatted(MiddleEarth.MOD_ID), faction.getFullName());
            player.sendMessage(targetText.withColor(ColorsME.WARNING.color));
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
    /*
    public static boolean forceTeleportToSpawnMiddleEarthId(ServerPlayerEntity target, Identifier spawnId){
        BlockPos spawnBlockPos = null;
        spawnBlockPos = getSpawnBlockPos(target.getWorld(), spawnId);
        if(spawnBlockPos == null)
            return false;
        DimensionRegistryME.teleportPlayerToMe(target, new Vec3d(spawnBlockPos.getX(), spawnBlockPos.getY(), spawnBlockPos.getZ()), false, false);
        return true;
    }

    public static BlockPos getSpawnBlockPos(World world, Identifier spawnId) {
        BlockPos spawnBlockPos = null;
        for(FactionOld faction: FactionLookup.getAllFactions(world)){
            SpawnDataHandler spawnDataHandler = faction.getSpawnData();
            if(spawnDataHandler != null)
                spawnBlockPos = spawnDataHandler.getSpawnBlockPos(spawnId);
            if(spawnBlockPos != null) {
                return spawnBlockPos;
            }

            if(faction.getSubFactions() != null){
                for(Identifier subfactionId : faction.getSubFactions()){
                    try {
                        FactionOld subFaction = null;
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
    */
}
