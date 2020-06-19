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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransformer(transformer: Transformer)

    @Query("SELECT * FROM Transformer")
    fun getTransformersList(): List<Transformer>

    @Query("SELECT * FROM Transformer WHERE id =:id")
    fun getTransformer(id: String): Transformer
}