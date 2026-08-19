package net.sevenstars.middleearth.gui.shapinganvil;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.block.special.forge.MultipleStackRecipeInput;
import net.sevenstars.middleearth.block.special.shapingAnvil.ShapingAnvilBlockEntity;
import net.sevenstars.middleearth.gui.ModScreenHandlers;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreen;
import net.sevenstars.middleearth.network.packets.C2S.AnvilIndexPacket;
import net.sevenstars.middleearth.network.packets.S2C.InscriptionEnchantInfoPacket;
import net.sevenstars.middleearth.network.packets.S2C.ShapingAnvilRecipePacket;
import net.sevenstars.middleearth.recipe.AnvilShapingRecipe;
import net.sevenstars.middleearth.recipe.ArtisanRecipe;
import net.sevenstars.middleearth.recipe.RecipesME;

import java.util.List;

public class ShapingAnvilScreenHandler extends AbstractContainerMenu {
    private final Container inventory;
    private final ContainerData propertyDelegate;
    protected BlockPos pos;
    private final Level world;
    private List<RecipeHolder<AnvilShapingRecipe>> availableRecipes;
    private ItemStack outputStack;
    private Player player;
    private DataSlot selectedIndex;
    private DataSlot recipesSize;

    public ShapingAnvilScreenHandler(int syncId, Inventory playerInventory, BlockPos blockPos) {
        this(syncId, playerInventory, new SimpleContainer(1), new SimpleContainerData(2));
        this.pos = blockPos;
    }

    public ShapingAnvilScreenHandler(int syncId, Inventory playerInventory, Container inventory, ContainerData delegate) {
        super(ModScreenHandlers.TREATED_ANVIL_SCREEN_HANDLER, syncId);
        checkContainerSize(inventory, 1);
        this.inventory = inventory;
        inventory.startOpen(playerInventory.player);
        this.propertyDelegate = delegate;
        this.pos = inventory instanceof ShapingAnvilBlockEntity anvil ? anvil.getBlockPos() : BlockPos.ZERO;
        this.world = playerInventory.player.level();
        this.outputStack = ItemStack.EMPTY;
        this.player = playerInventory.player;

        this.selectedIndex = DataSlot.standalone();
        this.recipesSize = DataSlot.standalone();

        this.addDataSlot(selectedIndex).set(-1);
        this.addDataSlot(recipesSize).set(0);

        this.addSlot(new ShapingAnvilSlot(inventory, 0, 136, 33){
            @Override
            public void setChanged() {
                super.setChanged();
                if(player.level().isClientSide) {
                    ShapingAnvilScreen screen = (ShapingAnvilScreen)Minecraft.getInstance().screen;
                    if(screen != null) screen.clearOutputs();
                }
                ShapingAnvilScreenHandler.this.updateStack(ShapingAnvilScreenHandler.this.inventory);
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addDataSlots(delegate);


    }

    @Override
    public ItemStack quickMoveStack(Player player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.getContainerSize()) {
                if (!this.moveItemStackTo(originalStack, this.inventory.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 0, this.inventory.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return newStack;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void updateScreen(){
        this.updateStack(this.inventory);
    }

    public void updateStack(Container inventory) {
        ItemStack input = inventory.getItem(0);
        if (!this.world.isClientSide){
            RecipeManager serverRecipeManager = this.world.getRecipeManager();
            this.availableRecipes = serverRecipeManager.getRecipesFor(
                    RecipesME.ANVIL_SHAPING,
                    new SingleRecipeInput(input),
                    this.world
            );
            recipesSize.set(this.availableRecipes.size());
            int index = 0;
            for(RecipeHolder<AnvilShapingRecipe> recipe : availableRecipes) {
                ShapingAnvilRecipePacket newPacket = new ShapingAnvilRecipePacket(index++, recipe.value().getOutput());
                PacketDistributor.sendToPlayer((ServerPlayer) player, newPacket);
            }
        }
    }

    public int getAvailableRecipeCount() {
        int count = 0;
        ItemStack input = inventory.getItem(0);
        if (!this.world.isClientSide){
            RecipeManager serverRecipeManager = this.world.getRecipeManager();
            this.availableRecipes = serverRecipeManager.getRecipesFor(
                    RecipesME.ANVIL_SHAPING,
                    new SingleRecipeInput(input),
                    this.world
            );
            count = this.availableRecipes.size();
        }
        return count;
    }

    public List<RecipeHolder<AnvilShapingRecipe>> getAvailableRecipes() {
        return availableRecipes;
    }

    public int getSelectedRecipe() {
        return selectedIndex.get();
    }
    public void setSelectedRecipe(int index) {
        selectedIndex.set(index);
        AnvilIndexPacket anvilIndexPacket = new AnvilIndexPacket(index, getPos().getX(), getPos().getY(), getPos().getZ());
        PacketDistributor.sendToServer(anvilIndexPacket);
    }

    public boolean clickMenuButton(Player player, int id) {
        this.player = player;
        return this.isInBounds(id);
    }

    private boolean isInBounds(int id) {
        return id >= 0 && id < this.recipesSize.get();
    }

    public ItemStack getOutputStack() {
        return outputStack;
    }

    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
