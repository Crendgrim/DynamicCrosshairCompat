package mod.crend.dynamiccrosshair.compat.impl;

import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.exception.CrosshairContextChange;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;

//? if better-combat {
import net.bettercombat.api.MinecraftClient_BetterCombat;
import net.bettercombat.api.WeaponAttributes;
import net.bettercombat.logic.WeaponRegistry;
//?}

public class ApiImplBetterCombat implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "bettercombat";
	}

	//? if better-combat {
	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public HitResult overrideHitResult(CrosshairContext context, HitResult hitResult) {
		if (hitResult.getType() == HitResult.Type.MISS) {
			WeaponAttributes attributes = WeaponRegistry.getAttributes(context.getItemStack());
			if (attributes != null && attributes.attackRange() > 3) {
				// Vanilla attack range missed. Try to find an entity within attacking range.
				EntityHitResult entityHitResult = context.raycastForEntity(attributes.attackRange());
				if (entityHitResult != null) {
					return entityHitResult;
				}
			}
		}

		return hitResult;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		WeaponAttributes attributes = WeaponRegistry.getAttributes(context.getItemStack());
		if (attributes != null) {
			if (!((MinecraftClient_BetterCombat) (MinecraftClient.getInstance())).hasTargetsInReach()) {
				throw new CrosshairContextChange(BlockHitResult.createMissed(context.getHitResult().getPos(), Direction.UP, null));
			}
		}
		return null;
	}

	@Override
	public boolean isMeleeWeapon(ItemStack itemStack) {
		WeaponAttributes attributes = WeaponRegistry.getAttributes(itemStack);
		return (attributes != null);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (context.includeMeleeWeapon()) {
			WeaponAttributes attributes = WeaponRegistry.getAttributes(context.getItemStack());
			if (attributes != null && context.isMainHand()) {
				return new Crosshair(InteractionType.MELEE_WEAPON);
			}
		}

		return null;
	}
	//?}
}
