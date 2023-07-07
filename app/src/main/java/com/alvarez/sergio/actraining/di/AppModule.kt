package com.alvarez.sergio.actraining.di

import android.app.Application
import androidx.room.Room
import com.alvarez.sergio.actraining.data.AndroidPermissionChecker
import com.alvarez.sergio.actraining.data.database.NeoDataBase
import com.alvarez.sergio.actraining.data.database.NeoRoomDataSource
import com.alvarez.sergio.actraining.data.datasource.NeoLocalDataSource
import com.alvarez.sergio.actraining.data.datasource.NeoRemoteDataSource
import com.alvarez.sergio.actraining.data.repositories.PermissionChecker
import com.alvarez.sergio.actraining.data.server.NeoServerDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataBase(app: Application) = Room.databaseBuilder(
        app,
        NeoDataBase::class.java,
        "neo-neos-db"
    ).build()

    @Provides
    @Singleton
    fun provideNeoDao(db: NeoDataBase) = db.neoDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: NeoServerDataSource): NeoRemoteDataSource

    @Binds
    abstract fun bindLocalDataSource(neoRoomDataSource: NeoRoomDataSource): NeoLocalDataSource

    // @Binds
    // abstract fun bindLocationDataSource(locationDataSource: PlayServicesLocationDataSource): LocationDataSource

    @Binds
    abstract fun bindPermissionChecker(permissionChecker: AndroidPermissionChecker): PermissionChecker
}
