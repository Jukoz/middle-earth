package net.sevenstars.middleearth.block.special.forge;

import net.sevenstars.middleearth.item.ResourceItemsME;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.StringIdentifiable;

public enum MetalTypes implements StringIdentifiable {
    EMPTY(-1, "empty", null, null, false, 0),

    COPPER(0, "copper", Items.COPPER_INGOT, null, true, 11823181),
    TIN(1, "tin", ResourceItemsME.TIN_INGOT, ResourceItemsME.TIN_NUGGET, true, 13026492),

    BRONZE(2, "bronze", ResourceItemsME.BRONZE_INGOT, ResourceItemsME.BRONZE_NUGGET, false, 10714446),
    CRUDE(3, "crude", ResourceItemsME.CRUDE_INGOT, ResourceItemsME.CRUDE_NUGGET, false, 7560021),

    LEAD(4, "lead", ResourceItemsME.LEAD_INGOT, ResourceItemsME.LEAD_NUGGET, false, 6384761),
    SILVER(5, "silver", ResourceItemsME.SILVER_INGOT, ResourceItemsME.SILVER_NUGGET, false, 15397618),
    IRON(6, "iron", Items.IRON_INGOT, Items.IRON_NUGGET, true, 15527148),
    GOLD(7, "gold", Items.GOLD_INGOT, Items.GOLD_NUGGET, true, 14594349),

    STEEL(8, "steel", ResourceItemsME.STEEL_INGOT, ResourceItemsME.STEEL_NUGGET, false, 12369344 ),
    BURZUM_STEEL(9, "burzum_steel", ResourceItemsME.BURZUM_STEEL_INGOT, ResourceItemsME.BURZUM_STEEL_NUGGET, false, 5985355),
    EDHEL_STEEL(10, "edhel_steel", ResourceItemsME.EDHEL_STEEL_INGOT, ResourceItemsME.EDHEL_STEEL_NUGGET, false, 15921385),
    KHAZAD_STEEL(11, "khazad_steel", ResourceItemsME.KHAZAD_STEEL_INGOT, ResourceItemsME.KHAZAD_STEEL_NUGGET, false, 6778743),

    MITHRIL(12, "mithril", ResourceItemsME.MITHRIL_INGOT, ResourceItemsME.MITHRIL_NUGGET, false,14278631),

    NETHERITE(13, "netherite", Items.NETHERITE_INGOT, null, true, 6445145),

    CHICKEN_NUGGY(14, "chicken_nugget", ResourceItemsME.THERAPOD_NUGGET, ResourceItemsME.PTEROSAUR_NUGGET, false, 11242339),
    ;

    public static MetalTypes fromValue(String name) {
        for (MetalTypes metal : MetalTypes.values()) {
            if (metal.name.equals(name)) return metal;
        }
        System.out.println("CRASH AT : " + name);
        return null;
    }

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
