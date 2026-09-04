package net.sevenstars.middleearth.gui.onboarding.onboarding_faction;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.World;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.factions.FactionOld;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class OnboardingFactionScreenService {
    /*
    private List<FactionOld> factions = new ArrayList<>();
    public OnboardingFactionScreenService(World world){
        DynamicRegistryManager registryManager = world.getRegistryManager();
        this.factions = registryManager.getOrThrow(DynamicRegistriesME.FACTION).stream().toList();
    }

    public List<FactionOld> getFactionsByDisposition(DispositionType dispositionType){
        List<FactionOld> foundFactions = new ArrayList<>();
        for(FactionOld faction : factions){
            if(faction.getDisposition() == dispositionType && faction.isJoinable())
                foundFactions.add(faction);
        }
        foundFactions.sort(Comparator.comparingInt(FactionOld::getFactionSelectionOrderIndex));
        return foundFactions;
    }
     */
}
