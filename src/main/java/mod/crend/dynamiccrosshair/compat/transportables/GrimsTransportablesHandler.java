package mod.crend.dynamiccrosshair.compat.transportables;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.thegrimsey.transportables.TransportablesBlocks;
import net.thegrimsey.transportables.entity.AbstractCarriageEntity;
import net.thegrimsey.transportables.items.LinkerItem;

import java.util.UUID;

public class GrimsTransportablesHandler {
	public static Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();

		if (itemStack.getItem() instanceof LinkerItem) {
			if (context.isWithBlock()) {
				BlockState blockState = context.getBlockState();
				if (context.player.isSneaking()) {
					if (blockState.isOf(TransportablesBlocks.TELESENDER_RAIL)) {
						return Crosshair.USABLE;
					}
				} else if (blockState.getBlock() instanceof AbstractRailBlock) {
					return Crosshair.USABLE;
				}
			} else if (context.isWithEntity()) {
				Entity entity = context.getEntity();
				if (entity instanceof AbstractHorseEntity) {
					NbtCompound tag = itemStack.getNbt();
					if (tag != null && tag.contains("LinkingCarriage")) {
						UUID carriageId = tag.getUuid("LinkingCarriage");
						Entity targetEntity = ((ServerWorld)entity.world).getEntity(carriageId);
						if (targetEntity instanceof AbstractCarriageEntity carriage && carriage.canLinkWith((AbstractHorseEntity)entity)) {
							return Crosshair.USABLE;
						}
					}
				} else if (entity instanceof AbstractCarriageEntity) {
					if (context.player.isSneaking()) {
						return Crosshair.USABLE;
					}
				}
			}
		}

		return null;
	}
}
