# BannanaSkin - AppleSkin with Custom Saturation Colors

A customized version of **AppleSkin** with added saturation overlay color customization features.

## 🍎 Original Project Credit

This project is based on [**AppleSkin**](https://github.com/squeek502/AppleSkin) by [squeek502](https://github.com/squeek502).

**Original AppleSkin** is a Minecraft mod that adds various food-related HUD improvements formerly provided by AppleCore. Full credit goes to the original author for the excellent base functionality.

- **Original Repository**: https://github.com/squeek502/AppleSkin
- **Original Author**: squeek502
- **License**: Unlicense

## 🎨 New Features

### `/bannana` Command - Saturation Color Customization

This custom version adds the `/bannana` command that allows you to customize the color of the saturation overlay (the little icons around hunger drumsticks).

#### Available Commands:

##### **Help & Information**
- `/bannana` - Show help with all available commands
- `/bannana show` - Display current color settings

##### **RGB Mode**
- `/bannana rgb <red> <green> <blue>` - Set RGB values (0-255)
  - Example: `/bannana rgb 255 0 0` (pure red)
  - Example: `/bannana rgb 0 255 255` (cyan)
  - Example: `/bannana rgb 255 165 0` (orange)

##### **HSL Mode**
- `/bannana hsl <hue> <gamma>` - Set HSL values
  - **Hue**: 0.0-1.0 (0=red, 0.33=green, 0.66=blue, 1.0=red)
  - **Gamma**: 0.0-2.0 (brightness/intensity)
  - Example: `/bannana hsl 0.5 1.5` (cyan with high brightness)
  - Example: `/bannana hsl 0.75 1.2` (bright purple)

##### **Mode Control**
- `/bannana mode rgb` - Switch to RGB mode
- `/bannana mode hsl` - Switch to HSL mode

##### **Utilities**
- `/bannana reset` - Reset to default values (white)

### Features:
- ✅ **Real-time color changes** - See changes immediately in-game
- ✅ **Persistent settings** - Colors save between game sessions
- ✅ **Two color modes** - RGB for precise control, HSL for artistic control
- ✅ **Input validation** - Commands prevent invalid values
- ✅ **Colored feedback** - Command responses use colored text
- ✅ **Easy reset** - One command to restore defaults

## 🔧 Building

1. Clone this repository
2. Open a command line and execute `./gradlew build`
3. The compiled mod will be in `build/libs/bannanaSkin.jar`

## 📦 Installation

1. Install [Fabric Loader](https://fabricmc.net/use/installer/)
2. Install [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
3. Place `bannanaSkin.jar` in your mods folder
4. Launch Minecraft and enjoy customizable saturation colors!

## 🎮 Usage Examples

```bash
# Make saturation overlay red
/bannana rgb 255 0 0

# Make it a nice cyan color
/bannana rgb 0 255 255

# Use HSL for a rainbow effect (change hue value)
/bannana hsl 0.0 1.5   # Red
/bannana hsl 0.33 1.5  # Green  
/bannana hsl 0.66 1.5  # Blue

# Reset to default
/bannana reset

# Check current settings
/bannana show
```

## 🏷️ Compatibility

- **Minecraft Version**: 1.21
- **Mod Loader**: Fabric
- **Base Mod**: AppleSkin (all original features preserved)

## 📜 License

This customized version maintains the same **Unlicense** license as the original AppleSkin project.

## 🤝 Contributing

This is a personal customization project. For the main AppleSkin mod, please visit the [original repository](https://github.com/squeek502/AppleSkin).

## 📞 Support

For issues specific to the `/bannana` color customization features, please open an issue on this repository.

For general AppleSkin functionality, please refer to the [original AppleSkin repository](https://github.com/squeek502/AppleSkin).

---

**Special thanks to squeek502 for creating the amazing AppleSkin mod that made this customization possible!** 🎉