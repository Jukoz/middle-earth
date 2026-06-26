package net.sevenstars.middleearth.entity.npcs.data;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.entity.TrackedDataHandlerRegistryME;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityTextureData;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.CombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.MeleeCombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.RangedCombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.runtime.CombatArchetypeRuntimeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.runtime.MeleeCombatArchetypeRuntimeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.runtime.RangedCombatArchetypeRuntimeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.utils.CombatArchetypeDataUtil;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;

import java.util.Optional;

public class NpcEntityDataHolder {
    NpcEntity owner;

    private static final TrackedData<String> CATEGORY;
    private static final TrackedData<String> FACTION_ID;
    private static final TrackedData<String> NPC_DATA_ID;
    private static final TrackedData<Long> INITIALIZATION_TICK;
    private static final TrackedData<NpcEntityTextureData> TEXTURE_DATA;
    private static final TrackedData<Optional<BlockPos>> STRUCTURE_MANAGER_POS;
    private static final TrackedData<Optional<BlockPos>> BED_POS;
    private static final TrackedData<Boolean> FIGHTING;
    private static final TrackedData<Boolean> BLOCKING;
    private static final TrackedData<NbtCompound> COMBAT_RUNTIME_DATA;

    public NpcEntityDataHolder(NpcEntity npcEntity) {
        owner = npcEntity;
    }

    public static void initialize() {
        // This calls the { static } block
    }

    public void initDataTracker(DataTracker.Builder builder) {
        builder.add(INITIALIZATION_TICK, 0l);
        builder.add(CATEGORY, "");
        builder.add(FACTION_ID, "");
        builder.add(NPC_DATA_ID, "");
        builder.add(TEXTURE_DATA, new NpcEntityTextureData());
        builder.add(STRUCTURE_MANAGER_POS, Optional.empty());
        builder.add(BED_POS, Optional.empty());
        builder.add(FIGHTING, false);
        builder.add(BLOCKING, false);
        builder.add(COMBAT_RUNTIME_DATA, new NbtCompound());
        assignStructureManager(null);
        assignBed(null);
    }

    public void assignStructureManager(StructureManagerBlockEntity blockEntity) {
        if(owner == null) return;
        boolean hasStructure = blockEntity != null;
        if(hasStructure) this.owner.getDataTracker().set(STRUCTURE_MANAGER_POS, Optional.of(blockEntity.getPos()));
    }

    public void assignBed(BedBlockEntity bedBlockEntity) {
        if(owner == null ) return;
        if(bedBlockEntity != null) {
            this.owner.getDataTracker().set(BED_POS, Optional.of(bedBlockEntity.getPos()));
        }
    }

    public void writeEntityData(WriteView view) {
        DataTracker dataTracker = owner.getDataTracker();

        view.put("NpcDataId", Codec.STRING, dataTracker.get(NPC_DATA_ID));
        view.put("FactionId", Codec.STRING, dataTracker.get(FACTION_ID));
        view.put("EntityCategory", Codec.STRING, dataTracker.get(CATEGORY));
        view.put("NpcTextureData", NpcEntityTextureData.CODEC, dataTracker.get(TEXTURE_DATA));
        view.put("StructureManagerPos", BlockPos.CODEC, dataTracker.get(STRUCTURE_MANAGER_POS).orElse(new BlockPos(0, 0, 0)));
        view.put("BedPos", BlockPos.CODEC, dataTracker.get(BED_POS).orElse(new BlockPos(0, 0, 0)));
        view.put("InitializationTick", Codec.LONG, dataTracker.get(INITIALIZATION_TICK));
        view.put("CombatRuntimeData", NbtCompound.CODEC, dataTracker.get(COMBAT_RUNTIME_DATA));
    }

    public void readEntityData(ReadView view) {
        DataTracker dataTracker = owner.getDataTracker();

        view.read("NpcDataId", Codec.STRING)
                .ifPresent(this::setNpcData);
        view.read("FactionId", Identifier.CODEC)
                .ifPresent(this::setFactionId);
        view.read("EntityCategory", Codec.STRING)
                .ifPresent(this::setNpcCategory);
        view.read("NpcTextureData", NpcEntityTextureData.CODEC)
                .ifPresent(this::setNpcTextureData);
        view.read("StructureManagerPos", BlockPos.CODEC)
                .ifPresent(this::setStructureManagerPos);
        view.read("BedPos", BlockPos.CODEC)
                .ifPresent(this::setBedPos);
        view.read("InitializationTick", Codec.LONG)
                .ifPresent(x -> dataTracker.set(INITIALIZATION_TICK, x));
        view.read("CombatRuntimeData", NbtCompound.CODEC)
                .ifPresent(this::setCombatRuntimeData);

        owner.tryToInitializeData();

        if(owner.isAiDisabled() && getNpcDataId() != null){
            owner.initializeForCurrentNpcData();
        }
    }

    public void setFactionId(Identifier identifier) {
        if(identifier == null)
            return;
        this.owner.getDataTracker().set(FACTION_ID, identifier.toString());
    }

    public Identifier getFactionId() {
        return Identifier.of(this.owner.getDataTracker().get(FACTION_ID));
    }

    public void setNpcData(Identifier id) {
        setNpcData(id.toString());
    }

    public void setNpcData(NpcData npcData) {
        setNpcData(npcData.getId().toString());
    }

    public void setNpcData(String value) {
        if(value == null || value.isEmpty())
            return;
        this.owner.getDataTracker().set(NPC_DATA_ID, value);
    }

    public Identifier getNpcDataId() {
        return Identifier.of(this.owner.getDataTracker().get(NPC_DATA_ID));
    }

    public NpcData getNpcData() {
        var id = getNpcDataId();
        if(id == null)
            return null;
        return owner.getWorld().getRegistryManager().getOrThrow(DynamicRegistriesME.NPC).get(id);
    }

    protected RaceType getRaceType(){
        if(getNpcDataId() == null || getNpcData() == null)
            return null;
        Race race = RaceLookup.getRace(owner.getWorld(), getNpcData().getRace());
        if(race == null)
            return null;
        return race.getRaceType();
    }

    public void setNpcCategory(String entityCategory) {
        if(entityCategory.isEmpty())
            return;
        this.owner.getDataTracker().set(CATEGORY, entityCategory);
    }

    public void setNpcTextureData(NpcEntityTextureData npcEntityTextureData) {
        if(npcEntityTextureData == null)
            return;
        this.owner.getDataTracker().set(TEXTURE_DATA, npcEntityTextureData);
    }

    public void setStructureManagerPos(BlockPos blockPos) {
        if(blockPos == null) this.owner.getDataTracker().set(STRUCTURE_MANAGER_POS, Optional.empty());
        else this.owner.getDataTracker().set(STRUCTURE_MANAGER_POS, Optional.of(blockPos));
    }

    public void setBedPos(BlockPos blockPos) {
        if(blockPos == null) this.owner.getDataTracker().set(BED_POS, Optional.empty());
        else this.owner.getDataTracker().set(BED_POS, Optional.of(blockPos));
    }

    public void setCombatRuntimeData(CombatArchetypeRuntimeData runtimeData){
        setCombatRuntimeData(runtimeData.getNbt());
    }

    public void setCombatRuntimeData(NbtCompound nbt){
        this.owner.getDataTracker().set(COMBAT_RUNTIME_DATA, nbt);
    }
    public CombatArchetypeRuntimeData getCombatRuntimeData(){
        NbtCompound nbt = this.owner.getDataTracker().get(COMBAT_RUNTIME_DATA);
        return buildCombatRuntimeData(nbt);
    }
    public CombatArchetypeRuntimeData buildCombatRuntimeData(NbtCompound nbt){
        CombatArchetypeData dataFound = CombatArchetypeDataUtil.create(nbt.getCompoundOrEmpty("data"));
        if(dataFound == null)
            return null;
        CombatArchetypeRuntimeData combatArchetype = switch (dataFound.getArchetype()){
            case MELEE -> new MeleeCombatArchetypeRuntimeData((MeleeCombatArchetypeData) dataFound);
            case RANGED -> new RangedCombatArchetypeRuntimeData((RangedCombatArchetypeData) dataFound);
            case HYBRID -> null;
            case SHIELDED -> null;
            case LANCERS -> null;
            case SENTINEL -> null;
            case BRAWLER -> null;
            case BEAST_RIDER -> null;
        };

        return combatArchetype;
    }

    public void setFighting(boolean state){
        this.owner.getDataTracker().set(FIGHTING, state);
    }

    public boolean getFighting(){
        return this.owner.getDataTracker().get(FIGHTING);
    }

    public void setInitializationTick(long tick) {
        this.owner.getDataTracker().set(INITIALIZATION_TICK, tick);
    }

    public Long getInitializationTick() {
        return this.owner.getDataTracker().get(INITIALIZATION_TICK);
    }
    public void setBlockingState(boolean state) {
        var blockingItem = this.owner.getBlockingItem();
        if(blockingItem == null){
            return;
        }
        this.owner.getDataTracker().set(BLOCKING, state);
        this.owner.getOffHandStack().getUseAction();
    }
    public boolean isBlocking() {
        return this.owner.getDataTracker().get(BLOCKING);
    }
    public EntityCategories getNpcCategory() {
        var category = this.owner.getDataTracker().get(CATEGORY);
        if(category == null || category.isEmpty())
            return null;
        return EntityCategories.valueOf(category);
    }
    public BlockPos getStructureManagerPos() {
        return this.owner.getDataTracker().get(STRUCTURE_MANAGER_POS).orElse(null);
        /*if(this.owner.getBrain().getOptionalMemory(MemoryModulesME.STRUCTURE_MANAGER_HOST_POS).isPresent()){
            return this.owner.getBrain().getOptionalMemory(MemoryModulesME.STRUCTURE_MANAGER_HOST_POS).get();
        }
        return null;*/
    }

    public BlockPos getAssignedBedPos() {
        return this.owner.getDataTracker().get(BED_POS).orElse(null);
        /*if(this.owner.getBrain().getOptionalMemory(MemoryModulesME.ASSIGNED_BED_POS).isPresent()){
            return this.owner.getBrain().getOptionalMemory(MemoryModulesME.ASSIGNED_BED_POS).get();
        }
        return null;*/
    }

    public NpcEntityTextureData getNpcTextureData() {
        return this.owner.getDataTracker().get(TEXTURE_DATA);
    }

    static {
        INITIALIZATION_TICK = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistryME.INITIALIZATION_TICK);
        FACTION_ID = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistryME.FACTION_ID);
        NPC_DATA_ID = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistryME.NPC_DATA_ID);
        CATEGORY = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistryME.CATEGORY);
        TEXTURE_DATA = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistryME.NPC_ENTITY_TEXTURE_DATA);
        STRUCTURE_MANAGER_POS = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_POS);
        BED_POS = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_POS);
        FIGHTING = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        BLOCKING = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        COMBAT_RUNTIME_DATA = DataTracker.registerData(NpcEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
    }
}
