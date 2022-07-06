package mod.crend.dynamiccrosshair.compat.campanion;

import com.terraformersmc.campanion.Campanion;
import com.terraformersmc.campanion.block.BaseTentBlock;
import com.terraformersmc.campanion.block.LawnChairBlock;
import com.terraformersmc.campanion.block.LeatherTanner;
import com.terraformersmc.campanion.block.RopeBridgePostBlock;
import com.terraformersmc.campanion.blockentity.RopeBridgePostBlockEntity;
import com.terraformersmc.campanion.item.*;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class ApiImplCampanion implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Campanion.MOD_ID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof FlareItem
				|| item instanceof GrapplingHookItem
				|| item instanceof MarshmallowOnAStickItem
				|| item instanceof SkippingStoneItem
				|| item instanceof SleepingBagItem;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof BackpackItem;
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		ItemStack handItemStack = context.getItemStack();
		Item item = handItemStack.getItem();
		if (item instanceof BackpackItem) {
			EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(handItemStack);
			ItemStack equippedStack = context.player.getEquippedStack(equipmentSlot);
			if (equippedStack.isEmpty()) {
				return Crosshair.USE_ITEM;
			}
		}

		if (item instanceof PlaceableTentItem tentItem) {
			if (item instanceof TentBagItem && TentBagItem.isEmpty(handItemStack)) {
				if (context.isWithBlock()) {
					if (context.getBlock() instanceof BaseTentBlock) {
						return Crosshair.CORRECT_TOOL;
					}
				}
			} else {
				HitResult result = context.player.raycast(10.0, 0.0F, true);
				if (result instanceof BlockHitResult && result.getType() == HitResult.Type.BLOCK) {
					BlockPos base = ((BlockHitResult) result).getBlockPos().up();
					if (tentItem.getErrorPosition(context.world, base, handItemStack).isEmpty()) {
						return Crosshair.HOLDING_BLOCK;
					}
				}
			}
		}

		return null;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack handItemStack = context.getItemStack();

		if (block instanceof LawnChairBlock) {
			return Crosshair.INTERACTABLE;
		}

		if (block instanceof LeatherTanner leatherTannerBlock) {
			if (leatherTannerBlock.getAge(blockState) == 0) {
				if (handItemStack.isOf(Items.LEATHER)) {
					return Crosshair.USE_ITEM;
				}
			} else {
				return Crosshair.INTERACTABLE;
			}
		}

		if (block instanceof RopeBridgePostBlock) {
			if (handItemStack.isOf(CampanionItems.ROPE)) {
				return Crosshair.USE_ITEM;
			}
			if (handItemStack.isIn(ItemTags.PLANKS)) {
				if (context.getBlockEntity() instanceof RopeBridgePostBlockEntity ropeBridgeEntity) {
					if (!ropeBridgeEntity.getGhostPlanks().entrySet().isEmpty()) {
						return Crosshair.USE_ITEM;
					}
				}
			}
			return new Crosshair().withFlag(Crosshair.Flag.FixedAll);
		}

		if (block instanceof BaseTentBlock) {
			if (handItemStack.getItem() instanceof DyeItem) {
				return Crosshair.USE_ITEM;
			}
		}

		return null;
	}
}