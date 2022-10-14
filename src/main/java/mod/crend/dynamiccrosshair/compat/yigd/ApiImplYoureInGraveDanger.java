package mod.crend.dynamiccrosshair.compat.yigd;

import com.b1n_ry.yigd.block.GraveBlock;
import com.b1n_ry.yigd.block.entity.GraveBlockEntity;
import com.b1n_ry.yigd.config.RetrievalTypeConfig;
import com.b1n_ry.yigd.config.YigdConfig;
import com.b1n_ry.yigd.core.DeadPlayerData;
import com.b1n_ry.yigd.core.DeathInfoManager;
import com.mojang.authlib.GameProfile;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class ApiImplYoureInGraveDanger implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "yigd";
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
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
					return Crosshair.USE_ITEM;
				}

			}
		}
		return null;
	}
}
