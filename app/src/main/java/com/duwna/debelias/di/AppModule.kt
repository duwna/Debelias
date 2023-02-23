package com.duwna.debelias.di

import android.content.Context
import android.os.Vibrator
import androidx.core.content.getSystemService
import com.duwna.debelias.data.MessageHandler
import com.duwna.debelias.data.PersistentStorage
import com.duwna.debelias.data.ResourceManager
import com.duwna.debelias.navigation.Navigator
import com.duwna.debelias.navigation.NavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePersistentStorage(@ApplicationContext context: Context) =
        PersistentStorage(context)

    @Provides
    fun provideVibrator(@ApplicationContext context: Context): Vibrator =
        requireNotNull(context.getSystemService())

    @Provides
    fun provideResourceManager(@ApplicationContext context: Context): ResourceManager =
        ResourceManager(context)

    @Provides
    @Singleton
    fun provideMessageHandler() = MessageHandler()

    @Provides
    @Singleton
    fun provideNavigator(): Navigator = NavigatorImpl()
}
