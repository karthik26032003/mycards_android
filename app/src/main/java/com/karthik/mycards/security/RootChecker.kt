package com.karthik.mycards.security

import android.content.Context
import android.os.Build

/**
 * Best-effort root detection. No client-side check is bulletproof (a
 * sufficiently determined rooted device can hide from all of these), but
 * this is the conventional layer used industry-wide short of a paid
 * server-side attestation service like Google's Play Integrity API.
 */
object RootChecker {

    private val ROOT_BINARY_PATHS = listOf(
        "/system/bin/su",
        "/system/xbin/su",
        "/sbin/su",
        "/system/su",
        "/vendor/bin/su",
        "/system/bin/.ext/.su",
        "/system/usr/we-need-root/su"
    )

    private val ROOT_APP_PACKAGES = listOf(
        "com.topjohnwu.magisk",       // Magisk Manager
        "com.noshufou.android.su",    // Superuser
        "com.koushikdutta.superuser",
        "eu.chainfire.supersu",       // SuperSU
        "com.thirdparty.superuser"
    )

    fun isDeviceRooted(context: Context): Boolean =
        hasTestKeysBuildTag() || hasRootBinary() || hasRootManagementApp(context)

    private fun hasTestKeysBuildTag(): Boolean =
        Build.TAGS?.contains("test-keys") == true

    private fun hasRootBinary(): Boolean =
        ROOT_BINARY_PATHS.any { java.io.File(it).exists() }

    private fun hasRootManagementApp(context: Context): Boolean {
        val pm = context.packageManager
        return ROOT_APP_PACKAGES.any { pkg ->
            try {
                pm.getPackageInfo(pkg, 0)
                true
            } catch (e: android.content.pm.PackageManager.NameNotFoundException) {
                false
            }
        }
    }
}
