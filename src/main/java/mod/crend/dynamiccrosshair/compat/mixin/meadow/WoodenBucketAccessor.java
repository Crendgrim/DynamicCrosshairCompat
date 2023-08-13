package mod.crend.dynamiccrosshair.compat.mixin.meadow;

import net.minecraft.fluid.Fluid;
import net.satisfyu.meadow.item.WoodenBucket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = WoodenBucket.class, remap = false)
public interface WoodenBucketAccessor {
	@Accessor
	Fluid getFluid();
}
