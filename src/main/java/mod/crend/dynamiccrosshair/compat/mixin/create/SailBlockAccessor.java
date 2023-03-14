package mod.crend.dynamiccrosshair.compat.mixin.create;

import com.simibubi.create.content.contraptions.components.structureMovement.bearing.SailBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = SailBlock.class, remap = false)
public interface SailBlockAccessor {
	@Accessor
	static int getPlacementHelperId() {
		throw new RuntimeException();
	}
}
