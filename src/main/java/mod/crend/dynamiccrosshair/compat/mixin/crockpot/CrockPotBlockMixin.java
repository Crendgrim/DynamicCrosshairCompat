//? if a-cute-little-crock-pot {
package mod.crend.dynamiccrosshair.compat.mixin.crockpot;

import com.github.nosrick.crockpot.block.CrockPotBlock;
import com.github.nosrick.crockpot.blockentity.CrockPotBlockEntity;
import com.github.nosrick.crockpot.blockentity.ElectricCrockPotBlockEntity;
import com.github.nosrick.crockpot.config.ConfigManager;
import com.github.nosrick.crockpot.tag.Tags;
import com.github.nosrick.crockpot.util.UUIDUtil;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potions;
//? if <1.20.6 {
import net.minecraft.potion.PotionUtil;
//?} else {
/*import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.component.DataComponentTypes;
 *///?}
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = CrockPotBlock.class, remap = false)
public class CrockPotBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof CrockPotBlockEntity potBlockEntity) {
			ItemStack itemStack = context.getItemStack();
			if (itemStack.isOf(Items.NAME_TAG) && ConfigManager.canLockPots()) {
				if (potBlockEntity.isOwner(UUIDUtil.NO_PLAYER) || potBlockEntity.isOwner(context.getPlayer().getUuid())) {
					return InteractionType.USE_ITEM_ON_BLOCK;
				}
			} else if (potBlockEntity.isOwner(UUIDUtil.NO_PLAYER)
					|| potBlockEntity.isOwner(context.getPlayer().getUuid())
					|| (context.getPlayer().isCreative() && ConfigManager.creativePlayersIgnoreLocks())) {
				if (itemStack.isEmpty()) {
					return InteractionType.INTERACT_WITH_BLOCK;
				} else if (!(potBlockEntity instanceof ElectricCrockPotBlockEntity) && itemStack.isOf(Blocks.REDSTONE_BLOCK.asItem())) {
					return InteractionType.USE_ITEM_ON_BLOCK;
				} else {
					BlockState blockState = context.getBlockState();
					if (blockState.get(CrockPotBlock.HAS_LIQUID)) {
						if (blockState.get(CrockPotBlock.HAS_LIQUID) && potBlockEntity.canBoil()) {
							if (itemStack.isOf(Items.BOWL)) {
								return InteractionType.FILL_ITEM_FROM_BLOCK;
							} else if (potBlockEntity.canAddFood(itemStack)) {
								return InteractionType.PLACE_ITEM_ON_BLOCK;
							}
						}
					} else {
						if (itemStack.isIn(Tags.CONSUMABLE_WATER_SOURCES_ITEMS)
								|| itemStack.isIn(Tags.INFINITE_WATER_SOURCES_ITEMS)) {
							return InteractionType.FILL_BLOCK_FROM_ITEM;
						} else if (ConfigManager.canFillWithWaterBottle()
								&& itemStack.getItem() instanceof PotionItem
								//? if <1.20.6 {
								&& PotionUtil.getPotion(itemStack) == Potions.WATER
								//?} else {
								/*&& ((RegistryEntry<?>) itemStack.getItem().getComponents().get(DataComponentTypes.POTION_CONTENTS).potion().get()).value() == Potions.WATER.value()
								*///?}
						) {
							return InteractionType.FILL_BLOCK_FROM_ITEM;
						}
					}
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
