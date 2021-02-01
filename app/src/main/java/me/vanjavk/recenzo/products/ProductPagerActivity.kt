package me.vanjavk.recenzo.products

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_product_pager.*
import me.vanjavk.recenzo.R
import me.vanjavk.recenzo.RECENZO_PROVIDER_CONTENT_URI
import me.vanjavk.recenzo.framework.fetchProducts
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

        val itemBarcode:String? = intent.getStringExtra(ITEM_BARCODE)
        itemPosition = intent.getIntExtra(ITEM_POSITION, 0)
        println(itemPosition)
        if (itemBarcode==null){
            products = fetchProducts()
        }else{
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
                    cursor.getInt(cursor.getColumnIndex(Product::rating.name)),
                )
        }

        if (product!=null){
            products = mutableListOf(product)
        }
        }
        viewPager.isUserInputEnabled = false;
        viewPager.adapter = ProductPagerAdapter(products, this)
        viewPager.currentItem = itemPosition
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // kao da je netko back pritisnuo
        return super.onSupportNavigateUp()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return false;
    }
}