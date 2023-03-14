package mod.crend.dynamiccrosshair.compat.mixin.create;

import com.simibubi.create.content.contraptions.components.steam.SteamEngineBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = SteamEngineBlock.class, remap = false)
public interface SteamEngineBlockAccessor {
	@Accessor
	static int getPlacementHelperId() {
		throw new RuntimeException();
	}
}
