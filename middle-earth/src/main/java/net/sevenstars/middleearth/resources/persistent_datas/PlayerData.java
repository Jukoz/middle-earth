package net.sevenstars.middleearth.resources.persistent_datas;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class PlayerData {
    private Identifier faction;
    private Identifier spawn;
    private Identifier race;
    private BlockPos posOrigin;
    private Identifier dimensionOrigin;

    public NbtCompound createNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        if(faction != null)
            nbtCompound.putString("faction", faction.toString());
        if(spawn != null)
            nbtCompound.putString("spawn", spawn.toString());
        if(race != null)
            nbtCompound.putString("race", race.toString());
        if(posOrigin != null)
            nbtCompound.putIntArray("origin_pos", new int[]{posOrigin.getX(), posOrigin.getY(), posOrigin.getZ()});
        if(dimensionOrigin != null)
            nbtCompound.putString("dimensionOrigin", dimensionOrigin.toString());
        return nbtCompound;
    }
    public PlayerData() {}

    public PlayerData(NbtCompound nbtCompound) {
        if(nbtCompound.getString("faction").isPresent())
            faction = Identifier.of(nbtCompound.getString("faction").get());
        if(nbtCompound.getString("spawn").isPresent())
            spawn = Identifier.of(nbtCompound.getString("spawn").get());
        if(nbtCompound.getString("race").isPresent())
            race = Identifier.of(nbtCompound.getString("race").get());
        if(nbtCompound.getIntArray("posOrigin").isPresent()){
            var intArray = nbtCompound.getIntArray("posOrigin").get();
            posOrigin = new BlockPos(intArray[0], intArray[1], intArray[2]);
        }
        if(nbtCompound.getString("dimensionOrigin").isPresent())
            dimensionOrigin = Identifier.of(nbtCompound.getString("dimensionOrigin").get());
    }
    public boolean assignNewFactionInformation(Identifier factionId, Identifier spawnId){
        this.faction = factionId;
        this.spawn = spawnId;
        return true;
    }

    public boolean assignNewRace(Identifier raceId){
        this.race = raceId;
        return true;
    }

    public boolean assignNewOrigin(Identifier dimensionOrigin, BlockPos newBlockPos){
        this.dimensionOrigin = dimensionOrigin;
        this.posOrigin = newBlockPos;
        return true;
    }
    public Identifier getFaction(){
        return this.faction;
    }
    public Identifier getRace(){
        return this.race;
    }
    public Identifier getSpawn(){
        return this.spawn;
    }

    public Identifier getDimensionOrigin(){
        return this.spawn;
    }
    public BlockPos getOriginPos(){
        return this.posOrigin;
    }
    /*
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
        return world.getRegistryManager().getOrThrow(MiddleEarthRaces.KEY).get(this.race);
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
     */
}
