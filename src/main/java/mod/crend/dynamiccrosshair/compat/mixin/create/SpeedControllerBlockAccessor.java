package mod.crend.dynamiccrosshair.compat.mixin.create;

import com.simibubi.create.content.contraptions.relays.advanced.SpeedControllerBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = SpeedControllerBlock.class, remap = false)
public interface SpeedControllerBlockAccessor {
	@Accessor
	static int getPlacementHelperId() {
		throw new RuntimeException();
	}
}
