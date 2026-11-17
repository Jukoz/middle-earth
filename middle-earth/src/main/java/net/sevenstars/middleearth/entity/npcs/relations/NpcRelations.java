package net.sevenstars.middleearth.entity.npcs.relations;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.entity.EntityTypeTagsME;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.common.AffinityLevel;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.FactionCreatures;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import org.jetbrains.annotations.Nullable;

// The single place that decides whether an npc treats someone as an enemy, a friend or neither
public class NpcRelations {

    public static Relation of(NpcEntity npc, Entity other) {
        if(other == npc)
            return Relation.ALLY;
        if(involvesPrisoner(npc, other))
            return Relation.NEUTRAL;
        if(npc.hasGrudgeAgainst(other) || other == npc.getHuntTarget())
            return Relation.ENEMY;
        return standing(npc, other);
    }

    // How the npc sees someone by faction and kind. Its own grudges are left out, except those against a mount's rider
    public static Relation standing(NpcEntity npc, Entity other) {
        if(other == npc)
            return Relation.ALLY;
        if(involvesPrisoner(npc, other))
            return Relation.NEUTRAL;
        // A mount takes the side of its rider
        Entity rider = other.getControllingPassenger();
        if(rider != null && rider != other)
            return of(npc, rider);
        if(other instanceof NpcEntity otherNpc)
            return ofFaction(npc.getFaction(), otherNpc.getFactionIdentifier());
        if(other instanceof PlayerEntity player)
            return ofPlayer(npc, player);
        return ofCreature(npc, other);
    }

    // Prisoners aren't hostile to anyone and no one is hostile to them
    private static boolean involvesPrisoner(NpcEntity npc, Entity other) {
        return npc.isPrisoner() || other instanceof NpcEntity otherNpc && otherNpc.isPrisoner();
    }

    private static Relation ofPlayer(NpcEntity npc, PlayerEntity player) {
        if(player.getEntityWorld().isClient() || !player.canTakeDamage())
            return Relation.NEUTRAL;
        PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
        Identifier playerFaction = playerData == null ? null : playerData.getFaction();
        Faction faction = npc.getFaction();
        // Good factions leave a player who hasn't joined a faction alone
        if(playerFaction == null)
            return faction != null && faction.getDisposition() == DispositionType.GOOD ? Relation.NEUTRAL : Relation.ENEMY;
        return ofFaction(faction, playerFaction);
    }

    private static Relation ofFaction(@Nullable Faction faction, @Nullable Identifier otherFaction) {
        if(faction == null || otherFaction == null)
            return Relation.ENEMY;
        if(faction.getId().equals(otherFaction))
            return Relation.ALLY;
        AffinityLevel affinity = faction.affinityToward(otherFaction);
        return affinity == null ? Relation.NEUTRAL : Relation.of(affinity);
    }

    // The faction's creature lists take priority, then the rules every npc shares
    private static Relation ofCreature(NpcEntity npc, Entity other) {
        // A wild cave troll attacks every npc, whoever keeps trolls
        if(other instanceof CaveTrollEntity caveTroll && !caveTroll.isTame())
            return Relation.ENEMY;
        EntityType<?> type = other.getType();
        Faction faction = npc.getFaction();
        if(faction != null) {
            FactionCreatures creatures = faction.getCreatures();
            if(creatures.isFriendly(type))
                return Relation.FRIEND;
            if(creatures.isHostile(type))
                return Relation.ENEMY;
        }
        // Not every monster can be listed in a tag, so also go by class
        if(type.isIn(EntityTypeTagsME.NPC_ENEMIES) || other instanceof HostileEntity)
            return Relation.ENEMY;
        return Relation.NEUTRAL;
    }
}
