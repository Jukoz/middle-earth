package net.jukoz.me.item;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.datageneration.content.models.SimpleBigItemModel;
import net.jukoz.me.datageneration.content.models.SimpleHandheldItemModel;
import net.jukoz.me.datageneration.content.models.SimpleItemModel;
import net.jukoz.me.item.items.PipeItem;
import net.jukoz.me.item.items.SmithingHammerItem;
import net.jukoz.me.item.items.weapons.CustomAxeWeaponItem;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.item.utils.ModToolMaterials;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModToolItems {
    private static final int IRON_DAMAGE = 1;
    private static final int IRON_AXE_DAMAGE = IRON_DAMAGE + 5;
    private static final int IRON_PICKAXE_DAMAGE = IRON_DAMAGE - 1;
    private static final int IRON_HOE_DAMAGE = IRON_DAMAGE - 2;
    private static final float IRON_ATTACK_SPEED = -2.5f;
    private static final float IRON_AXE_ATTACK_SPEED = -3.1f;
    private static final float IRON_HOE_ATTACK_SPEED = -2f;

    public static final Item COPPER_SMITHING_HAMMER = registerItemHandheld("copper_smithing_hammer",
            new SmithingHammerItem(new Item.Settings(), ModToolMaterials.COPPER_HAMMER));

    public static final Item SMITHING_HAMMER = registerItemHandheld("smithing_hammer",
            new SmithingHammerItem(new Item.Settings(), ModToolMaterials.STEEL_HAMMER));
    public static final Item NOBLE_SMITHING_HAMMER = registerItemHandheld("noble_smithing_hammer",
            new SmithingHammerItem(new Item.Settings(), ModToolMaterials.NOBLE_STEEL_HAMMER));

    public static final Item DWARVEN_SMITHING_HAMMER = registerItemHandheld("dwarven_smithing_hammer",
            new SmithingHammerItem(new Item.Settings(), ModToolMaterials.KHAZAD_STEEL_HAMMER));
    public static final Item DWARVEN_NOBLE_SMITHING_HAMMER = registerItemHandheld("dwarven_noble_smithing_hammer",
            new SmithingHammerItem(new Item.Settings(), ModToolMaterials.KHAZAD_NOBLE_STEEL_HAMMER));

    public static final Item ELVEN_SMITHING_HAMMER = registerItemHandheld("elven_smithing_hammer",
            new SmithingHammerItem(new Item.Settings(), ModToolMaterials.EDHEL_STEEL_HAMMER));
    public static final Item ELVEN_NOBLE_SMITHING_HAMMER = registerItemHandheld("elven_noble_smithing_hammer",
            new SmithingHammerItem(new Item.Settings(), ModToolMaterials.EDHEL_NOBLE_STEEL_HAMMER));

    public static final Item ORCISH_SMITHING_HAMMER = registerItemHandheld("orcish_smithing_hammer",
            new SmithingHammerItem(new Item.Settings(), ModToolMaterials.BURZUM_STEEL_HAMMER));
    public static final Item ORCISH_ELITE_SMITHING_HAMMER = registerItemHandheld("orcish_elite_smithing_hammer",
            new SmithingHammerItem(new Item.Settings(), ModToolMaterials.BURZUM_NOBLE_STEEL_HAMMER));

    public static final Item MITHRIL_SMITHING_HAMMER = registerItemHandheld("mithril_smithing_hammer",
            new SmithingHammerItem(new Item.Settings(), ModToolMaterials.MITHRIL_HAMMER));

    public static final Item BRONZE_PICKAXE = registerItemHandheld("bronze_pickaxe",
            new PickaxeItem(ModToolMaterials.BRONZE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.BRONZE, 0.5f, -2.9f))));
    public static final Item BRONZE_AXE = registerItemHandheld("bronze_axe",
            new CustomAxeWeaponItem(ModToolMaterials.BRONZE));
    public static final Item BRONZE_SHOVEL = registerItemHandheld("bronze_shovel",
            new ShovelItem(ModToolMaterials.BRONZE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.BRONZE, 1.5f, -3.0f))));
    public static final Item BRONZE_HOE = registerItemHandheld("bronze_hoe",
            new HoeItem(ModToolMaterials.BRONZE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.BRONZE, -2.0f, -1.0f))));

    public static final Item CRUDE_PICKAXE = registerItemHandheld("crude_pickaxe",
            new PickaxeItem(ModToolMaterials.CRUDE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.CRUDE, 1.0f, -2.8f))));
    public static final Item CRUDE_AXE = registerItemHandheld("crude_axe",
            new AxeItem(ModToolMaterials.CRUDE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.CRUDE, 6.0f, -3.1f))));

    public static final Item CRUDE_SHOVEL = registerItemHandheld("crude_shovel",
            new ShovelItem(ModToolMaterials.CRUDE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.CRUDE, 1.5f, -3.0f))));
    public static final Item CRUDE_HOE = registerItemHandheld("crude_hoe",
            new HoeItem(ModToolMaterials.CRUDE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.CRUDE, -2.0f, -1.0f))));

    public static final Item STEEL_PICKAXE = registerItemHandheld("steel_pickaxe",
            new PickaxeItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.STEEL, 1.0f, -2.8f))));
    public static final Item STEEL_AXE = registerItemHandheld("steel_axe",
            new CustomAxeWeaponItem(ModToolMaterials.STEEL));
    public static final Item STEEL_SHOVEL = registerItemHandheld("steel_shovel",
            new ShovelItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.STEEL, 1.5f, -3.0f))));
    public static final Item STEEL_HOE = registerItemHandheld("steel_hoe",
            new HoeItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.STEEL, -2.0f, -1.0f))));

    public static final Item BURZUM_STEEL_PICKAXE = registerItemHandheld("burzum_steel_pickaxe",
            new PickaxeItem(ModToolMaterials.BURZUM_STEEL, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.BURZUM_STEEL, 1.0f, -2.8f))));
    public static final Item BURZUM_STEEL_AXE = registerItemHandheld("burzum_steel_axe",
            new CustomAxeWeaponItem(ModToolMaterials.BURZUM_STEEL));
    public static final Item BURZUM_STEEL_SHOVEL = registerItemHandheld("burzum_steel_shovel",
            new ShovelItem(ModToolMaterials.BURZUM_STEEL, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.BURZUM_STEEL, 1.5f, -3.0f))));
    public static final Item BURZUM_STEEL_HOE = registerItemHandheld("burzum_steel_hoe",
            new HoeItem(ModToolMaterials.BURZUM_STEEL, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.BURZUM_STEEL, -2.0f, -1.0f))));

    public static final Item EDHEL_STEEL_PICKAXE = registerItemHandheld("edhel_steel_pickaxe",
            new PickaxeItem(ModToolMaterials.EDHEL_STEEL, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.EDHEL_STEEL, 1.0f, -2.8f))));
    public static final Item EDHEL_STEEL_AXE = registerItemDualModel("edhel_steel_axe",
            new AxeItem(ModToolMaterials.EDHEL_STEEL, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.EDHEL_STEEL, 6.0f, -3.1f))));
    public static final Item EDHEL_STEEL_SHOVEL = registerItemHandheld("edhel_steel_shovel",
            new ShovelItem(ModToolMaterials.EDHEL_STEEL, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.EDHEL_STEEL, 1.5f, -3.0f))));
    public static final Item EDHEL_STEEL_HOE = registerItemHandheld("edhel_steel_hoe",
            new HoeItem(ModToolMaterials.EDHEL_STEEL, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.EDHEL_STEEL, -2.0f, -1.0f))));
    
    public static final Item KHAZAD_STEEL_PICKAXE = registerItemHandheld("khazad_steel_pickaxe",
            new PickaxeItem(ModToolMaterials.KHAZAD_STEEL, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.KHAZAD_STEEL, 1.0f, -2.8f))));
    public static final Item KHAZAD_STEEL_AXE = registerItemHandheld("khazad_steel_axe",
            new AxeItem(ModToolMaterials.KHAZAD_STEEL, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.KHAZAD_STEEL, 6.0f, -3.1f))));
    public static final Item KHAZAD_STEEL_SHOVEL = registerItemHandheld("khazad_steel_shovel",
            new ShovelItem(ModToolMaterials.KHAZAD_STEEL, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.KHAZAD_STEEL, 1.5f, -3.0f))));
    public static final Item KHAZAD_STEEL_HOE = registerItemHandheld("khazad_steel_hoe",
            new HoeItem(ModToolMaterials.KHAZAD_STEEL, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.KHAZAD_STEEL, -2.0f, -1.0f))));

    public static final Item MITHRIL_PICKAXE = registerItemHandheld("mithril_pickaxe",
            new PickaxeItem(ModToolMaterials.MITHRIL, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.MITHRIL, 1.0f, -2.7f)).fireproof()));
    public static final Item MITHRIL_AXE = registerItemHandheld("mithril_axe",
            new CustomAxeWeaponItem(new Item.Settings().fireproof(), ModToolMaterials.MITHRIL));
    public static final Item MITHRIL_SHOVEL = registerItemHandheld("mithril_shovel",
            new ShovelItem(ModToolMaterials.MITHRIL, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.MITHRIL, 1.5f, -3.0f)).fireproof()));
    public static final Item MITHRIL_HOE = registerItemHandheld("mithril_hoe",
            new HoeItem(ModToolMaterials.MITHRIL, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.MITHRIL, -2.0f, -1.0f)).fireproof()));
/*
    public static final Item BRONZE_BUCKET = registerItemGenerated("bronze_bucket",
            new BronzeBucketItem(Fluids.EMPTY, new Item.Settings().maxCount(16)));
    public static final Item BROKEN_BRONZE_BUCKET = registerItemGenerated("broken_bronze_bucket",
            new Item(new Item.Settings().maxCount(16)));
    public static final Item BRONZE_WATER_BUCKET = registerItemGenerated("bronze_water_bucket",
            new BronzeBucketItem(Fluids.WATER, new Item.Settings().recipeRemainder(BRONZE_BUCKET).maxCount(1)));
    public static final Item BRONZE_POWDER_SNOW_BUCKET = registerItemGenerated("bronze_powder_snow_bucket",
            new CustomPowderSnowBucket(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, new Item.Settings().maxCount(1)));
    public static final Item BRONZE_MILK_BUCKET = registerItemGenerated("bronze_milk_bucket",
            new MilkBucketItem(new Item.Settings().maxCount(1)));
    public static final Item BRONZE_PUFFERFISH_BUCKET = registerItemGenerated("bronze_pufferfish_bucket",
            new EntityBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().maxCount(1)));
    public static final Item BRONZE_SALMON_BUCKET = registerItemGenerated("bronze_salmon_bucket",
            new EntityBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().maxCount(1)));
    public static final Item BRONZE_COD_BUCKET = registerItemGenerated("bronze_cod_bucket",
            new EntityBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().maxCount(1)));
    public static final Item BRONZE_TROPICAL_FISH_BUCKET = registerItemGenerated("bronze_tropical_fish_bucket",
            new EntityBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().maxCount(1)));
    public static final Item BRONZE_AXOLOTL_BUCKET = registerItemGenerated("bronze_axolotl_bucket",
            new EntityBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_AXOLOTL, new Item.Settings().maxCount(1)));
    public static final Item BRONZE_TADPOLE_BUCKET = registerItemGenerated("bronze_tadpole_bucket",
            new EntityBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, new Item.Settings().maxCount(1)));

    public static final Item MITHRIL_BUCKET = registerItemGenerated("mithril_bucket",
            new BucketItem(Fluids.EMPTY, new Item.Settings().maxCount(16).fireproof()));
    public static final Item MITHRIL_WATER_BUCKET = registerItemGenerated("mithril_water_bucket",
            new BucketItem(Fluids.WATER, new Item.Settings().recipeRemainder(MITHRIL_BUCKET).maxCount(1).fireproof()));
    public static final Item MITHRIL_LAVA_BUCKET = registerItemGenerated("mithril_lava_bucket",
            new BucketItem(Fluids.LAVA, new Item.Settings().recipeRemainder(MITHRIL_BUCKET).maxCount(1).fireproof()));
    public static final Item MITHRIL_POWDER_SNOW_BUCKET = registerItemGenerated("mithril_powder_snow_bucket",
            new PowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, new Item.Settings().maxCount(1).fireproof()));
    public static final Item MITHRIL_MILK_BUCKET = registerItemGenerated("mithril_milk_bucket",
            new MilkBucketItem(new Item.Settings().maxCount(1).fireproof()));
    public static final Item MITHRIL_PUFFERFISH_BUCKET = registerItemGenerated("mithril_pufferfish_bucket",
            new EntityBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().maxCount(1).fireproof()));
    public static final Item MITHRIL_SALMON_BUCKET = registerItemGenerated("mithril_salmon_bucket",
            new EntityBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().maxCount(1).fireproof()));
    public static final Item MITHRIL_COD_BUCKET = registerItemGenerated("mithril_cod_bucket",
            new EntityBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().maxCount(1).fireproof()));
    public static final Item MITHRIL_TROPICAL_FISH_BUCKET = registerItemGenerated("mithril_tropical_fish_bucket",
            new EntityBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().maxCount(1).fireproof()));
    public static final Item MITHRIL_AXOLOTL_BUCKET = registerItemGenerated("mithril_axolotl_bucket",
            new EntityBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_AXOLOTL, new Item.Settings().maxCount(1).fireproof()));
    public static final Item MITHRIL_TADPOLE_BUCKET = registerItemGenerated("mithril_tadpole_bucket",
            new EntityBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, new Item.Settings().maxCount(1).fireproof()));
    */

    public static final Item PIPE = registerItem2dGUI3dPerson("pipe", new PipeItem(new Item.Settings().maxCount(1), 3));
    public static final Item CLAYSHIRE_PIPE = registerItem2dGUI3dPerson("clayshire_pipe", new PipeItem(new Item.Settings().maxCount(1), 3));
    public static final Item RIVERBEND_PIPE = registerItem2dGUI3dPerson("riverbend_pipe", new PipeItem(new Item.Settings().maxCount(1), 3 ));
    public static final Item BRIMMINGBEND_PIPE = registerItem2dGUI3dPerson("brimmingbend_pipe", new PipeItem(new Item.Settings().maxCount(1), 5));
    public static final Item LONGBOTTOM_PIPE = registerItem2dGUI3dPerson("longbottom_pipe", new PipeItem(new Item.Settings().maxCount(1), 5));


    private static Item registerItem(String name, Item item) {
        ModItemGroups.TOOLS_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerItemHandheld(String name, Item item) {
        ModItemGroups.TOOLS_CONTENTS.add(item.getDefaultStack());
        SimpleHandheldItemModel.items.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerItemDualModel(String name, Item item) {
        ModItemGroups.TOOLS_CONTENTS.add(item.getDefaultStack());
        SimpleBigItemModel.items.add(item);
        return Items.register(Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerItemGenerated(String name, Item item) {
        ModItemGroups.TOOLS_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }
    public static Item registerItem2dGUI3dPerson(String name, Item item){
        SimpleBigItemModel.genericItems.add(item);
        ModItemGroups.TOOLS_CONTENTS.add(item.getDefaultStack());
        return Items.register(Identifier.of(MiddleEarth.MOD_ID, name), item);
    }
    public static void registerModItems() {
        LoggerUtil.logDebugMsg("Registering Mod Tool Items for " + MiddleEarth.MOD_ID);
    }

}
