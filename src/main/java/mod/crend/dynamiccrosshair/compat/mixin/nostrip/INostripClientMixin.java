package mod.crend.dynamiccrosshair.compat.mixin.nostrip;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import us.potatoboy.nostrip.client.NostripClient;

@Mixin(value = NostripClient.class, remap = false)
public interface INostripClientMixin {
	@Accessor
	boolean getDoStrip();
}
