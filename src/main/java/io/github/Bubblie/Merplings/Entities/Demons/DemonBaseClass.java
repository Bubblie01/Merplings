package io.github.Bubblie.Merplings.Entities.Demons;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.AttackWithOwnerGoal;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.CallbackI;

import java.util.Optional;
import java.util.UUID;

public abstract class DemonBaseClass extends PathAwareEntity
{
    protected static final TrackedData<Optional<UUID>> OWNER_UUID;

    protected DemonBaseClass(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
    }

    @Override
    protected void initGoals() {

    }

    public void setOwnerUuid(@Nullable UUID uuid) {
        this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    public void setOwner(PlayerEntity player)
    {
        this.setOwnerUuid(player.getUuid());

    }


    static
    {
        OWNER_UUID = DataTracker.registerData(DemonBaseClass.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    }
}
