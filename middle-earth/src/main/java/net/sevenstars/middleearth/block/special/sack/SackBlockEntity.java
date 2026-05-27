package net.sevenstars.middleearth.block.special.sack;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;

public class SackBlockEntity extends ChestBlockEntity {
    public SackBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BEECH_COFFER, pos, state);

        this.setHeldStacks(DefaultedList.ofSize(this.size(), ItemStack.EMPTY));
    }

    @Override
    public int size() {
        return 9;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(MiddleEarth.of("sack").toTranslationKey("screen"));
    }
}
