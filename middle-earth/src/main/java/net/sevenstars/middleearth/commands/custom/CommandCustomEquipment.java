package net.sevenstars.middleearth.commands.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.commands.ModCommands;
import net.sevenstars.middleearth.commands.suggestions.AllBackAttachmentsSuggestionProvider;
import net.sevenstars.middleearth.commands.suggestions.AllHelmetAttachmentsSuggestionProvider;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
import net.sevenstars.middleearth.item.items.armor.BackAttachmentItem;
import net.sevenstars.middleearth.item.items.armor.CustomChestplateItem;
import net.sevenstars.middleearth.item.items.armor.CustomHelmetItem;
import net.sevenstars.middleearth.item.items.armor.HelmetAttachmentItem;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsStatesME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.sevenstars.middleearth.utils.ModColors;
import java.util.Objects;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class CommandCustomEquipment {
    private static final String EQUIPMENT = "equipment";
    private static final String BACK_ATTACHMENT = "back_attachment";
    private static final String HELMET_ATTACHMENT = "helmet_attachment";
    private static final String BACK_ATTACHMENT_VALUE = "back_attachment_value";
    private static final String HELMENT_ATTACHMENT_VALUE = "helmet_attachment_value";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandRegistryAccess, Commands.CommandSelection registrationEnvironment) {

        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermission(2))
                .then(literal(EQUIPMENT)
                    .then(literal(BACK_ATTACHMENT)
                        .then(argument(BACK_ATTACHMENT_VALUE, StringArgumentType.string())
                                .suggests(new AllBackAttachmentsSuggestionProvider())
                                .executes(CommandCustomEquipment::setBackAttachment)))));

        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermission(2))
                .then(literal(EQUIPMENT)
                    .then(literal(HELMET_ATTACHMENT)
                        .then(argument(HELMENT_ATTACHMENT_VALUE, StringArgumentType.string())
                                .suggests(new AllHelmetAttachmentsSuggestionProvider())
                                .executes(CommandCustomEquipment::setHelmetAttachment)))));
    }

    private static int setBackAttachment(CommandContext<CommandSourceStack> context) {
        BackAttachmentsME backAttachment = BackAttachmentsME.valueOf(StringArgumentType.getString(context, BACK_ATTACHMENT_VALUE).toUpperCase());

        ItemStack handStack = Objects.requireNonNull(context.getSource().getPlayer()).getMainHandItem();

        if (handStack.isEmpty()){
            MutableComponent sourceText = Component.translatable("command.%s.back_attachment.hand_empty".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }

        if ((handStack.getItem() instanceof CustomChestplateItem || handStack.getItem() instanceof BackAttachmentItem)){
            handStack.set(DataComponentTypesME.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(backAttachment));
            MutableComponent sourceText = Component.translatable("command.%s.back_attachment.success".formatted(MiddleEarth.MOD_ID)).append(Component.translatable("tooltip." + MiddleEarth.MOD_ID + "." + backAttachment.getName()));
            context.getSource().sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));
            return 0;
        } else {
            MutableComponent sourceText = Component.translatable("command.%s.back_attachment.wrong_item".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }
    }

    private static int setHelmetAttachment(CommandContext<CommandSourceStack> context) {
        HelmetAttachmentsME helmetAttachment = HelmetAttachmentsME.valueOf(StringArgumentType.getString(context, HELMENT_ATTACHMENT_VALUE).toUpperCase());

        ItemStack handStack = Objects.requireNonNull(context.getSource().getPlayer()).getMainHandItem();

        if (handStack.isEmpty()){
            MutableComponent sourceText = Component.translatable("command.%s.helmet_attachment.hand_empty".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }

        if ((handStack.getItem() instanceof CustomHelmetItem || handStack.getItem() instanceof HelmetAttachmentItem)){
            if (helmetAttachment.getConstantState() == HelmetAttachmentsStatesME.DOWN){
                handStack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(true, helmetAttachment, DyedItemColor.LEATHER_COLOR));
            } else if (helmetAttachment.getConstantState() == HelmetAttachmentsStatesME.UP || helmetAttachment.getConstantState() == null){
                handStack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(false, helmetAttachment, DyedItemColor.LEATHER_COLOR));
            }
            MutableComponent sourceText = Component.translatable("command.%s.helmet_attachment.success".formatted(MiddleEarth.MOD_ID)).append(Component.translatable("tooltip." + MiddleEarth.MOD_ID + "." + helmetAttachment.getName()));
            context.getSource().sendSystemMessage(sourceText.withColor(ModColors.SUCCESS.color));
            return 0;
        } else {
            MutableComponent sourceText = Component.translatable("command.%s.helmet_attachment.wrong_item".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendSystemMessage(sourceText.withColor(ModColors.WARNING.color));
            return 0;
        }
    }
}
