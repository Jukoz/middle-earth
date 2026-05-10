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
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.gui.ModScreenHandlers;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.network.packets.S2C.InscriptionEnchantInfoPacket;
import net.sevenstars.middleearth.recipe.RecipesME;
import net.sevenstars.middleearth.recipe.inscription.InscriptionRecipe;
import net.sevenstars.middleearth.recipe.inscription.InscriptionWordBank;
import net.sevenstars.middleearth.sound.SoundsME;
import net.sevenstars.middleearth.utils.ItemTagsME;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InscriptionTableScreenHandler extends ScreenHandler {
    private final ScreenHandlerContext context;
    private final World world;
    public List<RecipeEntry<InscriptionRecipe>> outputRecipes;
    public RegistryEntry<Enchantment> enchant;
    public int level;
    public final Inventory input;

    public PlayerEntity player;

    private byte[] availableWords;
    public List<String> selectedWords;
    private final Property levelCost;

    private static final Identifier EMPTY_SLOT_CHISEL_TEXTURE = MiddleEarth.of("container/slot/chisel");

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
                return stack.isIn(ItemTagsME.EARLY_CHISELS);
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

        if(this.hasAll()){
            words.addAll(InscriptionWordBank.wordBank.get(catalyst.getItem()));
            words.addAll(InscriptionWordBank.wordBank.get(null));
        }
        return words;
    }

    public byte[] getAvailableWords() {
        return this.availableWords;
    }

    public void updateAvailableWords(byte[] wordsIndexes) {
        availableWords = wordsIndexes;
    }

    public void updateWords(boolean add, String word, boolean reset){
        boolean foundEnchant = false;
        RegistryEntry<Enchantment> resultEnchant = null;
        int resultLevel = 0;
        int resultMaxLevel = 0;
        int resultLevelCost = 0;

        if (!reset){
            if (add){
                if (this.selectedWords.isEmpty()){
                    world.playSound(null, this.player.getBlockPos(), SoundsME.CHISEL_HIT_FIRST, SoundCategory.BLOCKS, 1.0F, 0.95F + world.random.nextFloat() * 0.1F);
                } else if(this.selectedWords.size() == 1) {
                    world.playSound(null, this.player.getBlockPos(), SoundsME.CHISEL_HIT_SECOND, SoundCategory.BLOCKS, 1.0F, 0.95F + world.random.nextFloat() * 0.1F);
                }else if(this.selectedWords.size() == 2) {
                    world.playSound(null, this.player.getBlockPos(), SoundsME.CHISEL_HIT_THIRD, SoundCategory.BLOCKS, 1.0F, 0.95F + world.random.nextFloat() * 0.1F);
                }
                this.selectedWords.add(word);
            } else {
                this.selectedWords.remove(word);
            }
        }
        if (!this.outputRecipes.isEmpty()){
            for (RecipeEntry<InscriptionRecipe> recipe : this.outputRecipes){
                if (this.selectedWords.size() == 2 && recipe.value().inputWords.size() == 3 && add){
                    if (Objects.equals(this.selectedWords.get(1), recipe.value().inputWords.get(1))
                            && recipe.value().enchant.value().isAcceptableItem(input.getStack(2))
                            && this.selectedWords.get(0).equals(recipe.value().inputWords.get(0))){
                    }
                }
                if (recipe.value().inputWords.equals(this.selectedWords)){
                    if (canEnchant(input.getStack(2), recipe.value().enchant, recipe.value().level)){
                        foundEnchant = true;
                        resultEnchant = recipe.value().enchant;
                        resultLevel = recipe.value().level;
                        resultMaxLevel = recipe.value().enchant.value().getMaxLevel();
                        resultLevelCost = recipe.value().levelCost;
                    }
                }
            }

            List<String> words = new ArrayList<>();
            List<String> allowedWords = new ArrayList<>();
            if(this.hasAll()){
                words.addAll(InscriptionWordBank.wordBank.get(this.input.getStack(0).getItem()));
                words.addAll(InscriptionWordBank.wordBank.get(null));
            }

            availableWords = new byte[words.size() + 1];
            words.add(word);
            ServerRecipeManager serverRecipeManager = (ServerRecipeManager) this.world.getRecipeManager();
            List<RecipeEntry<InscriptionRecipe>> availableRecipes = serverRecipeManager.getAllOfType(RecipesME.INSCRIPTION_TABLE)
                    .stream().filter((inscriptionRecipeRecipeEntry -> {
                            if(!inscriptionRecipeRecipeEntry.value().enchant.value().isAcceptableItem(input.getStack(2))) return false;
                            if(selectedWords.isEmpty()) return true;
                            else return inscriptionRecipeRecipeEntry.value().inputWords.getFirst().equals(selectedWords.getFirst());
                    })).toList();

            for(RecipeEntry<InscriptionRecipe> recipe : availableRecipes) {
                List<String> recipeWords = recipe.value().inputWords;
                for(String availableWord : recipeWords) {
                    if(!allowedWords.contains(availableWord)) {
                        allowedWords.add(availableWord);
                    }
                }
            }

            int index = 0;
            for(String wordElement : words) {
                if(allowedWords.contains(wordElement) || selectedWords.contains(wordElement)) {
                    availableWords[index] = 1;
                }
                index++;
            }

            InscriptionEnchantInfoPacket newPacket;
            if (foundEnchant){
                newPacket = new InscriptionEnchantInfoPacket(resultEnchant.value().description().copy().getString(), resultLevel, resultMaxLevel, availableWords);
                this.enchant = resultEnchant;
                this.level = resultLevel;
                calculateCost(resultLevelCost, resultEnchant);
            } else {
                newPacket = new InscriptionEnchantInfoPacket("", 0, 0, availableWords);
                this.enchant = null;
                this.level = 0;
                this.levelCost.set(0);
            }
            ServerPlayNetworking.send((ServerPlayerEntity) player, newPacket);
        }
    }

    private void updateInput(Inventory inventory) {
        ItemStack inputChisel = inventory.getStack(1);
        if (this.hasAll()) {
             if (!this.world.isClient){
                updateWords(false, "", true);
                ServerRecipeManager serverRecipeManager = (ServerRecipeManager) this.world.getRecipeManager();
                this.outputRecipes = serverRecipeManager.getAllMatches(RecipesME.INSCRIPTION_TABLE, new SingleStackRecipeInput(inputChisel), this.world).toList();
             }
        } else {
            updateWords(false, "", true);
            this.outputRecipes = new ArrayList<>();
            this.selectedWords = new ArrayList<>();
        }
    }

    private boolean canEnchant(ItemStack stack, RegistryEntry<Enchantment> enchant, int level) {
        boolean acceptableItem = enchant.value().isAcceptableItem(stack);
        if (acceptableItem && EnchantmentHelper.isCompatible(stack.getEnchantments().getEnchantments(), enchant)){
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
            world.playSound(null, this.player.getBlockPos(), SoundsME.CHISEL_ENCHANT, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
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
