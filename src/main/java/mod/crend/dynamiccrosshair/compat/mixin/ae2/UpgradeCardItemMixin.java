//? if applied-energistics-2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.api.parts.IPartHost;
import appeng.api.parts.SelectedPart;
import appeng.api.upgrades.IUpgradeInventory;
import appeng.api.upgrades.IUpgradeableObject;
import appeng.items.materials.UpgradeCardItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = UpgradeCardItem.class, remap = false)
public class UpgradeCardItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getPlayer().isSneaking()) {
			IUpgradeInventory upgrades = null;
			BlockEntity be = context.getBlockEntity();
			if (be instanceof IPartHost partHost) {
				SelectedPart sp = partHost.selectPartWorld(context.getHitResult().getPos());
				if (sp.part instanceof IUpgradeableObject) {
					upgrades = ((IUpgradeableObject)sp.part).getUpgrades();
				}
			} else if (be instanceof IUpgradeableObject) {
				upgrades = ((IUpgradeableObject)be).getUpgrades();
			}
			if (upgrades != null && !upgrades.isEmpty()) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
