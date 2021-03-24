package io.github.Bubblie.Merplings.Entities;

import io.github.Bubblie.Merplings.Entities.BaoEntity.AnnoyingEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import io.github.Bubblie.Merplings.Entities.BaoEntity.AnnoyingEntity;

@Environment(EnvType.CLIENT)
public class EntityInitializer implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        EntityRendererRegistry.INSTANCE.register(AnnoyingEntity.BAO, (dispatcher,context) -> {
            return new AnnoyingEntityRenderer(dispatcher);
        });

    }
}
