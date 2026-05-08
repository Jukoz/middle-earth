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
    public static List<RecipeItem> heavyHelmets = new ArrayList<>();
    public static List<RecipeItem> heavyBoots = new ArrayList<>();

    public static void registerRecipesTemplates() {
        heavyHelmets = new ArrayList<>() {
            {
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.STEEL, false, EquipmentItemsME.GONDORIAN_PLATE_HELMET));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.STEEL, false, EquipmentItemsME.EORLING_MARSHAL_HELMET));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.GOLD, true, EquipmentItemsME.HORSE_LORD_HELMET));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.STEEL, false, EquipmentItemsME.BARDING_SOLDIER_HELMET));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.KHAZAD_STEEL, false, EquipmentItemsME.EREBOR_PLATE_HELMET));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, false, EquipmentItemsME.EGLADIL_SENTINEL_HELMET));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, false, EquipmentItemsME.GALADHRIM_HELMET));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, false, EquipmentItemsME.WOODLAND_REALM_SOLDIER_HELMET));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, false, EquipmentItemsME.WARDEN_OF_THE_GLADE_HELMET));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, true, EquipmentItemsME.WOODLAND_REALM_COMMANDER_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.MORDOR_GREAT_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.MORDOR_SNOUT_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.BLACK_URUK_PLATE_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.BLACK_URUK_FACE_PLATE_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.BLACK_CASTELLAN_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, true, EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.DOL_GULDUR_MARAUDER_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.WEATHERED_DOL_GULDUR_MARAUDER_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.DOL_GULDUR_STALKER_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.WEATHERED_DOL_GULDUR_STALKER_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, true, EquipmentItemsME.BLACK_REAVER_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.URUK_HAI_PLATE_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.URUK_HAI_SAPPER_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.URUK_HAI_BERSERKER_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.STEEL,  true, EquipmentItemsME.ORTHANC_GUARD_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_CRESTED_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_LONG_HORN_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_SMALL_HORN_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_LARGE_CREST_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.MORIA_GOBLIN_SCREECHER_HELMET));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.MORIA_GOBLIN_CAPTAIN_HELMET));
            }
        };

        heavyBoots = new ArrayList<>() {
            {
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.STEEL, false, EquipmentItemsME.GONDORIAN_PLATE_BOOTS));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.GOLD, true, EquipmentItemsME.GONDORIAN_KINGS_GUARD_BOOTS));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.GOLD, true, EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_BOOTS));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.STEEL, false, EquipmentItemsME.EORLING_MARSHAL_BOOTS));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.GOLD, true, EquipmentItemsME.HORSE_LORD_BOOTS));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.STEEL, false, EquipmentItemsME.BARDING_PLATED_BOOTS));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.KHAZAD_STEEL, false, EquipmentItemsME.EREBOR_PLATE_BOOTS));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.KHAZAD_STEEL, false, EquipmentItemsME.EREBOR_GATEWARDEN_BOOTS));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.KHAZAD_STEEL, true, EquipmentItemsME.RAVENHILL_SENTINEL_BOOTS));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.KHAZAD_STEEL, false, EquipmentItemsME.RAVENHILL_WATCHWARDEN_BOOTS));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, false, EquipmentItemsME.EGLADIL_SENTINEL_BOOTS));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, false, EquipmentItemsME.GALADHRIM_BOOTS));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, true, EquipmentItemsME.GALADHRIM_LORD_BOOTS));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, false, EquipmentItemsME.WOODLAND_REALM_SOLDIER_BOOTS));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, false, EquipmentItemsME.WARDEN_OF_THE_GLADE_BOOTS));
                add(new RecipeItem(DispositionType.GOOD, MetalTypes.EDHEL_STEEL, true, EquipmentItemsME.WOODLAND_REALM_COMMANDER_BOOTS));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.BLACK_URUK_PLATE_BOOTS));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.BLACK_CASTELLAN_BOOTS));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, true, EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_BOOTS));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.DOL_GULDUR_MARAUDER_BOOTS));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.WEATHERED_DOL_GULDUR_MARAUDER_BOOTS));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.DOL_GULDUR_STALKER_BOOTS));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.WEATHERED_DOL_GULDUR_STALKER_BOOTS));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.URUK_HAI_PLATE_BOOTS));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.STEEL,  true, EquipmentItemsME.ORTHANC_GUARD_BOOTS));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATED_BOOTS));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.MORIA_GOBLIN_SCREECHER_BOOTS));
                add(new RecipeItem(DispositionType.EVIL, MetalTypes.BURZUM_STEEL, false, EquipmentItemsME.MORIA_GOBLIN_CHIEF_BOOTS));
            }
        };
    }

    public record RecipeItem(DispositionType disposition, MetalTypes metalType, boolean noble, Item output) {
        @Override
        public DispositionType disposition() {
            return disposition;
        }

        @Override
        public MetalTypes metalType() {
            return metalType;
        }

        @Override
        public boolean noble() {
            return noble;
        }

        @Override
        public Item output() {
            return output;
        }
    }
}
