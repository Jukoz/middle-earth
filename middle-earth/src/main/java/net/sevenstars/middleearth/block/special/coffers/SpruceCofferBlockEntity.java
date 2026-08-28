package net.sevenstars.middleearth.block.special.coffers;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;

public class SpruceCofferBlockEntity extends ChestBlockEntity {
    public SpruceCofferBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistryME.SPRUCE_COFFER, pos, state);

        this.setHeldStacks(DefaultedList.ofSize(this.size(), ItemStack.EMPTY));
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(MiddleEarth.id("spruce_coffer").toTranslationKey("screen"));
    }
}
