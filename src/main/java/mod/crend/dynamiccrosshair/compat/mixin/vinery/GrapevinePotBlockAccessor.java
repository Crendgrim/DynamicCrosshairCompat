package mod.crend.dynamiccrosshair.compat.mixin.vinery;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.IntProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import satisfyu.vinery.block.GrapevinePotBlock;

@Mixin(value = GrapevinePotBlock.class, remap = false)
public interface GrapevinePotBlockAccessor {
	@Accessor
	static IntProperty getSTAGE() { throw new AssertionError(); }
	@Accessor
	static IntProperty getSTORAGE() { throw new AssertionError(); }

	@Invoker
	boolean invokeCanTakeWine(BlockState state, ItemStack stackInHand);
}
