package mod.crend.dynamiccrosshair.compat.mixin.ironchest;

import anner.ironchest.items.UpgradeItem;
import anner.ironchest.items.UpgradeTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = UpgradeItem.class, remap = false)
public interface UpgradeItemAccessor {
	@Accessor
	UpgradeTypes getType();
}
