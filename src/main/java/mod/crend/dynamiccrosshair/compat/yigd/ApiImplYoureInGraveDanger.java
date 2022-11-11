package mod.crend.dynamiccrosshair.compat.yigd;

import com.b1n_ry.yigd.block.GraveBlock;
import com.b1n_ry.yigd.block.entity.GraveBlockEntity;
import com.b1n_ry.yigd.config.RetrievalTypeConfig;
import com.b1n_ry.yigd.config.YigdConfig;
import com.b1n_ry.yigd.core.DeadPlayerData;
import com.b1n_ry.yigd.core.DeathInfoManager;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.List;
import java.util.UUID;

public class ApiImplYoureInGraveDanger implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "yigd";
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof GraveBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		if (context.getBlock() instanceof GraveBlock && context.getBlockEntity() instanceof GraveBlockEntity grave) {
			RetrievalTypeConfig retrievalType = YigdConfig.getConfig().graveSettings.retrievalType;
			if ((retrievalType == RetrievalTypeConfig.ON_USE || retrievalType == RetrievalTypeConfig.ON_BREAK_OR_USE || retrievalType == null) && grave.getGraveOwner() != null && !grave.isClaimed()) {
				return Crosshair.INTERACTABLE;
			} else if (grave.getGraveOwner() != null && grave.isClaimed()) {
				if (context.isMainHand()) {
					UUID graveId = grave.getGraveId();
					UUID ownerId = grave.getGraveOwner().getId();
					List<DeadPlayerData> graves = DeathInfoManager.INSTANCE.data.get(ownerId);
					if (graves != null) {
						for (DeadPlayerData data : graves) {
							if (graveId.equals(data.id)) {
								return Crosshair.INTERACTABLE;
							}
						}
					}
				}
			} else {
				ItemStack heldItem = context.getItemStack();
				if (heldItem.getItem() == Items.PLAYER_HEAD && heldItem.hasNbt()) {
					return Crosshair.USABLE;
				}

			}
		}
		return null;
	}
}
