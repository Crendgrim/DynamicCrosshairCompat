package mod.crend.dynamiccrosshair.compat.mixin.pfm;

import com.unlikepaladin.pfm.blocks.BasicBathtubBlock;
import com.unlikepaladin.pfm.blocks.behavior.BathtubBehavior;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = BasicBathtubBlock.class, remap = false)
public interface BasicBathtubBlockAccessor {
	@Accessor
	Map<Item, BathtubBehavior> getBehaviorMap();
}
