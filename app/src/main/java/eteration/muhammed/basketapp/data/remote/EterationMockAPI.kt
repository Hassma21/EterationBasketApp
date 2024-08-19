package eteration.muhammed.basketapp.data.remote

import eteration.muhammed.basketapp.model.ProductDto
import retrofit2.http.GET

interface EterationMockAPI {
    @GET("/products")
    suspend fun getProducts(): List<ProductDto>
}