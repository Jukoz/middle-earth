package net.sevenstars.middleearth.features.storage.block.coffers;

import net.sevenstars.middleearth.features.storage.StorageBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;

public class FirCofferBlockEntity extends ChestBlockEntity {
    public FirCofferBlockEntity(BlockPos pos, BlockState state) {
        super(StorageBlockEntities.FIR_COFFER, pos, state);

        this.setHeldStacks(DefaultedList.ofSize(this.size(), ItemStack.EMPTY));
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(MiddleEarth.id("fir_coffer").toTranslationKey("screen"));
    }
}
