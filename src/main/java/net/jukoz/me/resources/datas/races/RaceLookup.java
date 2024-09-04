package net.jukoz.me.resources.datas.races;

import net.jukoz.me.utils.IdentifierUtil;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RaceLookup {
    private static HashMap<Identifier, Race> knowRaces = new HashMap<>();

    public static void addRace(Race race){
        knowRaces.put(race.getId(), race);
    }

    public static List<Race> getRacesFromString(List<String> races) {
        List<Race> fetchedRaces = new ArrayList<>();
        for(String race : races){
            Identifier raceId = IdentifierUtil.getIdentifierFromString(race);
            Race foundRace = knowRaces.get(raceId);
            fetchedRaces.add(foundRace);
        }
        return fetchedRaces;
    }

    public static Race getRaceFromString(String race) {
        Identifier raceId = IdentifierUtil.getIdentifierFromString(race);
        Race foundRace = knowRaces.get(raceId);
        return foundRace;
    }
}
