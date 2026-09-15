package com.karthik.mycards.di

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.karthik.mycards.data.local.CardDao
import com.karthik.mycards.data.local.MyCardsDatabase
import com.karthik.mycards.data.local.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * Hilt module: recipes for building app-wide singletons.
 * @InstallIn(SingletonComponent) = these live for the whole app lifetime.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MyCardsDatabase =
        Room.databaseBuilder<MyCardsDatabase>(
            context = context,
            name = "mycards.db"
        )
            // Room 3.0 requires an explicit SQLite driver + a coroutine context.
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()

    @Provides
    fun provideCardDao(db: MyCardsDatabase): CardDao = db.cardDao()

    @Provides
    fun provideTransactionDao(db: MyCardsDatabase): TransactionDao = db.transactionDao()
}
