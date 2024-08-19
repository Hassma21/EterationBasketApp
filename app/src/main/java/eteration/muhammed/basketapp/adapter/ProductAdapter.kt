package eteration.muhammed.basketapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import eteration.muhammed.basketapp.R
import eteration.muhammed.basketapp.databinding.ItemProductBinding
import eteration.muhammed.basketapp.domain.model.Product
import eteration.muhammed.basketapp.presantation.features.product.ProductFragmentDirections

class ProductAdapter(
    val productList: ArrayList<Product>,
    private val onAddProductToBasketClicked: (productId: Int) -> Unit
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(), ProductListener {

    private lateinit var binding: ItemProductBinding

    class ProductViewHolder(val binding: ItemProductBinding) : ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_product,
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.product = productList[position]
        holder.binding.listener = this
        holder.binding.addToCartButton.setOnClickListener {
            onAddProductToBasketClicked(productList[position].id)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProductList(newCountryList: List<Product>) {
        productList.clear()
        productList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onProductClicked(product: Product) {
        val action =
            ProductFragmentDirections.actionProductFragmentToProductDetailFragment(product.id)
        Navigation.findNavController(binding.root).navigate(action)
    }
}