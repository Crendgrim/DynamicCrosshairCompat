package mod.crend.dynamiccrosshair.compat.immersiveportals;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.HitResult;
import qouteall.imm_ptl.core.block_manipulation.BlockManipulationClient;

public class ApiImplImmersivePortals implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "imm_ptl_core";
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public HitResult overrideHitResult(CrosshairContext context, HitResult hitResult) {
		if (BlockManipulationClient.isPointingToPortal()) {
			return BlockManipulationClient.remoteHitResult;
		}
		return hitResult;
	}

	@Override
	public ClientWorld overrideWorld() {
		return BlockManipulationClient.getRemotePointedWorld();
	}
}
