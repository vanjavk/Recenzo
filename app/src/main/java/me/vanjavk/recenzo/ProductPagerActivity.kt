package me.vanjavk.recenzo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_item_pager.*
import me.vanjavk.recenzo.framework.fetchItems
import me.vanjavk.recenzo.model.Product

// public static
const val ITEM_POSITION = "hr.algebra.nasa.item_position"

class ProductPagerActivity : AppCompatActivity() {

    private lateinit var products: MutableList<Product>
    private var itemPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_pager)

        init()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun init() {
        products = fetchItems()
        itemPosition = intent.getIntExtra(ITEM_POSITION, 0)
        viewPager.adapter = ProductPagerAdapter(products, this)
        viewPager.currentItem = itemPosition
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // kao da je netko back pritisnuo
        return super.onSupportNavigateUp()
    }
}