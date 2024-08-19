package eteration.muhammed.basketapp.data.repository

import eteration.muhammed.basketapp.data.remote.EterationMockAPI
import eteration.muhammed.basketapp.model.ProductDto
import eteration.muhammed.basketapp.domain.repository.ProductRepository
import eteration.muhammed.basketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOError
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val api: EterationMockAPI) :
    ProductRepository {
    override suspend fun getProducts(): Flow<Resource<List<ProductDto>>> = flow{

        try{
            emit(Resource.Loading())
            val productList =  api.getProducts()
            emit(Resource.Success(productList))
        }catch (e : IOError){
            emit(Resource.Error(message = "No Internet Connection"))
        }
        catch (e : IOError){
            emit(Resource.Error(message = e.localizedMessage ?: "Error"))
        }
    }
}