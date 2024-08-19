package eteration.muhammed.basketapp.presantation.features.product_detail

import eteration.muhammed.basketapp.domain.model.ProductDetail

data class ProductDetailState(
    var product: ProductDetail? = null,
    var isLoading: Boolean = false,
    var error: String = ""
)