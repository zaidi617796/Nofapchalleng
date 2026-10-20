package com.nofapchallenge.app.guard;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Uninstall protection: device admin hone se uninstall se pehle
 * Settings me admin deactivate karna parta hai (friction, rok nahi).
 */
public class GuardAdminReceiver extends DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {
        GuardPrefs.clearCooling(context);
    }
}
