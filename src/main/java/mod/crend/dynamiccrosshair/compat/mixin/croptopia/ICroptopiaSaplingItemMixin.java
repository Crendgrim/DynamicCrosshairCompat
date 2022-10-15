package mod.crend.dynamiccrosshair.compat.mixin.croptopia;

import com.epherical.croptopia.items.CroptopiaSaplingItem;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = CroptopiaSaplingItem.class, remap = false)
public interface ICroptopiaSaplingItemMixin {

	@Accessor
	Block getVanillaLeafBlock();
}
