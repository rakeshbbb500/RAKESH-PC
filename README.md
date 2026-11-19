# RAKESH PC

A premium dark, Windows-inspired, 16:9 desktop workspace hosted on GitHub Pages.

## Features
- Supabase email/password authentication and protected desktop session.
- Windows-style Start menu, taskbar, draggable/minimizable/maximizable app windows.
- Quick app search, live clock and date.
- Cloud Notes synchronized to the signed-in user's Supabase account.
- Private Cloud File Manager: upload, list, download and delete files in the user's own folder (50 MB per-file limit).
- Calculator with basic arithmetic.
- Wallpaper and accent-color personalization.
- Shortcuts to Chrome, Gmail, GitHub, ChatGPT, Angel One and Codemagic.

## Supabase setup
The browser uses the project's public publishable key only. Never commit a service-role or secret key.
The `rakeshpc_notes` table has Row Level Security and restricts each record to its owner.
The private `rakeshpc-files` Storage bucket restricts file access to the authenticated user's UUID folder.
Create the account in Supabase Dashboard → Authentication → Users. No password is stored by this app.

## Publish
Enable GitHub Pages for the `main` branch and repository root:
https://rakeshbbb500.github.io/RAKESH-PC/

## Important
This is a web-based desktop workspace, not a replacement for Windows or a native desktop OS. External services open in a new browser tab. Notes and files require an internet connection and an active Supabase session. Wallpaper and accent preferences are saved in the current browser.
