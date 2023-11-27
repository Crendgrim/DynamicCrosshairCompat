package mod.crend.dynamiccrosshair.compat.goblintraders;

import com.mrcrayfish.goblintraders.Constants;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ApiImplGoblinTraders implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Constants.MOD_ID;
	}

	@Override
	public boolean isInteractableEntity(Entity entity) {
		return entity instanceof AbstractGoblinEntity;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();
		ItemStack itemStack = context.getItemStack();

		if (entity instanceof AbstractGoblinEntity goblin) {
			if (itemStack.isOf(Items.NAME_TAG)) {
				return Crosshair.USABLE;
			}
			if (goblin.isAlive()
					&& !goblin.hasCustomer()
					&& !goblin.isBaby()
					&& (goblin.isFireImmune() || !goblin.isOnFire())
					&& !goblin.isStunned()
					&& !goblin.getOffers().isEmpty()
			) {
				return Crosshair.INTERACTABLE;
			}
		}

		return null;
	}
}
