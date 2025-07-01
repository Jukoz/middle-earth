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

    private StructureManagerData structureManagerData;

    private List<LivingEntity> entities = new ArrayList<>();
    private StructureManagerNestData structureManagerNestData;

    public StructureManagerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STRUCTURE_MANAGER, pos, state);
    }

    @Override
    public void setWorld(World world) {
        super.setWorld(world);
        Initialize();
        entities.clear();
    }

    private void Initialize() {
        structureManagerData = StructureManagerService.GetStructureManagerData(world, StructureManagerDatasME.TEMPLATE.getId());
        if(structureManagerData == null)
            MiddleEarth.LOGGER.logWarn("StructureManagerBlockEntity :: Data couldn't be found for the structure manager block %s".formatted(pos.toString()));
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
        if(this.structureManagerNestData != null){
            this.structureManagerNestData.setRespawnTickProgress(world.getTickManager().getStepTicks());
            view.put("%s.entities".formatted(ID), StructureManagerNestData.CODEC, this.structureManagerNestData);
            MiddleEarth.LOGGER.logDebugMsg("Write Data : %s".formatted(this.structureManagerNestData.getRespawnTickProgress()));
        }
      }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        var list = view.getTypedListView("%s.entities".formatted(ID), StructureManagerNestData.CODEC).stream().toList();
        if(!list.isEmpty())
            this.structureManagerNestData = list.getFirst();
        if(structureManagerNestData == null)
            structureManagerData = StructureManagerService.GetStructureManagerData(world, StructureManagerDatasME.TEMPLATE.getId());

        if(structureManagerNestData != null)
            MiddleEarth.LOGGER.logDebugMsg("Read Data : %s".formatted(this.structureManagerNestData.getRespawnTickProgress()));
        else
            MiddleEarth.LOGGER.logDebugMsg("Read Data : Couldn't read structure manager nest data");
    }

    public void alertDeath(LivingEntity entity) {
        if(entity.getWorld().isClient)
            return;
        this.entities.remove(entity);

        if(this.entities.isEmpty()){
            MiddleEarth.LOGGER.logDebugMsg("Attempting to respawn all entities");
            RespawnAll();
        }
    }

    private void RespawnAll(){
        if(this.world == null)
                return;
        MiddleEarth.LOGGER.logDebugMsg("Respawning all entities");

        for(int i = 0; i < 3; i ++){
            StructureSpawnNest nest = structureManagerData.getNpcSpawnNest().getFirst();
            StructureSpawnNestPool pool = nest.getNpcSpawnNestPool().getFirst();

            NpcEntity entityToAdd = StructureManagerService.SpawnEntity(world, pool.getNpcIdentifier(), pool.getFactionIdentifier(), pos.add(nest.getBlockPosOffset()), 10);
            this.entities.add(entityToAdd);
            entityToAdd.setStructureManagerHost(this);
        }
    }

    public static void tick(ServerWorld world, BlockPos blockPos, BlockState blockState, StructureManagerBlockEntity entity) {
        if(entity.entities.isEmpty()){
            entity.RespawnAll();
        }
    }

}
