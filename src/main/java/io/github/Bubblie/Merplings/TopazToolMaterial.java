package io.github.Bubblie.Merplings;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class TopazToolMaterial implements ToolMaterial
{
    public static final TopazToolMaterial INSTANCE = new TopazToolMaterial();


    //Sets durability
    public int getDurability()
    {
        return 1000;
    }

    //Sets how fast tools are when mining blocks
    public float getMiningSpeedMultiplier() {
        return 0;
    }

    //Sets attack damage. Remember it totals the damage by adding materialDamage + the toolDamage you specify in the creation of said tool item + 1
    public float getAttackDamage()
    {
        return 4.0F;
    }

    public int getMiningLevel()
    {
        return 3;
    }

    public int getEnchantability()
    {
        return 15;
    }

    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.TOPAZ);
    }


}
