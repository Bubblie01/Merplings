package io.github.Bubblie.Merplings.Entities.BaoEntity;

import com.google.common.collect.Lists;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.collection.DefaultedList;
import org.lwjgl.system.CallbackI;

import java.util.Set;

public class EntityInventory extends SimpleInventory
{
    public EntityInventory(int size)
    {
        super(size);
    }

    public EntityInventory(ItemStack items) {
        super(items);
    }

    public ItemStack addItem(Item item, int count) {
        ItemStack itemStack = new ItemStack(item, count);

        for(int i = 0; i<this.size(); i++)
        {
            ItemStack itemStack2 = this.getStack(i);
            if(itemStack2.isEmpty())
            {
                this.addStack(itemStack);
                break;
            }
        }

        return itemStack;
    }

    public void searchIngredient(Tag.Identified<Item> itemTag)
    {
        for(int i = 0; i<this.size(); i++)
        {
            ItemStack itemWithTags = this.getStack(i);
            System.out.println(itemWithTags.getItem());

        }

    }



}
