package net.sevenstars.middleearth.item.utils.armor.backAttachments;

import net.sevenstars.middleearth.client.model.equipment.chest.ChestplateAddonModel;
import net.sevenstars.middleearth.client.model.equipment.chest.backAttachments.armored.*;
import net.sevenstars.middleearth.client.model.equipment.chest.backAttachments.unarmored.*;

public enum BackAttachmentModelsME {
    SLIM_MODELS(new CapeSlimModel(CapeSlimModel.getTexturedModelData().bakeRoot()), new UnarmoredCapeSlimModel(UnarmoredCapeSlimModel.getTexturedModelData().bakeRoot())),
    MEDIUM_MODELS(new CapeMediumModel(CapeMediumModel.getTexturedModelData().bakeRoot()), new UnarmoredCapeMediumModel(UnarmoredCapeMediumModel.getTexturedModelData().bakeRoot())),
    WIDE_MODELS(new CapeWideModel(CapeWideModel.getTexturedModelData().bakeRoot()), new UnarmoredCapeWideModel(UnarmoredCapeWideModel.getTexturedModelData().bakeRoot())),
    WIDE_PAULDRON_MODELS(new CapeWidePauldronModel(CapeWidePauldronModel.getTexturedModelData().bakeRoot()), new UnarmoredCapeWidePauldronModel(UnarmoredCapeWidePauldronModel.getTexturedModelData().bakeRoot())),
    FUR_MODELS(new FurCapeModel(FurCapeModel.getTexturedModelData().bakeRoot()), new UnarmoredFurCapeModel(UnarmoredFurCapeModel.getTexturedModelData().bakeRoot())),
    SURCOAT_MODELS(new CapeSurcoatModel(CapeSurcoatModel.getTexturedModelData().bakeRoot()), new UnarmoredCapeSurcoatModel(UnarmoredCapeSurcoatModel.getTexturedModelData().bakeRoot())),
    SURCOAT_FUR_MODELS(new FurSurcoatModel(FurSurcoatModel.getTexturedModelData().bakeRoot()), new UnarmoredFurSurcoatModel(UnarmoredFurSurcoatModel.getTexturedModelData().bakeRoot())),
    NAZGUL_ROBES_MODEL(new NazgulRobesModel(NazgulRobesModel.getTexturedModelData().bakeRoot()), new NazgulRobesModel(NazgulRobesModel.getTexturedModelData().bakeRoot())),
    SKULL_SPIKES(new SkullSpikesModel(SkullSpikesModel.getTexturedModelData().bakeRoot()), new SkullSpikesModel(SkullSpikesModel.getTexturedModelData().bakeRoot())),
    ;

    private final ChestplateAddonModel armoredModel;
    private final ChestplateAddonModel unarmoredModel;

    BackAttachmentModelsME(ChestplateAddonModel armoredModel, ChestplateAddonModel unarmoredModel){
        this.armoredModel = armoredModel;
        this.unarmoredModel = unarmoredModel;
    }

    public ChestplateAddonModel getArmoredModel() {
        return armoredModel;
    }

    public ChestplateAddonModel getUnarmoredModel() {
        return unarmoredModel;
    }
}