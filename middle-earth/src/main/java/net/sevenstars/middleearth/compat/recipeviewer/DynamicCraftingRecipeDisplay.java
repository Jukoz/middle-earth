package net.sevenstars.middleearth.compat.recipeviewer;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
import net.sevenstars.middleearth.item.dataComponents.MountArmorAddonComponent;
import net.sevenstars.middleearth.item.items.HeldBannerItem;
import net.sevenstars.middleearth.item.items.armor.CustomChestplateItem;
import net.sevenstars.middleearth.item.items.armor.CustomHelmetItem;
import net.sevenstars.middleearth.item.items.shields.CustomBannerShieldItem;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsStatesME;
import net.sevenstars.middleearth.recipe.BackAttachmentRecipe;
import net.sevenstars.middleearth.recipe.BackAttachmentRemovalRecipe;
import net.sevenstars.middleearth.recipe.CustomItemDecorationRecipe;
import net.sevenstars.middleearth.recipe.HelmetAttachmentRecipe;
import net.sevenstars.middleearth.recipe.HelmetAttachmentRemovalRecipe;
import net.sevenstars.middleearth.recipe.MountArmorAddonRemovalRecipe;
import net.sevenstars.middleearth.recipe.MountArmorSideSkullAddonRecipe;
import net.sevenstars.middleearth.recipe.MountArmorTopSkullAddonRecipe;
import net.sevenstars.middleearth.utils.ItemTagsME;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * Builds finite, valid examples for custom crafting recipes whose real result depends on input components.
 */
public final class DynamicCraftingRecipeDisplay {
    private static final List<MountArmorAddonComponent> MOUNT_ADDON_STATES = List.of(
            new MountArmorAddonComponent(false, false),
            new MountArmorAddonComponent(true, false),
            new MountArmorAddonComponent(false, true),
            new MountArmorAddonComponent(true, true)
    );

    private DynamicCraftingRecipeDisplay() {
    }

    public static boolean supports(CraftingRecipe recipe) {
        return recipe instanceof BackAttachmentRecipe
                || recipe instanceof BackAttachmentRemovalRecipe
                || recipe instanceof HelmetAttachmentRecipe
                || recipe instanceof HelmetAttachmentRemovalRecipe
                || recipe instanceof MountArmorAddonRemovalRecipe
                || recipe instanceof MountArmorSideSkullAddonRecipe
                || recipe instanceof MountArmorTopSkullAddonRecipe
                || recipe instanceof CustomItemDecorationRecipe;
    }

    public static Display create(CraftingRecipe recipe) {
        return switch (recipe) {
            case BackAttachmentRecipe ignored -> backAttachment(false);
            case BackAttachmentRemovalRecipe ignored -> backAttachment(true);
            case HelmetAttachmentRecipe ignored -> helmetAttachment(false);
            case HelmetAttachmentRemovalRecipe ignored -> helmetAttachment(true);
            case MountArmorAddonRemovalRecipe ignored -> mountArmorRemoval();
            case MountArmorSideSkullAddonRecipe ignored -> mountArmorSideAddon();
            case MountArmorTopSkullAddonRecipe ignored -> mountArmorTopAddon();
            case CustomItemDecorationRecipe ignored -> itemDecoration();
            default -> throw new IllegalArgumentException("Unsupported dynamic crafting recipe: "
                    + recipe.getClass().getName());
        };
    }

    private static Display backAttachment(boolean removal) {
        List<ItemStack> chestplates = defaultStacks(EquipmentItemsME.armorPiecesListChestplates,
                stack -> stack.getItem() instanceof CustomChestplateItem
                        && stack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA) == null);
        List<ItemStack> attachments = defaultStacks(EquipmentItemsME.backAttachments,
                stack -> stack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA) != null);
        List<ItemStack> attachedChestplates = combineCoverage(
                chestplates, attachments, DynamicCraftingRecipeDisplay::attachBack);

        return removal
                ? new Display(List.of(attachedChestplates, List.of(new ItemStack(Items.SHEARS))), chestplates, 2, 1)
                : new Display(List.of(chestplates, attachments), attachedChestplates, 2, 1);
    }

    private static Display helmetAttachment(boolean removal) {
        List<ItemStack> helmets = defaultStacks(EquipmentItemsME.armorPiecesListHelmets,
                stack -> stack.getItem() instanceof CustomHelmetItem
                        && stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA) == null);
        List<ItemStack> attachments = defaultStacks(EquipmentItemsME.helmetAttachments,
                stack -> stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA) != null);
        List<ItemStack> attachedHelmets = combineCoverage(
                helmets, attachments, DynamicCraftingRecipeDisplay::attachHelmet);

        return removal
                ? new Display(List.of(attachedHelmets, List.of(new ItemStack(Items.SHEARS))), helmets, 2, 1)
                : new Display(List.of(helmets, attachments), attachedHelmets, 2, 1);
    }

    private static Display mountArmorRemoval() {
        List<ItemStack> armors = registryStacks(stack -> stack.is(ItemTagsME.WARG_ARMORS));
        List<ItemStack> decoratedArmors = withMountAddonStates(armors);
        List<ItemStack> cleanArmors = decoratedArmors.stream()
                .map(stack -> {
                    ItemStack clean = stack.copyWithCount(1);
                    clean.remove(DataComponentTypesME.MOUNT_ARMOR_DATA);
                    return clean;
                })
                .toList();
        return new Display(
                List.of(decoratedArmors, List.of(new ItemStack(Items.SHEARS))),
                cleanArmors,
                2,
                1
        );
    }

    private static Display mountArmorSideAddon() {
        List<ItemStack> armors = registryStacks(stack -> stack.is(ItemTagsME.WARG_ARMORS));
        List<ItemStack> armorStates = withMountAddonStates(armors).stream()
                .filter(stack -> !currentSideAddon(stack))
                .toList();
        armorStates = withUnmodifiedStacks(armors, armorStates);
        List<ItemStack> outputs = armorStates.stream()
                .map(stack -> setMountAddon(stack, currentTopAddon(stack), true))
                .toList();
        return new Display(
                List.of(armorStates, List.of(new ItemStack(Items.STRING)),
                        List.of(new ItemStack(Items.SKELETON_SKULL))),
                outputs,
                3,
                1
        );
    }

    private static Display mountArmorTopAddon() {
        List<ItemStack> armors = List.of(
                new ItemStack(EquipmentItemsME.WARG_MORDOR_PLATE_ARMOR),
                new ItemStack(EquipmentItemsME.WARG_GUNDABAD_PLATE_ARMOR)
        );
        List<ItemStack> armorStates = withMountAddonStates(armors).stream()
                .filter(stack -> !currentTopAddon(stack))
                .toList();
        armorStates = withUnmodifiedStacks(armors, armorStates);
        List<ItemStack> outputs = armorStates.stream()
                .map(stack -> setMountAddon(stack, true, currentSideAddon(stack)))
                .toList();
        return new Display(
                List.of(armorStates, List.of(new ItemStack(Items.STICK)),
                        List.of(new ItemStack(Items.SKELETON_SKULL))),
                outputs,
                3,
                1
        );
    }

    private static Display itemDecoration() {
        List<ItemStack> targets = registryStacks(stack ->
                (stack.getItem() instanceof CustomBannerShieldItem
                        || stack.getItem() instanceof HeldBannerItem)
                        && stack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY)
                        .layers().isEmpty());
        List<ItemStack> banners = registryStacks(stack -> stack.getItem() instanceof BannerItem);
        List<ItemStack> outputs = combineCoverage(
                targets, banners, DynamicCraftingRecipeDisplay::decorateItem);
        return new Display(List.of(targets, banners), outputs, 2, 1);
    }

    private static ItemStack attachBack(ItemStack chestplate, ItemStack attachment) {
        BackAttachmentDataComponent data = attachment.get(DataComponentTypesME.BACK_ATTACHMENT_DATA);
        if (data == null) {
            return ItemStack.EMPTY;
        }
        DyedItemColor dyedColor = attachment.get(DataComponents.DYED_COLOR);
        int color = dyedColor == null ? 0 : dyedColor.rgb();
        return BackAttachmentDataComponent.setBackAttachmentWithColor(
                chestplate, data.backAttachment(), color);
    }

    private static ItemStack attachHelmet(ItemStack helmet, ItemStack attachment) {
        HelmetAttachmentDataComponent data = attachment.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);
        if (data == null) {
            return ItemStack.EMPTY;
        }
        DyedItemColor dyedColor = attachment.get(DataComponents.DYED_COLOR);
        int color = dyedColor == null ? 0 : dyedColor.rgb();
        boolean down = data.helmetAttachment().getConstantState() == HelmetAttachmentsStatesME.DOWN;
        return HelmetAttachmentDataComponent.setHelmetAttachmentWithcolor(
                helmet, down, data.helmetAttachment(), color);
    }

    private static ItemStack decorateItem(ItemStack target, ItemStack banner) {
        if (!(banner.getItem() instanceof BannerItem bannerItem)) {
            return ItemStack.EMPTY;
        }
        ItemStack output = target.copyWithCount(1);
        BannerPatternLayers patterns = banner.get(DataComponents.BANNER_PATTERNS);
        if (patterns == null) {
            output.remove(DataComponents.BANNER_PATTERNS);
        } else {
            output.set(DataComponents.BANNER_PATTERNS, patterns);
        }
        output.set(DataComponents.BASE_COLOR, bannerItem.getColor());
        return output;
    }

    private static List<ItemStack> withMountAddonStates(List<ItemStack> armors) {
        List<ItemStack> stacks = new ArrayList<>(armors.size() * MOUNT_ADDON_STATES.size());
        for (ItemStack armor : armors) {
            for (MountArmorAddonComponent state : MOUNT_ADDON_STATES) {
                ItemStack stack = armor.copyWithCount(1);
                stack.set(DataComponentTypesME.MOUNT_ARMOR_DATA, state);
                stacks.add(stack);
            }
        }
        return List.copyOf(stacks);
    }

    private static List<ItemStack> withUnmodifiedStacks(List<ItemStack> base, List<ItemStack> modified) {
        List<ItemStack> combined = new ArrayList<>(base.size() + modified.size());
        base.forEach(stack -> combined.add(stack.copyWithCount(1)));
        modified.forEach(stack -> combined.add(stack.copyWithCount(1)));
        return List.copyOf(combined);
    }

    private static boolean currentTopAddon(ItemStack stack) {
        MountArmorAddonComponent data = stack.get(DataComponentTypesME.MOUNT_ARMOR_DATA);
        return data != null && data.topArmorAddon();
    }

    private static boolean currentSideAddon(ItemStack stack) {
        MountArmorAddonComponent data = stack.get(DataComponentTypesME.MOUNT_ARMOR_DATA);
        return data != null && data.sideArmorAddon();
    }

    private static ItemStack setMountAddon(ItemStack stack, boolean top, boolean side) {
        ItemStack output = stack.copyWithCount(1);
        output.set(DataComponentTypesME.MOUNT_ARMOR_DATA, new MountArmorAddonComponent(top, side));
        return output;
    }

    private static List<ItemStack> combineCoverage(List<ItemStack> first, List<ItemStack> second,
                                                   BiFunction<ItemStack, ItemStack, ItemStack> operation) {
        if (first.isEmpty() || second.isEmpty()) {
            return List.of();
        }
        int size = Math.max(first.size(), second.size());
        List<ItemStack> outputs = new ArrayList<>(size);
        for (int index = 0; index < size; index++) {
            ItemStack output = operation.apply(
                    first.get(index % first.size()), second.get(index % second.size()));
            if (!output.isEmpty()) {
                outputs.add(output);
            }
        }
        return List.copyOf(outputs);
    }

    private static List<ItemStack> defaultStacks(Collection<Item> items, Predicate<ItemStack> filter) {
        return items.stream()
                .map(Item::getDefaultInstance)
                .filter(stack -> !stack.isEmpty())
                .filter(filter)
                .sorted(Comparator.comparing(DynamicCraftingRecipeDisplay::itemId))
                .map(ItemStack::copy)
                .toList();
    }

    private static List<ItemStack> registryStacks(Predicate<ItemStack> filter) {
        return BuiltInRegistries.ITEM.stream()
                .map(Item::getDefaultInstance)
                .filter(stack -> !stack.isEmpty())
                .filter(filter)
                .sorted(Comparator.comparing(DynamicCraftingRecipeDisplay::itemId))
                .map(ItemStack::copy)
                .toList();
    }

    private static String itemId(ItemStack stack) {
        return BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
    }

    public record Display(List<List<ItemStack>> inputs, List<ItemStack> outputs, int width, int height) {
        public Display {
            inputs = inputs.stream().map(List::copyOf).toList();
            outputs = List.copyOf(outputs);
            if (inputs.isEmpty() || inputs.stream().anyMatch(List::isEmpty) || outputs.isEmpty()) {
                throw new IllegalStateException("Dynamic crafting display has no valid examples");
            }
            if (width < 1 || height < 1 || width * height < inputs.size()) {
                throw new IllegalArgumentException("Dynamic crafting display does not fit its grid");
            }
        }
    }
}
