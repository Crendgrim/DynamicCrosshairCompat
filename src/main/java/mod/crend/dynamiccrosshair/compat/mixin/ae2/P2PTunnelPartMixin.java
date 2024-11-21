//? if applied-energistics-2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.api.features.P2PTunnelAttunement;
import appeng.api.implementations.items.IMemoryCard;
import appeng.api.parts.IPartItem;
import appeng.parts.AEBasePart;
import appeng.parts.p2p.P2PTunnelPart;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = P2PTunnelPart.class, remap = false)
public abstract class P2PTunnelPartMixin extends AEBasePart implements DynamicCrosshairBlock {
	public P2PTunnelPartMixin(IPartItem<?> partItem) {
		super(partItem);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (itemStack.getItem() instanceof IMemoryCard) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}

		ItemStack newType = P2PTunnelAttunement.getTunnelPartByTriggerItem(itemStack);
		if (!newType.isEmpty()
				&& newType.getItem() != this.getPartItem()
				&& newType.getItem() instanceof IPartItem) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}

		return InteractionType.EMPTY;
	}
}
//?}
