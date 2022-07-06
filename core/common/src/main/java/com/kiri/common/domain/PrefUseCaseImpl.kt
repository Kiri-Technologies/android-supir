package com.kiri.common.domain

import com.kiri.common.data.pref.PrefRepositoryImpl

class PrefUseCaseImpl(private val repositoryImpl: PrefRepositoryImpl) : PrefUseCase {

    override var firstStart: Boolean
        get() = repositoryImpl.firstStart
        set(value) {
            repositoryImpl.firstStart = value
        }
    override var token: String?
        get() = repositoryImpl.token
        set(value) {
            repositoryImpl.token = value
        }
    override var accountData: String?
        get() = repositoryImpl.accountData
        set(value) {
            repositoryImpl.accountData = value
        }
    override var angkotId: String?
        get() = repositoryImpl.angkotId
        set(value) {
            repositoryImpl.angkotId = value
        }
    override var routeId: String?
        get() = repositoryImpl.routeId
        set(value) {
            repositoryImpl.routeId = value
        }

    override fun removeByKey(key: String) {
        repositoryImpl.removeByKey(key)
    }
}
