package io.github.Bubblie.Merplings.mixin;

import com.mojang.authlib.GameProfile;
import io.github.Bubblie.Merplings.Mana.IManaManager;
import io.github.Bubblie.Merplings.Mana.ManaManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements IManaManager
{


    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ManaManager getManaManager()
    {
        return manaManager;
    }

    @Inject(at = @At("HEAD"), method = "readCustomDataFromNbt")
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci)
    {
        getManaManager().readNbt(nbt);
    }

    @Inject(at = @At("HEAD"), method = "writeCustomDataToNbt")
    public void writeCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci)
    {
        getManaManager().writeNbt(nbt);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;update(Lnet/minecraft/entity/player/PlayerEntity;)V", shift = At.Shift.AFTER), method = "tick")
    public void tick(CallbackInfo ci)
    {
        getManaManager().updateMana(this);
    }


}
