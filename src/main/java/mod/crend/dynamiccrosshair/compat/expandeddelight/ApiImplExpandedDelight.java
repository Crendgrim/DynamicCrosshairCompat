package mod.crend.dynamiccrosshair.compat.expandeddelight;

import com.ianm1647.expandeddelight.ExpandedDelight;
import com.ianm1647.expandeddelight.block.custom.CinnamonLogBlock;
import com.ianm1647.expandeddelight.block.custom.JuicerBlock;
import com.ianm1647.expandeddelight.block.custom.MortarPestleBlock;
import com.ianm1647.expandeddelight.block.entity.JuicerBlockEntity;
import com.ianm1647.expandeddelight.item.ItemList;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class ApiImplExpandedDelight implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return ExpandedDelight.MOD_ID;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof JuicerBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		Block block = context.getBlock();

		if (block instanceof CinnamonLogBlock) {
			if (context.getItem() instanceof AxeItem) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof JuicerBlock && context.getBlockEntity() instanceof JuicerBlockEntity juicerBlockEntity) {
			if (context.getItemStack().isOf(Items.GLASS_BOTTLE) && !juicerBlockEntity.getStack(3).isEmpty()) {
				return Crosshair.USABLE;
			}
			return Crosshair.INTERACTABLE;
		}

		return null;
	}
}
