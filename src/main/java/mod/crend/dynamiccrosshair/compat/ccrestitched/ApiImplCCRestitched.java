package mod.crend.dynamiccrosshair.compat.ccrestitched;

import dan200.computercraft.shared.MediaProviders;
import dan200.computercraft.shared.computer.blocks.BlockComputer;
import dan200.computercraft.shared.media.items.ItemPrintout;
import dan200.computercraft.shared.peripheral.diskdrive.BlockDiskDrive;
import dan200.computercraft.shared.peripheral.diskdrive.TileDiskDrive;
import dan200.computercraft.shared.peripheral.modem.wired.BlockCable;
import dan200.computercraft.shared.peripheral.modem.wired.BlockWiredModemFull;
import dan200.computercraft.shared.peripheral.modem.wired.TileCable;
import dan200.computercraft.shared.peripheral.printer.BlockPrinter;
import dan200.computercraft.shared.pocket.items.ItemPocketComputer;
import dan200.computercraft.shared.turtle.blocks.BlockTurtle;
import dan200.computercraft.shared.turtle.blocks.TileTurtle;
import dan200.computercraft.shared.util.Colour;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DyeColor;

public class ApiImplCCRestitched implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "computercraft";
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (item instanceof ItemPrintout || item instanceof ItemPocketComputer);
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();

		if (context.isWithBlock()) {
			Block block = context.getBlock();
			if (block instanceof BlockDiskDrive && context.getBlockEntity() instanceof TileDiskDrive drive) {
				if (drive.getStack(0).isEmpty() && MediaProviders.get(itemStack) != null) {
					return Crosshair.USE_ITEM;
				}
			}
			if (block instanceof BlockCable && context.getBlockEntity() instanceof TileCable cable) {
				if (cable.getCachedState().get(BlockCable.CABLE) && cable.hasModem()) {
					return Crosshair.INTERACTABLE;
				}
			}
		}
		return null;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();

		if (block instanceof BlockComputer<?>
				|| block instanceof BlockDiskDrive
				|| block instanceof BlockWiredModemFull
				|| block instanceof BlockPrinter
		) {
			if (!context.player.isSneaking()) {
				return Crosshair.INTERACTABLE;
			}
		}
		if (block instanceof BlockTurtle && context.getBlockEntity() instanceof TileTurtle turtle) {
			ItemStack itemStack = context.getItemStack();
			if (itemStack.getItem() instanceof DyeItem dye) {
				Colour turtleColor = Colour.fromHex(turtle.getColour());
				if (turtleColor == null || DyeColor.byId(15 - turtleColor.ordinal()) != dye.getColor()) {
					return Crosshair.USE_ITEM;
				}
			}
			if (itemStack.isOf(Items.WATER_BUCKET) && turtle.getColour() != -1) {
				return Crosshair.USE_ITEM;
			}
			if (itemStack.isOf(Items.NAME_TAG) && itemStack.hasCustomName()) {
				return Crosshair.USE_ITEM;
			}
			if (!context.player.isSneaking()) {
				return Crosshair.INTERACTABLE;
			}
		}

		return null;
	}
}
