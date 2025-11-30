package net.sevenstars.middleearth.gui.inscriptiontable;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.gui.ModScreenHandlers;
import net.sevenstars.middleearth.item.ToolItemsME;
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
    public RegistryEntry<Enchantment> enchant;
    public int level;
    public final Inventory input;

    public PlayerEntity player;

    public List<String> selectedWords;
    private final Property levelCost;

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

        this.addSlot(new Slot(this.input, 0, 135, 48) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isIn(ItemTagsME.INSCRIPTION_CATALYSTS) && !this.hasStack();
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.input, 1, 225, 48) {
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

        this.addSlot(new Slot(this.input, 2, 180, 48){
            @Override
            public boolean canInsert(ItemStack stack) {
                return (stack.isEnchantable() || stack.hasEnchantments()) && !stack.isOf(Items.BOOK);
            }
        });

        this.context = context;
        this.world = playerInventory.player.getWorld();

        this.levelCost = Property.create();
        this.addProperty(this.levelCost);

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    public int getLevelCost() {
        return this.levelCost.get();
    }

    public int getPlayerLevels(){
        return this.player.experienceLevel;
    }

    public boolean hasGem(){
        return !this.input.getStack(0).isEmpty();
    }

    public boolean hasChisel(){
        return !this.input.getStack(1).isEmpty();
    }

    public boolean hasInput(){
        return !this.input.getStack(2).isEmpty();
    }

    public boolean hasAll(){
        return this.hasGem() && this.hasChisel() && this.hasInput();
    }

    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModDecorativeBlocks.INSCRIPTION_TABLE);
    }

    public List<String> getWords(){
        ItemStack catalyst = this.input.getStack(0);

        List<String> words = new ArrayList<>();

        if (this.hasAll()){
            words.addAll(InscriptionWordBank.wordBank.get(catalyst.getItem()));
            words.addAll(InscriptionWordBank.wordBank.get(null));
        }

        return words;
    }

    public void updateWords(boolean add, String word, boolean reset){
        boolean foundEnchant = false;
        RegistryEntry<Enchantment> resultEnchant = null;
        int resultLevel = 0;
        int resultMaxLevel = 0;
        int resultLevelCost = 0;

        if (!reset){
            if (add){
                this.selectedWords.add(word);
            } else {
                this.selectedWords.remove(word);
            }
        }
        if (!this.outputRecipes.isEmpty()){
            System.out.println("current recipes: " + this.outputRecipes);
            System.out.println("current words: " + this.selectedWords);
            for (RecipeEntry<InscriptionRecipe> recipe : this.outputRecipes){
                if (recipe.value().inputWords.equals(this.selectedWords)){
                    if (canEnchant(input.getStack(2), recipe.value().enchant, recipe.value().level)){
                        foundEnchant = true;
                        resultEnchant = recipe.value().enchant;
                        resultLevel = recipe.value().level;
                        resultMaxLevel = recipe.value().enchant.value().getMaxLevel();
                        resultLevelCost = recipe.value().levelCost;
                        System.out.println("enchant: " + resultEnchant.toString() + " " + resultLevel + " " + resultLevelCost);
                    }
                }
            }

            InscriptionEnchantInfoPacket newPacket;
            if (foundEnchant){
                newPacket = new InscriptionEnchantInfoPacket(Enchantment.getName(resultEnchant, resultLevel).getString(), resultLevel, resultMaxLevel);
                this.enchant = resultEnchant;
                this.level = resultLevel;
                calculateCost(resultLevelCost, resultEnchant);
            } else {
                newPacket = new InscriptionEnchantInfoPacket("", 0, 0);
                this.enchant = null;
                this.level = 0;
                this.levelCost.set(0);
            }
            ServerPlayNetworking.send((ServerPlayerEntity) player, newPacket);
        }
    }

    private void updateInput(Inventory inventory) {
        ItemStack inputChisel = inventory.getStack(1);
        System.out.println("update input");
        if (this.hasAll()) {
             if (!this.world.isClient){
                updateWords(false, "", true);
                ServerRecipeManager serverRecipeManager = (ServerRecipeManager) this.world.getRecipeManager();
                this.outputRecipes = serverRecipeManager.getAllMatches(ModRecipes.INSCRIPTION_TABLE, new SingleStackRecipeInput(inputChisel), this.world).toList();
            }
        } else {
            System.out.println("missing item");
            updateWords(false, "", true);
            this.outputRecipes = new ArrayList<>();
            this.selectedWords = new ArrayList<>();
        }
    }

    private boolean canEnchant(ItemStack stack, RegistryEntry<Enchantment> enchant, int level){
        if (EnchantmentHelper.isCompatible(stack.getEnchantments().getEnchantments(), enchant) && enchant.value().isAcceptableItem(stack)){
            return stack.getEnchantments().getLevel(enchant) == level - 1;
        } else {
            if (stack.getEnchantments().getEnchantments().contains(enchant)){
                return stack.getEnchantments().getLevel(enchant) == level - 1;
            } else {
                return false;
            }
        }
    }

    public void enchantItem(){
        ItemStack stackCatalyst = this.input.getStack(0);
        ItemStack stackChisel = this.input.getStack(1);
        ItemStack stack = this.input.getStack(2);

        if(stackCatalyst.get(DataComponentTypes.MAX_DAMAGE) == null){
            if (stackCatalyst.isOf(Items.LAPIS_LAZULI)) this.input.setStack(0, ItemStack.EMPTY);
            stackCatalyst.set(DataComponentTypes.MAX_DAMAGE, 2);
            stackCatalyst.setDamage(stackCatalyst.getDamage() + 1);
        } else {
            if (stackCatalyst.getDamage() == stackCatalyst.getMaxDamage()){
                this.input.setStack(0, ItemStack.EMPTY);
            } else {
                stackCatalyst.setDamage(stackCatalyst.getDamage() + 1);
            }
        }

        if ((stackChisel.getDamage() == stackChisel.getMaxDamage()) && !stackChisel.isOf(ToolItemsME.MITHRIL_CHISEL)){
            this.input.setStack(1, ItemStack.EMPTY);
        } else {
            stackChisel.setDamage(stackChisel.getDamage() + 1);
        }

        if (this.enchant != null && this.level != 0){
            if (!player.isInCreativeMode()) {
                player.addExperienceLevels(-this.levelCost.get());
            }
            this.levelCost.set(0);

            stack.addEnchantment(this.enchant, this.level);
            world.playSound(null, this.player.getBlockPos(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
        }

        updateWords(false, "", true);
    }

    public void calculateCost(int levelCost, RegistryEntry<Enchantment> enchant) {
        ItemStack itemStack = this.input.getStack(2);
        int k = 0;

        if (!itemStack.isEmpty()){
            ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(EnchantmentHelper.getEnchantments(itemStack));

            for (RegistryEntry<Enchantment> enchantEntry : builder.getEnchantments()) {
                if (enchantEntry != enchant) {
                    k++;
                }
            }
            this.levelCost.set(levelCost + k);
        }
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
                if (!this.insertItem(originalStack, 4, this.slots.size(), true)) {
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
