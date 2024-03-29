package mod.crend.dynamiccrosshair.compat.friendsandfoes;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.block.CopperButtonBlock;
import com.faboslav.friendsandfoes.block.OxidizableButtonBlock;
import com.faboslav.friendsandfoes.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.entity.GlareEntity;
import com.faboslav.friendsandfoes.entity.MaulerEntity;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;

public class ApiImplFriendsAndFoes implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return FriendsAndFoes.MOD_ID;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();
		Entity entity = context.getEntity();

		if (entity instanceof CopperGolemEntity copperGolem) {
			if (item == Items.COPPER_INGOT && copperGolem.getHealth() != copperGolem.getMaxHealth()) {
				return Crosshair.USABLE;
			}
			if (item == Items.HONEYCOMB && !copperGolem.isWaxed() && !copperGolem.isOxidized()) {
				return Crosshair.USABLE;
			}
			if (item instanceof AxeItem && (copperGolem.isWaxed() || copperGolem.isDegraded())) {
				return Crosshair.USABLE;
			}
		}
		if (entity instanceof GlareEntity glare) {
			if (item == Items.GLOW_BERRIES) {
				if (!glare.isTamed() || (glare.getHealth() < glare.getMaxHealth() && !glare.isBreedingItem(itemStack))) {
					return Crosshair.USABLE;
				}
			}
			if (glare.isBreedingItem(itemStack)) {
				if (glare.isBaby() || (glare.getBreedingAge() == 0 && glare.canEat())) {
					return Crosshair.USABLE;
				}
			} else if (glare.isOwner(context.player) && !glare.isGrumpy()) {
				return Crosshair.INTERACTABLE;
			}
		}
		if (entity instanceof MaulerEntity mauler && !mauler.hasAngerTime()) {
			if (itemStack.hasEnchantments() || item == Items.ENCHANTED_BOOK) {
				if (mauler.getStoredExperiencePoints() < 1395) {
					return Crosshair.USABLE;
				}
			} else if (item == Items.GLASS_BOTTLE) {
				if (mauler.getStoredExperiencePoints() > 7) {
					return Crosshair.USABLE;
				}
			}
		}

		return null;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Item item = context.getItem();
		if (blockState.getBlock() instanceof CopperButtonBlock) {
			if (blockState.getBlock() instanceof OxidizableButtonBlock oxidizableButtonBlock) {
				if (item instanceof HoneycombItem) {
					return Crosshair.USABLE;
				}
				if (item instanceof AxeItem && oxidizableButtonBlock.getDegradationLevel() != Oxidizable.OxidationLevel.UNAFFECTED) {
					return Crosshair.USABLE;
				}
			} else {
				if (item instanceof AxeItem) {
					return Crosshair.USABLE;
				}
			}
		}
		return null;
	}
}
