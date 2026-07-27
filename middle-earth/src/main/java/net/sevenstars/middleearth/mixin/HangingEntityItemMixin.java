package net.sevenstars.middleearth.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.item.HangingEntityItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Set;

@Mixin(HangingEntityItem.class)
public class HangingEntityItemMixin {
    private static final String BOENNDAL = "painting.middle-earth.author.boenndal";
    private static final String SCOSHER = "painting.middle-earth.author.scosher";
    private static final Set<String> BOENNDAL_PAINTINGS = Set.of(
            "dwarf_portrait",
            "dwarven_plaque",
            "gondorian_tapestry",
            "hobbit_portrait",
            "human_portrait",
            "note_board",
            "orc_portrait",
            "pan_board",
            "prancing_pony",
            "rohirric_tapestry",
            "small_mirror",
            "tall_mirror",
            "tool_board"
    );

    @Inject(method = "appendHoverText", at = @At("TAIL"))
    private void middleEarth$replacePaintingAuthor(
            ItemStack stack,
            Item.TooltipContext context,
            List<Component> components,
            TooltipFlag flag,
            CallbackInfo callbackInfo
    ) {
        if (!stack.is(Items.PAINTING)) {
            return;
        }

        HolderLookup.Provider registries = context.registries();
        CustomData entityData = stack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
        if (registries == null || entityData.isEmpty()) {
            return;
        }

        entityData.read(registries.createSerializationContext(NbtOps.INSTANCE), Painting.VARIANT_MAP_CODEC)
                .result()
                .flatMap(holder -> holder.unwrapKey())
                .map(key -> key.location())
                .filter(id -> id.getNamespace().equals("middle-earth"))
                .ifPresent(id -> replaceAuthorComponent(components, id));
    }

    private static void replaceAuthorComponent(List<Component> components, ResourceLocation paintingId) {
        String authorKey;
        if (paintingId.getPath().equals("elf_portrait")) {
            authorKey = SCOSHER;
        } else if (BOENNDAL_PAINTINGS.contains(paintingId.getPath())) {
            authorKey = BOENNDAL;
        } else {
            return;
        }

        String generatedAuthorKey = paintingId.toLanguageKey("painting", "author");
        for (int index = components.size() - 1; index >= 0; index--) {
            Component component = components.get(index);
            if (component.getContents() instanceof TranslatableContents translated
                    && translated.getKey().equals(generatedAuthorKey)) {
                components.set(index, Component.translatable(authorKey).withStyle(ChatFormatting.GRAY));
                return;
            }
        }
    }
}
