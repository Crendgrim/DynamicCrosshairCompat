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
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolItem;
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
	public Crosshair computeFromItem(CrosshairContext context) {
		if (!client.getDoStrip() && context.includeTool() && context.isWithBlock()) {
			Item item = context.getItem();
			BlockState blockState = context.getBlockState();
			if (item instanceof ToolItem && AxeItem.STRIPPED_BLOCKS.containsKey(blockState.getBlock())
					|| (item instanceof ShovelItem && ShovelItem.PATH_STATES.containsKey(blockState.getBlock()))
			) {
				return new Crosshair(InteractionType.TOOL);
			}
		}

		return null;
	}
	//?}
}
