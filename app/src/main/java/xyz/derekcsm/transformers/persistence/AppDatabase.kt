package xyz.derekcsm.transformers.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.derekcsm.transformers.model.Transformer

@Database(entities = [Transformer::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transformersDao(): TransformersDao
}
