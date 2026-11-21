package net.sevenstars.middleearth.entity.beasts.cave_troll;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ItemModelManager;
import net.sevenstars.middleearth.client.TrollRiderCamera;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.sevenstars.middleearth.entity.beasts.BeastEntityRenderState;

@Environment(EnvType.CLIENT)
public class CaveTrollEntityRenderState extends BeastEntityRenderState {
    public final ItemRenderState handItemState = new ItemRenderState();
    public boolean hasBanner;
    public boolean fadeBanner;
    public DyeColor bannerColor = DyeColor.WHITE;
    public BannerPatternsComponent bannerPatterns = BannerPatternsComponent.DEFAULT;

    public CaveTrollVariant variant = CaveTrollVariant.GREEN;
    public boolean isEnraged = false;
    public Text customName;
    public float handSwingProgress;
    public AnimationState chaseAnimationState = new AnimationState();
    public AnimationState scavengingAnimationState = new AnimationState();
    public AnimationState startSleepingAnimationState = new AnimationState();
    public AnimationState sleepingAnimationState = new AnimationState();
    public AnimationState stopSleepingAnimationState = new AnimationState();
    public AnimationState roaringAnimationState = new AnimationState();
    public AnimationState smashingAnimationState = new AnimationState();

    public static void updateRenderState(LivingEntity entity, CaveTrollEntityRenderState state, ItemModelManager itemModelManager) {
        ItemStack banner = entity instanceof CaveTrollEntity troll ? troll.getBannerStack() : ItemStack.EMPTY;
        state.hasBanner = !banner.isEmpty();
        MinecraftClient client = MinecraftClient.getInstance();
        state.fadeBanner = TrollRiderCamera.isActive(client) && client.getCameraEntity().getVehicle() == entity;
        if(state.hasBanner) {
            DyeColor color = banner.getItem() instanceof BannerItem bannerItem ? bannerItem.getColor() : banner.get(DataComponentTypes.BASE_COLOR);
            state.bannerColor = color != null ? color : DyeColor.WHITE;
            state.bannerPatterns = banner.getOrDefault(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT);
        }
        itemModelManager.updateForLivingEntity(state.handItemState, entity.getStackInArm(Arm.RIGHT), ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, entity);
    }
}
