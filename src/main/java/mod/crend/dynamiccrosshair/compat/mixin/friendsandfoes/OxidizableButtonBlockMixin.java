//? if friends-and-foes {
package mod.crend.dynamiccrosshair.compat.mixin.friendsandfoes;

import com.faboslav.friendsandfoes.block.Oxidizable;
import com.faboslav.friendsandfoes.block.OxidizableButtonBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoneycombItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = OxidizableButtonBlock.class, remap = false)
public abstract class OxidizableButtonBlockMixin implements DynamicCrosshairBlock {

	@Shadow public abstract net.minecraft.block.Oxidizable.OxidationLevel getDegradationLevel();

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		Item item = context.getItem();
		if (item instanceof HoneycombItem) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		if (item instanceof AxeItem && getDegradationLevel() != Oxidizable.OxidationLevel.UNAFFECTED) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
