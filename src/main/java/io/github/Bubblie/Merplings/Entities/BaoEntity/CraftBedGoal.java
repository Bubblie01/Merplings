package io.github.Bubblie.Merplings.Entities.BaoEntity;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.GoToWalkTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.*;
import net.minecraft.recipe.book.RecipeBook;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import io.github.Bubblie.Merplings.Entities.BaoEntity.AnnoyingEntity;
import net.minecraft.world.WorldView;

import javax.swing.plaf.basic.BasicComboBoxUI;
import java.util.EnumSet;
import java.util.Set;

public class CraftBedGoal extends MoveToTargetPosGoal {
    public final AnnoyingEntity mob;
    private int PlankCounter;
    private int WoolCounter;
    public CraftBedGoal(AnnoyingEntity mob, double speed) {
        super(mob,speed,8,1);
        this.mob = mob;
    }


    @Override
    public boolean canStart()
    {

        PlankCounter = mob.getInventory().count(Items.OAK_PLANKS);
        WoolCounter = mob.getInventory().count(Items.WHITE_WOOL);
        /*
        if(mob.getInventory().containsAny(ImmutableSet.of(Items.OAK_PLANKS, Items.WHITE_WOOL)))
        {
            return true;
        }

         */


        if(PlankCounter>=3 && WoolCounter>=3 && !mob.getInventory().containsAny(ImmutableSet.of(Items.WHITE_BED)))
        {
            return super.canStart();
        }

        return false;
    }

    @Override
    public void start()
    {
            BlockPos blockPos = this.getTargetPos();
            this.startMovingToTarget();
            System.out.println(blockPos);
                    if(this.mob.getBlockPos().equals(blockPos)) {
                        this.mob.getInventory().removeItem(Items.OAK_PLANKS, 3);
                        this.mob.getInventory().removeItem(Items.WHITE_WOOL, 3);
                        this.mob.getInventory().addItem(Items.WHITE_BED, 1);
                    }




        }


    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return world.getBlockState(pos).isOf(Blocks.CRAFTING_TABLE);
    }

    @Override
    public boolean shouldResetPath() {
        return this.tryingTime % 40 == 0;
    }

    @Override
    protected void startMovingToTarget() {
        super.startMovingToTarget();
    }
}
