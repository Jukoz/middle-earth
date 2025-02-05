package net.sevenstars.middleearth.block.special.wood_pile;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.ModBlockEntities;
import net.sevenstars.middleearth.block.ModDecorativeBlocks;
import net.sevenstars.middleearth.gui.wood_pile.WoodPileScreenHandler;
import net.sevenstars.middleearth.utils.ImplementedInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import static net.sevenstars.middleearth.block.special.wood_pile.WoodPileBlock.STAGE;

public class WoodPileBlockEntity extends LootableContainerBlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private static final String ID = "wood_pile";
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);

    public WoodPileBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WOOD_PILE, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen." + MiddleEarth.MOD_ID + "." + ID);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("me.container.wood_pile");
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return this.inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new WoodPileScreenHandler(syncId, playerInventory, this);
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.readLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory, registryLookup);
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        if (!this.writeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory, registryLookup);
        }
    }

    public void setInventory(DefaultedList<ItemStack> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            this.inventory.set(i, inventory.get(i));
        }
    }
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
        if(this.hasAmount(9)){
            this.getWorld().setBlockState(this.getPos(), ModDecorativeBlocks.WOOD_PILE.getDefaultState()
                    .with(STAGE, 3)
                    .with(Properties.HORIZONTAL_FACING, this.getWorld().getBlockState(this.getPos()).get(Properties.HORIZONTAL_FACING)));
        } else if (this.hasAmount(5)){
            this.getWorld().setBlockState(this.getPos(), ModDecorativeBlocks.WOOD_PILE.getDefaultState()
                    .with(STAGE, 2)
                    .with(Properties.HORIZONTAL_FACING, this.getWorld().getBlockState(this.getPos()).get(Properties.HORIZONTAL_FACING)));
        } else if (this.isEmpty()){
            this.getWorld().setBlockState(this.getPos(), ModDecorativeBlocks.WOOD_PILE.getDefaultState()
                    .with(STAGE, 0)
                    .with(Properties.HORIZONTAL_FACING, this.getWorld().getBlockState(this.getPos()).get(Properties.HORIZONTAL_FACING)));
        } else if (!this.hasAmount(5) && !isEmpty()){
            this.getWorld().setBlockState(this.getPos(), ModDecorativeBlocks.WOOD_PILE.getDefaultState()
                    .with(STAGE, 1)
                    .with(Properties.HORIZONTAL_FACING, this.getWorld().getBlockState(this.getPos()).get(Properties.HORIZONTAL_FACING)));
        }
    }
    @Override
    public ItemStack removeStack(int slot, int count) {
        ItemStack result = Inventories.splitStack(getItems(), slot, count);
        if (!result.isEmpty()) {
            markDirty();
        }

        return result;
    }

    public boolean hasAmount(int amount) {
        int result = 0;
        for (int i = 0; i < size(); i++) {
            ItemStack stack = getStack(i);
            if (!stack.isEmpty()) {
                result++;
            }
        }
        return result >= amount;
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public void clear() {
        inventory.clear();
    }


    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }
}