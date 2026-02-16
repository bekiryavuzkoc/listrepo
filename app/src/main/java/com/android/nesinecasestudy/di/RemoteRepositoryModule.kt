package com.android.nesinecasestudy.di

import com.android.nesinecasestudy.data.repository.remote.PostRemoteDataSourceImpl
import com.android.nesinecasestudy.domain.repository.PostRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteRepositoryModule {

    @Binds
    abstract fun bindPostRemoteDataSource(impl: PostRemoteDataSourceImpl): PostRemoteDataSource
}