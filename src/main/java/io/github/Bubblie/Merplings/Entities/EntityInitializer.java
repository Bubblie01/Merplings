package io.github.Bubblie.Merplings.Entities;

import io.github.Bubblie.Merplings.Entities.BaoEntity.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import static io.github.Bubblie.Merplings.Main.MOD_ID;

@Environment(EnvType.CLIENT)
public class EntityInitializer implements ClientModInitializer
{
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID,"bao"),"merp");
    @Override
    public void onInitializeClient()
    {
        EntityRendererRegistry.INSTANCE.register(AnnoyingEntity.BAO, (context) -> {
            return new AnnoyingEntityRenderer(context);
        });

        EntityRendererRegistry.INSTANCE.register(UndeadEntity.UNDEAD, (context) -> {
            return new UndeadEntityRenderer(context);
        });

    }
}
