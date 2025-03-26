package net.sevenstars.middleearth.gui.artisantable;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.ModEquipmentItems;
import net.sevenstars.middleearth.item.ModToolItems;
import net.sevenstars.middleearth.item.ModWeaponItems;
import net.sevenstars.middleearth.network.packets.C2S.ArtisanTableTabPacket;
import net.sevenstars.middleearth.recipe.ArtisanRecipe;
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
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
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
    public static final int SLOT_SCALE = 18;
    public static final int SLOT_OFFSET_X = 12;
    public static final int SLOT_OFFSET_Y = 15;
    public static final int SPRITE_OFFSET_X = SLOT_OFFSET_X + 1;
    public static final int SPRITE_OFFSET_Y = SLOT_OFFSET_Y + 1;

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
        categories.add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.LEFT, index, getTabTranslation("weapons"), ModWeaponItems.GONDORIAN_NOBLE_LONGSWORD.getDefaultStack()));
        tabs.put(index, new ArrayList<>());
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 0, getTabTranslation("sword"), ModWeaponItems.STEEL_SWORD.getDefaultStack(), ArtisanTableInputsShape.SWORD));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 1, getTabTranslation("axe"), ModToolItems.STEEL_AXE.getDefaultStack(), ArtisanTableInputsShape.AXE));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 2, getTabTranslation("spear"), ModWeaponItems.STEEL_SPEAR.getDefaultStack(), ArtisanTableInputsShape.SPEAR));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 3, getTabTranslation("bow"), ModWeaponItems.GONDORIAN_BOW.getDefaultStack(), ArtisanTableInputsShape.BOW));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 4, getTabTranslation("crossbow"), Items.CROSSBOW.getDefaultStack(), ArtisanTableInputsShape.CROSSBOW));
        index++;

        categories.add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.LEFT, index, getTabTranslation("tools"), ModToolItems.MITHRIL_PICKAXE.getDefaultStack()));
        tabs.put(index, new ArrayList<>());
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 0, getTabTranslation("pickaxe"), ModToolItems.STEEL_PICKAXE.getDefaultStack(), ArtisanTableInputsShape.PICKAXE));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 1, getTabTranslation("shovel"), ModToolItems.STEEL_SHOVEL.getDefaultStack(), ArtisanTableInputsShape.SHOVEL));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 2, getTabTranslation("hoe"), ModToolItems.STEEL_HOE.getDefaultStack(), ArtisanTableInputsShape.HOE));
        index++;

        categories.add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.LEFT, index, getTabTranslation("armors"), ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE.getDefaultStack()));
        tabs.put(index, new ArrayList<>());
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 0, getTabTranslation("helmet"), ModEquipmentItems.RAVENHILL_WATCHWARDEN_HELMET.getDefaultStack(), ArtisanTableInputsShape.HELMET));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 1, getTabTranslation("chestplate"), ModEquipmentItems.RAVENHILL_WATCHWARDEN_CHESTPLATE.getDefaultStack(), ArtisanTableInputsShape.CHESTPLATE));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 2, getTabTranslation("leggings"), ModEquipmentItems.RAVENHILL_WATCHWARDEN_LEGGINGS.getDefaultStack(), ArtisanTableInputsShape.LEGGINGS));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 3, getTabTranslation("boots"), ModEquipmentItems.RAVENHILL_WATCHWARDEN_BOOTS.getDefaultStack(), ArtisanTableInputsShape.BOOTS));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 4, getTabTranslation("mount_armor"), ModEquipmentItems.ROHIRRIC_HORSE_ARMOR.getDefaultStack(), ArtisanTableInputsShape.MOUNT_ARMOR));
        index++;

        categories.add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.LEFT, index, getTabTranslation("shields"), Items.SHIELD.getDefaultStack()));
        tabs.put(index, new ArrayList<>());
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 0, getTabTranslation("light_shield"), ModWeaponItems.GUNDABAD_WOODEN_SHIELD.getDefaultStack(), ArtisanTableInputsShape.LIGHT_SHIELD));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 1, getTabTranslation("medium_shield"), ModWeaponItems.ROUND_SHIELD.getDefaultStack(), ArtisanTableInputsShape.MEDIUM_SHIELD));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 2, getTabTranslation("heavy_shield"), ModWeaponItems.URUK_HAI_WHITE_HAND_SHIELD.getDefaultStack(), ArtisanTableInputsShape.HEAVY_SHIELD));
        index++;

        categories.add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.LEFT, index, getTabTranslation("misc"), ModEquipmentItems.STRAW_HAT.getDefaultStack()));
        tabs.put(index, new ArrayList<>());
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 0, getTabTranslation("hat"), ModEquipmentItems.STRAW_HAT.getDefaultStack(), ArtisanTableInputsShape.HAT));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 1, getTabTranslation("hood"), ModEquipmentItems.HOOD.getDefaultStack(), ArtisanTableInputsShape.HOOD));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 2, getTabTranslation("cape"), ModEquipmentItems.CAPE.getDefaultStack(), ArtisanTableInputsShape.CAPE));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 3, getTabTranslation("pipe"), ModToolItems.CLAY_PIPE.getDefaultStack(), ArtisanTableInputsShape.PIPE));
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
        changeTab();

        this.armorStand = new ArmorStandEntity(this.client.world, 0.0, 0.0, 0.0);
        this.armorStand.setHideBasePlate(true);
        this.armorStand.setShowArms(true);
        this.armorStand.bodyYaw = 210.0f;
        this.armorStand.setPitch(25.0f);
        this.armorStand.headYaw = this.armorStand.getYaw();
        this.armorStand.prevHeadYaw = this.armorStand.getYaw();
        this.equipArmorStand((this.handler).getSlot(9).getStack());
    }


    @Override
    public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
        if (slotId == 9) {
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
            if (item instanceof ArmorItem armorItem) {
                EquippableComponent equippableComponent = (EquippableComponent)stack.get(DataComponentTypes.EQUIPPABLE);
                EquipmentSlot equipmentSlot = equippableComponent != null ? equippableComponent.slot() : EquipmentSlot.OFFHAND;
                this.armorStand.equipStack(equipmentSlot, stack.copy());
            } else {
                this.armorStand.equipStack(EquipmentSlot.OFFHAND, itemStack);
            }
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        //RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = this.x;
        int j = this.y;
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        int k = (int)(41.0F * this.scrollAmount);
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, i + 143, j + 15 + k, 232 + (this.shouldScroll() ? 0 : 12), 0, 12, 15, 256, 256);

        int l = this.x + 76;
        int m = this.y + 14;
        int n = this.scrollOffset + 12;

        ArtisanTableInputsShape shape = selectedTab.getInputShape();
        for(int w = 0; w < 3; w++) {
            for(int z = 0; z < 3; z++) {
                if(handler.slots.get(w*3 + z).isEnabled()) {
                    context.drawTexture(RenderLayer::getGuiTextured, TEXTURE,x + SLOT_OFFSET_X + SLOT_SCALE*z,y + SLOT_OFFSET_Y + SLOT_SCALE*w, 232, 15, SLOT_SCALE, SLOT_SCALE, 256, 256);
                    InputType inputType = shape.getInputType(z, w);
                    if(!handler.slots.get(w*3 + z).hasStack()) {
                        switch (inputType){
                            case HANDLE -> context.drawTexture(RenderLayer::getGuiTextured, TEXTURE,x + SPRITE_OFFSET_X + SLOT_SCALE*z,y + SPRITE_OFFSET_Y + SLOT_SCALE*w, 232, 87, 16, 16, 256, 256);
                            case HILT -> context.drawTexture(RenderLayer::getGuiTextured, TEXTURE,x + SPRITE_OFFSET_X + SLOT_SCALE*z,y + SPRITE_OFFSET_Y + SLOT_SCALE*w, 232, 103, 16, 16, 256, 256);
                            case BLADE -> context.drawTexture(RenderLayer::getGuiTextured, TEXTURE,x + SPRITE_OFFSET_X + SLOT_SCALE*z,y + SPRITE_OFFSET_Y + SLOT_SCALE*w, 232, 119, 16, 16, 256, 256);
                            case AXE -> context.drawTexture(RenderLayer::getGuiTextured, TEXTURE,x + SPRITE_OFFSET_X + SLOT_SCALE*z,y + SPRITE_OFFSET_Y + SLOT_SCALE*w, 232, 135, 16, 16, 256, 256);
                            case PICKAXE -> context.drawTexture(RenderLayer::getGuiTextured, TEXTURE,x + SPRITE_OFFSET_X + SLOT_SCALE*z,y + SPRITE_OFFSET_Y + SLOT_SCALE*w, 232, 151, 16, 16, 256, 256);
                            case SHOVEL -> context.drawTexture(RenderLayer::getGuiTextured, TEXTURE,x + SPRITE_OFFSET_X + SLOT_SCALE*z,y + SPRITE_OFFSET_Y + SLOT_SCALE*w, 232, 167, 16, 16, 256, 256);
                            case HOE -> context.drawTexture(RenderLayer::getGuiTextured, TEXTURE,x + SPRITE_OFFSET_X + SLOT_SCALE*z,y + SPRITE_OFFSET_Y + SLOT_SCALE*w, 232, 183, 16, 16, 256, 256);
                        }
                    }
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
            category.drawBackground(context, x, y + 3, category == this.selectedCategory);
            category.drawIcon(context, x, y + 3);
        }
        for (ArtisanTableTab tab : this.tabs.get(selectedCategory.getIndex())) {
            tab.drawBackground(context, x + 3, y, tab == this.selectedTab);
            tab.drawIcon(context, x + 3, y);
        }

        for (ArtisanTableTab category : this.categories) {
            if (category.isClickOnTab(x, y + 3, mouseX, mouseY)) continue;
            context.drawTooltip(this.textRenderer, category.getTitle(), mouseX, mouseY);
        }
        for (ArtisanTableTab tab : this.tabs.get(selectedCategory.getIndex())) {
            if (tab.isClickOnTab(x + 3, y, mouseX, mouseY)) continue;
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
                    context.drawItemTooltip(this.textRenderer, list.get(l).value().getOutput(), x, y);
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

            context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, k, m - 1, 0, n, 16, 18, 256,256);
        }

    }

    private void renderRecipeIcons(DrawContext context, int x, int y, int scrollOffset) {
        List<RecipeEntry<ArtisanRecipe>> list = this.handler.getAvailableRecipes();
        for (int i = this.scrollOffset; i < scrollOffset && i < (this.handler).getAvailableRecipeCount(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            context.drawItem(list.get(i).value().getOutput(), k, m);
        }

    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.mouseClicked = false;
        if (button == 0) {
            for (ArtisanTableTab category : this.categories) {
                if (category.isClickOnTab(x, y, mouseX, mouseY)) continue;
                this.selectedCategory = category;
                selectedTab = tabs.get(selectedCategory.getIndex()).getFirst();
                MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                changeTab();
                break;
            }
            for (ArtisanTableTab tab : tabs.get(selectedCategory.getIndex())) {
                if (tab.isClickOnTab(x, y, mouseX, mouseY)) continue;
                this.selectedTab = tab;
                MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
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
                    MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
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

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (this.shouldScroll()) {
            int i = this.getMaxScroll();
            float f = (float)verticalAmount / (float)i;
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
        handler.changeTab(selectedTab.getInputShape().getId());
        ClientPlayNetworking.send(new ArtisanTableTabPacket(selectedTab.getInputShape().getId(), handler.syncId));
    }

    private static Text getTabTranslation(String tab) {
        return Text.translatable("screen." + MiddleEarth.MOD_ID + ".artisan_table." + tab);
    }
}
