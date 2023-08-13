package mod.crend.dynamiccrosshair.compat.crockpot;

import com.github.nosrick.crockpot.CrockPotMod;
import com.github.nosrick.crockpot.block.CrockPotBlock;
import com.github.nosrick.crockpot.blockentity.CrockPotBlockEntity;
import com.github.nosrick.crockpot.blockentity.ElectricCrockPotBlockEntity;
import com.github.nosrick.crockpot.config.ConfigManager;
import com.github.nosrick.crockpot.tag.Tags;
import com.github.nosrick.crockpot.util.UUIDUtil;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;

public class ApiImplCrockpot implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return CrockPotMod.MOD_ID;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof CrockPotBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		ItemStack itemStack = context.getItemStack();

		if (blockState.getBlock() instanceof CrockPotBlock && context.getBlockEntity() instanceof CrockPotBlockEntity potBlockEntity) {
			if (itemStack.isOf(Items.NAME_TAG) && ConfigManager.canLockPots()) {
				if (potBlockEntity.isOwner(UUIDUtil.NO_PLAYER) || potBlockEntity.isOwner(context.player.getUuid())) {
					return Crosshair.USABLE;
				}
			} else if (potBlockEntity.isOwner(UUIDUtil.NO_PLAYER)
					|| potBlockEntity.isOwner(context.player.getUuid())
					|| (context.player.isCreative() && ConfigManager.creativePlayersIgnoreLocks())) {
				if (itemStack.isEmpty()) {
					return Crosshair.INTERACTABLE;
				} else if (!(potBlockEntity instanceof ElectricCrockPotBlockEntity) && itemStack.isOf(Blocks.REDSTONE_BLOCK.asItem())) {
					return Crosshair.USABLE;
				} else {
					if (blockState.get(CrockPotBlock.HAS_LIQUID)) {
						if (blockState.get(CrockPotBlock.HAS_LIQUID) && potBlockEntity.canBoil()) {
							if (itemStack.isOf(Items.BOWL)) {
								return Crosshair.USABLE;
							} else if (potBlockEntity.canAddFood(itemStack)) {
								return Crosshair.USABLE;
							}
						}
					} else {
						if (itemStack.isIn(Tags.CONSUMABLE_WATER_SOURCES_ITEMS)
								|| itemStack.isIn(Tags.INFINITE_WATER_SOURCES_ITEMS)) {
							return Crosshair.USABLE;
						} else if (ConfigManager.canFillWithWaterBottle()
								&& itemStack.getItem() instanceof PotionItem
								&& PotionUtil.getPotion(itemStack) == Potions.WATER) {
							return Crosshair.USABLE;
						}
					}
				}
			}
		}

		return null;
	}
}
