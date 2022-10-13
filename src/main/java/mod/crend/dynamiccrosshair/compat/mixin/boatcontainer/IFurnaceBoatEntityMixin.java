package mod.crend.dynamiccrosshair.compat.mixin.boatcontainer;

import de.kxmischesdomi.boatcontainer.common.entity.FurnaceBoatEntity;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = FurnaceBoatEntity.class, remap = false)
public interface IFurnaceBoatEntityMixin {
	@Accessor
	short getFuel();

	@Accessor
	static Ingredient getINGREDIENT() {
		throw new AssertionError();
	}

}
