package net.jukoz.me.block.special.shapingAnvil;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlockEntities;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.block.special.forge.ForgeBlockEntity;
import net.jukoz.me.block.special.forge.MultipleStackRecipeInput;
import net.jukoz.me.gui.shapinganvil.ShapingAnvilScreenHandler;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.TemperatureDataComponent;
import net.jukoz.me.recipe.AlloyingRecipe;
import net.jukoz.me.recipe.AnvilShapingRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemStackSet;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShapingAnvilBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, SidedInventory {
    private static final String ID = "shaping_anvil";

    public int progress = 0;
    public static final int MAX_PROGRESS = 100;

    public static final int OUTPUT_SLOT = 1;

    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(2, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;

    public ShapingAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SHAPING_ANVIL, pos, state);

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return progress;
            }

            @Override
            public void set(int index, int value) {
                if (index == 0) {
                    progress = value;
                }
            }

            @Override
            public int size() {
                return 1;
            }
        };
    }

    public void bonk(ShapingAnvilBlockEntity entity){
        ItemStack input = entity.getStack(0);

        List<RecipeEntry<AnvilShapingRecipe>> match = entity.getWorld().getRecipeManager()
                .getAllMatches(AnvilShapingRecipe.Type.INSTANCE, new SingleStackRecipeInput(input), entity.getWorld());

        if (!match.isEmpty() && input.get(ModDataComponentTypes.TEMPERATURE_DATA) != null  && hasShapingRecipe(entity)){
            this.progress += 20;

            input.set(ModDataComponentTypes.TEMPERATURE_DATA, new TemperatureDataComponent(input.get(ModDataComponentTypes.TEMPERATURE_DATA).temperature() - 100));

            ItemStack output = match.getFirst().value().getOutput();

            System.out.println("input: " + input);

            for(int i = 0; i < match.size(); i++){
                System.out.println("output: " + match.get(i).value().getOutput());
            }

            if (progress >= MAX_PROGRESS){
                entity.removeStack(0);
                if(input.get(DataComponentTypes.TRIM) != null){
                    output.set(DataComponentTypes.TRIM, input.get(DataComponentTypes.TRIM));
                    output.set(ModDataComponentTypes.TEMPERATURE_DATA, new TemperatureDataComponent(input.get(ModDataComponentTypes.TEMPERATURE_DATA).temperature()));
                }
                entity.setStack(1, output);
                progress = 0;
            }
        }
    }

    private static boolean hasShapingRecipe(ShapingAnvilBlockEntity entity) {
        SimpleInventory inventory1 = new SimpleInventory(entity.size());
        ItemStack input;

        inventory1.setStack(0, entity.getStack(0));
        input = entity.getStack(0);

        if(input.isEmpty()) return false;

        SingleStackRecipeInput inputStack = new SingleStackRecipeInput(input);
        List<RecipeEntry<AnvilShapingRecipe>> match = entity.getWorld().getRecipeManager()
                .getAllMatches(AnvilShapingRecipe.Type.INSTANCE, inputStack, entity.getWorld());

        return match.getFirst().value().getOutput() != null;
    }


    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt(ID + ".progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt(ID + ".progress");
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen." + MiddleEarth.MOD_ID + "." + ID);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ShapingAnvilScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] slots = new int[inventory.size()];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = i;
        }
        return slots;
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            ItemStack itemStack = getStack(i);
            if (!itemStack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public void clear() {
        inventory.clear();
    }
}
