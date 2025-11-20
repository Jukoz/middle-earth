package net.sevenstars.middleearth.gui.inscriptiontable;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.gui.ModScreenHandlers;
import net.sevenstars.middleearth.network.packets.S2C.InscriptionEnchantInfoPacket;
import net.sevenstars.middleearth.recipe.ModRecipes;
import net.sevenstars.middleearth.recipe.inscription.InscriptionRecipe;
import net.sevenstars.middleearth.recipe.inscription.InscriptionWordBank;
import net.sevenstars.middleearth.utils.IdentifierUtil;
import net.sevenstars.middleearth.utils.ItemTagsME;

import java.util.ArrayList;
import java.util.List;

public class InscriptionTableScreenHandler extends ScreenHandler {
    private final ScreenHandlerContext context;
    private final World world;
    public List<RecipeEntry<InscriptionRecipe>> outputRecipes;
    public final Inventory input;

    public PlayerEntity player;

    public List<String> selectedWords;

    private static final Identifier EMPTY_SLOT_CHISEL_TEXTURE = IdentifierUtil.create("container/slot/chisel");

    public InscriptionTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public InscriptionTableScreenHandler(int syncId, PlayerInventory playerInventory, final ScreenHandlerContext context) {
        super(ModScreenHandlers.INSCRIPTION_SCREEN_HANDLER, syncId);

        this.outputRecipes = new ArrayList<>();
        this.selectedWords = new ArrayList<>();

        this.player = playerInventory.player;

        this.input = new SimpleInventory(3) {
            @Override
            public void markDirty() {
                super.markDirty();
                InscriptionTableScreenHandler.this.updateInput(input);
            }
        };

        this.addSlot(new Slot(this.input, 0, 135, 53) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isIn(ItemTagsME.INSCRIPTION_CATALYSTS);
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.input, 1, 225, 53) {
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

    public void updateWords(boolean add, String word){
        boolean foundEnchant = false;
        RegistryEntry<Enchantment> resultEnchant = null;
        int resultLevel = 0;

        if (add){
            this.selectedWords.add(word);
        } else {
            this.selectedWords.remove(word);
        }
        if (!this.outputRecipes.isEmpty()){
            for (RecipeEntry<InscriptionRecipe> recipe : this.outputRecipes){
                System.out.println("------------------------");
                System.out.println("recipes: " + recipe);
                System.out.println("input words: " + recipe.value().inputWords);
                System.out.println("selected words: " + this.selectedWords);
                if (recipe.value().inputWords.equals(this.selectedWords)){
                    foundEnchant = true;
                    resultEnchant = recipe.value().enchant;
                    resultLevel = recipe.value().level;
                }
            }

            InscriptionEnchantInfoPacket newPacket;
            if (foundEnchant){
                newPacket = new InscriptionEnchantInfoPacket(Enchantment.getName(resultEnchant, resultLevel).getString(), resultLevel);
                System.out.println("enchant name: " + Enchantment.getName(resultEnchant, resultLevel).getString());
            } else {
                newPacket = new InscriptionEnchantInfoPacket("", 0);
                System.out.println("none found");
            }
            ServerPlayNetworking.send((ServerPlayerEntity) player, newPacket);
        }
    }

    private void updateInput(Inventory inventory) {
        ItemStack input = inventory.getStack(1);
        if (!this.world.isClient){
            if (!input.isEmpty() || !inventory.getStack(0).isEmpty() || !inventory.getStack(2).isEmpty()) {
                ServerRecipeManager serverRecipeManager = (ServerRecipeManager) this.world.getRecipeManager();
                this.outputRecipes = serverRecipeManager.getAllMatches(ModRecipes.INSCRIPTION_TABLE, new SingleStackRecipeInput(input), this.world).toList();
            } else {
                this.outputRecipes = new ArrayList<>();
                this.selectedWords = new ArrayList<>();
            }
        }
    }

    public boolean hasGem(){
         return !this.input.getStack(0).isEmpty();
    }

    public boolean hasChisel(){
        return !this.input.getStack(1).isEmpty();
    }

    public ScreenHandlerType<?> getType() {
        return ModScreenHandlers.INSCRIPTION_SCREEN_HANDLER;
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
