package io.github.Bubblie.Merplings.Mana;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.Bubblie.Merplings.mixin.PlayerEntityMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class ManaHud extends DrawableHelper
{
    public void render(MatrixStack matrices) {
        MinecraftClient client = MinecraftClient.getInstance();
        RenderSystem.setShaderTexture(0,new Identifier("merp", "textures/gui/mana.png"));
        InGameHud hud = new InGameHud(client);
        IManaManager manaManager = (IManaManager)client.player;
        Random random = new Random();
        int playerMana = manaManager.getManaManager().manaLevel;
        int maxMana = manaManager.getManaManager().maxManaLevel;
        int ticker = manaManager.getManaManager().manaTickTimer;
        int scaledWidth = client.getWindow().getScaledWidth();
        int scaledHeight = client.getWindow().getScaledHeight();
        int x = scaledWidth / 2 + 79;
        int y = scaledHeight - 51;

        for(int i = 0; i<maxMana/2; i++) {

            int decider = i*2+1;
            this.drawTexture(matrices, x - (i * 12), y, 127, 11, 12, 12, 256, 256);

            if(decider==playerMana)
            {
                this.drawTexture(matrices, (x+6)-(i*12),y+1,152,12,5,10,256,256);
            }

            if(decider<playerMana)
            {
                this.drawTexture(matrices, (x+2)-(i*12),y+1,142,12,9,9,256,256);
            }

            if(ticker==manaManager.getManaManager().getMaxTime()-1)
            {
                y = y + (random.nextInt(1)-1);
            }


        }




    }

}
