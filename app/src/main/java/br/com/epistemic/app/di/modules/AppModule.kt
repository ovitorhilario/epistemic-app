package br.com.epistemic.app.di.modules

import android.content.Context
import br.com.epistemic.app.data.repository.UserPreferencesRepositoryImpl
import br.com.epistemic.app.domain.repository.UserPreferencesRepository
import br.com.epistemic.app.presentation.activities.main.dataStore
import br.com.epistemic.app.presentation.receivers.contract.AlarmScheduler
import br.com.epistemic.app.presentation.receivers.impl.AlarmSchedulerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @CoroutineDispatcherIO
    @Provides
    fun providesDispatcherIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @CoroutineDispatcherUnconfined
    @Provides
    fun providesDispatcherUnconfined(): CoroutineDispatcher {
        return Dispatchers.Unconfined
    }

    @Provides
    fun providesUserRepository(
        @ApplicationContext context: Context
    ): UserPreferencesRepository {
        return UserPreferencesRepositoryImpl(context.dataStore)
    }

    @Provides
    fun providesAlarmScheduler(
        @ApplicationContext context: Context
    ): AlarmScheduler {
        return AlarmSchedulerImpl(context)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CoroutineDispatcherIO

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CoroutineDispatcherUnconfined