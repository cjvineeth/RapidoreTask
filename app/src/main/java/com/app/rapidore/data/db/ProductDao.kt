package com.app.rapidore.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.TypeConverters
import com.app.rapidore.data.db.model.CartDBModel
import com.app.rapidore.data.db.model.CartWithProductDetails
import com.app.rapidore.data.db.model.ProductDBModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("select * From tbl_product")
     fun fetchProducts(): Flow<MutableList<ProductDBModel>>

    @Query("DELETE FROM tbl_product")
      fun deleteProduct()

    @Query("select * From tbl_product WHERE id=:productId")
    fun fetchProduct(productId:Int): Flow<ProductDBModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAllProducts(model :List<ProductDBModel>)

    @Query("select * From tbl_cart")
    fun fetchCart(): Flow<MutableList<CartDBModel>>

    @Query("select * From tbl_cart WHERE userId=:id")
    fun fetchCartUser(id:Int): Flow<CartDBModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCarts(model :List<CartDBModel>)

    @Transaction
    @Query("SELECT * FROM tbl_cart")
    fun getCartsWithProductDetails(): List<CartWithProductDetails>

}