package com.kiri.common.data.pref

interface PrefRepository {
    var firstStart: Boolean
    var token: String?
    var accountData: String?
    var isRidingAngkot: Boolean

    fun removeByKey(key: String)
}
