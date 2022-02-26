package com.kiri.common.data.pref

import androidx.core.content.edit

class PrefRepository(private val sharedPref: SharedPref) : PrefRepositoryImpl {

    override var firstStart: Boolean
        get() = sharedPref.preferences.getBoolean(PrefKey.FIRSTTIME, true)
        set(value) {
            sharedPref.preferences.edit {
                putBoolean(PrefKey.FIRSTTIME, value)
            }
        }
}
