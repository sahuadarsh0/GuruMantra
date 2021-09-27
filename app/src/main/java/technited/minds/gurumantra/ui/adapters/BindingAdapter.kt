package technited.minds.gurumantra.ui.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import technited.minds.gurumantra.R

object BindingAdapters {

    @BindingAdapter("android:setImage")
    @JvmStatic
    fun loadImage(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load(imageUrl).apply(RequestOptions().circleCrop()).placeholder(R.drawable.logo)
            .error(R.drawable.logo)
            .into(view)
    }

    @BindingAdapter("android:setImageUrl")
    @JvmStatic
    fun setImage(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load(imageUrl)
            .error(R.drawable.logo)
            .into(view)
    }


    @BindingAdapter("android:setMobile")
    @JvmStatic
    fun setMobile(view: TextView, text: String?) {
        val output = "XXXXX" + (text?.substring(5))
        view.text = output
    }
}