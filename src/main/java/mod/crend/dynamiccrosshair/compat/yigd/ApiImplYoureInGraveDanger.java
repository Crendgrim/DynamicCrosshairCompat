package mod.crend.dynamiccrosshair.compat.yigd;

import com.b1n_ry.yigd.block.GraveBlock;
import com.b1n_ry.yigd.block.entity.GraveBlockEntity;
import com.b1n_ry.yigd.components.GraveComponent;
import com.b1n_ry.yigd.config.YigdConfig;
import com.b1n_ry.yigd.data.DeathInfoManager;
import com.b1n_ry.yigd.data.GraveStatus;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;

import java.util.Optional;
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
			YigdConfig config = YigdConfig.getConfig();
			if (config.graveConfig.retrieveMethods.onClick) {
				GraveComponent graveComponent = grave.getComponent();
				if (graveComponent == null) {
					UUID graveId = grave.getGraveId();
					if (graveId != null) {
						Optional<GraveComponent> component = DeathInfoManager.INSTANCE.getGrave(graveId);
						if (component.isPresent()) {
							graveComponent = component.get();
						}
					}
					if (graveComponent == null) {
						ItemStack stack = context.getItemStack();
						NbtCompound nbt = stack.getNbt();
						if (!context.player.isSneaking() && stack.isOf(Items.PLAYER_HEAD) && nbt != null && nbt.get("SkullOwner") != null) {
							return Crosshair.USABLE;
						}
						return null;
					}
				}
				if (graveComponent.getStatus() != GraveStatus.CLAIMED || config.graveConfig.persistentGraves.enabled) {
					return Crosshair.INTERACTABLE;
				}
			}
		}
		return null;
	}
}
