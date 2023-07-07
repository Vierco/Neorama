package com.alvarez.sergio.actraining.di

import android.app.Application
import com.alvarez.sergio.actraining.ui.Detail.NeoDetailFragment
import com.alvarez.sergio.actraining.ui.Main.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AppDataModule::class
    ]
)
interface AppComponent {

    fun inject(mainFragment: MainFragment)
    fun inject(detailFragment: NeoDetailFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}
