package net.sevenstars.middleearth.datageneration.content.tags;

import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class MineableHoe {
    public static List<Block> blocks = new ArrayList<>() {
    {
        add(NatureBlockRegistryME.LEBETHRON_LEAVES);

        add(NatureBlockRegistryME.BERRY_HOLLY_LEAVES);

        add(NatureBlockRegistryME.DRY_LARCH_LEAVES);

        add(NatureBlockRegistryME.FLOWERING_MALLORN_LEAVES);

        add(NatureBlockRegistryME.ORANGE_MAPLE_LEAVES);
        add(NatureBlockRegistryME.RED_MAPLE_LEAVES);
        add(NatureBlockRegistryME.YELLOW_MAPLE_LEAVES);

        add(NatureBlockRegistryME.DRY_PINE_LEAVES);
    }};
}
