package mod.crend.dynamiccrosshair.compat.shroomdealers;

import com.marwinekk.shroomdealers.ShroomDealersMod;
import com.marwinekk.shroomdealers.entity.AmethystDeceiverDealerEntity;
import com.marwinekk.shroomdealers.entity.BayBoleteDealerEntity;
import com.marwinekk.shroomdealers.entity.ChampignonDealerEntity;
import com.marwinekk.shroomdealers.entity.ToadstoolDealerEntity;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ApiImplShroomDealers implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return ShroomDealersMod.MODID;
	}

	@Override
	public boolean isInteractableEntity(Entity entity) {
		return entity instanceof AmethystDeceiverDealerEntity
				|| entity instanceof BayBoleteDealerEntity
				|| entity instanceof ChampignonDealerEntity
				|| entity instanceof ToadstoolDealerEntity
				;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();
		ItemStack itemStack = context.getItemStack();

		if (isInteractableEntity(entity)) {
			if (itemStack.isOf(Blocks.RED_MUSHROOM.asItem()) || itemStack.isOf(Blocks.BROWN_MUSHROOM.asItem())) {
				return Crosshair.USABLE;
			}
		}
		return null;
	}
}
