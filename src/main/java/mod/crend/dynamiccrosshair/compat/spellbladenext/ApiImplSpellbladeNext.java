package mod.crend.dynamiccrosshair.compat.spellbladenext;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.spellbladenext.fabric.ExampleModFabric;
import net.spellbladenext.fabric.Reaver;
import net.spellbladenext.fabric.items.Offering;
import net.spellbladenext.fabric.items.PrismaticEffigy;
import net.spellbladenext.items.FriendshipBracelet;

public class ApiImplSpellbladeNext implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "spellbladenext";
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof Offering;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof PrismaticEffigy || item instanceof FriendshipBracelet;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof Offering) {
			if (context.player.hasStatusEffect(ExampleModFabric.HEX.get())) {
				return Crosshair.USABLE;
			}
		}
		return null;
	}

	@Override
	public boolean isInteractableEntity(Entity entity) {
		return entity instanceof Reaver;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();

		if (entity instanceof Reaver reaver) {
			if (!reaver.getOffers().isEmpty() && reaver.getMainHandStack().isEmpty()) {
				return Crosshair.INTERACTABLE;
			}
		}

		return null;
	}
}
