package mod.crend.dynamiccrosshair.compat.mixin.create;

import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerTileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = BlazeBurnerTileEntity.class, remap = false)
public interface BlazeBurnerTileEntityAccessor {
	@Accessor
	boolean getGoggles();
}
