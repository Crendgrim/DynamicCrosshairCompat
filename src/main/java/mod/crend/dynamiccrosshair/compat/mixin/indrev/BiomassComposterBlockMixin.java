//? if industrial-revolution {
package mod.crend.dynamiccrosshair.compat.mixin.indrev;

import me.steven.indrev.blockentities.farms.BiomassComposterBlockEntity;
import me.steven.indrev.blocks.misc.BiomassComposterBlock;
import me.steven.indrev.registry.IRBlockRegistry;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.fabric.CrosshairFluidContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BiomassComposterBlock.class, remap = false)
public class BiomassComposterBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof BiomassComposterBlockEntity blockEntity) {
			if (CrosshairFluidContext.canInteractWithFluidStorage(context, blockEntity.getFluidInv())) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
			ItemStack itemStack = context.getItemStack();
			if (itemStack.isEmpty() || ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.containsKey(itemStack.getItem())) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			} else if (context.getBlockState().get(BiomassComposterBlock.Companion.getCLOSED())) {
				return InteractionType.INTERACT_WITH_BLOCK;
			} else if (itemStack.isOf(IRBlockRegistry.INSTANCE.getPLANKS().asItem())) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
