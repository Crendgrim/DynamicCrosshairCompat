package mod.crend.dynamiccrosshair.compat.meadow;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.satisfyu.meadow.Meadow;
import net.satisfyu.meadow.item.WoodenBucket;

public class ApiImplMeadowForced implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "meadow:forced";
	}

	@Override
	public String getModId() {
		return Meadow.MOD_ID;
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();
		ItemStack itemStack = context.getItemStack();

		if (entity instanceof CowEntity || entity instanceof SheepEntity || entity instanceof GoatEntity) {
			if (itemStack.isOf(Items.BUCKET) || itemStack.getItem() instanceof WoodenBucket) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
