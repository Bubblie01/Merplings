package io.github.Bubblie.Merplings;

import io.github.Bubblie.Merplings.Entities.BaoEntity.AnnoyingEntity;
import io.github.Bubblie.Merplings.SpellSlipsAndBooks.CastSlip;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems
{
    public static final Item TOPAZ = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
    public static ToolItem TOPAZ_SWORD = new CustomSwordItem(TopazToolMaterial.INSTANCE, 2, -2.4F, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item BAO_SPAWN_EGG = new SpawnEggItem(AnnoyingEntity.BAO, 0x0d00ff, 0xFFFFFF, new Item.Settings().group(ItemGroup.MISC));
    public static final CastSlip CAST_SLIP = new CastSlip(new Item.Settings().group(ItemGroup.MISC));
    public static final FunnyEnchant FUNNY_ENCHANTMENT = new FunnyEnchant(Enchantment.Rarity.UNCOMMON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    public static void registerItems()
    {
        Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, "topaz"), TOPAZ);
        Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID,"topaz_sword"), TOPAZ_SWORD);
        Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID,"bao_egg"), BAO_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, "cast_slip"),CAST_SLIP);
        Registry.register(Registry.ENCHANTMENT, new Identifier(Main.MOD_ID, "funny_enchantment"), FUNNY_ENCHANTMENT);
    }
}
