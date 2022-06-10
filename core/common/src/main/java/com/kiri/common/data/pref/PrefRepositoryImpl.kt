package com.kiri.common.data.pref

import androidx.core.content.edit

class PrefRepositoryImpl(private val sharedPref: SharedPref) : PrefRepository {

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
    override var accountData: String?
        get() = sharedPref.preferences.getString(PrefKey.PROFILE, "{}")
        set(value) {
            sharedPref.preferences.edit {
                putString(PrefKey.PROFILE, value)
            }
        }
    override var isRidingAngkot: Boolean
        get() = sharedPref.preferences.getBoolean(PrefKey.IS_RIDING_ANGKOT, false)
        set(value) {
            sharedPref.preferences.edit {
                putBoolean(PrefKey.IS_RIDING_ANGKOT, value)
            }
        }

    override fun removeByKey(key: String) {
        sharedPref.preferences.edit {
            remove(key)
        }
    }
}
