package net.sevenstars.middleearth.gui.onboarding.onboarding_faction;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OnboardingFactionScreenService {
    private List<Faction> factions = new ArrayList<>();
    public OnboardingFactionScreenService(Level world){
        RegistryAccess registryManager = world.registryAccess();
        this.factions = registryManager.lookupOrThrow(DynamicRegistriesME.FACTION)
                .listElements()
                .map(reference -> reference.value())
                .toList();
    }

    public List<Faction> getFactionsByDisposition(DispositionType dispositionType){
        List<Faction> foundFactions = new ArrayList<>();
        for(Faction faction : factions){
            if(faction.getDisposition() == dispositionType && faction.isJoinable())
                foundFactions.add(faction);
        }
        foundFactions.sort(Comparator.comparingInt(Faction::getFactionSelectionOrderIndex));
        return foundFactions;
    }
}
