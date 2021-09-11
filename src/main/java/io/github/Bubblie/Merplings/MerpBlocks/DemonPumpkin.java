package io.github.Bubblie.Merplings.MerpBlocks;

import io.github.Bubblie.Merplings.Mana.IManaManager;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class DemonPumpkin extends HorizontalFacingBlock {

    public static final DirectionProperty FACING;



    public DemonPumpkin(AbstractBlock.Settings settings) {
        super(settings);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
            if(!world.isClient)
            {
                System.out.println("WOOOO YEAAAH BABY");
                IManaManager manaManager = (IManaManager)player;
                manaManager.getManaManager().subtractMana(1);
            }

            return ActionResult.SUCCESS;

    }

    static
    {
        FACING = HorizontalFacingBlock.FACING;
    }



}
