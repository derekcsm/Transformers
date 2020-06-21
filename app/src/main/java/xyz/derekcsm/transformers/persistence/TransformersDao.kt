package xyz.derekcsm.transformers.persistence

import androidx.room.*
import xyz.derekcsm.transformers.model.Transformer

@Dao
interface TransformersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransformersList(pokemonList: List<Transformer>)

    @Query("SELECT * FROM Transformer")
    fun getTransformersList(): List<Transformer>

    @Query("SELECT * FROM Transformer WHERE id =:id")
    fun getTransformer(id: String): Transformer

    @Query("DELETE FROM Transformer WHERE id =:id")
    fun deleteTransformer(id: String)


    @Transaction
    fun upsert(transformer: Transformer) {
        val id = insert(transformer)
        if (id == -1L) {
            update(transformer)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(obj: Transformer): Long

    @Update
    abstract fun update(obj: Transformer)
}