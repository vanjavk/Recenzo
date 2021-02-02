package me.vanjavk.recenzo.products

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import me.vanjavk.recenzo.R
import me.vanjavk.recenzo.framework.startActivity
import me.vanjavk.recenzo.model.Product
import java.io.File

class ProductAdapter(private val items: MutableList<Product>, private val context: Context)
    : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivItem: ImageView = itemView.findViewById(R.id.ivItem)
        private val tvItem: TextView = itemView.findViewById(R.id.tvItem)
        fun bind(product: Product) {
            Picasso.get()
                .load(File(product.picturePath))
                .error(R.drawable.star)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivItem)
            tvItem.text = product.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.product, parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            context.startActivity<ProductPagerActivity>(ITEM_POSITION, position)
        }
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}