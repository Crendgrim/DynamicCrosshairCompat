package mod.crend.dynamiccrosshair.compat;

import mod.crend.dynamiccrosshair.DynamicCrosshair;
import mod.crend.dynamiccrosshair.compat.ironchests.ApiImplIronChests;
import mod.crend.dynamiccrosshair.compat.ironchests.ApiImplIronChestsRestocked;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class DynamicCrosshairCompat implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        /*
         * There are two mods re-implementing the IronChests mod on Fabric:
         * - Iron Chests (Fabric)
         * - Iron Chests: Restocked
         *
         * Both mods use the same mod id, which causes issues with how we check whether they are loaded.
         * This workaround makes sure to load the appropriate compatibility API.
         */
        FabricLoader fabricLoader = FabricLoader.getInstance();
        if (fabricLoader.isModLoaded("ironchests")) {
            fabricLoader.getModContainer("ironchests").ifPresent(modContainer -> {
                if (modContainer.getMetadata().getName().contains("Restocked")) {
                    DynamicCrosshair.registerApi(new ApiImplIronChestsRestocked());
                } else {
                    DynamicCrosshair.registerApi(new ApiImplIronChests());
                }
            });
        }
    }
}
