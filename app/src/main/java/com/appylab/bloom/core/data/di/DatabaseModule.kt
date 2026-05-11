package com.appylab.bloom.core.data.di

import android.content.Context
import androidx.room.Room
import com.appylab.bloom.core.data.db.BloomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideBloomDatabase(
        @ApplicationContext context: Context
    ): BloomDatabase {
        return Room.databaseBuilder(
            context,
            BloomDatabase::class.java,
            "bloom_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideFoodEntryDao(database: BloomDatabase) = database.foodEntryDao()

    @Provides
    fun provideWorkoutSessionDao(database: BloomDatabase) = database.workoutSessionDao()

    @Provides
    fun provideExerciseLogDao(database: BloomDatabase) = database.exerciseLogDao()

    @Provides
    fun provideSetLogDao(database: BloomDatabase) = database.setLogDao()

    @Provides
    fun provideDailyStepsDao(database: BloomDatabase) = database.dailyStepsDao()

    @Provides
    fun provideWeightEntryDao(database: BloomDatabase) = database.weightEntryDao()
}
