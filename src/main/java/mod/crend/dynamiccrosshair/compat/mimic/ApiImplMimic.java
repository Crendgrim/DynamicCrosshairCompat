package mod.crend.dynamiccrosshair.compat.mimic;

import ca.lukegrahamlandry.mimic.Constants;
import ca.lukegrahamlandry.mimic.entities.MimicEntity;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.component.ModifierUse;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.util.registry.Registry;

public class ApiImplMimic implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "mimic";
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		if (context.getEntity() instanceof MimicEntity mimic) {
			if (mimic.isTamed() && context.player.isSneaking()) {
				return Crosshair.INTERACTABLE;
			} else {
				ItemStack stack = context.getItemStack();
				if (stack.getItem() == Registry.ITEM.get(Constants.MIMIC_LOCK_ID) && !mimic.isTamed() && !mimic.isLocked()) {
					return Crosshair.USABLE;
				} else if (stack.getItem() == Registry.ITEM.get(Constants.MIMIC_KEY_ID) && !mimic.isTamed() && !mimic.isLocked()) {
					return Crosshair.USABLE;
				} else if (mimic.isStealth()) {
					Item item = stack.getItem();
					Crosshair crosshair = Crosshair.REGULAR;
					if (item instanceof MiningToolItem tool) {
						if (tool.isSuitableFor(Blocks.CHEST.getDefaultState())) {
							crosshair = Crosshair.CORRECT_TOOL;
						} else {
							crosshair = Crosshair.INCORRECT_TOOL;
						}
					}
					if (context.shouldInteract()) {
						crosshair = crosshair.withModifier(ModifierUse.INTERACTABLE);
					}
					return crosshair.withFlag(Crosshair.Flag.FixedAll);
				}
			}
			return Crosshair.REGULAR;
		}
		return null;
	}
}
