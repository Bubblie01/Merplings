package io.github.Bubblie.Merplings.mixin;

import com.mojang.datafixers.DataFixer;
import io.github.Bubblie.Merplings.Structures.StructureData;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.resource.DefaultClientResourcePack;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class ExampleMixin {
	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		System.out.println("Shut the fuck up");

	}
}
