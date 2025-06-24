package squeek.appleskin.client;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import squeek.appleskin.ModConfig;

public class BannanaCommand
{
	public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher)
	{
		dispatcher.register(
			ClientCommandManager.literal("bannana")
				.executes(BannanaCommand::showHelp)
				// RGB commands
				.then(ClientCommandManager.literal("rgb")
					.then(ClientCommandManager.argument("red", IntegerArgumentType.integer(0, 255))
						.then(ClientCommandManager.argument("green", IntegerArgumentType.integer(0, 255))
							.then(ClientCommandManager.argument("blue", IntegerArgumentType.integer(0, 255))
								.executes(BannanaCommand::setRGB)))))
				// HSL commands
				.then(ClientCommandManager.literal("hsl")
					.then(ClientCommandManager.argument("hue", FloatArgumentType.floatArg(0.0f, 1.0f))
						.then(ClientCommandManager.argument("gamma", FloatArgumentType.floatArg(0.0f, 2.0f))
							.executes(BannanaCommand::setHSL))))
				// Mode switching
				.then(ClientCommandManager.literal("mode")
					.then(ClientCommandManager.literal("rgb")
						.executes(BannanaCommand::setModeRGB))
					.then(ClientCommandManager.literal("hsl")
						.executes(BannanaCommand::setModeHSL)))
				// Reset to defaults
				.then(ClientCommandManager.literal("reset")
					.executes(BannanaCommand::resetToDefaults))
				// Show current values
				.then(ClientCommandManager.literal("show")
					.executes(BannanaCommand::showCurrentValues))
		);
	}

	private static int showHelp(CommandContext<FabricClientCommandSource> context)
	{
		context.getSource().sendFeedback(Text.literal("=== Bannana Saturation Color Commands ===").formatted(Formatting.GOLD));
		context.getSource().sendFeedback(Text.literal("/bannana rgb <red> <green> <blue> - Set RGB values (0-255)").formatted(Formatting.YELLOW));
		context.getSource().sendFeedback(Text.literal("/bannana hsl <hue> <gamma> - Set HSL values (hue: 0.0-1.0, gamma: 0.0-2.0)").formatted(Formatting.YELLOW));
		context.getSource().sendFeedback(Text.literal("/bannana mode rgb - Switch to RGB mode").formatted(Formatting.YELLOW));
		context.getSource().sendFeedback(Text.literal("/bannana mode hsl - Switch to HSL mode").formatted(Formatting.YELLOW));
		context.getSource().sendFeedback(Text.literal("/bannana reset - Reset to default values").formatted(Formatting.YELLOW));
		context.getSource().sendFeedback(Text.literal("/bannana show - Show current values").formatted(Formatting.YELLOW));
		return 1;
	}

	private static int setRGB(CommandContext<FabricClientCommandSource> context)
	{
		int red = IntegerArgumentType.getInteger(context, "red");
		int green = IntegerArgumentType.getInteger(context, "green");
		int blue = IntegerArgumentType.getInteger(context, "blue");
		
		ModConfig.INSTANCE.saturationOverlayRed = red;
		ModConfig.INSTANCE.saturationOverlayGreen = green;
		ModConfig.INSTANCE.saturationOverlayBlue = blue;
		ModConfig.INSTANCE.useSaturationHSLMode = false;
		
		saveConfig();
		
		context.getSource().sendFeedback(Text.literal("Set RGB values: ")
			.append(Text.literal("R=" + red).formatted(Formatting.RED))
			.append(Text.literal(" G=" + green).formatted(Formatting.GREEN))
			.append(Text.literal(" B=" + blue).formatted(Formatting.BLUE)));
		return 1;
	}
	
	private static int setHSL(CommandContext<FabricClientCommandSource> context)
	{
		float hue = FloatArgumentType.getFloat(context, "hue");
		float gamma = FloatArgumentType.getFloat(context, "gamma");
		
		ModConfig.INSTANCE.saturationOverlayHue = hue;
		ModConfig.INSTANCE.saturationOverlayGamma = gamma;
		ModConfig.INSTANCE.useSaturationHSLMode = true;
		
		saveConfig();
		
		context.getSource().sendFeedback(Text.literal("Set HSL values: ")
			.append(Text.literal("Hue=" + String.format("%.2f", hue)).formatted(Formatting.AQUA))
			.append(Text.literal(" Gamma=" + String.format("%.2f", gamma)).formatted(Formatting.LIGHT_PURPLE)));
		return 1;
	}
	
	private static int setModeRGB(CommandContext<FabricClientCommandSource> context)
	{
		ModConfig.INSTANCE.useSaturationHSLMode = false;
		saveConfig();
		
		context.getSource().sendFeedback(Text.literal("Switched to RGB mode").formatted(Formatting.GREEN));
		showCurrentValues(context);
		return 1;
	}
	
	private static int setModeHSL(CommandContext<FabricClientCommandSource> context)
	{
		ModConfig.INSTANCE.useSaturationHSLMode = true;
		saveConfig();
		
		context.getSource().sendFeedback(Text.literal("Switched to HSL mode").formatted(Formatting.GREEN));
		showCurrentValues(context);
		return 1;
	}
	
	private static int resetToDefaults(CommandContext<FabricClientCommandSource> context)
	{
		ModConfig.INSTANCE.saturationOverlayRed = 255;
		ModConfig.INSTANCE.saturationOverlayGreen = 255;
		ModConfig.INSTANCE.saturationOverlayBlue = 255;
		ModConfig.INSTANCE.saturationOverlayHue = 0.0f;
		ModConfig.INSTANCE.saturationOverlayGamma = 1.0f;
		ModConfig.INSTANCE.useSaturationHSLMode = false;
		
		saveConfig();
		
		context.getSource().sendFeedback(Text.literal("Reset to default values (white, RGB mode)").formatted(Formatting.GREEN));
		return 1;
	}
	
	private static int showCurrentValues(CommandContext<FabricClientCommandSource> context)
	{
		context.getSource().sendFeedback(Text.literal("=== Current Saturation Color Settings ===").formatted(Formatting.GOLD));
		context.getSource().sendFeedback(Text.literal("Mode: " + (ModConfig.INSTANCE.useSaturationHSLMode ? "HSL" : "RGB")).formatted(Formatting.AQUA));
		
		if (ModConfig.INSTANCE.useSaturationHSLMode)
		{
			context.getSource().sendFeedback(Text.literal("Hue: " + String.format("%.2f", ModConfig.INSTANCE.saturationOverlayHue)).formatted(Formatting.YELLOW));
			context.getSource().sendFeedback(Text.literal("Gamma: " + String.format("%.2f", ModConfig.INSTANCE.saturationOverlayGamma)).formatted(Formatting.YELLOW));
		}
		else
		{
			context.getSource().sendFeedback(Text.literal("Red: " + ModConfig.INSTANCE.saturationOverlayRed).formatted(Formatting.RED));
			context.getSource().sendFeedback(Text.literal("Green: " + ModConfig.INSTANCE.saturationOverlayGreen).formatted(Formatting.GREEN));
			context.getSource().sendFeedback(Text.literal("Blue: " + ModConfig.INSTANCE.saturationOverlayBlue).formatted(Formatting.BLUE));
		}
		return 1;
	}
	
	private static void saveConfig()
	{
		AutoConfig.getConfigHolder(ModConfig.class).save();
	}
} 