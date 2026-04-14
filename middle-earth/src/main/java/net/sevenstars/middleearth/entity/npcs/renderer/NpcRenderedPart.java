package net.sevenstars.middleearth.entity.npcs.renderer;

public enum NpcRenderedPart {
    BODY("body"),
    HEAD("head"),
    FEET("feet"),
    EAR("ear"),
    NOSE("nose"),
    SCAR("scar"),
    EYE("eye"),
    EYE_EMISSIVE("eye_emissive"),
    EYE_EMISSIVE_TOGGLE("eye_emissive_toggle"),
    HAIR("hair"),
    HAIR_ADDON("hair_addon"),
    EYEBROW("eyebrow"),
    BEARD("beard"),
    BEARD_ADDON("beard_addon"),
    CLOTHING_BASE("clothing_base"),
    CLOTHING_OVER("clothing_over"),
    CLOTHING_EXTRA("clothing_extra");

    private String fieldName;

    NpcRenderedPart(String fieldName){
        this.fieldName = fieldName;
    }

    public String getField(){
        return fieldName;
    }
}
