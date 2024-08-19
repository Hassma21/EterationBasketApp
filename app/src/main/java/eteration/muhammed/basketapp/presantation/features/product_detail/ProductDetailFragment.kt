package eteration.muhammed.basketapp.presantation.features.product_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import eteration.muhammed.basketapp.R
import eteration.muhammed.basketapp.databinding.FragmentProductDetailBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    private val viewModel: ProductDetailViewModel by viewModels()
    private var productId = 0
    private lateinit var binding: FragmentProductDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            productId = ProductDetailFragmentArgs.fromBundle(it).productId
        }
        viewModel.getDataFromRoom(productId)
        binding.productToolbar.toolbarIcon.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.buttonAddToCart.setOnClickListener {
            viewModel.addProductToBasket(productId, requireContext())
        }
        observeFlowData()
    }

    private fun observeFlowData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                val product = state.product
                val isLoading = state.isLoading
                val error = state.error

                handleLoadingState(isLoading)
                handleErrorState(error)
                if (product != null && !isLoading && error.isEmpty()) {
                    binding.selectedProduct = product
                    setProductViewsVisibility(View.VISIBLE)
                } else {
                    setProductViewsVisibility(View.GONE)
                }
            }
        }
    }

    private fun handleLoadingState(isLoading: Boolean) {
        binding.productDetailLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun handleErrorState(error: String) {
        if (error.isNotEmpty()) {
            binding.textError.visibility = View.VISIBLE
            binding.textError.text = error
        } else {
            binding.textError.visibility = View.GONE
        }
    }

    private fun setProductViewsVisibility(visibility: Int) {
        binding.productDescription.visibility = visibility
        binding.productPrice.visibility = visibility
        binding.productImage.visibility = visibility
        binding.priceText.visibility = visibility
        binding.productTitle.visibility = visibility
        binding.buttonAddToCart.visibility = visibility
    }
}
