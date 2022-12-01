package mod.crend.dynamiccrosshair.compat.mcd;

import chronosacaria.mcdar.Mcdar;
import chronosacaria.mcdar.artefacts.*;
import chronosacaria.mcdar.artefacts.beacon.AbstractBeaconItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ApiImplMCDArtifacts implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Mcdar.MOD_ID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof ArtefactAgilityItem
				|| item instanceof ArtefactQuiverItem
				|| item instanceof BlastFungusItem
				|| item instanceof UpdraftTomeItem
				|| (item instanceof ArtefactStatusInflictingItem && !(item instanceof SatchelOfElementsItem))
				;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof AbstractBeaconItem
				|| item instanceof ArtefactDefenciveItem
				|| item instanceof HarvesterItem
				|| item instanceof LightningRodItem
				|| item instanceof SatchelOfElementsItem
				|| item instanceof ArtefactSummoningItem
				;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		Item item = context.getItem();
		if (context.isCoolingDown()) return null;

		if (item instanceof AbstractBeaconItem beacon) {
			if (beacon.canFire(context.player, context.getItemStack())) {
				return Crosshair.USABLE;
			}
		}
		if (item instanceof HarvesterItem) {
			if (context.player.totalExperience >= 40 || context.player.isCreative()) {
				return Crosshair.USABLE;
			}
		}
		if (item instanceof LightningRodItem || item instanceof SatchelOfElementsItem) {
			if (context.player.totalExperience >= 15 || context.player.isCreative()) {
				return Crosshair.USABLE;
			}
		}
		if (item instanceof ArtefactSummoningItem && context.isWithBlock()) {
			return Crosshair.USABLE;
		}
		if (item instanceof ArtefactDefenciveItem) {
			if (item instanceof TotemOfShieldingItem
					|| item instanceof TotemOfRegenerationItem
					|| item instanceof TotemOfSoulProtectionItem) {
				if (context.isWithBlock()) {
					return Crosshair.USABLE;
				}
			} else if (item instanceof SoulHealerItem) {
				if (context.player.totalExperience >= 20 || context.player.isCreative()) {
					return Crosshair.USABLE;
				}
			} else {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
