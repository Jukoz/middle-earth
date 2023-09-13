package net.jesteur.me.mixin;

import net.jesteur.me.MiddleEarth;
import net.jesteur.me.item.ModRessourceItems;
import net.jesteur.me.utils.ParticleManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;


@Mixin(Block.class)
public abstract class BlockEntityLandedMixin {
	private static final float RANDOM_PARTICLE_RANGE = 0.25f;
	private static final float PARTICLE_VELOCITY = 0.25f;
	private static final int PARTICLE_QUANTITY = 15;
	private static final float ITEM_SPAWN_Y_OFFSET = 0.75f;
	private static final float ITEM_CRAFT_RANGE = 2f;

	/**
	 *
	 * **/
	@Inject(method = "onLandedUpon", at = @At(value = "HEAD"))
	private void damage(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo ci) {

		if(entity.getType() == EntityType.ITEM) {
			ItemEntity entityItem = (ItemEntity) entity;
			if(entityItem.getStack().getItem() == ModRessourceItems.EMPTY_PHIAL) {
				if(world.isNight()) {
					Box box = entityItem.getBoundingBox().expand(ITEM_CRAFT_RANGE);
					Position position = entityItem.getPos();
					List<ItemEntity> entities = world.getEntitiesByType(EntityType.ITEM, box, Entity::isTouchingWater);

					for (ItemEntity itm : entities) {
						if (itm.getStack().getItem() == Items.GLOWSTONE_DUST || itm.getStack().getItem() == Items.GLOW_INK_SAC) {

							world.playSound(position.getX(), position.getY(), position.getZ(), SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 1, 1.2f, true);

							ParticleManager.createParticles(world, entityItem.getPos(), ParticleTypes.END_ROD);
							removeOneItemFromStackEntity(itm);
							removeOneItemFromStackEntity(entityItem);

							if(!world.isClient()) {
								ServerWorld serverWorld = (ServerWorld) entity.getWorld();
								if (serverWorld != null) {
									ItemEntity newItem = new ItemEntity(EntityType.ITEM, world);
									newItem.setStack(new ItemStack(ModRessourceItems.STARLIGHT_PHIAL, 1));
									newItem.setPos(entity.getX(), entity.getY() + ITEM_SPAWN_Y_OFFSET, entity.getZ());
									serverWorld.spawnEntityAndPassengers(newItem);
									newItem.refreshPositionAndAngles(entity.getX(), entity.getY() + ITEM_SPAWN_Y_OFFSET, entity.getZ(), 0, 0);
								}
							}
							break;
						}
					}
				}
			}
		}
	}

	private void removeOneItemFromStackEntity(ItemEntity item) {
		int count = item.getStack().getCount();
		if(count == 1) item.kill();
		else {
			item.setStack(item.getStack().copyWithCount(count - 1));
		}
	}
}
