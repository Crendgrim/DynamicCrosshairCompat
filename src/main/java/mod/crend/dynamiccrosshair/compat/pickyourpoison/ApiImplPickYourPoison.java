package mod.crend.dynamiccrosshair.compat.pickyourpoison;

import ladysnake.pickyourpoison.common.PickYourPoison;
import ladysnake.pickyourpoison.common.entity.PoisonDartFrogEntity;
import ladysnake.pickyourpoison.common.item.ThrowingDartItem;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IEntityHandler;
import mod.crend.dynamiccrosshair.api.IThrowableItemHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.Items;

public class ApiImplPickYourPoison implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return PickYourPoison.MODID;
	}

	@Override
	public IEntityHandler getEntityHandler() {
		return (player, itemStack, entity) -> {
			if (entity instanceof PoisonDartFrogEntity) {
				if (itemStack.isOf(Items.BOWL)) {
					return Crosshair.USE_ITEM;
				}
				if (itemStack.getItem() instanceof ThrowingDartItem throwingDart) {
					if (throwingDart.getStatusEffectInstance() == null) {
						return Crosshair.USE_ITEM;
					}
				}
			}

			return null;
		};
	}

	@Override
	public IThrowableItemHandler getThrowableItemHandler() {
		return (player, itemStack) -> {
			if (itemStack.getItem() instanceof ThrowingDartItem) {
				return Crosshair.THROWABLE;
			}

			return null;
		};
	}
}
