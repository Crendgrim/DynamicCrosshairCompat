package mod.crend.dynamiccrosshair.compat.autoworkstations;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import uk.co.cablepost.autoworkstations.AutoWorkstations;
import uk.co.cablepost.autoworkstations.auto_anvil.AutoAnvilBlock;
import uk.co.cablepost.autoworkstations.auto_brewing_stand.AutoBrewingStandBlock;
import uk.co.cablepost.autoworkstations.auto_crafting_table.AutoCraftingTableBlock;
import uk.co.cablepost.autoworkstations.auto_enchanting_table.AutoEnchantingTableBlock;
import uk.co.cablepost.autoworkstations.auto_experience_orb_emitter.AutoExperienceOrbEmitterBlock;
import uk.co.cablepost.autoworkstations.auto_experience_orb_vacuum.AutoExperienceOrbVacuumBlock;
import uk.co.cablepost.autoworkstations.auto_furnace.AutoFurnaceBlock;

public class ApiImplAutoWorkstations implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return AutoWorkstations.MOD_ID;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		Block block = context.getBlock();
		if (block instanceof AutoAnvilBlock
				|| block instanceof AutoBrewingStandBlock
				|| block instanceof AutoCraftingTableBlock
				|| block instanceof AutoEnchantingTableBlock
				|| block instanceof AutoExperienceOrbEmitterBlock
				|| block instanceof AutoExperienceOrbVacuumBlock
				|| block instanceof AutoFurnaceBlock
		) {
			return Crosshair.INTERACTABLE;
		}
		return null;
	}
}
