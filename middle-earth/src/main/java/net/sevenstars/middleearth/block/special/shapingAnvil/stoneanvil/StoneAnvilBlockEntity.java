package net.sevenstars.middleearth.block.special.shapingAnvil.stoneanvil;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
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
    public void bonk(ShapingAnvilBlockEntity entity, ServerWorld world) {
        ItemStack input = entity.getStack(0);

        ArmorTrim trim = input.get(DataComponentTypes.TRIM);
        if (trim != null){
            Optional<RegistryKey<ArmorTrimMaterial>> armorTrimMaterialRegistryKey = trim.material().getKey();
            if(armorTrimMaterialRegistryKey.isPresent()) {
                RegistryKey<ArmorTrimMaterial> armorTrimMaterial = armorTrimMaterialRegistryKey.get();
                if (!armorTrimMaterial.getValue().equals(MiddleEarth.of("bronze")) &&
                        !armorTrimMaterial.getValue().equals(MiddleEarth.of("crude")) &&
                        !armorTrimMaterial.getValue().equals(MiddleEarth.of("iron"))) {
                    return;
                }
            }
        }

        super.bonk(entity, world);
    }
}
