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
import kotlinx.android.synthetic.main.product_pager.view.*
import me.vanjavk.recenzo.R
import me.vanjavk.recenzo.framework.updateProductRating
import me.vanjavk.recenzo.framework.updateRatingVisual
import me.vanjavk.recenzo.model.Product
import java.io.File


class ProductPagerAdapter(private val products: MutableList<Product>, private val context: Context)
    : RecyclerView.Adapter<ProductPagerAdapter.ViewHolder>(){

    // vise nije static -> moguc je memory leak
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val ivItem: ImageView = itemView.findViewById(R.id.ivItem)
        private val tvBarcode: TextView = itemView.findViewById(R.id.tvBarcode)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val ivStar1: ImageView = itemView.findViewById(R.id.ivStar1)
        val ivStar2: ImageView = itemView.findViewById(R.id.ivStar2)
        val ivStar3: ImageView = itemView.findViewById(R.id.ivStar3)
        val ivStar4: ImageView = itemView.findViewById(R.id.ivStar4)
        val ivStar5: ImageView = itemView.findViewById(R.id.ivStar5)

        // bind se kontinuirano poziva za svaki od positiona
        fun bind(product: Product) {
            Picasso.get()
                .load(File(product.picturePath))
                .error(R.drawable.star)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivItem)
            tvBarcode.text = product.barcode
            tvTitle.text = product.title
            tvDescription.text = product.description
            updateRatingVisual(product.rating)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_pager, parent, false)



        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product = products[position]

        holder.itemView.ivStar1.setOnClickListener {
            product.rating = 1;
            updateProductRating(context,product)
            notifyItemChanged(position) // observable
        }
        holder.itemView.ivStar2.setOnClickListener {
            product.rating = 2;
            updateProductRating(context,product)
            notifyItemChanged(position) // observable
        }
        holder.itemView.ivStar3.setOnClickListener {
            product.rating = 3;
            updateProductRating(context,product)
            notifyItemChanged(position) // observable
        }
        holder.itemView.ivStar4.setOnClickListener {
            product.rating = 4;
            updateProductRating(context,product)
            notifyItemChanged(position) // observable
        }
        holder.itemView.ivStar5.setOnClickListener {
            product.rating = 5;
            updateProductRating(context,product)
            notifyItemChanged(position) // observable
        }
        holder.bind(product)
    }

    override fun getItemCount() = products.size


}