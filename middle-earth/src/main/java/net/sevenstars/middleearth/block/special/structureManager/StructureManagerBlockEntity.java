package net.sevenstars.middleearth.block.special.structureManager;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.ModBlockEntities;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenHandler;
import net.sevenstars.middleearth.resources.StructureManagerDatasME;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNest;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StructureManagerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private static final String ID = "structure_manager";
    private Identifier structureManagerDataIdentifier;
    private StructureManagerData structureManagerData;
    private StructureManagerNestDataMap structureManagerNestDataMap;

    public StructureManagerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STRUCTURE_MANAGER, pos, state);
    }

    @Override
    public void setWorld(World world) {
        super.setWorld(world);
        if(world == null || world.isClient)
            return;
        Initialize();
    }

    private void Initialize() {
        MiddleEarth.LOGGER.logDebugMsg("SMBE : Initialization");
        structureManagerDataIdentifier = StructureManagerDatasME.TEMPLATE.getId();

        if(structureManagerDataIdentifier != null){
            structureManagerData = StructureManagerService.GetStructureManagerData(world, structureManagerDataIdentifier);
        } else {
            structureManagerData = StructureManagerService.GetStructureManagerData(world, structureManagerDataIdentifier);
        }

        if(structureManagerData == null){
            MiddleEarth.LOGGER.logWarn("SMBE :: Data couldn't be found for the structure manager block %s".formatted(pos.toString()));
            return;
        }
        List<StructureManagerNestData> nestDatas = new ArrayList<>();
        for(StructureSpawnNest nest : structureManagerData.getNpcSpawnNest()){
            nestDatas.add(new StructureManagerNestData(nest));
        }
        structureManagerNestDataMap = new StructureManagerNestDataMap(nestDatas);

        MiddleEarth.LOGGER.logWarn("SMBE :: Data is successfully initialized %s".formatted(pos.toString()));
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen.%s.%s".formatted(MiddleEarth.MOD_ID, ID));
    }

    @Override
    public Object getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return pos;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new StructureManagerScreenHandler(syncId, playerInventory);
    }
    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        MiddleEarth.LOGGER.logWarn("SMBE :: WriteData :: began");
        view.putString("%s.structure_manager_id".formatted(ID), structureManagerData.getId().toString());
        view.put("%s.nests".formatted(ID), StructureManagerNestDataMap.CODEC, this.structureManagerNestDataMap);
    }



    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        MiddleEarth.LOGGER.logWarn("SMBE :: ReadData :: began");

        var foundId = view.getString("%s.structure_manager_id".formatted(ID), null);
        if(foundId != null){
            structureManagerDataIdentifier = Identifier.of(foundId);
            if(world != null){
                structureManagerData = StructureManagerService.GetStructureManagerData(world, Identifier.of(foundId));
                if(structureManagerData == null)
                    return;
            }
        }

        var query = view.read("%s.nests".formatted(ID), StructureManagerNestDataMap.CODEC);
        query.ifPresent(managerNestData -> this.structureManagerNestDataMap = managerNestData);

        if(structureManagerNestDataMap != null)
            MiddleEarth.LOGGER.logDebugMsg("SMBE :: ReadData :: %s".formatted(this.structureManagerNestDataMap.getDatas().size()));
        else
            MiddleEarth.LOGGER.logDebugMsg("SMBE :: ReadData :: Couldn't read structure manager nest data");
    }

    public void alertDeath(LivingEntity entity) {
        if(entity.getWorld().isClient)
            return;

        this.structureManagerNestDataMap.alertDeath(entity);
    }

    private void Respawn(StructureManagerNestData nestData){
        if(this.world == null)
                return;
        MiddleEarth.LOGGER.logDebugMsg("SMBE :: Respawn :: Respawning all entities for %s".formatted(nestData.getId()));

        StructureSpawnNest nest = structureManagerData.getNpcSpawnNest(nestData.getId());
        StructureSpawnNestPool pool = nest.getRandomPool();

        int npcAmount = pool.getEntityAmount();
        for(int i = 0; i < npcAmount; i ++){

            NpcEntity entityToAdd = StructureManagerService.SpawnEntity(world, pool.getNpcIdentifier(), pool.getFactionIdentifier(), pos.add(nest.getBlockPosOffset()), 10);
            for(StructureManagerNestData data : structureManagerNestDataMap.getDatas()){
                data.addEntity(entityToAdd);
            }
            entityToAdd.setStructureManagerHost(this);
        }
    }

    private void tick(ServerWorld world, BlockPos blockPos, BlockState blockState) {
        for(StructureManagerNestData data : structureManagerNestDataMap.getDatas()){
            if(data.canRespawn(world)){
                MiddleEarth.LOGGER.logDebugMsg("SMBE :: AlertDeath :: Attempting to respawn all entities");
                Respawn(data);
            }
        }
    }

    public static void tick(ServerWorld world, BlockPos blockPos, BlockState blockState, StructureManagerBlockEntity entity) {
        entity.tick(world, blockPos, blockState);

    }
}
