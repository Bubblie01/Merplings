package io.github.Bubblie.Merplings;

import io.github.Bubblie.Merplings.Entities.BaoEntity.AnnoyingEntity;
import io.github.Bubblie.Merplings.Entities.BaoEntity.UndeadEntity;
import io.github.Bubblie.Merplings.Entities.ModSounds;
import io.github.Bubblie.Merplings.MerpBlocks.BlockRegistry;
import io.github.Bubblie.Merplings.Spells.SpellRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Main implements ModInitializer {

	public static final String MOD_ID = "merp";
	public static final SpellRegistry SPELL_REGISTRY = new SpellRegistry();

	@Override
	public void onInitialize()
	{

		ModItems.registerItems();
		AnnoyingEntity.registerAnnoyingEntity();
		ModSounds.registerSounds();
		UndeadEntity.registerUndeadEntity();
		BlockRegistry.RegisterBlocks();


	}
}
