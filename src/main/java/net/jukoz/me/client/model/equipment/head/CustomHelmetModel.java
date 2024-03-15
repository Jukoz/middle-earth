package net.jukoz.me.client.model.equipment.head;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.ToRad;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class CustomHelmetModel<T extends LivingEntity> extends BipedEntityModel<T> {

    public  Identifier HELMET_ADDON_TEXTURE;

    public CustomHelmetModel(ModelPart root) {
        super(root);
    }
}