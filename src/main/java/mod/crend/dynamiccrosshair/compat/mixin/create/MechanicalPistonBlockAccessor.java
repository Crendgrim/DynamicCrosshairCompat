package mod.crend.dynamiccrosshair.compat.mixin.create;

import com.simibubi.create.content.contraptions.piston.MechanicalPistonBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = MechanicalPistonBlock.class, remap = false)
public interface MechanicalPistonBlockAccessor {
	@Accessor
	boolean getIsSticky();
}
