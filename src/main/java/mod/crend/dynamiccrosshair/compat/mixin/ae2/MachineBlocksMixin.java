//? if ae2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.block.crafting.MolecularAssemblerBlock;
import appeng.block.crafting.PatternProviderBlock;
import appeng.block.misc.CellWorkbenchBlock;
import appeng.block.misc.ChargerBlock;
import appeng.block.misc.CondenserBlock;
import appeng.block.misc.InscriberBlock;
import appeng.block.misc.InterfaceBlock;
import appeng.block.misc.VibrationChamberBlock;
import appeng.block.networking.WirelessAccessPointBlock;
import appeng.block.qnb.QuantumLinkChamberBlock;
import appeng.block.spatial.SpatialAnchorBlock;
import appeng.block.spatial.SpatialIOPortBlock;
import appeng.block.storage.ChestBlock;
import appeng.block.storage.DriveBlock;
import appeng.block.storage.IOPortBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {
		MolecularAssemblerBlock.class,
		ChestBlock.class,
		DriveBlock.class,
		IOPortBlock.class,
		PatternProviderBlock.class,
		CellWorkbenchBlock.class,
		ChargerBlock.class,
		CondenserBlock.class,
		InscriberBlock.class,
		InterfaceBlock.class,
		VibrationChamberBlock.class,
		WirelessAccessPointBlock.class,
		QuantumLinkChamberBlock.class,
		SpatialAnchorBlock.class,
		SpatialIOPortBlock.class,
})
public class MachineBlocksMixin extends AEBaseEntityBlockMixin {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (!context.getPlayer().isSneaking()) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		return super.dynamiccrosshair$compute(context);
	}
}
//?}
