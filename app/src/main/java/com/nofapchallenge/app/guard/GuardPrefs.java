package com.nofapchallenge.app.guard;

import android.content.Context;
import android.content.SharedPreferences;

/** Focus Guard + uninstall-protection ki settings. */
public class GuardPrefs {
    private static final String P = "NoFapGuard";
    private static final String K_GUARD = "guard_on";
    private static final String K_BLOCK_PREFIX = "block_";
    private static final String K_COOL_UNTIL = "cool_until";

    /** 24 ghante ki cooling (millis). */
    public static final long COOL_MS = 24L * 60 * 60 * 1000;

    private static SharedPreferences p(Context c) {
        return c.getSharedPreferences(P, Context.MODE_PRIVATE);
    }

    public static boolean isGuardOn(Context c) {
        return p(c).getBoolean(K_GUARD, false);
    }

    public static void setGuardOn(Context c, boolean on) {
        p(c).edit().putBoolean(K_GUARD, on).apply();
    }

    public static boolean isAppBlocked(Context c, String pkg) {
        return p(c).getBoolean(K_BLOCK_PREFIX + pkg, true); // default: sab block
    }

    public static void setAppBlocked(Context c, String pkg, boolean blocked) {
        p(c).edit().putBoolean(K_BLOCK_PREFIX + pkg, blocked).apply();
    }

    /** Cooling shuru karo — ab se 24 ghante baad hi protection hat sakegi. */
    public static void startCooling(Context c) {
        p(c).edit().putLong(K_COOL_UNTIL, System.currentTimeMillis() + COOL_MS).apply();
    }

    /** Kitne ghante baaki hain (0 = cooling nahi chal rahi). */
    public static long coolingHoursLeft(Context c) {
        long until = p(c).getLong(K_COOL_UNTIL, 0);
        long left = until - System.currentTimeMillis();
        if (left <= 0) {
            if (until != 0) p(c).edit().remove(K_COOL_UNTIL).apply();
            return 0;
        }
        return (left + 3599999) / 3600000; // round up
    }

    public static void clearCooling(Context c) {
        p(c).edit().remove(K_COOL_UNTIL).apply();
    }
}
