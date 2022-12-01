package mod.crend.dynamiccrosshair.compat.darkutilities;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.darkhax.darkutilities.features.filters.BlockEntityFilter;
import net.darkhax.darkutilities.features.flatblocks.BlockFlatTile;
import net.darkhax.darkutilities.features.tomes.ItemTomeFont;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ApiImplDarkUtilities implements DynamicCrosshairApi {
	private final String NAMESPACE = "darkutils";
	@Override
	public String getNamespace() {
		return NAMESPACE;
	}

	private final Identifier ENCHANTING_TOME = new Identifier(NAMESPACE, "tome_enchanting");
	private final Identifier SHADOW_TOME = new Identifier(NAMESPACE, "tome_shadows");
	private final Identifier VECTOR_PLATE = new Identifier(NAMESPACE, "vector_plate");
	private final Identifier VECTOR_PLATE_FAST = new Identifier(NAMESPACE, "vector_plate_fast");
	private final Identifier VECTOR_PLATE_EXTREME = new Identifier(NAMESPACE, "vector_plate_extreme");
	private final Identifier VECTOR_PLATE_ULTRA = new Identifier(NAMESPACE, "vector_plate_ultra");

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof BlockEntityFilter;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		if (context.getBlock() instanceof BlockEntityFilter) {
			if (context.player.isSneaking()) {
				return Crosshair.INTERACTABLE;
			}
		}
		if (context.getBlock() instanceof BlockFlatTile) {
			var key = context.getBlockState().getRegistryEntry().getKey();
			if (key.isPresent()) {
				Identifier identifier = key.get().getValue();
				if (identifier.equals(VECTOR_PLATE)
						|| identifier.equals(VECTOR_PLATE_FAST)
						|| identifier.equals(VECTOR_PLATE_EXTREME)
						|| identifier.equals(VECTOR_PLATE_ULTRA)) {
					if (context.player.isSneaking()) {
						return Crosshair.INTERACTABLE;
					}
				}
			}
		}

		return null;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		var key = itemStack.getRegistryEntry().getKey();
		if (key.isPresent()) {
			Identifier identifier = key.get().getValue();
			return identifier.equals(ENCHANTING_TOME) || identifier.equals(SHADOW_TOME);
		}
		return false;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();

		var key = itemStack.getRegistryEntry().getKey();
		if (key.isPresent()) {
			Identifier identifier = key.get().getValue();
			if (identifier.equals(ENCHANTING_TOME)) {
				if (context.player.totalExperience >= 20 && !context.isCoolingDown()) {
					return Crosshair.USABLE;
				}
			}
			if (identifier.equals(SHADOW_TOME)) {
				if (context.isWithBlock()) {
					if (context.getBlock() instanceof BlockFlatTile && !context.getBlockState().get(BlockFlatTile.HIDDEN)) {
						return Crosshair.USABLE;
					}
				} else if (context.isWithEntity()) {
					Entity entity = context.getEntity();
					if ((entity instanceof ItemFrameEntity || entity instanceof ArmorStandEntity) && !entity.isInvisible()) {
						return Crosshair.USABLE;
					} else if (entity instanceof LivingEntity livingEntity
							&& !livingEntity.hasStatusEffect(StatusEffects.INVISIBILITY)) {
						return Crosshair.USABLE;
					}
				}
			}
			if (context.getItem() instanceof ItemTomeFont) {
				if (context.isWithBlock()) {
					BlockEntity blockEntity = context.getBlockEntity();
					if (blockEntity instanceof LockableContainerBlockEntity || blockEntity instanceof SignBlockEntity) {
						return Crosshair.USABLE;
					}
				} else if (context.isWithEntity()) {
					if (itemStack.hasCustomName() || context.getEntity().hasCustomName()) {
						return Crosshair.USABLE;
					}
				}
			}
		}

		return null;
	}
}
