package com.rakeshpc.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Build;
import android.app.ActivityManager;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Typeface;

public class LinuxPcActivity extends Activity {
    private int dp(float value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    private TextView text(String value, int size, int color) {
        TextView v = new TextView(this);
        v.setText(value);
        v.setTextSize(size);
        v.setTextColor(color);
        return v;
    }

    private LinearLayout card(String title, String detail) {
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(dp(18), dp(16), dp(18), dp(16));
        box.setBackgroundColor(Color.rgb(25, 34, 52));
        TextView heading = text(title, 17, Color.WHITE);
        heading.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        TextView body = text(detail, 13, Color.rgb(190, 202, 222));
        body.setPadding(0, dp(7), 0, 0);
        box.addView(heading);
        box.addView(body);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2);
        p.bottomMargin = dp(12);
        box.setLayoutParams(p);
        return box;
    }

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(8, 13, 24));
        getWindow().setNavigationBarColor(Color.rgb(8, 13, 24));

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(Color.rgb(8, 13, 24));
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(24), dp(22), dp(24), dp(24));
        scroll.addView(root);

        TextView brand = text("RAKESH PC", 28, Color.WHITE);
        brand.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        root.addView(brand);
        TextView subtitle = text("LOCAL LINUX COMPUTER", 12, Color.rgb(111, 176, 255));
        subtitle.setPadding(0, dp(4), 0, dp(18));
        root.addView(subtitle);

        root.addView(card("Linux Engine",
            "Not installed in this build. The native runtime and Debian root filesystem must be packaged and verified before Linux commands can run."));
        root.addView(card("Device compatibility",
            "Android: " + Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")\nCPU ABI: " + Build.SUPPORTED_ABIS[0] + "\n" + memoryInfo()));
        root.addView(card("Desktop environment",
            "Planned: lightweight XFCE-style desktop, window manager, file manager and terminal inside RAKESH PC. These components are not active yet."));
        root.addView(card("Security model",
            "No root access. Linux files will stay inside RAKESH PC's private app storage unless the user explicitly exports files. Android remains the host operating system."));

        Button status = new Button(this);
        status.setText("CHECK LINUX ENGINE STATUS");
        status.setAllCaps(false);
        status.setOnClickListener(v -> Toast.makeText(this,
            "Linux runtime bundle is not included yet. No external terminal app is required by the final design.",
            Toast.LENGTH_LONG).show());
        root.addView(status);

        TextView note = text("Build stage 1: native Linux workspace shell. Next: integrate and test the ARM64 PRoot runtime and Debian rootfs before enabling Install / Terminal.", 12, Color.rgb(160, 174, 198));
        note.setPadding(0, dp(16), 0, 0);
        root.addView(note);

        setContentView(scroll);
    }

    private String memoryInfo() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
        long totalMb = info.totalMem / (1024 * 1024);
        long availableMb = info.availMem / (1024 * 1024);
        return "RAM reported by Android: " + totalMb + " MB total, " + availableMb + " MB currently available";
    }
}