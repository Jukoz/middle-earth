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
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.ModBlockEntities;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StructureManagerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private static final String ID = "structure_manager";

    private List<LivingEntity> entities = new ArrayList<>();

    public StructureManagerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STRUCTURE_MANAGER, pos, state);
    }

    @Override
    public void setWorld(World world) {
        super.setWorld(world);
        RespawnAll();
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen." + MiddleEarth.MOD_ID + "." + ID);
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


    public void alertDeath(LivingEntity entity) {
        if(entity.getWorld().isClient)
            return;
        this.entities.remove(entity);

        if(this.entities.isEmpty()){
            RespawnAll();
        }
    }

    private void RespawnAll(){
        if(this.world == null)
                return;
        Random random = new Random();
        MiddleEarth.LOGGER.logDebugMsg("Respawning all entities");


        for(int i = 0; i < 3; i ++){
            NpcEntity npcEntityToSpawn = new NpcEntity(ModEntities.NPC, this.getWorld());
            npcEntityToSpawn.setPos(pos.getX() + random.nextInt(-3, 3), pos.getY() + 2, pos.getZ() + random.nextInt(-3, 3));
            npcEntityToSpawn.setStructureManagerHost(this);
            entities.add(npcEntityToSpawn);
            this.world.spawnEntity(npcEntityToSpawn);
        }
    }
    public static void tick(ServerWorld world, BlockPos blockPos, BlockState blockState, StructureManagerBlockEntity entity) {
        // MiddleEarth.LOGGER.logDebugMsg("current time : " + world.getTime());
        // Make it so the entities respawns after a tick delay (1 day = 24 000, 5 days = 120 000)
    }
}
