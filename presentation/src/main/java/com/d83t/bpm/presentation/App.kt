package com.d83t.bpm.presentation

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        
        // 카카오 로그인을 위한 SDK 셋업
        KakaoSdk.init(this, "ced602989810575b0c1d032415db11c8")

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}