package com.kiri.trip.domain.usecase.models

import com.kiri.trip.data.models.TotalEarningsData

data class TotalEarningsDomain(
    val totalPendapatan: Int
) {
    constructor(data: TotalEarningsData) : this(
        data.totalPendapatan ?: 0
    )
}
