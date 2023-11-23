package net.jukoz.me.entity.hobbits.shire;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.entity.humanoid_npc.HumanoidNpcEntityModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.mob.MobEntity;

@Environment(value= EnvType.CLIENT)
public class ShireHobbitModelNpc<T extends MobEntity>
        extends HumanoidNpcEntityModel<T> {

    public ShireHobbitModelNpc(ModelPart root) {
        super(root);
    }
}
