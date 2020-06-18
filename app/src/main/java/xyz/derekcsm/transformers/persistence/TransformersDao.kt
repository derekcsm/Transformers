package xyz.derekcsm.transformers.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import xyz.derekcsm.transformers.model.Transformer

@Dao
interface TransformersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransformersList(pokemonList: List<Transformer>)

    @Query("SELECT * FROM Transformer")
    fun getTransformersList(): List<Transformer>
}