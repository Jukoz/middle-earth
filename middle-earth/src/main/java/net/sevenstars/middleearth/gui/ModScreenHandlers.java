package net.sevenstars.middleearth.gui;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.crockpot.CrockpotScreenHandler;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreenHandler;
import net.sevenstars.middleearth.gui.forge.ForgeAlloyingScreenHandler;
import net.sevenstars.middleearth.gui.shapinganvil.ShapingAnvilScreenHandler;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenHandler;
import net.sevenstars.middleearth.gui.wood_pile.WoodPileScreenHandler;
import net.sevenstars.middleearth.gui.inscriptiontable.InscriptionTableScreenHandler;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenData;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreenData;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreenHandler;

public class ModScreenHandlers {
    public static MenuType<WoodPileScreenHandler> WOOD_PILE_SCREEN_HANDLER
            = new MenuType<>(WoodPileScreenHandler::new, FeatureFlags.VANILLA_SET);
    
    public static final MenuType<CrockpotScreenHandler> CROCKPOT_SCREEN_HANDLER
            = IMenuTypeExtension.create((syncId, inventory, buffer) ->
                    new CrockpotScreenHandler(syncId, inventory, BlockPos.STREAM_CODEC.decode(buffer)));

    public static MenuType<ArtisanTableScreenHandler> ARTISAN_SCREEN_HANDLER
            = IMenuTypeExtension.create((syncId, inventory, buffer) ->
                    new ArtisanTableScreenHandler(syncId, inventory, ByteBufCodecs.STRING_UTF8.decode(buffer)));

    public static MenuType<InscriptionTableScreenHandler> INSCRIPTION_SCREEN_HANDLER
            = new MenuType<>(InscriptionTableScreenHandler::new, FeatureFlags.VANILLA_SET);

    public static MenuType<ShapingAnvilScreenHandler> TREATED_ANVIL_SCREEN_HANDLER
            = IMenuTypeExtension.create((syncId, inventory, buffer) ->
                    new ShapingAnvilScreenHandler(syncId, inventory, BlockPos.STREAM_CODEC.decode(buffer)));

    public static final MenuType<ForgeAlloyingScreenHandler> FORGE_ALLOYING_SCREEN_HANDLER
            = IMenuTypeExtension.create((syncId, inventory, buffer) ->
                    new ForgeAlloyingScreenHandler(syncId, inventory, BlockPos.STREAM_CODEC.decode(buffer)));

    public static final MenuType<StructureManagerScreenHandler> STRUCTURE_MANAGER_SCREEN_HANDLER
            = IMenuTypeExtension.create((syncId, inventory, buffer) ->
                    new StructureManagerScreenHandler(syncId, inventory, StructureManagerScreenData.PACKET_CODEC.decode(buffer)));

    public static final MenuType<StructureNestScreenHandler> STRUCTURE_NEST_SCREEN_HANDLER
            = IMenuTypeExtension.create((syncId, inventory, buffer) ->
                    new StructureNestScreenHandler(syncId, inventory, StructureNestScreenData.PACKET_CODEC.decode(buffer)));


    public static void registerAllScreenHandlers() {
        register("wood_pile", WOOD_PILE_SCREEN_HANDLER);
        register("forge_alloying", FORGE_ALLOYING_SCREEN_HANDLER);
        register("artisan_table", ARTISAN_SCREEN_HANDLER);
        register("inscription_table", INSCRIPTION_SCREEN_HANDLER);
        register("treated_anvil", TREATED_ANVIL_SCREEN_HANDLER);
        register("structure_manager", STRUCTURE_MANAGER_SCREEN_HANDLER);
        register("structure_nest", STRUCTURE_NEST_SCREEN_HANDLER);
        register("crockpot",CROCKPOT_SCREEN_HANDLER );
    }

    private static void register(String name, MenuType<?> handlerType) {
        RegistrationBridge.register(BuiltInRegistries.MENU, MiddleEarth.of(name), handlerType);
    }
}
