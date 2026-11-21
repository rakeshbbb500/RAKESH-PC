# RAKESH PC Linux Engine Integration Audit

Date: 2026-11-21

## Goal

Run a genuine ARM64 Linux userspace inside the RAKESH PC Android application, without asking the user to install Termux or another terminal application. Android remains the host OS; this is not a replacement Android kernel, a full hardware VM, or a rooted device.

## Target device

- OPPO A3x 5G
- User-reported RAM: 4 GB
- User-reported storage: 128 GB
- Android ABI target: arm64-v8a (verify at runtime before enabling installation)

## Engine evaluation

The upstream `oonid/pr` project publishes a standalone Android `:proot-engine` library and a `ProotHost` / `ProotLauncher` API. It documents no-Termux Linux distributions and supports Debian. Its engine is substantially more than copying a generic PRoot binary: it contains Android-specific seccomp, SELinux and W^X handling, a native PTY bridge, a patched PRoot and a CLI/bootstrap layer.

Reference: https://github.com/oonid/pr

### Compatibility constraints found

- Its current engine module declares minSdk 28; the existing RAKESH PC app currently declares minSdk 23.
- The upstream project currently builds with a newer Android Gradle Plugin / Kotlin toolchain and compileSdk 37, while this app currently uses AGP 8.7.3 and Java 17.
- The upstream native engine is arm64-v8a only.
- The engine needs its matching native binaries, JNI PTY bridge, bootstrap logic and package assets. Copying only `ProotLauncher.kt` will not work.
- The upstream project has a multi-license map: Android library code is MIT; PRoot and CLI components include GPL-2.0-or-later / GPL-3.0-or-later components. Any redistribution must preserve notices and corresponding source/license obligations.
- Android app-private executable files are affected by target-SDK W^X and zygote seccomp restrictions. A generic downloaded executable in filesDir is not a safe/working substitute for the upstream loader arrangement.

## Current implementation state

The current feature branch adds a native Linux workspace screen and a launcher entry point. It reports device information and clearly says the runtime is not installed. It does **not** yet run Linux commands, install Debian, or launch XFCE.

## Required next engineering steps

1. Pin a reviewed upstream commit/tag of `oonid/pr`; inspect its license notices and source changes.
2. Build its `:proot-engine` AAR and native binaries for arm64-v8a in CI using its documented NDK/Rust build scripts.
3. Integrate the AAR into this app and implement `ProotHost` using RAKESH PC's private app directories.
4. Implement a cancellable, resumable Debian ARM64 bootstrap with archive integrity checks and progress reporting.
5. Add a native PTY terminal UI and verify shell commands on the actual OPPO A3x 5G.
6. Only after terminal and package manager tests pass, add a lightweight graphical session. A GUI display server/compositor is a separate engineering task; XFCE must not be advertised as working before it is tested.
7. Run Codemagic debug APK build and install/test on the target phone before merging.

## Resource policy for 4 GB RAM

Start with a minimal Debian base and a terminal-only first milestone. Keep XFCE and larger applications optional. Do not download a multi-gigabyte desktop by default. Check free storage before installation and allow the user to cancel and resume safely.

## Acceptance tests

- App starts with no extra terminal app installed.
- Engine and rootfs state are detected correctly.
- Debian shell runs `uname -m`, `cat /etc/os-release`, `id`, `pwd`.
- `apt update` succeeds using Android's active network DNS.
- Closing/reopening the app does not corrupt the rootfs.
- User can remove the Linux environment from inside RAKESH PC.
- No root permission, service-role secret, or unsafe arbitrary WebView-to-shell bridge is used.
