//? if youre-in-grave-danger {
package mod.crend.dynamiccrosshair.compat.mixin.yigd;

import com.b1n_ry.yigd.block.GraveBlock;
import com.b1n_ry.yigd.block.entity.GraveBlockEntity;
import com.b1n_ry.yigd.components.GraveComponent;
import com.b1n_ry.yigd.config.YigdConfig;
import com.b1n_ry.yigd.data.DeathInfoManager;
import com.b1n_ry.yigd.data.GraveStatus;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import java.util.Optional;
import java.util.UUID;

@Mixin(value = GraveBlock.class, remap = false)
public class GraveBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
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
						if (!context.getPlayer().isSneaking() && stack.isOf(Items.PLAYER_HEAD) && nbt != null && nbt.get("SkullOwner") != null) {
							return InteractionType.USE_ITEM_ON_BLOCK;
						}
						return InteractionType.EMPTY;
					}
				}
				if (graveComponent.getStatus() != GraveStatus.CLAIMED || config.graveConfig.persistentGraves.enabled) {
					return InteractionType.INTERACT_WITH_BLOCK;
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
