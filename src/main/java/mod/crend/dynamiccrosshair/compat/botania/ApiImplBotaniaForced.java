package mod.crend.dynamiccrosshair.compat.botania;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.world.World;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.item.material.ItemEnderAir;

public class ApiImplBotaniaForced implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "botania:forced";
	}

	@Override
	public String getModId() {
		return BotaniaAPI.MODID;
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		if (context.getItem() instanceof GlassBottleItem) {
			if (MinecraftClient.getInstance().world.getRegistryKey() == World.END) {
				return Crosshair.USE_ITEM;
			}
			if (ItemEnderAir.pickupFromEntity(MinecraftClient.getInstance().world, context.player.getBoundingBox().expand(1.0))) {
				return Crosshair.USE_ITEM;
			}
		}
		return null;
	}
}
