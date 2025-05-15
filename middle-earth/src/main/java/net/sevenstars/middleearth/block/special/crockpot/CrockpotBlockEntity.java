package net.sevenstars.middleearth.block.special.crockpot;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.UseRemainderComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.ModBlockEntities;
import net.sevenstars.middleearth.block.special.forge.MultipleStackRecipeInput;
import net.sevenstars.middleearth.recipe.CrockpotRecipe;
import net.sevenstars.middleearth.recipe.ModRecipes;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CrockpotBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, SidedInventory {
    private static final String ID = "crockpot";
    public static final int OUTPUT_SLOT = 4;
    public static final int COOK_TIME = 60;
    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(5, ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate;
    private final ServerRecipeManager.MatchGetter<MultipleStackRecipeInput, ? extends CrockpotRecipe> matchGetter;
    private int progress = 0;
    private Random random;

    public CrockpotBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CROCKPOT, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CrockpotBlockEntity.this.progress;
                    default -> throw new IllegalStateException("Unexpected value: " + index);
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CrockpotBlockEntity.this.progress = value;
                }
            }

            @Override
            public int size() {
                return 1;
            }
        };
        this.matchGetter = ServerRecipeManager.createCachedMatchGetter(ModRecipes.CROCKPOT);
        random = Random.create();
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, CrockpotBlockEntity blockEntity) {
        ArrayList<ItemStack> ingredients = new ArrayList<>();
        for(int i = 0; i < blockEntity.inventory.size(); i++) {
            ItemStack ingredient = blockEntity.inventory.get(i);
            if(!ingredient.isEmpty()) {
                ingredients.add(ingredient);
            }
        }
        boolean markDirty = false;
        if (blockEntity.isBoiling() && !world.isClient) {
            blockEntity.randomBubbles();
            ServerWorld serverWorld = (ServerWorld) world;
            MultipleStackRecipeInput recipeInput = new MultipleStackRecipeInput(ingredients);
            RecipeEntry recipeEntry;
            if (ingredients.size() >= 2) {
                recipeEntry = blockEntity.matchGetter.getFirstMatch(recipeInput, serverWorld).orElse(null);
            } else {
                recipeEntry = null;
                blockEntity.progress = Math.max(blockEntity.progress - 1, 0);
            }
            markDirty = true;
            ++blockEntity.progress;
            if (blockEntity.progress >= COOK_TIME) {
                blockEntity.progress = 0;
                craftRecipe(world.getRegistryManager(), recipeEntry, recipeInput, blockEntity.inventory);
                blockEntity.recipeCraftedSound();
            }
        }

        if (markDirty) {
            markDirty(world, pos, state);
        }

    }

    public static void clientTick(World world, BlockPos pos, BlockState state, CrockpotBlockEntity blockEntity) {
        if(blockEntity.isCooking())
        {
            double x = (double)pos.getX() + 0.5;
            double y = (double)pos.getY() + 0.5;
            double z = (double)pos.getZ() + 0.5;
            if (blockEntity.random.nextDouble() < 0.12) {
                world.playSound(null, pos, SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            double i = blockEntity.random.nextDouble() * 0.4 - 0.2;
            double j = blockEntity.random.nextDouble() * 0.4 - 0.2;
            world.addParticleClient(ParticleTypes.BUBBLE, x + i, y, z + j, 0.0, 0.1, 0.0);
        }
    }

    public void randomBubbles() {
        if(isCooking())
        {
            double x = (double)pos.getX() + 0.5;
            double y = (double)pos.getY() + 0.5;
            double z = (double)pos.getZ() + 0.5;
            if (random.nextDouble() < 0.12) {
                world.playSound(null, pos, SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            double i = random.nextDouble() * 0.4 - 0.2;
            double j = random.nextDouble() * 0.4 - 0.2;
            world.addParticleClient(ParticleTypes.BUBBLE, x + i, y, z + j, 0.0, 0.1, 0.0);
        }
    }

    private static boolean craftRecipe(DynamicRegistryManager dynamicRegistryManager, @Nullable RecipeEntry<CrockpotRecipe> recipe,
                                       MultipleStackRecipeInput input, DefaultedList<ItemStack> inventory) {
        if (recipe != null) {
            ItemStack craftedStack = recipe.value().craft(input, dynamicRegistryManager);
            inventory.set(OUTPUT_SLOT, craftedStack.copy());
            for(int i = 0; i < OUTPUT_SLOT; i++) {
                inventory.set(i, ItemStack.EMPTY);
            }
            return true;
        } else {
            return false;
        }
    }

    public void recipeCraftedSound() {
        double x = (double)pos.getX() + 0.5;
        double y = (double)pos.getY() + 0.5;
        double z = (double)pos.getZ() + 0.5;
        world.playSound(null, pos, SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 1.1F, 0.8F);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CrockpotScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public boolean isCooking() {
        return progress > 0;
    }

    public boolean isHanging() {
        if(world != null) {
            BlockState blockState = world.getBlockState(getPos());
            if(blockState == null || blockState.isAir()) return false;
            return blockState.get(CrockpotBlock.HANGING);
        }
        return false;
    }

    public boolean isBoiling() {
        return isHanging() && hasOutput();
    }

    public boolean hasOutput() {
        return !getStack(OUTPUT_SLOT).isEmpty();
    }

    public boolean fill(ItemStack itemStack) {
        if(getStack(OUTPUT_SLOT).isEmpty()) {
            if(itemStack.getItem() == Items.WATER_BUCKET) {
                setStack(OUTPUT_SLOT, itemStack);
                return true;
            }
        }
        return false;
    }

    public ItemStack fillBowl(Item remainder) {
        if(hasOutput()) {
            ItemStack outputStack = getStack(OUTPUT_SLOT);
            UseRemainderComponent remainderComponent = outputStack.get(DataComponentTypes.USE_REMAINDER);
            if(remainderComponent != null) {
                ItemStack recipeRemainder = remainderComponent.convertInto();
                if (recipeRemainder.getItem() == remainder) {
                    ItemStack result = outputStack.copy();
                    result.setCount(1);
                    outputStack.decrement(1);
                    if(outputStack.getCount() == 0) {
                        outputStack = ItemStack.EMPTY;
                    }
                    setStack(OUTPUT_SLOT, outputStack);
                    return outputStack;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void onBlockReplaced(BlockPos pos, BlockState oldState) {
        if (this.world != null) {
            DefaultedList<ItemStack> items = (DefaultedList<ItemStack>) getList();
            items.removeLast();
            ItemScatterer.spawn(this.world, pos, items);
        }
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
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false; // Do not extract liquid output into hopper.
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return this.inventory.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

    public List<ItemStack> getList() {
        return new ArrayList<>(this.inventory.stream().toList());
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
        this.inventory.set(slot, stack);
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
        this.inventory.clear();
    }

    @Override
    public Object getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen." + MiddleEarth.MOD_ID + "." + ID);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, this.inventory, true, registries);
        nbt.putInt(ID + ".progress", this.progress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        this.inventory.clear();
        Inventories.readNbt(nbt, this.inventory, registries);
        this.progress = nbt.getInt(ID + ".progress", 0);
    }
}
