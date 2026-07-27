package net.sevenstars.middleearth.block.special.structureManager.nest;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.sevenstars.middleearth.block.utils.ExtendedMenuProviderME;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreenData;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class StructureNestBlockEntity extends BlockEntity implements ExtendedMenuProviderME {
    private static final String ID = "structure_nest";

    private enum SyncedData {
        MANAGER_ID("%s.ManagerId".formatted(ID)),
        NEST_ID("%s.NestId".formatted(ID)),
        SPAWN_RADIUS("%s.SpawnRadius".formatted(ID)),
        IS_ENABLED("%s.IsEnabled".formatted(ID));

        public final String name;
        SyncedData(String name){
            this.name = name;
        }
    }
    @Nullable
    protected ResourceLocation managerId;
    @Nullable
    protected ResourceLocation nestId;
    protected int spawnRadius;
    protected boolean isEnabled;

    protected int fails = 0;
    boolean initialized = false;

    public StructureNestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STRUCTURE_NEST, pos, state);
    }

    @Override
    public void writeOpeningData(RegistryFriendlyByteBuf buffer) {
        StructureNestScreenData.PACKET_CODEC.encode(buffer, new StructureNestScreenData(this.worldPosition,
                Optional.ofNullable(this.managerId), Optional.ofNullable(this.nestId), spawnRadius, isEnabled));
    }

    public Component getDisplayName() {
        return Component.translatable("screen.%s.%s".formatted(MiddleEarth.MOD_ID, ID));
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new StructureNestScreenHandler(syncId, playerInventory, new StructureNestScreenData(this.worldPosition,
                Optional.ofNullable(this.managerId),
                Optional.ofNullable(this.nestId),
                this.spawnRadius,
                this.isEnabled));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains(SyncedData.MANAGER_ID.name)) {
            managerId = ResourceLocation.tryParse(tag.getString(SyncedData.MANAGER_ID.name));
        }
        if (tag.contains(SyncedData.NEST_ID.name)) {
            nestId = ResourceLocation.tryParse(tag.getString(SyncedData.NEST_ID.name));
        }
        spawnRadius = tag.getInt(SyncedData.SPAWN_RADIUS.name);
        isEnabled = tag.getBoolean(SyncedData.IS_ENABLED.name);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (nestId != null) {
            tag.putString(SyncedData.NEST_ID.name, nestId.toString());
        }
        if (managerId != null) {
            tag.putString(SyncedData.MANAGER_ID.name, managerId.toString());
        }
        tag.putInt(SyncedData.SPAWN_RADIUS.name, this.spawnRadius);
        tag.putBoolean(SyncedData.IS_ENABLED.name, this.isEnabled);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void applySettings(
            @Nullable ResourceLocation structureManagerId,
            @Nullable ResourceLocation structureNestId,
            int newRadius,
            boolean enabled
    ) {
        if (Objects.equals(this.managerId, structureManagerId)
                && Objects.equals(this.nestId, structureNestId)
                && this.spawnRadius == newRadius
                && this.isEnabled == enabled) {
            return;
        }
        this.managerId = structureManagerId;
        this.nestId = structureNestId;
        this.spawnRadius = newRadius;
        this.isEnabled = enabled;
        updateListeners();
    }

    private void updateListeners() {
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(
                    this.getBlockPos(),
                    this.getBlockState(),
                    this.getBlockState(),
                    Block.UPDATE_CLIENTS
            );
        }
    }

    public static void tickEvent(Level world, BlockPos blockPos, BlockState blockState, StructureNestBlockEntity entity) {
        entity.tickEvent(world, blockState);
    }

    private void tickEvent(Level world, BlockState blockState) {
        if(world.isClientSide)
            return;

        if(!blockState.getValue(StructureNestBlock.ENABLED)) {
            fails = 0;
            return;
        }

        long tickOffset = Math.floorMod(worldPosition.asLong(), 20L);
        if(managerId == null || nestId == null
                || Math.floorMod(world.getGameTime() + tickOffset, 20L) != 0)
            return;

        StructureManagerBlockEntity structureManagerBlockEntity = StructureManagerService.getClosest(world, worldPosition, 20);
        if(structureManagerBlockEntity == null) {
            fails++;
        }
        else {
            if(structureManagerBlockEntity.subscribeNest(this.worldPosition, this.managerId, this.nestId, this.spawnRadius))
            {
                world.destroyBlock(worldPosition, false);
                world.removeBlockEntity(worldPosition);
                initialized = true;
                updateListeners();
            } else {
                fails++;
            }
        }
        if(fails >= 12) {
            world.destroyBlock(worldPosition, false);
            world.removeBlockEntity(worldPosition);
            updateListeners();
        }
    }
}
