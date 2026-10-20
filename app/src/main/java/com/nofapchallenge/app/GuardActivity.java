package com.nofapchallenge.app;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nofapchallenge.app.guard.FocusGuardService;
import com.nofapchallenge.app.guard.GuardAdminReceiver;
import com.nofapchallenge.app.guard.GuardPrefs;

import java.util.List;

/** Focus Guard + uninstall protection ki settings. */
public class GuardActivity extends AppCompatActivity {

    private LinearLayout root;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.guard_title));
        buildUi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        buildUi(); // permission status refresh
    }

    private void buildUi() {
        ScrollView scroll = new ScrollView(this);
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        int pad = dp(18);
        root.setPadding(pad, pad, pad, pad);
        root.setBackgroundColor(0xFF0F172A);
        scroll.addView(root, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setContentView(scroll);

        boolean serviceOn = isServiceEnabled();
        boolean guardOn = GuardPrefs.isGuardOn(this);

        // ===== Focus Guard section =====
        root.addView(title(getString(R.string.guard_title)));
        root.addView(body(getString(R.string.guard_desc)));

        TextView status = new TextView(this);
        if (guardOn && serviceOn) status.setText(getString(R.string.guard_status_on));
        else if (guardOn) status.setText(getString(R.string.guard_status_no_perm));
        else status.setText(getString(R.string.guard_status_off));
        status.setTextSize(15);
        status.setTypeface(null, Typeface.BOLD);
        status.setTextColor(guardOn && serviceOn ? 0xFF22C55E : 0xFFF59E0B);
        status.setPadding(0, dp(10), 0, dp(4));
        root.addView(status);

        if (!serviceOn) {
            Button enable = btn(getString(R.string.guard_enable_perm), 0xFF0284C7);
            enable.setOnClickListener(v -> {
                Toast.makeText(this, R.string.guard_perm_hint, Toast.LENGTH_LONG).show();
                try {
                    startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                } catch (Exception ignored) { }
            });
            root.addView(withMargin(enable, 8));
        }

        // Master toggle — OFF karne par hold-to-unlock friction
        Button toggle = btn(guardOn
                ? getString(R.string.guard_turn_off)
                : getString(R.string.guard_turn_on), guardOn ? 0xFFDC2626 : 0xFF16A34A);
        if (!guardOn) {
            toggle.setOnClickListener(v -> {
                GuardPrefs.setGuardOn(this, true);
                buildUi();
            });
        } else {
            toggle.setText(getString(R.string.guard_hold_off));
            attachHoldToUnlock(toggle);
        }
        root.addView(withMargin(toggle, 8));

        // Per-app toggles
        root.addView(subtitle(getString(R.string.guard_apps_title)));
        String last = "";
        for (int i = 0; i < FocusGuardService.BLOCKED_PKGS.length; i++) {
            String pkg = FocusGuardService.BLOCKED_PKGS[i];
            String name = FocusGuardService.BLOCKED_NAMES[i];
            if (name.equals(last)) continue; // TikTok duplicate skip
            last = name;
            CheckBox cb = new CheckBox(this);
            cb.setText(name);
            cb.setTextSize(16);
            cb.setTextColor(0xFFE2E8F0);
            cb.setChecked(GuardPrefs.isAppBlocked(this, pkg));
            final String p = pkg;
            cb.setOnCheckedChangeListener((b, checked) -> GuardPrefs.setAppBlocked(this, p, checked));
            root.addView(cb);
        }

        // ===== Uninstall protection section =====
        root.addView(title(getString(R.string.admin_title)));
        root.addView(body(getString(R.string.admin_desc)));

        DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName admin = new ComponentName(this, GuardAdminReceiver.class);
        boolean adminOn = dpm != null && dpm.isAdminActive(admin);

        if (adminOn) {
            TextView as = new TextView(this);
            as.setText(getString(R.string.admin_status_on));
            as.setTextSize(15);
            as.setTypeface(null, Typeface.BOLD);
            as.setTextColor(0xFF22C55E);
            root.addView(withMargin(as, 8));

            long hours = GuardPrefs.coolingHoursLeft(this);
            if (hours > 0) {
                TextView cool = new TextView(this);
                cool.setText(getString(R.string.admin_cooling, (int) hours));
                cool.setTextSize(14);
                cool.setTextColor(0xFFF59E0B);
                root.addView(withMargin(cool, 6));
            } else {
                Button rm = btn(getString(R.string.admin_request_remove), 0xFF475569);
                rm.setOnClickListener(v -> new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.admin_remove_title))
                        .setMessage(getString(R.string.admin_remove_msg))
                        .setPositiveButton(getString(R.string.admin_remove_yes), (d, w) -> {
                            GuardPrefs.startCooling(this);
                            buildUi();
                        })
                        .setNegativeButton(getString(R.string.reset_no), null)
                        .show());
                root.addView(withMargin(rm, 8));
            }
        } else {
            Button en = btn(getString(R.string.admin_enable), 0xFF7C3AED);
            en.setOnClickListener(v -> {
                Intent i = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                i.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin);
                i.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                        getString(R.string.admin_explain));
                try {
                    startActivity(i);
                } catch (Exception ignored) { }
            });
            root.addView(withMargin(en, 8));
        }

        root.addView(body(getString(R.string.admin_honest)));
    }

    /** Guard OFF karne ke liye 3 second hold — jaldi wala trigger rokne ke liye. */
    private void attachHoldToUnlock(Button b) {
        final long[] downAt = {0};
        b.setOnTouchListener((v, e) -> {
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                downAt[0] = System.currentTimeMillis();
                b.setText(getString(R.string.guard_holding));
                handler.postDelayed(() -> {
                    if (downAt[0] != 0) {
                        downAt[0] = 0;
                        GuardPrefs.setGuardOn(this, false);
                        Toast.makeText(this, R.string.guard_off_toast, Toast.LENGTH_SHORT).show();
                        buildUi();
                    }
                }, 3000);
                return true;
            } else if (e.getAction() == MotionEvent.ACTION_UP
                    || e.getAction() == MotionEvent.ACTION_CANCEL) {
                downAt[0] = 0;
                b.setText(getString(R.string.guard_hold_off));
                return true;
            }
            return false;
        });
    }

    private boolean isServiceEnabled() {
        AccessibilityManager am = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (am == null) return false;
        List<AccessibilityServiceInfo> list =
                am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
        for (AccessibilityServiceInfo info : list) {
            if (info.getResolveInfo().serviceInfo.name.equals(FocusGuardService.class.getName())) {
                return true;
            }
        }
        return false;
    }

    private TextView title(String t) {
        TextView tv = new TextView(this);
        tv.setText(t);
        tv.setTextSize(20);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextColor(0xFFFFFFFF);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = dp(14);
        lp.bottomMargin = dp(4);
        tv.setLayoutParams(lp);
        return tv;
    }

    private TextView subtitle(String t) {
        TextView tv = new TextView(this);
        tv.setText(t);
        tv.setTextSize(16);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextColor(0xFFFBBF24);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = dp(14);
        lp.bottomMargin = dp(4);
        tv.setLayoutParams(lp);
        return tv;
    }

    private TextView body(String t) {
        TextView tv = new TextView(this);
        tv.setText(t);
        tv.setTextSize(13);
        tv.setTextColor(0xFF94A3B8);
        return tv;
    }

    private Button btn(String t, int color) {
        Button b = new Button(this);
        b.setText(t);
        b.setTextSize(15);
        b.setTextColor(0xFFFFFFFF);
        b.setBackgroundColor(color);
        b.setAllCaps(false);
        b.setPadding(dp(10), dp(13), dp(10), dp(13));
        return b;
    }

    private android.view.View withMargin(android.view.View v, int topDp) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = dp(topDp);
        v.setLayoutParams(lp);
        return v;
    }

    private int dp(int v) {
        return Math.round(v * getResources().getDisplayMetrics().density);
    }
}
