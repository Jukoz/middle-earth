package net.jukoz.me.entity.hobbits.shire;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.entity.HumanoidEntityModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

@Environment(value= EnvType.CLIENT)
public class ShireHobbitModel<T extends MobEntity>
        extends HumanoidEntityModel<T> {

    public ShireHobbitModel(ModelPart root) {
        super(root);
    }
}
