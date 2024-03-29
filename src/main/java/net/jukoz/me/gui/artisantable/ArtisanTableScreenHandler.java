package net.jukoz.me.gui.artisantable;

import com.google.common.collect.Lists;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.gui.ModScreenHandlers;
import net.jukoz.me.recipe.ArtisanRecipe;
import net.jukoz.me.recipe.ModRecipes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ArtisanTableScreenHandler extends ScreenHandler {
    private final ScreenHandlerContext context;
    private final Property selectedRecipe;
    private final World world;
    private List availableRecipes;
    private ItemStack inputStack;
    long lastTakeTime;
    final Slot inputSlot0;
    final Slot inputSlot1;
    final Slot inputSlot2;
    final Slot inputSlot3;
    final Slot inputSlot4;
    final Slot inputSlot5;
    final Slot outputSlot;
    Runnable contentsChangedListener;
    public final Inventory input;
    final CraftingResultInventory output;

    public ArtisanTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public ArtisanTableScreenHandler(int syncId, PlayerInventory playerInventory, final ScreenHandlerContext context) {
        super(ModScreenHandlers.ARTISAN_SCREEN_HANDLER, syncId);
        this.selectedRecipe = Property.create();
        this.availableRecipes = Lists.newArrayList();
        this.inputStack = ItemStack.EMPTY;
        this.contentsChangedListener = () -> {
        };
        this.input = new SimpleInventory(6) {
            public void markDirty() {
                super.markDirty();
                ArtisanTableScreenHandler.this.onContentChanged(this);
                ArtisanTableScreenHandler.this.contentsChangedListener.run();
            }
        };
        this.output = new CraftingResultInventory();
        this.context = context;
        this.world = playerInventory.player.getWorld();

        this.inputSlot0 = this.addSlot(new Slot(this.input, 0, 31, 14));
        this.inputSlot1 = this.addSlot(new Slot(this.input, 1, 12, 34));
        this.inputSlot2 = this.addSlot(new Slot(this.input, 2, 31, 34));
        this.inputSlot3 = this.addSlot(new Slot(this.input, 3, 50, 34));
        this.inputSlot4 = this.addSlot(new Slot(this.input, 4, 21, 53));
        this.inputSlot5 = this.addSlot(new Slot(this.input, 5, 41, 53));
        this.outputSlot = this.addSlot(new Slot(this.output, 6, 184, 33) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack itemStack) {
                itemStack.onCraft(player.getWorld(), player, itemStack.getCount());
                ArtisanTableScreenHandler.this.output.unlockLastRecipe(player, this.getInputStacks());
                itemStack = ArtisanTableScreenHandler.this.inputSlot0.takeStack(1);
                ArtisanTableScreenHandler.this.inputSlot1.takeStack(1);
                ArtisanTableScreenHandler.this.inputSlot2.takeStack(1);
                ArtisanTableScreenHandler.this.inputSlot3.takeStack(1);
                ArtisanTableScreenHandler.this.inputSlot4.takeStack(1);
                ArtisanTableScreenHandler.this.inputSlot5.takeStack(1);

                if (!itemStack.isEmpty()) {
                    ArtisanTableScreenHandler.this.populateResult();
                }
                context.run((world, pos) -> {
                    long l = world.getTime();
                    if (ArtisanTableScreenHandler.this.lastTakeTime != l) {
                        world.playSound(null, (BlockPos)pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ArtisanTableScreenHandler.this.lastTakeTime = l;
                    }
                });
                super.onTakeItem(player, itemStack);
            }

            private List<ItemStack> getInputStacks() {
                return List.of(ArtisanTableScreenHandler.this.inputSlot0.getStack(),
                        ArtisanTableScreenHandler.this.inputSlot1.getStack(),
                        ArtisanTableScreenHandler.this.inputSlot2.getStack(),
                        ArtisanTableScreenHandler.this.inputSlot3.getStack(),
                        ArtisanTableScreenHandler.this.inputSlot4.getStack(),
                        ArtisanTableScreenHandler.this.inputSlot5.getStack());
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        this.addProperty(this.selectedRecipe);
    }

    public int getSelectedRecipe() {
        return this.selectedRecipe.get();
    }

    public List<ArtisanRecipe> getAvailableRecipes() {
        return this.availableRecipes;
    }

    public int getAvailableRecipeCount() {
        return this.availableRecipes.size();
    }

    public boolean canCraft() {
        return this.inputSlot0.hasStack() && !this.availableRecipes.isEmpty();
    }

    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModDecorativeBlocks.ARTISAN_TABLE);
    }

    public boolean onButtonClick(PlayerEntity player, int id) {
        if (this.isInBounds(id)) {
            this.selectedRecipe.set(id);
            this.populateResult();
        }
        return true;
    }

    private boolean isInBounds(int id) {
        return id >= 0 && id < this.availableRecipes.size();
    }

    public void onContentChanged(Inventory inventory) {
        ItemStack itemStack = this.inputSlot0.getStack();
        this.inputStack = itemStack.copy();
        this.updateInput(inventory, itemStack);
    }

    private void updateInput(Inventory input, ItemStack stack) {
        this.availableRecipes.clear();
        this.selectedRecipe.set(-1);
        this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
        if (!stack.isEmpty()) {
            this.availableRecipes = this.world.getRecipeManager().getAllMatches(ModRecipes.ARTISAN_TABLE, input, this.world);
        }
    }

    void populateResult() {
        if (!this.availableRecipes.isEmpty() && this.isInBounds(this.selectedRecipe.get())) {
            ArtisanRecipe artisanRecipe = (ArtisanRecipe)this.availableRecipes.get(this.selectedRecipe.get());
            ItemStack itemStack = artisanRecipe.craft(this.input, this.world.getRegistryManager());
            if (itemStack.isItemEnabled(this.world.getEnabledFeatures())) {
                this.output.setLastRecipe(artisanRecipe);
                this.outputSlot.setStackNoCallbacks(itemStack);
            } else {
                this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
            }
        } else {
            this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
        }
        this.sendContentUpdates();
    }

    public ScreenHandlerType<?> getType() {
        return ModScreenHandlers.ARTISAN_SCREEN_HANDLER;
    }

    public void setContentsChangedListener(Runnable contentsChangedListener) {
        this.contentsChangedListener = contentsChangedListener;
    }

    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
    }

    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack stack = ItemStack.EMPTY;
        Slot invSlot = this.slots.get(slot);

        if(invSlot.hasStack()) {
            ItemStack originalStack = invSlot.getStack();
            Item item = originalStack.getItem();
            stack = originalStack.copy();
            if (slot == 6){
                item.onCraft(originalStack, player.getWorld(), player);
                if (!this.insertItem(originalStack, 7, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                invSlot.onQuickTransfer(originalStack, stack);
            } else if(slot < this.input.size()) {
                if(!this.insertItem(originalStack, this.input.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.input.size(), false)) {
                return ItemStack.EMPTY;
            }
            if (originalStack.isEmpty()) {
                invSlot.setStack(ItemStack.EMPTY);
            } else {
                invSlot.markDirty();
            }

            invSlot.onTakeItem(player, originalStack);
            this.sendContentUpdates();
        }
        return stack;
    }

    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.output.removeStack(6);
        this.context.run((world, pos) -> {
            this.dropInventory(player, this.input);
        });
    }
    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 36 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 36 + i * 18, 142));
        }
    }
}
