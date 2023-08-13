package mod.crend.dynamiccrosshair.compat.mixin.boatcontainer;

import de.kxmischesdomi.boatcontainer.common.entity.BoatWithBlockEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = BoatWithBlockEntity.class, remap = false)
public interface BoatWithBlockEntityAccessor {
	@Invoker
	boolean invokeCanAddPassenger(Entity passenger);
}
