package io.github.Bubblie.Merplings;

import io.github.Bubblie.Merplings.Entities.BaoEntity.AnnoyingEntity;
import io.github.Bubblie.Merplings.Entities.EntitySounds;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {

	public static final String MOD_ID = "merp";

	@Override
	public void onInitialize()
	{
		ModItems.registerItems();
		AnnoyingEntity.registerAnnoyingEntity();
		EntitySounds.registerEntitySounds();

	}
}
