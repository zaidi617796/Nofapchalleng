package com.nofapchallenge.app;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nofapchallenge.app.data.NoFapData;

/** SOS: dua + 5 actions (timer ke saath) + calm-down reminder. */
public class SosActivity extends AppCompatActivity {

    private CountDownTimer timer;
    private TextView timerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.menu_sos));

        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20), dp(20), dp(20), dp(20));
        root.setBackgroundColor(0xFF0F172A);
        scroll.addView(root, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setContentView(scroll);

        TextView head = sectionHead(getString(R.string.sos_dua_title));
        root.addView(head);

        TextView duaAr = new TextView(this);
        duaAr.setText(NoFapData.DUA_AR);
        duaAr.setTextSize(20);
        duaAr.setTextColor(0xFFFFFFFF);
        duaAr.setGravity(Gravity.CENTER);
        duaAr.setBackgroundColor(0xFF1E293B);
        duaAr.setPadding(dp(16), dp(16), dp(16), dp(16));
        root.addView(duaAr);

        TextView duaUr = new TextView(this);
        duaUr.setText(NoFapData.DUA_UR);
        duaUr.setTextSize(14);
        duaUr.setTextColor(0xFFCBD5E1);
        LinearLayout.LayoutParams ulp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ulp.topMargin = dp(10);
        root.addView(duaUr, ulp);

        TextView duaEn = new TextView(this);
        duaEn.setText(NoFapData.DUA_EN);
        duaEn.setTextSize(12);
        duaEn.setTextColor(0xFF94A3B8);
        LinearLayout.LayoutParams elp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        elp.topMargin = dp(6);
        root.addView(duaEn, elp);

        TextView grad = new TextView(this);
        grad.setText(getString(R.string.sos_dua_grading));
        grad.setTextSize(11);
        grad.setTextColor(0xFF64748B);
        LinearLayout.LayoutParams glp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        glp.topMargin = dp(6);
        glp.bottomMargin = dp(16);
        root.addView(grad, glp);

        root.addView(sectionHead(getString(R.string.sos_actions_title)));

        for (String action : NoFapData.SOS_ACTIONS) {
            TextView a = new TextView(this);
            a.setText("\u2022  " + action);
            a.setTextSize(15);
            a.setTextColor(0xFFE2E8F0);
            a.setBackgroundColor(0xFF1E293B);
            a.setPadding(dp(14), dp(12), dp(14), dp(12));
            LinearLayout.LayoutParams alp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            alp.topMargin = dp(8);
            root.addView(a, alp);
        }

        // 5-minute timer
        timerView = new TextView(this);
        timerView.setText("05:00");
        timerView.setTextSize(40);
        timerView.setTypeface(null, Typeface.BOLD);
        timerView.setTextColor(0xFF22C55E);
        timerView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tlp.topMargin = dp(20);
        root.addView(timerView, tlp);

        Button timerBtn = new Button(this);
        timerBtn.setText(getString(R.string.sos_timer_start));
        timerBtn.setTextColor(0xFFFFFFFF);
        timerBtn.setBackgroundColor(0xFF16A34A);
        timerBtn.setAllCaps(false);
        timerBtn.setOnClickListener(v -> startTimer());
        LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        blp.topMargin = dp(8);
        blp.bottomMargin = dp(16);
        root.addView(timerBtn, blp);

        TextView calm = new TextView(this);
        calm.setText(NoFapData.SOS_CALM);
        calm.setTextSize(15);
        calm.setTypeface(null, Typeface.BOLD);
        calm.setTextColor(0xFFFBBF24);
        calm.setGravity(Gravity.CENTER);
        calm.setBackgroundColor(0xFF1E293B);
        calm.setPadding(dp(16), dp(16), dp(16), dp(16));
        root.addView(calm);
    }

    private void startTimer() {
        if (timer != null) timer.cancel();
        timer = new CountDownTimer(5 * 60 * 1000, 1000) {
            @Override
            public void onTick(long ms) {
                long s = ms / 1000;
                timerView.setText(String.format("%02d:%02d", s / 60, s % 60));
            }

            @Override
            public void onFinish() {
                timerView.setText(getString(R.string.sos_timer_done));
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        if (timer != null) timer.cancel();
        super.onDestroy();
    }

    private TextView sectionHead(String text) {
        TextView t = new TextView(this);
        t.setText(text);
        t.setTextSize(20);
        t.setTypeface(null, Typeface.BOLD);
        t.setTextColor(0xFFFFFFFF);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = dp(10);
        t.setLayoutParams(lp);
        return t;
    }

    private int dp(int v) {
        return Math.round(v * getResources().getDisplayMetrics().density);
    }
}
