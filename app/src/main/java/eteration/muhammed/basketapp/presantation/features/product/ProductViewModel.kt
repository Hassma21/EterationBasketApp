package eteration.muhammed.basketapp.presantation.features.product

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eteration.muhammed.basketapp.core.AppRepository
import eteration.muhammed.basketapp.core.persistence.readInt
import eteration.muhammed.basketapp.core.persistence.readLong
import eteration.muhammed.basketapp.core.persistence.saveInt
import eteration.muhammed.basketapp.core.persistence.saveLong
import eteration.muhammed.basketapp.data.local.ProductDao
import eteration.muhammed.basketapp.domain.model.Product
import eteration.muhammed.basketapp.domain.repository.ProductRepository
import eteration.muhammed.basketapp.model.ProductDto
import eteration.muhammed.basketapp.model.mapper.toProductList
import eteration.muhammed.basketapp.util.Constant.REFRESH_TIME
import eteration.muhammed.basketapp.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val productDao: ProductDao
) : ViewModel() {
    private val _state = MutableStateFlow(ProductState())
    val state: StateFlow<ProductState> = _state


    fun refreshData(context: Context) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            AppRepository.SaveTime.readLong(context).collect { updateTime ->
                if (updateTime != 0L && System.nanoTime() - updateTime < REFRESH_TIME) {
                    getDataFromSQLite()
                } else {
                    getDataFromAPI(context)
                }
            }
        }

    }

    fun refreshFromAPI(context: Context) {
        viewModelScope.launch {
            getDataFromAPI(context)
        }
    }

    private suspend fun getDataFromSQLite() {
        _state.value.isLoading = true
        productDao.getAllProducts()
            .collect { products ->
                showCountries(products.toProductList())
            }
    }

    private fun getDataFromAPI(context: Context) {
        _state.value.isLoading = true
        viewModelScope.launch {
            productRepository.getProducts()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            storeInSQLite(context, result.data!!)
                        }

                        is Resource.Error -> {
                            _state.update {
                                it.copy(isLoading = false, error = result.message!!)
                            }
                        }

                        is Resource.Loading -> {
                            _state.update {
                                it.copy(isLoading = true)
                            }
                        }
                    }
                }
        }

    }

    private fun storeInSQLite(context: Context, productList: List<ProductDto>) {
        viewModelScope.launch {
            productDao.deleteAllCountries()
            productDao.insertAll(*productList.toTypedArray())
            showCountries(productList.toProductList())
            AppRepository.SaveTime.saveLong(
                context, System.nanoTime()
            )
        }
    }

    private fun showCountries(productList: List<Product>) {
        _state.update {
            it.copy(products = productList, isLoading = false, error = "")
        }
    }

    fun searchProducts(query: String) {
        viewModelScope.launch {
            productDao.searchProductListing(query).collect { products ->
                _state.update {
                    it.copy(products = products.toProductList())
                }
            }
        }
    }

    fun addProductToBasket(productId: Int, context: Context) {
        viewModelScope.launch {
            AppRepository.ProductId.readInt(context).collect { existingProductId ->
                if (existingProductId == 0) {
                    AppRepository.ProductId.saveInt(context, productId)
                    AppRepository.ProductQuantity.saveInt(context, 1)
                    AppRepository.AllProductQuantity.saveInt(context, AppRepository.AllProductQuantity.readInt(context).first() + 1)
                } else {
                    if (existingProductId == productId) {
                        AppRepository.ProductQuantity.readInt(context).collect { existingQuantity ->
                            AppRepository.ProductQuantity.saveInt(context, existingQuantity + 1)
                            AppRepository.AllProductQuantity.saveInt(context, AppRepository.AllProductQuantity.readInt(context).first() + 1)
                        }
                    } else {
                        AppRepository.ProductId.saveInt(context, productId)
                        AppRepository.ProductQuantity.saveInt(context, 1)
                        AppRepository.AllProductQuantity.saveInt(context, AppRepository.AllProductQuantity.readInt(context).first() + 1)
                    }
                }
            }

        }
    }
}




