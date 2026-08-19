package net.sevenstars.middleearth.block.special;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabBlock;

public class OxidizableVerticalSlabBlock extends VerticalSlabBlock implements WeatheringCopper {
    private final WeatherState oxidationLevel;

    public OxidizableVerticalSlabBlock(WeatherState oxidationLevel, Properties settings) {
        super(settings);
        this.oxidationLevel = oxidationLevel;
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        this.changeOverTime(state, world, pos, random);
    }

    public boolean isRandomlyTicking(BlockState state) {
        return WeatheringCopper.getNext(state.getBlock()).isPresent();
    }

    public WeatherState getAge() {
        return this.oxidationLevel;
    }
}
