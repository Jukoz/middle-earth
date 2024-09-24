package net.jukoz.me.resources.persistent_datas;

import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.resources.MiddleEarthFactions;
import net.jukoz.me.resources.MiddleEarthRaces;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.FactionType;
import net.jukoz.me.resources.datas.RaceType;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.resources.datas.races.Race;
import net.jukoz.me.resources.datas.races.RaceLookup;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PlayerData {
    private AffiliationData affiliationData;
    private Identifier race;
    private BlockPos overworldSpawnCoordinates;

    public PlayerData(){
        this.affiliationData = null;
    }
    public void setRace(Identifier raceId){
        this.race = raceId;
    }
    public Identifier getRace(){
        return this.race;
    }

    public void setAffiliationData(AffiliationData affiliationData){
        this.affiliationData = affiliationData;
    }

    public boolean hasAffilition(){
        return affiliationData != null;
    }

    public Race getRace(World world){
        return world.getRegistryManager().get(MiddleEarthRaces.RACE_KEY).get(this.race);
    }
    public Faction getFaction(World world) throws FactionIdentifierException{
        if(!hasAffilition())
            return null;
        Faction faction = FactionLookup.getFactionById(world, affiliationData.faction);
        if(faction.getFactionType() == FactionType.SUBFACTION){
            Identifier parentFactionIdentifier = faction.getParentFactionId();
            if(parentFactionIdentifier == null){
                LoggerUtil.logError(faction.getName() + " is said to be a subfaction, but does not have a parent faction, returning the obtained faction by default.");
                return faction;
            }
            faction = FactionLookup.getFactionById(world, parentFactionIdentifier);
        }
        return faction;
    }
    public Faction getSubfaction(World world) throws FactionIdentifierException{
        if(!hasAffilition())
            return null;
        Faction faction = FactionLookup.getFactionById(world, affiliationData.faction);
        if(faction.getFactionType() == FactionType.FACTION)
            return null;
        return faction;
    }

    public Faction getCurrentFaction(World world) throws FactionIdentifierException {
        if(!hasAffilition())
            return null;
        return FactionLookup.getFactionById(world, affiliationData.faction);
    }
    public Alignment getCurrentAlignment() {
        if(!hasAffilition())
            return null;
        return affiliationData.alignment;
    }
    public Identifier getCurrentFactionId() {
        if(!hasAffilition())
            return null;
        return affiliationData.faction;
    }

    public Identifier getCurrentSpawnId(){
        if(!hasAffilition())
            return null;
        return affiliationData.spawnId;
    }

    public RaceType getRaceType(World world){
        Race foundRace = getRace(world);
        if(race == null || foundRace == null)
            return RaceType.NONE;
        return foundRace.getRaceType();
    }

    public Vec3d getSpawnMiddleEarthCoordinate(World world){
        if(!hasAffilition())
            return null;
        return affiliationData.getSpawnMiddleEarthCoordinate(world);
    }

    @Override
    public String toString() {
        String text = "";
        if(hasAffilition())
            text += affiliationData+"\n";
        if(race != null)
            text += "Race="+race+"\n";
        if(overworldSpawnCoordinates != null)
            text += "Overworld="+overworldSpawnCoordinates+"\n";

        if(!text.equals(""))
            return text;
        else
            return "No Data";
    }

    public void setOverworldSpawn(BlockPos overworldSpawnCoordinate) {
        this.overworldSpawnCoordinates = overworldSpawnCoordinate;
    }
    public BlockPos getOverworldSpawnCoordinates() {
        return overworldSpawnCoordinates;
    }

    public void clearData() {
        this.affiliationData = null;
        this.overworldSpawnCoordinates = null;
    }

    public boolean setSpawnMiddleEarthId(World world, Identifier foundId) throws FactionIdentifierException {
        if(hasAffilition()){
            if(FactionLookup.getFactionById(world,affiliationData.faction).getSpawnData().getSpawnList().containsKey(foundId)){
                affiliationData.spawnId = foundId;
                return true;
            }
        }
        return false;
    }
}
