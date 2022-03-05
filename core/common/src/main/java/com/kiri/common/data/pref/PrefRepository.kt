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

    override var token: String?
        get() = sharedPref.preferences.getString(PrefKey.TOKEN, null)
        set(value) {
            sharedPref.preferences.edit {
                putString(PrefKey.TOKEN, value)
            }
        }

    override fun removeByKey(key: String) {
        sharedPref.preferences.edit {
            remove(key)
        }
    }
}
