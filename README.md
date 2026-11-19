# RAKESH PC

A premium dark, Windows-inspired desktop launcher built for GitHub Pages with Supabase Authentication.

## Files
- `index.html` / `styles.css` — desktop login screen
- `app.js` — Supabase email/password sign-in
- `desktop.html` / `desktop.css` — authenticated desktop landing screen
- `desktop.js` — session check and sign-out

## Supabase setup
The frontend uses the project's public publishable key. This key is intended for browser use; never put a Supabase secret or service-role key in this repository.

Create the allowed account in Supabase Dashboard → Authentication → Users. Email/password sign-in is handled by Supabase Auth. No password is stored by this site.

## Publish
Enable GitHub Pages for the `main` branch and repository root. Then open the published site URL.

## Important
The desktop page checks for an active Supabase session, but GitHub Pages is a static host. For sensitive data or privileged operations, enforce authorization with Supabase Row Level Security and server-side controls.