package com.kiri.trip.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kiri.common.utils.BaseApiResponse
import com.kiri.common.utils.Resource
import com.kiri.common.utils.ResourceFb
import com.kiri.trip.BuildConfig
import com.kiri.trip.data.endpoint.RemoteDataSource
import com.kiri.trip.data.models.AngkotConfirmData
import com.kiri.trip.data.models.AngkotDistanceData
import com.kiri.trip.data.models.CreateHistoryData
import com.kiri.trip.data.models.EarningsByTodayData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.LocationBody
import com.kiri.trip.data.models.PremiumData
import com.kiri.trip.data.models.RiwayatNarikData
import com.kiri.trip.data.models.RoutesData
import com.kiri.trip.data.models.SetWayBody
import com.kiri.trip.data.models.ToggleFullBody
import com.kiri.trip.data.models.ToggleStopBody
import com.kiri.trip.data.models.TotalEarningsData
import com.kiri.trip.data.models.TripHistoryData
import com.kiri.trip.data.models.UserAngkot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AngkotRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : AngkotRepository,
    BaseApiResponse() {

    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference

    override suspend fun getTripHistory(sopirId: String): Flow<Resource<List<TripHistoryData>>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getTripHistory(sopirId) })
        }
    }

    override suspend fun getTripByAngkot(
        is_done: Int,
        angkotId: String,
        supirId: String
    ): Flow<Resource<List<TripHistoryData>>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getTripByAngkot(is_done, angkotId, supirId) })
        }
    }

    override suspend fun getFeedbackByTripId(tripId: String): Flow<Resource<FeedbackData>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getFeedbackByTripId(tripId) })
        }
    }

    override suspend fun getAngkot(supirId: String): Flow<Resource<List<AngkotConfirmData>>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getAngkot(supirId) })
        }
    }

    override suspend fun getRideHistory(
        angkotId: String,
        supirId: String
    ): Flow<Resource<List<RiwayatNarikData>>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getRideHistory(angkotId, supirId) })
        }
    }

    override suspend fun getListAngkotConfirmation(supirId: String): Flow<Resource<List<Nothing>>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getListAngkotConfirmation(supirId) })
        }
    }

    override suspend fun doConfirmAngkot(
        id: String,
        isConfirmed: Int
    ): Flow<Resource<Nothing>> {
        return flow {
            emit(
                safeApiCall {
                    remoteDataSource.doConfirmAngkot(
                        id,
                        isConfirmed
                    )
                }
            )
        }
    }

    override suspend fun getTotalEarnings(
        angkotId: String,
        supirId: String
    ): Flow<Resource<TotalEarningsData>> {
        return flow { emit(safeApiCall { remoteDataSource.getTotalEarnings(angkotId, supirId) }) }
    }

    override suspend fun getTodayEarning(
        angkotId: String,
        supirId: String
    ): Flow<Resource<Int>> {
        return flow { emit(safeApiCall { remoteDataSource.getTodayEarning(angkotId, supirId) }) }
    }

    override suspend fun getAvgUser(supirId: String): Flow<Resource<Int>> {
        return flow { emit(safeApiCall { remoteDataSource.getAvgUser(supirId) }) }
    }

    override suspend fun getUserToday(supirId: String): Flow<Resource<Int>> {
        return flow { emit(safeApiCall { remoteDataSource.getUserToday(supirId) }) }
    }

    override suspend fun getEarningsByToday(
        angkotId: String,
        supirId: String
    ): Flow<Resource<EarningsByTodayData>> {
        return flow { emit(safeApiCall { remoteDataSource.getEarningsByToday(angkotId, supirId) }) }
    }

    override suspend fun createEarningNote(
        historyId: String,
        finishRide: String?,
        earnings: Int?
    ): Flow<Resource<Nothing>> {
        return flow {
            emit(
                safeApiCall {
                    remoteDataSource.createEarningNote(
                        historyId, finishRide, earnings
                    )
                }
            )
        }
    }

    override suspend fun getRoutesById(angkotId: String): Flow<Resource<RoutesData>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getRoutesById(angkotId) })
        }
    }

    override fun getAngkotDistance(angkotId: String): MutableLiveData<ResourceFb<AngkotDistanceData>> {
        val angkotDistanceData = rootRef.child("jarak_antar_angkot").child("angkot_$angkotId")

        val mLiveData = MutableLiveData<ResourceFb<AngkotDistanceData>>()
        mLiveData.value = ResourceFb.loading()
        angkotDistanceData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val data = snapshot.getValue(AngkotDistanceData::class.java)
                    mLiveData.value = ResourceFb.success(data!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                mLiveData.value = ResourceFb.error(error.message)
            }
        })

        return mLiveData
    }

    override suspend fun statusAngkot(
        angkotId: String,
        is_Beroperasi: String,
        supirId: String?
    ): Flow<Resource<Nothing>> {
        return flow {
            emit(
                safeApiCall {
                    remoteDataSource.statusAngkot(
                        angkotId,
                        is_Beroperasi,
                        supirId
                    )
                }
            )
        }
    }

    override suspend fun createHistory(
        supirId: String,
        angkotId: String,
        rideTime: String
    ): Flow<Resource<CreateHistoryData>> {
        return flow {
            emit(safeApiCall { remoteDataSource.createHistory(supirId, angkotId, rideTime) })
        }
    }

    override suspend fun setWayMaps(body: SetWayBody): Flow<Resource<Nothing>> {
        return flow {
            emit(
                safeApiCall {
                    remoteDataSource.setWayMaps(
                        "${BuildConfig.BASE_URL_MAPS}tarikangkot",
                        body
                    )
                }
            )
        }
    }

    override suspend fun setLocation(body: LocationBody): Flow<Resource<Nothing>> {
        return flow {
            emit(
                safeApiCall {
                    remoteDataSource.setLocation(
                        "${BuildConfig.BASE_URL_MAPS}setlocation",
                        body
                    )
                }
            )
        }
    }

    override suspend fun toggleStop(body: ToggleStopBody): Flow<Resource<Nothing>> {
        return flow {
            emit(
                safeApiCall {
                    remoteDataSource.toggleStop(
                        "${BuildConfig.BASE_URL_MAPS}togglestop",
                        body
                    )
                }
            )
        }
    }

    override suspend fun toggleFull(body: ToggleFullBody): Flow<Resource<Nothing>> {
        return flow {
            emit(
                safeApiCall {
                    remoteDataSource.toggleFull(
                        "${BuildConfig.BASE_URL_MAPS}togglefull",
                        body
                    )
                }
            )
        }
    }

    override fun getUserAngkotRide(angkotId: String): MutableLiveData<ResourceFb<MutableList<UserAngkot>>> {
        val angkotDistanceData =
            rootRef.child("penumpang_naik_turun")
                .child("angkot_$angkotId")
                .child("naik")

        val titikNaik: MutableList<UserAngkot> = mutableListOf()
        val idNaik: MutableList<Int> = mutableListOf()
        val mLiveData = MutableLiveData<ResourceFb<MutableList<UserAngkot>>>()
        mLiveData.value = ResourceFb.loading()
        angkotDistanceData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                titikNaik.clear()
                for (childData in snapshot.children) {
                    val data = childData.getValue(UserAngkot::class.java)
                    if (data != null) {
                        if (!idNaik.contains(data.id_titik_naik)) {
                            idNaik.add(data.id_titik_naik)
                            titikNaik.add(data)
                        }
                    }
                }
                mLiveData.value = ResourceFb.success(titikNaik)
            }

            override fun onCancelled(error: DatabaseError) {
                mLiveData.value = ResourceFb.error(error.message)
            }
        })

        return mLiveData
    }

    override fun getUserAngkotDrop(angkotId: String): MutableLiveData<ResourceFb<MutableList<UserAngkot>>> {
        val angkotDistanceData =
            rootRef.child("penumpang_naik_turun")
                .child("angkot_$angkotId")
                .child("turun")

        val titikTurun: MutableList<UserAngkot> = mutableListOf()
        val idTurun: MutableList<Int> = mutableListOf()
        val mLiveData = MutableLiveData<ResourceFb<MutableList<UserAngkot>>>()
        mLiveData.value = ResourceFb.loading()
        angkotDistanceData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                titikTurun.clear()
                for (childData in snapshot.children) {
                    val data = childData.getValue(UserAngkot::class.java)
                    if (data != null) {
                        if (!idTurun.contains(data.id_titik_turun)) {
                            idTurun.add(data.id_titik_turun)
                            titikTurun.add(data)
                        }
                    }
                }
                mLiveData.value = ResourceFb.success(titikTurun)
            }

            override fun onCancelled(error: DatabaseError) {
                mLiveData.value = ResourceFb.error(error.message)
            }
        })

        return mLiveData
    }

    override suspend fun premium(supirId: String): Flow<Resource<PremiumData>> {
        return flow { emit(safeApiCall { remoteDataSource.premium(supirId) }) }
    }
}
