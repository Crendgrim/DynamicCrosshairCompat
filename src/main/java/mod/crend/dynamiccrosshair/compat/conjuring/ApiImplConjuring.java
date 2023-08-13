package mod.crend.dynamiccrosshair.compat.conjuring;

import com.glisco.conjuring.Conjuring;
import com.glisco.conjuring.blocks.*;
import com.glisco.conjuring.blocks.conjurer.ConjurerBlock;
import com.glisco.conjuring.blocks.gem_tinkerer.GemTinkererBlock;
import com.glisco.conjuring.blocks.gem_tinkerer.GemTinkererBlockEntity;
import com.glisco.conjuring.blocks.soul_weaver.SoulWeaverBlock;
import com.glisco.conjuring.blocks.soul_weaver.SoulWeaverBlockEntity;
import com.glisco.conjuring.blocks.soulfire_forge.SoulfireForgeBlock;
import com.glisco.conjuring.blocks.soulfire_forge.SoulfireForgeBlockEntity;
import com.glisco.conjuring.items.ConjuringFocus;
import com.glisco.conjuring.items.ConjuringItems;
import com.glisco.conjuring.items.ConjuringScepter;
import com.glisco.conjuring.items.SuperiorConjuringScepter;
import com.glisco.conjuring.items.soul_alloy_tools.SoulAlloyTool;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;

public class ApiImplConjuring implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Conjuring.MOD_ID;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof ConjurerBlock
				|| block instanceof GemTinkererBlock
				|| block instanceof SoulWeaverBlock
				|| block instanceof SoulfireForgeBlock
				|| block instanceof BlackstonePedestalBlock
				|| block instanceof SoulFunnelBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		BlockState blockState = context.getBlockState();
		Block block = context.getBlock();

		if (block instanceof ConjurerBlock) {
			if (itemStack.getItem() instanceof ConjuringScepter) {
				return Crosshair.INTERACTABLE;
			}
		}
		if (block instanceof GemTinkererBlock && context.getBlockEntity() instanceof GemTinkererBlockEntity tinkerer) {
			if (context.isMainHand()) {
				if (tinkerer.isRunning()) {
					if (tinkerer.isCraftingComplete() && itemStack.isEmpty()) {
						return Crosshair.INTERACTABLE;
					}
				} else {
					if (itemStack.isEmpty()) {
						return Crosshair.INTERACTABLE;
					}
					return Crosshair.USABLE;
				}
			}
		}
		if (block instanceof SoulWeaverBlock && context.getBlockEntity() instanceof SoulWeaverBlockEntity weaver) {
			if (!weaver.isRunning()) {
				if (itemStack.getItem().equals(ConjuringItems.CONJURATION_ESSENCE) && !weaver.isLit()) {
					return Crosshair.USABLE;
				}
				if (itemStack.getItem() instanceof ConjuringScepter) {
					return Crosshair.USABLE;
				}
				if (weaver.getItem().isEmpty()) {
					if (!itemStack.isEmpty()) {
						return Crosshair.USABLE;
					}
				} else {
					return Crosshair.INTERACTABLE;
				}
			}
		}
		if (block instanceof SoulfireForgeBlock) {
			if (itemStack.isOf(Items.FLINT_AND_STEEL) && !blockState.get(SoulfireForgeBlock.BURNING)) {
				return Crosshair.USABLE;
			}

			return Crosshair.INTERACTABLE;
		}
		if (block instanceof BlackstonePedestalBlock && context.getBlockEntity() instanceof BlackstonePedestalBlockEntity pedestal) {
			if (!pedestal.isActive()) {
				if (pedestal.getItem().isEmpty()) {
					if (!itemStack.isEmpty()) {
						return Crosshair.USABLE;
					}
				}
				else {
					return Crosshair.INTERACTABLE;
				}
			}
		}
		if (block instanceof SoulFunnelBlock) {
			if (itemStack.isEmpty() && context.player.isSneaking()) {
				return Crosshair.INTERACTABLE;
			}
			if (itemStack.isOf(Items.SOUL_SAND) && !blockState.get(SoulFunnelBlock.FILLED)) {
				return Crosshair.USABLE;
			}
			if (itemStack.getItem() instanceof ConjuringScepter) {
				return Crosshair.USABLE;
			}
			if (blockState.get(SoulFunnelBlock.FILLED)) {
				SoulFunnelBlockEntity funnel = (SoulFunnelBlockEntity) context.getBlockEntity();
				if (funnel.getItem().isEmpty()) {
					if (itemStack.getItem() instanceof ConjuringFocus) {
						return Crosshair.USABLE;
					}
				} else if (!funnel.isRitualRunning()) {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		return null;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof SoulAlloyTool
				|| itemStack.isOf(ConjuringItems.CONJURATION_ESSENCE)
				|| itemStack.getItem() instanceof ConjuringScepter;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.isOf(ConjuringItems.ENCHIRIDION);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (!context.includeUsableItem()) return null;

		ItemStack itemStack = context.getItemStack();

		if (itemStack.getItem() instanceof SoulAlloyTool) {
			if (SoulAlloyTool.isSecondaryEnabled(itemStack)) {
				return Crosshair.USABLE;
			}
		}

		if (itemStack.isOf(ConjuringItems.CONJURATION_ESSENCE)) {
			if (context.player.isSneaking() && context.isWithBlock() && context.getBlockState().isIn(BlockTags.BASE_STONE_OVERWORLD)) {
				return Crosshair.USABLE;
			}
		}

		if (itemStack.getItem() instanceof ConjuringScepter && context.player.isSneaking()) {
			boolean isSuperior = (itemStack.getItem() instanceof SuperiorConjuringScepter);
			if (context.isWithBlock()) {
				BlockEntity blockEntity = context.getBlockEntity();
				if (isSuperior && blockEntity instanceof SoulfireForgeBlockEntity forge && forge.isRunning()) {
					return Crosshair.USABLE;
				}
				if (blockEntity instanceof BlackstonePedestalBlockEntity pedestal && !pedestal.isActive()) {
					return Crosshair.USABLE;
				}
				if (blockEntity instanceof RitualCore) {
					return Crosshair.USABLE;
				}
			} else {
				if (ConjuringScepter.isLinking(itemStack)) {
					return Crosshair.USABLE;
				}
			}
		}

		return null;
	}
}
