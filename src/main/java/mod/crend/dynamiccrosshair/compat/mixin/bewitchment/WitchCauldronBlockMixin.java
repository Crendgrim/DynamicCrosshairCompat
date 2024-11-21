//? if bewitchment {
package mod.crend.dynamiccrosshair.compat.mixin.bewitchment;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import moriyashiine.bewitchment.common.block.WitchCauldronBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.NameTagItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = WitchCauldronBlock.class, remap = false)
public class WitchCauldronBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if ((itemStack.getItem() instanceof NameTagItem && itemStack.hasCustomName())
				|| itemStack.isOf(Items.BUCKET)
				|| itemStack.isOf(Items.WATER_BUCKET)
				|| itemStack.isOf(Items.GLASS_BOTTLE)
				|| (itemStack.isOf(Items.POTION) && PotionUtil.getPotion(itemStack) == Potions.WATER)
		) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
