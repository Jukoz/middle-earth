package net.jesteur.me.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jesteur.me.entity.barrow_wights.BarrowWightEntity;
import net.jesteur.me.statusEffects.ModStatusEffects;
import net.jesteur.me.utils.HallucinationData;
import net.jesteur.me.utils.IEntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin {

    @Shadow @Final public ClientWorld clientWorld;


    @Inject(method = "getFovMultiplier()F", at =@At("TAIL"), cancellable = true)
    private void injected(CallbackInfoReturnable<Float> cir) {
    PlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;
        //player.changeLookDirection(2, 2);

        if(player.hasStatusEffect(ModStatusEffects.HALLUCINATION)){
            /*LivingEntity lookAt = clientWorld.getClosestEntity(BarrowWightEntity.class, TargetPredicate.createNonAttackable(), null, player.getX(), player.getY(), player.getZ(), player.getBoundingBox().expand(5));
            if( lookAt != null ){
                MinecraftClient.getInstance().player.sendMessage(Text.literal("Closest Entity UUID: " + lookAt.getUuidAsString()));
                player.lookAt(player.getCommandSource().getEntityAnchor(), new Vec3d(lookAt.getX(), lookAt.getEyeY()-1.4f, lookAt.getZ()));
            }*/
            float intensity = (float) HallucinationData.readHallucination((IEntityDataSaver) player) / 100f;
            cir.setReturnValue(cir.getReturnValue() * (1 - intensity/4));

        }

        LivingEntity lookAt = clientWorld.getClosestEntity(BarrowWightEntity.class, TargetPredicate.createAttackable(), player, player.getX(), player.getY(), player.getZ(),
                player.getBoundingBox().expand(12.0, 12.0, 12.0));
        //List<Entity> list = this.clientWorld.getOtherEntities(player, new Box(-15, -15, -15, 15, 15,15));
        //System.out.println(lookAt);
        if(lookAt != null) {
            double dX = lookAt.getX() - player.getX(); // d
            double dY = lookAt.getY() - player.getY(); // e
            double dZ = lookAt.getZ() - player.getZ(); // f
            double g = Math.sqrt(dX * dX + dZ * dZ);

            float destPitch = MathHelper.wrapDegrees((float)(-(MathHelper.atan2(dY, g) * 57.2957763671875)));
            float destYaw = MathHelper.wrapDegrees((float)(MathHelper.atan2(dZ, dX) * 57.2957763671875) - 90.0f);

            destPitch = MathHelper.lerp(0.1f, destPitch, player.getPitch());
            destYaw = MathHelper.lerp(0.1f, destYaw, player.getYaw());

            //destPitch = player.getPitch();
            //destYaw = player.getYaw();

            player.setPitch(destPitch);
            player.setYaw(destYaw);
            player.setHeadYaw(player.getYaw());
            player.prevPitch = player.getPitch();
            player.prevYaw = player.getYaw();

            Vec3d currentLookAt = player.getCameraPosVec(0);
            MinecraftClient.getInstance().player.sendMessage(Text.literal("Dest Yaw: " + destYaw + " while player: " + player.getYaw()));

            //float lookAtX = MathCurves.lerp(player.getYaw(), (float) lookAt.getX(), 0.01f);
            //float lookAtY = MathCurves.lerp((float) currentLookAt.getY(), (float) lookAt.getEyeY(), 0.01f);
            //float lookAtZ = MathCurves.lerp((float) currentLookAt.getZ(), (float) lookAt.getZ(), 0.01f);
            //player.lookAt(player.getCommandSource().getEntityAnchor(), new Vec3d(lookAtX, lookAtY, lookAtZ)); // lookAt.getEyeY()
            //player.setPitch((float) MathHelper.lerp(player.getPitch(), destPitch, 0.1f));
        }

    }

}
