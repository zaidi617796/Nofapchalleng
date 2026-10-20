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

/** Daleel: verified Qur'an/hadith references + disclaimers. */
public class DaleelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.menu_daleel));

        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20), dp(20), dp(20), dp(20));
        root.setBackgroundColor(0xFF0F172A);
        scroll.addView(root, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setContentView(scroll);

        // R1 — Surah An-Nur 24:30
        root.addView(refCard(
                getString(R.string.ref1_title),
                NoFapData.R1_AR, true,
                NoFapData.R1_UR,
                NoFapData.R1_EN,
                getString(R.string.ref1_use)));

        // R2 — Sahih Muslim 1400
        root.addView(refCard(
                getString(R.string.ref2_title),
                null, false,
                NoFapData.R2_UR,
                NoFapData.R2_EN,
                getString(R.string.ref2_use)));

        // R4 — Tirmidhi (relapse)
        root.addView(refCard(
                getString(R.string.ref4_title),
                null, false,
                NoFapData.R4_UR,
                NoFapData.R4_EN,
                getString(R.string.ref4_use)));

        TextView disc1 = new TextView(this);
        disc1.setText(NoFapData.DISCLAIMER_MEDICAL);
        disc1.setTextSize(12);
        disc1.setTextColor(0xFF94A3B8);
        LinearLayout.LayoutParams l1 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        l1.topMargin = dp(20);
        root.addView(disc1, l1);

        TextView disc2 = new TextView(this);
        disc2.setText(NoFapData.DISCLAIMER_RELIGIOUS);
        disc2.setTextSize(12);
        disc2.setTextColor(0xFF94A3B8);
        LinearLayout.LayoutParams l2 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        l2.topMargin = dp(8);
        root.addView(disc2, l2);
    }

    private LinearLayout refCard(String title, String arabic, boolean showArabic,
                                 String ur, String en, String use) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundColor(0xFF1E293B);
        card.setPadding(dp(16), dp(14), dp(16), dp(14));
        LinearLayout.LayoutParams clp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        clp.topMargin = dp(10);
        card.setLayoutParams(clp);

        TextView t = new TextView(this);
        t.setText(title);
        t.setTextSize(17);
        t.setTypeface(null, Typeface.BOLD);
        t.setTextColor(0xFFFBBF24);
        card.addView(t);

        if (showArabic && arabic != null) {
            TextView ar = new TextView(this);
            ar.setText(arabic);
            ar.setTextSize(19);
            ar.setTextColor(0xFFFFFFFF);
            ar.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams alp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            alp.topMargin = dp(10);
            card.addView(ar, alp);
        }

        TextView u = new TextView(this);
        u.setText(ur);
        u.setTextSize(14);
        u.setTextColor(0xFFE2E8F0);
        LinearLayout.LayoutParams ulp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ulp.topMargin = dp(10);
        card.addView(u, ulp);

        TextView e = new TextView(this);
        e.setText(en);
        e.setTextSize(12);
        e.setTextColor(0xFF94A3B8);
        LinearLayout.LayoutParams elp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        elp.topMargin = dp(6);
        card.addView(e, elp);

        TextView us = new TextView(this);
        us.setText(use);
        us.setTextSize(12);
        us.setTypeface(null, Typeface.ITALIC);
        us.setTextColor(0xFF86EFAC);
        LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        slp.topMargin = dp(8);
        card.addView(us, slp);

        return card;
    }

    private int dp(int v) {
        return Math.round(v * getResources().getDisplayMetrics().density);
    }
}
