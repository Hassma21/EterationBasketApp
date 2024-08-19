package eteration.muhammed.basketapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import eteration.muhammed.basketapp.model.ProductDto
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert
    suspend fun insertAll(vararg countries : ProductDto)

    @Query("SELECT * FROM ProductDto")
    fun getAllProducts(): Flow<List<ProductDto>>

    @Query("SELECT * FROM ProductDto WHERE id = :productId")
    fun getProduct(productId: Int): Flow<ProductDto>

    @Query(
        """
            SELECT * 
            FROM ProductDto 
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
                LOWER(brand) LIKE '%' || LOWER(:query) || '%'
        """
    )
    fun searchProductListing(query: String): Flow<List<ProductDto>>

    @Query("DELETE FROM ProductDto")
    suspend fun deleteAllCountries()
}