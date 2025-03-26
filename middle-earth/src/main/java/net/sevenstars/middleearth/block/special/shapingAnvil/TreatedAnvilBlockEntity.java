package net.sevenstars.middleearth.block.special.shapingAnvil;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.equipment.trim.ArmorTrimPattern;
import net.minecraft.recipe.ServerRecipeManager;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.ModBlockEntities;
import net.sevenstars.middleearth.block.special.forge.MetalTypes;
import net.sevenstars.middleearth.gui.shapinganvil.ShapingAnvilScreenHandler;
import net.sevenstars.middleearth.item.ModDataComponentTypes;
import net.sevenstars.middleearth.item.dataComponents.TemperatureDataComponent;
import net.sevenstars.middleearth.particles.ModParticleTypes;
import net.sevenstars.middleearth.recipe.AnvilShapingRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TreatedAnvilBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, SidedInventory {
    private static final String ID = "shaping_anvil";

    public int outputIndex = 0;
    public int maxOutputIndex = 0;

    public final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(1, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;

    //TODO make work in creative somehow

    public TreatedAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TREATED_ANVIL, pos, state);

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> TreatedAnvilBlockEntity.this.outputIndex;
                    case 1 -> TreatedAnvilBlockEntity.this.maxOutputIndex;
                    default -> throw new IllegalStateException("Unexpected value: " + index);
                };
            }
            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> TreatedAnvilBlockEntity.this.outputIndex = value;
                    case 1 -> TreatedAnvilBlockEntity.this.maxOutputIndex = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    public TreatedAnvilBlockEntity(BlockEntityType type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> TreatedAnvilBlockEntity.this.outputIndex;
                    case 1 -> TreatedAnvilBlockEntity.this.maxOutputIndex;
                    default -> throw new IllegalStateException("Unexpected value: " + index);
                };
            }
            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> TreatedAnvilBlockEntity.this.outputIndex = value;
                    case 1 -> TreatedAnvilBlockEntity.this.maxOutputIndex = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    public static void updateIndex(boolean left, Vec3d coords, ServerPlayerEntity player){
        BlockPos pos = new BlockPos((int) coords.getX(), (int) coords.getY(), (int) coords.getZ());

        BlockEntity shapingAnvilBlockEntity = player.getWorld().getBlockEntity(pos);

        if(shapingAnvilBlockEntity instanceof TreatedAnvilBlockEntity entity){
            if (left){
                if(entity.outputIndex == 0){
                    entity.outputIndex = entity.maxOutputIndex;
                    entity.update();
                } else {
                    entity.outputIndex -= 1;
                    entity.update();
                }
            } else{
                if (entity.outputIndex == entity.maxOutputIndex){
                    entity.outputIndex = 0;
                    entity.update();
                } else if(entity.outputIndex > entity.maxOutputIndex){
                    entity.outputIndex = entity.maxOutputIndex;
                } else{
                    entity.outputIndex += 1;
                    entity.update();
                }
            }
        }
    }

    public ItemStack getRenderStack(TreatedAnvilBlockEntity entity) {
        return entity.getStack(0);
    }

    public void bonk(TreatedAnvilBlockEntity entity, ServerWorld world){
        ItemStack input = entity.getStack(0);

        ServerRecipeManager serverRecipeManager = (ServerRecipeManager)entity.getWorld().getRecipeManager();
        List<RecipeEntry<AnvilShapingRecipe>> match = serverRecipeManager.getAllMatches(AnvilShapingRecipe.Type.INSTANCE, new SingleStackRecipeInput(input), entity.getWorld()).toList();

        if (!match.isEmpty() && input.get(ModDataComponentTypes.TEMPERATURE_DATA) != null  && hasShapingRecipe(entity)){
            int temperature = input.get(ModDataComponentTypes.TEMPERATURE_DATA).temperature();

            entity.getWorld().playSound(null, pos, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 1.5f, 1.0f - (float) temperature / 1000);

            int minRandProgress = 6;
            int maxRandProgress = 14;

            if (input.getMaxDamage() == 0 && input.getDamage() == 0){
                input.set(DataComponentTypes.MAX_DAMAGE, match.get(entity.outputIndex).value().getAmount());
                input.setDamage(match.get(entity.outputIndex).value().getAmount() - (int) (Math.random() * (maxRandProgress - minRandProgress) + minRandProgress));
            } else{
                input.setDamage(input.getDamage() - (int) (Math.random() * (maxRandProgress - minRandProgress) + minRandProgress));
            }

            World serverWorld = this.getWorld();
            if (serverWorld instanceof ServerWorld) {
                ((ServerWorld)serverWorld).spawnParticles(ModParticleTypes.ANVIL_SPARK_PARTICLE, pos.getX()+ 0.5f, pos.getY() + 1.0f, pos.getZ() + 0.5f, Math.max(temperature / 10, 3), 0.0, 0.0, 0.0, 0.0);
            }

            int minRandTemperature = 10;
            int maxRandTemperature = 18;
            int value = (int) (Math.random() * (maxRandTemperature - minRandTemperature) + minRandTemperature);

            if ((input.get(ModDataComponentTypes.TEMPERATURE_DATA).temperature() - value) <= 0){
                input.remove(ModDataComponentTypes.TEMPERATURE_DATA);
            } else {
                input.set(ModDataComponentTypes.TEMPERATURE_DATA, new TemperatureDataComponent(input.get(ModDataComponentTypes.TEMPERATURE_DATA).temperature() - value));
            }
            RegistryWrapper.Impl<ArmorTrimMaterial>  armorTrimMaterialRegistry = entity.getWorld().getRegistryManager().getOrThrow(RegistryKeys.TRIM_MATERIAL);
            RegistryWrapper.Impl<ArmorTrimPattern>  armorTrimPatternRegistry = entity.getWorld().getRegistryManager().getOrThrow(RegistryKeys.TRIM_PATTERN);


            if (input.getDamage() == 0){
                ItemStack output = match.get(entity.outputIndex).value().craft(new SingleStackRecipeInput(input), entity.world.getRegistryManager());

                if(input.get(DataComponentTypes.TRIM) != null){
                    output.set(DataComponentTypes.TRIM, input.get(DataComponentTypes.TRIM));
                } else{
                    MetalTypes metal = MetalTypes.EMPTY;
                    if(input.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "ingot_shaping")))) {
                        metal = MetalTypes.getMetalByIngot(input.getItem());
                    }else if(input.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "nugget_shaping")))) {
                        metal = MetalTypes.getMetalByNugget(input.getItem());
                    }
                    if (metal.isVanilla()){
                        output.set(DataComponentTypes.TRIM, new ArmorTrim(
                                armorTrimMaterialRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL, Identifier.of(metal.getName()))),
                                armorTrimPatternRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TRIM_PATTERN, Identifier.of(MiddleEarth.MOD_ID,"smithing_part")))));
                    } else {
                        output.set(DataComponentTypes.TRIM, new ArmorTrim(
                                armorTrimMaterialRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TRIM_MATERIAL, Identifier.of(MiddleEarth.MOD_ID, metal.getName()))),
                                armorTrimPatternRegistry.getOrThrow(RegistryKey.of(RegistryKeys.TRIM_PATTERN, Identifier.of(MiddleEarth.MOD_ID, "smithing_part")))));
                    }
                }
                if (input.get(ModDataComponentTypes.TEMPERATURE_DATA) != null){
                    output.set(ModDataComponentTypes.TEMPERATURE_DATA, new TemperatureDataComponent(input.get(ModDataComponentTypes.TEMPERATURE_DATA).temperature()));
                }
                entity.getWorld().playSound(null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                entity.setStack(0, output);
                entity.update();
            }
        } else {
            World serverWorld = this.getWorld();
            if (serverWorld instanceof ServerWorld) {
                ((ServerWorld)serverWorld).spawnParticles(ModParticleTypes.ANVIL_SPARK_PARTICLE, pos.getX()+ 0.5f, pos.getY() + 1.0f, pos.getZ() + 0.5f, 2, 0.0, 0.0, 0.0, 0.0);
            }
            entity.getWorld().playSound(null, pos, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 1.5f, 1.0f);
        }
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, TreatedAnvilBlockEntity entity) {
        ItemStack input = entity.getStack(0);
        if (!input.isEmpty()){
            ServerRecipeManager serverRecipeManager = (ServerRecipeManager)entity.getWorld().getRecipeManager();
            List<RecipeEntry<AnvilShapingRecipe>> match = serverRecipeManager.getAllMatches(AnvilShapingRecipe.Type.INSTANCE, new SingleStackRecipeInput(input), entity.getWorld()).toList();
            if(!match.isEmpty()){
                entity.maxOutputIndex = match.size() - 1;
                if (entity.outputIndex > entity.maxOutputIndex){
                    entity.outputIndex = entity.maxOutputIndex;
                }
                entity.update();
            } else {
                entity.maxOutputIndex = 0;
                entity.update();
            }
        } else {
            entity.maxOutputIndex = 0;
            entity.update();
        }
    }

    private static boolean hasShapingRecipe(TreatedAnvilBlockEntity entity) {
        SimpleInventory inventory1 = new SimpleInventory(entity.size());
        ItemStack input;

        inventory1.setStack(0, entity.getStack(0));
        input = entity.getStack(0);

        if(input.isEmpty()) return false;

        SingleStackRecipeInput inputStack = new SingleStackRecipeInput(input);
        ServerRecipeManager serverRecipeManager = (ServerRecipeManager)entity.getWorld().getRecipeManager();
        List<RecipeEntry<AnvilShapingRecipe>> match = serverRecipeManager.getAllMatches(AnvilShapingRecipe.Type.INSTANCE, inputStack, entity.getWorld()).toList();

        return match.getFirst().value().getOutput() != null;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, this.inventory, true, registryLookup);
        nbt.putInt("current-index", this.outputIndex);
        nbt.putInt("current-max-index", this.maxOutputIndex);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.inventory.clear();
        Inventories.readNbt(nbt, this.inventory, registryLookup);
        this.outputIndex = nbt.getInt("current-index");
        this.maxOutputIndex = nbt.getInt("current-max-index");
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

    public void update() {
        markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return getStack(0).isEmpty();
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
        ItemStack result = Inventories.splitStack(inventory, slot, amount);
        if (!result.isEmpty()) {
            update();
        }
        return result;
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
        update();
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
