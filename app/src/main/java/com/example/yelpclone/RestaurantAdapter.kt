package com.example.yelpclone

import android.content.Context
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.yelpclone.databinding.ItemRestaurantBinding


class RestaurantAdapter(val context: Context, private val restaurants: List<YelpRestaurant>) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRestaurantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val restaurant=restaurants[position]
        holder.bind(restaurant)

    }

    override fun getItemCount()=restaurants.size

    inner class ViewHolder(val Binding:ItemRestaurantBinding) : RecyclerView.ViewHolder(Binding.root) {
        fun bind(restaurant: YelpRestaurant) {
            Binding.tvName.text=restaurant.name
            Binding.ratingBar.rating= restaurant.rating.toFloat()
            Binding.tvNumReviews.text= restaurant.numReviews.toString()
            Binding.tvAddress.text=restaurant.location.address
            Binding.tvCategory.text= restaurant.categories[0].title
            Binding.tvDistance.text=restaurant.displayDistance()
            Binding.tvPrice.text=restaurant.price
            Glide.with(context).load(restaurant.imageUrl).apply(RequestOptions().transform(CenterCrop(),RoundedCorners(20))).into(Binding.imageView)


        }
    }
}
