package mod.crend.dynamiccrosshair.compat.bitsandchisels;

import io.github.coolmineman.bitsandchisels.BitsBlock;
import io.github.coolmineman.bitsandchisels.BitsBlockEntity;
import io.github.coolmineman.bitsandchisels.api.BitUtils;
import io.github.coolmineman.bitsandchisels.blueprints.Blueprint;
import io.github.coolmineman.bitsandchisels.chisel.DiamondChisel;
import io.github.coolmineman.bitsandchisels.chisel.IronChisel;
import io.github.coolmineman.bitsandchisels.chisel.SmartChisel;
import io.github.coolmineman.bitsandchisels.wrench.WrenchItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.component.CrosshairVariant;
import mod.crend.dynamiccrosshair.component.ModifierUse;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;

public class ApiImplBitsAndChisels implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "bitsandchisels";
	}

	@Override
	public Crosshair checkTool(CrosshairContext context) {
		Item item = context.getItem();
		if (item instanceof IronChisel || item instanceof DiamondChisel || item instanceof SmartChisel) {
			if (context.isWithBlock()) {
				BlockHitResult hit = (BlockHitResult) context.hitResult;
				Direction side = hit.getSide();
				int x = (int) Math.floor((hit.getPos().getX() - (double) context.getBlockPos().getX()) * 16.0 + (double) side.getOffsetX() * -0.5);
				int y = (int) Math.floor((hit.getPos().getY() - (double) context.getBlockPos().getY()) * 16.0 + (double) side.getOffsetY() * -0.5);
				int z = (int) Math.floor((hit.getPos().getZ() - (double) context.getBlockPos().getZ()) * 16.0 + (double) side.getOffsetZ() * -0.5);
				if (BitUtils.exists(BitUtils.getBit(context.world, context.getBlockPos(), x, y, z))) {
					// chiselable block: hide crosshair so it's easier to see
					return Crosshair.NONE.withFlag(Crosshair.Flag.FixedStyle);
				}
				// non-chiselable block
				return Crosshair.INCORRECT_TOOL;
			} else {
				// fall back to vanilla tool crosshair
				return null;
			}
		}
		if (item instanceof WrenchItem) {
			if (context.isWithBlock()) {
				if (context.getBlock() instanceof BitsBlock) {
					return new Crosshair(CrosshairVariant.HoldingTool, ModifierUse.USE_ITEM);
				}
				return Crosshair.INCORRECT_TOOL;
			}
			return Crosshair.TOOL;
		}

		return null;
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (itemStack.getItem() instanceof Blueprint && context.isWithBlock()) {
			if (itemStack.getSubNbt("blueprint") != null || context.getBlockEntity() instanceof BitsBlockEntity) {
				return Crosshair.USE_ITEM;
			}
		}

		return null;
	}
}
