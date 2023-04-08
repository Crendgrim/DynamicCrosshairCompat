package mod.crend.dynamiccrosshair.compat.mixin.xps_additions;

import com.notker.xps_additions.items.StaffOfRebark;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Optional;

@Mixin(value = StaffOfRebark.class, remap = false)
public interface StaffOfRebarkAccessor {
	@Invoker
	Optional<BlockState> invokeGetUnStrippedState(BlockState state);
}
