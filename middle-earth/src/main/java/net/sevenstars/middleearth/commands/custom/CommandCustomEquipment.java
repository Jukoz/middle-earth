package net.sevenstars.middleearth.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.commands.ModCommands;
import net.sevenstars.middleearth.commands.suggestions.AllBackAttachmentsSuggestionProvider;
import net.sevenstars.middleearth.commands.suggestions.AllHelmetAttachmentsSuggestionProvider;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.dataComponents.CustomDyeableDataComponent;
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
import net.sevenstars.middleearth.item.items.armor.BackAttachmentItem;
import net.sevenstars.middleearth.item.items.armor.CustomChestplateItem;
import net.sevenstars.middleearth.item.items.armor.CustomHelmetItem;
import net.sevenstars.middleearth.item.items.armor.HelmetAttachmentItem;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsStatesME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
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
    private static final String BACK_ATTACHMENT = "back_attachment";
    private static final String HELMET_ATTACHMENT = "helmet_attachment";
    private static final String BACK_ATTACHMENT_VALUE = "back_attachment_value";
    private static final String HELMENT_ATTACHMENT_VALUE = "helmet_attachment_value";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {

        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2))
                .then(literal(EQUIPMENT)
                    .then(literal(BACK_ATTACHMENT)
                        .then(argument(BACK_ATTACHMENT_VALUE, StringArgumentType.string())
                                .suggests(new AllBackAttachmentsSuggestionProvider())
                                .executes(CommandCustomEquipment::setBackAttachment)))));

        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2))
                .then(literal(EQUIPMENT)
                    .then(literal(HELMET_ATTACHMENT)
                        .then(argument(HELMENT_ATTACHMENT_VALUE, StringArgumentType.string())
                                .suggests(new AllHelmetAttachmentsSuggestionProvider())
                                .executes(CommandCustomEquipment::setHelmetAttachment)))));
    }

    private static int setBackAttachment(CommandContext<ServerCommandSource> context) {
        BackAttachmentsME backAttachment = BackAttachmentsME.valueOf(StringArgumentType.getString(context, BACK_ATTACHMENT_VALUE).toUpperCase());

        ItemStack handStack = Objects.requireNonNull(context.getSource().getPlayer()).getInventory().getSelectedStack();

        if (handStack.isEmpty()){
            MutableText sourceText = Text.translatable("command.%s.back_attachment.hand_empty".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }

        if ((handStack.getItem() instanceof CustomChestplateItem || handStack.getItem() instanceof BackAttachmentItem)){
            handStack.set(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(backAttachment));
            MutableText sourceText = Text.translatable("command.%s.back_attachment.success".formatted(MiddleEarth.MOD_ID)).append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + backAttachment.getName()));
            context.getSource().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
            return 0;
        } else {
            MutableText sourceText = Text.translatable("command.%s.back_attachment.wrong_item".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }
    }

    private static int setHelmetAttachment(CommandContext<ServerCommandSource> context) {
        HelmetAttachmentsME helmetAttachment = HelmetAttachmentsME.valueOf(StringArgumentType.getString(context, HELMENT_ATTACHMENT_VALUE).toUpperCase());

        ItemStack handStack = Objects.requireNonNull(context.getSource().getPlayer()).getInventory().getSelectedStack();

        if (handStack.isEmpty()){
            MutableText sourceText = Text.translatable("command.%s.helmetAttachment.hand_empty".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }

        if ((handStack.getItem() instanceof CustomHelmetItem || handStack.getItem() instanceof HelmetAttachmentItem)){
            if (helmetAttachment.getConstantState() == HelmetAttachmentsStatesME.DOWN){
                handStack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(true, helmetAttachment, CustomDyeableDataComponent.DEFAULT_COLOR));
            } else if (helmetAttachment.getConstantState() == HelmetAttachmentsStatesME.UP || helmetAttachment.getConstantState() == null){
                handStack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(false, helmetAttachment, CustomDyeableDataComponent.DEFAULT_COLOR));
            }
            MutableText sourceText = Text.translatable("command.%s.helme_attachment.success".formatted(MiddleEarth.MOD_ID)).append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + helmetAttachment.getName()));
            context.getSource().sendMessage(sourceText.withColor(ModColors.SUCCESS.color));
            return 0;
        } else {
            MutableText sourceText = Text.translatable("command.%s.helmet_attachment.wrong_item".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }
    }
}
