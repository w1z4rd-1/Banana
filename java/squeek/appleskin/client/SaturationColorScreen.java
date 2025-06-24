package squeek.appleskin.client;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import squeek.appleskin.ModConfig;

public class SaturationColorScreen extends Screen
{
	private final Screen parent;
	private ModConfig config;
	
	// RGB sliders
	private SliderWidget redSlider;
	private SliderWidget greenSlider;
	private SliderWidget blueSlider;
	
	// HSL sliders
	private SliderWidget hueSlider;
	private SliderWidget gammaSlider;
	
	// Mode toggle
	private ButtonWidget modeToggleButton;
	
	// Preview area
	private static final int PREVIEW_SIZE = 32;
	
	public SaturationColorScreen(Screen parent)
	{
		super(Text.literal("Saturation Color Customization"));
		System.out.println("DEBUG: SaturationColorScreen constructor called");
		this.parent = parent;
		this.config = ModConfig.INSTANCE;
		System.out.println("DEBUG: SaturationColorScreen constructor completed, config: " + (config != null ? "loaded" : "null"));
	}
	
	@Override
	protected void init()
	{
		System.out.println("DEBUG: SaturationColorScreen.init() called");
		super.init();
		System.out.println("DEBUG: super.init() completed");
		
		try {
			System.out.println("DEBUG: Starting screen initialization...");
			int centerX = this.width / 2;
			int startY = this.height / 4;
			System.out.println("DEBUG: Screen dimensions - width: " + this.width + ", height: " + this.height);
			
			// Mode toggle button
			System.out.println("DEBUG: Creating mode toggle button...");
			this.modeToggleButton = this.addDrawableChild(ButtonWidget.builder(
				Text.literal(config.useSaturationHSLMode ? "HSL Mode" : "RGB Mode"),
				button -> {
					System.out.println("DEBUG: Mode toggle clicked");
					config.useSaturationHSLMode = !config.useSaturationHSLMode;
					button.setMessage(Text.literal(config.useSaturationHSLMode ? "HSL Mode" : "RGB Mode"));
					rebuildSliders();
				})
				.dimensions(centerX - 50, startY, 100, 20)
				.build());
			System.out.println("DEBUG: Mode toggle button created");
			
			System.out.println("DEBUG: Building sliders...");
			buildSliders();
			System.out.println("DEBUG: Sliders built");
			
			// Buttons
			System.out.println("DEBUG: Creating reset button...");
			this.addDrawableChild(ButtonWidget.builder(
				Text.literal("Reset to Default"),
				button -> resetToDefaults())
				.dimensions(centerX - 150, this.height - 50, 100, 20)
				.build());
			System.out.println("DEBUG: Reset button created");
			
			System.out.println("DEBUG: Creating done button...");
			this.addDrawableChild(ButtonWidget.builder(
				ScreenTexts.DONE,
				button -> {
					saveConfig();
					this.client.setScreen(parent);
				})
				.dimensions(centerX + 50, this.height - 50, 100, 20)
				.build());
			System.out.println("DEBUG: Done button created");
			System.out.println("DEBUG: SaturationColorScreen initialization completed successfully");
		} catch (Exception e) {
			System.err.println("ERROR: Exception in SaturationColorScreen.init(): " + e.getMessage());
			e.printStackTrace();
			throw e; // Re-throw to see if this is caught elsewhere
		}
	}
	
	private void buildSliders()
	{
		System.out.println("DEBUG: buildSliders() called");
		int centerX = this.width / 2;
		int startY = this.height / 4 + 30;
		System.out.println("DEBUG: buildSliders() - mode is " + (config.useSaturationHSLMode ? "HSL" : "RGB"));
		
		try {
			if (config.useSaturationHSLMode)
			{
				System.out.println("DEBUG: Creating HSL sliders...");
				// HSL sliders
				System.out.println("DEBUG: Creating hue slider...");
				this.hueSlider = this.addDrawableChild(new SliderWidget(centerX - 100, startY, 200, 20, 
					Text.literal("Hue: " + String.format("%.2f", config.saturationOverlayHue)), 
					config.saturationOverlayHue) {
					@Override
					protected void updateMessage() {
						config.saturationOverlayHue = (float) this.value;
						this.setMessage(Text.literal("Hue: " + String.format("%.2f", config.saturationOverlayHue)));
					}
					
					@Override
					protected void applyValue() {
						config.saturationOverlayHue = (float) this.value;
					}
				});
				System.out.println("DEBUG: Hue slider created");
				
				System.out.println("DEBUG: Creating gamma slider...");
				this.gammaSlider = this.addDrawableChild(new SliderWidget(centerX - 100, startY + 30, 200, 20,
					Text.literal("Gamma: " + String.format("%.2f", config.saturationOverlayGamma)),
					config.saturationOverlayGamma / 2.0) {
					@Override
					protected void updateMessage() {
						config.saturationOverlayGamma = (float) (this.value * 2.0);
						this.setMessage(Text.literal("Gamma: " + String.format("%.2f", config.saturationOverlayGamma)));
					}
					
					@Override
					protected void applyValue() {
						config.saturationOverlayGamma = (float) (this.value * 2.0);
					}
				});
				System.out.println("DEBUG: Gamma slider created");
			}
			else
			{
				System.out.println("DEBUG: Creating RGB sliders...");
				// RGB sliders
				System.out.println("DEBUG: Creating red slider...");
				this.redSlider = this.addDrawableChild(new SliderWidget(centerX - 100, startY, 200, 20,
					Text.literal("Red: " + config.saturationOverlayRed),
					config.saturationOverlayRed / 255.0) {
					@Override
					protected void updateMessage() {
						config.saturationOverlayRed = (int) (this.value * 255);
						this.setMessage(Text.literal("Red: " + config.saturationOverlayRed));
					}
					
					@Override
					protected void applyValue() {
						config.saturationOverlayRed = (int) (this.value * 255);
					}
				});
				System.out.println("DEBUG: Red slider created");
				
				System.out.println("DEBUG: Creating green slider...");
				this.greenSlider = this.addDrawableChild(new SliderWidget(centerX - 100, startY + 30, 200, 20,
					Text.literal("Green: " + config.saturationOverlayGreen),
					config.saturationOverlayGreen / 255.0) {
					@Override
					protected void updateMessage() {
						config.saturationOverlayGreen = (int) (this.value * 255);
						this.setMessage(Text.literal("Green: " + config.saturationOverlayGreen));
					}
					
					@Override
					protected void applyValue() {
						config.saturationOverlayGreen = (int) (this.value * 255);
					}
				});
				System.out.println("DEBUG: Green slider created");
				
				System.out.println("DEBUG: Creating blue slider...");
				this.blueSlider = this.addDrawableChild(new SliderWidget(centerX - 100, startY + 60, 200, 20,
					Text.literal("Blue: " + config.saturationOverlayBlue),
					config.saturationOverlayBlue / 255.0) {
					@Override
					protected void updateMessage() {
						config.saturationOverlayBlue = (int) (this.value * 255);
						this.setMessage(Text.literal("Blue: " + config.saturationOverlayBlue));
					}
					
					@Override
					protected void applyValue() {
						config.saturationOverlayBlue = (int) (this.value * 255);
					}
				});
				System.out.println("DEBUG: Blue slider created");
			}
			System.out.println("DEBUG: buildSliders() completed successfully");
		} catch (Exception e) {
			System.err.println("ERROR: Exception in buildSliders(): " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	
	private void rebuildSliders()
	{
		// Remove existing sliders
		if (redSlider != null) this.remove(redSlider);
		if (greenSlider != null) this.remove(greenSlider);
		if (blueSlider != null) this.remove(blueSlider);
		if (hueSlider != null) this.remove(hueSlider);
		if (gammaSlider != null) this.remove(gammaSlider);
		
		// Add new sliders
		buildSliders();
	}
	
	private void resetToDefaults()
	{
		config.saturationOverlayRed = 255;
		config.saturationOverlayGreen = 255;
		config.saturationOverlayBlue = 255;
		config.saturationOverlayHue = 0.0f;
		config.saturationOverlayGamma = 1.0f;
		config.useSaturationHSLMode = false;
		
		// Rebuild the screen
		this.clearChildren();
		this.init();
	}
	
	private void saveConfig()
	{
		AutoConfig.getConfigHolder(ModConfig.class).save();
	}
	
	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta)
	{
		this.renderBackground(context, mouseX, mouseY, delta);
		super.render(context, mouseX, mouseY, delta);
		
		// Render color preview
		int centerX = this.width / 2;
		int previewY = this.height / 4 + 120;
		
		int color = getPreviewColor();
		
		// Draw preview background (checkerboard pattern)
		context.fill(centerX - PREVIEW_SIZE/2, previewY, centerX + PREVIEW_SIZE/2, previewY + PREVIEW_SIZE, 0xFF808080);
		context.fill(centerX - PREVIEW_SIZE/2, previewY, centerX, previewY + PREVIEW_SIZE/2, 0xFFB0B0B0);
		context.fill(centerX, previewY + PREVIEW_SIZE/2, centerX + PREVIEW_SIZE/2, previewY + PREVIEW_SIZE, 0xFFB0B0B0);
		
		// Draw preview color
		context.fill(centerX - PREVIEW_SIZE/2, previewY, centerX + PREVIEW_SIZE/2, previewY + PREVIEW_SIZE, color);
		
		// Draw preview label
		context.drawCenteredTextWithShadow(this.textRenderer, "Preview", centerX, previewY + PREVIEW_SIZE + 10, 0xFFFFFF);
	}
	
	private int getPreviewColor()
	{
		if (config.useSaturationHSLMode)
		{
			// Convert HSL to RGB
			float hue = config.saturationOverlayHue;
			float saturation = 1.0f;
			float lightness = config.saturationOverlayGamma * 0.5f;
			
			// Clamp values
			hue = MathHelper.clamp(hue, 0.0f, 1.0f);
			lightness = MathHelper.clamp(lightness, 0.0f, 1.0f);
			
			// Convert HSL to RGB
			float c = (1.0f - Math.abs(2.0f * lightness - 1.0f)) * saturation;
			float x = c * (1.0f - Math.abs((hue * 6.0f) % 2.0f - 1.0f));
			float m = lightness - c / 2.0f;
			
			float r, g, b;
			
			if (hue < 1.0f / 6.0f) {
				r = c; g = x; b = 0;
			} else if (hue < 2.0f / 6.0f) {
				r = x; g = c; b = 0;
			} else if (hue < 3.0f / 6.0f) {
				r = 0; g = c; b = x;
			} else if (hue < 4.0f / 6.0f) {
				r = 0; g = x; b = c;
			} else if (hue < 5.0f / 6.0f) {
				r = x; g = 0; b = c;
			} else {
				r = c; g = 0; b = x;
			}
			
			int red = (int) ((r + m) * 255);
			int green = (int) ((g + m) * 255);
			int blue = (int) ((b + m) * 255);
			
			return ColorHelper.Argb.getArgb(255, red, green, blue);
		}
		else
		{
			// Use RGB values directly
			return ColorHelper.Argb.getArgb(255, config.saturationOverlayRed, config.saturationOverlayGreen, config.saturationOverlayBlue);
		}
	}
	
	@Override
	public void close()
	{
		saveConfig();
		super.close();
	}
} 