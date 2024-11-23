//? if archon {
package mod.crend.dynamiccrosshair.compat.mixin.archon;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import safro.archon.api.spell.Spell;
import safro.archon.item.WandItem;
import safro.archon.util.ArchonUtil;

import java.util.ArrayList;

@Mixin(value = WandItem.class, remap = false)
public abstract class WandItemMixin implements DynamicCrosshairItem {
	@Shadow public abstract ArrayList<Spell> getSpells(PlayerEntity player);

	@Shadow public abstract @Nullable Spell getCurrentSpell(ItemStack stack, PlayerEntity player);

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (!getSpells(context.getPlayer()).isEmpty()) {
			Spell current = getCurrentSpell(context.getItemStack(), context.getPlayer());
			if (context.getPlayer().isSneaking()) {
				return InteractionType.USE_ITEM;
			}

			if (current != null) {
				if (context.isWithBlock()) {
					if (current.isBlockCasted() && ArchonUtil.canRemoveMana(context.getPlayer(), current.getManaCost())) {
						return InteractionType.USE_ITEM_ON_BLOCK;
					}
				} else {
					if (!current.isBlockCasted() && current.canCast(context.getWorld(), context.getPlayer(), context.getItemStack())) {
						return InteractionType.USE_ITEM_ON_BLOCK;
					}
				}
			}
		}

		return InteractionType.EMPTY;
	}
}
//?}
