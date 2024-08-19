package eteration.muhammed.basketapp.model.mapper

import eteration.muhammed.basketapp.domain.model.Product
import eteration.muhammed.basketapp.domain.model.ProductDetail
import eteration.muhammed.basketapp.model.ProductDto

fun ProductDto.toProductDetail(): ProductDetail {
    return ProductDetail(
        id = this.id,
        image = this.image,
        name = this.name,
        price = this.price,
        description = this.description
    )
}

fun ProductDto.toProduct(): Product {
    return Product(
        id = this.id,
        image = this.image,
        name = this.name,
        price = this.price
    )
}

fun List<ProductDto>.toProductList(): List<Product> {
    return this.map { productDto ->
        Product(
            id = productDto.id,
            image = productDto.image,
            name = productDto.name,
            price = productDto.price
        )
    }
}