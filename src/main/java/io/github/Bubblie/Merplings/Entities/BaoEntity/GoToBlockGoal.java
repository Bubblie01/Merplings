package io.github.Bubblie.Merplings.Entities.BaoEntity;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class GoToBlockGoal extends Goal
{


    private final AnnoyingEntity mob;
    private final Block block;
    public GoToBlockGoal(Block block, AnnoyingEntity mob)
    {
        this.mob = mob;
        this.block = block;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK, Control.JUMP, Control.TARGET));

    }

    @Override
    public boolean canStart() {

        return false;
    }

    @Override
    public void start() {
        super.start();
    }

    private boolean isBlock(BlockPos pos)
    {
        return mob.world.canSetBlock(pos) && mob.world.getBlockState(pos).getBlock().is(block);
    }


}
