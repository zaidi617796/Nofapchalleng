package com.nofapchallenge.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nofapchallenge.app.data.NoFapData;

/** Home: streak counter + aaj ka quote + navigation. */
public class MainActivity extends AppCompatActivity {

    public static final String PREFS = "NoFapChallenge";
    public static final String KEY_START = "streak_start";
    public static final String KEY_RELAPSES = "relapse_count";

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20), dp(24), dp(20), dp(24));
        root.setBackgroundColor(0xFF0F172A);
        scroll.addView(root, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setContentView(scroll);

        TextView title = new TextView(this);
        title.setText(getString(R.string.app_name));
        title.setTextSize(26);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(0xFFFFFFFF);
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        TextView sub = new TextView(this);
        sub.setText(getString(R.string.tagline));
        sub.setTextSize(14);
        sub.setTextColor(0xFF94A3B8);
        sub.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams subLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        subLp.topMargin = dp(4);
        subLp.bottomMargin = dp(20);
        root.addView(sub, subLp);

        long start = prefs.getLong(KEY_START, 0);
        if (start == 0) {
            // Pehli dafa: welcome + shuru ka button
            TextView verse = new TextView(this);
            verse.setText(NoFapData.R1_UR);
            verse.setTextSize(15);
            verse.setTextColor(0xFFE2E8F0);
            verse.setGravity(Gravity.CENTER);
            verse.setBackgroundColor(0xFF1E293B);
            verse.setPadding(dp(16), dp(16), dp(16), dp(16));
            root.addView(verse);

            Button begin = menuButton(getString(R.string.start_challenge), 0xFF16A34A);
            begin.setOnClickListener(v -> {
                prefs.edit().putLong(KEY_START, System.currentTimeMillis()).apply();
                recreate();
            });
            LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            blp.topMargin = dp(24);
            root.addView(begin, blp);
        } else {
            long days = (System.currentTimeMillis() - start) / 86400000L;

            // Bara streak counter
            TextView count = new TextView(this);
            count.setText(String.valueOf(days));
            count.setTextSize(84);
            count.setTypeface(null, Typeface.BOLD);
            count.setTextColor(0xFF22C55E);
            count.setGravity(Gravity.CENTER);
            root.addView(count);

            TextView daysLabel = new TextView(this);
            daysLabel.setText(getString(R.string.days_clean));
            daysLabel.setTextSize(16);
            daysLabel.setTextColor(0xFF94A3B8);
            daysLabel.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams dlp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dlp.bottomMargin = dp(16);
            root.addView(daysLabel, dlp);

            // Aaj ka quote
            int qi = (int) ((System.currentTimeMillis() / 86400000L) % NoFapData.QUOTES.length);
            TextView quote = new TextView(this);
            quote.setText("\u201C" + NoFapData.QUOTES[qi].textUr + "\u201D");
            quote.setTextSize(16);
            quote.setTextColor(0xFFE2E8F0);
            quote.setGravity(Gravity.CENTER);
            quote.setBackgroundColor(0xFF1E293B);
            quote.setPadding(dp(16), dp(16), dp(16), dp(16));
            root.addView(quote);

            // Agla milestone
            String next = null;
            for (NoFapData.Milestone m : NoFapData.MILESTONES) {
                if (m.day > days) {
                    next = getString(R.string.next_milestone, m.day);
                    break;
                }
            }
            if (next != null) {
                TextView nm = new TextView(this);
                nm.setText(next);
                nm.setTextSize(13);
                nm.setTextColor(0xFFFBBF24);
                nm.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams nlp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                nlp.topMargin = dp(12);
                nlp.bottomMargin = dp(8);
                root.addView(nm, nlp);
            }

            // Menu buttons
            Button sos = menuButton(getString(R.string.menu_sos), 0xFFDC2626);
            sos.setOnClickListener(v -> startActivity(new Intent(this, SosActivity.class)));
            root.addView(withTopMargin(sos, 12));

            Button miles = menuButton(getString(R.string.menu_milestones), 0xFF0284C7);
            miles.setOnClickListener(v -> startActivity(new Intent(this, MilestonesActivity.class)));
            root.addView(withTopMargin(miles, 12));

            Button daleel = menuButton(getString(R.string.menu_daleel), 0xFF7C3AED);
            daleel.setOnClickListener(v -> startActivity(new Intent(this, DaleelActivity.class)));
            root.addView(withTopMargin(daleel, 12));

            Button guard = menuButton(getString(R.string.menu_guard), 0xFFB45309);
            guard.setOnClickListener(v -> startActivity(new Intent(this, GuardActivity.class)));
            root.addView(withTopMargin(guard, 12));

            Button reset = menuButton(getString(R.string.menu_reset), 0xFF475569);
            reset.setOnClickListener(v -> confirmReset());
            root.addView(withTopMargin(reset, 12));
        }

        // Disclaimer — hamesha neeche
        TextView disc = new TextView(this);
        disc.setText(NoFapData.DISCLAIMER_MEDICAL);
        disc.setTextSize(11);
        disc.setTextColor(0xFF64748B);
        disc.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams dlp2 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dlp2.topMargin = dp(28);
        root.addView(disc, dlp2);
    }

    private void confirmReset() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.reset_title))
                .setMessage(getString(R.string.reset_msg))
                .setPositiveButton(getString(R.string.reset_yes), (d, w) -> {
                    int r = prefs.getInt(KEY_RELAPSES, 0);
                    prefs.edit()
                            .putLong(KEY_START, System.currentTimeMillis())
                            .putInt(KEY_RELAPSES, r + 1)
                            .apply();
                    recreate();
                })
                .setNegativeButton(getString(R.string.reset_no), null)
                .show();
    }

    private Button menuButton(String text, int color) {
        Button b = new Button(this);
        b.setText(text);
        b.setTextSize(17);
        b.setTextColor(0xFFFFFFFF);
        b.setBackgroundColor(color);
        b.setPadding(dp(12), dp(16), dp(12), dp(16));
        b.setAllCaps(false);
        return b;
    }

    private Button withTopMargin(Button b, int marginDp) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = dp(marginDp);
        b.setLayoutParams(lp);
        return b;
    }

    private int dp(int v) {
        return Math.round(v * getResources().getDisplayMetrics().density);
    }
}
