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
import net.minecraft.registry.Registries;
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

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Optional;

public class ShapingAnvilBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, SidedInventory {
    private static final String ID = "shaping_anvil";

    public int outputIndex = 0;
    public int maxOutputIndex = 0;

    public final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(1, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;

    //TODO tooltip showing output
    //TODO tooltip hammer for stupid
    //TODO remove great axe head
    //TODO update voxel shape
    //TODO small particles when bonk and empty
    //TODO make work in creative somehow
    //TODO screen crash index output mismatch
    //TODO inverted models rendering + tweaks

    public ShapingAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SHAPING_ANVIL, pos, state);

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                if (index == 0) {
                    return ShapingAnvilBlockEntity.this.outputIndex;
                } else {
                    throw new IllegalStateException("Unexpected value: " + index);
                }
            }
            @Override
            public void set(int index, int value) {
                if (index == 0) {
                    ShapingAnvilBlockEntity.this.outputIndex = value;
                } else {
                    throw new IllegalStateException("Unexpected value: " + index);
                }
            }

            @Override
            public int size() {
                return 1;
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
                    entity.update();
                } else {
                    entity.outputIndex -= 1;
                    entity.update();
                }
            } else{
                if (entity.outputIndex == entity.maxOutputIndex){
                    entity.outputIndex = 0;
                    entity.update();
                } else {
                    entity.outputIndex += 1;
                    entity.update();
                }
            }
        }
    }

    public ItemStack getRenderStack(ShapingAnvilBlockEntity entity) {
        return entity.getStack(0);
    }

    public void bonk(ShapingAnvilBlockEntity entity){
        ItemStack input = entity.getStack(0);

        List<RecipeEntry<AnvilShapingRecipe>> match = entity.getWorld().getRecipeManager()
            .getAllMatches(AnvilShapingRecipe.Type.INSTANCE, new SingleStackRecipeInput(input), entity.getWorld());

        if (!match.isEmpty() && input.get(ModDataComponentTypes.TEMPERATURE_DATA) != null  && hasShapingRecipe(entity)){

            if (input.getMaxDamage() == 0 && input.getDamage() == 0){
                input.set(DataComponentTypes.MAX_DAMAGE, 100);
                input.setDamage(100);
                input.setDamage(input.getDamage() - 20);
            } else{
                input.setDamage(input.getDamage() - 20);
            }

            World serverWorld = this.getWorld();
            if (serverWorld instanceof ServerWorld) {
                ((ServerWorld)serverWorld).spawnParticles(ModParticleTypes.ANVIL_SPARK_PARTICLE, pos.getX()+ 0.5f, pos.getY() + 1.0f, pos.getZ() + 0.5f, 5, 0.0, 0.0, 0.0, 0.0);
            }

            input.set(ModDataComponentTypes.TEMPERATURE_DATA, new TemperatureDataComponent(input.get(ModDataComponentTypes.TEMPERATURE_DATA).temperature() - 100));

            RegistryWrapper.Impl<ArmorTrimMaterial>  armorTrimMaterialRegistry = entity.getWorld().getRegistryManager().getWrapperOrThrow(RegistryKeys.TRIM_MATERIAL);
            RegistryWrapper.Impl<ArmorTrimPattern>  armorTrimPatternRegistry = entity.getWorld().getRegistryManager().getWrapperOrThrow(RegistryKeys.TRIM_PATTERN);

            if (input.getDamage() == 0){
                ItemStack output = match.get(entity.outputIndex).value().craft(new SingleStackRecipeInput(input), entity.world.getRegistryManager());

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
                entity.getWorld().playSoundAtBlockCenter(pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
                entity.setStack(0, output);
                entity.update();
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
        Inventories.writeNbt(nbt, this.inventory, true, registryLookup);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.inventory.clear();
        Inventories.readNbt(nbt, this.inventory, registryLookup);
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
