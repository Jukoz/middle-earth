package net.jukoz.me.block.special.wood_pile;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlockEntities;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.network.ModNetworks;
import net.jukoz.me.utils.ImplementedInventory;
import net.jukoz.me.gui.wood_pile.WoodPileScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import static net.jukoz.me.block.special.wood_pile.WoodPileBlock.STAGE;

public class WoodPileBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private static final String ID = "wood_pile";
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);

    public WoodPileBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WOOD_PILE, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen." + MiddleEarth.MOD_ID + "." + ID);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new WoodPileScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
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
        if(this.hasAmount(5)){
            this.getWorld().setBlockState(this.getPos(), ModDecorativeBlocks.WOOD_PILE.getDefaultState()
                    .with(STAGE, 2)
                    .with(Properties.HORIZONTAL_FACING, this.getWorld().getBlockState(this.getPos()).get(Properties.HORIZONTAL_FACING)));
        }  else if (this.isEmpty()){
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
        if(world != null && !world.isClient()) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(inventory.size());
            for(int i = 0; i < inventory.size(); i++) {
                data.writeItemStack(inventory.get(i));
            }
            data.writeBlockPos(getPos());

            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
                ServerPlayNetworking.send(player, ModNetworks.ITEM_SYNC, data);
            }
        }
        super.markDirty();
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