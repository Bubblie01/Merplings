package io.github.Bubblie.Merplings.Entities.BaoEntity;

import io.github.Bubblie.Merplings.Entities.EntityInitializer;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import static io.github.Bubblie.Merplings.Main.MOD_ID;

public class AnnoyingEntityRenderer extends MobEntityRenderer<AnnoyingEntity, AnnoyingEntityModel>
{


    public AnnoyingEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new AnnoyingEntityModel(context.getPart(EntityModelLayers.PLAYER)),0.5f);
    }

    @Override
    public Identifier getTexture(AnnoyingEntity entity)
    {
        return new Identifier(MOD_ID,"textures/entities/bao/bao_skin.png");
    }

}
