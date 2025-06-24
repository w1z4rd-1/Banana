package squeek.appleskin;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import squeek.appleskin.api.AppleSkinApi;
import squeek.appleskin.client.BannanaCommand;
import squeek.appleskin.client.DebugInfoHandler;
import squeek.appleskin.client.HUDOverlayHandler;
import squeek.appleskin.client.TooltipOverlayHandler;
import squeek.appleskin.network.ClientSyncHandler;

public class AppleSkin implements ClientModInitializer
{
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitializeClient()
	{
		ClientSyncHandler.init();
		ModConfig.init();
		HUDOverlayHandler.init();
		TooltipOverlayHandler.init();
		DebugInfoHandler.init();
		
		// Register the bannana command
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			BannanaCommand.register(dispatcher);
		});
		
		FabricLoader.getInstance().getEntrypointContainers("appleskin", AppleSkinApi.class).forEach(entrypoint -> {
			try
			{
				entrypoint.getEntrypoint().registerEvents();
			}
			catch (Throwable e)
			{
				LOGGER.error("Failed to load entrypoint for mod {}", entrypoint.getProvider().getMetadata().getId(), e);
			}
		});
	}
}
