package net.sevenstars.middleearth.block.special.gems;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.sevenstars.middleearth.light.BeamLitBlock;
import net.sevenstars.middleearth.registries.content.gems.GemTypeRegistry;
import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/** A gem hidden in rock: looks and mines like its host stone, with only a coloured glint as a tell. */
public class GemOreBlock extends ExperienceDroppingBlock implements BeamLitBlock {
    public static final MapCodec<GemOreBlock> CODEC = createCodec(GemOreBlock::new);
    public static final EnumProperty<GemKind> GEM = EnumProperty.of("gem", GemKind.class);
    public static final EnumProperty<GemOreStone> STONE = EnumProperty.of("stone", GemOreStone.class);

    private static final float SPARKS_PER_TICK_AT_FULL_LIGHT = 2.0F;
    private static final float GLEAM_SHARE = 0.25F;
    private static final float DISPLAY_TICKS_PER_SECOND = 0.46F;
    private static final float FULL_LIGHT = 15;
    private static final float DIMMEST = 0.2F;

    public GemOreBlock(Settings settings) {
        super(UniformIntProvider.create(3, 7), settings);
        setDefaultState(getStateManager().getDefaultState().with(GEM, GemKind.RUBY).with(STONE, GemOreStone.STONE));
    }

    @Override
    public MapCodec<? extends ExperienceDroppingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(GEM, STONE);
    }

    @Override
    protected float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        BlockState disguise = state.get(STONE).block().getDefaultState();
        float hardness = disguise.getHardness(world, pos);
        if (hardness < 0) {
            return 0.0F;
        }
        int divisor = player.canHarvest(disguise) ? 30 : 100;
        return player.getBlockBreakingSpeed(disguise) / hardness / divisor;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        glint(world, pos, state, random, SPARKS_PER_TICK_AT_FULL_LIGHT);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void onBeamLit(World world, BlockPos pos, BlockState state, Random random) {
        glint(world, pos, state, random, SPARKS_PER_TICK_AT_FULL_LIGHT * GLEAM_SHARE * DISPLAY_TICKS_PER_SECOND / 20);
    }

    @Environment(EnvType.CLIENT)
    private static void glint(World world, BlockPos pos, BlockState state, Random random, float atFullLight) {
        if (!world.isClient) {
            return;
        }
        java.util.Optional<net.sevenstars.middleearth.resources.datas.gems.GemType> gemType =
            GemTypeRegistry.get(world.getRegistryManager(), state.get(GEM).key());
        for (Direction face : Direction.values()) {
            BlockPos open = pos.offset(face);
            if (world.getBlockState(open).isOpaqueFullCube()) {
                continue;
            }
            float light = Math.min(world.getLightLevel(open), FULL_LIGHT) / FULL_LIGHT;
            float expected = atFullLight * light;
            int sparks = (int) expected + (random.nextFloat() < expected - (int) expected ? 1 : 0);
            float brightness = DIMMEST + (1 - DIMMEST) * light;
            for (int i = 0; i < sparks; i++) {
                int rgb = gemType.map(type -> type.sparkRgb(random)).orElse(0xFFFFFF);
                int dimmed = ((int) (((rgb >> 16) & 0xFF) * brightness) << 16) | ((int) (((rgb >> 8) & 0xFF) * brightness) << 8)
                    | (int) ((rgb & 0xFF) * brightness);
                Direction.Axis axis = face.getAxis();
                double x = axis == Direction.Axis.X ? 0.5 + 0.5625 * face.getOffsetX() : random.nextFloat();
                double y = axis == Direction.Axis.Y ? 0.5 + 0.5625 * face.getOffsetY() : random.nextFloat();
                double z = axis == Direction.Axis.Z ? 0.5 + 0.5625 * face.getOffsetZ() : random.nextFloat();
                net.sevenstars.middleearth.client.particle.GemSparks.spark(pos.getX() + x, pos.getY() + y, pos.getZ() + z, dimmed);
            }
        }
    }
}
