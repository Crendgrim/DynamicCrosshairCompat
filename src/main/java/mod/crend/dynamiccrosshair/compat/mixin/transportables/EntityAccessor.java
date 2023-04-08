package mod.crend.dynamiccrosshair.compat.mixin.transportables;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityAccessor {
	@Invoker
	boolean invokeCanStartRiding(Entity entity);

	@Invoker
	boolean invokeCanAddPassenger(Entity passenger);
}
