package mod.crend.dynamiccrosshair.compat.mixin.mcwroofs;

import net.kikoz.mcwroofs.objects.gutters.RainGutter;
import net.minecraft.state.property.BooleanProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = RainGutter.class, remap = false)
public interface RainGutterAccessor {
	@Accessor
	static BooleanProperty getWATER() {
		throw new AssertionError();
	}
}
