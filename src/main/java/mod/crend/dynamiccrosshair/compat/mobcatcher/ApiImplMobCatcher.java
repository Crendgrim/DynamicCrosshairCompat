package mod.crend.dynamiccrosshair.compat.mobcatcher;

import com.kwpugh.mob_catcher.MobCatcher;
import com.kwpugh.mob_catcher.init.ItemInit;
import com.kwpugh.mob_catcher.init.TagInit;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.item.ItemStack;

public class ApiImplMobCatcher implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return MobCatcher.MOD_ID;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.isOf(ItemInit.MOB_CATCHER) || itemStack.isOf(ItemInit.MOB_CATCHER_HOSTILE);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();

		if (context.includeUsableItem()
				&& (itemStack.isOf(ItemInit.MOB_CATCHER) || itemStack.isOf(ItemInit.MOB_CATCHER_HOSTILE))) {
			if (context.isWithBlock() && itemStack.hasGlint()) {
				return Crosshair.USABLE;
			}
			if (context.isWithEntity() && !itemStack.hasGlint()) {
				Entity entity = context.getEntity();

				if (itemStack.isOf(ItemInit.MOB_CATCHER)) {
					if (MobCatcher.CONFIG.SETTINGS.enableDatapackMobTypes) {
						if (entity.getType().isIn(TagInit.MOBS_PASSIVE)) {
							return Crosshair.USABLE;
						}
					} else {
						if (entity instanceof AnimalEntity
								|| entity instanceof MerchantEntity
								|| entity instanceof GolemEntity
								|| entity instanceof SquidEntity
								|| entity instanceof FishEntity
								|| entity instanceof DolphinEntity
								|| entity instanceof AllayEntity
								|| entity instanceof BatEntity
						) {
							return Crosshair.USABLE;
						}
					}
				} else { // hostile mob catcher
					if (MobCatcher.CONFIG.SETTINGS.enableDatapackMobTypes) {
						if (entity.getType().isIn(TagInit.MOBS_HOSTILE)) {
							return Crosshair.USABLE;
						}
					} else {
						if ((entity instanceof HostileEntity && !(entity instanceof WitherEntity))
								|| entity instanceof SlimeEntity
								|| entity instanceof GhastEntity
								|| entity instanceof PhantomEntity) {
							return Crosshair.USABLE;
						}
					}
				}
			}
		}

		return null;
	}
}
