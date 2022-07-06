package mod.crend.dynamiccrosshair.compat.mixin.additionaladditions;

import dqu.additionaladditions.block.CopperPatinaBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = CopperPatinaBlock.class, remap = false)
public interface ICopperPatinaBlockMixin {

	@Invoker
	static boolean invokeIsFullyConnected(BlockState state) {
		throw new AssertionError();
	}

	@Invoker
	static boolean invokeIsNotConnected(BlockState state) {
		throw new AssertionError();
	}
}
