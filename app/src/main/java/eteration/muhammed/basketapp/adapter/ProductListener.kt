package eteration.muhammed.basketapp.adapter

import eteration.muhammed.basketapp.domain.model.Product

interface ProductListener {
    fun onProductClicked(product: Product)
}