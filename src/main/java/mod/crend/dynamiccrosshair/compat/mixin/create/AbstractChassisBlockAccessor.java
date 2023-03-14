package mod.crend.dynamiccrosshair.compat.mixin.create;

import com.simibubi.create.content.contraptions.components.structureMovement.chassis.AbstractChassisBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = AbstractChassisBlock.class, remap = false)
public interface AbstractChassisBlockAccessor {
	@Invoker
	boolean invokeGlueAllowedOnSide(BlockView world, BlockPos pos, BlockState state, Direction side);
}
