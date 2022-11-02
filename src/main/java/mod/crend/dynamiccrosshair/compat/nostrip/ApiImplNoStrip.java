package mod.crend.dynamiccrosshair.compat.nostrip;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.mixin.nostrip.INostripClientMixin;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.mixin.IAxeItemMixin;
import mod.crend.dynamiccrosshair.mixin.IShovelItemMixin;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolItem;
import us.potatoboy.nostrip.client.NostripClient;

import java.util.List;

public class ApiImplNoStrip implements DynamicCrosshairApi {
	INostripClientMixin client;
	boolean doStrip;

	@Override
	public String getNamespace() {
		return "nostrip";
	}

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
	public Crosshair checkUsableItem(CrosshairContext context) {
		if (!client.getDoStrip() && context.isWithBlock()) {
			Item item = context.getItem();
			BlockState blockState = context.getBlockState();
			if (item instanceof ToolItem && IAxeItemMixin.getSTRIPPED_BLOCKS().containsKey(blockState.getBlock())) {
				return Crosshair.TOOL;
			}
			if (item instanceof ShovelItem && IShovelItemMixin.getPATH_STATES().containsKey(blockState.getBlock())) {
				return Crosshair.TOOL;
			}
		}

		return null;
	}
}
