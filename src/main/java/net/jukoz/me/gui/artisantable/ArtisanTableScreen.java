package net.jukoz.me.gui.artisantable;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModToolItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.network.packets.C2S.ArtisanTableTabPacket;
import net.jukoz.me.recipe.ArtisanRecipe;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Environment(value= EnvType.CLIENT)
public class ArtisanTableScreen extends HandledScreen<ArtisanTableScreenHandler> implements ScreenHandlerListener {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/artisan_table.png");

    private float scrollAmount;
    private boolean mouseClicked;
    private int scrollOffset;
    private boolean canCraft;

    private static final Vector3f field_45497 = new Vector3f();
    private static final Quaternionf ARMOR_STAND_ROTATION = new Quaternionf().rotationXYZ(0.43633232f, 0.0f, (float)Math.PI);

    private ArmorStandEntity armorStand;
    private final List<ArtisanTableTab> categories = new ArrayList<>();
    private final HashMap<Integer, List<ArtisanTableTab>> tabs = new HashMap<>();
    @Nullable
    private ArtisanTableTab selectedCategory;
    private ArtisanTableTab selectedTab;

    public ArtisanTableScreen(ArtisanTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 232;
        this.backgroundHeight = 166;
        handler.setContentsChangedListener(this::onInventoryChange);

        int index = 0;
        categories.add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.LEFT, index, Text.of("Weapons"), ModWeaponItems.GONDORIAN_NOBLE_LONGSWORD.getDefaultStack()));
        tabs.put(index, new ArrayList<>());
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 0, Text.of("Swords"), ModWeaponItems.STEEL_SWORD.getDefaultStack()));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 1, Text.of("Axe"), ModToolItems.STEEL_AXE.getDefaultStack()));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 2, Text.of("Spear"), ModWeaponItems.STEEL_SPEAR.getDefaultStack()));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 3, Text.of("Bow"), ModWeaponItems.GONDORIAN_BOW.getDefaultStack()));
        index++;

        categories.add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.LEFT, index, Text.of("Tools"), ModToolItems.MITHRIL_PICKAXE.getDefaultStack()));
        tabs.put(index, new ArrayList<>());
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 0, Text.of("Pickaxe"), ModToolItems.STEEL_PICKAXE.getDefaultStack()));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 1, Text.of("Shovel"), ModToolItems.STEEL_SHOVEL.getDefaultStack()));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 2, Text.of("Hoe"), ModToolItems.STEEL_HOE.getDefaultStack()));
        index++;

        categories.add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.LEFT, index, Text.of("Armors"), ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE.getDefaultStack()));
        tabs.put(index, new ArrayList<>());
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 0, Text.of("Helmet"), ModEquipmentItems.RAVENHILL_WATCHWARDEN_HELMET.getDefaultStack()));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 1, Text.of("Chestplate"), ModEquipmentItems.RAVENHILL_WATCHWARDEN_CHESTPLATE.getDefaultStack()));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 2, Text.of("Leggings"), ModEquipmentItems.RAVENHILL_WATCHWARDEN_LEGGINGS.getDefaultStack()));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 3, Text.of("Boots"), ModEquipmentItems.RAVENHILL_WATCHWARDEN_BOOTS.getDefaultStack()));
        index++;

        categories.add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.LEFT, index, Text.of("Shields"), ModEquipmentItems.URUK_HAI_WHITE_HAND_SHIELD.getDefaultStack()));
        tabs.put(index, new ArrayList<>());
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 0, Text.of("Light Shields"), ModEquipmentItems.ROUND_SHIELD.getDefaultStack()));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 1, Text.of("Heavy Shields"), ModEquipmentItems.URUK_HAI_SHIELD.getDefaultStack()));
        index++;

        categories.add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.LEFT, index, Text.of("Misc"), ModEquipmentItems.STRAW_HAT.getDefaultStack()));
        tabs.put(index, new ArrayList<>());
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 0, Text.of("Hats"), ModEquipmentItems.STRAW_HAT.getDefaultStack()));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 1, Text.of("Hoods"), ModEquipmentItems.FUR_HOOD.getDefaultStack()));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 2, Text.of("Cloaks"), ModEquipmentItems.FUR_CLOAK.getDefaultStack()));
        index++;

        selectedCategory = categories.getFirst();
        selectedTab = tabs.get(selectedCategory.getIndex()).getFirst();
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        titleY -= 1;

        (this.handler).addListener(this);

        this.armorStand = new ArmorStandEntity(this.client.world, 0.0, 0.0, 0.0);
        this.armorStand.setHideBasePlate(true);
        this.armorStand.setShowArms(true);
        this.armorStand.bodyYaw = 210.0f;
        this.armorStand.setPitch(25.0f);
        this.armorStand.headYaw = this.armorStand.getYaw();
        this.armorStand.prevHeadYaw = this.armorStand.getYaw();
        this.equipArmorStand((this.handler).getSlot(6).getStack());
    }


    @Override
    public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
        if (slotId == 6) {
            this.equipArmorStand(stack);
        }
    }

    @Override
    public void onPropertyUpdate(ScreenHandler handler, int property, int value) {

    }

    private void equipArmorStand(ItemStack stack) {
        if (this.armorStand == null) {
            return;
        }
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            this.armorStand.equipStack(equipmentSlot, ItemStack.EMPTY);
        }
        if (!stack.isEmpty()) {
            ItemStack itemStack = stack.copy();
            Item item = stack.getItem();
            if (item instanceof ArmorItem) {
                ArmorItem armorItem = (ArmorItem)item;
                this.armorStand.equipStack(armorItem.getSlotType(), itemStack);
            } else {
                this.armorStand.equipStack(EquipmentSlot.OFFHAND, itemStack);
            }
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = this.x;
        int j = this.y;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        int k = (int)(41.0F * this.scrollAmount);
        context.drawTexture(TEXTURE, i + 143, j + 15 + k, 232 + (this.shouldScroll() ? 0 : 12), 0, 12, 15);
        int l = this.x + 76;
        int m = this.y + 14;
        int n = this.scrollOffset + 12;

        for(int w = 0; w < 3; w++) {
            for(int z = 0; z < 3; z++) {
                if(handler.slots.get(w*3 + z).isEnabled()) {
                    context.drawTexture(TEXTURE,x + 11 + 19*z,y + 14 + 19*w, 232, 15, 18, 18);
                }
            }
        }

        this.renderRecipeBackground(context, mouseX, mouseY, l, m, n);
        this.renderRecipeIcons(context, l, m, n);
        InventoryScreen.drawEntity(context, this.x + 206, this.y + 75, 30.0f, field_45497, ARMOR_STAND_ROTATION, null, this.armorStand);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);

        super.render(context, mouseX, mouseY, delta);

        for (ArtisanTableTab category : this.categories) {
            category.drawBackground(context, x, y, category == this.selectedCategory);
            category.drawIcon(context, x, y);
        }
        for (ArtisanTableTab tab : this.tabs.get(selectedCategory.getIndex())) {
            tab.drawBackground(context, x, y, tab == this.selectedTab);
            tab.drawIcon(context, x, y);
        }

        for (ArtisanTableTab category : this.categories) {
            if (!category.isClickOnTab(x, y, mouseX, mouseY)) continue;
            context.drawTooltip(this.textRenderer, category.getTitle(), mouseX, mouseY);
        }
        for (ArtisanTableTab tab : this.tabs.get(selectedCategory.getIndex())) {
            if (!tab.isClickOnTab(x, y, mouseX, mouseY)) continue;
            context.drawTooltip(this.textRenderer, tab.getTitle(), mouseX, mouseY);
        }
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
        super.drawMouseoverTooltip(context, x, y);
        if (this.canCraft) {
            int i = this.x + 76;
            int j = this.y + 14;
            int k = this.scrollOffset + 12;
            List<RecipeEntry<ArtisanRecipe>> list = (this.handler).getAvailableRecipes();

            for(int l = this.scrollOffset; l < k && l < (this.handler).getAvailableRecipeCount(); ++l) {
                int m = l - this.scrollOffset;
                int n = i + m % 4 * 16;
                int o = j + m / 4 * 18 + 2;
                if (x >= n && x < n + 16 && y >= o && y < o + 18) {
                    context.drawItemTooltip(this.textRenderer, list.get(l).value().getResult(this.client.world.getRegistryManager()), x, y);
                }
            }
        }
    }

    private void renderRecipeBackground(DrawContext context, int mouseX, int mouseY, int x, int y, int scrollOffset) {
        for(int i = this.scrollOffset; i < scrollOffset && i < ((ArtisanTableScreenHandler)this.handler).getAvailableRecipeCount(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            int n = this.backgroundHeight;
            if (i == (this.handler).getSelectedRecipe()) {
                n += 18;
            } else if (mouseX >= k && mouseY >= m && mouseX < k + 16 && mouseY < m + 18) {
                n += 36;
            }

            context.drawTexture(TEXTURE, k, m - 1, 0, n, 16, 18);
        }

    }

    private void renderRecipeIcons(DrawContext context, int x, int y, int scrollOffset) {
        List<RecipeEntry<ArtisanRecipe>> list = this.handler.getAvailableRecipes();
        for (int i = this.scrollOffset; i < scrollOffset && i < (this.handler).getAvailableRecipeCount(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            context.drawItem(list.get(i).value().getResult(this.client.world.getRegistryManager()), k, m);
        }

    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.mouseClicked = false;
        if (button == 0) {
            for (ArtisanTableTab category : this.categories) {
                if (!category.isClickOnTab(x, y, mouseX, mouseY)) continue;
                this.selectedCategory = category;
                selectedTab = tabs.get(selectedCategory.getIndex()).getFirst();
                changeTab();
                break;
            }
            for (ArtisanTableTab tab : tabs.get(selectedCategory.getIndex())) {
                if (!tab.isClickOnTab(x, y, mouseX, mouseY)) continue;
                this.selectedTab = tab;
                changeTab();
                break;
            }
        }
        if (this.canCraft) {
            int i = this.x + 76;
            int j = this.y + 14;
            int k = this.scrollOffset + 12;

            for(int l = this.scrollOffset; l < k; ++l) {
                int m = l - this.scrollOffset;
                double d = mouseX - (double)(i + m % 4 * 16);
                double e = mouseY - (double)(j + m / 4 * 18);
                if (d >= 0.0 && e >= 0.0 && d < 16.0 && e < 18.0 && (this.handler).onButtonClick(this.client.player, l)) {
                    MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                    this.client.interactionManager.clickButton((this.handler).syncId, l);
                    return true;
                }
            }

            i = this.x + 119;
            j = this.y + 9;
            if (mouseX >= (double)i && mouseX < (double)(i + 12) && mouseY >= (double)j && mouseY < (double)(j + 54)) {
                this.mouseClicked = true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.mouseClicked && this.shouldScroll()) {
            int i = this.y + 14;
            int j = i + 54;
            this.scrollAmount = ((float)mouseY - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
            this.scrollAmount = MathHelper.clamp(this.scrollAmount, 0.0F, 1.0F);
            this.scrollOffset = (int)((double)(this.scrollAmount * (float)this.getMaxScroll()) + 0.5) * 4;
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (this.shouldScroll()) {
            int i = this.getMaxScroll();
            float f = (float)amount / (float)i;
            this.scrollAmount = MathHelper.clamp(this.scrollAmount - f, 0.0F, 1.0F);
            this.scrollOffset = (int)((double)(this.scrollAmount * (float)i) + 0.5) * 4;
        }

        return true;
    }

    private boolean shouldScroll() {
        return this.canCraft && this.handler.getAvailableRecipeCount() > 12;
    }

    protected int getMaxScroll() {
        return (this.handler.getAvailableRecipeCount() + 4 - 1) / 4 - 3;
    }

    private void onInventoryChange() {
        this.canCraft = this.handler.canCraft();
        if (!this.canCraft) {
            this.scrollAmount = 0.0F;
            this.scrollOffset = 0;
        }
    }
    
    private void changeTab() {
        ClientPlayNetworking.send(new ArtisanTableTabPacket(selectedCategory.getIndex(), selectedTab.getIndex(), handler.syncId));
    }
}
