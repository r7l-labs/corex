#!/bin/bash
set -e

echo "Committing changes..."
git commit -m "Initial CoreX plugin with custom join/leave messages for Minecraft 1.21"

echo "Pushing to remote..."
git push origin main

echo "Building plugin..."
mvn clean package

echo "Creating GitHub release..."
gh release create v1.0.0 \
  --title "CoreX v1.0.0" \
  --notes "🎮 Initial Release - CoreX Minecraft Plugin

## Features
- ✅ Custom join messages with green checkmark (✓) and grey text
- ❌ Custom leave messages with red cross (✗) and grey text
- 🎨 Clean and professional styling
- 🚀 Lightweight and efficient
- 🔧 Compatible with Minecraft 1.21+

## Installation
1. Download \`CoreX-1.0.0.jar\` from the release assets
2. Place it in your server's \`plugins\` folder
3. Restart your server

## Building from Source
\`\`\`bash
mvn clean package
\`\`\`

## Message Format
**Join:** \`✓ PlayerName has joined the game!\`
**Leave:** \`✗ PlayerName has left the game!\`" \
  target/CoreX-1.0.0.jar

echo "Done!"
