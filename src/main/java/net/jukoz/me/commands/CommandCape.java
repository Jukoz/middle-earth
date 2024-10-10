package net.jukoz.me.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.commands.suggestions.AllCapesSuggestionProvider;
import net.jukoz.me.item.ModDataComponentTypes;
import net.jukoz.me.item.dataComponents.CapeDataComponent;
import net.jukoz.me.item.items.CapeChestplateItem;
import net.jukoz.me.item.items.CustomChestplateItem;
import net.jukoz.me.item.utils.ModCapes;
import net.jukoz.me.utils.ModColors;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.Objects;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandCape {
    private static final String CAPE = "cape";
    private static final String CAPE_VALUE = "cape_value";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {

        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .then(literal(CAPE)
                        .then(argument(CAPE_VALUE, StringArgumentType.string())
                                .suggests(new AllCapesSuggestionProvider())
                                .executes(CommandCape::setCape))));
    }

    private static int setCape(CommandContext<ServerCommandSource> context) {
        ModCapes cape = ModCapes.valueOf(StringArgumentType.getString(context, CAPE_VALUE).toUpperCase());

        ItemStack handStack = Objects.requireNonNull(context.getSource().getPlayer()).getInventory().getMainHandStack();

        if ((handStack.getItem() instanceof CustomChestplateItem || handStack.getItem() instanceof CapeChestplateItem) && handStack.get(ModDataComponentTypes.CAPE_DATA) != null){
            handStack.set(ModDataComponentTypes.CAPE_DATA, CapeDataComponent.newCape(cape));
            MutableText sourceText = Text.translatable("command.me.cape.success").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + cape.getName()));
            context.getSource().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
            return 0;
        }

        MutableText sourceText = Text.translatable("command.me.cape.fail");
        context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
        return 0;
    }
}
