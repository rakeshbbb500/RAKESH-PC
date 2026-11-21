# RAKESH PC

A premium dark desktop workspace with an Android application wrapper.

## Android app
- Android application module: `android-app`
- Landscape immersive WebView shell for the existing GitHub Pages workspace.
- Native **Linux PC** workspace entry point.
- Linux workspace reports device ABI / Android version / RAM and clearly indicates whether a Linux runtime is installed.

## Linux runtime status
The current Android build contains the native Linux workspace shell only. It does **not** yet bundle PRoot, a Debian root filesystem, or an XFCE desktop. Terminal and Linux installation are not advertised as working until those runtime components are integrated and tested.

The intended rootless design is an app-private ARM64 Linux userspace running over the Android kernel. Android remains the host OS; this is not a replacement OS or a hardware virtual machine. Android's Virtualization Framework APIs require restricted system permission and are not a general third-party-app route. See the Android platform documentation.

## Build
```sh
gradle -p android-app --no-daemon assembleDebug
```

Codemagic workflow: `rakesh-pc-android`.

## Web workspace
Enable GitHub Pages for the `main` branch and repository root:
https://rakeshbbb500.github.io/RAKESH-PC/

Supabase uses the public client key only. Never commit a service-role or secret key.
