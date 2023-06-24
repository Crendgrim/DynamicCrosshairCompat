package mod.crend.dynamiccrosshair.compat.mixin.create;

import com.simibubi.create.content.decoration.girder.GirderBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = GirderBlock.class, remap = false)
public interface GirderBlockAccessor {
	@Accessor
	static int getPlacementHelperId() {
		throw new RuntimeException();
	}
}
