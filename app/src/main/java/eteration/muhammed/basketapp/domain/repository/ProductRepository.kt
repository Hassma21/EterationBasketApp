package eteration.muhammed.basketapp.domain.repository

import eteration.muhammed.basketapp.model.ProductDto
import eteration.muhammed.basketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Flow<Resource<List<ProductDto>>>
}