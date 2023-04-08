package mod.crend.dynamiccrosshair.compat.mixin.bewitchment;

import moriyashiine.bewitchment.common.block.SaltLineBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = SaltLineBlock.class, remap = false)
public interface SaltLineBlockAccessor {
	@Invoker
	static boolean invokeIsFullyConnected(BlockState state) { throw new AssertionError(); }

	@Invoker
	static boolean invokeIsNotConnected(BlockState state) { throw new AssertionError(); }

	@Invoker
	BlockState invokeGetPlacementState(BlockView world, BlockPos pos);
}
