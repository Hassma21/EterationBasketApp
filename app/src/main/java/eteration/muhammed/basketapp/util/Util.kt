package eteration.muhammed.basketapp.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.squareup.picasso.Picasso
import eteration.muhammed.basketapp.R

fun ImageView.setImage(url: String?, contex: Context) {
    if (url.isNullOrEmpty()) {
        return
    }
    Picasso.get()
        .load(url)
        .placeholder(placeHolderProgressBar(contex))
        .error(R.mipmap.ic_launcher_round)
        .fit()
        .centerCrop()
        .into(this)
}

fun placeHolderProgressBar(contex: Context): CircularProgressDrawable {
    return CircularProgressDrawable(contex).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:dowlandImage")
fun dowlandImage(view: ImageView, url: String?) {
    view.setImage(url, view.context)
}