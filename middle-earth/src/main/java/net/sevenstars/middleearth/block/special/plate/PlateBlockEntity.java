package net.sevenstars.middleearth.block.special.plate;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.SingleStackInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class PlateBlockEntity extends BlockEntity implements SingleStackInventory.SingleStackBlockEntityInventory {
    private ItemStack food = ItemStack.EMPTY;
    private RegistryKey lootTable;
    private long lootTableSeed;
    private boolean blockPlaced = false;

    public PlateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLATE, pos, state);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        view.put("placed", Codec.BOOL, blockPlaced);

        if (!this.writeLootTableToData(view) && !this.food.isEmpty()) {
            view.put("item", ItemStack.CODEC, this.food);
        }
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        this.food.copyAndEmpty();
        this.blockPlaced = view.getBoolean("placed", false);
        if (!this.readLootTableFromData(view)) {
            this.food = view.read("item", ItemStack.CODEC).orElse(ItemStack.EMPTY);
        } else {
            this.food = ItemStack.EMPTY;
        }
    }

    public void setLootTable(RegistryKey<LootTable> lootTable, long seed) {
        this.lootTable = lootTable;
        this.lootTableSeed = seed;
    }

    private boolean readLootTableFromData(ReadView view) {
        this.lootTable = (RegistryKey)view.read("LootTable", LootTable.TABLE_KEY).orElse(null);
        this.lootTableSeed = view.getLong("LootTableSeed", 0L);
        return this.lootTable != null;
    }

    private boolean writeLootTableToData(WriteView view) {
        if (this.lootTable == null) {
            return false;
        } else {
            view.put("LootTable", LootTable.TABLE_KEY, this.lootTable);
            if (this.lootTableSeed != 0L) {
                view.putLong("LootTableSeed", this.lootTableSeed);
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

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }

    @Override
    public BlockEntity asBlockEntity() {
        return this;
    }

    @Override
    public ItemStack getStack() {
        return food;
    }

    @Override
    public void setStack(ItemStack stack) {
        this.food = stack;
        update();
    }

    public void generateItem(ServerWorld world) {
        if (this.lootTable != null && blockPlaced) {
            LootTable lootTable = world.getServer().getReloadableRegistries().getLootTable(this.lootTable);

            LootWorldContext lootWorldContext = (new LootWorldContext.Builder(world)).build(LootContextTypes.EMPTY);
            ObjectArrayList<ItemStack> lootList = lootTable.generateLoot(lootWorldContext, this.lootTableSeed);
            ItemStack itemLoot = lootList.get(world.getRandom().nextInt(lootList.size()));

            this.food = itemLoot;
            this.lootTable = null;
            blockPlaced = false;
            update();
        }
    }

    public void update() {
        markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }
}
