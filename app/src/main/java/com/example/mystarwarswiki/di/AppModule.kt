package com.example.mystarwarswiki.di

import android.content.Context
import androidx.room.Room
import com.example.mystarwarswiki.data.local.dao.PersonDao
import com.example.mystarwarswiki.data.local.db.AppDatabase
import com.example.mystarwarswiki.data.remote.api.StarWarsApi
import com.example.mystarwarswiki.data.repository.PersonRepositoryImpl
import com.example.mystarwarswiki.domain.repository.PersonRepository
import com.example.mystarwarswiki.domain.usecase.GetPeopleUseCase
import com.example.mystarwarswiki.domain.usecase.GetPersonByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttp(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(okHttpClient: OkHttpClient): StarWarsApi {
        return Retrofit.Builder()
            .baseUrl("https://swapi.dev/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StarWarsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "star_wars_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePersonDao(db: AppDatabase): PersonDao = db.personDao()

    @Provides
    @Singleton
    fun provideRepository(
        api: StarWarsApi,
        dao: PersonDao
    ): PersonRepository {
        return PersonRepositoryImpl(api, dao)
    }

    @Provides
    fun provideGetPeopleUseCase(
        repository: PersonRepository
    ): GetPeopleUseCase = GetPeopleUseCase(repository)

    @Provides
    fun provideGetPersonByIdUseCase(
        repository: PersonRepository
    ): GetPersonByIdUseCase = GetPersonByIdUseCase(repository)
}