package mod.crend.dynamiccrosshair.compat.mixin.bewitchment;

import moriyashiine.bewitchment.common.entity.living.util.BWTameableEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = BWTameableEntity.class, remap = false)
public interface BWTameableEntityAccessor {
	@Invoker
	boolean invokeIsTamingItem(ItemStack itemStack);
}
