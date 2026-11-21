package com.rakeshpc.app;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class LinuxPcActivity extends Activity {
    private static final String TERMUX_URL = "https://f-droid.org/packages/com.termux/";
    private static final String X11_URL = "https://github.com/termux/termux-x11/releases";
    private static final String SETUP_COMMANDS =
        "pkg update -y && pkg upgrade -y\n" +
        "pkg install x11-repo -y\n" +
        "pkg install proot-distro -y\n" +
        "pkg install termux-x11-nightly -y\n" +
        "proot-distro install debian\n" +
        "proot-distro login debian -- apt update\n" +
        "proot-distro login debian -- apt install xfce4 dbus-x11 -y";

    private int dp(float value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    private TextView text(String value, float size, int color) {
        TextView view = new TextView(this);
        view.setText(value);
        view.setTextSize(size);
        view.setTextColor(color);
        view.setPadding(0, dp(5), 0, dp(5));
        return view;
    }

    private Button button(String label, Runnable action) {
        Button button = new Button(this);
        button.setText(label);
        button.setAllCaps(false);
        button.setTextSize(15);
        button.setOnClickListener(v -> action.run());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, dp(52));
        params.topMargin = dp(8);
        button.setLayoutParams(params);
        return button;
    }

    private void openUrl(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    private void launchTermux() {
        Intent launch = getPackageManager().getLaunchIntentForPackage("com.termux");
        if (launch != null) {
            startActivity(launch);
        } else {
            Toast.makeText(this, "પહેલા Termux install કરો.", Toast.LENGTH_LONG).show();
            openUrl(TERMUX_URL);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(8, 11, 20));
        getWindow().setNavigationBarColor(Color.rgb(8, 11, 20));

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(22), dp(22), dp(22), dp(28));
        page.setBackgroundColor(Color.rgb(8, 11, 20));
        scroll.addView(page);

        page.addView(text("RAKESH PC  /  LINUX", 13, Color.rgb(120, 180, 255)));
        TextView title = text("Your phone. Your Linux computer.", 26, Color.WHITE);
        title.setTypeface(null, 1);
        page.addView(title);
        page.addView(text(
            "આ તમારા OPPO A3x 5G પર ચાલતું local Linux environment છે. Android બદલાશે નહીં અને root કરવાની જરૂર નથી.",
            16, Color.rgb(205, 214, 230)));

        TextView warning = text(
            "4GB RAM માટે હળવું Debian + XFCE રાખ્યું છે. પ્રથમ setup માટે Wi-Fi વાપરો અને ઓછામાં ઓછું 8–10GB ખાલી storage રાખો. ઉપલબ્ધ જગ્યા પ્રમાણે વધુ પણ લાગી શકે.",
            14, Color.rgb(255, 205, 130));
        warning.setPadding(dp(12), dp(12), dp(12), dp(12));
        warning.setBackgroundColor(Color.rgb(45, 34, 20));
        LinearLayout.LayoutParams warningParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        warningParams.topMargin = dp(14);
        page.addView(warning, warningParams);

        page.addView(text("STEP 1 — Install required apps", 18, Color.WHITE));
        page.addView(text(
            "Termux અને Termux:X11 બંને official source પરથી install કરો. બંને એક જ source/signing family માંથી હોવા જોઈએ; અલગ sources મિક્સ ન કરો.",
            14, Color.rgb(190, 200, 218)));
        page.addView(button("1. Install Termux (F-Droid)", () -> openUrl(TERMUX_URL)));
        page.addView(button("2. Get Termux:X11 (official releases)", () -> openUrl(X11_URL)));

        page.addView(text("STEP 2 — Install Debian + XFCE", 18, Color.WHITE));
        page.addView(text(
            "Termux ખોલો. નીચેના બધા commands એક પછી એક paste કરો. પ્રથમ વખત download અને install થવામાં સમય લાગશે.",
            14, Color.rgb(190, 200, 218)));
        TextView commands = text(SETUP_COMMANDS, 13, Color.rgb(160, 220, 180));
        commands.setTextIsSelectable(true);
        commands.setPadding(dp(12), dp(12), dp(12), dp(12));
        commands.setBackgroundColor(Color.rgb(20, 28, 40));
        page.addView(commands);
        page.addView(button("Copy Linux setup commands", () -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setPrimaryClip(ClipData.newPlainText("RAKESH PC Linux setup", SETUP_COMMANDS));
            Toast.makeText(this, "Commands copied. હવે Termux માં paste કરો.", Toast.LENGTH_LONG).show();
        }));

        page.addView(text("STEP 3 — Start your Linux desktop", 18, Color.WHITE));
        page.addView(text(
            "Debian install થયા પછી Termux માં આ commands ચલાવો. પછી Termux:X11 app ખોલો. Desktop બંધ કરવા XFCE માં Log Out કરો.",
            14, Color.rgb(190, 200, 218)));
        String startCommands =
            "termux-x11 :1 &\n" +
            "proot-distro login --shared-tmp debian -- /bin/bash -lc 'export DISPLAY=:1; dbus-launch --exit-with-session xfce4-session'";
        TextView startText = text(startCommands, 13, Color.rgb(160, 220, 180));
        startText.setTextIsSelectable(true);
        startText.setPadding(dp(12), dp(12), dp(12), dp(12));
        startText.setBackgroundColor(Color.rgb(20, 28, 40));
        page.addView(startText);
        page.addView(button("Copy desktop start commands", () -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setPrimaryClip(ClipData.newPlainText("RAKESH PC Linux start", startCommands));
            Toast.makeText(this, "Start commands copied.", Toast.LENGTH_SHORT).show();
        }));

        page.addView(button("Open Termux", this::launchTermux));
        page.addView(button("Back to RAKESH PC", this::finish));
        page.addView(text(
            "Important: RAKESH PC guides and launches the local Linux setup; Termux/PRoot actually runs Linux. This is not a replacement for Android or a full hardware-virtualized PC. Performance and background execution depend on Android/ColorOS.",
            12, Color.rgb(145, 157, 178)));

        setContentView(scroll);
    }
}
