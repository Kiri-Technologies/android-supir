package com.kiri.trip.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kiri.common.utils.BaseApiResponse
import com.kiri.common.utils.Resource
import com.kiri.trip.data.endpoint.RemoteDataSource
import com.kiri.trip.data.models.AngkotConfirmData
import com.kiri.trip.data.models.AngkotData
import com.kiri.trip.data.models.EarningsByTodayData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.RiwayatNarikData
import com.kiri.trip.data.models.RoutesData
import com.kiri.trip.data.models.TotalEarningsData
import com.kiri.trip.data.models.TripHistoryData
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
        earningId: String,
        earnings: Int
    ): Flow<Resource<Nothing>> {
        return flow {
            emit(
                safeApiCall {
                    remoteDataSource.createEarningNote(
                        earningId,
                        earnings
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

    override suspend fun getAngkotDistance(): MutableLiveData<Resource<AngkotData>> {
//        val angkotDistanceData: DatabaseReference =
//            rootRef.child("jarak_antar_angkot").child("angkot_${pref.angkotId}")
//
        val mLiveData = MutableLiveData<Resource<AngkotData>>()
//        mLiveData.postValue(Resource.loading(null))
//        angkotDistanceData.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()) {
//                    val data = snapshot.value as AngkotData
//                    mLiveData.postValue(Resource.success(null))
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                mLiveData.postValue(Resource.error(error.message))
//            }
//        })
//
        return mLiveData
    }
}
