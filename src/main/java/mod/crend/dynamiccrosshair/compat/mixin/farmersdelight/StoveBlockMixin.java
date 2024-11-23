//? if farmers-delight-refabricated {
package mod.crend.dynamiccrosshair.compat.mixin.farmersdelight;

//? if <1.21 {
import io.github.fabricators_of_create.porting_lib.tool.ToolAction;
import io.github.fabricators_of_create.porting_lib.tool.ToolActions;
import io.github.fabricators_of_create.porting_lib.tool.addons.ToolActionItem;
import io.github.fabricators_of_create.porting_lib.tool.extensions.VanillaToolActionItem;
import net.minecraft.inventory.SimpleInventory;
import org.spongepowered.asm.mixin.Unique;
//?}
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import vectorwing.farmersdelight.common.block.StoveBlock;
import vectorwing.farmersdelight.common.block.entity.StoveBlockEntity;

@Mixin(value = StoveBlock.class, remap = false)
public class StoveBlockMixin implements DynamicCrosshairBlock {
	//? if <1.21 {
	@Unique
	// From PortingLib; copy so we don't have to depend on it
	public boolean dynamiccrosshair$portingLib_canPerformAction(ItemStack itemStack, ToolAction toolAction) {
		var item = itemStack.getItem();
		if (item instanceof ToolActionItem toolActionItem)
			return toolActionItem.canPerformAction(itemStack, toolAction);
		if (item instanceof VanillaToolActionItem toolActionItem)
			return toolActionItem.port_lib$canPerformAction(itemStack, toolAction);
		return false;
	}
	//?}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockState state = context.getBlockState();
		ItemStack heldStack = context.getItemStack();
		Item heldItem = heldStack.getItem();
		if (state.get(StoveBlock.LIT)) {
			//? if <1.21 {
			if (dynamiccrosshair$portingLib_canPerformAction(heldStack, ToolActions.SHOVEL_DIG)) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
			//?}

			if (heldItem == Items.WATER_BUCKET) {
				return InteractionType.FILL_BLOCK_FROM_ITEM;
			}
		} else {
			if (heldItem instanceof FlintAndSteelItem) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}

			if (heldItem instanceof FireChargeItem) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}

		if (context.getBlockEntity() instanceof StoveBlockEntity stoveEntity) {
			int stoveSlot = stoveEntity.getNextEmptySlot();
			if (stoveSlot < 0 || stoveEntity.isStoveBlockedAbove()) {
				return InteractionType.EMPTY;
			}

			var recipe = stoveEntity.getMatchingRecipe(/*? if <1.21 {*/new SimpleInventory(heldStack), stoveSlot/*?} else {*//*heldStack*//*?}*/);
			if (recipe.isPresent()) {
				return InteractionType.PLACE_ITEM_ON_BLOCK;
			}
		}

		return InteractionType.EMPTY;
	}
}
//?}
