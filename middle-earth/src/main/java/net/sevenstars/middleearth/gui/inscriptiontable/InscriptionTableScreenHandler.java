package net.sevenstars.middleearth.gui.inscriptiontable;

import com.mojang.datafixers.util.Pair;
import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
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

public class InscriptionTableScreenHandler extends AbstractContainerMenu {
    public static final int MAX_SELECTED_WORDS = 3;
    public static final int MAX_WORD_LENGTH = 32;

    private final ContainerLevelAccess context;
    private final Level world;
    public List<RecipeHolder<InscriptionRecipe>> outputRecipes;
    public Holder<Enchantment> enchant;
    public int level;
    public final Container input;

    public Player player;

    private byte[] availableWords;
    public List<String> selectedWords;
    private final DataSlot levelCost;

    private static final ResourceLocation EMPTY_SLOT_CHISEL_TEXTURE = MiddleEarth.of("container/slot/chisel");

    public InscriptionTableScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, ContainerLevelAccess.NULL);
    }

    public InscriptionTableScreenHandler(int syncId, Inventory playerInventory, final ContainerLevelAccess context) {
        super(ModScreenHandlers.INSCRIPTION_SCREEN_HANDLER, syncId);

        this.outputRecipes = new ArrayList<>();
        this.selectedWords = new ArrayList<>();

        this.player = playerInventory.player;

        this.input = new SimpleContainer(3) {
            @Override
            public void setChanged() {
                super.setChanged();
                InscriptionTableScreenHandler.this.updateInput(input);
            }
        };

        this.addSlot(new Slot(this.input, 0, 135, 48) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ItemTagsME.INSCRIPTION_CATALYSTS) && !this.hasItem();
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.input, 1, 225, 48) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ItemTagsME.EARLY_CHISELS);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SLOT_CHISEL_TEXTURE);
            }
        });

        this.addSlot(new Slot(this.input, 2, 180, 48){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return (stack.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "enchantable/bow"))) ||
                        stack.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "enchantable/crossbow")))
                        || stack.isEnchantable() || stack.isEnchanted()) && !stack.is(Items.BOOK);
            }
        });

        this.context = context;
        this.world = playerInventory.player.level();

        this.levelCost = DataSlot.standalone();
        this.addDataSlot(this.levelCost);

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
        return !this.input.getItem(0).isEmpty();
    }

    public boolean hasChisel(){
        return !this.input.getItem(1).isEmpty();
    }

    public boolean hasInput(){
        return !this.input.getItem(2).isEmpty();
    }

    public boolean hasAll(){
        return this.hasGem() && this.hasChisel() && this.hasInput();
    }

    public boolean stillValid(Player player) {
        return stillValid(this.context, player, ModDecorativeBlocks.INSCRIPTION_TABLE);
    }

    public List<String> getWords(){
        ItemStack catalyst = this.input.getItem(0);

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

    public boolean canApplyWordUpdate(boolean add, String word) {
        if (word == null
                || word.isBlank()
                || word.length() > MAX_WORD_LENGTH
                || selectedWords.size() > MAX_SELECTED_WORDS
                || !getWords().contains(word)) {
            return false;
        }
        return add
                ? selectedWords.size() < MAX_SELECTED_WORDS && !selectedWords.contains(word)
                : selectedWords.contains(word);
    }

    public boolean canConfirmSelection() {
        return hasAll()
                && enchant != null
                && level > 0
                && !selectedWords.isEmpty()
                && selectedWords.size() <= MAX_SELECTED_WORDS
                && (player.hasInfiniteMaterials() || player.experienceLevel >= levelCost.get());
    }

    public void updateWords(boolean add, String word, boolean reset){
        boolean foundEnchant = false;
        Holder<Enchantment> resultEnchant = null;
        int resultLevel = 0;
        int resultMaxLevel = 0;
        int resultLevelCost = 0;

        if (!reset){
            if (add){
                if (this.selectedWords.isEmpty()){
                    world.playSound(null, this.player.blockPosition(), SoundsME.CHISEL_HIT_FIRST, SoundSource.BLOCKS, 1.0F, 0.95F + world.random.nextFloat() * 0.1F);
                } else if(this.selectedWords.size() == 1) {
                    world.playSound(null, this.player.blockPosition(), SoundsME.CHISEL_HIT_SECOND, SoundSource.BLOCKS, 1.0F, 0.95F + world.random.nextFloat() * 0.1F);
                }else if(this.selectedWords.size() == 2) {
                    world.playSound(null, this.player.blockPosition(), SoundsME.CHISEL_HIT_THIRD, SoundSource.BLOCKS, 1.0F, 0.95F + world.random.nextFloat() * 0.1F);
                }
                this.selectedWords.add(word);
            } else {
                this.selectedWords.remove(word);
            }
        }
        if (!this.outputRecipes.isEmpty()){
            for (RecipeHolder<InscriptionRecipe> recipe : this.outputRecipes){
                if (this.selectedWords.size() == 2 && recipe.value().inputWords.size() == 3 && add){
                    if (Objects.equals(this.selectedWords.get(1), recipe.value().inputWords.get(1))
                            && recipe.value().enchant.value().canEnchant(input.getItem(2))
                            && this.selectedWords.get(0).equals(recipe.value().inputWords.get(0))){
                    }
                }
                if (recipe.value().inputWords.equals(this.selectedWords)){
                    if (canEnchant(input.getItem(2), recipe.value().enchant, recipe.value().level)){
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
                words.addAll(InscriptionWordBank.wordBank.get(this.input.getItem(0).getItem()));
                words.addAll(InscriptionWordBank.wordBank.get(null));
            }

            availableWords = new byte[words.size() + 1];
            words.add(word);
            RecipeManager serverRecipeManager = this.world.getRecipeManager();
            List<RecipeHolder<InscriptionRecipe>> availableRecipes = serverRecipeManager.getAllRecipesFor(RecipesME.INSCRIPTION_TABLE)
                    .stream().filter((inscriptionRecipeRecipeEntry -> {
                            if(!inscriptionRecipeRecipeEntry.value().enchant.value().canEnchant(input.getItem(2))) return false;
                            if(selectedWords.isEmpty()) return true;
                            else return inscriptionRecipeRecipeEntry.value().inputWords.getFirst().equals(selectedWords.getFirst());
                    })).toList();

            for(RecipeHolder<InscriptionRecipe> recipe : availableRecipes) {
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
            PacketDistributor.sendToPlayer((ServerPlayer) player, newPacket);
        }
    }

    private void updateInput(Container inventory) {
        ItemStack inputChisel = inventory.getItem(1);
        if (this.hasAll()) {
             if (!this.world.isClientSide){
                updateWords(false, "", true);
                RecipeManager serverRecipeManager = this.world.getRecipeManager();
                this.outputRecipes = serverRecipeManager.getRecipesFor(
                        RecipesME.INSCRIPTION_TABLE,
                        new SingleRecipeInput(inputChisel),
                        this.world
                );
             }
        } else {
            updateWords(false, "", true);
            this.outputRecipes = new ArrayList<>();
            this.selectedWords = new ArrayList<>();
        }
    }

    private boolean canEnchant(ItemStack stack, Holder<Enchantment> enchant, int level) {
        boolean acceptableItem = enchant.value().canEnchant(stack);
        if (acceptableItem && EnchantmentHelper.isEnchantmentCompatible(stack.getEnchantments().keySet(), enchant)){
            return stack.getEnchantments().getLevel(enchant) == level - 1;
        } else {
            if (stack.getEnchantments().keySet().contains(enchant)){
                return stack.getEnchantments().getLevel(enchant) == level - 1;
            } else {
                return false;
            }
        }
    }

    public void enchantItem(){
        if (!canConfirmSelection()) {
            return;
        }
        ItemStack stackCatalyst = this.input.getItem(0);
        ItemStack stackChisel = this.input.getItem(1);
        ItemStack stack = this.input.getItem(2);

        if(stackCatalyst.get(DataComponents.MAX_DAMAGE) == null){
            if (stackCatalyst.is(Items.LAPIS_LAZULI)) this.input.setItem(0, ItemStack.EMPTY);
            stackCatalyst.set(DataComponents.MAX_DAMAGE, 2);
            stackCatalyst.setDamageValue(stackCatalyst.getDamageValue() + 1);
        } else {
            if (stackCatalyst.getDamageValue() == stackCatalyst.getMaxDamage()){
                this.input.setItem(0, ItemStack.EMPTY);
            } else {
                stackCatalyst.setDamageValue(stackCatalyst.getDamageValue() + 1);
            }
        }

        if ((stackChisel.getDamageValue() == stackChisel.getMaxDamage()) && !stackChisel.is(ToolItemsME.MITHRIL_CHISEL)){
            this.input.setItem(1, ItemStack.EMPTY);
        } else {
            stackChisel.setDamageValue(stackChisel.getDamageValue() + 1);
        }

        if (this.enchant != null && this.level != 0){
            if (!player.hasInfiniteMaterials()) {
                player.giveExperienceLevels(-this.levelCost.get());
            }
            this.levelCost.set(0);

            stack.enchant(this.enchant, this.level);
            world.playSound(null, this.player.blockPosition(), SoundsME.CHISEL_ENCHANT, SoundSource.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
        }

        updateWords(false, "", true);
    }

    public void calculateCost(int levelCost, Holder<Enchantment> enchant) {
        ItemStack itemStack = this.input.getItem(2);
        int k = 0;

        if (!itemStack.isEmpty()){
            ItemEnchantments.Mutable builder = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(itemStack));

            for (Holder<Enchantment> enchantEntry : builder.keySet()) {
                if (enchantEntry != enchant) {
                    k++;
                }
            }
            this.levelCost.set(levelCost + k);
        }
    }

    public MenuType<?> getType() {
        return ModScreenHandlers.INSCRIPTION_SCREEN_HANDLER;
    }

    public ItemStack quickMoveStack(Player player, int slot) {
        ItemStack stack = ItemStack.EMPTY;
        Slot invSlot = this.slots.get(slot);

        if(invSlot.hasItem()) {
            ItemStack originalStack = invSlot.getItem();
            Item item = originalStack.getItem();
            stack = originalStack.copy();
            if(slot < this.input.getContainerSize()) {
                if(!this.moveItemStackTo(originalStack, this.input.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 0, this.input.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }
            if (originalStack.isEmpty()) {
                invSlot.setByPlayer(ItemStack.EMPTY);
            } else {
                invSlot.setChanged();
            }

            invSlot.onTake(player, originalStack);
            this.broadcastChanges();
        }
        return stack;
    }

    public void removed(Player player) {
        super.removed(player);
        this.clearContainer(player, this.input);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 108 + j * 18, 102 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 108 + i * 18, 160));
        }
    }
}
