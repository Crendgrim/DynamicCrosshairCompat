//? if galosphere {
package mod.crend.dynamiccrosshair.compat.mixin.galosphere;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.orcinus.galosphere.entities.Spectre;
import net.orcinus.galosphere.init.GItems;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(value = Spectre.class, remap = false)
public abstract class SpectreMixin implements DynamicCrosshairEntity {

	@Shadow public abstract boolean canBeManipulated();
	@Shadow public abstract @Nullable UUID getManipulatorUUID();

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (canBeManipulated() && (itemStack.isOf(GItems.SPECTRE_BOUND_SPYGLASS) || itemStack.isOf(Items.SPYGLASS))) {
			return InteractionType.USE_ITEM_ON_ENTITY;
		} else if (itemStack.isOf(Items.GLASS_BOTTLE)) {
			return InteractionType.USE_ITEM_ON_ENTITY;
		} else if (itemStack.isOf(GItems.ALLURITE_SHARD) && getManipulatorUUID() == null && !canBeManipulated()) {
			return InteractionType.USE_ITEM_ON_ENTITY;
		}
		return InteractionType.EMPTY;
	}
}
//?}
