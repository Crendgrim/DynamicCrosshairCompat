package mod.crend.dynamiccrosshair.compat.pickyourpoison;

import ladysnake.pickyourpoison.common.PickYourPoison;
import ladysnake.pickyourpoison.common.entity.PoisonDartFrogEntity;
import ladysnake.pickyourpoison.common.item.ThrowingDartItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.ItemCategory;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ApiImplPickYourPoison implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return PickYourPoison.MODID;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		if (context.getEntity() instanceof PoisonDartFrogEntity) {
			ItemStack itemStack = context.getItemStack();
			if (itemStack.isOf(Items.BOWL)) {
				return Crosshair.USABLE;
			}
			if (itemStack.getItem() instanceof ThrowingDartItem throwingDart) {
				if (throwingDart.getStatusEffectInstance() == null) {
					return Crosshair.USABLE;
				}
			}
		}

		return null;
	}

	@Override
	public ItemCategory getItemCategory(ItemStack itemStack) {
		if (itemStack.getItem() instanceof ThrowingDartItem) {
			return ItemCategory.THROWABLE;
		}
		return ItemCategory.NONE;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (context.includeThrowable() && context.getItem() instanceof ThrowingDartItem) {
			return Crosshair.THROWABLE;
		}

		return null;
	}
}
