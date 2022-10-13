package mod.crend.dynamiccrosshair.compat.bettercombat;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.component.CrosshairContextChange;
import net.bettercombat.BetterCombat;
import net.bettercombat.api.MinecraftClient_BetterCombat;
import net.bettercombat.api.WeaponAttributes;
import net.bettercombat.logic.WeaponRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;

public class ApiImplBetterCombat implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return BetterCombat.MODID;
	}

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
	public Crosshair checkEntity(CrosshairContext context) {
		WeaponAttributes attributes = WeaponRegistry.getAttributes(context.getItemStack());
		if (attributes != null) {
			if (!((MinecraftClient_BetterCombat) (MinecraftClient.getInstance())).hasTargetsInRange()) {
				throw new CrosshairContextChange(BlockHitResult.createMissed(context.hitResult.getPos(), Direction.UP, null));
			}
		}
		return null;
	}

	@Override
	public Crosshair checkMeleeWeapon(CrosshairContext context) {
		WeaponAttributes attributes = WeaponRegistry.getAttributes(context.getItemStack());
		if (attributes != null && context.isMainHand()) {
			return Crosshair.MELEE_WEAPON;
		}

		return null;
	}
}
