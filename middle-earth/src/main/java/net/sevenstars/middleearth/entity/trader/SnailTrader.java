package net.sevenstars.middleearth.entity.trader;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SpiderNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerData;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.*;
import net.sevenstars.middleearth.item.ModResourceItems;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntityVariant;

import org.jetbrains.annotations.Nullable;

public class SnailTrader extends MerchantEntity {
    public final AnimationState crawlingAnimationState = new AnimationState();
    public static final int CLIMBING_TIME_TRANSITION = 12;
    private static final TrackedData<Byte> SNAIL_FLAGS;
    private int climbingTicks = 0;
    int moreCropsTicks;

    private static final int[] LEVEL_BASE_EXPERIENCE = new int[]{0, 10, 70, 150, 250};
    private PlayerEntity lastCustomer;
    protected int experience;
    protected int level;
    protected int levelUpTimer;
    protected boolean levelingUp;

    public SnailTrader(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
        level = 1;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SnailEatCropGoal(this));
        this.goalSelector.add(1, new WanderAroundFarGoal(this, 0.8));
    }

    public static DefaultAttributeContainer.Builder createSnailAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 2)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.05f)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE, 1)
                .add(EntityAttributes.ARMOR, 0.5f);
    }

    public SnailEntityVariant getVariant() {
        return SnailEntityVariant.byId(this.getId());
    }
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.SNAIL_TRADER.create(world, SpawnReason.BREEDING);
    }

    static class SnailEatCropGoal
            extends MoveToTargetPosGoal {
        private final SnailTrader snail;
        private boolean wantsCrops;
        private boolean hasTarget;

        public SnailEatCropGoal(SnailTrader snail) {
            super(snail, 1.0f, 16);
            this.snail = snail;
        }

        @Override
        public boolean canStart() {
            if (this.cooldown <= 0) {
                if(this.snail.getWorld() instanceof ServerWorld serverWorld)
                    if (!serverWorld.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                        return false;
                    }
                this.hasTarget = false;
                this.wantsCrops = this.snail.wantsCrops();
            }
            return super.canStart();
        }

        @Override
        public boolean shouldContinue() {
            return this.hasTarget && super.shouldContinue();
        }

        @Override
        public void tick() {
            super.tick();
            this.snail.getLookControl().lookAt((double)this.targetPos.getX() + 0.5, this.targetPos.getY() + 1, (double)this.targetPos.getZ() + 0.5, 10.0f, this.snail.getMaxLookPitchChange());
            if (this.hasReached()) {
                World world = this.snail.getWorld();
                BlockPos blockPos = this.targetPos.up();
                BlockState blockState = world.getBlockState(blockPos);
                Block block = blockState.getBlock();
                if (this.hasTarget && block.getDefaultState().isIn(BlockTags.CROPS)) {
                    world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
                    world.breakBlock(blockPos, true, this.snail);
                    this.snail.onEatingGrass();
                    this.snail.moreCropsTicks = 1200;
                }
                this.hasTarget = false;
                this.cooldown = 10;
            }
        }

        @Override
        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            BlockState blockState = world.getBlockState(pos);
            if (blockState.isOf(Blocks.FARMLAND) && this.wantsCrops && !this.hasTarget && (blockState = world.getBlockState(pos.up())).isIn(BlockTags.CROPS)) {
                this.hasTarget = true;
                return true;
            }

            return false;
        }
    }

    boolean wantsCrops() {
        return this.moreCropsTicks <= 0;
    }

    @Override
    protected void mobTick(ServerWorld world) {
        if (this.moreCropsTicks > 0) {
            this.moreCropsTicks -= this.random.nextInt(3);
            if (this.moreCropsTicks < 0) {
                this.moreCropsTicks = 0;
            }
        }
        super.mobTick(world);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SLIME_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SLIME_HURT;
    }

    @Override
    public void jump() {
        // Snail is not able to jump
    }

    protected EntityNavigation createNavigation(World world) {
        return new SpiderNavigation(this, world);
    }

    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SNAIL_FLAGS, (byte)0);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if(this.getWorld().isClient || !this.isAlive() || this.hasCustomer()) {
            return super.interactMob(player, hand);
        }
        if(this.getOffers().isEmpty()) {
            return super.interactMob(player, hand);
        }

        sendOffersToCustomer();
        this.beginTradeWith(player);

        return ActionResult.SUCCESS;
    }

    @Override
    protected void afterUsing(TradeOffer offer) {
        int i = 3 + this.random.nextInt(4);
        this.experience += offer.getMerchantExperience();
        this.lastCustomer = this.getCustomer();
        if (canLevelUp(experience) && this.level <= 4) {
            level++;
            this.levelUpTimer = 40;
            this.levelingUp = true;
            fillRecipes();
            i += 5;
        }
        sendOffersToCustomer();

        if (offer.shouldRewardPlayerExperience()) {
            this.getWorld().spawnEntity(new ExperienceOrbEntity(this.getWorld(), this.getX(), this.getY() + 0.5, this.getZ(), i));
        }
    }

    @Override
    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    private void sendOffersToCustomer() {
        TradeOfferList tradeOfferList = this.getOffers();
        PlayerEntity playerEntity = this.getCustomer();
        if (playerEntity != null && !tradeOfferList.isEmpty()) {
            playerEntity.sendTradeOffers(
                    playerEntity.currentScreenHandler.syncId,
                    tradeOfferList,
                    this.getLevel(),
                    this.getExperience(),
                    this.isLeveledMerchant(),
                    this.canRefreshTrades()
            );
        }
    }

    protected boolean canLevelUp(int experience) {
        int neededExp = LEVEL_BASE_EXPERIENCE[this.level];
        if(experience >= neededExp) {
            return true;
        }
        return false;
    }

    @Override
    protected void fillRecipes() {
        this.offers = new TradeOfferList();
        offers.add(new TradeOffer(new TradedItem(ModResourceItems.COPPER_COIN, 1),
                Items.WHEAT_SEEDS.getDefaultStack().copyWithCount(2), 16, 2, 1.25f));
        offers.add(new TradeOffer(new TradedItem(ModResourceItems.COPPER_COIN, 2),
                Items.WHEAT.getDefaultStack().copyWithCount(3), 16, 5, 1.15f));

        if(level >= 2) {
            offers.add(new TradeOffer(new TradedItem(ModResourceItems.COPPER_COIN, 4),
                Items.POTATO.getDefaultStack().copyWithCount(2), 16, 10, 1.5f));
        }
        if(level >= 3) {
            offers.add(new TradeOffer(new TradedItem(ModResourceItems.COPPER_COIN, 9),
                    ModResourceItems.BELL_PEPPER_SEEDS.getDefaultStack().copyWithCount(2), 16, 20, 2f));
            offers.add(new TradeOffer(new TradedItem(ModResourceItems.SILVER_COIN, 1),
                    Items.MELON_SEEDS.getDefaultStack().copyWithCount(2), 16, 20, 2f));
        }
        if(level >= 4) {
            offers.add(new TradeOffer(new TradedItem(ModResourceItems.SILVER_COIN, 1),
                    ModResourceItems.SILVER_NUGGET.getDefaultStack().copyWithCount(4), 16, 25, 3f));

            offers.add(new TradeOffer(new TradedItem(ModResourceItems.SILVER_COIN, 3),
                    Items.SLIME_BALL.getDefaultStack().copyWithCount(2), 16, 40, 3f));
        }
        if(level >= 5) {
            offers.add(new TradeOffer(new TradedItem(ModResourceItems.GOLD_COIN, 1),
                    Items.BAMBOO.getDefaultStack().copyWithCount(5), 16, 50, 3f));
        }
    }

    private void beginTradeWith(PlayerEntity customer) {
        this.prepareOffersFor(customer);
        this.setCustomer(customer);
        this.sendOffers(customer, this.getDisplayName(), this.level);
    }

    private void prepareOffersFor(PlayerEntity player) {
        int i = 0; // this.getReputation(player);
        if (i != 0) {
            for (TradeOffer tradeOffer : this.getOffers()) {
                tradeOffer.increaseSpecialPrice(-MathHelper.floor((float)i * tradeOffer.getPriceMultiplier()));
            }
        }

        if (player.hasStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE)) {
            StatusEffectInstance statusEffectInstance = player.getStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE);
            int j = statusEffectInstance.getAmplifier();

            for (TradeOffer tradeOffer2 : this.getOffers()) {
                double d = 0.3 + 0.0625 * (double)j;
                int k = (int)Math.floor(d * (double)tradeOffer2.getOriginalFirstBuyItem().getCount());
                tradeOffer2.increaseSpecialPrice(-Math.max(k, 1));
            }
        }
    }

    @Override
    public TradeOfferList getOffers() {
        return super.getOffers();
    }

    @Override
    public void trade(TradeOffer offer) {
        offer.use();
        this.ambientSoundChance = -this.getMinAmbientSoundDelay();
        this.afterUsing(offer);
        //if (this.customer instanceof ServerPlayerEntity) { // Advancement
        //    Criteria.VILLAGER_TRADE.trigger((ServerPlayerEntity)this.customer, this, offer.getSellItem());
        //}

    }

    //public void createTrades() {
    //    this.offers = new TradeOfferList();
    //    offers.add(new TradeOffer(new TradedItem(ModResourceItems.COPPER_COIN, 1),
    //            Items.SLIME_BALL.getDefaultStack().copyWithCount(2), 16, 0, 1.25f));
    //    offers.add(new TradeOffer(new TradedItem(ModResourceItems.COPPER_COIN, 2),
    //            Items.WHEAT.getDefaultStack().copyWithCount(3), 16, 0, 1.15f));
    //}


    @Override
    protected SoundEvent getTradingSound(boolean sold) {
        return sold ? SoundEvents.ENTITY_SLIME_JUMP_SMALL : SoundEvents.ENTITY_SLIME_SQUISH_SMALL;
    }

    @Override
    public void playCelebrateSound() {
        this.playSound(SoundEvents.ENTITY_SLIME_JUMP);
    }

    @Override
    public void onEatingGrass() {
        super.onEatingGrass();
        if (this.isBaby()) {
            this.growUp(60);
        }
        if(random.nextDouble() <= 0.15D) {
            World world = this.getWorld();
            SnailTrader snailSpawn = ((EntityType<SnailTrader>) EntityType.get(MiddleEarth.MOD_ID + ":snail").get()).create(world, SpawnReason.BREEDING);
            snailSpawn.updatePosition(this.getX(), this.getY(), this.getZ());
            world.spawnEntity(snailSpawn);
        }
    }

    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            this.setClimbingWall(this.horizontalCollision);
        }
        if(isClimbing()) {
            this.setVelocity(0, 0.01, 0);
        }

        this.crawlingAnimationState.startIfNotRunning(this.age);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if(isClimbingWall()) {

            this.climbingTicks = Math.min(CLIMBING_TIME_TRANSITION, this.climbingTicks + 1);
        } else {
            this.climbingTicks = Math.max(0, this.climbingTicks - 1);
        }
    }

    public boolean isClimbing() {
        return this.isClimbingWall();
    }

    public boolean isClimbingWall() {
        return (this.dataTracker.get(SNAIL_FLAGS) & 1) != 0;
    }

    public boolean isCollidingWall() {
        return this.horizontalCollision;
    }

    public void setClimbingWall(boolean climbing) {
        byte b = (Byte)this.dataTracker.get(SNAIL_FLAGS);
        if (climbing) {
            b = (byte)(b | 1);
        } else {
            b &= -2;
        }

        this.dataTracker.set(SNAIL_FLAGS, b);
    }

    public int getClimbingTicks() {
        return this.climbingTicks;
    }

    static {
        SNAIL_FLAGS = DataTracker.registerData(SnailTrader.class, TrackedDataHandlerRegistry.BYTE);
    }
}
