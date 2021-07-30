package io.github.Bubblie.Merplings.Mana;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ManaHud extends DrawableHelper
{
    public void render(MatrixStack matrices) {
        MinecraftClient client = MinecraftClient.getInstance();
        client.getTextureManager().bindTexture(new Identifier("merp", "textures/gui/mana.png"));

        ManaManager manaManager = new ManaManager();

        this.drawTexture(matrices,4,36,0,26,12,12,64,64);
    }

}
