package net.sevenstars.middleearth.gui.inscriptiontable;

import com.mojang.authlib.GameProfile;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.special.forge.MultipleStackRecipeInput;
import net.sevenstars.middleearth.gui.ModScreenHandlers;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableInputsShape;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreenHandler;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableSlot;
import net.sevenstars.middleearth.recipe.InscriptionRecipe;
import net.sevenstars.middleearth.recipe.ModRecipes;
import net.sevenstars.middleearth.resources.datas.Disposition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InscriptionTableScreenHandler extends ScreenHandler {
    private final ScreenHandlerContext context;
    private final World world;
    private RecipeEntry<InscriptionRecipe> outputRecipe;
    private ItemStack inputStack;
    long lastTakeTime;
    private ArtisanTableSlot[][] inputSlots;
    Runnable contentsChangedListener;
    public final Inventory input;
    final CraftingResultInventory output;
    final Slot outputSlot;
    private PlayerEntity playerEntity;
    private ArtisanTableInputsShape inputsShape = null;

    public InscriptionTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);

    }

    public InscriptionTableScreenHandler(int syncId, PlayerInventory playerInventory, final ScreenHandlerContext context) {
        super(ModScreenHandlers.ARTISAN_SCREEN_HANDLER, syncId);
        this.inputStack = ItemStack.EMPTY;

        this.input = new SimpleInventory(7) {
            public void markDirty() {
                super.markDirty();
                InscriptionTableScreenHandler.this.updateInput(this);
            }
        };
        this.output = new CraftingResultInventory();

        this.addSlot(new Slot(this.input, 0, 47, 35));
        this.addSlot(new Slot(this.input, 1, 47, 15));
        this.addSlot(new Slot(this.input, 2, 68, 25));
        this.addSlot(new Slot(this.input, 3, 68, 45));
        this.addSlot(new Slot(this.input, 4, 47, 55));
        this.addSlot(new Slot(this.input, 5, 27, 45));
        this.addSlot(new Slot(this.input, 6, 27, 25));

        this.outputSlot = this.addSlot(new Slot(this.output, 7, 129, 35) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack itemStack) {
                itemStack.onCraftByPlayer(player, itemStack.getCount());

                for(int y = 0; y < 6; y++) {
                    slots.forEach(slot -> {
                        slot.takeStack(1);
                    });
                }

                long l = world.getTime();
                if (InscriptionTableScreenHandler.this.lastTakeTime != l) {
                    world.playSound(null, (BlockPos)player.getBlockPos(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    InscriptionTableScreenHandler.this.lastTakeTime = l;
                }

                super.onTakeItem(player, itemStack);
            }

            private List<ItemStack> getInputStacks() {
                return Arrays.stream(inputSlots)
                        .flatMap(slots -> Arrays.stream(slots).map(Slot::getStack))
                        .collect(Collectors.toList());
            }
        });

        this.context = context;
        this.world = playerInventory.player.getWorld();

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    public boolean canUse(PlayerEntity player) {
        this.playerEntity = player;
        return canUse(this.context, player, ModDecorativeBlocks.INSCRIPTION_TABLE);
    }


    private void updateInput(Inventory inventory) {
        List<ItemStack> inputs = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            inputs.add(inventory.getStack(i));
        }
        this.outputSlot.setStackNoCallbacks(ItemStack.EMPTY);
        if (!inputs.isEmpty()) {
            if (inputs.size() >= 2 && !this.world.isClient){
                ServerRecipeManager serverRecipeManager = (ServerRecipeManager) this.world.getRecipeManager();
                Optional<RecipeEntry<InscriptionRecipe>> optionalOutput = serverRecipeManager.getFirstMatch(ModRecipes.INSCRIPTION_TABLE, new MultipleStackRecipeInput(inputs), this.world);
                optionalOutput.ifPresent(inscriptionRecipeRecipeEntry -> this.outputRecipe = inscriptionRecipeRecipeEntry);
                populateResult();
            }
        }
    }

    void populateResult() {
        if (this.outputRecipe != null && this.outputRecipe.value() != null) {
            List<ItemStack> inputs = new ArrayList<>();
            for (int i = 0; i < this.input.size(); i++) {
                inputs.add(this.input.getStack(i));
            }

            ItemStack itemStack = outputRecipe.value().craft(new MultipleStackRecipeInput(inputs), this.world.getRegistryManager());

            if (itemStack.isItemEnabled(this.world.getEnabledFeatures())) {
                this.output.setLastRecipe(outputRecipe);
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
        return ModScreenHandlers.INSCRIPTION_SCREEN_HANDLER;
    }

    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
    }

    public ItemStack quickMove(PlayerEntity player, int slot) {
        this.playerEntity = player;
        ItemStack stack = ItemStack.EMPTY;
        Slot invSlot = this.slots.get(slot);

        if(invSlot.hasStack()) {
            ItemStack originalStack = invSlot.getStack();
            Item item = originalStack.getItem();
            stack = originalStack.copy();
            if (slot == 6){
                item.onCraft(originalStack, player.getWorld());
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
        this.dropInventory(player, this.input);
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
