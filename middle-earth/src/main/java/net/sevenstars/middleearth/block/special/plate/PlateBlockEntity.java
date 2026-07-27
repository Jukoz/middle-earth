package net.sevenstars.middleearth.block.special.plate;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.ticks.ContainerSingleItem;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class PlateBlockEntity extends BlockEntity implements ContainerSingleItem.BlockContainerSingleItem {
    private ItemStack food = ItemStack.EMPTY;
    private ResourceKey lootTable;
    private long lootTableSeed;
    private boolean blockPlaced = false;

    public PlateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLATE, pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("placed", blockPlaced);

        if (!this.writeLootTableToData(tag) && !this.food.isEmpty()) {
            tag.put("item", this.food.save(registries));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.food.copyAndClear();
        this.blockPlaced = tag.getBoolean("placed");
        if (!this.readLootTableFromData(tag) && tag.contains("item")) {
            this.food = ItemStack.parse(registries, tag.get("item")).orElse(ItemStack.EMPTY);
        } else {
            this.food = ItemStack.EMPTY;
        }
    }

    public void setLootTable(ResourceKey<LootTable> lootTable, long seed) {
        this.lootTable = lootTable;
        this.lootTableSeed = seed;
    }

    private boolean readLootTableFromData(CompoundTag tag) {
        ResourceLocation id = tag.contains("LootTable") ? ResourceLocation.tryParse(tag.getString("LootTable")) : null;
        this.lootTable = id == null ? null : ResourceKey.create(Registries.LOOT_TABLE, id);
        this.lootTableSeed = tag.getLong("LootTableSeed");
        return this.lootTable != null;
    }

    private boolean writeLootTableToData(CompoundTag tag) {
        if (this.lootTable == null) {
            return false;
        } else {
            tag.putString("LootTable", this.lootTable.location().toString());
            if (this.lootTableSeed != 0L) {
                tag.putLong("LootTableSeed", this.lootTableSeed);
            }

            return true;
        }
    }

    public void setBlockPlaced() {
        blockPlaced = true;
    }

    public boolean isBlockPlaced() {
        return blockPlaced;
    }

    public static void tick(Level world, BlockPos pos, BlockState state, PlateBlockEntity blockEntity) {
        if(blockEntity.blockPlaced) {
            blockEntity.generateItem((ServerLevel) world);
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public BlockEntity getContainerBlockEntity() {
        return this;
    }

    @Override
    public ItemStack getTheItem() {
        return food;
    }

    @Override
    public void setTheItem(ItemStack stack) {
        if (ItemStack.matches(this.food, stack)) {
            return;
        }
        this.food = stack;
        update();
    }

    public void generateItem(ServerLevel world) {
        if (this.lootTable != null && blockPlaced) {
            LootTable lootTable = world.getServer().reloadableRegistries().getLootTable(this.lootTable);

            LootParams lootWorldContext = (new LootParams.Builder(world)).create(LootContextParamSets.EMPTY);
            ObjectArrayList<ItemStack> lootList = lootTable.getRandomItems(lootWorldContext, this.lootTableSeed);
            ItemStack itemLoot = ItemStack.EMPTY;
            if(!lootList.isEmpty()) itemLoot = lootList.get(world.getRandom().nextInt(lootList.size()));

            this.food = itemLoot;
            this.lootTable = null;
            blockPlaced = false;
            update();
        }
    }

    public void update() {
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(
                    worldPosition,
                    getBlockState(),
                    getBlockState(),
                    Block.UPDATE_CLIENTS
            );
        }
    }
}
