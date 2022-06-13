package mod.crend.dynamiccrosshair.compat.mixin.mcwroofs;

import net.kikoz.mcwroofs.objects.gutters.GutterTall;
import net.minecraft.state.property.BooleanProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = GutterTall.class, remap = false)
public interface IGutterTallMixin {
	@Accessor
	static BooleanProperty getWATER() {
		throw new AssertionError();
	}
}
