package eteration.muhammed.basketapp.presantation.features.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import eteration.muhammed.basketapp.R
import eteration.muhammed.basketapp.adapter.ProductAdapter
import eteration.muhammed.basketapp.databinding.FragmentProductBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment : Fragment() {
    private val viewModel: ProductViewModel by viewModels()
    private val productAdapter = ProductAdapter(ArrayList(), ::onAddProductToBasketClicked)
    private lateinit var binding: FragmentProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductBinding.bind(view)
        viewModel.refreshData(requireContext())
        binding.rcwProductList.layoutManager = GridLayoutManager(context, 2)
        binding.rcwProductList.adapter = productAdapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchProducts(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.searchProducts(it)
                }
                return true
            }
        })
        binding.buttonSelectFilter.setOnClickListener {
            //TODO: Implement Filter
            Toast.makeText(context, "Not Implemented Yet", Toast.LENGTH_SHORT).show()
        }

        binding.swiperefresh.setOnRefreshListener {
            binding.rcwProductList.visibility = View.GONE
            binding.productError.visibility = View.GONE
            binding.productLoading.visibility = View.VISIBLE
            viewModel.refreshFromAPI(requireContext())
            binding.swiperefresh.isRefreshing = false
        }
        observeFlowData()
    }


    private fun observeFlowData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                val products = state.products
                products.let {
                    if (products.isNotEmpty()) {
                        binding.rcwProductList.visibility = View.VISIBLE
                        productAdapter.updateProductList(products)
                    }
                }
                val error = state.error
                if (error.isNotEmpty()) {
                    binding.productError.visibility = View.VISIBLE
                    binding.productError.text = error
                } else {
                    binding.productError.visibility = View.GONE
                }
                val loading = state.isLoading
                if (loading) {
                    binding.productLoading.visibility = View.VISIBLE
                    binding.rcwProductList.visibility = View.GONE
                    binding.productError.visibility = View.GONE
                } else {
                    binding.productLoading.visibility = View.GONE
                }
            }
        }
    }

    private fun onAddProductToBasketClicked(productId: Int) {
        viewModel.addProductToBasket(productId, requireContext()) // Example quantity
    }
}