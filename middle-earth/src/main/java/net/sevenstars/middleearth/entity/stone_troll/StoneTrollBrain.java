package net.sevenstars.middleearth.entity.stone_troll;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.api.entity.ai.brain.SchedulesAPI;
import net.sevenstars.api.entity.ai.brain.task.*;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.ai.brain.task.CaveTrollDigForFoodTask;
import net.sevenstars.middleearth.entity.ai.brain.task.CaveTrollEatFoodTask;
import net.sevenstars.middleearth.entity.ai.brain.task.CaveTrollSleepTask;
import net.sevenstars.middleearth.loot.LootTablesME;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

// TODO WALK TO CAMPFIRE AT NIGHT
// TODO SIT AROUND CAMPFIRE AT NIGHT
// TODO BUILD CAMPFIRE IF NONE AROUND
// TODO FIND SHADE DURING DAY
// TODO MOVE BARTERING TO UNIQUE CLASS
// TODO IDEA: Split schedule during the night into hunting and idling.
// TODO IDEA: Troll will want food during the hunting phase and treasures during the idling phase 
public class StoneTrollBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super StoneTrollEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    protected static Brain<?> create(StoneTrollEntity troll, Dynamic<?> dynamic) {
        Brain.Profile<StoneTrollEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
        Brain<StoneTrollEntity> brain = profile.deserialize(dynamic);

        addCoreActivities(brain);
        addIdleActivities(brain, troll);
        addRestActivities(brain, troll);
        addFightActivities(brain, troll);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.FIGHT);

        brain.setSchedule(SchedulesAPI.NOCTURNAL);

        brain.refreshActivities(troll.getWorld().getTimeOfDay(), troll.getWorld().getTime());
        return brain;
    }

    private static void addCoreActivities(Brain<StoneTrollEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new MoveToTargetTask(),
                new UpdateLookControlTask(45, 90)
        ));
    }

    // Plays at night
    private static void addIdleActivities(Brain<StoneTrollEntity> brain, StoneTrollEntity troll) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                    Pair.of(0, LookAtMobTask.create(5)),
                    Pair.of(1, RememberBlockLocationTask.create(Blocks.CAMPFIRE, MemoryModuleType.MEETING_POINT)),
                    Pair.of(2, new RandomTask<>(
                            ImmutableMap.of(
                                    MemoryModuleType.MEETING_POINT, MemoryModuleState.VALUE_ABSENT),
                            ImmutableList.of(
                                    Pair.of(StrollTask.create(1.0F), 5),
                                    Pair.of(PlaceBlockNearbyTask.create(() -> true, MemoryModuleType.MEETING_POINT, Blocks.CAMPFIRE), 1)
                    ))),
                    Pair.of(3, ForgetMemorizedPosTask.create(() -> shouldForgetMeetingPoint(troll), MemoryModuleType.MEETING_POINT)),
                    Pair.of(4, MoveTowardsPosMemoryTask.create(MemoryModuleType.MEETING_POINT, 1.0f, 5, 30, 300)),
                    Pair.of(5, new SitDownTask(MemoryModuleType.MEETING_POINT, 5)),
                    Pair.of(99, ScheduleActivityTask.create())
        ));
    }

    // Plays during the day
    private static void addRestActivities(Brain<StoneTrollEntity> brain, StoneTrollEntity troll) {
        brain.setTaskList(Activity.REST, ImmutableList.of(
                // If in daylight, start panicking
                // Go home
                //Pair.of(1, ForgetMemorizedPosTask.create(() -> shouldForgetHome(troll), MemoryModuleType.HOME)),
                // If no home present, find new home
                Pair.of(2, new SleepOnGroundTask()),
                Pair.of(99, ScheduleActivityTask.create())
        ));
    }

    // Do not fight in daylight
    private static void addFightActivities(Brain<StoneTrollEntity> brain, StoneTrollEntity troll) {
        brain.setTaskList(Activity.FIGHT, ImmutableList.of(
                        Pair.of(0, ForgetAttackTargetTask.create()),
                        Pair.of(2, new RandomTask<>(ImmutableList.of(
                                Pair.of(MeleeAttackTask.create(30), 4),
                                Pair.of(RangedApproachTask.create(2.5F), 3)
                        )))
                ),
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT)
                )
        );
    }

    public static void updateActivities(StoneTrollEntity troll) {
        Optional<LivingEntity> optional = troll.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);

        if(optional != null && optional.isPresent()) {
            troll.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT));
        }
        else {
            troll.getBrain().resetPossibleActivities(ImmutableList.of());
        }

        troll.getBrain().refreshActivities(troll.getWorld().getTimeOfDay(), troll.getWorld().getTime());
    }

    public static boolean shouldForgetHome(StoneTrollEntity troll) {
        Optional<GlobalPos> optional = troll.getBrain().getOptionalRegisteredMemory(MemoryModuleType.HOME);

        if(optional != null && optional.isPresent()) {
            return troll.getWorld().isSkyVisible(optional.get().pos());
        }

        return false;
    }

    public static boolean shouldForgetMeetingPoint(StoneTrollEntity troll) {
        Optional<GlobalPos> optional = troll.getBrain().getOptionalRegisteredMemory(MemoryModuleType.MEETING_POINT);

        if(optional != null && optional.isPresent()) {
            return !troll.getWorld().getBlockState(optional.get().pos()).isOf(Blocks.CAMPFIRE);
        }

        return false;
    }

    //region Bartering
    // Get list of possible items from loot table
    private static List<ItemStack> getBarteredItem(StoneTrollEntity troll) {
        LootTable lootTable = troll.getWorld().getServer().getReloadableRegistries().getLootTable(LootTablesME.STONE_TROLL_BARTERING_GAMEPLAY);
        List<ItemStack> list = lootTable.generateLoot(
                new LootWorldContext.Builder((ServerWorld)troll.getWorld()).add(LootContextParameters.THIS_ENTITY, troll).build(LootContextTypes.BARTER)
        );
        return list;
    }

    // Checks if item is suitable for bartering
    private static boolean acceptsForBarter(ItemStack stack) {
        return stack.isOf(Items.GOLD_INGOT);
    }

    private static void doBarter(StoneTrollEntity troll, List<ItemStack> items) {
        Optional<PlayerEntity> optional = troll.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
        if (optional.isPresent()) {
            dropBarteredItem(troll, (PlayerEntity)optional.get(), items);
        } else {
            dropBarteredItem(troll, items);
        }
    }

    // Take item in barter
    private static void barterItem(StoneTrollEntity troll, ItemStack stack) {
        ItemStack itemStack = troll.addItem(stack);
        dropBarteredItem(troll, Collections.singletonList(itemStack));
    }

    // Drop item on ground if no player nearby
    private static void dropBarteredItem(StoneTrollEntity troll, List<ItemStack> items) {
        drop(troll, items, findGround(troll));
    }

    // Drop item on ground if player nearby
    private static void dropBarteredItem(StoneTrollEntity troll, PlayerEntity player, List<ItemStack> items) {
        drop(troll, items, player.getPos());
    }

    // Get nearby pos on ground
    private static Vec3d findGround(StoneTrollEntity troll) {
        Vec3d vec3d = FuzzyTargeting.find(troll, 4, 2);
        return vec3d == null ? troll.getPos() : vec3d;
    }

    // Drop item
    private static void drop(StoneTrollEntity troll, List<ItemStack> items, Vec3d pos) {
        if (!items.isEmpty()) {
            troll.swingHand(Hand.OFF_HAND);

            for (ItemStack itemStack : items) {
                TargetUtil.give(troll, itemStack, pos.add(0.0, 1.0, 0.0));
            }
        }
    }

    protected static void consumeOffHandItem(ServerWorld world, StoneTrollEntity troll, boolean barter) {
        ItemStack itemStack = troll.getStackInHand(Hand.OFF_HAND); // Get held item
        troll.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);   // Clear held item
        boolean bl = acceptsForBarter(itemStack);   // Check if item in hand is valid barter item
        if (barter && bl) { // If valid, continue with barter
            doBarter(troll, getBarteredItem(troll));
        } else if (!bl) {   // Not a valid item, return item
            barterItem(troll, itemStack);
        }

    }



    //endregion

    static {
        SENSORS = ImmutableList.of(
                SensorType.HURT_BY,
                SensorType.NEAREST_PLAYERS,
                SensorType.NEAREST_LIVING_ENTITIES
        );
        MEMORY_MODULES = ImmutableList.of(
                MemoryModuleType.WALK_TARGET,
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                MemoryModuleType.PATH,
                MemoryModuleType.IS_IN_WATER,
                MemoryModuleType.HURT_BY,
                MemoryModuleType.HURT_BY_ENTITY,
                MemoryModuleType.VISIBLE_MOBS,
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleType.ATTACK_COOLING_DOWN,
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.NEAREST_ATTACKABLE,
                MemoryModuleType.NEAREST_PLAYERS,
                MemoryModuleType.HOME,
                MemoryModuleType.MEETING_POINT
        );
    }
}
