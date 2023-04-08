package mod.crend.dynamiccrosshair.compat.mixin.bygonenether;

import com.izofar.bygonenether.entity.WarpedEnderMan;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = WarpedEnderMan.class, remap = false)
public interface WarpedEnderManAccessor {
	@Invoker
	boolean invokeIsReadyForShearing();
}
