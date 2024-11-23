//? if extended-drawers {
package mod.crend.dynamiccrosshair.compat.mixin.extended_drawers;

//? if =1.20.1 {
import io.github.mattidragon.extendeddrawers.block.base.StorageDrawerBlock;
//?} else
/*import io.github.mattidragon.extendeddrawers.block.DrawerBlock;*/
import io.github.mattidragon.extendeddrawers.block.entity.DrawerBlockEntity;
import io.github.mattidragon.extendeddrawers.block.entity.StorageDrawerBlockEntity;
import io.github.mattidragon.extendeddrawers.item.LimiterItem;
import io.github.mattidragon.extendeddrawers.item.UpgradeItem;
import io.github.mattidragon.extendeddrawers.misc.DrawerRaycastUtil;
import io.github.mattidragon.extendeddrawers.registry.ModItems;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = /*? if =1.20.1 {*/StorageDrawerBlock/*?} else {*//*DrawerBlock*//*?}*/.class, remap = false)
@SuppressWarnings("UnstableApiUsage")
public abstract class StorageDrawerBlockMixin <T extends StorageDrawerBlockEntity> implements DynamicCrosshairBlock {
	@Shadow public abstract boolean isFront(BlockState state, Direction direction);

	//? if =1.20.1 {
	@Shadow public abstract int getSlotIndex(T t, Vec2f vec2f);

	@Shadow public abstract StorageView<ItemVariant> getSlot(T t, int i);
	//?} else {
	/*@Shadow protected abstract int getSlot(Vec2f par1);
	*///?}

	@Unique
	private int dynamiccrosshair$getClickedSlot(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Vec2f clickedPosition = DrawerRaycastUtil.calculateFaceLocation(
				context.getBlockPos(),
				context.getHitResult().getPos(),
				context.getBlockHitSide(),
				//? if =1.20.1 {
				blockState.get(StorageDrawerBlock.FACING),
				blockState.get(StorageDrawerBlock.FACE)
				//?} else
				/*blockState.get(DrawerBlock.FACING)*/
		);
		//? if =1.20.1 {
		@SuppressWarnings("unchecked")
		T drawer = (T) context.getBlockEntity();
		return this.getSlotIndex(drawer, clickedPosition);
		//?} else
		/*return getSlot(clickedPosition);*/
	}
	@Unique
	@SuppressWarnings("unchecked")
	private StorageView<ItemVariant> dynamiccrosshair$getClickedSlotContents(CrosshairContext context) {
		//? if =1.20.1 {
		return getSlot((T) context.getBlockEntity(), dynamiccrosshair$getClickedSlot(context));
		//?} else
		/*return ((DrawerBlockEntity) context.getBlockEntity()).storages[dynamiccrosshair$getClickedSlot(context)];*/
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (this.isFront(context.getBlockState(), context.getBlockHitSide()) && context.getPlayer().canModifyBlocks()) {
			ItemStack handItemStack = context.getItemStack();
			if (context.getPlayer().isSneaking()) {
				Item item = handItemStack.getItem();
				if (item instanceof UpgradeItem || item instanceof LimiterItem || item == ModItems.LOCK) {
					return InteractionType.USE_ITEM_ON_BLOCK;
				}
			} else {
				StorageView<ItemVariant> storage = dynamiccrosshair$getClickedSlotContents(context);
				ItemVariant storedItem = storage.getResource();
				if (handItemStack.isEmpty()) {
					if (!storage.isResourceBlank()) {
						return InteractionType.TAKE_ITEM_FROM_BLOCK;
					}
				} else {
					if (storedItem.isBlank() || storedItem.getItem() == handItemStack.getItem()) {
						return InteractionType.PLACE_ITEM_ON_BLOCK;
					}
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
