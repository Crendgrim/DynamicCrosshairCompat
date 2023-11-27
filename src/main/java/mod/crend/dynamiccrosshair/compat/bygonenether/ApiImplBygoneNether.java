package mod.crend.dynamiccrosshair.compat.bygonenether;

import com.izofar.bygonenether.BygoneNetherMod;
import com.izofar.bygonenether.entity.PiglinPrisoner;
import com.izofar.bygonenether.entity.WarpedEnderMan;
import com.izofar.bygonenether.entity.ai.PiglinPrisonerAi;
import com.izofar.bygonenether.item.WarpedEnderpearlItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.mixin.bygonenether.WarpedEnderManAccessor;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.PiglinActivity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ApiImplBygoneNether implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return BygoneNetherMod.MODID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof WarpedEnderpearlItem;
	}

	@Override
	public boolean isInteractableEntity(Entity entity) {
		return entity instanceof PiglinPrisoner;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();
		ItemStack itemStack = context.getItemStack();

		if (entity instanceof PiglinPrisoner piglinPrisoner) {
			if (PiglinPrisonerAi.canAdmire(piglinPrisoner, itemStack) && piglinPrisoner.getActivity() != PiglinActivity.ADMIRING_ITEM) {
				return Crosshair.USABLE;
			}
		}

		if (entity instanceof WarpedEnderMan warpedEnderMan) {
			if (itemStack.isOf(Items.SHEARS) && ((WarpedEnderManAccessor) warpedEnderMan).invokeIsReadyForShearing()) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
