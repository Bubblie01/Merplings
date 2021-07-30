package io.github.Bubblie.Merplings.MerpBlocks;

import io.github.Bubblie.Merplings.Main;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockRegistry
{
    public static final DemonPumpkin DEMON_PUMPKIN = new DemonPumpkin(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).strength(4.0f));
    public static final FunnyTorch FUNNY_TORCH = new FunnyTorch(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().luminance((state) -> {
        return 14;
    }).sounds(BlockSoundGroup.WOOD), ParticleTypes.FLAME);
    public static final FunnyWallTorch FUNNY_WALL_TORCH = new FunnyWallTorch(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().luminance((state) -> {
        return 14;
    }).sounds(BlockSoundGroup.WOOD), ParticleTypes.FLAME);

    public static void RegisterBlocks()
    {
        //Demon Pumpkin
        Registry.register(Registry.BLOCK, new Identifier(Main.MOD_ID,"demon_pumpkin_block"), DEMON_PUMPKIN);
        Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, "demon_pumpkin_block"), new BlockItem(DEMON_PUMPKIN, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));

        BlockRenderLayerMap.INSTANCE.putBlock(FUNNY_TORCH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FUNNY_WALL_TORCH, RenderLayer.getCutout());
        Registry.register(Registry.BLOCK, new Identifier(Main.MOD_ID,"funny_torch_block"), FUNNY_TORCH);
        Registry.register(Registry.BLOCK, new Identifier(Main.MOD_ID, "funny_wall_torch"),FUNNY_WALL_TORCH);
        Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, "funny_torch_block"), (BlockItem)(new WallStandingBlockItem(FUNNY_TORCH, FUNNY_WALL_TORCH, new FabricItemSettings().group(ItemGroup.DECORATIONS))));
    }

}
