package com.karthik.mycards

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Custom Application class. @HiltAndroidApp switches on Hilt's
 * dependency-injection system for the whole app.
 * Registered in AndroidManifest.xml via android:name.
 */
@HiltAndroidApp
class MyCardsApp : Application()
