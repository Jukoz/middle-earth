package net.sevenstars.middleearth.item.utils.armor.hoods;

import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;
import net.sevenstars.middleearth.client.model.equipment.head.hoods.armored.*;
import net.sevenstars.middleearth.client.model.equipment.head.hoods.unarmored.UnarmoredFurHoodDownModel;
import net.sevenstars.middleearth.client.model.equipment.head.hoods.unarmored.UnarmoredFurHoodModel;
import net.sevenstars.middleearth.client.model.equipment.head.hoods.unarmored.UnarmoredHoodModel;
import net.sevenstars.middleearth.client.model.equipment.head.hoods.unarmored.UnarmoredTallHoodModel;
import net.minecraft.entity.LivingEntity;

public enum ModHoodModels {
    REGULAR_MODELS(new HoodModel<>(HoodModel.getTexturedModelData().createModel()), new HoodModel<>(HoodModel.getTexturedModelData().createModel()),
            new UnarmoredHoodModel<>(UnarmoredHoodModel.getTexturedModelData().createModel()), new UnarmoredHoodModel<>(UnarmoredHoodModel.getTexturedModelData().createModel())),
    TALL_MODELS(new TallHoodModel<>(TallHoodModel.getTexturedModelData().createModel()), new TallHoodModel<>(TallHoodModel.getTexturedModelData().createModel()),
            new UnarmoredTallHoodModel<>(UnarmoredTallHoodModel.getTexturedModelData().createModel()), new UnarmoredTallHoodModel<>(UnarmoredTallHoodModel.getTexturedModelData().createModel())),
    FUR_MODELS(new FurHoodModel<>(FurHoodModel.getTexturedModelData().createModel()), new FurHoodDownModel<>(FurHoodDownModel.getTexturedModelData().createModel()),
            new UnarmoredFurHoodModel<>(UnarmoredFurHoodModel.getTexturedModelData().createModel()), new UnarmoredFurHoodDownModel<>(UnarmoredFurHoodDownModel.getTexturedModelData().createModel())),
    NAZGUL_MODELS(new NazgulHoodModel<>(NazgulHoodModel.getTexturedModelData().createModel()), new NazgulHoodModel<>(NazgulHoodModel.getTexturedModelData().createModel()),
            new NazgulHoodModel<>(NazgulHoodModel.getTexturedModelData().createModel()), new NazgulHoodModel<>(NazgulHoodModel.getTexturedModelData().createModel())),
    ;

    private final HelmetAddonModel<LivingEntity> armoredModel;
    private final HelmetAddonModel<LivingEntity> armoredDownModel;
    private final HelmetAddonModel<LivingEntity> unarmoredModel;
    private final HelmetAddonModel<LivingEntity> unarmoredDownModel;

    ModHoodModels(HelmetAddonModel<LivingEntity> armoredModel, HelmetAddonModel<LivingEntity> armoredDownModel,
                  HelmetAddonModel<LivingEntity> unarmoredModel, HelmetAddonModel<LivingEntity> unarmoredDownModel){
        this.armoredModel = armoredModel;
        this.armoredDownModel = armoredDownModel;
        this.unarmoredModel = unarmoredModel;
        this.unarmoredDownModel = unarmoredDownModel;
    }

    public HelmetAddonModel<LivingEntity> getArmoredModel() {
        return armoredModel;
    }

    public HelmetAddonModel<LivingEntity> getArmoredDownModel() {
        return armoredDownModel;
    }

    public HelmetAddonModel<LivingEntity> getUnarmoredModel() {
        return unarmoredModel;
    }

    public HelmetAddonModel<LivingEntity> getUnarmoredDownModel() {
        return unarmoredDownModel;
    }
}
