package io.github.Bubblie.Merplings.Entities.BaoEntity;

import net.minecraft.entity.ai.goal.Goal;

public class BuildHouseGoal extends Goal {

    private final AnnoyingEntity merpling;

    public BuildHouseGoal(AnnoyingEntity merpling)
    {
        this.merpling = merpling;
    }



    @Override
    public boolean canStart() {
        return false;
    }
}
