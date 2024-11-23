//? if friends-and-foes {
package mod.crend.dynamiccrosshair.compat.mixin.friendsandfoes;

import com.faboslav.friendsandfoes.common.entity.MaulerEntity;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = MaulerEntity.class, remap = false)
public abstract class MaulerEntityMixin implements DynamicCrosshairEntity {
	@Shadow public abstract int getStoredExperiencePoints();

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();
		if (itemStack.hasEnchantments() || item == Items.ENCHANTED_BOOK) {
			if (getStoredExperiencePoints() < 1395) {
				return InteractionType.FILL_ENTITY_FROM_ITEM;
			}
		} else if (item == Items.GLASS_BOTTLE) {
			if (getStoredExperiencePoints() > 7) {
				return InteractionType.FILL_ITEM_FROM_ENTITY;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
