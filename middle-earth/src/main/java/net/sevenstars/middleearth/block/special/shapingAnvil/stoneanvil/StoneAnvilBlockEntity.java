package net.sevenstars.middleearth.block.special.shapingAnvil.stoneanvil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import net.sevenstars.middleearth.block.special.shapingAnvil.ShapingAnvilBlockEntity;

import java.util.Optional;

public class StoneAnvilBlockEntity extends ShapingAnvilBlockEntity {

    public StoneAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STONE_ANVIL, pos, state);
    }

    public StoneAnvilBlockEntity(BlockEntityType type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void bonk(ShapingAnvilBlockEntity entity, ServerLevel world) {
        ItemStack input = entity.getItem(0);

        ArmorTrim trim = input.get(DataComponents.TRIM);
        if (trim != null){
            Optional<ResourceKey<TrimMaterial>> armorTrimMaterialRegistryKey = trim.material().unwrapKey();
            if(armorTrimMaterialRegistryKey.isPresent()) {
                ResourceKey<TrimMaterial> armorTrimMaterial = armorTrimMaterialRegistryKey.get();
                if (!armorTrimMaterial.location().equals(MiddleEarth.of("bronze")) &&
                        !armorTrimMaterial.location().equals(MiddleEarth.of("crude")) &&
                        !armorTrimMaterial.location().equals(MiddleEarth.of("iron"))) {
                    return;
                }
            }
        }

        super.bonk(entity, world);
    }
}
