package me.vanjavk.recenzo.items

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_products.*
import me.vanjavk.recenzo.R
import me.vanjavk.recenzo.framework.fetchItems
import me.vanjavk.recenzo.model.Product


class ProductsFragment : Fragment() {

    private lateinit var products: MutableList<Product>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        products = requireContext().fetchItems()
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productAdapter =ProductAdapter(products, requireContext())
        rvItems.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = productAdapter
        }

    }


}