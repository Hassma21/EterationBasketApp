package eteration.muhammed.basketapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import eteration.muhammed.basketapp.model.ProductDto

@Database(
    entities = [ProductDto::class],
    version = 1
)
abstract class BasketDatabase : RoomDatabase() {
    abstract val productDao: ProductDao

    companion object {
        @Volatile
        private var instance: BasketDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(Any()) {
            instance ?: makeDB(context).also {
                instance = it
            }
        }

        private fun makeDB(context: Context): BasketDatabase {
            val databaseName =
                "basketdatabase"

            return Room.databaseBuilder(
                context.applicationContext,
                BasketDatabase::class.java,
                databaseName
            ).build()
        }

        fun getInstance(): BasketDatabase = instance!!
    }
}