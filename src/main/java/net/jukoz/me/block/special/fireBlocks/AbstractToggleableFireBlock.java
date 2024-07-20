package net.jukoz.me.block.special.fireBlocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractToggleableFireBlock extends BlockWithEntity {

    public static final BooleanProperty LIT = Properties.LIT;

    public AbstractToggleableFireBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LIT, false));
    }

    protected abstract MapCodec<? extends AbstractToggleableFireBlock> getCodec();

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = player.getActiveHand();
        if (!world.isClient && player.getAbilities().allowModifyWorld) {
            if(player.isCreative()){
                world.setBlockState(pos, state.cycle(LIT));
            } else {
                ItemStack itemStack = player.getStackInHand(hand);
                if (state.get(LIT) && itemStack.isIn(ItemTags.SHOVELS)) {
                    extinguish(null, state, world, pos);
                } else if (!state.get(LIT) && itemStack.isOf(Items.FLINT_AND_STEEL) || itemStack.isOf(Items.TORCH)) {
                    setLit(world, state, pos, true);
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    protected static void setLit(WorldAccess world, BlockState state, BlockPos pos, boolean lit) {
        world.setBlockState(pos, state.with(LIT, lit), 2 | 3);
        if(lit){
            world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.5F, 1.0F);
        }
    }

    protected static void extinguish(@Nullable PlayerEntity player, BlockState state, WorldAccess world, BlockPos pos) {
        setLit(world, state, pos, false);

        world.playSound(null, pos, SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 1.5F, 1.0F);
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{LIT});
    }

    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
