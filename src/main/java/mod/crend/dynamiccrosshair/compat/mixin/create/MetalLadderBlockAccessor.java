package mod.crend.dynamiccrosshair.compat.mixin.create;

import com.simibubi.create.content.curiosities.deco.MetalLadderBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = MetalLadderBlock.class, remap = false)
public interface MetalLadderBlockAccessor {
	@Accessor
	static int getPlacementHelperId() {
		throw new RuntimeException();
	}
}
