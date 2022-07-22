package com.kiri.common.data.pref

interface PrefRepository {
    var firstStart: Boolean
    var token: String?
    var accountData: String?
    var angkotId: String?
    var routeId: String?
    var histoyId: String?

    fun removeByKey(key: String)
}
