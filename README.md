# Android launcher project for Decker D9-005 / Opel Astra H GID concept

This repository contains a first-pass Android launcher concept styled like a classic Opel Astra H GID dashboard / board-computer UI.

## Included
- Android application project skeleton
- landscape launcher UI with GID-inspired "bortovyi computer" feel
- speed / RPM / coolant / battery panels
- ELM327/USB stub for CAN-bus integration
- live clock updater

## Intended target
- Decker D9-005 Android head unit
- USB ELM327 / CAN adapter via OBD-II interface
- Opel Astra H dashboard-inspired UI

## Structure
- `app/src/main/res/layout/activity_main.xml` — main UI layout
- `app/src/main/java/com/decker/astra/launcher/MainActivity.kt` — activity with live clock and mock data
- `app/src/main/java/com/decker/astra/launcher/ElmUsbManager.kt` — USB serial stub

## Build
Open in Android Studio and run on a compatible Android device or emulator.

## Notes
This is a UI-focused prototype. For production usage, replace the mock CAN data with a real ELM327/serial protocol implementation and secure app permission handling.
