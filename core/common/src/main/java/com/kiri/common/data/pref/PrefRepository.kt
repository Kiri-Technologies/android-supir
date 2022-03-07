package com.kiri.common.data.pref

interface PrefRepository {
    var firstStart: Boolean
    var token: String?
    var accountData: String?

    fun removeByKey(key: String)
}
