package net.jukoz.me.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.datageneration.content.models.SimpleHandheldItemModel;
import net.jukoz.me.datageneration.content.models.SimpleItemModel;
import net.jukoz.me.item.items.BronzeBucketItem;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.item.utils.ModToolMaterials;
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
            new PickaxeItem(ModToolMaterials.JADE, IRON_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item JADE_AXE = registerItemHandheld("jade_axe",
            new AxeItem(ModToolMaterials.JADE, IRON_AXE_DAMAGE, IRON_AXE_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item JADE_SHOVEL = registerItemHandheld("jade_shovel",
            new ShovelItem(ModToolMaterials.JADE, IRON_PICKAXE_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item JADE_HOE = registerItemHandheld("jade_hoe",
            new HoeItem(ModToolMaterials.JADE, IRON_HOE_DAMAGE, IRON_HOE_ATTACK_SPEED, new FabricItemSettings()));
    
    public static final Item BRONZE_PICKAXE = registerItemHandheld("bronze_pickaxe",
            new PickaxeItem(ModToolMaterials.BRONZE, IRON_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item BRONZE_AXE = registerItemHandheld("bronze_axe",
            new AxeItem(ModToolMaterials.BRONZE, IRON_AXE_DAMAGE, IRON_AXE_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item BRONZE_SHOVEL = registerItemHandheld("bronze_shovel",
            new ShovelItem(ModToolMaterials.BRONZE, IRON_PICKAXE_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item BRONZE_HOE = registerItemHandheld("bronze_hoe",
            new HoeItem(ModToolMaterials.BRONZE, IRON_HOE_DAMAGE, IRON_HOE_ATTACK_SPEED, new FabricItemSettings()));

    public static final Item ORC_STEEL_PICKAXE = registerItemHandheld("orc_steel_pickaxe",
            new PickaxeItem(ModToolMaterials.ORC_STEEL, IRON_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item ORC_STEEL_AXE = registerItemHandheld("orc_steel_axe",
            new AxeItem(ModToolMaterials.ORC_STEEL, IRON_AXE_DAMAGE, IRON_AXE_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item ORC_STEEL_SHOVEL = registerItemHandheld("orc_steel_shovel",
            new ShovelItem(ModToolMaterials.ORC_STEEL, IRON_PICKAXE_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item ORC_STEEL_HOE = registerItemHandheld("orc_steel_hoe",
            new HoeItem(ModToolMaterials.ORC_STEEL, IRON_HOE_DAMAGE, IRON_HOE_ATTACK_SPEED, new FabricItemSettings()));

    public static final Item STEEL_PICKAXE = registerItemHandheld("steel_pickaxe",
            new PickaxeItem(ModToolMaterials.STEEL, IRON_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item STEEL_AXE = registerItemHandheld("steel_axe",
            new AxeItem(ModToolMaterials.STEEL, IRON_AXE_DAMAGE, IRON_AXE_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item STEEL_SHOVEL = registerItemHandheld("steel_shovel",
            new ShovelItem(ModToolMaterials.STEEL, IRON_PICKAXE_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item STEEL_HOE = registerItemHandheld("steel_hoe",
            new HoeItem(ModToolMaterials.STEEL, IRON_HOE_DAMAGE, IRON_HOE_ATTACK_SPEED, new FabricItemSettings()));

    public static final Item URUK_STEEL_PICKAXE = registerItemHandheld("uruk_steel_pickaxe",
            new PickaxeItem(ModToolMaterials.URUK_STEEL, IRON_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item URUK_STEEL_AXE = registerItemHandheld("uruk_steel_axe",
            new AxeItem(ModToolMaterials.URUK_STEEL, IRON_AXE_DAMAGE, IRON_AXE_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item URUK_STEEL_SHOVEL = registerItemHandheld("uruk_steel_shovel",
            new ShovelItem(ModToolMaterials.URUK_STEEL, IRON_PICKAXE_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item URUK_STEEL_HOE = registerItemHandheld("uruk_steel_hoe",
            new HoeItem(ModToolMaterials.URUK_STEEL, IRON_HOE_DAMAGE, IRON_HOE_ATTACK_SPEED, new FabricItemSettings()));

    public static final Item ELVEN_STEEL_PICKAXE = registerItemHandheld("elven_steel_pickaxe",
            new PickaxeItem(ModToolMaterials.ELVEN_STEEL, IRON_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item ELVEN_STEEL_AXE = registerItemHandheld("elven_steel_axe",
            new AxeItem(ModToolMaterials.ELVEN_STEEL, IRON_AXE_DAMAGE, IRON_AXE_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item ELVEN_STEEL_SHOVEL = registerItemHandheld("elven_steel_shovel",
            new ShovelItem(ModToolMaterials.ELVEN_STEEL, IRON_PICKAXE_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item ELVEN_STEEL_HOE = registerItemHandheld("elven_steel_hoe",
            new HoeItem(ModToolMaterials.ELVEN_STEEL, IRON_HOE_DAMAGE, IRON_HOE_ATTACK_SPEED, new FabricItemSettings()));
    
    public static final Item DWARVEN_STEEL_PICKAXE = registerItemHandheld("dwarven_steel_pickaxe",
            new PickaxeItem(ModToolMaterials.DWARVEN_STEEL, IRON_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item DWARVEN_STEEL_AXE = registerItemHandheld("dwarven_steel_axe",
            new AxeItem(ModToolMaterials.DWARVEN_STEEL, IRON_AXE_DAMAGE, IRON_AXE_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item DWARVEN_STEEL_SHOVEL = registerItemHandheld("dwarven_steel_shovel",
            new ShovelItem(ModToolMaterials.DWARVEN_STEEL, IRON_PICKAXE_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item DWARVEN_STEEL_HOE = registerItemHandheld("dwarven_steel_hoe",
            new HoeItem(ModToolMaterials.DWARVEN_STEEL, IRON_HOE_DAMAGE, IRON_HOE_ATTACK_SPEED, new FabricItemSettings()));

    public static final Item MITHRIL_PICKAXE = registerItemHandheld("mithril_pickaxe",
            new PickaxeItem(ModToolMaterials.MITHRIL, IRON_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings().fireproof()));
    public static final Item MITHRIL_AXE = registerItemHandheld("mithril_axe",
            new AxeItem(ModToolMaterials.MITHRIL, IRON_AXE_DAMAGE, IRON_AXE_ATTACK_SPEED, new FabricItemSettings().fireproof()));
    public static final Item MITHRIL_SHOVEL = registerItemHandheld("mithril_shovel",
            new ShovelItem(ModToolMaterials.MITHRIL, IRON_PICKAXE_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings().fireproof()));
    public static final Item MITHRIL_HOE = registerItemHandheld("mithril_hoe",
            new HoeItem(ModToolMaterials.MITHRIL, IRON_HOE_DAMAGE, IRON_HOE_ATTACK_SPEED, new FabricItemSettings().fireproof()));

    public static final Item BRONZE_BUCKET = registerItemGenerated("bronze_bucket",
            new BronzeBucketItem(Fluids.EMPTY, new FabricItemSettings().maxCount(16)));
    public static final Item BROKEN_BRONZE_BUCKET = registerItemGenerated("broken_bronze_bucket",
            new Item(new FabricItemSettings().maxCount(16)));
    public static final Item BRONZE_WATER_BUCKET = registerItemGenerated("bronze_water_bucket",
            new BucketItem(Fluids.WATER, new FabricItemSettings().maxCount(1)));
    public static final Item BRONZE_POWDER_SNOW_BUCKET = registerItemGenerated("bronze_powder_snow_bucket",
            new PowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, new FabricItemSettings().maxCount(1)));
    public static final Item BRONZE_MILK_BUCKET = registerItemGenerated("bronze_milk_bucket",
            new MilkBucketItem(new FabricItemSettings().maxCount(1)));
    public static final Item BRONZE_PUFFERFISH_BUCKET = registerItemGenerated("bronze_pufferfish_bucket",
            new EntityBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item BRONZE_SALMON_BUCKET = registerItemGenerated("bronze_salmon_bucket",
            new EntityBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item BRONZE_COD_BUCKET = registerItemGenerated("bronze_cod_bucket",
            new EntityBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item BRONZE_TROPICAL_FISH_BUCKET = registerItemGenerated("bronze_tropical_fish_bucket",
            new EntityBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));
    public static final Item BRONZE_AXOLOTL_BUCKET = registerItemGenerated("bronze_axolotl_bucket",
            new EntityBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_AXOLOTL, new FabricItemSettings().maxCount(1)));
    public static final Item BRONZE_TADPOLE_BUCKET = registerItemGenerated("bronze_tadpole_bucket",
            new EntityBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, new FabricItemSettings().maxCount(1)));

    public static final Item MITHRIL_BUCKET = registerItemGenerated("mithril_bucket",
            new BucketItem(Fluids.EMPTY, new FabricItemSettings().maxCount(16).fireproof()));
    public static final Item MITHRIL_WATER_BUCKET = registerItemGenerated("mithril_water_bucket",
            new BucketItem(Fluids.WATER, new FabricItemSettings().maxCount(1).fireproof()));
    public static final Item MITHRIL_LAVA_BUCKET = registerItemGenerated("mithril_lava_bucket",
            new BucketItem(Fluids.LAVA, new FabricItemSettings().maxCount(1).fireproof()));
    public static final Item MITHRIL_POWDER_SNOW_BUCKET = registerItemGenerated("mithril_powder_snow_bucket",
            new PowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, new FabricItemSettings().maxCount(1).fireproof()));
    public static final Item MITHRIL_MILK_BUCKET = registerItemGenerated("mithril_milk_bucket",
            new MilkBucketItem(new FabricItemSettings().maxCount(1).fireproof()));
    public static final Item MITHRIL_PUFFERFISH_BUCKET = registerItemGenerated("mithril_pufferfish_bucket",
            new EntityBucketItem(EntityType.PUFFERFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1).fireproof()));
    public static final Item MITHRIL_SALMON_BUCKET = registerItemGenerated("mithril_salmon_bucket",
            new EntityBucketItem(EntityType.SALMON, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1).fireproof()));
    public static final Item MITHRIL_COD_BUCKET = registerItemGenerated("mithril_cod_bucket",
            new EntityBucketItem(EntityType.COD, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1).fireproof()));
    public static final Item MITHRIL_TROPICAL_FISH_BUCKET = registerItemGenerated("mithril_tropical_fish_bucket",
            new EntityBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1).fireproof()));
    public static final Item MITHRIL_AXOLOTL_BUCKET = registerItemGenerated("mithril_axolotl_bucket",
            new EntityBucketItem(EntityType.AXOLOTL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_AXOLOTL, new FabricItemSettings().maxCount(1).fireproof()));
    public static final Item MITHRIL_TADPOLE_BUCKET = registerItemGenerated("mithril_tadpole_bucket",
            new EntityBucketItem(EntityType.TADPOLE, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, new FabricItemSettings().maxCount(1).fireproof()));
    
    private static Item registerItemHandheld(String name, Item item) {
        ModItemGroups.TOOLS_CONTENTS.add(item.getDefaultStack());
        SimpleHandheldItemModel.items.add(item);
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerItemGenerated(String name, Item item) {
        ModItemGroups.TOOLS_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.debug("Registering Mod Tool Items for " + MiddleEarth.MOD_ID);
    }
}
