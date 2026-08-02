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
    private int availableWordsRevision = -1;
    public List<String> selectedWords;
    private final DataSlot levelCost;
    private final DataSlot selectionRevision;

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

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        this.context = context;
        this.world = playerInventory.player.level();

        this.levelCost = DataSlot.standalone();
        this.addDataSlot(this.levelCost);
        this.selectionRevision = DataSlot.standalone();
        this.addDataSlot(this.selectionRevision);

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    public int getLevelCost() {
        return this.levelCost.get();
    }

    public int getSelectionRevision() {
        return this.selectionRevision.get();
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

    public boolean updateAvailableWords(int revision, byte[] wordsIndexes) {
        if (revision < this.availableWordsRevision) {
            return false;
        }
        this.availableWordsRevision = revision;
        this.availableWords = wordsIndexes.clone();
        return true;
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
        RecipeHolder<InscriptionRecipe> recipe = findSelectedRecipe();
        return recipe != null
                && (player.hasInfiniteMaterials()
                || player.experienceLevel >= getLevelCost(recipe.value().levelCost, recipe.value().enchant));
    }

    public void updateWords(boolean add, String word, boolean reset){
        Holder<Enchantment> resultEnchant = null;
        int resultLevel = 0;
        int resultMaxLevel = 0;
        int resultLevelCost = 0;

        if (reset) {
            this.selectedWords.clear();
        } else {
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

        List<String> words = this.getWords();
        List<String> allowedWords = new ArrayList<>(this.selectedWords);
        this.availableWords = new byte[words.size()];

        for (RecipeHolder<InscriptionRecipe> recipe : this.outputRecipes) {
            List<String> recipeWords = recipe.value().inputWords;
            if (!words.containsAll(recipeWords)
                    || !hasSelectedPrefix(recipeWords)
                    || !canEnchant(this.input.getItem(2), recipe.value().enchant, recipe.value().level)) {
                continue;
            }

            if (recipeWords.equals(this.selectedWords)) {
                resultEnchant = recipe.value().enchant;
                resultLevel = recipe.value().level;
                resultMaxLevel = recipe.value().enchant.value().getMaxLevel();
                resultLevelCost = recipe.value().levelCost;
            } else {
                allowedWords.add(recipeWords.get(this.selectedWords.size()));
            }
        }

        for (int index = 0; index < words.size(); index++) {
            if (allowedWords.contains(words.get(index))) {
                this.availableWords[index] = 1;
            }
        }

        InscriptionEnchantInfoPacket packet;
        if (resultEnchant != null) {
            packet = new InscriptionEnchantInfoPacket(this.containerId, this.selectionRevision.get(), resultEnchant.value().description().copy().getString(), resultLevel, resultMaxLevel, this.availableWords);
            this.enchant = resultEnchant;
            this.level = resultLevel;
            calculateCost(resultLevelCost, resultEnchant);
        } else {
            packet = new InscriptionEnchantInfoPacket(this.containerId, this.selectionRevision.get(), "", 0, 0, this.availableWords);
            this.enchant = null;
            this.level = 0;
            this.levelCost.set(0);
        }
        PacketDistributor.sendToPlayer((ServerPlayer) this.player, packet);
    }

    private boolean hasSelectedPrefix(List<String> recipeWords) {
        if (this.selectedWords.size() > recipeWords.size()) {
            return false;
        }
        for (int index = 0; index < this.selectedWords.size(); index++) {
            if (!Objects.equals(this.selectedWords.get(index), recipeWords.get(index))) {
                return false;
            }
        }
        return true;
    }

    private void updateInput(Container inventory) {
        if (this.world.isClientSide) {
            return;
        }

        this.selectionRevision.set(this.selectionRevision.get() + 1);
        this.selectedWords.clear();
        if (this.hasAll()) {
            this.outputRecipes = this.world.getRecipeManager().getRecipesFor(
                    RecipesME.INSCRIPTION_TABLE,
                    new SingleRecipeInput(inventory.getItem(1)),
                    this.world
            );
        } else {
            this.outputRecipes = new ArrayList<>();
        }
        updateWords(false, "", true);
    }

    private RecipeHolder<InscriptionRecipe> findSelectedRecipe() {
        if (!this.hasAll()
                || this.selectedWords.isEmpty()
                || this.selectedWords.size() > MAX_SELECTED_WORDS) {
            return null;
        }

        List<String> words = this.getWords();
        if (!words.containsAll(this.selectedWords)) {
            return null;
        }

        for (RecipeHolder<InscriptionRecipe> recipe : this.world.getRecipeManager().getRecipesFor(
                RecipesME.INSCRIPTION_TABLE,
                new SingleRecipeInput(this.input.getItem(1)),
                this.world
        )) {
            if (recipe.value().inputWords.equals(this.selectedWords)
                    && words.containsAll(recipe.value().inputWords)
                    && canEnchant(this.input.getItem(2), recipe.value().enchant, recipe.value().level)) {
                return recipe;
            }
        }
        return null;
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
        RecipeHolder<InscriptionRecipe> recipe = findSelectedRecipe();
        if (recipe == null) {
            return;
        }

        int cost = getLevelCost(recipe.value().levelCost, recipe.value().enchant);
        if (!this.player.hasInfiniteMaterials() && this.player.experienceLevel < cost) {
            return;
        }

        ItemStack stackCatalyst = this.input.getItem(0);
        ItemStack stackChisel = this.input.getItem(1);
        ItemStack stack = this.input.getItem(2);

        if (!this.player.hasInfiniteMaterials()) {
            if (stackCatalyst.is(Items.LAPIS_LAZULI)) {
                this.input.setItem(0, ItemStack.EMPTY);
            } else {
                if (stackCatalyst.get(DataComponents.MAX_DAMAGE) == null) {
                    stackCatalyst.set(DataComponents.MAX_DAMAGE, 2);
                }
                damageOrConsumeInput(0, stackCatalyst);
            }

            if (!stackChisel.is(ToolItemsME.MITHRIL_CHISEL)) {
                damageOrConsumeInput(1, stackChisel);
            }
            this.player.giveExperienceLevels(-cost);
        }

        stack.enchant(recipe.value().enchant, recipe.value().level);
        this.levelCost.set(0);
        this.world.playSound(null, this.player.blockPosition(), SoundsME.CHISEL_ENCHANT, SoundSource.BLOCKS, 1.0F, this.world.random.nextFloat() * 0.1F + 0.9F);
        this.input.setChanged();
    }

    private void damageOrConsumeInput(int slot, ItemStack stack) {
        int nextDamage = stack.getDamageValue() + 1;
        if (nextDamage >= stack.getMaxDamage()) {
            this.input.setItem(slot, ItemStack.EMPTY);
        } else {
            stack.setDamageValue(nextDamage);
        }
    }

    public void calculateCost(int levelCost, Holder<Enchantment> enchant) {
        this.levelCost.set(getLevelCost(levelCost, enchant));
    }

    private int getLevelCost(int baseCost, Holder<Enchantment> enchant) {
        ItemStack itemStack = this.input.getItem(2);
        int k = 0;

        if (!itemStack.isEmpty()){
            ItemEnchantments.Mutable builder = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(itemStack));

            for (Holder<Enchantment> enchantEntry : builder.keySet()) {
                if (!enchantEntry.equals(enchant)) {
                    k++;
                }
            }
        }
        return baseCost + k;
    }

    public MenuType<?> getType() {
        return ModScreenHandlers.INSCRIPTION_SCREEN_HANDLER;
    }

    public ItemStack quickMoveStack(Player player, int slot) {
        if (slot < 0 || slot >= this.slots.size()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = ItemStack.EMPTY;
        Slot invSlot = this.slots.get(slot);

        if(invSlot.hasItem()) {
            ItemStack originalStack = invSlot.getItem();
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
