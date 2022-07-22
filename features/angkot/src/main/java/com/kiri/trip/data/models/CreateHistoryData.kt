package com.kiri.trip.data.models

import com.google.gson.annotations.SerializedName

data class CreateHistoryData(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("mulai_narik")
	val mulaiNarik: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("angkot_id")
	val angkotId: String? = null
)
