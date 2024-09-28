package net.jukoz.me.block.special.shapingAnvil;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlockEntities;
import net.jukoz.me.block.special.forge.ForgeBlockEntity;
import net.jukoz.me.block.special.forge.MetalTypes;
import net.jukoz.me.gui.shapinganvil.ShapingAnvilScreenHandler;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.TemperatureDataComponent;
import net.jukoz.me.particles.ModParticleTypes;
import net.jukoz.me.recipe.AnvilShapingRecipe;
import net.minecraft.block.Block;
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
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.item.trim.ArmorTrimPattern;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ShapingAnvilBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, SidedInventory {
    private static final String ID = "shaping_anvil";

    public int progress = 0;
    public static final int MAX_PROGRESS = 100;

    public int outputIndex = 0;
    public int maxOutputIndex = 0;

    public final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(2, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;

    //TODO rendering of item not updated when both slots empty

    public ShapingAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SHAPING_ANVIL, pos, state);

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> ShapingAnvilBlockEntity.this.progress;
                    case 1 -> ShapingAnvilBlockEntity.this.outputIndex;
                    case 2 -> ShapingAnvilBlockEntity.this.maxOutputIndex;
                    default -> throw new IllegalStateException("Unexpected value: " + index);
                };
            }
            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> ShapingAnvilBlockEntity.this.progress = value;
                    case 1 -> ShapingAnvilBlockEntity.this.outputIndex = value;
                    case 2 -> ShapingAnvilBlockEntity.this.maxOutputIndex = value;
                    default -> throw new IllegalStateException("Unexpected value: " + index);
                };
            }

            @Override
            public int size() {
                return 3;
            }
        };
    }

    public static void updateIndex(boolean left, Vec3d coords, ServerPlayerEntity player){
        BlockPos pos = new BlockPos((int) coords.getX(), (int) coords.getY(), (int) coords.getZ());

        Optional<ShapingAnvilBlockEntity> shapingAnvilBlockEntity = player.getWorld().getBlockEntity(pos, ModBlockEntities.SHAPING_ANVIL);

        if(shapingAnvilBlockEntity.isPresent()){
            ShapingAnvilBlockEntity entity = shapingAnvilBlockEntity.get();
            if (left){
                if(entity.outputIndex == 0){
                    entity.outputIndex = entity.maxOutputIndex;
                } else {
                    entity.outputIndex -= 1;
                }
            } else{
                if (entity.outputIndex == entity.maxOutputIndex){
                    entity.outputIndex = 0;
                } else {
                    entity.outputIndex += 1;
                }
            }
        }
    }

    public ItemStack getRenderStack() {
        if (this.getStack(1).isEmpty()){
            if (this.getStack(0).isEmpty()){
                return ItemStack.EMPTY;
            } else {
                return this.getStack(0);
            }
        } else {
            return this.getStack(1);
        }
    }

    public void bonk(ShapingAnvilBlockEntity entity){
        ItemStack input = entity.getStack(0);

        List<RecipeEntry<AnvilShapingRecipe>> match = entity.getWorld().getRecipeManager()
            .getAllMatches(AnvilShapingRecipe.Type.INSTANCE, new SingleStackRecipeInput(input), entity.getWorld());

        if (!match.isEmpty() && input.get(ModDataComponentTypes.TEMPERATURE_DATA) != null  && hasShapingRecipe(entity) && entity.getStack(1).isEmpty()){
            this.progress += 20;

            World serverWorld = this.getWorld();
            if (serverWorld instanceof ServerWorld) {
                ((ServerWorld)serverWorld).spawnParticles(ModParticleTypes.ANVIL_SPARK_PARTICLE, pos.getX()+ 0.5f, pos.getY() + 1.0f, pos.getZ() + 0.5f, 5, 0.0, 0.0, 0.0, 0.0);
            }

            input.set(ModDataComponentTypes.TEMPERATURE_DATA, new TemperatureDataComponent(input.get(ModDataComponentTypes.TEMPERATURE_DATA).temperature() - 100));

            RegistryWrapper.Impl<ArmorTrimMaterial>  armorTrimMaterialRegistry = entity.getWorld().getRegistryManager().getWrapperOrThrow(RegistryKeys.TRIM_MATERIAL);
            RegistryWrapper.Impl<ArmorTrimPattern>  armorTrimPatternRegistry = entity.getWorld().getRegistryManager().getWrapperOrThrow(RegistryKeys.TRIM_PATTERN);

            if (progress >= MAX_PROGRESS){
                ItemStack output = match.get(entity.outputIndex).value().craft(new SingleStackRecipeInput(input), entity.world.getRegistryManager());

                entity.removeStack(0);
                if(input.get(DataComponentTypes.TRIM) != null){
                    output.set(DataComponentTypes.TRIM, input.get(DataComponentTypes.TRIM));
                    output.set(ModDataComponentTypes.TEMPERATURE_DATA, new TemperatureDataComponent(input.get(ModDataComponentTypes.TEMPERATURE_DATA).temperature()));
                } else{
                    if(input.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "ingot_shaping")))){
                        MetalTypes metal = MetalTypes.getMetalByIngot(input.getItem());
                        output.set(DataComponentTypes.TRIM, new ArmorTrim(
                                armorTrimMaterialRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL, Identifier.of(MiddleEarth.MOD_ID, metal.getName()))),
                                armorTrimPatternRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TRIM_PATTERN, Identifier.of(MiddleEarth.MOD_ID, "smithing_part")))));
                        output.set(ModDataComponentTypes.TEMPERATURE_DATA, new TemperatureDataComponent(input.get(ModDataComponentTypes.TEMPERATURE_DATA).temperature()));
                    }
                }
                entity.setStack(1, output);
                update();
                progress = 0;
            }
        }
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, ShapingAnvilBlockEntity entity) {
        ItemStack input = entity.getStack(0);
        if (!input.isEmpty()){
            List<RecipeEntry<AnvilShapingRecipe>> match = entity.getWorld().getRecipeManager()
                    .getAllMatches(AnvilShapingRecipe.Type.INSTANCE, new SingleStackRecipeInput(input), entity.getWorld());;
            if(!match.isEmpty()){
                entity.maxOutputIndex = match.size() - 1;
            } else {
                entity.maxOutputIndex = 0;
            }
        } else {
            entity.maxOutputIndex = 0;
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
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] slots = new int[inventory.size()];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = i;
        }
        return slots;
    }

    private void update() {
        markDirty();
        if(world != null)
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
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
        update();
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        update();
        return Inventories.removeStack(inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
            update();
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

    @Override
    public Object getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }
}
