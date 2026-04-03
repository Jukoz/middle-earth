package net.sevenstars.middleearth.registries;

import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.utils.IdentifierUtil;

public class CharacterClothesRegistryME {
    private final static String base = "character_clothes/base/";
    private final static String over = "character_clothes/over/";
    private final static String extra = "character_clothes/extra/";

    public record Base(){
        public final static Identifier PANTS_BEIGE                                      = IdentifierUtil.build(base + "pants_beige");
        public final static Identifier PANTS_BROWN                                      = IdentifierUtil.build(base + "pants_brown");
        public final static Identifier PANTS_DARK_BROWN                                 = IdentifierUtil.build(base + "pants_dark_brown");

        public final static Identifier THONG_BEIGE                                      = IdentifierUtil.build(base + "thong_beige");
        public final static Identifier THONG_BROWN                                      = IdentifierUtil.build(base + "thong_brown");
        public final static Identifier THONG_DARK_BROWN                                 = IdentifierUtil.build(base + "thong_dark_brown");
    }

    public record Over(){
        public final static Identifier SHIRT_BEIGE                                      = IdentifierUtil.build(over + "shirt_beige");
        public final static Identifier SHIRT_BURGUNDY                                   = IdentifierUtil.build(over + "shirt_burgundy");

        public final static Identifier DRESS_BURGUNDY_AND_WHITE                         = IdentifierUtil.build(over + "dress_burgundy_and_white");
    }

    public record Extra(){
        public final static Identifier SCARF_BROWN                                      = IdentifierUtil.build(extra + "scarf_brown");
        public final static Identifier SCARF_DARK_BROWN                                 = IdentifierUtil.build(extra + "scarf_dark_brown");
    }

    public static Identifier RemovePath(Identifier identifier){
        String val = identifier.toString();

        val = val.replace(base, "");
        val = val.replace(over, "");
        val = val.replace(extra, "");

        return Identifier.of(val);
    }
}
