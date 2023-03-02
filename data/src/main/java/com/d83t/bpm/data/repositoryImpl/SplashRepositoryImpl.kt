package com.d83t.bpm.data.repositoryImpl

import com.d83t.bpm.data.datastore.DataStoreManager
import com.d83t.bpm.data.model.request.UserVerificationRequest
import com.d83t.bpm.data.model.response.SignUpResponse.Companion.toDataModel
import com.d83t.bpm.data.network.BPMResponse
import com.d83t.bpm.data.network.BPMResponseHandler
import com.d83t.bpm.data.network.ErrorResponse.Companion.toDataModel
import com.d83t.bpm.data.network.MainApi
import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.UserInfo
import com.d83t.bpm.domain.repository.SplashRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class SplashRepositoryImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val mainApi: MainApi
) : SplashRepository {

    override fun getKakaoUserId(): Flow<Long?> {
        return dataStoreManager.getKakaoUserId()
    }

    override suspend fun setKakaoUserId(kakaoUserId: Long): Flow<Long?> {
        return dataStoreManager.setKakaoUserId(kakaoUserId)
    }

    override fun getUserToken(): Flow<String?> {
        return dataStoreManager.getUserToken()
    }

    override suspend fun setUserToken(userToken: String): Flow<String?> {
        return dataStoreManager.setUserToken(userToken)
    }

    override suspend fun sendSignIn(kakaoUserId: Long): Flow<ResponseState<UserInfo>> {
        return flow {
            BPMResponseHandler().handle {
                mainApi.sendKakaoUserIdVerification(UserVerificationRequest(kakaoUserId))
            }.onEach { result ->
                when (result) {
                    is BPMResponse.Success -> emit(ResponseState.Success(result.data.toDataModel()))
                    is BPMResponse.Error -> emit(ResponseState.Error(result.error.toDataModel()))
                }
            }.collect()
        }
    }
}