package com.d83t.bpm.data.repositoryImpl

import com.d83t.bpm.data.model.SignUpResponse.Companion.toDataModel
import com.d83t.bpm.data.network.BPMResponse
import com.d83t.bpm.data.network.BPMResponseHandler
import com.d83t.bpm.data.network.ErrorResponse.Companion.toDataModel
import com.d83t.bpm.data.network.MainApi
import com.d83t.bpm.data.util.createImageMultipartBody
import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.UserInfo
import com.d83t.bpm.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import java.io.File
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val mainApi: MainApi
) : SignUpRepository {
    override suspend fun sendSignUp(
        kakaoId: Long,
        nickname: String,
        bio: String,
        image: File
    ): Flow<ResponseState<UserInfo>> {
        return flow {
            BPMResponseHandler().handle {
                mainApi.signUp(
                    kakaoId = kakaoId,
                    nickname = nickname,
                    bio = bio,
                    file = createImageMultipartBody(
                        key = "file",
                        file = image
                    )
                )
            }.onEach { result ->
                when (result) {
                    is BPMResponse.Success -> emit(ResponseState.Success(result.data.toDataModel()))
                    is BPMResponse.Error -> emit(ResponseState.Error(result.error.toDataModel()))
                }
            }.collect()
        }
    }
}