package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.util.Identifier;

public class TrollArmorItem extends HorseArmorItem {

    private static final String ENTITY_TEXTURE_PREFIX = "textures/entity/troll/";
    private final String entityTexture;


    public TrollArmorItem(int bonus, String name, Settings settings) {
        super(bonus, name, settings);
        this.entityTexture = "textures/entity/troll/armor/troll_armor_" + name + ".png";
    }

    public Identifier getEntityTexture() {
        return new Identifier(MiddleEarth.MOD_ID ,this.entityTexture);
    }
}
