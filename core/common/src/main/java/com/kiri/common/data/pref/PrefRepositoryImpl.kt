package com.kiri.common.data.pref

interface PrefRepositoryImpl {
    var firstStart: Boolean
    var token: String?

    fun removeByKey(key: String)
}
