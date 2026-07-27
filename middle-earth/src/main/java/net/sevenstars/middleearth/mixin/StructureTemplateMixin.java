package net.sevenstars.middleearth.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(StructureTemplate.class)
public class StructureTemplateMixin {
    @Inject(method = "processEntityInfos", at = @At("RETURN"), cancellable = true)
    private static void middleEarth$writeLegacyAttachmentPosition(
            StructureTemplate template,
            LevelAccessor level,
            BlockPos origin,
            StructurePlaceSettings settings,
            List<StructureTemplate.StructureEntityInfo> sourceInfos,
            CallbackInfoReturnable<List<StructureTemplate.StructureEntityInfo>> cir
    ) {
        List<StructureTemplate.StructureEntityInfo> processedInfos = cir.getReturnValue();
        List<StructureTemplate.StructureEntityInfo> fixedInfos = null;

        for (int index = 0; index < processedInfos.size(); index++) {
            StructureTemplate.StructureEntityInfo info = processedInfos.get(index);
            if (!middleEarth$isBlockAttachedEntity(info.nbt.getString("id"))) {
                continue;
            }

            CompoundTag fixedNbt = info.nbt.copy();
            fixedNbt.putInt("TileX", info.blockPos.getX());
            fixedNbt.putInt("TileY", info.blockPos.getY());
            fixedNbt.putInt("TileZ", info.blockPos.getZ());
            fixedNbt.remove("block_pos");

            if (fixedInfos == null) {
                fixedInfos = new ArrayList<>(processedInfos);
            }
            fixedInfos.set(index, new StructureTemplate.StructureEntityInfo(info.pos, info.blockPos, fixedNbt));
        }

        if (fixedInfos != null) {
            cir.setReturnValue(fixedInfos);
        }
    }

    private static boolean middleEarth$isBlockAttachedEntity(String entityId) {
        return switch (entityId) {
            case "minecraft:painting",
                 "minecraft:item_frame",
                 "minecraft:glow_item_frame",
                 "minecraft:leash_knot" -> true;
            default -> false;
        };
    }
}
