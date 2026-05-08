package net.sevenstars.middleearth.datageneration.content.tags;

import net.minecraft.item.Item;
import net.sevenstars.middleearth.block.special.forge.MetalTypes;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;

import java.util.ArrayList;
import java.util.List;

public class ArmorTags {
    public static List<Item> armors = new ArrayList<>();

    public static List<Item> headArmors = new ArrayList<>();
    public static List<Item> chestArmors = new ArrayList<>();
    public static List<Item> legArmors = new ArrayList<>();
    public static List<Item> footArmors = new ArrayList<>();

    public static List<Item> basicArmors = new ArrayList<>();
    public static List<Item> lightArmors = new ArrayList<>();
    public static List<Item> mediumArmors = new ArrayList<>();
    public static List<Item> sturdyArmors = new ArrayList<>();
    public static List<Item> heavyArmors = new ArrayList<>();

    public static List<Item> incompleteArmors = new ArrayList<>();

    // Recipes
    public static List<RecipeItem> heavyBoots = new ArrayList<>() {
        {
            add(new RecipeItem(DispositionType.GOOD, MetalTypes.STEEL, EquipmentItemsME.GONDORIAN_PLATE_BOOTS));
            add(new RecipeItem(DispositionType.GOOD, MetalTypes.STEEL, EquipmentItemsME.GONDORIAN_KINGS_GUARD_BOOTS));
            add(new RecipeItem(DispositionType.GOOD, MetalTypes.STEEL, EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_BOOTS));
            add(new RecipeItem(DispositionType.GOOD, MetalTypes.STEEL, EquipmentItemsME.EORLING_MARSHAL_BOOTS));
            add(new RecipeItem(DispositionType.GOOD, MetalTypes.STEEL, EquipmentItemsME.HORSE_LORD_BOOTS));
            add(new RecipeItem(DispositionType.GOOD, MetalTypes.STEEL, EquipmentItemsME.BARDING_PLATED_BOOTS));
            add(new RecipeItem(DispositionType.GOOD, MetalTypes.KHAZAD_STEEL, EquipmentItemsME.EREBOR_PLATE_BOOTS));
            add(new RecipeItem(DispositionType.GOOD, MetalTypes.KHAZAD_STEEL, EquipmentItemsME.EREBOR_GATEWARDEN_BOOTS));
            add(new RecipeItem(DispositionType.GOOD, MetalTypes.KHAZAD_STEEL, EquipmentItemsME.RAVENHILL_SENTINEL_BOOTS));
            add(new RecipeItem(DispositionType.GOOD, MetalTypes.KHAZAD_STEEL, EquipmentItemsME.RAVENHILL_WATCHWARDEN_BOOTS));
            add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, EquipmentItemsME.EGLADIL_SENTINEL_BOOTS));
            add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, EquipmentItemsME.GALADHRIM_BOOTS));
            add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, EquipmentItemsME.GALADHRIM_LORD_BOOTS));
            add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, EquipmentItemsME.WOODLAND_REALM_SOLDIER_BOOTS));
            add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, EquipmentItemsME.WARDEN_OF_THE_GLADE_BOOTS));
            add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, EquipmentItemsME.ERYN_GALEN_WATCHWARDEN_BOOTS));
            add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, EquipmentItemsME.OXIDISED_ERYN_GALEN_WATCHWARDEN_BOOTS));
            add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, EquipmentItemsME.WOODLAND_REALM_COMMANDER_BOOTS));
            add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, EquipmentItemsME.SILVAN_LORD_BOOTS));
            add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, EquipmentItemsME.BLACK_URUK_PLATE_BOOTS));
            add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, EquipmentItemsME.BLACK_CASTELLAN_BOOTS));
            add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, EquipmentItemsME.DOL_GULDUR_MARAUDER_BOOTS));
            add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, EquipmentItemsME.WEATHERED_DOL_GULDUR_MARAUDER_BOOTS));
            add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, EquipmentItemsME.URUK_HAI_PLATE_BOOTS));
            add(new RecipeItem(DispositionType.EVIL, MetalTypes.STEEL, EquipmentItemsME.ORTHANC_GUARD_BOOTS));
            add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATED_BOOTS));
            add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, EquipmentItemsME.MORIA_GOBLIN_SCREECHER_BOOTS));
            add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, EquipmentItemsME.MORIA_GOBLIN_CHIEF_BOOTS));
        }
    };

    public record RecipeItem(DispositionType disposition, MetalTypes metalType, Item output) {

    }
}
