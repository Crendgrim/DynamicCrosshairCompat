//? if mariums-soulslike-weaponry {
package mod.crend.dynamiccrosshair.compat.mixin.soulsweapons;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.item.ItemStack;
//? if =1.20.4 {
/*import net.soulsweaponry.items.SoulReaper;
*///?} else
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.soulsweaponry.items.ISummonAllies;
import net.soulsweaponry.items.SoulHarvestingItem;
import net.soulsweaponry.items.scythe.SoulReaper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = SoulReaper.class, remap = false)
public abstract class SoulReaperMixin /*? if >=1.21 {*//*extends SoulHarvestingItem*//*?}*/ implements DynamicCrosshairItem {
	//? if >=1.21 {
	/*public SoulReaperMixin(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
	}

	@Shadow public abstract boolean isDisabled(ItemStack stack);
	*///?}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		//? if <1.21 {
		if (itemStack.hasNbt() && itemStack.getNbt().contains("kills")) {
			int power = itemStack.getNbt().getInt("kills");
			if (power >= 3) {
				return InteractionType.USE_ITEM;
			}
		}
		//?} else {
		/*if (!this.isDisabled(itemStack) && this.getSouls(itemStack) >= 3) {
			return InteractionType.USE_ITEM;
		}
		*///?}
		return InteractionType.EMPTY;
	}
}
//?}
