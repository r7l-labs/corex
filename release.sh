#!/bin/bash
set -e

echo "Building plugin..."
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
mvn clean package

echo "Creating GitHub release..."
VERSION=$(mvn -q -Dexec.executable=echo -Dexec.args='${project.version}' --non-recursive exec:exec)
gh release create v$VERSION \
  --title "CoreX v$VERSION - Highly Configurable Join System" \
  --notes "🎮 Major Update - CoreX Minecraft Plugin

## 🎉 What's New
- 🎯 **Highly Configurable System** - Everything is now configurable via \`config.yml\`
- 📢 **Custom Titles** - Display welcome titles with customizable text, colors, and timing
- 🎆 **Fireworks on Join** - Spawn fireworks in specific worlds with customizable effects
- 🔊 **Sound Effects** - Play sounds on join/leave events
- 🎨 **Enhanced Messages** - Full color code support with placeholder support
- ⏰ **Reminders** - Broadcast configurable reminder messages with titles and sounds
- 🔄 **Reload Command** - Reload the configuration without restarting the server using \`/corexreload\`

## ✨ Features
- ✅ Configurable join/leave messages with color codes
- 🎯 Title display system with subtitle support
- 🎆 Fireworks with multiple effect types (BALL, BALL_LARGE, BURST, CREEPER, STAR)
- 🌈 Custom firework colors and fade effects
- 🔊 Sound effects for join/leave events
- 🌍 World-specific firework spawning
- ⚙️ Comprehensive config.yml with detailed comments
- 🚀 Java 11 and Spigot API 1.12.2 support

## Configuration
The plugin now includes a detailed \`config.yml\` that allows you to customize:
- Join/leave message format and colors
- Title text, subtitle, and display timing
- Firework colors, types, and effects
- Sound effects and volumes
- World restrictions for features
- Reminder messages, interval, titles, and sounds

## Installation
1. Download \`CoreX-$VERSION.jar\` from the release assets
2. Place it in your server's \`plugins\` folder
3. Restart your server
4. Edit \`plugins/CoreX/config.yml\` to customize settings
5. Reload the plugin with \`/corexreload\`

## Upgrading from v2.0.0
- Simply replace the old JAR file
- A default config will be generated on first run
- Review and customize the new configuration options

## Building from Source
\`\`\`bash
mvn clean package
\`\`\`

## Requirements
- Java 11+
- Spigot/Paper 1.12.2+

## Default Message Format
**Join:** \`✓ PlayerName has joined the game!\`
**Leave:** \`✗ PlayerName has left the game!\`
**Title:** \`Welcome Back!\`
**Subtitle:** \`Hello, PlayerName!\`" \
  target/CoreX-$VERSION.jar

echo "Done!"
