package io.github.Bubblie.Merplings.mixin;

import io.github.Bubblie.Merplings.Mana.ManaHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class ManaHudGuiMixin
{
    private static ManaHud manaHud;
    @Inject(at = @At("TAIL"), method = "renderStatusBars")
    private void render(MatrixStack matrices, CallbackInfo ci)
    {
        PlayerEntity player = MinecraftClient.getInstance().player;
        manaHud = new ManaHud();
        manaHud.render(matrices);
    }


}
