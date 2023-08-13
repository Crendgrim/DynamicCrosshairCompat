package mod.crend.dynamiccrosshair.compat.mixin.meadow;

import net.minecraft.block.Block;
import net.satisfyu.meadow.block.WoodenFlowerPotBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(value = WoodenFlowerPotBlock.class, remap = false)
public interface WoodenFlowerPotBlockAccessor {
	@Accessor
	static Map<Block, Block> getWOODEN_CONTENT_TO_POTTED() { throw new AssertionError(); }

	@Invoker
	boolean invokeIsEmpty();

}
