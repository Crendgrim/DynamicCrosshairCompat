package mod.crend.dynamiccrosshair.compat.mixin.decorative_blocks;

import lilypuree.decorative_blocks.blocks.SeatBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = SeatBlock.class, remap = false)
public interface ISeatBlockMixin {
	@Invoker
	static boolean invokeIsPlayerInRange(PlayerEntity player, BlockPos pos) {
		throw new AssertionError();
	}
}
