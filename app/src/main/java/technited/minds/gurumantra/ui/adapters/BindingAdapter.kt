package technited.minds.gurumantra.ui.adapters

import android.graphics.Paint
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
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
        input?.forEach { s -> output += "$s\n\n" }
        view.text = output
    }

    @BindingAdapter("android:strikeText")
    @JvmStatic
    fun strikeText(view: TextView, text: String?) {
        view.paintFlags = view.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        view.text = text
    }

    @BindingAdapter("android:htmlText")
    @JvmStatic
    fun htmlText(view: TextView, text: String?) {
        val output = text?.let { Html.fromHtml(it) }
        view.text = output
    }

    @BindingAdapter("android:stock")
    @JvmStatic
    fun stock(view: TextView, text: Int?) {
        var output = ""
        if (text != null) {
            if (text >= 0) {
                output = "In Stock"
                view.setTextColor(getColor(view.context, R.color.zm_green))
            } else {
                output = "Out of Stock"
                view.setTextColor(getColor(view.context, R.color.red))
            }
        }
        view.text = output
    }

    @BindingAdapter("android:notePackage", "android:userPackage")
    @JvmStatic
    fun setVisible(view: ImageView, note: Int?, user: Int?) {
        if (note != null && user != null) {
            if (note == 1) {
                view.visibility = View.GONE
            } else if (user == 2 && note == 2) {
                view.visibility = View.GONE
            } else  {
                view.visibility = View.VISIBLE
            }
        }
    }
}