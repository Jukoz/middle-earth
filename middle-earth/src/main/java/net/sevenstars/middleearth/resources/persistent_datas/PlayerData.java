package net.sevenstars.middleearth.resources.persistent_datas;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.resources.MiddleEarthRaces;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.FactionType;
import net.sevenstars.middleearth.resources.datas.RaceType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.races.Race;
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
        this.race = null;
        this.overworldSpawnCoordinates = null;
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
                MiddleEarth.LOGGER.logError(faction.getName() + " is said to be a subfaction, but does not have a parent faction, returning the obtained faction by default.");
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
    public Disposition getCurrentDisposition() {
        if(!hasAffilition())
            return null;
        return affiliationData.disposition;
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
        this.race = null;
    }

    public boolean setSpawnMiddleEarthId(World world, Identifier foundId) throws FactionIdentifierException {
        if(hasAffilition()){
            if(FactionLookup.getFactionById(world,affiliationData.faction).getSpawnData().getAllSpawnIdentifiers().contains(foundId)){
                affiliationData.spawnId = foundId;
                return true;
            }
        }
        return false;
    }
}
