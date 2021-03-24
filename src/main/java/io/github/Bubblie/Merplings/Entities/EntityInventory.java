package io.github.Bubblie.Merplings.Entities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface EntityInventory extends Inventory
{
    DefaultedList<ItemStack> getItems();


    static EntityInventory of(DefaultedList<ItemStack> items)
    {
        return () -> items;
    }

    static EntityInventory ofSize(int size)
    {
        return of(DefaultedList.ofSize(size, ItemStack.EMPTY));
    }

    @Override
    default int size()
    {
        return getItems().size();
    }

    default boolean isEmpty()
    {
       for(int checkInven = 0; checkInven < size(); checkInven++)
       {
           ItemStack stack = getStack(checkInven);
           if(!stack.isEmpty())
           {
               return false;
           }
       }
       return true;
    }

    default ItemStack removeStack(int slot, int count) {
        ItemStack result = Inventories.splitStack(getItems(), slot, count);
        if (!result.isEmpty()) {
            markDirty();
        }
        return result;
    }

    @Override
    default ItemStack removeStack(int slot) {
        return Inventories.removeStack(getItems(), slot);
    }

    default void setStack(int slot, ItemStack stack)
    {
        getItems().set(slot, stack);
        if(stack.getCount() > getMaxCountPerStack())
        {
            stack.setCount(getMaxCountPerStack());
        }
    }

    @Override
    default ItemStack getStack(int slot) {
        return getItems().get(slot);
    }

    @Override
    default void clear() {
        getItems().clear();
    }

    default void markDirty()
    {

    }




    @Override
    default boolean canPlayerUse(PlayerEntity player)
    {
        return false;
    }



}
