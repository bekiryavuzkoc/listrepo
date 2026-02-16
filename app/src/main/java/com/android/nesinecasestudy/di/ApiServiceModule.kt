package com.android.nesinecasestudy.di

import com.android.nesinecasestudy.data.repository.remote.PostApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideInboxApiService(
       retrofit: Retrofit
    ): PostApiService =
        retrofit.create(PostApiService::class.java)
}