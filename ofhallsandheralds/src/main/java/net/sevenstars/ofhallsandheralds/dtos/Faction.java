package net.sevenstars.ofhallsandheralds.dtos;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;

import java.util.List;
import java.util.Optional;

public class Faction {
    public static final Codec<Faction> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        // Core
        Codec.BOOL.fieldOf("joinable").forGetter(Faction::isJoinable),
        RegistryKey.createCodec(DynamicRegistriesHH.FACTION_PALETTE).optionalFieldOf("palette").forGetter(Faction::getPalette),
        // Relation
        Codec.BOOL.optionalFieldOf("isParent").forGetter(Faction::getOptionalIsParent),
        RegistryKey.createCodec(DynamicRegistriesHH.FACTION).optionalFieldOf("parent_faction").forGetter(Faction::getOptionalParentFaction),
        // Customization
        RegistryKey.createCodec(DynamicRegistriesHH.BANNER).fieldOf("banner").forGetter(Faction::getBanner),
        Codec.list(RegistryKey.createCodec(DynamicRegistriesHH.SPAWN)).fieldOf("spawns").forGetter(Faction::getSpawnData),
        // Events
        Codec.list(Codec.STRING).fieldOf("command_join").forGetter(Faction::getJoinCommands),
        Codec.list(Codec.STRING).fieldOf("command_leave").forGetter(Faction::getLeaveCommands)
    ).apply(instance, Faction::new));

    // Core
    private boolean isJoinable;
    private RegistryKey<FactionPalette> palette;
    // Relation
    private boolean isParent;
    private RegistryKey<Faction> parentFaction;
    // Customization
    private RegistryKey<Banner> banner;
    private List<RegistryKey<Spawn>> spawns;
    // Events
    private List<String> joinCommands;
    private List<String> leaveCommands;

    public Faction(boolean isJoinable, Optional<RegistryKey<FactionPalette>> palette, Optional<Boolean> isParent, Optional<RegistryKey<Faction>> parentFaction, RegistryKey<Banner> banner, List<RegistryKey<Spawn>> spawnData, List<String> commandJoins, List<String> commandLeave) {
        this.isJoinable = isJoinable;
        this.palette = palette.orElse(null);
        this.isParent = isParent.orElse(false);
        this.parentFaction = parentFaction.orElse(null);

        this.banner = banner;
        this.spawns = spawnData;

        this.joinCommands = commandJoins;
        this.leaveCommands = commandLeave;
    }

    public Faction(boolean isJoinable, RegistryKey<FactionPalette>  palette, RegistryKey<Banner> banner, List<RegistryKey<Spawn>> spawns, List<String> commandJoins, List<String> commandLeave) {
        this.isJoinable = isJoinable;
        this.palette = palette;
        this.banner = banner;
        this.spawns = spawns;

        this.joinCommands = commandJoins;
        this.leaveCommands = commandLeave;
    }

    public Faction isParent(){
        this.isParent = true;
        this.parentFaction = null;
        return this;
    }

    public Faction withParentFaction(RegistryKey<Faction> parentFaction){
        this.parentFaction = parentFaction;
        this.isParent = false;
        return this;
    }

    public boolean isJoinable() {
        return isJoinable;
    }

    private Optional<RegistryKey<FactionPalette>> getPalette() {
        return Optional.ofNullable(palette);
    }

    public Optional<Boolean> getOptionalIsParent(){
        return isParent ? Optional.of(true) : Optional.empty();
    }

    public Optional<RegistryKey<Faction>> getOptionalParentFaction(){
        return Optional.ofNullable(parentFaction);
    }

    public RegistryKey<Banner> getBanner(){
        return this.banner;
    }

    public List<RegistryKey<Spawn>> getSpawnData(){
        return spawns;
    }

    private List<String> getLeaveCommands() {
        return leaveCommands;
    }

    private List<String> getJoinCommands() {
        return joinCommands;
    }
}
