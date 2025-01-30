package net.sevenstars.middleearth.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.TrollEntity;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntity;
import net.sevenstars.middleearth.entity.goals.CustomBowAttackGoal;
import net.sevenstars.middleearth.entity.goals.NpcTargetPlayerGoal;
import net.sevenstars.middleearth.entity.spider.MirkwoodSpiderEntity;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomLongbowWeaponItem;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.DispositionUtil;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.NpcUtil;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcGearData;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Predicate;

public class NpcEntity extends PathAwareEntity implements RangedAttackMob {
    protected Disposition disposition;
    private Identifier raceId;
    private Item bow;
    private final CustomBowAttackGoal<NpcEntity> bowAttackGoal = new CustomBowAttackGoal<NpcEntity>(this, 1.0, 16, 30.0f);
    private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.5, false);
    public NpcRank rank;
    protected NpcEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.updateAttackType();
        for (int i = 0; i < 4; i++) {
            Arrays.fill(this.armorDropChances, 0.0f);
        }
    }

    protected Identifier getFactionId(){
        return null;
    }
    protected Identifier getRaceId(){
        return null;
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData);
        this.updateAttackType();
        return entityData;
    }

    @Override
    protected void initGoals() {
        Identifier factionId = getFactionId();
        if(factionId == null)
            disposition = Disposition.NEUTRAL;
        else {
            try {
                disposition = FactionLookup.getFactionById(getWorld(), factionId).getDisposition();
            } catch (FactionIdentifierException e) {
                disposition = Disposition.NEUTRAL; // Attacks everyone, no judgement made
            }
        }

        this.targetSelector.add(0, new RevengeGoal(this, this.getClass()));
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));


        this.targetSelector.add(2, new NpcTargetPlayerGoal(this));
    }

    public void updateAttackType() {
        if (this.getWorld() != null && !this.getWorld().isClient) {
            this.goalSelector.remove(this.meleeAttackGoal);
            this.goalSelector.remove(this.bowAttackGoal);

            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (itemStack != null && itemStack.getItem() instanceof BowItem) {
                this.bow = itemStack.getItem();
            } else {
                this.bow = null;
            }

            if (this.bow != null) {
                this.bowAttackGoal.setAttackInterval(16);
                this.goalSelector.add(2, this.bowAttackGoal);
            } else {
                this.goalSelector.add(2, this.meleeAttackGoal);
            }
        }
    }

    public Disposition getDisposition(){
        return disposition;
    }
    public Item getBow(){
        return this.bow;
    }

    public void setBow(Item bow){
        this.bow = bow;
    }

    public NpcRank getRank() {
        return rank;
    }

    public void setRank(NpcRank rank) {
        this.rank = rank;
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        if(target == null || getWorld().getDifficulty() == Difficulty.PEACEFUL){
            return false;
        }

        if(target instanceof PlayerEntity player) {
            if(player.isCreative()){
                return false;
            }
            if(disposition != null){
                PlayerData data = StateSaverAndLoader.getPlayerState(player);
                if(data != null){
                    Disposition playerDisposition = data.getCurrentDisposition();
                    if(playerDisposition == disposition){
                        return false;
                    }
                    if(playerDisposition == null)
                        return true;
                    return true;
                }
            }
        }
        return super.canTarget(target);
    }

    public static enum State {
        NEUTRAL,
        ATTACKING,
    }

    public State getState() {
        if (this.isAttacking()) {
            return State.ATTACKING;
        }
        return State.NEUTRAL;
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        tryToEquipGears(this.getRank(), this.getRaceId(), getFactionId());
    }

    @Override
    public boolean isPersistent() {
        return super.isPersistent();
    }

    public void equipStack(EquipmentSlot slot, ItemStack stack) {
        super.equipStack(slot, stack);
        if (!this.getWorld().isClient) {
            this.updateAttackType();
        }
    }

    @Override
    public boolean canUseRangedWeapon(RangedWeaponItem weapon) {
        return weapon == getBow();
    }

    public ItemStack getProjectileType(ItemStack stack) {
        if (stack.getItem() instanceof BowItem) {
            Predicate<ItemStack> predicate = ((RangedWeaponItem)stack.getItem()).getHeldProjectiles();
            ItemStack itemStack = RangedWeaponItem.getHeldProjectile(this, predicate);
            return itemStack.isEmpty() ? new ItemStack(Items.ARROW) : itemStack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        ItemStack itemStack = this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, getBow()));
        ItemStack itemStack2 = this.getProjectileType(itemStack);
        PersistentProjectileEntity persistentProjectileEntity = this.createArrowProjectile(itemStack2, pullProgress, itemStack);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333) - persistentProjectileEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);

        boolean isLongbow =(this.bow instanceof CustomLongbowWeaponItem);
        float power = (isLongbow) ? 2.5F : 1.6f;
        float uncertaintyBase = (isLongbow) ? 10 : 14;
        float yVelocityModifier = (isLongbow) ?  0.10000000298023224f : 0.20000000298023224f;

        persistentProjectileEntity.setVelocity(d, e + g * yVelocityModifier, f, power, (uncertaintyBase - this.getWorld().getDifficulty().getId() * 4));

        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.getWorld().spawnEntity(persistentProjectileEntity);
    }

    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier, @Nullable ItemStack shotFrom) {
        return ProjectileUtil.createArrowProjectile(this, arrow, damageModifier, shotFrom);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.updateAttackType();
    }
    /*
     @Override
    public int getXpToDrop() {
        int exp = 0;
        switch (this.getRank()){
            case NpcRank.MILITIA -> exp = 10;
            case NpcRank.SOLDIER -> exp = 15;
            case NpcRank.KNIGHT -> exp = 20;
            case NpcRank.VETERAN -> exp = 25;
            case NpcRank.LEADER -> exp = 30;
        }
        return exp;
    }


    @Override
    protected void applyDamage(DamageSource source, float amount) {
        super.applyDamage(source, amount);
    }


    @Override
    protected void dropXp(@Nullable Entity attacker) {
        if(attacker instanceof PlayerEntity player && canDrop(player, null)){
            super.dropXp(attacker);
        }
    }

    @Override
    protected void dropLoot(DamageSource damageSource, boolean causedByPlayer) {
        if(damageSource.getAttacker() instanceof PlayerEntity player && canDrop(player, damageSource)){
            super.dropLoot(damageSource, causedByPlayer);
        }
    }
     */

    private boolean canDrop(PlayerEntity player, DamageSource damageSource) {
        /*
        // If we want more control over what drop and what doesn't allow drops
        if(!causedByPlayer){
            String damageSourceValue = damageSource.getTypeRegistryEntry().getIdAsString();
            if(Objects.equals(damageSourceValue, DamageTypes.IN_WALL.getValue().toString()))
                return false;
        }
        */

        if(player != null){
            Disposition playerDisposition = DispositionUtil.getDisposition(player);
            return playerDisposition == null || playerDisposition != getDisposition();
        }

        return true;
    }

    @Override
    protected void dropEquipment(ServerWorld world, DamageSource source, boolean causedByPlayer) {
        return;
    }

    protected void tryToEquipGears(NpcRank npcRank, Identifier raceId, Identifier factionId) {
        if(factionId == null)
            return;
        try{
            Faction faction = FactionLookup.getFactionById(getWorld(), factionId);
            Race race = RaceLookup.getRace(getWorld(), raceId);
            NpcData data = faction.getRandomGear(getWorld(), npcRank, race);
            if(data == null)
                return;
            NpcGearData gearData = data.getGear();
            NpcUtil.equipAll(this, gearData);
        } catch (FactionIdentifierException e) {
            MiddleEarth.LOGGER.logError("NpcEntity::Couldn't find faction registry with [%s] for rank [%s]".formatted(factionId, npcRank.toString()));
            throw new RuntimeException(e);
        }
    }

    public int initGoodTargetSelector(int i){
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, TrollEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, WargEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MirkwoodSpiderEntity.class, true));
        /*
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, IsengardUrukHaiEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorBlackUrukEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MistyHobgoblinEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, IsengardOrcEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorOrcEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MistyGoblinEntity.class, true));

        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, WildGoblinEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, BanditHumanEntity.class, true));
        */
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, PlayerEntity.class, true));

        return i;
    }

    public int initEvilTargetSelector(int i){
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MirkwoodSpiderEntity.class, true));
        /*
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, GondorHumanEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, RohanHumanEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, LongbeardDwarfEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, ShireHobbitEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, DaleHumanEntity.class, true));

        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, WildGoblinEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, BanditHumanEntity.class, true));
        */
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, PlayerEntity.class, true));

        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, BroadhoofGoatEntity.class, true));
        return i;
    }

    public int initNeutralTargetSelector(int i){
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MirkwoodSpiderEntity.class, true));
        /*
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, IsengardUrukHaiEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorBlackUrukEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MistyHobgoblinEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, IsengardOrcEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorOrcEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MistyGoblinEntity.class, true));

        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, GondorHumanEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, RohanHumanEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, LongbeardDwarfEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, ShireHobbitEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, DaleHumanEntity.class, true));
*/
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, PlayerEntity.class, true));

        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, TrollEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, WargEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, HorseEntity.class, true));

        return i;
    }
}
