package technited.minds.gurumantra.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import technited.minds.gurumantra.databinding.ItemListCouponBinding
import technited.minds.gurumantra.model.Coupon
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.system.exitProcess

class CouponsAdapter(private val onItemClicked: (Coupon) -> Unit) : ListAdapter<Coupon, CouponsAdapter
.CouponsViewHolder>(DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponsViewHolder =
        CouponsViewHolder(
            ItemListCouponBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    companion object {
        private val DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<Coupon>() {
            override fun areItemsTheSame(oldItem: Coupon, newItem: Coupon): Boolean =
                oldItem.ccId == newItem.ccId


            override fun areContentsTheSame(oldItem: Coupon, newItem: Coupon): Boolean =
                oldItem == newItem

        }
    }

    override fun onBindViewHolder(holder: CouponsViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    inner class CouponsViewHolder(private val binding: ItemListCouponBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(coupon: Coupon, onItemClicked: (Coupon) -> Unit) {
            binding.coupon = coupon
            val old = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val present = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
            val final = SimpleDateFormat("yyMMddHHmmss")

            val c: Calendar = Calendar.getInstance()
            println("Present Date : " + c.time)

            val formattedDate: Date? = old.parse(coupon.expiredAt)
            val today: Date? = present.parse(c.time.toString())

            val finalToday = final.format(today)
            val finalGiven = final.format(formattedDate)
            println("Date formatted : $finalGiven taday : $finalToday")
            if (finalToday.toString().toDouble() > finalGiven.toString().toDouble()) {
                binding.couponExpired.visibility = View.VISIBLE
                binding.couponActive.visibility = View.GONE
            } else {
                binding.couponExpired.visibility = View.GONE
                binding.couponActive.visibility = View.VISIBLE
            }
        }
    }
}