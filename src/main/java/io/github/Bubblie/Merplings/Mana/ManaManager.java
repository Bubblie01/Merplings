package io.github.Bubblie.Merplings.Mana;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.lwjgl.system.CallbackI;

public class ManaManager
{
    public int manaLevel = 20;
    public int maxManaLevel = 20;
    public int manaTickTimer;
    public int maxTime;



    public void addMana(int mana)
    {
        this.manaLevel = Math.min(mana + this.manaLevel, 20);
    }

    public void subtractMana(int mana){this.setManaLevel(Math.max(0 , this.manaLevel-mana));}

    public int getManaLevel() {
        return manaLevel;
    }

    public int getGetMaxManaLevel(){return maxManaLevel;}

    public void updateMana(LivingEntity player)
    {
        this.setDefaultMaxTime();
        if(this.manaLevel<this.maxManaLevel) {
            this.manaTickTimer++;
            if(this.manaTickTimer==this.maxTime) {
                this.addMana(1);
                this.manaTickTimer = 0;
            }
        }

    }

    public void setManaLevel(int manaLevel)
    {
        this.manaLevel = manaLevel;
    }

    public void setDefaultMaxTime()
    {
        this.maxTime = 30;
    }

    public int getMaxTime()
    {
        return this.maxTime;
    }

    public void readNbt(NbtCompound nbt)
    {
        nbt.getInt("manaLevel");
        nbt.getInt("maxManaLevel");
        nbt.getInt("manaTickTimer");

    }

    public void writeNbt(NbtCompound nbt)
    {
        nbt.putInt("manaLevel", this.manaLevel);
        nbt.putInt("maxManaLevel", this.maxManaLevel);
        nbt.putInt("manaTickTimer", this.manaTickTimer);
    }

}
