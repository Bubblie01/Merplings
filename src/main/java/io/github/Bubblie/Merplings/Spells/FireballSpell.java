package io.github.Bubblie.Merplings.Spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class FireballSpell extends Spell {
    public FireballSpell(Identifier id, int cost) {
        super(id, cost);
    }

    @Override
    public void cast(ServerPlayerEntity player)
    {
        super.cast(player);
        //Gets mouse location by using the tick specified between 0-1 in the .getRotationVec method and then uses the provided tick to store the pitch and yaw.
        Vec3d mouseLocation = player.getRotationVec(0.5F);
        //Velocities of the fireball
        double XCord = mouseLocation.getX();
        double YCord = mouseLocation.getY();
        double ZCord = mouseLocation.getZ();
        //Fireball entity and velocity
        FireballEntity fireballEntity = new FireballEntity(player.world,player,XCord, YCord, ZCord, 1);
        //Updates the position and puts the fireball in the player's eyesight level and the player x and z positions.
        fireballEntity.updatePosition(player.getX(), player.getEyeY(), player.getZ());
        player.world.spawnEntity(fireballEntity);
    }
}
