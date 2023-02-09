package com.d83t.bpm.data.mapper

import com.d83t.bpm.data.base.BaseResponse
import com.d83t.bpm.domain.base.BaseModel

interface DataMapper<in R : BaseResponse, out D : BaseModel> {
    fun R.toDataModel(): D
}
