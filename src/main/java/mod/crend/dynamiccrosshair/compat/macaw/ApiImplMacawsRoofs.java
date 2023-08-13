package mod.crend.dynamiccrosshair.compat.macaw;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.mixin.mcwroofs.GutterTallAccessor;
import mod.crend.dynamiccrosshair.compat.mixin.mcwroofs.RainGutterAccessor;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.kikoz.mcwroofs.MacawsRoofs;
import net.kikoz.mcwroofs.objects.gutters.GutterTall;
import net.kikoz.mcwroofs.objects.gutters.RainGutter;
import net.kikoz.mcwroofs.objects.items.Hammer;
import net.kikoz.mcwroofs.objects.roofs.BaseRoof;
import net.kikoz.mcwroofs.objects.roofs.RoofGlass;
import net.kikoz.mcwroofs.objects.roofs.RoofTopNew;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.BooleanProperty;

public class ApiImplMacawsRoofs implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return MacawsRoofs.MOD_ID;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof RoofGlass;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (block instanceof BaseRoof || block instanceof RoofGlass || block instanceof RoofTopNew || block instanceof RainGutter) {
			if (itemStack.getItem() instanceof Hammer) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof RoofGlass) {
			if (itemStack.getItem() != block.asItem()) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (block instanceof RainGutter || block instanceof GutterTall) {
			BooleanProperty water;
			if (block instanceof RainGutter) {
				water = RainGutterAccessor.getWATER();
			} else {
				water = GutterTallAccessor.getWATER();
			}
			boolean hasWater = blockState.get(water);
			if (!hasWater && itemStack.isOf(Items.WATER_BUCKET)) {
				return Crosshair.USABLE;
			}
			if (hasWater && (itemStack.isOf(Items.GLASS_BOTTLE) || itemStack.isOf(Items.BUCKET))) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
