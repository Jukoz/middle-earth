package net.sevenstars.middleearth.gui.inscriptiontable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.special.forge.MultipleStackRecipeInput;
import net.sevenstars.middleearth.gui.ModScreenHandlers;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableInputsShape;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableSlot;
import net.sevenstars.middleearth.recipe.inscription.InscriptionRecipe;
import net.sevenstars.middleearth.recipe.ModRecipes;
import net.sevenstars.middleearth.recipe.inscription.InscriptionWordBank;
import net.sevenstars.middleearth.utils.IdentifierUtil;
import net.sevenstars.middleearth.utils.ItemTagsME;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InscriptionTableScreenHandler extends ScreenHandler {
    private final ScreenHandlerContext context;
    private final World world;
    private RecipeEntry<InscriptionRecipe> outputRecipe;
    public final Inventory input;
    final CraftingResultInventory output;

    private static final Identifier EMPTY_SLOT_LAPIS_LAZULI_TEXTURE = Identifier.ofVanilla("container/slot/lapis_lazuli");
    private static final Identifier EMPTY_SLOT_CHISEL_TEXTURE = IdentifierUtil.create("container/slot/chisel_slot");

    public InscriptionTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public InscriptionTableScreenHandler(int syncId, PlayerInventory playerInventory, final ScreenHandlerContext context) {
        super(ModScreenHandlers.ARTISAN_SCREEN_HANDLER, syncId);

        this.input = new SimpleInventory(3) {
            public void markDirty() {
                super.markDirty();
                InscriptionTableScreenHandler.this.updateInput(this);
            }
        };
        this.output = new CraftingResultInventory();

        this.addSlot(new Slot(this.input, 0, 135, 53){
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isIn(ItemTagsME.INSCRIPTION_CATALYSTS);
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }

            public Identifier getBackgroundSprite() {
                return EMPTY_SLOT_LAPIS_LAZULI_TEXTURE;
            }

        });
        this.addSlot(new Slot(this.input, 1, 225, 53){
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isIn(ItemTagsME.CHISELS);
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }

            public Identifier getBackgroundSprite() {
                return EMPTY_SLOT_CHISEL_TEXTURE;
            }
        });

        this.addSlot(new Slot(this.input, 2, 180, 53));

        this.context = context;
        this.world = playerInventory.player.getWorld();

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModDecorativeBlocks.INSCRIPTION_TABLE);
    }

    public List<String> getWords(){
        ItemStack catalyst = this.input.getStack(0);

        List<String> words = new ArrayList<>();

        if (!catalyst.isEmpty()){
            words.addAll(InscriptionWordBank.wordBank.get(catalyst.getItem()));
            words.addAll(InscriptionWordBank.wordBank.get(null));
        }

        return words;
    }

    public boolean hasGem(){
         return !this.input.getStack(0).isEmpty();
    }

    private void updateInput(Inventory inventory) {
        List<ItemStack> inputs = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            inputs.add(inventory.getStack(i));
        }
        if (!inputs.isEmpty()) {
            if (inputs.size() == 3 && !this.world.isClient){
                ServerRecipeManager serverRecipeManager = (ServerRecipeManager) this.world.getRecipeManager();
                Optional<RecipeEntry<InscriptionRecipe>> optionalOutput = serverRecipeManager.getFirstMatch(ModRecipes.INSCRIPTION_TABLE, new MultipleStackRecipeInput(inputs), this.world);
                optionalOutput.ifPresent(inscriptionRecipeRecipeEntry -> {
                    System.out.println(inscriptionRecipeRecipeEntry.value().inputWords);
                });
                optionalOutput.ifPresent(inscriptionRecipeRecipeEntry -> inventory.getStack(2).addEnchantment(inscriptionRecipeRecipeEntry.value().enchant, inscriptionRecipeRecipeEntry.value().level));
            }
        }
    }

    void populateResult() {
        /*if (this.outputRecipe != null && this.outputRecipe.value() != null) {
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
        }*/
        this.sendContentUpdates();
    }


    public ScreenHandlerType<?> getType() {
        return ModScreenHandlers.INSCRIPTION_SCREEN_HANDLER;
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
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 108 + j * 18, 102 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 108 + i * 18, 160));
        }
    }
}
