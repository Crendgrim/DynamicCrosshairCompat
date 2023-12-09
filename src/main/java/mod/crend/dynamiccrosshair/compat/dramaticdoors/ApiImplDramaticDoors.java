package mod.crend.dynamiccrosshair.compat.dramaticdoors;

import com.fizzware.dramaticdoors.DramaticDoors;
import com.fizzware.dramaticdoors.blockentities.TallNetheriteDoorBlockEntity;
import com.fizzware.dramaticdoors.blocks.*;
import com.fizzware.dramaticdoors.compat.Compats;
import com.fizzware.dramaticdoors.tags.DDBlockTags;
import com.fizzware.dramaticdoors.tags.DDItemTags;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

public class ApiImplDramaticDoors implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return DramaticDoors.MOD_ID;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof TallDoorBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = context.getBlock();
		if (block instanceof ShortNetheriteDoorBlock || block instanceof TallNetheriteDoorBlock) {
			if (context.getBlockEntity() instanceof TallNetheriteDoorBlockEntity door) {
				if (!context.player.isSpectator()) {
					ItemStack stack = context.getItemStack();
					boolean isKey = stack.isIn(DDItemTags.KEY);
					if (!context.player.isInSneakingPose() || !isKey || !context.player.isCreative() && !door.isCorrectKey(stack)) {
						if (door.password == null) {
							if (isKey) {
								return Crosshair.USABLE;
							}
						} else {
							if (context.player.isCreative() || TallNetheriteDoorBlockEntity.hasKeyInInventory(context.player, door.password) == TallNetheriteDoorBlockEntity.KeyStatus.CORRECT_KEY) {
								return Crosshair.INTERACTABLE;
							}
						}
					} else {
						return Crosshair.INTERACTABLE;
					}
				}
			}
		}
		if (block instanceof ShortDoorBlock door) {
			if (door.type().canOpenByHand() || blockState.isIn(DDBlockTags.HAND_OPENABLE_SHORT_METAL_DOORS)) {
				if ((!Compats.SUPPLEMENTARIES_INSTALLED || door != Registries.BLOCK.get(ShortDoorBlock.GOLD_DOOR_RES) || !blockState.get(ShortDoorBlock.POWERED))
						&& (door != Registries.BLOCK.get(ShortDoorBlock.SILVER_DOOR_RES) || blockState.get(ShortDoorBlock.POWERED))) {
					return Crosshair.INTERACTABLE;
				}
			}
		}
		if (block instanceof TallDoorBlock door) {
			if (door.type().canOpenByHand() || blockState.isIn(DDBlockTags.HAND_OPENABLE_TALL_METAL_DOORS)) {
				if ((!Compats.SUPPLEMENTARIES_INSTALLED || door != Registries.BLOCK.get(TallDoorBlock.GOLD_DOOR_RES) || !blockState.get(TallDoorBlock.POWERED))
						&& (door != Registries.BLOCK.get(TallDoorBlock.SILVER_DOOR_RES) || blockState.get(TallDoorBlock.POWERED))) {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		return null;
	}
}
