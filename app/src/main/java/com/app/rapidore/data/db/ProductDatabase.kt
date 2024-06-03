package com.app.rapidore.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.rapidore.data.db.convertors.CommonConverters
import com.app.rapidore.data.db.model.CartDBModel
import com.app.rapidore.data.db.model.ProductDBModel


@Database(entities = [ProductDBModel::class,CartDBModel::class], version = 36, exportSchema = false)
@TypeConverters(CommonConverters::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract val productDao: ProductDao

    companion object {

        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getInstance(context: Context): ProductDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProductDatabase::class.java,
                        "product_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}