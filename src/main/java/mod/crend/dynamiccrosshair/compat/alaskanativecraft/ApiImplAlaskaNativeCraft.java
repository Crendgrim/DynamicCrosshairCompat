package mod.crend.dynamiccrosshair.compat.alaskanativecraft;

import com.github.platymemo.alaskanativecraft.AlaskaNativeCraft;
import com.github.platymemo.alaskanativecraft.block.DryingRackBlock;
import com.github.platymemo.alaskanativecraft.block.entity.DryingRackBlockEntity;
import com.github.platymemo.alaskanativecraft.entity.DogsledEntity;
import com.github.platymemo.alaskanativecraft.item.DogsledItem;
import com.github.platymemo.alaskanativecraft.recipe.DryingRecipe;
import mod.crend.dynamiccrosshair.DynamicCrosshair;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.config.BlockCrosshairPolicy;

import java.util.Optional;

public class ApiImplAlaskaNativeCraft implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return AlaskaNativeCraft.MOD_ID;
	}

	@Override
	public Crosshair checkEntity(CrosshairContext context) {
		if (context.getEntity() instanceof DogsledEntity && !context.player.isSneaking()) {
			return Crosshair.INTERACTABLE;
		}

		return null;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		if (context.getBlock() instanceof DryingRackBlock && context.getBlockEntity() instanceof DryingRackBlockEntity blockEntity) {
			Optional<DryingRecipe> optional = blockEntity.getRecipeFor(context.getItemStack());
			if (optional.isPresent()) {
				return Crosshair.USE_ITEM;
			}
			if (blockEntity.getItemsBeingDried().size() > 0) {
				return Crosshair.INTERACTABLE;
			}
		}

		return null;
	}

	@Override
	public Crosshair checkBlockItem(CrosshairContext context) {
		if (context.getItem() instanceof DogsledItem) {
			if (DynamicCrosshair.config.dynamicCrosshairHoldingBlock() == BlockCrosshairPolicy.IfInteractable) {
				if (context.isWithBlock()) {
					return Crosshair.HOLDING_BLOCK;
				}
			} else return Crosshair.HOLDING_BLOCK;
		}

		return null;
	}
}
