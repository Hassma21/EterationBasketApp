package eteration.muhammed.basketapp.presantation.features.product

import eteration.muhammed.basketapp.domain.model.Product

data class ProductState(
    var products: List<Product> = emptyList(),
    var isLoading: Boolean = false,
    var error: String = ""
)
