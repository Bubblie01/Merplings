package io.github.Bubblie.Merplings.Mana;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ManaManager
{
    private int manaLevel = 20;
    private float exhaustion;
    private int manaTickTimer;

    public void addMana(int mana)
    {
        this.manaLevel = Math.min(mana + this.manaLevel, 20);
    }

    public int getManaLevel() {
        return manaLevel;
    }

    public void manaItem(Item item, ItemStack stack)
    {

    }

    public boolean isManaItem()
    {
        return false;
    }
}
