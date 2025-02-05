package net.sevenstars.middleearth.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.commands.ModCommands;
import net.sevenstars.middleearth.commands.suggestions.AllCapesSuggestionProvider;
import net.sevenstars.middleearth.commands.suggestions.AllHoodsSuggestionProvider;
import net.sevenstars.middleearth.item.ModDataComponentTypes;
import net.sevenstars.middleearth.item.dataComponents.CapeDataComponent;
import net.sevenstars.middleearth.item.dataComponents.CustomDyeableDataComponent;
import net.sevenstars.middleearth.item.dataComponents.HoodDataComponent;
import net.sevenstars.middleearth.item.items.armor.CapeChestplateItem;
import net.sevenstars.middleearth.item.items.armor.CustomChestplateItem;
import net.sevenstars.middleearth.item.items.armor.CustomHelmetItem;
import net.sevenstars.middleearth.item.items.armor.HoodHelmetItem;
import net.sevenstars.middleearth.item.utils.armor.capes.ModCapes;
import net.sevenstars.middleearth.item.utils.armor.hoods.ModHoodStates;
import net.sevenstars.middleearth.item.utils.armor.hoods.ModHoods;
import net.sevenstars.middleearth.utils.ModColors;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.Objects;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandCustomEquipment {
    private static final String EQUIPMENT = "equipment";
    private static final String CAPE = "cape";
    private static final String HOOD = "hood";
    private static final String CAPE_VALUE = "cape_value";
    private static final String HOOD_VALUE = "hood_value";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {

        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2))
                .then(literal(EQUIPMENT)
                    .then(literal(CAPE)
                        .then(argument(CAPE_VALUE, StringArgumentType.string())
                                .suggests(new AllCapesSuggestionProvider())
                                .executes(CommandCustomEquipment::setCape)))));

        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2))
                .then(literal(EQUIPMENT)
                    .then(literal(HOOD)
                        .then(argument(HOOD_VALUE, StringArgumentType.string())
                                .suggests(new AllHoodsSuggestionProvider())
                                .executes(CommandCustomEquipment::setHood)))));
    }

    private static int setCape(CommandContext<ServerCommandSource> context) {
        ModCapes cape = ModCapes.valueOf(StringArgumentType.getString(context, CAPE_VALUE).toUpperCase());

        ItemStack handStack = Objects.requireNonNull(context.getSource().getPlayer()).getInventory().getMainHandStack();

        if (handStack.isEmpty()){
            MutableText sourceText = Text.translatable("command.me.cape.hand_empty");
            context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }

        // TODO: literally cape-thingy such as gondorian_hero_cape should not be setCape-d
        if ((handStack.getItem() instanceof CustomChestplateItem || handStack.getItem() instanceof CapeChestplateItem)){
            handStack.set(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(cape));
            MutableText sourceText = Text.translatable("command.me.cape.success").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + cape.getName()));
            context.getSource().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
            return 0;
        } else {
            MutableText sourceText = Text.translatable("command.me.cape.wrong_item");
            context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }
    }

    private static int setHood(CommandContext<ServerCommandSource> context) {
        ModHoods hood = ModHoods.valueOf(StringArgumentType.getString(context, HOOD_VALUE).toUpperCase());

        ItemStack handStack = Objects.requireNonNull(context.getSource().getPlayer()).getInventory().getMainHandStack();

        if (handStack.isEmpty()){
            MutableText sourceText = Text.translatable("command.me.hood.hand_empty");
            context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }

        if ((handStack.getItem() instanceof CustomHelmetItem || handStack.getItem() instanceof HoodHelmetItem)){
            if (hood.getConstantState() == ModHoodStates.DOWN){
                handStack.set(ModDataComponentTypes.HOOD_DATA, new HoodDataComponent(true, hood, CustomDyeableDataComponent.DEFAULT_COLOR));
            } else if (hood.getConstantState() == ModHoodStates.UP || hood.getConstantState() == null){
                handStack.set(ModDataComponentTypes.HOOD_DATA, new HoodDataComponent(false, hood, CustomDyeableDataComponent.DEFAULT_COLOR));
            }
            MutableText sourceText = Text.translatable("command.me.hood.success").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + hood.getName()));
            context.getSource().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
            return 0;
        } else {
            MutableText sourceText = Text.translatable("command.me.hood.wrong_item");
            context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }
    }
}
