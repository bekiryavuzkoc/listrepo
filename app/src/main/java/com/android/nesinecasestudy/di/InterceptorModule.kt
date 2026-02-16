package com.android.nesinecasestudy.di

import com.android.nesinecasestudy.BuildConfig
import com.android.nesinecasestudy.domain.utils.CONTENT_JSON
import com.android.nesinecasestudy.domain.utils.CUSTOM_HEADER_CONNECT_TIMEOUT
import com.android.nesinecasestudy.domain.utils.CUSTOM_HEADER_READ_TIMEOUT
import com.android.nesinecasestudy.domain.utils.DEFAULT_CONNECT_TIME_OUT
import com.android.nesinecasestudy.domain.utils.DEFAULT_READ_TIME_OUT
import com.android.nesinecasestudy.domain.utils.HEADER_CONTENT_TYPE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Singleton
    @Provides
    fun provideDefaultHeaderInterceptor(): Interceptor {
        return Interceptor {
            val requestBuilder = it.request()
                .newBuilder()
                .addHeader(HEADER_CONTENT_TYPE, CONTENT_JSON)
            it.proceed(requestBuilder.build())
        }
    }

    @Singleton
    @Provides
    fun provideCustomTimeoutInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()

            var connectTimeout = chain.connectTimeoutMillis()
            var readTimeout = chain.readTimeoutMillis()

            val builder = request.newBuilder()

            request.header(CUSTOM_HEADER_CONNECT_TIMEOUT)?.let {
                connectTimeout = it.toInt()
                builder.removeHeader(CUSTOM_HEADER_CONNECT_TIMEOUT)
            } ?: run {
                connectTimeout = DEFAULT_CONNECT_TIME_OUT.toInt()
            }

            request.header(CUSTOM_HEADER_READ_TIMEOUT)?.let {
                readTimeout = it.toInt()
                builder.removeHeader(CUSTOM_HEADER_READ_TIMEOUT)
            } ?: run {
                readTimeout = DEFAULT_READ_TIME_OUT.toInt()
            }

            chain.withConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .withReadTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .proceed(builder.build())
        }
    }
}