package mod.crend.dynamiccrosshair.compat.mixin.adorn;

import juuxel.adorn.block.SeatBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = SeatBlock.class, remap = false)
public interface SeatBlockAccessor {
	@Invoker("isSittingEnabled")
	boolean invokeIsSittingEnabled();
}
