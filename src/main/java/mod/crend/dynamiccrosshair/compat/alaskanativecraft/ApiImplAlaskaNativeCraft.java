package mod.crend.dynamiccrosshair.compat.alaskanativecraft;

import com.github.platymemo.alaskanativecraft.AlaskaNativeCraft;
import com.github.platymemo.alaskanativecraft.block.DryingRackBlock;
import com.github.platymemo.alaskanativecraft.block.entity.DryingRackBlockEntity;
import com.github.platymemo.alaskanativecraft.entity.DogsledEntity;
import com.github.platymemo.alaskanativecraft.item.DogsledItem;
import com.github.platymemo.alaskanativecraft.recipe.DryingRecipe;
import mod.crend.dynamiccrosshair.DynamicCrosshair;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IBlockInteractHandler;
import mod.crend.dynamiccrosshair.api.IBlockItemHandler;
import mod.crend.dynamiccrosshair.api.IEntityHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.config.BlockCrosshairPolicy;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.HitResult;

import java.util.Optional;

public class ApiImplAlaskaNativeCraft implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return AlaskaNativeCraft.MOD_ID;
	}

	@Override
	public IEntityHandler getEntityHandler() {
		return (player, itemStack, entity) -> {

			if (entity instanceof DogsledEntity && !player.isSneaking()) {
				return Crosshair.INTERACTABLE;
			}

			return null;
		};
	}

	@Override
	public IBlockInteractHandler getBlockInteractHandler() {
		return (player, itemStack, blockPos, blockState) -> {
			if (blockState.getBlock() instanceof DryingRackBlock) {
				DryingRackBlockEntity blockEntity = (DryingRackBlockEntity) MinecraftClient.getInstance().world.getBlockEntity(blockPos);
				Optional<DryingRecipe> optional = blockEntity.getRecipeFor(itemStack);
				if (optional.isPresent()) {
					return Crosshair.USE_ITEM;
				}
				if (blockEntity.getItemsBeingDried().size() > 0) {
					return Crosshair.INTERACTABLE;
				}
			}

			return null;
		};
	}

	@Override
	public IBlockItemHandler getBlockItemHandler() {
		return (player, itemStack) -> {
			if (itemStack.getItem() instanceof DogsledItem) {
				if (DynamicCrosshair.config.dynamicCrosshairHoldingBlock() == BlockCrosshairPolicy.IfInteractable) {
					HitResult hitResult = MinecraftClient.getInstance().crosshairTarget;
					if (hitResult.getType() == HitResult.Type.BLOCK) {
						return Crosshair.HOLDING_BLOCK;
					}
				} else return Crosshair.HOLDING_BLOCK;
			}
			return null;
		};
	}
}
