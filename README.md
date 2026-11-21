# RAKESH PC

A premium dark, Windows-inspired desktop workspace hosted on GitHub Pages, with an Android application wrapper.

## Existing workspace features
- Supabase email/password authentication and protected desktop session.
- Windows-style Start menu, taskbar, draggable/minimizable/maximizable app windows.
- Quick app search, live clock and date.
- Cloud Notes synchronized to the signed-in user's Supabase account.
- Private Cloud File Manager: upload, list, download and delete files in the user's own folder (50 MB per-file limit).
- Calculator with basic arithmetic.
- Wallpaper and accent-color personalization.
- Shortcuts to Chrome, Gmail, GitHub, ChatGPT, Angel One and Codemagic.

## Android app
- Android application module: `android-app`.
- Landscape immersive WebView shell for the existing GitHub Pages workspace.
- Native **Linux PC** workspace entry point.
- Linux workspace displays Android version, CPU ABI and available RAM.

## Linux runtime status — important
The current feature branch contains the native Linux workspace shell only. It does **not** yet bundle the PRoot engine, a Debian ARM64 root filesystem, a working Linux terminal, or an XFCE desktop. The Linux PC screen is a status/integration placeholder; it does not execute Linux commands yet.

The intended design is a rootless ARM64 Linux userspace inside RAKESH PC, without a separate Termux installation. Android remains the host OS; this is not a replacement kernel or a full hardware virtual machine. The candidate engine is https://github.com/oonid/pr. Its Android-specific native binaries, bootstrap, Kotlin integration, licensing notices and on-device tests still need to be completed before Linux can be enabled.

## Build the current Android workspace APK
```sh
gradle -p android-app --no-daemon assembleDebug
```

Expected artifact: `android-app/app/build/outputs/apk/debug/app-debug.apk`.
Codemagic workflow: `rakesh-pc-android` (debug APK build).

## Supabase setup
The browser uses the project's public publishable key only. Never commit a service-role or secret key. The `rakeshpc_notes` table has Row Level Security and restricts each record to its owner. The private `rakeshpc-files` Storage bucket restricts file access to the authenticated user's UUID folder. Create the account in Supabase Dashboard → Authentication → Users. No password is stored by this app.

## Publish
Enable GitHub Pages for the `main` branch and repository root:
https://rakeshbbb500.github.io/RAKESH-PC/

## Important
The existing desktop is a web-based workspace. Notes and cloud files require an internet connection and an active Supabase session. Wallpaper and accent preferences are saved in the current browser.
