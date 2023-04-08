package mod.crend.dynamiccrosshair.compat.xpstorage;

import com.notker.xp_storage.blocks.StorageBlock;
import com.notker.xp_storage.blocks.StorageBlockEntity;
import com.notker.xp_storage.items.Xp_removerItem;
import com.notker.xp_storage.regestry.ModFluids;
import com.notker.xp_storage.regestry.ModItems;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Util;

import java.util.Objects;

public class XpStorageHandler {
	public static Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (block instanceof StorageBlock && context.getBlockEntity() instanceof StorageBlockEntity tile) {
			boolean tileIsLocked = !tile.player_uuid.equals(Util.NIL_UUID);
			boolean isTileOwner = tile.player_uuid.equals(context.player.getUuid());
			if (itemStack.isOf(ModItems.INSPECTOR)) {
				return Crosshair.INTERACTABLE;
			}

			if (tileIsLocked && !isTileOwner && !context.player.isCreative()) {
				return null;
			}

			if (itemStack.isOf(ModItems.KEY) && (isTileOwner || context.player.isCreative())) {
				return Crosshair.USABLE;
			}

			if (itemStack.isOf(Items.REDSTONE_TORCH)
					|| itemStack.isOf(ModItems.XP_BERRIES)
					|| itemStack.isOf(Items.SCULK)
					|| itemStack.isOf(Items.GLASS_BOTTLE)
					|| itemStack.isOf(Items.EXPERIENCE_BOTTLE)
					|| itemStack.isOf(Items.BUCKET)
					|| itemStack.isOf(ModFluids.XP_BUCKET)
			) {
				return Crosshair.USABLE;
			}

			if (itemStack.isOf(ModItems.LOCK)) {
				if (!tileIsLocked) {
					return Crosshair.USABLE;
				}
			}

			if (itemStack.isOf(ModItems.XP_REMOVER)) {
				if (itemStack.hasNbt() && Objects.requireNonNull(itemStack.getNbt()).getBoolean(Xp_removerItem.tagId)) {
					return Crosshair.INTERACTABLE;
				}
				return Crosshair.USABLE;
			}

			if (itemStack.isDamaged() && EnchantmentHelper.getLevel(Enchantments.MENDING, itemStack) > 0) {
				return Crosshair.INTERACTABLE;
			}
		}

		return null;
	}
}
