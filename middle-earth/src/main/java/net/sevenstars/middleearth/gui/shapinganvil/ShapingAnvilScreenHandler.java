package net.sevenstars.middleearth.gui.shapinganvil;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.Property;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.forge.MultipleStackRecipeInput;
import net.sevenstars.middleearth.gui.ModScreenHandlers;
import net.sevenstars.middleearth.network.packets.S2C.InscriptionEnchantInfoPacket;
import net.sevenstars.middleearth.network.packets.S2C.ShapingAnvilRecipePacket;
import net.sevenstars.middleearth.recipe.AnvilShapingRecipe;
import net.sevenstars.middleearth.recipe.ArtisanRecipe;
import net.sevenstars.middleearth.recipe.RecipesME;

import java.util.List;

public class ShapingAnvilScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    protected BlockPos pos;
    private final World world;
    private List<RecipeEntry<AnvilShapingRecipe>> availableRecipes;
    private ItemStack outputStack;
    private PlayerEntity player;

    public ShapingAnvilScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos blockPos) {
        this(syncId, playerInventory, new SimpleInventory(1), new ArrayPropertyDelegate(2));
        this.pos = blockPos;
    }

    public ShapingAnvilScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(ModScreenHandlers.TREATED_ANVIL_SCREEN_HANDLER, syncId);
        checkSize(inventory, 1);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = delegate;
        this.pos = BlockPos.ORIGIN;
        this.world = playerInventory.player.getWorld();
        this.outputStack = ItemStack.EMPTY;
        this.player = playerInventory.player;

        this.addSlot(new ShapingAnvilSlot(inventory, 0, 136, 33){
            @Override
            public void markDirty() {
                super.markDirty();
                ShapingAnvilScreenHandler.this.updateStack(ShapingAnvilScreenHandler.this.inventory);
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(delegate);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void updateStack(Inventory inventory) {
        ItemStack input = inventory.getStack(0);
        if (!this.world.isClient){
            ServerRecipeManager serverRecipeManager = (ServerRecipeManager) this.world.getRecipeManager();
            this.availableRecipes = serverRecipeManager.getAllMatches(RecipesME.ANVIL_SHAPING, new SingleStackRecipeInput(input), this.world).toList();

            int index = 0;
            for(RecipeEntry<AnvilShapingRecipe> recipe : availableRecipes) {
                ShapingAnvilRecipePacket newPacket = new ShapingAnvilRecipePacket(index++, recipe.value().getOutput());
                ServerPlayNetworking.send((ServerPlayerEntity) player, newPacket);
            }
        }
    }

    public int getAvailableRecipeCount() {
        int count = 0;
        ItemStack input = inventory.getStack(0);
        if (!this.world.isClient){
            ServerRecipeManager serverRecipeManager = (ServerRecipeManager) this.world.getRecipeManager();
            this.availableRecipes = serverRecipeManager.getAllMatches(RecipesME.ANVIL_SHAPING, new SingleStackRecipeInput(input), this.world).toList();
            count = this.availableRecipes.size();
        }
        return count;
    }

    public List<RecipeEntry<AnvilShapingRecipe>> getAvailableRecipes() {
        return availableRecipes;
    }

    public int getSelectedRecipe() {
        return 0;
    }

    public ItemStack getOutputStack() {
        return outputStack;
    }

    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
