package net.sevenstars.middleearth.resources.persistent_datas;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.ofhallsandheralds.dtos.faction.Faction;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;

public class PlayerData {
    /*
    private RegistryEntry<Faction> faction;
    private Identifier spawn;
    private Identifier race;
    private BlockPos posOrigin;
    private Identifier dimensionOrigin;

    private int delversFearCountInSeconds = 0;

    public NbtCompound createNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        if(faction != null)
            nbtCompound.putString("faction", faction.getIdAsString());
        if(spawn != null)
            nbtCompound.putString("spawn", spawn.toString());
        if(race != null)
            nbtCompound.putString("race", race.toString());
        if(posOrigin != null)
            nbtCompound.putIntArray("origin_pos", new int[]{posOrigin.getX(), posOrigin.getY(), posOrigin.getZ()});
        if(dimensionOrigin != null)
            nbtCompound.putString("dimensionOrigin", dimensionOrigin.toString());

        nbtCompound.putInt("delversFearCountInSeconds", delversFearCountInSeconds);

        return nbtCompound;
    }
    public PlayerData() {}

    public PlayerData(NbtCompound nbtCompound) {
        if (nbtCompound.getString("faction").isPresent()) {
            Identifier id = MiddleEarth.ofId(nbtCompound.getString("faction").get());
            faction = RegistryKey.of(DynamicRegistriesHH.FACTION, id);
        }
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

        if(nbtCompound.getInt("delversFearCountInSeconds").isPresent())
            nbtCompound.putInt("delversFearCountInSeconds", delversFearCountInSeconds);
        else
            delversFearCountInSeconds = 0;
    }
    public boolean assignNewFactionInformation(RegistryKey<Faction> faction, Identifier spawnId){
        this.faction = faction;
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
    public RegistryKey<Faction> getFaction(){
        return this.faction;
    }
    public Identifier getRace(){
        return this.race;
    }
    public Identifier getSpawn(){
        return this.spawn;
    }

    public Identifier getDimensionOrigin(){
        return this.dimensionOrigin;
    }
    public BlockPos getOriginPos(){
        return this.posOrigin;
    }


    public int getDelversFearCountInSeconds(){
        return this.delversFearCountInSeconds;
    }
    public void addToDelversFearCountInSeconds(){
         this.delversFearCountInSeconds += 1;
    }
    public void resetDelversFearCount() {
        this.delversFearCountInSeconds = 0;
    }

     */
}
