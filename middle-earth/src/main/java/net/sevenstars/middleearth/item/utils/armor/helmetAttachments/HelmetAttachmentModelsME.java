package net.sevenstars.middleearth.item.utils.armor.helmetAttachments;

import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmetAttachments.armored.*;
import net.sevenstars.middleearth.client.model.equipment.head.helmetAttachments.unarmored.UnarmoredFurHoodDownModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmetAttachments.unarmored.UnarmoredFurHoodModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmetAttachments.unarmored.UnarmoredHoodModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmetAttachments.unarmored.UnarmoredTallHoodModel;

public enum HelmetAttachmentModelsME {
    REGULAR_MODELS(new HoodModel(HoodModel.getTexturedModelData().createModel()), new HoodModel(HoodModel.getTexturedModelData().createModel()),
            new UnarmoredHoodModel(UnarmoredHoodModel.getTexturedModelData().createModel()), new UnarmoredHoodModel(UnarmoredHoodModel.getTexturedModelData().createModel())),
    TALL_MODELS(new TallHoodModel(TallHoodModel.getTexturedModelData().createModel()), new TallHoodModel(TallHoodModel.getTexturedModelData().createModel()),
            new UnarmoredTallHoodModel(UnarmoredTallHoodModel.getTexturedModelData().createModel()), new UnarmoredTallHoodModel(UnarmoredTallHoodModel.getTexturedModelData().createModel())),
    FUR_MODELS(new FurHoodModel(FurHoodModel.getTexturedModelData().createModel()), new FurHoodDownModel(FurHoodDownModel.getTexturedModelData().createModel()),
            new UnarmoredFurHoodModel(UnarmoredFurHoodModel.getTexturedModelData().createModel()), new UnarmoredFurHoodDownModel(UnarmoredFurHoodDownModel.getTexturedModelData().createModel())),
    NAZGUL_MODELS(new NazgulHoodModel(NazgulHoodModel.getTexturedModelData().createModel()), new NazgulHoodModel(NazgulHoodModel.getTexturedModelData().createModel()),
            new NazgulHoodModel(NazgulHoodModel.getTexturedModelData().createModel()), new NazgulHoodModel(NazgulHoodModel.getTexturedModelData().createModel())),
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
