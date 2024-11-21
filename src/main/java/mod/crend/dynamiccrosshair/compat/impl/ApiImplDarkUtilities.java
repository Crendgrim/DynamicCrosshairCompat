package mod.crend.dynamiccrosshair.compat.impl;

import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
//? if dark-utilities {
import net.darkhax.darkutilities.features.flatblocks.BlockFlatTile;
import net.darkhax.darkutilities.features.tomes.ItemTomeFont;
//?}
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

	//? if dark-utilities {

	private final Identifier ENCHANTING_TOME = new Identifier(NAMESPACE, "tome_enchanting");
	private final Identifier SHADOW_TOME = new Identifier(NAMESPACE, "tome_shadows");

	@Override
	public boolean isUsable(ItemStack itemStack) {
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
				if (context.getPlayer().totalExperience >= 20 && !context.isCoolingDown()) {
					return new Crosshair(InteractionType.USE_ITEM);
				}
			}
			if (identifier.equals(SHADOW_TOME)) {
				if (context.isWithBlock()) {
					if (context.getBlock() instanceof BlockFlatTile && !context.getBlockState().get(BlockFlatTile.HIDDEN)) {
						return new Crosshair(InteractionType.USE_ITEM_ON_BLOCK);
					}
				} else if (context.isWithEntity()) {
					Entity entity = context.getEntity();
					if ((entity instanceof ItemFrameEntity || entity instanceof ArmorStandEntity) && !entity.isInvisible()) {
						return new Crosshair(InteractionType.USE_ITEM_ON_ENTITY);
					} else if (entity instanceof LivingEntity livingEntity
							&& !livingEntity.hasStatusEffect(StatusEffects.INVISIBILITY)) {
						return new Crosshair(InteractionType.USE_ITEM_ON_ENTITY);
					}
				}
			}
			if (context.getItem() instanceof ItemTomeFont) {
				if (context.isWithBlock()) {
					BlockEntity blockEntity = context.getBlockEntity();
					if (blockEntity instanceof LockableContainerBlockEntity || blockEntity instanceof SignBlockEntity) {
						return new Crosshair(InteractionType.USE_ITEM_ON_BLOCK);
					}
				} else if (context.isWithEntity()) {
					if (itemStack.hasCustomName() || context.getEntity().hasCustomName()) {
						return new Crosshair(InteractionType.USE_ITEM_ON_ENTITY);
					}
				}
			}
		}

		return null;
	}
	//?}
}
