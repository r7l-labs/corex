# CoreX Minecraft Plugin

A Minecraft plugin with custom join and leave messages featuring cool styling.

## Features

- **Custom Join Messages**: Green checkmark (✓) with grey text when players join
- **Custom Leave Messages**: Red cross (✗) with grey text when players leave
- Clean and professional styling

## Installation

1. Build the plugin using Maven:
   ```bash
   mvn clean package
   ```

2. Copy the generated JAR file from `target/CoreX-1.0.0.jar` to your server's `plugins` folder

3. Restart your server or reload plugins

## Building

This plugin uses Maven. To build:

```bash
mvn clean package
```

The compiled plugin will be in the `target` directory.

## Requirements

- Spigot/Paper 1.21 or higher
- Java 17 or higher

## Message Format

### Join Message
```
✓ PlayerName has joined the game!
```
- Green checkmark
- Grey player name and message text

### Leave Message
```
✗ PlayerName has left the game!
```
- Red cross
- Grey player name and message text

## License

See LICENSE file for details.

## Author

r7l-labs
