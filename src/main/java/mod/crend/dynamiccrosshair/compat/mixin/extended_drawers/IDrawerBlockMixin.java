package mod.crend.dynamiccrosshair.compat.mixin.extended_drawers;

import io.github.mattidragon.extendeddrawers.block.DrawerBlock;
import net.minecraft.util.math.Vec2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = DrawerBlock.class, remap = false)
public interface IDrawerBlockMixin {
	@Invoker
	int invokeGetSlot(Vec2f internalPos);
}
