package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.block.enums.DoubleBlockHalf;
import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.sevenstars.middleearth.block.special.DroopingIciclesBlock;

final class Icicles {
    private Icicles() {
    }

    // a long icicle takes this block and the one below it
    static void hang(BlockBuffer blocks, int x, int y, int z, boolean drooping) {
        if (drooping) {
            blocks.put(x, y, z, NatureBlockRegistryME.DROOPING_ICICLES.getDefaultState().with(DroopingIciclesBlock.HALF, DoubleBlockHalf.UPPER));
            blocks.put(x, y - 1, z, NatureBlockRegistryME.DROOPING_ICICLES.getDefaultState().with(DroopingIciclesBlock.HALF, DoubleBlockHalf.LOWER));
        } else {
            blocks.put(x, y, z, NatureBlockRegistryME.SHORT_ICICLES.getDefaultState());
        }
    }

    // roll is out of 256; half the hits are long icicles where there is room
    static void maybeHang(BlockBuffer blocks, int x, int y, int z, int roll, int chance) {
        if (roll >= chance || !blocks.get(x, y, z).isAir()) return;
        hang(blocks, x, y, z, roll < chance / 2 && blocks.get(x, y - 1, z).isAir());
    }
}
