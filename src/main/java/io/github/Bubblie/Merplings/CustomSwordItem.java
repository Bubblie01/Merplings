package io.github.Bubblie.Merplings;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Tickable;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.entity.LivingEntity;



public class CustomSwordItem extends SwordItem
{


    public CustomSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings)
    {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        //Gets mouse location by using the tick specified between 0-1 in the .getRotationVec method and then uses the provided tick to store the pitch and yaw.
        Vec3d mouseLocation = user.getRotationVec(0.5F);
        //Velocities of the fireball
        double XCord = mouseLocation.getX();
        double YCord = mouseLocation.getY();
        double ZCord = mouseLocation.getZ();
        //Fireball entity and velocity
        FireballEntity fireballEntity = new FireballEntity(user.world, user, XCord, YCord, ZCord);
        //Updates the position and puts the fireball in the player's eyesight level and the player x and z positions.
        fireballEntity.updatePosition(user.getX(), user.getEyeY(), user.getZ());
        //Spawns the fireball entity in the world.
        user.world.spawnEntity(fireballEntity);
        //Removes 50 durability everytime a fireball is spawned using right click
        stack.damage(50, (LivingEntity)user, (entity) -> {
            entity.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });

        //Sets a cooldown for 20 ticks. every 20 ticks equals 1 second real time.
        user.getItemCooldownManager().set(this, 40);

        //returns success based on the item in the user's hand
        return TypedActionResult.success(stack);
    }

}
