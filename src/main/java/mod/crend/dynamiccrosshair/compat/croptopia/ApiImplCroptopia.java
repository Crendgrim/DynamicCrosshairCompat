package mod.crend.dynamiccrosshair.compat.croptopia;

import com.epherical.croptopia.CroptopiaMod;
import com.epherical.croptopia.blocks.CroptopiaCropBlock;
import com.epherical.croptopia.blocks.LeafCropBlock;
import com.epherical.croptopia.items.CroptopiaSaplingItem;
import com.epherical.croptopia.items.GuideBookItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.mixin.croptopia.ICroptopiaSaplingItemMixin;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ApiImplCroptopia implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "croptopia";
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (item instanceof GuideBookItem);
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof CroptopiaSaplingItem sapling && context.isWithBlock()) {
			if (context.getBlock() == ((ICroptopiaSaplingItemMixin) sapling).getVanillaLeafBlock()) {
				return Crosshair.USE_ITEM;
			}
		}

		return null;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		if (block instanceof CroptopiaCropBlock cropBlock
				&& !CroptopiaMod.getInstance().platform().skipHarvest()
				&& blockState.get(cropBlock.getAgeProperty()) == cropBlock.getMaxAge()
		) {
			return Crosshair.INTERACTABLE;
		}
		if (block instanceof LeafCropBlock leafBlock
				&& !CroptopiaMod.getInstance().platform().skipHarvest()
				&& blockState.get(leafBlock.getAgeProperty()) == leafBlock.getMaxAge()
		) {
			return Crosshair.INTERACTABLE;
		}

		return null;
	}
}
