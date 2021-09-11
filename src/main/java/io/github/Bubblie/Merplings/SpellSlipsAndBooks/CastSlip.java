package io.github.Bubblie.Merplings.SpellSlipsAndBooks;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;


public class CastSlip extends Item {
    public CastSlip(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient)
        MinecraftClient.getInstance().setScreen(new CastSlipEditScreen(user, user.getStackInHand(hand),hand));
        return super.use(world, user, hand);
    }
}
