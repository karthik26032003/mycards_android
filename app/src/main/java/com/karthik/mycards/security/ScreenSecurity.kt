package com.karthik.mycards.security

import android.view.WindowManager
import androidx.activity.ComponentActivity

/**
 * Blocks screenshots/screen recording of this Activity (FLAG_SECURE also
 * blanks its thumbnail in the recent-apps switcher), and tells Android to
 * silently drop touches whenever another window is drawn on top of ours —
 * the standard defense against "overlay" (tapjacking) attacks, where a
 * malicious transparent app tries to capture taps meant for this one.
 */
fun ComponentActivity.enableScreenSecurity() {
    window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
    window.decorView.filterTouchesWhenObscured = true
}
