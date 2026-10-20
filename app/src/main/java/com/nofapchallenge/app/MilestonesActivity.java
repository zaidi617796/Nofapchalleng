package com.nofapchallenge.app;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nofapchallenge.app.data.NoFapData;

/** Milestones: 11 din-targets, reached/unreached state ke saath. */
public class MilestonesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.menu_milestones));

        long start = getSharedPreferences(MainActivity.PREFS, MODE_PRIVATE)
                .getLong(MainActivity.KEY_START, 0);
        long days = start == 0 ? 0 : (System.currentTimeMillis() - start) / 86400000L;

        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20), dp(20), dp(20), dp(20));
        root.setBackgroundColor(0xFF0F172A);
        scroll.addView(root, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setContentView(scroll);

        for (NoFapData.Milestone m : NoFapData.MILESTONES) {
            boolean reached = days >= m.day;

            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.HORIZONTAL);
            card.setBackgroundColor(reached ? 0xFF14532D : 0xFF1E293B);
            card.setPadding(dp(14), dp(12), dp(14), dp(12));
            LinearLayout.LayoutParams clp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            clp.topMargin = dp(8);
            root.addView(card, clp);

            TextView badge = new TextView(this);
            badge.setText(reached ? "\u2713" : String.valueOf(m.day));
            badge.setTextSize(22);
            badge.setTypeface(null, Typeface.BOLD);
            badge.setTextColor(reached ? 0xFF22C55E : 0xFFFBBF24);
            badge.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(dp(56), dp(56));
            blp.rightMargin = dp(12);
            card.addView(badge, blp);

            LinearLayout col = new LinearLayout(this);
            col.setOrientation(LinearLayout.VERTICAL);
            card.addView(col, new LinearLayout.LayoutParams(0,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

            TextView dayT = new TextView(this);
            dayT.setText(getString(R.string.milestone_day, m.day));
            dayT.setTextSize(13);
            dayT.setTypeface(null, Typeface.BOLD);
            dayT.setTextColor(reached ? 0xFF86EFAC : 0xFF94A3B8);
            col.addView(dayT);

            TextView msg = new TextView(this);
            msg.setText(m.textUr);
            msg.setTextSize(14);
            msg.setTextColor(0xFFE2E8F0);
            col.addView(msg);
        }
    }

    private int dp(int v) {
        return Math.round(v * getResources().getDisplayMetrics().density);
    }
}
