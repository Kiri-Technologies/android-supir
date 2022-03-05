package com.kiri.common.domain

import com.kiri.common.data.pref.PrefRepository

class PrefUseCase(private val repository: PrefRepository) : PrefUseCaseImpl {

    override var firstStart: Boolean
        get() = repository.firstStart
        set(value) {
            repository.firstStart = value
        }
    override var token: String?
        get() = repository.token
        set(value) {
            repository.token = value
        }

    override fun removeByKey(key: String) {
        repository.removeByKey(key)
    }
}
