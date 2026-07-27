package net.sevenstars.middleearth.block.special.coffers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;

public class BeechCofferBlockEntity extends ChestBlockEntity {
    public BeechCofferBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BEECH_COFFER, pos, state);

        this.setItems(NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(MiddleEarth.of("beech_coffer").toLanguageKey("screen"));
    }
}
