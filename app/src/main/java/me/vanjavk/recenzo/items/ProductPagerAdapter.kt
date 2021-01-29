package me.vanjavk.recenzo.items

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.product_pager.view.*
import me.vanjavk.recenzo.R
import me.vanjavk.recenzo.model.Product
import java.io.File


class ProductPagerAdapter(private val items: MutableList<Product>, private val context: Context)
    : RecyclerView.Adapter<ProductPagerAdapter.ViewHolder>(){

    // vise nije static -> moguc je memory leak
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val ivItem: ImageView = itemView.findViewById(R.id.ivItem)
        private val ivRead: ImageView = itemView.findViewById(R.id.ivRead)
        private val tvBarcode: TextView = itemView.findViewById(R.id.tvBarcode)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)

        // bind se kontinuirano poziva za svaki od positiona
        fun bind(product: Product) {
            Picasso.get()
                .load(File(product.picturePath))
                .error(R.drawable.star)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivItem)
//            ivRead.setImageResource(if (product.read) R.drawable.green_flag else R.drawable.red_flag)
            tvBarcode.text = product.barcode
            tvTitle.text = product.title
            tvDescription.text = product.description


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_pager, parent, false)


        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items[position]

//        holder.itemView.ivRead.setOnClickListener {
//            item.read = !item.read
//            context.contentResolver.update(
//                ContentUris.withAppendedId(RECENZO_PROVIDER_CONTENT_URI, item._id!!),
//                ContentValues().apply {
//                    put(Product::read.name, item.read)
//                },
//                null,
//                null
//            )
//            notifyItemChanged(position) // observable
//        }
        holder.bind(item)
    }

    override fun getItemCount() = items.size


}