package com.d83t.bpm.data.repositoryImpl

import com.d83t.bpm.data.model.response.StudioResponse.Companion.toDataModel
import com.d83t.bpm.data.network.BPMResponse
import com.d83t.bpm.data.network.BPMResponseHandler
import com.d83t.bpm.data.network.ErrorResponse.Companion.toDataModel
import com.d83t.bpm.data.network.MainApi
import com.d83t.bpm.domain.model.ResponseState
import com.d83t.bpm.domain.model.Studio
import com.d83t.bpm.domain.repository.SearchStudioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SearchStudioRepositoryImpl @Inject constructor(
    private val mainApi: MainApi
) : SearchStudioRepository {
    override suspend fun fetchSearchStudioResult(
        query: String
    ): Flow<ResponseState<List<Studio>>> {
        return flow {
            BPMResponseHandler().handle {
                mainApi.searchStudio(query = query)
            }.onEach { result ->
                when (result) {
                    is BPMResponse.Success -> emit(ResponseState.Success(result.data.studios.map { it.toDataModel() }))
                    is BPMResponse.Error -> emit(ResponseState.Error(result.error.toDataModel()))
                }
            }.collect()
        }
    }
}