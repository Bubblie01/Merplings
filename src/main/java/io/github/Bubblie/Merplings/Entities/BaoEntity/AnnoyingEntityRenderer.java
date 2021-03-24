package io.github.Bubblie.Merplings.Entities.BaoEntity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import static io.github.Bubblie.Merplings.Main.MOD_ID;

public class AnnoyingEntityRenderer extends MobEntityRenderer<AnnoyingEntity, AnnoyingEntityModel>
{
    public AnnoyingEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new AnnoyingEntityModel(0,false), 0.5f);
        this.addFeature(new HeldItemFeatureRenderer<>(this));
        this.addFeature(new ArmorFeatureRenderer(this, new AnnoyingEntityModel(0.5F, false), new AnnoyingEntityModel(0.5F, false)));
        this.addFeature(new HeadFeatureRenderer(this));
    }
    @Override
    public Identifier getTexture(AnnoyingEntity entity)
    {
        return new Identifier(MOD_ID,"textures/entities/bao/bao_skin.png");
    }

}
