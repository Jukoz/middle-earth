package net.sevenstars.middleearth.entity.spider.spawn;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.AnimationState;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerRenderState;

@Environment(EnvType.CLIENT)
public class SpawnOfShelobRenderState extends ShelobiteScuttlerRenderState {
    public final AnimationState blockAnimationState = new AnimationState();

    public SpawnOfShelobRenderState() {

    }
}
