package net.jukoz.me.block.special.forge;

import net.jukoz.me.item.ModResourceItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.StringIdentifiable;

public enum MetalTypes implements StringIdentifiable {
    EMPTY(-1, "empty", null, null, false, 0),

    COPPER(0, "copper", Items.COPPER_INGOT, null, true, 11823181),
    TIN(1, "tin", ModResourceItems.TIN_INGOT, ModResourceItems.TIN_NUGGET, true, 13026492),

    BRONZE(2, "bronze", ModResourceItems.BRONZE_INGOT, ModResourceItems.BRONZE_NUGGET, false, 10714446),
    CRUDE(3, "crude", ModResourceItems.CRUDE_INGOT, ModResourceItems.CRUDE_NUGGET, false, 7560021),

    LEAD(4, "lead", ModResourceItems.LEAD_INGOT, ModResourceItems.LEAD_NUGGET, false, 6384761),
    SILVER(5, "silver", ModResourceItems.SILVER_INGOT, ModResourceItems.SILVER_NUGGET, false, 15397618),
    IRON(6, "iron", Items.IRON_INGOT, Items.IRON_NUGGET, true, 15527148),
    GOLD(7, "gold", Items.GOLD_INGOT, Items.GOLD_NUGGET, true, 14594349),

    STEEL(8, "steel", ModResourceItems.STEEL_INGOT, ModResourceItems.STEEL_NUGGET, false, 12369344 ),
    BURZUM_STEEL(9, "burzum_steel", ModResourceItems.BURZUM_STEEL_INGOT, ModResourceItems.BURZUM_STEEL_NUGGET, false, 5985355),
    EDHEL_STEEL(10, "edhel_steel", ModResourceItems.EDHEL_STEEL_INGOT, ModResourceItems.EDHEL_STEEL_NUGGET, false, 15921385),
    KHAZAD_STEEL(11, "khazad_steel", ModResourceItems.KHAZAD_STEEL_INGOT, ModResourceItems.KHAZAD_STEEL_NUGGET, false, 6778743),

    MITHRIL(12, "mithril", ModResourceItems.MITHRIL_INGOT, ModResourceItems.MITHRIL_NUGGET, false,14278631),

    NETHERITE(13, "netherite", Items.NETHERITE_INGOT, null, true, 6445145),
    ;

    private final int id;
    private final String name;
    private final Item ingot;
    private final Item nugget;
    private final boolean vanilla;
    private final int color;

    MetalTypes(int id, String name, Item ingot, Item nugget, boolean vanilla, int color){
        this.id = id;
        this.name = name;
        this.ingot = ingot;
        this.nugget = nugget;
        this.vanilla = vanilla;
        this.color = color;
    }

    public boolean isVanilla() {
        return vanilla;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    @Override
    public String asString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Item getIngot() {
        return ingot;
    }

    public Item getNugget() {
        return nugget;
    }

    public static MetalTypes getValue(int value) {
        for(MetalTypes e: MetalTypes.values()) {
            if(e.id == value) {
                return e;
            }
        }
        return null;
    }

    public static MetalTypes getMetalByIngot(Item ingot) {
        for(MetalTypes e: MetalTypes.values()) {
            if(e.getIngot() == ingot) {
                return e;
            }
        }
        return null;
    }

    public static MetalTypes getMetalByNugget(Item nugget) {
        for(MetalTypes e: MetalTypes.values()) {
            if(e.getNugget() == nugget) {
                return e;
            }
        }
        return null;
    }
}
