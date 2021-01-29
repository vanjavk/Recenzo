package me.vanjavk.recenzo.items

import android.os.Bundle
import android.view.MotionEvent
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_product_pager.*
import kotlinx.android.synthetic.main.fragment_scan.*
import me.vanjavk.recenzo.R
import me.vanjavk.recenzo.RECENZO_PROVIDER_CONTENT_URI
import me.vanjavk.recenzo.framework.fetchItems
import me.vanjavk.recenzo.model.Product


// public static
const val ITEM_BARCODE = "me.vanjavk.recenzo.product_barcode"
const val ITEM_POSITION = "me.vanjavk.recenzo.product_position"

class ProductPagerActivity() : AppCompatActivity() {

    private lateinit var products: MutableList<Product>
    private var itemPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_pager)

        init()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun init() {

        val itemBarcode = intent.getStringExtra(ITEM_BARCODE)
        val cursor = application.contentResolver.query(
            RECENZO_PROVIDER_CONTENT_URI,
            null, "barcode='${itemBarcode}'", null, null
        )
        var product : Product? = null
        if (cursor != null && !(!cursor.moveToFirst() || cursor.getCount() == 0)) {
            product=
                Product(
                    cursor.getLong(cursor.getColumnIndex(Product::_id.name)),
                    cursor.getString(cursor.getColumnIndex(Product::barcode.name)),
                    cursor.getString(cursor.getColumnIndex(Product::title.name)),
                    cursor.getString(cursor.getColumnIndex(Product::description.name)),
                    cursor.getString(cursor.getColumnIndex(Product::picturePath.name)),
                )
        }
        if (product!=null){
            products = mutableListOf(product)

        }
        viewPager.isUserInputEnabled = false;
        viewPager.adapter = ProductPagerAdapter(products, this)
        viewPager.currentItem = 0
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // kao da je netko back pritisnuo
        return super.onSupportNavigateUp()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return false;
    }
}