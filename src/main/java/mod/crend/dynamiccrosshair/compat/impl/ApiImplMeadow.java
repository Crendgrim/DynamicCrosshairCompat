package mod.crend.dynamiccrosshair.compat.impl;

import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

//? if meadow {
//? if =1.20.1 {
import net.satisfy.meadow.block.storage.CheeseRackBlock;
import net.satisfy.meadow.item.WoodenBucket;
//?} else {
/*import net.satisfyu.meadow.block.CheeseRackBlock;
import net.satisfyu.meadow.item.WoodenBucket;
*///?}
//?}

public class ApiImplMeadow implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "meadow";
	}

	//? if meadow {
	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public boolean forceInvalidate(CrosshairContext context) {
		if (context.isWithBlock() && context.getBlock() instanceof CheeseRackBlock) {
			return true;
		}
		return (context.getItem() instanceof WoodenBucket);
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();
		ItemStack itemStack = context.getItemStack();

		if (entity instanceof CowEntity || entity instanceof SheepEntity || entity instanceof GoatEntity) {
			if (itemStack.isOf(Items.BUCKET) || itemStack.getItem() instanceof WoodenBucket) {
				return new Crosshair(InteractionType.USE_ITEM_ON_ENTITY);
			}
		}

		return null;
	}
	//?}
}
