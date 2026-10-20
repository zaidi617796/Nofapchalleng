package com.nofapchallenge.app.guard;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

import com.nofapchallenge.app.GuardBlockActivity;

/**
 * Focus Guard: jab koi blocked app foreground me aaye to NoFap ki
 * block screen khol do. Accessibility permission user khud deta hai.
 */
public class FocusGuardService extends AccessibilityService {

    /** Block honay wali apps (package names). */
    public static final String[] BLOCKED_PKGS = {
            "com.instagram.android",       // Instagram
            "com.google.android.youtube",  // YouTube (Shorts)
            "com.zhiliaoapp.musically",    // TikTok
            "com.ss.android.ugc.trill",    // TikTok (alt)
            "com.facebook.katana",         // Facebook
            "com.twitter.android",         // X (Twitter)
    };

    public static final String[] BLOCKED_NAMES = {
            "Instagram", "YouTube", "TikTok", "TikTok", "Facebook", "X"
    };

    /** Taake block screen khud ko dobara trigger na kare. */
    private String lastBlocked = "";
    private long lastBlockAt = 0;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return;
        if (!GuardPrefs.isGuardOn(this)) return;
        CharSequence cs = event.getPackageName();
        if (cs == null) return;
        String pkg = cs.toString();

        // Apni app ko kabhi block na karo
        if (pkg.equals(getPackageName())) return;

        for (String b : BLOCKED_PKGS) {
            if (b.equals(pkg) && GuardPrefs.isAppBlocked(this, pkg)) {
                long now = System.currentTimeMillis();
                if (pkg.equals(lastBlocked) && now - lastBlockAt < 2000) return; // debounce
                lastBlocked = pkg;
                lastBlockAt = now;
                Intent i = new Intent(this, GuardBlockActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("blocked_pkg", pkg);
                try {
                    startActivity(i);
                } catch (Exception ignored) { }
                return;
            }
        }
    }

    @Override
    public void onInterrupt() { }
}
