package mod.crend.dynamiccrosshair.compat.dramaticdoors;

import com.fizzware.dramaticdoors.fabric.DramaticDoors;
import com.fizzware.dramaticdoors.fabric.blockentities.TallNetheriteDoorBlockEntity;
import com.fizzware.dramaticdoors.fabric.blocks.ShortDoorBlock;
import com.fizzware.dramaticdoors.fabric.blocks.ShortNetheriteDoorBlock;
import com.fizzware.dramaticdoors.fabric.blocks.TallDoorBlock;
import com.fizzware.dramaticdoors.fabric.blocks.TallNetheriteDoorBlock;
import com.fizzware.dramaticdoors.fabric.compat.Compats;
import com.fizzware.dramaticdoors.fabric.compat.registries.SupplementariesCompat;
import com.fizzware.dramaticdoors.fabric.tags.DDBlockTags;
import com.fizzware.dramaticdoors.fabric.tags.DDItemTags;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;

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
				if (Compats.SUPPLEMENTARIES_INSTALLED) {
					if (block == SupplementariesCompat.SHORT_GOLD_DOOR && blockState.get(ShortDoorBlock.POWERED)) {
						return null;
					}
					if (block == SupplementariesCompat.SHORT_SILVER_DOOR && !blockState.get(ShortDoorBlock.POWERED)) {
						return null;
					}
				}
				return Crosshair.INTERACTABLE;
			}
		}
		if (block instanceof TallDoorBlock door) {
			if (door.type().canOpenByHand() || blockState.isIn(DDBlockTags.HAND_OPENABLE_TALL_METAL_DOORS)) {
				if (Compats.SUPPLEMENTARIES_INSTALLED) {
					if (block == SupplementariesCompat.TALL_GOLD_DOOR && blockState.get(TallDoorBlock.POWERED)) {
						return null;
					}
					if (block == SupplementariesCompat.TALL_SILVER_DOOR && !blockState.get(TallDoorBlock.POWERED)) {
						return null;
					}
				}
				return Crosshair.INTERACTABLE;
			}
		}

		return null;
	}
}
