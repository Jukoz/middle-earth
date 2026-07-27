package net.sevenstars.middleearth.item.utils.armor.helmetAttachments;

import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmetAttachments.armored.*;
import net.sevenstars.middleearth.client.model.equipment.head.helmetAttachments.unarmored.UnarmoredFurHoodDownModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmetAttachments.unarmored.UnarmoredFurHoodModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmetAttachments.unarmored.UnarmoredHoodModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmetAttachments.unarmored.UnarmoredTallHoodModel;

public enum HelmetAttachmentModelsME {
    REGULAR_MODELS(new HoodModel(HoodModel.getTexturedModelData().bakeRoot()), new HoodModel(HoodModel.getTexturedModelData().bakeRoot()),
            new UnarmoredHoodModel(UnarmoredHoodModel.getTexturedModelData().bakeRoot()), new UnarmoredHoodModel(UnarmoredHoodModel.getTexturedModelData().bakeRoot())),
    TALL_MODELS(new TallHoodModel(TallHoodModel.getTexturedModelData().bakeRoot()), new TallHoodModel(TallHoodModel.getTexturedModelData().bakeRoot()),
            new UnarmoredTallHoodModel(UnarmoredTallHoodModel.getTexturedModelData().bakeRoot()), new UnarmoredTallHoodModel(UnarmoredTallHoodModel.getTexturedModelData().bakeRoot())),
    FUR_MODELS(new FurHoodModel(FurHoodModel.getTexturedModelData().bakeRoot()), new FurHoodDownModel(FurHoodDownModel.getTexturedModelData().bakeRoot()),
            new UnarmoredFurHoodModel(UnarmoredFurHoodModel.getTexturedModelData().bakeRoot()), new UnarmoredFurHoodDownModel(UnarmoredFurHoodDownModel.getTexturedModelData().bakeRoot())),
    NAZGUL_MODELS(new NazgulHoodModel(NazgulHoodModel.getTexturedModelData().bakeRoot()), new NazgulHoodModel(NazgulHoodModel.getTexturedModelData().bakeRoot()),
            new NazgulHoodModel(NazgulHoodModel.getTexturedModelData().bakeRoot()), new NazgulHoodModel(NazgulHoodModel.getTexturedModelData().bakeRoot())),
    SKULL_MODELS(new HelmetSkullModel(HelmetSkullModel.getTexturedModelData().bakeRoot()), new HelmetSkullModel(HelmetSkullModel.getTexturedModelData().bakeRoot()),
            new HelmetSkullModel(NazgulHoodModel.getTexturedModelData().bakeRoot()), new HelmetSkullModel(HelmetSkullModel.getTexturedModelData().bakeRoot())),
    ;

    private final HelmetAddonModel armoredModel;
    private final HelmetAddonModel armoredDownModel;
    private final HelmetAddonModel unarmoredModel;
    private final HelmetAddonModel unarmoredDownModel;

    HelmetAttachmentModelsME(HelmetAddonModel armoredModel, HelmetAddonModel armoredDownModel,
                             HelmetAddonModel unarmoredModel, HelmetAddonModel unarmoredDownModel){
        this.armoredModel = armoredModel;
        this.armoredDownModel = armoredDownModel;
        this.unarmoredModel = unarmoredModel;
        this.unarmoredDownModel = unarmoredDownModel;
    }

    public HelmetAddonModel getArmoredModel() {
        return armoredModel;
    }

    public HelmetAddonModel getArmoredDownModel() {
        return armoredDownModel;
    }

    public HelmetAddonModel getUnarmoredModel() {
        return unarmoredModel;
    }

    public HelmetAddonModel getUnarmoredDownModel() {
        return unarmoredDownModel;
    }
}
