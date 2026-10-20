package com.nofapchallenge.app;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nofapchallenge.app.data.NoFapData;
import com.nofapchallenge.app.guard.FocusGuardService;

/** Jab blocked app khule to ye screen aati hai. */
public class GuardBlockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String pkg = getIntent().getStringExtra("blocked_pkg");
        String appName = pkg != null ? appName(pkg) : "";

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        int pad = dp(24);
        root.setPadding(pad, pad, pad, pad);
        root.setBackgroundColor(0xFF0F172A);

        TextView emoji = new TextView(this);
        emoji.setText("\uD83D\uDEAB");
        emoji.setTextSize(64);
        emoji.setGravity(Gravity.CENTER);
        root.addView(emoji);

        TextView t = new TextView(this);
        t.setText(getString(R.string.blocked_title, appName));
        t.setTextSize(22);
        t.setTypeface(null, Typeface.BOLD);
        t.setTextColor(0xFFFFFFFF);
        t.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tlp.topMargin = dp(16);
        root.addView(t, tlp);

        TextView verse = new TextView(this);
        verse.setText(NoFapData.R1_UR);
        verse.setTextSize(15);
        verse.setTextColor(0xFFE2E8F0);
        verse.setGravity(Gravity.CENTER);
        verse.setBackgroundColor(0xFF1E293B);
        verse.setPadding(dp(16), dp(16), dp(16), dp(16));
        LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vlp.topMargin = dp(20);
        root.addView(verse, vlp);

        Button back = new Button(this);
        back.setText(getString(R.string.blocked_back));
        back.setTextSize(17);
        back.setTextColor(0xFFFFFFFF);
        back.setBackgroundColor(0xFF16A34A);
        back.setAllCaps(false);
        back.setPadding(dp(12), dp(16), dp(12), dp(16));
        back.setOnClickListener(v -> {
            // Home screen par wapas — blocked app peeche reh jati hai
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.addCategory(Intent.CATEGORY_HOME);
            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(home);
            finish();
        });
        LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        blp.topMargin = dp(24);
        root.addView(back, blp);

        Button streak = new Button(this);
        streak.setText(getString(R.string.blocked_streak));
        streak.setTextSize(15);
        streak.setTextColor(0xFFFFFFFF);
        streak.setBackgroundColor(0xFF0284C7);
        streak.setAllCaps(false);
        streak.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
        LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        slp.topMargin = dp(12);
        root.addView(streak, slp);

        setContentView(root);
    }

    private String appName(String pkg) {
        for (int i = 0; i < FocusGuardService.BLOCKED_PKGS.length; i++) {
            if (FocusGuardService.BLOCKED_PKGS[i].equals(pkg)) {
                return FocusGuardService.BLOCKED_NAMES[i];
            }
        }
        return "";
    }

    @Override
    public void onBackPressed() {
        // Back se block bypass na ho — kuch na karo
    }

    private int dp(int v) {
        return Math.round(v * getResources().getDisplayMetrics().density);
    }
}
