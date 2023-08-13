package mod.crend.dynamiccrosshair.compat.spellengine;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.block.BlockState;
import net.spell_engine.SpellEngineMod;
import net.spell_engine.spellbinding.SpellBindingBlock;

public class ApiImplSpellEngine implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return SpellEngineMod.ID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof SpellBindingBlock;
	}
}
