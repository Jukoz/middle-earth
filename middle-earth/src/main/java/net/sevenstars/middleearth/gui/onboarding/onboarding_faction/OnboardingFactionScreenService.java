package net.sevenstars.middleearth.gui.onboarding.onboarding_faction;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.World;
import net.sevenstars.middleearth.resources.FactionsME;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.factions.Faction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OnboardingFactionScreenService {
    private List<Faction> factions = new ArrayList<>();
    public OnboardingFactionScreenService(World world){
        DynamicRegistryManager registryManager = world.getRegistryManager();
        this.factions = registryManager.getOrThrow(FactionsME.KEY).stream().toList();
    }

    public List<Faction> getFactionsByDisposition(Disposition disposition){
        List<Faction> foundFactions = new ArrayList<>();
        for(Faction faction : factions){
            if(faction.getDisposition() == disposition)
                foundFactions.add(faction);
        }
        foundFactions.sort(Comparator.comparingInt(Faction::getFactionSelectionOrderIndex));
        return foundFactions;
    }
}
