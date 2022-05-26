package com.android.muncher.di

import android.content.Context
import com.android.muncher.data.network.request.AuthApi
import com.android.muncher.data.network.source.RemoteDataSource
import com.android.muncher.data.network.request.UserApi
import com.android.muncher.data.network.source.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.android.muncher.data.repository.AuthRepository
import com.android.muncher.data.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(): RemoteDataSource {
        return RemoteDataSource()
    }

    @Singleton
    @Provides
    fun provideAuthApi(
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): AuthApi {
        return remoteDataSource.buildApiServices(AuthApi::class.java, context)
    }

    @Singleton
    @Provides
    fun provideUserApi(
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): UserApi {
        return remoteDataSource.buildApiServices(UserApi::class.java, context)
    }

    @Singleton
    @Provides
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }



    @Provides
    fun provideAuthRepository(
        authApi: AuthApi,
        userPreferences: UserPreferences
    ): AuthRepository {
        return AuthRepository(authApi, userPreferences)
    }

    @Provides
    fun provideUserRepository(userApi: UserApi): UserRepository {
        return UserRepository(userApi)
    }
}