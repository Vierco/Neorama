package com.alvarez.sergio.actraining.ui.Detail

import androidx.lifecycle.SavedStateHandle
import com.alvarez.sergio.actraining.di.NeoId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal class DetailViewModelModule {
    @Provides
    @ViewModelScoped
    @NeoId
    fun provideNeoId(savedStateHandle: SavedStateHandle) =
        NeoDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).neoEntityId
}
