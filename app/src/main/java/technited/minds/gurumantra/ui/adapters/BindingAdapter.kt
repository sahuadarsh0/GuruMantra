package technited.minds.gurumantra.ui.adapters

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import technited.minds.gurumantra.R
import technited.minds.gurumantra.utils.Constants

object BindingAdapters {

    @BindingAdapter("android:setImage")
    @JvmStatic
    fun loadImage(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load(Constants.URL.toString() + imageUrl).apply(RequestOptions().circleCrop())
            .error(R.drawable.splash)
            .into(view)
    }

    @BindingAdapter("android:setImageUrl")
    @JvmStatic
    fun setImage(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load(Constants.URL.toString() + imageUrl)
            .error(R.drawable.splash)
            .into(view)
    }


    @BindingAdapter("android:setMobile")
    @JvmStatic
    fun setMobile(view: TextView, text: String?) {
        val output = "XXXXX" + (text?.substring(5))
        view.text = output
    }

    @BindingAdapter("android:splitText")
    @JvmStatic
    fun setSplitText(view: TextView, text: String?) {
        val input = text?.split(",")
        var output = ""
        input?.forEach { s -> output+= "$s\n" }
        view.text =output
    }

    @BindingAdapter("android:htmlText")
    @JvmStatic
    fun htmlText(view: TextView, text: String?) {
        val output = text?.let { Html.fromHtml(it) }
        view.text = output
    }
}