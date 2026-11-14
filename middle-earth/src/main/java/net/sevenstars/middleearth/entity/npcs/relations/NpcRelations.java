package net.sevenstars.middleearth.entity.npcs.relations;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.entity.EntityTypeTagsME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.common.AffinityLevel;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.FactionCreatures;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import org.jetbrains.annotations.Nullable;

// The one place that says who an npc counts as an enemy, a friend or neither
public class NpcRelations {

    public static Relation of(NpcEntity npc, Entity other) {
        if(other == npc)
            return Relation.ALLY;
        if(npc.hasGrudgeAgainst(other) || other == npc.getHuntTarget())
            return Relation.ENEMY;
        return standing(npc, other);
    }

    // How the npc stands with someone before anything they did to it personally
    public static Relation standing(NpcEntity npc, Entity other) {
        if(other == npc)
            return Relation.ALLY;
        // A mount is on the side of whoever rides it
        Entity rider = other.getControllingPassenger();
        if(rider != null && rider != other)
            return of(npc, rider);
        if(other instanceof NpcEntity otherNpc)
            return ofFaction(npc.getFaction(), otherNpc.getFactionIdentifier());
        if(other instanceof PlayerEntity player)
            return ofPlayer(npc, player);
        return ofCreature(npc, other);
    }

    private static Relation ofPlayer(NpcEntity npc, PlayerEntity player) {
        if(player.getWorld().isClient || !player.canTakeDamage())
            return Relation.NEUTRAL;
        PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
        Identifier playerFaction = playerData == null ? null : playerData.getFaction();
        Faction faction = npc.getFaction();
        // Good folk leave a wanderer who has sworn to no one alone
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

    // The faction's own word first, then what every npc thinks
    private static Relation ofCreature(NpcEntity npc, Entity other) {
        EntityType<?> type = other.getType();
        Faction faction = npc.getFaction();
        if(faction != null) {
            FactionCreatures creatures = faction.getCreatures();
            if(creatures.isFriendly(type))
                return Relation.FRIEND;
            if(creatures.isHostile(type))
                return Relation.ENEMY;
        }
        // Every monster can't be listed in a tag, so those are known by their class
        if(type.isIn(EntityTypeTagsME.NPC_ENEMIES) || other instanceof HostileEntity)
            return Relation.ENEMY;
        return Relation.NEUTRAL;
    }
}
