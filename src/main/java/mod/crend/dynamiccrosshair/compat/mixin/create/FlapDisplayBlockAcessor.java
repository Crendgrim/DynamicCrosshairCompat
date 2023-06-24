package mod.crend.dynamiccrosshair.compat.mixin.create;

import com.simibubi.create.content.trains.display.FlapDisplayBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = FlapDisplayBlock.class, remap = false)
public interface FlapDisplayBlockAcessor {
	@Accessor
	static int getPlacementHelperId() {
		throw new RuntimeException();
	}
}
