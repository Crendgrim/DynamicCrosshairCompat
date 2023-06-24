package mod.crend.dynamiccrosshair.compat.mixin.create;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = BlazeBurnerBlockEntity.class, remap = false)
public interface BlazeBurnerBlockEntityAccessor {
	@Accessor
	boolean getGoggles();
}
