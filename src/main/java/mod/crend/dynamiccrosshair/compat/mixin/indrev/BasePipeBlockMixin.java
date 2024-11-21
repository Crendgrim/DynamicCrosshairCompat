//? if industrial-revolution {
package mod.crend.dynamiccrosshair.compat.mixin.indrev;

import me.steven.indrev.IndustrialRevolution;
import me.steven.indrev.blockentities.cables.BasePipeBlockEntity;
import me.steven.indrev.blocks.machine.pipes.BasePipeBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BasePipeBlock.class, remap = false)
public class BasePipeBlockMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();
		if (itemStack.isIn(IndustrialRevolution.INSTANCE.getWRENCH_TAG())) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		if (context.getBlockEntity() instanceof BasePipeBlockEntity pipeBlockEntity
				&& pipeBlockEntity.getCoverState() == null
				&& !context.getPlayer().isSneaking()
				&& item instanceof BlockItem blockItem
				&& !(item instanceof BlockEntityProvider)
				&& blockItem.getBlock().getDefaultState().isFullCube(context.getWorld(), context.getBlockPos())
		) {
			return InteractionType.PLACE_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
