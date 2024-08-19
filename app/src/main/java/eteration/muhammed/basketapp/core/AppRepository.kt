package eteration.muhammed.basketapp.core

import eteration.muhammed.basketapp.core.persistence.Setting

object AppRepository {
    val SaveTime = Setting("save_time", 0L)
    val ProductId = Setting("product_id", 0)
    val ProductQuantity = Setting("product_quantity", 0)
    val AllProductQuantity = Setting("all_product_quantity", 0)
}