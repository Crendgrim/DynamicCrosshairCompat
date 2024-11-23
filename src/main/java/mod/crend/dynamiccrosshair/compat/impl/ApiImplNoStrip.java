package mod.crend.dynamiccrosshair.compat.impl;

//? if no-strip

import mod.crend.dynamiccrosshair.compat.mixin.nostrip.INostripClientMixin;
import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;

//? if no-strip
import us.potatoboy.nostrip.client.NostripClient;
import java.util.List;

public class ApiImplNoStrip implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "nostrip";
	}

	//? if no-strip {
	INostripClientMixin client;
	boolean doStrip;

	@Override
	public void init() {
		List<EntrypointContainer<ClientModInitializer>> list = FabricLoader.getInstance().getEntrypointContainers("client", ClientModInitializer.class);
		for (EntrypointContainer<ClientModInitializer> entry : list) {
			if (entry.getEntrypoint() instanceof NostripClient nostripClient) {
				client = (INostripClientMixin) nostripClient;
				return;
			}
		}
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public boolean forceInvalidate(CrosshairContext context) {
		if (doStrip != client.getDoStrip()) {
			doStrip = client.getDoStrip();
			return true;
		}
		return false;
	}

	@Override
	public Crosshair overrideFromItem(CrosshairContext context, InteractionType interactionType) {
		if (interactionType == InteractionType.USABLE_TOOL && !client.getDoStrip()) {
			return new Crosshair(InteractionType.TOOL).combine(new Crosshair(context.checkToolWithBlock()));
		}
		return null;
	}
	//?}
}
