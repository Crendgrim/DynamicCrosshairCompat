package mod.crend.dynamiccrosshair.compat.mixin.create;

import com.simibubi.create.content.logistics.block.mechanicalArm.ArmTileEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ArmTileEntity.class, remap = false)
public interface ArmTileEntityAccessor {
	@Accessor
	ItemStack getHeldItem();
}
