//? if paladins-furniture {
package mod.crend.dynamiccrosshair.compat.mixin.pfm;

import com.unlikepaladin.pfm.blocks.AbstractSittableBlock;
import com.unlikepaladin.pfm.entity.ChairEntity;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import java.util.List;

@Mixin(value = AbstractSittableBlock.class, remap = false)
public class AbstractSittableBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (!context.getPlayer().isSneaking()) {
			List<ChairEntity> active = context.getWorld().getEntitiesByClass(ChairEntity.class, new Box(context.getBlockPos()), Entity::hasPlayerRider);
			if (active.isEmpty()) {
				return InteractionType.MOUNT_BLOCK;
			}
		}
		return null;
	}
}
//?}
