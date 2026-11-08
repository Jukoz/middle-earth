package net.sevenstars.middleearth.block.special.gems;


import net.sevenstars.middleearth.registries.content.gems.GemTypeRegistry;
import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/**
 * A gem hidden in the rock. It looks exactly like the stone named by {@link #STONE} and mines at that
 * stone's speed; the only tell is a glint of dust in the gem's colour on its open faces.
 * Drops its raw gem (loot table, Fortune applies; Silk Touch also gives the raw gem) and 3-7 XP.
 * Needs an iron pickaxe or better (block tags).
 */
public class GemOreBlock extends ExperienceDroppingBlock {
    public static final MapCodec<GemOreBlock> CODEC = createCodec(GemOreBlock::new);
    public static final EnumProperty<GemKind> GEM = EnumProperty.of("gem", GemKind.class);
    public static final EnumProperty<GemOreStone> STONE = EnumProperty.of("stone", GemOreStone.class);

    /** Glint tuning: dust size (redstone ore uses 1.0), coloured specks per open face per call, white spark odds. */
    private static final float GLINT_SIZE = 1.2F;
    private static final int SPECKS_PER_FACE = 2;
    private static final int SPARK_ONE_IN = 2;

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

    /** Mine at the disguise stone's speed so the block doesn't give itself away. */
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
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        // The client already calls this only every few seconds per nearby block, so every call glints.
        int rgb = GemTypeRegistry.get(world.getRegistryManager(), state.get(GEM).key())
            .map(gemType -> gemType.color().getRgb())
            .orElse(0xFFFFFF);
        DustParticleEffect glint = new DustParticleEffect(rgb, GLINT_SIZE);
        // Every open face gets a couple of coloured specks, and now and then a white spark, just off the
        // surface (same placement as redstone ore).
        for (Direction face : Direction.values()) {
            if (world.getBlockState(pos.offset(face)).isOpaqueFullCube()) {
                continue;
            }
            for (int i = 0; i < SPECKS_PER_FACE; i++) {
                spawnOnFace(world, pos, face, random, glint);
            }
            if (random.nextInt(SPARK_ONE_IN) == 0) {
                spawnOnFace(world, pos, face, random, ParticleTypes.ELECTRIC_SPARK);
            }
        }
    }

    private static void spawnOnFace(World world, BlockPos pos, Direction face, Random random, ParticleEffect particle) {
        Direction.Axis axis = face.getAxis();
        double x = axis == Direction.Axis.X ? 0.5 + 0.5625 * face.getOffsetX() : random.nextFloat();
        double y = axis == Direction.Axis.Y ? 0.5 + 0.5625 * face.getOffsetY() : random.nextFloat();
        double z = axis == Direction.Axis.Z ? 0.5 + 0.5625 * face.getOffsetZ() : random.nextFloat();
        world.addParticleClient(particle, pos.getX() + x, pos.getY() + y, pos.getZ() + z, 0.0, 0.0, 0.0);
    }
}
