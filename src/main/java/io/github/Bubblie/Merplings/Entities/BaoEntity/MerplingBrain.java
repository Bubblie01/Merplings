package io.github.Bubblie.Merplings.Entities.BaoEntity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.util.math.IntRange;

import java.util.List;

public class MerplingBrain
{
    public static Brain<?> create(Brain<AnnoyingEntity> merp)
    {

        addCoreTasks(merp);
        addIdleActivities(merp);
        addFightTasks(merp);
        merp.setCoreActivities(ImmutableSet.of(Activity.CORE));
        merp.setDefaultActivity(Activity.IDLE);
        merp.resetPossibleActivities();
        return merp;
    }


    public static void addIdleActivities(Brain<AnnoyingEntity> merp)
    {

        merp.setTaskList(Activity.IDLE, 10, ImmutableList.of(new TimeLimitedTask(new FollowMobTask(8.0F), IntRange.between(30,60)), makeRandomWanderTask(), new GoTowardsLookTarget(0.5F, 2)));

    }
    public static void addCoreTasks(Brain<AnnoyingEntity> merp) {
        merp.setTaskList(Activity.CORE, 0, ImmutableList.of(new LookAroundTask(45, 90), new WanderAroundTask(), new StrollTask(0.7F)));
    }

    public static void addFightTasks(Brain<AnnoyingEntity> merp)
    {
        merp.setTaskList(Activity.FIGHT, 10, ImmutableList.of(new MeleeAttackTask(40),new RangedApproachTask(1.0F)), MemoryModuleType.ATTACK_TARGET);
    }


    private static RandomTask<AnnoyingEntity> makeRandomWanderTask() {
        /*
        return new RandomTask(ImmutableList.of(Pair.of(new StrollTask(0.6F), 2),
                Pair.of(FindEntityTask.create(AnnoyingEntity.BAO, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), 2),
                Pair.of(new ConditionalTask(MerplingBrain::canWander, new GoTowardsLookTarget(0.6F, 3)), 2),
                Pair.of(new WaitTask(30, 60), 1)));
         */
        return new RandomTask(ImmutableList.of(Pair.of(new StrollTask(0.6F), 2), Pair.of(FindEntityTask.create(AnnoyingEntity.BAO, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), 2),Pair.of(new WaitTask(30, 60), 1)));
    }



    /*
    public static boolean canWander(Object o) {
        return true;
    }

     */



}
