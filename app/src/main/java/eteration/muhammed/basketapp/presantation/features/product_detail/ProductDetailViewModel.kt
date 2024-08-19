package eteration.muhammed.basketapp.presantation.features.product_detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eteration.muhammed.basketapp.core.AppRepository
import eteration.muhammed.basketapp.core.persistence.readInt
import eteration.muhammed.basketapp.core.persistence.saveInt
import eteration.muhammed.basketapp.data.local.ProductDao
import eteration.muhammed.basketapp.model.mapper.toProductDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productDao: ProductDao
) : ViewModel() {
    private val _state = MutableStateFlow(ProductDetailState())
    val state: StateFlow<ProductDetailState> = _state

    fun getDataFromRoom(uuid: Int) {
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            productDao.getProduct(uuid).collect { product ->
                _state.value = ProductDetailState(
                    product = product.toProductDetail(),
                    isLoading = false,
                    error = ""
                )
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