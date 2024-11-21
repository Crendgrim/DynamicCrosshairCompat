//? if ironchestsfabric {
package mod.crend.dynamiccrosshair.compat.mixin.ironchest;

import anner.ironchest.blocks.ChestTypes;
import anner.ironchest.items.UpgradeItem;
import anner.ironchest.items.UpgradeTypes;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.ChestBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = UpgradeItem.class, remap = false)
public class UpgradeItemMixin implements DynamicCrosshairBlock {
	@Shadow UpgradeTypes type;

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock()) {
			Block block = context.getBlock();
			if (type.canUpgrade(ChestTypes.WOOD)) {
				if (block instanceof ChestBlock) {
					return InteractionType.USE_ITEM_ON_BLOCK;
				}
			} else if (block.getDefaultState() == ChestTypes.get(type.source).getDefaultState()
					&& context.getBlockEntity() instanceof ChestBlockEntity chest
					&& ChestBlockEntity.getPlayersLookingInChestCount(context.getWorld(), context.getBlockPos()) == 0
					&& chest.canPlayerUse(context.getPlayer())
			) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
