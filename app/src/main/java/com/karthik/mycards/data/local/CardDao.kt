package com.karthik.mycards.data.local

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for cards. You declare WHAT you want;
 * Room generates the SQL implementation at build time.
 */
@Dao
interface CardDao {

    // Live stream of all cards — the UI re-updates automatically when data changes.
    @Query("SELECT * FROM cards")
    fun getAllCards(): Flow<List<Card>>

    // Live stream of a single card by id.
    @Query("SELECT * FROM cards WHERE id = :cardId")
    fun getCard(cardId: Int): Flow<Card?>

    // One-off count (suspend = runs off the main thread).
    @Query("SELECT COUNT(*) FROM cards")
    suspend fun count(): Int

    @Insert
    suspend fun insertAll(cards: List<Card>)
}
