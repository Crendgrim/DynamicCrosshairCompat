package mod.crend.dynamiccrosshair.compat.mixin.create;

import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = FluidTankBlock.class, remap = false)
public interface FluidTankBlockAccessor {
	@Accessor
	boolean getCreative();
}
