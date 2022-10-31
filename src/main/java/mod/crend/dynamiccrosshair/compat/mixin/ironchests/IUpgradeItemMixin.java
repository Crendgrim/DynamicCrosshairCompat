package mod.crend.dynamiccrosshair.compat.mixin.ironchests;

import io.github.cyberanner.ironchests.items.UpgradeItem;
import io.github.cyberanner.ironchests.items.UpgradeTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = UpgradeItem.class, remap = false)
public interface IUpgradeItemMixin {
	@Accessor
	UpgradeTypes getType();
}
