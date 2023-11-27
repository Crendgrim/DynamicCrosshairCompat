package mod.crend.dynamiccrosshair.compat.zenith;

import dev.shadowsoffire.apotheosis.Apotheosis;
import dev.shadowsoffire.apotheosis.ench.library.EnchLibraryBlock;
import dev.shadowsoffire.apotheosis.ench.objects.TomeItem;
import dev.shadowsoffire.apotheosis.garden.EnderLeadItem;
import dev.shadowsoffire.apotheosis.potion.PotionCharmItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class ApiImplZenith implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Apotheosis.MODID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof TomeItem
				|| item instanceof PotionCharmItem;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof EnderLeadItem;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof EnderLeadItem) {
			NbtCompound tag = itemStack.getOrCreateSubNbt("entity_data");
			if (!tag.isEmpty()) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof EnchLibraryBlock;
	}
}
