package mod.crend.dynamiccrosshair.compat.wondrouswilds;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.blocks.NestBoxBlock;
import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import com.ineffa.wondrouswilds.items.ScrollOfSecretsItem;
import com.ineffa.wondrouswilds.registry.WondrousWildsItems;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class ApiImplWondrousWilds implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return WondrousWilds.MOD_ID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof ScrollOfSecretsItem;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof NestBoxBlock;
	}

	@Override
	public boolean isInteractableEntity(Entity entity) {
		return entity instanceof WoodpeckerEntity;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();
		ItemStack itemStack = context.getItemStack();

		if (entity instanceof WoodpeckerEntity woodpecker) {
			ItemStack woodpeckerHeldStack = woodpecker.getStackInHand(Hand.MAIN_HAND);
			if (woodpecker.isTame()) {
				if (context.isMainHand() && itemStack.isEmpty() && !woodpeckerHeldStack.isEmpty()) {
					return Crosshair.INTERACTABLE;
				}

				if (!itemStack.isEmpty()) {
					int woodpeckerSpaceRemaining = woodpeckerHeldStack.getMaxCount() - woodpeckerHeldStack.getCount();
					if (woodpeckerSpaceRemaining > 0) {
						if (woodpeckerHeldStack.isEmpty() || woodpeckerHeldStack.getItem() == itemStack.getItem()) {
							return Crosshair.USABLE;
						}
					}
				}
			} else if (itemStack.getItem() == WondrousWildsItems.LOVIFIER) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
