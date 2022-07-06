package com.kiri.common.data.pref

interface PrefRepository {
    var firstStart: Boolean
    var token: String?
    var accountData: String?
    var angkotId: String?
    var routeId: String?

    fun removeByKey(key: String)
}
