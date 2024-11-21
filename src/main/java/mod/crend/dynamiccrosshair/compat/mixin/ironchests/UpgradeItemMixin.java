//? if ironchestsrestocked {
package mod.crend.dynamiccrosshair.compat.mixin.ironchests;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import tech.thatgravyboat.ironchests.api.chesttype.ChestUpgradeType;
import tech.thatgravyboat.ironchests.common.blocks.GenericChestBlockEntity;
import tech.thatgravyboat.ironchests.common.items.UpgradeItem;

@Mixin(value = UpgradeItem.class, remap = false)
public class UpgradeItemMixin implements DynamicCrosshairItem {
	@Shadow @Final public ChestUpgradeType type;

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockEntity blockEntity = context.getBlockEntity();
		if (blockEntity instanceof GenericChestBlockEntity chestBlockEntity) {
			if (chestBlockEntity.viewers() == 0
					&& chestBlockEntity.getChestType().equals(type.from())
					&& chestBlockEntity.checkUnlocked(context.getPlayer())
			) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		} else if (blockEntity instanceof ChestBlockEntity chestBlockEntity){
			if (ChestBlockEntity.getPlayersLookingInChestCount(context.getWorld(), context.getBlockPos()) == 0
					&& context.getBlockState().isIn(UpgradeItem.REPLACEABLE_CHEST_TAG)
					&& type.from() == null
					&& chestBlockEntity.checkUnlocked(context.getPlayer())
			) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
