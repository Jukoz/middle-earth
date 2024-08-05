package net.jukoz.me.item;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.datageneration.content.models.SimpleHandheldItemModel;
import net.jukoz.me.datageneration.content.models.SimpleItemModel;
import net.jukoz.me.item.items.BronzeBucketItem;
import net.jukoz.me.item.items.CustomPowderSnowBucket;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.item.utils.ModToolMaterials;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ModToolItems {
    private static final int IRON_DAMAGE = 1;
    private static final int IRON_AXE_DAMAGE = IRON_DAMAGE + 5;
    private static final int IRON_PICKAXE_DAMAGE = IRON_DAMAGE - 1;
    private static final int IRON_HOE_DAMAGE = IRON_DAMAGE - 2;
    private static final float IRON_ATTACK_SPEED = -2.5f;
    private static final float IRON_AXE_ATTACK_SPEED = -3.1f;
    private static final float IRON_HOE_ATTACK_SPEED = -2f;

    public static final Item JADE_PICKAXE = registerItemHandheld("jade_pickaxe",
            new PickaxeItem(ModToolMaterials.JADE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.JADE, 0.0f, -3.0f))));
    public static final Item JADE_AXE = registerItemHandheld("jade_axe",
            new AxeItem(ModToolMaterials.JADE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.JADE, 6.0f, -3.1f))));
    public static final Item JADE_SHOVEL = registerItemHandheld("jade_shovel",
            new ShovelItem(ModToolMaterials.JADE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.JADE, 1.5f, -3.0f))));
    public static final Item JADE_HOE = registerItemHandheld("jade_hoe",
            new HoeItem(ModToolMaterials.JADE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.JADE, -2.0f, -1.0f))));
    
    public static final Item BRONZE_PICKAXE = registerItemHandheld("bronze_pickaxe",
            new PickaxeItem(ModToolMaterials.BRONZE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.BRONZE, 0.5f, -2.9f))));
    public static final Item BRONZE_AXE = registerItemHandheld("bronze_axe",
            new AxeItem(ModToolMaterials.BRONZE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.BRONZE, 6.0f, -3.1f))));
    public static final Item BRONZE_SHOVEL = registerItemHandheld("bronze_shovel",
            new ShovelItem(ModToolMaterials.BRONZE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.BRONZE, 1.5f, -3.0f))));
    public static final Item BRONZE_HOE = registerItemHandheld("bronze_hoe",
            new HoeItem(ModToolMaterials.BRONZE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.BRONZE, -2.0f, -1.0f))));

    public static final Item ORC_STEEL_PICKAXE = registerItemHandheld("orc_steel_pickaxe",
            new PickaxeItem(ModToolMaterials.ORC_STEEL, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.ORC_STEEL, 1.0f, -2.8f))));
    public static final Item ORC_STEEL_AXE = registerItemHandheld("orc_steel_axe",
            new AxeItem(ModToolMaterials.ORC_STEEL, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.ORC_STEEL, 6.0f, -3.1f))));
    public static final Item ORC_STEEL_SHOVEL = registerItemHandheld("orc_steel_shovel",
            new ShovelItem(ModToolMaterials.ORC_STEEL, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.ORC_STEEL, 1.5f, -3.0f))));
    public static final Item ORC_STEEL_HOE = registerItemHandheld("orc_steel_hoe",
            new HoeItem(ModToolMaterials.ORC_STEEL, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.ORC_STEEL, -2.0f, -1.0f))));

    public static final Item STEEL_PICKAXE = registerItemHandheld("steel_pickaxe",
            new PickaxeItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.STEEL, 1.0f, -2.8f))));
    public static final Item STEEL_AXE = registerItemHandheld("steel_axe",
            new AxeItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.STEEL, 6.0f, -3.1f))));
    public static final Item STEEL_SHOVEL = registerItemHandheld("steel_shovel",
            new ShovelItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.STEEL, 1.5f, -3.0f))));
    public static final Item STEEL_HOE = registerItemHandheld("steel_hoe",
            new HoeItem(ModToolMaterials.STEEL, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.STEEL, -2.0f, -1.0f))));

    public static final Item URUK_STEEL_PICKAXE = registerItemHandheld("uruk_steel_pickaxe",
            new PickaxeItem(ModToolMaterials.URUK_STEEL, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.URUK_STEEL, 1.0f, -2.8f))));
    public static final Item URUK_STEEL_AXE = registerItemHandheld("uruk_steel_axe",
            new AxeItem(ModToolMaterials.URUK_STEEL, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.URUK_STEEL, 6.0f, -3.1f))));
    public static final Item URUK_STEEL_SHOVEL = registerItemHandheld("uruk_steel_shovel",
            new ShovelItem(ModToolMaterials.URUK_STEEL, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.URUK_STEEL, 1.5f, -3.0f))));
    public static final Item URUK_STEEL_HOE = registerItemHandheld("uruk_steel_hoe",
            new HoeItem(ModToolMaterials.URUK_STEEL, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.URUK_STEEL, -2.0f, -1.0f))));

    public static final Item ELVEN_STEEL_PICKAXE = registerItemHandheld("elven_steel_pickaxe",
            new PickaxeItem(ModToolMaterials.ELVEN_STEEL, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.ELVEN_STEEL, 1.0f, -2.8f))));
    public static final Item ELVEN_STEEL_AXE = registerItemHandheld("elven_steel_axe",
            new AxeItem(ModToolMaterials.ELVEN_STEEL, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.ELVEN_STEEL, 6.0f, -3.1f))));
    public static final Item ELVEN_STEEL_SHOVEL = registerItemHandheld("elven_steel_shovel",
            new ShovelItem(ModToolMaterials.ELVEN_STEEL, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.ELVEN_STEEL, 1.5f, -3.0f))));
    public static final Item ELVEN_STEEL_HOE = registerItemHandheld("elven_steel_hoe",
            new HoeItem(ModToolMaterials.ELVEN_STEEL, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.ELVEN_STEEL, -2.0f, -1.0f))));
    
    public static final Item DWARVEN_STEEL_PICKAXE = registerItemHandheld("dwarven_steel_pickaxe",
            new PickaxeItem(ModToolMaterials.DWARVEN_STEEL, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.DWARVEN_STEEL, 1.0f, -2.8f))));
    public static final Item DWARVEN_STEEL_AXE = registerItemHandheld("dwarven_steel_axe",
            new AxeItem(ModToolMaterials.DWARVEN_STEEL, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.DWARVEN_STEEL, 6.0f, -3.1f))));
    public static final Item DWARVEN_STEEL_SHOVEL = registerItemHandheld("dwarven_steel_shovel",
            new ShovelItem(ModToolMaterials.DWARVEN_STEEL, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.DWARVEN_STEEL, 1.5f, -3.0f))));
    public static final Item DWARVEN_STEEL_HOE = registerItemHandheld("dwarven_steel_hoe",
            new HoeItem(ModToolMaterials.DWARVEN_STEEL, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.DWARVEN_STEEL, -2.0f, -1.0f))));

    public static final Item MITHRIL_PICKAXE = registerItemHandheld("mithril_pickaxe",
            new PickaxeItem(ModToolMaterials.MITHRIL, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.MITHRIL, 1.0f, -2.7f)).fireproof()));
    public static final Item MITHRIL_AXE = registerItemHandheld("mithril_axe",
            new AxeItem(ModToolMaterials.MITHRIL, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.MITHRIL, 6.0f, -3.1f)).fireproof()));
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
    private static Item registerItemHandheld(String name, Item item) {
        ModItemGroups.TOOLS_CONTENTS.add(item.getDefaultStack());
        SimpleHandheldItemModel.items.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerItemGenerated(String name, Item item) {
        ModItemGroups.TOOLS_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        LoggerUtil.logDebugMsg("Registering Mod Tool Items for " + MiddleEarth.MOD_ID);
    }
}
